package com.sqindia.avcdemo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

    private ArrayList<Model> undercarrige;
    private ArrayList<Model> driverseat;
    private ArrayList<Model> frontend;
    private ArrayList<Model> enginecompartment;
    private ArrayList<Model> leftfront;
    private ArrayList<Model> leftrear;
    private ArrayList<Model> rearofvehicle;
    private ArrayList<Model> rightrear;
    private ArrayList<Model> rightsidefront;
    private ArrayList<Model> finalexception;
    private static int SPLASH_TIME_OUT = 5000;
    ProgressDialog mProgressDialog;

    boolean flag = false;
    static Button one_btn, two_btn, three_btn, four_btn, five_btn, six_btn, seven_btn, eight_btn, nine_btn, wholesale_btn;
    ImageView next;
    CheckBox checkBox_tunrnback, checkBox_wholesale,chk_mvac;
    Configuration config;
    EditText date_et;
    private int year, month, day;
    private Calendar calendar;
    private BottomSheetBehavior bottomSheetBehavior;
    SignaturePad mSignaturePad;
    ListView lview;
    static ArrayList<String> firstundr;
    static ArrayList<String> colorcode;

    static Map<Integer, String> underCarriageAdapterStatusMap = new HashMap<Integer, String>();
    static Map<Integer, String> driverAdapterStatusMap = new HashMap<Integer, String>();
    static Map<Integer, String> leftRearAdapterStatusMap = new HashMap<Integer, String>();
    static Map<Integer, String> rearofVehicleStatusMap = new HashMap<Integer, String>();
    static Map<Integer, String> rightRearAdapterStatusMap = new HashMap<Integer, String>();
    static Map<Integer, String> rightSideFrontAdapterStatusMap = new HashMap<Integer, String>();
    static Map<Integer, String> frontEndAdapterStatusMap = new HashMap<Integer, String>();
    static Map<Integer, String> engineCompartmentAdapterStatusMap = new HashMap<Integer, String>();
    static Map<Integer, String> leftFrontAdapterStatusMap = new HashMap<Integer, String>();


    //static ArrayList<String>


    ListView lview4, lview5;
    static ListView rightrear_lv;
    EditText empId, vinno_et, make_et;
    EditText edt_mva, edt_wizard, edt_mileage;
    String str_mva, str_wizard, str_date, str_mileage, str_vin;
    int count;
    Handler hl_txtwatcher = new Handler();
    boolean bl_chk_txt = false;
    LinearLayout framelayout;
    static EditText right_side_treadsize,right_rear_traedsize,left_rear_treadsize,left_front_teaedsize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avis_budget);

        lview4 = (ListView) findViewById(R.id.listview4);
        lview5 = (ListView) findViewById(R.id.listview5);
        framelayout = (LinearLayout) findViewById(R.id.framelayout);
        right_side_treadsize=(EditText)findViewById(R.id.right_side_treadsize);
        right_rear_traedsize=(EditText)findViewById(R.id.right_rear_traedsize);
        left_rear_treadsize=(EditText)findViewById(R.id.left_rear_treadsize);
        left_front_teaedsize=(EditText)findViewById(R.id.left_front_teaedsize);

        date_et = (EditText) findViewById(R.id.date_et);
        firstundr = new ArrayList<>();
        colorcode = new ArrayList<>();
        firstundr.clear();
        colorcode.clear();

        mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);
        leftfront = new ArrayList<Model>();
        undercarrige = new ArrayList<Model>();
        rightsidefront = new ArrayList<Model>();
        frontend = new ArrayList<Model>();
        enginecompartment = new ArrayList<Model>();
        leftrear = new ArrayList<Model>();
        finalexception = new ArrayList<Model>();
        driverseat = new ArrayList<Model>();
        leftrear = new ArrayList<Model>();
        rearofvehicle = new ArrayList<Model>();
        rightrear = new ArrayList<Model>();
        finalexception = new ArrayList<Model>();

        config = getResources().getConfiguration();
        framelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);
            }
        });

        chk_mvac=(CheckBox) findViewById(R.id.chk_mvac);
        checkBox_tunrnback = (CheckBox) findViewById(R.id.cbx_turn);
        checkBox_wholesale = (CheckBox) findViewById(R.id.cbx_wholesale);

        checkBox_tunrnback.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    checkBox_wholesale.setChecked(false);
            }
        });


        checkBox_wholesale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    checkBox_tunrnback.setChecked(false);
            }
        });


        lview = (ListView) findViewById(R.id.listview);
        one_btn = (Button) findViewById(R.id.one_btn);
        empId = (EditText) findViewById(R.id.emp_id);

        vinno_et = (EditText) findViewById(R.id.vinno_et);
        make_et = (EditText) findViewById(R.id.makeid);
        edt_mva = (EditText) findViewById(R.id.edt_mva);
        edt_mileage = (EditText) findViewById(R.id.edt_mileage);
        edt_wizard = (EditText) findViewById(R.id.edt_wizard);
        two_btn = (Button) findViewById(R.id.two_btn);
        three_btn = (Button) findViewById(R.id.three_btn);
        four_btn = (Button) findViewById(R.id.four_btn);
        five_btn = (Button) findViewById(R.id.five_btn);
        six_btn = (Button) findViewById(R.id.six_btn);
        seven_btn = (Button) findViewById(R.id.seven_btn);
        next = (ImageView) findViewById(R.id.next_btn);
        nine_btn = (Button) findViewById(R.id.nine_btn);
        eight_btn = (Button) findViewById(R.id.eight_btn);
        Spinner wheel = (Spinner) findViewById(R.id.spinner1);
        Spinner seat = (Spinner) findViewById(R.id.spinner2);
        Spinner engine = (Spinner) findViewById(R.id.enginesize_spn);
        Spinner checkedby = (Spinner) findViewById(R.id.checked_spn);


        String[] wheel_items = new String[]{"Wheels Type", "Alloy", "Steel"};
        String[] seat_items = new String[]{"Seat Type", "Cloth", "Vinyl", "Leather"};

        String[] engine_size = new String[]{"Engine Size", "4 cyl", "6 cyl", "8 cyl", "Other"};
        String[] checked_by = new String[]{"Checked By", "User1", "User2", "User3"};
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String dateString = sdf.format(date);
        date_et.setText(dateString);

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_signature);
        final TextView tv_sign_hint = (TextView) dialog.findViewById(R.id.tv_sign_hint);
        mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);
        final TextView tv_clear = (TextView) findViewById(R.id.tv_sign_clear);

        final Button sub_btn = (Button) dialog.findViewById(R.id.submit_btn);


            vinno_et.addTextChangedListener(new MyCustomEditTextListener(vinno_et));



        sub_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                tv_sign_hint.setText("");
            }

            @Override
            public void onSigned() {
            }

            @Override
            public void onClear() {
                tv_sign_hint.setText("Sign here");
            }
        });


        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignaturePad.clear();
                tv_sign_hint.setText("Sign here");
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = vinno_et.getText().toString().trim();
                str_mva = edt_mva.getText().toString().trim();
                str_date = date_et.getText().toString().trim();
                str_wizard = edt_wizard.getText().toString().trim();
                str_mileage = edt_mileage.getText().toString().trim();
                str_vin = vinno_et.getText().toString().trim();
                String model=make_et.getText().toString().trim();


                Boolean statusFlag = true;


               //@@@@@@@@@@@@@@@@@ VALIDATION
          if(!checkBox_tunrnback.isChecked()==false || !checkBox_wholesale.isChecked()==false) {
                    if (edt_mva.getText().toString().length() > 0) {
                        if (edt_wizard.getText().toString().length() > 0) {
                            if (edt_mileage.getText().toString().length() > 0) {
                                if (make_et.getText().toString().length() > 0) {
                                    if(!chk_mvac.isChecked()==false) {
                                        if (!MainActivity.underCarriageAdapterStatusMap.containsValue("Select")) {
                                            if (!MainActivity.driverAdapterStatusMap.containsValue("Select")) {
                                                if (!MainActivity.leftRearAdapterStatusMap.containsValue("Select")) {
                                                    if (!MainActivity.rearofVehicleStatusMap.containsValue("Select")) {
                                                        if (!MainActivity.rightRearAdapterStatusMap.containsValue("Select")) {
                                                            if (!MainActivity.rightSideFrontAdapterStatusMap.containsValue("Select")) {
                                                                if (!MainActivity.frontEndAdapterStatusMap.containsValue("Select")) {
                                                                    if (!MainActivity.engineCompartmentAdapterStatusMap.containsValue("Select")) {
                                                                        if (!MainActivity.leftFrontAdapterStatusMap.containsValue("Select")) {
                                                                            if (!mSignaturePad.isEmpty()) {
                                                                                if (empId.getText().toString().length() > 0) {


                                                                                    ItemDetailsWrapper wrapper = new ItemDetailsWrapper(firstundr);
                                                                                    ItemDetailsWrapper wrapper1 = new ItemDetailsWrapper(colorcode);
                                                                                    Intent i = new Intent(getApplicationContext(), SecondPage.class);
                                                                                    i.putExtra("obj", wrapper);
                                                                                    i.putExtra("color", wrapper1);
                                                                                    i.putExtra("Make/Model",model);
                                                                                    startActivity(i);

                                                                                   overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                                                                              } else {
                                                                                    Toast.makeText(MainActivity.this, "Please Enter the Emp ID", Toast.LENGTH_SHORT).show();
                                                                                }

                                                                            } else {
                                                                                Toast.makeText(MainActivity.this, "Signature can not be blank", Toast.LENGTH_SHORT).show();
                                                                            }

                                                                        } else {
                                                                            Toast.makeText(getApplicationContext(), "please select the mandatory details in Left Front", Toast.LENGTH_LONG).show();

                                                                        }

                                                                    } else {
                                                                        Toast.makeText(getApplicationContext(), "please select the mandatory details in Engine Compartment", Toast.LENGTH_LONG).show();
                                                                    }

                                                                } else {
                                                                    Toast.makeText(getApplicationContext(), "please select the mandatory details in Front End", Toast.LENGTH_LONG).show();
                                                                }

                                                            } else {
                                                                Toast.makeText(getApplicationContext(), "please select the mandatory details in Right Side Front", Toast.LENGTH_LONG).show();
                                                            }

                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "please select the mandatory details in Right Rear", Toast.LENGTH_LONG).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "please select the mandatory details in Rear of Vehicle", Toast.LENGTH_LONG).show();
                                                    }
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "please select the mandatory details in Left Rear", Toast.LENGTH_LONG).show();
                                                }

                                            } else {
                                                Toast.makeText(getApplicationContext(), "please select the mandatory details in Driver Seat/Cockpit", Toast.LENGTH_LONG).show();
                                            }


                                        } else {
                                            Toast.makeText(getApplicationContext(), "please select the mandatory details in Under Carriage Front", Toast.LENGTH_LONG).show();

                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "please select MVAC", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "Please scan VIN number or enter your Make/Model", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Invalid Actual Mileage", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Invalid Wizard Mileage", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(MainActivity.this, "Please Enter MVA", Toast.LENGTH_SHORT).show();
                    }
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Select Turnback or Wholesale",Toast.LENGTH_LONG).show();
            }
            }
        });

        final CustomAdapter checked_by_Adapter = new CustomAdapter(MainActivity.this, android.R.layout.simple_spinner_item, checked_by) {
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
                //tv.setTypeface(opensans);
                tv.setTextSize(10);
                tv.setPadding(10, 10, 10, 10);
                if (position == 0) {
                    tv.setTextColor(Color.parseColor("#000000"));
                } else {
                    tv.setTextColor(Color.parseColor("#000000"));
                }
                return view;
            }


            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextSize(9);
                tv.setPadding(0, 0, 0, 0);

                if (position == 0) {
                    tv.setTextColor(Color.parseColor("#000000"));
                } else {
                    tv.setTextColor(Color.parseColor("#000000"));
                }
                return view;
            }
        };
        checkedby.setAdapter(checked_by_Adapter);


        final CustomAdapter engine_size_Adapter = new CustomAdapter(MainActivity.this, android.R.layout.simple_spinner_item, engine_size) {
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
                tv.setTextSize(10);
                tv.setPadding(10, 10, 10, 10);
                if (position == 0) {
                    tv.setTextColor(Color.parseColor("#000000"));
                } else {
                    tv.setTextColor(Color.parseColor("#000000"));
                }
                return view;
            }


            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextSize(9);
                tv.setPadding(0, 0, 0, 0);

                if (position == 0) {
                    tv.setTextColor(Color.parseColor("#000000"));
                } else {
                    tv.setTextColor(Color.parseColor("#000000"));
                }
                return view;
            }
        };
        engine.setAdapter(engine_size_Adapter);


        final CustomAdapter wheel_items_Adapter = new CustomAdapter(MainActivity.this, android.R.layout.simple_spinner_item, wheel_items) {
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
                //tv.setTypeface(opensans);
                tv.setTextSize(13);
                tv.setPadding(10, 10, 10, 10);
                if (position == 0) {
                    tv.setTextColor(getResources().getColor(R.color.dark_black));
                } else {
                    tv.setTextColor(getResources().getColor(R.color.dark_black));
                }
                return view;
            }


            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextSize(10);
                tv.setPadding(0, 0, 0, 0);

                //	tv.setTypeface(opensans);
                if (position == 0) {
                    tv.setTextColor(Color.parseColor("#000000"));
                } else {
                    tv.setTextColor(Color.parseColor("#000000"));
                }
                return view;
            }
        };
        wheel.setAdapter(wheel_items_Adapter);

        final CustomAdapter arrayAdapter = new CustomAdapter(MainActivity.this, android.R.layout.simple_spinner_item, seat_items) {
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
                //tv.setTypeface(opensans);
                tv.setTextSize(13);
                tv.setPadding(10, 10, 10, 10);
                if (position == 0) {
                    tv.setTextColor(Color.parseColor("#000000"));
                } else {
                    tv.setTextColor(Color.parseColor("#000000"));

                }
                return view;
            }


            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextSize(9);
                tv.setPadding(0, 0, 0, 0);

                if (position == 0) {
                    tv.setTextColor(Color.parseColor("#000000"));
                } else {
                    tv.setTextColor(Color.parseColor("#000000"));
                }
                return view;
            }
        };
        seat.setAdapter(arrayAdapter);

        undercarriageAdapter adapter = new undercarriageAdapter(this, undercarrige);
        lview.setAdapter(adapter);


        populateList(); //1st Under Carriage Front
        DriverSeatCockpit(); //2nd Drivers Seat/Cockpit
        LeftRear();  //3rd Left Rear
        RightofVehicle(); //4th Rear of Vehicle
        RightRearList(); //5th Right Rear
        RightSideFront(); //6th Ride Side Front
        FrontEnd(); //7th Front End
        EngineCompartment(); //8th Engine Compartment
        LeftFront(); //9th Left Front
        FinalInspection(); //Extra Final Inspection


        adapter.notifyDataSetChanged();
        rightrear_lv = (ListView) findViewById(R.id.listview2);
        RightRearAdapter adapter1 = new RightRearAdapter(this, rightrear);
        rightrear_lv.setAdapter(adapter1);

        adapter1.notifyDataSetChanged();

        ListView lview2 = (ListView) findViewById(R.id.listview3);
        RightSideFrontAdapter adapter2 = new RightSideFrontAdapter(this, rightsidefront);
        lview2.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();


        DriverSeatsAdapter adapter4 = new DriverSeatsAdapter(this, driverseat);
        lview4.setAdapter(adapter4);
        adapter4.notifyDataSetChanged();


        LeftRearAdapter adapter5 = new LeftRearAdapter(this, leftrear);
        lview5.setAdapter(adapter5);
        adapter5.notifyDataSetChanged();


        ListView lview6 = (ListView) findViewById(R.id.listview6);
        RearofVehicleAdapter adapter3 = new RearofVehicleAdapter(this, rearofvehicle);
        lview6.setAdapter(adapter3);
        adapter3.notifyDataSetChanged();

        ListView lview7 = (ListView) findViewById(R.id.listview7);
        FrontEndAdapter adapter7 = new FrontEndAdapter(this, frontend);

        lview7.setAdapter(adapter7);
        adapter7.notifyDataSetChanged();

        ListView lview8 = (ListView) findViewById(R.id.listview8);
        EngineCompartmentAdapter adapter8 = new EngineCompartmentAdapter(this, enginecompartment);
        lview8.setAdapter(adapter8);
        adapter5.notifyDataSetChanged();

        ListView lview9 = (ListView) findViewById(R.id.listview9);
        LeftFrontAdapter adapter9 = new LeftFrontAdapter(this, leftfront);

        lview9.setAdapter(adapter9);
        adapter9.notifyDataSetChanged();

