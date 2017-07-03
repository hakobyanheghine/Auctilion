package com.crossover.auctilion.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.crossover.auctilion.R;
import com.crossover.auctilion.data.Auction;
import com.crossover.auctilion.manager.AuctionManager;
import com.crossover.auctilion.manager.ItemManager;
import com.crossover.auctilion.manager.LoginManager;
import com.crossover.auctilion.utils.AuctionAdapter;
import com.crossover.auctilion.utils.AuctionAdapterOnClickHandler;
import com.crossover.auctilion.utils.Utils;

/**
 * Created by heghine on 2/11/17.
 */

public class AuctionActivity extends AppCompatActivity {

    private AuctionAdapter auctionAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction);

        ((TextView) findViewById(R.id.auction_welcome_text)).setText(String.format(getString(R.string.welcome_user), LoginManager.getInstance().getUser().name));

        findViewById(R.id.auction_add_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddNewItemDialog();
            }
        });

        // RecyclerView for showing currently active auctions
        RecyclerView auctionsRecyclerView = (RecyclerView) findViewById(R.id.auctions_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        auctionsRecyclerView.setLayoutManager(linearLayoutManager);
        auctionsRecyclerView.setHasFixedSize(true);

        auctionAdapter = new AuctionAdapter(AuctionManager.getInstance().currentAuctions, new AuctionAdapterOnClickHandler() {
            @Override
            public void onClick(Auction auction) {
                // open corresponding auction details activity
                AuctionManager.getInstance().currentAuction = auction;
                startAuctionActivity();
            }
        });

        auctionsRecyclerView.setAdapter(auctionAdapter);
    }

    private void startAuctionActivity() {
        AuctionActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Intent auctionItemActivity = new Intent(AuctionActivity.this, AuctionItemActivity.class);
                startActivity(auctionItemActivity);
            }
        });
    }

    private void showAddNewItemDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // getting layout inflater
        LayoutInflater inflater = getLayoutInflater();

        View addItemView = inflater.inflate(R.layout.dialog_add_item, null);
        final EditText nameEditText = (EditText) addItemView.findViewById(R.id.add_item_name);
        final EditText descEditText = (EditText) addItemView.findViewById(R.id.add_item_desc);
        final EditText priceEditText = (EditText) addItemView.findViewById(R.id.add_item_price);
        final EditText durationEditText = (EditText) addItemView.findViewById(R.id.add_item_duration);

        // inflating the layout for the dialog
        builder.setView(addItemView)
                // adding action buttons
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // get user data
                        String name = nameEditText.getText().toString();
                        String desc = descEditText.getText().toString();
                        String price = priceEditText.getText().toString();
                        String duration = durationEditText.getText().toString();

                        if (name.isEmpty() || desc.isEmpty() || price.isEmpty()) {
                            // show toast for incomplete data
                            Utils.showToast(AuctionActivity.this, AuctionActivity.this.getString(R.string.incomplete_data));
                        } else if (!Utils.validateIfStringIsNumber(price) || !Utils.validateIfStringIsNumber(duration)) {
                            // show wrong price message
                            Utils.showToast(AuctionActivity.this, AuctionActivity.this.getString(R.string.wrong_price));
                        } else {
                            // continue to adding the item
                            addNewAuctionItem(name, desc, Double.parseDouble(price), Long.parseLong(duration));
                        }

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

    private void addNewAuctionItem(String name, String desc, double price, long duration) {
        long itemId = ItemManager.getInstance().addNewItem(name, desc, price);
        AuctionManager.getInstance().addNewAuction(itemId, duration);
        auctionAdapter.notifyDataSetChanged();
    }
}
