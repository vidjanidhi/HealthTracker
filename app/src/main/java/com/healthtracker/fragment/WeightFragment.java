package com.healthtracker.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;

import com.healthtracker.R;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.component.MyFontEdittextView;

public class WeightFragment extends Fragment  {

    private MyFontTextView tvWeight;
    private MyFontEdittextView etWeight;
    private MyFontTextView tvFat;
    private MyFontEdittextView etFat;
    private MyFontTextView tvAbdomen;
    private MyFontEdittextView etAbdomen;
    private MyFontTextView tvWaist;
    private MyFontEdittextView etWaist;
    private MyFontTextView tvHip;
    private MyFontEdittextView etHip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weight_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvWeight = (MyFontTextView) view.findViewById(R.id.tv_weight);
        etWeight = (MyFontEdittextView) view.findViewById(R.id.et_weight);
        tvFat = (MyFontTextView) view.findViewById(R.id.tv_fat);
        etFat = (MyFontEdittextView) view.findViewById(R.id.et_fat);
        tvAbdomen = (MyFontTextView) view.findViewById(R.id.tv_abdomen);
        etAbdomen = (MyFontEdittextView) view.findViewById(R.id.et_abdomen);
        tvWaist = (MyFontTextView) view.findViewById(R.id.tv_waist);
        etWaist = (MyFontEdittextView) view.findViewById(R.id.et_waist);
        tvHip = (MyFontTextView) view.findViewById(R.id.tv_hip);
        etHip = (MyFontEdittextView) view.findViewById(R.id.et_hip);
    }

}
