package com.jbr.dailyfinance.client.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.jbr.dailyfinance.api.repository.client.Category;
import com.jbr.dailyfinance.api.repository.client.Product;

/**
 *
 * @author jbr
 */
public class CategoryImpl extends JavaScriptObject implements Category, JsonEntity<CategoryImpl> {

    protected CategoryImpl() {}

    @Override
    public final native Long getId() /*-{ return this.id; }-*/;
    @Override
    public final native String getName() /*-{ return this.name; }-*/;
    @Override
    public final native void setName(String newname) /*-{ this.name = newname; }-*/;
    public final native void setId(Long newid) /*-{ this.id = newid; }-*/;
    public final native String getTypeRaw() /*-{ return this.type; }-*/;
    public final native void setTypeRaw(String newtype) /*-{ this.type = newtype; }-*/;
    @Override
    public final Type getType() {
        if (getTypeRaw() == null)
            return null;
        return Category.Type.valueOf(getTypeRaw());
    }

    @Override
    public final void setType(Type mType) {
        if (mType == null)
            return;
        setTypeRaw(mType.toString());
    }

    @Override
    public final CategoryImpl toNewJsonEntity() {
        return this;
    }

    @Override
    public final String toJson() {
        return new JSONObject(this).toString();
    }



}
