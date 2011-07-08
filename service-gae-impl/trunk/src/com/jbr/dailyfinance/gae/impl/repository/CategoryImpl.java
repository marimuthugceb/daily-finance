package com.jbr.dailyfinance.gae.impl.repository;

import com.google.appengine.api.datastore.Entity;
import com.jbr.dailyfinance.api.repository.server.CategorySecurable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jbr
 */
@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.NONE)
public class CategoryImpl extends BaseEntity implements CategorySecurable {
    public static final String KIND = "category";

    private enum p {
        name,
        type;
    }

    public CategoryImpl(Long id) {
        super(id, KIND);
    }

    public CategoryImpl() {
        super(KIND);
    }

    public CategoryImpl(Entity entity) {
        super(entity);
    }

    public static CategoryImpl fetchById(Long id) {
        return new CategoryImpl(fetchById(id, KIND));
    }

    @XmlElement
    @Override
    public String getName() {
        return (String) entity.getProperty(p.name.toString());
    }

    @Override
    public void setName(String mName) {
        entity.setProperty(p.name.toString(), mName);
    }

    @XmlElement
    @Override
    public Type getType() {
        return (Type) entity.getProperty(p.type.toString());
    }

    @Override
    public void setType(Type mType) {
        entity.setProperty(p.type.toString(), mType);
    }
}
