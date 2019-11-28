package com.cic.serviciosgps;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static android.content.ContentValues.TAG;


public class ServicioIntentService extends IntentService {
    public ServicioIntentService() {
        super("ServicioIntentService");
    }
    private double longitude, latitude;

    @SuppressLint("WrongThread")
    @Override

    protected void onHandleIntent(Intent intent) {
        longitude = intent.getExtras().getDouble("longitude");
        latitude = intent.getExtras().getDouble("latitude");
        SystemClock.sleep(500);
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File (root.getAbsolutePath() + "/Download");
        File file = new File(dir, "LatLong.csv");
        if(!file.exists())
            writeToSDFile();
        writeToSDFileText();


    }

    private void writeToSDFile(){

        File root = android.os.Environment.getExternalStorageDirectory();
        Log.d("External file",""+root);


        File dir = new File (root.getAbsolutePath() + "/Download");
        dir.mkdirs();
        File file = new File(dir, "LatLong.csv");

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println("Latitude , Longitude");
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i(TAG, "******* File not found. Did you" +
                    " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToSDFileText(){

        File root = android.os.Environment.getExternalStorageDirectory();
        Log.d("External file",""+root);


        File dir = new File (root.getAbsolutePath() + "/Download");
        dir.mkdirs();
        File file = new File(dir, "LatLong.csv");

        try {
            FileOutputStream f = new FileOutputStream(file,true);
            PrintWriter pw = new PrintWriter(f);
            pw.println(latitude+","+longitude);
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i(TAG, "File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
