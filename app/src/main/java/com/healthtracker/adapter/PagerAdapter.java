package com.healthtracker.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.healthtracker.fragment.WeightDairyFragment;
import com.healthtracker.util.AppConstant;

/**
 * Created by local nidhi on 06-05-2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        WeightDairyFragment tab1 = new WeightDairyFragment();
        Bundle bundle = new Bundle();

        switch (position) {
            case 0:
                bundle.putInt(AppConstant.FRAGMENT_ID, AppConstant.WEIGHT_FRAGMENT);
                tab1.setArguments(bundle);
                return tab1;
            case 1:

                bundle.putInt(AppConstant.FRAGMENT_ID, AppConstant.GLUCOSE_FRAGMENT);
                tab1.setArguments(bundle);
                return tab1;
            case 2:
                bundle.putInt(AppConstant.FRAGMENT_ID, AppConstant.B_P_FRAGMENT);
                tab1.setArguments(bundle);
                return tab1;
            case 3:
                bundle.putInt(AppConstant.FRAGMENT_ID, AppConstant.CHOLESTROL_FRAGMENT);
                tab1.setArguments(bundle);
                return tab1;
            case 4:
                bundle.putInt(AppConstant.FRAGMENT_ID, AppConstant.THYROID_FRAGMENT);
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
