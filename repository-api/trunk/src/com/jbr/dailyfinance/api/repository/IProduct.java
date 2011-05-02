package com.jbr.dailyfinance.api.repository;
/**
 *
 * @author jbr
 */
public interface IProduct {
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
