package com.sqindia.srinarapp.ElectroplatingModule;
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
import android.text.InputType;
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

import com.sqindia.srinarapp.MachiningModule.MachiningEntryActivity;
import com.sqindia.srinarapp.MachiningModule.ViewMachineJob;
import com.sqindia.srinarapp.R;
import com.sqindia.srinarapp.SuperAdmin.DashboardActivity;
import com.sqindia.srinarapp.Adapter.CustomAdapter;
import com.sqindia.srinarapp.Adapter.CustomAdapterArrayList;
import com.sqindia.srinarapp.Fonts.FontsOverride;
import com.sqindia.srinarapp.Model.MachineList;
import com.sqindia.srinarapp.Utils.Config;
import com.sqindia.srinarapp.Utils.HttpUtils;
import com.sqindia.srinarapp.Utils.Util;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class ElectroplateEntryActivity extends Activity{
    MaterialBetterSpinner shift_spn,subpart_spn;
    AutoCompleteTextView ac_machine,ac_operator,ac_mainpart;
    Typeface segoeui;
    String selected_mc_approval,shift,emp_id,lineorder,str_sued,str_shift,str_machine,str_operator,str_part,str_subpart,session_token,str_ele_qty,str_remarks,str_dept,str_role,str_name,str_entrydate;
    LinearLayout back_machine;
    Button upload_entry_electro;
    TextView bal_tv,view_electroplat,total_production_tv,used_production_tv,subpart_tv,view_entry,date_edt,desc_tv;
    EditText qty_edt,remarks_edt;
    SharedPreferences.Editor editor;

    ArrayList<String> operator_name_arraylist=new ArrayList<>();
    ArrayList<String> main_part_arraylist=new ArrayList<>();
    ArrayList<String> part_arraylist=new ArrayList<>();
    CustomAdapterArrayList mainpartAdapter,subpartAdapter;
    Map<String, String> subpart_hash_id = new HashMap<>();
    Map<String, String> subpart_hash_initial = new HashMap<>();
    Map<String, String> subpart_hash_desc = new HashMap<>();
    Map<String, String> subpart_hash_mc_approval = new HashMap<>();
    Map<String, String> subpart_hash_allocate = new HashMap<>();
    Map<String, String> hash_line_order = new HashMap<>();
    String m_part,selected_initial,selected_id,selected_ep_completed,selected_desc;
    int type_qty,bal_qty;

    String[] shift_data = {"SHIFT-A (6. 00 AM TO 2.00 PM)", "SHIFT - B (2.00 PM TO 10.00 PM)", "SHIFT -C (10.00 PM TO 6.00 AM)","Shift - D (6.00 AM TO 6.00 PM)","Genaral Shift -G (8.30 AM TO 5.00 PM)"};
    String[] machine_data = {"Line-1  PASSIVATION", "Line-2  NICKEL", "Line-3  TRI-METAL","Line-4  SILVER","Line-5  SELECTIVE GOLD","Line-6  GOLD","Line-7  Washing"};
    Dialog loader_dialog;
    private Calendar calendar;
    private int year, month, day;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.electroplate_part);

        View v1 = getWindow().getDecorView().getRootView();
        FontsOverride.overrideFonts(ElectroplateEntryActivity.this, v1);
        segoeui = Typeface.createFromAsset(getAssets(), "fonts/segoeuil.ttf");

        //token get from Login Activity:
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();
        session_token = sharedPreferences.getString("str_sessiontoken", "");
        emp_id=sharedPreferences.getString("emp_id","");
        str_dept=sharedPreferences.getString("userpermission","");
        str_name=sharedPreferences.getString("emp_name","");
        str_role=sharedPreferences.getString("str_role","");

        shift_spn=findViewById(R.id.shift_spn);
        subpart_spn=findViewById(R.id.subpart_spn);
        back_machine=findViewById(R.id.back_machine);
        upload_entry_electro=findViewById(R.id.upload_entry_electro);
        qty_edt=findViewById(R.id.qnty_edt);
        view_electroplat=findViewById(R.id.view_machining);
        bal_tv=findViewById(R.id.bal_tv);
        desc_tv=findViewById(R.id.desc_tv);
        total_production_tv=findViewById(R.id.total_production_tv);
        used_production_tv=findViewById(R.id.used_production_tv);
        remarks_edt=findViewById(R.id.remarks_edt);
        subpart_tv=findViewById(R.id.subpart_tv);
        ac_machine=findViewById(R.id.ac_machine);
        ac_operator=findViewById(R.id.ac_operator);
        ac_mainpart=findViewById(R.id.ac_mainpart);
        view_entry=findViewById(R.id.view_entry);
        date_edt=findViewById(R.id.date_edt);
        shift_spn.setTypeface(segoeui);
        subpart_spn.setTypeface(segoeui);

        upload_entry_electro.setEnabled(true);

        //set default Loader:
        loader_dialog = new Dialog(ElectroplateEntryActivity.this);
        loader_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loader_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader_dialog.setCancelable(false);
        loader_dialog.setContentView(R.layout.test_loader);

        total_production_tv.setVisibility(View.GONE);
        used_production_tv.setVisibility(View.GONE);
        subpart_spn.setVisibility(View.GONE);
        subpart_tv.setVisibility(View.GONE);
        bal_tv.setVisibility(View.GONE);

        ac_machine.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        ac_operator.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        ac_mainpart.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        if (Util.Operations.isOnline(ElectroplateEntryActivity.this)) {

            //get All Operator Async:
            new getOperatorAsync().execute();

            //get All Main Parts:
            new getMainPartsAsync().execute();

        }else
        {
            new SweetAlertDialog(ElectroplateEntryActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("No Internet Connectivity")
                    .show();
        }



        view_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent del_plate=new Intent(getApplicationContext(),ViewElectroplateJob.class);
                startActivity(del_plate);
                finish();
            }
        });

        date_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(999);

            }
        });


        //Creating the instance of ArrayAdapter containing list of machine names
        ArrayAdapter<String> adapter_machine = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, machine_data);

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


        //Creating the instance of ArrayAdapter containing list of machine names
        ArrayAdapter<String> adapter_operator = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, operator_name_arraylist);

        ac_operator.setThreshold(1);//will start working from first character
        ac_operator.setAdapter(adapter_operator);//setting the adapter data into the AutoCompleteTextView
        ac_operator.setTextColor(Color.parseColor("#3c3c3c"));


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
                (this,R.layout.autocomplete_dialog,R.id.auto_item, main_part_arraylist);

        ac_mainpart.setThreshold(1);//will start working from first character
        ac_mainpart.setAdapter(adapter_mainpart);//setting the adapter data into the AutoCompleteTextView
        ac_mainpart.setTextColor(Color.parseColor("#3c3c3c"));





        ac_mainpart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(adapterView.getWindowToken(), 0);
                str_part = adapterView.getItemAtPosition(i).toString();
                //get All Sub Parts:
                new getSubPartsAsync().execute();
            }
        });


        view_electroplat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(str_dept.equals("PEC"))
                {
                    Intent view_ele=new Intent(getApplicationContext(),QcElectroplateJob.class);
                    startActivity(view_ele);
                    finish();
                }


                if(str_dept.equals("MCECAC"))
                {
                    Intent view_ele=new Intent(getApplicationContext(),QcElectroplateJob.class);
                    startActivity(view_ele);
                    finish();
                }

                else if(str_dept.equals("ALL"))
                {
                    Intent view_ele=new Intent(getApplicationContext(),QcElectroplateJob.class);
                    startActivity(view_ele);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Can't accessed for Electroplating.....",Toast.LENGTH_LONG).show();
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
                FontsOverride.overrideFonts(ElectroplateEntryActivity.this, view);
                str_shift = adapterView.getItemAtPosition(i).toString();
                Log.e("tag", "PRINT SHIFT------>" + str_shift);

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
                else if(str_shift.equals("Shift - D (6.00 AM TO 6.00 PM)"))
                {
                    shift="D";
                }
                else if(str_shift.equals("Genaral Shift -G (8.30 AM TO 5.00 PM)"))
                {
                    shift="G";
                }

            }
        });



        subpart_spn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                FontsOverride.overrideFonts(ElectroplateEntryActivity.this, view);
                str_subpart = adapterView.getItemAtPosition(i).toString();

                String[] separated = str_subpart.split(" ");
                m_part=separated[0].trim();


                    lineorder=hash_line_order.get(str_subpart);
                selected_desc=subpart_hash_desc.get(str_subpart);
                    selected_initial=subpart_hash_initial.get(str_subpart);
                    selected_id=subpart_hash_id.get(str_subpart);
                    selected_mc_approval=subpart_hash_mc_approval.get(str_subpart);
                    selected_ep_completed=subpart_hash_allocate.get(str_subpart);


                    if(lineorder.contains("E"))
                    {
                        if(lineorder.contains("M")&&lineorder.contains("E"))
                        {
                            total_production_tv.setVisibility(View.VISIBLE);
                            used_production_tv.setVisibility(View.VISIBLE);
                            bal_tv.setVisibility(View.VISIBLE);
                            total_production_tv.setText("Total Production: "+selected_mc_approval);
                            int final_=Integer.parseInt(selected_mc_approval)- Integer.parseInt(selected_ep_completed);
                            used_production_tv.setText(String.valueOf(final_));
                            desc_tv.setText("("+ selected_desc+")");
                        }
                        else if(lineorder.contains("E"))
                        {
                            total_production_tv.setVisibility(View.VISIBLE);
                            used_production_tv.setVisibility(View.VISIBLE);
                            bal_tv.setVisibility(View.VISIBLE);
                            total_production_tv.setText("Total Production: "+selected_initial);
                            int final_=Integer.parseInt(selected_initial)- Integer.parseInt(selected_ep_completed);
                            used_production_tv.setText(String.valueOf(final_));
                            desc_tv.setText("("+ selected_desc+")");
                        }
                    }
                    else
                    {
                        total_production_tv.setVisibility(View.VISIBLE);
                        used_production_tv.setVisibility(View.VISIBLE);
                        bal_tv.setVisibility(View.GONE);
                        total_production_tv.setText("Total Production: "+selected_initial);
                        //int final_=Integer.parseInt(selected_initial)- Integer.parseInt(selected_ep_completed);
                        used_production_tv.setText("No Plating Process");
                        desc_tv.setText("("+ selected_desc+")");
                    }
            }
        });


        back_machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back_mac=new Intent(getApplicationContext(),DashboardActivity.class);
                startActivity(back_mac);
                finish();
            }
        });


        upload_entry_electro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                str_ele_qty=qty_edt.getText().toString().trim();
                str_remarks=remarks_edt.getText().toString().trim();
                str_sued=used_production_tv.getText().toString().trim();
                str_entrydate=date_edt.getText().toString().trim();
                try {
                     type_qty=Integer.parseInt(str_ele_qty);
                     bal_qty=Integer.parseInt(str_sued);

                }catch (NumberFormatException e)
                {

                }


                if (Util.Operations.isOnline(ElectroplateEntryActivity.this)) {

                    if(!date_edt.getText().toString().trim().equals("")) {
                        if (str_shift != null) {
                            if (str_machine != null) {
                                if (str_operator != null) {
                                    if (str_part != null) {
                                        if (str_subpart != null) {

                                            if (!str_ele_qty.equals("")) {
                                                if (type_qty > bal_qty) {
                                                    Toast.makeText(getApplicationContext(), "Please enter minimum value for Balance Quantity", Toast.LENGTH_LONG).show();
                                                } else {
                                                    if (!str_ele_qty.equals("0")) {
                                                        //allocate Machine Work Parts:
                                                        upload_entry_electro.setEnabled(false);
                                                        new allocateMAchineWorkAsync().execute();
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "Please enter Valid Quantity", Toast.LENGTH_LONG).show();
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
                    new SweetAlertDialog(ElectroplateEntryActivity.this, SweetAlertDialog.ERROR_TYPE)
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
        date_edt.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back_mac=new Intent(getApplicationContext(),DashboardActivity.class);
        startActivity(back_mac);
        finish();

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
                return jsonStr = HttpUtils.makeRequestToken(Config.WEB_URL_VIEW_OPERATOR_PLATE, session_token);

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
                    JSONArray jsonArray=jo.getJSONArray("operator");
                    for(int l=0;l<jsonArray.length();l++)
                    {
                        JSONObject jsonObject =jsonArray.getJSONObject(l);
                        String operator_name=jsonObject.getString("operatorname");
                        operator_name_arraylist.add(operator_name);
                    }







                } else {
                    /*new SweetAlertDialog(AddMachine.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("WARNING MESSAGE!!!")
                            .setContentText(msg)

                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();

                                }
                            })
                            .show();*/
                }
            } catch (Exception e) {

            }


        }

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
                    /*new SweetAlertDialog(AddMachine.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("WARNING MESSAGE!!!")
                            .setContentText(msg)

                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();

                                }
                            })
                            .show();*/
                }
            } catch (Exception e) {

            }


        }

    }

    //@@@@@@@@@@@@@@@@@@@@@@@@ CALL SUB PART API
    public class getSubPartsAsync extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
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

            try {
                JSONObject jo = new JSONObject(s);
                String status = jo.getString("status");

                if (status.equals("true")) {
                    JSONArray jsonArray=jo.getJSONArray("subparts");
                    subpart_spn.setVisibility(View.VISIBLE);
                    subpart_tv.setVisibility(View.VISIBLE);
                    part_arraylist.clear();
                    subpart_hash_id.clear();
                    subpart_hash_initial.clear();
                    subpart_hash_allocate.clear();
                    for(int l=0;l<jsonArray.length();l++)
                    {
                        JSONObject jsonObject =jsonArray.getJSONObject(l);
                        String subpart_id=jsonObject.getString("partid");
                        String subpart_name=jsonObject.getString("subpart");
                        String initial_qty=jsonObject.getString("machinedapprovedqty");
                        String balance_qty=jsonObject.getString("electroplatedcompletedqty");
                        String subpartmonth=jsonObject.getString("subpartmonth");

                        part_arraylist.add(subpartmonth);
                        subpart_hash_id.put(jsonObject.getString("subpartmonth"),jsonObject.getString("partid"));
                        subpart_hash_desc.put(jsonObject.getString("subpartmonth"),jsonObject.getString("partdescription"));
                    hash_line_order.put(jsonObject.getString("subpartmonth"),jsonObject.getString("lineorder"));
                        subpart_hash_initial.put(jsonObject.getString("subpartmonth"),jsonObject.getString("initialqty"));
                        subpart_hash_mc_approval.put(jsonObject.getString("subpartmonth"),jsonObject.getString("machinedapprovedqty"));
                    subpart_hash_allocate.put(jsonObject.getString("subpartmonth"),jsonObject.getString("electroplatedcompletedqty"));
                }



                    // Spinner Adapter:
                    subpartAdapter = new CustomAdapterArrayList(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, part_arraylist) {
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
                    /*new SweetAlertDialog(AddMachine.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("WARNING MESSAGE!!!")
                            .setContentText(msg)

                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();

                                }
                            })
                            .show();*/
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
            dialog = new ProgressDialog(ElectroplateEntryActivity.this);
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
                jsonObject.accumulate("machine_line", str_machine);
                jsonObject.accumulate("operatorname", str_operator);
                jsonObject.accumulate("qtycompleted",str_ele_qty);
                jsonObject.accumulate("wcremarks", str_remarks);
                jsonObject.accumulate("mainpart", str_part);
                jsonObject.accumulate("subpart", m_part);
                jsonObject.accumulate("wcentryby",emp_id);
                jsonObject.accumulate("production_date",str_entrydate);
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest1(Config.WEB_URL_ALLOCATE_ELECTROPLATE_WORK,json, session_token);

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

                    new SweetAlertDialog(ElectroplateEntryActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("SUCCESS MESSAGE!!!")
                            .setContentText(msg+" for "+str_operator)

                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();

                                    Intent s=new Intent(getApplicationContext(),ElectroplateEntryActivity.class);
                                    startActivity(s);
                                    finish();

                                }
                            })
                            .show();


                } else {
                    new SweetAlertDialog(ElectroplateEntryActivity.this, SweetAlertDialog.WARNING_TYPE)
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
