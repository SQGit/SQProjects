package com.startemplan.vyuspot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class Dashboard extends Activity {

    ImageView img_emg, img_accident, img_recovery, img_view, img_map, sos, voic, img_popup,img_group;
    TextView text_emg, text_accident, text_recovery, text_view, text_map, text_welcome;
    String lati,longi,namecode;
    SweetAlertDialog pDialog;

    public static String URL_SOS = "http://sqindia01.cloudapp.net/vyuspot/api/v1/spot/addSos";

    String token;


    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Log.d("tag","dashboard");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("pus","");
        editor.commit();
        token= sharedPreferences.getString("token", "");

        namecode = sharedPreferences.getString("name", "");

        Log.d("tag", "<-----name----->" + namecode);


        text_welcome = (TextView) findViewById(R.id.welcome);

        if ((namecode == "")) {
            text_welcome.setText("Welcome Guest");
        } else {
            text_welcome.setText("Welcome " + namecode);
        }


        img_emg = (ImageView) findViewById(R.id.aa);
        img_accident = (ImageView) findViewById(R.id.bb);
        img_recovery = (ImageView) findViewById(R.id.cc);
        img_view = (ImageView) findViewById(R.id.dd);
        img_map = (ImageView) findViewById(R.id.ee);
        sos = (ImageView) findViewById(R.id.sos);
        voic = (ImageView) findViewById(R.id.voic);
        img_popup = (ImageView) findViewById(R.id.context);
        img_popup.setOnCreateContextMenuListener(this);

        img_group = (ImageView) findViewById(R.id.group);


        text_emg = (TextView) findViewById(R.id.text1);
        text_accident = (TextView) findViewById(R.id.text2);
        text_recovery = (TextView) findViewById(R.id.text3);
        text_view = (TextView) findViewById(R.id.text4);
        text_map = (TextView) findViewById(R.id.text5);


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/robol.ttf");
        text_emg.setTypeface(tf);
        text_accident.setTypeface(tf);
        text_recovery.setTypeface(tf);
        text_view.setTypeface(tf);
        text_map.setTypeface(tf);



        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) Dashboard.this.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);







        img_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContextMenu(v);
            }

        });


        img_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent govoic = new Intent(getApplicationContext(), Admin_ManageGroup.class);
                startActivity(govoic);
            }
        });







        voic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent govoic = new Intent(getApplicationContext(), VoiceAct.class);
                startActivity(govoic);


            }
        });
        img_emg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goD = new Intent(getApplicationContext(), SpotEmergency.class);
                startActivity(goD);

            }
        });
        img_accident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goA = new Intent(getApplicationContext(), SpotAccident.class);
                startActivity(goA);

            }
        });
        img_recovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goN = new Intent(getApplicationContext(), SpotResourceNeed.class);
                startActivity(goN);
            }
        });
        img_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goV = new Intent(getApplicationContext(), View_Needs.class);
                startActivity(goV);

            }
        });

        img_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goM = new Intent(getApplicationContext(), MapView.class);
                startActivity(goM);

            }
        });

        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:102"));

                startActivity(callIntent);

            }


        });
    }





    private class SOSSendToServer extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new SweetAlertDialog(Dashboard.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            pDialog.setTitleText("Sending Your location...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {

                Log.d("tag","worked"+token);

                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("latitude", lati);
                jsonObject.accumulate("longitude", longi);



                json = jsonObject.toString();

                return jsonStr = HttpUtils.makeRequest(URL_SOS, json, token);
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

            String to = "mbrsalman@gmail.com   ";
            String subject ="emergency";
            String message = "@  "+lati+longi;

            if(s==null)
            {
                //Toast.makeText(getApplicationContext(), "send by email..." + lati + longi, Toast.LENGTH_LONG).show();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);

                // need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client"));

            }
            try {
                JSONObject jo = new JSONObject(s);

                String status = jo.getString("status");

                //String msg = jo.getString("message");
                Log.d("tag", "<-----Status----->" + status);
                Log.d("tag","t"+status);
                Log.e("tag", "t" + status);


                if (status.equals("SUCCESS")) {

                    Log.d("tag","t"+status);
                    Log.e("tag","t"+status);

                    new SweetAlertDialog(Dashboard.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("SOS Alert")
                            .setContentText("Your Location Sent as SOS")
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
                    Log.d("tag","t"+status);
                    Log.e("tag", "t" + status);
                    Toast.makeText(getApplicationContext(), "Retry or check your Network", Toast.LENGTH_LONG).show();
                } else {

                    new SweetAlertDialog(Dashboard.this, SweetAlertDialog.WARNING_TYPE)
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




    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.opt_menu, menu);
        MenuItem bedMenuItem = menu.findItem(R.id.item1);


        if(token.length() <5)
        {
            bedMenuItem.setTitle("LogIn");

        }

        if(token.length() >5){
            bedMenuItem.setTitle("Logout");
        }


    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:


                if(item.getTitle() == "LogIn")
                {
                    Intent goReg = new Intent(getApplicationContext(), Login.class);
                    startActivity(goReg);
                }
                else if(item.getTitle() == "Logout")
                {


                    new SweetAlertDialog(Dashboard.this,SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Do you want to Logout?")
                            .setConfirmText("Yes!")
                            .setCancelText("No")

                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    token = null;
                                    //text_welcome.setText("Welcome Guest");
                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.remove("name");
                                    editor.remove("token");
                                    editor.commit();

                                    Intent goDas = new Intent(getApplicationContext(),Dashboard.class);
                                    goDas.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    goDas.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    goDas.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(goDas);

                                    sDialog.dismiss();

                                }
                            })
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();

                                }
                            })
                            .show();

                }


                return true;
            case R.id.item2:
                //Toast.makeText(getApplicationContext(),"Settings",Toast.LENGTH_LONG).show();
                Intent goSett = new Intent(Dashboard.this, Settings.class);
                startActivity(goSett);
                return true;
            case R.id.item3:
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                share.putExtra(Intent.EXTRA_SUBJECT, "Share VyuSpot");
                share.putExtra(Intent.EXTRA_TITLE, "This app helps at Disaster Times...");
                share.putExtra(Intent.EXTRA_TEXT, "https://startemplan.com");

                startActivity(Intent.createChooser(share, "Share link!"));

                return true;

            case R.id.item4:

                Intent goHelp = new Intent(Dashboard.this, Help.class);
                startActivity(goHelp);
                return true;

            case R.id.item5:

                Intent goCre = new Intent(Dashboard.this, Admin_CreateGroup.class);
                startActivity(goCre);
                return true;

            case R.id.item6:

                Intent goJoi = new Intent(Dashboard.this, Admin_JoinGroup.class);
                startActivity(goJoi);
                return true;

            default:
                return super.onContextItemSelected(item);

        }


    }





    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "Rining, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "Offline");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");


                if (isPhoneCalling) {

                    Intent i = new Intent(getApplicationContext(), Dashboard.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    isPhoneCalling = false;
                }

            }
        }
    }













    public void onBackPressed() {


        new SweetAlertDialog(Dashboard.this,SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Do you want to exit the Application?")
                .setConfirmText("Yes!")
                .setCancelText("No")

                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Intent i1 = new Intent(Intent.ACTION_MAIN);
                        i1.setAction(Intent.ACTION_MAIN);
                        i1.addCategory(Intent.CATEGORY_HOME);
                        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i1);
                        finish();

                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();

                    }
                })
                .show();

    }


}
