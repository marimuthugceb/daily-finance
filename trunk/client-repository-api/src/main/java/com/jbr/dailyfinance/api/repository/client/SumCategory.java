package com.jbr.dailyfinance.api.repository.client;

import java.util.Date;

/**
 *
 * @author jbr
 */
public interface SumCategory extends Entity {
    Date getSumDate();
    Long getCategoryId();
    Double getSum();
}
