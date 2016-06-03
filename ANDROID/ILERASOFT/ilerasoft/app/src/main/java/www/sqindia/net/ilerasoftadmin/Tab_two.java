package www.sqindia.net.ilerasoftadmin;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alamkanak.weekview.WeekViewEvent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by RSA on onew/8/2016.
 */
public class Tab_two extends Fragment {
TextView categoryName_tv,status_tv,date_tv,time_tv,desc_tv;
    String bookingRefId,token;
    SweetAlertDialog pDialog;
    public static String URL_REGISTER = "http://sqindia01.cloudapp.net/cologix/api/v1/booking/getByRefId";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab2, container, false);
        categoryName_tv=(TextView)root.findViewById(R.id.tv1);
        status_tv=(TextView)root.findViewById(R.id.tv2);
        date_tv=(TextView)root.findViewById(R.id.tv3);
        time_tv=(TextView)root.findViewById(R.id.tv4);
        desc_tv=(TextView)root.findViewById(R.id.tv_desc);

        bookingRefId=BookingFragment.categoryId;
        Log.d("tag","categoryId"+bookingRefId);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        token = sharedPreferences.getString("TOKEN", "");
        new MyActivityAsync1().execute();





        return root;
    }

    class MyActivityAsync1 extends AsyncTask<String, Void, String> {
        public MyActivityAsync1() {
            String json = "", jsonStr = "";
        }

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.getWindow().setLayout(1800, 400);
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("bookingRefId", bookingRefId);
                //jsonObject.accumulate("categoryId",categoryId);
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest1(URL_REGISTER, json, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.d("tag", "<-----RESULT---->" + jsonStr);
            super.onPostExecute(jsonStr);
            pDialog.dismiss();


            try {
                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");
                Log.d("tag", "<-----<<<<<<<<<Status>>>>>>>----->" + status);

                JSONObject data1 = jo.getJSONObject("data");
                Log.d("tag", "<-----Status----->" + data1);

               JSONArray jsonarray = data1.getJSONArray("bookings");

                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject joo = jsonarray.getJSONObject(i);

                    String categoryName = joo.getString("categoryName");
                    String categoryDescription = joo.getString("categoryDescription");
                    String bookFrom = joo.getString("bookFrom");
                    String categoryImage = joo.getString("categoryImage");

                    String status1 = joo.getString("status");


                    categoryName_tv.setText(categoryName);
                    status_tv.setText("BOOKED");

                    desc_tv.setText(categoryDescription);

                    StringTokenizer tk = new StringTokenizer(bookFrom);

                    String date1 = tk.nextToken();  // <---  yyyy-mm-dd
                    String time = tk.nextToken();
                    date_tv.setText(date1);
                    time_tv.setText(time);
                    Log.d("tag", "<-----date----->" + date1  +"time"+time);
                }




              //String categoryId = joo.getString("categoryId");




            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.d("tag", "<-----zz----->" + e.getMessage());
            }

        }


    }
}