package com.jbr.dailyfinance.client;

import com.jbr.dailyfinance.client.entities.CategoryImpl;

/**
 *
 * @author jbr
 */
public class CategoryComs extends BasisComs<CategoryImpl> {
    Long categoryId;

    public CategoryComs() {
        super(CategoryImpl.class);
    }

    @Override
    public String getResourceUrl() {
        if (categoryId == null)
            return "resources/category";
        return "resources/category/" + categoryId.toString();
    }

    @Override
    public String getJsonName() {
        return "category";
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long ticketId) {
        this.categoryId = ticketId;
    }


}
