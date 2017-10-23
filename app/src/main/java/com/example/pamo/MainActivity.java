package com.example.pamo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button mapsActivityButton = (Button) findViewById(R.id.maps_activity_button);
        final Button listViewActivityButton = (Button) findViewById(R.id.list_view_button);

        final Intent secondActivityIntent = new Intent(this, AddLocationActivity.class);
        final Intent mapsActivityIntent = new Intent(this, MapsActivity.class);

        final Intent listViewActivityIntent = new Intent(this, ListViewActivity.class);

        listViewActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(listViewActivityIntent);
            }
        });

        mapsActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mapsActivityIntent);
            }
        });

    }
}
