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
public final class TicketImpl extends JavaScriptObject implements Ticket {

    protected TicketImpl() {}

    public final native String getIdRaw() /*-{ return this.id; }-*/;
    public final native void setIdRaw(String newid) /*-{ this.id = newid; }-*/;
    public final native String getStoreIdRaw() /*-{ return this.storeId; }-*/;
    public final native void setStoreIdRaw(String mStoreId) /*-{ this.storeId = mStoreId; }-*/;
    public final native String getTicketDateRaw() /*-{ return this.ticketDate; }-*/ ;
    public final native void setTicketDateRaw(String mTicketDate) /*-{ this.ticketDate = mTicketDate; }-*/;
    public final native String getStoreName() /*-{ return this.storeName; }-*/ ;
    public final native void setStoreName(String storename) /*-{ this.storeName = storename; }-*/;

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

    public final String getTicketDateAsString() {
        return HumanDate.getFormat().format(getTicketDate());

    }

    public final TicketImpl toNewJsonEntity() {
        return this;
    }

    public final String toJson() {
        return new JSONObject(this).toString();
    }

    @Override
    public Long getId() {
        return Long.valueOf(getIdRaw());
    }

    @Override
    public Long getStoreId() {
        return Long.valueOf(getStoreIdRaw());
    }

    @Override
    public void setStoreId(Long mStoreId) {
        setStoreIdRaw(mStoreId.toString());
    }

}
