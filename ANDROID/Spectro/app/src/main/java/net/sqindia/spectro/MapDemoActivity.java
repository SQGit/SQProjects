package net.sqindia.spectro;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sloop.fonts.FontsManager;

import java.util.ArrayList;

/**
 * Created by $Krishna on 30-08-2016.
 */
public class MapDemoActivity extends FragmentActivity{
    GoogleMap googleMap,gmap;
    LinearLayout gback;
    TextView btn_source;
    MarkerOptions markerOptions;
    ArrayList<String> latia;
    ArrayList<String> longia;
    double regi;
    BitmapDescriptor icon;
    int i;
    Marker marker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        FontsManager.initFormAssets(this, "fonts/avenir.otf");       //initialization
        FontsManager.changeFonts(this);
        gback = (LinearLayout) findViewById(R.id.layout_gpsback);
        btn_source = (TextView) findViewById(R.id.bt_source);

        btn_source.setText("Find Source");
        btn_source.setBackgroundResource(R.drawable.dash_blue);
        btn_source.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));

        btn_source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    btn_source.setText("Clear Source");
                    btn_source.setBackgroundResource(R.drawable.dash_red);
                    btn_source.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));

                    for (int i=0;i<latia.size();i++){
                        // LatLng position1 = new LatLng(Double.valueOf(latia.get(i)), Double.valueOf(longia.get(i)));
                        //MarkerOptions options = new MarkerOptions().position(position1);

                        // Drawing marker on the map
                        drawMarker(new LatLng(Double.parseDouble(latia.get(i)), Double.parseDouble(longia.get(i))));
                    }

                    i = 1;

                } else {
                    googleMap.clear();
                    regi= getIntent().getDoubleExtra("reg", 0);
                    if(regi<0.383173) {

                        icon = BitmapDescriptorFactory.fromResource(R.drawable.blu);
                    }
                    else{
                        icon = BitmapDescriptorFactory.fromResource(R.drawable.red);
                    }
                    double latitude = getIntent().getDoubleExtra("lat", 0);

                    // Receiving longitude from MainActivity screen
                    double longitude = getIntent().getDoubleExtra("lng", 0);
                    LatLng position = new LatLng(latitude, longitude);

                    MarkerOptions options = new MarkerOptions().icon(icon);

                    // Setting position for the MarkerOptions
                    options.position(position);

                    // Setting title for the MarkerOptions
                    options.title("Position");

                    // Setting snippet for the MarkerOptions
                    options.snippet("Latitude:"+latitude+",Longitude:"+longitude);

                    // Getting Reference to SupportMapFragment of activity_map.xml
                    SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

                    // Getting reference to google map
                    googleMap = fm.getMap();

                    // Adding Marker on the Google Map
                    googleMap.addMarker(options);

                    //drawMarker(position);
                    drawCircle(position);

                    // Creating CameraUpdate object for position
                    CameraUpdate updatePosition = CameraUpdateFactory.newLatLng(position);
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(position).zoom(19f).tilt(70).build();
                    // Creating CameraUpdate object for zoom
                    CameraUpdate updateZoom = CameraUpdateFactory.zoomBy(14);

                    // Updating the camera position to the user input latitude and longitude
                    googleMap.moveCamera(updatePosition);

                    // Applying zoom to the marker position
                    //  googleMap.animateCamera(updatePosition);
                    googleMap.animateCamera(CameraUpdateFactory
                            .newCameraPosition(cameraPosition));
                    btn_source.setText("Find Source");
                   // markerOptions.

                    btn_source.setBackgroundResource(R.drawable.dash_blue);
                    btn_source.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
                    i = 0;

                }
            }
        });

        latia = new ArrayList<>();
        longia = new ArrayList<>();

        gback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        // Receiving latitude from MainActivity screen
        double latitude = getIntent().getDoubleExtra("lat", 0);

        // Receiving longitude from MainActivity screen
        double longitude = getIntent().getDoubleExtra("lng", 0);

         regi= getIntent().getDoubleExtra("reg", 0);


        latia = getIntent().getStringArrayListExtra("lata");

        longia = getIntent().getStringArrayListExtra("lona");




        Log.e("tag","arrsize"+latia.size());

        String tim = getIntent().getStringExtra("ti");
        Log.e("tag","mtim"+tim);

        LatLng position = new LatLng(latitude, longitude);
        Log.e("tag","asd"+regi);
        if(regi<0.383173) {

            icon = BitmapDescriptorFactory.fromResource(R.drawable.blu);
        }
        else{
            icon = BitmapDescriptorFactory.fromResource(R.drawable.red);
        }

        // Instantiating MarkerOptions class
        MarkerOptions options = new MarkerOptions().icon(icon);

        // Setting position for the MarkerOptions
        options.position(position);

        // Setting title for the MarkerOptions
        options.title("Position");

        // Setting snippet for the MarkerOptions
        options.snippet("Latitude:"+latitude+",Longitude:"+longitude);

        // Getting Reference to SupportMapFragment of activity_map.xml
        SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

        // Getting reference to google map
         googleMap = fm.getMap();

        // Adding Marker on the Google Map
        googleMap.addMarker(options);

        //drawMarker(position);
        drawCircle(position);

        // Creating CameraUpdate object for position
        CameraUpdate updatePosition = CameraUpdateFactory.newLatLng(position);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(position).zoom(19f).tilt(70).build();
        // Creating CameraUpdate object for zoom
        CameraUpdate updateZoom = CameraUpdateFactory.zoomBy(14);

        // Updating the camera position to the user input latitude and longitude
        googleMap.moveCamera(updatePosition);

        // Applying zoom to the marker position
      //  googleMap.animateCamera(updatePosition);
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }
    private void drawMarker(LatLng point) {

        //Marker marker = map.addMarker(..);
         markerOptions = new MarkerOptions();
        markerOptions.position(point);
        SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

        // Getting reference to google map
        googleMap = fm.getMap();
      googleMap.addMarker(markerOptions);
    }

    private void drawCircle(LatLng point) {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(point);
        circleOptions.radius(20);
        circleOptions.strokeColor(Color.TRANSPARENT);
        circleOptions.fillColor(0x30059BFF);
        circleOptions.strokeWidth(2);
        googleMap.addCircle(circleOptions);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
