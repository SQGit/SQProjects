package com.emergencysavior.emergencysaviorwatch;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.sandro.restaurant.Restaurant;
import com.sloop.fonts.FontsManager;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by RSA on 07-06-2016.
 */
public class MapActivity extends Activity {
    private static Button trackingButton;
    private boolean currentlyTracking;
    private RadioGroup intervalRadioGroup;
    private int intervalInMinutes = 1;
    private AlarmManager alarmManager;
    private Intent gpsTrackerIntent;
    private PendingIntent pendingIntent;
    AVLoadingIndicatorView ballspin;
    private Typeface tf;
    public static final String EXTRA_IS_TRANSPARENT = "is_transparent";
    private boolean isTransparent;
    private String token, transaction_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpstracker);
        FontsManager.initFormAssets(this, "fonts/ques.otf");       //initialization
        FontsManager.changeFonts(this);
        intervalRadioGroup = (RadioGroup) findViewById(R.id.intervalRadioGroup);
        trackingButton = (Button) findViewById(R.id.trackingButton);
        ballspin = (AVLoadingIndicatorView) findViewById(R.id.gps);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.emergencysavior.gpstracker.prefs", Context.MODE_PRIVATE);
        currentlyTracking = sharedPreferences.getBoolean("currentlyTracking", false);
        boolean firstTimeLoadindApp = sharedPreferences.getBoolean("firstTimeLoadindApp", true);
        if (firstTimeLoadindApp) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTimeLoadindApp", false);
            editor.putString("appID", UUID.randomUUID().toString());
            editor.apply();
        }
        intervalRadioGroup.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        saveInterval();
                    }
                });
        trackingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                trackLocation(view);
            }
        });
    }

    private void saveInterval() {
        if (currentlyTracking) {
            Toast.makeText(getApplicationContext(), R.string.user_needs_to_restart_tracking, Toast.LENGTH_LONG).show();
        }

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.emergencysavior.gpstracker.prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (intervalRadioGroup.getCheckedRadioButtonId()) {
            case R.id.i1:
                editor.putInt("intervalInMinutes", 1);
                break;
            case R.id.i5:
                editor.putInt("intervalInMinutes", 5);
                break;
            case R.id.i15:
                editor.putInt("intervalInMinutes", 15);
                break;
        }

        editor.apply();
    }

    protected void trackLocation(View v) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.emergencysavior.gpstracker.prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (!saveUserSettings()) {
            return;
        }

        if (!checkIfGooglePlayEnabled()) {
            return;
        }

        if (currentlyTracking) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MapActivity.this);
            transaction_id = sharedPreferences.getString("transaction_id", "");
            Log.d("tag", "transaction id  = " + transaction_id);
            new Real_time_tracking_async_end(transaction_id).execute();
            cancelAlarmManager();

            currentlyTracking = false;
            editor.putBoolean("currentlyTracking", false);
            editor.putString("sessionID", "");
        } else {
            new Real_time_tracking_async_start().execute();
            startAlarmManager();
            currentlyTracking = true;
            editor.putBoolean("currentlyTracking", true);
            editor.putFloat("totalDistanceInMeters", 0f);
            editor.putBoolean("firstTimeGettingPosition", true);
            editor.putString("sessionID", UUID.randomUUID().toString());
        }

        editor.apply();
        setTrackingButtonState();
    }

    private boolean saveUserSettings() {


        SharedPreferences sharedPreferences = this.getSharedPreferences("com.emergencysavior.gpstracker.prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (intervalRadioGroup.getCheckedRadioButtonId()) {
            case R.id.i1:
                editor.putInt("intervalInMinutes", 1);
                break;
            case R.id.i5:
                editor.putInt("intervalInMinutes", 5);
                break;
            case R.id.i15:
                editor.putInt("intervalInMinutes", 15);
                break;
        }


        editor.apply();

        return true;
    }

    private boolean checkIfGooglePlayEnabled() {
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {
            return true;
        } else {
            Log.e("tag", "unable to connect to google play services.");
            Toast.makeText(getApplicationContext(), R.string.google_play_services_unavailable, Toast.LENGTH_LONG).show();
            return false;
        }
    }


    @Override
    public void onResume() {
        Log.d("tag", "onResume");
        super.onResume();

        displayUserSettings();
        setTrackingButtonState();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void displayUserSettings() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.emergencysavior.gpstracker.prefs", Context.MODE_PRIVATE);
        intervalInMinutes = sharedPreferences.getInt("intervalInMinutes", 1);

        switch (intervalInMinutes) {
            case 1:
                intervalRadioGroup.check(R.id.i1);
                break;
            case 5:
                intervalRadioGroup.check(R.id.i5);
                break;
            case 15:
                intervalRadioGroup.check(R.id.i15);
                break;
        }


    }

    private void setTrackingButtonState() {
        if (currentlyTracking) {
            trackingButton.setBackgroundResource(R.drawable.bluebutton);
            trackingButton.setTextColor(Color.WHITE);
            trackingButton.setText(R.string.tracking_is_on);
        } else {
            trackingButton.setBackgroundResource(R.drawable.orrangebutton);
            trackingButton.setTextColor(Color.WHITE);
            trackingButton.setText(R.string.tracking_is_off);
        }
    }

    private void startAlarmManager() {
        Log.d("tag", "startAlarmManager");

        Context context = getBaseContext();
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        gpsTrackerIntent = new Intent(context, GpsTrackerAlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, gpsTrackerIntent, 0);
        ballspin.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.emergencysavior.gpstracker.prefs", Context.MODE_PRIVATE);
        intervalInMinutes = sharedPreferences.getInt("intervalInMinutes", 1);

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                intervalInMinutes * 60000, // 60000 = 1 minute
                pendingIntent);
    }

    private void cancelAlarmManager() {
        Log.d("tag", "cancelAlarmManager");

        Context context = getBaseContext();
        ballspin.setVisibility(View.GONE);
        Intent gpsTrackerIntent = new Intent(context, GpsTrackerAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, gpsTrackerIntent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }


    class Real_time_tracking_async_start extends AsyncTask<String, Void, String> {

        public Real_time_tracking_async_start() {

        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";

            try {
                JSONObject jsonObject = new JSONObject();

                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return jsonStr = HttpUtils.makeRequest(CONFIG.URL + "/realtime/start", json, "wtdq97wwlsshfq17wz4v");
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
                Log.d("tag", "exception" + e.getMessage());
            }


            return null;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.d("tag", "<-----json result---->" + jsonStr);
            super.onPostExecute(jsonStr);

            if (jsonStr == "") {

                new Restaurant(MapActivity.this, "Internet connectivity issue", Snackbar.LENGTH_LONG)
                        .setBackgroundColor(Color.RED)
                        .setTextColor(Color.WHITE)
                        .show();
                Intent act_back = new Intent(getApplicationContext(), Locationview.class);
                startActivity(act_back);

            } else {

                try {

                    JSONObject jo = new JSONObject(jsonStr);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                    transaction_id = jo.getString("transaction_id");
                    Log.d("tag", "transaction_id " + transaction_id);
                    SharedPreferences s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor edit = s_pref.edit();
                    edit.remove("transaction_id");
                    edit.putString("transaction_id", transaction_id);
                    edit.apply();
                    if (status.equals("success")) {
                        Log.d("tag", "status  = " + status);

                    } else {
                        Log.d("tag", "status  = " + status);
                        new Restaurant(MapActivity.this, "Please try again", Snackbar.LENGTH_LONG)
                                .setBackgroundColor(Color.RED)
                                .setTextColor(Color.WHITE)
                                .show();
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
            }
        }

    }


    class Real_time_tracking_async_end extends AsyncTask<String, Void, String> {
        String transaction_id;

        public Real_time_tracking_async_end(String transaction_id) {

            this.transaction_id = transaction_id;

        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                Log.d("tag )))))))", transaction_id);
                jsonObject.accumulate("transaction_id", transaction_id);
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return jsonStr = HttpUtils.makeRequest(CONFIG.URL + "/realtime/end", json, "wtdq97wwlsshfq17wz4v");
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
                Log.d("tag", "exception" + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.d("tag", "<-----json result---->" + jsonStr);
            super.onPostExecute(jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jo = new JSONObject(jsonStr);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");

                    if (status.equals("success")) {
                        new Restaurant(MapActivity.this, "Tracking Stopped", Snackbar.LENGTH_LONG)
                                .setBackgroundColor(Color.RED)
                                .setTextColor(Color.WHITE)
                                .show();
                    } else {
                        new Restaurant(MapActivity.this, "Please try again", Snackbar.LENGTH_LONG)
                                .setBackgroundColor(Color.RED)
                                .setTextColor(Color.WHITE)
                                .show();
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();


                }

            }

        }
    }
}
