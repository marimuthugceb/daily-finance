package com.jbr.dailyfinance.gae.datastore;

import com.jbr.dailyfinance.api.repository.server.SecurableEntity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.jbr.dailyfinance.gae.impl.repository.UserImpl;

import static com.google.appengine.api.datastore.FetchOptions.Builder.*;
import com.jbr.dailyfinance.gae.impl.repository.DatastoreEntity;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jbr
 */
public class UnsecuredDatastore  {
    final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public static <T extends DatastoreEntity> T put(T entity) {
        Key key = datastore.put(entity.getEntity());
        System.out.println("Puttet key: " + key.getKind());
        return (T) entity;
    }

    public static <T extends DatastoreEntity> T get(Class clazz, String kind, long id)
            throws EntityNotFoundException {
        Entity entity = datastore.get(KeyFactory.createKey(kind, id));
        final T securedEntity = (T) toClazz(clazz, entity);
        return securedEntity;
    }

    public static <T extends DatastoreEntity> T toClazz(Class<T> clazz, Entity entity) throws RuntimeException {
        try {
            Constructor constructor = clazz.getConstructor(Entity.class);
            Object newInstance = constructor.newInstance(entity);
            if (newInstance instanceof SecurableEntity) {
                SecurableEntity secEntity = (SecurableEntity) newInstance;
                return (T)secEntity;
            } else {
                throw new RuntimeException("New instance could not be casted to SecurableEntity");
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void delete(DatastoreEntity entity) {
        datastore.delete(entity.getEntity().getKey());
    }

    public static <T extends DatastoreEntity> List<T> getList(
            Class clazz, Query query,
            int startRow, int rowLimit) {
        if (query == null)
            throw new IllegalArgumentException("query is null");
        PreparedQuery pq = datastore.prepare(query);
        final List<Entity> asList = pq.asList(withOffset(startRow).limit(rowLimit));
        final List<T> toList = new ArrayList<T>(asList.size());
        for (Entity entity : asList) {
            toList.add((T) toClazz(clazz, entity));
        }
        System.out.println("Numbers in list " + toList.size());
        return toList;
    }
}
