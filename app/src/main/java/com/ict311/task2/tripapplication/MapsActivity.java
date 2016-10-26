package com.ict311.task2.tripapplication;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity {
int tripID;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db=new DatabaseHelper(this);
        tripID=Integer.parseInt(getIntent().getStringExtra("tripID"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        double lat=db.getTripLatitude(tripID);
        double longi=db.getTripLongitude(tripID);
        List<Address> geoCodeMatches = null;
        try {
            geoCodeMatches = new Geocoder(this).getFromLocation(lat,longi,1);


            double latitude=0.0;
            double longitude=0.0;
            if (!geoCodeMatches.isEmpty())
            {
                latitude = geoCodeMatches.get(0).getLatitude();
                longitude = geoCodeMatches.get(0).getLongitude();
            }

            LatLng LatLng = new LatLng(latitude, longitude);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(18));
            mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Marker"));
        } catch (IOException e) {



            e.printStackTrace();
        }
    }
}
