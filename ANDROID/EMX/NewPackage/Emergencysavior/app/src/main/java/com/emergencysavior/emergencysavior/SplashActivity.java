package com.emergencysavior.emergencysavior;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.widget.TextView;

import tr.xip.errorview.ErrorView;


/**
 * Created by RSA on 28-02-2016.
 */
public class SplashActivity extends DetailActivity {

    TextView tv;
    private static int SPLASH_TIME_OUT = 4000;
    Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(this);
        setContentView(R.layout.splashactivity);
        tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        tv = (TextView) findViewById(R.id.dss);
        tv.setTypeface(tf);
        StartAnimations();
    }

    private void StartAnimations() {

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
                String activation_code = sharedPreferences.getString("activation_code", "");
                String Login = sharedPreferences.getString("Session_token", "");
                Log.d("tag", "<-----CODE----->" + activation_code);
                if (Util.Operations.isOnline(SplashActivity.this)) {
                    if ((activation_code == "")) {
                        Intent i = new Intent(SplashActivity.this, Signup.class);
                        startActivity(i);


                    } else if ((Login == "")) {
                        Intent i = new Intent(SplashActivity.this,Login.class);
                        startActivity(i);


                    } else {

                        Intent i = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(i);
                    }

                } else {
                    setContentView(R.layout.errorview);
                    final ErrorView mErrorView = (ErrorView) findViewById(R.id.error_view);

                    mErrorView.setOnRetryListener(new ErrorView.RetryListener() {
                        @Override
                        public void onRetry() {
                            Intent i = new Intent(SplashActivity.this, SplashActivity.class);
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


                // finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
