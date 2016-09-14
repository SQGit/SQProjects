package com.startemplan.vyuspot;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.balysv.materialripple.MaterialRippleLayout;
import com.rey.material.widget.EditText;
import com.rey.material.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Salman on 5/20/2016.
 */
public class Admin_JoinGroup extends Activity {

    public static final int DIALOG_ERROR_CONNECTION = 1;
    protected Context context;
    ListView listview;
    GroupList_Adapter grp_apdapter;
    EditText et_city, et_area;
    ArrayList<String> city_list;
    String[] citys;
    double dlatitude, dlongitude;
    GpsT gps;
    MaterialRippleLayout mtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.joingroup);

        listview = (ListView) findViewById(R.id.lview);
        et_city = (EditText) findViewById(R.id.city);
        et_area = (EditText) findViewById(R.id.area);


        if (!isOnline(this)) {
            showDialog(DIALOG_ERROR_CONNECTION);
        } else {

            gps = new GpsT(Admin_JoinGroup.this);
            if (gps.canGetLocation()) {
                dlatitude = gps.getLatitude();
                dlongitude = gps.getLongitude();
                String Addr = gps.getAddress();
                et_city.setText(Addr);
            } else {

            }


        }




        mtp = (MaterialRippleLayout) findViewById(R.id.ripple);

        mtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });







        grp_apdapter = new GroupList_Adapter(getApplicationContext());
        listview.setAdapter(grp_apdapter);


        city_list = new ArrayList<>();

        city_list.add("thanjavur");
        city_list.add("chennai");
        city_list.add("trichy");
        city_list.add("madurai");
        city_list.add("karur");
        city_list.add("selam");
        city_list.add("connor");
        city_list.add("porur");

        citys = new String[]{"thanjavur", "chennai", "trichy", "madurai", "karur", "selam"};


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, city_list);

        et_city.setAdapter(arrayAdapter);
        et_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                et_city.showDropDown();
            }
        });


    }


    public boolean isOnline(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni != null && ni.isConnected())
            return true;
        else
            return false;
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DIALOG_ERROR_CONNECTION:
                AlertDialog.Builder errorDialog = new AlertDialog.Builder(this);
                errorDialog.setTitle("Error");
                errorDialog.setMessage("No internet connection.");

                errorDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                        //finish();
                    }
                });

                errorDialog.setNeutralButton("Cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent goD = new Intent(getApplicationContext(), Dashboard.class);
                                startActivity(goD);
                                //finish();
                                //dialog.dismiss();
                            }
                        });

                AlertDialog errorAlert = errorDialog.create();
                return errorAlert;

            default:
                break;
        }
        return dialog;
    }


}
