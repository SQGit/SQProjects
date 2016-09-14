package com.startemplan.vyuspot;


import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;

import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class VoiceAct extends Activity {
    private static final String TAG = "AnimationStarter";
    ObjectAnimator textColorAnim;
    TextView recording;
    Button play, stop;
    ImageButton record;
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    SweetAlertDialog psDialog;
    String audioPath,token;

    ImageButton imgb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voiceact);

        play = (Button) findViewById(R.id.button3);
        stop = (Button) findViewById(R.id.button2);
        record = (ImageButton) findViewById(R.id.button);
        recording = (TextView) findViewById(R.id.textView2);

        imgb = (ImageButton) findViewById(R.id.bck);

        recording.setSelected(true);
        //recording.setVisibility(View.GONE);
        stop.setEnabled(false);
        play.setEnabled(false);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(VoiceAct.this);
        token = sharedPreferences.getString("token", "");

        Log.d("tag", token);



        imgb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mydir = new File(Environment.getExternalStorageDirectory() + "/Vyuspot/media/audios/");

        if (!mydir.exists())
            mydir.mkdirs();
        else
            Log.d("error", "dir. already exists");

        File video = new File(mydir, "audios" + timeStamp + ".3gp");
        audioPath = Uri.fromFile(video).toString().substring(7);
        Log.d("tag", audioPath);


        //outputFile = String.valueOf(mydir);
        //outputFile=Environment.getExternalStorageDirectory().getAbsolutePath()+"/recording.3gp";

        myAudioRecorder = new

                MediaRecorder();

        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(audioPath);
        Log.d("tag", audioPath);

        record.setOnClickListener(new View.OnClickListener()

                                  {
                                      @Override
                                      public void onClick(View v) {
                                          recording.setText("Recording...");
                                          // recording.setVisibility(View.VISIBLE);
                                          textColorAnim = ObjectAnimator.ofInt(recording, "textColor", Color.parseColor("#2196F3"), Color.parseColor("#F44336"));
                                          textColorAnim.setDuration(500);
                                          textColorAnim.setEvaluator(new ArgbEvaluator());
                                          textColorAnim.setRepeatCount(ValueAnimator.INFINITE);
                                          textColorAnim.setRepeatMode(ValueAnimator.REVERSE);
                                          textColorAnim.start();


                                          try {
                                              myAudioRecorder.prepare();
                                              myAudioRecorder.start();


                                          } catch (IllegalStateException e) {
                                              // TODO Auto-generated catch block
                                              e.printStackTrace();
                                          } catch (IOException e) {
                                              // TODO Auto-generated catch block
                                              e.printStackTrace();
                                          }

                                          record.setEnabled(false);
                                          stop.setEnabled(true);

                                          // Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
                                      }
                                  }

        );


        stop.setOnClickListener(new View.OnClickListener()

                                {
                                    @Override
                                    public void onClick(View v) {
                                        if (textColorAnim != null) {
                                            textColorAnim.cancel();
                                            recording.setText("Recorded Successfully");
                                            recording.setTextColor(Color.parseColor("#2196F3"));
                                        }
                                        myAudioRecorder.stop();
                                        myAudioRecorder.release();
                                        myAudioRecorder = null;


                                        stop.setEnabled(false);
                                        recording.setEnabled(true);
                                        play.setEnabled(true);

                                        //Toast.makeText(getApplicationContext(), "Audio recorded successfully", Toast.LENGTH_LONG).show();
                                    }
                                }

        );

        play.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) throws
                                            IllegalArgumentException, SecurityException, IllegalStateException {
                                        if (!isOnline(VoiceAct.this)) {

                                            Toast.makeText(getApplicationContext(), "Please check your connecton", Toast.LENGTH_LONG).show();


                                        } else {
                                            //Internet available. Do what's required when internet is available.

                                            new UploadAudioToServer().execute();


                                        }

                                    }
                                }

        );
    }


    public boolean isOnline(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni != null && ni.isConnected())
            return true;
        else
            return false;
    }


    private class UploadAudioToServer extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            psDialog = new SweetAlertDialog(VoiceAct.this, SweetAlertDialog.PROGRESS_TYPE);
            psDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            psDialog.setTitleText("Uploading Audio");
            psDialog.setCancelable(false);
            psDialog.show();

        }


        @Override
        protected String doInBackground(Void... params) {

            return uploadFile();

        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://sqindia01.cloudapp.net/vyuspot/api/v1/spot/addAudio");
            httppost.setHeader("sessionToken", token);

            try {
                MultipartEntity entity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);
                File sourceFile = new File(audioPath);
                entity.addPart("fileUpload", new FileBody(sourceFile));
                httppost.setEntity(entity);
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("tag", "Response from server: " + result);

            psDialog.dismiss();


            try {
                JSONObject jo = new JSONObject(result);

                String status = jo.getString("status");

                Log.d("tag", "<-----Status----->" + status);


                if (status.equals("SUCCESS")) {

                    new SweetAlertDialog(VoiceAct.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Message Alert")
                            .setContentText("Your audio sent to emergency team")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.cancel();
                                    Intent goD = new Intent(getApplicationContext(), Dashboard.class);
                                    startActivity(goD);
                                    finish();


                                }
                            })
                            .show();

                }

                else if (status.equals("SUCCESS")) {

                    new SweetAlertDialog(VoiceAct.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Message Alert")
                            .setContentText("Network Error, Try again later")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.cancel();
                                    Intent goD = new Intent(getApplicationContext(), Dashboard.class);
                                    startActivity(goD);
                                    finish();


                                }
                            })
                            .show();

                }
                else {

                    new SweetAlertDialog(VoiceAct.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops!")
                            .setContentText("Check your internet or try again")
                            .setConfirmText("OK")
                            .show();
                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }

    }


    private class UploadImageToServer extends AsyncTask<Void, Integer, String> {

        SweetAlertDialog psDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            psDialog = new SweetAlertDialog(VoiceAct.this, SweetAlertDialog.PROGRESS_TYPE);
            psDialog.show();
            psDialog.setTitle("Loading");

        }


        @Override
        protected String doInBackground(Void... params) {

            return uploadFile();

        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://sqindia01.cloudapp.net/vyuspot/api/v1/spot/addaudio");
            httppost.setHeader("sessionToken", "61b4d6fdf60218cdb2f3f27a72e4edcf");
            httppost.setHeader("Content-type", "application/x-www-form-urlencoded");

            try {
                MultipartEntity entity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);

                File sourceFile = new File(outputFile);

                // Adding file data to http body
                entity.addPart("fileUpload", new FileBody(sourceFile));

                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();
                Log.d("tags", outputFile);

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                    Log.d("tagss", responseString);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                    Log.d("tagss", responseString);
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("tago", "Response from server: " + result);
            Log.d("tago", outputFile);

            psDialog.dismiss();

            //Toast.makeText(getApplicationContext(),"Add Successfully",Toast.LENGTH_LONG).show();
            // showing the server response in an alert dialog


            super.onPostExecute(result);
        }

    }


    class UploadFileToServer2 extends AsyncTask<String, Void, String> {

        SweetAlertDialog pdialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new SweetAlertDialog(VoiceAct.this, SweetAlertDialog.PROGRESS_TYPE);
            pdialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            pdialog.setTitleText("Loading");
            pdialog.setCancelable(false);
            pdialog.show();

        }


        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;


            try {

                HttpClient client = new DefaultHttpClient();


                // HttpPost postMethod = new HttpPost("http://sqindia01.cloudapp.net/vyuspot/api/v1/spot/addAccidentVideo");

                HttpPost httppost = new HttpPost("http://sqindia01.cloudapp.net/vyuspot/api/v1/spot/addAudio");
                httppost.setHeader("sessionToken", "61b4d6fdf60218cdb2f3f27a72e4edcf");
                // postMethod.addHeader("spotId", "00554");
                // File file = new File(selectedImagePath);
                // postMethod.addHeader("spotId", "201");
                Log.d("log", "" + outputFile);
                File files = new File(outputFile);
                MultipartEntity entity = new MultipartEntity();
                FileBody contentFiles = new FileBody(files);
                entity.addPart("fileUpload", contentFiles);
                httppost.setEntity(entity);
                HttpResponse response = client.execute(httppost);
                HttpEntity r_entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                Log.d("log", "" + responseString);
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                    Log.d("log", "" + responseString);
                } else {
                    responseString = "Error occurred! Http Status Code: " + statusCode;
                    Log.d("log", "" + responseString);
                }
            } catch (IOException e) {
                responseString = e.toString();
            }
            return responseString;
        }


        @Override
        protected void onPostExecute(String s) {

            pdialog.dismiss();

            Log.d("tag", s);

            //Toast.makeText(getApplicationContext(),"finishedddddddddddddd.........",Toast.LENGTH_LONG).show();
        }

    }


}