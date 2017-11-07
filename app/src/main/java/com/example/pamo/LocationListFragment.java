package com.example.pamo;

import android.support.v4.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.pamo.db.DatabaseHelper;

import java.util.List;

public class LocationListFragment extends ListFragment {
    public static final String ALERT_DIALOG_TITLE = "Usuwanie";
    public static final String ALERT_DIALOG_TEXT = "Usunąć?";
    public static final String ALERT_DIALOG_YES = "Tak";
    public static final String ALERT_DIALOG_NO = "Nie";
    public OnLocationSelectedListener mCallback;
    private DatabaseHelper db;
    private List<String> names;

    interface OnLocationSelectedListener {
        void onLocationSelected(String name);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DatabaseHelper(getActivity());

        names = db.getAllNames();
        setListAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_activated_1, names));

    }

    @Override
    public void onStart() {
        super.onStart();

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long arg3) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(ALERT_DIALOG_TITLE);
                builder.setMessage(ALERT_DIALOG_TEXT);
                builder.setIconAttribute(android.R.attr.alertDialogIcon);

                builder.setPositiveButton(ALERT_DIALOG_YES, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int ii) {
                        db.deleteLocation(names.get(position));
                        names.remove(position);
                        setListAdapter(new ArrayAdapter<>(getActivity(),
                                android.R.layout.simple_list_item_activated_1, names));
//                        getListAdapter().remove(names.get(position));
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

        if (getFragmentManager().findFragmentById(R.id.location_details_fragment) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnLocationSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnWebAddressSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d("aAA", "onListItemClick: "+position+names.get(position));
        mCallback.onLocationSelected(names.get(position));
        getListView().setItemChecked(position, true);
    }
}
