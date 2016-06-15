package com.healthtracker.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.healthtracker.AddMeditationActivity;
import com.healthtracker.MainActivity;
import com.healthtracker.R;
import com.healthtracker.adapter.SelectedMedListAdapter;
import com.healthtracker.component.MyFontButton;
import com.healthtracker.component.MyFontEdittextView;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.helper.DataBaseHelper;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.model.LogEntry;
import com.healthtracker.util.AppConstant;
import com.healthtracker.util.Util;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by local nidhi on 18-05-2016.
 */
public class AddLogFragment extends Fragment implements View.OnClickListener, CalendarDatePickerDialogFragment.OnDateSetListener, RadialTimePickerDialogFragment.OnTimeSetListener {

    Activity activity;
    DataBaseHelper dbhelper;
    PreferenceHelper phelper;
    int fragment_id = 0;
    String date;


    ListView lstSelectedItems;
    SelectedMedListAdapter selectedMedListAdapter;
    private MyFontTextView tvDate;
    private MyFontTextView tvTime;
    private LinearLayout llFragment;
    private MyFontEdittextView etNote;
    private MyFontButton btnSave;
    private MyFontButton btnCancel;
    private MyFontButton btnAddLog;
    Spinner spinner;
    public static ArrayList<com.healthtracker.model.Log> selectedLogList = new ArrayList<com.healthtracker.model.Log>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this.getActivity();
        Bundle bundle = getArguments();
        fragment_id = bundle.getInt(AppConstant.FRAGMENT_ID);
        dbhelper = new DataBaseHelper(activity);
        phelper = new PreferenceHelper(activity);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_log, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner = (Spinner) view.findViewById(R.id.spinner_for_time);

        tvDate = (MyFontTextView) view.findViewById(R.id.tv_date);
        tvTime = (MyFontTextView) view.findViewById(R.id.tv_time);
        llFragment = (LinearLayout) view.findViewById(R.id.ll_fragment);
        etNote = (MyFontEdittextView) view.findViewById(R.id.et_note);
        btnSave = (MyFontButton) view.findViewById(R.id.btn_save);
        btnCancel = (MyFontButton) view.findViewById(R.id.btn_cancel);
        btnAddLog = (MyFontButton) view.findViewById(R.id.btn_add_log);
        lstSelectedItems = (ListView) view.findViewById(R.id.lst_selected_items);
        btnAddLog.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        tvTime.setOnClickListener(this);
        date = Util.getToday();
        Log.i("today date", date);
        tvDate.setText(date);
        ArrayList<String> timeList = new ArrayList<String>();


