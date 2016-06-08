package com.healthtracker.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.healthtracker.AddDetailActivity;
import com.healthtracker.MainActivity;
import com.healthtracker.R;
import com.healthtracker.adapter.BPAdapter;
import com.healthtracker.adapter.CholestrolAdapter;
import com.healthtracker.adapter.GlucoseListItemAdapter;
import com.healthtracker.adapter.ThyroidAdapter;
import com.healthtracker.adapter.WeightAdapter;
import com.healthtracker.component.MyFontButton;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.helper.DataBaseHelper;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.model.BloodPresure;
import com.healthtracker.model.Glucose;
import com.healthtracker.model.Thyroid;
import com.healthtracker.model.Weight;
import com.healthtracker.model.cholesterol;
import com.healthtracker.util.AppConstant;

public class WeightDairyFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    ListView lstWeight;
    Activity activity;
    DataBaseHelper dbhelper;
    PreferenceHelper phelper;
    int fragment_id = 0;
    MyFontTextView fragmentTitle;
    public static WeightAdapter weightAdapter;
    public static GlucoseListItemAdapter glucoseListItemAdapter;
    public static BPAdapter bpAdapter;
    public static CholestrolAdapter cholestrolAdapter;
    public static ThyroidAdapter thyroidAdapter;


    public WeightDairyFragment() {
        super();
    }


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
        return inflater.inflate(R.layout.weight_dairy_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lstWeight = (ListView) view.findViewById(R.id.lst_weight);
        fragmentTitle = (MyFontTextView) view.findViewById(R.id.tv_fragment_title);
        Log.i("customer id", phelper.getInteger(AppConstant.USER_ID) + "");

        switch (fragment_id) {
            case AppConstant.WEIGHT_FRAGMENT:
                fragmentTitle.setText("Weight,Fat,Abdomen,Waist,Hip");
                if (MainActivity.WeightList.size() > 0) {
                    weightAdapter = new WeightAdapter(activity, MainActivity.WeightList);
                    lstWeight.setAdapter(weightAdapter);
                }

                break;
            case AppConstant.GLUCOSE_FRAGMENT:
                fragmentTitle.setText("Glucose - type  / HbA1c");
                glucoseListItemAdapter = new GlucoseListItemAdapter(activity, MainActivity.glucoseArrayList);
                if (MainActivity.glucoseArrayList.size() > 0) {
                    lstWeight.setAdapter(glucoseListItemAdapter);
                }
                break;
            case AppConstant.B_P_FRAGMENT:
                fragmentTitle.setText("Systolic / diastolic , Heart Rate");
                bpAdapter = new BPAdapter(activity, MainActivity.bloodPresureArrayList);
                if (MainActivity.bloodPresureArrayList.size() > 0) {
                    lstWeight.setAdapter(bpAdapter);
                }
                break;
            case AppConstant.CHOLESTROL_FRAGMENT:
                fragmentTitle.setText("HDL , LDL , Total , Triglycerides");
                cholestrolAdapter = new CholestrolAdapter(activity, MainActivity.cholesterolArrayList);
                if (MainActivity.cholesterolArrayList.size() > 0) {
                    lstWeight.setAdapter(cholestrolAdapter);
                }
                break;
            case AppConstant.THYROID_FRAGMENT:
                fragmentTitle.setText("TshLevel");
                thyroidAdapter = new ThyroidAdapter(activity, MainActivity.thyroidArrayList);
                if (MainActivity.thyroidArrayList.size() > 0) {
                    lstWeight.setAdapter(thyroidAdapter);
                }
                break;
        }

        lstWeight.setOnItemClickListener(this);
        lstWeight.setOnItemLongClickListener(this);


    }

    @Override
    public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {
        final Dialog dialog = new Dialog(parent.getContext());

        switch (fragment_id) {
            case AppConstant.WEIGHT_FRAGMENT:
                dialog.setContentView(R.layout.dialog_diary_weight);
                setDialogDimention(dialog);
                showWeightDialog(dialog, parent.getContext(), position);

                break;
            case AppConstant.GLUCOSE_FRAGMENT:
                dialog.setContentView(R.layout.dialog_diary_glucose);
                setDialogDimention(dialog);
                showGlucoseDialog(dialog, parent.getContext(), position);
                break;
            case AppConstant.B_P_FRAGMENT:
                dialog.setContentView(R.layout.dialog_diary_bp);
                setDialogDimention(dialog);
                showBPDialog(dialog, parent.getContext(), position);
                break;
            case AppConstant.CHOLESTROL_FRAGMENT:
                dialog.setContentView(R.layout.dialog_diary_cholesterol);
                setDialogDimention(dialog);
                showCholesterolDialog(dialog, parent.getContext(), position);
                break;
            case AppConstant.THYROID_FRAGMENT:
                dialog.setContentView(R.layout.dialog_diary_thyroid);
                setDialogDimention(dialog);
                showThyroidDialog(dialog, parent.getContext(), position);
                break;
        }
    }

    @Override
    public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
        final Dialog dialog = new Dialog(parent.getContext());
        dialog.setContentView(R.layout.long_click_menu);
        MyFontButton btnEdit;
        MyFontButton btnDelete;
        btnEdit = (MyFontButton) dialog.findViewById(R.id.btn_edit);
        btnDelete = (MyFontButton) dialog.findViewById(R.id.btn_delete);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), AddDetailActivity.class);
                switch (fragment_id) {
                    case AppConstant.WEIGHT_FRAGMENT:
                        intent.putExtra(AppConstant.FRAGMENT_ID, AppConstant.WEIGHT_FRAGMENT);
                        break;
                    case AppConstant.GLUCOSE_FRAGMENT:
                        intent.putExtra(AppConstant.FRAGMENT_ID, AppConstant.GLUCOSE_FRAGMENT);
                        break;
                    case AppConstant.B_P_FRAGMENT:
                        intent.putExtra(AppConstant.FRAGMENT_ID, AppConstant.B_P_FRAGMENT);
                        break;
                    case AppConstant.CHOLESTROL_FRAGMENT:
                        intent.putExtra(AppConstant.FRAGMENT_ID, AppConstant.CHOLESTROL_FRAGMENT);
                        break;
                    case AppConstant.THYROID_FRAGMENT:
                        intent.putExtra(AppConstant.FRAGMENT_ID, AppConstant.THYROID_FRAGMENT);
                        break;
                }
                intent.putExtra(AppConstant.IS_EDIT, true);
                intent.putExtra(AppConstant.POSITION, position);
                startActivity(intent);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (fragment_id) {
                    case AppConstant.WEIGHT_FRAGMENT:
                        dbhelper.deleteWeight(MainActivity.WeightList.get(position).getRowId());
                        MainActivity.WeightList.remove(position);
                        weightAdapter.notifyDataSetChanged();
                        dialog.cancel();
                        break;
                    case AppConstant.GLUCOSE_FRAGMENT:
                        dbhelper.deleteGlucose(MainActivity.glucoseArrayList.get(position).getRowId());
                        MainActivity.WeightList.remove(position);

                        glucoseListItemAdapter.notifyDataSetChanged();
                        dialog.cancel();

                        break;
                    case AppConstant.B_P_FRAGMENT:
                        dbhelper.deleteBP(MainActivity.bloodPresureArrayList.get(position).getRowId());
                        MainActivity.WeightList.remove(position);

                        bpAdapter.notifyDataSetChanged();
                        dialog.cancel();

                        break;
                    case AppConstant.CHOLESTROL_FRAGMENT:
                        dbhelper.deleteCholesterol(MainActivity.cholesterolArrayList.get(position).getRowId());
                        MainActivity.WeightList.remove(position);

                        cholestrolAdapter.notifyDataSetChanged();
                        dialog.cancel();

                        break;
                    case AppConstant.THYROID_FRAGMENT:
                        dbhelper.deleteThyroid(MainActivity.thyroidArrayList.get(position).getRowId());
                        MainActivity.WeightList.remove(position);

                        thyroidAdapter.notifyDataSetChanged();
                        dialog.cancel();

                        break;
                }
            }
        });
        dialog.show();
        return true;
    }

    void showThyroidDialog(final Dialog dialog, final Context context, final int position) {

        MyFontTextView tvDialogGlucoseDate;
        MyFontTextView tvDialogGlucoseTime;
        MyFontTextView tvDialogThyroidTshlevel;
        MyFontTextView tvDialogWeightNote;
        MyFontButton btnDialogCancel;
        MyFontButton btnDialogEdit;
        tvDialogGlucoseDate = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_glucose_date);
        tvDialogGlucoseTime = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_glucose_time);
        tvDialogThyroidTshlevel = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_thyroid_tshlevel);
        tvDialogWeightNote = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_weight_note);
        btnDialogCancel = (MyFontButton) dialog.findViewById(R.id.btn_dialog_caancel);
        btnDialogEdit = (MyFontButton) dialog.findViewById(R.id.btn_dialog_edit);

        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnDialogEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddDetailActivity.class);
                intent.putExtra(AppConstant.FRAGMENT_ID, AppConstant.THYROID_FRAGMENT);
                intent.putExtra(AppConstant.IS_EDIT, true);
                intent.putExtra(AppConstant.POSITION, position);
                startActivity(intent);
            }
        });
        Thyroid thyroid = MainActivity.thyroidArrayList.get(position);
        tvDialogGlucoseDate.setText(thyroid.getDate());
        tvDialogGlucoseTime.setText(thyroid.getTime());
        tvDialogWeightNote.setText(thyroid.getNote());
        tvDialogThyroidTshlevel.setText(thyroid.getTshLevel() + "");
        dialog.show();
    }

    void showCholesterolDialog(final Dialog dialog, final Context context, final int position) {
        MyFontTextView tvDialogBpDate;
        MyFontTextView tvDialogBpTime;
        MyFontTextView tvDialogCHdl;
        MyFontTextView tvDialogCLdl;
        MyFontTextView tvDialogCTotal;
        MyFontTextView tvDialogCTriglyceride;
        MyFontTextView tvDialogWeightNote;
        MyFontButton btnDialogCancel;
        MyFontButton btnDialogEdit;
        tvDialogBpDate = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_bp_date);
        tvDialogBpTime = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_bp_time);
        tvDialogCHdl = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_c_hdl);
        tvDialogCLdl = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_c_ldl);
        tvDialogCTotal = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_c_total);
        tvDialogCTriglyceride = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_c_triglyceride);
        tvDialogWeightNote = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_weight_note);
        btnDialogCancel = (MyFontButton) dialog.findViewById(R.id.btn_dialog_caancel);
        btnDialogEdit = (MyFontButton) dialog.findViewById(R.id.btn_dialog_edit);
        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnDialogEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddDetailActivity.class);
                intent.putExtra(AppConstant.FRAGMENT_ID, AppConstant.CHOLESTROL_FRAGMENT);
                intent.putExtra(AppConstant.IS_EDIT, true);
                intent.putExtra(AppConstant.POSITION, position);

                startActivity(intent);
            }
        });
        cholesterol cholesterol = MainActivity.cholesterolArrayList.get(position);
        tvDialogBpDate.setText(cholesterol.getDate());
        tvDialogBpTime.setText(cholesterol.getTime());
        tvDialogWeightNote.setText(cholesterol.getNote());
        tvDialogCHdl.setText(cholesterol.getHdl() + "");
        tvDialogCLdl.setText(cholesterol.getLdl() + "");
        tvDialogCTotal.setText(cholesterol.getTotal() + "");
        tvDialogCTriglyceride.setText(cholesterol.getTriglyceride() + "");
        dialog.show();


    }

    public static void setDialogDimention(Dialog dialog) {
        ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    void showBPDialog(final Dialog dialog, final Context context, final int position) {
        MyFontTextView tvDialogBpDate;
        MyFontTextView tvDialogBpTime;
        MyFontTextView tvDialogBpSystolic;
        MyFontTextView tvDialogBpDiastolic;
        MyFontTextView tvDialogBpHeartbit;
        MyFontTextView tvDialogBpSPressure;
        MyFontTextView tvDialogBpDPressure;
        MyFontTextView tvDialogWeightNote;
        MyFontButton btnDialogCancel;
        MyFontButton btnDialogEdit;

        tvDialogBpDate = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_bp_date);
        tvDialogBpTime = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_bp_time);
        tvDialogBpSystolic = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_bp_systolic);
        tvDialogBpDiastolic = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_bp_diastolic);
        tvDialogBpHeartbit = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_bp_heartbit);
        tvDialogBpSPressure = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_bp_s_pressure);
        tvDialogBpDPressure = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_bp_d_pressure);
        tvDialogWeightNote = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_weight_note);
        btnDialogCancel = (MyFontButton) dialog.findViewById(R.id.btn_dialog_caancel);
        btnDialogEdit = (MyFontButton) dialog.findViewById(R.id.btn_dialog_edit);
        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnDialogEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddDetailActivity.class);
                intent.putExtra(AppConstant.FRAGMENT_ID, AppConstant.B_P_FRAGMENT);
                intent.putExtra(AppConstant.IS_EDIT, true);
                intent.putExtra(AppConstant.POSITION, position);

                startActivity(intent);
            }
        });

        BloodPresure bloodPresure = MainActivity.bloodPresureArrayList.get(position);
        tvDialogBpDate.setText(bloodPresure.getDate());
        tvDialogBpTime.setText(bloodPresure.getTime());
        tvDialogWeightNote.setText(bloodPresure.getNote());
        tvDialogBpSystolic.setText(bloodPresure.getSystolic() + "");
        tvDialogBpDiastolic.setText(bloodPresure.getDiastolic() + "");
        tvDialogBpHeartbit.setText(bloodPresure.getHeartrate() + "");
        tvDialogBpSPressure.setText("");
        tvDialogBpDPressure.setText("");
        dialog.show();
    }

    void showGlucoseDialog(final Dialog dialog, final Context context, final int position) {

        MyFontTextView tvDialogGlucoseDate;
        MyFontTextView tvDialogGlucoseTime;
        MyFontTextView tvDialogGlucoseGlucose;
        MyFontTextView tvDialogGlucoseType;
        MyFontTextView tvDialogGlucoseHba1c;
        MyFontTextView tvDialogWeightNote;
        MyFontButton btnDialogCancel;
        MyFontButton btnDialogEdit;
        tvDialogGlucoseDate = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_glucose_date);
        tvDialogGlucoseTime = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_glucose_time);
        tvDialogGlucoseGlucose = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_glucose_glucose);
        tvDialogGlucoseType = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_glucose_type);
        tvDialogGlucoseHba1c = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_glucose_hba1c);
        tvDialogWeightNote = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_weight_note);
        btnDialogCancel = (MyFontButton) dialog.findViewById(R.id.btn_dialog_caancel);
        btnDialogEdit = (MyFontButton) dialog.findViewById(R.id.btn_dialog_edit);
        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnDialogEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddDetailActivity.class);
                intent.putExtra(AppConstant.FRAGMENT_ID, AppConstant.GLUCOSE_FRAGMENT);
                intent.putExtra(AppConstant.IS_EDIT, true);
                intent.putExtra(AppConstant.POSITION, position);

                startActivity(intent);
            }
        });
        Glucose glucose = MainActivity.glucoseArrayList.get(position);
        tvDialogGlucoseDate.setText(glucose.getDate());
        tvDialogGlucoseTime.setText(glucose.getTime());
        tvDialogWeightNote.setText(glucose.getNote());
        tvDialogGlucoseGlucose.setText(glucose.getGlucose() + "");
        tvDialogGlucoseType.setText(glucose.getTestingTime());
        tvDialogGlucoseHba1c.setText(glucose.getHba1c() + "");
        dialog.show();

    }

    public void showWeightDialog(final Dialog dialog, final Context context, final int position) {
        MyFontTextView tvDialogWeightDate;
        MyFontTextView tvDialogWeightTime;
        MyFontTextView tvDialogWeightWeight;
        MyFontTextView tvDialogWeightBmi;
        MyFontTextView tvDialogWeightFat;
        MyFontTextView tvDialogWeightAbdomen;
        MyFontTextView tvDialogWeightWaist;
        MyFontTextView tvDialogWeightHip;
        MyFontTextView tvDialogWeightNote;

        MyFontButton btnCancel, btnEdit;
        tvDialogWeightDate = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_weight_date);
        tvDialogWeightTime = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_weight_time);
        tvDialogWeightWeight = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_weight_weight);
        tvDialogWeightBmi = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_weight_bmi);
        tvDialogWeightFat = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_weight_fat);
        tvDialogWeightAbdomen = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_weight_abdomen);
        tvDialogWeightWaist = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_weight_waist);
        tvDialogWeightHip = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_weight_hip);

        tvDialogWeightNote = (MyFontTextView) dialog.findViewById(R.id.tv_dialog_weight_note);
        btnCancel = (MyFontButton) dialog.findViewById(R.id.btn_dialog_caancel);
        btnEdit = (MyFontButton) dialog.findViewById(R.id.btn_dialog_edit);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddDetailActivity.class);
                intent.putExtra(AppConstant.FRAGMENT_ID, AppConstant.WEIGHT_FRAGMENT);
                intent.putExtra(AppConstant.IS_EDIT, true);
                intent.putExtra(AppConstant.POSITION, position);
                startActivity(intent);
            }
        });
        Weight weight = MainActivity.WeightList.get(position);
        tvDialogWeightDate.setText(weight.getDate());
        tvDialogWeightTime.setText(weight.getTime());
        tvDialogWeightWeight.setText(weight.getWeight() + "");
        tvDialogWeightBmi.setText("");
        tvDialogWeightFat.setText(weight.getFat() + "");
        tvDialogWeightAbdomen.setText(weight.getAbdomen() + "");
        tvDialogWeightWaist.setText(weight.getWaist() + "");
        tvDialogWeightHip.setText(weight.getHips() + "");
        tvDialogWeightNote.setText(weight.getNote());
        dialog.show();
    }


}
