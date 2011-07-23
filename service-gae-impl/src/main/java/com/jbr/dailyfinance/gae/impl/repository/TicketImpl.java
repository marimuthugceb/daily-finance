package com.jbr.dailyfinance.gae.impl.repository;

import com.google.appengine.api.datastore.Entity;
import com.jbr.dailyfinance.api.repository.client.Ticket;
import com.jbr.dailyfinance.api.repository.server.TicketSecurable;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jbr
 */
@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.NONE)
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

    @XmlElement
    @Override
    public Long getStoreId() {
        return (Long) entity.getProperty(p.storeId.toString());
    }

    @XmlElement
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

    @Override
    public String toString() {
        return "Ticket: {id:" + getId() + ", ticketDate:" + getTicketDate()  +  ", storeId:" + getStoreId() + "}";
    }

}
