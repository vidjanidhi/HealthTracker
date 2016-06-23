package com.healthtracker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.healthtracker.component.MyFontTextView;
import com.healthtracker.helper.DataBaseHelper;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.util.AppConstant;
import com.healthtracker.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;

public class ManageActivity extends ActionBarBaseActivitiy implements OnClickListener {

    private static final int YOUR_RESULT_CODE = 1;
    private static final String TAG = "tag";
    public int position = 0;
    public int dialogId = 0;
    PreferenceHelper phelper;
    String path = null;
    private LinearLayout llManageSendEmail;
    private LinearLayout llManageUsers;
    private LinearLayout llManageExport;
    private LinearLayout llManageReminder;
    private LinearLayout llManageEraseAll;
    private LinearLayout llManageSave;
    private LinearLayout llManageImportData;
    private LinearLayout llManageGlucoseUnits;
    private MyFontTextView tvManageGlucoseUnits;
    private LinearLayout llManageHbalcUnits;
    private MyFontTextView tvManageHba1cUnits;
    private LinearLayout llManageWeightUnits;
    private MyFontTextView tvManageWeightUnits;
    private LinearLayout llManageLenghtUnits;
    private MyFontTextView tvManageLengthUnits;
    private LinearLayout llManageDateFormat;
    private MyFontTextView tvDateFormat;

