/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jbr.dailyfinance.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import java.io.Serializable;

/**
 *
 * @author jbr
 */
public class TicketLineEntity extends BaseEntity<TicketLineEntity> implements Serializable {
    public static final String TAG = "DailyFinance";
    private Long mId;
    private Long mProductId;
    private Long mTicketId;
    private Long mAmount;
    private Integer mNumber;

    public TicketLineEntity() {
    }

    public static TicketLineEntity getEntity(Cursor c) {
        if (c == null || c.isAfterLast())
            return null;
        TicketLineEntity e = new TicketLineEntity();
        if (c.getColumnIndex("productid")>-1)
            e.setProductId(c.getLong(c.getColumnIndex("productid")));
        if (c.getColumnIndex("ticketid")>-1)
            e.setTicketId(c.getLong(c.getColumnIndex("ticketid")));
        if (c.getColumnIndex("amount")>-1)
            e.setAmount(c.getLong(c.getColumnIndex("amount")));
        if (c.getColumnIndex("number")>-1)
            e.setNumber(c.getInt(c.getColumnIndex("number")));
        e.setId(c.getLong(c.getColumnIndex("_id")));
        return e;
    }

    @Override
    public Long getId() {
        return mId;
    }

    public void setId(Long mId) {
        this.mId = mId;
    }

    public Long getAmount() {
        return mAmount;
    }

    public void setAmount(Long mAmount) {
        this.mAmount = mAmount;
    }

    public Float getAmountAsFloat() {
        if (getAmount() == null)
            return null;
        try {
            Float priceInOre = new Float(getAmount());
            float priceInKr = priceInOre.floatValue() / 100f;
            return priceInKr;
        } catch (NumberFormatException e) {
            Log.e(TAG, "NumberFormatException on converting "
                    + getAmount() + " to float!");
            return null;
        }
    }

    public void setAmountAsDouble(Double price) {
        if (price == null) {
            setAmount(null);
            return;
        }
        setAmount(new Float(price.floatValue() * 100f).longValue());
    }


    public Integer getNumber() {
        return mNumber;
    }

    public void setNumber(Integer mNumber) {
        this.mNumber = mNumber;
    }

    public Long getProductId() {
        return mProductId;
    }

    public void setProductId(Long mProductId) {
        this.mProductId = mProductId;
    }

    public Long getTicketId() {
        return mTicketId;
    }

    public void setTicketId(Long mTicketId) {
        this.mTicketId = mTicketId;
    }



    @Override
    public String getTableName() {
        return "ticketlines";
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues(4);
        cv.put("productid", mProductId);
        cv.put("ticketid", mTicketId);
        cv.put("amount", mAmount);
        cv.put("number", mNumber);
        return cv;
    }
}
