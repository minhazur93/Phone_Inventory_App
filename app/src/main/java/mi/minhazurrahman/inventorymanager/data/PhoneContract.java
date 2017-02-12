package mi.minhazurrahman.inventorymanager.data;

import android.provider.BaseColumns;

/**
 * Created by lara on 2/10/16.
 */
public class PhoneContract {

    public PhoneContract() {
    }

    public static final class PhoneEntry implements BaseColumns {

        public static final String TABLE_NAME = "stock";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_SUPPLIER_NAME = "supplier_name";
        public static final String COLUMN_SUPPLIER_PHONE = "supplier_phone";
        public static final String COLUMN_SUPPLIER_EMAIL = "supplier_email";
        public static final String COLUMN_IMAGE = "image";

        public static final String CREATE_TABLE_STOCK = "CREATE TABLE " +
                PhoneContract.PhoneEntry.TABLE_NAME + "(" +
                PhoneContract.PhoneEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PhoneContract.PhoneEntry.COLUMN_NAME + " TEXT NOT NULL," +
                PhoneContract.PhoneEntry.COLUMN_PRICE + " TEXT NOT NULL," +
                PhoneContract.PhoneEntry.COLUMN_QUANTITY + " INTEGER NOT NULL DEFAULT 0," +
                PhoneContract.PhoneEntry.COLUMN_SUPPLIER_NAME + " TEXT NOT NULL," +
                PhoneContract.PhoneEntry.COLUMN_SUPPLIER_PHONE + " TEXT NOT NULL," +
                PhoneContract.PhoneEntry.COLUMN_SUPPLIER_EMAIL + " TEXT NOT NULL," +
                PhoneEntry.COLUMN_IMAGE + " TEXT NOT NULL" + ");";
    }
}
