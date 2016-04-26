package com.healthtracker.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;

import com.healthtracker.R;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.component.MyFontEdittextView;

public class BPFragment extends Fragment  {

    private MyFontTextView tvSystolic;
    private MyFontEdittextView etSystolic;
    private MyFontTextView tvDiastolic;
    private MyFontEdittextView etDiastolic;
    private MyFontTextView tvHeartRate;
    private MyFontEdittextView etHeartRate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.b_p_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvSystolic = (MyFontTextView) view.findViewById(R.id.tv_systolic);
        etSystolic = (MyFontEdittextView) view.findViewById(R.id.et_systolic);
        tvDiastolic = (MyFontTextView) view.findViewById(R.id.tv_diastolic);
        etDiastolic = (MyFontEdittextView) view.findViewById(R.id.et_diastolic);
        tvHeartRate = (MyFontTextView) view.findViewById(R.id.tv_heart_rate);
        etHeartRate = (MyFontEdittextView) view.findViewById(R.id.et_heart_rate);
    }

}
