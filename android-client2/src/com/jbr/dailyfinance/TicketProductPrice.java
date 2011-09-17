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
import android.widget.Toast;

/**
 *
 * @author jbr
 */
public class TicketProductPrice extends Activity {
    public static final String TAG = "DailyFinance";
    private Float mSinglePriceAsFloat;
    private Button mSave;
    private EditText mSinglePrice;
    private EditText mPices;
    private EditText mMultiPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_product_price);
        //setTitle(R.string.ticket_product_price_title);
        mSinglePriceAsFloat = getIntent().getExtras().getFloat("SINGLEPRICE");
        mSinglePrice = (EditText)findViewById(R.id.singleprice);
        if (mSinglePriceAsFloat != null)
            mSinglePrice.setText(mSinglePriceAsFloat.toString());
        mPices = (EditText)findViewById(R.id.pices);
        mMultiPrice = (EditText)findViewById(R.id.multiprice);

        mSave = (Button)findViewById(R.id.save);
        mSinglePrice.setSelectAllOnFocus(true);
        mSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (mMultiPrice.getText() != null &&
                            mMultiPrice.getText().length() > 0) {
                        getIntent().putExtra("MULTIPRICE",
                                new Float(mMultiPrice.getText().toString()));
                        Log.d(TAG, "Returning multiprice as " +
                                getIntent().getExtras().get("MULTIPRICE").toString());

                    }
                    if (mSinglePrice.getText() != null &&
                            mSinglePrice.getText().length() > 0) {
                        getIntent().putExtra("SINGLEPRICE",
                            new Float(mSinglePrice.getText().toString()));
                        Log.d(TAG, "Returning singleprice as " +
                                getIntent().getExtras().get("SINGLEPRICE").toString());

                    }
                    /*
                    getIntent().putExtra("MULTIPRICE",
                            (mMultiPrice.getText() == null ||
                            mMultiPrice.getText().length() == 0) ? null :
                                new Float(mMultiPrice.getText().toString()));
                     *
                    getIntent().putExtra("SINGLEPRICE",
                            (mSinglePrice.getText() == null ||
                            mSinglePrice.getText().length() == 0) ? null :
                                new Float(mSinglePrice.getText().toString()));
                     */
                    getIntent().putExtra("PICES",
                            (mPices.getText() == null ||
                            mPices.getText().length() == 0) ?
                        null : new Long(mPices.getText().toString()));

                    setResult(RESULT_OK, getIntent());
                    finish();
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(),
                            "Fejl ved angivelse af priser, " + e.toString(),
                            Toast.LENGTH_LONG).show();
                    Log.e(TAG, e.toString(), e);
                }
            }
        });
    }
}
