package com.jbr.dailyfinance.api.repository.client;

/**
 *
 * @author jbr
 */
public interface IStore extends Entity {
    Long getId();
    String getName();
    void setName(String name);

}
