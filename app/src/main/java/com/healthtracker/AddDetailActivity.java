package com.healthtracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.healthtracker.component.MyFontButton;
import com.healthtracker.component.MyFontEdittextView;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.fragment.BPFragment;
import com.healthtracker.fragment.CholestrolFragment;
import com.healthtracker.fragment.GlucoseFragment;
import com.healthtracker.fragment.ThyroidFragment;
import com.healthtracker.fragment.WeightFragment;

public class AddDetailActivity extends AppCompatActivity {


    private ScrollView scrollView;
    private MyFontTextView tvDate;
    private MyFontTextView tvTime;
    private LinearLayout llFragment;
    private MyFontEdittextView etNote;
    private MyFontButton btnSave;
    private MyFontButton btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_detail);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        tvDate = (MyFontTextView) findViewById(R.id.tv_date);
        tvTime = (MyFontTextView) findViewById(R.id.tv_time);
        llFragment = (LinearLayout) findViewById(R.id.ll_fragment);
        etNote = (MyFontEdittextView) findViewById(R.id.et_note);
        btnSave = (MyFontButton) findViewById(R.id.btn_save);
        btnCancel = (MyFontButton) findViewById(R.id.btn_cancel);


        int fragment_id = getIntent().getIntExtra("fragment_id", 1);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment f = null;
        switch (fragment_id) {
            case 1:
                f = new WeightFragment();
                break;

            case 2:
                f = new GlucoseFragment();
                break;
            case 3:
                f = new BPFragment();
                break;
            case 4:
                f = new CholestrolFragment();
                break;
            case 5:
                f = new ThyroidFragment();
                break;

        }

        fragmentTransaction.add(R.id.ll_fragment, f, "HELLO");
        fragmentTransaction.commit();
    }
}
