package com.jbr.dailyfinance.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

/**
 *
 * @author jbr
 */
public abstract class JsonEntity<J extends JavaScriptObject> extends JavaScriptObject {
    public abstract J toNewJsonEntity();
    
    public abstract Long getId();

    public final boolean equals(JsonEntity j) {
        System.out.println("Equals run");
        if (getId() == null)
            return false;
        return getId().equals(j.getId());
    }

    public final String asJson() {
        return new JSONObject(this).toString();
    }
}
