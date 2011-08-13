package com.jbr.dailyfinance.client.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.jbr.dailyfinance.api.repository.client.Store;

/**
 *
 * @author jbr
 */
public class StoreImpl extends JavaScriptObject implements Store {

    protected StoreImpl() {}

    public final native String getIdRaw() /*-{ return this.id; }-*/;

    @Override
    public final Long getId() {
        return Long.parseLong(getIdRaw());
    }

    public final native void setIdRaw(String newid) /*-{ this.id = newid; }-*/;
    public final void setId(Long id) {
        setIdRaw(id.toString());
    }

    @Override
    public final native String getName() /*-{ return this.name; }-*/;
    @Override
    public final native void setName(String newname) /*-{ this.name = newname; }-*/;


    public final StoreImpl toNewJsonEntity() {
        return this;
    }

    public final String toJson() {
        return new JSONObject(this).toString();
    }

}
