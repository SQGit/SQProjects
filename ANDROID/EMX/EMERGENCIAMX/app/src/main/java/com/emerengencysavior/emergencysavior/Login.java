package com.emerengencysavior.emergencysavior;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import at.markushi.ui.CircleButton;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by RSA on 2/25/2016.
 */
public class Login extends Activity {
    TextView tv, tv1, forgotPassword, or,txt_forgot;
    private EditText inputEmail, inputPassword;
    private Button signup, activate;
    CircleButton login;
    String email, password, confirm_password, mac;
    SweetAlertDialog pDialog;
    ImageButton back;
    Typeface tf, tf1;

    ProgressDialog mProgressDialog;
    public static final String EXTRA_IS_TRANSPARENT = "is_transparent";
    private boolean isTransparent;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    private LinearLayout mRootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isTransparent = getIntent().getBooleanExtra(EXTRA_IS_TRANSPARENT, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> A C T I O N B A R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");

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

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
        String activation_code = sharedPreferences.getString("activation_code", "");
        final String Login = sharedPreferences.getString("Session_token", "");
        mac = sharedPreferences.getString("mac", "");
        tf1 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.otf");
        tv = (TextView) findViewById(R.id.dss);
        tv1 = (TextView) findViewById(R.id.login);
        inputEmail = (EditText) findViewById(R.id.input_Email);
        inputPassword = (EditText) findViewById(R.id.input_Password);
        forgotPassword = (TextView) findViewById(R.id.forgot);
        or = (TextView) findViewById(R.id.OR);
        activate = (Button) findViewById(R.id.btn_activate);
        signup = (Button) findViewById(R.id.btn_sign_up);
        login = (CircleButton) findViewById(R.id.btn_login);
        back = (ImageButton) findViewById(R.id.img_back);

        txt_forgot=(TextView) findViewById(R.id.forgot);

        tv.setTypeface(tf);
        tv1.setTypeface(tf1);
        inputEmail.setTypeface(tf);
        inputPassword.setTypeface(tf);
        forgotPassword.setTypeface(tf);
        or.setTypeface(tf);
        signup.setTypeface(tf);
        activate.setTypeface(tf);
        txt_forgot.setTypeface(tf);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
            }
        });
        txt_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Forgot_password.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(com.emerengencysavior.emergencysavior.Login.this)
                        .setTitle("ALERT")
                        .setMessage("Are you sure you want to exit")
                        .setNegativeButton(android.R.string.no, null)
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                com.emerengencysavior.emergencysavior.Login.super.onBackPressed();

                                Intent i1 = new Intent(Intent.ACTION_MAIN);
                                i1.setAction(Intent.ACTION_MAIN);
                                i1.addCategory(Intent.CATEGORY_HOME);
                                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i1);
                                finish();
                            }
                        }).create().show();

            }
        });
        activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "On progress", Toast.LENGTH_LONG).show();

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(inputPassword.getWindowToken(), 0);


                if (Util.Operations.isOnline(com.emerengencysavior.emergencysavior.Login.this)) {

                    if ((!email.isEmpty()) && (!password.isEmpty())) {
                        new MyActivityAsync(email, password, mac).execute();
                    } else {
                        new SweetAlertDialog(com.emerengencysavior.emergencysavior.Login.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("ALERT")
                                .setContentText("Please Enter Username and Password")
                                .setConfirmText("ok")
                                .show();


                    }
                } else {

                    new SweetAlertDialog(com.emerengencysavior.emergencysavior.Login.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("ALERT")
                            .setContentText("No Internet Connectivity")
                            .show();

                    //  Toast.makeText(getApplicationContext(), "No Internet Connectivity", Toast.LENGTH_SHORT).show();

                }


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

        String email, password, mac;

        public MyActivityAsync(String email, String password, String mac) {
            String json = "", jsonStr = "";
            this.email = email;
            this.password = password;
            this.mac = mac;


        }

        protected void onPreExecute() {

            super.onPreExecute();
            mProgressDialog = new ProgressDialog(Login.this);
            mProgressDialog.setMessage("Please wait ");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            // Show progressdialog
            mProgressDialog.show();


        }

        protected String doInBackground(String... params) {
            Log.d("tag", "email===>" + email);
            Log.d("tag", "password===>" + password);
            Log.d("tag", "mac===>" + mac);
            String json = "", jsonStr = "";
            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("email", email);
                jsonObject.accumulate("password", password);
                jsonObject.accumulate("mac", "1234");
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return jsonStr = HttpUtils.makeRequest(CONFIG.URL + "/signin", json);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            super.onPostExecute(jsonStr);
            Log.d("tag", "<-----json result---->" + jsonStr);

            mProgressDialog.dismiss();
            if (jsonStr.equals("")) {

                new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("ALERT")
                        .setContentText("Internet Connectivity very slow? try again")
                        .setConfirmText("ok")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent i = new Intent(getApplicationContext(), Login.class);
                                startActivity(i);

                            }
                        })
                        .show();
            } else {

                try {
                    JSONObject result = new JSONObject(jsonStr);
                    String status = result.getString("status");
                    if (status.equals("success")) {
                        String session_token = result.getString("session_token");
                        String email = result.getString("email");
                        String firstname = result.getString("firstname");
                        String lastname = result.getString("lastname");
                        String password = result.getString("password");
                        String residential_phone = result.getString("residential_phone");
                        String mobile = result.getString("mobile");
                        String dob = result.getString("dob");
                        String gender = result.getString("gender");
                        String address = result.getString("address");
                        String city = result.getString("city");
                        String state = result.getString("state");
                        String zipcode = result.getString("zipcode");
                        final String image_url = result.getString("image_url");
                        Log.d("tag", status + "," + session_token + "," + firstname + "," + email + "," + lastname + "," + password + "," + residential_phone + "," + mobile + "," + dob + "," + gender);


                        SharedPreferences s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor edit = s_pref.edit();
                        // edit.clear();
                        edit.remove("Firstname");
                        edit.remove("Lastname");
                        edit.remove("Password");
                        edit.remove("Residential_phone");
                        edit.remove("Mobile");
                        edit.remove("Dob");
                        edit.remove("Gender");
                        edit.remove("Address");
                        edit.remove("City");
                        edit.remove("State");
                        edit.remove("Zipcode");

                        edit.putString("Email", email);
                        edit.putString("Session_token", session_token);
                        edit.putString("Firstname", firstname);
                        edit.putString("Lastname", lastname);
                        edit.putString("Password", password);
                        edit.putString("Residential_phone", residential_phone);
                        edit.putString("Mobile", mobile);
                        edit.putString("Dob", dob);
                        edit.putString("Gender", gender);
                        edit.putString("Address", address);
                        edit.putString("City", city);
                        edit.putString("State", state);
                        edit.putString("Zipcode", zipcode);
                        edit.putString("Image_url", image_url);
                        edit.commit();
                        onRestart();
                        if (image_url.equals("null")) {

                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        } else {
                            new DownloadImage().execute(image_url);
                        }


                    }else {
                        new SweetAlertDialog(Login.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("ALERT")
                                .setConfirmText("Try again")
                                .setContentText("Incorrect Username or Password")

                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent i = new Intent(getApplicationContext(), Login.class);
                                        startActivity(i);
                                    }
                                })
                                .show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }


    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> D O W N L O A D  I M A G E >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(Login.this);
            mProgressDialog.setMessage("Please wait ...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {

            Log.d("tag", "bitmap result start" + result);

                SharedPreferences s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = s_pref.edit();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                result.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                String encodedString = Base64.encodeToString(b, Base64.DEFAULT);
                Log.d("tag", "encodedString_from_image=>" + encodedString);
                edit.remove("zxc");
                edit.putString("zxc", encodedString);
                edit.commit();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                mProgressDialog.dismiss();
            }
    }
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onBackPressed() {

    }
}