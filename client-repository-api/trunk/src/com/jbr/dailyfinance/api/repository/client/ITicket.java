package com.jbr.dailyfinance.api.repository.client;

/**
 *
 * @author jbr
 */
public interface ITicket extends Entity {

    Long getId();
    Long getStoreId();
    String getTicketDate();
    void setStoreId(Long mStoreId);
    void setTicketDate(String mTicketDate);

}
