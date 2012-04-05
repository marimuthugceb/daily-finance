package com.jbr.dailyfinance.gae.datastore;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.jbr.dailyfinance.api.repository.server.CategorySecurable;
import com.jbr.dailyfinance.api.service.CategoryServices;
import com.jbr.dailyfinance.gae.impl.repository.CategoryImpl;
import java.util.List;

/**
 *
 * @author jbr
 */
public class CategoryServicesImpl extends BasicOperationsImpl<CategorySecurable>
        implements CategoryServices {

    private static final MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
    private static final UserService userService = UserServiceFactory.getUserService();

    private static final String CATEGORIES = "CATEGORIES";

    private static String getCacheKey() {
        return CATEGORIES + "/" + userService.getCurrentUser().getEmail();
    }


    public CategoryServicesImpl() {
        super(CategoryImpl.class, CategoryImpl.KIND);
    }

    @Override
    public CategorySecurable newEntity() {
        return new CategoryImpl();
    }

    @Override
    public CategorySecurable put(CategorySecurable entity) {
        // Add to cache
        List<CategorySecurable> categories = (List<CategorySecurable>)
                cache.get(getCacheKey());
        if (categories == null) {
            // No was in cache. Dont do anything then
        } else {
            categories.add(entity);
            cache.put(getCacheKey(), categories);
        }

        return super.put(entity);
    }

    /**
     * Get all categories. Either from cache or from datastore
     * @return
     */
    @Override
    public List<CategorySecurable> list() {
        List<CategorySecurable> categories = (List<CategorySecurable>) 
                cache.get(getCacheKey());
        if (categories == null) {
            System.out.println("Catgorylist was not in cache");
            categories = super.list();
            cache.put(getCacheKey(), categories);
        } else
            System.out.println("Categorylist returned from cache");

        return categories;
    }


}
