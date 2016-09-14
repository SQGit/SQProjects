package com.startemplan.vyuspot;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Salman on 3/23/2016.
 */
public class Map_View extends MapBase {


    private ClusterManager<MyItem> mClusterManager;

    List<Marker> markers = new ArrayList<Marker>();
    double lat, lng;
    private static final String REGEX_INPUT_BOUNDARY_BEGINNING = "\\A";

    public static String URL_VIEW = "https://img_emg.uguu.se/dpnwaj_id.json";


    @Override
    protected void startProcess() {


        getMap().getMyLocation();

        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(12.8491892, 80.0610739), 10));

        mClusterManager = new ClusterManager<MyItem>(this, getMap());
        getMap().setOnCameraChangeListener(mClusterManager);
        getMap().setOnMarkerClickListener(mClusterManager);

        Marker marker = getMap().addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.mipmap.conts)));


        Marker marker1 = getMap().addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.mipmap.conts)));

       // new MapActivity().execute();
    /*    try {
            readItems();

        } catch (JSONException img_map) {
            Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }*/

    }


    private void readItems() throws JSONException {
        InputStream inputStream = getResources().openRawResource(R.raw.police);
        List<MyItem> items = new MyItemReader().read(inputStream);
        // mClusterManager.addItems(items);
        //  String sdoi = String.valueOf(items.get(4));

        // Log.img_view("tag", "soie" + sdoi);

        JSONObject jsonobject = JsonFuctions.getJSONfromURL("https://img_emg.uguu.se/pvdihe_police.json");
        String json = jsonobject.toString();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            lat = object.getDouble("lat");
            lng = object.getDouble("lng");

            Marker marker = getMap().addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.mipmap.conts)));
            markers.add(marker);
        }


        markers.size();
   /*     LatLng sydney = new LatLng(-34, 151);
        LatLng sydney2 = new LatLng(-35, 153);
        getMap().addMarker(new MarkerOptions().position(new LatLng(90.85484848, 12.5845454)).title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.mipmap.conts)));

        Marker marker = getMap().addMarker(new MarkerOptions().position(sydney2));
        markers.add(marker);*/


    }




    class MapActivity extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                JSONObject jsonobject = JsonFuctions.getJSONfromURL(URL_VIEW);
                json = jsonobject.toString();
                Log.e("tag", "JO00000000000000" + jsonobject);
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

            if (jsonStr == "") {


            } else {

                try {
                    JSONObject jo = new JSONObject(jsonStr);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");

                    if (status.equals("SUCCESS")) {

                        JSONObject data = jo.getJSONObject("data");

                        JSONArray ja = data.getJSONArray("spots");

                        for (int i1 = 0; i1 < ja.length(); i1++) {
                            HashMap<String, String> map = new HashMap<String, String>();
                            data = ja.getJSONObject(i1);

                            lat = data.getDouble("lat");
                            lng = data.getDouble("lng");
                            Marker marker = getMap().addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.mipmap.conts)));
                            markers.add(marker);

                        }

                    } else {


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }







}
