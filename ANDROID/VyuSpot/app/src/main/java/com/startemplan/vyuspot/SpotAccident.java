package com.startemplan.vyuspot;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;

import com.rey.material.widget.EditText;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by User on 21-12-2015.
 */
public class SpotAccident extends Activity {
    public static final int MEDIA_TYPE_VIDEO = 2;
    // public static final String PHOTO_URL = "http://sqindia01.cloudapp.net/vyuspot/api/v1/spot/addAccidentPhoto";
    static final int DIALOG_ERROR_CONNECTION = 1;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    public static SpotAccident ActivityContext = null;
    public static String URL_REGISTER = "http://sqindia01.cloudapp.net/vyuspot/api/v1/spot/add";
    ScrollView scr;
    ImageButton back;
    EditText name, locationname, landmark, tag, contactno;
    ImageButton fbb, fbs;
    String cameraImagePath = null, videopath = null;

    String token, uname;

    GpsT gps;
    String locationAddress, Name, Locationname, Landmark, Tag, Contactno, latitide, longitude;
    TextView ltlg, latlong, latt, samp;
    ImageView get_photo;
    //VideoView get_video;
    FrameLayout fl;
    double dlatitude, dlongitude;
    FrameLayout frml;
    Button photo, send, loc, video;
    MaterialRippleLayout mtp;

    String spotid;

    public static final int ACTION_CAMERA = 0, CAMERA_GALLARY = 1, ACTION_VIDEO = 2, VIDEO_GALLERY = 3;

    SweetAlertDialog pDialog;
    public ProgressDialog dialog;
    SweetAlertDialog psDialog;


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.spot_accident);
        Log.d("tag", "accident");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SpotAccident.this);
        token = sharedPreferences.getString("token", "");
        uname = sharedPreferences.getString("name", "");
        Log.d("tag", token);


        if (token.length() < 5) {

            new SweetAlertDialog(SpotAccident.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Warning")
                    .setContentText("You Have to Register First!!!")
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            sweetAlertDialog.cancel();
                            Intent goD = new Intent(getApplicationContext(), Register.class);
                            startActivity(goD);


                        }
                    })
                    .show();

        }


        ActivityContext = this;
        //image button
        loc = (Button) findViewById(R.id.loc_but);
        back = (ImageButton) findViewById(R.id.bckbtn);
        latt = (TextView) findViewById(R.id.lattx);
        //samp = (TextView) findViewById(R.id.txts);
        //edit text
        name = (EditText) findViewById(R.id.namem);
        name.setText(uname);

        locationname = (EditText) findViewById(R.id.locatm);
        landmark = (EditText) findViewById(R.id.landm);
        tag = (EditText) findViewById(R.id.tagm);
        contactno = (EditText) findViewById(R.id.contm);
        //or = (TextView) findViewById(R.id.or);
        latlong = (TextView) findViewById(R.id.latlongt);

        //frame layout
        frml = (FrameLayout) findViewById(R.id.frme_lyt);

        //button
        photo = (Button) findViewById(R.id.poto);
        send = (Button) findViewById(R.id.send);
        video = (Button) findViewById(R.id.vido);

        //fbs= (ImageButton) findViewById(R.id.fbss);
        fbb = (ImageButton) findViewById(R.id.fbs);

        // snd = (Button) findViewById(R.id.send2);
        //textview
        // ltlg = (TextView) findViewById(R.id.latlongt);

        //imageview
        get_photo = (ImageView) findViewById(R.id.imageView);

        scr = (ScrollView) findViewById(R.id.scrooll);
        mtp = (MaterialRippleLayout) findViewById(R.id.ripple);

        //get_video = (VideoView) findViewById(R.id.videoView);

        fl = (FrameLayout) findViewById(R.id.frame_photo);
