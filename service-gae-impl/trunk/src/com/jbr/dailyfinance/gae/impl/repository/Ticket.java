package com.jbr.dailyfinance.gae.impl.repository;

import com.google.appengine.api.datastore.Entity;
import com.jbr.dailyfinance.api.repository.client.ITicket;

/**
 *
 * @author jbr
 */
public class Ticket extends BaseEntity implements ITicket {
    private static final String KIND = "ticket";

    private enum p {
        ticketDate,
        storeId;
    }

    public Ticket(Long id) {
        super(id, KIND);
    }

    public Ticket() {
        super(KIND);
    }

    public Ticket(Entity entity) {
        super(entity);
    }

    @Override
    public Long getStoreId() {
        return (Long) entity.getProperty(p.storeId.toString());
    }

    @Override
    public String getTicketDate() {
        return (String) entity.getProperty(p.ticketDate.toString());
    }

    @Override
    public void setStoreId(Long mStoreId) {
        entity.setProperty(p.storeId.toString(), mStoreId);
    }

    @Override
    public void setTicketDate(String mTicketDate) {
        entity.setProperty(p.ticketDate.toString(), mTicketDate);
    }

}
