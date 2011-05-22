/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jbr.dailyfinance.db;

import android.content.ContentValues;
import android.database.Cursor;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jbr
 */
public class TicketEntity extends BaseEntity<TicketEntity> implements Serializable {
    public static final String TAG = "DailyFinance";
    private Long mId;
    private String mTicketDate;
    private Long mStoreId;

    public TicketEntity() {
    }

    public TicketEntity(Long mId, Long mStoreId, String mTicketDate) {
        this.mId = mId;
        this.mStoreId = mStoreId;
        this.mTicketDate = mTicketDate;
    }

    public static TicketEntity getEntity(Cursor c) {
        if (c == null || c.isAfterLast())
            return null;
        TicketEntity e = new TicketEntity();
        if (c.getColumnIndex("ticketdate")>-1)
            e.setTicketDate(c.getString(c.getColumnIndex("ticketdate")));
        if (c.getColumnIndex("storeid")>-1)
            e.setStoreId(c.getLong(c.getColumnIndex("storeid")));
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

    public String getTicketDate() {
        return mTicketDate;
    }

    public Date getTicketDateAsDate() {
        if (getTicketDate() == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            return sdf.parse(getTicketDate());
        } catch (ParseException ex) {
            return null;
        }
    }

    public String getTicketDateFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(getTicketDateAsDate());
    }

    public void setTicketDateAsDate(Date ticketDate) {
        if (ticketDate == null)
            setTicketDate(null);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        setTicketDate(sdf.format(ticketDate));
    }

    public void setTicketDate(String mTicketDate) {
        this.mTicketDate = mTicketDate;
    }

    public Long getStoreId() {
        return mStoreId;
    }

    public void setStoreId(Long mStoreId) {
        this.mStoreId = mStoreId;
    }

    @Override
    public String getTableName() {
        return "tickets";
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues(4);
        cv.put("ticketdate", mTicketDate);
        cv.put("storeid", mStoreId);
        return cv;
    }

    @Override
    public String toString() {
        return "TicketEntity{" + "mId=" + mId + "mTicketDate=" + mTicketDate + "mStoreId=" + mStoreId + '}';
    }

    
}
