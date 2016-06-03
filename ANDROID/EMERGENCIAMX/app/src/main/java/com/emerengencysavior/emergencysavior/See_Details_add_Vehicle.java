package com.emerengencysavior.emergencysavior;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by RSA on 23-03-2016.
 */
public class See_Details_add_Vehicle extends AppCompatActivity {
    ScrollView scroll_bottomvalue;
    TextView aditionalinformation, txt_sign;
    LinearLayout additional;
    Button btn_back, btn_next;
    Spinner spr_vehicle_type, spr_additional__vehicle_type, spr_make, spr_additional_make;
    //*********************************************************************************************************************************
    EditText  editText_model, editText_color, editText_no_of_doors, editText_year, editText_licensed_plate, editText_reg_no, editText_vehicle_commends;
    String string_spr_vehicle_type, string_spr__make, string_model, string_color, string_no_of_doors, string_year, string_licensed_plate, string_reg_no, string_additional_vehicle_commends;
    //*********************************************************************************************************************************
    EditText editText_additional__model, editText_additional__color, editText_additional__no_of_doors, editText_additional__year, editText_additional__licensed_plate, editText_additional__reg_no, editText_additional_vehicle_commends;
    String string_additional_spr_vehicle_type, string_additional_spr__make, string_additional__model, string_additional__color, string_additional__no_of_doors, string_additional__year, string_additional__licensed_plate, string_additional__reg_no, string_additional__additional_vehicle_commends;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_details_add_vehicle);
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> F O N T S I N T I A L Z A T I O N >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.otf");

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> A C T I O N B A R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setIcon(R.drawable.navicon);
        SpannableStringBuilder SS = new SpannableStringBuilder(Html.fromHtml("<font color='#3c5899'><small><b>ADD VEHICLE</b></small> </font>"));
        SS.setSpan(new CustomTypefaceSpan("#3c5899", font2), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> A C T I O N B A R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> I D E N T I F Y T H E I D  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        aditionalinformation = (TextView) findViewById(R.id.additional_information);
        scroll_bottomvalue = (ScrollView) findViewById(R.id.bottomvalue);
        additional = (LinearLayout) findViewById(R.id.additional_information_view);
        additional.setVisibility(View.GONE);
        txt_sign = (TextView) findViewById(R.id.dss);
        btn_back = (Button) findViewById(R.id.btn_previous);
        btn_next = (Button) findViewById(R.id.btn_next);

        spr_vehicle_type = (Spinner) findViewById(R.id.spr_vehicle__type);
        spr_make = (Spinner) findViewById(R.id.spr_vehicle__Make);
        editText_model = (EditText) findViewById(R.id.edt_vehicle___model);
        editText_color = (EditText) findViewById(R.id.edt_vehicle___color);
        editText_no_of_doors = (EditText) findViewById(R.id.edt_vehicle___no_of_doors);
        editText_year = (EditText) findViewById(R.id.edt_vehicle___year);
        editText_licensed_plate = (EditText) findViewById(R.id.edt_vehicle___licensed_plate);

        editText_vehicle_commends = (EditText) findViewById(R.id.edt_vehicle___additionalcomment);


        spr_additional__vehicle_type = (Spinner) findViewById(R.id.spr_vehicle_additional_type);
        spr_additional_make = (Spinner) findViewById(R.id.spr_vehicle_additional_Make);
        editText_additional__model = (EditText) findViewById(R.id.edt_vehicle_additional__model);
        editText_additional__color = (EditText) findViewById(R.id.edt_vehicle_additional__color);
        editText_additional__no_of_doors = (EditText) findViewById(R.id.edt_vehicle_additional__no_of_doors);
        editText_additional__year = (EditText) findViewById(R.id.edt_vehicle_additional__year);
        editText_additional__licensed_plate = (EditText) findViewById(R.id.edt_vehicle_additional__licensed_plate);
        editText_additional_vehicle_commends = (EditText) findViewById(R.id.edt_vehicle_additional__additionalcomment);

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> I D E N T I F Y T H E I D  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> S E T T Y P E F A C E      >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        aditionalinformation.setTypeface(tf);
        txt_sign.setTypeface(tf);
        btn_back.setTypeface(tf);
        btn_next.setTypeface(tf);
        editText_model.setTypeface(tf);
        editText_color.setTypeface(tf);
        editText_no_of_doors.setTypeface(tf);
        editText_year.setTypeface(tf);
        editText_licensed_plate.setTypeface(tf);
        editText_vehicle_commends.setTypeface(tf);
        editText_additional__model.setTypeface(tf);
        editText_additional__color.setTypeface(tf);
        editText_additional__no_of_doors.setTypeface(tf);
        editText_additional__year.setTypeface(tf);
        editText_additional__licensed_plate.setTypeface(tf);

        editText_additional_vehicle_commends.setTypeface(tf);

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> S E T T Y P E F A C E      >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> L I S T E N E R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        aditionalinformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (additional.getVisibility() == View.VISIBLE) {
                    additional.setVisibility(View.GONE);
                } else {
                    additional.setVisibility(View.VISIBLE);
                    scroll_bottomvalue.post(new Runnable() {
                        @Override
                        public void run() {
                            scroll_bottomvalue.scrollTo(0, scroll_bottomvalue.getBottom());
                            scroll_bottomvalue.fullScroll(scroll_bottomvalue.FOCUS_DOWN);

                        }
                    });

                }
            }
        });
        //************************************************* spinner value **************************************************
        MySpinnerAdapter<CharSequence> adapter = new MySpinnerAdapter<CharSequence>(this,R.layout.spinner_item, Arrays.asList(getResources().getStringArray(R.array.vehicletype)));
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spr_vehicle_type.setAdapter(adapter);
        spr_vehicle_type.setPrompt("Vehicle type");

        spr_vehicle_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if (position > 0) {

                    Log.d("tag", "string_spr_vehicle_type =====>" + selectedItemText);
                    string_spr_vehicle_type = selectedItemText;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        MySpinnerAdapter<CharSequence> adapter_additional = new MySpinnerAdapter<CharSequence>(this,R.layout.spinner_item, Arrays.asList(getResources().getStringArray(R.array.vehicletype)));

        adapter_additional.setDropDownViewResource(R.layout.spinner_item);
        spr_additional__vehicle_type.setAdapter(adapter_additional);
        spr_additional__vehicle_type.setPrompt("Vehicle type");
        spr_additional__vehicle_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if (position > 0) {
                    Log.d("tag", "string_additional_spr_vehicle_type =====>" + selectedItemText);
                    string_additional_spr_vehicle_type = selectedItemText;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        MySpinnerAdapter<CharSequence> adapter_make = new MySpinnerAdapter<CharSequence>(this,R.layout.spinner_item, Arrays.asList(getResources().getStringArray(R.array.carmakecompany)));
        adapter_make.setDropDownViewResource(R.layout.spinner_item);
        spr_make.setAdapter(adapter_make);
        spr_make.setPrompt("Vehicle type");
        spr_make.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if (position > 0) {
                    // Notify the selected item text

                    // kept = selectedItemText.substring(0, selectedItemText.indexOf(","));

                    /// code = "+" + kept.toString();
                    Log.d("tag", "add code =====>" + selectedItemText);
                    string_spr__make = selectedItemText;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        MySpinnerAdapter<CharSequence> adapter_additional_make = new MySpinnerAdapter<CharSequence>(this,R.layout.spinner_item, Arrays.asList(getResources().getStringArray(R.array.carmakecompany)));
        adapter_additional_make.setDropDownViewResource(R.layout.spinner_item);
        spr_additional_make.setAdapter(adapter_additional_make);
        spr_additional_make.setPrompt("Vehicle type");
        spr_additional_make.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if (position > 0) {

                    Log.d("tag", "add code =====>" + selectedItemText);
                    string_additional_spr__make = selectedItemText;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> L I S T E N E R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), See_Details_add_subject.class);
                startActivity(intent);
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> S T R I N G C O N CO D I N A T E ONE  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                string_model = editText_model.getText().toString().trim();
                string_color = editText_color.getText().toString().trim();
                string_no_of_doors = editText_no_of_doors.getText().toString().trim();
                string_year = editText_year.getText().toString().trim();
                string_licensed_plate = editText_licensed_plate.getText().toString().trim();
                string_additional_vehicle_commends = editText_vehicle_commends.getText().toString().trim();
                //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> S T R I N G C O N C O D I N A T E  ONE  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> S T R I N G C O N CO D I N A T E ONE  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                // string_additional__make = editText_additional__make.getText().toString().trim();
                string_additional__model = editText_additional__model.getText().toString().trim();
                string_additional__color = editText_additional__color.getText().toString().trim();
                string_additional__no_of_doors = editText_additional__no_of_doors.getText().toString().trim();
                string_additional__year = editText_additional__year.getText().toString().trim();
                string_additional__licensed_plate = editText_additional__licensed_plate.getText().toString().trim();

                string_additional__additional_vehicle_commends = editText_additional_vehicle_commends.getText().toString().trim();

                //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> S T R I N G C O N C O D I N A T E  ONE  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


                //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> S T R I N G C O N CO D I N A T E  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedpreferences.edit();
                ////$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ R E M O V E  P R E F E R E N C E S$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                edit.remove("string_spr_vehicle_type");
                edit.remove("string_spr__make");
                edit.remove("string_model");
                edit.remove("string_color");
                edit.remove("string_no_of_doors");
                edit.remove("string_year");
                edit.remove("string_licensed_plate");
                edit.remove("string_additional_vehicle_commends");

                edit.remove("string_additional_spr__make");
                edit.remove("string_additional__model");
                edit.remove("string_additional__color");
                edit.remove("string_additional__no_of_doors");
                edit.remove("string_additional__year");
                edit.remove("string_additional__licensed_plate");
                edit.remove("string_additional_spr_vehicle_type");
                edit.remove("string_additional__additional_vehicle_commends");

                ////$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ R E M O V E  P R E F E R E N C E $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                Log.d("tag", "string_spr_vehicle_type" + string_spr_vehicle_type);
                Log.d("tag", "string_make" + string_spr__make);
                Log.d("tag", "string_model" + string_model);
                Log.d("tag", "string_color" + string_color);
                Log.d("tag", "string_no_of_doors" + string_no_of_doors);
                Log.d("tag", "string_year" + string_year);
                Log.d("tag", "string_licensed_plate" + string_licensed_plate);
                Log.d("tag", "string_additional_vehicle_commends" + string_additional_vehicle_commends);

                Log.d("tag", "string_additional_spr_vehicle_type" + string_additional_spr_vehicle_type);
                Log.d("tag", "string_additional_spr__make" + string_additional_spr__make);
                Log.d("tag", "string_additional__model" + string_additional__model);
                Log.d("tag", "string_additional__color" + string_additional__color);
                Log.d("tag", "string_additional__no_of_doors" + string_additional__no_of_doors);
                Log.d("tag", "string_additional__year" + string_additional__year);
                Log.d("tag", "string_additional__licensed_plate" + string_additional__licensed_plate);

                Log.d("tag", "string_additional__additional_vehicle_commends" + string_additional__additional_vehicle_commends);
                ////$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ P U T P R E F E R E N C E  $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                edit.putString("string_spr_vehicle_type", string_spr_vehicle_type);
                edit.putString("string_spr__make", string_spr__make);
                edit.putString("string_model", string_model);
                edit.putString("string_color", string_color);
                edit.putString("string_no_of_doors", string_no_of_doors);
                edit.putString("string_year", string_year);
                edit.putString("string_licensed_plate", string_licensed_plate);
                edit.putString("string_additional_vehicle_commends", string_additional_vehicle_commends);
                edit.putString("string_additional_spr_vehicle_type", string_additional_spr_vehicle_type);
                edit.putString("string_additional_spr__make", string_additional_spr__make);
                edit.putString("string_additional__model", string_additional__model);
                edit.putString("string_additional__color", string_additional__color);
                edit.putString("string_additional__no_of_doors", string_additional__no_of_doors);
                edit.putString("string_additional__year", string_additional__year);
                edit.putString("string_additional__licensed_plate", string_additional__licensed_plate);
                edit.putString("string_additional__additional_vehicle_commends", string_additional__additional_vehicle_commends);
                edit.commit();

                Intent intent = new Intent(getApplicationContext(), Seesomething_PhotoActivity.class);
                startActivity(intent);

            }
        });


    }
}
