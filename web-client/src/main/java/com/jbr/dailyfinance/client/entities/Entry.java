package com.jbr.dailyfinance.client.entities;

import com.google.gwt.core.client.JavaScriptObject;

/**
 *
 * @author jbr
 */
public final class Entry extends JavaScriptObject {

    protected Entry() {}

    public final native String getKey() /*-{ return this.key; }-*/;
    public final native String getValue() /*-{ return this.value; }-*/;

}
