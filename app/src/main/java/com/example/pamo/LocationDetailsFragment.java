package com.example.pamo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pamo.db.DatabaseHelper;
import com.example.pamo.entities.Location;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationDetailsFragment extends Fragment implements OnMapReadyCallback {

    public static final String LOCATION_NAME_STRING = "locationName";

    private GoogleMap mMap;
    private View mView;
    private DatabaseHelper db;
    private Location location;
    private TextView locationDetailsActivityName;
    private TextView locationDetailsActivityDescription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_location_details, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        db = new DatabaseHelper(getActivity());


        locationDetailsActivityName = (TextView) mView.findViewById(R.id.location_details_activity_name);
        locationDetailsActivityDescription = (TextView) mView.findViewById(R.id.location_details_activity_description);

        if(getArguments() != null) {
            String name = getArguments().getString(LOCATION_NAME_STRING);
            setLocation(name);
        }
        return mView;
    }

    public void setLocation(String name) {
        location = db.getByName(name);
        locationDetailsActivityName.setText(location.getName());
        locationDetailsActivityDescription.setText(location.getDescrition());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        if(location != null) {
            displayLocation();
        }
    }

    public void displayLocation() {
        LatLng locationCoords = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(locationCoords).title(location.getName()));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationCoords, 10));
    }
}
