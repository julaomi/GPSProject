package com.example.tadje.gpsproject;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tadje.gpsproject.Maps.LocationChangedListener;
import com.example.tadje.gpsproject.Maps.LocationManager;
import com.example.tadje.gpsproject.Persistence.AppDatabase;
import com.example.tadje.gpsproject.model.Locations;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.tadje.gpsproject.R.id.mapView;


@SuppressLint("ValidFragment")
public class MapsFragment extends Fragment implements OnMapReadyCallback, LocationSource.OnLocationChangedListener {

    private MapView viewMap;
    public GoogleMap mMap;
    private List<Locations> locations;
    private double latitudefrom;
    private double latitudeto;
    private double longitudefrom;
    private double longitudeto;
    private double speedto;
    private double speed;
    private double speedKMHour;

    Location location;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        viewMap = view.findViewById(mapView);
        viewMap.onCreate(savedInstanceState);
        MapsInitializer.initialize(this.getActivity());
        viewMap.getMapAsync(this);

        FloatingActionButton floatingActionButton = this.getActivity().findViewById(R.id
                .positionButton);
        floatingActionButton.setVisibility(View.VISIBLE);

        return view;
    }


    @Override
    public void onResume() {
        viewMap.onResume();
        super.onResume();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng ckc = new LatLng(52.251823, 10.515543);
        GroundOverlayOptions ckcpio = new GroundOverlayOptions().image(BitmapDescriptorFactory
                .fromResource(R.drawable.ckc_group)).position(ckc, 80f, 60f);
        mMap.addGroundOverlay(ckcpio);
        mMap.addMarker(new MarkerOptions().position(ckc).title("ckc Group Braunschweig"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ckc));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
    }



    @Override
    public void onLocationChanged(Location location) {
            checkSpeedFromLocationsForPolylines();
    }







    public interface OnFragmentInteractionListener {
    }

    public void setmMap(GoogleMap mMap) {
        this.mMap = mMap;
    }

    public GoogleMap getmMap() {
        return mMap;
    }

//    final Handler handler = new Handler();
//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            checkSpeedFromLocationsForPolylines();
//            handler.postDelayed(runnable, 1000);
//        }
//
//    };



    public void checkSpeedFromLocationsForPolylines() {

        locations = AppDatabase.getInstance().locationsDao().getAll();

        if (locations.size() != 0 || locations.size() != 1) {
            createPolylinesBetweenToPoints();
        }
    }



    //Create polylines
    public void createPolylinesBetweenToPoints() {


        for (int i = 0; i <= (locations.size() - 2); i++) {
            int nextposition = i + 1;


            speedKMHour = ((locations.get(i).getSpeed()) * 3600) / 1000;
            afterTenMinutesStopRecordingLocations();

            latitudefrom = locations.get(i).getLatitude();
            latitudeto = locations.get(nextposition).getLatitude();
            longitudefrom = locations.get(i).getLongitude();
            longitudeto = locations.get(nextposition).getLongitude();
            speedto = locations.get(nextposition).getSpeed();

            int polycolor = polylineColor(speedto);

            Polyline polyline = mMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .add(
                            new LatLng(latitudefrom, longitudefrom),
                            new LatLng(latitudeto, longitudeto)
                    )
                    .color(polycolor)
            );

            polyline.setTag("");
        }
        setmMap(mMap);
    }


    private void afterTenMinutesStopRecordingLocations() {
        Timer timer = new Timer();
        if (speedKMHour == 0.0) {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    LocationManager.getInstance().stopUsingGPS();
                }
            }, 0, 600000);
        } else {
            timer.cancel();
        }
    }

    //sets the polyline color depending on speed
    private int polylineColor(double speedto) {
        int speedInt;
        int color = 0;

        this.speed = speedto;
        speedInt = (int) Math.round(speed);

        if (speedInt <= 10) {
            color = Color.parseColor("#99ff33");
        } else if (speedInt >= 11 && speedInt <= 30) {
            color = Color.parseColor("#ffff00");
        } else if (speedInt >= 31 && speedInt <= 50) {
            color = Color.parseColor("#ff9900");
        } else if (speedInt >= 51) {
            color = Color.parseColor("#ff3300");
        }
        return color;
    }
}

