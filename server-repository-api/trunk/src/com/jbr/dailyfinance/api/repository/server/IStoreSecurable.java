package com.jbr.dailyfinance.api.repository.server;

import com.jbr.dailyfinance.api.repository.client.IStore;

/**
 *
 * @author jbr
 */
public interface IStoreSecurable extends IStore {
    IUser getUser();
    void setUser(IUser user);
}
