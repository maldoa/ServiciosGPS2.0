package com.cic.serviciosgps;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import com.cic.serviciosgps.MainActivity.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static android.content.ContentValues.TAG;

public class ServicioHiloPrincipal extends Service {
    private Handler hdlr = new Handler();
    private int i;
    private double longitude, latitude;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        i =0;
        longitude = intent.getExtras().getDouble("longitude");
        latitude = intent.getExtras().getDouble("latitude");

        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File (root.getAbsolutePath() + "/Download");
        File file = new File(dir, "LatLong.csv");
        if(!file.exists())
            writeToSDFile();
        writeToSDFileText();


        MainActivity.pgsBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            public void run() {
                while (i < 100) {
                    i += 10;
                    // Update the progress bar and display the current value in text view
                    hdlr.post(new Runnable() {
                        public void run() {
                            MainActivity.pgsBar.setProgress(i); }
                    });
                    try {
                        // Sleep for 100 milliseconds to show the progress slowly.
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
