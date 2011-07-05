package com.jbr.dailyfinance.gae.impl.repository;

import com.google.appengine.api.datastore.Entity;
import com.jbr.dailyfinance.api.repository.server.IStoreSecurable;
import com.jbr.dailyfinance.api.repository.server.IUser;
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
public class Store extends StoreSecurable implements Serializable, IStoreSecurable, DatastoreEntity, Comparable<Store> {
    private BaseEntity e;
    public static final String KIND = "store";

    private enum p {
        name;
    }

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

    public Store(Entity en) {
        e = new BaseEntity(en);
    }

    public Store() {
        e = new BaseEntity(KIND);
    }

    public Store(Long id) {
        e = new BaseEntity(id, KIND);
    }

    @XmlElement
    @Override
    public String getName() {
        return (String) e.entity.getProperty(p.name.toString());
    }

    @Override
    public void setName(String name) {
        e.entity.setProperty(p.name.toString(), name);
    }

    @Override
    public IUser getUser() {
        return e.getUser();
    }

    @Override
    public void setUser(IUser user) {
        e.setUser(user);
    }

    @Override
    public int compareTo(Store o) {
        if (this.getName() == null || o.getName() == null)
            return -1;
        return this.getName().compareToIgnoreCase(o.getName());
    }
}
