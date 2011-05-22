/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jbr.dailyfinance.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import java.io.Serializable;
import java.text.DecimalFormat;

/**
 *
 * @author jbr
 */
public class ProductEntity extends BaseEntity<ProductEntity> implements Serializable {
    public static final String TAG = "DailyFinance";
    private String mName;
    private String mEan;
    private Long mId;
    private Long mPrice;
    private String mManufacturer;
    private Long mCategoryId;

    public ProductEntity() {
    }

    public ProductEntity(String nName, String nEan, String mManufacturer, Long singlePrize) {
        this.mName = nName;
        this.mEan = nEan;
        this.mManufacturer = mManufacturer;
        this.mPrice = singlePrize;

    }

    public static ProductEntity getEntity(Cursor c) {
        if (c == null || c.isAfterLast())
            return null;
        ProductEntity e = new ProductEntity();
        if (c.getColumnIndex("name")>-1)
            e.setName(c.getString(c.getColumnIndex("name")));
        if (c.getColumnIndex("ean")>-1)
            e.setEan(c.getString(c.getColumnIndex("ean")));
        if (c.getColumnIndex("manufacturer")>-1)
            e.setManufacturer(c.getString(c.getColumnIndex("manufacturer")));
        Log.v(TAG, "singleprize column " + c.getColumnIndex("price"));
        if (c.getColumnIndex("price")>-1)
            e.setPrice(c.getLong(c.getColumnIndex("price")));
        if (c.getColumnIndex("categoryid")>-1)
            e.setCategoryId(c.getLong(c.getColumnIndex("categoryid")));
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

    public String getManufacturer() {
        return mManufacturer;
    }

    public void setManufacturer(String mManufacturer) {
        this.mManufacturer = mManufacturer;
    }

    public Long getPrice() {
        Log.v(TAG, "Getting singleprize is " + mPrice);
        return mPrice;
    }

    public void setPrice(Long mSinglePrize) {
        Log.v(TAG, "Setting singleprise to " + mSinglePrize);
        this.mPrice = mSinglePrize;
    }

    public Float getPriceAsDouble() {
        if (getPrice() == null)
            return null;
        try {
            Float priceInOre = new Float(getPrice());
            float priceInKr = priceInOre.floatValue() / 100f;
            return priceInKr;
        } catch (NumberFormatException e) {
            Log.e(TAG, "NumberFormatException on converting "
                    + getPrice() + " to float!");
            return null;
        }
    }

    public void setPriceAsDouble(Double price) {
        if (price == null) {
            setPrice(null);
            return;
        }
        setPrice(new Float(price.floatValue() * 100f).longValue());
    }

    public String getPriceFormatted() {
        if (getPriceAsDouble()==null)
            return null;
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        return decimalFormat.format(getPriceAsDouble());
    }

    public void setPriceFormatted(String price) {
        setPriceAsDouble(new Double(price.replaceAll(",", ".")));
    }

    public String getEan() {
        return mEan;
    }

    public void setEan(String nEan) {
        this.mEan = nEan;
    }

    public String getName() {
        return mName;
    }

    public void setName(String nName) {
        this.mName = nName;
    }

    public Long getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(Long mCategoryId) {
        this.mCategoryId = mCategoryId;
    }

    @Override
    public String getTableName() {
        return "products";
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues(4);
        cv.put("price", mPrice);
        cv.put("ean", mEan);
        cv.put("name", mName);
        cv.put("manufacturer", mManufacturer);
        cv.put("categoryid", mCategoryId);
        return cv;
    }



}
