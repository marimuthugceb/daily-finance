package com.jbr.dailyfinance.client.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.jbr.dailyfinance.api.repository.client.Category;

/**
 *
 * @author jbr
 */
public class CategoryImpl extends JavaScriptObject implements Category, Identifiable {

    protected CategoryImpl() {}

    public final native String getIdRaw() /*-{ return this.id; }-*/;
    @Override
    public final Long getId() {
        return Long.valueOf(getIdRaw());
    }
    public final native void setIdRaw(String newid) /*-{ this.id = newid; }-*/;
    @Override
    public final native String getName() /*-{ return this.name; }-*/;
    @Override
    public final native void setName(String newname) /*-{ this.name = newname; }-*/;
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

    public final CategoryImpl toNewJsonEntity() {
        return this;
    }

    public final String toJson() {
        return new JSONObject(this).toString();
    }



}
