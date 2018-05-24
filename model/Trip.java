package com.example.tadje.gpsproject.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by tadje on 23.05.2018.
 */

    @Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns =
            "userFK"), tableName ="trip")


    public class Trip {

        @PrimaryKey(autoGenerate = true)
        private int id;

        @ColumnInfo(name = "tripNumber")
        private int tripNumber;

        @ColumnInfo(name = "userFK")
        private int userFK;



        public Trip( int tripNumber ,int userFK) {

            this.tripNumber = tripNumber;
            this.userFK = userFK;
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserFK() {
        return userFK;
    }

    public void setUserFK(int userFK) {
        this.userFK = userFK;
    }

    public int getTripNumber() {
        return tripNumber;
    }

    public void setTripNumber(int tripNumber) {
        this.tripNumber = tripNumber;
    }
}
