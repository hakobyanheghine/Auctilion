package com.crossover.auctilion.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crossover.auctilion.R;
import com.crossover.auctilion.data.Auction;

import java.util.ArrayList;

/**
 * Created by heghine on 2/11/17.
 */

public class AuctionAdapter extends RecyclerView.Adapter<AuctionAdapter.AuctionAdapterViewHolder> {

    private ArrayList<Auction> auctions;
    private AuctionAdapterOnClickHandler onClickHandler;

    public AuctionAdapter(ArrayList<Auction> auctions, AuctionAdapterOnClickHandler onClickHandler) {
        this.auctions = auctions;
        this.onClickHandler = onClickHandler;
    }

    @Override
    public AuctionAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.item_auction;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new AuctionAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AuctionAdapterViewHolder holder, int position) {
        Auction auction = auctions.get(position);
        holder.auctionName.setText(auction.item.name);
        holder.auctionDesc.setText(auction.item.description);

        String remainingTime = Utils.convertTimeInSecondsToString(auction.remainingTime);
        holder.timeLeft.setText("Time left \n" + remainingTime); // this should be changed to take the string from strings.xml
    }

    @Override
    public int getItemCount() {
        return auctions.size();
    }

    // ViewHolder class for keeping references to necessary views
    public class AuctionAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView auctionName;
        public final TextView auctionDesc;
        public final TextView timeLeft;

        public AuctionAdapterViewHolder(View itemView) {
            super(itemView);

            auctionName = (TextView) itemView.findViewById(R.id.item_auction_name);
            auctionDesc = (TextView) itemView.findViewById(R.id.item_auction_desc);
            timeLeft = (TextView) itemView.findViewById(R.id.item_auction_time_left);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Auction auction = auctions.get(adapterPosition);
            onClickHandler.onClick(auction);
        }
    }
}
