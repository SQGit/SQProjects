package com.startemplan.vyuspot;


import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import com.balysv.materialripple.MaterialRippleLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class View_Needs extends Activity {


    public ListView lv;
    MaterialRippleLayout mtp;
    ArrayList<Getter_Setter> lv1 = new ArrayList<Getter_Setter>();

    @Override
    public void onBackPressed() {
        finish();


    }

    SweetAlertDialog pDialog;


    ArrayList<HashMap<String, String>> contactList;


    static String NAME = "Name";
    static String LOCATION = "Locationname";
    static String LANDMARK = "Landmark";
    static String TYPE = "sp";
    static String PHONE = "phone";
    static String HashTag = "Tag";
    public static String URL_VIEW = "http://sqindia01.cloudapp.net/vyuspot/api/v1/spot/listRecoveryNeeded";
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_needs);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        lv = (ListView) findViewById(R.id.listview);

        mtp = (MaterialRippleLayout) findViewById(R.id.ripple);

        contactList = new ArrayList<HashMap<String, String>>();
        contactList.clear();
        lv1.clear();


        mtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();


            }
        });



        if (!isOnline(View_Needs.this)) {


            new SweetAlertDialog(View_Needs.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Warning")
                    .setContentText("No Internet is available")
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            sweetAlertDialog.cancel();
                            Intent goD = new Intent(getApplicationContext(), Dashboard.class);
                            startActivity(goD);


                        }
                    })
                    .show();


        } else {
            //Internet available. Do what's required when internet is available.

            new MyActivityAsync().execute();


        }





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





    class MyActivityAsync extends AsyncTask<String, Void, String> {


        public MyActivityAsync() {
            String json = "", jsonStr = "";

        }

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new SweetAlertDialog(View_Needs.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();


        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";
            try {

                JSONObject jsonobject = JsonFuctions.getJSONfromURL(URL_VIEW);

                Log.e("tag", "JO00000000000000" + jsonobject);

                json = jsonobject.toString();

                return json;
            } catch (Exception e) {
                Log.e("InputStream", e.getLocalizedMessage());
            }
            return jsonStr;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("tag", "<-----rerseres---->" + jsonStr);
            super.onPostExecute(jsonStr);
            pDialog.dismiss();
            if (jsonStr == "") {

                new SweetAlertDialog(View_Needs.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();
            } else {

                try {
                    JSONObject jo = new JSONObject(jsonStr);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                   // Log.img_map("tag", "<-----Status----->" + status);

                    if (status.equals("SUCCESS")) {

                        JSONObject data = jo.getJSONObject("data");
                        //Log.img_map("tag", "<-----jsonobject----->" + data);
                        JSONArray ja = data.getJSONArray("spots");

                        for (int i1 = 0; i1 < ja.length(); i1++) {
                            HashMap<String, String> map = new HashMap<String, String>();
                            data = ja.getJSONObject(i1);
                            map.put("Name", data.getString("spotPersonName"));
                            map.put("Location", data.getString("spotLocation"));
                            map.put("Landmark", data.getString("spotLandmark"));
                            map.put("ContactNo", data.getString("spotContactNo"));
                            map.put("Needs", data.getString("spotResourceNeeded"));
                            map.put("spotid", data.getString("spotId"));
                            map.put("care1", data.getString("careBy"));
                            map.put("care2", data.getString("careByContact"));
                            //map.put("Cared", data.getString("spotResourceNeeded"));
                            contactList.add(map);

                            Getter_Setter getset = new Getter_Setter();

                            getset.set_NAME(data.getString("spotPersonName"));
                            getset.set_LOCATION(data.getString("spotLocation"));
                            getset.set_LANDMARK(data.getString("spotLandmark"));
                            getset.set_NUMBER(data.getString("spotContactNo"));
                            getset.set_SPOTID(data.getString("spotId"));
                            getset.set_NEED(data.getString("spotResourceNeeded"));
                            getset.set_CARE1(data.getString("careBy"));
                            getset.set_CAREDBY(data.getString("careBy") + data.getString("careByContact"));

                            lv1.add(getset);



                        }


                        Getter_Setter_Adapter adapter1 = new Getter_Setter_Adapter(View_Needs.this,lv1);
                        lv.setAdapter(adapter1);


                       // lv.setAdapter(new View_Needs_Adapter(View_Needs.this, contactList));

                    } else {
                        pDialog.dismiss();

                        new SweetAlertDialog(View_Needs.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("ERROR MESSAGE!!!")
                                .setContentText("invalid details")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.cancel();
                                    }
                                })
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


