package com.emergencysavior.emergencysaviorwatch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import at.markushi.ui.CircleButton;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by RSA on 06-06-2016.
 */
public class Dasboard_Activity_two extends Fragment implements View.OnClickListener {


    CircleButton btn_call, btn_Need_help, btn_active_shooter, btn_my_location;
    TextView txt_call, txt_help, txt_activeshooter, txt_mylocation;
    String tranctional_type, token, firstname, email, tranctional_id, str_address;
    SweetAlertDialog pDialog;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dashboard_activity_two, container, false);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ques.otf");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        token = sharedPreferences.getString("Firstname", "");
        firstname = sharedPreferences.getString("session_token", "");
        email = sharedPreferences.getString("email", "");

        btn_call = (CircleButton) v.findViewById(R.id.call911);
        btn_Need_help = (CircleButton) v.findViewById(R.id.need_help);
        btn_active_shooter = (CircleButton) v.findViewById(R.id.activeshooter);
        btn_my_location = (CircleButton) v.findViewById(R.id.map_location);
        txt_call = (TextView) v.findViewById(R.id.call);
        txt_help = (TextView) v.findViewById(R.id.help);
        txt_activeshooter = (TextView) v.findViewById(R.id.Act);
        txt_mylocation = (TextView) v.findViewById(R.id.txt_maplocation);

        txt_call.setTypeface(tf);
        txt_help.setTypeface(tf);
        txt_activeshooter.setTypeface(tf);
        txt_mylocation.setTypeface(tf);

        btn_call.setOnClickListener(this);
        btn_Need_help.setOnClickListener(this);
        btn_active_shooter.setOnClickListener(this);
        btn_my_location.setOnClickListener(this);

        return v;
    }

    public static Dasboard_Activity_two newInstance(String text) {

        Dasboard_Activity_two f = new Dasboard_Activity_two();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }


    @Override
    public void onClick(View v) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        AlertDialog alertDialog;
        switch (v.getId()) {

            case R.id.call911:
                alertDialogBuilder.setTitle("ALERT");
                alertDialogBuilder
                        .setMessage("Please confirm ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                tranctional_type = ("call911");
                                new MyActivityAsync(tranctional_type).execute();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                break;
            case R.id.need_help:

                alertDialogBuilder.setTitle("ALERT");
                alertDialogBuilder
                        .setMessage("Please confirm ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                tranctional_type = ("need_help");
                                //new MyActivityAsync(tranctional_type).execute();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
            case R.id.activeshooter:
                alertDialogBuilder.setTitle("ALERT");
                alertDialogBuilder
                        .setMessage("Please confirm ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                tranctional_type = ("activeshooter");
                                //  new MyActivityAsync(tranctional_type).execute();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                break;
            case R.id.map_location:
                alertDialogBuilder.setTitle("ALERT");
                alertDialogBuilder
                        .setMessage("Please confirm")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                tranctional_type = ("map location");
                                //  new MyActivityAsync(tranctional_type).execute();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                break;

        }
    }

    private class MyActivityAsync extends AsyncTask<String, Void, String> {
        String tranctional_type;

        public MyActivityAsync(String tranctional_type) {
            this.tranctional_type = tranctional_type;
        }

        protected void onPreExecute() {
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Please wait... ");
            pDialog.setCancelable(true);
            pDialog.getWindow().setLayout(500, 200);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("transaction_type", tranctional_type);
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return jsonStr = HttpUtils.makeRequest(CONFIG.URL + "/transactionstart", json, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
                Log.d("tag", "exception" + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.d("tag", "<-----json result---->" + jsonStr);
            super.onPostExecute(jsonStr);
            pDialog.dismiss();
            if (jsonStr == "") {

                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("ALERT")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();
            } else {

                try {
                    JSONObject jo = new JSONObject(jsonStr);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                    tranctional_id = jo.getString("transaction_id");
                    if (status.equals("success")) {
                        switch (tranctional_type) {

                            case "call911":

                        }


                        SharedPreferences preferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        str_address = preferences.getString("addressLine", "");


                    } else {


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
