package com.crossover.auctilion.data;

import android.database.Cursor;

import com.crossover.auctilion.manager.DatabaseManager;

/**
 * Created by heghine on 2/11/17.
 */

public class Auction {
    // all the fields of data class are public for easy access, and for reducing function calls
    public int auctionId;
    public long itemId;
    public long startDate; // in milliseconds
    public long duration; // in seconds

    public long remainingTime; // in seconds, this one is calculated later

    public Item item;

    public Auction() {

    }

    public Auction(int auctionId, long itemId, long startDate, long duration) {
        this.auctionId = auctionId;
        this.itemId = itemId;
        this.startDate = startDate;
        this.duration = duration;
    }

    public Auction(Cursor cursor) { // this constructor is for initializing auction data from database
        auctionId = cursor.getInt(cursor.getColumnIndex(DatabaseManager.KEY_AUCTION_ID));
        itemId = cursor.getLong(cursor.getColumnIndex(DatabaseManager.KEY_AUCTION_ITEM_ID));
        startDate = cursor.getLong(cursor.getColumnIndex(DatabaseManager.KEY_AUCTION_START_DATE));
        duration = cursor.getLong(cursor.getColumnIndex(DatabaseManager.KEY_AUCTION_DURATION));
    }
}
