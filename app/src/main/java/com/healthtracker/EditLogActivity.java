package com.healthtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.healthtracker.component.MyFontButton;
import com.healthtracker.component.MyFontEdittextView;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.helper.DataBaseHelper;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.model.Log;
import com.healthtracker.model.LogEntry;
import com.healthtracker.util.AppConstant;
import com.healthtracker.util.Util;

import java.util.ArrayList;
import java.util.Calendar;

public class EditLogActivity extends ActionBarBaseActivitiy implements View.OnClickListener, CalendarDatePickerDialogFragment.OnDateSetListener, RadialTimePickerDialogFragment.OnTimeSetListener {

    private MyFontTextView tvDate;
    private MyFontTextView tvTime;
    private Spinner spinnerForTime;
    private MyFontTextView tvLogname;
    private MyFontEdittextView etLogQuantity;
    private MyFontEdittextView etNote;
    private MyFontButton btnEditLog;
    private MyFontButton btnDeleteLog;
    DataBaseHelper dataBaseHelper;
    PreferenceHelper preferenceHelper;
    int log_entry_id = 0;
    int rowId = 0;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_log);

        log_entry_id = getIntent().getExtras().getInt(AppConstant.LOG_ENTRY_ID);
        dataBaseHelper = new DataBaseHelper(this);
        preferenceHelper = new PreferenceHelper(this);

        setTitle(getString(R.string.app_name) + "-" + dataBaseHelper.getUser(preferenceHelper.getInteger(AppConstant.USER_ID)).getUserName());

        getView();
        LogEntry logEntry = dataBaseHelper.getLogEntryFromId(log_entry_id);
        rowId = logEntry.getRowId();
        date = logEntry.getDate();
        tvDate.setText(logEntry.getDate());
        tvTime.setText(logEntry.getTime());
        etNote.setText(logEntry.getNote());
        etLogQuantity.setText(logEntry.getQuantity() + "");
        Log log = dataBaseHelper.getLogFromRowId(logEntry.getRowId());
        tvLogname.setText(log.getName() + "(" + log.getUnit() + ")");

        if (log.getLogId() == AppConstant.MIS_FRAGMENT) {
            spinnerForTime.setVisibility(View.GONE);
            tvLogname.setVisibility(View.GONE);
            etLogQuantity.setVisibility(View.GONE);
        }

        String compareValue = logEntry.getSelectedTime();
        ArrayList<String> timeList = Util.getSelectedTime(log.getLogId());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForTime.setAdapter(dataAdapter);
        if (compareValue != null) {
            int spinnerPosition = dataAdapter.getPosition(compareValue);
            spinnerForTime.setSelection(spinnerPosition);
        }
    }

    void getView() {
        tvDate = (MyFontTextView) findViewById(R.id.tv_date);
        tvTime = (MyFontTextView) findViewById(R.id.tv_time);
        spinnerForTime = (Spinner) findViewById(R.id.spinner_for_time);
        tvLogname = (MyFontTextView) findViewById(R.id.tv_logname);
        etLogQuantity = (MyFontEdittextView) findViewById(R.id.et_log_quantity);
        etNote = (MyFontEdittextView) findViewById(R.id.et_note);
        btnEditLog = (MyFontButton) findViewById(R.id.btn_edit_log);
        btnDeleteLog = (MyFontButton) findViewById(R.id.btn_delete_log);
        btnDeleteLog.setOnClickListener(this);
        btnEditLog.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        tvTime.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_delete_log:
                long id1 = dataBaseHelper.deleteLogEntry(log_entry_id);
                if (id1 > 0) {
                    Toast.makeText(this, "deleted successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, LogDisplayActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.btn_edit_log:
                LogEntry logEntry = new LogEntry();
                logEntry.setId(log_entry_id);
                logEntry.setUserId(preferenceHelper.getInteger(AppConstant.USER_ID));
                logEntry.setRowId(rowId);
                logEntry.setQuantity(Integer.parseInt(etLogQuantity.getText().toString()));
                logEntry.setNote(etNote.getText().toString());
                logEntry.setDate(date);
                logEntry.setTime(tvTime.getText().toString());
                logEntry.setSelectedTime(spinnerForTime.getSelectedItem().toString());
                long id = dataBaseHelper.updateLogEntry(logEntry);
                if (id > 0) {
                    Toast.makeText(this, "updated successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, LogDisplayActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_date:
                final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(this)
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setDoneText("Done")
                        .setCancelText("Cancel")
                        .setThemeLight();
                cdp.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
                break;
            case R.id.tv_time:
                final String FRAG_TAG_TIME_PICKER = "fragment_date_picker_name";
                RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                        .setOnTimeSetListener(this)
                        .setStartTime(10, 10)
                        .setDoneText("Done")
                        .setCancelText("Cancel")
                        .setThemeLight();
                rtpd.show(getSupportFragmentManager(), FRAG_TAG_TIME_PICKER);
                break;

        }

    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear,
                          int dayOfMonth) {

        date = dayOfMonth + "/" + monthOfYear + "/" + year;
        if (preferenceHelper.getInteger(AppConstant.DATE_SELECTED) == AppConstant.DATE_SELECTED_UNIT_DMY)
            tvDate.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
        else
            tvDate.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
    }

    @Override
    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
        boolean isPM = (hourOfDay >= 12);
        tvTime.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
    }
}
