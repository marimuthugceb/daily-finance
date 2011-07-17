package com.jbr.dailyfinance.client.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.jbr.dailyfinance.api.repository.client.Store;

/**
 *
 * @author jbr
 */
public class StoreImpl extends JavaScriptObject implements Store, JsonEntity<StoreImpl> {

    protected StoreImpl() {}

    @Override
    public final native Long getId() /*-{ return this.id; }-*/;
    public final native void setId(Long newid) /*-{ this.id = newid; }-*/;
    @Override
    public final native String getName() /*-{ return this.name; }-*/;
    @Override
    public final native void setName(String newname) /*-{ this.name = newname; }-*/;


    @Override
    public final StoreImpl toNewJsonEntity() {
        return this;
    }

    @Override
    public final String toJson() {
        return new JSONObject(this).toString();
    }

}
