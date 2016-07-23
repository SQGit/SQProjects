package com.emergencysavior.emergencysavior;

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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by RSA on 23-03-2016.
 */
public class See_Details_add_subject extends AppCompatActivity {
    ScrollView scroll_bottomvalue;
    TextView aditionalinformation, sign;
    LinearLayout additional;
    Button btn_back, btn_next;
    Spinner spr_gender, spr_additonal_gender, spr_state, spr_additonal_state;
    EditText editText_name, editText_alise_name, editText_height, editText_weight, editText_race, editText_age, editText_eye_color, editText_glasses, editText_hair_color, editText_clothing, editText_address_city, editText_state, editText_additional_indo;
    String string_subject_name, string_alise_name, string_height, string_weight, string_race, string_spr_gender, string_age, string_eye_color, string_glasses, string_hair_color, string_clothing, string_address_city, string_spr_state, string_additional_indo;
    //****************************  A N O T H E R *******************************************************************************************************************************************************************************************************************************************************************************************************************************
    EditText editText_another__name, editText_another_alise_name, editText_another_height, editText_another_weight, editText_another_race,editText_another_age, editText_another_eye_color, editText_another_glasses, editText_another_hair_color, editText_another_clothing, editText_another_address_city,  editText_another_additional_indo;
    String string_another_subject_name, string_another_alise_name, string_another_height, string_another_weight, string_another_race, string_another_spr_gender, string_another_age, string_another_eye_color, string_another_glasses, string_another_hair_color, string_another_clothing, string_another_address_city, string_another_spr_state, string_another_additional_indo;

