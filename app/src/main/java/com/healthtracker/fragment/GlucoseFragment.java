package com.healthtracker.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Spinner;

import com.healthtracker.R;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.component.MyFontEdittextView;

public class GlucoseFragment extends Fragment  {

    private Spinner spinner;
    private MyFontTextView tvGlucose;
    private MyFontEdittextView etGlucose;
    private MyFontTextView tvHba1c;
    private MyFontEdittextView etHba1c;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.glucose_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner = (Spinner) view.findViewById(R.id.spinner);
        tvGlucose = (MyFontTextView) view.findViewById(R.id.tv_glucose);
        etGlucose = (MyFontEdittextView) view.findViewById(R.id.et_glucose);
        tvHba1c = (MyFontTextView) view.findViewById(R.id.tv_hba1c);
        etHba1c = (MyFontEdittextView) view.findViewById(R.id.et_hba1c);
    }

}
