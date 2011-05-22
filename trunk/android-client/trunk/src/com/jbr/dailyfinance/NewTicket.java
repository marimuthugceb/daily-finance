/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jbr.dailyfinance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import com.jbr.dailyfinance.db.DatabaseHelper;
import com.jbr.dailyfinance.db.TicketEntity;
import java.util.GregorianCalendar;

/**
 *
 * @author jbr
 */
public class NewTicket extends Activity {
    public static final String TAG = "DailyFinance";
    private Spinner mStore;
    private Button mSave;
    private DatabaseHelper dbHelper;
    private DatePicker mTicketDate;
    private Long mId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_ticket);
        setTitle(R.string.new_ticket_title);
        mStore = (Spinner) findViewById(R.id.store);
        mTicketDate = (DatePicker) findViewById(R.id.date);
        mSave = (Button) findViewById(R.id.next);

        mStore.requestFocus();
        dbHelper = new DatabaseHelper(this);

        dbHelper.openDataBase();
        final SimpleCursorAdapter storeAdaptor =
                new SimpleCursorAdapter(this,
                        android.R.layout.simple_spinner_item,
                        dbHelper.getAllStores(),
                        new String[]{"name"},
                        new int[]{android.R.id.text1});

        storeAdaptor.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        mStore.setAdapter(storeAdaptor);

        mSave.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                TicketEntity te = new TicketEntity();
                GregorianCalendar date = new GregorianCalendar(
                        mTicketDate.getYear(), 
                        mTicketDate.getMonth(), 
                        mTicketDate.getDayOfMonth());
                te.setTicketDateAsDate(date.getTime());
                te.setStoreId(mStore.getSelectedItemId());
                
                long id = dbHelper.update(te);
                ticket(id);
            }
        });
    }

    private void ticket(long id) {
        Log.v(TAG,"Starting ticket activity, ticketid " + id);
        Intent myIntent = new Intent(this, Ticket.class);

        myIntent.putExtra("TICKETID", id);
        this.startActivity(myIntent);
        //this.startActivityForResult(myIntent, 2);
    }

    @Override
    protected void onDestroy() {
        if (dbHelper != null)
            dbHelper.close();
        super.onDestroy();
    }
/*
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
