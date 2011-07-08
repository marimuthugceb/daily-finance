package com.jbr.dailyfinance.gae.datastore;

import com.jbr.dailyfinance.api.repository.client.Store;
import com.jbr.dailyfinance.api.repository.server.StoreSecurable;
import com.jbr.dailyfinance.api.service.StoreServices;
import com.jbr.dailyfinance.gae.impl.repository.StoreImpl;

/**
 *
 * @author jbr
 */
public class StoreServicesImpl extends BasicOperationsImpl<StoreSecurable>
        implements StoreServices {

    public StoreServicesImpl() {
        super(Store.class, StoreImpl.KIND);
    }

    @Override
    public StoreSecurable newEntity() {
        return new StoreImpl();
    }
}
