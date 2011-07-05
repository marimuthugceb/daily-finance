package com.jbr.dailyfinance.api.service;

import com.jbr.dailyfinance.api.repository.client.Entity;
import java.util.List;

/**
 *
 * @author jbr
 */
public interface BasicOperations<E extends Entity> {
    /**
     *
     * @param id of the entity to retrieve
     * @return
     */
    E get(Long id);
    /**
     *
     * @param entity persisted
     * @return entity where persisted id is filled in
     */
    E put(E entity);
    /**
     * Delete the entity from persistence storage
     * @param entity
     */
    void delete(E entity);
    /**
     * Retreive a new empty instance of entity
     */
    E newEntity();

    /**
     * Retrive a list of all entities
     * @return
     */
    List<E> list();
}
