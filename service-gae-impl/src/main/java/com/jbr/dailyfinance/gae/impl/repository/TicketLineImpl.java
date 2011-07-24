package com.jbr.dailyfinance.gae.impl.repository;


import com.google.appengine.api.datastore.Entity;
import com.jbr.dailyfinance.api.repository.client.TicketLine;
import com.jbr.dailyfinance.api.repository.server.TicketLineSecurable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jbr
 */
@XmlRootElement(name = "ticketline")
@XmlAccessorType(XmlAccessType.NONE)
public class TicketLineImpl extends BaseEntity implements TicketLine, TicketLineSecurable {
    public static final String KIND = "ticketline";

    private enum p {
        number,
        productId,
        categoryId,
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

    @XmlElement
    @Override
    public Double getAmount() {
        return (Double) entity.getProperty(p.amount.toString());
    }

    @XmlElement
    @Override
    public Integer getNumber() {
        return ((Long) entity.getProperty(p.number.toString())).intValue();
    }

    @XmlElement
    @Override
    public Long getProductId() {
        return (Long) entity.getProperty(p.productId.toString());
    }

    @XmlElement
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
        entity.setProperty(p.number.toString(), new Long(mNumber.longValue()));
    }

    @Override
    public void setProductId(Long mProductId) {
        entity.setProperty(p.productId.toString(), mProductId);
    }

    @Override
    public void setTicketId(Long mTicketId) {
        entity.setProperty(p.ticketId.toString(), mTicketId);
    }

    @Override
    public Long getCategoryId() {
        return (Long) entity.getProperty(p.categoryId.toString());
    }

    @Override
    public void setCategoryId(Long mCategoryId) {
        entity.setProperty(p.categoryId.toString(), mCategoryId);
    }

}
