/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jbr.dailyfinance;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.jbr.dailyfinance.db.DatabaseHelper;
import com.jbr.dailyfinance.db.NoSimpleCursorAdapter;
import com.jbr.dailyfinance.db.ProductEntity;

/**
 *
 * @author jbr
 */
public class EditProduct extends Activity {
    public static final String TAG = "DailyFinance";
    private TextView mEan;
    private EditText mName;
    private EditText mManufacturer;
    private EditText mPrice;
    private Spinner  mCategory;
    private Button mSave;
    private Button mRemove;
    private DatabaseHelper dbHelper;
    private Long mId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_edit);
        mName = (EditText) findViewById(R.id.name);
        mManufacturer = (EditText) findViewById(R.id.manufacturer);
        mEan = (TextView) findViewById(R.id.ean);
        mSave = (Button) findViewById(R.id.save);
        mRemove = (Button) findViewById(R.id.remove);
        mPrice = (EditText) findViewById(R.id.singleprize);
        mCategory = (Spinner) findViewById(R.id.category);

        mName.requestFocus();
        dbHelper = new DatabaseHelper(this);
        dbHelper.openDataBase();

        final NoSimpleCursorAdapter categoryAdaptor =
                new NoSimpleCursorAdapter(this,
                        android.R.layout.simple_spinner_item,
                        dbHelper.getAllCategories(),
                        new String[]{"name"},
                        new int[]{android.R.id.text1});

        categoryAdaptor.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        mCategory.setAdapter(categoryAdaptor);

        if (getIntent().getExtras().getString("EAN") != null) {
            final String ean = getIntent().getExtras().getString("EAN");
            // If EAN allready exsist load it up
            ProductEntity e = dbHelper.searchEanAsEntity(ean);
            setFields(e);
            mEan.setText(ean);
        }
        if (getIntent().getExtras().getLong("ID", -1) != -1) {
            final Long id = getIntent().getExtras().getLong("ID");
            ProductEntity pe = dbHelper.searchProductById(id);
            setFields(pe);
            if (dbHelper.isProductIdInUse(id))
                mRemove.setVisibility(Button.INVISIBLE);
        } else
            mRemove.setVisibility(Button.INVISIBLE);



        mSave.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                ProductEntity productEntity = new ProductEntity();
                productEntity.setEan(mEan.getText().toString());
                productEntity.setManufacturer(mManufacturer.getText().toString());
                productEntity.setName(mName.getText().toString());
                productEntity.setCategoryId(mCategory.getSelectedItemId());
                Log.v(TAG, "Setting categoryid to " + productEntity.getCategoryId());
                if (mId != null)
                    productEntity.setId(mId);
                productEntity.setPriceFormatted(mPrice.getText().toString());
                long update = dbHelper.update(productEntity);

                getIntent().putExtra("PRODUCTENTITY", productEntity);

                setResult(update < 0 ? RESULT_CANCELED : RESULT_OK, getIntent());
                dbHelper.close();
                finish();
            }
        });

        mRemove.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dbHelper.removeProduct(mId);
                dbHelper.close();
                finish();
            }
        });
    }

    private void setFields(ProductEntity e) {
        if (e == null) {
            mName.setText("");
            mManufacturer.setText("");
            mPrice.setText("");
            mEan.setText("");
            mId = null;
            return;
        }
        mName.setText(e.getName());
        mManufacturer.setText(e.getManufacturer());
        mPrice.setText(e.getPriceFormatted());
        mEan.setText(e.getEan());
        mId = e.getId();
        Log.v(TAG, "Setting spinner to categoryid " + e.getCategoryId());
        mCategory.setSelection(((NoSimpleCursorAdapter)mCategory.getAdapter())
                .getIndexOfId(e.getCategoryId()), true);
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
/*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.products_menu, menu);
        return true;
    }

 * 
 */
}
