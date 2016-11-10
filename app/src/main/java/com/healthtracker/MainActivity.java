package com.healthtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.healthtracker.helper.DataBaseHelper;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.model.BloodPresure;
import com.healthtracker.model.Glucose;
import com.healthtracker.model.Thyroid;
import com.healthtracker.model.User;
import com.healthtracker.model.Weight;
import com.healthtracker.model.cholesterol;
import com.healthtracker.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarBaseActivitiy implements View.OnClickListener {
    Spinner spinner1;
    LinearLayout llWeight;
    LinearLayout llGlucose;
    LinearLayout llBp;
    LinearLayout llColestrol;
    LinearLayout llThyroid;
    LinearLayout llDiary;
    LinearLayout llChart;
    LinearLayout llLogs;
    LinearLayout llStatistics;
    LinearLayout llManage;
    DataBaseHelper dbhelper;
    PreferenceHelper phelper;

    private AdView mAdView;

    public static ArrayList<Weight> WeightList;
    public static ArrayList<Glucose> glucoseArrayList;
    public static ArrayList<BloodPresure> bloodPresureArrayList;
    public static ArrayList<cholesterol> cholesterolArrayList;
    public static ArrayList<Thyroid> thyroidArrayList;

    void findViews() {
        spinner1 = (Spinner) findViewById(R.id.spinner);
        llWeight = (LinearLayout) findViewById(R.id.ll_weight);
        llGlucose = (LinearLayout) findViewById(R.id.ll_glucose);
        llBp = (LinearLayout) findViewById(R.id.ll_bp);
        llColestrol = (LinearLayout) findViewById(R.id.ll_colestrol);
        llThyroid = (LinearLayout) findViewById(R.id.ll_thyroid);
        llDiary = (LinearLayout) findViewById(R.id.ll_diary);
        llChart = (LinearLayout) findViewById(R.id.ll_chart);
        llLogs = (LinearLayout) findViewById(R.id.ll_logs);
        llStatistics = (LinearLayout) findViewById(R.id.ll_statistics);
        llManage = (LinearLayout) findViewById(R.id.ll_manage);
        llWeight.setOnClickListener(this);
        llBp.setOnClickListener(this);
        llColestrol.setOnClickListener(this);
        llGlucose.setOnClickListener(this);
        llThyroid.setOnClickListener(this);
        llLogs.setOnClickListener(this);
        llStatistics.setOnClickListener(this);
        llManage.setOnClickListener(this);
        llChart.setOnClickListener(this);
        llDiary.setOnClickListener(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.app_name));
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        findViews();
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        dbhelper = new DataBaseHelper(this);
        phelper = new PreferenceHelper(this);
        setUsersList();
        checkPreferencesForUnits();
    }

    private void checkPreferencesForUnits() {
        if (!phelper.is_exist(AppConstant.LENGTH_SELECTED)) {
            phelper.setInteger(AppConstant.LENGTH_SELECTED, AppConstant.LENGTH_SELECTED_UNIT_INCH);
        }
        if (!phelper.is_exist(AppConstant.WEIGHT_SELECTED)) {
            phelper.setInteger(AppConstant.WEIGHT_SELECTED, AppConstant.WEIGHT_SELECTED_UNIT_POUNDS);
        }
        if (!phelper.is_exist(AppConstant.GLUCOSE_SELECTED)) {
            phelper.setInteger(AppConstant.GLUCOSE_SELECTED, AppConstant.GLUCOSE_SELECTED_UNIT_MG_PER_DL);
        }
        if (!phelper.is_exist(AppConstant.HBA1C_SELECTED)) {
            phelper.setInteger(AppConstant.HBA1C_SELECTED, AppConstant.HBA1C_SELECTED_UNIT_IFFC);
        }
        if (!phelper.is_exist(AppConstant.DATE_SELECTED)) {
            phelper.setInteger(AppConstant.DATE_SELECTED, AppConstant.DATE_SELECTED_UNIT_MDY);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    private void setUsersList() {
        ArrayList<User> usersList = dbhelper.getAllUser();
        List<String> ary = new ArrayList<String>();
        for (User user : usersList) {
            ary.add(user.getUserName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ary);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
        spinner1.setOnItemSelectedListener(new ItemSelectedListener());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        setUsersList();
        WeightList = dbhelper.getAllWeight(phelper.getInteger(AppConstant.USER_ID));
        glucoseArrayList = dbhelper.getAllGlucose(phelper.getInteger(AppConstant.USER_ID));
        bloodPresureArrayList = dbhelper.getAllBP(phelper.getInteger(AppConstant.USER_ID));
        cholesterolArrayList = dbhelper.getAllCholestrol(phelper.getInteger(AppConstant.USER_ID));
        thyroidArrayList = dbhelper.getAllThyroid(phelper.getInteger(AppConstant.USER_ID));
    }

    @Override
    public void onClick(View v) {
        if (!phelper.is_exist(AppConstant.USER_ID)) {
            Intent intent_new_user = new Intent(this, NewUserActivity.class);
            startActivity(intent_new_user);
        } else {
            switch (v.getId()) {
                case R.id.ll_weight:
                    openAddDetailActivity(1, this);
                    break;

                case R.id.ll_glucose:
                    openAddDetailActivity(2, this);
                    break;
                case R.id.ll_bp:
                    openAddDetailActivity(3, this);
                    break;
                case R.id.ll_colestrol:
                    openAddDetailActivity(4, this);
                    break;
                case R.id.ll_thyroid:
                    openAddDetailActivity(5, this);
                    break;
                case R.id.ll_logs:
                    Intent intentlog = new Intent(this, LogDisplayActivity.class);
                    startActivity(intentlog);
                    break;
                case R.id.ll_chart:
                    Intent intent2 = new Intent(this, ChartActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.ll_diary:
                    Intent intent4 = new Intent(this, DiaryActivity.class);
                    startActivity(intent4);
                    break;
                case R.id.ll_manage:
                    Intent intent3 = new Intent(this, ManageActivity.class);
                    startActivity(intent3);
                    break;
                case R.id.ll_statistics:
                    Intent intent5 = new Intent(this, StatisticsActivity.class);
                    startActivity(intent5);
                    break;
            }
        }
    }

    public void openAddDetailActivity(int fragment_id, Activity acct) {
        Intent intent = new Intent(acct, AddDetailActivity.class);
        intent.putExtra(AppConstant.FRAGMENT_ID, fragment_id);
        intent.putExtra(AppConstant.IS_EDIT, false);
        intent.putExtra(AppConstant.USER_ID, phelper.getInteger(AppConstant.USER_ID));
        startActivity(intent);
    }

    public class ItemSelectedListener implements AdapterView.OnItemSelectedListener {
        //get strings of first item
        String firstItem = String.valueOf(spinner1.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            phelper.setInteger(AppConstant.USER_ID, dbhelper.getUserIdFromPosition(pos));
//            Toast.makeText(parent.getContext(),
//                    "You have selected : " + parent.getItemAtPosition(pos).toString(),
//                    Toast.LENGTH_LONG).show();
            // Todo when item is selected by the user
        }


        @Override
        public void onNothingSelected(AdapterView<?> arg) {
            Toast.makeText(arg.getContext(), "please select any user", Toast.LENGTH_LONG).show();
        }
    }
}
