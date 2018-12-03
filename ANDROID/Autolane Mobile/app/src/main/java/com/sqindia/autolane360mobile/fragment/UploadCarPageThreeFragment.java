package com.sqindia.autolane360mobile.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sqindia.autolane360mobile.R;
import com.sqindia.autolane360mobile.activity.DashboardActivity;
import com.sqindia.autolane360mobile.activity.GetFilePathFromDevice;
import com.sqindia.autolane360mobile.activity.LoginActivity;
import com.sqindia.autolane360mobile.activity.UploadCar;
import com.sqindia.autolane360mobile.activity.ViewCarActivity;
import com.sqindia.autolane360mobile.utils.Utils;

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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;


public class UploadCarPageThreeFragment extends Fragment implements View.OnClickListener{

    public static ImageView birdeyeview_vack_iv,front_seatview_iv,video_img;
    public static ImageView upload9,upload10;
    String path_birdeye_view,path_frontseat_view;
    Dialog cameraDialog;
    LinearLayout lnr_takepic, lnr_takegallery;
    Bitmap bitmap;
    String selectedImagePath;
    SharedPreferences sharedPrefces;
    SharedPreferences.Editor edit;
    private static final int VIDEO_CAPTURE = 101;

    String get_date,get_mileage,get_trim,get_location,get_vinstatus,get_vehicle,get_interior,get_exterior,get_price;
    String get_year,get_make,get_model,get_cartype,get_startcode,get_engine,get_transmission;
    String get_door,get_manufacture,get_ac_cond,get_carkey,get_dealerinfo,get_description;
    String get_img1,get_img2,get_img3,get_img4,get_img5,get_img6,get_img7,get_img8,get_img9,get_img10;

    protected static final int TAKE_CAR_PIC9 = 17;
    protected static final int TAKE_CAR_PIC10 = 18;

