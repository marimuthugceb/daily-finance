package com.jbr.dailyfinance.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.jbr.gwt.json.client.JsonUtils;
import com.jbr.gwt.json.client.JsonUtils.ElementCallback;
import com.jbr.gwt.json.client.JsonUtils.ListCallback;
import com.jbr.gwt.json.client.JsonUtils.RemoveCallback;
import java.util.List;

/**
 *
 * @author jbr
 */
public abstract class BasisComs<T extends JavaScriptObject>  {
    protected final Class clazz;
    public abstract String getResourceUrl();
    public abstract String getJsonName();

    public BasisComs(Class clazz) {
        this.clazz = clazz;
    }

    protected static RequestBuilder newBuilder(Method httpMethod, String url) {
        RequestBuilder builder = new RequestBuilder(httpMethod, url);
        builder.setHeader("Cache-Control","no-cache");
        builder.setHeader("Accept","application/json");
        return builder;
    }


    public void list(final ListCallback<T> callback) {
        list(0, 10000, callback);
    }

    public void list(int startRecord, int records, final ListCallback<T> callback) {
        RequestBuilder builder = newBuilder(RequestBuilder.GET, getResourceUrl() +
                "?records=" + records + "&startrecord=" + startRecord);
        System.out.println("Getting for URL: " + builder.getUrl());
        try {
            Request request = builder.sendRequest(null, new RequestCallback() {
            @Override
            public void onError(Request request, Throwable exception) {
                throw new RuntimeException(exception);
            }

            @Override
            public void onResponseReceived(Request request, Response response) {
              if (200 == response.getStatusCode()) {
                    final String text = response.getText();
                    System.out.println(text);
                        try {
                            final List<T> list = JsonUtils.asListOf(
                                    clazz, getJsonName(), text);
                            System.out.println("Size of list: " +   list.size());
                            callback.onResponseOk(list);
                        } catch (RequestException ex) {
                            System.out.println(ex);
                        }
              } else {
                  System.out.println("Error " + response.getStatusText());
              }
            }
          });

        } catch (RequestException e) {
            System.out.println("Couldn't retrieve JSON " + e);
            //displayError("Couldn't retrieve JSON");
        }
    }

    public void put(final Integer index, JavaScriptObject entity, final ElementCallback<T, Integer> callback) {
        // Flush out all unnessesary fields from entity, simply by creating a new one
        JavaScriptObject newEntity = entity; // = new JSONObject(entity).toString();
        RequestBuilder builder = newBuilder(RequestBuilder.PUT, getResourceUrl());
        System.out.println("PUT to URL: " + builder.getUrl() + " content:" + newEntity.toString());
        try {
            builder.setHeader("Content-type", "application/json");
            Request request = builder.sendRequest(
                    JsonUtils.toJson(newEntity), new RequestCallback() {
                @Override
                public void onError(Request request, Throwable exception) {
                    Window.alert(exception.getLocalizedMessage());
                }

                @Override
                public void onResponseReceived(Request request, Response response) {
                    if (200 == response.getStatusCode()) {
                        final String text = response.getText();
                        System.out.println(text);
                        if (text == null || text.equalsIgnoreCase("null"))
                            callback.onResponseOk(null, index);
                        else {
                            JSONValue jv = JSONParser.parseStrict(text);
                            callback.onResponseOk((T)jv.isObject().getJavaScriptObject(), index);
                        }
                    } else {
                        System.out.println("Error " + response.getStatusText());
                        //displayError("Couldn't retrieve JSON (" + response.getStatusText()
                        //    + ")");
                    }
                };
            });

        } catch (RequestException e) {
            System.out.println("Couldn't retrieve JSON " + e);
            //displayError("Couldn't retrieve JSON");
        }
    }

    public void delete(Long id, final RemoveCallback callback) {
        RequestBuilder builder = newBuilder(RequestBuilder.DELETE, getResourceUrl() + "/" + id.toString());
        System.out.println("Deleting for URL: " + builder.getUrl());
        try {
            builder.sendRequest(null, new RequestCallback() {
                @Override
            public void onError(Request request, Throwable exception) {
                throw new RuntimeException(exception);
            }

                @Override
            public void onResponseReceived(Request request, Response response) {
                if (204 == response.getStatusCode()) {
                    System.out.println("Ok. Removed");
                    callback.onResponseOk();
                } else {
                    System.out.println("Error " + response.getStatusText());
                //displayError("Couldn't retrieve JSON (" + response.getStatusText()
                //    + ")");
              }
            }
          });

        } catch (RequestException e) {
            System.out.println("Couldn't retrieve JSON " + e);
            //displayError("Couldn't retrieve JSON");
        }
    }

}
