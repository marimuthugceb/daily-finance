package com.jbr.dailyfinance.gae.impl.repository;


import com.google.appengine.api.datastore.Entity;
import com.jbr.dailyfinance.api.repository.client.ITicketLine;

/**
 *
 * @author jbr
 */
public class TicketLine extends BaseEntity implements ITicketLine {
    private static final String KIND = "ticketline";

    private enum p {
        number,
        productId,
        ticketId,
        amount;
    }

    public TicketLine(Long id) {
        super(id, KIND);
    }

    public TicketLine() {
        super(KIND);
    }

    public TicketLine(Entity entity) {
        super(entity);
    }


    @Override
    public Long getAmount() {
        return (Long) entity.getProperty(p.amount.toString());
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
    public void setAmount(Long mAmount) {
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
