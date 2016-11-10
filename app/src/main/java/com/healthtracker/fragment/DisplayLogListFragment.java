package com.healthtracker.fragment;

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

import java.util.ArrayList;

public class DisplayLogListFragment extends Fragment {

    private ListView lstLogs;
    DataBaseHelper dataBaseHelper;
    PreferenceHelper preferenceHelper;
    ArrayList<LogEntry> logEntryArrayList = new ArrayList<LogEntry>();
    DisplayLogListAdapter displayLogListAdapter;
    int days = 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataBaseHelper = new DataBaseHelper(getActivity());
        preferenceHelper = new PreferenceHelper(getActivity());
        return inflater.inflate(R.layout.fragment_display_log_list, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int ItemsPosition = preferenceHelper.getInteger(AppConstant.DAY_SELECTED);
        switch (ItemsPosition) {
            case 0:
                days = 100;
                break;
            case 1:
                days = 7;
                break;
            case 2:
                days = 14;
                break;
            case 3:
                days = 30;
                break;
            case 4:
                days = 90;
                break;
        }
        lstLogs = (ListView) view.findViewById(R.id.lst_logs);
        logEntryArrayList = dataBaseHelper.getAllLogEntry(preferenceHelper.getInteger(AppConstant.USER_ID));
        final ArrayList<LogEntry> logEntryArrayListForType = getSelectedTypeLog();
        if (logEntryArrayListForType.size() > 0) {
            Log.i("displayloglist", "not null");
            Log.i("listfor type size", logEntryArrayListForType.size() + "");
            if (days != 100) {
                Log.i("display log list", "for " + days + " days");
                final ArrayList<LogEntry> logEntryArrayListForDays = getSelectedDayLog(days, logEntryArrayListForType);
                if (logEntryArrayListForDays.size() > 0) {
                    displayLogListAdapter = new DisplayLogListAdapter(getActivity(), logEntryArrayListForDays);
                    Log.i("log list size", logEntryArrayListForDays.size() + "");
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
                }
            } else {
                Log.i("display log list", "for " + days + " days");
                displayLogListAdapter = new DisplayLogListAdapter(getActivity(), logEntryArrayListForType);
                Log.i("log list size", logEntryArrayListForType.size() + "");
                lstLogs.setAdapter(displayLogListAdapter);
                lstLogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int LogEntryId = logEntryArrayListForType.get(position).getId();
                        Intent intent = new Intent(getActivity(), EditLogActivity.class);
                        intent.putExtra(AppConstant.LOG_ENTRY_ID, LogEntryId);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    ArrayList<LogEntry> getSelectedTypeLog() {
        ArrayList<LogEntry> logEntryArrayListForType = new ArrayList<LogEntry>();
        if (preferenceHelper.getBoolean(AppConstant.MED_SELECTED)) {
            for (int i = 0; i < logEntryArrayList.size(); i++) {
                int rowid = logEntryArrayList.get(i).getRowId();
                if (dataBaseHelper.getLogFromRowId(rowid).getLogId() == AppConstant.MED_FRAGMENT) {
                    logEntryArrayListForType.add(logEntryArrayList.get(i));
                }
            }
        }
        if (preferenceHelper.getBoolean(AppConstant.FOOD_SELECTED)) {
            for (int i = 0; i < logEntryArrayList.size(); i++) {
                int rowid = logEntryArrayList.get(i).getRowId();
                if (dataBaseHelper.getLogFromRowId(rowid).getLogId() == AppConstant.FOOD_FRAGMENT) {
                    logEntryArrayListForType.add(logEntryArrayList.get(i));
                }
            }
        }
        if (preferenceHelper.getBoolean(AppConstant.ACTIVITY_SELECTED)) {
            for (int i = 0; i < logEntryArrayList.size(); i++) {
                int rowid = logEntryArrayList.get(i).getRowId();
                if (dataBaseHelper.getLogFromRowId(rowid).getLogId() == AppConstant.ACTIVITY_FRAGMENT) {
                    logEntryArrayListForType.add(logEntryArrayList.get(i));
                }
            }
        }
        if (preferenceHelper.getBoolean(AppConstant.MIS_SELECTED)) {
            for (int i = 0; i < logEntryArrayList.size(); i++) {
                int rowid = logEntryArrayList.get(i).getRowId();
                if (dataBaseHelper.getLogFromRowId(rowid).getLogId() == AppConstant.MIS_FRAGMENT) {
                    logEntryArrayListForType.add(logEntryArrayList.get(i));
                }
            }
        }
        return logEntryArrayListForType;
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
