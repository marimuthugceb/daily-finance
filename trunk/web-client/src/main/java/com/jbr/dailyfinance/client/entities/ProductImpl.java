package com.jbr.dailyfinance.client.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.jbr.dailyfinance.api.repository.client.Product;

/**
 *
 * @author jbr
 */
public class ProductImpl extends JavaScriptObject implements Product, JsonEntity<ProductImpl> {

    protected ProductImpl() {}

    @Override
    public final native Long getId() /*-{ return this.id; }-*/;
    @Override
    public final native String getName() /*-{ return this.name; }-*/;
    @Override
    public final native void setName(String newname) /*-{ this.name = newname; }-*/;
    public final native void setId(Long newid) /*-{ this.id = newid; }-*/;
    @Override
    public final native Long getCategoryId() /*-{ return this.categoryId; }-*/;
    @Override
    public final native void setCategoryId(Long categoryid) /*-{ this.categoryId = categoryid; }-*/;
    @Override
    public final native String getEan() /*-{ return this.ean; }-*/;
    @Override
    public final native void setEan(String ean) /*-{ this.ean = ean; }-*/;
    @Override
    public final native String getManufacturer() /*-{ return this.manu; }-*/;
    @Override
    public final native void setManufacturer(String manu) /*-{ this.manu = manu; }-*/;
    @Override
    public final native Double getPrice() /*-{ return this.price; }-*/;
    @Override
    public final native void setPrice(Double price) /*-{ this.price = price; }-*/;

    @Override
    public final ProductImpl toNewJsonEntity() {
        return this;
    }

    @Override
    public final String toJson() {
        return new JSONObject(this).toString();
    }

}
