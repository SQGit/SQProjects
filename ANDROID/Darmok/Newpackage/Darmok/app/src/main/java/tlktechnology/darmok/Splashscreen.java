package tlktechnology.darmok;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by sqindia on 29-07-2016.
 */
public class Splashscreen extends DetailActivity {
    TextView customtittle, custom_body;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Suissnord.otf");
        customtittle = (TextView) findViewById(R.id.custom_tittle);
        customtittle.setTypeface(tf);
        Thread background = new Thread() {
            public void run() {

                try {
                    sleep(5 * 1000);
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                } catch (Exception ignored) {

                }
            }
        };
        background.start();
    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();

    }
}
