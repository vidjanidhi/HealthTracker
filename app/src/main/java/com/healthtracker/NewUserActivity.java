package com.healthtracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.healthtracker.component.MyFontButton;
import com.healthtracker.component.MyFontEdittextView;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.helper.DataBaseHelper;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.model.User;
import com.healthtracker.util.AppConstant;
import com.healthtracker.util.Util;

import java.util.Calendar;

public class NewUserActivity extends ActionBarBaseActivitiy implements View.OnClickListener, CalendarDatePickerDialogFragment.OnDateSetListener {

    private MyFontEdittextView etNewuserUsername;
    private RadioGroup radioSex;
    private RadioButton radioMale;
    private RadioButton radioFemale;
    private MyFontEdittextView etNewuserAge;
    private MyFontTextView tvNewuserHeight;
    private MyFontEdittextView etNewuserHeight;
    private MyFontButton btnNewuserSave;
    private MyFontButton btnNewuserCancel;
    PreferenceHelper phelper;
    DataBaseHelper dbhelper;
    boolean activityEdit = false;
    int date, month, year, edit_userId = 0;
    private boolean IsDateOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        if (getIntent().getExtras() != null) {
            edit_userId = getIntent().getIntExtra(AppConstant.USER_ID, 1);
            activityEdit = true;
        }
        findviews();
        setTitle(getString(R.string.app_name));
        phelper = new PreferenceHelper(this);
        dbhelper = new DataBaseHelper(this);
        int lengthSeleted = phelper.getInteger(AppConstant.LENGTH_SELECTED);
        if (lengthSeleted == AppConstant.LENGTH_SELECTED_UNIT_CM) {
            tvNewuserHeight.setText("Height(cm)");
        } else {
            tvNewuserHeight.setText("Height(inch)");
        }
        Log.i("activity edit", activityEdit + "");
        if (activityEdit) {
            User useer = dbhelper.getUser(edit_userId);
            Log.i("user object", useer.getUserName() + useer.getAge());
            etNewuserAge.setText(useer.getAge() + "");
            etNewuserUsername.setText(useer.getUserName());
            etNewuserHeight.setText(useer.getHeight() + "");

            if (useer.getGender() == User.MALE) {
                radioMale.setChecked(true);
            } else {
                radioFemale.setChecked(true);
            }
        }
    }

    private void findviews() {

        etNewuserUsername = (MyFontEdittextView) findViewById(R.id.et_newuser_username);
        radioSex = (RadioGroup) findViewById(R.id.radioSex);
        radioMale = (RadioButton) findViewById(R.id.radioMale);
        radioFemale = (RadioButton) findViewById(R.id.radioFemale);
        etNewuserAge = (MyFontEdittextView) findViewById(R.id.et_newuser_age);
        tvNewuserHeight = (MyFontTextView) findViewById(R.id.tv_newuser_height);
        etNewuserHeight = (MyFontEdittextView) findViewById(R.id.et_newuser_height);
        btnNewuserSave = (MyFontButton) findViewById(R.id.btn_newuser_save);
        btnNewuserCancel = (MyFontButton) findViewById(R.id.btn_newuser_cancel);
        btnNewuserSave.setOnClickListener(this);
        btnNewuserCancel.setOnClickListener(this);
        etNewuserAge.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_newuser_save:
                if (setAllDetails()) {
                    User user = new User();
                    user.setUserName(etNewuserUsername.getText().toString());
                    user.setHeight(Integer.parseInt(etNewuserHeight.getText().toString()));
                    user.setAge(year, month, date);
                    int selectedId = radioSex.getCheckedRadioButtonId();
                    if ((selectedId == R.id.radioMale)) {
                        user.setGender(User.MALE);
                    } else {
                        user.setGender(User.FEMALE);
                    }
                    if (activityEdit) {
                        dbhelper.updateUser(user);

                    } else {
                        int userid = dbhelper.addUser(user);
                        Log.i("user id", userid + "");
                    }

                    Intent intent1 = new Intent(this, MainActivity.class);
                    startActivity(intent1);
                } else {
                    Toast.makeText(this, "Enter All Details Right", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.btn_newuser_cancel:

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

                break;

            case R.id.et_newuser_age:
                final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(this)
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setDoneText("Done")
                        .setCancelText("Cancel")
                        .setThemeLight();
                cdp.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
                break;
        }

    }

    private boolean setAllDetails() {
        if (IsDateOk) {
            if (!etNewuserUsername.getText().toString().isEmpty()) {
                if (!etNewuserHeight.getText().toString().isEmpty()) {
                    if (!etNewuserAge.getText().toString().isEmpty()) {
                        return true;
                    } else {
                        return false;
                    }
                } else
                    return false;

            } else {
                return false;
            }
        } else return false;

    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        date = dayOfMonth;
        month = monthOfYear;
        this.year = year;
        if (Util.getDaysDifference(dayOfMonth + "/" + monthOfYear + "/" + year) > 0) {
            IsDateOk = true;
            if (phelper.getInteger(AppConstant.DATE_SELECTED) == AppConstant.DATE_SELECTED_UNIT_DMY) {
                etNewuserAge.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
            } else {
                etNewuserAge.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
            }
        } else IsDateOk = false;

    }
}
