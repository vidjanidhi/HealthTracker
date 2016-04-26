package com.healthtracker.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;

import com.healthtracker.R;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.component.MyFontEdittextView;

public class ThyroidFragment extends Fragment  {

    private MyFontTextView tvTshLevel;
    private MyFontEdittextView etTshLevel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.thyroid_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTshLevel = (MyFontTextView) view.findViewById(R.id.tv_tsh_level);
        etTshLevel = (MyFontEdittextView) view.findViewById(R.id.et_tsh_level);
    }

}
