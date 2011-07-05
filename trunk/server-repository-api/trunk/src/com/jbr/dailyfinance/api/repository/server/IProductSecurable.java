package com.jbr.dailyfinance.api.repository.server;

import com.jbr.dailyfinance.api.repository.client.IProduct;

/**
 *
 * @author jbr
 */
public interface IProductSecurable extends IProduct {
    IUser getUser();
    void setUser(IUser user);
}
