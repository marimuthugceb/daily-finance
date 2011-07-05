package com.jbr.dailyfinance.gae.impl.repository;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.jbr.dailyfinance.api.repository.server.IUser;
import com.jbr.dailyfinance.api.repository.server.SecurableEntity;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jbr
 */
@XmlTransient
public class BaseEntity implements Serializable, SecurableEntity, DatastoreEntity  {
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
    public IUser getUser() {
        final User u = (User) entity.getProperty("user");
        return new com.jbr.dailyfinance.gae.impl.repository.User(u);
    }

    @Override
    public void setUser(IUser user) {
        User u = new User(user.getEmail(), user.getAuthDomain(), user.getUserId());
        entity.setProperty("user", u);
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
