package com.example.tadje.gpsproject;

import android.app.Application;

import com.example.tadje.gpsproject.Maps.MyLocationManager;
import com.example.tadje.gpsproject.Persistence.AppDatabase;

/**
 * Created by tadje on 14.05.2018.
 */

public class GPSApplication extends Application {

    private static GPSApplication instance = null;

    public static GPSApplication getInstance() {

        if (instance == null){
            instance = new GPSApplication();
        }

        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //initialize database
        AppDatabase.getInstance(this);
        MyLocationManager.getInstance();
    }
}