        switch (fragment_id) {
            case AppConstant.MED_FRAGMENT:
                btnAddLog.setText("Add Meditation");
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
                if (AddMeditationActivity.selectedMeditationList.size() > 0) {
                    selectedMedListAdapter = new SelectedMedListAdapter(this.getActivity(), AddMeditationActivity.selectedMeditationList);
                    lstSelectedItems.setAdapter(selectedMedListAdapter);
                }
                break;
            case AppConstant.FOOD_FRAGMENT:
                timeList.add(AppConstant.BREAKFAST);
                timeList.add(AppConstant.LUNCH);
                timeList.add(AppConstant.DINNER);
                timeList.add(AppConstant.SNACK);
                timeList.add(AppConstant.OTHER);
                btnAddLog.setText("Add food");
                if (AddMeditationActivity.selectedFoodList.size() > 0) {
                    selectedMedListAdapter = new SelectedMedListAdapter(this.getActivity(), AddMeditationActivity.selectedFoodList);
                    lstSelectedItems.setAdapter(selectedMedListAdapter);
                }
                break;
            case AppConstant.ACTIVITY_FRAGMENT:
                timeList.add(AppConstant.BEFORE_BREAKFAST);
                timeList.add(AppConstant.AFTER_BREAKFAST);
                timeList.add(AppConstant.BEFORE_LUNCH);
                timeList.add(AppConstant.AFTER_LUNCH);
                timeList.add(AppConstant.BEFORE_DINNER);
                timeList.add(AppConstant.AFTTER_DINNER);
                btnAddLog.setText("Add activity");
                timeList.add(AppConstant.OTHER);

                if (AddMeditationActivity.selectedActivityList.size() > 0) {
                    selectedMedListAdapter = new SelectedMedListAdapter(this.getActivity(), AddMeditationActivity.selectedActivityList);
                    lstSelectedItems.setAdapter(selectedMedListAdapter);
                }
                break;
            case AppConstant.MIS_FRAGMENT:
                btnAddLog.setVisibility(View.GONE);
                break;
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, timeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:

                switch (fragment_id) {
                    case AppConstant.MED_FRAGMENT:
                        selectedLogList = AddMeditationActivity.selectedMeditationList;
                        break;
                    case AppConstant.FOOD_FRAGMENT:
                        selectedLogList = AddMeditationActivity.selectedFoodList;
                        break;
                    case AppConstant.ACTIVITY_FRAGMENT:
                        selectedLogList = AddMeditationActivity.selectedActivityList;
                        break;
                }
                if (selectedLogList.size() > 0) {
                    for (int i = 0; i < selectedLogList.size(); i++) {
                        View view = getViewByPosition(i, lstSelectedItems);
                        MyFontEdittextView et = (MyFontEdittextView) view.findViewById(R.id.et_selected_med_quantity);
                        if (et.getText().toString() == null) {
                            Toast.makeText(this.getActivity(), "enter quantity first", Toast.LENGTH_LONG).show();
                        } else {
                            LogEntry logEntry = new LogEntry();
                            logEntry.setUserId(phelper.getInteger(AppConstant.USER_ID));
                            logEntry.setDate(tvDate.getText().toString());
                            logEntry.setTime(tvTime.getText().toString());
                            logEntry.setSelectedTime(spinner.getSelectedItem().toString());
                            logEntry.setNote(etNote.getText().toString());
                            logEntry.setRowId(selectedLogList.get(i).getRowId());
                            logEntry.setQuantity(Integer.parseInt(et.getText().toString()));
                            int id = dbhelper.addLogEntry(logEntry);
                            Log.i("id", id + "");
                            if (id > 0) {
                                Intent intent = new Intent(this.getActivity(), MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                }

                break;
            case R.id.btn_cancel:

                break;
            case R.id.btn_add_log:
                Intent intent = new Intent(this.getActivity(), AddMeditationActivity.class);
                switch (fragment_id) {
                    case AppConstant.MED_FRAGMENT:
                        intent.putExtra(AppConstant.FRAGMENT_ID, AppConstant.MED_FRAGMENT);
                        break;

                    case AppConstant.FOOD_FRAGMENT:
                        intent.putExtra(AppConstant.FRAGMENT_ID, AppConstant.FOOD_FRAGMENT);
                        break;

                    case AppConstant.ACTIVITY_FRAGMENT:
                        intent.putExtra(AppConstant.FRAGMENT_ID, AppConstant.ACTIVITY_FRAGMENT);
                        break;
                }
                startActivity(intent);
                break;
            case R.id.tv_time:
                final String FRAG_TAG_TIME_PICKER = "fragment_date_picker_name";
                RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                        .setOnTimeSetListener(this)
                        .setStartTime(10, 10)
                        .setDoneText("Done")
                        .setCancelText("Cancel")
                        .setThemeLight();
                rtpd.show(getFragmentManager(), FRAG_TAG_TIME_PICKER);

                break;
            case R.id.tv_date:

                final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(this)
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setDoneText("Done")
                        .setCancelText("Cancel")
                        .setThemeLight();
                cdp.show(getFragmentManager(), FRAG_TAG_DATE_PICKER);

                break;

        }

    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear,
                          int dayOfMonth) {

        date = dayOfMonth + "/" + monthOfYear + "/" + year;

        if (phelper.getInteger(AppConstant.DATE_SELECTED) == AppConstant.DATE_SELECTED_UNIT_DMY)

            tvDate.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
        else
            tvDate.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    @Override
    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
        tvTime.setText(hourOfDay + ":" + minute);
    }
}
