package com.emerengencysavior.emergencysavior;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by RSA on 2/23/2016.
 */
public class Signup extends Activity {
    public static final String EXTRA_IS_TRANSPARENT = "is_transparent";
    private boolean isTransparent;
    TextView txt_tittle, txt_powerby, txt_policy;
    EditText input_First_Name, input_Last_Name, input_Email, input_Password, input_Conform_Password, activate_mac;
    String first_name, last_name, email, password, confirm_password,mac;
    Button btn_activate;
    ImageButton img_btn_back;
    ProgressDialog mProgressDialog;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    Typeface tf,tf1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isTransparent = getIntent().getBooleanExtra(EXTRA_IS_TRANSPARENT, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedpreferences.edit();
        edit.remove("audiostatus");
        edit.remove("gpsstatus");
        edit.remove("messagestatus");
        edit.remove("emailstatus");
        edit.putString("audiostatus", "true");
        edit.putString("gpsstatus", "true");
        edit.putString("messagestatus", "true");
        edit.putString("emailstatus", "true");
        edit.commit();

        tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        tf1 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.otf");

        txt_tittle = (TextView) findViewById(R.id.tittle);
        txt_powerby = (TextView) findViewById(R.id.dss);
        txt_policy = (TextView) findViewById(R.id.textView);

        input_First_Name = (EditText) findViewById(R.id.First_Name);
        input_Last_Name = (EditText) findViewById(R.id.Last_Name);
        input_Email = (EditText) findViewById(R.id.Email);
        input_Password = (EditText) findViewById(R.id.Password);
        input_Conform_Password = (EditText) findViewById(R.id.Confirm_Password);
        activate_mac = (EditText) findViewById(R.id.edt_mac);

        btn_activate = (Button) findViewById(R.id.btn_activate);
        img_btn_back= (ImageButton) findViewById(R.id.img_back);
        input_First_Name.setTypeface(tf);
        input_Last_Name.setTypeface(tf);
        input_Email.setTypeface(tf);
        input_Password.setTypeface(tf);
        input_Conform_Password.setTypeface(tf);
        txt_tittle.setTypeface(tf1);
        txt_powerby.setTypeface(tf);
        txt_policy.setTypeface(tf);
        activate_mac.setTypeface(tf);
        btn_activate.setTypeface(tf);


        btn_activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                first_name = input_First_Name.getText().toString().trim();
                last_name = input_Last_Name.getText().toString().trim();
                email = input_Email.getText().toString().trim();
                password = input_Password.getText().toString().trim();
                confirm_password = input_Conform_Password.getText().toString().trim();
                mac = activate_mac.getText().toString().trim();


                if (Util.Operations.isOnline(Signup.this)) {

                    if ((!first_name.isEmpty()) && (!last_name.isEmpty()) &&
                            (!email.isEmpty()) &&
                            (!password.isEmpty()) &&
                            (!confirm_password.isEmpty()) &&
                            (!mac.isEmpty())) {
                        if (password.equals(confirm_password)) {
                            new MyActivityAsync(first_name, last_name, email, password, mac).execute();
                        } else {
                            new SweetAlertDialog(Signup
                                    .this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("ALERT")
                                    .setContentText("Please Check Password Fields")
                                    .show();
                        }


                    } else {
                        new SweetAlertDialog(Signup
                                .this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("ALERT")
                                .setContentText("Please Enter all the Fields")
                                .show();
                    }
                } else {
                    new SweetAlertDialog(Signup
                            .this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("ALERT")
                            .setContentText("No Internet Connectivity")
                            .show();

                }

            }
        });
        img_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });
        setStatusBar();

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    protected void setStatusBar() {
        if (isTransparent) {
            StatusBarUtil.setTransparent(this);
        } else {
            StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
        }
    }

    class MyActivityAsync extends AsyncTask<String, Void, String> {
        String first_name, last_name, email, password, mac;

        public MyActivityAsync(String first_name,
                               String last_name,
                               String email,
                               String password,
                               String mac) {
            String json = "", jsonStr = "";
            this.first_name = first_name;
            this.last_name = last_name;
            this.email = email;
            this.password = password;
            this.mac = mac;


        }

        protected void onPreExecute() {

            super.onPreExecute();

            mProgressDialog = new ProgressDialog(Signup.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Registering..");
            // Set progressdialog message
            mProgressDialog.setMessage("Please wait");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            // Show progressdialog
            mProgressDialog.show();


        }


        @Override
        protected String doInBackground(String... params) {
            Log.d("input Name", first_name);
            Log.d("last_name", last_name);
            Log.d("email", email);
            Log.d("password", password);
            Log.d("mac", mac);
            String json = "", s = "";


            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.accumulate("firstname", first_name);
                jsonObject.accumulate("lastname", last_name);
                jsonObject.accumulate("email", email);
                jsonObject.accumulate("password", password);
                jsonObject.accumulate("mac", mac);
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return s = HttpUtils.makeRequest(CONFIG.URL+"/signup", json);
            } catch (JSONException e) {

                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("json result", s);

            mProgressDialog.dismiss();

            if (s == "") {

                new SweetAlertDialog(Signup.this, SweetAlertDialog.ERROR_TYPE)
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
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Signup.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("activation_code", "12345");
                        editor.putString("mac", mac);
                        editor.commit();
                        Intent i = new Intent(getApplicationContext(), Login.class);
                        startActivity(i);
                    } else {
                        new SweetAlertDialog(Signup.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("ALERT")
                                .setContentText("Email already exists")
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

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }



}
