package com.jbr.dailyfinance.client;

import com.jbr.dailyfinance.client.entities.TicketImpl;
import com.jbr.gwt.json.client.JsonUtils;
import java.util.Date;

/**
 *
 * @author jbr
 */
public class TicketComs extends BasisComs<TicketImpl> {
    Date ticketDate;

    public TicketComs() {
        super(TicketImpl.class);
    }

    @Override
    public String getResourceUrl() {
        final String ticketListUrl = "resources/ticketdate/" + JsonUtils.jsonFormat.format(ticketDate) + "/ticket";
        return ticketListUrl;
    }

    @Override
    public String getJsonName() {
        return "ticket";
    }

    public Date getTicketDate() {
        return ticketDate;
    }

    public void setTicketDate(Date ticketDate) {
        this.ticketDate = ticketDate;
    }
}