    DataBaseHelper dataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        setTitle("Health tracker");
        dataBaseHelper = new DataBaseHelper(this);
        phelper = new PreferenceHelper(this);
        findviews();


    }

    private void findviews() {
        llManageSendEmail = (LinearLayout) findViewById(R.id.ll_manage_send_email);
        llManageUsers = (LinearLayout) findViewById(R.id.ll_manage_users);
        llManageExport = (LinearLayout) findViewById(R.id.ll_manage_export);
        llManageReminder = (LinearLayout) findViewById(R.id.ll_manage_reminder);
        llManageEraseAll = (LinearLayout) findViewById(R.id.ll_manage_erase_all);
        llManageSave = (LinearLayout) findViewById(R.id.ll_manage_save);
        llManageImportData = (LinearLayout) findViewById(R.id.ll_manage_import_data);
        llManageGlucoseUnits = (LinearLayout) findViewById(R.id.ll_manage_glucose_units);
        tvManageGlucoseUnits = (MyFontTextView) findViewById(R.id.tv_manage_glucose_units);
        llManageHbalcUnits = (LinearLayout) findViewById(R.id.ll_manage_hbalc_units);
        tvManageHba1cUnits = (MyFontTextView) findViewById(R.id.tv_manage_hba1c_units);
        llManageWeightUnits = (LinearLayout) findViewById(R.id.ll_manage_weight_units);
        tvManageWeightUnits = (MyFontTextView) findViewById(R.id.tv_manage_weight_units);
        llManageLenghtUnits = (LinearLayout) findViewById(R.id.ll_manage_lenght_units);
        tvManageLengthUnits = (MyFontTextView) findViewById(R.id.tv_manage_length_units);
        llManageDateFormat = (LinearLayout) findViewById(R.id.ll_manage_date_format);
        tvDateFormat = (MyFontTextView) findViewById(R.id.tv_date_format);

        llManageSendEmail.setOnClickListener(this);
        llManageUsers.setOnClickListener(this);
        llManageExport.setOnClickListener(this);
        llManageReminder.setOnClickListener(this);
        llManageEraseAll.setOnClickListener(this);
        llManageSave.setOnClickListener(this);
        llManageImportData.setOnClickListener(this);
        llManageGlucoseUnits.setOnClickListener(this);
        llManageHbalcUnits.setOnClickListener(this);
        llManageWeightUnits.setOnClickListener(this);
        llManageLenghtUnits.setOnClickListener(this);
        llManageDateFormat.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_new_user:
                Intent intent = new Intent(this, NewUserActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_edit_user:

                Intent intent_edit = new Intent(this, NewUserActivity.class);
                intent_edit.putExtra(AppConstant.USER_ID, phelper.getInteger(AppConstant.USER_ID));
                startActivity(intent_edit);

                break;
            case R.id.btn_delete_user:
                phelper.removeKey(AppConstant.USER_ID);
                dataBaseHelper.deleteUser(phelper.getInteger(AppConstant.USER_ID));
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.ll_manage_send_email:
                exportDB();
                String filename = DataBaseHelper.DATABASE_NAME;
                File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename);
                Uri path = Uri.fromFile(filelocation);
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
// set the type to 'email'
                emailIntent.setType("vnd.android.cursor.dir/email");
// the attachment
                emailIntent.putExtra(Intent.EXTRA_STREAM, path);
// the mail subject
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                break;
            case R.id.ll_manage_users:

                Dialog forgotPasswordDialog = new Dialog(this);
                forgotPasswordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                forgotPasswordDialog.getWindow().setBackgroundDrawable(
                        new ColorDrawable(android.graphics.Color.TRANSPARENT));
                forgotPasswordDialog.setContentView(R.layout.manage_user_dialog);
                forgotPasswordDialog.setCancelable(true);
                Button btnNewUser = (Button) forgotPasswordDialog.findViewById(R.id.btn_new_user);
                Button btnEditUser = (Button) forgotPasswordDialog.findViewById(R.id.btn_edit_user);
                Button btnDeleteUser = (Button) forgotPasswordDialog.findViewById(R.id.btn_delete_user);
                btnNewUser.setOnClickListener(this);
                btnEditUser.setOnClickListener(this);
                btnDeleteUser.setOnClickListener(this);
//                etForgetEmail = (MyFontEdittextView) forgotPasswordDialog
//                        .findViewById(R.id.etForgetEmail);
//                forgotPasswordDialog.findViewById(R.id.tvForgetSubmit)
//                        .setOnClickListener(this);
//                etForgetEmail.requestFocus();
//                activity.showKeyboard(etForgetEmail);

                forgotPasswordDialog.show();

                break;
            case R.id.ll_manage_export:
                exportDB();
                break;
            case R.id.ll_manage_reminder:

                break;
            case R.id.ll_manage_erase_all:
                this.deleteDatabase(DataBaseHelper.DATABASE_NAME);
                phelper.clearAll();
                Intent intent_new = new Intent(this, MainActivity.class);
                startActivity(intent_new);

                break;
            case R.id.ll_manage_save:

                break;
            case R.id.ll_manage_import_data:
                importDB();

                break;
            case R.id.ll_manage_glucose_units:
                dialogId = 1;
                final CharSequence[] items = {AppConstant.GLUCOSE_MMOL_PER_L, AppConstant.GLUCOSE_MG_PER_DL};
                int selectedItem = phelper.getInteger(AppConstant.GLUCOSE_SELECTED);
                showDialog(items, selectedItem, dialogId);
                break;
            case R.id.ll_manage_hbalc_units:
                dialogId = 2;
                final CharSequence[] hba1c_items = {AppConstant.HBA1C_DCCT, AppConstant.HBA1C_IFFC};
                int selectedHba1cItem = phelper.getInteger(AppConstant.HBA1C_SELECTED);
                showDialog(hba1c_items, selectedHba1cItem, dialogId);
                break;
            case R.id.ll_manage_weight_units:
                dialogId = 3;
                final CharSequence[] weight_items = {AppConstant.WEIGHT_KG, AppConstant.WEIGHT_POUNDS};
                int selectedweightItem = phelper.getInteger(AppConstant.WEIGHT_SELECTED);
                showDialog(weight_items, selectedweightItem, dialogId);
                break;
            case R.id.ll_manage_lenght_units:
                dialogId = 4;
                final CharSequence[] length_items = {AppConstant.LENGTH_CM, AppConstant.LENGTH_INCH};
                int selectedlengthItem = phelper.getInteger(AppConstant.LENGTH_SELECTED);
                showDialog(length_items, selectedlengthItem, dialogId);
                break;
            case R.id.ll_manage_date_format:
                dialogId = 5;
                final CharSequence[] date_items = {AppConstant.DATE_DMY, AppConstant.DATE_MDY};
                int selectedDateItem = phelper.getInteger(AppConstant.DATE_SELECTED);
                showDialog(date_items, selectedDateItem, dialogId);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            Log.d(TAG, "File Uri: " + uri.toString());
            // Get the path

            try {
                path = Util.getPath(this, uri);
                try {
                    dataBaseHelper.importDatabase(path, this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "File Path: " + path);
            // Get the file instance
            // File file = new File(path);
            // Initiate the upload
        }
    }

    private void importDB() {
        // TODO Auto-generated method stub

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        startActivityForResult(intent, YOUR_RESULT_CODE);
//        try {
//            File sd = Environment.getExternalStorageDirectory();
//            File data = Environment.getDataDirectory();
//
//            if (sd.canWrite()) {
//                String currentDBPath = "//data//" + "com.healthtracker"
//                        + "//databases//" + DataBaseHelper.DATABASE_NAME;
//                String backupDBPath = DataBaseHelper.DATABASE_NAME;
//                File backupDB = new File(data, currentDBPath);
//                File currentDB = new File(sd, backupDBPath);
//
//                FileChannel src = new FileInputStream(currentDB).getChannel();
//                FileChannel dst = new FileOutputStream(backupDB).getChannel();
//                dst.transferFrom(src, 0, src.size());
//                src.close();
//                dst.close();
//                Toast.makeText(getBaseContext(), backupDB.toString(),
//                        Toast.LENGTH_LONG).show();
//
//            }
//        } catch (Exception e) {
//
//            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
//                    .show();
//
//        }
    }

    private void exportDB() {
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + "com.healthtracker" + "/databases/" + DataBaseHelper.DATABASE_NAME;
        String backupDBPath = DataBaseHelper.DATABASE_NAME;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDialog(final CharSequence[] items, int selectedItem, final int dialogId) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an option");

        builder.setSingleChoiceItems(items, selectedItem, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                switch (dialogId) {
                    case 1:
                        if (item == AppConstant.GLUCOSE_SELECTED_UNIT_MMOL_PER_L) {
                            Log.i("item", item + "");
                            tvManageGlucoseUnits.setText(AppConstant.GLUCOSE_TEXT_MMOL_PER_L);
                            phelper.setInteger(AppConstant.GLUCOSE_SELECTED, AppConstant.GLUCOSE_SELECTED_UNIT_MMOL_PER_L);
                        } else if (item == AppConstant.GLUCOSE_SELECTED_UNIT_MG_PER_DL) {
                            tvManageGlucoseUnits.setText(AppConstant.GLUCOSE_TEXT_MG_PER_DL);
                            phelper.setInteger(AppConstant.GLUCOSE_SELECTED, AppConstant.GLUCOSE_SELECTED_UNIT_MG_PER_DL);
                        }
                        break;

                    case 2:
                        if (item == AppConstant.HBA1C_SELECTED_UNIT_DCCT) {
                            Log.i("item", item + "");
                            tvManageHba1cUnits.setText(AppConstant.HBA1C_TEXT_DCCT);
                            phelper.setInteger(AppConstant.HBA1C_SELECTED, AppConstant.HBA1C_SELECTED_UNIT_DCCT);
                        } else if (item == AppConstant.HBA1C_SELECTED_UNIT_IFFC) {
                            tvManageHba1cUnits.setText(AppConstant.HBA1C_TEXT_IFFC);
                            phelper.setInteger(AppConstant.HBA1C_SELECTED, AppConstant.HBA1C_SELECTED_UNIT_IFFC);
                        }
                        break;
                    case 3:
                        if (item == AppConstant.WEIGHT_SELECTED_UNIT_KG) {
                            Log.i("item", item + "");
                            tvManageWeightUnits.setText(AppConstant.WEIGHT_TEXT_KG);
                            phelper.setInteger(AppConstant.WEIGHT_SELECTED, AppConstant.WEIGHT_SELECTED_UNIT_KG);
                        } else if (item == AppConstant.WEIGHT_SELECTED_UNIT_POUNDS) {
                            tvManageWeightUnits.setText(AppConstant.WEIGHT_TEXT_POUNDS);
                            phelper.setInteger(AppConstant.WEIGHT_SELECTED, AppConstant.WEIGHT_SELECTED_UNIT_POUNDS);
                        }
                        break;
                    case 4:
                        if (item == AppConstant.LENGTH_SELECTED_UNIT_CM) {
                            Log.i("item", item + "");
                            tvManageLengthUnits.setText(AppConstant.LENGTH_TEXT_CM);
                            phelper.setInteger(AppConstant.LENGTH_SELECTED, AppConstant.LENGTH_SELECTED_UNIT_CM);
                        } else if (item == 1) {
                            tvManageLengthUnits.setText(AppConstant.LENGTH_TEXT_INCH);
                            phelper.setInteger(AppConstant.LENGTH_SELECTED, AppConstant.LENGTH_SELECTED_UNIT_INCH);
                        }
                        break;
                    case 5:
                        if (item == AppConstant.DATE_SELECTED_UNIT_DMY) {
                            Log.i("item", item + "");
                            phelper.setInteger(AppConstant.DATE_SELECTED, AppConstant.DATE_SELECTED_UNIT_DMY);
                        } else if (item == AppConstant.DATE_SELECTED_UNIT_MDY) {
                            phelper.setInteger(AppConstant.DATE_SELECTED, AppConstant.DATE_SELECTED_UNIT_MDY);
                        }
                        break;
                }
                dialog.dismiss();
            }
        });
        builder.setCancelable(true);
        final AlertDialog alert = builder.create();
        alert.show();


    }
}
