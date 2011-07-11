package com.jbr.dailyfinance.entities;

import com.jbr.dailyfinance.api.repository.client.Product;

/**
 *
 * @author jbr
 */
public class ProductImpl extends JsonEntity<ProductImpl> implements Product {

    protected ProductImpl() {}

    public final native Long getId() /*-{ return this.id; }-*/;
    public final native String getName() /*-{ return this.name; }-*/;
    public final native void setName(String newname) /*-{ this.name = newname; }-*/;
    public final native void setId(Long newid) /*-{ this.id = newid; }-*/;
    public final native Long getCategoryId() /*-{ return this.categoryId; }-*/;
    public final native void setCategoryId(Long categoryid) /*-{ this.categoryId = categoryid; }-*/;
    public final native String getEan() /*-{ return this.ean; }-*/;
    public final native void setEan(String ean) /*-{ this.ean = ean; }-*/;
    public final native String getManufacturer() /*-{ return this.manu; }-*/;
    public final native void setManufacturer(String manu) /*-{ this.manu = manu; }-*/;
    public final native Double getPrice() /*-{ return this.price; }-*/;
    public final native void setPrice(Double price) /*-{ this.price = price; }-*/;

    @Override
    public ProductImpl toNewJsonEntity() {
        return this;
    }

}
