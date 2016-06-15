package com.healthtracker;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.healthtracker.component.MyFontButton;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_display);
        findViews();
        dataBaseHelper = new DataBaseHelper(this);
        preferenceHelper = new PreferenceHelper(this);
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
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_type:

                break;
            case R.id.btn_days:
                final CharSequence[] items = {"All", "7 Day", "14 Day", "30 Day", "90 Day"};
                int selectedItem = preferenceHelper.getInteger(AppConstant.GLUCOSE_SELECTED);
                showDialog(items, selectedItem);

                break;
            case R.id.btn_today:

                break;
            case R.id.btn_list:
                BTN_LIST_SELECTED = true;
                updateUI();
                break;
            case R.id.btn_month:
                BTN_LIST_SELECTED = false;
                updateUI();
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
                dialog.dismiss();
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


        } else {
            btnList.setBackgroundColor(ContextCompat.getColor(this, R.color.datepicker_bg));
            btnToday.setVisibility(View.VISIBLE);
            btnMonth.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_blue));
            btnType.setVisibility(View.GONE);
            btnDays.setVisibility(View.GONE);
        }
    }
}
