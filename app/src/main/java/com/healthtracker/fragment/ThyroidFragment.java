package com.healthtracker.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.healthtracker.AddDetailActivity;
import com.healthtracker.MainActivity;
import com.healthtracker.R;
import com.healthtracker.component.MyFontEdittextView;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.model.Thyroid;
import com.healthtracker.util.AppConstant;

public class ThyroidFragment extends Fragment {

    private MyFontTextView tvTshLevel;
    private MyFontEdittextView etTshLevel;
    PreferenceHelper phelper = new PreferenceHelper(getContext());
    int position;
    boolean is_edit = false;

    @Override
    public void onResume() {
        super.onResume();
        setTextviews();

    }

    private void setTextviews() {
        if (is_edit) {
            Thyroid thyroid = MainActivity.thyroidArrayList.get(position);
            etTshLevel.setText(thyroid.getTshLevel() + "");
            AddDetailActivity.tvTime.setText(thyroid.getTime());
            AddDetailActivity.tvDate.setText(thyroid.getDate());
            AddDetailActivity.etNote.setText(thyroid.getNote());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phelper = new PreferenceHelper(getActivity());
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            position = bundle.getInt(AppConstant.POSITION);
            is_edit = true;
        }
    }

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
