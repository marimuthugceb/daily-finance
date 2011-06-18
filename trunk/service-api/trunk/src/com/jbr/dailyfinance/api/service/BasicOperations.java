package com.jbr.dailyfinance.api.service;

import com.jbr.dailyfinance.api.repository.Entity;

/**
 *
 * @author jbr
 */
public interface BasicOperations<E extends Entity> {
    E get(Long id);
    E put(E entity);
    void delete(E entity);

}
