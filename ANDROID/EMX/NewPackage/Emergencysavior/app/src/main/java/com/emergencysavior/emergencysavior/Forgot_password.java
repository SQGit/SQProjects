package com.emergencysavior.emergencysavior;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
 * Created by RSA on 03-05-2016.
 */
public class Forgot_password extends AppCompatActivity {
    TextView txt_tittle,txt_powerby;
    EditText edt_user_id;
    String str_user_id;
    Button btn_send;
    ProgressDialog mProgressDialog;
    Typeface tf;
    ImageButton img_back;
    public static final String EXTRA_IS_TRANSPARENT = "is_transparent";
    private boolean isTransparent;
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isTransparent = getIntent().getBooleanExtra(EXTRA_IS_TRANSPARENT, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        txt_powerby = (TextView) findViewById(R.id.dss);
        edt_user_id = (EditText) findViewById(R.id.Edt_user_id);
        txt_tittle = (TextView) findViewById(R.id.txt_tittle);
        btn_send = (Button) findViewById(R.id.btn_send);
        img_back = (ImageButton) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
        });
        edt_user_id.setTypeface(tf);
        txt_tittle.setTypeface(tf);
        btn_send.setTypeface(tf);
        txt_powerby.setTypeface(tf);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_user_id = edt_user_id.getText().toString().trim();

                if (Util.Operations.isOnline(Forgot_password.this)) {
                    if ((!str_user_id.isEmpty())) {
                        new forgotAsync(str_user_id).execute();
                    } else {
                        new SweetAlertDialog(Forgot_password.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("ALERT")
                                .setContentText("Please Enter  Email")
                                .setConfirmText("ok")
                                .show();
                    }

                } else {
                    new SweetAlertDialog(Forgot_password.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("ALERT")
                            .setContentText("No Internet Connectivity")
                            .show();

                }

            }
        });
        setStatusBar();
    }
    protected void setStatusBar() {
        if (isTransparent) {
            StatusBarUtil.setTransparent(this);
        } else {
            StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    private class forgotAsync extends AsyncTask<String, Void, String> {
        String str_user_id;

        public forgotAsync(String str_user_id) {
            this.str_user_id = str_user_id;
        }

        protected void onPreExecute() {

            super.onPreExecute();
            mProgressDialog = new ProgressDialog(Forgot_password.this);
            mProgressDialog.setMessage("Please wait ");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            // Show progressdialog
            mProgressDialog.show();


        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("email", str_user_id);
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return jsonStr = HttpUtils.makeRequest(CONFIG.URL + "/forgotpwd", json);
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

                new SweetAlertDialog(Forgot_password.this, SweetAlertDialog.ERROR_TYPE)
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
                        new SweetAlertDialog(Forgot_password.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("ALERT")
                                .setContentText("Password reset Email sent successfully")
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
                        new SweetAlertDialog(Forgot_password.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("ALERT")
                                .setContentText("Email Id is Invalid and not found in server")
                                .setConfirmText("ok")
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
}
