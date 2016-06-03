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
 * Created by TekCampuz on 12/22/2015.
 */
public class TrackingCancel extends AppCompatActivity {

    SweetAlertDialog pDialog;

    TrackingCancelAdapter mja;
    ArrayList<HashMap<String, String>> contactList;
    static String CategoryNAME = "categoryName";
    static String BookFrom = "bookFrom";
    static String BookTo = "bookTo";
    static String BookingReferenceId = "bookingReferenceId";
    static String TagInTime = "tagInTime";
    static String TagOutTime = "tagOutTime";
    static String BookingId = "bookingId";
    static String Status = "status";
    public static String URL_REGISTER = "http://sqindia01.cloudapp.net/cologix/api/v1/booking/getAll";
    static String token;
    JSONObject jsonObject, jsonKey;

    String bookingId;

    TextView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking_cancel);


        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'><big><b>CANCELLATION</b></big> </font>"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xcececece));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.bck_arrow);

        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

      /*  back = (TextView) findViewById(R.id.tv);
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(TrackingCancel.this, TrackingDevice.class);
                startActivity(i);

            }
        });*/

        contactList = new ArrayList<HashMap<String, String>>();

        new MyActivityAsync(bookingId).execute();

    }
    class MyActivityAsync extends AsyncTask<String, Void, String> {

        String bookingId;

        public MyActivityAsync(String bookingId) {
            String json = "", jsonStr = "";
            this.bookingId = bookingId;
        }
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new SweetAlertDialog(TrackingCancel.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();


        }
        protected String doInBackground(String... params) {
            //  Log.d("input_Name", bookingId);
            String json = "", jsonStr = "";
            try {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(TrackingCancel.this);
                token= sharedPreferences.getString("TOKEN", "");
                JSONObject jsonObject = new JSONObject();
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return jsonStr = HttpUtils.makeRequest1(URL_REGISTER, json, token);
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

                new SweetAlertDialog(TrackingCancel.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Internet Connectivity very slow? try again")
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
                            map.put("bookingId", data.getString("bookingId"));
                            map.put("bookingReferenceId",data.getString("bookingReferenceId"));
                            map.put("tagInTime", data.getString("tagInTime"));
                            map.put("tagOutTime",data.getString("tagOutTime"));
                            map.put("status",data.getString("status"));

                            contactList.add(map);
                        }


                        ListView list = (ListView)findViewById(R.id.listView);
                        mja = new TrackingCancelAdapter(TrackingCancel.this,contactList);
                        list.setAdapter(mja);
                        pDialog.dismiss();
                        Log.d("tag", "<-----Listview----->" + list);



                    }


                    else
                    {
                        pDialog.dismiss();

                        new SweetAlertDialog(TrackingCancel.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("No booking found !")
                               .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        Intent i = new Intent(TrackingCancel.this, TrackingDevice.class);
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

