package com.crossover.auctilion.utils;

import com.crossover.auctilion.data.Auction;
import com.crossover.auctilion.data.AuctionBid;

/**
 * Created by heghine on 2/12/17.
 */

// an interface for propagating item onClick back to the activity
public interface AuctionAdapterOnClickHandler {
    void onClick(Auction auction);
}