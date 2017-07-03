package com.crossover.auctilion.data;

import android.database.Cursor;

import com.crossover.auctilion.manager.DatabaseManager;

/**
 * Created by heghine on 2/11/17.
 */

public class User {
    // all the fields of data class are public for easy access, and for reducing function calls
    public int userId;
    public String name;
    public String email;
    public String password;

    public User() {

    }

    public User(int userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(Cursor cursor) { // this constructor is for initializing user data from database
        userId = cursor.getInt(cursor.getColumnIndex(DatabaseManager.KEY_USER_ID));
        name = cursor.getString(cursor.getColumnIndex(DatabaseManager.KEY_USER_NAME));
        email = cursor.getString(cursor.getColumnIndex(DatabaseManager.KEY_USER_EMAIL));
        password = cursor.getString(cursor.getColumnIndex(DatabaseManager.KEY_USER_PASSWORD));
    }
}
