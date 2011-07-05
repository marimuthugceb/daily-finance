package com.jbr.dailyfinance.gae.datastore;

import com.jbr.dailyfinance.api.repository.server.StoreSecurable;
import com.jbr.dailyfinance.api.service.StoreServices;
import com.jbr.dailyfinance.gae.impl.repository.Product;
import com.jbr.dailyfinance.gae.impl.repository.Store;

/**
 *
 * @author jbr
 */
public class StoreServicesImpl extends BasicOperationsImpl<StoreSecurable>
        implements StoreServices {

    public StoreServicesImpl() {
        super(Product.class, Product.KIND);
    }

    @Override
    public StoreSecurable newEntity() {
        return new Store();
    }
}
