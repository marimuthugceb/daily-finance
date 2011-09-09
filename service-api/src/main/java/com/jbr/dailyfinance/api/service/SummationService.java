package com.jbr.dailyfinance.api.service;

import com.jbr.dailyfinance.api.repository.client.SumCategoryType;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jbr
 */
public interface SummationService {

    List<SumCategoryType> getSumOfMonthByCategoryType(Date month);
    void updateSumCategories();
}
