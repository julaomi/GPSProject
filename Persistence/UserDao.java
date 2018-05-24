package com.example.tadje.gpsproject.Persistence;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.tadje.gpsproject.model.Locations;
import com.example.tadje.gpsproject.model.User;

import java.util.List;

/**
 * Created by tadje on 14.05.2018.
 */

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();


    @Insert
    void insertAll(User... user);

    @Delete
    void delete(User user);

}

