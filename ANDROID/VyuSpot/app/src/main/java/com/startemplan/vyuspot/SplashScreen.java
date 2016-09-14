package com.startemplan.vyuspot;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.WindowManager;


public class SplashScreen extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        goCheck();


    }

    public void goCheck() {

        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        } else {


            new Handler().postDelayed(new Runnable() {


                @Override
                public void run() {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);
                    String reg_sts = sharedPreferences.getString("register", "");
                    String code1 = sharedPreferences.getString("test1", "");

                    Log.d("tag", "<-----CODE----->" + reg_sts);
                    if ((reg_sts == "")) {
                        Intent i = new Intent(SplashScreen.this, Register.class);

                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i);

                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                        finish();
                    } else {
                        if ((code1 == "")) {
                            Intent i = new Intent(SplashScreen.this, Login.class);
                            startActivity(i);
                            finish();
                        } else {
                            Intent i = new Intent(SplashScreen.this, Dashboard.class);
                            startActivity(i);
                            finish();
                        }
                    }

                    finish();
                }
            }, 3000);
        }
    }


    public void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        dialog.dismiss();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        dialog.dismiss();
                        finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d("tag","pause");
        //finish();
        //goCheck();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("tag","resume");
       // goCheck();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("tag","restart");
        goCheck();
    }
}
