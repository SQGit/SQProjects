package www.sqindia.net.ilerasoftadmin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ANANDH on 12-12-2015.
 */
public class Login extends AppCompatActivity {

    public static String URL_REGISTER = "http://sqindia01.cloudapp.net/cologix/api/v1/user/login";
    Button submit;
    TextView exit;
    EditText user_id, Password;
    String user_Name, user_Password;
    String token;
    JSONObject jsonObject, jsonKey;
    SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'><big><b>LOGIN</b></big> </font>"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xcececece));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.bck_arrow);

        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

        user_id = (EditText) findViewById(R.id.userid);
        Password = (EditText) findViewById(R.id.password);
        // exit = (TextView) findViewById(R.id.back_iv);
        submit = (Button) findViewById(R.id.submited);
      /*  exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Registeration.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
               *//* Intent i = new Intent(Login.this, Registeration.class);
                startActivity(i);*//*
            }
        });*/
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                user_Name = user_id.getText().toString();
                user_Password = Password.getText().toString();
                Log.d("input Name", user_Name);
                Log.d("input password", user_Password);

                ///----------------------------------------------------------------------------------------------------------------

                if (Util.Operations.isOnline(Login.this)) {

                    if ((!user_Name.isEmpty()) && (!user_Password.isEmpty())) {
                        // When Email entered is Valid
                        new MyActivityAsync(user_Name, user_Password).execute();
                    } else {
                        new SweetAlertDialog(Login.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("REQUEST ALERT?")
                                .setContentText("Please enter your details!")
                                .setConfirmText("ok")
                                .show();
                        //   Toast.makeText(getApplicationContext(), "Fields Invalid!!", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("No Internet Connectivity")
                            .show();
                    //  Toast.makeText(getApplicationContext(), "No Internet Connectivity", Toast.LENGTH_SHORT).show();

                }

            }
            ///----------------------------------------------------------------------------------------------------------------

        });
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // This is the up button
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), Registeration.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
                //NavUtils.navigateUpFromSameTask(this);
                // overridePendingTransition(R.animator.anim_left, R.animator.anim_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    class MyActivityAsync extends AsyncTask<String, Void, String> {

        String user_Name, user_Password;

        public MyActivityAsync(String user_Name, String user_Password) {
            String json = "", jsonStr = "";
            this.user_Name = user_Name;
            this.user_Password = user_Password;


        }

        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = new SweetAlertDialog(Login.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.getWindow().setLayout(1800, 400);
            pDialog.show();


        }

        protected String doInBackground(String... params) {
            Log.d("input Name", user_Name);
            Log.d("input password", user_Password);
            String json = "", jsonStr = "";
            try {


                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("userName", user_Name);
                jsonObject.accumulate("password", user_Password);
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return jsonStr = HttpUtils.makeRequest(URL_REGISTER, json);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.d("tag", "<-----rerseres---->" + jsonStr);
            super.onPostExecute(jsonStr);
            pDialog.dismiss();
            if (jsonStr == "") {

                new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();
            } else {

                try {
                    JSONObject jo = new JSONObject(jsonStr);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                    Log.d("tag", "<-----Status----->" + status);
                    //      JSONObject data = jo.getJSONObject("data");

                    if (status.equals("SUCCESS")) {
                        JSONObject data = jo.getJSONObject("data");
                        token = data.getString("sessionToken");
                        Log.d("tag", "<-----Send sessionToken----->" + token);

                        SharedPreferences s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor edit = s_pref.edit();
                        edit.putString("TOKEN", token);
                        edit.putString("login", "1234");
                        edit.commit();
                        pDialog.dismiss();
                        new SweetAlertDialog(Login.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("SUCCESS MESSAGE!!!")
                                .setConfirmText("Ok")
                                .setContentText("Login Succesfully...")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent i = new Intent(getApplicationContext(), Dashboard.class);
                                        startActivity(i);
                                    }
                                })
                                .show();


                    } else {
                        pDialog.dismiss();

                        new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("ERROR MESSAGE!!!")
                                .setContentText("Incorrect UserName & Password...")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.cancel();
                                    }
                                })
                                .show();

                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


}
