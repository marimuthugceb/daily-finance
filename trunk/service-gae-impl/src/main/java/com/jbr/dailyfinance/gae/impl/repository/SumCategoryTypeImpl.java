package com.jbr.dailyfinance.gae.impl.repository;

import com.jbr.dailyfinance.api.repository.client.Category.Type;
import com.jbr.dailyfinance.api.repository.client.SumCategoryType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author jbr
 */
@XmlRootElement(name = "sumcategorytype")
@XmlAccessorType(XmlAccessType.FIELD)
public class SumCategoryTypeImpl implements SumCategoryType {
    Type categoryType;
    Double sum;

    public SumCategoryTypeImpl() {
    }

    public SumCategoryTypeImpl(Type categoryType, Double sum) {
        this.categoryType = categoryType;
        this.sum = sum;
    }

    @Override
    public Type getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Type categoryType) {
        this.categoryType = categoryType;
    }

    @Override
    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }


}
