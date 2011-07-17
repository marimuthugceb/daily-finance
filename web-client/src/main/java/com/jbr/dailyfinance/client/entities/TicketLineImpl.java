package com.jbr.dailyfinance.client.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.jbr.dailyfinance.api.repository.client.TicketLine;

/**
 *
 * @author jbr
 */
public class TicketLineImpl extends JavaScriptObject implements TicketLine, JsonEntity<TicketLineImpl> {

    protected TicketLineImpl() {}

    @Override
    public final native Long getId() /*-{ return this.id; }-*/;
    public final native void setId(Long newid) /*-{ this.id = newid; }-*/;
    public final native void setName(String newname) /*-{ this.name = newname; }-*/;

    @Override
    public final native Double getAmount() /*-{ return this.amount; }-*/;

    @Override
    public final native Integer getNumber() /*-{ return this.number; }-*/;

    @Override
    public final native Long getProductId() /*-{ return this.productid; }-*/;

    @Override
    public final native Long getTicketId() /*-{ return this.ticketid; }-*/;

    @Override
    public final native void setAmount(Double mAmount) /*-{ this.amount = mAmount; }-*/;

    @Override
    public final native void setNumber(Integer mNumber) /*-{ this.number = mNumber; }-*/;

    @Override
    public final native void setProductId(Long mProductId) /*-{ this.productid = mProductId; }-*/;

    @Override
    public final native void setTicketId(Long mTicketId) /*-{ this.ticketid = mTicketId; }-*/;

    @Override
    public final TicketLineImpl toNewJsonEntity() {
        return this;
    }

    @Override
    public final String toJson() {
        return new JSONObject(this).toString();
    }

}
