package com.healthtracker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.healthtracker.component.MyFontButton;
import com.healthtracker.fragment.DisplayLogListFragment;
import com.healthtracker.fragment.DisplayLogMonthFragment;
import com.healthtracker.fragment.WeightDairyFragment;
import com.healthtracker.helper.DataBaseHelper;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.model.LogEntry;
import com.healthtracker.util.AppConstant;

import java.util.ArrayList;

public class LogDisplayActivity extends AppCompatActivity implements View.OnClickListener {
    MyFontButton btnToday;
    MyFontButton btnList;
    MyFontButton btnMonth;
    MyFontButton btnType;
    MyFontButton btnDays;
    MyFontButton btnAddLog;
    LinearLayout llFragmentForLog;
    DataBaseHelper dataBaseHelper;
    PreferenceHelper preferenceHelper;
    Boolean BTN_LIST_SELECTED = true;
    ArrayList<LogEntry> logEntryArrayList;
    static final CharSequence[] items = {"All", "7 Day", "14 Day", "30 Day", "90 Day"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_display);
        findViews();
        updateUI();
        dataBaseHelper = new DataBaseHelper(this);
        preferenceHelper = new PreferenceHelper(this);
        if (!preferenceHelper.is_exist(AppConstant.DAY_SELECTED)) {
            preferenceHelper.setInteger(AppConstant.DAY_SELECTED, 0);
            btnDays.setText(items[0]);
        } else {
            btnDays.setText(items[preferenceHelper.getInteger(AppConstant.DAY_SELECTED)]);
        }
        logEntryArrayList = dataBaseHelper.getAllLogEntry(preferenceHelper.getInteger(AppConstant.USER_ID));

    }

    void findViews() {
        btnToday = (MyFontButton) findViewById(R.id.btn_today);
        btnList = (MyFontButton) findViewById(R.id.btn_list);
        btnMonth = (MyFontButton) findViewById(R.id.btn_month);
        btnType = (MyFontButton) findViewById(R.id.btn_type);
        btnDays = (MyFontButton) findViewById(R.id.btn_days);
        btnAddLog = (MyFontButton) findViewById(R.id.btn_add_log);
        llFragmentForLog = (LinearLayout) findViewById(R.id.ll_fragment_for_log);
        btnToday.setOnClickListener(this);
        btnList.setOnClickListener(this);
        btnMonth.setOnClickListener(this);
        btnType.setOnClickListener(this);
        btnDays.setOnClickListener(this);
        btnAddLog.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_type:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.log_type_layout);
                dialog.setCancelable(false);
                WeightDairyFragment.setDialogDimention(dialog);
                final CheckBox chkMed, chkFood, chkActivity, chkMis;
                MyFontButton btnLogDone;
                chkMed = (CheckBox) dialog.findViewById(R.id.chk_meds);
                chkFood = (CheckBox) dialog.findViewById(R.id.chk_food);
                chkActivity = (CheckBox) dialog.findViewById(R.id.chk_activity);
                chkMis = (CheckBox) dialog.findViewById(R.id.chk_mis);

                if (preferenceHelper.is_exist(AppConstant.MED_SELECTED)) {
                    if (preferenceHelper.getBoolean(AppConstant.MED_SELECTED))
                        chkMed.setChecked(true);
                    else
                        chkMed.setChecked(false);
                    if (preferenceHelper.getBoolean(AppConstant.FOOD_SELECTED))
                        chkFood.setChecked(true);
                    else
                        chkFood.setChecked(false);
                    if (preferenceHelper.getBoolean(AppConstant.ACTIVITY_SELECTED))
                        chkActivity.setChecked(true);
                    else
                        chkActivity.setChecked(false);
                    if (preferenceHelper.getBoolean(AppConstant.MIS_SELECTED))
                        chkMis.setChecked(true);
                    else
                        chkMis.setChecked(false);
                }
                btnLogDone = (MyFontButton) dialog.findViewById(R.id.btn_log_done);
                btnLogDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (chkMed.isChecked())
                            preferenceHelper.setBoolean(AppConstant.MED_SELECTED, true);
                        else preferenceHelper.setBoolean(AppConstant.MED_SELECTED, false);
                        if (chkFood.isChecked())
                            preferenceHelper.setBoolean(AppConstant.FOOD_SELECTED, true);
                        else preferenceHelper.setBoolean(AppConstant.FOOD_SELECTED, false);
                        if (chkActivity.isChecked())
                            preferenceHelper.setBoolean(AppConstant.ACTIVITY_SELECTED, true);
                        else preferenceHelper.setBoolean(AppConstant.ACTIVITY_SELECTED, false);
                        if (chkMis.isChecked())
                            preferenceHelper.setBoolean(AppConstant.MIS_SELECTED, true);
                        else preferenceHelper.setBoolean(AppConstant.MIS_SELECTED, false);
                        dialog.dismiss();
                        updateUI();
                    }
                });
                dialog.show();


                break;
            case R.id.btn_days:
                int selectedItem = preferenceHelper.getInteger(AppConstant.DAY_SELECTED);
                showDialog(items, selectedItem);
                break;
            case R.id.btn_today:

//                DisplayLogMonthFragment.calendarView.setDate(new Date().getTime());
                updateUI();
                break;
            case R.id.btn_list:
                BTN_LIST_SELECTED = true;
                updateUI();
                break;
            case R.id.btn_month:
                BTN_LIST_SELECTED = false;
                updateUI();
                break;
            case R.id.btn_add_log:
                Intent intent = new Intent(this, AddLogActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void showDialog(final CharSequence[] items, int selectedItem) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an option");
        builder.setSingleChoiceItems(items, selectedItem, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int position) {
                Log.i("item", position + "");
                btnDays.setText(items[position]);
                preferenceHelper.setInteger(AppConstant.DAY_SELECTED, position);
                dialog.dismiss();
                updateUI();
            }
        });
        builder.setCancelable(true);
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void updateUI() {
        if (BTN_LIST_SELECTED) {
            btnList.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_blue));
            btnToday.setVisibility(View.GONE);
            btnMonth.setBackgroundColor(ContextCompat.getColor(this, R.color.datepicker_bg));
            btnType.setVisibility(View.VISIBLE);
            btnDays.setVisibility(View.VISIBLE);
            Fragment newFragment = new DisplayLogListFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.ll_fragment_for_log, newFragment).commit();
        } else {
            btnList.setBackgroundColor(ContextCompat.getColor(this, R.color.datepicker_bg));
            btnToday.setVisibility(View.VISIBLE);
            btnMonth.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_blue));
            btnType.setVisibility(View.GONE);
            btnDays.setVisibility(View.GONE);
            Fragment newFragment = new DisplayLogMonthFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.ll_fragment_for_log, newFragment).commit();
        }
    }
}
