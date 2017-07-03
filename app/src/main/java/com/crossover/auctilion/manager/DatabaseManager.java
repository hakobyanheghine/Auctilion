package com.crossover.auctilion.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.crossover.auctilion.data.Auction;
import com.crossover.auctilion.data.Item;
import com.crossover.auctilion.data.User;

import java.util.ArrayList;

/**
 * Created by heghine on 2/11/17.
 */

public class DatabaseManager {
    private static DatabaseManager instance;

    private DatabaseManager() {}

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }

        return instance;
    }

    private static int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "auctilion";

    private static final String TABLE_USER = "user";
    public static final String KEY_USER_ID = "id";
    public static final String KEY_USER_NAME = "name";
    public static final String KEY_USER_EMAIL = "email";
    public static final String KEY_USER_PASSWORD = "password";

    private static final String TABLE_ITEM = "item";
    public static final String KEY_ITEM_ID = "id";
    public static final String KEY_ITEM_NAME = "name";
    public static final String KEY_ITEM_DESCRIPTION = "description";
    public static final String KEY_ITEM_PRICE = "price";
    public static final String KEY_ITEM_PRICE_CURRENCY = "price_currency";

    private static final String TABLE_AUCTION = "auction";
    public static final String KEY_AUCTION_ID = "id";
    public static final String KEY_AUCTION_ITEM_ID = "item_id";
    public static final String KEY_AUCTION_START_DATE = "start_date";
    public static final String KEY_AUCTION_DURATION = "duration";

    private static final String CREATE_USER = "CREATE TABLE " + TABLE_USER + "("
            + KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_USER_NAME + " TEXT NOT NULL, "
            + KEY_USER_EMAIL + " TEXT NOT NULL, "
            + KEY_USER_PASSWORD + " TEXT NOT NULL);";


    private static final String CREATE_ITEM = "CREATE TABLE " + TABLE_ITEM + "("
            + KEY_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_ITEM_NAME + " TEXT NOT NULL, "
            + KEY_ITEM_DESCRIPTION + " TEXT NOT NULL, "
            + KEY_ITEM_PRICE + " REAL NOT NULL, "
            + KEY_ITEM_PRICE_CURRENCY + " TEXT NOT NULL);";

    private static final String CREATE_AUCTION = "CREATE TABLE " + TABLE_AUCTION + "("
            + KEY_AUCTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_AUCTION_ITEM_ID + " INTEGER NOT NULL, "
            + KEY_AUCTION_START_DATE + " INTEGER, "
            + KEY_AUCTION_DURATION + " INTEGER);";

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            // this will create necessary tables for app to function normally
            sqLiteDatabase.execSQL(CREATE_USER);
            sqLiteDatabase.execSQL(CREATE_ITEM);
            sqLiteDatabase.execSQL(CREATE_AUCTION);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private boolean isInitialized;

    public void init(Context context) {
        if (databaseHelper != null) {
            databaseHelper.close();
        }

        if (database != null) {
            database.close();
        }

        try { // try to obtain writable database
            databaseHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
            database = databaseHelper.getWritableDatabase();
            isInitialized = true;
        } catch (Exception e) {
            e.printStackTrace();
            context.deleteDatabase(DATABASE_NAME);
            // try one more time
            try {
                database = databaseHelper.getWritableDatabase();
                isInitialized = true;
            } catch (Exception ex) {
                ex.printStackTrace();
                isInitialized = false;
            }
        }
    }

    /**
     *
     * Start of USER table stuff
     *
     */

    public ArrayList<User> getAllUsers() {
        ArrayList<User> result = new ArrayList<>();

        if (!isInitialized) {
            return result;
        }

        Cursor cursor = null;

        try {
            cursor = database.rawQuery("SELECT * FROM " + TABLE_USER + ";", null);
            boolean hasRows = cursor.moveToFirst();
            if (hasRows) {
                while (!cursor.isAfterLast()) {
                    result.add(new User(cursor));
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return result;
    }

    public User getUserWithEmailAndPass(String email, String pass) {
        User result = null;

        if (!isInitialized) {
            return result;
        }

        Cursor cursor = null;

        try {
            cursor = database.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE email = ? AND password = ?;", new String[] {email, pass});
            boolean hasRows = cursor.moveToFirst();
            if (hasRows) {
                while (!cursor.isAfterLast()) {
                    result = new User(cursor);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return result;
    }

    public long addNewUser(String name, String email, String pass) {
        if (!isInitialized) {
            return -1;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USER_NAME, name);
        contentValues.put(KEY_USER_EMAIL, email);
        contentValues.put(KEY_USER_PASSWORD, pass);

        long id = database.insert(TABLE_USER, null, contentValues);

        return id;
    }


    /**
     * End of USER table stuff
     */


    /**
     *
     * Start of ITEM table stuff
     *
     */

    public ArrayList<Item> getAllItems() {
        ArrayList<Item> result = new ArrayList<>();

        if (!isInitialized) {
            return result;
        }

        Cursor cursor = null;

        try {
            cursor = database.rawQuery("SELECT * FROM " + TABLE_ITEM + ";", null);
            boolean hasRows = cursor.moveToFirst();
            if (hasRows) {
                while (!cursor.isAfterLast()) {
                    result.add(new Item(cursor));
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return result;
    }

    public long addNewItem(String name, String desc, double price, String priceCurrency) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ITEM_NAME, name);
        contentValues.put(KEY_ITEM_DESCRIPTION, desc);
        contentValues.put(KEY_ITEM_PRICE, price);
        contentValues.put(KEY_ITEM_PRICE_CURRENCY, priceCurrency);

        long id = database.insert(TABLE_ITEM, null, contentValues);

        return id;
    }

    /**
     * End of ITEM table stuff
     */


    /**
     * Start of AUCTION table stuff
     */

    public ArrayList<Auction> getAllAuctions() {
        ArrayList<Auction> result = new ArrayList<>();

        if (!isInitialized) {
            return result;
        }

        Cursor cursor = null;

        try {
            cursor = database.rawQuery("SELECT * FROM " + TABLE_AUCTION + ";", null);
            boolean hasRows = cursor.moveToFirst();
            if (hasRows) {
                while (!cursor.isAfterLast()) {
                    result.add(new Auction(cursor));
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return result;
    }

    public long addNewAuctionItem(long itemId, long startDate, long duration) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_AUCTION_ITEM_ID, itemId);
        contentValues.put(KEY_AUCTION_START_DATE, startDate);
        contentValues.put(KEY_AUCTION_DURATION, duration);

        long id = database.insert(TABLE_AUCTION, null, contentValues);

        return id;
    }

    /**
     * End of AUCTION table stuff
     */
}
