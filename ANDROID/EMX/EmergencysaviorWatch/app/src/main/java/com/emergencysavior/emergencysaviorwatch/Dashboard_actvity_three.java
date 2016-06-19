package com.emergencysavior.emergencysaviorwatch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import at.markushi.ui.CircleButton;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by RSA on 06-06-2016.
 */
public class Dashboard_actvity_three extends Fragment {
    Typeface tf;
    CircleButton btn_alarm, btn_rescue_Me, btn_see_something, btn_rtt;
    CircleButton btn_emergency_earthquake, btn_emergency_flood, btn_emergency_storms, btn_emergency_tsunami, btn_emergency_volcanoe, btn_emergency_iamok;
    TextView txt_alarm, txt_rescue, txt_iamok, txt_mylocation;
    String tranctional_type, token, email, tranctional_id, str_audiostatus, str_gpsstatus, str_messagestatus, str_emailstatus, str_call911status;
    SweetAlertDialog pDialog;
    String message, firstname, gps_location, addressLine, city, state, uid, str_latitude, str_Longitude, str_address, str_city, str_state;
    ;
    public static Location loc;
    double latitude, longitude;
    private boolean isBgChanged, isBgChanged1, isBgChanged2;
    MediaPlayer mediaPlayer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dashboard_activity_three, container, false);
        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ques.otf");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        token = sharedPreferences.getString("session_token", "");
        firstname = sharedPreferences.getString("Firstname", "");
        email = sharedPreferences.getString("email", "");
        SharedPreferences pref = getActivity().getPreferences(0);
        str_audiostatus = pref.getString("audiostatus", "empty");
        str_gpsstatus = pref.getString("gpsstatus", "empty");
        str_messagestatus = pref.getString("messagestatus", "empty");
        str_emailstatus = pref.getString("emailstatus", "empty");
        str_call911status = pref.getString("call911status", "empty");

        str_latitude = pref.getString("Latitude", "empty");
        str_Longitude = pref.getString("Longitude", "empty");


        if (Util.Operations.isOnline(getActivity())) {
            MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
                @Override
                public void gotLocation(final Location location) {


                    loc = location;
                    System.out.println("Latitude: " + loc.getLatitude());
                    System.out.println("Longitude: " + loc.getLongitude());
                    latitude = loc.getLatitude();
                    longitude = loc.getLongitude();
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(getActivity(), Locale.getDefault());

                    try {
                        addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                        str_address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        str_city = addresses.get(0).getLocality();
                        str_state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();


                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("tag", "cannot get Address!");
                    }


                }


            };
            MyLocation myLocation = new MyLocation();
            myLocation.getLocation(getActivity(), locationResult);

        } else {

            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("ALERT")
                    .setContentText("Internet Connectivity very slow? try again")
                    .setConfirmText("ok")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            Intent intent = new Intent(getActivity(), Locationview.class);
                            startActivity(intent);

                        }
                    })
                    .show();
        }

        btn_alarm = (CircleButton) v.findViewById(R.id.alarm);
        btn_rescue_Me = (CircleButton) v.findViewById(R.id.btn_rescue_Me);
        btn_see_something = (CircleButton) v.findViewById(R.id.see_something);
        btn_rtt = (CircleButton) v.findViewById(R.id.btn_rtt);
        txt_alarm = (TextView) v.findViewById(R.id.txt_alarm);
        txt_rescue = (TextView) v.findViewById(R.id.txt_rescue);
        txt_iamok = (TextView) v.findViewById(R.id.txt_sos);
        txt_mylocation = (TextView) v.findViewById(R.id.txt_iamok);
        btn_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                AlertDialog alertDialog;
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
            }
        });

        btn_rescue_Me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ques.otf");
                Dialog alertDialog_rescue = new Dialog(getActivity());
                alertDialog_rescue.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertDialog_rescue.setContentView(R.layout.rescue_me_activity);
                alertDialog_rescue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                btn_emergency_earthquake = (CircleButton) alertDialog_rescue.findViewById(R.id.emergency_earthquake);
                btn_emergency_flood = (CircleButton) alertDialog_rescue.findViewById(R.id.emergency_flood);
                btn_emergency_storms = (CircleButton) alertDialog_rescue.findViewById(R.id.emergency_storms);
                btn_emergency_tsunami = (CircleButton) alertDialog_rescue.findViewById(R.id.emergency_tsunami);
                btn_emergency_volcanoe = (CircleButton) alertDialog_rescue.findViewById(R.id.emergency_volcanoe);
                btn_emergency_iamok = (CircleButton) alertDialog_rescue.findViewById(R.id.emergency_iam_ok);


                final TextView txt_earthquake = (TextView) alertDialog_rescue.findViewById(R.id.txt_earthquake);
                final TextView txt_flood = (TextView) alertDialog_rescue.findViewById(R.id.txt_flood);
                final TextView txt_storms = (TextView) alertDialog_rescue.findViewById(R.id.txt_storms);
                final TextView tx_tsunami = (TextView) alertDialog_rescue.findViewById(R.id.tx_tsunami);
                final TextView txt_volcanoes = (TextView) alertDialog_rescue.findViewById(R.id.txt_volcanoes);
                final TextView txt_iamok = (TextView) alertDialog_rescue.findViewById(R.id.txt_iamok);

                btn_emergency_earthquake.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        AlertDialog alertDialog;
                        alertDialogBuilder.setTitle("ALERT");
                        alertDialogBuilder
                                .setMessage("Please confirm ")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        tranctional_type = ("Earthquake");
                                        new MyActivityAsync_rescue_me(tranctional_type).execute();
                                        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
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

                    }
                });
                btn_emergency_flood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        AlertDialog alertDialog;
                        alertDialogBuilder.setTitle("ALERT");
                        alertDialogBuilder
                                .setMessage("Please confirm ")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        tranctional_type = ("Emergency Flood");
                                        new MyActivityAsync_rescue_me(tranctional_type).execute();
                                        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
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
                    }
                });
                btn_emergency_storms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        AlertDialog alertDialog;
                        alertDialogBuilder.setTitle("ALERT");
                        alertDialogBuilder
                                .setMessage("Please confirm ")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        tranctional_type = ("Emergency Storms");
                                        new MyActivityAsync_rescue_me(tranctional_type).execute();
                                        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
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
                    }
                });
                btn_emergency_tsunami.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        AlertDialog alertDialog;
                        alertDialogBuilder.setTitle("ALERT");
                        alertDialogBuilder
                                .setMessage("Please confirm ")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        tranctional_type = ("Emergency Tsunami");
                                        new MyActivityAsync_rescue_me(tranctional_type).execute();
                                        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
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
                    }
                });
                btn_emergency_volcanoe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        AlertDialog alertDialog;
                        alertDialogBuilder.setTitle("ALERT");
                        alertDialogBuilder
                                .setMessage("Please confirm ")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        tranctional_type = ("Emergency Volcano");
                                        new MyActivityAsync_rescue_me(tranctional_type).execute();
                                        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
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
                    }
                });
                btn_emergency_iamok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        AlertDialog alertDialog;
                        alertDialogBuilder.setTitle("ALERT");
                        alertDialogBuilder
                                .setMessage("Please confirm ")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        tranctional_type = ("Emergency I am ok");
                                        new MyActivityAsync_rescue_me(tranctional_type).execute();
                                        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
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
                    }
                });

                txt_earthquake.setTypeface(tf);
                txt_flood.setTypeface(tf);
                txt_storms.setTypeface(tf);
                tx_tsunami.setTypeface(tf);
                txt_volcanoes.setTypeface(tf);
                txt_iamok.setTypeface(tf);
                alertDialog_rescue.show();

            }
        });
        btn_see_something.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Under Process", Toast.LENGTH_LONG).show();

            }
        });
        btn_rtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(),MapActivity.class);
                startActivity(intent);

            }
        });

        if (Util.Operations.isOnline(getActivity())) {
            MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
                @Override
                public void gotLocation(final Location location) {
                    loc = location;
                    latitude = loc.getLatitude();
                    longitude = loc.getLongitude();


                }


            };
            MyLocation myLocation = new MyLocation();
            myLocation.getLocation(getActivity(), locationResult);

        } else {

            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("ALERT")
                    .setContentText("Internet Connectivity very slow? try again")
                    .setConfirmText("ok")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            Intent intent = new Intent(getActivity(), Locationview.class);
                            startActivity(intent);

                        }
                    })
                    .show();
        }
        return v;
    }

    public static Dashboard_actvity_three newInstance(String text) {

        Dashboard_actvity_three f = new Dashboard_actvity_three();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    private class MyActivityAsync extends AsyncTask<String, Void, String> {
        String tranctional_type;

        public MyActivityAsync(String tranctional_type) {
            this.tranctional_type = tranctional_type;
        }

        protected void onPreExecute() {
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
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

                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("ALERT")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();
            } else {
                try {
                    JSONObject jo = new JSONObject(jsonStr);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                    tranctional_id = jo.getString("transaction_id");
                    Log.d("tag", "tranctional_id" + tranctional_id);


                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(getActivity(), Locale.getDefault());

                    try {
                        addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        addressLine = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        city = addresses.get(0).getLocality();
                        state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();


                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("tag", "cannot get Address!");
                    }

                    if (status.equals("success")) {


                        latitude = loc.getLatitude();
                        longitude = loc.getLongitude();


                        Log.d("tag", "gps_location" + gps_location);
                        switch (tranctional_type) {

                            case "alarm":


                                tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ques.otf");
                                Dialog alertDialog = new Dialog(getActivity());
                                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                alertDialog.setContentView(R.layout.custom_dialog);
                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                final CircleButton btn_call = (CircleButton) alertDialog.findViewById(R.id.emergency_call);
                                final CircleButton btn_emergency_siren = (CircleButton) alertDialog.findViewById(R.id.emergency_siren);
                                final CircleButton btn_policeman = (CircleButton) alertDialog.findViewById(R.id.emergency_policeman);
                                final TextView txt_siren = (TextView) alertDialog.findViewById(R.id.txt_siren);
                                final TextView txt_call_911 = (TextView) alertDialog.findViewById(R.id.txt_call_911);
                                final TextView txt_policeman = (TextView) alertDialog.findViewById(R.id.txt_policeman);
                                txt_siren.setTypeface(tf);
                                txt_call_911.setTypeface(tf);
                                txt_policeman.setTypeface(tf);


                                btn_call.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        gps_location = (CONFIG.GPS_URL_LOCATION + str_latitude + "," + str_Longitude);
                                        isBgChanged = !isBgChanged;
                                        if (isBgChanged) {
                                            isBgChanged1 = false;
                                            isBgChanged2 = false;

                                            if (mediaPlayer != null) {
                                                mediaPlayer.stop();
                                            }
                                            mediaPlayer = MediaPlayer.create(getActivity(), R.raw.voice);
                                            mediaPlayer.start();

                                            btn_call.setImageResource(R.mipmap.ic_stop);
                                            btn_emergency_siren.setImageResource(R.mipmap.flood);
                                            btn_policeman.setImageResource(R.mipmap.ic_police);
                                            if (str_emailstatus.equals("true")) {
                                                Log.d("tag", "Address2" + addressLine + "," + city + "," + state);
                                                message = (firstname + " has pressed  911 send  police to: " + str_address + "," + str_city + "," + str_city + "" + gps_location);
                                                new message_from_email(message, str_latitude, str_Longitude).execute();
                                            }
                                            if (str_messagestatus.equals("true")) {
                                                message = (firstname + " has pressed  911 send  police to: " + str_address + "," + str_city + "," + str_city + "" + gps_location);
                                                new message(message).execute();
                                            }
                                        } else {
                                            mediaPlayer.stop();
                                            btn_call.setImageResource(R.mipmap.call_911);
                                            btn_emergency_siren.setImageResource(R.mipmap.flood);
                                            btn_policeman.setImageResource(R.mipmap.ic_police);

                                        }

                                    }
                                });
                                btn_emergency_siren.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        gps_location = (CONFIG.GPS_URL_LOCATION + str_latitude + "," + str_Longitude);
                                        isBgChanged1 = !isBgChanged1;
                                        if (isBgChanged1) {
                                            isBgChanged = false;
                                            isBgChanged2 = false;
                                            if (mediaPlayer != null) {
                                                mediaPlayer.stop();
                                            }
                                            mediaPlayer = MediaPlayer.create(getActivity(), R.raw.ssound);
                                            mediaPlayer.start();
                                            btn_emergency_siren.setImageResource(R.mipmap.ic_stop);
                                            btn_call.setImageResource(R.mipmap.call_911);
                                            btn_policeman.setImageResource(R.mipmap.ic_police);
                                            if (str_emailstatus.equals("true")) {
                                                message = (firstname + " has pressed  Siren send  police to:" + str_address + "," + str_city + "," + str_city + "" + gps_location);
                                                new message_from_email(message, str_latitude, str_Longitude).execute();
                                            }
                                            if (str_messagestatus.equals("true")) {
                                                message = (firstname + " has pressed  Siren send  police to:" + str_address + "," + str_city + "," + str_city + "" + gps_location);
                                                new message(message).execute();
                                            }

                                        } else {
                                            mediaPlayer.stop();

                                            btn_emergency_siren.setImageResource(R.mipmap.flood);
                                            btn_call.setImageResource(R.mipmap.call_911);
                                            btn_policeman.setImageResource(R.mipmap.ic_police);
                                        }
                                    }
                                });
                                btn_policeman.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        gps_location = (CONFIG.GPS_URL_LOCATION + str_latitude + "," + str_Longitude);
                                        isBgChanged2 = !isBgChanged2;
                                        if (isBgChanged2) {
                                            isBgChanged = false;
                                            isBgChanged1 = false;
                                            if (mediaPlayer != null) {
                                                mediaPlayer.stop();
                                            }
                                            mediaPlayer = MediaPlayer.create(getActivity(), R.raw.policeman);
                                            mediaPlayer.start();
                                            btn_policeman.setImageResource(R.mipmap.ic_stop);
                                            btn_emergency_siren.setImageResource(R.mipmap.flood);
                                            btn_call.setImageResource(R.mipmap.call_911);
                                            if (str_emailstatus.equals("true")) {
                                                message = (firstname + "has pressed  Policeman send  police to:" + str_address + "," + str_city + "," + str_city + "" + gps_location);
                                                new message_from_email(message, str_latitude, str_Longitude).execute();
                                            }
                                            if (str_messagestatus.equals("true")) {
                                                message = (firstname + "has pressed  Policeman send  police to:" + str_address + "," + str_city + "," + str_city + "" + gps_location);
                                                new message(message).execute();
                                            }
                                        } else {
                                            mediaPlayer.stop();
                                            btn_policeman.setImageResource(R.mipmap.ic_police);
                                            btn_emergency_siren.setImageResource(R.mipmap.flood);
                                            btn_call.setImageResource(R.mipmap.call_911);
                                        }
                                    }
                                });
                                alertDialog.show();

                                break;
                        }
                    } else {
                        if (msg.indexOf("session_token") >= 0) {
                            TelephonyManager tManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                            uid = tManager.getDeviceId();
                            Log.d("tag", "uid" + uid);

                            new MyAsyncsessiontoken(uid).execute();

                        } else {
                            Toast.makeText(getActivity(), "Invaild device", Toast.LENGTH_LONG).show();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> E M A I L   U P L O A D >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private class message_from_email extends AsyncTask<String, Void, String> {
        String message;
        String str_latitude, str_Longitude;

        public message_from_email(String message, String str_latitude, String str_Longitude) {
            this.message = message;
            this.str_latitude = str_latitude;
            this.str_Longitude = str_Longitude;
        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();

                jsonObject.accumulate("message", message);
                jsonObject.accumulate("lat", str_latitude);
                jsonObject.accumulate("lang", str_Longitude);
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
            try {
                JSONObject result = new JSONObject(jsonStr);
                String status = result.getString("status");
                String msg = result.getString("message");

                if (msg.indexOf("session_token") >= 0) {
                    TelephonyManager tManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                    uid = tManager.getDeviceId();
                    Log.d("tag", "uid" + uid);

                    new MyAsyncsessiontoken(uid).execute();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> E M A I L   U P L O A D >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
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

                return;
            }
            try {
                JSONObject result = new JSONObject(jsonStr);
                String status = result.getString("status");
                String msg = result.getString("message");

                if (msg.indexOf("session_token") >= 0) {
                    TelephonyManager tManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                    uid = tManager.getDeviceId();
                    Log.d("tag", "uid" + uid);

                    new MyAsyncsessiontoken(uid).execute();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> M E S S A G E  U P L O A D >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private class MyAsyncsessiontoken extends AsyncTask<String, Void, String> {

        String uid;

        public MyAsyncsessiontoken(String uid) {
            this.uid = uid;
        }


        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("device_id", uid);
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest("http://api.emergencysavior.com/webapi/emx/qrwear/sessionid", json);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("tag", "<-----sessiontoken---->" + s);
            try {
                JSONObject result = new JSONObject(s);
                String status = result.getString("status");
                String session_token = result.getString("session_token");
                if (status.equals("fail")) {

                    Toast.makeText(getActivity(), "Invaild Device", Toast.LENGTH_LONG).show();

                } else {
                    new MyActivityAsync(tranctional_type).execute();
                    SharedPreferences pref = getActivity().getPreferences(0);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.remove("session_token");
                    edit.putString("session_token", session_token);
                    edit.apply();
                    Log.e("tag", "tranctional_type " + tranctional_type);

                }
            } catch (JSONException e) {
                e.printStackTrace();
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

                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("ALERT")
                        .setContentText("Internet Connectivity very slow? try again")
                        .setConfirmText("ok")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                pDialog.dismiss();
                            }
                        })
                        .show();
            } else {
                try {
                    JSONObject jo = new JSONObject(jsonStr);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                    tranctional_id = jo.getString("transaction_id");
                    if (status.equals("success")) {
                        gps_location = (CONFIG.GPS_URL_LOCATION + str_latitude + "," + str_Longitude);
                        Log.d("tag", "gps_location" + gps_location);

                        switch (tranctional_type) {

                            case "Earthquake":

                                if (str_emailstatus.equals("true")) {
                                    message = ("This is " + firstname + " There is an Earthquake in my area … send help immediately " + str_address + " " + gps_location);
                                    new message_from_email(message, str_latitude, str_Longitude).execute();
                                }

                                if (str_messagestatus.equals("true")) {
                                    message = ("This is " + firstname + " There is an Earthquake in my area … send help immediately " + str_address /*+ " " + gps_location*/);
                                    Log.d("tag", "message E  = " + message);
                                    new message_rescue_me(message).execute();
                                }

                                break;
                            case "Emergency Flood":

                                if (str_emailstatus.equals("true")) {
                                    message = ("This is " + firstname + " There is an Flood in my area … send help immediately " + str_address + " " + gps_location);
                                    new message_from_email(message, str_latitude, str_Longitude).execute();
                                }
                                if (str_messagestatus.equals("true")) {

                                    message = ("This is " + firstname + " There is an Flood in my area … send help immediately " + str_address /*+ " " + gps_location*/);
                                    Log.d("tag", "message F  = " + message);
                                    new message_rescue_me(message).execute();
                                }

                                break;
                            case "Emergency Storms":
                                if (str_emailstatus.equals("true")) {
                                    message = ("This is " + firstname + " There is an Major Storm in my area … send help immediately " + str_address + " " + gps_location);
                                    new message_from_email(message, str_latitude, str_Longitude).execute();
                                }

                                if (str_messagestatus.equals("true")) {
                                    message = ("This is " + firstname + " There is an Major Storm in my area ... send help immediately " + str_address /*+ " " + gps_location*/);
                                    Log.d("tag", "message S  = " + message);
                                    new message_rescue_me(message).execute();
                                }
                                break;
                            case "Emergency Tsunami":

                                if (str_emailstatus.equals("true")) {
                                    message = ("This is " + firstname + " There is an Large Tsunami in my area... send help immediately " + str_address + " " + gps_location);
                                    new message_from_email(message, str_latitude, str_Longitude).execute();
                                }

                                if (str_messagestatus.equals("true")) {
                                    message = ("This is " + firstname + " There is an Large Tsunami in my area... send help immediately " + str_address /*+ " " + gps_location*/);
                                    Log.d("tag", "message T  = " + message);
                                    new message_rescue_me(message).execute();
                                }
                                break;
                            case "Emergency Volcano":


                                if (str_emailstatus.equals("true")) {
                                    message = ("This is " + firstname + " There is an Erupting Volcano in my area... send help immediately " + str_address + " " + gps_location);
                                    new message_from_email(message, str_latitude, str_Longitude).execute();
                                }

                                if (str_messagestatus.equals("true")) {
                                    message = ("This is " + firstname + " There is an Erupting Volcano in my area... send help immediately " + str_address /*+ " " + gps_location*/);
                                    Log.d("tag", "message V  = " + message);
                                    new message_rescue_me(message).execute();
                                }
                                break;


                            case "Emergency I am ok":
                                if (str_emailstatus.equals("true")) {
                                    message = ("This is " + firstname + " is Ok .. No Help Needed at: " + str_address + " " + gps_location);
                                    new message_from_email(message, str_latitude, str_Longitude).execute();
                                }

                                if (str_messagestatus.equals("true")) {
                                    message = (firstname + " is Ok .. No Help Needed at: " + str_address /*+ " " + gps_location*/);
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
                pDialog.dismiss();
            }
            try {
                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");

                if (status.equals("success")) {
                     pDialog.dismiss();
                     } else {
                    Intent act_back = new Intent(getActivity(), MainActivity.class);
                    startActivity(act_back);
                    Toast.makeText(getActivity(), "Message not Sent", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            pDialog.dismiss();

        }
    }
}