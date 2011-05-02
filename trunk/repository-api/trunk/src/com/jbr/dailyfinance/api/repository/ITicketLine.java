package com.jbr.dailyfinance.api.repository;

/**
 *
 * @author jbr
 */
public interface ITicketLine {
    Long getId();
    Long getAmount();
    Integer getNumber();
    Long getProductId();
    Long getTicketId();
    void setAmount(Long mAmount);
    void setNumber(Integer mNumber);
    void setProductId(Long mProductId);
    void setTicketId(Long mTicketId);
}
