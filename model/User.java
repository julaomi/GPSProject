package com.example.tadje.gpsproject.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.widget.EditText;

/**
 * Created by tadje on 14.05.2018.
 */
@Entity(tableName = "user")
public class User {

    @PrimaryKey (autoGenerate = true)
    private int id;

    @ColumnInfo(name = "firstname")
    private String firstname;

    @ColumnInfo(name = "lastname")
    private String lastname;

    @ColumnInfo(name = "birthday")
        private String birthday;


    public User( String firstname, String lastname, String birthday) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
