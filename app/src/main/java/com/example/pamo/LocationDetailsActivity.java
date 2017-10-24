package com.example.pamo;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.pamo.R;
import com.example.pamo.db.DatabaseHelper;
import com.example.pamo.entities.Location;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationDetailsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseHelper db;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        db = new DatabaseHelper(getApplicationContext());

        String name = getIntent().getStringExtra("locationName");
        final TextView locationDetailsActivityName = (TextView) findViewById(R.id.location_details_activity_name);
        final TextView locationDetailsActivityDescription = (TextView) findViewById(R.id.location_details_activity_description);

        location = db.getByName(name);

        locationDetailsActivityName.setText(location.getName());
        locationDetailsActivityDescription.setText(location.getDescrition());

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        LatLng locationCoords = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(locationCoords).title(location.getName()));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationCoords, 10));
    }
}
