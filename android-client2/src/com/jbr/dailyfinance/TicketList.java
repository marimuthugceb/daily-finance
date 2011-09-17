/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jbr.dailyfinance;

import java.text.ParseException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jbr.dailyfinance.db.DatabaseHelper;

/**
 *
 * @author jbr
 */
public class TicketList extends Activity {
    public static final String   TAG = "DailyFinance.TicketList";
    private final DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);
    private ListView             mTicketList;
    private TextView             mSumText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabaseHelper.openDataBase();
        setContentView(R.layout.ticket_list);
        mTicketList = (ListView) findViewById(R.id.tickets);
        mSumText = (TextView) findViewById(R.id.sum_values);
        CursorAdapter cursorAdapter = new CursorAdapter(this,
                mDatabaseHelper.getTicketsOverview()) {

            @Override
            public View newView(Context cntxt, Cursor cursor, ViewGroup vg) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = vi.inflate(R.layout.ticket_row, vg, false);
                return v;
            }

            @Override
            public void bindView(View view, Context cntxt, Cursor cursor) {
                if (cursor != null) {
                    TextView firstLine = (TextView) view.findViewById(R.id.firstLine);
                    TextView secondLine = (TextView) view.findViewById(R.id.secondLine);
                    TextView priceLine = (TextView) view.findViewById(R.id.price);
                    firstLine.setText(cursor.getString(2));
                    try {
                        secondLine.setText(DatabaseHelper.dateFormat.format(
                                DatabaseHelper.dateParser.parse(cursor.getString(1))));
                    } catch (ParseException ex) {
                        
                    }
                    priceLine.setText(DatabaseHelper.decimalFormat.format(cursor.getFloat(3)/100));
                }
            }

            @Override
            public void notifyDataSetChanged() {
                Log.v(TAG, "Requering cursor");
                super.notifyDataSetChanged();
            }
        };
        Log.d(TAG, "TicketList " + mTicketList);
        Log.d(TAG, "Cursoradaptor " + cursorAdapter);

        mTicketList.setAdapter(cursorAdapter);
        mTicketList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                Log.d(TAG, "OnClick " + l);
                startTicketActivity(l);
            }
        });
        updateSum();
        registerForContextMenu(mTicketList);
    }

    private void updateSum() {
        // There is somehow switched around. Dont know why
        mSumText.setText(String.format(getString(R.string.sum_on_tickets), 
                DatabaseHelper.currencyFormat(mDatabaseHelper.getSumByTypeByMonth(0, "N")),
                DatabaseHelper.currencyFormat(mDatabaseHelper.getSumByTypeByMonth(0, "F"))));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ticket_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.settings:
            startProductListActivity();
            break;
        case R.id.add_ticket:
            startNewTicketActivity();
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                                    ContextMenuInfo menuInfo) {
        Log.v(TAG, "Create Context menu " + v.getId());
        if (v.getId()==R.id.tickets) {
            AdapterView.AdapterContextMenuInfo info =
                    (AdapterView.AdapterContextMenuInfo)menuInfo;
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.ticket_list_context_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.remove_ticket:
                AdapterContextMenuInfo menuInfo =
                        (AdapterContextMenuInfo) item.getMenuInfo();
                long ticketId = mTicketList.getAdapter().getItemId(menuInfo.position);
                Log.v(TAG, "Removing ticketid " + ticketId);

                mDatabaseHelper.removeTicket(ticketId);
                ((CursorAdapter)mTicketList.getAdapter()).getCursor().requery();
                ((CursorAdapter)mTicketList.getAdapter()).notifyDataSetChanged();
                return true;
        default:
            return super.onContextItemSelected(item);
        }
    }

    private void startProductListActivity() {
        Intent myIntent = new Intent(this, ProductList.class);
        this.startActivity(myIntent);
    }

    private void startTicketActivity(long ticketId) {
        Intent myIntent = new Intent(this, Ticket.class);
        myIntent.putExtra("TICKETID", ticketId);
        this.startActivity(myIntent);
    }

    private void startNewTicketActivity() {
        Log.v(TAG,"Starting newticket activity");
        Intent myIntent = new Intent(this, NewTicket.class);
        //this.startActivityForResult(myIntent, 2);
        this.startActivity(myIntent);
        
    }

    @Override
    protected void onDestroy() {
        if (mDatabaseHelper != null)
            mDatabaseHelper.close();
        super.onDestroy();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (mTicketList != null) {
            ((CursorAdapter)mTicketList.getAdapter()).getCursor().requery();
            ((CursorAdapter)mTicketList.getAdapter()).notifyDataSetChanged();
            updateSum();
        }
    }



}
