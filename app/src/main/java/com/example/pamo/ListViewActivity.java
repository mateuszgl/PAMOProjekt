package com.example.pamo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.pamo.R;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ListView listView = (ListView) findViewById(R.id.list_view);

        final String[] pages = new String[3];
        pages[0] = "http://www.gdansk.pjwstk.edu.pl";
        pages[1] = "http://www.pjwstk.edu.pl";
        pages[2] = "http://www.krzysztofpawlowski.info";

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, pages);

        listView.setAdapter(adapter);

        final Intent webViewIntent = new Intent(this, WebViewActivity.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                webViewIntent.putExtra("address", pages[position]);
                startActivity(webViewIntent);
            }
        });
    }
}
