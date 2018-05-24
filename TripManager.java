package com.example.tadje.gpsproject;

/**
 * Created by tadje on 23.05.2018.
 */

public class TripManager {

    private static TripManager instance = null;

    private int selectedUserID;
    private int tripNumber;

    private TripManager() {

    }
    public static TripManager getInstance() {

        if (instance == null){
            instance = new TripManager();
        }

        return instance;
    }


    public int getSelectedUserID() {
        return selectedUserID;
    }

    public void setSelectedUserID(int selectedUserID) {
        this.selectedUserID = selectedUserID;
    }

    public int getTripNumber() {
        return tripNumber;
    }

    public void setTripNumber(int tripNumber) {
        this.tripNumber = tripNumber;
    }
}
