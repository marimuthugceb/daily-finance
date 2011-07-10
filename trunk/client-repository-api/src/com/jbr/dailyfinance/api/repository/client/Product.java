package com.jbr.dailyfinance.api.repository.client;
/**
 *
 * @author jbr
 */
public interface Product extends Entity {
    Long getId();
    Long getCategoryId();
    String getEan();
    String getManufacturer();
    String getName();
    Double getPrice();
    void setCategoryId(Long id);
    void setEan(String ean);
    void setManufacturer(String manufacturer);
    void setName(String name);
    void setPrice(Double price);
}
