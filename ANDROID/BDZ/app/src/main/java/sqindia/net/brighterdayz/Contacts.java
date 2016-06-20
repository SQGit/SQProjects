package sqindia.net.brighterdayz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by sagayam on 26-04-2016.
 */
public class Contacts extends Activity {

    TextView txt_1,txt_2,txt_3,txt_4,txt_5,txt_6,txt_7,txt_8,txt_9,txt_10,txt_11;
    Typeface tf;
    LinearLayout back;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);


        txt_1 = (TextView) findViewById(R.id.contacts);
        txt_2 = (TextView) findViewById(R.id.quote1);
        txt_3 = (TextView) findViewById(R.id.quote2);
        txt_4 = (TextView) findViewById(R.id.quote3);
        txt_5 = (TextView) findViewById(R.id.quote4);
        txt_6= (TextView) findViewById(R.id.quote5);
        txt_7 = (TextView) findViewById(R.id.quote6);
        txt_8 = (TextView) findViewById(R.id.quote7);
        txt_9 = (TextView) findViewById(R.id.quote8);
        txt_10 = (TextView) findViewById(R.id.mob1);
        txt_11 = (TextView) findViewById(R.id.mob2);

     /*   String tempString = " 020 339 77856";
         txt_10 = (TextView) findViewById(R.id.mob1);
        SpannableString spanString = new SpannableString(tempString);
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        txt_10.setText(spanString);
        String tempString1 = " 075 350 47120";
        txt_11 = (TextView) findViewById(R.id.mob2);
        SpannableString spanString1 = new SpannableString(tempString1);
        spanString1.setSpan(new UnderlineSpan(), 0, spanString1.length(), 0);
        txt_11.setText(spanString1);*/

        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "rr.ttf");

        txt_1.setTypeface(tf);
        txt_2.setTypeface(tf);
        txt_3.setTypeface(tf);
        txt_4.setTypeface(tf);
        txt_5.setTypeface(tf);
        txt_6.setTypeface(tf);
        txt_7.setTypeface(tf);
        txt_8.setTypeface(tf);
        txt_9.setTypeface(tf);
        txt_10.setTypeface(tf);
        txt_11.setTypeface(tf);

        txt_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent h = new Intent(Intent.ACTION_DIAL);
                h.setData(Uri.parse("tel:020 339 77856"));
                startActivity(h);
            }
        });
        txt_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent h = new Intent(Intent.ACTION_DIAL);
                h.setData(Uri.parse("tel:075 350 47120"));
                startActivity(h);
            }
        });


        back = (LinearLayout) findViewById(R.id.Lcontacts);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Contacts.this,Home.class);
                startActivity(i);
            }
        });


    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(Contacts.this,Home.class);
        startActivity(i);
        super.onBackPressed();
    }

}
