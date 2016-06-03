package com.emerengencysavior.emergencysavior;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import at.markushi.ui.CircleButton;

/**
 * Created by RSA on 21-03-2016.
 */
public class Seesomething_DetailsActivity extends AppCompatActivity {

    CircleButton btn_add_subject,btn_add_vehicle;
    TextView txt_add_subject,txt_add_vehicle,sign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seesomething_details_activity);
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> F O N T S I N T I A L Z A T I O N >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.otf");

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> A C T I O N B A R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setIcon(R.drawable.navicon);
        SpannableStringBuilder SS = new SpannableStringBuilder(Html.fromHtml("<font color='#3c5899'><small><b>DETAILS</b></small> </font>"));
        SS.setSpan(new CustomTypefaceSpan("#3c5899", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> A C T I O N B A R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> I D E N T I F Y T H E I D  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        btn_add_subject= (CircleButton) findViewById(R.id.circle_btn_add_subject);
        btn_add_vehicle= (CircleButton) findViewById(R.id.circle_btn_add_vehicle);
        txt_add_subject= (TextView) findViewById(R.id.txt_add_subject);
        txt_add_vehicle= (TextView) findViewById(R.id.txt_add_vehicle);
        sign = (TextView) findViewById(R.id.dss);

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> I D E N T I F Y T H E I D  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> S E T T Y P E F A C E      >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        txt_add_subject.setTypeface(tf);
        txt_add_vehicle.setTypeface(tf);
        sign.setTypeface(tf);
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> S E T T Y P E F A C E      >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> L I S T E N E R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        btn_add_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), See_Details_add_subject.class);
                startActivity(intent);
            }
        });
        txt_add_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), See_Details_add_Vehicle.class);
                startActivity(intent);
            }
        });


        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> L I S T E N E R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    }
}
