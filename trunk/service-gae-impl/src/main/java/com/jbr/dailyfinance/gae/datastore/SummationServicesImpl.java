package com.jbr.dailyfinance.gae.datastore;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.jbr.dailyfinance.api.service.SummationService;
import com.jbr.dailyfinance.api.repository.client.Category;
import com.jbr.dailyfinance.api.repository.client.Category.Type;
import com.jbr.dailyfinance.api.repository.client.SumCategoryType;
import com.jbr.dailyfinance.api.repository.server.SumCategorySecurable;
import com.jbr.dailyfinance.api.repository.server.TicketLineSecurable;
import com.jbr.dailyfinance.api.repository.server.TicketSecurable;
import com.jbr.dailyfinance.gae.impl.repository.SumCategoryImpl;
import com.jbr.dailyfinance.gae.impl.repository.SumCategoryTypeImpl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 *
 * @author jbr
 */
public class SummationServicesImpl implements SummationService {

    private final static MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
    private final static SimpleDateFormat df = new SimpleDateFormat("MMyy");

    private static final UserService userService = UserServiceFactory.getUserService();
    private static final String SUMCATEGORYMONTH_KEY_ID = "SUMCATEGORYMONTH";
    private static final String SUMCATEGORYMONTH_KEYS = "SUMCATEGORYMONTH_KEYS";

    private static final Map<String, SumCategoryImpl> categoryCache =
            new HashMap<String, SumCategoryImpl>();

    private static String getCacheKey(String monthAndYear) {
        return SUMCATEGORYMONTH_KEY_ID + "/" + monthAndYear + "/" + userService.getCurrentUser().getEmail();
    }

    public static String getCacheKey(Date monthAndYear, long id) {
        return SUMCATEGORYMONTH_KEY_ID + "/" + toMonthAndYear(monthAndYear) + "/" + Long.toString(id);
    }

    private static void putSumOfMonthByCategoryTypeInCache(Date month, SumCategoryType sum) {
        System.out.println(String.format("Putting sumOfMonth %s to cache!", toMonthAndYear(month)));
        cache.put(getCacheKey(toMonthAndYear(month)), sum);
    }

    private static SumCategoryType getSumOfMonthByCategoryTypeFromCache(Date month) {
        return (SumCategoryType) cache.get(getCacheKey(toMonthAndYear(month)));
    }

    public static void putSumOfMonthByCategoryInCache(Date month, SumCategoryImpl category) {
        System.out.println(String.format("Putting sumOfMonth %s to cache!",
                toMonthAndYear(month)));
        categoryCache.put(getCacheKey(month, category.getCategoryId()), category);
        cache.put(getCacheKey(month, category.getCategoryId()), category);

    }

    public static SumCategoryImpl getSumOfMonthByCategoryFromCache(Date month,
            long categoryId) {
        return (SumCategoryImpl) categoryCache.get(getCacheKey(month,
                categoryId));
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
        return df.format(month);
    }

    public static Date firstInNextMonth(Date startDate) {
        Calendar c1 = GregorianCalendar.getInstance();
        c1.setTime(startDate);
        c1.add(Calendar.MONTH, 1);
        Date endDate = c1.getTime();
        return endDate;
    }

    public void updateSumCategories(int yyyy) {
        updateSumCategories(0, yyyy);
    }
    /**
     * Update SumCategory sum table in datastore.
     * This should be run by a scheduler / cron
     *
     *
     */
    @Override
    public void updateSumCategories(int mm, int yyyy) {
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
        categoryCache.clear();

        //List<TicketSecurable> tls = tsserv.listUnsecured();
        System.out.println("Searching for tickets...");
        List<TicketSecurable> tls;
        if (mm == 0)
            tls = tsserv.getTickets(new Date(yyyy-1900, 0, 1),
                new Date(yyyy-1900, 11, 31));
        else
            tls = tsserv.getTickets(new Date(yyyy-1900, mm-1, 1),
                new Date(yyyy-1900, mm, 1));
        System.out.println(String.format("Summing %s tickets", tls.size()));

        for (TicketSecurable t : tls) {
            System.out.println("Summing ticketid " + t.getId());
            for (TicketLineSecurable tl : tlserv.listForAllUsers(t.getId())) {
                addToSumCategory(t, tl);
            }
        }

        System.out.println("Done summing");

        // Persist sums
        //scserv.deleteAll();
        System.out.println(String.format("Persisting %s sumcategories.",
                categoryCache.keySet().size()));
        for (String key : categoryCache.keySet()) {
            SumCategoryImpl sc = categoryCache.get(key);
            scserv.putUnsecured(sc);
        }
        System.out.println(String.format("Persisting %s sumcategories to memcache.",
                categoryCache.keySet().size()));
        HashSet<String> keys = (HashSet<String>) cache.get(SUMCATEGORYMONTH_KEYS);
        if (keys == null)
            keys = new HashSet<String>();
        keys.addAll(categoryCache.keySet());
        cache.put(SUMCATEGORYMONTH_KEYS, keys);
    }


    public static List<SumCategorySecurable> list(Date startdate, Date enddate) {
        Set<String> keys = (Set<String>)cache.get(SUMCATEGORYMONTH_KEYS);
        if (keys == null) {
            System.out.println("No keys in memcache");
            return Collections.EMPTY_LIST;
        }
        ArrayList<SumCategorySecurable> list = new ArrayList<SumCategorySecurable>();
        for (String key : keys) {
            SumCategoryImpl sc = (SumCategoryImpl) cache.get(key);
            if (sc == null)
                continue;
            if (sc.getSumDate().before(startdate) ||
                    sc.getSumDate().after(enddate))
                continue;
            list.add(sc);
        }
        System.out.println(String.format("Returning %s sumcategories between %s and %s",
                list.size(), startdate, enddate));
        return list;
    }

    private void addToSumCategory(TicketSecurable t, TicketLineSecurable tl) {
        Date firstInMonth = firstInMonth(t.getTicketDate());
        SumCategoryImpl sc = getSumOfMonthByCategoryFromCache(
                firstInMonth, tl.getCategoryId());
        if (sc == null) {
            sc = new SumCategoryImpl(getCacheKey(firstInMonth,
                    tl.getCategoryId()));
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
        putSumOfMonthByCategoryInCache(firstInMonth, sc);
    }

}
