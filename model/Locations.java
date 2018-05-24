package com.example.tadje.gpsproject.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;

/**
 * Created by tadje on 14.05.2018.
 */


@Entity (foreignKeys = @ForeignKey(entity = Trip.class, parentColumns = "id",
        childColumns = "tripFK"), tableName = "locations")
public class Locations {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="tripFK")
    private int tripFK;

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "longitude")
    private double longitude;

    @ColumnInfo(name = "speed")
    private double speed;


    public Locations( int tripFK, double latitude, double longitude,double speed) {
        this.tripFK =  tripFK;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTripFK() {
        return tripFK;
    }
    public void setTripFK(int tripID) {
        this.tripFK = tripFK;
    }

}
