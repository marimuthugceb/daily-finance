package com.jbr.dailyfinance.gae.impl.repository;

import com.google.appengine.api.datastore.Entity;
import com.jbr.dailyfinance.api.repository.client.SumCategory;
import com.jbr.dailyfinance.api.repository.server.SumCategorySecurable;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author jbr
 */
@XmlRootElement(name = "sumcategory")
@XmlAccessorType(XmlAccessType.NONE)
public class SumCategoryImpl extends BaseEntity
        implements SumCategory, SumCategorySecurable {
    public static final String KIND = "sumcategory";

    public enum p {
        sumDate,
        categoryId,
        sum;
    }

    public SumCategoryImpl(Long id) {
        super(id, KIND);
    }

    public SumCategoryImpl() {
        super(KIND);
    }

    public SumCategoryImpl(Entity entity) {
        super(entity);
    }

    @XmlElement
    @Override
    public Long getCategoryId() {
        return (Long)entity.getProperty(p.categoryId.toString());
    }

    public void setCategoryId(Long categoryId) {
        entity.setProperty(p.categoryId.toString(), categoryId);
    }

    @XmlElement
    @Override
    public Double getSum() {
        return (Double)entity.getProperty(p.sum.toString());
    }

    public void setSum(Double sum) {
        entity.setProperty(p.sum.toString(), sum);
    }

    @XmlElement
    @Override
    public Date getSumDate() {
        return (Date)entity.getProperty(p.sumDate.toString());
    }

    public void setSumDate(Date sumDate) {
        entity.setProperty(p.sumDate.toString(), sumDate);
    }
}
