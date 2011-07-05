package com.jbr.dailyfinance.api.repository.server;

import com.jbr.dailyfinance.api.repository.client.Entity;

/**
 *
 * @author jbr
 */
public interface SecurableEntity<E extends Entity> extends Entity {
    public IUser getUser();
    public void setUser(IUser user);
}
