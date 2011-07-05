package com.jbr.dailyfinance.api.repository.server;

import com.jbr.dailyfinance.api.repository.client.ITicket;

/**
 *
 * @author jbr
 */
public interface ITicketSecurable extends ITicket {
    IUser getUser();
    void setUser(IUser user);
}
