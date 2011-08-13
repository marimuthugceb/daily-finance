package com.jbr.dailyfinance.web.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author jbr
 */
public class ISODate {
    public static DateFormat dateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }
}
