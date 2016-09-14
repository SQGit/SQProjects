package com.startemplan.vyuspot;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.balysv.materialripple.MaterialRippleLayout;
import com.devspark.robototextview.widget.RobotoTextView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class View_Needs_User extends Activity{

    MaterialRippleLayout mtp,mtp2;
    TextView tt;
    RobotoTextView txt1,txt2,txt3,txt4,txt5;
    public static String URL_REGISTER = "http://sqindia01.cloudapp.net/vyuspot/api/v1/spot/addCareUser";
    RippleView sndb;
    SweetAlertDialog pDialog;
    String token;
    String spotid,caredby,aa1,aa2,aa3,aa4,aa5,bb1,bb2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_needs_user);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(View_Needs_User.this);
        token= sharedPreferences.getString("token", "");
        Log.d("tag", token);

        mtp = (MaterialRippleLayout) findViewById(R.id.ripple);
        sndb = (RippleView) findViewById(R.id.more);


        txt1 = (RobotoTextView) findViewById(R.id.stext1);
        txt2 = (RobotoTextView) findViewById(R.id.stext2);
        txt3 = (RobotoTextView) findViewById(R.id.stext3);
        txt4 = (RobotoTextView) findViewById(R.id.stext4);
        txt5 = (RobotoTextView) findViewById(R.id.stext5);



       Bundle bundle = getIntent().getExtras();
         aa1 = bundle.getString("a1");
         aa2 = bundle.getString("a2");
         aa3 = bundle.getString("a3");
         aa4 = bundle.getString("a4");
         aa5 = bundle.getString("a5");
         bb1 = bundle.getString("b1");
        // bb2 = bundle.getString("b2");

        Log.e("tag",bb1);
       // Log.img_map("tag",bb2);

        spotid = bundle.getString("spot");

        Log.e("tagss",spotid);

        Log.e("tag1", aa1);
        Log.e("tag2", aa2);
        Log.e("tag3", aa3);
        Log.e("tag4", aa4);

        txt1.setText(aa1);
        txt2.setText(aa2);
        txt3.setText(aa3);
        txt4.setText(aa4);
        txt5.setText(aa5);


        mtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


        sndb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("tagspot",spotid);

                Log.e("tag1", aa1);
                Log.e("tag2", aa2);
                Log.e("tag3", aa3);
                Log.e("tag4", aa4);
                Log.e("tag4", aa5);
                Log.e("tag4", bb1);
              //  Log.img_map("tag4", bb2);

              new UploadSpotToServer().execute();

               // Toast.makeText(getApplicationContext(),"Your Details Shared to server",Toast.LENGTH_LONG).show();
               // finish();
            /*    Intent goDashboard = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(goDashboard);*/

            }
        });




    }








    class UploadSpotToServer extends AsyncTask<String, Void, String> {




        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("tag", "s1");

            pDialog = new SweetAlertDialog(View_Needs_User.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            String json = "", jsonStr = "";

            try {

                //{"location":"nungambakkam","type":"DISASTER","waterLevel":"5 feet","hashTags":"#flood","latitide":"13.0610677","longitude":"80.2404959"}
                // 3. build jsonObject
                JSONObject jsonObject = new JSONObject();


                jsonObject.accumulate("spotId", spotid);
                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest(URL_REGISTER, json,token);
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

            try {
                JSONObject jo = new JSONObject(s);

                String status = jo.getString("status");


                JSONObject jo1 = new JSONObject(s);

                String status1 = jo1.getString("data");

                if (status.equals("SUCCESS")) {


                        new SweetAlertDialog(View_Needs_User.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Message Alert")
                                .setContentText("Your Details Shared  Sucessfully")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                        sweetAlertDialog.cancel();
                                        Intent goDashboard = new Intent(getApplicationContext(), Dashboard.class);
                                        startActivity(goDashboard);
                                        finish();
                                    }
                                })
                                .show();


                        //Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();

                }else{

                    new SweetAlertDialog(View_Needs_User.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops!")
                            .setContentText("Check your internet or try again")
                            .setConfirmText("OK")
                            .show();
                             finish();
                    //  Toast.makeText(getActivity(), "Check your internet or try again", Toast.LENGTH_LONG).show();
                }



            }catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }


    }






















}
