package com.emergencysavior.emergencysaviorwatch;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import at.markushi.ui.CircleButton;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by RSA on 16-06-2016.
 */
public class Locationview extends Activity  {
    GoogleMap googleMap;
    LocationManager locationManager;
    ImageView image_map;

    double latitude, longitude;
    public static Location loc;
    TextView txt_address;
    CircleButton circle_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        image_map = (ImageView) findViewById(R.id.image_map);
        circle_continue = (CircleButton) findViewById(R.id.circle_continue);
        txt_address = (TextView) findViewById(R.id.txt_address);


        MapFragment fm = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

        circle_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
            }
        });

        if (Util.Operations.isOnline(Locationview.this)) {
            MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
                @Override
                public void gotLocation(final Location location) {
                    SharedPreferences pref = getPreferences(0);
                    SharedPreferences.Editor edit = pref.edit();
                    loc = location;
                    System.out.println("Latitude: " + loc.getLatitude());
                    System.out.println("Longitude: " + loc.getLongitude());
                    latitude = loc.getLatitude();
                    longitude = loc.getLongitude();
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(Locationview.this, Locale.getDefault());

                    try {
                        addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();

                        edit.remove("address");
                        edit.remove("city");
                        edit.remove("state");
                        edit.remove("Latitude");
                        edit.remove("Longitude");
                        edit.putString("Latitude", String.valueOf(loc.getLatitude()));
                        edit.putString("Longitude", String.valueOf(loc.getLongitude()));
                        edit.putString("address", address);
                        edit.putString("city", city);
                        edit.putString("state", state);
                        edit.apply();
                        txt_address.setText(address + "," + city + "," + state);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("tag", "cannot get Address!");
                    }


                }


            };
            MyLocation myLocation = new MyLocation();
            myLocation.getLocation(Locationview.this, locationResult);

        } else {

            new SweetAlertDialog(Locationview.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("ALERT")
                    .setContentText("Internet Connectivity very slow? try again")
                    .setConfirmText("ok")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(), Locationview.class);
                            startActivity(intent);

                        }
                    })
                    .show();
        }


        if (Util.Operations.isOnline(Locationview.this)) {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
            if (status != ConnectionResult.SUCCESS) {
                int requestCode = 10;
                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
                dialog.show();

            } else {


                googleMap = fm.getMap();
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String provider = locationManager.getBestProvider(criteria, true);
                Location location = locationManager.getLastKnownLocation(provider);

            }

        }
    }


}
