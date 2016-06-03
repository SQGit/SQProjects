package com.emerengencysavior.emergencysavior;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by RSA on 22-03-2016.
 */
public class Seesomething_My_information extends AppCompatActivity {

    TextView txt_anonymous, txt_sign;
    EditText editText_name, editText_phone, editText_emil, editText_address;
    String string_name, string_phone, string_email, string_address, token, incident_id, str, first_image, second_image, third_image, fourth_image, fith_image, sixth_image, seventh_image, eighth_image, nineth_image, tenth_image;
    SwitchButton switchButton_enable;
    Button btn_submit;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    private ProgressDialog dialog;
        ArrayList<String> slected_item_image;
        String[] imagearray;
    SweetAlertDialog pDialog;
    String
            spr_type_of_acitivy,
            str_what_happened,
            str_when,
            str_location,
            str_city,
            str_address,

    string_subject_name,
            string_alise_name,
            string_height,
            string_weight,
            string_race,
            string_spr_gender,
            string_age,
            string_eye_color,
            string_glasses,
            string_hair_color,
            string_clothing,
            string_address_city,
            string_spr_state,
            string_additional_indo,


    string_another_subject_name,
            string_another_alise_name,
            string_another_height,
            string_another_weight,
            string_another_race,
            string_another_spr_gender,
            string_another_age,
            string_another_eye_color,
            string_another_glasses,
            string_another_hair_color,
            string_another_clothing,
            string_another_address_city,
            string_another_spr_state,
            string_another_additional_indo,

    string_spr_vehicle_type,
            string_spr__make,
            string_model,
            string_color,
            string_no_of_doors,
            string_year,
            string_licensed_plate,
            string_additional_vehicle_commends,

    string_additional_spr_vehicle_type,
            string_additional_spr__make,
            string_additional__model,
            string_additional__color,
            string_additional__no_of_doors,
            string_additional__year,
            string_additional__licensed_plate,
            string_additional__additional_vehicle_commends;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seesomething_my_information);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Seesomething_My_information.this);
        token = sharedPreferences.getString("Session_token", "");

        //@ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ S H A R E D P R E F E R E N C E @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @  @ @  @ @ @ @
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

