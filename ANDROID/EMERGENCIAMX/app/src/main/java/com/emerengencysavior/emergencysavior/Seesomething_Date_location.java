package com.emerengencysavior.emergencysavior;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;

/**
 * [* Created by RSA on 21-03-2016.
 */
public class Seesomething_Date_location extends AppCompatActivity {
    EditText editext_what_happened, editext_when, editext_location, editext_country, editext_city, editext_address;
    String str_type_activity, str_what_happened, str_when, str_location, str_country, str_city, str_address, spr_type_of_acitivy;
    Button btn_back, btn_next;
    TextView txt_sign;
    Spinner spinner_type_activity;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seesomething_date_location);
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> F O N T S I N T I A L Z A T I O N >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        final Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> A C T I O N B A R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setIcon(R.drawable.navicon);
        SpannableStringBuilder SS = new SpannableStringBuilder(Html.fromHtml("<font color='#3c5899'><small><b>DATE & LOCATION</b></small> </font>"));
        SS.setSpan(new CustomTypefaceSpan("#3c5899", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> A C T I O N B A R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        spinner_type_activity = (Spinner) findViewById(R.id.spr_type_activity);
        editext_what_happened = (EditText) findViewById(R.id.edt_what_happened);
        editext_when = (EditText) findViewById(R.id.edt_when);
        editext_location = (EditText) findViewById(R.id.edt_location);
        editext_city = (EditText) findViewById(R.id.edt_city);
        editext_address = (EditText) findViewById(R.id.edt_address);
        btn_back = (Button) findViewById(R.id.btn_previous);
        btn_next = (Button) findViewById(R.id.btn_next);
        txt_sign = (TextView) findViewById(R.id.dss);
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>T E X T T Y P E F A C E>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // spinner_type_activity.setTypeface(tf);
        editext_what_happened.setTypeface(tf);
        editext_when.setTypeface(tf);
        editext_location.setTypeface(tf);
        editext_city.setTypeface(tf);
        editext_address.setTypeface(tf);
        btn_back.setTypeface(tf);
        btn_next.setTypeface(tf);
        txt_sign.setTypeface(tf);

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>T E X T T Y P E F A C E>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        MySpinnerAdapter<CharSequence> adapter = new MySpinnerAdapter<CharSequence>(this,R.layout.spinner_item, Arrays.asList(getResources().getStringArray(R.array.Typeofactivity)));
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner_type_activity.setAdapter(adapter);
        spinner_type_activity.setPrompt("Type of Activity");

        spinner_type_activity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);



                if (position > 0) {
                    spr_type_of_acitivy = selectedItemText;
                    Log.d("tag", "spinner_type_activity =====>" + spr_type_of_acitivy);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> L I S T E N E R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Seesomething_dashboard.class);
                startActivity(intent);
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> sharedpreference >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

                str_what_happened = editext_what_happened.getText().toString().trim();
                str_when = editext_when.getText().toString().trim();
                str_location = editext_location.getText().toString().trim();
                str_city = editext_city.getText().toString().trim();
                str_address = editext_address.getText().toString().trim();

                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedpreferences.edit();
                edit.remove("spr_type_of_acitivy");
                edit.remove("str_what_happened");
                edit.remove("str_when");
                edit.remove("str_location");
                edit.remove("str_city");
                edit.remove("str_address");
                ////$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                Log.d("tag", "spr_type_of_acitivy" + spr_type_of_acitivy);
                Log.d("tag", "str_what_happened" + str_what_happened);
                Log.d("tag", "str_when" + str_when);
                Log.d("tag", "str_location" + str_location);
                Log.d("tag", "str_city" + str_city);
                Log.d("tag", "str_address" + str_address);
////$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                edit.putString("spr_type_of_acitivy", spr_type_of_acitivy);
                edit.putString("str_what_happened", str_what_happened);
                edit.putString("str_when", str_when);
                edit.putString("str_location", str_location);
                edit.putString("str_city", str_city);
                edit.putString("str_address", str_address);
                edit.commit();

                //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> sharedpreference >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                Intent intent = new Intent(getApplicationContext(), Seesomething_DetailsActivity.class);
                startActivity(intent);

            }
        });

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> L I S T E N E R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    }

}