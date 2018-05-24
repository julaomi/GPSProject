package com.example.tadje.gpsproject.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.tadje.gpsproject.model.Trip;

import java.util.List;

/**
 * Created by tadje on 23.05.2018.
 */

@Dao
public interface TripDao {

    @Query("SELECT * FROM trip")
    List<Trip> getAll();

    @Query("SELECT * FROM trip WHERE tripNumber IN (:tripNumb)")
    List<Trip> loadAllByTripNumber(int[] tripNumb);

    @Insert
    void insertAll(Trip... trip);

    @Delete
    void delete(Trip trip);
}
