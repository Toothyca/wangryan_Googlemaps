package com.example.mymapsapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int mapType;
    private LocationManager locationManager;
    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;

    private static final long MIN_TIME_BW_UPDATES = 1000*5;
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATE = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sandiego = new LatLng(32.986370, -117.023490);
        mMap.addMarker(new MarkerOptions().position(sandiego).title("Marker in birthplace"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sandiego));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mapType = GoogleMap.MAP_TYPE_NORMAL;
    }

    public void getLocation()
    {
        try{
            locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            //get GPS status
            if(isGPSEnabled) Log.d("MyMaps", "getLocation: GPS is enabled");

            //get network status (cell tower + wifi)
            //ad code here to update isNetworkEnabled and output Log.d

            if(!isGPSEnabled && isNetworkEnabled) //no provider enabled
                Log.d("MyMaps", "getLocation");
            else
            {
                if(isNetworkEnabled){
                    //add Log.d here
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATE, locationListenerNetwork);
                }
                if(isGPSEnabled)
                {
                    //add Log.d here
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATE, locationListenerGps);
                }
            }
        }
        catch(Exception e) {
            // Log.d here
            e.printStackTrace();
        }
    }

    public void fun(View view)
    {
        if (mapType == 1)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            mapType = GoogleMap.MAP_TYPE_TERRAIN;
        }
        else
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mapType = GoogleMap.MAP_TYPE_NORMAL;
        }
    }
}
