package com.jbr.dailyfinance.client;

import com.jbr.dailyfinance.client.entities.SumCategoryTypeImpl;
import com.jbr.dailyfinance.web.rest.ISODate;
import com.jbr.gwt.json.client.JsonUtils;
import java.util.Date;

/**
 *
 * @author jbr
 */
public class SumComs extends BasisComs<SumCategoryTypeImpl> {
    Date month;

    public void setMonth(Date month) {
        this.month = month;
    }

    public SumComs() {
        super(SumCategoryTypeImpl.class);
    }

    @Override
    public String getResourceUrl() {
        return "resources/sum/month/" + JsonUtils.jsonFormat.format(month);
    }

    @Override
    public String getJsonName() {
        return "sumcategorytype";
    }


}
