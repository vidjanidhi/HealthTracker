package com.healthtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.healthtracker.adapter.PagerAdapter;
import com.healthtracker.helper.DataBaseHelper;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.util.AppConstant;

public class DiaryActivity extends ActionBarBaseActivitiy {

    int tabId = 100;
    DataBaseHelper dbhelper;
    PreferenceHelper phelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        if (getIntent().getExtras() != null) {
            tabId = getIntent().getIntExtra(AppConstant.TAB_ID, 100);
        }
        dbhelper = new DataBaseHelper(this);
        phelper = new PreferenceHelper(this);
        setTitle(getString(R.string.app_name) + "-" + dbhelper.getUser(phelper.getInteger(AppConstant.USER_ID)).getUserName());

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Weight").setIcon(R.drawable.weight));
        tabLayout.addTab(tabLayout.newTab().setText("Glucose").setIcon(R.drawable.glucose));
        tabLayout.addTab(tabLayout.newTab().setText("B.P").setIcon(R.drawable.bp));
        tabLayout.addTab(tabLayout.newTab().setText("Cholestorol").setIcon(R.drawable.colesterol));
        tabLayout.addTab(tabLayout.newTab().setText("Thyroid").setIcon(R.drawable.thyroid));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
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

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
