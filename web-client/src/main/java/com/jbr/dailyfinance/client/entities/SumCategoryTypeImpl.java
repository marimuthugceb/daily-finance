package com.jbr.dailyfinance.client.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.json.client.JSONObject;
import com.jbr.dailyfinance.api.repository.client.Category;
import com.jbr.dailyfinance.api.repository.client.Category.Type;
import com.jbr.dailyfinance.api.repository.client.SumCategoryType;
import com.jbr.gwt.json.client.JsonUtils;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jbr
 */
public final class SumCategoryTypeImpl extends JavaScriptObject implements SumCategoryType {

    protected SumCategoryTypeImpl() {}

    public final native JavaScriptObject getSumWithTypeRaw() /*-{ return this.sumWithType; }-*/;
    public final Double getSum() {
        return Double.valueOf(0);
    }
    public final native String getTypeRaw() /*-{ return this.categoryType; }-*/;
    public final Type getCategoryType() {
        if (getTypeRaw() == null)
            return null;
        return Category.Type.valueOf(getTypeRaw());
    }

    public final SumCategoryTypeImpl toNewJsonEntity() {
        return this;
    }

    public final String toJson() {
        return new JSONObject(this).toString();
    }

    public final native String getSumDateRaw() /*-{ return this.sumDate; }-*/;
    @Override
    public Date getSumDate() {
        if (getSumDateRaw() == null)
            return null;
        return JsonUtils.jsonFormat.parse(getSumDateRaw());
    }

    @Override
    public Map<Type, Double> getSumWithType() {
        JavaScriptObject jsonWithMap = getSumWithTypeRaw();
        final Map<Type, Double> map = new EnumMap<Type, Double>(Type.class);
        try {
            List<Entry> asListOf = JsonUtils.asListOf(Entry.class, "entry", jsonWithMap);
            System.out.println("List of sum with type: " + asListOf.size());
            for (Entry entry : asListOf) {
                map.put(Type.valueOf(entry.getKey()), new Double(entry.getValue()));
            }

        } catch (RequestException ex) {
            System.out.println(ex);
        }
        return map;
    }

}
