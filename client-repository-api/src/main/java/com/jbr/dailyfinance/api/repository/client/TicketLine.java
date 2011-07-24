package com.jbr.dailyfinance.api.repository.client;

/**
 *
 * @author jbr
 */
public interface TicketLine extends Entity {
    Long getId();
    Double getAmount();
    Integer getNumber();
    /**
     * Used when we have the product. Can be null.
     * @return
     */
    Long getProductId();
    Long getTicketId();
    /**
     * CategoryId is used when there is no detailed product available
     * @return
     */
    Long getCategoryId();
    void setAmount(Double mAmount);
    void setNumber(Integer mNumber);
    void setProductId(Long mProductId);
    void setCategoryId(Long mCategoryId);
    void setTicketId(Long mTicketId);
}
