package com.example.tadje.gpsproject.Maps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.example.tadje.gpsproject.Persistence.AppDatabase;
import com.example.tadje.gpsproject.R;
import com.example.tadje.gpsproject.TripManager;
import com.example.tadje.gpsproject.model.Locations;
import com.example.tadje.gpsproject.model.Trip;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;


/**
 * Created by tadje on 09.05.2018.
 */

public class LocationManager implements LocationListener {

    private static LocationManager instance = null;

    private Context mContext;
    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;
    private boolean canGetLocation = false;
    private Location location;
    private double latitude;
    private double longitude;
    private double speed;
    // The minimum distance to change updates in metters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    // The minimum time beetwen updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 5;
    private android.location.LocationManager locationManager;
    private List<LocationChangedListener> listeners = new ArrayList<>();


    public LocationManager() {
    }


    public static LocationManager getInstance() {
        if (instance == null) {
            instance = new LocationManager();
        }

        return instance;
    }

    // initialize for get the locations
    public android.location.Location locationInitialize(Context context) {

        mContext = context;
        locationManager = (android.location.LocationManager) mContext
                .getSystemService(LOCATION_SERVICE);

        isGPSEnabled = locationManager
                .isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);

        isNetworkEnabled = locationManager
                .isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER);

        checkForPermissions();

        return location;
    }


    public void checkForPermissions() {

        if (isGPSEnabled || isNetworkEnabled) {

            this.canGetLocation = true;


            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission
                    .ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(mContext, Manifest.permission
                            .ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
/*
                String provider = isGPSEnabled ? android.location.LocationManager.GPS_PROVIDER :
                        android.location.LocationManager.NETWORK_PROVIDER;
                locationManager.requestLocationUpdates(provider, MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
*/
                locationManager.requestLocationUpdates(android.location.LocationManager
                                .GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        this);
                locationManager.requestLocationUpdates(android.location.LocationManager
                                .NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        this);

                lastLocationFromProvider();
            }
        }
    }


    @SuppressLint("MissingPermission")
    private void lastLocationFromProvider() {

        if (locationManager != null) {
            location = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);

            if (location == null) {
                location = locationManager.getLastKnownLocation(android.location.LocationManager
                        .NETWORK_PROVIDER);
            }

            initializeLocationVariablen(location);
        }
    }

    public void initializeLocationVariablen(Location location) {

        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            speed = calculateSpeedInKMHour(location);

            putLocationToDatabase();
        }
    }


    private void putLocationToDatabase() {

        int tripNumb;
        List<Trip> trips = AppDatabase.getInstance().tripDao().getAll();

        if (trips.size() != 0) {

            tripNumb = TripManager.getInstance().getTripNumber();
            Locations location = new Locations(tripNumb, latitude, longitude, speed);
            AppDatabase.getInstance().locationsDao().insertAll(location);
        }
    }


    //Speed from milliseconds to km/h
    private double calculateSpeedInKMHour(Location location) {

        double speedKMHour = ((location.getSpeed()) * 3600) / 1000;

        return speedKMHour;
    }

    //stop the location updates
    @SuppressLint("NewApi")
    public void stopUsingGPS() {
        if (locationManager != null) {
            if (mContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager
                            .PERMISSION_GRANTED && mContext.checkSelfPermission(Manifest.permission
                    .ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.removeUpdates(this);
            locationManager = null;
        }
    }

    // if an automatic location update happends then start the methode
    // initializeLocationVariablen with the new location and notifyLocationChangedListener
    @Override
    public void onLocationChanged(Location location) {

        initializeLocationVariablen(location);
        Toast.makeText(mContext, mContext.getString(R.string.newLocation), Toast.LENGTH_LONG)
                .show();

        notifyLocationChangedListener(location);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    //register Listener to the listener List
    public void registerLocationChangedListener(LocationChangedListener locationChangedListener) {
        if (!listeners.contains(locationChangedListener)) {
            listeners.add(locationChangedListener);
        }
    }

    //remove the listener from the listener list
    public void unregisterLocationChangedListener(LocationChangedListener
                                                         locationChangedListener) {
        if (listeners.contains(locationChangedListener)) {
            listeners.remove(locationChangedListener);
        }
    }

    //inform the registered listener about the new location
    public void notifyLocationChangedListener(Location location) {
        for (LocationChangedListener listener : listeners) {
            listener.onLocationChanged(location);
        }
    }
}
