package com.jbr.dailyfinance.client.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.jbr.dailyfinance.api.repository.client.TicketLine;

/**
 *
 * @author jbr
 */
public final class TicketLineImpl extends JavaScriptObject implements TicketLine {

    protected TicketLineImpl() {}

    public final native String getIdRaw() /*-{ return this.id; }-*/;
    @Override
    public Long getId() {
        return Long.valueOf(getIdRaw());
    }
    public final native void setIdRaw(String newid) /*-{ this.id = newid; }-*/;

    public final native String getAmountRaw() /*-{ return this.amount; }-*/;

    @Override
    public final Double getAmount() {
        return Double.valueOf(getAmountRaw());
    };

    public final native String getNumberRaw() /*-{ return this.number; }-*/;
    @Override
    public Integer getNumber() {
        return Integer.valueOf(getNumberRaw());
    }

    public final native String getProductIdRaw() /*-{ return this.productId; }-*/;
    @Override
    public Long getProductId() {
        return Long.valueOf(getProductIdRaw());
    }

    public final native String getTicketIdRaw() /*-{ return this.ticketId; }-*/;
    @Override
    public Long getTicketId() {
        return Long.valueOf(getTicketIdRaw());
    }

    public final native void setAmountRaw(String mAmount) /*-{ this.amount = mAmount; }-*/;
    @Override
    public void setAmount(Double d) {
        setAmountRaw(d.toString());
    }

    public final native void setNumberRaw(String mNumber) /*-{ this.number = mNumber; }-*/;
    @Override
    public void setNumber(Integer number) {
        setNumberRaw(number.toString());
    }

    public final native void setProductIdRaw(String mProductId) /*-{ this.productId = mProductId; }-*/;
    @Override
    public void setProductId(Long productId) {
        setProductIdRaw(productId.toString());
    }

    public final native void setTicketIdRaw(String mTicketId) /*-{ this.ticketId = mTicketId; }-*/;
    @Override
    public void setTicketId(Long ticketId) {
        setTicketIdRaw(ticketId.toString());
    }

    public final native String getCategoryIdRaw() /*-{ return this.categoryId; }-*/;
    @Override
    public Long getCategoryId() {
        return Long.valueOf(getCategoryIdRaw());
    }

    public final native void setCategoryIdRaw(String mCategoryId) /*-{ this.categoryId = mCategoryId }-*/ ;
    @Override
    public void setCategoryId(Long categoryId) {
        setCategoryIdRaw(categoryId.toString());
    }

    public final TicketLineImpl toNewJsonEntity() {
        return this;
    }

    public final String toJson() {
        return new JSONObject(this).toString();
    }

    public final native void setCategoryName(String newname) /*-{ this.categoryname = newname; }-*/;
    public final native String getCategoryName() /*-{ return this.categoryname; }-*/;

    public final native void setProductName(String newname) /*-{ this.productname = newname; }-*/;
    public final native String getProductName() /*-{ return this.productname; }-*/;

}
