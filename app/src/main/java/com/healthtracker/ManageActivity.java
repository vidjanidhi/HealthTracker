package com.healthtracker;

import android.os.Bundle;

public class ManageActivity extends ActionBarBaseActivitiy {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        setTitle("Health tracker");
    }
}
