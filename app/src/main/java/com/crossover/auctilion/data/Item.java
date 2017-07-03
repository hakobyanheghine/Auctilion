package com.crossover.auctilion.data;

import android.database.Cursor;

import com.crossover.auctilion.manager.DatabaseManager;

/**
 * Created by heghine on 2/11/17.
 */

public class Item {
    // all the fields of data class are public for easy access, and for reducing function calls
    public long itemId;
    public String name;
    public String description;
    public double price;
    public String priceCurrency;

    public Item() {

    }

    public Item(long itemId, String name, String description, double price, String priceCurrency) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.priceCurrency = priceCurrency;
    }

    public Item(Cursor cursor) { // this constructor is for initializing item data from database
        itemId = cursor.getLong(cursor.getColumnIndex(DatabaseManager.KEY_ITEM_ID));
        name = cursor.getString(cursor.getColumnIndex(DatabaseManager.KEY_ITEM_NAME));
        description = cursor.getString(cursor.getColumnIndex(DatabaseManager.KEY_ITEM_DESCRIPTION));
        price = cursor.getDouble(cursor.getColumnIndex(DatabaseManager.KEY_ITEM_PRICE));
        priceCurrency = cursor.getString(cursor.getColumnIndex(DatabaseManager.KEY_ITEM_PRICE_CURRENCY));
    }
}
