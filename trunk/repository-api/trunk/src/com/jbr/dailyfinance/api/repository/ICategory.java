package com.jbr.dailyfinance.api.repository;

/**
 *
 * @author jbr
 */
public interface ICategory extends Entity {

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
