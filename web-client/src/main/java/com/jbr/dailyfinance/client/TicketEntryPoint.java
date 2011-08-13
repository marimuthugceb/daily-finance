package com.jbr.dailyfinance.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.jbr.dailyfinance.client.ui.TicketsUI;

/**
 * Main entry point.
 *
 * @author jbr
 */
public class TicketEntryPoint implements EntryPoint {
    /** 
     * Creates a new instance of gwtEntryPoint
     */
    public TicketEntryPoint() {
    }

    /** 
     * The entry point method, called automatically by loading a module
     * that declares an implementing class as an entry-point
     */
    public void onModuleLoad() {
        RootLayoutPanel.get().add(new TicketsUI());
    }
}
