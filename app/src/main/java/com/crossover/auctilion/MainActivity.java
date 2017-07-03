package com.crossover.auctilion;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.crossover.auctilion.activity.AuctionActivity;
import com.crossover.auctilion.manager.LoginManager;
import com.crossover.auctilion.manager.PreferenceManager;
import com.crossover.auctilion.service.DatabaseInitService;
import com.crossover.auctilion.utils.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing SharedPreferences for future use
        PreferenceManager.getInstance().init(this);

        // starting DatabaseInitService, so it will create necessary databases for us in background service
        Intent databaseInitIntent = new Intent(this, DatabaseInitService.class);
        this.startService(databaseInitIntent);

        findViewById(R.id.main_sign_in_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignInDialog();
            }
        });

        findViewById(R.id.main_sign_up_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignUpDialog();
            }
        });
    }


    /**
     * Creates and shows sign in dialog with custom view
     */
    private void showSignInDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // getting layout inflater
        LayoutInflater inflater = getLayoutInflater();

        // inflating layout for the dialog
        View signInView = inflater.inflate(R.layout.dialog_sign_in, null);
        final EditText emailEditText = (EditText) signInView.findViewById(R.id.sign_in_email);
        final EditText passEditText = (EditText) signInView.findViewById(R.id.sign_in_pass);


        builder.setView(signInView)
                // adding action buttons
                .setPositiveButton(R.string.sign_in, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String email = emailEditText.getText().toString();
                        String pass = passEditText.getText().toString();

                        if (email.isEmpty() || pass.isEmpty()) {
                            // show toast for incomplete data
                            Utils.showToast(MainActivity.this, MainActivity.this.getString(R.string.incomplete_data));
                        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            // show toast for wrong email
                            Utils.showToast(MainActivity.this, MainActivity.this.getString(R.string.incorrect_email));
                        } else {
                            // continue to sign in user
                            boolean isSignedIn = LoginManager.getInstance().signInUser(email, pass);

                            if (isSignedIn) {
                                // continue to app
                                startAuctionActivity();
                            } else {
                                Utils.showToast(MainActivity.this, MainActivity.this.getString(R.string.incorrect_data));
                            }
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

    /**
     * Creates and shows sign up dialog with custom view
     */
    private void showSignUpDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // getting layout inflater
        LayoutInflater inflater = getLayoutInflater();

        View signUpView = inflater.inflate(R.layout.dialog_sign_up, null);
        final EditText nameEditText = (EditText) signUpView.findViewById(R.id.sign_up_name);
        final EditText emailEditText = (EditText) signUpView.findViewById(R.id.sign_up_email);
        final EditText passEditText = (EditText) signUpView.findViewById(R.id.sign_up_pass);

        // inflating the layout for the dialog
        builder.setView(signUpView)
                // adding action buttons
                .setPositiveButton(R.string.sign_up, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // get user data
                        String name = nameEditText.getText().toString();
                        String email = emailEditText.getText().toString();
                        String pass = passEditText.getText().toString();

                        if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                            // show toast for incomplete data
                            Utils.showToast(MainActivity.this, MainActivity.this.getString(R.string.incomplete_data));
                        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            // show toast for wrong email
                            Utils.showToast(MainActivity.this, MainActivity.this.getString(R.string.incorrect_email));
                        } else {
                            // continue to sign up user
                            boolean isSignedUp = LoginManager.getInstance().signUpUser(name, email, pass);

                            if (isSignedUp) {
                                // continue to app
                                startAuctionActivity();
                            } else {
                                Utils.showToast(MainActivity.this, MainActivity.this.getString(R.string.already_signed_up));
                            }
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

    private void startAuctionActivity() {
        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Intent auctionActivity = new Intent(MainActivity.this, AuctionActivity.class);
                startActivity(auctionActivity);
            }
        });
    }
}
