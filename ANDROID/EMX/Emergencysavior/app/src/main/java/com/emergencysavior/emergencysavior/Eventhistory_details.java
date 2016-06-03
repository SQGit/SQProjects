package com.emergencysavior.emergencysavior;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.sandro.restaurant.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by RSA on 28-03-2016.
 */
public class Eventhistory_details extends AppCompatActivity {
    String transctione_item_name, token, transaction_item_type;
    TextView Transaction_id, Transaction_sms, Transaction_email;

    public static final String EXTRA_IS_TRANSPARENT = "is_transparent";
    private boolean isTransparent;
    SweetAlertDialog pDialog;
    static TextView transction_type, call, sms, email;
    ArrayList<HashMap<String, String>> contactList;
    ArrayList<HashMap<String, String>> smsList;
    ArrayList<HashMap<String, String>> emailList;

    ListView list, list_sms, list_email;
    SimpleAdapter adapter, adapter2, adapter3;
    TextView txt_call_name, txt_call_phone, txt_call_message, txt_sms_name, txt_sms_phone, txt_sms_message, txt_email_name, txt_email_email, txt_email_message;
    Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isTransparent = getIntent().getBooleanExtra(EXTRA_IS_TRANSPARENT, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testinglistrow);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Eventhistory_details.this);
        token = sharedPreferences.getString("Session_token", "");
        tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        call = (TextView) findViewById(R.id.call);
        sms = (TextView) findViewById(R.id.sms);
        email = (TextView) findViewById(R.id.email);

        Transaction_id = (TextView) findViewById(R.id.Transaction_id);
        Transaction_sms = (TextView) findViewById(R.id.Transaction_sms);
        Transaction_email = (TextView) findViewById(R.id.Transaction_email);


        transction_type = (TextView) findViewById(R.id.transction_type);
        contactList = new ArrayList<HashMap<String, String>>();
        smsList = new ArrayList<HashMap<String, String>>();
        emailList = new ArrayList<HashMap<String, String>>();
        Transaction_id.setTypeface(tf);
        Transaction_sms.setTypeface(tf);
        Transaction_email.setTypeface(tf);

        call.setTypeface(tf);
        sms.setTypeface(tf);
        email.setTypeface(tf);
        transction_type.setTypeface(tf);

        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.otf");
        SpannableStringBuilder SS = new SpannableStringBuilder(Html.fromHtml("<font color='#3c5899'><small><b>HISTORY DETAILS</b></small> </font>"));
        SS.setSpan(new CustomTypefaceSpan("#3c5899", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            transctione_item_name = extras.getString("transctione_item_id");
            transaction_item_type = extras.getString("transaction_type");
            Log.d("tag", "<-----Transction_item_id_Recive---->" + transctione_item_name);
            Log.d("tag", "<-----transaction_item_type---->" + transaction_item_type);
            transction_type.setText(transaction_item_type);
            new Transction_item(transctione_item_name).execute();
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

    private class Transction_item extends AsyncTask<String, Void, String> {

        String transctione_item_name;
        public Transction_item(String transctione_item_name) {
            this.transctione_item_name=transctione_item_name;

        }


        protected void onPreExecute() {
            pDialog = new SweetAlertDialog(Eventhistory_details.this, SweetAlertDialog.PROGRESS_TYPE);
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
                jsonObject.accumulate("transaction_id", transctione_item_name);

                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return jsonStr = HttpUtils.makeRequest1(CONFIG.URL + "/transactiondetail", json, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }


            return null;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.d("tag", "<-----history_download_item_detailst---->" + jsonStr);
            super.onPostExecute(jsonStr);
            pDialog.dismiss();
            if (jsonStr == "") {

                new SweetAlertDialog(Eventhistory_details.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();
            } else {

                try {
                    JSONObject result1 = new JSONObject(jsonStr);
                    String status = result1.getString("status");
                    String msg = result1.getString("message");
                    final String number_of_call = result1.getString("icall");
                    final String number_of_sms = result1.getString("isms");
                    final String number_of_email = result1.getString("iemail");
                    call.setText("Call : " + number_of_call);
                    sms.setText("Sms : " + number_of_sms);
                    email.setText("Email : " + number_of_email);


                    if (status.equals("fail")) {

                        Log.d("tag", "<-----msg---->" + msg);
                    } else {
                        if ((number_of_call.equals("0")) && (number_of_sms.equals("0")) && (number_of_email.equals("0"))) {

                            new Restaurant(Eventhistory_details.this, "Nothing to show", Snackbar.LENGTH_LONG)
                                    .setBackgroundColor(Color.RED)
                                    .setTextColor(Color.WHITE)
                                    .show();


                        } else {
                            if (!result1.isNull("call")) {
                                JSONArray ja = result1.getJSONArray("call");
                                for (int i1 = 0; i1 < ja.length(); i1++) {
                                    HashMap<String, String> callmap = new HashMap<String, String>();
                                    JSONObject data = ja.getJSONObject(i1);
                                    callmap.put("name", data.getString("name"));
                                    callmap.put("phone", data.getString("phone"));
                                    callmap.put("message", data.getString("message"));
                                    contactList.add(callmap);
                                    Log.d("tag", "<-----call details---->" + ja);
                                }

                                list = (ListView) findViewById(R.id.listView);
                                tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
                                adapter = new SimpleAdapter(Eventhistory_details.this, contactList, R.layout.call_details, new String[]
                                        {"name", "phone", "message"},
                                        new int[]{R.id.txt_call_details_name,
                                                R.id.txt_call_detail_phone, R.id.txt_call_details_message}) {
                                    @Override
                                    public View getView(int pos, View convertView, ViewGroup parent) {
                                        View v = convertView;
                                        if (v == null) {
                                            LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                            v = vi.inflate(R.layout.call_details, null);

                                            txt_call_name = (TextView) v.findViewById(R.id.txt_call_details_name);
                                            txt_call_phone = (TextView) v.findViewById(R.id.txt_call_detail_phone);
                                            txt_call_message = (TextView) v.findViewById(R.id.txt_call_details_message);

                                            txt_call_name.setTypeface(tf);
                                            txt_call_phone.setTypeface(tf);
                                            txt_call_message.setTypeface(tf);

                                            txt_call_name.setText(contactList.get(pos).get("name"));
                                            txt_call_phone.setText(contactList.get(pos).get("phone"));
                                            txt_call_message.setText(contactList.get(pos).get("message"));

                                        }

                                        return v;
                                    }


                                };

                                list.setAdapter(adapter);
                                //**************************************************
                            } else {
                                new Restaurant(Eventhistory_details.this, "Call Not found", Snackbar.LENGTH_LONG)
                                        .setBackgroundColor(Color.RED)
                                        .setTextColor(Color.WHITE)
                                        .show();
                            }


                            if (!result1.isNull("sms")) {
                                JSONArray ja_sms = result1.getJSONArray("sms");
                                for (int i1 = 0; i1 < ja_sms.length(); i1++) {
                                    HashMap<String, String> smsmap = new HashMap<String, String>();
                                    JSONObject data = ja_sms.getJSONObject(i1);
                                    smsmap.put("name", data.getString("name"));
                                    smsmap.put("phone", data.getString("phone"));
                                    smsmap.put("message", data.getString("message"));
                                    smsList.add(smsmap);
                                }
                                list_sms = (ListView) findViewById(R.id.listView_sms);
                                adapter2 = new SimpleAdapter(Eventhistory_details.this, smsList, R.layout.sms_details, new String[]
                                        {"name", "phone", "message"},
                                        new int[]{R.id.txt_sms_details_name,
                                                R.id.txt_sms_detail_phone, R.id.txt_sms_detai_message}) {
                                    @Override
                                    public View getView(int pos, View convertView, ViewGroup parent) {
                                        View v = convertView;
                                        if (v == null) {
                                            LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                            v = vi.inflate(R.layout.sms_details, null);
                                            txt_sms_name = (TextView) v.findViewById(R.id.txt_sms_details_name);
                                            txt_sms_phone = (TextView) v.findViewById(R.id.txt_sms_detail_phone);
                                            txt_sms_message = (TextView) v.findViewById(R.id.txt_sms_detai_message);
                                            txt_sms_name.setTypeface(tf);
                                            txt_sms_phone.setTypeface(tf);
                                            txt_sms_message.setTypeface(tf);

                                            txt_sms_name.setText(smsList.get(pos).get("name"));
                                            txt_sms_phone.setText(smsList.get(pos).get("phone"));
                                            txt_sms_message.setText(smsList.get(pos).get("message"));

                                        }
                                        return v;
                                    }
                                };
                                list_sms.setAdapter(adapter2);
                            } else {

                                new Restaurant(Eventhistory_details.this, "Sms Not found", Snackbar.LENGTH_LONG)
                                        .setBackgroundColor(Color.RED)
                                        .setTextColor(Color.WHITE)
                                        .show();
                            }


                            if (!result1.isNull("email")) {
                                JSONArray ja_email = result1.getJSONArray("email");
                                Log.d("tag", "<-----email details---->" + ja_email);


                                for (int i1 = 0; i1 < ja_email.length(); i1++) {
                                    HashMap<String, String> email_map = new HashMap<String, String>();
                                    JSONObject data = ja_email.getJSONObject(i1);
                                    email_map.put("name", data.getString("name"));
                                    email_map.put("Email", data.getString("Email"));
                                    email_map.put("message", data.getString("message"));
                                    emailList.add(email_map);
                                }


                                list_email = (ListView) findViewById(R.id.listView_email);
                                adapter3 = new SimpleAdapter(Eventhistory_details.this, emailList, R.layout.email_details, new String[]
                                        {"name", "Email", "message"},
                                        new int[]{R.id.txt_email_details_name, R.id.txt_email_detail_email, R.id.txt_email_detail_message}) {
                                    @Override
                                    public View getView(int pos, View convertView, ViewGroup parent) {
                                        View v = convertView;
                                        if (v == null) {
                                            LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                            v = vi.inflate(R.layout.email_details, null);
                                            txt_email_name = (TextView) v.findViewById(R.id.txt_email_details_name);
                                            txt_email_email = (TextView) v.findViewById(R.id.txt_email_detail_email);
                                            txt_email_message = (TextView) v.findViewById(R.id.txt_email_detail_message);
                                            txt_email_name.setTypeface(tf);
                                            txt_email_email.setTypeface(tf);
                                            txt_email_message.setTypeface(tf);
                                            txt_email_name.setText(emailList.get(pos).get("name"));
                                            txt_email_email.setText(emailList.get(pos).get("Email"));
                                            txt_email_message.setText(emailList.get(pos).get("message"));

                                        }

                                        return v;
                                    }


                                };
                                list_email.setAdapter(adapter3);


                            } else {

                                new Restaurant(Eventhistory_details.this, "Email Not found", Snackbar.LENGTH_LONG)
                                        .setBackgroundColor(Color.RED)
                                        .setTextColor(Color.WHITE)
                                        .show();
                            }
                        }
                    }

                } catch (JSONException e) {
                    Log.d("tag", "<-----some thing unusual---->" + e.getMessage() +"\n"+e.getStackTrace());
                    e.printStackTrace();
                }
            }
        }
    }
}
