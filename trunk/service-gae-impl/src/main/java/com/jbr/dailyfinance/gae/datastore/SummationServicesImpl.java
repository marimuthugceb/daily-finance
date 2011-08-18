package com.jbr.dailyfinance.gae.datastore;

import com.jbr.dailyfinance.api.repository.client.Category;
import com.jbr.dailyfinance.api.repository.client.Category.Type;
import com.jbr.dailyfinance.api.repository.client.SumCategoryType;
import com.jbr.dailyfinance.api.repository.server.TicketLineSecurable;
import com.jbr.dailyfinance.api.repository.server.TicketSecurable;
import com.jbr.dailyfinance.gae.impl.repository.SumCategoryTypeImpl;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.List;


/**
 *
 * @author jbr
 */
public class SummationServicesImpl {
    public List<SumCategoryTypeImpl> getSumOfMonthByCategoryType(Date month) {
        EnumMap<Category.Type, Double> returnMap = new EnumMap<Category.Type, Double>(Category.Type.class);
        returnMap.put(Type.food, Double.valueOf(0d));
        returnMap.put(Type.nonFood, Double.valueOf(0d));
        Calendar c = GregorianCalendar.getInstance();
        c.setTime(month);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date startDate = c.getTime();

        Calendar c1 = GregorianCalendar.getInstance();
        c1.setTime(startDate);
        c1.add(Calendar.MONTH, 1);
        Date endDate = c1.getTime();

        TicketServicesImpl tsi = new TicketServicesImpl();
        List<TicketSecurable> tickets = tsi.getTickets(startDate, endDate);

        TicketLineServicesImpl tlsi = new TicketLineServicesImpl();
        CategoryServicesImpl csi = new CategoryServicesImpl();

        for (TicketSecurable ticket : tickets) {
            List<TicketLineSecurable> ticketLines = tlsi.list(ticket.getId());
            for (TicketLineSecurable tls : ticketLines) {
                System.out.println("categoryid=" + tls.getCategoryId());
                System.out.println("categoryname=" + csi.get(tls.getCategoryId()).getName());
                Type type = csi.get(tls.getCategoryId()).getType();
                returnMap.put(type, returnMap.get(type).doubleValue() + tls.getAmount());
            }

        }
        System.out.println("Sum map: " + returnMap.toString());
        return Arrays.asList(
                new SumCategoryTypeImpl(Type.food, returnMap.get(Type.food)),
                new SumCategoryTypeImpl(Type.nonFood, returnMap.get(Type.nonFood)));
    }
}
