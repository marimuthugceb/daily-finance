package com.jbr.dailyfinance.gae.datastore;

import com.google.appengine.api.datastore.Query;
import com.jbr.dailyfinance.api.repository.server.TicketSecurable;
import com.jbr.dailyfinance.api.service.TicketServices;
import com.jbr.dailyfinance.gae.impl.repository.TicketImpl;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jbr
 */
public class TicketServicesImpl extends BasicOperationsImpl<TicketSecurable>
        implements TicketServices {

    public TicketServicesImpl() {
        super(TicketImpl.class, TicketImpl.KIND);
    }

    @Override
    public TicketSecurable newEntity() {
        return new TicketImpl();
    }

    @Override
    public List<TicketSecurable> list(int startRecord, int records) {
        System.out.println("Getting sorted list of tickets. ticketdate desc");
        final Query q = new Query(kind);
        q.addSort(TicketImpl.p.ticketDate.toString(), Query.SortDirection.DESCENDING);
        return (List)SecuredDatastore.getList(clazz, q, startRecord, records);
    }

    public List<TicketSecurable> getTickets(Date ticketDate) {
        if (ticketDate == null)
            return super.list();

        final Query q = new Query(kind);
        q.addFilter(TicketImpl.p.ticketDate.toString(), Query.FilterOperator.EQUAL, ticketDate);
        return (List)SecuredDatastore.getList(clazz, q, 0, 10000);
    }

    public List<TicketSecurable> getTickets(Date startDate, Date endDate) {
        if (startDate == null || endDate == null)
            throw new IllegalArgumentException("startDate or endDate is null");

        Query qTickets = new Query(kind);

        qTickets.addFilter(TicketImpl.p.ticketDate.toString(),
                Query.FilterOperator.GREATER_THAN_OR_EQUAL, startDate);

        qTickets.addFilter(TicketImpl.p.ticketDate.toString(),
                Query.FilterOperator.LESS_THAN, endDate);

        return (List)SecuredDatastore.getList(clazz, qTickets, 0, 10000);

    }
}
