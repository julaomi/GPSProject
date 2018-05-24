package com.example.tadje.gpsproject.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.tadje.gpsproject.model.Locations;

import java.util.List;

/**
 * Created by tadje on 14.05.2018.
 */

@Dao
public interface LocationsDao {
    @Query("SELECT * FROM locations")
    List<Locations> getAll();

    @Query("SELECT * FROM locations WHERE id IN (:Ids)")
    List<Locations> loadAllByIds(int[] Ids);

    @Insert
    void insertAll(Locations... locations);

    @Delete
    void delete(Locations locations);

}
