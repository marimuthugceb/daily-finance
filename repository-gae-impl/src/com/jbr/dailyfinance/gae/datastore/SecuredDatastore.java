package com.jbr.dailyfinance.gae.datastore;

import com.jbr.dailyfinance.gae.impl.repository.SecurableEntity;
import java.util.ArrayList;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.lang.reflect.Constructor;
import java.util.List;
import static com.google.appengine.api.datastore.FetchOptions.Builder.*;

/**
 *
 * @author jbr
 */
public class SecuredDatastore {
    final static UserService userService = UserServiceFactory.getUserService();
    final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public static <T extends SecurableEntity> T put(T entity) {
        entity.setUser(userService.getCurrentUser());
        Key key = datastore.put(entity.getEntity());
        return (T) entity;
    }

    public static <T extends SecurableEntity> T get(Class clazz, long id)
            throws EntityNotFoundException, NotAllowedException {
        Entity entity = datastore.get(KeyFactory.createKey(clazz.getSimpleName(), id));
        final T securedEntity = (T) toClazz(clazz, entity);
        if (securedEntity.getUser().getEmail().equalsIgnoreCase(
                userService.getCurrentUser().getEmail()))
            return securedEntity;
        else
            throw new NotAllowedException("No access"); 
    }

    public static <T extends SecurableEntity> T toClazz(Class<T> clazz, Entity entity) throws RuntimeException {
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

    public static void delete(SecurableEntity entity) throws NotAllowedException {
        if (entity.getUser().getEmail().equals(userService.getCurrentUser().getEmail()))
            datastore.delete(entity.getEntity().getKey());
        else
            throw new NotAllowedException();
    }

    public static <T extends SecurableEntity> List<T> getList(Class clazz, Query query, int rowLimit) {
        query.addFilter("user", FilterOperator.EQUAL, userService.getCurrentUser());
        PreparedQuery pq = datastore.prepare(query);
        final List<Entity> asList = pq.asList(withLimit(rowLimit));
        final List<T> toList = new ArrayList<T>(asList.size());
        for (Entity entity : asList) {
            toList.add((T) toClazz(clazz, entity));
        }
        return toList;
    }

    /*
    public Query newQuery(String sql) {
        if (sql.toLowerCase().indexOf("where")>-1) {
            String[] split = sql.split("where ");
            sql = split[0] + " where user == '" + userService.getCurrentUser() + "' and " + split[1];
        } else {
            String[] split = sql.split(" order by ");
            sql = split[0] + " where user == '" + userService.getCurrentUser() + "' order by " + split[1];

        }
        System.out.println("SQL is " + sql);
        return pm.newQuery(sql);
    }
     * 
     */


}
