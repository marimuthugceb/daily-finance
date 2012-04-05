package com.jbr.dailyfinance.gae.impl.repository;

import com.google.appengine.api.datastore.Entity;
import com.jbr.dailyfinance.api.repository.client.SumCategory;
import com.jbr.dailyfinance.api.repository.server.SumCategorySecurable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

    public StringBuilder toCommaSeparetedString(List<Long> list) {
        StringBuilder ids = new StringBuilder();
        for (Long l : list) {
            ids.append(l.toString() + ",");
        }
        return ids;
    }

    public List<Long> toListOfLongs(final String ids) throws NumberFormatException {
        if (ids == null)
            return new ArrayList<Long>();
        String[] split = ids.split(",");
        List<String> listAsString = Arrays.asList(split);
        List<Long> list = new ArrayList<Long>(listAsString.size());
        for (String string : listAsString) {
            list.add(new Long(string));
        }
        return list;
    }

    public enum p {
        sumDate,
        categoryId,
        sum,
        ticketIds,
        ticketLineIds;
    }

    public SumCategoryImpl(String name) {
        super(name, KIND);
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

    @XmlElement
    @Override
    public List<Long> getTicketIds() {
        final String ids = (String) entity.getProperty(p.ticketIds.toString());
        List<Long> list = toListOfLongs(ids);
        return list;
    }

    public void setTicketIds(String ids) {
        entity.setProperty(p.ticketIds.toString(), ids);
    }

    public void addTicketId(Long ticketId) {
        String ids = (String) entity.getProperty(p.ticketIds.toString());
        if (ids==null)
            ids = ticketId.toString() + ",";
        ids = ids += ticketId.toString() + ",";
        entity.setProperty(p.ticketIds.toString(), ids.toString());
    }
    public void addTicketLineId(Long ticketId) {
        String ids = (String) entity.getProperty(p.ticketLineIds.toString());
        if (ids==null)
            ids = ticketId.toString() + ",";
        ids = ids += ticketId.toString() + ",";
        entity.setProperty(p.ticketLineIds.toString(), ids.toString());
    }

    @XmlElement
    @Override
    public List<Long> getTicketLineIds() {
        final String ids = (String) entity.getProperty(p.ticketLineIds.toString());
        List<Long> list = toListOfLongs(ids);
        return list;
    }

    public void setTicketLineIds(String ids) {
        entity.setProperty(p.ticketLineIds.toString(), ids);
    }
}
