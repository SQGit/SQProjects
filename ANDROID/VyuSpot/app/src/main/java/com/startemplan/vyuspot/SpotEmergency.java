package com.startemplan.vyuspot;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.balysv.materialripple.MaterialRippleLayout;
import com.rey.material.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SpotEmergency extends Activity {


    static final int DIALOG_ERROR_CONNECTION = 1;
    public static String URL_REGISTER = "http://sqindia01.cloudapp.net/vyuspot/api/v1/spot/add";
    List<String> typelist = new ArrayList<String>();
    GpsT gps;
    Button send, lcbutton;
    TextView latlongt, typed, tltlg, lvltxt;
    MaterialRippleLayout mtp;
    String roleItem;
    double latitude1, longitude2;
    String type, location, level, hashTags, latitide, longitude;
    com.andexert.library.RippleView rsend;
    com.rey.material.widget.EditText mwatr, mloc, mhast;
    com.rey.material.widget.Spinner spin;
    String lvl, smsbody;
    SweetAlertDialog pDialog;
    public String token;


    String[] sting;
    int[] imgs;

    int ilvl;
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12;


    private Timer mTimer = null;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spot_emergency);
        Log.d("tag", "emergency");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SpotEmergency.this);
        token = sharedPreferences.getString("token", "");
        Log.d("tag", token);

        if (token.length() < 5) {

            new SweetAlertDialog(SpotEmergency.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Warning")
                    .setContentText("You Have to Register First!!!")
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            sweetAlertDialog.cancel();
                            Intent goD = new Intent(getApplicationContext(), Register.class);
                            startActivity(goD);


                        }
                    })
                    .show();

        }


        spin = (com.rey.material.widget.Spinner) findViewById(R.id.spinner);
        mtp = (MaterialRippleLayout) findViewById(R.id.ripple);
        rsend = (RippleView) findViewById(R.id.more);
        send = (Button) findViewById(R.id.send);
        lcbutton = (Button) findViewById(R.id.loc_but);
        latlongt = (TextView) findViewById(R.id.latlongt);
        typed = (TextView) findViewById(R.id.typed);
        tltlg = (TextView) findViewById(R.id.tltlg);
        mloc = (EditText) findViewById(R.id.locationm);
        // mwatr = (EditText) findViewById(R.id.waterlevelm);
        mhast = (EditText) findViewById(R.id.hashtagm);

        lvltxt = (TextView) findViewById(R.id.leveltext);

        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        b4 = (Button) findViewById(R.id.b4);
        b5 = (Button) findViewById(R.id.b5);
        b6 = (Button) findViewById(R.id.b6);
        b7 = (Button) findViewById(R.id.b7);
        b8 = (Button) findViewById(R.id.b8);
        b9 = (Button) findViewById(R.id.b9);
        b10 = (Button) findViewById(R.id.b10);
        b11 = (Button) findViewById(R.id.b11);
        b12 = (Button) findViewById(R.id.b12);


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/robol.ttf");

        send.setTypeface(tf);
        lcbutton.setTypeface(tf);
        latlongt.setTypeface(tf);
        mloc.setTypeface(tf);
//        mwatr.setTypeface(tf);
        mhast.setTypeface(tf);
        tltlg.setTypeface(tf);
        lvltxt.setTypeface(tf);
        b1.setTypeface(tf);
        b2.setTypeface(tf);
        b3.setTypeface(tf);
        b4.setTypeface(tf);
        b5.setTypeface(tf);
        b6.setTypeface(tf);
        b7.setTypeface(tf);
        b8.setTypeface(tf);
        b9.setTypeface(tf);
        b10.setTypeface(tf);
        b11.setTypeface(tf);
        b12.setTypeface(tf);


