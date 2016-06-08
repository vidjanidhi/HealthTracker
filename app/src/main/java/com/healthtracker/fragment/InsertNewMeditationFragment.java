package com.healthtracker.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.healthtracker.R;
import com.healthtracker.component.MyFontEdittextView;
import com.healthtracker.helper.DataBaseHelper;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.util.AppConstant;

/**
 * Created by local nidhi on 19-05-2016.
 */
public class InsertNewMeditationFragment extends Fragment {
    Activity activity;
    DataBaseHelper dbhelper;
    PreferenceHelper phelper;
    int fragment_id = 0;

    private MyFontEdittextView etInsertMedName;
    private Spinner spinnerInsertMedUnit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this.getActivity();
        Bundle bundle = getArguments();
        fragment_id = bundle.getInt(AppConstant.FRAGMENT_ID);
        dbhelper = new DataBaseHelper(activity);
        phelper = new PreferenceHelper(activity);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_insert_med, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etInsertMedName = (MyFontEdittextView) view.findViewById(R.id.et_insert_med_name);
        spinnerInsertMedUnit = (Spinner) view.findViewById(R.id.spinner_insert_med_unit);

    }
}
