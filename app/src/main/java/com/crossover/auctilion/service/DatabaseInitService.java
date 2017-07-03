package com.crossover.auctilion.service;

import android.app.IntentService;
import android.content.Intent;

import com.crossover.auctilion.manager.AuctionManager;
import com.crossover.auctilion.manager.DatabaseManager;
import com.crossover.auctilion.manager.ItemManager;

/**
 * Created by heghine on 2/11/17.
 */

public class DatabaseInitService extends IntentService {

    public DatabaseInitService() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // initialize local database
        DatabaseManager.getInstance().init(this);

        /**
         * The next 6 lines (which are commented) are for adding mock auction data to database
         *
         * Please, uncomment it before running the app for the first time
         */
//        long itemId1 = DatabaseManager.getInstance().addNewItem("LG Nexus 5", "Android smartphone device", 25, "USD");
//        long itemId2 = DatabaseManager.getInstance().addNewItem("Antique Jewelry Box", "19th century Victorian era box", 10, "USD");
//        long itemId3 = DatabaseManager.getInstance().addNewItem("Pen", "Blue writing pen", 0.5, "USD");
//
//        DatabaseManager.getInstance().addNewAuctionItem(itemId1, System.currentTimeMillis(), 20 * 60);
//        DatabaseManager.getInstance().addNewAuctionItem(itemId2, System.currentTimeMillis(), 40 * 60);
//        DatabaseManager.getInstance().addNewAuctionItem(itemId3, System.currentTimeMillis(), 60 * 60);

        // load all items from database into allItems
        ItemManager.getInstance().allItems = DatabaseManager.getInstance().getAllItems();

        // also initialize all the current currentAuctions
        AuctionManager.getInstance().initCurrentAuctions();
    }
}
