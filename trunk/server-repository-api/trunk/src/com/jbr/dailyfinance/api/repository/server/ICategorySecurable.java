package com.jbr.dailyfinance.api.repository.server;

import com.jbr.dailyfinance.api.repository.client.ICategory;

/**
 *
 * @author jbr
 */
public interface ICategorySecurable extends ICategory {
    IUser getUser();
    void setUser(IUser user);
}
