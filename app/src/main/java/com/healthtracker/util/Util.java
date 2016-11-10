package com.healthtracker.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYSeriesRenderer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by local nidhi on 17-05-2016.
 */
public class Util {

    public static String getToday(int selectedDate) {
        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH);
        int month1 = month + 1;
        String date;
        if (selectedDate == AppConstant.DATE_SELECTED_UNIT_DMY)

            date = today.get(Calendar.DAY_OF_MONTH) + "/" + month1 + "/" + today.get(Calendar.YEAR);
        else
            date = month1 + "/" + today.get(Calendar.DAY_OF_MONTH) + "/" + today.get(Calendar.YEAR);


        return date;
    }

    public static String getFormatedDate(String date, int selectedDate) {
        String[] ary = date.split("/");
        String date1;
        if (selectedDate == AppConstant.DATE_SELECTED_UNIT_DMY)

            date1 = ary[0] + "/" + ary[1] + "/" + ary[2];
        else
            date1 = ary[1] + "/" + ary[0] + "/" + ary[2];

        return date1;

    }

    public static Date getDateFromString(String date) {
        String[] ary = date.split("/");
        GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(ary[2]), Integer.parseInt(ary[1]) - 1, Integer.parseInt(ary[0]));
        return gc.getTime();
    }

    public static long getDaysDifference(String date) {
        Calendar thatDay = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        String[] ary = date.split("/");
        Log.i("date", ary[0]);

        thatDay.set(Calendar.DAY_OF_MONTH, Integer.parseInt(ary[0]));
        thatDay.set(Calendar.MONTH, Integer.parseInt(ary[1]) - 1); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR, Integer.parseInt(ary[2]));

        long diff = today.getTimeInMillis() - thatDay.getTimeInMillis(); //result in millis
        long daysDifference = diff / (24 * 60 * 60 * 1000);

        Log.i("difference", daysDifference + "");
        return daysDifference;

    }

    public static boolean IsDateSame(String date, int selectedDay, int SelectedMonth, int SelectedYr, int selectedDate) {
        String[] ary = date.split("/");
        Log.i("day dates", date + " " + selectedDay + " " + SelectedMonth + " " + SelectedYr);
        if (selectedDate == AppConstant.DATE_SELECTED_UNIT_DMY) {
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
        } else {
            if (selectedDay == Integer.parseInt(ary[1])) {
                if ((SelectedMonth + 1 == Integer.parseInt(ary[0]))) {
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

    public static void copy(File source, File destination) throws IOException {

        FileChannel in = new FileInputStream(source).getChannel();
        FileChannel out = new FileOutputStream(destination).getChannel();

        try {
            in.transferTo(0, in.size(), out);
        } catch (Exception e1) {
            // post to log
        } finally {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
        }
    }

    public static String getPath1(Uri uri, Context context) {

        String path = null;
        String[] projection = {MediaStore.Files.FileColumns.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        if (cursor == null) {
            path = uri.getPath();
        } else {
            cursor.moveToFirst();
            int column_index = cursor.getColumnIndexOrThrow(projection[0]);
            path = cursor.getString(column_index);
            cursor.close();
        }

        return ((path == null || path.isEmpty()) ? (uri.getPath()) : path);
    }


    public static String getRealPathFromUri(Uri uri, Context context) {
        String result = "";
        String documentID;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            String[] pathParts = uri.getPath().split("/");
            documentID = pathParts[pathParts.length - 1];
        } else {
            String pathSegments[] = uri.getLastPathSegment().split(":");
            documentID = pathSegments[pathSegments.length - 1];
        }
        String mediaPath = MediaStore.Images.Media.DATA;
        Cursor imageCursor = context.getContentResolver().query(uri, new String[]{mediaPath}, MediaStore.Images.Media._ID + "=" + documentID, null, null);
        if (imageCursor.moveToFirst()) {
            result = imageCursor.getString(imageCursor.getColumnIndex(mediaPath));
        }
        return result;
    }

//    public static String getPath(Context context, Uri uri) throws URISyntaxException {
//        if ("content".equalsIgnoreCase(uri.getScheme())) {
//            String[] projection = {"_data"};
//            Cursor cursor = null;
//
//            try {
//                cursor = context.getContentResolver().query(uri, projection, null, null, null);
//                int column_index = cursor.getColumnIndexOrThrow("_data");
//                if (cursor.moveToFirst()) {
//                    return cursor.getString(column_index);
//                }
//            } catch (Exception e) {
//                // Eat it
//            }
//        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            return uri.getPath();
//        }
//
//        return null;
//    }

    public static XYSeriesRenderer[] getSeriesRenderer(int i) {
        XYSeriesRenderer[] visitsRenderer = new XYSeriesRenderer[i];
        for (int j = 0; j < i; j++) {
            visitsRenderer[j] = new XYSeriesRenderer();
            switch (j) {
                case 0:
                    visitsRenderer[j].setColor(Color.GREEN);
                    break;

                case 1:
                    visitsRenderer[j].setColor(Color.RED);
                    break;

                case 2:
                    visitsRenderer[j].setColor(Color.BLUE);
                    break;
            }
            visitsRenderer[j].setPointStyle(PointStyle.POINT);
            visitsRenderer[j].setFillPoints(true);
            visitsRenderer[j].setLineWidth(3);
            visitsRenderer[j].setChartValuesTextSize(10);
            visitsRenderer[j].setDisplayBoundingPoints(true);
            visitsRenderer[j].setPointStrokeWidth(5);
        }

        return visitsRenderer;
    }
}
