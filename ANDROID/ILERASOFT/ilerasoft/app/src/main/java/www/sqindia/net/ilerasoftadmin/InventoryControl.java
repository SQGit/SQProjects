package www.sqindia.net.ilerasoftadmin;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ANANDH on 12-12-2015.
 */
public class InventoryControl extends AppCompatActivity {
    ImageView eqipment,utilazation,statistics;
    TextView back;


    //*************************
    SQLiteDatabase sdb;
    public static String URL_REGISTER = "http://sqindia01.cloudapp.net/cologix/api/v1/equipment/getUtilizationData";
    String period;
    ArrayList<PrePlot> Status_engine_Data = new ArrayList<PrePlot>();


    //*************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.inventorycontrol);

        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'><big><b>INVENTORY CONTROL</b></big> </font>"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xcececece));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.bck_arrow);

        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&



        new MyActivityAsync(period).execute();


       // back=(TextView)findViewById(R.id.back_iv);
        eqipment=(ImageView)findViewById(R.id.eqipment);
        statistics=(ImageView)findViewById(R.id.statistics);



        DataBaseHandler x = new DataBaseHandler(getApplicationContext(), "TABLE_NAME.db", null, 1);
        SQLiteDatabase sdb = x.getReadableDatabase();
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(InventoryControl.this, Analytics_Dash.class);
                startActivity(i);
            }
        });
        utilazation=(ImageView)findViewById(R.id.utilizatn);
           /* back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InventoryControl.this, Dashboard.class);
                startActivity(i);

            }
        });*/
             eqipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InventoryControl.this, AddEquipmentForm.class);
                startActivity(i);

            }
        });
        utilazation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InventoryControl.this, Utilization.class);
                startActivity(i);

            }
        });



    }

    class MyActivityAsync extends AsyncTask<String, Void, String> {

        String period;

        public MyActivityAsync(String period) {
            this.period = period;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";


            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("period", "year");

                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest1(URL_REGISTER, json, "20f5f35ee561d4f69391ed9758c39ced");
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;
        }
    @Override
        protected void onPostExecute(String s) {
            Log.d("tag", "<-----rerseres---->" + s);


            super.onPostExecute(s);

            try {
                JSONObject jo = new JSONObject(s);
                String status = jo.getString("status");
                String msg = jo.getString("message");
                Log.d("tag", "<-----Status----->" + status);
                if (status.equals("SUCCESS")) {
                    JSONObject data = jo.getJSONObject("data");
                    JSONArray ja = data.getJSONArray("equipments");
                    for (int i1 = 0; i1 < ja.length(); i1++) {
                        data = ja.getJSONObject(i1);
                        final String status_engine = data.getString("categoryName");
                        final String total_time = data.getString("percentage");
                        DataBaseHandler x = new DataBaseHandler(getApplicationContext(), "ILERASOFT.db", null, 1);
                        sdb = x.getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        cv.put("CATEGORY_NAME", total_time);
                        cv.put("PERCENTAGE", status_engine);
                        sdb.insert("TABLE_NAME", null, cv);
                        sdb.close();
                    }
                } else {
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
       /* Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);*/
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
