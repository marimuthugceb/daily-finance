package com.jbr.dailyfinance.gae.datastore;

import com.jbr.dailyfinance.api.repository.client.TicketLine;
import com.jbr.dailyfinance.api.repository.server.TicketLineSecurable;
import com.jbr.dailyfinance.api.service.TicketLineServices;
import com.jbr.dailyfinance.gae.impl.repository.TicketLineImpl;

/**
 *
 * @author jbr
 */
public class TicketLineServicesImpl extends BasicOperationsImpl<TicketLineSecurable>
        implements TicketLineServices {

    public TicketLineServicesImpl() {
        super(TicketLine.class, TicketLineImpl.KIND);
    }

    @Override
    public TicketLineSecurable newEntity() {
        return new TicketLineImpl();
    }
}
