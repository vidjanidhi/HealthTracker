package com.healthtracker;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.healthtracker.adapter.PagerAdapterForAddLog;
import com.healthtracker.util.AppConstant;

public class AddLogActivity extends AppCompatActivity {
    int tabId = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_log);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_add_log);
        if (getIntent().getExtras() != null) {
            tabId = getIntent().getIntExtra(AppConstant.TAB_ID, 100);
        }
        tabLayout.addTab(tabLayout.newTab().setText("Meds"));
        tabLayout.addTab(tabLayout.newTab().setText("Food"));
        tabLayout.addTab(tabLayout.newTab().setText("Activity"));
        tabLayout.addTab(tabLayout.newTab().setText("miscellaneous"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager_add_log);
        final PagerAdapterForAddLog adapter = new PagerAdapterForAddLog
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        if (tabId != 100) {
            viewPager.setCurrentItem(tabId);
        }

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });


    }
}
