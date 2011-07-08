package com.jbr.dailyfinance.api.repository.client;

/**
 *
 * @author jbr
 */
public interface TicketLine extends Entity {
    Long getId();
    Double getAmount();
    Integer getNumber();
    Long getProductId();
    Long getTicketId();
    void setAmount(Double mAmount);
    void setNumber(Integer mNumber);
    void setProductId(Long mProductId);
    void setTicketId(Long mTicketId);
}
