/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jbr.dailyfinance.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 *
 * @author jbr
 */
public abstract class BaseEntity<T> {
    public static final String TAG = "DailyFinance";

    /**
     * Updates the given record in db by _id. If none is update then insert
     * the given record as a new row.
     * @param s record to update or insert
     * @return primary key (id)
     */
    public long updateOrInsertInDb(SQLiteDatabase s) {
        long return_id;
        if (getId() != null) {
            String[] id = {getId().toString()};
            return_id = getId();
            // Update row
            if (s.update(getTableName(), getContentValues(), "_id = ?", id) < 0) {
                Log.v(TAG, "No rows was updated, inserting rows instead");
                return_id = s.insert(getTableName(), null, getContentValues());
                Log.v(TAG, "New id is " + return_id);
            }
        } else {
                Log.v(TAG, "_id is null, inserting rows instead");
                return_id = s.insert(getTableName(), null, getContentValues());
                Log.v(TAG, "New id is " + return_id);
        }

        return return_id;
    }

    public abstract String getTableName();

    public abstract ContentValues getContentValues();

    public abstract Long getId();

}
