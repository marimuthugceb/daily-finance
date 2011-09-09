package com.jbr.dailyfinance.client.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.jbr.dailyfinance.api.repository.client.Category;
import com.jbr.dailyfinance.api.repository.client.Category.Type;
import com.jbr.dailyfinance.api.repository.client.SumCategoryType;
import com.jbr.gwt.json.client.JsonUtils;
import java.util.Date;

/**
 *
 * @author jbr
 */
public final class SumCategoryTypeImpl extends JavaScriptObject implements SumCategoryType {

    protected SumCategoryTypeImpl() {}

    public final native String getSumRaw() /*-{ return this.sum; }-*/;
    @Override
    public final Double getSum() {
        return Double.valueOf(getSumRaw());
    }
    public final native String getTypeRaw() /*-{ return this.categoryType; }-*/;
    @Override
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

}
