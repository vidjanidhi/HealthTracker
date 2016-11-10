package com.healthtracker;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.helper.DataBaseHelper;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.util.AppConstant;
import com.healthtracker.util.ExportImport;
import com.healthtracker.util.Util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.Calendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ManageActivity extends ActionBarBaseActivitiy implements OnClickListener, RadialTimePickerDialogFragment.OnTimeSetListener {
    int n = 1;
    String currentDBPath = "/data/" + "com.healthtracker" + "/databases/" + DataBaseHelper.DATABASE_NAME;


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

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
    String root;

    DataBaseHelper dataBaseHelper;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        setTitle(getString(R.string.app_name));
        dataBaseHelper = new DataBaseHelper(this);
        phelper = new PreferenceHelper(this);
        root = Environment.getExternalStorageDirectory().toString();
        findviews();
    }

    private void findviews() {
        llManageSendEmail = (LinearLayout) findViewById(R.id.ll_manage_send_email);
        llManageUsers = (LinearLayout) findViewById(R.id.ll_manage_users);
        llManageExport = (LinearLayout) findViewById(R.id.ll_manage_export);
        llManageReminder = (LinearLayout) findViewById(R.id.ll_manage_reminder);
        llManageEraseAll = (LinearLayout) findViewById(R.id.ll_manage_erase_all);
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
        llManageImportData.setOnClickListener(this);
        llManageGlucoseUnits.setOnClickListener(this);
        llManageHbalcUnits.setOnClickListener(this);
        llManageWeightUnits.setOnClickListener(this);
        llManageLenghtUnits.setOnClickListener(this);
        llManageDateFormat.setOnClickListener(this);
    }

    private static void zipFolder(String inputFolderPath, String outZipPath) {
        try {
            FileOutputStream fos = new FileOutputStream(outZipPath);
            ZipOutputStream zos = new ZipOutputStream(fos);
            File srcFile = new File(inputFolderPath);
            File[] files = srcFile.listFiles();
            Log.d("zip folder", "Zip directory: " + srcFile.getName());
            for (int i = 0; i < files.length; i++) {
                Log.d("aa", "Adding file: " + files[i].getName());
                byte[] buffer = new byte[1024];
                FileInputStream fis = new FileInputStream(files[i]);
                zos.putNextEntry(new ZipEntry(files[i].getName()));
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
        } catch (IOException ioe) {
            Log.e("", ioe.getMessage());
        }
    }

    private boolean unpackZip(String path, String zipname) {
        InputStream is;
        ZipInputStream zis;
        try {
            String filename;
            is = new FileInputStream(path + zipname);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;
            while ((ze = zis.getNextEntry()) != null) {
                // zapis do souboru
                filename = ze.getName();
                // Need to create directories if not exists, or
                // it will generate an Exception...
                if (ze.isDirectory()) {
                    File fmd = new File(path + filename);
                    fmd.mkdirs();
                    continue;
                }
                FileOutputStream fout = new FileOutputStream(path + filename);
                // cteni zipu a zapis
                while ((count = zis.read(buffer)) != -1) {
                    fout.write(buffer, 0, count);
                }
                fout.close();
                zis.closeEntry();
            }
            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
                File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename + ".db");
                Uri path = Uri.fromFile(filelocation);
                Log.i("Email path", path + "");
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
                ExportImport exportImport = new ExportImport();
                exportImport.export(this, dataBaseHelper);
//                exportDB();
                break;
            case R.id.ll_manage_import_data:
                ExportImport exportImport1 = new ExportImport();
                exportImport1.restore(this, dataBaseHelper);
//                importDB();
                break;
            case R.id.ll_manage_reminder:
                final String FRAG_TAG_TIME_PICKER = "fragment_date_picker_name";
                RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                        .setOnTimeSetListener(this)
                        .setStartTime(10, 10)
                        .setDoneText("Done")
                        .setCancelText("Cancel")
                        .setThemeLight();
                rtpd.show(getSupportFragmentManager(), FRAG_TAG_TIME_PICKER);
                break;
            case R.id.ll_manage_erase_all:
                new AlertDialog.Builder(this)
                        .setTitle("Erase All")
                        .setMessage("Are you sure you want to delete All data?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                deleteDatabase(DataBaseHelper.DATABASE_NAME);
                                phelper.clearAll();
                                Intent intent_new = new Intent(ManageActivity.this, MainActivity.class);
                                startActivity(intent_new);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
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

            path = Util.getPath1(uri, this);
            Log.i("path", path + "aa");
            try {
                if (dataBaseHelper.importDatabase(path, this)) {
                    Toast.makeText(this, "file copied to db folder", Toast.LENGTH_SHORT).show();
                }
//                    unpackZip(root,"/HealthTracker.zip");
            } catch (IOException e) {
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
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        //special for samsung
        Intent samintent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
        samintent.putExtra("CONTENT_TYPE", "*/*");
        samintent.addCategory(Intent.CATEGORY_DEFAULT);
        Intent chooseIntent = intent;
        if (getPackageManager().resolveActivity(samintent, 0) != null) {
            chooseIntent = Intent.createChooser(samintent, "open file");
            chooseIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent});
        } else {
            Intent.createChooser(intent, "open file");
        }
        try {
            startActivityForResult(chooseIntent, YOUR_RESULT_CODE);
        } catch (android.content.ActivityNotFoundException exx) {
            Toast.makeText(this, "no suitable file manager was found", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
        boolean isPM = (hourOfDay >= 12);
//        tvTime.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent12 = new Intent(this, NotifyService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent12, 0);
        alarmManager.cancel(pendingIntent);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 1);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 60 * 24, pendingIntent);
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void exportDB() {
        verifyStoragePermissions(this);

        String sourcepath = root + "/HealthTracker";
        String destPath = root + "/HealthTracker.zip";
        zipFolder(sourcepath, destPath);

        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;

        String backupDBPath = DataBaseHelper.DATABASE_NAME + ".db";
        n++;

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
