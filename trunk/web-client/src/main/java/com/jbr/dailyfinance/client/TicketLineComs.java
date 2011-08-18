package com.jbr.dailyfinance.client;

import com.jbr.dailyfinance.client.entities.TicketImpl;
import com.jbr.dailyfinance.client.entities.TicketLineImpl;

/**
 *
 * @author jbr
 */
public class TicketLineComs extends BasisComs<TicketLineImpl> {
    Long ticketId;

    public TicketLineComs() {
        super(TicketImpl.class);
    }

    @Override
    public String getResourceUrl() {
        if (ticketId == null)
            return "resources/ticketline";
        else
            return "resources/ticket/" + ticketId.toString() + "/ticketline";
    }

    @Override
    public String getJsonName() {
        return "ticketline";
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }



}
