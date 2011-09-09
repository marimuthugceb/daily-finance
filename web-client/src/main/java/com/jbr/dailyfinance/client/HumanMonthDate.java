package com.jbr.dailyfinance.client;

import com.google.gwt.i18n.client.DateTimeFormat;

/**
 *
 * @author jbr
 */
public class HumanMonthDate {

    public static DateTimeFormat getFormat() {
        return DateTimeFormat.getFormat("MMM yyyy");
    }
}
