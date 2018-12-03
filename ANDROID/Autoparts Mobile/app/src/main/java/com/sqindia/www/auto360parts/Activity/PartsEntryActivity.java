package com.sqindia.www.auto360parts.Activity;
import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.sqindia.www.auto360parts.Fragment.upload.GetFilePathFromDevice;
import com.sqindia.www.auto360parts.Adapter.CustomListAdapter;
import com.sqindia.www.auto360parts.Font.FontsOverride;
import com.sqindia.www.auto360parts.R;
import com.sqindia.www.auto360parts.Utils.Util;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;


public class PartsEntryActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView car_img1_iv, car_img2_iv, vin_img1_iv, vin_img2_iv;
    ImageView car_img1_plus_iv, car_img2_plus_iv, vin_img1_plus_iv, vin_img2_plus_iv;
    CheckBox chk_car1, chk_car2, chk_vin1, chk_vin2;
    Dialog cameraDialog;
    LinearLayout lnr_takepic, lnr_takegallery,back;
    String str_caryear,str_carmake,get_carbrand,get_carmake,get_caryear,get_carmodel,get_carmileage,get_carprice,get_carlocation,get_car1,get_car2,get_vin1,get_vin2;
    String[] carmake_data = {"US", "Japan", "Mexico", "Germany"};
    Typeface helvetica;
    Button submit_next_btn;
    FrameLayout whole_car_pic1, whole_car_pic2;
    Bitmap bitmap;
    String str_date,pathcar1,pathcar2,pathvin1,pathvin2;
    EditText carmodel_edt,mileage_edt,carlocation_edt,price_edt,vinno_edt;
    ArrayList<String> caryear_arraylist=new ArrayList<>();
    ArrayList<String> carmake_arraylist=new ArrayList<>();
    ArrayList<String> carmodel_arraylist=new ArrayList<>();
    MaterialBetterSpinner caryear_spn,make_spn,model_spn;
    // Gallery directory name to store the images or videos
    public static final String GALLERY_DIRECTORY_NAME = "Autoparts360";

    TextView date_edt;
    public static final String PREFS_NAME = "CARPARTS_PREFS";
    String collect_date,collect_carmake,collect_caryear,collect_carbrand,collect_carmodel,collect_carmileage,collect_carprice,collect_carlocation,collect_vinno;
    String collect_car1,collect_car2,collect_vin1,collect_vin2,str_carvinno,str_carmileage,str_carprice,str_location;
    protected static final int TAKE_CAR_PIC1 = 1;
    protected static final int TAKE_CAR_PIC2 = 2;
    protected static final int TAKE_VIN_PIC1 = 3;
    protected static final int TAKE_VIN_PIC2 = 4;

    protected static final int SELECT_CAR_PIC1 = 5;
    protected static final int SELECT_CAR_PIC2 = 6;
    protected static final int SELECT_VIN_PIC1 = 7;
    protected static final int SELECT_VIN_PIC2 = 8;

    Dialog loader_dialog;
    SharedPreferences sharedPrefces;
    SharedPreferences.Editor edit;
    private Calendar calendar;
    private int year, month, day;
    CustomAdapterArrayList carYearAdapter,carMakeAdapter,carModelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parts_entry);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        helvetica = Typeface.createFromAsset(getAssets(), "fonts/helevetical.ttf");
        View v1 = getWindow().getDecorView().getRootView();
        FontsOverride.overrideFonts(PartsEntryActivity.this, v1);

        sharedPrefces = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        edit = sharedPrefces.edit();
        cameraDialog = new Dialog(PartsEntryActivity.this);
        cameraDialog.getWindow().setBackgroundDrawableResource(R.drawable.white_curve_bg);

        //carmodel_edt=findViewById(R.id.carmodel_edt);
        mileage_edt=findViewById(R.id.mileage_edt);
        carlocation_edt=findViewById(R.id.carlocation_edt);
        date_edt=findViewById(R.id.date_edt);
        submit_next_btn = findViewById(R.id.submit_next_btn);
        whole_car_pic1 = findViewById(R.id.whole_car_pic1);
        whole_car_pic2 = findViewById(R.id.whole_car_pic2);
        price_edt=findViewById(R.id.price_edt);
        back=findViewById(R.id.back);
        vinno_edt=findViewById(R.id.vinno_edt);
        caryear_spn=findViewById(R.id.caryear_spn);
        model_spn=findViewById(R.id.model_spn);
        make_spn=findViewById(R.id.make_spn);


        //############ Image View:
        car_img1_iv = findViewById(R.id.car_img1_iv);
        car_img2_iv = findViewById(R.id.car_img2_iv);
        vin_img1_iv = findViewById(R.id.vin_img1_iv);
        vin_img2_iv = findViewById(R.id.vin_img2_iv);


        //############ Check Box:
        chk_car1 = findViewById(R.id.chk_car1);
        chk_car2 = findViewById(R.id.chk_car2);
        chk_vin1 = findViewById(R.id.chk_vin1);
        chk_vin2 = findViewById(R.id.chk_vin2);


        chk_car1.setVisibility(View.GONE);
        chk_car2.setVisibility(View.GONE);
        chk_vin1.setVisibility(View.GONE);
        chk_vin2.setVisibility(View.GONE);

        //############ Add Image View:
        car_img1_plus_iv = findViewById(R.id.car_img1_plus_iv);
        car_img2_plus_iv = findViewById(R.id.car_img2_plus_iv);
        vin_img1_plus_iv = findViewById(R.id.vin_img1_plus_iv);
        vin_img2_plus_iv = findViewById(R.id.vin_img2_plus_iv);


        //set default Loader:
        loader_dialog = new Dialog(PartsEntryActivity.this);
        loader_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loader_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader_dialog.setCancelable(false);
        loader_dialog.setContentView(R.layout.test_loader);

        caryear_spn.setVisibility(View.GONE);
        make_spn.setVisibility(View.GONE);
        model_spn.setVisibility(View.GONE);

        car_img1_iv.setOnClickListener(this);
        car_img2_iv.setOnClickListener(this);
        vin_img1_iv.setOnClickListener(this);
        vin_img2_iv.setOnClickListener(this);

        date_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(999);
            }
        });

        //check value:
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        edit = sharedPreferences.edit();
        collect_date=sharedPreferences.getString("store_date","");
        collect_carmake = sharedPreferences.getString("store_carmake", "");
        collect_caryear=sharedPreferences.getString("store_caryear","");
        Log.e("tag","print year---------------->"+collect_caryear);
        collect_carbrand=sharedPreferences.getString("store_carbrand","");
        collect_carmodel=sharedPreferences.getString("store_carmodel","");
        collect_carmileage=sharedPreferences.getString("store_carmileage","");
        collect_carprice=sharedPreferences.getString("store_carprice","");
        collect_carlocation=sharedPreferences.getString("store_carlocation","");
        collect_vinno=sharedPreferences.getString("store_vinno","");

        //image value:
        collect_car1=sharedPreferences.getString("store_car1","");
        collect_car2=sharedPreferences.getString("store_car2","");
        collect_vin1=sharedPreferences.getString("store_vin1","");
        collect_vin2=sharedPreferences.getString("store_vin2","");

        if(collect_date!=null)
        {
            date_edt.setText(collect_date);
        }

        if(!collect_caryear.isEmpty())
        {
            Log.e("tag","come inside---------------->");
            caryear_spn.setVisibility(View.VISIBLE);
            //Initializing an ArrayAdapter
        }
        else
        {

        }


        if(collect_carbrand!=null)
        {
            //carbrand_edt.setText(collect_carbrand);
        }

        if(collect_carmodel!=null)
        {
            //carmodel_edt.setText(collect_carmodel);
        }

        if(collect_carmileage!=null)
        {
            mileage_edt.setText(collect_carmileage);
        }

        if(collect_carprice!=null)
        {
            price_edt.setText(collect_carprice);
        }


        if(collect_carlocation!=null)
        {
            carlocation_edt.setText(collect_carlocation);
        }



        if(collect_car1!=null) {
            chk_car1.setChecked(true);
            File imgFile = new File(collect_car1);

            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                car_img1_iv.setImageBitmap(myBitmap);
                chk_car1.setVisibility(View.VISIBLE);
                car_img1_plus_iv.setVisibility(View.GONE);
            }
        }

        if(collect_car2!=null) {
            chk_car2.setChecked(true);
            File imgFile = new File(collect_car2);

            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                chk_car2.setVisibility(View.VISIBLE);
                car_img2_iv.setImageBitmap(myBitmap);
                car_img2_plus_iv.setVisibility(View.GONE);
            }
        }

        if(collect_vin1!=null) {

            chk_vin1.setChecked(true);
            File imgFile = new File(collect_vin1);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                chk_vin1.setVisibility(View.VISIBLE);
                vin_img1_iv.setImageBitmap(myBitmap);
                vin_img1_plus_iv.setVisibility(View.GONE);
            }
        }

        if(collect_vin2!=null) {
            chk_vin2.setChecked(true);

            File imgFile = new File(collect_vin2);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                vin_img2_iv.setImageBitmap(myBitmap);
                chk_vin2.setVisibility(View.VISIBLE);
                vin_img2_plus_iv.setVisibility(View.GONE);
            }
        }


        if(collect_vinno!=null)
        {
            vinno_edt.setText(collect_vinno);
        }


        if (Util.Operations.isOnline(PartsEntryActivity.this)) {
            new getCarYear().execute();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please Check Internet Connectivity",Toast.LENGTH_LONG).show();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back=new Intent(getApplicationContext(),DashboardActivity.class);
                startActivity(back);
                finish();
            }
        });


        caryear_spn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                str_caryear = adapterView.getItemAtPosition(i).toString();
                Log.e("tag", "str_year------>" + str_caryear);
                if (Util.Operations.isOnline(PartsEntryActivity.this)) {
                    new getCarMake(str_caryear).execute();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please check Internet Connectivity",Toast.LENGTH_LONG).show();
                }
            }
        });



        make_spn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                str_carmake = adapterView.getItemAtPosition(i).toString();
                Log.e("tag", "str_make------>" + str_carmake);

               if (Util.Operations.isOnline(PartsEntryActivity.this)) {
                   new getCarModel(str_caryear,str_carmake).execute();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please check Internet Connectivity",Toast.LENGTH_LONG).show();
                }
            }
        });


        model_spn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                get_carmodel = adapterView.getItemAtPosition(i).toString();
                Log.e("tag", "str_model------>" + get_carmodel);
            }
        });


        submit_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    str_date=date_edt.getText().toString();
                    str_carmileage=mileage_edt.getText().toString();
                    str_carprice=price_edt.getText().toString();
                    str_location=carlocation_edt.getText().toString();
                    str_carvinno=vinno_edt.getText().toString().trim();

                }catch (Exception e)
                {

                }

               if (Util.Operations.isOnline(getApplicationContext())){
                    if(!date_edt.getText().toString().equals("")) {
                        if (str_caryear!=null) {
                            if (str_carmake!=null) {
                                if (get_carmodel!=null) {
                                    if (!mileage_edt.getText().toString().equals("")) {
                                        if (!carlocation_edt.getText().toString().equals("")) {


                                            if( car_img1_iv.getDrawable()!=null || car_img2_iv.getDrawable()!=null){
                                               if((vin_img1_iv.getDrawable()!=null) || (vin_img2_iv.getDrawable()!=null) || (!vinno_edt.getText().toString().trim().equals(""))){
                                                    Intent takeparts = new Intent(getApplicationContext(), UploadCarParts.class);
                                                    edit.putString("store_date", str_date);
                                                    edit.putString("store_price", str_carprice);
                                                    edit.putString("store_caryear",str_caryear);
                                                    edit.putString("store_carbrand", str_carmake);
                                                    edit.putString("store_carmodel",get_carmodel);
                                                    edit.putString("store_carmileage", str_carmileage);
                                                    edit.putString("store_carprice",str_carprice);
                                                    edit.putString("store_carlocation", str_location);
                                                    edit.putString("store_vinno",str_carvinno);
                                                    edit.commit();
                                                    startActivity(takeparts);
                                                    finish();
                                               }
                                                else
                                                {
                                                    Toast.makeText(getApplicationContext(), "Please Capture atleast one Vin or Enter Vin No..", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                            else
                                            {
                                                Toast.makeText(getApplicationContext(), "Please Capture atleast one Whole Car Image..", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(), "Car Location Empty..", Toast.LENGTH_LONG).show();
                                        }

                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "Car Mileage Empty..", Toast.LENGTH_LONG).show();
                                    }

                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Car Model Empty..", Toast.LENGTH_LONG).show();
                                }



                            } else {
                                Toast.makeText(getApplicationContext(), "Car Make Empty..", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Car Year Empty..", Toast.LENGTH_LONG).show();
                        }
                    }

                    else
                    {
                        Toast.makeText(getApplicationContext(),"Select Date..",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                   Toast.makeText(getApplicationContext(),"Check Internet Connection",Toast.LENGTH_LONG).show();
                }

            }
        });





        //WHOLE CAR PIC1 Checkbox Condition--------------------------------------------------->
        chk_car1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (!chk_car1.isChecked()) {
                    edit.remove("store_car1");
                    edit.commit();
                    chk_car1.setChecked(false);
                    chk_car1.setVisibility(View.GONE);
                    car_img1_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    car_img1_iv.setEnabled(true);
                    car_img1_plus_iv.setVisibility(View.VISIBLE);
                    car_img1_iv.setImageResource(0);
                } else {
                    chk_car1.setChecked(true);
                    chk_car1.setVisibility(View.VISIBLE);
                    car_img1_iv.setEnabled(false);
                    car_img1_plus_iv.setVisibility(View.GONE);
                }
            }
        });


        //WHOLE CAR PIC2 Checkbox Condition--------------------------------------------------->
        chk_car2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (!chk_car2.isChecked()) {
                    edit.remove("store_car2");
                    edit.commit();
                    chk_car2.setChecked(false);
                    chk_car2.setVisibility(View.GONE);
                    car_img2_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    car_img2_iv.setEnabled(true);
                    car_img2_plus_iv.setVisibility(View.VISIBLE);
                    car_img2_iv.setImageResource(0);
                } else {
                    chk_car2.setChecked(true);
                    chk_car2.setVisibility(View.VISIBLE);
                    car_img2_iv.setEnabled(false);
                    car_img2_plus_iv.setVisibility(View.GONE);
                }

            }
        });


        //VIN CAR PIC1 Checkbox Condition--------------------------------------------------->
        chk_vin1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if (!chk_vin1.isChecked()) {
                    edit.remove("store_vin1");
                    edit.commit();
                    chk_vin1.setChecked(false);
                    chk_vin1.setVisibility(View.GONE);
                    vin_img1_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    vin_img1_iv.setEnabled(true);
                    vin_img1_plus_iv.setVisibility(View.VISIBLE);
                    vin_img1_iv.setImageResource(0);
                } else {
                    chk_vin1.setChecked(true);
                    chk_vin1.setVisibility(View.VISIBLE);
                    vin_img1_iv.setEnabled(false);
                    vin_img1_plus_iv.setVisibility(View.GONE);
                }

            }
        });


