package com.jbr.dailyfinance.gae.impl.repository;

import com.google.appengine.api.datastore.Entity;
import com.jbr.dailyfinance.api.repository.server.ProductSecurable;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jbr
 */
@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.NONE)
public class ProductImpl extends BaseEntity implements Serializable, ProductSecurable, DatastoreEntity {
    public final static String KIND = "product";

    private enum p {
        name,
        ean,
        price,
        manufacturer,
        categoryId;
    }

    public ProductImpl(Long id) {
        super(id, KIND);
    }

    public ProductImpl() {
        super(KIND);
    }

    public ProductImpl(Entity entity) {
        super(entity);
    }

    @XmlElement
    @Override
    public String getName() {
        return (String) entity.getProperty(p.name.toString());
    }


    @Override
    public void setName(String name) {
        entity.setProperty(p.name.toString(), name);
    }

    @XmlElement
    @Override
    public String getEan() {
        return (String) entity.getProperty(p.ean.toString());
    }

    @Override
    public void setEan(String ean) {
        entity.setProperty(p.ean.toString(), ean);
    }

    @XmlElement
    @Override
    public Double getPrice() {
        return (Double) entity.getProperty(p.price.toString());
    }

    @Override
    public void setPrice(Double price) {
        entity.setProperty(p.price.toString(), price);
    }

    @XmlElement
    @Override
    public String getManufacturer() {
        return (String) entity.getProperty(p.manufacturer.toString());
    }

    @Override
    public void setManufacturer(String manufacturer) {
        entity.setProperty(p.manufacturer.toString(), manufacturer);
    }

    @XmlElement
    @Override
    public Long getCategoryId() {
        return (Long) entity.getProperty(p.categoryId.toString());
    }

    @Override
    public void setCategoryId(Long id) {
        entity.setProperty(p.categoryId.toString(), id);
    }
}
