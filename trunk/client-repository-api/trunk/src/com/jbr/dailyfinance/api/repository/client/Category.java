package com.jbr.dailyfinance.api.repository.client;

/**
 *
 * @author jbr
 */
public interface Category extends Entity {

    public enum Type {
        food,
        nonFood;
    }

    Long getId();
    String getName();
    Type getType();
    void setName(String mName);
    void setType(Type mType);
}
