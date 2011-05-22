/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jbr.dailyfinance.db;

import android.content.ContentValues;
import android.database.Cursor;
import java.io.Serializable;

/**
 *
 * @author jbr
 */
public class StoreEntity extends BaseEntity<StoreEntity> implements Serializable {
    public static final String TAG = "DailyFinance";
    private String mName;
    private Long mId;

    public StoreEntity() {
    }

    public StoreEntity(String mName, Long mId) {
        this.mName = mName;
        this.mId = mId;
    }

    public static StoreEntity getEntity(Cursor c) {
        if (c == null || c.isAfterLast())
            return null;
        StoreEntity e = new StoreEntity();
        if (c.getColumnIndex("name")>-1)
            e.setName(c.getString(c.getColumnIndex("name")));
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

    public String getName() {
        return mName;
    }

    public void setName(String nName) {
        this.mName = nName;
    }

    @Override
    public String getTableName() {
        return "store";
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues(4);
        cv.put("name", mName);
        return cv;
    }



}
