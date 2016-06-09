package com.emergencysavior.emergencysaviorwatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by RSA on 09-06-2016.
 */
public class SplashActivity extends Activity {
    TextView tv;
    private static int SPLASH_TIME_OUT = 4000;
    Typeface tf;
    String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashactivity);
        tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        tv = (TextView) findViewById(R.id.dss);
        tv.setTypeface(tf);
        TelephonyManager tManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        uid = tManager.getDeviceId();
        if (Util.Operations.isOnline(SplashActivity.this)) {
            new MyAsyncSerialnumber(uid).execute();
        } else {
            new SweetAlertDialog(SplashActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("ALERT")
                    .setContentText("Internet Connectivity very slow? try again")
                    .setConfirmText("ok")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                            startActivity(intent);
                        }
                    })
                    .show();
        }

    }


    private class MyAsyncSerialnumber extends AsyncTask<String, Void, String> {
        String uid;

        public MyAsyncSerialnumber(String uid) {
            this.uid = uid;
        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("device_id", uid);


                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest("http://emergencysavior.com/webapi/emx/qrwear/watch", json);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("tag", "Response from the server" + s);
            super.onPostExecute(s);
            if (s.equals("")) {

                new SweetAlertDialog(SplashActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("ALERT")
                        .setContentText("Internet Connectivity very slow? try again")
                        .setConfirmText("ok")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .show();
            } else {

                try {
                    JSONObject result = new JSONObject(s);
                    String status = result.getString("status");
                    if (status.equals("success")) {

                        String session_token = result.getString("session_token");
                        String first_name = result.getString("first_name");
                        String email = result.getString("email");
                        String message = result.getString("status");
                        Log.d("tag", "status" + status);
                        Log.d("tag", "session_token" + session_token);
                        Log.d("tag", "first_name" + first_name);
                        Log.d("tag", "email" + email);
                        Log.d("tag", "message" + message);

                        SharedPreferences s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor edit = s_pref.edit();
                        edit.remove("Firstname");
                        edit.remove("session_token");
                        edit.remove("email");
                        edit.putString("Firstname", first_name);
                        edit.putString("session_token", session_token);
                        edit.putString("email", email);
                        edit.apply();
                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(intent);
                        finish();

                    } else if (status.equals("notfound")) {
                        Log.d("tag", "User id" + uid);
                        Intent intent = new Intent(getApplicationContext(), QRtesting.class);
                        startActivity(intent);
                        finish();

                    } else {

                        Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_SHORT).
                                show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

    }
}
