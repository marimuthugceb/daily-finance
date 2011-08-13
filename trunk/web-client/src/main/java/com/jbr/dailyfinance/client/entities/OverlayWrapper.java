package com.jbr.dailyfinance.client.entities;

import com.google.gwt.core.client.JavaScriptObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jbr
 */
public class OverlayWrapper<T extends JavaScriptObject> {
    T object;

    public OverlayWrapper() {
    }

    public OverlayWrapper(T object) {
        this.object = object;
    }

    public static <O extends JavaScriptObject> List<OverlayWrapper<O>> toList(List<O> objects) {
        final List<OverlayWrapper<O>> overlays = new ArrayList<OverlayWrapper<O>>();
        for (JavaScriptObject o : objects) {
            overlays.add(new OverlayWrapper<O>((O) o));
        }
        return overlays;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

}
