/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jbr.dailyfinance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.jbr.dailyfinance.db.CategoryEntity;
import com.jbr.dailyfinance.db.DatabaseHelper;
import com.jbr.dailyfinance.db.ProductEntity;
import com.jbr.dailyfinance.db.StoreEntity;
import com.jbr.dailyfinance.db.TicketEntity;
import com.jbr.dailyfinance.db.TicketLineEntity;

/**
 *
 * @author jbr
 */
public class Ticket extends Activity {
    public static final String TAG = "DailyFinance";
    private Long ticketId;
    private DatabaseHelper dbHelper;
    //private Button mScan;
    //private Button mAddManually;
    private ListView mTicketLines;
    private TextView mSumValue;
    private TicketEntity mTicketEntity;
    private TicketLineEntity mNewTicketLineEntity;
    public Button.OnClickListener mScanListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            startActivityForResult(intent, 0);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket);
        dbHelper = new DatabaseHelper(this);
        dbHelper.openDataBase();
        ticketId = getIntent().getExtras().getLong("TICKETID");
        mTicketEntity = dbHelper.getTicketEntityById(ticketId);
        if (mTicketEntity == null)
            Log.e(TAG, "mTicketEntity is null and tickid is " + ticketId);
        //mScan = (Button)findViewById(R.id.scan);
        //mScan.setOnClickListener(mScanListener);
        //mAddManually = (Button)findViewById(R.id.add_manually);
        /*mAddManually.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                chooseCategory();
            }
        });
 */
        mTicketLines = (ListView)findViewById(R.id.ticketlines);
        mSumValue = (TextView) findViewById(R.id.sum_values);

        registerForContextMenu(mTicketLines);
        final Long storeid = mTicketEntity.getStoreId();
        final StoreEntity storeEntity = dbHelper.getStoreEntityById(storeid);
        final String storeName = storeEntity.getName();
        
        final String titleString = String.format(getString(R.string.ticket_title), 
        		storeName, mTicketEntity.getTicketDateFormatted());
//        final String titleString = getString(R.string.ticket_title);
        setTitle(titleString);

        //mStore.requestFocus();
        /*
        final SimpleCursorAdapter categoryAdapter =
                new SimpleCursorAdapter(this,
                        android.R.layout.simple_spinner_item,
                        dbHelper.getAllCategories(),
                        new String[]{"name"},
                        new int[]{android.R.id.text1});

        categoryAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        mCategories.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> av, View view, int i, long l) {
                Log.v(TAG, "onItemSelected, selected item id " + mCategories.getSelectedItemId());
                mProducts.setAdapter(getNewProductAdapter());
                mProducts.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> av) {
                mProducts.setVisibility(View.INVISIBLE);
            }
        });

        mCategories.setAdapter(categoryAdapter);
        mProducts.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> av, View view, int i, long l) {
                startTicketProductPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> av) {
            }
        });

        */
        CursorAdapter ticketLinesAdapter = new CursorAdapter(
                this, dbHelper.getTicketOverview(ticketId)) {

            @Override
            public View newView(Context cntxt, Cursor cursor, ViewGroup vg) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                View v = vi.inflate(R.layout.products_row, vg, false);
                return v;
            }

            @Override
            public void bindView(View view, Context cntxt, Cursor cursor) {
                if (cursor != null) {
                    TextView firstLine = (TextView) view.findViewById(R.id.firstLine);
                    TextView secondLine = (TextView) view.findViewById(R.id.secondLine);
                    TextView priceLine = (TextView) view.findViewById(R.id.price);
                    if (cursor.getString(5).equalsIgnoreCase("-")) {
                        firstLine.setText(cursor.getString(7));
                        secondLine.setText("(" + cursor.getString(8) +  ")");
                    } else {
                        firstLine.setText(cursor.getString(5)); //productname
                        secondLine.setText(cursor.getString(6) + " (" + cursor.getString(7)
                                + "-" + cursor.getString(8) +  ")");
                    }
                    priceLine.setText(DatabaseHelper.decimalFormat.format(cursor.getDouble(3)/100));
                }
            }

            @Override
            public void notifyDataSetChanged() {
                Log.v(TAG, "Requering cursor");
                super.notifyDataSetChanged();
            }
        };  
        mTicketLines.setAdapter(ticketLinesAdapter);

        mTicketLines.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                Log.d(TAG, "OnClick " + l);
                startTicketProductPrice(
                        dbHelper.getTicketLineEntityById(l).getAmountAsFloat());
            }
        });
        updateSumValue();
    }

    private void updateSumValue() {
        double sum = calcSum();
        mSumValue.setText(DatabaseHelper.currencyFormat(sum));
    }

    private double calcSum() {
        final Cursor c = ((CursorAdapter)mTicketLines.getAdapter()).getCursor();
        double sum = 0d;
        //c.requery();
        c.moveToPosition(-1);
        while (c.moveToNext()) {
            sum += c.getDouble(3) / 100;
        }
        return sum;
    }
