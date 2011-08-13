package com.jbr.dailyfinance.gae.datastore;

import com.google.appengine.api.datastore.Query;
import com.jbr.dailyfinance.api.repository.server.TicketLineSecurable;
import com.jbr.dailyfinance.api.service.TicketLineServices;
import com.jbr.dailyfinance.gae.impl.repository.TicketLineImpl;
import java.util.List;

/**
 *
 * @author jbr
 */
public class TicketLineServicesImpl extends BasicOperationsImpl<TicketLineSecurable>
        implements TicketLineServices {

    public TicketLineServicesImpl() {
        super(TicketLineImpl.class, TicketLineImpl.KIND);
    }

    @Override
    public TicketLineSecurable newEntity() {
        return new TicketLineImpl();
    }

    public List<TicketLineSecurable> list(Long ticketId) {
        if (ticketId == null)
            return super.list();
        final Query q = new Query(kind);
        q.addFilter(TicketLineImpl.p.ticketId.toString(),
                Query.FilterOperator.EQUAL, ticketId);
        return (List)SecuredDatastore.getList(clazz, q, 0, 10000);
    }


}
