package com.jbr.dailyfinance.gae.impl.repository;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jbr
 */
@XmlTransient
public abstract class BaseEntity implements Serializable, SecurableEntity {
    protected final Entity entity;
    protected final String kind;

    public BaseEntity(Entity entity) {
        this.entity = entity;
        this.kind = entity.getKind();
    }

    public BaseEntity(String kind) {
        this.entity = new Entity(kind);
        this.kind = kind;
    }

    public BaseEntity(Long id, String kind) {
        entity = new Entity(KeyFactory.createKey(kind, id));
        this.kind = kind;
    }

    protected static Entity fetchById(Long id, String kind) {
        System.out.println("Fetching by id = " + id);
        if (id == null)
            return null;
        try {
            return DatastoreServiceFactory.getDatastoreService()
                    .get(KeyFactory.createKey(kind, id));
        } catch (EntityNotFoundException ex) {
            return null;
        }
    }

    @Override
    public User getUser() {
        return (User) entity.getProperty("user");
    }

    @Override
    public void setUser(User user) {
        entity.setProperty("user", user);
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @XmlElement
    public Long getId() {
        return entity.getKey().getId();
    }
}
