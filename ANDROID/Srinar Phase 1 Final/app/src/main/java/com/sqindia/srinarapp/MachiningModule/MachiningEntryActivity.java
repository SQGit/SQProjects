package com.sqindia.srinarapp.MachiningModule;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sqindia.srinarapp.AssemblingModule.AssemblingEntryActivity;
import com.sqindia.srinarapp.ElectroplatingModule.ElectroplateEntryActivity;
import com.sqindia.srinarapp.ElectroplatingModule.ViewElectroplateJob;
import com.sqindia.srinarapp.SuperAdmin.AddSingleParts;
import com.sqindia.srinarapp.SuperAdmin.DashboardActivity;
import com.sqindia.srinarapp.Adapter.CustomAdapter;
import com.sqindia.srinarapp.Adapter.CustomAdapterArrayList;
import com.sqindia.srinarapp.Fonts.FontsOverride;
import com.sqindia.srinarapp.Model.MachineList;
import com.sqindia.srinarapp.R;
import com.sqindia.srinarapp.Utils.Config;
import com.sqindia.srinarapp.Utils.HttpUtils;
import com.sqindia.srinarapp.Utils.Util;
import com.wang.avi.AVLoadingIndicatorView;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MachiningEntryActivity extends Activity{
    MaterialBetterSpinner shift_spn,subpart_spn;
    AutoCompleteTextView ac_machine,ac_operator,ac_mainpart;
    Typeface segoeui;
    String shift,str_empid,m_part,str_sued,str_shift,str_machine,str_operator,str_part,str_subpart,session_token,str_mac_qty,str_remarks,str_dept,str_name,str_role,machinename,str_entrydate;
    LinearLayout back_machine;
    Button upload_entry_machine;
    TextView head_titletv,total_production_tv,used_production_tv,view_machining,part,balance_tv,view_entry,date_edt,desc_tv;
    EditText qty_edt,remarks_edt;
    SharedPreferences.Editor editor;
    MachineList machineList;
    int type_qty,bal_qty;
    private Calendar calendar;
    private int year, month, day;


    ArrayList<String> machine_name_arraylist=new ArrayList<>();
    ArrayList<String> operator_name_arraylist=new ArrayList<>();
    ArrayList<String> main_part_arraylist=new ArrayList<>();
    ArrayList<String> subpart_arraylist=new ArrayList<>();
    CustomAdapterArrayList subpartAdapter;

    Map<String, String> subpart_hash_id = new HashMap<>();
    Map<String, String> subpart_hash_desc = new HashMap<>();
    Map<String, String> subpart_hash_initial = new HashMap<>();
    Map<String, String> subpart_hash_allocate = new HashMap<>();
    Map<String, String> hash_line_order = new HashMap<>();
    String lineorder,selected_initial,selected_id,selected_machine,selected_desc;
    AVLoadingIndicatorView avi_loader;
    Dialog loader_dialog;
    String[] shift_data = {"SHIFT-A (6. 00 AM TO 2.00 PM)", "SHIFT - B (2.00 PM TO 10.00 PM)", "SHIFT -C (10.00 PM TO 6.00 AM)","Genaral Shift -G (8.30 AM TO 5.00 PM)","Shift - D (6.00 AM TO 6.00 PM)","shift -E (6.00 PM TO 6.00 AM)"};


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.machining_entry);

        View v1 = getWindow().getDecorView().getRootView();
        FontsOverride.overrideFonts(MachiningEntryActivity.this, v1);
        segoeui = Typeface.createFromAsset(getAssets(), "fonts/segoeuil.ttf");

        //token get from Login Activity:
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();
        session_token = sharedPreferences.getString("str_sessiontoken", "");
        str_dept=sharedPreferences.getString("userpermission","");
        str_empid=sharedPreferences.getString("emp_id","");
        str_name=sharedPreferences.getString("emp_name","");
        str_role=sharedPreferences.getString("str_role","");

        shift_spn=findViewById(R.id.shift_spn);
        subpart_spn=findViewById(R.id.subpart_spn);
        back_machine=findViewById(R.id.back_machine);
        upload_entry_machine=findViewById(R.id.upload_entry_machine);
        qty_edt=findViewById(R.id.qty_edt);
        total_production_tv=findViewById(R.id.total_production_tv);
        balance_tv=findViewById(R.id.balance_tv);
        used_production_tv=findViewById(R.id.used_production_tv);
        view_machining=findViewById(R.id.view_machining);
        remarks_edt=findViewById(R.id.remarks_edt);
        avi_loader=findViewById(R.id.avi);
        part=findViewById(R.id.part);
        ac_machine=findViewById(R.id.ac_machine);
        ac_operator=findViewById(R.id.ac_operator);
        ac_mainpart=findViewById(R.id.ac_mainpart);
        view_entry=findViewById(R.id.view_entry);
        head_titletv=findViewById(R.id.head_titletv);
        date_edt=findViewById(R.id.date_edt);
        desc_tv=findViewById(R.id.desc_tv);

        shift_spn.setTypeface(segoeui);
        subpart_spn.setTypeface(segoeui);
        total_production_tv.setVisibility(View.GONE);
        used_production_tv.setVisibility(View.GONE);
        balance_tv.setVisibility(View.GONE);
        subpart_spn.setVisibility(View.GONE);
        part.setVisibility(View.GONE);
        upload_entry_machine.setEnabled(true);
        avi_loader.hide();

        ac_machine.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        ac_operator.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        ac_mainpart.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);



        //set default Loader:
        loader_dialog = new Dialog(MachiningEntryActivity.this);
        loader_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loader_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader_dialog.setCancelable(false);
        loader_dialog.setContentView(R.layout.test_loader);



        //get All Machines Name Async:
        if (Util.Operations.isOnline(MachiningEntryActivity.this)) {
            new getMachineAsync().execute();

            //get All Operator Async:
            new getOperatorAsync().execute();

            //get All Main Parts:
            new getMainPartsAsync().execute();


        }else
        {
            new SweetAlertDialog(MachiningEntryActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("No Internet Connectivity")
                    .show();
        }


        //Creating the instance of ArrayAdapter containing list of machine names
        ArrayAdapter<String> adapter_machine = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, machine_name_arraylist);

        ac_machine.setThreshold(1);//will start working from first character
        ac_machine.setAdapter(adapter_machine);//setting the adapter data into the AutoCompleteTextView
        ac_machine.setTextColor(Color.parseColor("#3c3c3c"));


        ac_machine.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(arg1.getWindowToken(), 0);
                str_machine = arg0.getItemAtPosition(arg2).toString();
            }

        });


        view_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent job_mac=new Intent(getApplicationContext(),QcMachineJob.class);
                startActivity(job_mac);
                finish();
            }
        });


        view_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent job_mac=new Intent(getApplicationContext(),ViewMachineJob.class);
                startActivity(job_mac);
                finish();
            }
        });





        //Creating the instance of ArrayAdapter containing list of machine names
        ArrayAdapter<String> adapter_operator = new ArrayAdapter<String>
                (this, R.layout.autocomplete_dialog,R.id.auto_item, operator_name_arraylist);

        ac_operator.setThreshold(1);//will start working from first character
        ac_operator.setAdapter(adapter_operator);//setting the adapter data into the AutoCompleteTextView
        ac_operator.setTextColor(Color.parseColor("#3c3c3c"));

        date_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(999);

            }
        });



        ac_operator.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(arg1.getWindowToken(), 0);
                str_operator = arg0.getItemAtPosition(arg2).toString();
            }
        });


        //Creating the instance of ArrayAdapter containing list of machine names
        ArrayAdapter<String> adapter_mainpart = new ArrayAdapter<String>
                (this, R.layout.autocomplete_dialog,R.id.auto_item, main_part_arraylist);
        ac_mainpart.setThreshold(1);//will start working from first character
        ac_mainpart.setAdapter(adapter_mainpart);//setting the adapter data into the AutoCompleteTextView
        ac_mainpart.setTextColor(Color.parseColor("#3c3c3c"));



        ac_mainpart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(adapterView.getWindowToken(), 0);
                str_part = adapterView.getItemAtPosition(i).toString();
                Log.e("tag","print part----->"+str_part);



                //get All Sub Parts:
                new getSubPartsAsync().execute();
            }
        });



        view_machining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(str_dept.equals("MCECAC"))
                {
                    Intent job_mac=new Intent(getApplicationContext(),QcMachineJob.class);
                    startActivity(job_mac);
                    finish();
                }
                if(str_dept.equals("MC"))
                {
                    Intent job_mac=new Intent(getApplicationContext(),QcMachineJob.class);
                    startActivity(job_mac);
                    finish();
                }
                else if(str_dept.equals("ALL"))
                {
                    Intent job_mac=new Intent(getApplicationContext(),QcMachineJob.class);
                    startActivity(job_mac);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Can't accessed for Machining.....",Toast.LENGTH_LONG).show();
                }
            }
        });

        // Shift Spinner Adapter:
        final CustomAdapter shiftAdapter = new CustomAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, shift_data) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return true;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTypeface(segoeui);
                tv.setTextSize(9);
                tv.setPadding(30, 55, 10, 25);
                if (position == 0) {
                    tv.setTextColor(Color.BLACK);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }


            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextSize(15);
                tv.setPadding(10, 20, 0, 20);
                tv.setTypeface(segoeui);
                if (position == 0) {
                    tv.setTextColor(Color.BLACK);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        shift_spn.setAdapter(shiftAdapter);


        shift_spn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FontsOverride.overrideFonts(MachiningEntryActivity.this, view);

                str_shift = adapterView.getItemAtPosition(i).toString();
                Log.e("tag","iiiiiiiiiiiii"+str_shift);
            if(str_shift.equals("SHIFT-A (6. 00 AM TO 2.00 PM)"))
            {
            shift="A";
            }
            else if(str_shift.equals("SHIFT - B (2.00 PM TO 10.00 PM)"))
            {
                shift="B";
            }
            else if(str_shift.equals("SHIFT -C (10.00 PM TO 6.00 AM)"))
            {
                shift="C";
            }
            else if(str_shift.equals("Genaral Shift -G (8.30 AM TO 5.00 PM)"))
            {
                shift="G";
            }
            else if(str_shift.equals("Shift - D (6.00 AM TO 6.00 PM)"))
            {
                shift="D";
            }
            else if(str_shift.equals("shift -E (6.00 PM TO 6.00 AM)"))
            {
                shift="E";
            }
            }
        });


        subpart_spn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FontsOverride.overrideFonts(MachiningEntryActivity.this, view);

                str_subpart = adapterView.getItemAtPosition(i).toString();
                String[] separated = str_subpart.split(" ");
                m_part=separated[0].trim();


                lineorder=hash_line_order.get(str_subpart);
                selected_id= subpart_hash_id.get(str_subpart);
                selected_desc=subpart_hash_desc.get(str_subpart);
                selected_initial= subpart_hash_initial.get(str_subpart);
                selected_machine= subpart_hash_allocate.get(str_subpart);


                    if(lineorder.contains("M"))
                    {
                        total_production_tv.setVisibility(View.VISIBLE);
                        used_production_tv.setVisibility(View.VISIBLE);
                        balance_tv.setVisibility(View.VISIBLE);
                        total_production_tv.setText("Total Production: "+selected_initial);
                        int final_ = Integer.parseInt(selected_initial) - Integer.parseInt(selected_machine);
                        used_production_tv.setText(String.valueOf(final_));
                        desc_tv.setText("("+ selected_desc+")");
                    }
                    else
                    {
                        total_production_tv.setVisibility(View.VISIBLE);
                        used_production_tv.setVisibility(View.VISIBLE);
                        balance_tv.setVisibility(View.GONE);
                        total_production_tv.setText("Total Production: "+selected_initial);
                        //int final_ = Integer.parseInt(selected_initial) - Integer.parseInt(selected_machine);
                        used_production_tv.setText("No Machining Process");
                        desc_tv.setText("("+ selected_desc+")");
                    }
            }
        });


        back_machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ac_machine.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                Intent back_mac=new Intent(getApplicationContext(),DashboardActivity.class);
                startActivity(back_mac);
                finish();
            }
        });


        upload_entry_machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_mac_qty=qty_edt.getText().toString().trim();
                str_remarks=remarks_edt.getText().toString().trim();
                str_sued=used_production_tv.getText().toString().trim();
                str_entrydate=date_edt.getText().toString().trim();

                try {
                    type_qty=Integer.parseInt(str_mac_qty);
                    bal_qty=Integer.parseInt(str_sued);

                }catch (NumberFormatException e)
                {
                }


                if (Util.Operations.isOnline(MachiningEntryActivity.this)) {

                    if(!date_edt.getText().toString().trim().equals("")) {
                        if (str_shift != null) {
                            if (str_machine != null) {
                                if (str_operator != null) {
                                    if (str_part!=null) {
                                        if (str_subpart != null) {
                                            if (!str_mac_qty.equals("")) {
                                                if (type_qty > bal_qty) {
                                                    Toast.makeText(getApplicationContext(), "Please enter minimum value for Balance Quantity", Toast.LENGTH_LONG).show();
                                                } else {
                                                    if (!str_mac_qty.equals("0")) {
                                                        //allocate Machine Work Parts:
                                                        upload_entry_machine.setEnabled(false);
                                                        new allocateMAchineWorkAsync().execute();

                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "This part Quantity is Completed.", Toast.LENGTH_LONG).show();
                                                    }

                                                }
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Please Select Machine Qty", Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Please Select Sub Part", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Please Select Part", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Please Select Operator", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Please Select Machine", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Please Select Shift", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                        {
                            Toast.makeText(getApplicationContext(), "Please Select Entry Date", Toast.LENGTH_LONG).show();
                        }
                }
                else {
                    new SweetAlertDialog(MachiningEntryActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("No Internet Connectivity")
                            .show();
                }
            }
        });
    }



    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {

            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);

            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            //showDate(year, month+1, day);
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }



    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
       /* date_edt.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));*/
 date_edt.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back_mac=new Intent(getApplicationContext(),DashboardActivity.class);
        startActivity(back_mac);
        finish();
    }



    //@@@@@@@@@@@@@@@@@@@@@@@@ GET ALL MACHINE NAME API
    public class getMachineAsync extends AsyncTask<String, Void, String> {

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
                return jsonStr = HttpUtils.makeRequestToken(Config.WEB_URL_VIEW_MACHINE, session_token);

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loader_dialog.dismiss();
            try {
                JSONObject jo = new JSONObject(s);
                String status = jo.getString("status");


                if (status.equals("true")) {
                    JSONArray jsonArray=jo.getJSONArray("machine");
                    for(int l=0;l<jsonArray.length();l++)
                    {
                        machineList = new MachineList();
                        JSONObject jsonObject =jsonArray.getJSONObject(l);
                        machinename=jsonObject.getString("machinename");
                        machine_name_arraylist.add(machinename);
                    }


                } else {
                    new SweetAlertDialog(MachiningEntryActivity.this, SweetAlertDialog.WARNING_TYPE)
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

    //@@@@@@@@@@@@@@@@@@@@@@@@ CALL VIEW OPERATOR API
    public class getOperatorAsync extends AsyncTask<String, Void, String> {

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
                return jsonStr = HttpUtils.makeRequestToken(Config.WEB_URL_VIEW_OPERATOR_MACHINE, session_token);

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loader_dialog.dismiss();

            try {
                JSONObject jo = new JSONObject(s);
                String status = jo.getString("status");

                if (status.equals("true")) {
                    JSONArray jsonArray=jo.getJSONArray("operator");
                    for(int l=0;l<jsonArray.length();l++)
                    {
                        JSONObject jsonObject =jsonArray.getJSONObject(l);
                        String operator_name=jsonObject.getString("operatorname");
                        operator_name_arraylist.add(operator_name);
                    }



                } else {

                }
            } catch (Exception e) {

            }


        }

    }

    //@@@@@@@@@@@@@@@@@@@@@@@@ CALL MAIN PART API
    @SuppressLint("StaticFieldLeak")
    public class getMainPartsAsync extends AsyncTask<String, Void, String> {

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
                return jsonStr = HttpUtils.makeRequestToken(Config.WEB_URL_MAIN_PART, session_token);

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loader_dialog.dismiss();
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

                }
            } catch (Exception e) {

            }


        }

    }

    //@@@@@@@@@@@@@@@@@@@@@@@@ CALL SUB PART API
    public class getSubPartsAsync extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            avi_loader.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";


            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("mainpart", str_part);
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest1(Config.WEB_URL_SUB_PART,json, session_token);

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            avi_loader.hide();

            try {
                JSONObject jo = new JSONObject(s);
                String status = jo.getString("status");

                if (status.equals("true")) {
                    subpart_spn.setVisibility(View.VISIBLE);
                    part.setVisibility(View.VISIBLE);
                    JSONArray jsonArray=jo.getJSONArray("subparts");
                    subpart_arraylist.clear();
                    subpart_hash_id.clear();
                    subpart_hash_initial.clear();
                    subpart_hash_allocate.clear();
                    for(int l=0;l<jsonArray.length();l++)
                    {


                        JSONObject jsonObject =jsonArray.getJSONObject(l);
                        String subpart_id=jsonObject.getString("partid");
                        String subpart_name=jsonObject.getString("subpart");
                        String partmonth=jsonObject.getString("partmonth");
                        String initial_qty=jsonObject.getString("initialqty");
                        String balance_qty=jsonObject.getString("machinedcompletedqty");
                        String subpartmonth=jsonObject.getString("subpartmonth");


                       subpart_arraylist.add(subpartmonth);
                        //subpart_arraylist.add(subpart_name);
                        subpart_hash_id.put(jsonObject.getString("subpartmonth"),jsonObject.getString("partid"));
                        subpart_hash_desc.put(jsonObject.getString("subpartmonth"),jsonObject.getString("partdescription"));
                        hash_line_order.put(jsonObject.getString("subpartmonth"),jsonObject.getString("lineorder"));
                        subpart_hash_initial.put(jsonObject.getString("subpartmonth"),jsonObject.getString("initialqty"));
                        subpart_hash_allocate.put(jsonObject.getString("subpartmonth"),jsonObject.getString("machinedcompletedqty"));
                    }



                    // Spinner Adapter:
                    subpartAdapter = new CustomAdapterArrayList(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, subpart_arraylist) {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                return true;
                            } else {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            tv.setTypeface(segoeui);
                            tv.setTextSize(9);
                            tv.setPadding(30, 55, 10, 25);
                            if (position == 0) {
                                tv.setTextColor(Color.BLACK);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }


                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            tv.setTextSize(15);
                            tv.setPadding(10, 20, 0, 20);
                            tv.setTypeface(segoeui);
                            if (position == 0) {
                                tv.setTextColor(Color.BLACK);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    subpart_spn.setAdapter(subpartAdapter);



                } else {
                    String msg=jo.getString("message");
                    new SweetAlertDialog(MachiningEntryActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("WARNING MESSAGE!!!")
                            .setContentText(msg)

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

    //@@@@@@@@@@@@@@@@@@@@@@@@ ALLOCATE MACHINE WORK API
    public class allocateMAchineWorkAsync extends AsyncTask<String, Void, String> {

        private ProgressDialog dialog;
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MachiningEntryActivity.this);
            dialog.setMessage("Assign work for "+str_operator+" please Wait.");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";


            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("shift", shift);
                jsonObject.accumulate("partid", selected_id);
                jsonObject.accumulate("qtycompleted", str_mac_qty);
                jsonObject.accumulate("machine_line", str_machine);
                jsonObject.accumulate("operatorname", str_operator);
                jsonObject.accumulate("wcremarks", str_remarks);
                jsonObject.accumulate("mainpart", str_part);
                jsonObject.accumulate("subpart", m_part);
                jsonObject.accumulate("wcentryby",str_empid);
                jsonObject.accumulate("production_date",str_entrydate);

                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest1(Config.WEB_URL_COMPLETE_MACHINE_WORK,json, session_token);

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            try {
                JSONObject jo = new JSONObject(s);
                String status = jo.getString("status");
                String msg =jo.getString("message");

                if (status.equals("true")) {
                    new SweetAlertDialog(MachiningEntryActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("SUCCESS MESSAGE!!!")
                            .setContentText(msg+" for "+str_operator)

                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();

                                    Intent job_mac=new Intent(getApplicationContext(),MachiningEntryActivity.class);
                                    startActivity(job_mac);
                                    finish();

                                }
                            })
                            .show();


                } else {
                    new SweetAlertDialog(MachiningEntryActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("WARNING MESSAGE!!!")
                            .setContentText(msg)

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
}
