package com.example.pamo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.pamo.R;

public class LocationDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        String name = getIntent().getStringExtra("name");
        final TextView secondActivityTextView = (TextView) findViewById(R.id.second_activity_text_view);

        secondActivityTextView.setText(name);

    }
}