/*

        ListView lview10 = (ListView) findViewById(R.id.listview10);
        listviewAdapterCheckbox adapter10 = new listviewAdapterCheckbox(this, finalexception);
        lview10.setAdapter(adapter10);
        adapter10.notifyDataSetChanged();
*/


        if (config.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            // Toast.makeText(this, "HIDDEN", Toast.LENGTH_SHORT).show();

        } else if (config.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO)

        {
            Toast.makeText(this, "Please Turn off Physical Keyboard from popup window", Toast.LENGTH_SHORT).show();
            config.hardKeyboardHidden = 2;
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showInputMethodPicker();
            // ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInputFromWindow(etm.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);

        }

    }

    private void populateList() {

        Model item1, item2, item3, item4, item5, item6, item7, item8;


        item1 = new Model("1", "Look Under Front Right", "true", "");
        undercarrige.add(item1);


        item2 = new Model("2", "Look Under Front Left", "true", "₹. 100");
        undercarrige.add(item2);

        item3 = new Model("3", "Look at Sub - Frame", "true", "₹. 50");
        undercarrige.add(item3);

        item4 = new Model("4", "Look Under Left SIDE & CTR RAILS", "true", "₹. 80");
        undercarrige.add(item4);


        item5 = new Model("5", "UnderCarriage -REAR", "true", "₹. 100");
        undercarrige.add(item5);

        item6 = new Model("6", "Rear Body Panel/Floor", "true", "₹. 100");
        undercarrige.add(item6);

        item7 = new Model("7", "Right Rear/Left Rear", "true", "₹. 100");
        undercarrige.add(item7);

        item8 = new Model("8", "Look Under Right-SIDE & CTR RAILS", "true", "₹. 100");
        undercarrige.add(item8);
    }


    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 2nd Drivers Seat/Cockpit @@@@@@@@@@@@@@@@@@@@@@@@@@@@
    private void DriverSeatCockpit() {

        Model item20, item19, item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11, item12, item13, item14, item15, item16, item17, item18;

        item1 = new Model("1", "REMOVE PM STICKER", "false", "");
        driverseat.add(item1);

        item2 = new Model("2", "Horn", "true", "");
        driverseat.add(item2);

        item3 = new Model("3", "TurnSignals", "true", "");
        driverseat.add(item3);

        item4 = new Model("4", "Wipers", "false", "");
        driverseat.add(item4);

        item5 = new Model("5", "Warning Lights-Engine /Tire", "true", "");
        driverseat.add(item5);

        item6 = new Model("6", "Visor / Mirror / Lights", "false", "");
        driverseat.add(item6);

        item7 = new Model("7", "Headliner", "false", "");
        driverseat.add(item7);

        item8 = new Model("8", "All Switches - Locks,Mirror", "false", "");
        driverseat.add(item8);

        item9 = new Model("9", "Overhead Lights", "false", "");
        driverseat.add(item9);
        item10 = new Model("10", "All Knobs/Vents", "false", "");
        driverseat.add(item10);
        item11 = new Model("11", "Radio/Stereo/Speakers", "false", "");
        driverseat.add(item11);
        item12 = new Model("12", "Heat/Ac", "true", "");
        driverseat.add(item12);

        item13 = new Model("13", "Cig Lighhters/Ashtrays", "false", "");
        driverseat.add(item13);
        item14 = new Model("14", "Seats/Power/Manual", "false", "");
        driverseat.add(item14);
        item15 = new Model("15", "Seatbelts", "true", "");
        driverseat.add(item15);
        item16 = new Model("16", "Trim - Door /Seat", "false", "");
        driverseat.add(item16);
        item17 = new Model("17", "Upholstory/Carpet", "false", "");
        driverseat.add(item17);
        item18 = new Model("18", "Cup Holder(rubber insert)", "false", "");
        driverseat.add(item18);
        item19 = new Model("19", "OPEN Trunk/Hood/Fuel Release", "true", "");
        driverseat.add(item19);
        item20 = new Model("20", "Mats TB Required-W/S not", "false", "");
        driverseat.add(item20);
    }


    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 3rd Left Rear @@@@@@@@@@@@@@@@@@@@@@@@@@@@
    private void LeftRear() {

        Model item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11;

        item1 = new Model("1", "Exterior Door/Mldgs/Body", "false", "normal");
        leftrear.add(item1);

        item2 = new Model("2", "LR Glass", "false", "normal");
        leftrear.add(item2);

        item3 = new Model("3", "Upholstery/Carpet", "false", "normal");
        leftrear.add(item3);

        item4 = new Model("4", "Trim-Door,Seat", "false", "normal");
        leftrear.add(item4);

        item5 = new Model("5", "Switches,Window,Lock", "false", "normal");
        leftrear.add(item5);

        item6 = new Model("6", "Seat Belt", "true", "normal");
        leftrear.add(item6);

        item7 = new Model("7", "Mats TB Required- W/S not", "false", "normal");
        leftrear.add(item7);

        item8 = new Model("8", "Tire-See Guidelines/Match", "false", "treadsize");
        leftrear.add(item8);

        item9 = new Model("9", "Wheel/Cover/Nuts/Caps", "false", "normal");
        leftrear.add(item9);

        item10 = new Model("10", "Qtr Pnl - Mldg/Body", "false", "normal");
        leftrear.add(item10);

        item11 = new Model("11", "Lights", "true", "normal");
        leftrear.add(item11);

    }


    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 4th Rear of Vehicle @@@@@@@@@@@@@@@@@@@@@@@@@@@@
    private void RightofVehicle() {

        Model item1, item2, item3, item4, item5, item6, item7, item8, item9;

        item1 = new Model("1", "Bumber", "false", "normal");
        rearofvehicle.add(item1);

        item2 = new Model("2", "Light/Lenses", "true", "normal");
        rearofvehicle.add(item2);

        item3 = new Model("3", "Deck Lid / Trunk / Body", "false", "normal");
        rearofvehicle.add(item3);

        item4 = new Model("4", "Conv't boot / Cargo Net", "false", "normal");
        rearofvehicle.add(item4);

        item5 = new Model("5", "Spare Tire / Jack / Mat", "true", "normal");
        rearofvehicle.add(item5);

        item6 = new Model("6", "Manual-Book / Air kit", "false", "normal");
        rearofvehicle.add(item6);

        item7 = new Model("7", "Roof", "true", "normal");
        rearofvehicle.add(item7);

        item8 = new Model("8", "Antenna / Gas Cap", "false", "normal");
        rearofvehicle.add(item8);

        item9 = new Model("9", "Back Glass", "false", "normal");
        rearofvehicle.add(item9);


    }


    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 5th Right Rear @@@@@@@@@@@@@@@@@@@@@@@@@@@@
    private void RightRearList() {

        Model item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11, item12;

        item1 = new Model("1", "Qtr Pnl - Mldg/Body", "false", "normal");
        rightrear.add(item1);

        item2 = new Model("2", "Lights", "false", "normal");
        rightrear.add(item2);

        item3 = new Model("3", "Tire - See Guidelines/Match", "true", "treadsize");
        rightrear.add(item3);

        item4 = new Model("4", "Wheel/Cover/Nuts/Caps", "false", "normal");
        rightrear.add(item4);

        item5 = new Model("5", "Exterior Door/Mldgs/Body", "false", "normal");
        rightrear.add(item5);

        item6 = new Model("6", "RR Glass", "false", "normal");
        rightrear.add(item6);

        item7 = new Model("7", "Trim-Door,Seat", "false", "normal");
        rightrear.add(item7);

        item8 = new Model("8", "Remove ROME Sticker", "false", "normal");
        rightrear.add(item8);

        item9 = new Model("9", "Switches,Window,Lock", "false", "normal");
        rightrear.add(item9);

        item10 = new Model("10", "Seat Belt", "false", "normal");
        rightrear.add(item10);

        item11 = new Model("11", "Upholstery/Carpet", "false", "normal");
        rightrear.add(item11);

        item12 = new Model("12", "Mats TB Required-W/S not", "false", "normal");
        rightrear.add(item12);

    }


    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 6th Right Side Front @@@@@@@@@@@@@@@@@@@@@@@@@@@@
    private void RightSideFront() {

        Model item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11, item12;

        item1 = new Model("1", "Exterior Door/Mldgs/Body", "false", "normal");
        rightsidefront.add(item1);

        item2 = new Model("2", "Rf Glass", "false", "normal");
        rightsidefront.add(item2);

        item3 = new Model("3", "Upholstery/Carpet", "false", "normal");
        rightsidefront.add(item3);

        item4 = new Model("4", "Mata TB Req W/S not", "false", "normal");
        rightsidefront.add(item4);

        item5 = new Model("5", "Trim-Door,Seat", "false", "normal");
        rightsidefront.add(item5);

        item6 = new Model("6", "Switches,Window,Lock", "false", "normal");
        rightsidefront.add(item6);

        item7 = new Model("7", "Seat Belt", "false", "normal");
        rightsidefront.add(item7);

        item8 = new Model("8", "Windshield (RF Side)", "false", "normal");
        rightsidefront.add(item8);

        item9 = new Model("9", "RF Fender Mldg,Body", "false", "normal");
        rightsidefront.add(item9);

        item10 = new Model("10", "Tire-See Guidelines/Match", "true", "treadsize");
        rightsidefront.add(item10);

        item11 = new Model("11", "Wheel/Cover/Nuts/Caps", "false", "normal");
        rightsidefront.add(item11);

        item12 = new Model("12", "Lights", "false", "normal");
        rightsidefront.add(item12);

    }


    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 7th Front End @@@@@@@@@@@@@@@@@@@@@@@@@@@@
    private void FrontEnd() {

        Model item1, item2, item3, item4, item5;

        item1 = new Model("1", "Headlamps", "false", "");
        frontend.add(item1);

        item2 = new Model("2", "Turn Signals", "false", "");
        frontend.add(item2);

        item3 = new Model("3", "Fog Lamps", "false", "");
        frontend.add(item3);

        item4 = new Model("4", "Bumper", "false", "");
        frontend.add(item4);

        item5 = new Model("4", "Hood/Grille", "false", "");
        frontend.add(item5);

    }


    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 8th Engine Compartment @@@@@@@@@@@@@@@@@@@@@@@@@@@@
    private void EngineCompartment() {

        Model item1, item2, item3, item4, item5;

        item1 = new Model("1", "Oil Filter", "true", "");
        enginecompartment.add(item1);

        item2 = new Model("2", "Battery/Coolant", "true", "");
        enginecompartment.add(item2);

        item3 = new Model("3", "Washer Flud:Blue", "true", "");
        enginecompartment.add(item3);

        item4 = new Model("4", "Dip Sticks,OIL,TRANS", "true", "");
        enginecompartment.add(item4);

        item5 = new Model("5", "oil cap", "true", "");
        enginecompartment.add(item5);

    }


    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 9th Left Front @@@@@@@@@@@@@@@@@@@@@@@@@@@@
    private void LeftFront() {

        Model item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11, item12;

        item1 = new Model("1", "Lights", "false", "normal");
        leftfront.add(item1);
        item2 = new Model("2", "LF Fender Mldg,Body", "false", "normal");
        leftfront.add(item2);
        item3 = new Model("3", "LF Glass", "false", "treadsize");
        leftfront.add(item3);
        item4 = new Model("4", "Exterior Door/Mldgs/Body", "false", "normal");
        leftfront.add(item4);
        item5 = new Model("5", "Wheel/Cover/Nuts/Caps", "false", "normal");
        leftfront.add(item5);
        item6 = new Model("6", "Tire-See Guidelines/Match", "true", "");
        leftfront.add(item6);
        item7 = new Model("7", "Windshield(LF Side)", "false", "normal");
        leftfront.add(item7);
        item8 = new Model("8", "Remove EZ pass/AIR IQ", "false", "normal");
        leftfront.add(item8);
        item9 = new Model("9", "Keys options TB, 1 Set", "true", "");
        leftfront.add(item9);
        item10 = new Model("10", "WHSL option 1 Set, 2 Set", "true", "normal");
        leftfront.add(item10);
        item11 = new Model("9", "Remotes options TB", "true", "normal");
        leftfront.add(item11);
        item12 = new Model("9", "Second Key Found", "false", "normal");
        leftfront.add(item12);

    }


    private void FinalInspection() {

        Model item1, item2, item3, item4;

        item1 = new Model("1", "Verified All Previous repair for Quality", "Fruits", "₹. 200");
        finalexception.add(item1);

        item2 = new Model("2", "Interior & Exterior Clean", "Fruits", "₹. 100");
        finalexception.add(item2);

        item3 = new Model("3", "Checked for Hail Damage", "Vegetable", "₹. 50");
        finalexception.add(item3);

        item4 = new Model("4", "Gas Level min 3/8", "Vegetable", "₹. 80");
        finalexception.add(item4);


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
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        date_et.setText(new StringBuilder().append(month).append("/")
                .append(day).append("/").append(year));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);


        }
        return super.dispatchTouchEvent(ev);
    }

    @SuppressLint("ValidFragment")
    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);

        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm + 1, dd);

        }

        public void populateSetDate(int year, int month, int day) {
            //edittext_offdate.setText(month+"/"+day+"/"+year);
            //date_et .setText(year + "/" + month + "/" + day);

        }
    }


    class getVin_Make extends AsyncTask<String, Void, String> {
        String vin_no;
        int pos;

        //https://api.edmunds.com/api/vehicle/v2/vins/3G5DB03E32S518612?fmt=json&api_key=zucnv9yrgtcgqdnxk7f5xzx9
        //https://api.edmunds.com/api/vehicle/v2/vins/2G1FC3D33C9165616?fmt=json&api_key=zucnv9yrgtcgqdnxk7f5xzx9
        // String web_p1 = "https://api.edmunds.com/api/vehicle/v2/vins/";
        //String web_p2 = "?fmt=json&api_key=zucnv9yrgtcgqdnxk7f5xzx9";

        String api_key = "bka9rsmkb8snppu7efzqtjfs";

        //  String api_key = "zucnv9yrgtcgqdnxk7f5xzx9";

        String web_p1 = "https://api.edmunds.com/api/v1/vehicle/vin/";
        String web_p2 = "/configuration?api_key=";
        String old_api = "26svh9z83ybumwkc3a45bkhu";


        public getVin_Make() {
            //this.vin_no = vinno;
        }

        public getVin_Make(String vinno) {

            this.vin_no = vinno.trim();

        }

        protected void onPreExecute() {
            super.onPreExecute();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(vinno_et.getWindowToken(), 0);
            if (vin_no.length() > 17) {
                if (vin_no.length() == 18) {
                    String valu = vin_no;
                    vin_no = valu.substring(1);
                } else if (vin_no.length() > 18) {
                    cancel(true);
                    bl_chk_txt = false;
                    Toast.makeText(getApplicationContext(), "Invalid VIN Number", Toast.LENGTH_LONG).show();
                }
            }

            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Loading..");
            mProgressDialog.setMessage("Please wait");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... strings) {
            String json = "", jsonStr = "";
            String virtual_url = "http://104.197.80.225:3010/random/turf";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("search", vin_no);
                json = jsonObject.toString();

                return jsonStr = PostService.makeRequest(virtual_url, json);
            } catch (Exception e) {

            }
            return null;
        }



        @Override
        protected void onPostExecute(String jsonStr) {
            super.onPostExecute(jsonStr);
            mProgressDialog.dismiss();
            try {
                JSONObject jo = new JSONObject(jsonStr);

                if (jo.has("value")) {
                    JSONObject val = jo.getJSONObject("value");

                    if (val.getString("success").equals("true")) {


                        String name = val.getString("make");
                        String name1 = val.getString("model");

                        make_et.append(name + " / " + name1);
                        make_et.setEnabled(false);
                        String vinname = name + " / " + name1;


                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect VIN Number", Toast.LENGTH_LONG).show();
                        make_et.setEnabled(true);
                        make_et.setText("");
                        vinno_et.setText("");

                    }
                } else {


                    if (jo.has("status")) {
                        if (jo.getString("message").contains("INTERNAL_SERVER_ERROR")) {
                            String msg = jo.getString("message");
                            //  stopScan();
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            make_et.setText("");
                            // BarcodeCaptureActivity.this.finish();
                        } else {
                            //stopScan();
                            //BarcodeCaptureActivity.this.finish();
                            Toast.makeText(getApplicationContext(), "Incorrect VIN Number", Toast.LENGTH_LONG).show();
                            make_et.setText("");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect VIN Number", Toast.LENGTH_LONG).show();
                        make_et.setText("");
                    }
                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                // BarcodeCaptureActivity.this.finish();

            }

        }
    }


    private class MyCustomEditTextListener implements TextWatcher {
        private int position;


        private EditText et;

        private MyCustomEditTextListener(EditText et) {

            this.et = et;

        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(final CharSequence s, int i, int i2, int i3) {



            hl_txtwatcher.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (s.toString().trim().length() > 16) {
                        vinno_et.setError(null);
                        if (s.toString().trim().length() > 17) {


                            vinno_et.setText(s.toString().trim().substring(1));
                            vinno_et.setSelection(17);
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(vinno_et.getWindowToken(), 0);
                        }
                        check_method(s.toString().trim());
                    } else {
                        bl_chk_txt = false;
                        vinno_et.setError("Invalid Vin");


                    }
                }
            }, 3500);

        }

        @Override
        public void afterTextChanged(final Editable editable) {

        }
    }

    private void check_method(String ss) {
        if (!bl_chk_txt) {
            bl_chk_txt = true;
            vinno_et.setError(null);
            new getVin_Make(ss).execute();

        }
    }
}
