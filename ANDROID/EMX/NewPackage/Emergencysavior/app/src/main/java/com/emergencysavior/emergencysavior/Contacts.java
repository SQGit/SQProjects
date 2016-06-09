package com.emergencysavior.emergencysavior;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by RSA on 05-03-2016.
 */
public class Contacts extends AppCompatActivity {
    public static final String EXTRA_IS_TRANSPARENT = "is_transparent";
    private boolean isTransparent;
    private static final int RESULT_PICK_CONTACT = 85500;
    ListView contactview;

    ArrayList<HashMap<String, String>> contactList;
    static String name = "name";
    static String mobile = "mobile";
    static String email = "email";



    private List<FloatingActionMenu> menus = new ArrayList<>();
    FloatingActionMenu menu1;
    private Handler mUiHandler = new Handler();
    TextView sign;
    SweetAlertDialog pDialog;
    String token,phone,Display_Name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isTransparent = getIntent().getBooleanExtra(EXTRA_IS_TRANSPARENT, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Contacts.this);
        token = sharedPreferences.getString("Session_token", "");

        new MyActivityAsync().execute();
        contactList = new ArrayList<HashMap<String, String>>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setIcon(R.drawable.navicon);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.otf");
        SpannableStringBuilder SS = new SpannableStringBuilder(Html.fromHtml("<font color='#3c5899'><small><b>CONTACTS</b></small> </font>"));
        SS.setSpan(new CustomTypefaceSpan("#3c5899", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);
        sign = (TextView) findViewById(R.id.dss);
        sign.setTypeface(tf);
         ////// / / / / / / / / / / / / Floating button /////////////////////////////////////////////

        final FloatingActionMenu menu1 = (FloatingActionMenu) findViewById(R.id.menu1);
        final FloatingActionButton programFab1 = new FloatingActionButton(this);
        programFab1.setButtonSize(FloatingActionButton.SIZE_MINI);
        programFab1.setLabelText("Add Contacts");
        programFab1.setImageResource(R.drawable.cont2);
        programFab1.setColorNormal(R.color.text_hint_color);

        menu1.addMenuButton(programFab1);
        final FloatingActionButton programFab3 = new FloatingActionButton(getApplicationContext());
        programFab3.setButtonSize(FloatingActionButton.SIZE_MINI);
        programFab3.setLabelText("Pick from contacts");
        programFab3.setColorNormal(R.color.text_hint_color);
        programFab3.setImageResource(R.drawable.cont1);
        menu1.addMenuButton(programFab3);
        programFab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), AddContact.class);
                i.putExtra("new_variable_name", "0");
                startActivity(i);
            }
        });
        programFab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddContact.class);
                i.putExtra("new_variable_name", "1");
                Log.d("tag", "send");
                startActivity(i);

            }
        });
        menu1.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menu1.isOpened()) {

                }

                menu1.toggle(true);
            }
        });
        ContextThemeWrapper context = new ContextThemeWrapper(getApplication(), R.style.MenuButtonsStyle);
        FloatingActionButton programFab2 = new FloatingActionButton(context);
        programFab2.setLabelText("Programmatically added button");
        menus.add(menu1);
        FloatingActionButton programFab4 = new FloatingActionButton(context);
        programFab4.setLabelText("Programmatically added button");
        menus.add(menu1);
        menu1.hideMenuButton(false);
        int delay = 400;
        for (final FloatingActionMenu menu : menus) {
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    menu.showMenuButton(true);
                }
            }, delay);
            delay += 150;
        }
        menu1.setClosedOnTouchOutside(true);
        /////////////////////////////////////////////////////////////////////////////////////////////


        if (Util.Operations.isOnline(Contacts.this)) {
            //  new MyActivityAsync(bookingId).execute();


        } else {

            new SweetAlertDialog(Contacts.this, SweetAlertDialog.ERROR_TYPE)
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

                            Intent i = new Intent(Contacts.this, Contacts.class);
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


            pDialog = new SweetAlertDialog(Contacts.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Please wait... ");
            pDialog.setCancelable(true);
            pDialog.getWindow().setLayout(1500, 400);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {


                JSONObject jsonObject = new JSONObject();
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return jsonStr = HttpUtils.makeRequest1(CONFIG.URL+"/contactsget", json, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }


            return null;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.d("tag", "<-----Contact_download_result---->" + jsonStr);
            super.onPostExecute(jsonStr);
            pDialog.dismiss();
            if (jsonStr == "") {

                new SweetAlertDialog(Contacts.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();
            } else {

                try {
                    JSONObject result1 = new JSONObject(jsonStr);
                    String status = result1.getString("status");
                    String msg = result1.getString("message");

                    if (status.equals("success")) {

                      /*  JSONObject data = result1.getJSONObject("contacts");*/
                        JSONArray ja = result1.getJSONArray("contacts");

                        for (int i1 = 0; i1 < ja.length(); i1++) {
                            HashMap<String, String> map = new HashMap<String, String>();
                            JSONObject data = ja.getJSONObject(i1);
                            map.put("name", data.getString("name"));
                            map.put("mobile", data.getString("mobile"));
                            map.put("email", data.getString("email").equals("null")?"Email not found":data.getString("email"));
                            Log.d("tag", "<-----Listview----->" + data.getString("email") + " " + data.getString("mobile") + " " + data.getString("name"));
                            contactList.add(map);


                        }
                        final ListView list = (ListView) findViewById(R.id.listView);
                        final Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
                        SimpleAdapter adapter = new SimpleAdapter(Contacts.this, contactList, R.layout.contact_lostrrow, new String[]{"name",
                                "email", "mobile"}, new int[]{R.id.Name,
                                R.id.Email, R.id.Phone}) {
                            @Override
                            public View getView(int pos, View convertView, ViewGroup parent) {
                                View v = convertView;
                                if (v == null) {
                                    LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    v = vi.inflate(R.layout.contact_lostrrow, null);
                                }
                                TextView tv = (TextView) v.findViewById(R.id.Name);
                                tv.setText(contactList.get(pos).get("name"));
                                tv.setTypeface(tf);
                                TextView tvs = (TextView) v.findViewById(R.id.Email);
                                tvs.setText(contactList.get(pos).get("email"));
                                tvs.setTypeface(tf);
                                TextView tvs1 = (TextView) v.findViewById(R.id.Phone);
                                tvs1.setText(contactList.get(pos).get("mobile"));
                                tvs1.setTypeface(tf);
                                return v;
                            }
                        };

                        // setListAdapter(adapter);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String data=list.getItemAtPosition(position).toString();
                                phone = ((TextView) view.findViewById(R.id.Phone)).getText().toString();
                               Display_Name = ((TextView) view.findViewById(R.id.Name)).getText().toString();
                                Log.d("tag", phone);

                                AlertDialog.Builder alert=new AlertDialog.Builder(Contacts.this);
                                alert.setTitle("Delete");
                                alert.setMessage(Display_Name + "\n"+phone);
                                alert.setCancelable(false);
                                alert.setIcon(R.drawable.navicon);

                                alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                        new Contacts_DeleteAsync(phone).execute();

                                    }
                                });
                                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog ale=alert.create();
                                ale.show();

                               // Toast.makeText(getApplicationContext(), data,Toast.LENGTH_LONG).show();

                            }
                        });
                        list.setAdapter(adapter);

                        Log.d("tag", "<-----msg----->" + msg);
                        String sub_string = msg.substring(0, 19);

                        Toast.makeText(getApplicationContext(), sub_string, Toast.LENGTH_LONG).show();
                    } else {
                        new SweetAlertDialog(Contacts
                                .this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("INVALID")
                                .setContentText("Contacts not found")
                                .show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private class Contacts_DeleteAsync extends AsyncTask<String, Void, String> {
        String phone;
        public Contacts_DeleteAsync(String phone) {
            this.phone=phone;

        }
        protected void onPreExecute() {


            pDialog = new SweetAlertDialog(Contacts.this, SweetAlertDialog.PROGRESS_TYPE);
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
                jsonObject.accumulate("mobile", phone);

                json = jsonObject.toString();
                JSONObject data = new JSONObject();

                data.accumulate("data", jsonObject);
                return jsonStr = HttpUtils.makeRequest1(CONFIG.URL+"/contactdelete", json, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }


            return null;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.d("tag", "<-----Contact_Delete_Result---->" + jsonStr);
            super.onPostExecute(jsonStr);
            pDialog.dismiss();
            if (jsonStr == "") {

                new SweetAlertDialog(Contacts.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();
            } else {

                try {
                    JSONObject jo = new JSONObject(jsonStr);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                    Log.d("tag", "<-----Status----->" + status);
                    //      JSONObject data = jo.getJSONObject("data");

                    if (status.equals("success")) {
                        // pDialog.dismiss();

                        Intent i = new Intent(getApplicationContext(), Contacts.class);
                        startActivity(i);
                    } else {
                        // pDialog.dismiss();

                        new SweetAlertDialog(Contacts.this, SweetAlertDialog.ERROR_TYPE)
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

    @Override
    public void onBackPressed() {

    }
}

