package com.jbr.dailyfinance.api.repository.client;

import java.util.Date;

/**
 *
 * @author jbr
 */
public interface Ticket extends Entity {

    Long getId();
    Long getStoreId();
    Date getTicketDate();
    void setStoreId(Long mStoreId);
    void setTicketDate(Date mTicketDate);

}
