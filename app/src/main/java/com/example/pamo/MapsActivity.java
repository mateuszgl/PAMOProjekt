package com.example.pamo;

import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pamo.R;
import com.example.pamo.db.DatabaseHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import entities.Location;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final Button changeTextButton = (Button) findViewById(R.id.search_location_button);
        final EditText mapsActivityEditText = (EditText) findViewById(R.id.maps_activity_edit_text);

        changeTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = mapsActivityEditText.getText().toString();
                search(location);
            }
        });
        db = new DatabaseHelper(getApplicationContext());
        Location location = new Location();
        location.setDescrition("descriptionnn");
        location.setName("nazwa1111");
        location.setLatitude(13);
        location.setLongitude(13);

        db.insert(location);

        Location location2 = new Location();
        location2.setDescrition("description22nn");
        location2.setName("nazw2222");
        location2.setLatitude(22);
        location2.setLongitude(22);

        db.insert(location2);


    }

    private void search(String location){

        List<Address> addressList;
        LatLng locationPos = new LatLng(0, 0);

        for(Location l : db.getAll()){
            Log.d("AAAAA", l.getName());
        }

        if(!location.equals("")) {
            Geocoder geocoder = new Geocoder(this);

            try {
                addressList = geocoder.getFromLocationName(location, 1);
                Address address = addressList.get(0);
                locationPos = new LatLng(address.getLatitude(), address.getLongitude());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mMap.addMarker(new MarkerOptions().position(locationPos).title(location));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(locationPos));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
