package com.example.leaditteam.cafeprototype.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.leaditteam.cafeprototype.helpers.Constant;
import com.example.leaditteam.cafeprototype.helpers.LocationHelper;
import com.example.leaditteam.cafeprototype.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    
    private GoogleMap mMap;
    
    private ArrayList<Double> mArListOfRoads = new ArrayList<>();
    private HashMap<Integer, LatLng> mLangHashmap = new HashMap<>();
    double myLot;
    double myLat;
    
    private HashMap<Integer, LocationHelper> mainHashmapForLocation = new HashMap<Integer, LocationHelper>();
    
    final private int requestCodePermission = 123;
    
    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        addActionBar();
        
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        
        
        getMainHashmap(savedInstanceState);
    }
    
    private void getMainHashmap(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle extas = getIntent().getExtras();
            if (extas != null) {
                mainHashmapForLocation  = (HashMap) extas.getSerializable(Constant.mainHashmapForLocation.name());
            }
        } else {
            mainHashmapForLocation  = (HashMap) savedInstanceState.getSerializable(Constant.mainHashmapForLocation.name());
        }
    
    }
    
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case requestCodePermission:
                mMap.setMyLocationEnabled(true);
                LocationManager locationManager = (LocationManager)
                        this.getSystemService(Context.LOCATION_SERVICE);
                create_location_listener(locationManager);
                break;
            default:
                Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar, menu);
        
        menu.findItem(R.id.filter).setVisible(false);
        menu.findItem(R.id.action_favorite).setVisible(false);
        
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onMapReady(GoogleMap map) {
        
        mMap = map;
        // Add some markers to the map, and add a data object to each marker.
        for (int i = 0; i != mainHashmapForLocation.size(); i++) {
            
            LatLng tempLat = new LatLng(mainHashmapForLocation.get(i).getLATITUDE(),
                    mainHashmapForLocation.get(i).getLONGITUDE());
            
            Marker mTempMarker = mMap.addMarker(new MarkerOptions()
                    .position(tempLat)
                    .title(mainHashmapForLocation.get(i).getTITLE()));
            mTempMarker.setTag(0);
            
            mLangHashmap.put(i, tempLat);
        }
        
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            
            mMap.setMyLocationEnabled(true);
            LocationManager locationManager = (LocationManager)
                    this.getSystemService(Context.LOCATION_SERVICE);
            create_location_listener(locationManager);
            
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    requestCodePermission);
        }
        
        
    }
    
    private void update_location(Location location) {
        myLat = location.getLatitude();
        myLot = location.getLongitude();
        
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(myLat, myLot)));
        double min_road = Double.MAX_VALUE;
        LatLng minLatLang = new LatLng(0, 0);
        for (int i = 0; i != mainHashmapForLocation.size(); i++) {
            
            mArListOfRoads.add(Math.sqrt((myLat - mLangHashmap.get(i).latitude) * (myLot - mLangHashmap.get(i).latitude)
                    + (myLot - mLangHashmap.get(i).longitude) * (myLot - mLangHashmap.get(i).longitude)));
            if (mArListOfRoads.get(i) < min_road) {
                min_road = mArListOfRoads.get(i);
                minLatLang = new LatLng(mLangHashmap.get(i).latitude, mLangHashmap.get(i).longitude);
            }
        }
        
        
        draw_line(new LatLng(myLat, myLot), minLatLang);
    }
    
    private void draw_line(LatLng from, LatLng destination) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(from);
        builder.include(destination);
        int padding = 100;
        
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.animateCamera(cu);
    }
    
    private void create_location_listener(LocationManager locationManager) {
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                update_location(location);
            }
            
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                
            }
            
            @Override
            public void onProviderEnabled(String provider) {
                
            }
            
            @Override
            public void onProviderDisabled(String provider) {
                
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                100,
                10,
                locationListener);
        Location amHere = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (amHere != null) {
            update_location(amHere);
        }
    }
    
    private void addActionBar() {
        //add Action Bar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0.8f);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        ///
    }
}
