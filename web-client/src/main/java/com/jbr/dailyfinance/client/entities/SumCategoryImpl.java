package com.jbr.dailyfinance.client.entities;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.jbr.dailyfinance.api.repository.client.SumCategory;
import com.jbr.gwt.json.client.JsonUtils;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jbr
 */
public final class SumCategoryImpl extends JavaScriptObject implements SumCategory {

    protected SumCategoryImpl() {}

    public final native String getSumRaw() /*-{ return this.sum; }-*/;
    @Override
    public final Double getSum() {
        return Double.valueOf(getSumRaw());
    }

    public final native String getCategoryIdRaw() /*-{ return this.categoryId; }-*/;
    public final Long getCategoryId() {
        return Long.valueOf(getCategoryIdRaw());
    }

    public final SumCategoryImpl toNewJsonEntity() {
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
        return JsonUtils.jsonFormat.parse(getSumDateRaw().substring(0, 10));
    }

    @Override
    public List<Long> getTicketIds() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Long> getTicketLineIds() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
