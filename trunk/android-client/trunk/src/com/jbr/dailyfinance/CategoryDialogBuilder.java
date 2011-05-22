/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jbr.dailyfinance;

import android.R.id;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import com.jbr.dailyfinance.db.CategoryEntity;

/**
 *
 * @author jbr
 */
public class CategoryDialogBuilder extends AlertDialog.Builder {

    public static final String TAG = "DailyFinance";
    private final Context         mContext;
    private final Cursor          mCursor;

    public CategoryDialogBuilder(Context context, Cursor listContent) {
        super(context);
        this.mContext = context;
        this.mCursor = listContent;
    }

    
    public AlertDialog create(OnClickListener listener) {
        build(listener);
        return super.create();
    }

    private void build(OnClickListener listener) {
        setTitle(R.string.category_prompt);
        final String[] items = new String[mCursor.getCount()+1];
        items[0] = mContext.getString(R.string.add);
        while (mCursor.moveToNext())
            items[mCursor.getPosition()+1] =
                    mCursor.getString(mCursor.getColumnIndex("name"));
        setItems(items, listener);
    }


    public abstract class CategoryOnClickListener implements OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int item) {
            if (item == 0) {
                addCategory();
            } else {
                mCursor.moveToPosition(item - 1);
                //Log.v(TAG, "allCategories " + mCursor.getPosition() + " " + item);
                final CategoryEntity entity = CategoryEntity.getEntity(mCursor);
                //Log.v(TAG, entity.toString());
                Long id = entity.getId();
                onClick(dialog, item, id);
            }
        }

        private void addCategory() {
            Log.v(TAG,"Starting edit category activity");
            Intent myIntent = new Intent(mContext, EditCategory.class);
            mContext.startActivity(myIntent);
        }

        public abstract void onClick(DialogInterface dialog, int item, Long id);

    }

}