//@ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ S E E  D E T A I L S  D A T E L O C A T I O N  @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @  @ @  @ @ @ @

        string_name = sharedPreferences.getString("Firstname", " ");
        string_phone = sharedPreferences.getString("Mobile", "");
        Log.d("tag", "defult value" + string_phone);
        string_email = sharedPreferences.getString("Email", "");
        string_address = sharedPreferences.getString("Address", " ");

        str = sharedpreferences.getString("selected","");

         imagearray=str.split("\\#\\#\\#\\#\\#");

        Log.d("tag", "selected image" + str);
        spr_type_of_acitivy = sharedpreferences.getString("spr_type_of_acitivy", "");
        str_what_happened = sharedpreferences.getString("str_what_happened", "");
        str_location = sharedpreferences.getString("str_location", "");
        str_city = sharedpreferences.getString("str_city", "");
        str_city = sharedpreferences.getString("str_city", "");

        Log.d("tag", "spr_type_of_acitivy" + spr_type_of_acitivy);
        Log.d("tag", "str_what_happened" + str_what_happened);
        Log.d("tag", "str_when" + str_when);
        Log.d("tag", "str_location" + str_location);
        Log.d("tag", "str_city" + str_city);
        Log.d("tag", "str_address" + str_address);
        //@ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ S E E  D E T A I L S  D A T E L O C A T I O N  @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @  @ @  @ @ @ @
        //@ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ S E E  D E T A I L S  A D D  SUBJECT @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @  @ @  @ @ @ @
        string_subject_name = sharedpreferences.getString("string_subject_name", "");
        string_alise_name = sharedpreferences.getString("string_alise_name", "");
        string_height = sharedpreferences.getString("string_height", "");
        string_weight = sharedpreferences.getString("string_weight", "");
        string_race = sharedpreferences.getString("string_race", "");
        string_spr_gender = sharedpreferences.getString("string_spr_gender", "");
        string_age = sharedpreferences.getString("string_age", "");
        string_eye_color = sharedpreferences.getString("string_eye_color", "");
        string_glasses = sharedpreferences.getString("string_glasses", "");
        string_hair_color = sharedpreferences.getString("string_hair_color", "");
        string_clothing = sharedpreferences.getString("string_clothing", "");
        string_address_city = sharedpreferences.getString("string_address_city", "");
        string_spr_state = sharedpreferences.getString("string_spr_state", "");
        string_additional_indo = sharedpreferences.getString("string_additional_indo", "");

        string_another_subject_name = sharedpreferences.getString("string_another_subject_name", "");
        string_another_alise_name = sharedpreferences.getString("string_another_alise_name", "");
        string_another_height = sharedpreferences.getString("string_another_height", "");
        string_another_weight = sharedpreferences.getString("string_another_weight", "");
        string_another_race = sharedpreferences.getString("string_another_race", "");
        string_another_spr_gender = sharedpreferences.getString("string_spr_gender", "");
        string_another_age = sharedpreferences.getString("string_another_spr_gender", "");
        string_another_eye_color = sharedpreferences.getString("string_another_eye_color", "");
        string_another_glasses = sharedpreferences.getString("string_another_glasses", "");
        string_another_hair_color = sharedpreferences.getString("string_another_hair_color", "");
        string_another_clothing = sharedpreferences.getString("string_another_clothing", "");
        string_another_address_city = sharedpreferences.getString("string_another_address_city", "");
        string_another_spr_state = sharedpreferences.getString("string_another_spr_state", "");
        string_another_additional_indo = sharedpreferences.getString("string_another_additional_indo", "");


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
        Log.d("tag", "string_spr_state" + string_spr_state);
        Log.d("tag", "string_additional_indo" + string_additional_indo);


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
        Log.d("tag", "string_another_spr_state" + string_another_spr_state);
        Log.d("tag", "string_another_additional_indo" + string_another_additional_indo);

//@ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ S E E  D E T A I L S  A D D  SUBJECT @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @  @ @  @ @ @ @

