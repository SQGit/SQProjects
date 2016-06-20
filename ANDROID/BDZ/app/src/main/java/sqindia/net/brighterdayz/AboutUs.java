package sqindia.net.brighterdayz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;

/**
 * Created by sagayam on 25-04-2016.
 */
public class AboutUs extends Activity {

    TextView txt_abt1,txt_abt2,txt_abt3,txt_abt4,txt_abt5,txt_abt6,txt_abt7,txt_abt8,txt_abt9,abt;

    LinearLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);


        abt = (TextView) findViewById(R.id.title_aboutus);
        txt_abt1 = (TextView) findViewById(R.id.about_us1);
        txt_abt2 = (TextView) findViewById(R.id.about_us2);
        txt_abt3 = (TextView) findViewById(R.id.about_us3);
        txt_abt4 = (TextView) findViewById(R.id.about_us4);
        txt_abt5 = (TextView) findViewById(R.id.about_us5);
        txt_abt6 = (TextView) findViewById(R.id.about_us6);
        txt_abt7 = (TextView) findViewById(R.id.about_us7);
        txt_abt8 = (TextView) findViewById(R.id.about_us8);
        txt_abt9 = (TextView) findViewById(R.id.about_us9);


        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "rr.ttf");


        abt.setTypeface(tf);
        txt_abt1.setTypeface(tf);
        txt_abt2.setTypeface(tf);
        txt_abt3.setTypeface(tf);
        txt_abt4.setTypeface(tf);
        txt_abt5.setTypeface(tf);
        txt_abt6.setTypeface(tf);
        txt_abt7.setTypeface(tf);
        txt_abt8.setTypeface(tf);
        txt_abt9.setTypeface(tf);




        back = (LinearLayout) findViewById(R.id.Laboutus);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent k = new Intent(AboutUs.this, Home.class);
                startActivity(k);

            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(AboutUs.this,Home.class);
        startActivity(i);
        super.onBackPressed();
    }


}
