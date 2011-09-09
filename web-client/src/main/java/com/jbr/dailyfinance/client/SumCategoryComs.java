package com.jbr.dailyfinance.client;

import com.jbr.dailyfinance.client.entities.SumCategoryImpl;
import com.jbr.gwt.json.client.JsonUtils;
import java.util.Date;

/**
 *
 * @author jbr
 */
public class SumCategoryComs extends BasisComs<SumCategoryImpl> {
    Date startDate;
    Date endDate;

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public SumCategoryComs() {
        super(SumCategoryImpl.class);
    }

    @Override
    public String getResourceUrl() {
        return "resources/sum/month/category/"
                + JsonUtils.jsonFormat.format(startDate)
                + "," + JsonUtils.jsonFormat.format(endDate);
    }

    @Override
    public String getJsonName() {
        return "sumcategory";
    }


}
