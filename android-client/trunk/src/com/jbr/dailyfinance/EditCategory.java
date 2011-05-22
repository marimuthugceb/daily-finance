/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jbr.dailyfinance;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.jbr.dailyfinance.db.CategoryEntity;
import com.jbr.dailyfinance.db.DatabaseHelper;

/**
 *
 * @author jbr
 */
public class EditCategory extends Activity {
    public static final String TAG = "DailyFinance";
    private EditText mName;
    private CheckBox mFood;
    private Button mSave;
    private Button mRemove;
    private DatabaseHelper dbHelper;
    private Long mId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_edit);
        mName = (EditText) findViewById(R.id.name);
        mSave = (Button) findViewById(R.id.save);
        mRemove = (Button) findViewById(R.id.remove);
        mFood = (CheckBox) findViewById(R.id.food);

        mName.requestFocus();
        dbHelper = new DatabaseHelper(this);
        dbHelper.openDataBase();

        if (getIntent().getExtras() != null &&
                getIntent().getExtras().getLong("ID", -1) != -1) {
            final Long id = getIntent().getExtras().getLong("ID");
            CategoryEntity ce = dbHelper.searchCategoryById(id);
            setFields(ce);
            if (dbHelper.isCategoryIdInUse(mId))
                mRemove.setVisibility(Button.INVISIBLE);
        } else
            mRemove.setVisibility(Button.INVISIBLE);



        mSave.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                CategoryEntity ce = new CategoryEntity();
                ce.setName(mName.getText().toString());
                ce.setType(mFood.isChecked()?"F":"N");
                ce.setId(mId);
                long update = dbHelper.update(ce);

                getIntent().putExtra("CATEGORYENTITY", ce);

                setResult(update < 0 ? RESULT_CANCELED : RESULT_OK, getIntent());
                dbHelper.close();
                finish();
            }
        });

        mRemove.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dbHelper.removeCategory(mId);
                setResult(RESULT_OK, getIntent());
                dbHelper.close();
                finish();
            }
        });

    }

    private void setFields(CategoryEntity e) {
        if (e == null) {
            mName.setText("");
            mFood.setSelected(false);
            mId = null;
            return;
        }
        mName.setText(e.getName());
        mFood.setChecked(e.getType().equalsIgnoreCase("F"));
        mId = e.getId();
    }

    @Override
    protected void onDestroy() {
        if (dbHelper != null)
            dbHelper.close();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        if (dbHelper != null)
            dbHelper.close();
        finish();
        super.onStop();
    }
}
