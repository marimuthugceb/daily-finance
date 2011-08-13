package com.jbr.dailyfinance.gae.datastore;

import com.google.appengine.api.datastore.Query;
import com.jbr.dailyfinance.api.repository.server.StoreSecurable;
import com.jbr.dailyfinance.api.service.StoreServices;
import com.jbr.dailyfinance.gae.impl.repository.DatastoreEntity;
import com.jbr.dailyfinance.gae.impl.repository.StoreImpl;
import java.util.List;

/**
 *
 * @author jbr
 */
public class StoreServicesImpl extends BasicOperationsImpl<StoreSecurable>
        implements StoreServices {

    public StoreServicesImpl() {
        super(StoreImpl.class, StoreImpl.KIND);
    }

    /**
     * Check for allready existing store name before adding
     * @param entity
     * @return
     */
    @Override
    public StoreSecurable put(StoreSecurable entity) {

        // If id allready exists then replace name
        if (entity.getId() != 0L && get(entity.getId()) != null) {
            return super.put(entity);
        }
        final StoreSecurable store = get(entity.getName());
        if (store == null)
            return super.put(entity);
        System.out.println("Refused to add allready existing store: "
                + entity.getName());
        return store;
    }



    @Override
    public StoreSecurable newEntity() {
        return new StoreImpl();
    }

    @Override
    public StoreSecurable get(String storeName) {
        final Query q = new Query(kind);
        q.addFilter("nameLowerCase", Query.FilterOperator.EQUAL, storeName.toLowerCase());
        List<DatastoreEntity> list = SecuredDatastore.getList(clazz, q, 0, 1);
        if (!list.isEmpty())
            return (StoreSecurable) list.get(0);
        return null;
    }
}
