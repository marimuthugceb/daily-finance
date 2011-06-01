package com.jbr.dailyfinance.gae.impl.repository;

import com.jbr.dailyfinance.api.repository.IStore;
import com.google.appengine.api.datastore.Entity;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jbr
 */
@XmlRootElement(name = "store")
@XmlAccessorType(XmlAccessType.NONE)
public class Store extends BaseEntity
        implements Serializable, Comparable<Store>, IStore{

    private static final String KIND = "store";
    private enum p {
        name;
    }

    public static Store fetchById(Long id) {
        return new Store(fetchById(id, KIND));
    }

    public Store(Entity entity) {
        super(entity);
    }

    public Store() {
        super(KIND);
    }

    public Store(Long id) {
        super(id, KIND);
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

    @Override
    public int compareTo(Store o) {
        if (this.getName() == null || o.getName() == null)
            return -1;
        return this.getName().compareToIgnoreCase(o.getName());
    }
}