//VIN CAR PIC2 Checkbox Condition--------------------------------------------------->
        chk_vin2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (!chk_vin2.isChecked()) {
                    edit.remove("store_vin2");
                    edit.commit();
                    chk_vin2.setChecked(false);
                    chk_vin2.setVisibility(View.GONE);
                    vin_img2_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    vin_img2_iv.setEnabled(true);
                    vin_img2_plus_iv.setVisibility(View.VISIBLE);
                    vin_img2_iv.setImageResource(0);
                } else {
                    chk_vin2.setChecked(true);
                    chk_vin2.setVisibility(View.VISIBLE);
                    vin_img2_iv.setEnabled(false);
                    vin_img2_plus_iv.setVisibility(View.GONE);
                }
            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back=new Intent(getApplicationContext(),DashboardActivity.class);
        startActivity(back);
        finish();
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
        date_edt.setText(new StringBuilder().append(month).append("-").append(day).append("-").append(year));
    }

    //call Camera Intent Dialog for all Imageview:
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // WHOLE CAR PIC ONE
            case R.id.car_img1_iv:


                cameraDialog.setContentView(R.layout.camera_dialog);
                lnr_takepic = cameraDialog.findViewById(R.id.lnr_takepic);
                lnr_takegallery = cameraDialog.findViewById(R.id.lnr_takegallery);

                // if button is clicked, close the custom dialog
                lnr_takepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();

                        if(checkPermission())
                        {
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, TAKE_CAR_PIC1);
                        }



                    }
                });


                lnr_takegallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();



                        if (checkPermission()) {
                            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            pickIntent.setType("image/*");
                            startActivityForResult(pickIntent, SELECT_CAR_PIC1);
                        }


                    }
                });
                cameraDialog.show();

                break;


            // WHOLE CAR PIC TWO
            case R.id.car_img2_iv:

                cameraDialog.setContentView(R.layout.camera_dialog);
                lnr_takepic = cameraDialog.findViewById(R.id.lnr_takepic);
                lnr_takegallery = cameraDialog.findViewById(R.id.lnr_takegallery);

                // if button is clicked, close the custom dialog
                lnr_takepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();

                        if(checkPermission()) {
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, TAKE_CAR_PIC2);
                        }
                    }
                });


                lnr_takegallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();

                        if (checkPermission()) {
                            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            pickIntent.setType("image/*");
                            startActivityForResult(pickIntent, SELECT_CAR_PIC2);
                        }


                    }
                });
                cameraDialog.show();

                break;


            // WHOLE VIN PIC ONE
            case R.id.vin_img1_iv:
                vin_img1_iv.setEnabled(true);

                cameraDialog.setContentView(R.layout.camera_dialog);
                lnr_takepic = cameraDialog.findViewById(R.id.lnr_takepic);
                lnr_takegallery = cameraDialog.findViewById(R.id.lnr_takegallery);

                // if button is clicked, close the custom dialog
                lnr_takepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();


                        if(checkPermission()) {
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, TAKE_VIN_PIC1);
                        }

                    }
                });


                lnr_takegallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();

                        if (checkPermission()) {
                            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            pickIntent.setType("image/*");
                            startActivityForResult(pickIntent, SELECT_VIN_PIC1);
                        }


                    }
                });
                cameraDialog.show();

                break;


            // WHOLE VIN PIC TWO
            case R.id.vin_img2_iv:
                vin_img1_iv.setEnabled(true);
                cameraDialog.setContentView(R.layout.camera_dialog);
                lnr_takepic = cameraDialog.findViewById(R.id.lnr_takepic);
                lnr_takegallery = cameraDialog.findViewById(R.id.lnr_takegallery);

                // if button is clicked, close the custom dialog
                lnr_takepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();

                        if(checkPermission()) {

                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, TAKE_VIN_PIC2);
                        }

                    }
                });


                lnr_takegallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();

                        if (checkPermission()) {
                            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            pickIntent.setType("image/*");
                            startActivityForResult(pickIntent, SELECT_VIN_PIC2);
                        }
                    }
                });
                cameraDialog.show();

                break;
            default:
                break;
        }

    }

    private Uri getOutputMediaFileUri(Context applicationContext, File file) {

        return FileProvider.getUriForFile(getApplicationContext(), this.getPackageName() + ".provider", file);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        if ((result == PackageManager.PERMISSION_GRANTED) && (result1 == PackageManager.PERMISSION_GRANTED) && (result2 == PackageManager.PERMISSION_GRANTED)) {
            return true;
        } else {
            ActivityCompat.requestPermissions(PartsEntryActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
            return false;
        }
    }
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        bitmap = null;
        pathcar1 = null;
        pathcar2=null;
        pathvin1=null;
        pathvin2=null;


        if (resultCode == RESULT_OK && requestCode == TAKE_CAR_PIC1) {

            bitmap = (Bitmap) data.getExtras().get("data");
            File sd = Environment.getExternalStorageDirectory();
            File imagepath = new File(sd.getAbsolutePath() + File.separator +
                    "FolderName" + File.separator + "InsideFolderName");

            if (!imagepath.isDirectory())
            {
                imagepath.mkdirs();
            }

            File mediaFile1 = new File(imagepath + File.separator + "img_" +
                    System.currentTimeMillis() + ".jpg");

            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(mediaFile1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            pathcar1=mediaFile1.getAbsolutePath();
            Log.e("tag","1111111111111111111111---------------->"+pathcar1);

                car_img1_iv.setImageBitmap(bitmap);

                chk_car1.setChecked(true);
                chk_car1.setVisibility(View.VISIBLE);
                car_img1_plus_iv.setVisibility(View.GONE);
                edit.putString("store_car1",pathcar1);
                edit.commit();



        } else if (resultCode == RESULT_OK && requestCode == TAKE_CAR_PIC2) {

            bitmap = (Bitmap) data.getExtras().get("data");
            File sd = Environment.getExternalStorageDirectory();
            File imagepath = new File(sd.getAbsolutePath() + File.separator +
                    "FolderName" + File.separator + "InsideFolderName");

            if (!imagepath.isDirectory())
            {
                imagepath.mkdirs();
            }

            File mediaFile2 = new File(imagepath + File.separator + "img_" +
                    System.currentTimeMillis() + ".jpg");

            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(mediaFile2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


                car_img2_iv.setImageBitmap(bitmap);
                chk_car2.setChecked(true);
                chk_car2.setVisibility(View.VISIBLE);
                car_img2_plus_iv.setVisibility(View.GONE);
                pathcar2=mediaFile2.getAbsolutePath();
                edit.putString("store_car2",pathcar2);
                edit.commit();



        } else if (resultCode == RESULT_OK && requestCode == TAKE_VIN_PIC1) {

            bitmap = (Bitmap) data.getExtras().get("data");
            File sd = Environment.getExternalStorageDirectory();
            File imagepath = new File(sd.getAbsolutePath() + File.separator +
                    "FolderName" + File.separator + "InsideFolderName");

            if (!imagepath.isDirectory())
            {
                imagepath.mkdirs();
            }

            File mediaFile3 = new File(imagepath + File.separator + "img_" +
                    System.currentTimeMillis() + ".jpg");

            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(mediaFile3);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            vin_img1_iv.setImageBitmap(bitmap);
                chk_vin1.setChecked(true);
                chk_vin1.setVisibility(View.VISIBLE);
                vin_img1_plus_iv.setVisibility(View.GONE);
                pathvin1=mediaFile3.getAbsolutePath();
                edit.putString("store_vin1",pathvin1);
                edit.commit();


        } else if (resultCode == RESULT_OK && requestCode == TAKE_VIN_PIC2) {

            bitmap = (Bitmap) data.getExtras().get("data");
            File sd = Environment.getExternalStorageDirectory();
            File imagepath = new File(sd.getAbsolutePath() + File.separator +
                    "FolderName" + File.separator + "InsideFolderName");

            if (!imagepath.isDirectory())
            {
                imagepath.mkdirs();
            }

            File mediaFile4 = new File(imagepath + File.separator + "img_" +
                    System.currentTimeMillis() + ".jpg");

            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(mediaFile4);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            vin_img2_iv.setImageBitmap(bitmap);
                chk_vin2.setChecked(true);
                chk_vin2.setVisibility(View.VISIBLE);
                vin_img2_plus_iv.setVisibility(View.GONE);
                pathvin2=mediaFile4.getAbsolutePath();
                edit.putString("store_vin2",pathvin2);
                edit.commit();


        } else if (resultCode == RESULT_OK && requestCode == SELECT_CAR_PIC1) {


            try {
                Uri selectedMediaUri = data.getData();
                pathcar1 = GetFilePathFromDevice.getPath(getApplicationContext(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedMediaUri);
                    car_img1_iv.setImageBitmap(bitmap);
                    car_img1_plus_iv.setVisibility(View.GONE);
                    chk_car1.setChecked(true);
                    chk_car1.setVisibility(View.VISIBLE);
                    edit.putString("store_car1",pathcar1);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }

        } else if (resultCode == RESULT_OK && requestCode == SELECT_CAR_PIC2) {
            try {
                Uri selectedMediaUri = data.getData();
                pathcar2 = GetFilePathFromDevice.getPath(getApplicationContext(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedMediaUri);
                    car_img2_iv.setImageBitmap(bitmap);
                    car_img2_plus_iv.setVisibility(View.GONE);
                    chk_car2.setChecked(true);
                    chk_car2.setVisibility(View.VISIBLE);

                    edit.putString("store_car2",pathcar2);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }

        } else if (resultCode == RESULT_OK && requestCode == SELECT_VIN_PIC1) {
            try {
                Uri selectedMediaUri = data.getData();
                pathvin1 = GetFilePathFromDevice.getPath(getApplicationContext(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedMediaUri);
                    vin_img1_iv.setImageBitmap(bitmap);
                    vin_img1_plus_iv.setVisibility(View.GONE);
                    chk_vin1.setChecked(true);
                    chk_vin1.setVisibility(View.VISIBLE);
                    edit.putString("store_vin1",pathvin1);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (NullPointerException e) {

            }

        } else if (resultCode == RESULT_OK && requestCode == SELECT_VIN_PIC2) {
            try {
                Uri selectedMediaUri = data.getData();
                pathvin2 = GetFilePathFromDevice.getPath(getApplicationContext(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedMediaUri);
                    vin_img2_iv.setImageBitmap(bitmap);
                    vin_img2_plus_iv.setVisibility(View.GONE);
                    chk_vin2.setChecked(true);
                    chk_vin2.setVisibility(View.VISIBLE);
                    edit.putString("store_vin2",pathvin2);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();

                }

            } catch (NullPointerException e) {

            }
        }
    }








    //GET YEAR API CALL: ---------------------------------------------------------------------->
    public class getCarYear extends AsyncTask<String, String, String> {
        private ProgressDialog dialog;
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(PartsEntryActivity.this);
            dialog.setMessage("Loading Year..., please wait.");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://carqueryapi.com/api/0.3/?cmd=getYears")
                    .get()
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "9a479868-31a0-4760-a9d8-7d9007955ad5")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
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
                caryear_arraylist.clear();
                JSONObject object=jo.getJSONObject("Years");
                String min_year = object.getString("min_year");
                String max_year = object.getString("max_year");


                int minyear=Integer.parseInt(min_year);
                int maxyaer=Integer.parseInt(max_year);


                for(int i=maxyaer;i>=minyear;i--)
                {

                    caryear_arraylist.add(String.valueOf(i));
                }

                caryear_spn.setVisibility(View.VISIBLE);

// Spinner Adapter:
                carYearAdapter = new CustomAdapterArrayList(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, caryear_arraylist) {
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
                        tv.setTypeface(helvetica);
                        tv.setTextSize(23);
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
                        tv.setTextSize(23);
                        tv.setPadding(10, 20, 0, 20);
                        tv.setTypeface(helvetica);
                        if (position == 0) {
                            tv.setTextColor(Color.BLACK);
                        } else {
                            tv.setTextColor(Color.BLACK);
                        }
                        return view;
                    }
                };
                caryear_spn.setAdapter(carYearAdapter);


                if (dialog.isShowing()) {
                    dialog.dismiss();
                }


            } catch (Exception e) {

            }
        }
    }

    //GET MAKE API CALL: ---------------------------------------------------------------------->
    public class getCarMake extends AsyncTask<String, String, String> {
        private ProgressDialog dialog1;
        String year_str;
        public getCarMake(String str_caryear) {
        this.year_str=str_caryear;

        }

        protected void onPreExecute() {
            super.onPreExecute();
            dialog1 = new ProgressDialog(PartsEntryActivity.this);
            dialog1.setMessage("Loading Car Make..., please wait.");
            dialog1.show();
        }

        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://www.carqueryapi.com/api/0.3/?cmd=getMakes&year="+year_str)
                    .get()
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "9a479868-31a0-4760-a9d8-7d9007955ad5")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (dialog1.isShowing()) {
                dialog1.dismiss();
            }


            try {
                JSONObject jo = new JSONObject(s);
                carmake_arraylist.clear();
                JSONArray jsonArray=jo.getJSONArray("Makes");

                for(int l=0;l<jsonArray.length();l++)
                {
                    JSONObject object=jsonArray.getJSONObject(l);
                    String make_id = object.getString("make_id");
                    String make_display = object.getString("make_display");
                    carmake_arraylist.add(make_display);
                }
                make_spn.setVisibility(View.VISIBLE);
                carMakeAdapter = new CustomAdapterArrayList(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, carmake_arraylist) {
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
                        tv.setTypeface(helvetica);
                        tv.setTextSize(23);
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
                        tv.setTextSize(23);
                        tv.setPadding(10, 20, 0, 20);
                        tv.setTypeface(helvetica);
                        if (position == 0) {
                            tv.setTextColor(Color.BLACK);
                        } else {
                            tv.setTextColor(Color.BLACK);
                        }
                        return view;
                    }
                };
                make_spn.setAdapter(carMakeAdapter);


            } catch (Exception e) {

            }
        }
    }

    //GET MODEL API CALL: ---------------------------------------------------------------------->
    public class getCarModel extends AsyncTask<String, String, String> {
        private ProgressDialog dialog2;
        String getyear_str;
        String make_str;

        public getCarModel(String str_caryear, String str_carmake) {
            this.getyear_str=str_caryear;
            this.make_str=str_carmake;

        }


        protected void onPreExecute() {
            super.onPreExecute();
            dialog2 = new ProgressDialog(PartsEntryActivity.this);
            dialog2.setMessage("Loading Car Model..., please wait.");
            dialog2.show();
        }

        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://www.carqueryapi.com/api/0.3/?cmd=getModels&year="+getyear_str+"&make="+make_str)
                    .get()
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "9a479868-31a0-4760-a9d8-7d9007955ad5")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (dialog2.isShowing()) {
                dialog2.dismiss();
            }

            try {
                JSONObject jo = new JSONObject(s);

                JSONArray jsonArray=jo.getJSONArray("Models");
                carmodel_arraylist.clear();

                for(int l=0;l<jsonArray.length();l++)
                {
                    JSONObject object=jsonArray.getJSONObject(l);
                    String model_name = object.getString("model_name");
                    carmodel_arraylist.add(model_name);
                }

                model_spn.setVisibility(View.VISIBLE);
                carModelAdapter = new CustomAdapterArrayList(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, carmodel_arraylist) {
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
                        tv.setTypeface(helvetica);
                        tv.setTextSize(23);
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
                        tv.setTextSize(23);
                        tv.setPadding(10, 20, 0, 20);
                        tv.setTypeface(helvetica);
                        if (position == 0) {
                            tv.setTextColor(Color.BLACK);
                        } else {
                            tv.setTextColor(Color.BLACK);
                        }
                        return view;
                    }
                };
                model_spn.setAdapter(carModelAdapter);



            } catch (Exception e) {

            }
        }
    }





}