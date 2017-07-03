package com.crossover.auctilion.manager;

import com.crossover.auctilion.data.Auction;
import com.crossover.auctilion.data.Item;

import java.util.ArrayList;

/**
 * Created by heghine on 2/11/17.
 */

public class AuctionManager {
    private static AuctionManager instance;

    private AuctionManager() {}

    public static AuctionManager getInstance() {
        if (instance == null) {
            instance = new AuctionManager();
        }

        return instance;
    }

    // an array to keep currently ongoing currentAuctions
    public ArrayList<Auction> currentAuctions = new ArrayList<>();

    // this is for showing currently selected Auction in AuctionItemActivity
    public Auction currentAuction;

    public void initCurrentAuctions() {
        ArrayList<Auction> allAuctions = DatabaseManager.getInstance().getAllAuctions();

        long currentTime = System.currentTimeMillis();
        for (int i = 0; i < allAuctions.size(); i++) {
            // calculating the remaining time for each auction
            long endTime = allAuctions.get(i).startDate + allAuctions.get(i).duration * 1000;
            if (endTime > currentTime) { // then this auction is still ongoing
                allAuctions.get(i).remainingTime = (endTime - currentTime) / 1000;
                allAuctions.get(i).item = ItemManager.getInstance().getItemById(allAuctions.get(i).itemId);
                currentAuctions.add(allAuctions.get(i));
            }
        }
    }

    public void addNewAuction(long itemId, long duration) {
        long startDate = System.currentTimeMillis();
        long auctionId = DatabaseManager.getInstance().addNewAuctionItem(itemId, startDate, duration);
        Auction auction = new Auction((int) auctionId, itemId, startDate, duration);
        auction.remainingTime = duration;
        auction.item = ItemManager.getInstance().getItemById(itemId);
        currentAuctions.add(0, auction);
    }

}
