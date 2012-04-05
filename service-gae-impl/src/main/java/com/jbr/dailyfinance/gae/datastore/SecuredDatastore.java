package com.jbr.dailyfinance.gae.datastore;

import com.google.appengine.api.memcache.MemcacheService;
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
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.jbr.dailyfinance.gae.impl.repository.DatastoreEntity;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jbr
 */
public class SecuredDatastore  {
    final static UserService userService = UserServiceFactory.getUserService();
    final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    final static UserImpl user = new UserImpl();
    final static MemcacheService cache = MemcacheServiceFactory.getMemcacheService();


    public static <T extends DatastoreEntity> T put(T entity) {
        entity.setUser(user);
        Key key = datastore.put(entity.getEntity());
        System.out.println("Puttet key: " + key.getKind());
        cache.put(key, entity.getEntity());
        System.out.println("Puttet key in cache: " + key.getKind());
        return (T) entity;
    }

    public static <T extends DatastoreEntity> T get(final Key key, Class clazz)
            throws EntityNotFoundException, NotAllowedException, RuntimeException {
        Entity entity = (Entity) cache.get(key);
        if (entity == null) {
            System.out.println("Not found in cache. Fetching from datastore");
            entity = datastore.get(key);
            cache.put(key, entity);
        } else {
            System.out.println("Found in cache!");
        }
        final T securedEntity = (T) toClazz(clazz, entity);
        if (securedEntity.getUser().getEmail().equalsIgnoreCase(
                userService.getCurrentUser().getEmail())) {
            return securedEntity;
        } else {
            throw new NotAllowedException(String.format("No access, to key %s, for user %s",
                    key, userService.getCurrentUser().getEmail()));
        }

    }

    public static <T extends DatastoreEntity> T get(Class clazz, String kind, String name)
            throws EntityNotFoundException, NotAllowedException {
        final Key key = KeyFactory.createKey(kind, name);
        System.out.println("Fetching from cache, " + kind + "-" + name);
        return get(key, clazz);
    }

    public static <T extends DatastoreEntity> T get(Class clazz, String kind, long id)
            throws EntityNotFoundException, NotAllowedException {
        final Key key = KeyFactory.createKey(kind, id);
        System.out.println("Fetching from cache, " + kind + "-" + id);
        return get(key, clazz);
    }

    public static <T extends DatastoreEntity> T toClazz(Class<T> clazz, Entity entity) throws RuntimeException {
        try {
            Constructor constructor = clazz.getConstructor(Entity.class);
            Object newInstance = constructor.newInstance(entity);
            if (newInstance instanceof SecurableEntity) {
                SecurableEntity secEntity = (SecurableEntity) newInstance;
                if (secEntity.getUser().getEmail().equals(userService.getCurrentUser().getEmail())) {
                    return (T)secEntity;
                } else {
                    throw new NotAllowedException();
                }
            } else {
                throw new RuntimeException("New instance could not be casted to SecurableEntity");
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void delete(DatastoreEntity entity) throws NotAllowedException {
        if (entity.getUser().getEmail().equals(userService.getCurrentUser().getEmail())) {
            cache.delete(entity.getEntity().getKey());
            datastore.delete(entity.getEntity().getKey());
        }
        else
            throw new NotAllowedException();
    }

    public static <T extends DatastoreEntity> List<T> getList(
            Class clazz, Query query,
            int startRow, int rowLimit) {
        if (query == null)
            throw new IllegalArgumentException("query is null");
        query.addFilter("user", FilterOperator.EQUAL, userService.getCurrentUser());
        PreparedQuery pq = datastore.prepare(query);

        final List<Entity> asList = pq.asList(withChunkSize(1000).offset(startRow).limit(rowLimit));
        final List<T> toList = new ArrayList<T>(asList.size());
        for (Entity entity : asList) {
            toList.add((T) toClazz(clazz, entity));
        }
        System.out.println("Numbers in list " + toList.size());
        return toList;
    }
}
