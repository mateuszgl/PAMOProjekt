package com.example.pamo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pamo.db.DatabaseHelper;
import com.example.pamo.entities.Location;

public class AddLocationActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private static final String FIELDS_REQUIRED_ERROR = "Musisz wypełnić wszystkie pola.";
    private static final String NAME_TAKEN_ERROR = "Miejsce o tej nazwie juz istnieje.";
    private static final String SAVED = "ZAPISANO";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        db = new DatabaseHelper(getApplicationContext());

        final EditText addLocationActivityName = (EditText) findViewById(R.id.add_location_activity_name);
        final EditText addLocationActivityDescription = (EditText) findViewById(R.id.add_location_activity_description);
        final Button addLocationActivityCancelButton = (Button) findViewById(R.id.add_location_activity_cancel_button);
        final Button addLocationActivitySaveButton = (Button) findViewById(R.id.add_location_activity_save_button);

        final double latitude = getIntent().getDoubleExtra("latitude",0);
        final double longitude = getIntent().getDoubleExtra("longitude",0);

        final Intent mainActivityIntent = new Intent(this, MainActivity.class);

        addLocationActivityCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mainActivityIntent);
            }
        });

        addLocationActivitySaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = addLocationActivityName.getText().toString();
                String description = addLocationActivityDescription.getText().toString();
                if(name.equals("")|| description.equals("")) {
                    Toast.makeText(AddLocationActivity.this, FIELDS_REQUIRED_ERROR, Toast.LENGTH_SHORT).show();
                } else {
                    if(db.getByName(name) != null){
                        Toast.makeText(AddLocationActivity.this, NAME_TAKEN_ERROR, Toast.LENGTH_SHORT).show();
                    } else {

                        Location location = new Location();
                        location.setLatitude(latitude);
                        location.setLongitude(longitude);
                        location.setName(name);
                        location.setDescrition(description);

                        db.insert(location);

                        startActivity(mainActivityIntent);
                        Toast.makeText(AddLocationActivity.this, SAVED, Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }
}
