package com.emerengencysavior.emergencysavior;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.ListDialogFragment;
import com.avast.android.dialogs.iface.IListDialogListener;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by RSA on 18-03-2016.
 */
public class Emergency_event_history extends AppCompatActivity implements IListDialogListener {
    public static final String EXTRA_IS_TRANSPARENT = "is_transparent";
    private boolean isTransparent;
    Button view;
    String token, transctione_item_name, switch_btn_call;
    TextView sign;
    SweetAlertDialog pDialog;
    Button btn_view;
    ArrayList<HashMap<String, String>> contactList;
    static String id = "id";
    static String time = "time";
    static String transaction_type = "transaction_type";

    private static final int REQUEST_LIST_SIMPLE = 9;
    private static final int REQUEST_LIST_SINGLE = 11;
    String typz = "", Transction_transaction_type;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isTransparent = getIntent().getBooleanExtra(EXTRA_IS_TRANSPARENT, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_event_history);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Emergency_event_history.this);
        token = sharedPreferences.getString("Session_token", "");
        contactList = new ArrayList<HashMap<String, String>>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setIcon(R.drawable.navicon);
        final Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.otf");
        SpannableStringBuilder SS = new SpannableStringBuilder(Html.fromHtml("<font color='#3c5899'><small><b>EVENT HISTORY</b></small> </font>"));
        SS.setSpan(new CustomTypefaceSpan("#3c5899", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);
        sign = (TextView) findViewById(R.id.dss);
        // btn_filter = (Button) findViewById(R.id.btn_filter);
        sign.setTypeface(tf);
        if (Util.Operations.isOnline(Emergency_event_history.this)) {
            new MyActivityAsync().execute();

        } else {

            new SweetAlertDialog(Emergency_event_history.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("No Internet Connectivity")
                    .setConfirmText("Try Again")
                    .setCancelText("Cancel")
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();

                        }
                    })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            Intent i = new Intent(Emergency_event_history.this, MainActivity.class);
                            startActivity(i);
                        }
                    })
                    .show();


        }
        setStatusBar();
    }

    protected void setStatusBar() {
        if (isTransparent) {
            StatusBarUtil.setTransparent(this);
        } else {
            StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
        }
    }


    private class MyActivityAsync extends AsyncTask<String, Void, String> {


        protected void onPreExecute() {
            pDialog = new SweetAlertDialog(Emergency_event_history.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Please wait... ");
            pDialog.setCancelable(true);
            pDialog.getWindow().setLayout(1000, 100);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {


                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("type", "call911");
                jsonObject.accumulate("type", "need_help");
                jsonObject.accumulate("type", "activeshooter");
                jsonObject.accumulate("type", "iamok");
                jsonObject.accumulate("type", "alarm");
                jsonObject.accumulate("type", "disaster");
                jsonObject.accumulate("type", "see_something");
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return jsonStr = HttpUtils.makeRequest1(CONFIG.URL + "/history", json, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }


            return null;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.d("tag", "<-----history_download_result---->" + jsonStr);
            super.onPostExecute(jsonStr);
            pDialog.dismiss();
            if (jsonStr == "") {

                new SweetAlertDialog(Emergency_event_history.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();
            } else {

                try {
                    JSONObject result1 = new JSONObject(jsonStr);
                    String status = result1.getString("status");
                    String msg = result1.getString("message");

                    if (status.equals("success")) {
                        if (result1.isNull("history")) {

                            Toast.makeText(getApplicationContext(), "No previous history to show", Toast.LENGTH_LONG).show();

                            return;
                        }

                        JSONArray ja = result1.getJSONArray("history");
                        Log.d("tag", "count" + ja.length());


                        for (int i1 = 0; i1 < ja.length(); i1++) {
                            HashMap<String, String> map = new HashMap<String, String>();
                            JSONObject data = ja.getJSONObject(i1);
                            map.put("id", data.getString("id"));
                            map.put("time", data.getString("time"));

                            switch (data.getString("transaction_type")) {
                                case "call911":
                                    typz = "Call 911";
                                    break;
                                case "need_help":
                                    typz = "Need Help";
                                    break;
                                case "activeshooter":
                                    typz = "Active Shooter";
                                    break;
                                case "iamok":
                                    typz = "I am ok";
                                    break;
                                case "alarm":
                                    typz = "Alarm";
                                    break;
                                case "disaster":
                                    typz = "Disaster";
                                    break;
                                case "see_something":
                                    typz = "See something & Do Something";
                                    break;
                            }


                            map.put("transaction_type", typz);
                            contactList.add(map);


                        }
                        final ListView list = (ListView) findViewById(R.id.listView);
                        final Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
                        SimpleAdapter adapter = new SimpleAdapter(Emergency_event_history.this, contactList, R.layout.emergency_history_all_item, new String[]{"transaction_type",
                                "time", "id"}, new int[]{R.id.Name,
                                R.id.time}) {
                            @Override
                            public View getView(final int pos, View convertView, ViewGroup parent) {
                                View v = convertView;
                                if (v == null) {
                                    LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    v = vi.inflate(R.layout.emergency_history_all_item, null);
                                }

                                TextView tv = (TextView) v.findViewById(R.id.Name);
                                tv.setText(contactList.get(pos).get("transaction_type"));
                                tv.setTypeface(tf);


                                TextView txt_date = (TextView) v.findViewById(R.id.txt_time);
                                TextView time = (TextView) v.findViewById(R.id.time);
                                btn_view = (Button) v.findViewById(R.id.btn_view);
                                btn_view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        transctione_item_name = contactList.get(pos).get("id");
                                        Transction_transaction_type = contactList.get(pos).get("transaction_type");
                                        Intent intent = new Intent(getApplicationContext(), Eventhistory_details.class);
                                        intent.putExtra("transctione_item_id", transctione_item_name);
                                        intent.putExtra("transaction_type", Transction_transaction_type);
                                        startActivity(intent);

                                    }
                                });
                                Calendar cal = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String mydate = "", mytime = "";
                                try {
                                    cal.setTime(sdf.parse(contactList.get(pos).get("time")));// all done
                                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                                    mydate = format1.format(cal.getTime());
                                    SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
                                    mytime = format2.format(cal.getTime());

                                } catch (Exception e) {
                                    Log.d("tag", e.getMessage());
                                }

                                time.setText(mytime);
                                txt_date.setText(mydate);

                                return v;
                            }
                        };
                        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                                transctione_item_name = contactList.get(position).get("id");
                                Transction_transaction_type = contactList.get(position).get("transaction_type");

                                ListDialogFragment
                                        .createBuilder(Emergency_event_history.this, getSupportFragmentManager())
                                        .setTitle("Please select option")
                                        .setItems(new String[]{"info", "delete"})
                                        .setRequestCode(REQUEST_LIST_SIMPLE)
                                        .show();


                                return false;
                            }
                        });
                        list.setAdapter(adapter);


                    } else {

                        Toast.makeText(getApplicationContext(), "Data Not found", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void onListItemSelected(CharSequence value, int number, int requestCode) {
        if (requestCode == REQUEST_LIST_SIMPLE || requestCode == REQUEST_LIST_SINGLE) {

            switch (number) {
                case 0:

                    Intent intent = new Intent(getApplicationContext(), Eventhistory_details.class);
                    intent.putExtra("transctione_item_id", transctione_item_name);
                    intent.putExtra("transaction_type", Transction_transaction_type);
                    startActivity(intent);
                    break;
                case 1:

                    new Contacts_DeleteAsync(transctione_item_name).execute();

                    break;

                default:
                    Log.d("tag", "Nothing to selected");
            }
        }


    }

    private class Contacts_DeleteAsync extends AsyncTask<String, Void, String> {

        String transctione_item_name;

        public Contacts_DeleteAsync(String transctione_item_name) {

            this.transctione_item_name = transctione_item_name;

        }

        protected void onPreExecute() {
            pDialog = new SweetAlertDialog(Emergency_event_history.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Deleting ");
            pDialog.setCancelable(true);
            pDialog.getWindow().setLayout(1800, 400);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("transaction_id", transctione_item_name);
                json = jsonObject.toString();
                JSONObject data = new JSONObject();

                data.accumulate("data", jsonObject);
                return jsonStr = HttpUtils.makeRequest1(CONFIG.URL + "/transactiondelete", json, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }


            return null;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.d("tag", "<-----Event_Item_Delete_Result---->" + jsonStr);
            super.onPostExecute(jsonStr);
            pDialog.dismiss();
            if (jsonStr == "") {

                new SweetAlertDialog(Emergency_event_history.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();
            } else {

                try {
                    JSONObject jo = new JSONObject(jsonStr);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                    Log.d("tag", "<-----Status----->" + status);
                    if (status.equals("success")) {
                        Intent i = new Intent(getApplicationContext(), Emergency_event_history.class);
                        startActivity(i);
                    } else {
                        // pDialog.dismiss();

                        new SweetAlertDialog(Emergency_event_history.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("INVALID")
                                .setContentText("Try after Sometime")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.cancel();
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
}

