package com.crossover.auctilion.manager;

import com.crossover.auctilion.data.User;

/**
 * Created by heghine on 2/11/17.
 */

public class LoginManager {
    private static LoginManager instance;

    private LoginManager() {}

    public static LoginManager getInstance() {
        if (instance == null) {
            instance = new LoginManager();
        }

        return instance;
    }

    private User user;

    public User getUser() {
        return user;
    }

    public boolean signUpUser(String name, String email, String pass) {
        boolean isSignUpSuccessful = true;
        User userFromDb = DatabaseManager.getInstance().getUserWithEmailAndPass(email, pass);
        if (userFromDb != null) { // check if email already exists, else save user to database
            isSignUpSuccessful = false;
        } else {
            int id = (int) DatabaseManager.getInstance().addNewUser(name, email, pass);
            user = new User(id, name, email, pass);
        }

        return isSignUpSuccessful;
    }

    public boolean signInUser(String email, String pass) {
        boolean isSignInSuccessful = true;

        //find user from database and get name and email
        User userFromDb = DatabaseManager.getInstance().getUserWithEmailAndPass(email, pass);
        if (userFromDb != null) {
            user = userFromDb;
        } else {
            isSignInSuccessful = false;
        }

        return isSignInSuccessful;
    }
}