//scr.pageScroll(View.FOCUS_UP);

        //scr.pageScroll(View.FOCUSABLES_ALL);


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/robol.ttf");

        send.setTypeface(tf);
        photo.setTypeface(tf);
        //video.setTypeface(tf);
        loc.setTypeface(tf);

        // or.setTypeface(tf);

        latt.setTypeface(tf);
        latlong.setTypeface(tf);
        name.setTypeface(tf);
        locationname.setTypeface(tf);
        landmark.setTypeface(tf);
        tag.setTypeface(tf);
        contactno.setTypeface(tf);


        String[] countries = {"flood", "chennaiflood", "sunami", "chennaiflood", "chennaidrought", "chennai", "kadalur", "vilupuram", "thanjavur", "nagapatinam", "pondicheri"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spintext, R.id.stext, countries);
        tag.setAdapter(adapter1);


        name.requestFocus();

        get_photo.setVisibility(View.GONE);
        // get_video.setVisibility(View.GONE);
        fl.setVisibility(View.GONE);


        mtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                /*Intent goDashboard = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(goDashboard);
*/
            }
        });


        if (!isOnline(this)) {
            showDialog(DIALOG_ERROR_CONNECTION); //displaying the created dialog.
        } else {
            //Internet available. Do what's required when internet is available.


            gps = new GpsT(SpotAccident.this);
            if (gps.canGetLocation()) {
                dlatitude = gps.getLatitude();
                dlongitude = gps.getLongitude();
                String lati = String.valueOf(dlatitude);
                String longi = String.valueOf(dlongitude);
                // String aa = gps.getAddress();
//            Log.img_view("tag", aa);
                String Addr = gps.getAddress();
                locationname.setText(Addr);
                latlong.setText("Latitude:" + lati + "\nLongitude:\t" + longi);
            } else {
                //SplashScreen sps = new SplashScreen();
                // sps.buildAlertMessageNoGps();
                //gps.showSettingsAlert();
            }


        }


        photo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                selectImage();
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectVideo();
            }
        });



       /* snd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // new UploadVideoToServer().execute();
            }
        });*/


        fbb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Name = name.getText().toString();
                Locationname = locationname.getText().toString();
                Landmark = landmark.getText().toString();
                Tag = tag.getText().toString();
                Contactno = contactno.getText().toString();
                latitide = String.valueOf(dlatitude);
                longitude = String.valueOf(dlongitude);

                String share = Locationname + " Near " + Landmark + " " + "#" + Tag + " Contact: " + Contactno + " #vyuspot" + " " + "#startemplan";


                String tweetUrl = String.format("https://twitter.com/intent/tweet?text=%s&url=%s",
                        urlEncode("Accident @"),
                        urlEncode(share));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));

                List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
                for (ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter")) {
                        intent.setPackage(info.activityInfo.packageName);
                    }
                }

                startActivity(intent);
                finish();


            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // new UploadImageToServer().execute();


                gps = new GpsT(SpotAccident.this);
                dlatitude = gps.getLatitude();
                dlongitude = gps.getLongitude();


                Name = name.getText().toString();
                Locationname = locationname.getText().toString();
                Landmark = landmark.getText().toString();
                Tag = tag.getText().toString();
                Contactno = contactno.getText().toString();
                latitide = String.valueOf(dlatitude);
                longitude = String.valueOf(dlongitude);


                if (!isOnline(SpotAccident.this)) {

                    showDialog(DIALOG_ERROR_CONNECTION);


                } else {


                    if (!Name.isEmpty() && !Locationname.isEmpty() && !Landmark.isEmpty() && !Tag.isEmpty() && !Contactno.isEmpty()) {


                        new UploadDataToServer(Name, Locationname, Landmark, Tag, Contactno, latitide, longitude).execute();


                    } else {

                        new SweetAlertDialog(SpotAccident.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Alert")
                                .setContentText("Please enter your details! ")
                                .setConfirmText("OK")
                                .show();
                    }
                }


            }
        });


        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                gps = new GpsT(SpotAccident.this);


                if (gps.canGetLocation()) {

                    dlatitude = gps.getLatitude();
                    dlongitude = gps.getLongitude();
                    String lati = String.valueOf(dlatitude);
                    String longi = String.valueOf(dlongitude);

                    String Addr = gps.getAddress();
                    locationname.setText(Addr);
                    latlong.setText("Latitude:\t" + lati + "\nLongitude:\t" + longi);

                } else {
                    gps.showSettingsAlert();
                }


            }
        });


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

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DIALOG_ERROR_CONNECTION:
                AlertDialog.Builder errorDialog = new AlertDialog.Builder(this);
                errorDialog.setTitle("Error");
                errorDialog.setMessage("No internet connection.");

                errorDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        //finish();
                    }
                });

                errorDialog.setNeutralButton("Cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent goD = new Intent(getApplicationContext(), Dashboard.class);
                                startActivity(goD);
                                //finish();
                                //dialog.dismiss();
                            }
                        });

                AlertDialog errorAlert = errorDialog.create();
                return errorAlert;

            default:
                break;
        }
        return dialog;
    }


    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.d("tag", "UTF-8 should always be supported");
            throw new RuntimeException("URLEncoder.encode() failed for " + s);
        }
    }


    private void selectImage() {


        final CharSequence[] items = {"Take Photo", "Choose from Gallery",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(SpotAccident.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {

                    fl.setVisibility(View.VISIBLE);
                    get_photo.setVisibility(View.VISIBLE);
                    Intent takecamera = new Intent("android.media.action.IMAGE_CAPTURE");
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    File mydir = new File(Environment.getExternalStorageDirectory() + "/Vyuspot/media/images/");

                    if (!mydir.exists())
                        mydir.mkdirs();
                    else
                        Log.d("error", "dir. already exists");

                    File image = new File(mydir, "images" + timeStamp + ".png");
                    takecamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
                    cameraImagePath = Uri.fromFile(image).toString().substring(7);
                    Log.d("tag1", "" + cameraImagePath);
                    //samp.setText(cameraImagePath);
                    startActivityForResult(takecamera, ACTION_CAMERA);


                } else if (items[item].equals("Choose from Gallery")) {

                    fl.setVisibility(View.VISIBLE);

                    get_photo.setVisibility(View.VISIBLE);

                    Intent cameragalary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(cameragalary, CAMERA_GALLARY);


                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void selectVideo() {


        final CharSequence[] items = {"Take Video", "Choose from Gallery",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(SpotAccident.this);
        builder.setTitle("Add Video!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Video")) {

                    get_photo.setVisibility(View.GONE);
                    fl.setVisibility(View.GONE);

                    Intent takevideo = new Intent("android.media.action.VIDEO_CAPTURE");
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    File mydir = new File(Environment.getExternalStorageDirectory() + "/Vyuspot/media/videos/");

                    if (!mydir.exists())
                        mydir.mkdirs();
                    else
                        Log.d("error", "dir. already exists");

                    File video = new File(mydir, "videos" + timeStamp + ".3gp");
                    takevideo.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(video));
                    videopath = Uri.fromFile(video).toString().substring(7);
                    Log.d("tag1", "" + videopath);
                    startActivityForResult(takevideo, ACTION_VIDEO);

                } else if (items[item].equals("Choose from Gallery")) {

                    get_photo.setVisibility(View.GONE);
                    fl.setVisibility(View.GONE);

                    Intent intent = new Intent();
                    intent.setType("video/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select img_emg Video "), VIDEO_GALLERY);


                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == ACTION_CAMERA) {
                get_photo.setImageBitmap(BitmapFactory.decodeFile(cameraImagePath));
                Log.d("tag2", "path" + cameraImagePath);
            } else if (requestCode == CAMERA_GALLARY) {
                onSelectFromGalleryResult(data);
            } else if (requestCode == ACTION_VIDEO) {
                // samp.setText(videopath);
                Log.d("tag2", videopath);
            } else if (requestCode == VIDEO_GALLERY) {
                Uri videouri = data.getData();
                videopath = getVPath(videouri);
                //samp.setText(videopath);
                Log.d("tag2", videopath);
            }
        }
    }


    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        cameraImagePath = getPath(selectedImage);
        //samp.setText(cameraImagePath);
        get_photo.setImageBitmap(BitmapFactory.decodeFile(cameraImagePath));
        Log.d("tag3", cameraImagePath);
    }

    @SuppressWarnings("deprecation")
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    public String getVPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();

        return path;
    }


    class UploadDataToServer extends AsyncTask<String, Void, String> {


        String Name, Locationname, Landmark, Tag, Contactno, latitide, longitude;

        public UploadDataToServer(String Name, String Locationname, String Landmark, String Tag, String Contactno, String latitide, String longitude) {

            this.Name = Name;
            this.Locationname = Locationname;
            this.Landmark = Landmark;
            this.Contactno = Contactno;
            this.Tag = Tag;
            this.latitide = latitide;
            this.longitude = longitude;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("tag", "s1");

            pDialog = new SweetAlertDialog(SpotAccident.this, SweetAlertDialog.PROGRESS_TYPE);
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

                jsonObject.accumulate("type", "ACCIDENT");
                jsonObject.accumulate("name", Name);
                jsonObject.accumulate("location", Locationname);
                jsonObject.accumulate("landmark", Landmark);
                jsonObject.accumulate("hashTags", Tag);
                jsonObject.accumulate("contactNumber", Contactno);
                jsonObject.accumulate("latitide", latitide);
                jsonObject.accumulate("longitude", longitude);
                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest(URL_REGISTER, json, token);
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
                JSONObject jo2 = new JSONObject(status1);
                String spot = jo2.getString("spotId");
                Log.d("tag", "<-----Status----->" + spot);

                spotid = spot;
                if (status.equals("SUCCESS")) {


                    if (cameraImagePath != null) {
                        new UploadImageToServer(spotid).execute();
                    } else if (videopath != null) {
                        new UploadVideoToServer(spotid).execute();
                    } else {

                        new SweetAlertDialog(SpotAccident.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Message Alert")
                                .setContentText("Spot Added Sucessfully")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                        sweetAlertDialog.cancel();
                                        finish();
                                    }
                                })
                                .show();


                        //Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                    }
                } else {

                    new SweetAlertDialog(SpotAccident.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops!")
                            .setContentText("Check your internet or try again")
                            .setConfirmText("OK")
                            .show();
                    //  Toast.makeText(getActivity(), "Check your internet or try again", Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }


    }


    private class UploadImageToServer extends AsyncTask<Void, Integer, String> {

        String spotid;

        public UploadImageToServer(String id) {

            this.spotid = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            psDialog = new SweetAlertDialog(SpotAccident.this, SweetAlertDialog.PROGRESS_TYPE);
            psDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            psDialog.setTitleText("Uploading Photo");
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
            HttpPost httppost = new HttpPost("http://sqindia01.cloudapp.net/vyuspot/api/v1/spot/addAccidentPhoto");
            httppost.setHeader("spotId", spotid);

            try {
                MultipartEntity entity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);
                File sourceFile = new File(cameraImagePath);
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
            new SweetAlertDialog(SpotAccident.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Message Alert")
                    .setContentText("Spot Added Sucessfully")
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            sweetAlertDialog.cancel();
                            finish();
                        }
                    })
                    .show();
        }

    }


    class UploadVideoToServer extends AsyncTask<String, Void, String> {


        String spotid;

        public UploadVideoToServer(String id) {

            this.spotid = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            psDialog = new SweetAlertDialog(SpotAccident.this, SweetAlertDialog.PROGRESS_TYPE);
            psDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            psDialog.setTitleText("Uploading Video");
            psDialog.setCancelable(false);
            psDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;


            try {

                HttpClient client = new DefaultHttpClient();
                HttpPost postMethod = new HttpPost("http://sqindia01.cloudapp.net/vyuspot/api/v1/spot/addAccidentVideo");
                postMethod.addHeader("spotId", spotid);
                Log.d("tag", "" + videopath);
                File files = new File(videopath);
                MultipartEntity entity = new MultipartEntity();
                FileBody contentFiles = new FileBody(files);
                entity.addPart("fileUpload", contentFiles);
                postMethod.setEntity(entity);
                HttpResponse response = client.execute(postMethod);
                HttpEntity r_entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                Log.d("tag", "" + responseString);
                if (statusCode == 200) {
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

            Log.d("tag", "responce from server" + s);

            psDialog.dismiss();
            new SweetAlertDialog(SpotAccident.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Message Alert")
                    .setContentText("Spot Added Sucessfully")
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            sweetAlertDialog.cancel();
                            finish();
                        }
                    })
                    .show();


        }

    }
}
