package com.jbr.dailyfinance.client.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONObject;
import com.jbr.dailyfinance.api.repository.client.Ticket;
import com.jbr.gwt.json.client.JsonUtils;
import java.util.Date;

/**
 *
 * @author jbr
 */
public class TicketImpl extends JavaScriptObject implements Ticket, JsonEntity<TicketImpl> {

    protected TicketImpl() {}

    @Override
    public final native Long getId() /*-{ return this.id; }-*/;
    public final native void setId(Long newid) /*-{ this.id = newid; }-*/;
    public final native void setName(String newname) /*-{ this.name = newname; }-*/;
    @Override
    public final native Long getStoreId() /*-{ return this.storeid; }-*/;
    @Override
    public final native void setStoreId(Long mStoreId) /*-{ this.storeid = mStoreId; }-*/;
    public final native String getTicketDateRaw() /*-{ return this.ticketdate; }-*/ ;
    public final native void setTicketDateRaw(String mTicketDate) /*-{ this.ticketdate = mTicketDate; }-*/;

    @Override
    public final Date getTicketDate() {
        if (getTicketDateRaw() == null)
            return null;
        return JsonUtils.toDate(getTicketDateRaw());
    }

    @Override
    public void setTicketDate(Date mTicketDate) {
        setTicketDateRaw(JsonUtils.jsonFormat.format(mTicketDate));
    }

    public final String getDateAsString() {
        final DateTimeFormat userFormat = DateTimeFormat.getFormat(
                DateTimeFormat.PredefinedFormat.DATE_FULL);
        return userFormat.format(getTicketDate());

    }

    @Override
    public final TicketImpl toNewJsonEntity() {
        return this;
    }

    @Override
    public final String toJson() {
        return new JSONObject(this).toString();
    }




}
