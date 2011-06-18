package com.jbr.dailyfinance.web.rest;

import com.jbr.dailyfinance.gae.datastore.BasicOperationsImpl;
import com.jbr.dailyfinance.gae.impl.repository.SecurableEntity;

/**
 *
 * @author jbr
 */
public class BaseEntityResource<E extends SecurableEntity> {
    final Class eClass;
    final BasicOperationsImpl<E> bo;

    public BaseEntityResource(Class eClass) {
        this.eClass = eClass;
        this.bo = new BasicOperationsImpl<E>(eClass);
    }

    public E getEntity(Long id) {
        return bo.get(id);
    }
    
}
