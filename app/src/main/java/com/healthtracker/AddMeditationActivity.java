package com.healthtracker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.healthtracker.adapter.MedListAdapter;
import com.healthtracker.component.MyFontButton;
import com.healthtracker.component.MyFontEdittextView;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.fragment.WeightDairyFragment;
import com.healthtracker.helper.DataBaseHelper;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.model.Log;
import com.healthtracker.util.AppConstant;

import java.util.ArrayList;

public class AddMeditationActivity extends ActionBarBaseActivitiy implements View.OnClickListener {

    Activity activity;
    DataBaseHelper dbhelper;
    PreferenceHelper phelper;
    int fragment_id = 0;

    private MyFontButton btnMedDone;
    private MyFontTextView tvMedTitle;
    private MyFontButton btnMedAdd;
    private ListView lstMeds;
    ArrayAdapter<String> dataAdapter;
    public static ArrayList<Log> selectedMeditationList = new ArrayList<Log>();
    public static ArrayList<Log> selectedFoodList = new ArrayList<Log>();
    public static ArrayList<Log> selectedActivityList = new ArrayList<Log>();
    public static MedListAdapter medListAdapter;
    public static ArrayList<Log> logList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meditation);

        activity = this;
        fragment_id = getIntent().getIntExtra(AppConstant.FRAGMENT_ID, 1);
        dbhelper = new DataBaseHelper(activity);
        phelper = new PreferenceHelper(activity);
        setTitle(getString(R.string.app_name) + "-" + dbhelper.getUser(phelper.getInteger(AppConstant.USER_ID)).getUserName());


        btnMedDone = (MyFontButton) findViewById(R.id.btn_med_done);
        tvMedTitle = (MyFontTextView) findViewById(R.id.tv_med_title);
        btnMedAdd = (MyFontButton) findViewById(R.id.btn_med_add);
        lstMeds = (ListView) findViewById(R.id.lst_meds);
        btnMedDone.setOnClickListener(this);
        btnMedAdd.setOnClickListener(this);

        switch (fragment_id) {
            case AppConstant.MED_FRAGMENT:
                logList = dbhelper.getAllLog(AppConstant.MED_FRAGMENT);
                tvMedTitle.setText("Pick Med(s)");
                if (selectedMeditationList.size() > 0) {
                    for (int i = 0; i < selectedMeditationList.size(); i++) {
                        for (int j = 0; j < logList.size(); j++) {
                            if (selectedMeditationList.get(i).getRowId() == logList.get(j).getRowId()) {
                                logList.get(j).setChecked(true);
                            }
                        }
                    }
                }

                break;
            case AppConstant.FOOD_FRAGMENT:
                logList = dbhelper.getAllLog(AppConstant.FOOD_FRAGMENT);
                tvMedTitle.setText("Pick Food(s)");
                if (selectedFoodList.size() > 0) {
                    for (int i = 0; i < selectedFoodList.size(); i++) {
                        for (int j = 0; j < logList.size(); j++) {
                            if (selectedFoodList.get(i).getRowId() == logList.get(j).getRowId()) {
                                logList.get(j).setChecked(true);
                            }
                        }
                    }
                }
                break;

            case AppConstant.ACTIVITY_FRAGMENT:
                logList = dbhelper.getAllLog(AppConstant.ACTIVITY_FRAGMENT);
                tvMedTitle.setText("Pick Activity(s)");
                if (selectedActivityList.size() > 0) {
                    for (int i = 0; i < selectedActivityList.size(); i++) {
                        for (int j = 0; j < logList.size(); j++) {
                            if (selectedActivityList.get(i).getRowId() == logList.get(j).getRowId()) {
                                logList.get(j).setChecked(true);
                            }
                        }
                    }
                }
                break;

        }
        medListAdapter = new MedListAdapter(this, logList, fragment_id);
        lstMeds.setAdapter(medListAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_med_done:
                switch (fragment_id) {
                    case AppConstant.MED_FRAGMENT:
                        selectedMeditationList.clear();
                        break;
                    case AppConstant.FOOD_FRAGMENT:
                        selectedFoodList.clear();
                        break;
                    case AppConstant.ACTIVITY_FRAGMENT:
                        selectedActivityList.clear();
                        break;
                }
                for (int i = 0; i < logList.size(); i++) {
                    if (logList.get(i).isChecked()) {
                        switch (fragment_id) {
                            case AppConstant.MED_FRAGMENT:
                                selectedMeditationList.add(logList.get(i));
                                break;
                            case AppConstant.FOOD_FRAGMENT:
                                selectedFoodList.add(logList.get(i));
                                break;
                            case AppConstant.ACTIVITY_FRAGMENT:
                                selectedActivityList.add(logList.get(i));
                                break;
                        }
                    }
                }
                Intent intent = new Intent(this, AddLogActivity.class);
                switch (fragment_id) {
                    case AppConstant.MED_FRAGMENT:
                        intent.putExtra(AppConstant.TAB_ID, 0);
                        break;

                    case AppConstant.FOOD_FRAGMENT:
                        intent.putExtra(AppConstant.TAB_ID, 1);
                        break;

                    case AppConstant.ACTIVITY_FRAGMENT:
                        intent.putExtra(AppConstant.TAB_ID, 2);
                        break;
                }
                startActivity(intent);
                break;
            case R.id.btn_med_add:
                ArrayList<String> units = new ArrayList<String>();
//                String[] units = new String[4];
                int logId = 0;
                switch (fragment_id) {
                    case AppConstant.MED_FRAGMENT:
                        units.add("mg");
                        units.add("pills");
                        units.add("puffs");
                        units.add("suppositories");
                        logId = AppConstant.MED_FRAGMENT;
                        break;

                    case AppConstant.FOOD_FRAGMENT:
                        units.add("grams");
                        logId = AppConstant.FOOD_FRAGMENT;
                        break;

                    case AppConstant.ACTIVITY_FRAGMENT:
                        units.add("mins");
                        logId = AppConstant.ACTIVITY_FRAGMENT;
                        break;
                }
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.fragment_insert_med);
                WeightDairyFragment.setDialogDimention(dialog);

                final MyFontEdittextView etInsertMedName;
                final Spinner spinnerInsertMedUnit;

                MyFontButton btnAdd;
                etInsertMedName = (MyFontEdittextView) dialog.findViewById(R.id.et_insert_med_name);
                spinnerInsertMedUnit = (Spinner) dialog.findViewById(R.id.spinner_insert_med_unit);
                btnAdd = (MyFontButton) dialog.findViewById(R.id.btn_add);

                dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, units);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerInsertMedUnit.setAdapter(dataAdapter);

                final int finalLogId = logId;
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log log = new Log();
                        if (!TextUtils.isEmpty(etInsertMedName.getText().toString()))
                            log.setName(etInsertMedName.getText().toString());
                        log.setUnit(spinnerInsertMedUnit.getSelectedItem().toString());
                        log.setLogId(finalLogId);

                        int id = dbhelper.addLog(log);
                        logList.add(log);
                        dataAdapter.notifyDataSetChanged();
                        android.util.Log.i("row id ", id + "");
                        dialog.cancel();

                    }
                });

                dialog.show();
                break;
        }

    }
}
