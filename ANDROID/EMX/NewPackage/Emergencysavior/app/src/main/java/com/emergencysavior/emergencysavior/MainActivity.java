package com.emergencysavior.emergencysavior;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jaeger.library.StatusBarUtil;
import com.sandro.restaurant.Restaurant;
import com.sloop.fonts.FontsManager;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import at.markushi.ui.CircleButton;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import io.fabric.sdk.android.Fabric;
import tr.xip.errorview.ErrorView;

/**
 * Created by ANANDH on 10/3/2015.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LocationListener, View.OnClickListener {
    Typeface tf;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    static TextView Needhelp, addrees, user_profile_name, user_profile_email;
    double latitude, longitude;
    GoogleMap googleMap;
    String json_firstname, email, imagepath, token, sh_vale_session_token, addressLine, gps_location, message, tranctional_type, tranctional_id;
    String shared_preference_audio, shared_preference_gps, shared_preference_message, shared_preference_email, shared_preference_call_911, provider;
    CircleImageView user_icon;
    private ProgressDialog dialog;
    private String str_address;
    SweetAlertDialog pDialog;
    private boolean isBgChanged, isBgChanged1, isBgChanged2;
    //////////////////// audio record/////////////////////////////////////
    private MediaRecorder myRecorder;
    MediaPlayer mediaPlayer;
    private String outputFile = null;
    TimerTask timerTask;
    Timer timer;
    final Handler handler = new Handler();
    /////////////////////////////////////////////////////////////////
    public static final String EXTRA_IS_TRANSPARENT = "is_transparent";
    private boolean isTransparent;
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> M A P I N T I A L >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    LocationManager locationManager;
    final Context context = this;
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> M A P I N T I A L >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    CircleButton Need, alarm, call, iamok, disaster, activeshooter, seesomething, postStatusUpdateButton, tweet;
    CircleButton btn_call, btn_emergency_siren, btn_policeman;
    CircleButton btn_emergency_earthquake, btn_emergency_flood, btn_emergency_storms, btn_emergency_tsunami, btn_emergency_volcanoe, btn_emergency_iamok;

    public void startTimer() {

        Toast.makeText(getApplicationContext(), "Start Recording ", Toast.LENGTH_LONG).show();
        Log.d("tag", "Timer starting now ");
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 20000, 10000); //
    }

    public void initializeTimerTask() {
        Toast.makeText(getApplicationContext(), "Stop Recording", Toast.LENGTH_LONG).show();
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


    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        json_firstname = sharedPreferences.getString("Firstname", "");
        imagepath = sharedPreferences.getString("Image_url", "");
        String encodedImage = sharedPreferences.getString("zxc", "");
        if (!encodedImage.equals("")) {
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            user_icon.setImageBitmap(decodedByte);
        }
    }


    // on Facebook//
    private static final String PERMISSION = "publish_actions";
    private static final Location SEATTLE_LOCATION = new Location("") {
        {
            setLatitude(47.6097);
            setLongitude(-122.3331);
        }
    };

    private final String PENDING_ACTION_BUNDLE_KEY =
            "com.emergencysavior.emergencysavior.hellofacebook:PendingAction";


    private PendingAction pendingAction = PendingAction.NONE;
    private boolean canPresentShareDialog;
    private boolean canPresentShareDialogWithPhotos;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private ShareDialog shareDialog;
    private FacebookCallback<Sharer.Result> shareCallback = new FacebookCallback<Sharer.Result>() {
        @Override
        public void onCancel() {
            Log.d("HelloFacebook", "Canceled");
        }

        @Override
        public void onError(FacebookException error) {
            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
            String title = getString(R.string.error);
            String alertMessage = error.getMessage();
            showResult(title, alertMessage);
        }

        @Override
        public void onSuccess(Sharer.Result result) {
            Log.d("HelloFacebook", "Success!");
            if (result.getPostId() != null) {
                String title = getString(R.string.success);
                String id = result.getPostId();
                String alertMessage = getString(R.string.successfully_posted_post, id);
                showResult(title, alertMessage);
            }
        }

        private void showResult(String title, String alertMessage) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(title)
                    .setMessage(alertMessage)
                    .setPositiveButton(R.string.ok, null)
                    .show();
        }
    };

    private enum PendingAction {
        NONE,
        POST_PHOTO,
        POST_STATUS_UPDATE
    }
    // on Facebook//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isTransparent = getIntent().getBooleanExtra(EXTRA_IS_TRANSPARENT, true);
        super.onCreate(savedInstanceState);

        // on create before onfacebook
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handlePendingAction();
                        updateUI();
                    }

                    @Override
                    public void onCancel() {
                        if (pendingAction != PendingAction.NONE) {
                            showAlert();
                            pendingAction = PendingAction.NONE;
                        }
                        updateUI();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        if (pendingAction != PendingAction.NONE
                                && exception instanceof FacebookAuthorizationException) {
                            showAlert();
                            pendingAction = PendingAction.NONE;
                        }
                        updateUI();
                    }

                    private void showAlert() {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle(R.string.cancelled)
                                .setMessage(R.string.permission_not_granted)
                                .setPositiveButton(R.string.ok, null)
                                .show();
                    }
                });

        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(
                callbackManager,
                shareCallback);

        if (savedInstanceState != null) {
            String name = savedInstanceState.getString(PENDING_ACTION_BUNDLE_KEY);
            pendingAction = PendingAction.valueOf(name);
        }

        // on create before facebook
        setContentView(R.layout.activity_main);
        MapFragment fm = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        FontsManager.initFormAssets(this, "fonts/ques.otf");       //initialization
        FontsManager.changeFonts(this);
        //************************************* User Name ***************************************************
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        json_firstname = sharedPreferences.getString("Firstname", "");
        sh_vale_session_token = sharedPreferences.getString("Session_token", "");
        //************************************* shared_preference_status ***************************************************
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        shared_preference_message = sharedpreferences.getString("messagestatus", "");
        shared_preference_audio = sharedpreferences.getString("audiostatus", "");
        shared_preference_gps = sharedpreferences.getString("gpsstatus", "");
        shared_preference_email = sharedpreferences.getString("emailstatus", "");
        shared_preference_call_911 = sharedpreferences.getString("call911status", "");
        //************************************* shared_preference_status ***************************************************
        token = sh_vale_session_token.toString();
        email = sharedPreferences.getString("Email", "");
        imagepath = sharedPreferences.getString("Image_url", "");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        SpannableStringBuilder SS = new SpannableStringBuilder("DASHBOARD");
        SS.setSpan(new CustomTypefaceSpan("DASHBOARD", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);
        addrees = (TextView) findViewById(R.id.addr);
        Needhelp = (TextView) findViewById(R.id.nee);
        Need = (CircleButton) findViewById(R.id.need_help);
        alarm = (CircleButton) findViewById(R.id.alarm);
        call = (CircleButton) findViewById(R.id.call1);
        iamok = (CircleButton) findViewById(R.id.iamok);
        disaster = (CircleButton) findViewById(R.id.btn_rescue_Me);
        activeshooter = (CircleButton) findViewById(R.id.activeshooter);
        seesomething = (CircleButton) findViewById(R.id.see_something);
        postStatusUpdateButton = (CircleButton) findViewById(R.id.facebook);
        tweet = (CircleButton) findViewById(R.id.twitter);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        user_icon = (CircleImageView) header.findViewById(R.id.user_profile_image);
        user_profile_name = (TextView) header.findViewById(R.id.user_profile_name);
        user_profile_email = (TextView) header.findViewById(R.id.user_profile_email);
        user_profile_name.setTypeface(tf);
        user_profile_email.setTypeface(tf);
        user_profile_name.setText(json_firstname);
        user_profile_email.setText(email);
        //********************************************************************************************************************
        // facebook //
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                updateUI();
                // It's possible that we were waiting for Profile to be populated in order to
                // post a status update.
                handlePendingAction();
            }
        };
        canPresentShareDialog = ShareDialog.canShow(ShareLinkContent.class);
        canPresentShareDialogWithPhotos = ShareDialog.canShow(
                SharePhotoContent.class);


        // facebook //
        /////////////////////////////////////////////////Dashboard operation//////////////////////////////////////////////////////////////////////////////////////
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  L E F T S I D E M E N U F O N T T Y P E F A C E >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);
            mi.getTitle();

            SubMenu subMenu = mi.getSubMenu();
            MenuItem subMenuItem = null;
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                    Log.d("sunmenu", "" + subMenuItem);
                }
            }
            applyFontToMenuItem(mi);
        }
        navigationView.setNavigationItemSelectedListener(this);
        if (Util.Operations.isOnline(MainActivity.this)) {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
            if (status != ConnectionResult.SUCCESS) {
                int requestCode = 10;
                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
                dialog.show();

            } else {


                googleMap = fm.getMap();
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String provider = locationManager.getBestProvider(criteria, true);
                Location location = locationManager.getLastKnownLocation(provider);

                if (location != null) {
                    onLocationChanged(location);
                }
                locationManager.requestLocationUpdates(provider, 40000, 0, this);
            }

        } else {
            setContentView(R.layout.errorview);
            final ErrorView mErrorView = (ErrorView) findViewById(R.id.error_view);

            mErrorView.setOnRetryListener(new ErrorView.RetryListener() {
                @Override
                public void onRetry() {
                    Intent i = new Intent(MainActivity.this, MainActivity.class);
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


            //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> M A P  O P E R A T I O N >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        }
        setStatusBar();
    }

    protected void setStatusBar() {
        if (isTransparent) {
            StatusBarUtil.setTransparent(this);
        } else {
            StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
        }
    }

    // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ L O C A T I O N  U P D A T E $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$


    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        // drawMarker(latLng);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        addrees.setText("Latitude:" + latitude + ", Longitude:" + longitude);
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {

            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {

                }
                sb.append(addresses.get(0).getAddressLine(0)).append(",");
                sb.append(addresses.get(0).getLocality()).append(",");
                sb.append(addresses.get(0).getCountryName());
                addrees.setText(sb);
                addressLine = sb.toString();
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedpreferences.edit();
                edit.remove("addressLine");
                edit.putString("addressLine", String.valueOf(sb));

                Log.d("tag","address"+addressLine);
                edit.apply();
            } else {

                addrees.setText("No Address returned!");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            addrees.setText("Canont get Address!");
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
        Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(callGPSSettingIntent);

    }

    private void drawMarker(LatLng point) {
        googleMap.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(point);
        markerOptions.title("Position");
        markerOptions.snippet(addressLine);
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(point));
    }


    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ L O C A T I O N U P D A T E $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

    public void buttonOnClick(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        AlertDialog alertDialog;

        switch (view.getId()) {

            case R.id.call1:
                alertDialogBuilder.setTitle("ALERT");
                alertDialogBuilder
                        .setMessage("Please confirm ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                tranctional_type = ("call911");
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
            case R.id.iamok:
                alertDialogBuilder.setTitle("ALERT");
                alertDialogBuilder
                        .setMessage("Please confirm")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                tranctional_type = ("iamok");
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
            case R.id.alarm:
                alertDialogBuilder.setTitle("ALERT");
                alertDialogBuilder
                        .setMessage("Please confirm  ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                tranctional_type = ("alarm");
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
            case R.id.btn_rescue_Me:
                gps_location = (CONFIG.GPS_URL_LOCATION + latitude + "," + longitude);
                tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
                Dialog alertDialog_rescue = new Dialog(MainActivity.this);
                alertDialog_rescue.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertDialog_rescue.setContentView(R.layout.rescue_me_custom_dialog);
                alertDialog_rescue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                btn_emergency_earthquake = (CircleButton) alertDialog_rescue.findViewById(R.id.emergency_earthquake);
                btn_emergency_flood = (CircleButton) alertDialog_rescue.findViewById(R.id.emergency_flood);
                btn_emergency_storms = (CircleButton) alertDialog_rescue.findViewById(R.id.emergency_storms);
                btn_emergency_tsunami = (CircleButton) alertDialog_rescue.findViewById(R.id.emergency_tsunami);
                btn_emergency_volcanoe = (CircleButton) alertDialog_rescue.findViewById(R.id.emergency_volcanoe);
                btn_emergency_iamok = (CircleButton) alertDialog_rescue.findViewById(R.id.emergency_iamok);


                final TextView txt_earthquake = (TextView) alertDialog_rescue.findViewById(R.id.txt_earthquake);
                final TextView txt_flood = (TextView) alertDialog_rescue.findViewById(R.id.txt_flood);
                final TextView txt_storms = (TextView) alertDialog_rescue.findViewById(R.id.txt_storms);
                final TextView tx_tsunami = (TextView) alertDialog_rescue.findViewById(R.id.tx_tsunami);
                final TextView txt_volcanoes = (TextView) alertDialog_rescue.findViewById(R.id.txt_volcanoes);
                final TextView txt_iamok = (TextView) alertDialog_rescue.findViewById(R.id.txt_iamok);

                btn_emergency_earthquake.setOnClickListener(this);
                btn_emergency_flood.setOnClickListener(this);
                btn_emergency_storms.setOnClickListener(this);
                btn_emergency_tsunami.setOnClickListener(this);
                btn_emergency_volcanoe.setOnClickListener(this);
                btn_emergency_iamok.setOnClickListener(this);

                txt_earthquake.setTypeface(tf);
                txt_flood.setTypeface(tf);
                txt_storms.setTypeface(tf);
                tx_tsunami.setTypeface(tf);
                txt_volcanoes.setTypeface(tf);
                txt_iamok.setTypeface(tf);


                alertDialog_rescue.show();
                break;
            case R.id.see_something:
                alertDialogBuilder.setTitle("ALERT");
                alertDialogBuilder
                        .setMessage("Please confirm")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                tranctional_type = ("see_something");
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
            case R.id.facebook:

                onClickPostStatusUpdate();
                break;
            case R.id.twitter:

                TwitterAuthConfig authConfig = new TwitterAuthConfig(CONFIG.TWITTER_KEY,CONFIG.TWITTER_SECRET);
                Fabric.with(this, new Twitter(authConfig));
                startActivity(new Intent(this, AndroidTwitter.class));
                break;
        }


    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        AlertDialog alertDialog;
        switch (v.getId()) {
            case R.id.emergency_earthquake:
                alertDialogBuilder.setTitle("ALERT");
                alertDialogBuilder
                        .setMessage("Please confirm ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                tranctional_type = ("Earthquake");
                                new MyActivityAsync_rescue_me(tranctional_type).execute();
                                pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("Please wait... ");
                                pDialog.setCancelable(false);
                                pDialog.getWindow().setLayout(500, 200);
                                pDialog.show();
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
            case R.id.emergency_flood:
                alertDialogBuilder.setTitle("ALERT");
                alertDialogBuilder
                        .setMessage("Please confirm ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                tranctional_type = ("Emergency Flood");
                                new MyActivityAsync_rescue_me(tranctional_type).execute();
                                pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("Please wait... ");
                                pDialog.setCancelable(false);
                                pDialog.getWindow().setLayout(500, 200);
                                pDialog.show();
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
            case R.id.emergency_storms:
                alertDialogBuilder.setTitle("ALERT");
                alertDialogBuilder
                        .setMessage("Please confirm ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                tranctional_type = ("Emergency Storms");
                                new MyActivityAsync_rescue_me(tranctional_type).execute();
                                pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("Please wait... ");
                                pDialog.setCancelable(false);
                                pDialog.getWindow().setLayout(500, 200);
                                pDialog.show();
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
            case R.id.emergency_tsunami:
                alertDialogBuilder.setTitle("ALERT");
                alertDialogBuilder
                        .setMessage("Please confirm ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                tranctional_type = ("Emergency Tsunami");
                                new MyActivityAsync_rescue_me(tranctional_type).execute();
                                pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("Please wait... ");
                                pDialog.setCancelable(false);
                                pDialog.getWindow().setLayout(500, 200);
                                pDialog.show();
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
            case R.id.emergency_volcanoe:
                alertDialogBuilder.setTitle("ALERT");
                alertDialogBuilder
                        .setMessage("Please confirm ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                tranctional_type = ("Emergency Volcano");
                                new MyActivityAsync_rescue_me(tranctional_type).execute();
                                pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("Please wait... ");
                                pDialog.setCancelable(false);
                                pDialog.getWindow().setLayout(500, 200);
                                pDialog.show();
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
            case R.id.emergency_iamok:
                alertDialogBuilder.setTitle("ALERT");
                alertDialogBuilder
                        .setMessage("Please confirm ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                tranctional_type = ("Emergency I am ok");
                                new MyActivityAsync_rescue_me(tranctional_type).execute();
                                pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("Please wait... ");
                                pDialog.setCancelable(false);
                                pDialog.getWindow().setLayout(500, 200);
                                pDialog.show();
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

    public void stop() {
        try {


            myRecorder.stop();
            myRecorder.reset();
            myRecorder.release();
            // myRecorder = null;
            Need.setEnabled(false);
            new Uploadaudio().execute();
            Log.d("tag", "audiopath=====>" + outputFile);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
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

    private void applyFontToMenuItem(MenuItem mi) {
        tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", tf), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
            int end = spanString.length();
            spanString.setSpan(new CustomTypefaceSpan("", font), 0, spanString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            item.setTitle(spanString);
            applyFontToMenuItem(item);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.safe_walking) {

            Intent i = new Intent(getApplicationContext(), Safewalking.class);
            startActivity(i);

            return true;
        }
        if (id == R.id.walk_timer) {
            Intent i = new Intent(MainActivity.this, Right_Menu_WalkTimer.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.real_time_tracking) {
            Intent i = new Intent(getApplicationContext(), Right_Menu_Real_time_tracking.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.crime_reporting) {
            Intent i = new Intent(getApplicationContext(), Right_Menu_Crime_Reporting.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.crime_Tracking) {
            Intent i = new Intent(getApplicationContext(), Right_Menu_Crime_Tracking.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.Scanqrcode) {

            Intent intent = new Intent(getApplicationContext(), ScannerActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.settings) {

            Intent intent = new Intent(getApplicationContext(), Right_Menu_Settings.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.Emergency_Event) {
            Intent i = new Intent(getApplicationContext(), Emergency_Event_Preference.class);
            startActivity(i);

        } else if (id == R.id.Profile) {
            Intent i = new Intent(getApplicationContext(), Profileupdate.class);
            startActivity(i);

        } else if (id == R.id.Contacts) {
            Intent i = new Intent(getApplicationContext(), Contacts.class);
            startActivity(i);


        } else if (id == R.id.User_Guide) {

        } else if (id == R.id.Emergency_event_History) {
            Intent i = new Intent(getApplicationContext(), Emergency_event_history.class);
            startActivity(i);

        } else if (id == R.id.flashlight) {
            Intent i = new Intent(getApplicationContext(), Flashlight.class);
            startActivity(i);
        } else if (id == R.id.Privacy_policy) {

            Intent i = new Intent(getApplicationContext(), Privacy.class);
            startActivity(i);

        } else if (id == R.id.Term_and_Conditions) {
            Intent i = new Intent(getApplicationContext(), Terms.class);
            startActivity(i);

        } else if (id == R.id.Exit) {
            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)

                    .setTitleText("Logout")
                    .setCancelText("No,cancel!")
                    .setConfirmText("Yes,Logout!")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.remove("Email");
                            edit.remove("Password");
                            edit.remove("Session_token");
                            edit.remove("Image_url");
                            edit.remove("Firstname");
                            edit.remove("Lastname");
                            edit.remove("Residential_phone");
                            edit.remove("Mobile");
                            edit.remove("Dob");
                            edit.remove("Gender");
                            edit.remove("Address");
                            edit.remove("City");
                            edit.remove("State");
                            edit.remove("Zipcode");
                            edit.remove("zxc");
                            edit.apply();
                            Intent i = new Intent(getApplicationContext(), Login.class);
                            startActivity(i);
                            finish();


                        }
                    })
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.cancel();
                        }
                    })
                    .show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> P R O F I L E  A U D I O  U P L O A D >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private class Uploadaudio extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
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
        protected void onPostExecute(String result) {
            Log.e("TAG", "audio_send====>: " + result);
            super.onPostExecute(result);
            dialog.dismiss();


        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("ALERT")
                .setMessage("Are you sure you want to exit")
                .setNegativeButton(android.R.string.no, null)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                        Intent i1 = new Intent(Intent.ACTION_MAIN);
                        i1.setAction(Intent.ACTION_MAIN);
                        i1.addCategory(Intent.CATEGORY_HOME);
                        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i1);
                        finish();
                    }
                }).create().show();

    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> M E S S A G E  U P L O A D >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private class message extends AsyncTask<String, Void, String> {
        String message;

        public message(String message) {

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
            Log.d("tag", "<-----message_json output---->" + jsonStr);
            super.onPostExecute(jsonStr);
            pDialog.dismiss();
            if (CONFIG.isStringNullOrWhiteSpace(jsonStr)) {

                new Restaurant(MainActivity.this, "Internet connectivity issue", Snackbar.LENGTH_LONG)
                        .setBackgroundColor(Color.RED)
                        .setTextColor(Color.WHITE)
                        .show();
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
            Log.d("tag", "message what final==>" + message);
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
            Log.d("tag", "<-----messageresult---->" + jsonStr);
            super.onPostExecute(jsonStr);
        }
    }

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
            Log.d("tag", "<-----mail result---->" + jsonStr);
            super.onPostExecute(jsonStr);
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

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    isPhoneCalling = false;
                }

            }
        }
    }

    private class MyActivityAsync extends AsyncTask<String, Void, String> {
        String tranctional_type;

        public MyActivityAsync(String tranctional_type) {
            this.tranctional_type = tranctional_type;
        }

        protected void onPreExecute() {
            pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
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

                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("ALERT")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();
            } else {
                try {
                    JSONObject jo = new JSONObject(jsonStr);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                    tranctional_id = jo.getString("transaction_id");
                    if (status.equals("success")) {
                        switch (tranctional_type) {
                            case "call911":
                                gps_location = (CONFIG.GPS_URL_LOCATION + latitude + "," + longitude);
                                if (shared_preference_message.equals("true")) {
                                    message = (json_firstname + "has called 911,send police to:" + addressLine + " " + gps_location);
                                    new message(message).execute();
                                }
                                if (shared_preference_audio.equals("true")) {
                                    message = (json_firstname + "has called 911,send police to:" + addressLine);
                                    new message_from_audio(message).execute();
                                }
                                if (shared_preference_email.equals("true")) {
                                    message = (json_firstname + "has called 911,send police to:" + addressLine + " " + gps_location);
                                    new message_from_email(message, latitude, longitude).execute();
                                }
                                if (shared_preference_call_911.equals("true")) {
                                    PhoneCallListener phoneListener = new PhoneCallListener();
                                    TelephonyManager telephonyManager = (TelephonyManager) MainActivity.this.getSystemService(Context.TELEPHONY_SERVICE);
                                    telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:911"));
                                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        return;
                                    }
                                    startActivity(callIntent);
                                }

                                if (shared_preference_gps.equals("true")) {
                                    message = (json_firstname + "has called 911,send police to:" + addressLine + " " + gps_location);
                                }
                                break;
                            case "need_help":
                                gps_location = (CONFIG.GPS_URL_LOCATION + latitude + "," + longitude);
                                if (shared_preference_message.equals("true")) {
                                    message = (json_firstname + "needs help at: " + addressLine + " " + gps_location);
                                    new message(message).execute();
                                }
                                if (shared_preference_audio.equals("true")) {

                                    start();
                                    Need.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), "Recording starting", Toast.LENGTH_LONG).show();
                                }
                                if (shared_preference_email.equals("true")) {
                                    message = (json_firstname + "needs help at: " + addressLine + " " + gps_location);
                                    new message_from_email(message, latitude, longitude).execute();
                                }
                                if (shared_preference_gps.equals("true")) {
                                    message = (json_firstname + "needs help at: " + addressLine + " " + gps_location);

                                }
                                break;

                            case "activeshooter":
                                gps_location = (CONFIG.GPS_URL_LOCATION + latitude + "," + longitude);
                                if (shared_preference_message.equals("true")) {
                                    message = (json_firstname + " has Active Shooter at:" + addressLine + " " + gps_location);
                                    new message(message).execute();
                                }
                                if (shared_preference_audio.equals("true")) {
                                    message = (json_firstname + " has Active Shooter at:" + addressLine + " " + gps_location);
                                    new message_from_audio(message).execute();
                                }
                                if (shared_preference_email.equals("true")) {
                                    message = (json_firstname + " has Active Shooter at:" + addressLine + " " + gps_location);
                                    new message_from_email(message, latitude, longitude).execute();
                                }

                                if (shared_preference_gps.equals("true")) {
                                    message = (json_firstname + " has Active Shooter at:" + addressLine + " " + gps_location);
                                }
                                break;
                            case "iamok":
                                gps_location = (CONFIG.GPS_URL_LOCATION + latitude + "," + longitude);
                                if (shared_preference_message.equals("true")) {
                                    message = (json_firstname + "is Ok .. No Help Needed at:" + addressLine + " " + gps_location);
                                    new message(message).execute();
                                }
                                if (shared_preference_audio.equals("true")) {
                                    message = (json_firstname + "is Ok .. No Help Needed at:" + addressLine + " " + gps_location);
                                    new message_from_audio(message).execute();
                                }
                                if (shared_preference_email.equals("true")) {
                                    message = (json_firstname + "is Ok .. No Help Needed at:" + addressLine + " " + gps_location);
                                    new message_from_email(message, latitude, longitude).execute();
                                }
                                if (shared_preference_gps.equals("true")) {
                                    message = (json_firstname + "is Ok .. No Help Needed at:" + addressLine + " " + gps_location);
                                }
                                break;
                            case "alarm":
                                gps_location = (CONFIG.GPS_URL_LOCATION + latitude + "," + longitude);
                                tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
                                Dialog alertDialog = new Dialog(MainActivity.this);
                                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                alertDialog.setContentView(R.layout.custom_dialog);
                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                btn_call = (CircleButton) alertDialog.findViewById(R.id.emergency_call);
                                btn_emergency_siren = (CircleButton) alertDialog.findViewById(R.id.emergency_siren);
                                btn_policeman = (CircleButton) alertDialog.findViewById(R.id.emergency_policeman);
                                final TextView txt_siren = (TextView) alertDialog.findViewById(R.id.txt_siren);
                                final TextView txt_call_911 = (TextView) alertDialog.findViewById(R.id.txt_call_911);
                                final TextView txt_policeman = (TextView) alertDialog.findViewById(R.id.txt_policeman);
                                txt_siren.setTypeface(tf);
                                txt_call_911.setTypeface(tf);
                                txt_policeman.setTypeface(tf);
                                btn_call.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        isBgChanged = !isBgChanged;
                                        if (isBgChanged) {
                                            isBgChanged1 = false;
                                            isBgChanged2 = false;

                                            if (mediaPlayer != null) {
                                                mediaPlayer.stop();
                                            }
                                            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.voice);
                                            mediaPlayer.start();

                                            btn_call.setImageResource(R.drawable.ic_stop);
                                            btn_emergency_siren.setImageResource(R.drawable.flood);
                                            btn_policeman.setImageResource(R.drawable.ic_police);
                                            if (shared_preference_email.equals("true")) {
                                                message = (json_firstname + " has pressed  911 send  police to: " + addressLine + " " + gps_location);
                                                new message_from_email(message, latitude, longitude).execute();
                                            }
                                            if (shared_preference_message.equals("true")) {
                                                message = (json_firstname + " has pressed  911 send  police to: " + addressLine + " " + gps_location);
                                                new message(message).execute();
                                            }
                                        } else {
                                            mediaPlayer.stop();

                                            btn_call.setImageResource(R.drawable.call);
                                            btn_emergency_siren.setImageResource(R.drawable.flood);
                                            btn_policeman.setImageResource(R.drawable.ic_police);

                                        }


                                    }
                                });
                                btn_emergency_siren.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        isBgChanged1 = !isBgChanged1;
                                        if (isBgChanged1) {
                                            isBgChanged = false;
                                            isBgChanged2 = false;
                                            if (mediaPlayer != null) {
                                                mediaPlayer.stop();
                                            }
                                            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.ssound);
                                            mediaPlayer.start();
                                            btn_emergency_siren.setImageResource(R.drawable.ic_stop);
                                            btn_call.setImageResource(R.drawable.call);
                                            btn_policeman.setImageResource(R.drawable.ic_police);

                                            if (shared_preference_message.equals("true")) {
                                                message = (json_firstname + " has pressed  Siren send  police to:" + addressLine + " " + gps_location);
                                                new message(message).execute();
                                            }
                                            if (shared_preference_email.equals("true")) {
                                                message = (json_firstname + " has pressed  Siren send  police to:" + addressLine + " " + gps_location);
                                                new message_from_email(message, latitude, longitude).execute();
                                            }

                                        } else {
                                            mediaPlayer.stop();

                                            btn_emergency_siren.setImageResource(R.drawable.flood);
                                            btn_call.setImageResource(R.drawable.call);
                                            btn_policeman.setImageResource(R.drawable.ic_police);
                                        }


                                    }
                                });
                                btn_policeman.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        isBgChanged2 = !isBgChanged2;

                                        if (isBgChanged2) {
                                            isBgChanged = false;
                                            isBgChanged1 = false;
                                            if (mediaPlayer != null) {
                                                mediaPlayer.stop();
                                            }
                                            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.policeman);
                                            mediaPlayer.start();
                                            btn_policeman.setImageResource(R.drawable.ic_stop);
                                            btn_emergency_siren.setImageResource(R.drawable.flood);
                                            btn_call.setImageResource(R.drawable.call);


                                            if (shared_preference_message.equals("true")) {
                                                message = (json_firstname + "has pressed  Policeman send  police to:" + addressLine + " " + gps_location);
                                                new message(message).execute();
                                            }
                                            if (shared_preference_email.equals("true")) {
                                                message = (json_firstname + "has pressed  Policeman send  police to:" + addressLine + " " + gps_location);
                                                new message_from_email(message, latitude, longitude).execute();
                                            }


                                        } else {
                                            mediaPlayer.stop();

                                            btn_policeman.setImageResource(R.drawable.ic_police);
                                            btn_emergency_siren.setImageResource(R.drawable.flood);
                                            btn_call.setImageResource(R.drawable.call);
                                        }
                                    }
                                });
                                alertDialog.show();
                                break;
                            case "see_something":
                                Intent i = new Intent(getApplicationContext(), Seesomething_dashboard.class);
                                startActivity(i);
                                break;

                        }
                    } else {
                        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("INVALID")
                                .setContentText("Try after Sometime")
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

    private class MyActivityAsync_rescue_me extends AsyncTask<String, Void, String> {
        String tranctional_type;

        public MyActivityAsync_rescue_me(String tranctional_type) {
            this.tranctional_type = tranctional_type;
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
            if (jsonStr == "") {

                new Restaurant(MainActivity.this, "Internet connectivity issue", Snackbar.LENGTH_LONG)
                        .setBackgroundColor(Color.RED)
                        .setTextColor(Color.WHITE)
                        .show();
                Intent act_back = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(act_back);
                pDialog.dismiss();
            } else {
                try {
                    JSONObject jo = new JSONObject(jsonStr);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                    tranctional_id = jo.getString("transaction_id");
                    if (status.equals("success")) {
                        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        str_address = sharedpreferences.getString("addressLine", "");
                        Log.d("tag", "str_address" + str_address);
                        switch (tranctional_type) {
                            case "Earthquake":
                                gps_location = (CONFIG.GPS_URL_LOCATION + latitude + "," + longitude);

                                if (shared_preference_email.equals("true")) {
                                    message = ("This is " + json_firstname + "There is an Earthquake in my area … send help immediately " + str_address + " " + gps_location);
                                    new message_from_email(message, latitude, longitude).execute();
                                }

                                if (shared_preference_message.equals("true")) {
                                    message = ("”This is " + json_firstname + "There is an Earthquake in my area … send help immediately " + str_address /*+ " " + gps_location*/);
                                    Log.d("tag", "message E  = " + message);
                                    new message_rescue_me(message).execute();
                                }

                                break;
                            case "Emergency Flood":
                                gps_location = (CONFIG.GPS_URL_LOCATION + latitude + "," + longitude);
                                if (shared_preference_email.equals("true")) {
                                    message = ("This is " + json_firstname + "There is an Flood in my area … send help immediately " + str_address + " " + gps_location);
                                    new message_from_email(message, latitude, longitude).execute();
                                }
                                if (shared_preference_message.equals("true")) {

                                    message = ("This is " + json_firstname + " There is an Flood in my area … send help immediately " + str_address /*+ " " + gps_location*/);
                                    Log.d("tag", "message F  = " + message);
                                    new message_rescue_me(message).execute();
                                }

                                break;
                            case "Emergency Storms":
                                gps_location = (CONFIG.GPS_URL_LOCATION + latitude + "," + longitude);
                                if (shared_preference_email.equals("true")) {
                                    message = ("This is " + json_firstname + "There is an Major Storm in my area … send help immediately " + str_address + " " + gps_location);
                                    new message_from_email(message, latitude, longitude).execute();
                                }

                                if (shared_preference_message.equals("true")) {
                                    message = ("This is " + json_firstname + " There is an Major Storm in my area ... send help immediately " + str_address /*+ " " + gps_location*/);
                                    Log.d("tag", "message S  = " + message);
                                    new message_rescue_me(message).execute();
                                }
                                break;
                            case "Emergency Tsunami":
                                gps_location = (CONFIG.GPS_URL_LOCATION + latitude + "," + longitude);
                                if (shared_preference_email.equals("true")) {
                                    message = ("This is " + json_firstname + " There is an Large Tsunami in my area... send help immediately " + str_address + " " + gps_location);
                                    new message_from_email(message, latitude, longitude).execute();
                                }

                                if (shared_preference_message.equals("true")) {
                                    message = ("This is " + json_firstname + " There is an Large Tsunami in my area... send help immediately " + str_address /*+ " " + gps_location*/);
                                    Log.d("tag", "message T  = " + message);
                                    new message_rescue_me(message).execute();
                                }
                                break;
                            case "Emergency Volcano":

                                gps_location = (CONFIG.GPS_URL_LOCATION + latitude + "," + longitude);

                                if (shared_preference_email.equals("true")) {
                                    message = ("This is " + json_firstname + " There is an Erupting Volcano in my area... send help immediately " + str_address + " " + gps_location);
                                    new message_from_email(message, latitude, longitude).execute();
                                }

                                if (shared_preference_message.equals("true")) {
                                    message = ("This is " + json_firstname + " There is an Erupting Volcano in my area... send help immediately " + str_address /*+ " " + gps_location*/);
                                    Log.d("tag", "message V  = " + message);
                                    new message_rescue_me(message).execute();
                                }
                                break;


                            case "Emergency I am ok":
                                gps_location = (CONFIG.GPS_URL_LOCATION + latitude + "," + longitude);
                                if (shared_preference_email.equals("true")) {
                                    message = ("This is " + json_firstname + "is Ok .. No Help Needed at: " + str_address + " " + gps_location);
                                    new message_from_email(message, latitude, longitude).execute();
                                }


                                if (shared_preference_message.equals("true")) {
                                    message = (json_firstname + "is Ok .. No Help Needed at:" + str_address /*+ " " + gps_location*/);
                                    Log.d("tag", "message I  = " + message);
                                    new message_rescue_me(message).execute();
                                }
                                break;

                        }

                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
    }

    private class message_rescue_me extends AsyncTask<String, Void, String> {
        String message;

        public message_rescue_me(String message) {
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
            Log.d("tag", "<-----message_json output---->" + jsonStr);
            super.onPostExecute(jsonStr);

            if (jsonStr == "") {

                new Restaurant(MainActivity.this, "Internet connectivity issue", Snackbar.LENGTH_LONG)
                        .setBackgroundColor(Color.RED)
                        .setTextColor(Color.WHITE)
                        .show();
                Intent act_back = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(act_back);
                pDialog.dismiss();
            }
            try {
                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");

                if (status.equals("success")) {


                    Intent act_back = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(act_back);
                    pDialog.dismiss();
                } else {

                    Intent act_back = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(act_back);
                    Toast.makeText(getApplicationContext(), "Message not Sent", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            pDialog.dismiss();

        }
    }


    // on facebook share

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(PENDING_ACTION_BUNDLE_KEY, pendingAction.name());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
    }

    private void updateUI() {
        boolean enableButtons = AccessToken.getCurrentAccessToken() != null;

        postStatusUpdateButton.setEnabled(enableButtons || canPresentShareDialog);
        //  postPhotoButton.setEnabled(enableButtons || canPresentShareDialogWithPhotos);

        Profile profile = Profile.getCurrentProfile();
        if (enableButtons && profile != null) {
            //   profilePictureView.setProfileId(profile.getId());
            // greeting.setText(getString(R.string.hello_user, profile.getFirstName()));
        } else {
            // profilePictureView.setProfileId(null);
            // greeting.setText(null);
        }
    }

    private void handlePendingAction() {
        PendingAction previouslyPendingAction = pendingAction;
        // These actions may re-set pendingAction if they are still pending, but we assume they
        // will succeed.
        pendingAction = PendingAction.NONE;

        switch (previouslyPendingAction) {
            case NONE:
                break;
            case POST_PHOTO:
                postPhoto();
                break;
            case POST_STATUS_UPDATE:
                postStatusUpdate();
                break;
        }
    }

    private void onClickPostStatusUpdate() {
        performPublish(PendingAction.POST_STATUS_UPDATE, canPresentShareDialog);
    }

    private void postStatusUpdate() {
        Profile profile = Profile.getCurrentProfile();
        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentTitle("Emergencia Savior")
                .setContentDescription(
                        "The 'Emergencia Savior' sample  showcases simple Facebook integration")
                .setContentUrl(Uri.parse("http://developers.facebook.com/docs/android"))
                .build();
        if (canPresentShareDialog) {
            shareDialog.show(linkContent);
        } else if (profile != null && hasPublishPermission()) {
            ShareApi.share(linkContent, shareCallback);
        } else {
            pendingAction = PendingAction.POST_STATUS_UPDATE;
        }
    }

  /*  private void onClickPostPhoto() {
        performPublish(PendingAction.POST_PHOTO, canPresentShareDialogWithPhotos);
    }*/

    private void postPhoto() {
        Bitmap image = BitmapFactory.decodeResource(this.getResources(), R.drawable.profile);
        SharePhoto sharePhoto = new SharePhoto.Builder().setBitmap(image).build();
        ArrayList<SharePhoto> photos = new ArrayList<>();
        photos.add(sharePhoto);

        SharePhotoContent sharePhotoContent =
                new SharePhotoContent.Builder().setPhotos(photos).build();
        if (canPresentShareDialogWithPhotos) {
            shareDialog.show(sharePhotoContent);
        } else if (hasPublishPermission()) {
            ShareApi.share(sharePhotoContent, shareCallback);
        } else {
            pendingAction = PendingAction.POST_PHOTO;
            // We need to get new permissions, then complete the action when we get called back.
            LoginManager.getInstance().logInWithPublishPermissions(
                    this,
                    Arrays.asList(PERMISSION));
        }
    }

    private boolean hasPublishPermission() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null && accessToken.getPermissions().contains("publish_actions");
    }

    private void performPublish(PendingAction action, boolean allowNoToken) {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null || allowNoToken) {
            pendingAction = action;
            handlePendingAction();
        }
    }

    // on facebook end
}
