package com.emergencysavior.emergencysavior;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.jaeger.library.StatusBarUtil;

import tr.xip.errorview.ErrorView;

/**
 * Created by RSA on 03-05-2016.
 */
public class Right_Menu_Safe_Walking extends AppCompatActivity implements LocationListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    Typeface tf;
    public static final String EXTRA_IS_TRANSPARENT = "is_transparent";
    private boolean isTransparent;
    protected LatLng curent_location;
    TextView txt_copyrights, autoCompletetxt_source;
    AutoCompleteTextView autoCompletetxt_destination;
    Button btn_start_trip, btn_cancel;
    protected GoogleApiClient mGoogleApiClient;
    private PlaceAutoCompleteAdapter mAdapter;
    private static final LatLngBounds BOUNDS_JAMAICA = new LatLngBounds(new LatLng(-57.965341647205726, 144.9987719580531),
            new LatLng(72.77492067739843, -9.998857788741589));
    protected GoogleMap map;
    protected LatLng start;
    protected LatLng end;
    double end_lat, end_lng;
    LocationManager locationManager;
    final Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isTransparent = getIntent().getBooleanExtra(EXTRA_IS_TRANSPARENT, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_safe_walking);


        ///         getting current location//


        if (Util.Operations.isOnline(Right_Menu_Safe_Walking.this)) {

            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
            if (status != ConnectionResult.SUCCESS) {
                int requestCode = 10;
                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
                dialog.show();

            } else {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String provider = locationManager.getBestProvider(criteria, true);
                Location location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    onLocationChanged(location);
                }



            }


        } else {
            setContentView(R.layout.errorview);
            final ErrorView mErrorView = (ErrorView) findViewById(R.id.error_view);

            mErrorView.setOnRetryListener(new ErrorView.RetryListener() {
                @Override
                public void onRetry() {
                    Intent i = new Intent(Right_Menu_Safe_Walking.this, MainActivity.class);
                    startActivity(i);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mErrorView.setConfig(ErrorView.Config.create()
                                    .title(getString(R.string.error_title))
                                    .titleColor(getResources().getColor(R.color.bpBlue))
                                    .subtitle(getString(R.string.error_subtitle_failed_one_more_time))
                                    .retryText(getString(R.string.error_view_retry))
                                    .build());
                        }
                    }, 5000);
                }
            });
        }

        ///         getting current location//

        tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        SpannableStringBuilder SS = new SpannableStringBuilder(Html.fromHtml("<font color='#3c5899'><small><b>SAFE WALKING</b></small> </font>"));
        SS.setSpan(new CustomTypefaceSpan("#3c5899", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);

        txt_copyrights = (TextView) findViewById(R.id.dss);
        autoCompletetxt_source = (TextView) findViewById(R.id.act_source);
        autoCompletetxt_destination = (AutoCompleteTextView) findViewById(R.id.act_destination);
        btn_start_trip = (Button) findViewById(R.id.btn_start_trip);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        txt_copyrights.setTypeface(tf);
        autoCompletetxt_source.setTypeface(tf);
        autoCompletetxt_destination.setTypeface(tf);
        btn_start_trip.setTypeface(tf);
        btn_cancel.setTypeface(tf);
        // autoCompletetxt_source.setThreshold(1);
        btn_start_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Right_Menu_Safe_Walking.this, Map.class);
                startActivity(i);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Right_Menu_Safe_Walking.this, MainActivity.class);
                startActivity(i);

            }
        });


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(Right_Menu_Safe_Walking.this)
                .addOnConnectionFailedListener(this)
                .build();
        MapsInitializer.initialize(this);
        mGoogleApiClient.connect();


            /* autoCompletetxt_source.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final PlaceAutoCompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
                final String placeId = String.valueOf(item.placeId);
                Log.d("tag", "Autocomplete item selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (!places.getStatus().isSuccess()) {
                            Log.e("tag", "Place query did not complete. Error: " + places.getStatus().toString());
                            places.release();
                            return;
                        }
                        final Place place = places.get(0);
                        start = place.getLatLng();
                    }
                });

            }
        });*/

        autoCompletetxt_destination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final PlaceAutoCompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
                final String placeId = String.valueOf(item.placeId);
                Log.i("tag", "Autocomplete item selected: " + item.description);
                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (!places.getStatus().isSuccess()) {
                            Log.e("tag", "Place query did not complete. Error: " + places.getStatus().toString());
                            places.release();
                            return;
                        }
                        final Place place = places.get(0);

                        end = place.getLatLng();
                        end_lat = curent_location.latitude;
                        end_lng = curent_location.longitude;
                        Log.d("tag", "ENDLATTTT" + end_lat);
                        Log.d("tag", "END LONGGG" + end_lng);


                        SharedPreferences sharedpreferences = getSharedPreferences("PREF", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sharedpreferences.edit();
                        edit.remove("SLat");
                        edit.remove("SLng");
                        edit.remove("ELat");
                        edit.remove("ELng");

                        edit.putLong("SLat", Double.doubleToLongBits(curent_location.latitude));
                        edit.putLong("SLng", Double.doubleToLongBits(curent_location.longitude));
                        edit.putLong("ELat", Double.doubleToLongBits(end.latitude));
                        edit.putLong("ELng", Double.doubleToLongBits(end.longitude));

                        Log.d("tag","start lat long"+curent_location.latitude+"= = = ="+curent_location.longitude);

                        Log.d("tag","start lat long"+curent_location.latitude+"= = ="+curent_location.longitude);
                       edit.apply();

                    }
                });

            }
        });

        mAdapter = new PlaceAutoCompleteAdapter(Right_Menu_Safe_Walking.this, R.layout.list, mGoogleApiClient, BOUNDS_JAMAICA, null);
        // autoCompletetxt_source.setAdapter(mAdapter);
        autoCompletetxt_destination.setAdapter(mAdapter);
        // autoCompletetxt_source.setTypeface(tf);
        autoCompletetxt_destination.setTypeface(tf);


        setStatusBar();
    }
    protected void setStatusBar() {
        if (isTransparent) {
            StatusBarUtil.setTransparent(this);
        } else {
            StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
        }
    }
    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

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
    public void onLocationChanged(Location location) {

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        curent_location = new LatLng(latitude, longitude);

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
}
