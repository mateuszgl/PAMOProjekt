package com.example.pamo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pamo.R;
import com.example.pamo.db.DatabaseHelper;

public class ListViewActivity extends AppCompatActivity {

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ListView listView = (ListView) findViewById(R.id.list_view);

        db = new DatabaseHelper(getApplicationContext());

        final String[] names = db.getAllNames().toArray(new String[0]);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, names);

        listView.setAdapter(adapter);

        final Intent locationDetailsActivityIntent = new Intent(this, LocationDetailsActivity.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                locationDetailsActivityIntent.putExtra("locationName", names[position]);
                startActivity(locationDetailsActivityIntent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long arg3) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ListViewActivity.this);
                builder.setTitle("Usuwanie");
                builder.setMessage("Usunąć?");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int ii) {
                        adapter.remove(names[position]);
                        db.deleteLocation(names[position]);
                    }
                });

                builder.setNegativeButton("Nie", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int ii) {
                                dialog.dismiss();
                            }
                        }
                );
                builder.show();
                return true;
            }

        });
    }
}
