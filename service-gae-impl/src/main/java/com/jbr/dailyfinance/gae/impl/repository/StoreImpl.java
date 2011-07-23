package com.jbr.dailyfinance.gae.impl.repository;

import com.google.appengine.api.datastore.Entity;
import com.jbr.dailyfinance.api.repository.server.StoreSecurable;
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
public class StoreImpl extends BaseEntity implements Serializable, StoreSecurable, DatastoreEntity, Comparable<StoreImpl> {
    public static final String KIND = "store";

    private enum p {
        name,
        nameLowerCase;
    }

    public StoreImpl(Entity en) {
        super(en);
    }

    public StoreImpl() {
        super(KIND);
    }

    public StoreImpl(Long id) {
        super(id, KIND);
    }

    @XmlElement
    @Override
    public String getName() {
        return (String) entity.getProperty(p.name.toString());
    }

    public String getNameLowerCase() {
        return (String) entity.getProperty(p.nameLowerCase.toString());
    }


    @Override
    public void setName(String name) {
        entity.setProperty(p.name.toString(), name);
        entity.setProperty(p.nameLowerCase.toString(), name.toLowerCase());
    }

    public StoreImpl setNameAndReturn(String name) {
        setName(name);
        return this;
    }

    @Override
    public String toString() {
        return "StoreImpl{name:" + getName() + '}';
    }






    @Override
    public int compareTo(StoreImpl o) {
        if (this.getName() == null || o.getName() == null)
            return -1;
        return this.getName().compareToIgnoreCase(o.getName());
    }
}
