package com.healthtracker.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
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

    public static boolean IsDateSame(String date, int selectedDay, int SelectedMonth, int SelectedYr) {
        String[] ary = date.split("/");
        Log.i("day dates", date + " " + selectedDay + " " + SelectedMonth + " " + SelectedYr);
        if (selectedDay == Integer.parseInt(ary[0])) {
            if ((SelectedMonth + 1 == Integer.parseInt(ary[1]))) {
                if (SelectedYr == Integer.parseInt(ary[2])) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else return false;
    }

    public static ArrayList<String> getSelectedTime(int fragment_id) {

        ArrayList<String> timeList = new ArrayList<String>();

        switch (fragment_id) {
            case AppConstant.MED_FRAGMENT:
                timeList.add(AppConstant.OUT_OF_BED);
                timeList.add(AppConstant.BEFORE_BREAKFAST);
                timeList.add(AppConstant.AFTER_BREAKFAST);
                timeList.add(AppConstant.BEFORE_LUNCH);
                timeList.add(AppConstant.AFTER_LUNCH);
                timeList.add(AppConstant.BEFORE_DINNER);
                timeList.add(AppConstant.AFTTER_DINNER);
                timeList.add(AppConstant.BEFORE_BED);
                timeList.add(AppConstant.DURING_NIGHT);
                timeList.add(AppConstant.AFTER_BED);
                timeList.add(AppConstant.BEFORE_SNACK);
                timeList.add(AppConstant.AFTER_SNACK);
                timeList.add(AppConstant.BEFORE_ACTIVIYT);
                timeList.add(AppConstant.DURING_ACTIVIYT);
                timeList.add(AppConstant.AFTER_ACTIVITY);
                timeList.add(AppConstant.OTHER);

                break;
            case AppConstant.FOOD_FRAGMENT:
                timeList.add(AppConstant.BREAKFAST);
                timeList.add(AppConstant.LUNCH);
                timeList.add(AppConstant.DINNER);
                timeList.add(AppConstant.SNACK);
                timeList.add(AppConstant.OTHER);

                break;
            case AppConstant.ACTIVITY_FRAGMENT:
                timeList.add(AppConstant.BEFORE_BREAKFAST);
                timeList.add(AppConstant.AFTER_BREAKFAST);
                timeList.add(AppConstant.BEFORE_LUNCH);
                timeList.add(AppConstant.AFTER_LUNCH);
                timeList.add(AppConstant.BEFORE_DINNER);
                timeList.add(AppConstant.AFTTER_DINNER);

                break;

        }
        return timeList;
    }

    public static void copyFile(FileInputStream fromFile, FileOutputStream toFile) throws IOException {
        FileChannel fromChannel = null;
        FileChannel toChannel = null;
        try {
            fromChannel = fromFile.getChannel();
            toChannel = toFile.getChannel();
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
        } finally {
            try {
                if (fromChannel != null) {
                    fromChannel.close();
                }
            } finally {
                if (toChannel != null) {
                    toChannel.close();
                }
            }
        }
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
}
