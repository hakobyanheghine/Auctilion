package com.crossover.auctilion.utils;

import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;

/**
 * Created by heghine on 2/11/17.
 */

public class Utils {

    public static void showToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static String convertTimeInSecondsToString(long timeInSeconds) {
        long minutes = timeInSeconds / 60; // calculate minutes
        long seconds = timeInSeconds - minutes * 60; // subtract off whole minutes
        long hours = minutes / 60; // calculate hours
        minutes = minutes - hours * 60; // subtract off whole hours
        long days = hours / 24; // calculate number of days
        hours = hours - days * 24; // subtract off whole days

        String result = "";
        if (days != 0) {
            if (hours != 0)
                result = days + "d " + hours + "h ";
            else
                result = days + "d";
        } else if (hours != 0) {
            if (minutes != 0)
                result = hours + "h " + minutes + "m ";
            else
                result = hours + "h ";
        } else if (minutes != 0) {
            if (seconds != 0)
                result = minutes + "m " + seconds + "s";
            else
                result = minutes + "m ";
        } else {
            result = seconds + "s";
        }

        return result;
    }

    public static boolean validateIfStringIsNumber(String number) {
        boolean result = false;

        try {
            Double.parseDouble(number);
            result = true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return result;
    }
}
