package www.sqindia.net.ilerasoftadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ANANDH on 12-12-2015.
 */
public class Registeration extends AppCompatActivity {

    Button submit;
    TextView exit;
    EditText HName, HUserid, HPassword, HConformP, HEmail, HPhone, Hstreet, HCity, HState, HPostalCode;
    String hname, huserid, hpassword, hemail, hconformp, hphone, hstreet, hcity, hstate, hpostalcode;


    JSONObject jsonObject, jsonKey;
    public static String URL_REGISTER = "http://sqindia01.cloudapp.net/cologix/api/v1/hospital/registerHospital";
    private int i = -1;
    SweetAlertDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeration);
        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'><big><b>REGISTER</b></big> </font>"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xcececece));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.bck_arrow);

        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&


        //---------------------------------------EditText Initialize--------------------------------------------------//
        HName = (EditText) findViewById(R.id.Hospital_name);
        HUserid = (EditText) findViewById(R.id.UserId);
        HPassword = (EditText) findViewById(R.id.password);
        HEmail = (EditText) findViewById(R.id.Email_Id);
        HConformP = (EditText) findViewById(R.id.ConformPassword);
        HPhone = (EditText) findViewById(R.id.Phone_Number);
        Hstreet = (EditText) findViewById(R.id.Street_Address);
        HCity = (EditText) findViewById(R.id.city);
        HState = (EditText) findViewById(R.id.state);
        HPostalCode = (EditText) findViewById(R.id.postal_code);

        //---------------------------------------EditText Initialize--------------------------------------------------//

       // exit = (TextView) findViewById(R.id.back_iv);
        submit = (Button) findViewById(R.id.Register);
       /* exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new SweetAlertDialog(Registeration.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Exit Application")
                        .setContentText("Are you sure Exit this Application?")
                        .setCancelText("No,Cancel")
                        .setConfirmText("Yes,Exit it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                *//*if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean("EXIT", false)) {
                                    finish();
                                }*//*


                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.cancel();
                            }
                        })
                        .show();
              //  Toast.makeText(getApplication(), "exit", Toast.LENGTH_LONG).show();



                *//*Intent i = new Intent(Registeration.this, Login.class);
                startActivity(i);*//*
            }
        });*/
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i = new Intent(Registeration.this, Login.class);
                startActivity(i);*/

                hname = HName.getText().toString();
                huserid = HUserid.getText().toString();
                hpassword = HPassword.getText().toString();
                hemail = HEmail.getText().toString();
                hconformp = HConformP.getText().toString();
                hphone = HPhone.getText().toString();
                hstreet = Hstreet.getText().toString();
                hcity = HCity.getText().toString();
                hstate = HState.getText().toString();
                hpostalcode = HPostalCode.getText().toString();


                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Registeration.this);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("hospital_name",hname);
                editor.commit();


                ///----------------------------------------------------------------------------------------------------------------

                if (!hname.isEmpty() &&
                        !huserid.isEmpty() &&
                        !hpassword.isEmpty() &&
                        !hemail.isEmpty() &&
                        !hconformp.isEmpty() &&
                        !hphone.isEmpty() &&
                        !hstreet.isEmpty() &&
                        !hcity.isEmpty() &&
                        !hstate.isEmpty() &&
                        !hpostalcode.isEmpty())
                {

                    if (hpassword.equals(hconformp))
                    {
                        new MyActivityAsync(hname, huserid, hpassword, hemail, hphone, hstreet, hcity, hstate, hpostalcode).execute();
                     }
                else
                    {
                        new SweetAlertDialog(Registeration.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("REQUEST ALERT!")
                                .setContentText("Sorry! Your Password does not match")
                                .setConfirmText("Ok")
                                .show();

                    }


                } else {

                    new SweetAlertDialog(Registeration.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("REQUEST ALERT!")
                            .setContentText("Please enter your details!")
                            .setConfirmText("Ok")
                            .show();
                            }
            }
        });


    }

    class MyActivityAsync extends AsyncTask<String, Void, String> {

        String hname, huserid, hpassword, hemail, hconformp, hphone, hstreet, hcity, hstate, hpostalcode;

        public MyActivityAsync(String hname,
                               String huserid,
                               String hpassword,
                               String hemail,
                               String hphone,
                               String hstreet,
                               String hcity,
                               String hstate,
                               String hpostalcode) {
            this.hname = hname;
            this.huserid = huserid;
            this.hpassword = hpassword;
            this.hemail = hemail;
            this.hphone = hphone;
            this.hstreet = hstreet;
            this.hcity = hcity;
            this.hstate = hstate;
            this.hpostalcode = hpostalcode;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new SweetAlertDialog(Registeration.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.getWindow().setLayout(1800, 400);
            pDialog.show();


        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";



            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("hospitalName", hname);
                jsonObject.accumulate("userName", huserid);
                jsonObject.accumulate("password", hpassword);
                jsonObject.accumulate("email", hemail);
                jsonObject.accumulate("phone", hphone);
                jsonObject.accumulate("hospitalAddress", hstreet);

                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest(URL_REGISTER, json);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            Log.d("tag", "<-----rerseres---->" + s);
            super.onPostExecute(s);

            pDialog.dismiss();
                           if (s == "") {

                    new SweetAlertDialog(Registeration.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Internet Connectivity very slow? try again")
                            .show();
                } else {


                try {
                    JSONObject jo = new JSONObject(s);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                    Log.d("tag", "<-----Status----->" + status);
                    if (status.equals("SUCCESS")) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Registeration.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("v_code", "12345");
                        editor.commit();
                        new SweetAlertDialog(Registeration.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("SUCCESS...")
                                .show();
                        Intent i = new Intent(getApplicationContext(), Login.class);
                        startActivity(i);
                    } else {
                         new SweetAlertDialog(Registeration.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("User already exist")
                                .show();
                                }
                            } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }





    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // This is the up button
            case android.R.id.home:

                new SweetAlertDialog(Registeration.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Exit Application")
                        .setContentText("Are you sure Exit this Application?")
                        .setCancelText("No, Cancel")
                        .setConfirmText("Yes,Exit it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                onRestart();
                                Intent i1 = new Intent(Intent.ACTION_MAIN);
                                i1.setAction(Intent.ACTION_MAIN);
                                i1.addCategory(Intent.CATEGORY_HOME);
                                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i1);
                                finish();


                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog)
                            {
                                sweetAlertDialog.cancel();
                            }
                        })
                        .show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}


