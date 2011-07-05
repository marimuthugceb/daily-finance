package com.jbr.dailyfinance.gae.impl.repository;

import com.google.appengine.api.datastore.Entity;
import com.jbr.dailyfinance.api.repository.server.IProductSecurable;
import com.jbr.dailyfinance.api.repository.server.IUser;
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
public class Product extends ProductSecurable implements Serializable, IProductSecurable, DatastoreEntity {
    public final static String KIND = "product";
    private BaseEntity e;


    @XmlElement
    @Override
    public Long getId() {
        return e.getId();
    }

    public void setId(Long id) {
        e = new BaseEntity(id, KIND);
    }

    @Override
    public Entity getEntity() {
        return e.getEntity();
    }

    private enum p {
        name,
        ean,
        price,
        manufacturer,
        categoryId;
    }

    public Product(Long id) {
        e = new BaseEntity(id, KIND);
    }

    public Product() {
        e = new BaseEntity(KIND);
    }

    public Product(Entity entity) {
        e = new BaseEntity(entity);
    }

    @XmlElement
    @Override
    public String getName() {
        return (String) e.getEntity().getProperty(p.name.toString());
    }


    @Override
    public void setName(String name) {
        e.getEntity().setProperty(p.name.toString(), name);
    }

    @XmlElement
    @Override
    public String getEan() {
        return (String) e.getEntity().getProperty(p.ean.toString());
    }

    @Override
    public void setEan(String ean) {
        e.getEntity().setProperty(p.ean.toString(), ean);
    }

    @XmlElement
    @Override
    public Double getPrice() {
        return (Double) e.getEntity().getProperty(p.price.toString());
    }

    @Override
    public void setPrice(Double price) {
        e.getEntity().setProperty(p.price.toString(), price);
    }

    @XmlElement
    @Override
    public String getManufacturer() {
        return (String) e.getEntity().getProperty(p.manufacturer.toString());
    }

    @Override
    public void setManufacturer(String manufacturer) {
        e.getEntity().setProperty(p.manufacturer.toString(), manufacturer);
    }

    @XmlElement
    @Override
    public Long getCategoryId() {
        return (Long) e.getEntity().getProperty(p.categoryId.toString());
    }

    @Override
    public void setCategoryId(Long id) {
        e.getEntity().setProperty(p.categoryId.toString(), id);
    }

    @Override
    public IUser getUser() {
        return e.getUser();
    }

    @Override
    public void setUser(IUser user) {
        e.setUser(user);
    }

}