    protected static final int SELECT_CAR_PIC9 = 19;
    protected static final int SELECT_CAR_PIC10 = 20;
    SharedPreferences sharedlogin;
    SharedPreferences.Editor editlogin;
    String userid_str,token;



    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_six, container, false);



        sharedlogin = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editlogin = sharedlogin.edit();
        userid_str = sharedlogin.getString("org_user_id", "");
        token=sharedlogin.getString("token","");


        cameraDialog = new Dialog(getActivity());
        cameraDialog.getWindow().setBackgroundDrawableResource(R.drawable.white_curve_bg);

        birdeyeview_vack_iv=view.findViewById(R.id.birdeyeview_vack_iv);
        front_seatview_iv=view.findViewById(R.id.front_seatview_iv);
        //video_img=view.findViewById(R.id.video_img);

        upload9=view.findViewById(R.id.upload9);
        upload10=view.findViewById(R.id.upload10);


        birdeyeview_vack_iv.setOnClickListener(this);
        front_seatview_iv.setOnClickListener(this);
        video_img.setOnClickListener(this);


        UploadCar.upload_part_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                edit = sharedPreferences.edit();
                get_date=sharedPreferences.getString("store_date","");
                get_mileage=sharedPreferences.getString("store_mileage","");
                get_trim = sharedPreferences.getString("store_trim", "");
                get_location=sharedPreferences.getString("store_location","");
                get_vinstatus=sharedPreferences.getString("store_vinstatus","");
                get_vehicle=sharedPreferences.getString("store_vehicle","");
                get_interior=sharedPreferences.getString("store_interior","");
                get_exterior=sharedPreferences.getString("store_exterior","");
                get_price=sharedPreferences.getString("store_price","");


                Log.e("tag","print 1---->"+get_date);
                Log.e("tag","print 2---->"+get_mileage);
                Log.e("tag","print 3---->"+get_trim);
                Log.e("tag","print 4---->"+get_location);
                Log.e("tag","print 5---->"+get_vinstatus);
                Log.e("tag","print 6---->"+get_vehicle);
                Log.e("tag","print 7---->"+get_interior);
                Log.e("tag","print 8---->"+get_exterior);
                Log.e("tag","print 9---->"+get_price);



                get_year=sharedPreferences.getString("store_caryear","");
                get_make=sharedPreferences.getString("store_carmake","");
                get_model = sharedPreferences.getString("store_carmodel", "");
                get_cartype=sharedPreferences.getString("store_cartype","");
                get_startcode=sharedPreferences.getString("store_carstartcode","");
                get_engine=sharedPreferences.getString("store_carengine","");
                get_transmission=sharedPreferences.getString("store_cartransmision","");



                Log.e("tag","print 10---->"+get_date);
                Log.e("tag","print 11---->"+get_mileage);
                Log.e("tag","print 12---->"+get_trim);
                Log.e("tag","print 13---->"+get_location);
                Log.e("tag","print 14---->"+get_vinstatus);
                Log.e("tag","print 15---->"+get_vehicle);
                Log.e("tag","print 16--->"+get_interior);


                get_door=sharedPreferences.getString("store_doors","");
                get_manufacture=sharedPreferences.getString("store_manufacture","");
                get_ac_cond = sharedPreferences.getString("store_AcCondition", "");
                get_carkey=sharedPreferences.getString("store_Keys","");
                get_dealerinfo=sharedPreferences.getString("store_dealerinfo","");
                get_description=sharedPreferences.getString("store_carengine","");



                Log.e("tag","print 17---->"+get_door);
                Log.e("tag","print 18---->"+get_manufacture);
                Log.e("tag","print 19---->"+get_ac_cond);
                Log.e("tag","print 20---->"+get_carkey);
                Log.e("tag","print 21---->"+get_dealerinfo);
                Log.e("tag","print 22---->"+get_description);



                get_img1=sharedPreferences.getString("store_fronview_path","");
                get_img2=sharedPreferences.getString("store_sideviewright_path","");
                get_img3 = sharedPreferences.getString("store_sideviewleft_path", "");
                get_img4=sharedPreferences.getString("store_backview_path","");


                Log.e("tag","IMG 1---->"+get_img1);
                Log.e("tag","IMG 2---->"+get_img2);
                Log.e("tag","IMG 3---->"+get_img3);
                Log.e("tag","IMG 4---->"+get_img4);


                get_img5=sharedPreferences.getString("store_frontdashboard_path","");
                get_img6=sharedPreferences.getString("store_engineview_path","");
                get_img7 = sharedPreferences.getString("store_frontbird_path", "");
                get_img8=sharedPreferences.getString("store_backseat_path","");

                Log.e("tag","IMG 5---->"+get_img5);
                Log.e("tag","IMG 6---->"+get_img6);
                Log.e("tag","IMG 7---->"+get_img7);
                Log.e("tag","IMG 8---->"+get_img8);



                get_img9=sharedPreferences.getString("store_birdeyeview_path","");
                get_img10=sharedPreferences.getString("store_frontseatview_path","");

                Log.e("tag","IMG 9---->"+get_img9);
                Log.e("tag","IMG 10---->"+get_img10);


                if (Utils.Operations.isOnline(getActivity())) {


                    new upload_car().execute();
                }
                else {
                    Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_LONG).show();
                }

            }
        });



        return view;
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if ((result == PackageManager.PERMISSION_GRANTED) && (result1 == PackageManager.PERMISSION_GRANTED) && (result2 == PackageManager.PERMISSION_GRANTED)) {
            return true;
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
            return false;
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // CAR 9
            case R.id.birdeyeview_vack_iv:


                cameraDialog.setContentView(R.layout.camera_dialog);
                lnr_takepic = cameraDialog.findViewById(R.id.lnr_takepic);
                lnr_takegallery = cameraDialog.findViewById(R.id.lnr_takegallery);


                // if button is clicked, close the custom dialog
                lnr_takepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();
                        if(checkPermission()) {

                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, TAKE_CAR_PIC9);

                        }
                    }
                });


                lnr_takegallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();

                        if (checkPermission()) {
                            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            pickIntent.setType("image/*");
                            startActivityForResult(pickIntent, SELECT_CAR_PIC9);
                        }


                    }
                });
                cameraDialog.show();

                break;


            // CAR 10
            case R.id.front_seatview_iv:

                cameraDialog.setContentView(R.layout.camera_dialog);
                lnr_takepic = cameraDialog.findViewById(R.id.lnr_takepic);
                lnr_takegallery = cameraDialog.findViewById(R.id.lnr_takegallery);

                // if button is clicked, close the custom dialog
                lnr_takepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();
                        if(checkPermission()) {

                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, TAKE_CAR_PIC10);
                        }
                    }
                });


                lnr_takegallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();

                        if (checkPermission()) {
                            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            pickIntent.setType("image/*");
                            startActivityForResult(pickIntent, SELECT_CAR_PIC10);
                        }

                    }
                });
                cameraDialog.show();
                break;





            default:
                break;
        }

    }








    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        bitmap = null;
        selectedImagePath = null;

        //********************CAR PIC 9 -------TAKE
        if (resultCode == RESULT_OK && requestCode == TAKE_CAR_PIC9) {

            bitmap = (Bitmap) data.getExtras().get("data");
            File sd = Environment.getExternalStorageDirectory();
            File imagepath = new File(sd.getAbsolutePath() + File.separator +
                    "FolderName" + File.separator + "InsideFolderName");

            if (!imagepath.isDirectory())
            {
                imagepath.mkdirs();
            }

            File mediaFile4 = new File(imagepath + File.separator + "img_" +
                    System.currentTimeMillis() + ".jpg");

            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(mediaFile4);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            birdeyeview_vack_iv.setImageBitmap(bitmap);
            //upload1.setVisibility(View.GONE);
            path_birdeye_view=mediaFile4.getAbsolutePath();
            edit.putString("store_birdeyeview_path",path_birdeye_view);
            edit.commit();


        }








        //********************CAR PIC 10 -------TAKE
        else if (resultCode == RESULT_OK && requestCode == TAKE_CAR_PIC10){

            bitmap = (Bitmap) data.getExtras().get("data");
            File sd = Environment.getExternalStorageDirectory();
            File imagepath = new File(sd.getAbsolutePath() + File.separator +
                    "FolderName" + File.separator + "InsideFolderName");

            if (!imagepath.isDirectory())
            {
                imagepath.mkdirs();
            }

            File mediaFile4 = new File(imagepath + File.separator + "img_" +
                    System.currentTimeMillis() + ".jpg");

            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(mediaFile4);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            front_seatview_iv.setImageBitmap(bitmap);
            // upload2.setVisibility(View.GONE);
            path_frontseat_view=mediaFile4.getAbsolutePath();
            edit.putString("store_frontseatview_path",path_frontseat_view);
            edit.commit();
        }






        //********************CAR PIC 9 -------SELECT
        else if (resultCode == RESULT_OK && requestCode == SELECT_CAR_PIC9){
            try {
                Uri selectedMediaUri = data.getData();
                path_birdeye_view = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    birdeyeview_vack_iv.setImageBitmap(bitmap);
                    //upload1.setVisibility(View.GONE);
                    edit.putString("store_birdeyeview_path",path_birdeye_view);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }


        }



        //********************CAR PIC 10 -------SELECT
        else if (resultCode == RESULT_OK && requestCode == SELECT_CAR_PIC10){
            try {
                Uri selectedMediaUri = data.getData();
                path_frontseat_view = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    front_seatview_iv.setImageBitmap(bitmap);
                    //upload2.setVisibility(View.GONE);

                    edit.putString("store_frontseatview_path",path_frontseat_view);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }

        }



    }









    public class upload_car extends AsyncTask<String, Void, String> {

        private ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Car Parts Uploading..., please Wait.");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String json = "", jsonStr = "";
            try {
                String responseString = null;
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://104.197.80.225/autolane360api/apis/add_car_stock1");
                //HttpPost httppost = new HttpPost("http://104.197.80.225/autoparts360/api/add_car_details");
                httppost.setHeader("X-API-KEY",token);
                httppost.setHeader("adminId",userid_str);
                //httppost.setHeader("date", get_date);
                httppost.setHeader("carType", get_cartype);
                httppost.setHeader("carYear", get_year);
                httppost.setHeader("carMake", get_make);
                httppost.setHeader("carModel",get_model);
                httppost.setHeader("mileage", get_mileage);
                httppost.setHeader("location",get_location);
                httppost.setHeader("vinStatus", get_vinstatus);
                httppost.setHeader("startCode", get_startcode);
                httppost.setHeader("engine", get_engine);
                httppost.setHeader("vehicleClass",get_vehicle);
                httppost.setHeader("price",get_price);


                httppost.setHeader("carTransmission",get_transmission );
                httppost.setHeader("door", get_door);
                httppost.setHeader("manufacturer", get_manufacture);
                httppost.setHeader("acCondition",get_ac_cond);
                httppost.setHeader("interiorColor", get_interior);
                httppost.setHeader("exteriorColor",get_exterior);
                httppost.setHeader("carKey", get_carkey);
                httppost.setHeader("dealerInfo", get_dealerinfo);
                httppost.setHeader("description", get_description);



                HttpResponse response = null;
                HttpEntity r_entity = null;


                MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                //upload Images     *************************************  1
                Log.e("tag","FFFFFFF------------------->"+get_img1);
                if(!get_img1.equals(""))
                {
                    Log.e("tag","inside1");
                    entity.addPart("frontViewImage", new FileBody(new File(get_img1), "image/jpeg"));
                }
                if(!get_img2.equals(""))
                {
                    Log.e("tag","inside2");
                    entity.addPart("sideViewRightImage", new FileBody(new File(get_img2), "image/jpeg"));
                }
                if(!get_img3.equals(""))
                {
                    entity.addPart("sideViewLeftImage", new FileBody(new File(get_img3), "image/jpeg"));
                }
                if(!get_img4.equals(""))
                {
                    entity.addPart("backViewImage", new FileBody(new File(get_img4), "image/jpeg"));
                }
                if(!get_img5.equals(""))
                {
                    entity.addPart("frontDashboardImage", new FileBody(new File(get_img5), "image/jpeg"));
                }
                if(!get_img6.equals(""))
                {
                    entity.addPart("engineViewImage", new FileBody(new File(get_img6), "image/jpeg"));
                }
                if(!get_img7.equals(""))
                {
                    entity.addPart("frontBirdEyeViewImage", new FileBody(new File(get_img7), "image/jpeg"));
                }
                if(!get_img8.equals(""))
                {
                    entity.addPart("backSeatViewwithOpenDoorsImage", new FileBody(new File(get_img8), "image/jpeg"));
                }
                if(!get_img9.equals(""))
                {
                    entity.addPart("BirdEyeViewBackImage", new FileBody(new File(get_img9), "image/jpeg"));
                }
                if(!get_img10.equals("")) {
                    entity.addPart("FrontSeatViewWithOpenDoorsImage", new FileBody(new File(get_img10), "image/jpeg"));
                }










                httppost.setEntity(entity);
                try {
                    try {
                        response = httpclient.execute(httppost);
                    } catch (Exception e) {
                        Log.e("tag", "ds:" + e.toString());
                    }
                    try {
                        r_entity = response.getEntity();
                    } catch (Exception e) {
                        Log.e("tag", "dsa:" + e.toString());
                    }

                    int statusCode = response.getStatusLine().getStatusCode();
                    Log.e("tag", response.getStatusLine().toString());
                    if (statusCode == 200) {
                        responseString = EntityUtils.toString(r_entity);
                        Log.e("tag", "rssss" + responseString);
                        // return success;

                    } else {
                        responseString = "Error occurred! Http Status Code: "
                                + statusCode;
                        Log.e("tag3", responseString);
                    }
                } catch (ClientProtocolException e) {
                    responseString = e.toString();
                    Log.e("tag44", responseString);
                } catch (IOException e) {
                    responseString = e.toString();
                    Log.e("tag45", responseString);
                }
                return responseString;
            } catch (Exception e) {
                Log.e("tag_InputStream0", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (dialog.isShowing())
            {
                dialog.dismiss();
                edit.remove("store_date");
                edit.remove("store_mileage");
                edit.remove("store_trim");
                edit.remove("store_location");
                edit.remove("store_vinstatus");
                edit.remove("store_vehicle");
                edit.remove("store_interior");
                edit.remove("store_exterior");
                edit.remove("store_price");
                edit.remove("store_caryear");
                edit.remove("store_carmake");
                edit.remove("store_carmodel");
                edit.remove("store_cartype");
                edit.remove("store_carstartcode");
                edit.remove("store_carengine");
                edit.remove("store_cartransmision");
                edit.remove("store_doors");
                edit.remove("store_manufacture");
                edit.remove("store_AcCondition");
                edit.remove("store_Keys");
                edit.remove("store_dealerinfo");
                edit.remove("store_carengine");


                //remove engine fields:
                edit.remove("store_fronview_path");
                edit.remove("store_sideviewright_path");
                edit.remove("store_sideviewleft_path");
                edit.remove("store_backview_path");

                edit.remove("store_frontdashboard_path");
                edit.remove("store_engineview_path");
                edit.remove("store_frontbird_path");
                edit.remove("store_backseat_path");
                edit.remove("store_birdeyeview_path");
                edit.remove("store_frontseatview_path");
                edit.commit();
            }

            try {
                if (s.length() > 0) {
                    try {
                        JSONObject jo = new JSONObject(s.toString());
                        String success = jo.getString("status");


                        if (success.equals("true")) {
                            String message=jo.getString("message");

                           Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();


                        } else {
                            String message=jo.getString("message");
                            Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e)
            {
            }
        }
    }

}
