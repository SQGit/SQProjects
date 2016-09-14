package net.sqindia.spectro;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by $Krishna on 27-08-2016.
 */
public class Splash extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 4000;
ImageView dsh_spec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        dsh_spec = (ImageView) findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(Splash.this, R.anim.rotate_around_center_point);
        dsh_spec.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(Splash.this, Dashboard.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
