package www.sqindia.net.ilerasoftadmin;

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
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ANANDH on 24-12-2015.
 */
public class Utilization extends AppCompatActivity {

    SweetAlertDialog pDialog;
    String period;
    Utilization_JsonAdapter mja;
    ArrayList<HashMap<String, String>> contactList;
    public static String URL_REGISTER = "http://sqindia01.cloudapp.net/cologix/api/v1/equipment/getUtilizationData";
    static String categoryNAME = "categoryName";
    static String percentage = "percentage";
    String token;
    TextView back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utilization_activity);

        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'><big><b>UTILIZATION</b></big> </font>"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xcececece));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.bck_arrow);

        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&


        contactList = new ArrayList<HashMap<String, String>>();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Utilization.this);
        token= sharedPreferences.getString("TOKEN", "");
         if (Util.Operations.isOnline(Utilization.this))
         {
            new MyActivityAsync(period).execute();
            } else {
                    new SweetAlertDialog(Utilization.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("No Internet Connectivity")
                    .show();
                    }
                    }

    class MyActivityAsync extends AsyncTask<String, Void, String> {

        String period;

        public MyActivityAsync(String period) {
            String json = "", jsonStr = "";
            this.period = period;
        }
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new SweetAlertDialog(Utilization.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();
            }
        protected String doInBackground(String... params) {
            //  Log.d("input_Name", bookingId);
            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("period","year");
                json = jsonObject.toString();
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
                pDialog.dismiss();
                new SweetAlertDialog(Utilization.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("No Internet Connectivity")
                        .setConfirmText("back")
                        .setCancelText("Try Again!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                Intent i = new Intent(Utilization.this, InventoryControl.class);
                                startActivity(i);
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent i = new Intent(Utilization.this, Utilization.class);
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
                    //{"status":"SUCCESS","message":"","data":{"equipments":[{"categoryName":"ECG","percentage":"0.00"}]}}
                    if (status.equals("SUCCESS")) {

                        JSONObject data = jo.getJSONObject("data");
                        JSONArray ja = data.getJSONArray("equipments");


                        for (int i1 = 0; i1 < ja.length(); i1++) {
                            HashMap<String, String> map = new HashMap<String, String>();
                            data = ja.getJSONObject(i1);
                            map.put("categoryName", data.getString("categoryName"));
                            map.put("percentage", data.getString("percentage"));
                            contactList.add(map);
                        }
                        ListView list = (ListView)findViewById(R.id.listView);
                        mja = new Utilization_JsonAdapter(Utilization.this,contactList);
                        list.setAdapter(mja);

                      pDialog.dismiss();
                        }
                        else
                    {
                        pDialog.dismiss();

                        new SweetAlertDialog(Utilization.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("No Current Report!")
                                .setContentText("")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.cancel();
                                        Intent i = new Intent(Utilization.this, InventoryControl.class);
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

}
