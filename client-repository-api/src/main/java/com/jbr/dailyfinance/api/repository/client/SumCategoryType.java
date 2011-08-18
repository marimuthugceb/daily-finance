package com.jbr.dailyfinance.api.repository.client;

/**
 *
 * @author jbr
 */
public interface SumCategoryType extends Entity {

    Double getSum();
    Category.Type getCategoryType();
}
