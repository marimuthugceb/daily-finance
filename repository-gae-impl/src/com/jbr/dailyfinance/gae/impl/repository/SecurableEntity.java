package com.jbr.dailyfinance.gae.impl.repository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.users.User;

/**
 *
 * @author jbr
 */
public interface SecurableEntity {
    public User getUser();
    public void setUser(User user);
    public Entity getEntity();

}
