package com.example.pamo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class LocationListActivity extends FragmentActivity
        implements LocationListFragment.OnLocationSelectedListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_list);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            LocationListFragment firstFragment = new LocationListFragment();
            firstFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    public void onLocationSelected(String name) {

        if (getResources().getConfiguration().orientation == 2) {
            LocationDetailsFragment locationDetailsFragment = (LocationDetailsFragment)
                    getSupportFragmentManager().findFragmentById(R.id.location_details_fragment);
            locationDetailsFragment.setLocation(name);
            locationDetailsFragment.displayLocation();

        } else {
            LocationDetailsFragment newFragment = new LocationDetailsFragment();
            Bundle args = new Bundle();
            args.putString(LocationDetailsFragment.LOCATION_NAME_STRING, name);
            newFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}

