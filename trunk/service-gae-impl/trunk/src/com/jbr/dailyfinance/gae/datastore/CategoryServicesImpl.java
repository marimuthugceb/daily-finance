package com.jbr.dailyfinance.gae.datastore;

import com.google.appengine.api.datastore.Category;
import com.jbr.dailyfinance.api.repository.server.CategorySecurable;
import com.jbr.dailyfinance.api.service.CategoryServices;
import com.jbr.dailyfinance.gae.impl.repository.CategoryImpl;

/**
 *
 * @author jbr
 */
public class CategoryServicesImpl extends BasicOperationsImpl<CategorySecurable>
        implements CategoryServices {

    public CategoryServicesImpl() {
        super(Category.class, CategoryImpl.KIND);
    }

    @Override
    public CategorySecurable newEntity() {
        return new CategoryImpl();
    }
}
