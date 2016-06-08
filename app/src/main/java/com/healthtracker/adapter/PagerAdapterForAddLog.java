package com.healthtracker.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.healthtracker.fragment.AddLogFragment;
import com.healthtracker.util.AppConstant;

/**
 * Created by local nidhi on 18-05-2016.
 */
public class PagerAdapterForAddLog extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterForAddLog(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        AddLogFragment tab1 = new AddLogFragment();
        Bundle bundle = new Bundle();

        switch (position) {
            case 0:
                bundle.putInt(AppConstant.FRAGMENT_ID, AppConstant.MED_FRAGMENT);
                tab1.setArguments(bundle);
                return tab1;
            case 1:
                bundle.putInt(AppConstant.FRAGMENT_ID, AppConstant.FOOD_FRAGMENT);
                tab1.setArguments(bundle);
                return tab1;
            case 2:
                bundle.putInt(AppConstant.FRAGMENT_ID, AppConstant.ACTIVITY_FRAGMENT);
                tab1.setArguments(bundle);
                return tab1;
            case 3:
                bundle.putInt(AppConstant.FRAGMENT_ID, AppConstant.MIS_FRAGMENT);
                tab1.setArguments(bundle);
                return tab1;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
