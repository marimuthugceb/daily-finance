package com.jbr.dailyfinance.api.service;

import com.jbr.dailyfinance.api.repository.client.SumCategory;
import com.jbr.dailyfinance.api.repository.server.SumCategorySecurable;
import java.util.Date;

/**
 *
 * @author jbr
 */
public interface SumCategoryServices extends BasicOperations<SumCategorySecurable> {
    public SumCategory get(Date sumDate, long categoryId);
}
