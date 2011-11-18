package com.jbr.dailyfinance.api.repository.client;

import java.util.Date;
import java.util.List;

/**
 *
 * @author jbr
 */
public interface SumCategory extends Entity {
    Date getSumDate();
    Long getCategoryId();
    Double getSum();
    List<Long> getTicketIds();
    List<Long> getTicketLineIds();
}
