package com.crossover.auctilion.data;

/**
 * Created by heghine on 2/12/17.
 */

public class AuctionBid {
    // all the fields of data class are public for easy access, and for reducing function calls
    public String name;
    public double price;
    public String priceCurrency;

    public AuctionBid() {}

    public AuctionBid(String name, double price, String priceCurrency) {
        this.name = name;
        this.price = price;
        this.priceCurrency = priceCurrency;
    }

}
