package com.example.tadje.gpsproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import com.example.tadje.gpsproject.Maps.LocationManager;
import com.example.tadje.gpsproject.Persistence.AppDatabase;
import com.example.tadje.gpsproject.ViewPager.ViewPagerAdapter;
import com.example.tadje.gpsproject.model.Trip;
import com.example.tadje.gpsproject.model.User;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;


public class MainActivity extends AppCompatActivity implements MapsFragment
        .OnFragmentInteractionListener, SettingFragment.OnFragmentInteractionListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, UserFragment.OnFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private ViewPagerAdapter mViewPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    private ViewPager mViewPager;
    private boolean LocationAvailable = false;
    boolean buttonStartStop = false;
    private Location location;
    private FloatingActionButton positionButton;
    private int tripNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = this;
        AppDatabase.getInstance(context);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mViewPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        TripManager.getInstance().setSelectedUserID(1);
        TripManager.getInstance().setTripNumber(1);

        if (checkPermission()) {
            location = LocationManager.getInstance().locationInitialize(context);
            startTripIfLocationHaveSpeed(location);
        }

        positionButton = findViewById(R.id.positionButton);
        positionButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOrStopOnButtonClick();
            }
        }));

    }


    //Button start and stop
    private void startOrStopOnButtonClick() {

        if (!buttonStartStop) {
            buttonStartStop = true;
            Toast.makeText(this, getString(R.string.startTrip), Toast.LENGTH_LONG).show();
            startNewTrip();


        } else {
            buttonStartStop = false;
            Toast.makeText(this, getString(R.string.stopTrip), Toast.LENGTH_LONG).show();
            LocationManager.getInstance().stopUsingGPS();
        }
    }


    private void startTripIfLocationHaveSpeed(Location location) {
        this.location = location;

        if (location != null) {
            double speedKMHour = ((location.getSpeed()) * 3600) / 1000;

            if (speedKMHour >= 5.0) {
                startNewTrip();
            }
        }
    }


    private void startNewTrip() {

        List<Trip> existTripList = AppDatabase.getInstance().tripDao().getAll();
        List<User> existUserList = AppDatabase.getInstance().userDao().getAll();
        Trip trip;

        if (existTripList.size() != 0) {

            tripNumber = TripManager.getInstance().getTripNumber();
            TripManager.getInstance().setTripNumber(tripNumber + 1);

        } else {
            //Add tripNumber by first time open
            tripNumber = 1;
            TripManager.getInstance().setTripNumber(tripNumber);

        }


        if (existUserList.size() != 0) {

            int userID = TripManager.getInstance().getSelectedUserID();
            trip = new Trip(tripNumber, userID);

        } else {

            //Add a user by first time open
            User user = new User("Test", "User", "05.03.2018");
            AppDatabase.getInstance().userDao().insertAll(user);
            trip = new Trip(tripNumber, user.getId());
        }

        //Add a new trip to the database
        AppDatabase.getInstance().tripDao().insertAll(trip);

        // Go in the Location Manager to get a location and fill the location in the database
        LocationManager.getInstance().locationInitialize(this);
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {
            LocationAvailable = true;
            return true;
        } else {
            LocationAvailable = false;
            return false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}


