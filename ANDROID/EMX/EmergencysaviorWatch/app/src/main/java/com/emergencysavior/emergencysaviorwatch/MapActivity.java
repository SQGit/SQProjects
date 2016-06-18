package com.emergencysavior.emergencysaviorwatch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by RSA on 07-06-2016.
 */
public class MapActivity extends Fragment {
    ImageView image_map;
    public static Location loc;
    double latitude, longitude;
    Bitmap bitmap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_activity, container, false);
        image_map= (ImageView) v.findViewById(R.id.image_map);

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

                if (Util.Operations.isOnline(getActivity())) {

                    callAsynchronousTask();

                } else {

                }


                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getActivity(), Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();

                    Log.d("tag", "Address" + address);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("tag", "cannot get Address!");
                }


            }


        };
        MyLocation myLocation = new MyLocation();
        myLocation.getLocation(getActivity(), locationResult);


        return v;

    }

    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            new LoadImage().execute("https://maps.googleapis.com/maps/api/staticmap?center=zoom=500&size=600x300&maptype=roadmap&markers=color:blue%7Clabel:S%7C12.8465489,80.0621143&key=AIzaSyBdf1uboSbpviRi6Gd0ykxVFuQxlABHs4k");
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 50000); //execute in every 50000 ms
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {


        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                image_map.setImageBitmap(image);

            }else{


                Toast.makeText(getActivity(), "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }





    public static MapActivity newInstance(String text) {

        MapActivity f = new MapActivity();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

}
