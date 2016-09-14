package com.startemplan.vyuspot;


import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Salman on 3/29/2016.
 */
public class MapView_Map extends MapBase {

    double Lati = 12.9256901;
    double Longi = 18.1114117;
    ArrayList<Double> latia = new ArrayList<Double>();
    ArrayList<Double> longia = new ArrayList<Double>();
    public static String url = "https://img_emg.uguu.se/onsghn_codebeautify%281%29%281%29.json";
    SweetAlertDialog pDialog;

    public static String URL;

    @Override
    protected void startProcess() {


        URL = getIntent().getStringExtra("service");

        Log.d("tag","map"+URL);


        // jsonprocess();

        // new MyActivityAsync().execute();

        getMap().setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker arg0) {

                // Getting view from the layout file info_window_layout
                View v = getLayoutInflater().inflate(R.layout.info_window_layout, null);

                // Getting the position from the marker
                LatLng latLng = arg0.getPosition();

                // Getting reference to the TextView to set latitude
                TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);

                // Getting reference to the TextView to set longitude
                TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);

                TextView tvNam = (TextView) v.findViewById(R.id.tv_name);

                TextView tvNeed = (TextView) v.findViewById(R.id.tv_need);


                tvNam.setText("Name:" + "Mufeed");
                tvNeed.setText("Need:" + "Food,Milk,Money");

                // Setting the latitude
                tvLat.setText("Contact:" + "987536485");

                // Setting the longitude
                tvLng.setText("Nearby:" + "MecHonda");

                // Returning the view containing InfoWindow contents
                return v;

            }
        });

        // Adding and showing marker while touching the GoogleMap
        getMap().setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng arg0) {
                // Clears any existing markers from the GoogleMap
                getMap().clear();

                // Creating an instance of MarkerOptions to set position
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting position on the MarkerOptions
                markerOptions.position(arg0);

                // Animating to the currently touched position
                getMap().animateCamera(CameraUpdateFactory.newLatLng(arg0));

                // Adding marker on the GoogleMap
                Marker marker = getMap().addMarker(markerOptions);

                // Showing InfoWindow on the GoogleMap
                //marker.showInfoWindow();

            }
        });


        latia.add(12.9328216);
        longia.add(80.1575657);

        latia.add(12.8450209);
        longia.add(80.0474856);

        latia.add(12.8509204);
        longia.add(80.0232814);

        latia.add(12.816609);
        longia.add(80.0146983);

        latia.add(12.8308362);
        longia.add(80.023968);

        for (int i = 0; i < latia.size(); i++) {

            Marker marker = getMap().addMarker(new MarkerOptions().position(new LatLng(latia.get(i), longia.get(i))));

            marker.showInfoWindow();

        }


     /*   for (int i = 0; i < 15; i++) {

            Log.img_view("tag", "" + i);
            double lati = Lati+1.1563;
            double longi = Longi+4.3863;
            LatLng sydney = new LatLng(lati, longi);
            Log.img_view("tag", "" + lati + "" + longi);
            Marker markr = getMap().addMarker(new MarkerOptions().position(sydney).title("karan").snippet("need:food,biscuit\ncontact:98242432403"));
            markr.showInfoWindow();

            //AdapterInfo aadpt = new AdapterInfo();
            //getMap().setInfoWindowAdapter(aadpt);
        }*/

    }


    class MyActivityAsync extends AsyncTask<String, Void, String> {


        public MyActivityAsync() {
            String json = "", jsonStr = "";

        }

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new SweetAlertDialog(MapView_Map.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();


        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";
            try {

                JSONObject jsonobject = JsonFuctions.getJSONfromURL(url);

                Log.e("tag", "J" + jsonobject);

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

           /*     new SweetAlertDialog(MapView_Map.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();
            } else {*/

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
                            latia.add(data.getDouble("lat"));
                            longia.add(data.getDouble("lng"));

                        }

                    } else {
                        pDialog.dismiss();

                        new SweetAlertDialog(MapView_Map.this, SweetAlertDialog.ERROR_TYPE)
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


    private void jsonprocess() {
        JSONObject jsonobject = JsonFuctions.getJSONfromURL("https://img_emg.uguu.se/exhbkj_asd.json");
        String json = jsonobject.toString();
        JSONArray array = null;
        try {
            array = new JSONArray(json);

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                latia.add(object.getDouble("lat"));
                longia.add(object.getDouble("lng"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
