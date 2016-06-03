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
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ANANDH on 22-12-2015.
 */
public class TrackingStatus extends AppCompatActivity {

    SweetAlertDialog pDialog;

    TrackingstatusAdapter mja;
    ArrayList<HashMap<String, String>> contactList;
    static String CategoryNAME = "categoryName";
    static String BookFrom = "bookFrom";
    static String BookTo = "bookTo";
    static String BookingReferenceId = "bookingReferenceId";
    static String TagInTime = "tagInTime";
    static String TagOutTime = "tagOutTime";
    public static String URL_REGISTER = "http://sqindia01.cloudapp.net/cologix/api/v1/booking/getAll";
    String token;
    JSONObject jsonObject, jsonKey;

    String bookingId;

    TextView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking_status);

        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'><big><b>TRACKING STATUS</b></big> </font>"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xcececece));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.bck_arrow);

        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&


        contactList = new ArrayList<HashMap<String, String>>();

        if (Util.Operations.isOnline(TrackingStatus.this))
        {
            new MyActivityAsync(bookingId).execute();

        }
        else
        {
            new SweetAlertDialog(TrackingStatus.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("No Internet Connectivity")
                    .setConfirmText("Try Again")
                    .setCancelText("Cancel")
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent i = new Intent(TrackingStatus.this, Dashboard.class);
                            startActivity(i);

                        }
                    })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            Intent i = new Intent(TrackingStatus.this, TrackingStatus.class);
                            startActivity(i);
                        }
                    })
                    .show();


            //  Toast.makeText(getApplicationContext(), "No Internet Connectivity", Toast.LENGTH_SHORT).show();

        }



    }
    class MyActivityAsync extends AsyncTask<String, Void, String> {

        String bookingId;

        public MyActivityAsync(String bookingId) {
            String json = "", jsonStr = "";
            this.bookingId = bookingId;
            }
            protected void onPreExecute() {
                super.onPreExecute();
                 pDialog = new SweetAlertDialog(TrackingStatus.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                pDialog.show();


        }
        protected String doInBackground(String... params) {
            //  Log.d("input_Name", bookingId);
            String json = "", jsonStr = "";
            try {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(TrackingStatus.this);
                token= sharedPreferences.getString("TOKEN", "");
                JSONObject jsonObject = new JSONObject();
              //  jsonObject.accumulate("bookingRefId","15-1001");
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return jsonStr = HttpUtils.makeRequest(URL_REGISTER, json,token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.d("tag", "<-----rerseres---->" + jsonStr);
            super.onPostExecute(jsonStr);
            // pDialog.dismiss();
            if (jsonStr == "") {

                new SweetAlertDialog(TrackingStatus.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Internet Connectivity very slow? try again")
                        .setCancelText("Cancel")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent i = new Intent(TrackingStatus.this, Dashboard.class);
                                startActivity(i);

                            }
                        })
                        .show();
            } else {

                try {
                    JSONObject jo = new JSONObject(jsonStr);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                    Log.d("tag", "<-----Status----->" + status);
                if (status.equals("SUCCESS")) {

                        JSONObject data = jo.getJSONObject("data");
                        JSONArray ja = data.getJSONArray("bookings");

                        for (int i1 = 0; i1 < ja.length(); i1++) {
                            HashMap<String, String> map = new HashMap<String, String>();
                            data = ja.getJSONObject(i1);
                            map.put("categoryName", data.getString("categoryName"));
                            map.put("bookFrom", data.getString("bookFrom"));
                            map.put("bookTo", data.getString("bookTo"));
                            map.put("bookingReferenceId",data.getString("bookingReferenceId"));
                            map.put("tagInTime", data.getString("tagInTime"));
                            map.put("tagOutTime",data.getString("tagOutTime"));
                            contactList.add(map);
                        }


                    ListView list = (ListView)findViewById(R.id.listView);
                    mja = new TrackingstatusAdapter(TrackingStatus.this,contactList);
                    list.setAdapter(mja);
                        pDialog.dismiss();
                        Log.d("tag", "<-----Listview----->" + list);

                       /* new SweetAlertDialog(TrackingDevice.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("SUCCESS MESSAGE!!!")
                                .setConfirmText("Ok")
                                .setContentText("Your Booking Here click now")

                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.cancel();

                                    }
                                })
                                .show();*/


                    }


                    else
                    {
                        pDialog.dismiss();

                        new SweetAlertDialog(TrackingStatus.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("No booking for this hospital !")
                                .setContentText("")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.cancel();
                                        Intent i = new Intent(TrackingStatus.this, Dashboard.class);
                                        startActivity(i);
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
        Log.d("CDA", "onBackPressed Called");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // This is the up button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
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
}

