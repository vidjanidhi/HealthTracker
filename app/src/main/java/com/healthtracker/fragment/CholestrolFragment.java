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
import com.healthtracker.model.cholesterol;
import com.healthtracker.util.AppConstant;

public class CholestrolFragment extends Fragment {

    private MyFontTextView tvHdl;
    private MyFontEdittextView etHdl;
    private MyFontTextView tvLdl;
    private MyFontEdittextView etLdl;
    private MyFontTextView tvTotal;
    private MyFontEdittextView etTotal;
    private MyFontTextView tvTriglycerides;
    private MyFontEdittextView etTriglycerides;
    PreferenceHelper phelper = new PreferenceHelper(getContext());
    int position;
    boolean is_edit = false;

    @Override
    public void onResume() {
        super.onResume();
        setTextviews();

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

    private void setTextviews() {
        if (is_edit) {
            cholesterol cholesterol = MainActivity.cholesterolArrayList.get(position);
            etHdl.setText(cholesterol.getHdl() + "");
            etLdl.setText(cholesterol.getLdl() + "");
            etTotal.setText(cholesterol.getTotal() + "");
            etTriglycerides.setText(cholesterol.getTriglyceride() + "");
            AddDetailActivity.tvTime.setText(cholesterol.getTime());
            AddDetailActivity.tvDate.setText(cholesterol.getDate());
            AddDetailActivity.etNote.setText(cholesterol.getNote());
        }
    }

}
