package com.jbr.dailyfinance.api.repository;

/**
 *
 * @author jbr
 */
public interface ITicket {

    Long getId();
    Long getStoreId();
    String getTicketDate();
    void setStoreId(Long mStoreId);
    void setTicketDate(String mTicketDate);

}
