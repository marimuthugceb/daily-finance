package com.jbr.gwt.json.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jbr
 */
public class  JsonUtils {
    public final static DateTimeFormat jsonFormat = DateTimeFormat.getFormat("yyyy-MM-dd");

    public static Date toDate(String isoDate) {
        return jsonFormat.parse(isoDate.substring(0, 10));
    }

    /**
    * Convert the string of JSON into JavaScript object.
    */
    public static <T extends JavaScriptObject> JsArray<T>
            asArrayOf(Class<T> c, String jsonObjectName, String jsonString) throws RequestException {
        JSONValue jv = JSONParser.parseStrict(jsonString);
        JSONObject o = jv.isObject();
        if (o == null || !o.containsKey(jsonObjectName))
            throw new RequestException("No " + jsonObjectName + " object found in return string from server");
        else {
            final JSONValue get = o.get(jsonObjectName);
            return  (JsArray) get.isArray().getJavaScriptObject();
        }
    }

    public static <T extends JavaScriptObject> List<T>
            asListOf(Class<T> c, String jsonObjectName, String jsonString) throws RequestException {
        JSONValue jv = JSONParser.parseStrict(jsonString);
        JSONObject o = jv.isObject();
        if (o == null || !o.containsKey(jsonObjectName))
            throw new RequestException("No " + jsonObjectName + " object found in return string from server");

        final List<T> list = new ArrayList<T>();
        final JSONValue get = o.get(jsonObjectName);
        if (get.isArray() == null)
            list.add((T) get.isObject().getJavaScriptObject());
        else {
            final JsArray jsArray = (JsArray)get.isArray().getJavaScriptObject();
            for (int i= 0; i < jsArray.length(); i++)
                list.add((T) jsArray.get(i));
        }
        return list;
    }

    public static <T extends JavaScriptObject> List<T> asListOf(JsArray<T> jsArray) {
        List<T> ret = new ArrayList<T>(jsArray.length());
        for (int i= 0; i < jsArray.length(); i++)
            ret.add(jsArray.get(i));
        return ret;
    }

    public interface ListCallback<T> {
        void onResponseOk(List<T> list);
    }

    public interface RemoveCallback {
        void onResponseOk();
    }

    public interface StringCallback {
        void onResponseOk(String string);
    }

    public interface ElementCallback<T, B> {
        void onResponseOk(T element, B backref);
    }

}
