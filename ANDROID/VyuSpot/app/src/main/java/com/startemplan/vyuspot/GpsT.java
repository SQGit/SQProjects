package com.startemplan.vyuspot;


import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GpsT extends Service implements LocationListener {

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 30 * 1; // 1 minute
    private final Context mContext;
    // Declaring img_emg Location Manager
    protected LocationManager locationManager;
    // flag for GPS status
    boolean isGPSEnabled = false;
    // flag for network status
    boolean isNetworkEnabled = false;
    // flag for GPS status
    boolean canGetLocation = false;
    boolean canGetNetwork = false;
    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    String SAddress;


    public Geocoder geocoder;
    public List<Address> addresses;


    List<Address> addressList;


    public String city;



    public GpsT(Context context) {
        this.mContext = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled)
            {

          /*      SplashScreen sps = new SplashScreen();
                sps.buildAlertMessageNoGps();*/
                // no network provider is enabled
            }
            else
            {
                this.canGetLocation = true;
                this.canGetNetwork = true;
                // First get location from Network Provider
                //showSettings2Alert();

                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");

                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();


                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }




                Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());



                    addressList = geocoder.getFromLocation(latitude, longitude, 1);

                    Address address = addressList.get(0);



                    String addres = address.getAddressLine(0);
                    String addres1 = address.getAddressLine(1);
                    String addres2 = address.getAddressLine(2);
                    String addres3 = address.getAddressLine(3);
                    Log.d("taag",addres);
                    Log.d("taag",addres1);
                    Log.d("taag",addres2);
//                    Log.d("taag",addres3);



               // Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());


                // addressList = geocoder.getFromLocation(latitude, longitude, 1);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GpsT.this);
        }
    }

    /**
     * Function to get latitude
     */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    public String getCity() {
        try
        {

            geocoder = new Geocoder(GpsT.this, Locale.getDefault());
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            city= addresses.get(0).getAddressLine(0);
            Log.d("city",city);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return city;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }



    public String getAddress() {
        if (location != null) {

            //Address address = addressList.get(0);

            try {
                Address address = addressList.get(0);


                String addres = address.getAddressLine(0);
                String addres1 = address.getAddressLine(1);
                SAddress = addres + ", " + addres1;
                Log.d("taag", SAddress);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        // return longitude
        return SAddress;
    }









    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }




    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    public void showSettings2Alert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("Network settings");

        // Setting Dialog Message
        alertDialog.setMessage("Net is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("wifi", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {

        getAddress();
       // int lat = (int) (location.getLatitude());
        //int lng = (int) (location.getLongitude());


        latitude = location.getLatitude();
        longitude = location.getLongitude();
        String ss = String.valueOf(latitude+longitude);
        Log.d("loc", "locationchanged");
        Log.d("loc", ss);

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("loc", "providerdisabled");
    }


    @Override
    public void onProviderEnabled(String provider) {
        Log.d("loc","provider enabled");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("loc","status changed");
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public boolean canGetNetwork() {
        return this.canGetNetwork;
    }
}