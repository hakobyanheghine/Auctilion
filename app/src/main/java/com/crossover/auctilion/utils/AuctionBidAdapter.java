package com.crossover.auctilion.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crossover.auctilion.R;
import com.crossover.auctilion.data.AuctionBid;

import java.util.ArrayList;

/**
 * Created by heghine on 2/12/17.
 */

public class AuctionBidAdapter extends RecyclerView.Adapter<AuctionBidAdapter.AuctionBidAdapterViewHolder> {

    private ArrayList<AuctionBid> auctionBids;

    public AuctionBidAdapter(ArrayList<AuctionBid> auctionBids) {
        this.auctionBids = auctionBids;
    }

    @Override
    public AuctionBidAdapter.AuctionBidAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.item_auction_bid;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new AuctionBidAdapter.AuctionBidAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AuctionBidAdapter.AuctionBidAdapterViewHolder holder, int position) {
        AuctionBid auctionBid = auctionBids.get(position);
        holder.bidderName.setText(auctionBid.name);
        holder.bidPrice.setText(auctionBid.price + " " + auctionBid.priceCurrency);
    }

    @Override
    public int getItemCount() {
        return auctionBids.size();
    }


    // ViewHolder class for keeping references to necessary views
    public class AuctionBidAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView bidderName;
        public final TextView bidPrice;

        public AuctionBidAdapterViewHolder(View itemView) {
            super(itemView);

            bidderName = (TextView) itemView.findViewById(R.id.item_auction_bid_name);
            bidPrice = (TextView) itemView.findViewById(R.id.item_auction_bid_price);
        }
    }
}
