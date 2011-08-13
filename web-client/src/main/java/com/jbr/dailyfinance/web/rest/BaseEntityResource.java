package com.jbr.dailyfinance.web.rest;

import com.jbr.dailyfinance.api.repository.server.SecurableEntity;
import com.jbr.dailyfinance.api.service.BasicOperations;
import java.util.List;

/**
 *
 * @author jbr
 */
public class BaseEntityResource<E extends SecurableEntity, B extends BasicOperations<E>> {
    final B bo;

    public BaseEntityResource(B bo) {
        this.bo = bo;
    }

    public E get(Long id) {
        return bo.get(id);
    }

    public E put(E e) {
        return bo.put(e);
    }

    public void delete(E e) {
        bo.delete(e);
    }

    public List<E> list() {
        return bo.list(0, 10000);
    }

    public List<E> list(int startRecord, int records) {
        return bo.list(startRecord, records);
    }
    
    public B getServiceImpl() {
        return bo;
    }


}
