package com.jbr.dailyfinance.gae.datastore;

/**
 *
 * @author jbr
 */
public class NotAllowedException extends Exception {

    public NotAllowedException() {
        super();
    }

    public NotAllowedException(String message) {
        super(message);
        System.out.println("Method not allowed: " + message);
    }

}