    //****************************  A N O T H E R *******************************************************************************************************************************************************************************************************************************************************************************************************************************

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_details_add_subject);
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> F O N T S I N T I A L Z A T I O N >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> A C T I O N B A R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setIcon(R.drawable.navicon);
        SpannableStringBuilder SS = new SpannableStringBuilder(Html.fromHtml("<font color='#3c5899'><small><b>ADD SUBJECT </b></small> </font>"));
        SS.setSpan(new CustomTypefaceSpan("#3c5899", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> A C T I O N B A R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> I D E N T I F Y T H E I D  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        aditionalinformation = (TextView) findViewById(R.id.additional_information);
        scroll_bottomvalue = (ScrollView) findViewById(R.id.bottomvalue);
        additional = (LinearLayout) findViewById(R.id.additional_information_view);
        additional.setVisibility(View.GONE);
        sign = (TextView) findViewById(R.id.dss);
        btn_back = (Button) findViewById(R.id.btn_previous);
        btn_next = (Button) findViewById(R.id.btn_next);

        editText_name = (EditText) findViewById(R.id.edt_name);
        editText_alise_name = (EditText) findViewById(R.id.edt_alias_name);
        editText_height = (EditText) findViewById(R.id.edt_height);
        editText_weight = (EditText) findViewById(R.id.edt_weight);
        editText_race = (EditText) findViewById(R.id.edt_race);
        editText_age = (EditText) findViewById(R.id.edt_age);

        spr_gender = (Spinner) findViewById(R.id.spr_gender);

        editText_eye_color = (EditText) findViewById(R.id.edt_eye_color);
        editText_glasses = (EditText) findViewById(R.id.edt_glassess);
        editText_hair_color = (EditText) findViewById(R.id.edt_haircolor);
        editText_clothing = (EditText) findViewById(R.id.edt_clothing);
        editText_address_city = (EditText) findViewById(R.id.edt_address);
        spr_state = (Spinner) findViewById(R.id.spr_state);
        editText_additional_indo = (EditText) findViewById(R.id.edt_add_info);


        //////////**********************************
        editText_another__name = (EditText) findViewById(R.id.edt_additional__name);
        editText_another_alise_name = (EditText) findViewById(R.id.edt_additional__alias_name);
        editText_another_height = (EditText) findViewById(R.id.edt_additional__height);
        editText_another_weight = (EditText) findViewById(R.id.edt_additional__weight);
        editText_another_race = (EditText) findViewById(R.id.edt_additional__race);
        spr_additonal_gender = (Spinner) findViewById(R.id.spr_additional_gender);
        editText_another_age = (EditText) findViewById(R.id.edt_additional__age);
        editText_another_eye_color = (EditText) findViewById(R.id.edt_additional__eye_color);
        editText_another_glasses = (EditText) findViewById(R.id.edt_additional__glassess);
        editText_another_hair_color = (EditText) findViewById(R.id.edt_additional__haircolor);
        editText_another_clothing = (EditText) findViewById(R.id.edt_additional__clothing);
        editText_another_address_city = (EditText) findViewById(R.id.edt_additional__address);
        spr_additonal_state = (Spinner) findViewById(R.id.spr_additional__state);
        editText_another_additional_indo = (EditText) findViewById(R.id.edt_additional_add_info);


        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> I D E N T I F Y T H E I D  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
         //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> S E T T Y P E F A C E      >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        btn_back.setTypeface(tf);
        btn_next.setTypeface(tf);

        aditionalinformation.setTypeface(tf);
        sign.setTypeface(tf);
        editText_name.setTypeface(tf);
        editText_alise_name.setTypeface(tf);
        editText_height.setTypeface(tf);
        editText_weight.setTypeface(tf);
        editText_race.setTypeface(tf);

        editText_age.setTypeface(tf);
        editText_eye_color.setTypeface(tf);
        editText_glasses.setTypeface(tf);
        editText_hair_color.setTypeface(tf);
        editText_clothing.setTypeface(tf);
        editText_address_city.setTypeface(tf);
        editText_additional_indo.setTypeface(tf);

        editText_another__name.setTypeface(tf);
        editText_another_alise_name.setTypeface(tf);
        editText_another_height.setTypeface(tf);
        editText_another_weight.setTypeface(tf);
        editText_another_race.setTypeface(tf);

        editText_another_age.setTypeface(tf);
        editText_another_eye_color.setTypeface(tf);
        editText_another_glasses.setTypeface(tf);
        editText_another_hair_color.setTypeface(tf);
        editText_another_clothing.setTypeface(tf);
        editText_another_address_city.setTypeface(tf);
        editText_another_additional_indo.setTypeface(tf);

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> S E T T Y P E F A C E      >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> L I S T E N E R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        aditionalinformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (additional.getVisibility() == View.VISIBLE) {
                    additional.setVisibility(View.GONE);
                } else {
                    additional.setVisibility(View.VISIBLE);
                    scroll_bottomvalue.post(new Runnable() {
                        @Override
                        public void run() {
                            scroll_bottomvalue.scrollTo(0, scroll_bottomvalue.getBottom());
                            scroll_bottomvalue.fullScroll(scroll_bottomvalue.FOCUS_DOWN);

                        }
                    });

                }
            }
        });


        //************************************************** spinner value ******************************************************************

        MySpinnerAdapter<CharSequence> adapter = new MySpinnerAdapter<CharSequence>(this,R.layout.spinner_item, Arrays.asList(getResources().getStringArray(R.array.Gender)));
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spr_gender.setAdapter(adapter);
        spr_gender.setPrompt("Country code");

        spr_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if (position > 0) {
                    Log.d("tag", "add code =====>" + selectedItemText);
                    string_spr_gender=selectedItemText;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        MySpinnerAdapter<CharSequence> adapter_additional_gender = new MySpinnerAdapter<CharSequence>(this,R.layout.spinner_item, Arrays.asList(getResources().getStringArray(R.array.Gender)));
        adapter_additional_gender.setDropDownViewResource(R.layout.spinner_item);
        spr_additonal_gender.setAdapter(adapter_additional_gender);
        spr_additonal_gender.setPrompt("Select gender");

        spr_additonal_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if (position > 0) {
                    Log.d("tag", "add code =====>" + selectedItemText);
                    string_another_spr_gender=selectedItemText;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        MySpinnerAdapter<CharSequence> adapter_state = new MySpinnerAdapter<CharSequence>(this,R.layout.spinner_item, Arrays.asList(getResources().getStringArray(R.array.usastate)));
        adapter_state.setDropDownViewResource(R.layout.spinner_item);
        spr_state.setAdapter(adapter_state);
        spr_state.setPrompt("State");

        spr_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if (position > 0) {

                    Log.d("tag", "add code =====>" + selectedItemText);
                    string_spr_state=selectedItemText;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        MySpinnerAdapter<CharSequence> adapter_additional_state = new MySpinnerAdapter<CharSequence>(this,R.layout.spinner_item, Arrays.asList(getResources().getStringArray(R.array.usastate)));
        adapter_additional_state.setDropDownViewResource(R.layout.spinner_item);
        spr_additonal_state.setAdapter(adapter_additional_state);
        spr_additonal_state.setPrompt("State");

        spr_additonal_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if (position > 0) {

                    Log.d("tag", "add code =====>" + selectedItemText);

                    string_another_spr_state=selectedItemText;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//************************************************** spinner value ******************************************************************
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> L I S T E N E R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Seesomething_Date_location.class);
                startActivity(intent);
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> S T R I N G C O N CO D I N A T E ONE  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                string_subject_name = editText_name.getText().toString().trim();
                string_alise_name = editText_alise_name.getText().toString().trim();
                string_height = editText_height.getText().toString().trim();
                string_weight = editText_weight.getText().toString().trim();
                string_race = editText_race.getText().toString().trim();

                string_age = editText_age.getText().toString().trim();

                //string_gender = spr_gender.getText().toString().trim();

                string_eye_color = editText_eye_color.getText().toString().trim();
                string_glasses = editText_glasses.getText().toString().trim();
                string_hair_color = editText_hair_color.getText().toString().trim();
                string_clothing = editText_clothing.getText().toString().trim();
                string_address_city = editText_address_city.getText().toString().trim();



                string_additional_indo = editText_additional_indo.getText().toString().trim();
                //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> S T R I N G C O N C O D I N A T E  ONE  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


                //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> S T R I N G C O N CO D I N A T E T W O  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                string_another_subject_name = editText_another__name.getText().toString().trim();
                string_another_alise_name = editText_another_alise_name.getText().toString().trim();
                string_another_height = editText_another_height.getText().toString().trim();
                string_another_weight = editText_another_weight.getText().toString().trim();
                string_another_race = editText_another_race.getText().toString().trim();

                string_another_age = editText_another_age.getText().toString().trim();
                string_another_eye_color = editText_another_eye_color.getText().toString().trim();
                string_another_glasses = editText_another_glasses.getText().toString().trim();
                string_another_hair_color = editText_another_hair_color.getText().toString().trim();
                string_another_clothing = editText_another_clothing.getText().toString().trim();
                string_another_address_city = editText_another_address_city.getText().toString().trim();



                string_another_additional_indo = editText_another_additional_indo.getText().toString().trim();
                //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> S T R I N G C O N C O D I N A T E  ONE  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



                //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> S T R I N G C O N CO D I N A T E  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedpreferences.edit();
                ////$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ R E M O V E  P R E F E R E N C E S$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                edit.remove("string_subject_name");
                edit.remove("string_alise_name");
                edit.remove("string_height");
                edit.remove("string_weight");
                edit.remove("string_race");
                edit.remove("string_spr_gender");
                edit.remove("string_age");
                edit.remove("string_eye_color");
                edit.remove("string_glasses");
                edit.remove("string_hair_color");
                edit.remove("string_clothing");
                edit.remove("string_address_city");
                edit.remove("string_spr_state");
                edit.remove("string_additional_indo");


                ////$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ R E M O V E  P R E F E R E N C E $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                Log.d("tag", "string_subject_name" + string_subject_name);
                Log.d("tag", "string_alise_name" + string_alise_name);
                Log.d("tag", "string_height" + string_height);
                Log.d("tag", "string_weight" + string_weight);
                Log.d("tag", "string_race" + string_race);
                Log.d("tag", "string_spr_gender" + string_spr_gender);
                Log.d("tag", "string_age" + string_age);
                Log.d("tag", "string_eye_color" + string_eye_color);
                Log.d("tag", "string_glasses" + string_glasses);
                Log.d("tag", "string_hair_color" + string_hair_color);
                Log.d("tag", "string_clothing" + string_clothing);
                Log.d("tag", "string_address_city" + string_address_city);
                Log.d("tag","string_spr_state"+string_spr_state);

                Log.d("tag", "string_additional_indo" + string_additional_indo);
                ////$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ P U T P R E F E R E N C E  $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                edit.putString("string_subject_name", string_subject_name);
                edit.putString("string_alise_name", string_alise_name);
                edit.putString("string_height", string_height);
                edit.putString("string_weight", string_weight);
                edit.putString("string_race", string_race);
                edit.putString("string_spr_gender", string_spr_gender);
                edit.putString("string_age", string_age);
                edit.putString("string_eye_color", string_eye_color);
                edit.putString("string_glasses", string_glasses);
                edit.putString("string_hair_color", string_hair_color);
                edit.putString("string_clothing", string_clothing);
                edit.putString("string_address_city", string_address_city);
                edit.putString("string_spr_state",string_spr_state);
                edit.putString("string_additional_indo", string_additional_indo);






                ////$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ R E M O V E  P R E F E R E N C E S$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                edit.remove("string_another_subject_name");
                edit.remove("string_another_alise_name");
                edit.remove("string_another_height");
                edit.remove("string_another_weight");
                edit.remove("string_another_race");
                edit.remove("string_another_spr_gender");
                edit.remove("string_another_age");
                edit.remove("string_another_eye_color");
                edit.remove("string_another_glasses");
                edit.remove("string_another_hair_color");
                edit.remove("string_another_clothing");
                edit.remove("string_another_address_city");
                edit.remove("string_another_spr_state");

                edit.remove("string_another_additional_indo");


                ////$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ R E M O V E  P R E F E R E N C E $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                Log.d("tag", "string_another_subject_name" + string_another_subject_name);
                Log.d("tag", "string_another_alise_name" + string_another_alise_name);
                Log.d("tag", "string_another_height" + string_another_height);
                Log.d("tag", "string_another_weight" + string_another_weight);
                Log.d("tag", "string_another_race" + string_another_race);
                Log.d("tag", "string_another_spr_gender" + string_another_spr_gender);
                Log.d("tag", "string_another_age" + string_another_age);
                Log.d("tag", "string_another_eye_color" + string_another_eye_color);
                Log.d("tag", "string_another_glasses" + string_another_glasses);
                Log.d("tag", "string_another_hair_color" + string_another_hair_color);
                Log.d("tag", "string_another_clothing" + string_another_clothing);
                Log.d("tag", "string_another_address_city" + string_another_address_city);
                Log.d("tag","string_another_spr_state"+string_another_spr_state);
                Log.d("tag", "string_another_additional_indo" + string_another_additional_indo);
                ////$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ P U T P R E F E R E N C E  $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                edit.putString("string_another_subject_name", string_another_subject_name);
                edit.putString("string_another_alise_name", string_another_alise_name);
                edit.putString("string_another_height", string_another_height);
                edit.putString("string_another_weight", string_another_weight);
                edit.putString("string_another_race", string_another_race);
                edit.putString("string_another_spr_gender",string_another_spr_gender);
                edit.putString("string_another_age", string_another_age);
                edit.putString("string_another_eye_color", string_another_eye_color);
                edit.putString("string_another_glasses", string_another_glasses);
                edit.putString("string_another_hair_color", string_another_hair_color);
                edit.putString("string_another_clothing", string_another_clothing);
                edit.putString("string_another_address_city", string_another_address_city);
                edit.putString("string_another_spr_state",string_another_spr_state);
                edit.putString("string_another_additional_indo", string_additional_indo);
                edit.commit();

                //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> P U T  P R E F E R E N C E  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>




                //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> P U T  P R E F E R E N C E  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

                Intent intent = new Intent(getApplicationContext(), See_Details_add_Vehicle.class);
                startActivity(intent);

            }
        });

    }
}
