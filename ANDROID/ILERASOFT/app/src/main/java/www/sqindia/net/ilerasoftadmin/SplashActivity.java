package www.sqindia.net.ilerasoftadmin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by ANANDH on 12-12-2015.
 */
public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 7000;

    public void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        StartAnimations();
    }
    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(R.id.LinearLayout1);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.logo);
        iv.clearAnimation();
        iv.startAnimation(anim);

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
                String code= sharedPreferences.getString("v_code", "");
                String Login= sharedPreferences.getString("login", "");

                Log.d("tag", "<-----CODE----->" + code);
                if((code == ""))
                {
                    Intent i = new Intent(SplashActivity.this, Registeration.class);
                    startActivity(i);
                }
                else if((Login == ""))
                {
                    Intent i = new Intent(SplashActivity.this, Login.class);
                    startActivity(i);
                }
                else
                {
                    Intent i = new Intent(SplashActivity.this, Dashboard.class);
                    startActivity(i);
                }

                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
