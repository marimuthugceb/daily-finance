package com.jbr.dailyfinance.gae.impl.repository;

import com.google.appengine.api.datastore.Category;
import com.jbr.dailyfinance.api.repository.client.Category.Type;
import com.jbr.dailyfinance.api.repository.client.SumCategoryType;
import java.io.Serializable;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author jbr
 */
@XmlRootElement(name = "sumcategorytype")
@XmlAccessorType(XmlAccessType.FIELD)
public class SumCategoryTypeImpl implements SumCategoryType, Serializable {
    Date sumDate;
    Map<Type, Double> sumWithType;

    public SumCategoryTypeImpl() {
    }

    public SumCategoryTypeImpl(Date sumDate, Type categoryType, Double sum) {
        if (sumWithType == null)
            sumWithType = new EnumMap<Type, Double>(Type.class);
        sumWithType.put(categoryType, sum);
        this.sumDate = sumDate;
    }

    @Override
    public Map<Type, Double> getSumWithType() {
        return sumWithType;
    }

    public void setSumWithType(Map<Type, Double> sumWithType) {
        this.sumWithType = sumWithType;
    }

    @Override
    public Date getSumDate() {
        return sumDate;
    }

    public void setSumDate(Date date) {
        sumDate = date;
    }


}
