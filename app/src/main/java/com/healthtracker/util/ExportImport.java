package com.healthtracker.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.healthtracker.helper.DataBaseHelper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by local nidhi on 05-11-2016.
 */
public class ExportImport {
    String currentDBPath = "/data/" + "com.healthtracker" + "/databases/" + DataBaseHelper.DATABASE_NAME;
    String BackupDirectory = "HealthTrackerBackup";
    String root = Environment.getExternalStorageDirectory().toString();


    //copy database file to external memory
    public void export(Context context, SQLiteOpenHelper db) {
        ProgressDialog progressDialog;
        progressDialog = ProgressDialog.show(context, "", "Please Wait...");
        progressDialog.setCancelable(false);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date now = new Date();
        String sourcepath = root + "/HealthTracker";
        String destPath = root + "/" + BackupDirectory + "/Backup" + formatter.format(now) + ".zip";
        zipFolder(sourcepath, destPath);
        Toast.makeText(context, "images Exported!", Toast.LENGTH_LONG).show();
        db.close();
        File dbFile = new File(Environment.getDataDirectory() + currentDBPath);
        File exportDir = new File(Environment.getExternalStorageDirectory(), BackupDirectory);
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "Backup" + formatter.format(now));
        try {
            file.createNewFile();
            this.copyFile(dbFile, file);
            Toast.makeText(context, "Backup is successful to SD card", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("export", e.getMessage(), e);
        }
        progressDialog.dismiss();
    }


    //gives all backup files stored in /renttracker
    public void restore(final Context context, final SQLiteOpenHelper db) {      //works
        db.close();
        //checking for files


        String path = Environment.getExternalStorageDirectory().toString() + "/" + BackupDirectory;
        Log.d("Files", "Path: " + path);
        File f = new File(path);
        File filea[] = f.listFiles();
        if (filea == null) {
            Toast.makeText(context, "No backup file found", Toast.LENGTH_SHORT).show();
            return;
        }
        if (filea.length == 0) {
            Toast.makeText(context, "No backup file found", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d("Files", "Size: " + filea.length);
        int b = 0;
        for (int i = 0; i < filea.length; i++) {
            Log.d("Files", "FileName:" + filea[i].getName());
            if (!filea[i].getName().contains("zip")) b++;
        }
        final CharSequence[] backupfiles = new CharSequence[b];
        int j = 0;
        for (int i = 0; i < filea.length; i++) {
            if (!filea[i].getName().contains("zip")) {
                Log.d("Files", "FileNameWithBackup:" + filea[i].getName());
                backupfiles[j] = filea[i].getName();
                j++;
            }
        }
        if (backupfiles.length == 0) {
            Toast.makeText(context, "No backup file found", Toast.LENGTH_SHORT).show();
            return;
        }
        //show dialogue
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Backup File");
        builder.setItems(backupfiles, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                restorefile(backupfiles[which].toString(), db, context);
            }
        });
        builder.show();
    }


    //restores given file as database file in app
    void restorefile(String dbFileName, SQLiteOpenHelper db, Context context) {

        String path = Environment.getExternalStorageDirectory() + "/" + BackupDirectory + "/" + dbFileName + ".zip";
        String destDir = Environment.getExternalStorageDirectory() + "/" + "HealthTracker";
        unpackZip(path, destDir);

        //start copying
        File dbFile =
                new File(Environment.getDataDirectory() + currentDBPath);


        File file = new File(Environment.getExternalStorageDirectory() + "/" + BackupDirectory + "/" + dbFileName);

        try {
            this.copyFile(file, dbFile);
            db.getWritableDatabase().close();
            Toast.makeText(context, "Restored Successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("import", e.getMessage(), e);
        }
    }

    //copy
    void copyFile(File src, File dst) throws IOException {
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try {
            //inChannel.transferTo(0, inChannel.size(), outChannel);
            outChannel.transferFrom(inChannel, 0, inChannel.size());        //works
        } finally {
            //if (inChannel != null)
            inChannel.close();
            //if (outChannel != null)
            outChannel.close();
        }
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

    private boolean unpackZip(String path, String destDir) {
        InputStream is;
        ZipInputStream zis;
        try {
            String filename;
            is = new FileInputStream(path);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;
            if (!destDir.isEmpty()) {
                deleteDir(destDir);
            }
            while ((ze = zis.getNextEntry()) != null) {
                // zapis do souboru
                filename = ze.getName();
                // Need to create directories if not exists, or
                // it will generate an Exception...
                if (ze.isDirectory()) {
                    File fmd = new File(destDir + "/" + filename);
                    fmd.mkdirs();
                    continue;
                }
                FileOutputStream fout = new FileOutputStream(destDir + filename);
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

    public void deleteDir(String dirName) {
        File dir = new File(Environment.getExternalStorageDirectory() + dirName);
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }
        }
    }


}
