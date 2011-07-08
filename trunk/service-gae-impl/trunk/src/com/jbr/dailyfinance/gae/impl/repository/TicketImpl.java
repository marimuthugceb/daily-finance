package com.jbr.dailyfinance.gae.impl.repository;

import com.google.appengine.api.datastore.Entity;
import com.jbr.dailyfinance.api.repository.client.Ticket;
import com.jbr.dailyfinance.api.repository.server.TicketSecurable;
import java.util.Date;

/**
 *
 * @author jbr
 */
public class TicketImpl extends BaseEntity implements Ticket, TicketSecurable {
    public static final String KIND = "ticket";

    private enum p {
        ticketDate,
        storeId;
    }

    public TicketImpl(Long id) {
        super(id, KIND);
    }

    public TicketImpl() {
        super(KIND);
    }

    public TicketImpl(Entity entity) {
        super(entity);
    }

    @Override
    public Long getStoreId() {
        return (Long) entity.getProperty(p.storeId.toString());
    }

    @Override
    public Date getTicketDate() {
        return (Date) entity.getProperty(p.ticketDate.toString());
    }

    @Override
    public void setStoreId(Long mStoreId) {
        entity.setProperty(p.storeId.toString(), mStoreId);
    }

    @Override
    public void setTicketDate(Date mTicketDate) {
        entity.setProperty(p.ticketDate.toString(), mTicketDate);
    }

}
