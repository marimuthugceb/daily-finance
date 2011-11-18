package com.jbr.dailyfinance.api.repository.client;

import java.util.Date;
import java.util.Map;

/**
 *
 * @author jbr
 */
public interface SumCategoryType extends Entity {
    Date getSumDate();
    Map<Category.Type, Double> getSumWithType();
}
