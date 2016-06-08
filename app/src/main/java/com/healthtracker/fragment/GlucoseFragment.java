package com.healthtracker.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.healthtracker.AddDetailActivity;
import com.healthtracker.MainActivity;
import com.healthtracker.R;
import com.healthtracker.component.MyFontEdittextView;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.model.Glucose;
import com.healthtracker.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class GlucoseFragment extends Fragment {

    Spinner spinner;
    MyFontTextView tvGlucose;
    public static MyFontEdittextView etGlucose;
    MyFontTextView tvHba1c;
    public static MyFontEdittextView etHba1c;
    PreferenceHelper phelper = new PreferenceHelper(getContext());
    int position;
    boolean is_edit = false;
    ArrayAdapter<String> dataAdapter;


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
        return inflater.inflate(R.layout.glucose_fragment, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTextviews();
    }

    private void setTextviews() {
        if (is_edit) {
            Glucose glucose = MainActivity.glucoseArrayList.get(position);
            etGlucose.setText(glucose.getGlucose() + "");
            etHba1c.setText(glucose.getHba1c() + "");
            AddDetailActivity.tvTime.setText(glucose.getTime());
            AddDetailActivity.tvDate.setText(glucose.getDate());
            AddDetailActivity.etNote.setText(glucose.getNote());

            // find selected value from spinner
            String compareValue = glucose.getTestingTime();
            if (!compareValue.equals(null)) {
                int spinnerPosition = dataAdapter.getPosition(compareValue);
                spinner.setSelection(spinnerPosition);
            }

        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner = (Spinner) view.findViewById(R.id.spinner_glucose);
        tvGlucose = (MyFontTextView) view.findViewById(R.id.tv_glucose);
        etGlucose = (MyFontEdittextView) view.findViewById(R.id.et_glucose);
        tvHba1c = (MyFontTextView) view.findViewById(R.id.tv_hba1c);
        etHba1c = (MyFontEdittextView) view.findViewById(R.id.et_hba1c);
        List<String> ary = new ArrayList<String>();
        ary.add("unscheduled");
        ary.add("Pre Breakfast");
        ary.add("Post");
        ary.add("Pre Lunch");
        ary.add("Post Lunch");
        ary.add("Pre Dinner");
        ary.add("Post Dinner");
        ary.add("Pre Exercise");
        ary.add("Post Exercise");
        ary.add("Snack");
        ary.add("sick");
        ary.add("Bedtime");
        ary.add("Low BG");
        dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ary);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new ItemSelectedListener());

    }

    public class ItemSelectedListener implements AdapterView.OnItemSelectedListener {
        //get strings of first item
        String firstItem = String.valueOf(spinner.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            Toast.makeText(parent.getContext(),
                    "You have selected : " + parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_LONG).show();
            // Todo when item is selected by the user
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {
            Toast.makeText(arg.getContext(), "please select any user", Toast.LENGTH_LONG).show();
        }
    }


}
