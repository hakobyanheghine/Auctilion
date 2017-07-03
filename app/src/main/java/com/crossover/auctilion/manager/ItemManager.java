package com.crossover.auctilion.manager;

import com.crossover.auctilion.data.Item;

import java.util.ArrayList;

/**
 * Created by heghine on 2/13/17.
 */

public class ItemManager {
    private static ItemManager instance;

    private ItemManager() {}

    public static ItemManager getInstance() {
        if (instance == null) {
            instance = new ItemManager();
        }

        return instance;
    }

    public static final String DEFAULT_CURRENCY = "USD";

    // an array to keep all items for currentAuctions
    public ArrayList<Item> allItems;

    public Item getItemById(long itemId) {
        for (int i = 0; i < allItems.size(); i++) {
            if (allItems.get(i).itemId == itemId) {
                return allItems.get(i);
            }
        }

        return null;
    }

    public long addNewItem(String name, String description, double price) {
        // add to database and get item id
        long itemId = DatabaseManager.getInstance().addNewItem(name, description, price, DEFAULT_CURRENCY);

        Item item = new Item(itemId, name, description, price, DEFAULT_CURRENCY);
        allItems.add(item);

        return itemId;
    }
}
