package com.crossover.auctilion.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.crossover.auctilion.R;
import com.crossover.auctilion.data.Auction;
import com.crossover.auctilion.data.AuctionBid;
import com.crossover.auctilion.manager.AuctionManager;
import com.crossover.auctilion.manager.LoginManager;
import com.crossover.auctilion.utils.AuctionBidAdapter;
import com.crossover.auctilion.utils.Utils;

import java.util.ArrayList;

/**
 * Created by heghine on 2/12/17.
 */

public class AuctionItemActivity extends AppCompatActivity {

    private AuctionBidAdapter auctionBidAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_item);

        Auction auction = AuctionManager.getInstance().currentAuction;

        ((TextView) findViewById(R.id.auction_name)).setText(auction.item.name);
        ((TextView) findViewById(R.id.auction_price)).setText(auction.item.price + " " + auction.item.priceCurrency);
        ((TextView) findViewById(R.id.auction_desc)).setText(auction.item.description);

        String remainingTime = Utils.convertTimeInSecondsToString(auction.remainingTime);
        ((TextView) findViewById(R.id.auction_time_left)).setText(remainingTime);


        // RecyclerView for showing currently active auctions
        RecyclerView auctionBidsRecyclerView = (RecyclerView) findViewById(R.id.auction_bids_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        auctionBidsRecyclerView.setLayoutManager(linearLayoutManager);
        auctionBidsRecyclerView.setHasFixedSize(true);

        // TODO this is a test data...actual data should be retrieved from database
        final ArrayList<AuctionBid> auctionBids = new ArrayList<>();
        auctionBids.add(new AuctionBid("Peter", 15, "USD"));
        auctionBids.add(new AuctionBid("Helen", 12.5, "USD"));
        auctionBids.add(new AuctionBid("Heghine", 11, "USD"));

        auctionBidAdapter = new AuctionBidAdapter(auctionBids);

        auctionBidsRecyclerView.setAdapter(auctionBidAdapter);

        final EditText bidEditText = (EditText) findViewById(R.id.auction_bid_edit);
        findViewById(R.id.auction_add_bid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bidPrice = bidEditText.getText().toString();
                if (Utils.validateIfStringIsNumber(bidPrice)) {
                    bidEditText.setText("");
                    double bidPriceNumber = Double.parseDouble(bidPrice);
                    auctionBids.add(0, new AuctionBid(LoginManager.getInstance().getUser().name, bidPriceNumber, "USD"));
                    auctionBidAdapter.notifyDataSetChanged();
                } else {
                    Utils.showToast(AuctionItemActivity.this, AuctionItemActivity.this.getString(R.string.wrong_bid));
                }
            }
        });
    }
}
