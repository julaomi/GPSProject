package com.example.tadje.gpsproject.Maps;

import android.graphics.Color;
import android.location.Location;

import com.example.tadje.gpsproject.MainActivity;
import com.example.tadje.gpsproject.MapsFragment;
import com.example.tadje.gpsproject.Persistence.AppDatabase;
import com.example.tadje.gpsproject.R;
import com.example.tadje.gpsproject.model.Locations;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tadje on 17.05.2018.
 */
//
//public class MapOptions{
//
//    List<Locations> locations;
//    double latitudefrom;
//    double latitudeto;
//    double longitudefrom;
//    double longitudeto;
//    double speedto;
//    double speed;
//    double speedKMHour;
//    Location location;
//    GoogleMap googleMap ;
//
//
//
//    public  void initializeLocationInformations(Location location) {
//        this.location = location;
//
//        locations = AppDatabase.getInstance().locationsDao().getAll();
//
//        if (locations.size() != 0 || locations.size() != 1) {
//
//
//            if (location != null) {
//                speed = location.getSpeed();
//                speedKMHour = ((location.getSpeed()) * 3600) / 1000;
//
//                afterTenMinutesStop();
//                createPolylinesBetweenToPoints();
//            }
//        }
//    }
//
//
//    private void afterTenMinutesStop() {
//        Timer timer = new Timer();
//        if (speed == 0.0) {
//            timer.scheduleAtFixedRate(new TimerTask() {
//                @Override
//                public void run() {
//                    MyLocationManager.getInstance().stopUsingGPS();
//                }
//            }, 0, 600000);
//        } else {
//            timer.cancel();
//        }
//    }
//
//
//    //Create polylines
//    public void createPolylinesBetweenToPoints() {
//
//        for (int i = 1; i <= locations.size() - 1; i++) {
//            int nextposition = i + 1;
//
//            latitudefrom = locations.get(i).getLatitude();
//            latitudeto = locations.get(nextposition).getLatitude();
//            longitudefrom = locations.get(i).getLongitude();
//            longitudeto = locations.get(nextposition).getLongitude();
//            speedto = locations.get(nextposition).getSpeed();
//            int polycolor = polylineColor(speedto);
//            Polyline polyline = googleMap.addPolyline(new PolylineOptions()
//                    .clickable(true)
//                    .add(
//                            new LatLng(latitudefrom, longitudefrom),
//                            new LatLng(latitudeto, longitudeto)
//                    )
//                    .color(polycolor)
//            );
//            polyline.setTag("");
//
//          MapsFragment.setmMap(googleMap);
//        }
//    }
//
//    //sets the polyline color depending on speed
//    private int polylineColor(double speedto) {
//        int speedInt;
//        int color = 0;
//
//        this.speed = speedto;
//        speedInt = (int) Math.round(speed);
//
//        if (speedInt <= 10) {
//            color = Color.parseColor("#99ff33");
//        } else if (speedInt >= 11 && speedInt <= 30) {
//            color = Color.parseColor("#ffff00");
//        } else if (speedInt >= 31 && speedInt <= 50) {
//            color = Color.parseColor("#ff9900");
//        } else if (speedInt >= 51) {
//            color = Color.parseColor("#ff3300");
//        }
//        return color;
//    }
//}
