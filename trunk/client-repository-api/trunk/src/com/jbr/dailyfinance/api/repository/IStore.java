package com.jbr.dailyfinance.api.repository;

/**
 *
 * @author jbr
 */
public interface IStore extends Entity {
    Long getId();
    String getName();
    void setName(String name);

}
