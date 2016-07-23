package com.emergencysavior.emergencysavior;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.ViewGroup;

import com.jaeger.library.StatusBarUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by RSA on 04-06-2016.
 */
public class ScannerActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler {
    public static final String EXTRA_IS_TRANSPARENT = "is_transparent";
    private boolean isTransparent;
    String token;
    private ZBarScannerView mScannerView;
    SweetAlertDialog pDialog;
    String R_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isTransparent = getIntent().getBooleanExtra(EXTRA_IS_TRANSPARENT, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner_activity);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ScannerActivity.this);
        token = sharedPreferences.getString("Session_token", "");
        ///////////////////////////////// actionbar ///////////////////////////////////////////////////////////////////////////
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        SpannableStringBuilder SS = new SpannableStringBuilder(Html.fromHtml("<font color='#3c5899'><small><b>SCAN QRCODE</b></small> </font>"));
        SS.setSpan(new CustomTypefaceSpan("#3c5899", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);
        ////////////////////////////////////// actionbar ///////////////////////////////////////////////////////////////////////////

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZBarScannerView(this);
        contentFrame.addView(mScannerView);

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
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(300);
        r.play();


        String result = rawResult.getContents().toString();
        Log.e("tag", "content" + result);

        if (!result.equals("")) {
            R_content = rawResult.getContents().toString();
            if (Util.Operations.isOnline(ScannerActivity.this)) {
                if (!R_content.equals("")) {

                    new QrcodescannerAsync(R_content).execute();
                } else {

                    new SweetAlertDialog(ScannerActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("REQUEST ALERT?")
                            .setContentText("Please Try again")
                            .setConfirmText("ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent i = new Intent(getApplicationContext(), ScannerActivity.class);
                                    startActivity(i);
                                    sweetAlertDialog.dismiss();
                                }
                            })
                            .show();
                }

            } else {
                new SweetAlertDialog(ScannerActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Oops!")
                        .setContentText("Check Your Internet Connection!")
                        .setConfirmText("ok")
                        .show();

            }

        } else {
            new SweetAlertDialog(ScannerActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Try again?")
                    .setContentText("Something went wrong?")
                    .setConfirmText("ok")
                    .show();

        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview();
            }
        }, 2000);
    }

    private class QrcodescannerAsync extends AsyncTask<String, Void, String> {
        String str_r_content;

        public QrcodescannerAsync(String r_content) {
            this.str_r_content = r_content;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new SweetAlertDialog(ScannerActivity.this, SweetAlertDialog.PROGRESS_TYPE);
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
                jsonObject.put("device_id", R_content);


                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest("http://api.emergencysavior.com/webapi/emx/qrwear/mobile", json, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("tag", "Response from the server" + s);
            super.onPostExecute(s);
            pDialog.dismiss();

            if (s == "") {

                new SweetAlertDialog(ScannerActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();
            } else {
                try {
                    JSONObject jo = new JSONObject(s);
                    String status = jo.getString("status");
                    if (status.equals("success")) {
                        new SweetAlertDialog(ScannerActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Sucess !")
                                .setContentText("Now Android Watch Ready.........")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                    }
                                })
                                .show();


                    } else {
                        new SweetAlertDialog(ScannerActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Alert")
                                .setContentText("Please Try again")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent i = new Intent(getApplicationContext(), ScannerActivity.class);
                                        startActivity(i);
                                    }
                                })
                                .show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Log.d("tag", "<-----Status----->" + e.getMessage());
                    Log.d("tag", "<-----Status----->" + e.getStackTrace());
                    e.printStackTrace();
                }
            }
        }
    }
}

