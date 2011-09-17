package com.jbr.dailyfinance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.jbr.dailyfinance.db.DatabaseHelper;
import com.jbr.dailyfinance.db.ProductEntity;

public class ProductList extends Activity {

    public static final String TAG = "DailyFinance";
    private Button mSaveButton;
    private Button mScanButton;
    private Button mNewTicketButton;
    private EditText mEanText;
    private ListView mListView;
    private DatabaseHelper mDatabaseHelper;
    public Button.OnClickListener mScan = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            startActivityForResult(intent, 0);
        }
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_list);
        mSaveButton = (Button) findViewById(R.id.ok);
        mScanButton = (Button) findViewById(R.id.scan);
        mNewTicketButton = (Button) findViewById(R.id.new_ticket);
        mEanText = (EditText) findViewById(R.id.ean);
        mListView = (ListView) findViewById(R.id.list);

        mSaveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.v(TAG, "Knap klikket");
                lookUpEan(mEanText.getText().toString());
            }
        });

        mScanButton.setOnClickListener(mScan);

        mNewTicketButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new_ticket();
            }
        });

        Log.v(TAG, "Opens database");
        mDatabaseHelper = new DatabaseHelper(this);
        try {
            mDatabaseHelper.openDataBase();
            Log.v(TAG, "Database opened");
        } catch (SQLException e) {
            Log.e(TAG, "Database not opened", e);
        }

        CursorAdapter cursorAdapter = new CursorAdapter(this, mDatabaseHelper.getAllProducts()) {

            @Override
            public View newView(Context cntxt, Cursor cursor, ViewGroup vg) {
                ProductEntity p = ProductEntity.getEntity(cursor);

                Log.v(TAG, "cursor is " + p.getEan());
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = vi.inflate(R.layout.products_row, vg, false);
                return v;
            }

            @Override
            public void bindView(View view, Context cntxt, Cursor cursor) {
                ProductEntity p = ProductEntity.getEntity(cursor);
                if (p != null) {
                    TextView firstLine = (TextView) view.findViewById(R.id.firstLine);
                    TextView secondLine = (TextView) view.findViewById(R.id.secondLine);
                    TextView priceLine = (TextView) view.findViewById(R.id.price);
                    firstLine.setText(p.getName());
                    secondLine.setText(p.getManufacturer());
                    priceLine.setText(p.getPriceFormatted());
                }
            }

            @Override
            public void notifyDataSetChanged() {
                Log.v(TAG, "Requering cursor");
                super.notifyDataSetChanged();
            }
        };
        mListView.setAdapter(cursorAdapter);
        
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                lookUpProductById(l);
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume. Updating list");
        ((CursorAdapter)mListView.getAdapter()).getCursor().requery();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.product_menu, menu);
        return true;
    }

    private void new_ticket() {
        Log.v(TAG,"Starting newticket activity");
        Intent myIntent = new Intent(this, NewTicket.class);
        this.startActivityForResult(myIntent, 2);
    }

    private void edit_category(long id) {
        Log.v(TAG,"Starting edit category activity");
        Intent myIntent = new Intent(this, EditCategory.class);
        myIntent.putExtra("ID", id);
        this.startActivity(myIntent);
    }

    private void chooseCategory() {
        CategoryDialogBuilder cdb = new CategoryDialogBuilder(
                this, mDatabaseHelper.getAllCategories());

        AlertDialog alert = cdb.create(cdb.new CategoryOnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item, Long id) {
                edit_category(id);
            }
        });
        alert.show();
    }

    private void lookUpEan(String ean) {
        Log.v(TAG,"Starting product activity");
        Intent myIntent = new Intent(this, EditProduct.class);
        myIntent.putExtra("EAN", ean);
        this.startActivityForResult(myIntent, 1);
    }

    private void lookUpProductById(long id) {
        Log.v(TAG,"Starting product activity");
        Intent myIntent = new Intent(this, EditProduct.class);
        myIntent.putExtra("ID", id);
        this.startActivityForResult(myIntent, 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Log.v(TAG, String.format("Res: %s, format: %s", contents, format));
                mEanText.setText(contents);
                lookUpEan(contents);

                // Handle successful scan
            } else if (resultCode == RESULT_CANCELED) {
                Log.v(TAG, "Scan canceled");
            }
        }
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //Refresh list somehow
                Log.v(TAG, "Update listview");
                ProductEntity pe = (ProductEntity)intent.getSerializableExtra("PRODUCTENTITY");
                Toast.makeText(this, "Tilfojede " + pe.getName(), Toast.LENGTH_SHORT).show();
                ((CursorAdapter)mListView.getAdapter()).notifyDataSetChanged();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.edit_category:
            chooseCategory();
            break;
        case R.id.edit_shop:
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
