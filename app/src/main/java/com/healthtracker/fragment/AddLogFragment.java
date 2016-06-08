package com.healthtracker.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.healthtracker.AddMeditationActivity;
import com.healthtracker.R;
import com.healthtracker.adapter.SelectedMedListAdapter;
import com.healthtracker.component.MyFontButton;
import com.healthtracker.component.MyFontEdittextView;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.helper.DataBaseHelper;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.util.AppConstant;
import com.healthtracker.util.Util;

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


        switch (fragment_id) {
            case AppConstant.MED_FRAGMENT:
                btnAddLog.setText("Add Meditation");
                if (AddMeditationActivity.selectedMeditationList.size() > 0) {
                    selectedMedListAdapter = new SelectedMedListAdapter(this.getActivity(), AddMeditationActivity.selectedMeditationList);
                    lstSelectedItems.setAdapter(selectedMedListAdapter);
                }
                break;
            case AppConstant.FOOD_FRAGMENT:
                btnAddLog.setText("Add food");
                if (AddMeditationActivity.selectedFoodList.size() > 0) {
                    selectedMedListAdapter = new SelectedMedListAdapter(this.getActivity(), AddMeditationActivity.selectedFoodList);
                    lstSelectedItems.setAdapter(selectedMedListAdapter);
                }
                break;
            case AppConstant.ACTIVITY_FRAGMENT:
                btnAddLog.setText("Add activity");
                if (AddMeditationActivity.selectedActivityList.size() > 0) {
                    selectedMedListAdapter = new SelectedMedListAdapter(this.getActivity(), AddMeditationActivity.selectedActivityList);
                    lstSelectedItems.setAdapter(selectedMedListAdapter);
                }
                break;
            case AppConstant.MIS_FRAGMENT:
                btnAddLog.setVisibility(View.GONE);
                break;
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:


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

    @Override
    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
        tvTime.setText(hourOfDay + ":" + minute);
    }
}
