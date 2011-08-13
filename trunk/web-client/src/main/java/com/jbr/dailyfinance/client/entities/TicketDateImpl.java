package com.jbr.dailyfinance.client.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.jbr.dailyfinance.api.repository.client.Ticket;
import com.jbr.dailyfinance.client.HumanDate;
import com.jbr.gwt.json.client.JsonUtils;
import java.util.Date;

/**
 *
 * @author jbr
 */
public final class TicketDateImpl extends JavaScriptObject {

    protected TicketDateImpl() {}

    public final native String getTicketDateRaw() /*-{ return this.ticketDate; }-*/ ;
    public final native void setTicketDateRaw(String mTicketDate) /*-{ this.ticketDate = mTicketDate; }-*/;

    public final Date getTicketDate() {
        if (getTicketDateRaw() == null)
            return null;
        return JsonUtils.toDate(getTicketDateRaw());
    }

    public void setTicketDate(Date mTicketDate) {
        setTicketDateRaw(JsonUtils.jsonFormat.format(mTicketDate));
    }

    public final String getTicketDateAsString() {
        return HumanDate.getFormat().format(getTicketDate());

    }

    public final TicketDateImpl toNewJsonEntity() {
        return this;
    }

    public final String toJson() {
        return new JSONObject(this).toString();
    }

}