//        mwatr.requestFocus();


        typelist.add("Flood");
        typelist.add("Storm");
        typelist.add("Disaster");
        //typelist.add("Accident");
        //typelist.add("AVALANCHES");
        typelist.add("Droughts");
        typelist.add("Tsunami");
        typelist.add("Fire");
        //typelist.add("VOLCANIC ERUPTION");


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setBackgroundColor(getResources().getColor(R.color.orange));

                b2.setBackgroundColor(getResources().getColor(R.color.grayy));
                b3.setBackgroundColor(getResources().getColor(R.color.grayy));
                b4.setBackgroundColor(getResources().getColor(R.color.grayy));
                b5.setBackgroundColor(getResources().getColor(R.color.grayy));
                b6.setBackgroundColor(getResources().getColor(R.color.grayy));
                b7.setBackgroundColor(getResources().getColor(R.color.grayy));
                b8.setBackgroundColor(getResources().getColor(R.color.grayy));
                b9.setBackgroundColor(getResources().getColor(R.color.grayy));
                b10.setBackgroundColor(getResources().getColor(R.color.grayy));
                b11.setBackgroundColor(getResources().getColor(R.color.grayy));
                b12.setBackgroundColor(getResources().getColor(R.color.grayy));


                ilvl = 1;
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b2.setBackgroundColor(getResources().getColor(R.color.orange));

                b1.setBackgroundColor(getResources().getColor(R.color.grayy));
                b3.setBackgroundColor(getResources().getColor(R.color.grayy));
                b4.setBackgroundColor(getResources().getColor(R.color.grayy));
                b5.setBackgroundColor(getResources().getColor(R.color.grayy));
                b6.setBackgroundColor(getResources().getColor(R.color.grayy));
                b7.setBackgroundColor(getResources().getColor(R.color.grayy));
                b8.setBackgroundColor(getResources().getColor(R.color.grayy));
                b9.setBackgroundColor(getResources().getColor(R.color.grayy));
                b10.setBackgroundColor(getResources().getColor(R.color.grayy));
                b11.setBackgroundColor(getResources().getColor(R.color.grayy));
                b12.setBackgroundColor(getResources().getColor(R.color.grayy));
                ilvl = 2;
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b3.setBackgroundColor(getResources().getColor(R.color.orange));

                b2.setBackgroundColor(getResources().getColor(R.color.grayy));
                b1.setBackgroundColor(getResources().getColor(R.color.grayy));
                b4.setBackgroundColor(getResources().getColor(R.color.grayy));
                b5.setBackgroundColor(getResources().getColor(R.color.grayy));
                b6.setBackgroundColor(getResources().getColor(R.color.grayy));
                b7.setBackgroundColor(getResources().getColor(R.color.grayy));
                b8.setBackgroundColor(getResources().getColor(R.color.grayy));
                b9.setBackgroundColor(getResources().getColor(R.color.grayy));
                b10.setBackgroundColor(getResources().getColor(R.color.grayy));
                b11.setBackgroundColor(getResources().getColor(R.color.grayy));
                b12.setBackgroundColor(getResources().getColor(R.color.grayy));


                ilvl = 3;
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b4.setBackgroundColor(getResources().getColor(R.color.orange));

                b2.setBackgroundColor(getResources().getColor(R.color.grayy));
                b3.setBackgroundColor(getResources().getColor(R.color.grayy));
                b1.setBackgroundColor(getResources().getColor(R.color.grayy));
                b5.setBackgroundColor(getResources().getColor(R.color.grayy));
                b6.setBackgroundColor(getResources().getColor(R.color.grayy));
                b7.setBackgroundColor(getResources().getColor(R.color.grayy));
                b8.setBackgroundColor(getResources().getColor(R.color.grayy));
                b9.setBackgroundColor(getResources().getColor(R.color.grayy));
                b10.setBackgroundColor(getResources().getColor(R.color.grayy));
                b11.setBackgroundColor(getResources().getColor(R.color.grayy));
                b12.setBackgroundColor(getResources().getColor(R.color.grayy));
                ilvl = 4;
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b5.setBackgroundColor(getResources().getColor(R.color.orange));

                b2.setBackgroundColor(getResources().getColor(R.color.grayy));
                b3.setBackgroundColor(getResources().getColor(R.color.grayy));
                b4.setBackgroundColor(getResources().getColor(R.color.grayy));
                b1.setBackgroundColor(getResources().getColor(R.color.grayy));
                b6.setBackgroundColor(getResources().getColor(R.color.grayy));
                b7.setBackgroundColor(getResources().getColor(R.color.grayy));
                b8.setBackgroundColor(getResources().getColor(R.color.grayy));
                b9.setBackgroundColor(getResources().getColor(R.color.grayy));
                b10.setBackgroundColor(getResources().getColor(R.color.grayy));
                b11.setBackgroundColor(getResources().getColor(R.color.grayy));
                b12.setBackgroundColor(getResources().getColor(R.color.grayy));
                ilvl = 5;
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b6.setBackgroundColor(getResources().getColor(R.color.orange));

                b2.setBackgroundColor(getResources().getColor(R.color.grayy));
                b3.setBackgroundColor(getResources().getColor(R.color.grayy));
                b4.setBackgroundColor(getResources().getColor(R.color.grayy));
                b5.setBackgroundColor(getResources().getColor(R.color.grayy));
                b1.setBackgroundColor(getResources().getColor(R.color.grayy));
                b7.setBackgroundColor(getResources().getColor(R.color.grayy));
                b8.setBackgroundColor(getResources().getColor(R.color.grayy));
                b9.setBackgroundColor(getResources().getColor(R.color.grayy));
                b10.setBackgroundColor(getResources().getColor(R.color.grayy));
                b11.setBackgroundColor(getResources().getColor(R.color.grayy));
                b12.setBackgroundColor(getResources().getColor(R.color.grayy));
                ilvl = 6;
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b7.setBackgroundColor(getResources().getColor(R.color.orange));

                b2.setBackgroundColor(getResources().getColor(R.color.grayy));
                b3.setBackgroundColor(getResources().getColor(R.color.grayy));
                b4.setBackgroundColor(getResources().getColor(R.color.grayy));
                b5.setBackgroundColor(getResources().getColor(R.color.grayy));
                b6.setBackgroundColor(getResources().getColor(R.color.grayy));
                b1.setBackgroundColor(getResources().getColor(R.color.grayy));
                b8.setBackgroundColor(getResources().getColor(R.color.grayy));
                b9.setBackgroundColor(getResources().getColor(R.color.grayy));
                b10.setBackgroundColor(getResources().getColor(R.color.grayy));
                b11.setBackgroundColor(getResources().getColor(R.color.grayy));
                b12.setBackgroundColor(getResources().getColor(R.color.grayy));
                ilvl = 7;
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b8.setBackgroundColor(getResources().getColor(R.color.orange));

                b2.setBackgroundColor(getResources().getColor(R.color.grayy));
                b3.setBackgroundColor(getResources().getColor(R.color.grayy));
                b4.setBackgroundColor(getResources().getColor(R.color.grayy));
                b5.setBackgroundColor(getResources().getColor(R.color.grayy));
                b6.setBackgroundColor(getResources().getColor(R.color.grayy));
                b7.setBackgroundColor(getResources().getColor(R.color.grayy));
                b1.setBackgroundColor(getResources().getColor(R.color.grayy));
                b9.setBackgroundColor(getResources().getColor(R.color.grayy));
                b10.setBackgroundColor(getResources().getColor(R.color.grayy));
                b11.setBackgroundColor(getResources().getColor(R.color.grayy));
                b12.setBackgroundColor(getResources().getColor(R.color.grayy));
                ilvl = 8;
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b9.setBackgroundColor(getResources().getColor(R.color.orange));

                b2.setBackgroundColor(getResources().getColor(R.color.grayy));
                b3.setBackgroundColor(getResources().getColor(R.color.grayy));
                b4.setBackgroundColor(getResources().getColor(R.color.grayy));
                b5.setBackgroundColor(getResources().getColor(R.color.grayy));
                b6.setBackgroundColor(getResources().getColor(R.color.grayy));
                b7.setBackgroundColor(getResources().getColor(R.color.grayy));
                b8.setBackgroundColor(getResources().getColor(R.color.grayy));
                b1.setBackgroundColor(getResources().getColor(R.color.grayy));
                b10.setBackgroundColor(getResources().getColor(R.color.grayy));
                b11.setBackgroundColor(getResources().getColor(R.color.grayy));
                b12.setBackgroundColor(getResources().getColor(R.color.grayy));
                ilvl = 9;
            }
        });
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b10.setBackgroundColor(getResources().getColor(R.color.orange));

                b2.setBackgroundColor(getResources().getColor(R.color.grayy));
                b3.setBackgroundColor(getResources().getColor(R.color.grayy));
                b4.setBackgroundColor(getResources().getColor(R.color.grayy));
                b5.setBackgroundColor(getResources().getColor(R.color.grayy));
                b6.setBackgroundColor(getResources().getColor(R.color.grayy));
                b7.setBackgroundColor(getResources().getColor(R.color.grayy));
                b8.setBackgroundColor(getResources().getColor(R.color.grayy));
                b9.setBackgroundColor(getResources().getColor(R.color.grayy));
                b1.setBackgroundColor(getResources().getColor(R.color.grayy));
                b11.setBackgroundColor(getResources().getColor(R.color.grayy));
                b12.setBackgroundColor(getResources().getColor(R.color.grayy));
                ilvl = 10;
            }
        });
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b11.setBackgroundColor(getResources().getColor(R.color.orange));

                b2.setBackgroundColor(getResources().getColor(R.color.grayy));
                b3.setBackgroundColor(getResources().getColor(R.color.grayy));
                b4.setBackgroundColor(getResources().getColor(R.color.grayy));
                b5.setBackgroundColor(getResources().getColor(R.color.grayy));
                b6.setBackgroundColor(getResources().getColor(R.color.grayy));
                b7.setBackgroundColor(getResources().getColor(R.color.grayy));
                b8.setBackgroundColor(getResources().getColor(R.color.grayy));
                b9.setBackgroundColor(getResources().getColor(R.color.grayy));
                b10.setBackgroundColor(getResources().getColor(R.color.grayy));
                b1.setBackgroundColor(getResources().getColor(R.color.grayy));
                b12.setBackgroundColor(getResources().getColor(R.color.grayy));
                ilvl = 11;
            }
        });
        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b12.setBackgroundColor(getResources().getColor(R.color.orange));

                b2.setBackgroundColor(getResources().getColor(R.color.grayy));
                b3.setBackgroundColor(getResources().getColor(R.color.grayy));
                b4.setBackgroundColor(getResources().getColor(R.color.grayy));
                b5.setBackgroundColor(getResources().getColor(R.color.grayy));
                b6.setBackgroundColor(getResources().getColor(R.color.grayy));
                b7.setBackgroundColor(getResources().getColor(R.color.grayy));
                b8.setBackgroundColor(getResources().getColor(R.color.grayy));
                b9.setBackgroundColor(getResources().getColor(R.color.grayy));
                b10.setBackgroundColor(getResources().getColor(R.color.grayy));
                b11.setBackgroundColor(getResources().getColor(R.color.grayy));
                b1.setBackgroundColor(getResources().getColor(R.color.grayy));
                ilvl = 12;
            }
        });


        mhast.requestFocus();

        String[] countries = {"flood", "chennaiflood", "sunami", "chennaiflood", "chennaidrought", "chennai", "kadalur", "vilupuram", "thanjavur", "nagapatinam", "pondicheri"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spintext, R.id.stext, countries);
        mhast.setAdapter(adapter1);


        //ArrayAdapter<String> bran = new ArrayAdapter<String>(getApplicationContext(), R.layout.spintext, R.id.stext, typelist);
        //spin.setAdapter(bran);


        sting = new String[]{"Flood", "Storm",
                "Earthquake", "Tsunami", "Fire", "Others"};

        String[] subs = {"Heaven of all working codes ", "Google sub",
                "Microsoft sub", "Apple sub", "Yahoo sub", "Samsung sub"};


        imgs = new int[]{R.mipmap.listflood,
                R.mipmap.liststrom,
                R.mipmap.listdrought,
                R.mipmap.listsunami,
                R.mipmap.listfire,
                R.mipmap.listdisaster};


        spin.setAdapter(new MyAdapter(SpotEmergency.this, R.layout.spintext, sting));


        // mwatr.setHint("");


        mtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // mTimer.cancel();
            }
        });


       /* if (mTimer != null) {
            mTimer.cancel();
        } else {
            mTimer = new Timer();
        }
        mTimer.scheduleAtFixedRate(new goback(), 0, 30000);*/


        if (!isOnline(SpotEmergency.this)) {


            showDialog(DIALOG_ERROR_CONNECTION); //displaying the created dialog.
            //Toast.makeText(getApplicationContext(), "Oops Internet not connect Your details Send as img_emg SOS(sms charges may apply)...", Toast.LENGTH_SHORT).show();


        } else {
            //Internet available. Do what's required when internet is available.


            gps = new GpsT(SpotEmergency.this);
            if (gps.canGetLocation()) {
                latitude1 = gps.getLatitude();
                longitude2 = gps.getLongitude();
                String lati = String.valueOf(latitude1);
                String longi = String.valueOf(longitude2);
                String Addr = gps.getAddress();
                mloc.setText(Addr);
                latlongt.setText("Latitude:" + lati + "\nLongitude:\t" + longi);
            } else {
                SplashScreen sps = new SplashScreen();
                sps.buildAlertMessageNoGps();
                //gps.showSettingsAlert();
            }


        }


        spin.setOnItemSelectedListener(new com.rey.material.widget.Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(com.rey.material.widget.Spinner parent, View view, int position, long id) {


                roleItem = spin.getSelectedItem().toString();

                if (roleItem.equals("Flood")) {
                    /*mwatr.setHint("WaterLevel");
                    mwatr.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    lvl = mwatr.getText().toString() + "Feet";
                    Log.img_view("tag", "Role" + roleItem + lvl);*/
                    b1.setVisibility(View.VISIBLE);
                    b2.setVisibility(View.VISIBLE);
                    b3.setVisibility(View.VISIBLE);
                    b4.setVisibility(View.VISIBLE);
                    b5.setVisibility(View.VISIBLE);
                    b6.setVisibility(View.VISIBLE);
                    b7.setVisibility(View.VISIBLE);
                    b8.setVisibility(View.VISIBLE);
                    b9.setVisibility(View.VISIBLE);
                    b10.setVisibility(View.VISIBLE);
                    b11.setVisibility(View.VISIBLE);
                    b12.setVisibility(View.VISIBLE);
                    b1.setText("1");
                    b2.setText("2");
                    b3.setText("3");
                    b4.setText("4");
                    b5.setText("5");
                    b6.setText("6");
                    b7.setText("7");
                    b8.setText("8");
                    b9.setText("9");
                    b10.setText("10");
                    b11.setText("11");
                    b12.setText("12");


                    lvltxt.setText("WaterLevlel in feet");


                } else if (roleItem.equals("Storm")) {

                    /*mwatr.setHint("SpeedLevel");
                    mwatr.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    lvl = mwatr.getText().toString() + "pph";
                    Log.img_view("tag", "Role1" + roleItem + lvl);*/


                    b1.setText("Low");
                    b2.setText("High");
                    b3.setText("VeryHigh");
                    b4.setText("Extreme Damage");

                    b5.setVisibility(View.GONE);
                    b6.setVisibility(View.GONE);
                    b7.setVisibility(View.GONE);
                    b8.setVisibility(View.GONE);
                    b9.setVisibility(View.GONE);
                    b10.setVisibility(View.GONE);
                    b11.setVisibility(View.GONE);
                    b12.setVisibility(View.GONE);


                    lvltxt.setText("Damage Level (Approx...)");
                } else if (roleItem.equals("Earthquake")) {
                /*    mwatr.setHint("RicterLevel");
                    mwatr.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    lvl = mwatr.getText().toString() + "magnitude";
                    Log.img_view("tag", "Role1" + roleItem + lvl);*/


                    b1.setHeight(35);


                    b1.setText("LowDamage");
                    b2.setText("HighDamage");

                    b3.setText("VeryHighDamage");

                    b4.setText("Extreme Damage");


                    b5.setVisibility(View.GONE);
                    b6.setVisibility(View.GONE);
                    b7.setVisibility(View.GONE);
                    b8.setVisibility(View.GONE);
                    b9.setVisibility(View.GONE);
                    b10.setVisibility(View.GONE);
                    b11.setVisibility(View.GONE);
                    b12.setVisibility(View.GONE);


                    lvltxt.setText("Damage of Area (Approx..)");


                }                          /*    else if (roleItem.equals("AVALANCHES"))
                                               {

                                                   mwatr.setHint("Path Level");
                                                   Log.img_view("tag", "Role1" + roleItem);
                                               }*/ else if (roleItem.equals("Others")) {
                    b1.setText("Low");
                    b2.setText("High");
                    b3.setText("VeryHigh");
                    b4.setText("Extreme High");

                    b1.setHeight(45);

                    b5.setVisibility(View.GONE);
                    b6.setVisibility(View.GONE);
                    b7.setVisibility(View.GONE);
                    b8.setVisibility(View.GONE);
                    b9.setVisibility(View.GONE);
                    b10.setVisibility(View.GONE);
                    b11.setVisibility(View.GONE);
                    b12.setVisibility(View.GONE);

                    /*mwatr.setHint("SPI Level");
                    mwatr.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    lvl = mwatr.getText().toString() + "SPI";
                    Log.img_view("tag", "Role1" + roleItem + lvl);*/
                    lvltxt.setText("Damage Level...");
                } else if (roleItem.equals("Tsunami")) {
                    b1.setText("Low Level");
                    b2.setText("High Level");
                    b3.setText("VeryHigh Level");
                    b4.setText("Extreme Level");

                    b5.setVisibility(View.GONE);
                    b6.setVisibility(View.GONE);
                    b7.setVisibility(View.GONE);
                    b8.setVisibility(View.GONE);
                    b9.setVisibility(View.GONE);
                    b10.setVisibility(View.GONE);
                    b11.setVisibility(View.GONE);
                    b12.setVisibility(View.GONE);

                   /* mwatr.setHint("See floor Level");
                    mwatr.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    lvl = mwatr.getText().toString() + "Feet";
                    Log.img_view("tag", "Role1" + roleItem + lvl);*/
                    lvltxt.setText("Damage Caused by WaterlFlow (Approx..)");


                                                 /*  mwatr.setHint("Heat Level");
                                                   Log.img_view("tag", "Role1" + roleItem);*/
                } else if (roleItem.equals("Fire")) {

                    b1.setText("Low");
                    b2.setText("High");
                    b3.setText("VeryHigh");
                    b4.setText("Extreme Damage");

                    b5.setVisibility(View.GONE);
                    b6.setVisibility(View.GONE);
                    b7.setVisibility(View.GONE);
                    b8.setVisibility(View.GONE);
                    b9.setVisibility(View.GONE);
                    b10.setVisibility(View.GONE);
                    b11.setVisibility(View.GONE);
                    b12.setVisibility(View.GONE);

                   /* mwatr.setHint("Impact Level");
                    mwatr.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    lvl = mwatr.getText().toString() + "Imp";
                    Log.img_view("tag", "Role1" + roleItem + lvl);*/
                    lvltxt.setText("Impact of Fire");
                }


            }


        });

        level = String.valueOf(ilvl);
        Log.d("tag", level);


        rsend.setOnClickListener(new View.OnClickListener()

                                 {
                                     @Override
                                     public void onClick(View v) {

                                         gps = new GpsT(SpotEmergency.this);
                                         latitude1 = gps.getLatitude();
                                         longitude2 = gps.getLongitude();
                                         type = "FLOOD";//spin.getSelectedItem().toString();
                                         location = mloc.getText().toString();
                                         //level = mwatr.getText().toString();
                                         level = String.valueOf(ilvl);
                                         Log.d("tag", level);
                                         Log.d("tag", location);
                                         hashTags = "#" + mhast.getText().toString();
                                         latitide = String.valueOf(latitude1);
                                         longitude = String.valueOf(longitude2);
                                         Log.d("tag", type + location + level + hashTags + latitide + longitude);

                                       /*  if (Util.Operations.isOnline(SpotEmergency.this)) {
                                             Log.img_view("tag", "util");*/


                                         if (!isOnline(SpotEmergency.this)) {

                                             showDialog(DIALOG_ERROR_CONNECTION);


                                           /*  sad = new SweetAlertDialog(SpotEmergency.this, SweetAlertDialog.WARNING_TYPE)
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
                                                     });
                                             sad.show();*/


                                         } else {


                                             if (!level.isEmpty() && !location.isEmpty() && !hashTags.isEmpty()) {
                                                 Log.d("tag", "insideutil");
                                                 new MyActivityAsync(type, location, level, hashTags, latitide, longitude).execute();
                                             } else {
                                                 new SweetAlertDialog(SpotEmergency.this, SweetAlertDialog.WARNING_TYPE)
                                                         .setTitleText("Alert")
                                                         .setContentText("Please enter your details! ")
                                                         .setConfirmText("OK")
                                                         .show();
                                             }


                                         }


                                     /*    } else {


                                             if (!level.isEmpty() && !location.isEmpty() && !hashTags.isEmpty()) {

                                                 final AlertDialog.Builder errorDialog = new AlertDialog.Builder(SpotEmergency.this);
                                                 errorDialog.setTitle("No Internet");
                                                 errorDialog.setMessage("Carrier charges may apply Do you want sent as SMS");

                                                 errorDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                                     public void onClick(DialogInterface dialog, int id) {
                                                         smsbody = type + "\t" + location + "\t" + level + "\t" + hashTags + "\t" + latitide + "\t" + longitude;
                                                         Log.img_view("tag", smsbody);
                                                         sendSms();
                                                     }
                                                 });

                                                 errorDialog.setNeutralButton("Cancel",
                                                         new DialogInterface.OnClickListener() {

                                                             @Override
                                                             public void onClick(DialogInterface dialog, int id) {

                                                                 dialog.dismiss();
                                                                 //dialog.dismiss();
                                                             }
                                                         });

                                                 AlertDialog errorAlert = errorDialog.create();
                                                 errorAlert.show();
                                             } else {
                                                 new SweetAlertDialog(SpotEmergency.this, SweetAlertDialog.WARNING_TYPE)
                                                         .setTitleText("Alert")
                                                         .setContentText("Please enter your details! ")
                                                         .setConfirmText("OK")
                                                         .show();
                                             }

                                         *//*    new SweetAlertDialog(SpotEmergency.this, SweetAlertDialog.WARNING_TYPE)
                                                     .setTitleText("Oops!")
                                                     .setContentText("Check your internet or try again")
                                                     .setConfirmText("OK")
                                                     .show();*//*


                                         }*/

                                     }

                                 }

        );


        lcbutton.setOnClickListener(new View.OnClickListener()

                                    {
                                        @Override
                                        public void onClick(View v) {
                                            gps = new GpsT(SpotEmergency.this);
                                            if (gps.canGetLocation()) {
                                                latitude1 = gps.getLatitude();
                                                longitude2 = gps.getLongitude();
                                                String lati = String.valueOf(latitude1);
                                                String longi = String.valueOf(longitude2);
                                                String Addr = gps.getAddress();
                                                mloc.setText(Addr);
                                                latlongt.setText("Latitude:\t" + lati + "\nLongitude:\t" + longi);
                                            } else {
                                                SplashScreen sps = new SplashScreen();
                                                sps.buildAlertMessageNoGps();
                                                //gps.showSettingsAlert();
                                            }
                                        }
                                    }

        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        //  mTimer.cancel();
        Log.d("tag", "pause");
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


    protected void sendSMS() {
        Log.i("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        //smsIntent.putExtra("address", new String("012345"));
        smsIntent.putExtra("sms_body", smsbody);

        try {
            startActivity(smsIntent);
            finish();
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SpotEmergency.this, "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }


    public void sendSms() {
        try {

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("7200136004", null, smsbody, null, null);
            Log.d("tag", smsbody);
            Toast.makeText(getApplicationContext(), "Your sms has successfully sent!", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Your sms has failed...", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DIALOG_ERROR_CONNECTION:
                AlertDialog.Builder errorDialog = new AlertDialog.Builder(this);
                errorDialog.setTitle("Error");
                errorDialog.setMessage("No internet connection.");

                errorDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        finish();
                    }
                });

                errorDialog.setNeutralButton("Cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent goD = new Intent(getApplicationContext(), Dashboard.class);
                                startActivity(goD);
                                finish();
                                //dialog.dismiss();
                            }
                        });

                AlertDialog errorAlert = errorDialog.create();
                return errorAlert;

            default:
                break;
        }
        return dialog;
    }

    public class MyAdapter extends ArrayAdapter<String> {

        public MyAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.spintext, parent, false);

            TextView label = (TextView) row.findViewById(R.id.stext);

            label.setText(sting[position]);

            ImageView icon = (ImageView) row.findViewById(R.id.icon);
            icon.setImageResource(imgs[position]);

            return row;
        }
    }

  /*  class goback extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    // display toast*//*


                }

            });
        }

    }*/

    class MyActivityAsync extends AsyncTask<String, Void, String> {


        String type, location, level, hashTags, latitide, longitude;

        public MyActivityAsync(String type, String location, String level, String hashTags, String latitide, String longitude) {

            this.location = location;
            this.type = type;
            this.level = level;
            this.hashTags = hashTags;
            this.latitide = latitide;
            this.longitude = longitude;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("tag", "s1");

            pDialog = new SweetAlertDialog(SpotEmergency.this, SweetAlertDialog.PROGRESS_TYPE);
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

                //{"location":"nungambakkam","type":"DISASTER","waterLevel":"5 feet","hashTags":"#flood","latitide":"13.0610677","longitude":"80.2404959"}
                // 3. build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("location", location);
                jsonObject.accumulate("type", type);
                jsonObject.accumulate("waterLevel", level);
                jsonObject.accumulate("hashTags", hashTags);
                jsonObject.accumulate("latitide", latitide);
                jsonObject.accumulate("longitude", longitude);
                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest(URL_REGISTER, json, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("tag", "<-----rerseres---->" + s);
            super.onPostExecute(s);

            pDialog.dismiss();

            try {
                JSONObject jo = new JSONObject(s);

                String status = jo.getString("status");

                String msg = jo.getString("message");
                Log.d("tag", "<-----Status----->" + status);


                if (status.equals("SUCCESS")) {

                    new SweetAlertDialog(SpotEmergency.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Message Alert")
                            .setContentText("Spot Added Sucessfully")
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


                    //Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                } else if (!status.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Retry or check your Network", Toast.LENGTH_LONG).show();
                } else {

                    new SweetAlertDialog(SpotEmergency.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops!")
                            .setContentText("Check your internet or try again")
                            .setConfirmText("OK")
                            .show();
                    //  Toast.makeText(getActivity(), "Check your internet or try again", Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

}

