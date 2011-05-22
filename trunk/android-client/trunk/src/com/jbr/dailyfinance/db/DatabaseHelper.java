package com.jbr.dailyfinance.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.GregorianCalendar;


public class DatabaseHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.jbr.dailyfinance/databases/";
    private static String DB_NAME = "itemsDb";
    public static final SimpleDateFormat dateParser = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat monthParser = new SimpleDateFormat("yyyyMM");
    public static final DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    private SQLiteDatabase myDataBase;
    private final Context myContext;
    public static final String TAG = "DailyFinance";
    private static final String 
    	SQL_SUM_CATEGORY_TYPES = "select c.type, substr(t.ticketdate, 1,6), sum(tl.amount) " +
                                "from products p " +
                                "join categories c on c._id = p.categoryid " +
                                "join ticketlines tl on tl.productid = c._id " +
                                "join tickets t on tl.ticketid = t._id %s " +
                                "group by c.type, substr(t.ticketdate, 1,6)";
    private static final String 
        SQL_SUM_DATE_FILTER = "where t.ticketdate > ? and t.ticketdate < ? ";

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase()  {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            //do nothing - database already exist
        } else {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            //database does't exist yet.
        }

        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {
        createDataBase();

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, 
                SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null) {
            myDataBase.close();
        }

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

    public Cursor searchEan(String ean) {
        String[] params = {ean};
        return myDataBase.rawQuery("select * "
                + "from products where ean = ?", params);
    }

    public ProductEntity searchEanAsEntity(String ean) {
        Cursor c = searchEan(ean);
        c.moveToFirst();
        return ProductEntity.getEntity(c);
    }

    public ProductEntity searchProductById(long id) {
        String[] params = {new Long(id).toString()};
        Cursor c = myDataBase.rawQuery(
                "select * from products where _id = ?", params);
        c.moveToFirst();
        return ProductEntity.getEntity(c);

    }

    public Cursor getTicketLinesForTicketId(long ticketid) {
        String[] params = {new Long(ticketid).toString()};
        return myDataBase.rawQuery(
                "select * from ticketlines where ticketid = ?",
                params);
    }

    public Cursor getTicketsOverview() {
        return myDataBase.rawQuery(
                "select t._id as _id, t.ticketdate, s.name, sum(tl.amount) as amountsum " +
                "from tickets t left outer join ticketlines tl on tl.ticketid = t._id, stores s " +
                "where s._id = t.storeid " +
                "group by t._id, s.name " +
                "order by t.ticketdate desc", null);
    }

    public Cursor getTicketOverview(long ticketid) {
        String[] params = {new Long(ticketid).toString()};
        return myDataBase.rawQuery(
                "select tl._id, tl.productid, tl.ticketid, tl.amount, p.categoryid, "
                + "p.name productname, p.manufacturer, c.name categoryname, c.type "
                + "from ticketlines tl join products p on p._id = tl.productid "
                + "join categories c on c._id = p.categoryid "
                + "where ticketid = ?", params);
    }

    public Cursor getTicketCursorById(long ticketId) {
        String[] params = {new Long(ticketId).toString()};
        final Cursor cursor =
                myDataBase.rawQuery("select * from tickets where _id = ?", params);
        Log.v(TAG, "TicketCursor has rows? " + !cursor.isAfterLast());
        return cursor;
    }

    public TicketEntity getTicketEntityById(long ticketid) {
        final Cursor ticketCursorById = getTicketCursorById(ticketid);
        if (!ticketCursorById.moveToFirst())
            return null;
        final TicketEntity entity =
                TicketEntity.getEntity(ticketCursorById);
        Log.v(TAG, "TicketEntity is " + entity);
        return entity;
    }

    public Cursor getTicketLineCursorById(long ticketLineId) {
        String[] params = {new Long(ticketLineId).toString()};
        final Cursor cursor =
                myDataBase.rawQuery("select * from ticketlines where _id = ?", params);
        Log.v(TAG, "TicketLineCursor has rows? " + !cursor.isAfterLast());
        return cursor;
    }

    public TicketLineEntity getTicketLineEntityById(long ticketLineId) {
        final Cursor ticketLineCursorById = getTicketLineCursorById(ticketLineId);
        if (!ticketLineCursorById.moveToFirst())
            return null;
        final TicketLineEntity entity =
                TicketLineEntity.getEntity(ticketLineCursorById);
        Log.v(TAG, "TicketLineEntity is " + entity);
        return entity;
    }

    public Cursor getStoreCursorById(long storeid) {
        String[] params = {new Long(storeid).toString()};
        return myDataBase.rawQuery(
                "select * from stores where _id = ?",
                params);
    }

    public StoreEntity getStoreEntityById(long storeid) {
        final Cursor storeCursorById = getStoreCursorById(storeid);
        if (!storeCursorById.moveToFirst())
            return null;
        return StoreEntity.getEntity(storeCursorById);
    }

    public Cursor getAllProducts() {
        return myDataBase.rawQuery("select * "
                + "from products "
                + "where name <> '-' "
                + "order by name ", null);
    }

    public Cursor getAllProductsOfCategoryId(long categoryId) {
        String[] params = {new Long(categoryId).toString()};
        Log.v(TAG, "getAllProductsOfCategoryId for id " + categoryId);
        return myDataBase.rawQuery("select * "
                + "from products "
                + "where categoryid = ? "
                + "order by name ", params);
    }
    
    

    public Cursor getAllStores() {
        return myDataBase.rawQuery("select * from stores order by name", null);
    }

    public Cursor getAllCategories() {
        return myDataBase.rawQuery("select * from categories order by name", null);
    }

    public boolean isCategoryIdInUse(long id) {
        String[] params = {new Long(id).toString()};
        final Cursor categoriesInUse = myDataBase.rawQuery(
                "select _id from products where name <> '-' and categoryid = ?", params);
        while (categoriesInUse.moveToNext())
            if (isProductIdInUse(categoriesInUse.getLong(0)))
                return true;
        return false;
    }

    public boolean isProductIdInUse(long id) {
        String[] params = {new Long(id).toString()};
        return myDataBase.rawQuery("select 1 from ticketlines where productid = ?", params).getCount()>0;
    }
    
    public boolean isStoreIdInUse(long id) {
        String[] params = {new Long(id).toString()};
        return myDataBase.rawQuery("select 1 from tickets where storeid = ?", params).getCount()>0;
    }
    /**
     * Get the sum by types of this month
     * @return
     */
    private Cursor getSumByTypeByMonth(int monthsBack) {
        GregorianCalendar startCal = new GregorianCalendar();
        startCal.add(GregorianCalendar.MONTH, -monthsBack);
        GregorianCalendar endCal = new GregorianCalendar();
        endCal.add(GregorianCalendar.MONTH, 1-monthsBack);
    	String startDateString = monthParser.format(startCal.getTime());
    	String endDateString = monthParser.format(endCal.getTime());
    	String sql = String.format(SQL_SUM_CATEGORY_TYPES, SQL_SUM_DATE_FILTER);
    	String[] params = {startDateString, endDateString};
    	Log.d(TAG, "getSumTypeByMonth: MonthsBack: " + monthsBack + ". SQL is " + sql +
                " params is: " + Arrays.toString(params));
    	return myDataBase.rawQuery(sql, params);
    }

    public double getSumByTypeByMonth(int monthsBack, String type) {
        Cursor c = getSumByTypeByMonth(monthsBack);
        while (c.moveToNext()) {
            if (c.getString(0).equalsIgnoreCase(type))
                return c.getDouble(2)/100d;
        }
        return 0d;
    }

    public static String currencyFormat(Object value) {
        return decimalFormat.format(value);
    }
    
    public Cursor getSumByTypeLastMonth() {
    	return getSumByTypeByMonth(1);
    }

    public Cursor getSumByTypeThisMonth() {
    	return getSumByTypeByMonth(0);
    }

    
    public Cursor getSumByTypeAllTime() {
       	String sql = String.format(SQL_SUM_CATEGORY_TYPES, "");
    	Log.d(TAG, "getSumTypeByTypeAllTime: SQL is " + sql);
    	return myDataBase.rawQuery(sql, null);
    }

    public long update(ProductEntity e) {
        return e.updateOrInsertInDb(myDataBase);
    }

    public long update(TicketEntity e) {
        return e.updateOrInsertInDb(myDataBase);
    }

    public long update(TicketLineEntity e) {
        return e.updateOrInsertInDb(myDataBase);
    }

    public void removeTicket(long ticketId) {
        String[] params = {new Long(ticketId).toString()};
        myDataBase.delete("ticketlines", "ticketid = ?", params);
        myDataBase.delete("tickets", "_id = ?", params);
    }
    public void removeTicketLine(long ticketLineId) {
        String[] params = {new Long(ticketLineId).toString()};
        myDataBase.delete("ticketlines", "_id = ?", params);
    }

    public CategoryEntity searchCategoryById(Long id) {
        String[] params = {new Long(id).toString()};
        Cursor c = myDataBase.rawQuery(
                "select * from categories where _id = ?", params);
        c.moveToFirst();
        return CategoryEntity.getEntity(c);
    }

    public long update(CategoryEntity e) {
        boolean newCategory = e.getId() == null;

        final long newId = e.updateOrInsertInDb(myDataBase);
        if (newCategory) {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setName("-");
            productEntity.setManufacturer("");
            productEntity.setCategoryId(newId);
            update(productEntity);
        }

        return newId;
    }

    public void removeCategory(long id) {
        String[] params = {new Long(id).toString()};
        myDataBase.delete("products", "categoryid = ?", params);
        myDataBase.delete("categories", "_id = ?", params);
    }

    public void removeProduct(long id) {
        String[] params = {new Long(id).toString()};
        myDataBase.delete("products", "_id = ?", params);
    }


}
