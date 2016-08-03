package com.emergencysavior.emergencysavior;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.markushi.ui.CircleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class Safewalking extends AppCompatActivity implements RoutingListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    public static final String EXTRA_IS_TRANSPARENT = "is_transparent";
    private boolean isTransparent;
    public static GoogleMap map;
    protected LatLng start;
    protected LatLng end, curent_location;
    @InjectView(R.id.start)
    AutoCompleteTextView starting;
    @InjectView(R.id.destination)
    AutoCompleteTextView destination;
    @InjectView(R.id.send)
    ImageView send;
    CharSequence source_lable;
    CharSequence desination_lable;
    Marker marker;
    private static final String LOG_TAG = "MyActivity";
    protected GoogleApiClient mGoogleApiClient;
    private PlaceAutoCompleteAdapter mAdapter;
    private ProgressDialog progressDialog;
    private List<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.primary_dark, R.color.primary, R.color.primary_light, R.color.accent, R.color.primary_dark_material_light};
    private static final LatLngBounds BOUNDS_JAMAICA = new LatLngBounds(new LatLng(-57.965341647205726, 144.9987719580531),
            new LatLng(72.77492067739843, -9.998857788741589));

    SharedPreferences sharedPreferences;
    PendingIntent pendingIntent;
    public static LocationManager locationManager;
    Location mCurrentLocation;
    String mLastUpdateTime, token, firstname, current_address, transaction_id, str_reason, str_duration;
    double source_lat, source_long, desination_lat, desination_long;

    // mixing
    Typeface tf;
    ProgressDialog mProgressDialog;
    CircleButton toogleflag;

    boolean toggleflag = false;
    // mixing

    private static final int GPS_TIME_INTERVAL = 1000 * 60 * 5; // get gps location every 1 min
    private static final int GPS_DISTANCE = 1000; // set the distance value in meter

    @Override
    public void onCreate(Bundle savedInstanceState) {
        isTransparent = getIntent().getBooleanExtra(EXTRA_IS_TRANSPARENT, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.safe);
        ButterKnife.inject(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Safewalking.this);
        firstname = sharedPreferences.getString("Firstname", "");
        token = sharedPreferences.getString("Session_token", "");

        toogleflag = (CircleButton) findViewById(R.id.play);
        current_address = MainActivity.addrees.getText().toString();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toogleflag.setVisibility(View.GONE);
        tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        SpannableStringBuilder SS = new SpannableStringBuilder(Html.fromHtml("<font color='#3c5899'><small><b>SAFE WALKING<b></small> </font>"));
        SS.setSpan(new CustomTypefaceSpan("#3c5899", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);
        polylines = new ArrayList<>();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        MapsInitializer.initialize(this);
        mGoogleApiClient.connect();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();

            return;
        }
        map = mapFragment.getMap();
        map.clear();
        mAdapter = new PlaceAutoCompleteAdapter(this, android.R.layout.simple_list_item_1, mGoogleApiClient, BOUNDS_JAMAICA, null);
        sharedPreferences = getSharedPreferences("location", 0);
        String lat = sharedPreferences.getString("lat", "0");
        String lng = sharedPreferences.getString("lng", "0");
        String zoom = sharedPreferences.getString("zoom", "0");
        if (!lat.equals("0")) {
            drawCircle(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
            drawMarker(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
            map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))));
            map.animateCamera(CameraUpdateFactory.zoomTo(Float.parseFloat(zoom)));
        }
        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition position) {
                LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
                mAdapter.setBounds(bounds);
            }
        });
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(18.013610, -77.498803));
        CameraUpdate zoom1 = CameraUpdateFactory.zoomTo(17);
        map.moveCamera(center);
        map.animateCamera(zoom1);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 60 * 4, 0,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        curent_location = new LatLng(latitude, longitude);
                        mCurrentLocation = location;
                        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                        if (curent_location == end) {
                            Intent proximityIntent = new Intent("com.emergencysavior.emergencysavior.activity.proximity");
                            pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, proximityIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
                            if (ActivityCompat.checkSelfPermission(Safewalking.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Safewalking.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            locationManager.removeProximityAlert(pendingIntent);
                            map.clear();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();
                            editor.apply();
                        } else {
                            if (marker != null) {
                                marker.remove();
                            }
                            marker = map.addMarker(new MarkerOptions()
                                    .position(curent_location)
                                    .snippet(
                                            "Lat:" + location.getLatitude() + " Lng:" + location.getLongitude())
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.walk1))
                                    .title("" + firstname));
                            Log.e("lat", "" + curent_location);

                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            String trackstatus = sharedPreferences.getString("trackstatus", "");

                            switch (trackstatus) {
                                case "0":
                                    new SafewalkingupdateAsync1(source_lable, source_lat, source_long, transaction_id).execute();

                                    break;
                                case "1":
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    @Override
                    public void onProviderEnabled(String provider) {


                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                    }
                });
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                5000, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        /*CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(17);
                        map.animateCamera(zoom);
*/


                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
        starting.setAdapter(mAdapter);
        destination.setAdapter(mAdapter);
        starting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PlaceAutoCompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
                final String placeId = String.valueOf(item.placeId);
                source_lable = item.description;
                Log.i(LOG_TAG, "Autocomplete item selected: " + source_lable);
                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (!places.getStatus().isSuccess()) {
                            Log.e(LOG_TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                            places.release();
                            return;
                        }
                        final Place place = places.get(0);
                        start = place.getLatLng();
                    }
                });

            }
        });
        destination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PlaceAutoCompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
                final String placeId = String.valueOf(item.placeId);
                desination_lable = item.description;
                Log.i("tag", "Autocomplete item selected: " + desination_lable);

                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (!places.getStatus().isSuccess()) {
                            // Request did not complete successfully
                            Log.e(LOG_TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                            places.release();
                            return;
                        }
                        final Place place = places.get(0);
                        end = place.getLatLng();

                    }
                });

            }
        });
        starting.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int startNum, int before, int count) {
                if (start != null) {
                    start = null;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        destination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (end != null) {
                    end = null;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        toogleflag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleflag == false) {
                    toogleflag.setImageResource(R.drawable.ic_stop);
                    toggleflag = true;
                    source_lable = current_address;
                    source_lat = curent_location.latitude;
                    source_long = curent_location.longitude;
                    desination_lat = end.latitude;
                    desination_long = end.longitude;

                    new SafewalkingAsync(source_lable, source_lat, source_long, desination_lable, desination_lat, desination_long, str_duration).execute();
                } else {

                    toogleflag.setImageResource(R.drawable.ic_media_play);
                    toogleflag.setVisibility(View.GONE);
                    send.setVisibility(View.VISIBLE);
                    destination.setText("");
                    toggleflag = false;

                    Intent proximityIntent = new Intent("com.emergencysavior.emergencysavior.activity.proximity");
                    pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, proximityIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (ActivityCompat.checkSelfPermission(Safewalking.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Safewalking.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    locationManager.removeProximityAlert(pendingIntent);
                    map.clear();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    // DIALOG
                    final Dialog dialog1 = new Dialog(Safewalking.this);
                    dialog1.setContentView(R.layout.stop_safe_walking);
                    dialog1.setTitle("SEND REASON");
                    final EditText et_user = (EditText) dialog1.findViewById(R.id.et_user_name);
                    Button btn_save = (Button) dialog1.findViewById(R.id.btn_submit);
                    btn_save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            str_reason = et_user.getText().toString();
                            if (str_reason.isEmpty()) {
                                et_user.setError("REACHED or CANCELLED");

                            } else {
                                new Safewalking_end_Async(transaction_id, str_reason).execute();
                                dialog1.dismiss();
                            }
                        }
                    });
                    dialog1.show();
                }
            }
        });
        setStatusBar();
    }

    protected void setStatusBar() {
        if (isTransparent) {
            StatusBarUtil.setTransparent(this);
        } else {
            StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
        }
    }

    private void drawMarker(LatLng point) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(point);
        map.addMarker(markerOptions);
    }

    private void drawCircle(LatLng point) {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(point);
        circleOptions.radius(20);
        circleOptions.strokeColor(Color.BLACK);
        circleOptions.fillColor(0x30ff0000);
        circleOptions.strokeWidth(2);
        map.addCircle(circleOptions);

    }

    @OnClick(R.id.send)
    public void sendRequest() {
        if (OperationUtil.Operations.isOnline(this)) {
            route();
        } else {
            Toast.makeText(this, "No internet connectivity", Toast.LENGTH_SHORT).show();
        }
    }

    public void route() {
        if (end == null) {
            if (start == null) {
                if (starting.getText().length() > 0) {
                    starting.setError("Choose location from dropdown.");
                } else {
                    // Toast.makeText(this, "Please choose a starting point.", Toast.LENGTH_SHORT).show();
                }
            }
            if (curent_location == null) {
                if (destination.getText().length() > 0) {
                    destination.setError("Choose location from dropdown.");
                } else {
                    Toast.makeText(this, "Please choose a destination.", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            progressDialog = ProgressDialog.show(this, "Please wait.",
                    "Fetching route information.", true);
            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(false)
                    .waypoints(curent_location, end)
                    .build();
            routing.execute();

            toogleflag.setVisibility(View.VISIBLE);
            send.setVisibility(View.GONE);
            if (marker != null) {
                marker.remove();

            }
            // start Activity //
            Intent proximityIntent = new Intent("com.emergencysavior.emergencysavior.activity.proximity");
            pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, proximityIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.addProximityAlert(end.latitude, end.longitude, 20, -1, pendingIntent);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("lat", Double.toString(end.latitude));
            editor.putString("lng", Double.toString(end.longitude));
            editor.putString("zoom", Float.toString(map.getCameraPosition().zoom));
            editor.apply();

        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        // The Routing request failed
        progressDialog.dismiss();
        if (e != null) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingStart() {
        // The Routing Request starts
    }

    @Override
    public void onRoutingSuccess(List<Route> route, int shortestRouteIndex) {
        progressDialog.dismiss();

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(curent_location);
        builder.include(end);
        LatLngBounds bounds = builder.build();

        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        if (polylines.size() > 0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }
        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i < route.size(); i++) {
            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;
            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = map.addPolyline(polyOptions);
            polylines.add(polyline);
            str_duration = String.valueOf(route.get(i).getDurationValue());

        }

        // Start marker
        MarkerOptions options = new MarkerOptions();
        options.position(curent_location);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
        map.addMarker(options);

        // End marker
        options = new MarkerOptions();
        options.position(end);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
        map.addMarker(options);

    }

    @Override
    public void onRoutingCancelled() {
        Log.i(LOG_TAG, "Routing was cancelled.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.v(LOG_TAG, connectionResult.toString());
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% C U R R E N T L O C A T I O N %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    class ProximityActivity extends Activity {

        String notificationTitle;
        String notificationContent;
        String tickerMessage;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);

            boolean proximity_entering = getIntent().getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, true);

            if (proximity_entering) {
                Toast.makeText(getBaseContext(), "Entering the region", Toast.LENGTH_LONG).show();
                notificationTitle = "Proximity - Entry";
                notificationContent = "Entered the region";
                tickerMessage = "Entered the region";

                Toast.makeText(getApplicationContext(), " Tested ok", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getBaseContext(), "Exiting the region", Toast.LENGTH_LONG).show();
                notificationTitle = "Proximity - Exit";
                notificationContent = "Exited the region";
                tickerMessage = "Exited the region";

            }

            Intent notificationIntent = new Intent(getApplicationContext(), NotificationView.class);
            notificationIntent.putExtra("content", notificationContent);
            notificationIntent.setData(Uri.parse("tel:/" + (int) System.currentTimeMillis()));
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
            NotificationManager nManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                    .setWhen(System.currentTimeMillis())
                    .setContentText(notificationContent)
                    .setContentTitle(notificationTitle)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setTicker(tickerMessage)
                    .setContentIntent(pendingIntent);
            Notification notification = notificationBuilder.build();
            nManager.notify((int) System.currentTimeMillis(), notification);
            finish();


        }
    }

    private class SafewalkingAsync extends AsyncTask<String, Void, String> {
        CharSequence source_lable, desination_lable;
        String str_duration;
        double source_lat, source_long, desination_lat, desination_long;

        public SafewalkingAsync(CharSequence source_lable, double source_lat, double source_long, CharSequence desination_lable, double desination_lat, double desination_long, String str_duration) {
            this.source_lable = source_lable;
            this.source_lat = source_lat;
            this.source_long = source_long;
            this.desination_lable = desination_lable;
            this.desination_lat = desination_lat;
            this.desination_long = desination_long;
            this.str_duration = str_duration;

        }

        protected void onPreExecute() {

            super.onPreExecute();
            mProgressDialog = new ProgressDialog(Safewalking.this);
            mProgressDialog.setTitle("Searched Location..");
            mProgressDialog.setMessage("Please wait");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", s = "";
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.accumulate("from_location", current_address);
                jsonObject.accumulate("from_lat", source_lat);
                jsonObject.accumulate("from_long", source_long);
                jsonObject.accumulate("to_location", desination_lable);
                jsonObject.accumulate("to_lat", desination_lat);
                jsonObject.accumulate("to_long", desination_long);
                jsonObject.accumulate("duriation", str_duration);
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return s = HttpUtils.makeRequest(CONFIG.URL + "/safewalk/start", json, token);
            } catch (JSONException e) {

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("json result", s);
            mProgressDialog.dismiss();

            if (s == "") {

                new SweetAlertDialog(Safewalking.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("ALERT")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();
                mProgressDialog.dismiss();
            } else {
                try {
                    JSONObject jo = new JSONObject(s);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                    transaction_id = jo.getString("transaction_id");

                    Log.d("tag", "tranction id start" + transaction_id);
                    if (status.equals("success")) {
                        mProgressDialog.dismiss();
                        SharedPreferences s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor edit = s_pref.edit();
                        edit.putString("trackstatus", "0");
                        edit.apply();


                    } else {
                        Log.d("tag", "Something went wrong " + msg);
                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    mProgressDialog.dismiss();
                }
            }
        }
    }

    // END
    private class Safewalking_end_Async extends AsyncTask<String, Void, String> {

        String transaction_id, str_reason;

        public Safewalking_end_Async(String transaction_id, String str_reason) {
            this.transaction_id = transaction_id;
            this.str_reason = str_reason;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(Safewalking.this);
            mProgressDialog.setTitle("Stop location track..");
            mProgressDialog.setMessage("Please wait");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", s = "";
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.accumulate("transaction_id", transaction_id);
                jsonObject.accumulate("reason", str_reason);
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return s = HttpUtils.makeRequest(CONFIG.URL + "/safewalk/end", json, token);
            } catch (JSONException e) {

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("json result", s);
            mProgressDialog.dismiss();
            if (s == "") {

                new SweetAlertDialog(Safewalking.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("ALERT")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();
                mProgressDialog.dismiss();
            } else {
                try {
                    JSONObject jo = new JSONObject(s);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");

                    if (status.equals("success")) {
                        SharedPreferences s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor edit = s_pref.edit();
                        edit.remove("trackstatus");
                        edit.putString("trackstatus", "");
                        edit.apply();
                        Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_LONG).show();

                    } else {
                        Log.d("tag", "Something went wrong");
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    mProgressDialog.dismiss();
                }
            }

        }
    }

    private class SafewalkingupdateAsync1 extends AsyncTask<String, Void, String> {

        CharSequence source_lable;
        double source_lat, source_long;
        String transaction_id;

        public SafewalkingupdateAsync1(CharSequence source_lable, double source_lat, double source_long, String transaction_id) {

            this.source_lable = source_lable;
            this.source_lat = source_lat;
            this.source_long = source_long;
            this.transaction_id = transaction_id;
        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", s = "";
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("transaction_id", transaction_id);
                jsonObject.put("location", source_lable);
                jsonObject.put("latitude", source_lat);
                jsonObject.put("longitude", source_long);
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.put("data", jsonObject);
                return s = HttpUtils.makeRequest(CONFIG.URL + "/safewalk/update", json, token);
            } catch (JSONException e) {

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("json result", s);


            if (s == "") {

                new SweetAlertDialog(Safewalking.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("ALERT")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();

            } else {
                try {
                    JSONObject jo = new JSONObject(s);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");

                    if (status.equals("success")) {
                        SharedPreferences s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor edit = s_pref.edit();
                        edit.remove("trackstatus");
                        edit.putString("trackstatus", "0");
                        edit.apply();
                        Log.d("tag", " " + msg);

                        // Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_LONG).show();

                    } else {
                        Log.d("tag", "Something went wrong" + msg);
                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();


        Log.d("lifecycle", "onRestart invoked");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("lifecycle", "onDestroy invoked");
    }
}
