package com.healthtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.healthtracker.component.MyFontButton;
import com.healthtracker.component.MyFontEdittextView;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.model.BloodPresure;
import com.healthtracker.model.Glucose;
import com.healthtracker.model.Thyroid;
import com.healthtracker.model.Weight;
import com.healthtracker.model.cholesterol;
import com.healthtracker.util.Util;

import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity implements View.OnClickListener {
    MyFontButton btnStatisticsWeek;
    MyFontButton btnStatisticsMonth;
    MyFontButton btnStatisticsSixMonth;
    MyFontButton btnStatisticsCalculate;
    MyFontEdittextView etStatisticsDays;
    MyFontTextView tvStatisticsWeightUnit;
    MyFontTextView tvStatisticsWeight;
    MyFontTextView tvStatisticsFatUnit;
    MyFontTextView tvStatisticsFat;
    MyFontTextView tvStatisticsAbdomenUnit;
    MyFontTextView tvStatisticsAbdomen;
    MyFontTextView tvStatisticsWaistUnit;
    MyFontTextView tvStatisticsWaist;
    MyFontTextView tvStatisticsHipUnit;
    MyFontTextView tvStatisticsHip;
    MyFontTextView tvStatisticsGlucoseUnit;
    MyFontTextView tvStatisticsGlucose;
    MyFontTextView tvStatisticsHba1cUnit;
    MyFontTextView tvStatisticsHba1c;
    MyFontTextView tvStatisticsSystolicUnit;
    MyFontTextView tvStatisticsSystolic;
    MyFontTextView tvStatisticsDiastolicUnit;
    MyFontTextView tvStatisticsDiastolic;
    MyFontTextView tvStatisticsHeartRateUnit;
    MyFontTextView tvStatisticsHeartRate;
    MyFontTextView tvStatisticsHdlUnit;
    MyFontTextView tvStatisticsHdl;
    MyFontTextView tvStatisticsLdlUnit;
    MyFontTextView tvStatisticsLdl;
    MyFontTextView tvStatisticsTotalUnit;
    MyFontTextView tvStatisticsTotal;
    MyFontTextView tvStatisticsTriglycerideUnit;
    MyFontTextView tvStatisticsTriglyceride;
    MyFontTextView tvStatisticsTshLevelUnit;
    MyFontTextView tvStatisticsTshLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        findviews();
        tvStatisticsHdl.setText("No Data");
        tvStatisticsLdl.setText("No Data");
        tvStatisticsTotal.setText("No Data");
        tvStatisticsTriglyceride.setText("No Data");

        tvStatisticsSystolic.setText("No Data");
        tvStatisticsDiastolic.setText("No Data");
        tvStatisticsHeartRate.setText("No Data");

        tvStatisticsGlucose.setText("No Data");
        tvStatisticsHba1c.setText("No Data");


        tvStatisticsTshLevel.setText("No Data");

        tvStatisticsAbdomen.setText("No Data");
        tvStatisticsFat.setText("No Data");
        tvStatisticsWeight.setText("No Data");
        tvStatisticsWaist.setText("No Data");
        tvStatisticsHip.setText("No Data");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_statistics_week:

                updateUI(7);
                break;
            case R.id.btn_statistics_month:
                updateUI(30);

                break;
            case R.id.btn_statistics_six_month:

                updateUI(180);
                break;
            case R.id.btn_statistics_calculate:
                int days = Integer.parseInt(etStatisticsDays.getText().toString());
                if (days > 0) {
                    updateUI(days);
                } else {
                    Toast.makeText(this, "enter valid days", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    public void updateUI(int days) {
        getSelectedDayWeight(days);
        getSelectedDayGlucose(days);
        getSelectedDayBP(days);
        getSelectedDayCholesterol(days);
        getSelectedDayThyroid(days);

    }

    void getSelectedDayThyroid(int days) {
        ArrayList<Thyroid> thyroidArrayList = new ArrayList<Thyroid>();
        for (int i = 0; i < MainActivity.thyroidArrayList.size(); i++) {
            String date = MainActivity.thyroidArrayList.get(i).getDate();
            if (!TextUtils.isEmpty(date)) {
                long difference = Util.getDaysDifference(date);
                if (difference < days) {
                    thyroidArrayList.add(MainActivity.thyroidArrayList.get(i));
                }
            }
        }
        if (thyroidArrayList.size() > 0) {
            Thyroid thyroid = getAverageThyroid(thyroidArrayList);
            tvStatisticsTshLevel.setText(thyroid.getTshLevel() + "");


        } else {
            tvStatisticsTshLevel.setText("No Data");


        }
    }

    private Thyroid getAverageThyroid(ArrayList<Thyroid> thyroidArrayList) {
        Thyroid thyroid = new Thyroid();
        float tshLevel = 0;
        int size = thyroidArrayList.size();
        for (int i = 0; i < size; i++) {
            Thyroid w = thyroidArrayList.get(i);
            tshLevel += w.getTshLevel();

        }
        thyroid.setTshLevel(tshLevel / size);

        return thyroid;

    }

    void getSelectedDayCholesterol(int days) {
        ArrayList<cholesterol> cholesterolArrayList = new ArrayList<cholesterol>();
        for (int i = 0; i < MainActivity.cholesterolArrayList.size(); i++) {
            String date = MainActivity.cholesterolArrayList.get(i).getDate();
            if (!TextUtils.isEmpty(date)) {
                long difference = Util.getDaysDifference(date);
                if (difference < days) {
                    cholesterolArrayList.add(MainActivity.cholesterolArrayList.get(i));
                }
            }
        }
        if (cholesterolArrayList.size() > 0) {
            cholesterol cholesterol = getAveragecolestrol(cholesterolArrayList);
            tvStatisticsHdl.setText(cholesterol.getHdl() + "");
            tvStatisticsLdl.setText(cholesterol.getLdl() + "");
            tvStatisticsTotal.setText(cholesterol.getTotal() + "");
            tvStatisticsTriglyceride.setText(cholesterol.getTriglyceride() + "");


        } else {
            tvStatisticsHdl.setText("No Data");
            tvStatisticsLdl.setText("No Data");
            tvStatisticsTotal.setText("No Data");
            tvStatisticsTriglyceride.setText("No Data");

        }
    }

    cholesterol getAveragecolestrol(ArrayList<cholesterol> cholesterolArrayList) {

        cholesterol cholesterol = new cholesterol();
        float hdl = 0, ldl = 0, total = 0, triglyceride = 0;
        int size = cholesterolArrayList.size();
        for (int i = 0; i < size; i++) {
            cholesterol w = cholesterolArrayList.get(i);
            hdl += w.getHdl();
            ldl += w.getLdl();
            total += w.getTotal();
            triglyceride += w.getTriglyceride();
        }
        cholesterol.setHdl(hdl / size);
        cholesterol.setLdl(ldl / size);
        cholesterol.setTotal(total / size);
        cholesterol.setTriglyceride(triglyceride / size);
        return cholesterol;

    }

    void getSelectedDayBP(int days) {
        ArrayList<BloodPresure> bloodPresureArrayList = new ArrayList<BloodPresure>();
        for (int i = 0; i < MainActivity.bloodPresureArrayList.size(); i++) {
            String date = MainActivity.bloodPresureArrayList.get(i).getDate();
            if (!TextUtils.isEmpty(date)) {
                long difference = Util.getDaysDifference(date);
                if (difference < days) {
                    bloodPresureArrayList.add(MainActivity.bloodPresureArrayList.get(i));
                }
            }
        }
        if (bloodPresureArrayList.size() > 0) {
            BloodPresure bloodPresure = getAverageBP(bloodPresureArrayList);
            tvStatisticsSystolic.setText(bloodPresure.getSystolic() + "");
            tvStatisticsDiastolic.setText(bloodPresure.getDiastolic() + "");
            tvStatisticsHeartRate.setText(bloodPresure.getHeartrate() + "");


        } else {
            tvStatisticsSystolic.setText("No Data");
            tvStatisticsDiastolic.setText("No Data");
            tvStatisticsHeartRate.setText("No Data");

        }
    }

    BloodPresure getAverageBP(ArrayList<BloodPresure> bloodPresureArrayList) {
        BloodPresure bloodPresure = new BloodPresure();
        float systolic = 0, diastolic = 0, hrate = 0;
        int size = bloodPresureArrayList.size();
        for (int i = 0; i < size; i++) {
            BloodPresure w = bloodPresureArrayList.get(i);
            systolic += w.getSystolic();
            diastolic += w.getDiastolic();
            hrate += w.getHeartrate();
        }
        bloodPresure.setSystolic((int) systolic / size);
        bloodPresure.setDiastolic((int) diastolic / size);
        bloodPresure.setHeartrate((int) hrate / size);
        return bloodPresure;


    }

    void getSelectedDayGlucose(int days) {
        ArrayList<Glucose> glucoseArrayList = new ArrayList<Glucose>();
        for (int i = 0; i < MainActivity.glucoseArrayList.size(); i++) {
            String date = MainActivity.glucoseArrayList.get(i).getDate();
            if (!TextUtils.isEmpty(date)) {
                long difference = Util.getDaysDifference(date);
                if (difference < days) {
                    glucoseArrayList.add(MainActivity.glucoseArrayList.get(i));
                }
            }
        }
        if (glucoseArrayList.size() > 0) {
            Glucose glucose = getAverageGlucose(glucoseArrayList);
            tvStatisticsGlucose.setText(glucose.getGlucose() + "");
            tvStatisticsHba1c.setText(glucose.getHba1c() + "");

        } else {
            tvStatisticsGlucose.setText("No Data");
            tvStatisticsHba1c.setText("No Data");

        }

    }

    Glucose getAverageGlucose(ArrayList<Glucose> glucoseArrayList) {
        Glucose glucose = new Glucose();
        float glucose1 = 0;
        float hba1c = 0;
        int size = glucoseArrayList.size();
        for (int i = 0; i < size; i++) {
            Glucose w = glucoseArrayList.get(i);
            glucose1 += w.getGlucose();
            hba1c += w.getHba1c();
        }
        glucose.setGlucose(glucose1 / size);
        glucose.setHba1c(hba1c / size);
        return glucose;
    }

    public void getSelectedDayWeight(int days) {

        ArrayList<Weight> weightArrayList = new ArrayList<Weight>();

        for (int i = 0; i < MainActivity.WeightList.size(); i++) {
            String date = MainActivity.WeightList.get(i).getDate();
            if (!TextUtils.isEmpty(date)) {
                long difference = Util.getDaysDifference(date);
                if (difference < days) {
                    weightArrayList.add(MainActivity.WeightList.get(i));

                }
            }
        }
        if (weightArrayList.size() > 0) {
            Weight weight = getAverageWeight(weightArrayList);
            tvStatisticsAbdomen.setText(weight.getAbdomen() + "");
            tvStatisticsFat.setText(weight.getFat() + "");
            tvStatisticsWeight.setText(weight.getWeight() + "");
            tvStatisticsWaist.setText(weight.getWaist() + "");
            tvStatisticsHip.setText(weight.getHips() + "");
        } else {
            tvStatisticsAbdomen.setText("No Data");
            tvStatisticsFat.setText("No Data");
            tvStatisticsWeight.setText("No Data");
            tvStatisticsWaist.setText("No Data");
            tvStatisticsHip.setText("No Data");
        }
    }

    public Weight getAverageWeight(ArrayList<Weight> weightArrayList) {
        Weight weight = new Weight();
        float weight1 = 0;
        float fat = 0;
        float abdomen = 0;
        float waist = 0;
        float hip = 0;
        int size = weightArrayList.size();
        Log.i("size of araylist", size + "");
        for (int i = 0; i < weightArrayList.size(); i++) {
            Weight w = weightArrayList.get(i);
            weight1 += w.getWeight();
            fat += w.getFat();
            abdomen += w.getAbdomen();
            waist += w.getWaist();
            hip += w.getHips();
        }
        weight.setWeight(weight1 / size);
        weight.setFat((int) (fat / size));
        weight.setAbdomen(abdomen / size);
        weight.setWaist(waist / size);
        weight.setHips(hip / size);
        return weight;

    }

    private void findviews() {

        btnStatisticsWeek = (MyFontButton) findViewById(R.id.btn_statistics_week);
        btnStatisticsMonth = (MyFontButton) findViewById(R.id.btn_statistics_month);
        btnStatisticsSixMonth = (MyFontButton) findViewById(R.id.btn_statistics_six_month);
        btnStatisticsCalculate = (MyFontButton) findViewById(R.id.btn_statistics_calculate);
        etStatisticsDays = (MyFontEdittextView) findViewById(R.id.et_statistics_days);
        tvStatisticsWeightUnit = (MyFontTextView) findViewById(R.id.tv_statistics_weight_unit);
        tvStatisticsWeight = (MyFontTextView) findViewById(R.id.tv_statistics_weight);
        tvStatisticsFatUnit = (MyFontTextView) findViewById(R.id.tv_statistics_fat_unit);
        tvStatisticsFat = (MyFontTextView) findViewById(R.id.tv_statistics_fat);
        tvStatisticsAbdomenUnit = (MyFontTextView) findViewById(R.id.tv_statistics_abdomen_unit);
        tvStatisticsAbdomen = (MyFontTextView) findViewById(R.id.tv_statistics_abdomen);
        tvStatisticsWaistUnit = (MyFontTextView) findViewById(R.id.tv_statistics_waist_unit);
        tvStatisticsWaist = (MyFontTextView) findViewById(R.id.tv_statistics_waist);
        tvStatisticsHipUnit = (MyFontTextView) findViewById(R.id.tv_statistics_hip_unit);
        tvStatisticsHip = (MyFontTextView) findViewById(R.id.tv_statistics_hip);
        tvStatisticsGlucoseUnit = (MyFontTextView) findViewById(R.id.tv_statistics_glucose_unit);
        tvStatisticsGlucose = (MyFontTextView) findViewById(R.id.tv_statistics_glucose);
        tvStatisticsHba1cUnit = (MyFontTextView) findViewById(R.id.tv_statistics_hba1c_unit);
        tvStatisticsHba1c = (MyFontTextView) findViewById(R.id.tv_statistics_hba1c);
        tvStatisticsSystolicUnit = (MyFontTextView) findViewById(R.id.tv_statistics_systolic_unit);
        tvStatisticsSystolic = (MyFontTextView) findViewById(R.id.tv_statistics_systolic);
        tvStatisticsDiastolicUnit = (MyFontTextView) findViewById(R.id.tv_statistics_diastolic_unit);
        tvStatisticsDiastolic = (MyFontTextView) findViewById(R.id.tv_statistics_diastolic);
        tvStatisticsHeartRateUnit = (MyFontTextView) findViewById(R.id.tv_statistics_heart_rate_unit);
        tvStatisticsHeartRate = (MyFontTextView) findViewById(R.id.tv_statistics_heart_rate);
        tvStatisticsHdlUnit = (MyFontTextView) findViewById(R.id.tv_statistics_hdl_unit);
        tvStatisticsHdl = (MyFontTextView) findViewById(R.id.tv_statistics_hdl);
        tvStatisticsLdlUnit = (MyFontTextView) findViewById(R.id.tv_statistics_ldl_unit);
        tvStatisticsLdl = (MyFontTextView) findViewById(R.id.tv_statistics_ldl);
        tvStatisticsTotalUnit = (MyFontTextView) findViewById(R.id.tv_statistics_total_unit);
        tvStatisticsTotal = (MyFontTextView) findViewById(R.id.tv_statistics_total);
        tvStatisticsTriglycerideUnit = (MyFontTextView) findViewById(R.id.tv_statistics_triglyceride_unit);
        tvStatisticsTriglyceride = (MyFontTextView) findViewById(R.id.tv_statistics_triglyceride);
        tvStatisticsTshLevelUnit = (MyFontTextView) findViewById(R.id.tv_statistics_tsh_level_unit);
        tvStatisticsTshLevel = (MyFontTextView) findViewById(R.id.tv_statistics_tsh_level);
        btnStatisticsWeek.setOnClickListener(this);
        btnStatisticsMonth.setOnClickListener(this);
        btnStatisticsSixMonth.setOnClickListener(this);
        btnStatisticsCalculate.setOnClickListener(this);
    }
}
