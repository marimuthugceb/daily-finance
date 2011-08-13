package com.jbr.dailyfinance.client;

import com.jbr.dailyfinance.client.entities.StoreImpl;
import com.jbr.dailyfinance.client.entities.TicketImpl;

/**
 *
 * @author jbr
 */
public class StoreComs extends BasisComs<StoreImpl> {
    Long storeId;

    public StoreComs() {
        super(TicketImpl.class);
    }

    @Override
    public String getResourceUrl() {
        return "resources/store" + (storeId == null ? "" : "/" + storeId.toString());
    }

    @Override
    public String getJsonName() {
        return "store";
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

}
