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
import com.healthtracker.model.Weight;
import com.healthtracker.util.AppConstant;

public class WeightFragment extends Fragment {

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
    PreferenceHelper phelper = new PreferenceHelper(getContext());
    int position;
    boolean is_edit = false;

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

    @Override
    public void onResume() {
        super.onResume();
        setTextviews();
    }

    private void setTextviews() {

        if (phelper.getInteger(AppConstant.WEIGHT_SELECTED) == AppConstant.WEIGHT_SELECTED_UNIT_KG) {
            tvWeight.setText("Weight(kg)");
        } else {
            tvWeight.setText("Weight(lbs)");
        }
        if (phelper.getInteger(AppConstant.LENGTH_SELECTED) == AppConstant.LENGTH_SELECTED_UNIT_CM) {
            tvAbdomen.setText("Abdomen(cm)");
            tvWaist.setText("Waist(cm)");
            tvHip.setText("Hip(cm)");
        } else {
            tvAbdomen.setText("Abdomen(inch)");
            tvWaist.setText("Waist(inch)");
            tvHip.setText("Hip(inch)");
        }

        if (is_edit) {
            Weight weight = MainActivity.WeightList.get(position);
            etWeight.setText(weight.getWeight() + "");
            etWaist.setText(weight.getWaist() + "");
            etAbdomen.setText(weight.getAbdomen() + "");
            etFat.setText(weight.getFat() + "");
            etHip.setText(weight.getHips() + "");
            AddDetailActivity.etNote.setText(weight.getNote());
            AddDetailActivity.tvDate.setText(weight.getDate());
            AddDetailActivity.tvTime.setText(weight.getTime());
        }
    }
}
