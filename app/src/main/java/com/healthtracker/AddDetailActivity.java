package com.healthtracker;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.healthtracker.component.MyFontButton;
import com.healthtracker.component.MyFontEdittextView;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.fragment.BPFragment;
import com.healthtracker.fragment.CholestrolFragment;
import com.healthtracker.fragment.GlucoseFragment;
import com.healthtracker.fragment.ThyroidFragment;
import com.healthtracker.fragment.WeightFragment;
import com.healthtracker.helper.DataBaseHelper;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.model.BloodPresure;
import com.healthtracker.model.Glucose;
import com.healthtracker.model.Thyroid;
import com.healthtracker.model.Weight;
import com.healthtracker.model.cholesterol;
import com.healthtracker.util.AppConstant;
import com.healthtracker.util.UnitHelper;
import com.healthtracker.util.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class AddDetailActivity extends ActionBarBaseActivitiy implements View.OnClickListener, CalendarDatePickerDialogFragment.OnDateSetListener, RadialTimePickerDialogFragment.OnTimeSetListener {

    private static final int RESULT_LOAD_IMAGE = 1;
    ScrollView scrollView;
    public static MyFontTextView tvDate;
    public static MyFontTextView tvTime;
    LinearLayout llFragment;
    ImageView imgUploaded;
    MyFontButton btnUpload;
    public static MyFontEdittextView etNote;
    MyFontButton btnSave;
    MyFontButton btnCancel;
    int fragment_id, user_id;
    View view;
    boolean is_Edit;
    String date;
    String picturePath;

    DataBaseHelper dbhelper;
    PreferenceHelper phelper;
    int position;


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_detail);
        findviews();
        dbhelper = new DataBaseHelper(this);
        phelper = new PreferenceHelper(this);


        date = Util.getToday(phelper.getInteger(AppConstant.DATE_SELECTED));
        Log.i("today date", date);
        tvDate.setText(date);
        tvTime.setText(java.text.DateFormat.getTimeInstance().format(Calendar.getInstance().getTime()));

        if (getIntent().getExtras() != null) {
            fragment_id = getIntent().getIntExtra(AppConstant.FRAGMENT_ID, 1);
            is_Edit = getIntent().getBooleanExtra(AppConstant.IS_EDIT, false);
            user_id = getIntent().getIntExtra(AppConstant.USER_ID, 1);
            if (is_Edit) {
                position = getIntent().getIntExtra(AppConstant.POSITION, 1);
                Log.i("fragment id", fragment_id + "" + position + is_Edit);
            }

        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment f = null;
        Bundle bundle = new Bundle();
        switch (fragment_id) {
            case AppConstant.WEIGHT_FRAGMENT:
                setTitle("Weight" + "-" + dbhelper.getUser(phelper.getInteger(AppConstant.USER_ID)).getUserName());
                f = new WeightFragment();
                break;

            case AppConstant.GLUCOSE_FRAGMENT:
                setTitle("Glucose" + "-" + dbhelper.getUser(phelper.getInteger(AppConstant.USER_ID)).getUserName());
                f = new GlucoseFragment();
                break;
            case AppConstant.B_P_FRAGMENT:
                setTitle("Blood Presure" + "-" + dbhelper.getUser(phelper.getInteger(AppConstant.USER_ID)).getUserName());
                f = new BPFragment();
                break;
            case AppConstant.CHOLESTROL_FRAGMENT:
                setTitle("Cholesterol" + "-" + dbhelper.getUser(phelper.getInteger(AppConstant.USER_ID)).getUserName());
                f = new CholestrolFragment();
                break;
            case AppConstant.THYROID_FRAGMENT:
                setTitle("Thyroid" + "-" + dbhelper.getUser(phelper.getInteger(AppConstant.USER_ID)).getUserName());
                f = new ThyroidFragment();
                break;

        }
        if (is_Edit) {
            bundle.putInt(AppConstant.POSITION, position);
            f.setArguments(bundle);
        }
        fragmentTransaction.add(R.id.ll_fragment, f, "HELLO");
        fragmentTransaction.commit();


    }

    void findviews() {

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        tvDate = (MyFontTextView) findViewById(R.id.tv_date);
        tvTime = (MyFontTextView) findViewById(R.id.tv_time);
        imgUploaded = (ImageView) findViewById(R.id.img_uploaded);

        llFragment = (LinearLayout) findViewById(R.id.ll_fragment);
        btnUpload = (MyFontButton) findViewById(R.id.btn_upload);
        etNote = (MyFontEdittextView) findViewById(R.id.et_note);
        btnSave = (MyFontButton) findViewById(R.id.btn_save);
        btnCancel = (MyFontButton) findViewById(R.id.btn_cancel);
        btnUpload.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        tvTime.setOnClickListener(this);
        tvDate.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            Log.i("picture path", picturePath);
            cursor.close();
            imgUploaded.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            saveImage(BitmapFactory.decodeFile(picturePath));
//            Bitmap b=BitmapFactory.decodeFile(picturePath);
//            ByteArrayOutputStream bos=new ByteArrayOutputStream();
//            b.compress(Bitmap.CompressFormat.PNG, 100, bos);
//            imageByteArray=bos.toByteArray();
        }
    }

    private void saveImage(Bitmap b) {
        String root = Environment.getExternalStorageDirectory().toString();
        File dir = new File(root + "/HealthTracker");
        dir.mkdir();
        Long ts = System.currentTimeMillis() / 1000;
        String tsname = ts.toString() + ".jpg";
        File file = new File(dir, tsname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            b.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getCurrentFragmentDetail() {
        switch (fragment_id) {
            case AppConstant.WEIGHT_FRAGMENT:
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.ll_fragment);
                view = currentFragment.getView();
                Toast.makeText(this, "fragment id 1", Toast.LENGTH_LONG).show();
                if (view != null) {
//                    Toast.makeText(this, "view not null", Toast.LENGTH_LONG).show();
                    EditText et_weight = (EditText) view.findViewById(R.id.et_weight);
                    EditText et_fat = (EditText) view.findViewById(R.id.et_fat);
                    EditText et_abdomen = (EditText) view.findViewById(R.id.et_abdomen);
                    EditText et_waist = (EditText) view.findViewById(R.id.et_waist);
                    EditText et_hip = (EditText) view.findViewById(R.id.et_hip);

                    String weight = et_weight.getText().toString();
                    String fat = et_fat.getText().toString();
                    String abdomen = et_abdomen.getText().toString();
                    String waist = et_waist.getText().toString();
                    String hips = et_hip.getText().toString();


                    Toast.makeText(this, weight, Toast.LENGTH_LONG).show();
                    Weight weight1 = new Weight();
                    //user id
                    weight1.setUserId(user_id);
                    weight1.setByteArray(picturePath);
                    //weight
                    if (!TextUtils.isEmpty(weight)) {
                        if (phelper.getInteger(AppConstant.WEIGHT_SELECTED) == AppConstant.WEIGHT_SELECTED_UNIT_KG) {
                            weight1.setWeight(Float.parseFloat(weight));
                        } else {
                            weight1.setWeight(UnitHelper.lbsToKgConverter(Float.parseFloat(weight)));
                        }
                    } else {
                        weight1.setWeight(0);
                    }
                    //fat
                    if (!TextUtils.isEmpty(fat))
                        weight1.setFat(Integer.parseInt(fat));
                    else weight1.setFat(0);
                    //abdomen,waist,hips
                    if (phelper.getInteger(AppConstant.LENGTH_SELECTED) == AppConstant.LENGTH_SELECTED_UNIT_INCH) {
                        if (!TextUtils.isEmpty(abdomen))
                            weight1.setAbdomen(UnitHelper.inchToCmConverter(Float.parseFloat(abdomen)));
                        else weight1.setAbdomen(0);
                        if (!TextUtils.isEmpty(waist))
                            weight1.setWaist(UnitHelper.inchToCmConverter(Float.parseFloat(waist)));
                        else weight1.setWaist(0);
                        if (!TextUtils.isEmpty(hips))
                            weight1.setHips(UnitHelper.inchToCmConverter(Float.parseFloat(hips)));
                        else weight1.setHips(0);
                    } else {
                        if (!TextUtils.isEmpty(abdomen))
                            weight1.setAbdomen(Float.parseFloat(abdomen));
                        else weight1.setAbdomen(0);
                        if (!TextUtils.isEmpty(waist))
                            weight1.setWaist(Float.parseFloat(waist));
                        else weight1.setWaist(0);
                        if (!TextUtils.isEmpty(hips))
                            weight1.setHips(Float.parseFloat(hips));
                        else weight1.setHips(0);
                    }
                    weight1.setNote(etNote.getText().toString());
                    weight1.setDate(date);
                    weight1.setTime(tvTime.getText().toString());
                    long result;
                    if (is_Edit) {
                        weight1.setRowId(MainActivity.WeightList.get(position).getRowId());
                        result = dbhelper.updateWeight(weight1);
                    } else {
                        result = dbhelper.addWeight(weight1);
                    }
                    if (result >= 0) {
                        Log.i("insert weight result", result + "");
                        Intent intent1 = new Intent(this, MainActivity.class);
                        startActivity(intent1);
                    }
                }
                break;

            case AppConstant.GLUCOSE_FRAGMENT:
                Fragment currentFragment1 = getSupportFragmentManager().findFragmentById(R.id.ll_fragment);
                view = currentFragment1.getView();
                if (view != null) {
                    Spinner spinner;
                    MyFontEdittextView etGlucose;
                    MyFontEdittextView etHba1c;
                    Toast.makeText(this, "view not null", Toast.LENGTH_LONG).show();
                    spinner = (Spinner) view.findViewById(R.id.spinner_glucose);
                    String selectedItem = spinner.getSelectedItem().toString();
                    etGlucose = (MyFontEdittextView) view.findViewById(R.id.et_glucose);
                    String glucose = etGlucose.getText().toString();
                    etHba1c = (MyFontEdittextView) view.findViewById(R.id.et_hba1c);
                    String hba1c = etHba1c.getText().toString();

                    Glucose glucose1 = new Glucose();
                    glucose1.setByteArray(picturePath);
                    //gllucose
                    if (!TextUtils.isEmpty(glucose)) {
                        if (phelper.getInteger(AppConstant.GLUCOSE_SELECTED) == AppConstant.GLUCOSE_SELECTED_UNIT_MMOL_PER_L) {
                            glucose1.setGlucose(Float.parseFloat(glucose));
                        } else {
                            glucose1.setGlucose(UnitHelper.mgToMmol(Float.parseFloat(glucose)));
                        }
                    } else glucose1.setGlucose(0);
                    //hba1c
                    if (!TextUtils.isEmpty(hba1c))
                        glucose1.setHba1c(Float.parseFloat(hba1c));
                    else glucose1.setHba1c(0);

                    glucose1.setTestingTime(spinner.getSelectedItem().toString());
                    glucose1.setUserId(user_id);
                    glucose1.setNote(etNote.getText().toString());
                    glucose1.setDate(date);
                    glucose1.setTime(tvTime.getText().toString());
                    long result_glucose;
                    if (is_Edit) {
                        glucose1.setRowId(MainActivity.glucoseArrayList.get(position).getRowId());
                        result_glucose = dbhelper.updateGlucose(glucose1);
                    } else {
                        result_glucose = dbhelper.addGlucose(glucose1);
                    }
                    if (result_glucose >= 0) {
                        Log.i("insert weight result", result_glucose + "");
                        Intent intent1 = new Intent(this, MainActivity.class);
                        startActivity(intent1);
                    }
                }
                break;
            case AppConstant.B_P_FRAGMENT:
                Fragment currentFragment2 = getSupportFragmentManager().findFragmentById(R.id.ll_fragment);
                view = currentFragment2.getView();
                if (view != null) {
                    MyFontEdittextView etSystolic;
                    MyFontEdittextView etDiastolic;
                    MyFontEdittextView etHeartRate;
                    etSystolic = (MyFontEdittextView) view.findViewById(R.id.et_systolic);
                    String systolic = etSystolic.getText().toString();
                    etDiastolic = (MyFontEdittextView) view.findViewById(R.id.et_diastolic);
                    String diastolic = etDiastolic.getText().toString();
                    etHeartRate = (MyFontEdittextView) view.findViewById(R.id.et_heart_rate);
                    String heartrate = etHeartRate.getText().toString();

                    BloodPresure bloodPresure = new BloodPresure();
                    bloodPresure.setUserId(user_id);
                    bloodPresure.setByteArray(picturePath);
                    bloodPresure.setNote(etNote.getText().toString());
                    bloodPresure.setDate(date);
                    bloodPresure.setTime(tvTime.getText().toString());
                    if (!TextUtils.isEmpty(systolic))
                        bloodPresure.setSystolic(Integer.parseInt(systolic));
                    else bloodPresure.setSystolic(0);

                    if (!TextUtils.isEmpty(diastolic))
                        bloodPresure.setDiastolic(Integer.parseInt(diastolic));
                    else bloodPresure.setDiastolic(0);
                    if (!TextUtils.isEmpty(heartrate))
                        bloodPresure.setHeartrate(Integer.parseInt(heartrate));
                    else bloodPresure.setHeartrate(0);

                    long result_bp;

                    if (is_Edit) {
                        bloodPresure.setRowId(MainActivity.bloodPresureArrayList.get(position).getRowId());
                        result_bp = dbhelper.updateBp(bloodPresure);
                    } else {
                        result_bp = dbhelper.addBp(bloodPresure);
                    }
                    if (result_bp >= 0) {
                        Log.i("insert weight result", result_bp + "");
                        Intent intent1 = new Intent(this, MainActivity.class);
                        startActivity(intent1);
                    }
                }
                break;

            case AppConstant.CHOLESTROL_FRAGMENT:
                Fragment currentFragment3 = getSupportFragmentManager().findFragmentById(R.id.ll_fragment);
                view = currentFragment3.getView();
                if (view != null) {
                    MyFontEdittextView etHdl;
                    MyFontEdittextView etLdl;
                    MyFontEdittextView etTotal;
                    MyFontEdittextView etTriglycerides;
                    etHdl = (MyFontEdittextView) view.findViewById(R.id.et_hdl);
                    String hdl = etHdl.getText().toString();
                    etLdl = (MyFontEdittextView) view.findViewById(R.id.et_ldl);
                    String ldl = etLdl.getText().toString();
                    etTotal = (MyFontEdittextView) view.findViewById(R.id.et_total);
                    String total = etTotal.getText().toString();
                    etTriglycerides = (MyFontEdittextView) view.findViewById(R.id.et_triglycerides);
                    String triglyceride = etTriglycerides.getText().toString();

                    cholesterol cholestrol = new cholesterol();
                    cholestrol.setUserId(user_id);
                    cholestrol.setByteArray(picturePath);
                    cholestrol.setNote(etNote.getText().toString());
                    cholestrol.setDate(date);
                    cholestrol.setTime(tvTime.getText().toString());
                    if (!TextUtils.isEmpty(hdl))
                        cholestrol.setHdl(Float.parseFloat(hdl));
                    else cholestrol.setHdl(0);
                    if (!TextUtils.isEmpty(ldl))
                        cholestrol.setLdl(Float.parseFloat(ldl));
                    else cholestrol.setLdl(0);
                    if (!TextUtils.isEmpty(total))
                        cholestrol.setTotal(Float.parseFloat(total));
                    else cholestrol.setTotal(0);
                    if (!TextUtils.isEmpty(triglyceride))
                        cholestrol.setTriglyceride(Float.parseFloat(triglyceride));
                    else cholestrol.setTriglyceride(0);

                    long result_colestrol;
                    if (is_Edit) {
                        cholestrol.setRowId(MainActivity.cholesterolArrayList.get(position).getRowId());
                        result_colestrol = dbhelper.updateCholestrol(cholestrol);
                    } else {
                        result_colestrol = dbhelper.addCholestrol(cholestrol);
                    }
                    if (result_colestrol >= 0) {
                        Log.i("insert weight result", result_colestrol + "");
                        Intent intent1 = new Intent(this, MainActivity.class);
                        startActivity(intent1);
                    }

                }
                break;

            case AppConstant.THYROID_FRAGMENT:
                Fragment currentFragment4 = getSupportFragmentManager().findFragmentById(R.id.ll_fragment);
                view = currentFragment4.getView();
                if (view != null) {
                    MyFontEdittextView etTshLevel;
                    etTshLevel = (MyFontEdittextView) view.findViewById(R.id.et_tsh_level);
                    String tshLevel = etTshLevel.getText().toString();

                    Thyroid thyroid = new Thyroid();
                    thyroid.setUserId(user_id);
                    thyroid.setByteArray(picturePath);
                    thyroid.setNote(etNote.getText().toString());
                    thyroid.setDate(date);
                    thyroid.setTime(tvTime.getText().toString());
                    if (!TextUtils.isEmpty(tshLevel))
                        thyroid.setTshLevel(Float.parseFloat(tshLevel));
                    else thyroid.setTshLevel(0);

                    long result_thyroid;
                    if (is_Edit) {
                        thyroid.setRowId(MainActivity.thyroidArrayList.get(position).getRowId());
                        result_thyroid = dbhelper.updateThyroid(thyroid);
                    } else {

                        result_thyroid = dbhelper.addThyroid(thyroid);
                    }
                    if (result_thyroid >= 0) {
                        Log.i("insert weight result", result_thyroid + "");
                        Intent intent1 = new Intent(this, MainActivity.class);
                        startActivity(intent1);
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.btn_upload:
                ManageActivity.verifyStoragePermissions(this);
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                break;
            case R.id.btn_save:
                getCurrentFragmentDetail();
                break;

            case R.id.btn_cancel:
                finish();
                break;
            case R.id.tv_date:

                final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(this)
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setDoneText("Done")
                        .setCancelText("Cancel")
                        .setThemeLight();
                cdp.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);

                break;

            case R.id.tv_time:
                final String FRAG_TAG_TIME_PICKER = "fragment_date_picker_name";
                RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                        .setOnTimeSetListener(this)
                        .setStartTime(10, 10)
                        .setDoneText("Done")
                        .setCancelText("Cancel")
                        .setForced12hFormat()
                        .setThemeLight();
                rtpd.show(getSupportFragmentManager(), FRAG_TAG_TIME_PICKER);
                break;
        }
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear,
                          int dayOfMonth) {

        date = dayOfMonth + "/" + monthOfYear + "/" + year;

        if (phelper.getInteger(AppConstant.DATE_SELECTED) == AppConstant.DATE_SELECTED_UNIT_DMY)

            tvDate.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
        else
            tvDate.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
    }


    @Override

    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
//        tvTime.setText(hourOfDay + ":" + minute);
        boolean isPM = (hourOfDay >= 12);
        tvTime.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
    }
}
