package com.healthtracker;

import android.os.Bundle;

public class MainActivity extends ActionBarBaseActivitiy {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Health tracker");
    }
}
