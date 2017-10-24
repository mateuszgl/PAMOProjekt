package com.example.pamo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pamo.R;
import com.example.pamo.db.DatabaseHelper;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    public static final String ALERT_DIALOG_TITLE = "Usuwanie";
    public static final String ALERT_DIALOG_TEXT = "Usunąć?";
    public static final String ALERT_DIALOG_YES = "Tak";
    public static final String ALERT_DIALOG_NO = "Nie";
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ListView listView = (ListView) findViewById(R.id.list_view);

        db = new DatabaseHelper(getApplicationContext());

        final ArrayList<String> names = db.getAllNames();

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, names);

        listView.setAdapter(adapter);

        final Intent locationDetailsActivityIntent = new Intent(this, LocationDetailsActivity.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                locationDetailsActivityIntent.putExtra("locationName", names.get(position));
                startActivity(locationDetailsActivityIntent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long arg3) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ListViewActivity.this);
                builder.setTitle(ALERT_DIALOG_TITLE);
                builder.setMessage(ALERT_DIALOG_TEXT);
                builder.setIconAttribute(android.R.attr.alertDialogIcon);

                builder.setPositiveButton(ALERT_DIALOG_YES, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int ii) {
                        db.deleteLocation(names.get(position));
                        adapter.remove(names.get(position));
                    }
                });

                builder.setNegativeButton(ALERT_DIALOG_NO, new DialogInterface.OnClickListener()
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
