package com.jbr.dailyfinance.api.repository.client;

import java.util.Date;

/**
 *
 * @author jbr
 */
public interface SumCategoryType extends Entity {

    Date getSumDate();
    Double getSum();
    Category.Type getCategoryType();
}
