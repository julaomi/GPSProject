package com.example.tadje.gpsproject.Persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.tadje.gpsproject.model.Locations;
import com.example.tadje.gpsproject.model.Trip;
import com.example.tadje.gpsproject.model.User;

/**
 * Created by tadje on 14.05.2018.
 */

@Database(entities = {Locations.class, User.class, Trip.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase Instance = null;

    public static AppDatabase getInstance() {
        if (Instance == null) {
            throw new IllegalStateException("AppDatabase not initialized yet.");
        }
        return Instance;
    }

    public static AppDatabase getInstance(Context context) {
        if (Instance == null) {
            Instance = Room.databaseBuilder(context,
                    AppDatabase.class, "locations.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return Instance;
    }

    public abstract LocationsDao locationsDao();

    public abstract UserDao userDao();

    public abstract TripDao tripDao();

}
