package com.jbr.dailyfinance.api.repository.server;

import com.jbr.dailyfinance.api.repository.client.Entity;

/**
 *
 * @author jbr
 */
public interface SecurableEntity extends Entity {
    public User getUser();
    public void setUser(User user);
}