//@ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ S E E  D E T A I L S  A D D  V E H I C L E @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @  @ @  @ @ @ @
        string_spr_vehicle_type = sharedpreferences.getString("string_spr_vehicle_type", "");
        string_spr__make = sharedpreferences.getString("string_spr__make", "");
        string_model = sharedpreferences.getString("string_model", "");
        string_color = sharedpreferences.getString("string_color", "");
        string_no_of_doors = sharedpreferences.getString("string_no_of_doors", "");
        string_year = sharedpreferences.getString("string_year", "");
        string_licensed_plate = sharedpreferences.getString("string_licensed_plate", "");
        string_additional_vehicle_commends = sharedpreferences.getString("string_additional_vehicle_commends", "");


        string_additional_spr_vehicle_type = sharedpreferences.getString("string_additional_spr_vehicle_type", "");
        string_additional_spr__make = sharedpreferences.getString("string_additional_spr__make", "");
        string_additional__model = sharedpreferences.getString("string_additional__model", "");
        string_additional__color = sharedpreferences.getString("string_additional__color", "");
        string_additional__no_of_doors = sharedpreferences.getString("string_additional__no_of_doors", "");
        string_additional__year = sharedpreferences.getString("string_additional__year", "");
        string_additional__licensed_plate = sharedpreferences.getString("string_additional__licensed_plate", "");
        string_additional__additional_vehicle_commends = sharedpreferences.getString("string_additional__additional_vehicle_commends", "");


        Log.d("tag", "string_spr_vehicle_type" + string_spr_vehicle_type);
        Log.d("tag", "string_make" + string_spr__make);
        Log.d("tag", "string_model" + string_model);
        Log.d("tag", "string_color" + string_color);
        Log.d("tag", "string_no_of_doors" + string_no_of_doors);
        Log.d("tag", "string_year" + string_year);
        Log.d("tag", "string_licensed_plate" + string_licensed_plate);
        Log.d("tag", "string_additional_vehicle_commends" + string_additional_vehicle_commends);

        Log.d("tag", "string_additional_spr_vehicle_type" + string_additional_spr_vehicle_type);
        Log.d("tag", "string_additional_spr__make" + string_additional_spr__make);
        Log.d("tag", "string_additional__model" + string_additional__model);
        Log.d("tag", "string_additional__color" + string_additional__color);
        Log.d("tag", "string_additional__no_of_doors" + string_additional__no_of_doors);
        Log.d("tag", "string_additional__year" + string_additional__year);
        Log.d("tag", "string_additional__licensed_plate" + string_additional__licensed_plate);
        Log.d("tag", "string_additional__additional_vehicle_commends" + string_additional__additional_vehicle_commends);

        //@ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ S E E  D E T A I L S  A D D  V E H I C L E @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @  @ @  @ @ @ @


        //@ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ S H A R E D P R E F E R E N C E @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @  @ @  @ @ @ @

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> F O N T S I N T I A L Z A T I O N >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.otf");
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> A C T I O N B A R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setIcon(R.drawable.navicon);
        SpannableStringBuilder SS = new SpannableStringBuilder(Html.fromHtml("<font color='#3c5899'><small><b>MY INFORMATION</b></small> </font>"));
        SS.setSpan(new CustomTypefaceSpan("#3c5899", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> A C T I O N B A R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> I D E N T I F Y >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        txt_anonymous = (TextView) findViewById(R.id.txt_anonymous);
        txt_sign = (TextView) findViewById(R.id.dss);
        editText_name = (EditText) findViewById(R.id.edt_name);
        editText_phone = (EditText) findViewById(R.id.edt_phone);
        editText_emil = (EditText) findViewById(R.id.edt_Email);
        editText_address = (EditText) findViewById(R.id.edt_address);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        switchButton_enable = (SwitchButton) findViewById(R.id.sb_use_listener);

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  I D E N T I F Y >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  S E T T Y P E F A C E >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        txt_anonymous.setTypeface(tf);
        txt_sign.setTypeface(tf);
        editText_name.setTypeface(tf);
        editText_phone.setTypeface(tf);
        editText_emil.setTypeface(tf);
        editText_address.setTypeface(tf);

        if (!string_name.equals("null")) {
            editText_name.setText(string_name);
        } else {
            editText_name.setHint("Name");
        }
        if (!string_phone.equals("null")) {
            editText_phone.setText(string_phone);
        } else {
            editText_phone.setHint("Phone");
        }
        if (!string_email.equals("null")) {
            editText_emil.setText(string_email);
        } else {
            editText_emil.setHint("Email");
        }
        if (!string_address.equals("null")) {
            editText_address.setText(string_address);
        } else {
            editText_address.setHint("Address");
        }


        btn_submit.setTypeface(tf);

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  S E T T Y P E F A C E>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  S E T T Y P E F A C E>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        switchButton_enable.setChecked(false);

        switchButton_enable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editText_name.setVisibility(View.GONE);
                    editText_phone.setVisibility(View.GONE);
                    editText_address.setVisibility(View.GONE);
                    editText_emil.setVisibility(View.GONE);
                    Log.d("tag", "checked==>true" + isChecked);

                } else {
                    Log.d("tag", "checked==>false" + isChecked);
                    editText_name.setVisibility(View.VISIBLE);
                    editText_phone.setVisibility(View.VISIBLE);
                    editText_address.setVisibility(View.VISIBLE);
                    editText_emil.setVisibility(View.VISIBLE);
                }

            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                string_name = editText_name.getText().toString().trim();
                string_phone = editText_phone.getText().toString().trim();
                string_email = editText_emil.getText().toString().trim();
                string_address = editText_address.getText().toString().trim();

                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedpreferences.edit();

                edit.remove("string_name");
                edit.remove("string_phone");
                edit.remove("string_email");
                edit.remove("string_address");

                Log.d("tag", "string_name" + string_name);
                Log.d("tag", "string_phone" + string_phone);
                Log.d("tag", "string_email" + string_email);
                Log.d("tag", "string_address" + string_address);

                edit.putString("string_name", string_name);
                edit.putString("string_phone", string_phone);
                edit.putString("string_email", string_email);
                edit.putString("string_address", string_address);
                edit.commit();
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                string_name = sharedpreferences.getString("string_name", "");
                string_phone = sharedpreferences.getString("string_phone", "");
                string_email = sharedpreferences.getString("string_email", "");
                string_address = sharedpreferences.getString("string_address", "");


                if (Util.Operations.isOnline(Seesomething_My_information.this)) {
                    new MyActivityAsync(spr_type_of_acitivy,
                            str_what_happened,
                            str_when,
                            str_location,
                            str_city,
                            str_address,

                            string_subject_name,
                            string_alise_name,
                            string_height,
                            string_weight,
                            string_race,
                            string_spr_gender,
                            string_age,
                            string_eye_color,
                            string_glasses,
                            string_hair_color,
                            string_clothing,
                            string_address_city,
                            string_spr_state,
                            string_additional_indo,

                            string_another_subject_name,
                            string_another_alise_name,
                            string_another_height,
                            string_another_weight,
                            string_another_race,
                            string_another_spr_gender,
                            string_another_age,
                            string_another_eye_color,
                            string_another_glasses,
                            string_another_hair_color,
                            string_another_clothing,
                            string_another_address_city,
                            string_another_spr_state,
                            string_another_additional_indo,

                            string_spr_vehicle_type,
                            string_spr__make,
                            string_model,
                            string_color,
                            string_no_of_doors,
                            string_year,
                            string_licensed_plate,
                            string_additional_vehicle_commends,

                            string_additional_spr_vehicle_type,
                            string_additional_spr__make,
                            string_additional__model,
                            string_additional__color,
                            string_additional__no_of_doors,
                            string_additional__year,
                            string_additional__licensed_plate,
                            string_additional__additional_vehicle_commends,
                            string_name,
                            string_phone,
                            string_email,
                            string_address


                    ).execute();

                } else {

                    new SweetAlertDialog(Seesomething_My_information.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("ALERT")
                            .setContentText("No Internet Connectivity")
                            .show();
                }


            }
        });
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  S E T T Y P E F A C E>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    }

    @Override
    public void onBackPressed() {

    }

    private class MyActivityAsync extends AsyncTask<String, Void, String> {

        String spr_type_of_acitivy,
                str_what_happened,
                str_when,
                str_location,
                str_city,
                str_address,

        string_subject_name,
                string_alise_name,
                string_height,
                string_weight,
                string_race,
                string_spr_gender,
                string_age,
                string_eye_color,
                string_glasses,
                string_hair_color,
                string_clothing,
                string_address_city,
                string_spr_state,
                string_additional_indo,


        string_another_subject_name,
                string_another_alise_name,
                string_another_height,
                string_another_weight,
                string_another_race,
                string_another_spr_gender,
                string_another_age,
                string_another_eye_color,
                string_another_glasses,
                string_another_hair_color,
                string_another_clothing,
                string_another_address_city,
                string_another_spr_state,
                string_another_additional_indo,

        string_spr_vehicle_type,
                string_spr__make,
                string_model,
                string_color,
                string_no_of_doors,
                string_year,
                string_licensed_plate,
                string_additional_vehicle_commends,

        string_additional_spr_vehicle_type,
                string_additional_spr__make,
                string_additional__model,
                string_additional__color,
                string_additional__no_of_doors,
                string_additional__year,
                string_additional__licensed_plate,
                string_additional__additional_vehicle_commends,
                string_name,
                string_phone,
                string_email,
                string_address;

        public MyActivityAsync(String spr_type_of_acitivy,
                               String str_what_happened,
                               String str_when,
                               String str_location,
                               String str_city,
                               String str_address,
                               String string_subject_name,
                               String string_alise_name,
                               String string_height,
                               String string_weight,
                               String string_race,
                               String string_spr_gender,
                               String string_age,
                               String string_eye_color,
                               String string_glasses,
                               String string_hair_color,
                               String string_clothing,
                               String string_address_city,
                               String string_spr_state,
                               String string_additional_indo,
                               String string_another_subject_name,
                               String string_another_alise_name,
                               String string_another_height,
                               String string_another_weight,
                               String string_another_race,
                               String string_another_spr_gender,
                               String string_another_age,
                               String string_another_eye_color,
                               String string_another_glasses,
                               String string_another_hair_color,
                               String string_another_clothing,
                               String string_another_address_city,
                               String string_another_spr_state,
                               String string_another_additional_indo,
                               String string_spr_vehicle_type,
                               String string_spr__make,
                               String string_model,
                               String string_color,
                               String string_no_of_doors,
                               String string_year,
                               String string_licensed_plate,
                               String string_additional_vehicle_commends,
                               String string_additional_spr_vehicle_type,
                               String string_additional_spr__make,
                               String string_additional__model,
                               String string_additional__color,
                               String string_additional__no_of_doors,
                               String string_additional__year,
                               String string_additional__licensed_plate,
                               String string_additional__additional_vehicle_commends,
                               String string_name,
                               String string_phone,
                               String string_email,
                               String string_address) {

            this.spr_type_of_acitivy = spr_type_of_acitivy;
            this.str_what_happened = str_what_happened;
            this.str_when = str_when;
            this.str_location = str_location;
            this.str_city = str_city;
            this.str_address = str_address;

            this.string_subject_name = string_subject_name;
            this.string_alise_name = string_alise_name;
            this.string_height = string_height;
            this.string_weight = string_weight;
            this.string_race = string_race;
            this.string_spr_gender = string_spr_gender;
            this.string_age = string_age;
            this.string_eye_color = string_eye_color;
            this.string_glasses = string_glasses;
            this.string_hair_color = string_hair_color;
            this.string_clothing = string_clothing;
            this.string_address_city = string_address_city;
            this.string_spr_state = string_spr_state;
            this.string_additional_indo = string_additional_indo;


            this.string_another_subject_name = string_another_subject_name;
            this.string_another_alise_name = string_another_alise_name;
            this.string_another_height = string_another_height;
            this.string_another_weight = string_another_weight;
            this.string_another_race = string_another_race;
            this.string_another_spr_gender = string_another_spr_gender;
            this.string_another_age = string_another_age;
            this.string_another_eye_color = string_another_eye_color;
            this.string_another_glasses = string_another_glasses;
            this.string_another_hair_color = string_another_hair_color;
            this.string_another_clothing = string_another_clothing;
            this.string_another_address_city = string_another_address_city;
            this.string_another_spr_state = string_another_spr_state;
            this.string_another_additional_indo = string_another_additional_indo;

            this.string_spr_vehicle_type = string_spr_vehicle_type;
            this.string_spr__make = string_spr__make;
            this.string_model = string_model;
            this.string_color = string_color;
            this.string_no_of_doors = string_no_of_doors;
            this.string_year = string_year;
            this.string_licensed_plate = string_licensed_plate;
            this.string_additional_vehicle_commends = string_additional_vehicle_commends;

            this.string_additional_spr_vehicle_type = string_additional_spr_vehicle_type;
            this.string_additional_spr__make = string_additional_spr__make;
            this.string_additional__model = string_additional__model;
            this.string_additional__color = string_additional__color;
            this.string_additional__no_of_doors = string_additional__no_of_doors;
            this.string_additional__year = string_additional__year;
            this.string_additional__licensed_plate = string_additional__licensed_plate;
            this.string_additional__additional_vehicle_commends = string_additional__additional_vehicle_commends;

            this.string_name = string_name;
            this.string_phone = string_phone;
            this.string_email = string_email;
            this.string_address = string_address;
        }

        protected void onPreExecute() {

            super.onPreExecute();

            pDialog = new SweetAlertDialog(Seesomething_My_information.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Please wait... ");
            pDialog.setCancelable(true);
            pDialog.getWindow().setLayout(1000, 100);
            pDialog.show();


        }

        @Override
        protected String doInBackground(String... params) {

            String json = "", s = "";


            JSONObject jsonObject = new JSONObject();
            try {


                jsonObject.accumulate("type_of_activity", spr_type_of_acitivy);
                jsonObject.accumulate("what_happened", str_what_happened);
                jsonObject.accumulate("when", str_when);
                jsonObject.accumulate("location", str_location);
                jsonObject.accumulate("city", str_city);
                jsonObject.accumulate("address", str_address);


                jsonObject.accumulate("subject_name", string_subject_name);
                jsonObject.accumulate("subject_alias_name", string_alise_name);
                jsonObject.accumulate("subject_height", string_height);
                jsonObject.accumulate("subject_weight", string_weight);
                jsonObject.accumulate("subject_race", string_race);
                jsonObject.accumulate("subject_gender", string_spr_gender);
                jsonObject.accumulate("subject_age", string_age);
                jsonObject.accumulate("subject_eye_color", string_eye_color);
                jsonObject.accumulate("subject_glassses", string_glasses);
                jsonObject.accumulate("subject_hair_color", string_hair_color);
                jsonObject.accumulate("subject_clothing", string_clothing);
                jsonObject.accumulate("subject_address_city", string_address_city);
                jsonObject.accumulate("subject_state", string_spr_state);
                jsonObject.accumulate("subject_additional_info", string_additional_indo);


                jsonObject.accumulate("subject_additional_name", string_another_subject_name);
                jsonObject.accumulate("subject_additional_alias_name", string_another_alise_name);
                jsonObject.accumulate("subject_additional_weight", string_another_height);
                jsonObject.accumulate("subject_additional_weight", string_another_weight);
                jsonObject.accumulate("subject_additional_race", string_another_race);
                jsonObject.accumulate("subject_additional_gender", string_another_spr_gender);
                jsonObject.accumulate("subject_additional_age", string_another_age);
                jsonObject.accumulate("subject_additional_eye_color", string_another_eye_color);
                jsonObject.accumulate("subject_additional_glassses", string_another_glasses);
                jsonObject.accumulate("subject_additional_hair_color", string_another_hair_color);
                jsonObject.accumulate("subject_additional_clothing", string_another_clothing);
                jsonObject.accumulate("subject_additional_address_city", string_another_address_city);
                jsonObject.accumulate("subject_additional_state", string_another_spr_state);
                jsonObject.accumulate("subject_additional_additional_info", string_another_additional_indo);


                jsonObject.accumulate("vehicle_type", string_spr_vehicle_type);
                jsonObject.accumulate("vehicle_make", string_spr__make);
                jsonObject.accumulate("vehicle_model", string_model);
                jsonObject.accumulate("vehicle_color", string_color);
                jsonObject.accumulate("vehicle_no_door", string_no_of_doors);
                jsonObject.accumulate("vehicle_color_year", string_year);
                jsonObject.accumulate("vehicle_license_plate_no", string_licensed_plate);
                jsonObject.accumulate("vehicle_additional_info", string_additional_vehicle_commends);


                jsonObject.accumulate("vehicle_additional_type", string_additional_spr_vehicle_type);
                jsonObject.accumulate("vehicle_additional_make", string_additional_spr__make);
                jsonObject.accumulate("vehicle_additional_model", string_additional__model);
                jsonObject.accumulate("vehicle_additional_color", string_additional__color);
                jsonObject.accumulate("vehicle_additional_no_door", string_additional__no_of_doors);
                jsonObject.accumulate("vehicle_additional_color_year", string_additional__year);
                jsonObject.accumulate("vehicle_additional_license_plate_no", string_additional__licensed_plate);
                jsonObject.accumulate("vehicle_additional_additional_info", string_additional__additional_vehicle_commends);


                jsonObject.accumulate("myinformation_name", string_name);
                jsonObject.accumulate("myinformation_phone", string_phone);
                jsonObject.accumulate("myinformation_email", string_email);
                jsonObject.accumulate("myinformation_address", string_address);


                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return s = HttpUtils.makeRequest(CONFIG.URL + "/ssds", json, token);
            } catch (JSONException e) {

                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("json result", s);
            pDialog.dismiss();
            if (s == "") {

                new SweetAlertDialog(Seesomething_My_information.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("ALERT")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();
            } else {

                try {
                    JSONObject jo = new JSONObject(s);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                    Log.d("tag", "<-----Status----->" + status);
                    if (status.equals("success")) {
                        incident_id = jo.getString("incident_id");

                        Log.d("tag", "<-----incident_id----->" + incident_id);


                        new UploadFileToServer().execute();
                        Log.d("tag", "slected_item_image_parameter = " + slected_item_image);

                    } else {


                        Toast.makeText(getApplicationContext(), "Poasted failed please try again", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


        }
    }


    //############################################# M U L T P L E I M A G E U P L O A D #########################################
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {

        public UploadFileToServer() {


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Seesomething_My_information.this);
            dialog.setMessage("Uploading...");
            dialog.setCancelable(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

        }

        @Override
        protected String doInBackground(Void... params) {

            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;
            Log.d("tag", "session_token = " + token);
            Log.d("tag", "incident_id = " + incident_id);

            //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

            try {

                HttpClient client = new DefaultHttpClient();
                HttpPost postMethod = new HttpPost(CONFIG.MULTIPLE_IMAGE + "/api/ssdspicupload");
                postMethod.addHeader("session_token ", token);
                postMethod.addHeader("incident_id ", incident_id);
                MultipartEntity entity = new MultipartEntity();
                int i=1;
                for (String zimg : imagearray) {

                    File file = new File(String.valueOf(zimg));

                    FileBody contentFile = new FileBody(file);
                    entity.addPart("fileUpload" +(i++), contentFile);

                }

                 postMethod.setEntity(entity);
                HttpResponse response = client.execute(postMethod);
                HttpEntity r_entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    responseString = EntityUtils.toString(r_entity);

                    JSONObject result1 = new JSONObject(responseString);
                    String status = result1.getString("status");

                    if (status.equals("success")) {

                    }
                } else {
                    responseString = "Error occurred! Http Status Code: " + statusCode;
                }

            } catch (Exception e) {
                responseString = e.toString();
            }
            return responseString;

        }

        @Override
        protected void onPostExecute(String responseString) {
            Log.e("TAG", "Response from server: " + responseString);
            super.onPostExecute(responseString);
            dialog.dismiss();
            if (responseString == "") {

                new SweetAlertDialog(Seesomething_My_information.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();
            } else {

                try {
                    JSONObject result1 = new JSONObject(responseString);
                    String status = result1.getString("status");
                    String updatedurl = result1.getString("url");
                    Log.d("tag", "new Image_url " + updatedurl);
                    if (status.equals("success")) {
                        Intent i = new Intent(Seesomething_My_information.this, MainActivity.class);
                        startActivity(i);

                    } else {
                        Toast.makeText(getApplicationContext(), "Profile Image Uploaded but Something went wrong.. ", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }

    }


    //############################################# M U L T P L E I M A G E U P L O A D #########################################
}

