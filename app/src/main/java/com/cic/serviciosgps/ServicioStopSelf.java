package com.cic.serviciosgps;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import static android.content.ContentValues.TAG;

public class ServicioStopSelf extends Service {
    private double longitude, latitude;

    public ServicioStopSelf() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        // throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    public void onCreate()
    {
        super.onCreate();
    }

    public void onStart(Intent intent, int startID)
    {
        System.out.println("El servicio a comenzado...");
        longitude = intent.getExtras().getDouble("longitude");
        latitude = intent.getExtras().getDouble("latitude");
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File (root.getAbsolutePath() + "/Download");
        File file = new File(dir, "LatLong.csv");
        System.out.println(longitude+","+latitude);
        if(!file.exists())
            writeToSDFile();
        writeToSDFileText();
        this.stopSelf();
    }

    public void onDestroy()
    {
        super.onDestroy();
        System.out.println("El servicio a terminado...");
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
