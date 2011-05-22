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
public class CategoryEntity extends BaseEntity<CategoryEntity> implements Serializable {
    public static final String TAG = "DailyFinance";
    private Long mId;
    private String mName;
    private String mType;

    public CategoryEntity() {
    }


    public static CategoryEntity getEntity(Cursor c) {
        if (c == null || c.isBeforeFirst() || c.isAfterLast())
            return null;
        CategoryEntity e = new CategoryEntity();
        if (c.getColumnIndex("name")>-1)
            e.setName(c.getString(c.getColumnIndex("name")));
        if (c.getColumnIndex("type")>-1)
            e.setType(c.getString(c.getColumnIndex("type")));
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

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }



    @Override
    public String getTableName() {
        return "categories";
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues(4);
        cv.put("name", mName);
        cv.put("type", mType);
        return cv;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" + "mId=" + mId + "mName=" + mName + "mType=" + mType + '}';
    }

    
}
