package mi.minhazurrahman.inventorymanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lara on 2/10/16.
 */
public class PhoneDbHelper extends SQLiteOpenHelper {

    public final static String DB_NAME = "phoneinventory.db";
    public final static int DB_VERSION = 1;
    public final static String LOG_TAG = PhoneDbHelper.class.getCanonicalName();

    public PhoneDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PhoneContract.PhoneEntry.CREATE_TABLE_STOCK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertItem(StockItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PhoneContract.PhoneEntry.COLUMN_NAME, item.getProductName());
        values.put(PhoneContract.PhoneEntry.COLUMN_PRICE, item.getPrice());
        values.put(PhoneContract.PhoneEntry.COLUMN_QUANTITY, item.getQuantity());
        values.put(PhoneContract.PhoneEntry.COLUMN_SUPPLIER_NAME, item.getSupplierName());
        values.put(PhoneContract.PhoneEntry.COLUMN_SUPPLIER_PHONE, item.getSupplierPhone());
        values.put(PhoneContract.PhoneEntry.COLUMN_SUPPLIER_EMAIL, item.getSupplierEmail());
        values.put(PhoneContract.PhoneEntry.COLUMN_IMAGE, item.getImage());
        long id = db.insert(PhoneContract.PhoneEntry.TABLE_NAME, null, values);
    }

    public Cursor readStock() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                PhoneContract.PhoneEntry._ID,
                PhoneContract.PhoneEntry.COLUMN_NAME,
                PhoneContract.PhoneEntry.COLUMN_PRICE,
                PhoneContract.PhoneEntry.COLUMN_QUANTITY,
                PhoneContract.PhoneEntry.COLUMN_SUPPLIER_NAME,
                PhoneContract.PhoneEntry.COLUMN_SUPPLIER_PHONE,
                PhoneContract.PhoneEntry.COLUMN_SUPPLIER_EMAIL,
                PhoneContract.PhoneEntry.COLUMN_IMAGE
        };
        Cursor cursor = db.query(
                PhoneContract.PhoneEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }

    public Cursor readItem(long itemId) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                PhoneContract.PhoneEntry._ID,
                PhoneContract.PhoneEntry.COLUMN_NAME,
                PhoneContract.PhoneEntry.COLUMN_PRICE,
                PhoneContract.PhoneEntry.COLUMN_QUANTITY,
                PhoneContract.PhoneEntry.COLUMN_SUPPLIER_NAME,
                PhoneContract.PhoneEntry.COLUMN_SUPPLIER_PHONE,
                PhoneContract.PhoneEntry.COLUMN_SUPPLIER_EMAIL,
                PhoneContract.PhoneEntry.COLUMN_IMAGE
        };
        String selection = PhoneContract.PhoneEntry._ID + "=?";
        String[] selectionArgs = new String[] { String.valueOf(itemId) };

        Cursor cursor = db.query(
                PhoneContract.PhoneEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        return cursor;
    }

    public void updateItem(long currentItemId, int quantity) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PhoneContract.PhoneEntry.COLUMN_QUANTITY, quantity);
        String selection = PhoneContract.PhoneEntry._ID + "=?";
        String[] selectionArgs = new String[] { String.valueOf(currentItemId) };
        db.update(PhoneContract.PhoneEntry.TABLE_NAME,
                values, selection, selectionArgs);
    }

    public void sellOneItem(long itemId, int quantity) {
        SQLiteDatabase db = getWritableDatabase();
        int newQuantity = 0;
        if (quantity > 0) {
            newQuantity = quantity -1;
        }
        ContentValues values = new ContentValues();
        values.put(PhoneContract.PhoneEntry.COLUMN_QUANTITY, newQuantity);
        String selection = PhoneContract.PhoneEntry._ID + "=?";
        String[] selectionArgs = new String[] { String.valueOf(itemId) };
        db.update(PhoneContract.PhoneEntry.TABLE_NAME,
                values, selection, selectionArgs);
    }
}
