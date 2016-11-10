package com.healthtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.healthtracker.util.AppConstant;

public class ChartActivity extends ActionBarBaseActivitiy implements View.OnClickListener {

    private LinearLayout llWeight;
    private LinearLayout llGlucose;
    private LinearLayout llBp;
    private LinearLayout llThyroid;
    private LinearLayout llCholestrol;
    private LinearLayout llWeightGlucose;
    private LinearLayout llWeightCholestrol;
    private LinearLayout llWeightThyroid;
    private LinearLayout llWeightBp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        setTitle(getString(R.string.app_name));

        llWeight = (LinearLayout) findViewById(R.id.ll_weight);
        llGlucose = (LinearLayout) findViewById(R.id.ll_glucose);
        llBp = (LinearLayout) findViewById(R.id.ll_bp);
        llThyroid = (LinearLayout) findViewById(R.id.ll_thyroid);
        llCholestrol = (LinearLayout) findViewById(R.id.ll_cholestrol);
        llWeightCholestrol = (LinearLayout) findViewById(R.id.ll_weight_cholestrol);
        llWeightGlucose = (LinearLayout) findViewById(R.id.ll_weight_glucose);
        llWeightThyroid = (LinearLayout) findViewById(R.id.ll_weight_thyroid);
        llWeightBp = (LinearLayout) findViewById(R.id.ll_weight_bp);

        llWeight.setOnClickListener(this);
        llWeightThyroid.setOnClickListener(this);
        llWeightCholestrol.setOnClickListener(this);
        llWeightGlucose.setOnClickListener(this);
        llWeightBp.setOnClickListener(this);
        llBp.setOnClickListener(this);
        llCholestrol.setOnClickListener(this);
        llThyroid.setOnClickListener(this);
        llGlucose.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ChartDetailActivity.class);
        switch (v.getId()) {
            case R.id.ll_weight:
                intent.putExtra(AppConstant.CHART_ID, AppConstant.CHART_WEIGHT);
                break;
            case R.id.ll_glucose:
                intent.putExtra(AppConstant.CHART_ID, AppConstant.CHART_GLUCOSE);
                break;
            case R.id.ll_cholestrol:
                intent.putExtra(AppConstant.CHART_ID, AppConstant.CHART_CHOLESTROL);
                break;
            case R.id.ll_bp:
                intent.putExtra(AppConstant.CHART_ID, AppConstant.CHART_B_P);
                break;
            case R.id.ll_thyroid:
                intent.putExtra(AppConstant.CHART_ID, AppConstant.CHART_THYROID);
                break;
            case R.id.ll_weight_glucose:
                intent.putExtra(AppConstant.CHART_ID, AppConstant.CHART_WEIGHT_GLUCOSE);
                break;
            case R.id.ll_weight_bp:
                intent.putExtra(AppConstant.CHART_ID, AppConstant.CHART_WEIGHT_B_P);
                break;
            case R.id.ll_weight_cholestrol:
                intent.putExtra(AppConstant.CHART_ID, AppConstant.CHART_WEIGHT_CHOLESTROL);
                break;
            case R.id.ll_weight_thyroid:
                intent.putExtra(AppConstant.CHART_ID, AppConstant.CHART_WEIGHT_THYROID);
                break;
        }
        startActivity(intent);
    }
}
