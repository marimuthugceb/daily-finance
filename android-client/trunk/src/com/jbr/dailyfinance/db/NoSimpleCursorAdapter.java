/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbr.dailyfinance.db;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

/**
 *
 * @author jbr
 */
public class NoSimpleCursorAdapter extends SimpleCursorAdapter {

    public NoSimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
    }

    public int getIndexOfId(Long id) {
        if (id == null)
            return 0;
        Cursor c = getCursor();
        c.moveToPosition(-1);
        while (c.moveToNext()) {
            if (c.getLong(c.getColumnIndex("_id")) == id)
                return c.getPosition();
        }
        return 0;
    }
}
