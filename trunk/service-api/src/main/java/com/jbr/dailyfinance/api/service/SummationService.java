package com.jbr.dailyfinance.api.service;

import com.jbr.dailyfinance.api.repository.client.SumCategoryType;
import java.util.Date;

/**
 *
 * @author jbr
 */
public interface SummationService {

    SumCategoryType getSumOfMonthByCategoryType(Date month);
    void updateSumCategories(int mm, int yyyy);
}
