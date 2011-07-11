package com.jbr.dailyfinance.gae.impl.repository;

import com.google.appengine.api.datastore.Entity;
import com.jbr.dailyfinance.api.repository.server.SecurableEntity;

/**
 *
 * @author jbr
 */
public interface DatastoreEntity extends SecurableEntity {
    Entity getEntity();
}
