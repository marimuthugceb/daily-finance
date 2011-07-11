package com.jbr.dailyfinance.gae.impl.repository;


import com.google.appengine.api.datastore.Entity;
import com.jbr.dailyfinance.api.repository.client.TicketLine;
import com.jbr.dailyfinance.api.repository.server.TicketLineSecurable;

/**
 *
 * @author jbr
 */
public class TicketLineImpl extends BaseEntity implements TicketLine, TicketLineSecurable {
    public static final String KIND = "ticketline";

    private enum p {
        number,
        productId,
        ticketId,
        amount;
    }

    public TicketLineImpl(Long id) {
        super(id, KIND);
    }

    public TicketLineImpl() {
        super(KIND);
    }

    public TicketLineImpl(Entity entity) {
        super(entity);
    }


    @Override
    public Double getAmount() {
        return (Double) entity.getProperty(p.amount.toString());
    }

    @Override
    public Integer getNumber() {
        return (Integer) entity.getProperty(p.number.toString());
    }

    @Override
    public Long getProductId() {
        return (Long) entity.getProperty(p.productId.toString());
    }

    @Override
    public Long getTicketId() {
        return (Long) entity.getProperty(p.ticketId.toString());
    }

    @Override
    public void setAmount(Double mAmount) {
        entity.setProperty(p.amount.toString(), mAmount);
    }

    @Override
    public void setNumber(Integer mNumber) {
        entity.setProperty(p.number.toString(), mNumber);
    }

    @Override
    public void setProductId(Long mProductId) {
        entity.setProperty(p.productId.toString(), mProductId);
    }

    @Override
    public void setTicketId(Long mTicketId) {
        entity.setProperty(p.ticketId.toString(), mTicketId);
    }

}
