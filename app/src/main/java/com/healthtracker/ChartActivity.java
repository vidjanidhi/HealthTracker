package com.healthtracker;

import android.os.Bundle;

public class ChartActivity extends ActionBarBaseActivitiy {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        setTitle("Health tracker");
    }
}
