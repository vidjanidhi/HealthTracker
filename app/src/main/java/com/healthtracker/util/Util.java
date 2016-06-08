package com.healthtracker.util;

import android.util.Log;

import java.util.Calendar;

/**
 * Created by local nidhi on 17-05-2016.
 */
public class Util {

    public static String getToday() {
        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH);
        int month1 = month + 1;
        String date = today.get(Calendar.DAY_OF_MONTH) + "/" + month1 + "/" + today.get(Calendar.YEAR);
        return date;
    }

    public static long getDaysDifference(String date) {
        Calendar thatDay = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        String[] ary = date.split("/");
        Log.i("date", ary[0]);

        thatDay.set(Calendar.DAY_OF_MONTH, Integer.parseInt(ary[0]));
        thatDay.set(Calendar.MONTH, Integer.parseInt(ary[1])); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR, Integer.parseInt(ary[2]));

        long diff = today.getTimeInMillis() - thatDay.getTimeInMillis(); //result in millis
        long daysDifference = diff / (24 * 60 * 60 * 1000);

        Log.i("difference", daysDifference + "");
        return daysDifference;

    }
}
