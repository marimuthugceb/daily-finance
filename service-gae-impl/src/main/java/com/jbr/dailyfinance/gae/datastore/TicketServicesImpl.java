package com.jbr.dailyfinance.gae.datastore;

import com.jbr.dailyfinance.api.repository.client.Ticket;
import com.jbr.dailyfinance.api.repository.server.TicketSecurable;
import com.jbr.dailyfinance.api.service.TicketServices;
import com.jbr.dailyfinance.gae.impl.repository.TicketImpl;

/**
 *
 * @author jbr
 */
public class TicketServicesImpl extends BasicOperationsImpl<TicketSecurable>
        implements TicketServices {

    public TicketServicesImpl() {
        super(Ticket.class, TicketImpl.KIND);
    }

    @Override
    public TicketSecurable newEntity() {
        return new TicketImpl();
    }
}
