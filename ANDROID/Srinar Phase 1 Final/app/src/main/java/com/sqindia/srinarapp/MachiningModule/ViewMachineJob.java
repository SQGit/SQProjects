package com.sqindia.srinarapp.MachiningModule;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import com.sqindia.srinarapp.Fonts.FontsOverride;
import com.sqindia.srinarapp.Model.MachineJobList;
import com.sqindia.srinarapp.R;
import com.sqindia.srinarapp.Utils.Config;
import com.sqindia.srinarapp.Utils.HttpUtils;
import com.sqindia.srinarapp.Utils.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ViewMachineJob extends Activity {

    LinearLayout back,refresh;
    String session_token,str_part;
    SharedPreferences.Editor editor;
    MachineJobList machineJobList;
    private List<MachineJobList> machineJobLists;
    ViewMachineAdapter viewJobListAdapter;
    RecyclerView view_mac_recycler;
    Dialog loader_dialog;
    AutoCompleteTextView ac_part;
    final ArrayList<String> main_part_arraylist=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_machine_work);


        View v1 = getWindow().getDecorView().getRootView();
        FontsOverride.overrideFonts(ViewMachineJob.this, v1);


        //token get from Login Activity:
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();
        session_token = sharedPreferences.getString("str_sessiontoken", "");
        machineJobLists=new ArrayList<>();

        //************* Add Cast:
        back=findViewById(R.id.back);
        view_mac_recycler=findViewById(R.id.view_mac_recycler);
        refresh=findViewById(R.id.refresh);
        ac_part=findViewById(R.id.ac_part);
        ac_part.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back=new Intent(getApplicationContext(),MachiningEntryActivity.class);
                startActivity(back);
                finish();
            }
        });


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back=new Intent(getApplicationContext(),ViewMachineJob.class);
                startActivity(back);
                finish();
            }
        });

        //set default Loader:
        loader_dialog = new Dialog(ViewMachineJob.this);
        loader_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loader_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader_dialog.setCancelable(false);
        loader_dialog.setContentView(R.layout.test_loader);
        loader_dialog.show();

        new getMainPartsAsync().execute();

        if (Util.Operations.isOnline(ViewMachineJob.this)) {

            new viewMachineJobAsync().execute();
        }
        else
        {
            new SweetAlertDialog(ViewMachineJob.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("No Internet Connectivity")
                    .show();
        }


        ArrayAdapter<String> adapter_machine = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, main_part_arraylist);

        ac_part.setThreshold(1);//will start working from first character
        ac_part.setAdapter(adapter_machine);//setting the adapter data into the AutoCompleteTextView
        ac_part.setTextColor(Color.parseColor("#3c3c3c"));


        ac_part.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(arg1.getWindowToken(), 0);
                str_part = arg0.getItemAtPosition(arg2).toString();

                //get All Sub Parts:
                new groupingSubpartsByMain().execute();
            }

        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();


        Intent back=new Intent(getApplicationContext(),MachiningEntryActivity.class);
        startActivity(back);
        finish();
    }




    //@@@@@@@@@@@@@@@@@@@@@@@@ CALL MAIN PART API
    public class getMainPartsAsync extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";


            try {
                JSONObject jsonObject = new JSONObject();
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequestToken(Config.WEB_URL_MAIN_PART, session_token);

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jo = new JSONObject(s);
                String status = jo.getString("status");

                if (status.equals("true")) {
                    main_part_arraylist.clear();

                    JSONArray jsonArray=jo.getJSONArray("mainparts");

                    for(int l=0;l<jsonArray.length();l++)
                    {
                        JSONObject jsonObject =jsonArray.getJSONObject(l);
                        String mainpart_name=jsonObject.getString("mainpart");
                        main_part_arraylist.add(mainpart_name);
                    }


                } else {
                    new SweetAlertDialog(ViewMachineJob.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("WARNING MESSAGE!!!")
                            .setContentText("Server Error")

                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            })
                            .show();
                }
            } catch (Exception e) {

            }
        }
    }



    //@@@@@@@@@@@@@@@@@@@@@@@@ CALL VIEW MACHINE API
    public class viewMachineJobAsync extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            loader_dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";

            try {
                JSONObject jsonObject = new JSONObject();
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequestToken(Config.WEB_URL_GET_MACHINE_ENTRIES, session_token);

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            try {
                JSONObject jo = new JSONObject(s);
                String status = jo.getString("status");

                if (status.equals("true")) {
                    JSONArray jsonArray=jo.getJSONArray("machineworks");
                    machineJobLists.clear();
                    for(int l=0;l<jsonArray.length();l++)
                    {
                        machineJobList = new MachineJobList();
                        JSONObject jsonObject =jsonArray.getJSONObject(l);
                        machineJobList._id=jsonObject.getString("batchid");
                        machineJobList.mac_shift=jsonObject.getString("shift");

                        machineJobList.mac_operator=jsonObject.getString("operatorname");
                        machineJobList.mac_machine=jsonObject.getString("machine_line");
                        machineJobList.mac_partid=jsonObject.getString("partid");
                        machineJobList.mac_part=jsonObject.getString("mainpart");
                        machineJobList.mac_subpart=jsonObject.getString("subpart");
                        machineJobList.mac_worktype=jsonObject.getString("worktype");
                        machineJobList.mac_quantitycompleted=jsonObject.getString("qtycompleted");
                        machineJobList.mac_approved=jsonObject.getString("qtyapproved");
                        machineJobList.mac_rejected=jsonObject.getString("qtyrejected");
                        machineJobList.mac_date=jsonObject.getString("production_date");
                        machineJobList.mac_remarks=jsonObject.getString("wcremarks");
                        machineJobList.mac_wcentryby=jsonObject.getString("wcentryby");
                        machineJobLists.add(machineJobList);
                    }


                    // Setup and Handover data to recyclerview
                    viewJobListAdapter = new ViewMachineAdapter(ViewMachineJob.this, machineJobLists);
                    view_mac_recycler.setAdapter(viewJobListAdapter);
                    viewJobListAdapter.notifyDataSetChanged();
                    view_mac_recycler.setLayoutManager(new LinearLayoutManager(ViewMachineJob.this));
                    loader_dialog.dismiss();
                } else {
                    loader_dialog.dismiss();
                    String message = jo.getString("message");
                    new SweetAlertDialog(ViewMachineJob.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("WARNING MESSAGE!!!")
                            .setContentText(message)

                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();

                                    Intent back_mac=new Intent(getApplicationContext(),MachiningEntryActivity.class);
                                    startActivity(back_mac);
                                    finish();

                                }
                            })
                            .show();
                }
            } catch (Exception e) {

            }


        }

    }



    //@@@@@@@@@@@@@@@@@@@@@@@@ CALL VIEW MACHINE API
    public class groupingSubpartsByMain extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            loader_dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("mainpart", str_part);
                jsonObject.accumulate("worktype", "machining");
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest1(Config.WEB_URL_GROUPING_SUBPART,json, session_token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            try {
                JSONObject jo = new JSONObject(s);
                String status = jo.getString("status");

                if (status.equals("true")) {
                    JSONArray jsonArray=jo.getJSONArray("parts");
                    machineJobLists.clear();
                    viewJobListAdapter.notifyDataSetChanged();
                    for(int l=0;l<jsonArray.length();l++)
                    {
                        machineJobList = new MachineJobList();
                        JSONObject jsonObject =jsonArray.getJSONObject(l);
                        machineJobList._id=jsonObject.getString("batchid");
                        machineJobList.mac_shift=jsonObject.getString("shift");

                        machineJobList.mac_operator=jsonObject.getString("operatorname");
                        machineJobList.mac_machine=jsonObject.getString("machine_line");
                        machineJobList.mac_partid=jsonObject.getString("partid");
                        machineJobList.mac_part=jsonObject.getString("mainpart");
                        machineJobList.mac_subpart=jsonObject.getString("subpart");
                        machineJobList.mac_worktype=jsonObject.getString("worktype");
                        machineJobList.mac_quantitycompleted=jsonObject.getString("qtycompleted");
                        machineJobList.mac_approved=jsonObject.getString("qtyapproved");
                        machineJobList.mac_rejected=jsonObject.getString("qtyrejected");
                        machineJobList.mac_date=jsonObject.getString("wcentrycreatedat");
                        machineJobList.mac_remarks=jsonObject.getString("wcremarks");
                        machineJobList.mac_wcentryby=jsonObject.getString("wcentryby");
                        machineJobLists.add(machineJobList);
                        loader_dialog.dismiss();
                    }


                    // Setup and Handover data to recyclerview
                    viewJobListAdapter = new ViewMachineAdapter(ViewMachineJob.this, machineJobLists);
                    view_mac_recycler.setAdapter(viewJobListAdapter);
                    viewJobListAdapter.notifyDataSetChanged();
                    view_mac_recycler.setLayoutManager(new LinearLayoutManager(ViewMachineJob.this));

                } else {
                    loader_dialog.dismiss();
                    String message = jo.getString("message");
                    new SweetAlertDialog(ViewMachineJob.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("WARNING MESSAGE!!!")
                            .setContentText(message)

                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();

                                    Intent back_mac=new Intent(getApplicationContext(),ViewMachineJob.class);
                                    startActivity(back_mac);
                                    finish();

                                }
                            })
                            .show();
                }
            } catch (Exception e) {

            }


        }

    }
}
