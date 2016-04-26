package com.healthtracker.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;

import com.healthtracker.R;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.component.MyFontEdittextView;

public class CholestrolFragment extends Fragment  {

    private MyFontTextView tvHdl;
    private MyFontEdittextView etHdl;
    private MyFontTextView tvLdl;
    private MyFontEdittextView etLdl;
    private MyFontTextView tvTotal;
    private MyFontEdittextView etTotal;
    private MyFontTextView tvTriglycerides;
    private MyFontEdittextView etTriglycerides;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cholestrol_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHdl = (MyFontTextView) view.findViewById(R.id.tv_hdl);
        etHdl = (MyFontEdittextView) view.findViewById(R.id.et_hdl);
        tvLdl = (MyFontTextView) view.findViewById(R.id.tv_ldl);
        etLdl = (MyFontEdittextView) view.findViewById(R.id.et_ldl);
        tvTotal = (MyFontTextView) view.findViewById(R.id.tv_total);
        etTotal = (MyFontEdittextView) view.findViewById(R.id.et_total);
        tvTriglycerides = (MyFontTextView) view.findViewById(R.id.tv_triglycerides);
        etTriglycerides = (MyFontEdittextView) view.findViewById(R.id.et_triglycerides);
    }

}
