package com.jbr.dailyfinance.api.repository.server;

import com.jbr.dailyfinance.api.repository.client.ITicketLine;

/**
 *
 * @author jbr
 */
public interface ITicketLineSecurable extends ITicketLine {
    IUser getUser();
    void setUser(IUser user);
}