/*
    private SimpleCursorAdapter getNewProductAdapter() {
        final SimpleCursorAdapter productAdapter = new SimpleCursorAdapter(
                this, android.R.layout.simple_spinner_item,
                dbHelper.getAllProductsOfCategoryId(mCategories.getSelectedItemId()),
                new String[]{"name", "manufacturer"}, new int[]{android.R.id.text1, android.R.id.text2});
        productAdapter.setDropDownViewResource(
        android.R.layout.simple_spinner_dropdown_item);

        return productAdapter;
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ticket_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.add_manually:
        	chooseCategory();
            break;
        case R.id.add_by_scan:
        	Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            startActivityForResult(intent, 0);
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void chooseCategory() {
        CategoryDialogBuilder cdb = new CategoryDialogBuilder(
                this, dbHelper.getAllCategories());

        AlertDialog alert = cdb.create(cdb.new CategoryOnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item, Long id) {
                chooseProduct(id);
            }
        });
        alert.show();
    }

    private void chooseProduct(long categoryId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.product_prompt);
        final Cursor products = dbHelper.getAllProductsOfCategoryId(categoryId);
        final String[] items = new String[products.getCount()];
        while (products.moveToNext())
            items[products.getPosition()] =
                    products.getString(products.getColumnIndex("name")) + " - " +
                    products.getString(products.getColumnIndex("manufacturer"));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                products.moveToPosition(item);
                ProductEntity product = ProductEntity.getEntity(products);
                mNewTicketLineEntity = new TicketLineEntity();
                mNewTicketLineEntity.setProductId(product.getId());
                startTicketProductPrice(product.getPriceAsDouble());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private void startTicketProductPrice(Float lastPrice) {
        Intent myIntent = new Intent(this, TicketProductPrice.class);
        myIntent.putExtra("SINGLEPRICE", lastPrice);
        startActivityForResult(myIntent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
        case 0:
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Log.v(TAG, String.format("Res: %s, format: %s", contents, format));
                treatEan(contents);

                // Handle successful scan
            } else if (resultCode == RESULT_CANCELED) {
                Log.v(TAG, "Scan canceled");
            }
            break;

        case 1:
            ProductEntity pe = (ProductEntity)intent.getSerializableExtra("PRODUCTENTITY");
            if (pe == null)
                return;
            else
                treatEan(pe.getEan());
            break;
        case 2:
            if (resultCode == RESULT_OK) {
                
                Float singlePrice = (Float)intent.getSerializableExtra("SINGLEPRICE");

                mNewTicketLineEntity.setAmountAsDouble(new Double(singlePrice));
                mNewTicketLineEntity.setTicketId(ticketId);
                dbHelper.update(mNewTicketLineEntity);
                ((CursorAdapter)mTicketLines.getAdapter()).notifyDataSetChanged();
                //Toast.makeText(this, "Tilfojede ny linie", Toast.LENGTH_SHORT).show();
            }
        }
        updateSumValue();


    }

    @Override
    protected void onDestroy() {
        if (dbHelper != null)
            dbHelper.close();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mTicketLines != null) {
            ((CursorAdapter)mTicketLines.getAdapter()).getCursor().requery();
            ((CursorAdapter)mTicketLines.getAdapter()).notifyDataSetChanged();
            updateSumValue();
        }
    }




    private void startProductActivity(String ean) {
        Log.v(TAG,"Starting product activity");
        Intent myIntent = new Intent(this, EditProduct.class);
        myIntent.putExtra("EAN", ean);
        this.startActivityForResult(myIntent, 1);
    }
    /**
     * Treat the ean scan. 
     * Check if EAN is known. If true then add a ticketline with the productid
     * If unknown then start product activity to add the EAN
     */
    private void treatEan(String ean) {
        ProductEntity pe = dbHelper.searchEanAsEntity(ean);
        if (pe != null) {
            // TODO ask for price, and number
            // Add to ticketlines
            TicketLineEntity tl = new TicketLineEntity();
            tl.setAmount(pe.getPrice());
            tl.setNumber(1);
            tl.setProductId(pe.getId());
            tl.setTicketId(ticketId);
            dbHelper.update(tl);
        } else {
            // EAN unknown, show product activity
            startProductActivity(ean);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                                    ContextMenuInfo menuInfo) {
        Log.v(TAG, "Create Context menu " + v.getId());
        if (v.getId()==R.id.ticketlines) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.ticket_context_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.remove:
                AdapterContextMenuInfo menuInfo =
                        (AdapterContextMenuInfo) item.getMenuInfo();
                long ticketLineId = mTicketLines.getAdapter().getItemId(menuInfo.position);
                Log.v(TAG, "Removing ticketlineid " + ticketLineId);

                dbHelper.removeTicketLine(ticketLineId);
                ((CursorAdapter)mTicketLines.getAdapter()).getCursor().requery();
                ((CursorAdapter)mTicketLines.getAdapter()).notifyDataSetChanged();
                updateSumValue();
                return true;
        default:
            return super.onContextItemSelected(item);
        }
    }
}
