package com.jbr.dailyfinance.client;

import com.google.gwt.i18n.client.DateTimeFormat;

/**
 *
 * @author jbr
 */
public class HumanDate {

    public static DateTimeFormat getFormat() {
        return DateTimeFormat.getFormat("dd.MM.yyyy");
    }
}
