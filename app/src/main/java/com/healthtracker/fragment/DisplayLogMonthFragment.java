package com.healthtracker.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.healthtracker.EditLogActivity;
import com.healthtracker.R;
import com.healthtracker.adapter.DisplayLogListAdapter;
import com.healthtracker.helper.DataBaseHelper;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.model.LogEntry;
import com.healthtracker.util.AppConstant;
import com.healthtracker.util.Util;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class DisplayLogMonthFragment extends Fragment {

    // private ListView lstLogs;
    public static MaterialCalendarView calendarView;
    DataBaseHelper dataBaseHelper;
    PreferenceHelper preferenceHelper;
    ArrayList<LogEntry> logEntryArrayList = new ArrayList<LogEntry>();
    DisplayLogListAdapter displayLogListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataBaseHelper = new DataBaseHelper(getActivity());
        preferenceHelper = new PreferenceHelper(getActivity());
        return inflater.inflate(R.layout.fragment_display_log_month, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarView = (MaterialCalendarView) view.findViewById(R.id.cal_month_view);
        //lstLogs = (ListView) view.findViewById(R.id.lst_logs_for_month_view);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        logEntryArrayList = dataBaseHelper.getAllLogEntry(preferenceHelper.getInteger(AppConstant.USER_ID));
        final ArrayList<LogEntry> logEntryArrayListForDays = new ArrayList<LogEntry>();
        for (int i = 0; i < logEntryArrayList.size(); i++) {
            String date = logEntryArrayList.get(i).getDate();
            if (!TextUtils.isEmpty(date)) {
                boolean isSame = Util.IsDateSame(date, dayOfMonth, month, year, preferenceHelper.getInteger(AppConstant.DATE_SELECTED));
                Log.i("issame", isSame + "");
                if (isSame) {
                    logEntryArrayListForDays.add(logEntryArrayList.get(i));
                }
            }
        }
        displayLogListAdapter = new DisplayLogListAdapter(getActivity(), logEntryArrayListForDays);
//        lstLogs.setAdapter(displayLogListAdapter);
//        lstLogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                int LogEntryId = logEntryArrayListForDays.get(position).getId();
//                Intent intent = new Intent(getActivity(), EditLogActivity.class);
//                intent.putExtra(AppConstant.LOG_ENTRY_ID, LogEntryId);
//                startActivity(intent);
//            }
//        });
        calendarView.setDateSelected(calendar, true);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                Log.i("selected date", date.toString());
                int dayOfMonth = date.getDay();
                int month = date.getMonth();
                int year = date.getYear();
                final ArrayList<LogEntry> logEntryArrayListForDays = new ArrayList<LogEntry>();
                for (int i = 0; i < logEntryArrayList.size(); i++) {
                    String date1 = logEntryArrayList.get(i).getDate();
                    if (!TextUtils.isEmpty(date1)) {
                        boolean isSame = Util.IsDateSame(date1, dayOfMonth, month, year, preferenceHelper.getInteger(AppConstant.DATE_SELECTED));
                        Log.i("issame", isSame + "");
                        if (isSame) {
                            logEntryArrayListForDays.add(logEntryArrayList.get(i));
                        }
                    }
                }
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.list);
                dialog.setTitle("title");
//                dialog.setTitle(dayOfMonth + "/" + month + "/" + year);
                ListView lstLogs = (ListView) dialog.findViewById(R.id.lst_logs_for_month_view);


                /*********************************************************/
                displayLogListAdapter = new DisplayLogListAdapter(getActivity(), logEntryArrayListForDays);
                lstLogs.setAdapter(displayLogListAdapter);
                lstLogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int LogEntryId = logEntryArrayListForDays.get(position).getId();
                        Intent intent = new Intent(getActivity(), EditLogActivity.class);
                        intent.putExtra(AppConstant.LOG_ENTRY_ID, LogEntryId);
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        });


//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//
//            @Override
//            public void onSelectedDayChange(CalendarView view, int year, int month,
//                                            int dayOfMonth) {
//                final ArrayList<LogEntry> logEntryArrayListForDays = new ArrayList<LogEntry>();
//                for (int i = 0; i < logEntryArrayList.size(); i++) {
//                    String date = logEntryArrayList.get(i).getDate();
//                    if (!TextUtils.isEmpty(date)) {
//                        boolean isSame = Util.IsDateSame(date, dayOfMonth, month, year, preferenceHelper.getInteger(AppConstant.DATE_SELECTED));
//                        Log.i("issame", isSame + "");
//                        if (isSame) {
//                            logEntryArrayListForDays.add(logEntryArrayList.get(i));
//                        }
//                    }
//                }
//                displayLogListAdapter = new DisplayLogListAdapter(getActivity(), logEntryArrayListForDays);
//                lstLogs.setAdapter(displayLogListAdapter);
//                lstLogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        int LogEntryId = logEntryArrayListForDays.get(position).getId();
//                        Intent intent = new Intent(getActivity(), EditLogActivity.class);
//                        intent.putExtra(AppConstant.LOG_ENTRY_ID, LogEntryId);
//                        startActivity(intent);
//                    }
//                });
//            }
//        });

    }


    ArrayList<LogEntry> getSelectedDayLog(int days, ArrayList<LogEntry> logEntryArrayList) {
        ArrayList<LogEntry> logEntryArrayListForDays = new ArrayList<LogEntry>();
        for (int i = 0; i < logEntryArrayList.size(); i++) {
            String date = logEntryArrayList.get(i).getDate();
            if (!TextUtils.isEmpty(date)) {
                long difference = Util.getDaysDifference(date);
                if (difference < days) {
                    logEntryArrayListForDays.add(logEntryArrayList.get(i));
                }
            }
        }

        return logEntryArrayListForDays;
    }
}
