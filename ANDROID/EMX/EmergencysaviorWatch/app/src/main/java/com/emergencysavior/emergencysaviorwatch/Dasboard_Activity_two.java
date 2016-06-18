package com.emergencysavior.emergencysaviorwatch;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import at.markushi.ui.CircleButton;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by RSA on 06-06-2016.
 */
public class Dasboard_Activity_two extends Fragment implements View.OnClickListener {


    CircleButton btn_call, btn_Need_help, btn_active_shooter, btn_iamok;
    TextView txt_call, txt_help, txt_activeshooter, txt_mylocation;
    String tranctional_type, token, email, tranctional_id, str_audiostatus, str_gpsstatus, str_messagestatus, str_emailstatus, str_call911status;
    SweetAlertDialog pDialog;
    public static final String MyPREFERENCES = "MyPrefs";
    String message, firstname, gps_location, addressLine, city, state, uid,str_latitude,str_Longitude,str_address,str_city,str_state;
    public static Location loc;
    double latitude, longitude;
    private MediaRecorder myRecorder;
    private String outputFile = null;
    TimerTask timerTask;
    Timer timer;
    final Handler handler = new Handler();
    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dashboard_activity_two, container, false);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ques.otf");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        token = sharedPreferences.getString("session_token", "");
        firstname = sharedPreferences.getString("Firstname", "");
        email = sharedPreferences.getString("email", "");
        SharedPreferences pref = getActivity().getPreferences(0);
        str_audiostatus = pref.getString("audiostatus", "empty");
        str_gpsstatus = pref.getString("gpsstatus", "empty");
        str_messagestatus = pref.getString("messagestatus", "empty");
        str_emailstatus = pref.getString("emailstatus", "empty");
        str_call911status = pref.getString("call911status", "empty");

        str_latitude = pref.getString("Latitude", "empty");
        str_Longitude = pref.getString("Longitude", "empty");
        str_address = pref.getString("address", "empty");
        str_city = pref.getString("city", "empty");
        str_state = pref.getString("state", "empty");


        btn_call = (CircleButton) v.findViewById(R.id.call911);
        btn_Need_help = (CircleButton) v.findViewById(R.id.need_help);
        btn_active_shooter = (CircleButton) v.findViewById(R.id.activeshooter);
        btn_iamok = (CircleButton) v.findViewById(R.id.btn_iamok);
        txt_call = (TextView) v.findViewById(R.id.call);
        txt_help = (TextView) v.findViewById(R.id.help);
        txt_activeshooter = (TextView) v.findViewById(R.id.Act);
        txt_mylocation = (TextView) v.findViewById(R.id.txt_maplocation);
        txt_call.setTypeface(tf);
        txt_help.setTypeface(tf);
        txt_activeshooter.setTypeface(tf);
        txt_mylocation.setTypeface(tf);
        btn_call.setOnClickListener(this);
        btn_Need_help.setOnClickListener(this);
        btn_active_shooter.setOnClickListener(this);
        btn_iamok.setOnClickListener(this);
        if (Util.Operations.isOnline(getActivity())) {
            MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
                @Override
                public void gotLocation(final Location location) {
                    loc = location;
                    System.out.println("Latitude: " + loc.getLatitude());
                    System.out.println("Longitude: " + loc.getLongitude());
                    latitude = loc.getLatitude();
                    longitude = loc.getLongitude();
                    Log.d("tag", "latitude" + loc.getLatitude());
                    Log.d("tag", "longitude" + loc.getLongitude());

                }


            };
            MyLocation myLocation = new MyLocation();
            myLocation.getLocation(getActivity(), locationResult);

        } else {

            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("ALERT")
                    .setContentText("Internet Connectivity very slow? try again")
                    .setConfirmText("ok")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            Intent intent = new Intent(getActivity(), Locationview.class);
                            startActivity(intent);

                        }
                    })
                    .show();
        }

        return v;
    }

    public static Dasboard_Activity_two newInstance(String text) {

        Dasboard_Activity_two f = new Dasboard_Activity_two();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }


    @Override
    public void onClick(View v) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        AlertDialog alertDialog;
        switch (v.getId()) {

            case R.id.call911:
                alertDialogBuilder.setTitle("ALERT");
                alertDialogBuilder
                        .setMessage("Please confirm ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                tranctional_type = ("call911");
                                dialog.cancel();
                                new MyActivityAsync(tranctional_type).execute();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                break;
            case R.id.need_help:

                alertDialogBuilder.setTitle("ALERT");
                alertDialogBuilder
                        .setMessage("Please confirm ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                tranctional_type = ("need_help");
                                dialog.cancel();
                                new MyActivityAsync(tranctional_type).execute();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
            case R.id.activeshooter:
                alertDialogBuilder.setTitle("ALERT");
                alertDialogBuilder
                        .setMessage("Please confirm ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                tranctional_type = ("activeshooter");
                                dialog.cancel();
                                new MyActivityAsync(tranctional_type).execute();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                break;
            case R.id.btn_iamok:
                alertDialogBuilder.setTitle("ALERT");
                alertDialogBuilder
                        .setMessage("Please confirm")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                                tranctional_type = ("Iamok");
                                new MyActivityAsync(tranctional_type).execute();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                break;

        }
    }


    private class MyActivityAsync extends AsyncTask<String, Void, String> {
        String tranctional_type;

        public MyActivityAsync(String tranctional_type) {
            this.tranctional_type = tranctional_type;
        }

        protected void onPreExecute() {
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Please wait... ");
            pDialog.setCancelable(true);
            pDialog.getWindow().setLayout(500, 200);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("transaction_type", tranctional_type);
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return jsonStr = HttpUtils.makeRequest(CONFIG.URL + "/transactionstart", json, token);
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
            pDialog.dismiss();
            if (jsonStr == "") {

                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("ALERT")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();
            } else {

                try {
                    JSONObject jo = new JSONObject(jsonStr);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                    tranctional_id = jo.getString("transaction_id");
                    Log.d("tag", "tranctional_id" + tranctional_id);
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(getActivity(), Locale.getDefault());

                    try {
                        addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        addressLine = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        city = addresses.get(0).getLocality();
                        state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();

                        Log.d("tag", "Address2" + addressLine);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("tag", "cannot get Address!");
                    }

                    if (status.equals("success")) {
                        gps_location = (CONFIG.GPS_URL_LOCATION + loc.getLatitude() + "," + loc.getLongitude());

                        latitude = loc.getLatitude();
                        longitude = loc.getLongitude();


                        Log.d("tag", "gps_location" + gps_location);
                        switch (tranctional_type) {
                            case "call911":
                                if (str_messagestatus.equals("true")) {
                                    Log.d("tag", "Address2" + addressLine);
                                    message = (firstname + "has called 911,send police to:" + addressLine + "," + city + "," + state + " " + gps_location);
                                    Log.d("tag", "call 911 msg " + message);
                                    new messageasync(message).execute();
                                }
                                if (str_audiostatus.equals("true")) {
                                    message = (firstname + "has called 911,send police to:" + addressLine + "," + city + "," + state + " " + gps_location);
                                    new message_from_audio(message).execute();
                                }
                                if (str_emailstatus.equals("true")) {
                                    message = (firstname + "has called 911,send police to: " + addressLine + "," + city + "," + state + " " + gps_location);
                                    new message_from_email(message, latitude, longitude).execute();
                                }

                                if (str_call911status.equals("true")) {
                                    PhoneCallListener phoneListener = new PhoneCallListener();
                                    TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                                    telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:911"));
                                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        return;
                                    }
                                    startActivity(callIntent);

                                }
                                break;
                            case "need_help":
                                if (str_messagestatus.equals("true")) {
                                    message = (firstname + "needs help at: " + addressLine + "," + city + "," + state + " " + gps_location);

                                    new messageasync(message).execute();
                                }
                                if (str_audiostatus.equals("true")) {

                                    start();

                                }
                                if (str_emailstatus.equals("true")) {

                                    message = (firstname + "needs help at: " + addressLine + "," + city + "," + state + " " + gps_location);
                                    new message_from_email(message, latitude, longitude).execute();
                                }
                                break;
                            case "activeshooter":
                                if (str_messagestatus.equals("true")) {
                                    message = (firstname + " has Active Shooter at:" + addressLine + "," + city + "," + state + " " + gps_location);
                                    Log.d("tag", "call 911 msg " + message);
                                    new messageasync(message).execute();
                                }
                                if (str_audiostatus.equals("true")) {
                                    message = (firstname + " has Active Shooter at:" + addressLine + "," + city + "," + state + " " + gps_location);
                                    new message_from_audio(message).execute();
                                }
                                if (str_emailstatus.equals("true")) {
                                    message = (firstname + " has Active Shooter at:" + addressLine + " " + gps_location);
                                    new message_from_email(message, latitude, longitude).execute();
                                }
                                break;
                            case "Iamok":
                                if (str_messagestatus.equals("true")) {
                                    message = (firstname + "is Ok .. No Help Needed at:" + addressLine + "," + city + "," + state + " " + gps_location);
                                    new messageasync(message).execute();
                                }
                                if (str_audiostatus.equals("true")) {
                                    message = (firstname + "is Ok .. No Help Needed at:" + addressLine + "," + city + "," + state + " " + gps_location);
                                    new message_from_audio(message).execute();
                                }
                                if (str_emailstatus.equals("true")) {
                                    message = (firstname + "is Ok .. No Help Needed at:" + addressLine + "," + city + "," + state + " " + gps_location);
                                    new message_from_email(message, latitude, longitude).execute();
                                }
                                break;
                        }


                        SharedPreferences preferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        str_address = preferences.getString("addressLine", "");


                    } else {

                        Toast.makeText(getActivity(), "transction id", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void start() {


        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Emx/audio" + timeStamp + ".mp3";
            myRecorder = new MediaRecorder();
            myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            myRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            myRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            myRecorder.setOutputFile(outputFile);
            myRecorder.prepare();
            myRecorder.start();
            startTimer();
        } catch (IllegalStateException e) {

            e.printStackTrace();
        } catch (IOException e) {
            // prepare() fails
            e.printStackTrace();
        }

    }

    public void startTimer() {

        Toast.makeText(getActivity(), "Start Recording ", Toast.LENGTH_LONG).show();
        Log.d("tag", "Timer starting now ");
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 20000, 10000); //
    }

    public void initializeTimerTask() {
        Toast.makeText(getActivity(), "Stop Recording", Toast.LENGTH_LONG).show();
        Log.d("tag", "Timer starting initializeTimerTask ");
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        stop();

                        if (timer != null) {
                            timer.cancel();
                            timer = null;
                        }
                    }
                });
            }
        };
    }

    public void stop() {
        try {


            myRecorder.stop();
            myRecorder.reset();
            myRecorder.release();
            // myRecorder = null;

            new Uploadaudio().execute();
            Log.d("tag", "audiopath=====>" + outputFile);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> M E S S A G E  U P L O A D >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private class messageasync extends AsyncTask<String, Void, String> {
        String message;

        public messageasync(String message) {

            this.message = message;
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d("tag", "<-----tranctional_id---->" + tranctional_id);
            Log.d("tag", "What Message ==>" + message);
            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("message", message);
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return jsonStr = HttpUtils.makeRequest_transction(CONFIG.URL + "/dashboardtext", json, token, tranctional_id);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.d("tag", "<-----messageasync---->" + jsonStr);
            super.onPostExecute(jsonStr);
            pDialog.dismiss();
            if (CONFIG.isStringNullOrWhiteSpace(jsonStr)) {


            }
            try {
                JSONObject result = new JSONObject(jsonStr);
                String status = result.getString("status");
                String msg = result.getString("message");

                if (msg.indexOf("session_token") >= 0) {
                    TelephonyManager tManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                    uid = tManager.getDeviceId();
                    Log.d("tag", "uid" + uid);

                    new MyAsyncsessiontoken(uid).execute();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> M E S S A G E  U P L O A D >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> E M A I L   U P L O A D >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private class message_from_audio extends AsyncTask<String, Void, String> {

        String message;

        public message_from_audio(String message) {

            this.message = message;
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d("tag", "<-----message result---->" + tranctional_id);
            Log.d("tag", "message_from_audio==>" + message);
            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("message", message);
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return jsonStr = HttpUtils.makeRequest_transction(CONFIG.URL + "/dashboardaudio", json, token, tranctional_id);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.d("tag", "<-----message_from_audio---->" + jsonStr);
            super.onPostExecute(jsonStr);

            try {
                JSONObject result = new JSONObject(jsonStr);
                String status = result.getString("status");
                String msg = result.getString("message");

                if (msg.indexOf("session_token") >= 0) {
                    TelephonyManager tManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                    uid = tManager.getDeviceId();
                    Log.d("tag", "uid" + uid);

                    new MyAsyncsessiontoken(uid).execute();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> M E S S A G E A U D I O   U P L O A D >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> E M A I L   U P L O A D >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private class message_from_email extends AsyncTask<String, Void, String> {
        String message;
        double latitude, longitude;

        public message_from_email(String message, double latitude, double longitude) {
            this.message = message;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("message", message);
                jsonObject.accumulate("lat", latitude);
                jsonObject.accumulate("lang", longitude);
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return jsonStr = HttpUtils.makeRequest_transction(CONFIG.URL + "/dashboardemail", json, token, tranctional_id);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.d("tag", "<-----message_from_email---->" + jsonStr);
            super.onPostExecute(jsonStr);


            try {
                JSONObject result = new JSONObject(jsonStr);
                String status = result.getString("status");
                String msg = result.getString("message");

                if (msg.indexOf("session_token") >= 0) {
                    TelephonyManager tManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                    uid = tManager.getDeviceId();
                    Log.d("tag", "uid" + uid);

                    new MyAsyncsessiontoken(uid).execute();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> E M A I L   U P L O A D >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    /////////////////////////////////// P H O N E  C A L L  M O N I T O R //////////////////////////////////////////////////////////////////

    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                Log.i(LOG_TAG, "Rining, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                Log.i(LOG_TAG, "Offline");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                Log.i(LOG_TAG, "IDLE");
                if (isPhoneCalling) {

                    Intent i = new Intent(getActivity(), Dasboard_Activity_two.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    isPhoneCalling = false;
                }

            }
        }
    }


    private class MyAsyncsessiontoken extends AsyncTask<String, Void, String> {

        String uid;

        public MyAsyncsessiontoken(String uid) {
            this.uid = uid;
        }


        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("device_id", uid);
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest("http://api.emergencysavior.com/webapi/emx/qrwear/sessionid", json);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("tag", "<-----sessiontoken---->" + s);
            try {
                JSONObject result = new JSONObject(s);
                String status = result.getString("status");
                String session_token = result.getString("session_token");
                if (status.equals("fail")) {

                    Toast.makeText(getActivity(), "Invaild Device", Toast.LENGTH_LONG).show();

                } else {
                    new MyActivityAsync(tranctional_type).execute();
                    SharedPreferences pref = getActivity().getPreferences(0);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.remove("session_token");
                    edit.putString("session_token", session_token);
                    edit.apply();
                    Log.e("tag", "tranctional_type " + tranctional_type);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> P R O F I L E  A U D I O  U P L O A D >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private class Uploadaudio extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

        }

        @Override
        protected String doInBackground(Void... params) {

            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;
            //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

            try {

                HttpClient client = new DefaultHttpClient();
                HttpPost postMethod = new HttpPost(CONFIG.URL + "/api/emergencyaudio");
                postMethod.addHeader("session_token", token);
                postMethod.addHeader("location", addressLine);
                postMethod.addHeader("transaction_id", tranctional_id);
                File file = new File(outputFile);
                MultipartEntity entity = new MultipartEntity();
                FileBody contentFile = new FileBody(file);
                entity.addPart("fileUpload", contentFile);
                postMethod.setEntity(entity);
                HttpResponse response = client.execute(postMethod);
                HttpEntity r_entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: " + statusCode;
                }
            } catch (IOException e) {
                responseString = e.toString();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("TAG", "audio_send====>: " + jsonStr);
            super.onPostExecute(jsonStr);
            dialog.dismiss();
            try {
                JSONObject result = new JSONObject(jsonStr);
                String status = result.getString("status");
                String msg = result.getString("message");

                if (msg.indexOf("session_token") >= 0) {
                    TelephonyManager tManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                    uid = tManager.getDeviceId();
                    Log.d("tag", "uid" + uid);

                    new MyAsyncsessiontoken(uid).execute();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
