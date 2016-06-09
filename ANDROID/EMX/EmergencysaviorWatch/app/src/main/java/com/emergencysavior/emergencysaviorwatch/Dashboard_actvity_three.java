package com.emergencysavior.emergencysaviorwatch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import at.markushi.ui.CircleButton;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by RSA on 06-06-2016.
 */
public class Dashboard_actvity_three extends Fragment implements View.OnClickListener {
    Typeface tf;
    CircleButton btn_alarm, btn_rescue_Me, btn_see_something, btn_iamok;
    CircleButton btn_emergency_earthquake, btn_emergency_flood, btn_emergency_storms, btn_emergency_tsunami, btn_emergency_volcanoe, btn_emergency_iamok;
    TextView txt_alarm, txt_rescue, txt_iamok, txt_mylocation;
    String tranctional_type, firstname, token;
    SweetAlertDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dashboard_activity_three, container, false);
        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ques.otf");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        firstname = sharedPreferences.getString("Firstname", "");
        token = sharedPreferences.getString("Session_token", "");
        btn_alarm = (CircleButton) v.findViewById(R.id.alarm);
        btn_rescue_Me = (CircleButton) v.findViewById(R.id.btn_rescue_Me);
        btn_see_something = (CircleButton) v.findViewById(R.id.see_something);
        btn_iamok = (CircleButton) v.findViewById(R.id.btn_iamok);
        txt_alarm = (TextView) v.findViewById(R.id.txt_alarm);
        txt_rescue = (TextView) v.findViewById(R.id.txt_rescue);
        txt_iamok = (TextView) v.findViewById(R.id.txt_sos);
        txt_mylocation = (TextView) v.findViewById(R.id.txt_iamok);
        btn_alarm.setOnClickListener(this);
        btn_rescue_Me.setOnClickListener(this);
        btn_see_something.setOnClickListener(this);
        btn_iamok.setOnClickListener(this);

        return v;
    }

    public static Dashboard_actvity_three newInstance(String text) {

        Dashboard_actvity_three f = new Dashboard_actvity_three();
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
            case R.id.alarm:
                alertDialogBuilder.setTitle("ALERT");
                alertDialogBuilder
                        .setMessage("Please confirm  ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                tranctional_type = ("alarm");
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
            case R.id.btn_rescue_Me:


                Dialog alertDialog_rescue = new Dialog(getActivity());

                alertDialog_rescue.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertDialog_rescue.setContentView(R.layout.rescue_me_activity);
                alertDialog_rescue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                btn_emergency_earthquake = (CircleButton) alertDialog_rescue.findViewById(R.id.emergency_earthquake);
                btn_emergency_flood = (CircleButton) alertDialog_rescue.findViewById(R.id.emergency_flood);
                btn_emergency_storms = (CircleButton) alertDialog_rescue.findViewById(R.id.emergency_storms);
                btn_emergency_tsunami = (CircleButton) alertDialog_rescue.findViewById(R.id.emergency_tsunami);
                btn_emergency_volcanoe = (CircleButton) alertDialog_rescue.findViewById(R.id.emergency_volcanoe);
                btn_emergency_iamok = (CircleButton) alertDialog_rescue.findViewById(R.id.emergency_iam_ok);


                final TextView txt_earthquake = (TextView) alertDialog_rescue.findViewById(R.id.txt_earthquake);
                final TextView txt_flood = (TextView) alertDialog_rescue.findViewById(R.id.txt_flood);
                final TextView txt_storms = (TextView) alertDialog_rescue.findViewById(R.id.txt_storms);
                final TextView tx_tsunami = (TextView) alertDialog_rescue.findViewById(R.id.tx_tsunami);
                final TextView txt_volcanoes = (TextView) alertDialog_rescue.findViewById(R.id.txt_volcanoes);
                final TextView txt_iamok = (TextView) alertDialog_rescue.findViewById(R.id.txt_iamok);

                btn_emergency_earthquake.setOnClickListener(this);
                btn_emergency_flood.setOnClickListener(this);
                btn_emergency_storms.setOnClickListener(this);
                btn_emergency_tsunami.setOnClickListener(this);
                btn_emergency_volcanoe.setOnClickListener(this);
                btn_emergency_iamok.setOnClickListener(this);

                txt_earthquake.setTypeface(tf);
                txt_flood.setTypeface(tf);
                txt_storms.setTypeface(tf);
                tx_tsunami.setTypeface(tf);
                txt_volcanoes.setTypeface(tf);
                txt_iamok.setTypeface(tf);
                alertDialog_rescue.show();


                break;
            case R.id.see_something:
                Toast.makeText(getActivity(), "Under Progress", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_iamok:
                alertDialogBuilder.setTitle("ALERT");
                alertDialogBuilder
                        .setMessage("Please confirm")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                tranctional_type = ("iamok");
                                //   new MyActivityAsync(tranctional_type).execute();
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
        }
    }
}
