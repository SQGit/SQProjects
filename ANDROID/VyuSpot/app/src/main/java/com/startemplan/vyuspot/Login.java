package com.startemplan.vyuspot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.rey.material.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class Login extends Activity {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static String URLL = "http://sqindia01.cloudapp.net/vyuspot/api/v1/user/login";
    private static Pattern pattern;
    private static Matcher matcher;
    EditText username, userpass;
    String user, pass, gcmregId, device_id, token, name;
    SweetAlertDialog pDialog;
    TelephonyManager tm;
    TextView reg;
    Button login;
    MaterialRippleLayout mtp;
    GoogleCloudMessaging gcmObj;

    public static boolean validate(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private static void post(String endpoint, Map<String, String> params)
            throws IOException {

        URL url;
        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("taginvalid url: " + endpoint);
        }
        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        // constructs the POST body using the parameters
        while (iterator.hasNext()) {
            Map.Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=')
                    .append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }
        String body = bodyBuilder.toString();
        Log.d("tag", "Posting '" + body + "' to " + url);
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;
        try {
            Log.d("URL", "> " + url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            // handle the response
            int status = conn.getResponseCode();
            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences prefs = getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);

        gcmregId = prefs.getString("regId", "");
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        device_id = tm.getDeviceId();
        username = (EditText) findViewById(R.id.uname);
        userpass = (EditText) findViewById(R.id.pass);
        login = (Button) findViewById(R.id.send);
        reg = (TextView) findViewById(R.id.reg);
        mtp = (MaterialRippleLayout) findViewById(R.id.ripple);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                //i.putExtra("regId", gcmregId);

                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);

                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                finish();
            }
        });

        mtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = username.getText().toString();
                pass = userpass.getText().toString();


                if (!user.isEmpty() && !pass.isEmpty()) {

                    register();

                   /* if (validate(user)) {*/
                  /*  if (!TextUtils.isEmpty(gcmregId)) {


                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("test", "12345");
                        editor.putString("pus", "54321");
                        editor.putString("name", "username");
                        editor.commit();


                        Intent i = new Intent(getApplicationContext(), GreetingActivity.class);
                        i.putExtra("regId", gcmregId);
                        startActivity(i);
                        finish();
                    } else {*/
                    //gotoCheck();
                    // }
                    /*} else {
                        new SweetAlertDialog(Login.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Alert")
                                .setContentText("Username is Not valid! ")
                                .setConfirmText("OK")
                                .show();
                    }*/

                } else {

                    new SweetAlertDialog(Login.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Alert")
                            .setContentText("Please enter all details! ")
                            .setConfirmText("OK")
                            .show();
                }


            }
        });


    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        // When Play services not found in device
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                // Show Error dialog to install Play services
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                //Toast.makeText(
                //  applicationContext,"This device doesn't support Play services, App will not work normally",Toast.LENGTH_LONG).show();
                pDialog.setTitle("App wont work without google play services");
                pDialog.dismiss();

                finish();
            }
            return false;
        } else {
            //Toast.makeText(applicationContext,"This device supports Play services, App will work normally", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    private void gotoCheck() {

        pDialog = new SweetAlertDialog(Login.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        if (checkPlayServices()) {
            registerInBackground();
        }
    }

    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcmObj == null) {
                        gcmObj = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    gcmregId = gcmObj.register(ApplicationConstants.GOOGLE_PROJ_ID);
                    msg = "Registration ID :" + gcmregId;
                    Log.d("tag", gcmregId);

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                if (!TextUtils.isEmpty(gcmregId)) {

                  /* Toast.makeText(
                            applicationContext,
                            "Registered with GCM Server successfully.\n\n"
                                    + msg, Toast.LENGTH_SHORT).show();
*/
                    Log.d("tag1", gcmregId);


                    storegcmregIdinServer();


                } else {
                    // Toast.makeText(
                    //   applicationContext,
                    // "Reg ID Creation Failed.\n\nEither you haven't enabled Internet or GCM server is busy right now. Make sure you enabled Internet and try registering again after some time."
                    // + msg, Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    private void storegcmregIdinServer() {


        new AsyncTask<String, Void, String>() {


            protected void onPreExecute() {
                super.onPreExecute();
                Log.d("tag", "s1");
            }

            @Override
            protected String doInBackground(String... params) {
                // TODO Auto-generated method stub

                String json = "", jsonStr = "";

                try {

                    Map<String, String> hashmapu = new HashMap<String, String>();
                    hashmapu.put("emailId", user);
                    hashmapu.put("regId", gcmregId);
                    post(ApplicationConstants.APP_SERVER_URL, hashmapu);

                } catch (Exception e) {
                    Log.d("tagInputStream", e.getLocalizedMessage());
                }

                return null;

            }

            @Override
            protected void onPostExecute(String s) {
                Log.d("tag", user + gcmregId);
                Log.d("tag", "<-----rerseres---->" + s);
                register();
                super.onPostExecute(s);
            }

        }.execute();

    }

    private void register() {


        new AsyncTask<String, Void, String>() {


            protected void onPreExecute() {
                super.onPreExecute();
                Log.d("tag", "last");

                pDialog = new SweetAlertDialog(Login.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                pDialog.show();



            }

            @Override
            protected String doInBackground(String... params) {
                // TODO Auto-generated method stub

                String json = "", jsonStr = "";

                try {

                    JSONObject jsonObject = new JSONObject();


                    jsonObject.accumulate("userName", user);
                    jsonObject.accumulate("password", pass);
                    jsonObject.accumulate("deviceId", device_id);

                    Log.e("tag", device_id);
                    json = jsonObject.toString();
                    return jsonStr = HttpUtils.makeRequest1(URLL, json);
                } catch (Exception e) {
                    Log.d("InputStream", e.getLocalizedMessage());
                }

                return null;

            }

            @Override
            protected void onPostExecute(String s) {
                Log.e("tag", username + device_id);
                pDialog.dismiss();
                super.onPostExecute(s);
                Log.e("tag", "<-----rerseres---->" + s);


                try {
                    JSONObject jo = new JSONObject(s);

                    String status = jo.getString("status");

                    String msg = jo.getString("message");
                    Log.e("tag", "<-----Status----->" + status);

                    if (status.equals("SUCCESS")) {


                        JSONObject jo3 = new JSONObject(s);
                        JSONArray jsonarray = jo3.getJSONArray("data");
                        Log.e("tag", "<-----inside----->" + status);

                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject joo = jsonarray.getJSONObject(i);


                            token = joo.getString("authToken");
                            name = joo.getString("userName");
                            gcmregId = joo.getString("userGcmId");
                            Log.e("tag", "<-----tok----->" + token + name + gcmregId);
                        }

                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("test1", "535353");
                            editor.putString("pus", "54321");
                            editor.putString("token", token);
                            Log.e("tag", "tok" + token);
                            editor.putString("name", name);
                            editor.commit();


                            Intent ii = new Intent(getApplicationContext(), GreetingActivity.class);
                            ii.putExtra("regId", gcmregId);
                            startActivity(ii);
                            finish();





                        /*SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("test1", "535353");
                        editor.commit();

                        Intent i = new Intent(getApplicationContext(),Dashboard.class);
                        startActivity(i);
                        finish();*/
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Login Details", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), Login.class);
                        startActivity(i);
                        finish();
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        }.execute();

    }


}
