package com.emerengencysavior.emergencysavior;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import cn.pedant.SweetAlert.SweetAlertDialog;

//import com.google.android.gms.common.GooglePlayServicesUtil;

public class LocationService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    private static final String TAG = "LocationService";
    final static String MY_ACTION = "MY_ACTION";
    // use the emergencysavior defaultUploadWebsite for testing and then check your
    // location with your browser here: https://www.emergencysavior.com/gpstracker/displaymap.php
    private String defaultUploadWebsite;
    private SweetAlertDialog dialog;

    private boolean currentlyProcessingLocation = false;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;

    private String token;
    private String transaction_id;
    private double latitude,longitude;
    private StringBuilder addressline;


    @Override
    public void onCreate() {
        super.onCreate();

        defaultUploadWebsite = getString(R.string.default_upload_website);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // if we are currently trying to get a location and the alarm manager has called this again,
        // no need to start processing a new location.
        if (!currentlyProcessingLocation) {
            currentlyProcessingLocation = true;
            startTracking();
        }

        return START_NOT_STICKY;
    }

    private void startTracking() {
        Log.d(TAG, "startTracking");

        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {

            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            if (!googleApiClient.isConnected() || !googleApiClient.isConnecting()) {
                googleApiClient.connect();
            }
        } else {
            Log.e(TAG, "unable to connect to google play services.");
        }
    }

    protected void sendLocationDataToWebsite(Location location) {
        // formatted for mysql datetime format
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getDefault());
        Date date = new Date(location.getTime());

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.emergencysavior.gpstracker.prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        float totalDistanceInMeters = sharedPreferences.getFloat("totalDistanceInMeters", 0f);

        boolean firstTimeGettingPosition = sharedPreferences.getBoolean("firstTimeGettingPosition", true);

        if (firstTimeGettingPosition) {
            editor.putBoolean("firstTimeGettingPosition", false);
        } else {
            Location previousLocation = new Location("");
            previousLocation.setLatitude(sharedPreferences.getFloat("previousLatitude", 0f));
            previousLocation.setLongitude(sharedPreferences.getFloat("previousLongitude", 0f));

            float distance = location.distanceTo(previousLocation);
            totalDistanceInMeters += distance;
            editor.putFloat("totalDistanceInMeters", totalDistanceInMeters);
        }

        editor.putFloat("previousLatitude", (float) location.getLatitude());
        editor.putFloat("previousLongitude", (float) location.getLongitude());
        editor.apply();

        final RequestParams requestParams = new RequestParams();
        requestParams.put("latitude", Double.toString(location.getLatitude()));
        requestParams.put("longitude", Double.toString(location.getLongitude()));

        Double speedInMilesPerHour = location.getSpeed() * 2.2369;
        requestParams.put("speed", Integer.toString(speedInMilesPerHour.intValue()));

        try {
            requestParams.put("date", URLEncoder.encode(dateFormat.format(date), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
        }

        requestParams.put("locationmethod", location.getProvider());

        if (totalDistanceInMeters > 0) {
            requestParams.put("distance", String.format("%.1f", totalDistanceInMeters / 1609)); // in miles,
        } else {
            requestParams.put("distance", "0.0"); // in miles
        }


        Double accuracyInFeet = location.getAccuracy() * 3.28;
        requestParams.put("accuracy", Integer.toString(accuracyInFeet.intValue()));

        Double altitudeInFeet = location.getAltitude() * 3.28;
        requestParams.put("extrainfo", Integer.toString(altitudeInFeet.intValue()));

        requestParams.put("eventtype", "android");

        Float direction = location.getBearing();
        requestParams.put("direction", Integer.toString(direction.intValue()));

        final String uploadWebsite = sharedPreferences.getString("defaultUploadWebsite", defaultUploadWebsite);

        LoopjHttpClient.get(uploadWebsite, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                // LoopjHttpClient.debugLoopJ(TAG, "sendLocationDataToWebsite - success", uploadWebsite, requestParams, responseBody, headers, statusCode, null);
                stopSelf();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] errorResponse, Throwable e) {
                // LoopjHttpClient.debugLoopJ(TAG, "sendLocationDataToWebsite - failure", uploadWebsite, requestParams, errorResponse, headers, statusCode, e);
                stopSelf();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.e(TAG, "position: " + location.getLatitude() + ", " + location.getLongitude() + " accuracy: " + location.getAccuracy());

            latitude = location.getLatitude();
            longitude = location.getLongitude();
            SharedPreferences sharedPreferences = this.getSharedPreferences("com.emergencysavior.gpstracker.prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("latitude", "" + location.getLatitude());
            editor.putString("longitude", "" + location.getLongitude());



            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            try {

                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

                if (addresses != null && addresses.size() > 0) {
                    Address address = addresses.get(0);
                     addressline = new StringBuilder();
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {

                    }
                    addressline.append(addresses.get(0).getAddressLine(0)).append(",");
                    addressline.append(addresses.get(0).getLocality()).append(",");
                    addressline.append(addresses.get(0).getCountryName());
                    editor.remove("addressLine");
                    editor.putString("addressLine", String.valueOf(addressline));

                    editor.apply();
                    editor.commit();
                    Log.d("tag", "Session_token =" + token);
                    Log.d("tag", "transaction_id =" + transaction_id);
                    Log.d("tag", "latitude =" + latitude);
                    Log.d("tag", "Longitude =" + longitude);
                    Log.d("tag", "Longitude =" + addressline);

                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                editor.remove("addressLine");
                editor.putString("addressLine", "Canont get Address!");
                editor.apply();
                editor.commit();

            }

                new Real_time_tracking_async_update(latitude,longitude,addressline).execute();

           // new MyActivityAsync_Real_time_tracking_update(latitude,Longitude).execute();

            // we have our desired accuracy of 500 meters so lets quit this service,
            // onDestroy will be called and stop our location uodates
            if (location.getAccuracy() < 500.0f) {

                stopLocationUpdates();

                sendLocationDataToWebsite(location);
            }
        }
    }

    private void stopLocationUpdates() {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected");

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000); // milliseconds
        locationRequest.setFastestInterval(1000); // the fastest rate in milliseconds at which your app can handle location updates
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed");

        stopLocationUpdates();
        stopSelf();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "GoogleApiClient connection has been suspend");
    }

     class Real_time_tracking_async_update extends AsyncTask<String, Void, String> {

        double latitude,longitude;
        StringBuilder addressline;
        public Real_time_tracking_async_update(double latitude, double longitude, StringBuilder addressline) {

            this.latitude=latitude;
            this.longitude=longitude;
            this.addressline=addressline;

        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            token = sharedPreferences.getString("Session_token", "");
            transaction_id = sharedPreferences.getString("transaction_id", "");

            Log.d("tag", "Sesstion token =" + token);
            Log.d("tag", "transaction_id =" + transaction_id);
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("latitude", latitude);
                jsonObject.accumulate("Longitude", longitude);
                jsonObject.accumulate("transaction_id", transaction_id);
                jsonObject.accumulate("location",addressline);

                Log.d("tag", "Session_token =" + token);
                Log.d("tag", "transaction_id =" + transaction_id);
                Log.d("tag", "latitude =" + latitude);
                Log.d("tag", "longitude =" + longitude);
                Log.d("tag", "addressline =" + addressline);


                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return jsonStr = HttpUtils.makeRequest(CONFIG.URL + "/realtime/update", json, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
                Log.d("tag", "exception" + e.getMessage());
            }
            return null;
        }

         @Override
         protected void onPostExecute(String jsonStr) {
             super.onPostExecute(jsonStr);
             Log.d("tag", "Longitude =" + jsonStr);
             if (jsonStr == "") {


                 Intent act_back = new Intent(getApplicationContext(), MainActivity.class);
                 startActivity(act_back);

             }
         }
     }
}
