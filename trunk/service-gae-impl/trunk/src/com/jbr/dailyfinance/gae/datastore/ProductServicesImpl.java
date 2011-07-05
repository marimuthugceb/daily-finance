package com.jbr.dailyfinance.gae.datastore;

import com.jbr.dailyfinance.api.repository.server.ProductSecurable;
import com.jbr.dailyfinance.api.service.ProductServices;
import com.jbr.dailyfinance.gae.impl.repository.Product;

/**
 *
 * @author jbr
 */
public class ProductServicesImpl extends BasicOperationsImpl<ProductSecurable> 
        implements ProductServices {

    public ProductServicesImpl() {
        super(Product.class, Product.KIND);
    }

    @Override
    public ProductSecurable newEntity() {
        return new Product();
    }
}
