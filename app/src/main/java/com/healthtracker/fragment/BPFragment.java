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
import com.healthtracker.model.BloodPresure;
import com.healthtracker.util.AppConstant;

public class BPFragment extends Fragment {

    private MyFontTextView tvSystolic;
    private MyFontEdittextView etSystolic;
    private MyFontTextView tvDiastolic;
    private MyFontEdittextView etDiastolic;
    private MyFontTextView tvHeartRate;
    private MyFontEdittextView etHeartRate;
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
            BloodPresure bloodPresure = MainActivity.bloodPresureArrayList.get(position);
            etSystolic.setText(bloodPresure.getSystolic() + "");
            etDiastolic.setText(bloodPresure.getDiastolic() + "");
            etHeartRate.setText(bloodPresure.getHeartrate() + "");
            AddDetailActivity.tvTime.setText(bloodPresure.getTime());
            AddDetailActivity.tvDate.setText(bloodPresure.getDate());
            AddDetailActivity.etNote.setText(bloodPresure.getNote());
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
