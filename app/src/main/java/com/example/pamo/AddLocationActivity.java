package com.example.pamo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.pamo.R;
import com.example.pamo.db.DatabaseHelper;

public class AddLocationActivity extends AppCompatActivity {

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        double latitude = getIntent().getDoubleExtra("latitude",0);
        double longitude = getIntent().getDoubleExtra("longitude",0);


        final TextView secondActivityTextView = (TextView) findViewById(R.id.second_activity_text_view);

    }
}
