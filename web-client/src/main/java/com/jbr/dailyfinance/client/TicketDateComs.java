package com.jbr.dailyfinance.client;

import com.jbr.dailyfinance.client.entities.TicketDateImpl;
import com.jbr.dailyfinance.client.entities.TicketImpl;

/**
 *
 * @author jbr
 */
public class TicketDateComs extends BasisComs<TicketDateImpl> {

    public TicketDateComs() {
        super(TicketImpl.class);
    }

    @Override
    public String getResourceUrl() {
        return "resources/ticketdate";
    }

    @Override
    public String getJsonName() {
        return "ticketdate";
    }


}
