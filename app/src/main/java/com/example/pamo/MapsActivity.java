package com.example.pamo;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String EMPTY_STRING = "";
    public static final String LATITUDE_STRING = "latitude";
    public static final String LONGITUDE_STRING = "longitude";
    private GoogleMap mMap;
    private LatLng currentSearchLocation;
    public static final String NO_PREVIOUS_SEARCH_ERROR = "Musisz wyszukać lokalizacje do zapisania.";
    public static final String NO_SEARCH_QUERY_ERROR = "Podaj nazwę miejsca do wyszukania.";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final Button searchButton = (Button) findViewById(R.id.search_location_button);
        final Button saveButton = (Button) findViewById(R.id.save_location_button);
        final EditText mapsActivityEditText = (EditText) findViewById(R.id.maps_activity_search_text);

        final Intent addLocationActivityIntent = new Intent(this, AddLocationActivity.class);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = mapsActivityEditText.getText().toString();
                search(location);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentSearchLocation!=null){
                    addLocationActivityIntent.putExtra(LATITUDE_STRING, currentSearchLocation.latitude);
                    addLocationActivityIntent.putExtra(LONGITUDE_STRING, currentSearchLocation.longitude);
                    startActivity(addLocationActivityIntent);
                } else {
                    Toast.makeText(MapsActivity.this, NO_PREVIOUS_SEARCH_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void search(String location){

        List<Address> addressList;

        if(!location.equals(EMPTY_STRING)) {
            Geocoder geocoder = new Geocoder(this);

            try {
                addressList = geocoder.getFromLocationName(location, 1);
                Address address = addressList.get(0);
                currentSearchLocation = new LatLng(address.getLatitude(), address.getLongitude());

                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(currentSearchLocation).title(location));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentSearchLocation, 10));

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(MapsActivity.this, NO_SEARCH_QUERY_ERROR, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}
