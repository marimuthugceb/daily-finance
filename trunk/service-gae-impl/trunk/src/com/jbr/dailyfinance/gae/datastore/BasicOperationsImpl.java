/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jbr.dailyfinance.gae.datastore;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.jbr.dailyfinance.api.service.BasicOperations;
import com.jbr.dailyfinance.api.service.exceptions.NotFoundException;
import com.jbr.dailyfinance.gae.impl.repository.SecurableEntity;
import java.util.logging.Logger;

/**
 *
 * @author jbr
 */
public class BasicOperationsImpl<E extends SecurableEntity> implements BasicOperations<E> {
    final Class clazz;

    public BasicOperationsImpl(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public E get(Long id) {
        try {
            return SecuredDatastore.get(clazz, id);
        } catch (EntityNotFoundException ex) {
            throw new NotFoundException(ex);
        } catch (NotAllowedException ex) {
            throw new NotFoundException(ex);
        }
    }

    @Override
    public E put(E entity) {
        return SecuredDatastore.put(entity);
    }

    @Override
    public void delete(E entity) {
        try {
            SecuredDatastore.delete(entity);
        } catch (NotAllowedException ex) {
            Logger.getLogger(BasicOperationsImpl.class.getSimpleName()).
                    info(ex.getMessage());
            return;
        }
    }

}
