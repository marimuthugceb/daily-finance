package com.jbr.dailyfinance.gae.datastore;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Query;
import com.jbr.dailyfinance.api.repository.server.SecurableEntity;
import com.jbr.dailyfinance.api.service.BasicOperations;
import com.jbr.dailyfinance.api.service.exceptions.NotFoundException;
import com.jbr.dailyfinance.gae.impl.repository.DatastoreEntity;
import java.util.List;

/**
 *
 * @author jbr
 */
public abstract class BasicOperationsImpl<E extends SecurableEntity> implements BasicOperations<E> {
    final Class clazz;
    final String kind;

    public BasicOperationsImpl(Class clazz, String kind) {
        this.clazz = clazz;
        this.kind = kind;
    }

    @Override
    public E get(Long id) {
        try {
            return (E)SecuredDatastore.get(clazz, kind, id);
        } catch (EntityNotFoundException ex) {
            throw new NotFoundException(ex);
        } catch (NotAllowedException ex) {
            throw new NotFoundException(ex);
        }
    }

    @Override
    public E put(E entity) {
        return (E) SecuredDatastore.put((DatastoreEntity)entity);
    }

    @Override
    public E putUnsecured(E entity) {
        return (E) UnsecuredDatastore.put((DatastoreEntity)entity);
    }

    @Override
    public void delete(E entity) {
        try {
            SecuredDatastore.delete((DatastoreEntity)entity);
        } catch (NotAllowedException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<E> list() {
        return list(0, Integer.MAX_VALUE);
    }

    @Override
    public List<E> listUnsecured() {
        return listUnsecured(0, Integer.MAX_VALUE);
    }

    @Override
    public List<E> list(int startRecord, int records) {
        return (List)SecuredDatastore.getList(clazz,
                new Query(kind), startRecord, records);
    }

    @Override
    public List<E> listUnsecured(int startRecord, int records) {
        return (List)UnsecuredDatastore.getList(clazz,
                new Query(kind), startRecord, records);
    }
}
