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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarBaseActivitiy implements View.OnClickListener {
    private Spinner spinner1;
    private LinearLayout llWeight;
    private LinearLayout llGlucose;
    private LinearLayout llBp;
    private LinearLayout llColestrol;
    private LinearLayout llThyroid;
    private LinearLayout llDiary;
    private LinearLayout llChart;
    private LinearLayout llLogs;
    private LinearLayout llAddlogs;
    private LinearLayout llStatistics;
    private LinearLayout llManage;

    private void findViews() {
        spinner1 = (Spinner) findViewById(R.id.spinner);
        llWeight = (LinearLayout) findViewById(R.id.ll_weight);
        llGlucose = (LinearLayout) findViewById(R.id.ll_glucose);
        llBp = (LinearLayout) findViewById(R.id.ll_bp);
        llColestrol = (LinearLayout) findViewById(R.id.ll_colestrol);
        llThyroid = (LinearLayout) findViewById(R.id.ll_thyroid);
        llDiary = (LinearLayout) findViewById(R.id.ll_diary);
        llChart = (LinearLayout) findViewById(R.id.ll_chart);
        llLogs = (LinearLayout) findViewById(R.id.ll_logs);
        llAddlogs = (LinearLayout) findViewById(R.id.ll_addlogs);
        llStatistics = (LinearLayout) findViewById(R.id.ll_statistics);
        llManage = (LinearLayout) findViewById(R.id.ll_manage);
        llWeight.setOnClickListener(this);
        llBp.setOnClickListener(this);
        llColestrol.setOnClickListener(this);
        llGlucose.setOnClickListener(this);
        llThyroid.setOnClickListener(this);
        llLogs.setOnClickListener(this);
        llAddlogs.setOnClickListener(this);
        llStatistics.setOnClickListener(this);
        llManage.setOnClickListener(this);
        llChart.setOnClickListener(this);
        llDiary.setOnClickListener(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setTitle("Health tracker");
        List<String> list = new ArrayList<String>();
        list.add("nidhi");
        list.add("harvi");
        list.add("raghav");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(dataAdapter);
        spinner1.setOnItemSelectedListener(new ItemSelectedListener());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_weight:
                openAddDetailActivity(1,this);
                break;

            case R.id.ll_glucose:
                openAddDetailActivity(2, this);
                break;
            case R.id.ll_bp:
                openAddDetailActivity(3,this);
                break;
            case R.id.ll_colestrol:
                openAddDetailActivity(4,this);
                break;
            case R.id.ll_thyroid:
                openAddDetailActivity(5,this);
                break;
            case R.id.ll_logs:

                break;
            case R.id.ll_addlogs:

                break;
            case R.id.ll_chart:

                break;
            case R.id.ll_diary:

                break;
            case R.id.ll_manage:

                break;
            case R.id.ll_statistics:

                break;
        }
    }
    public void openAddDetailActivity(int fragment_id,Activity acct){
        Intent intent = new Intent(acct, AddDetailActivity.class);
        intent.putExtra("fragment_id",fragment_id);
        startActivity(intent);
    }

    public class ItemSelectedListener implements AdapterView.OnItemSelectedListener {
        //get strings of first item
        String firstItem = String.valueOf(spinner1.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (firstItem.equals(String.valueOf(spinner1.getSelectedItem()))) {
                // ToDo when first item is selected
            } else {
                Toast.makeText(parent.getContext(),
                        "You have selected : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_LONG).show();
                // Todo when item is selected by the user
            }
        }


        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }
    }
}
