package com.jbr.dailyfinance.gae.datastore;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.jbr.dailyfinance.api.service.SummationService;
import com.jbr.dailyfinance.api.repository.client.Category;
import com.jbr.dailyfinance.api.repository.client.Category.Type;
import com.jbr.dailyfinance.api.repository.client.SumCategoryType;
import com.jbr.dailyfinance.api.repository.server.TicketLineSecurable;
import com.jbr.dailyfinance.api.repository.server.TicketSecurable;
import com.jbr.dailyfinance.gae.impl.repository.SumCategoryImpl;
import com.jbr.dailyfinance.gae.impl.repository.SumCategoryTypeImpl;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.List;


/**
 *
 * @author jbr
 */
public class SummationServicesImpl implements SummationService {

    private final static MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMyy");

    private static final UserService userService = UserServiceFactory.getUserService();
    private static final String SUMCATEGORYMONTH_KEY_ID = "SUMCATEGORYMONTH";

    private static String getCacheKey(String monthAndYear) {
        return SUMCATEGORYMONTH_KEY_ID + "/" + monthAndYear + "/" + userService.getCurrentUser().getEmail();
    }

    private static void putSumOfMonthByCategoryTypeInCache(Date month, SumCategoryType sum) {
        System.out.println(String.format("Putting sumOfMonth %s to cache!", toMonthAndYear(month)));
        cache.put(getCacheKey(toMonthAndYear(month)), sum);
    }

    private static SumCategoryType getSumOfMonthByCategoryTypeFromCache(Date month) {
        return (SumCategoryType) cache.get(getCacheKey(toMonthAndYear(month)));
    }

    /**
     * To update cache
     * @param sct
     */
    public static void addToSumOfMonth(Date date, Category.Type categoryType, double amount) {
        System.out.println("Adding amount to month sum in cache");
        SumCategoryType sumCache =
                getSumOfMonthByCategoryTypeFromCache(date);
        if (sumCache == null) {
            System.out.println(String.format("New month %s created in cache", toMonthAndYear(date)));
            sumCache = new SumCategoryTypeImpl(date, categoryType, amount);
        } else {
            Double sum = sumCache.getSumWithType().get(categoryType);
            if (sum == null)
                sum = amount;
            else
                sum+=amount;
            System.out.println(String.format("Putting new sum %s in cache", sum));
            sumCache.getSumWithType().put(categoryType, sum);
            putSumOfMonthByCategoryTypeInCache(date, sumCache);
        }
    }


    @Override
    public SumCategoryType getSumOfMonthByCategoryType(Date month) {
        final SumCategoryType sumOfMonthByCategoryType =
                getSumOfMonthByCategoryTypeFromCache(month);
        if (sumOfMonthByCategoryType != null) {
            System.out.println(String.format("Returning sumOfMonth %s from cache as %s!",
                    toMonthAndYear(month), sumOfMonthByCategoryType));
            return sumOfMonthByCategoryType;
        }

        EnumMap<Category.Type, Double> returnMap = new EnumMap<Category.Type, Double>(Category.Type.class);
        returnMap.put(Type.food, Double.valueOf(0d));
        returnMap.put(Type.nonFood, Double.valueOf(0d));
        Date startDate = firstInMonth(month);
        Date endDate = firstInNextMonth(startDate);

        TicketServicesImpl tsi = new TicketServicesImpl();
        List<TicketSecurable> tickets = tsi.getTickets(startDate, endDate);

        TicketLineServicesImpl tlsi = new TicketLineServicesImpl();
        CategoryServicesImpl csi = new CategoryServicesImpl();

        for (TicketSecurable ticket : tickets) {
            List<TicketLineSecurable> ticketLines = tlsi.listForAllUsers(ticket.getId());
            for (TicketLineSecurable tls : ticketLines) {
                System.out.println("categoryid=" + tls.getCategoryId());
                System.out.println("categoryname=" + csi.get(tls.getCategoryId()).getName());
                Type type = csi.get(tls.getCategoryId()).getType();
                returnMap.put(type, returnMap.get(type).doubleValue() + tls.getAmount());
            }

        }
        System.out.println("Sum map: " + returnMap.toString() + " for " + toMonthAndYear(month));
        SumCategoryTypeImpl sumCategoryType = new SumCategoryTypeImpl();
        sumCategoryType.setSumDate(startDate);
        sumCategoryType.setSumWithType(returnMap);
        putSumOfMonthByCategoryTypeInCache(month, sumCategoryType);
        return sumCategoryType;
    }

    public static Date firstInMonth(Date month) {
        if (month == null)
            throw new IllegalArgumentException("Date is null");
        Calendar c = GregorianCalendar.getInstance();
        c.setTime(month);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date startDate = c.getTime();
        return startDate;
    }

    public static String toMonthAndYear(Date month) {
        return simpleDateFormat.format(month);
    }

    public static Date firstInNextMonth(Date startDate) {
        Calendar c1 = GregorianCalendar.getInstance();
        c1.setTime(startDate);
        c1.add(Calendar.MONTH, 1);
        Date endDate = c1.getTime();
        return endDate;
    }

    /**
     * Update SumCategory sum table in datastore.
     * This should be run by a scheduler / cron
     *
     *
     */
    @Override
    public void updateSumCategories() {
        // Iterate through all tickets.
        // Transform ticketdato to the 1st of the month
        // Iterate through the ticketlines of the ticket
        // get SumCategory of date and categoryid.
        // if found then add amount to sum.
        // if not found create a new and set sum = amount.
        // put the SumCategory back to datastore.
        // take next ticketline
        // take next ticket
        final TicketServicesImpl tsserv = new TicketServicesImpl();
        final TicketLineServicesImpl tlserv = new TicketLineServicesImpl();
        final SumCategoryServicesImpl scserv = new SumCategoryServicesImpl();
        scserv.deleteAll();

        List<TicketSecurable> tls = tsserv.listUnsecured();
        for (TicketSecurable t : tls) {
            Date firstInMonth = firstInMonth(t.getTicketDate());
            for (TicketLineSecurable tl : tlserv.listForAllUsers(t.getId())) {
                SumCategoryImpl sc = (SumCategoryImpl) scserv
                        .get(firstInMonth, tl.getCategoryId());
                if (sc == null) {
                    sc = new SumCategoryImpl();
                    sc.setCategoryId(tl.getCategoryId());
                    sc.setSumDate(firstInMonth);
                    sc.setSum(tl.getAmount());
                    sc.setUser(tl.getUser());
                    sc.setTicketIds(t.getId().toString() + ",");
                    sc.setTicketLineIds(tl.getId().toString() + ",");

                } else {
                    sc.setSum(tl.getAmount() + sc.getSum());
                    sc.addTicketId(t.getId());
                    sc.addTicketLineId(tl.getId());
                }
                scserv.putUnsecured(sc);
            }
        }
    }
}
