package com.emergencysavior.emergencysavior;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;
import com.jaeger.library.StatusBarUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by RSA on 3/2/2016.
 */
public class Profileupdate extends AppCompatActivity implements CalendarDatePickerDialogFragment.OnDateSetListener {
    SweetAlertDialog pDialog;
    private ProgressDialog dialog;
    String selectedImagePath;
    public static boolean flz = true;
    private static final int MY_INTENT_CLICK = 302;
    EditText firstname,
            lastname,
            residentialphone,
            emailaddress,
            confirmemail,
            password,
            confirmpassword,
            phone,
            dateofbith,
            gender,
            address,
            city,
            state,
            zipcode;

    String edt_firstname,
            edt_lastname,
            edt_residentialphone,
            edt_emailaddress,
            edt_password,
            edt_phone,
            edt_dateofbith,
            edt_gender,
            edt_address,
            edt_city,
            edt_state,
            edt_zipcode;
    TextView aditionalinformation, tv;
    Button btn_update;
    LinearLayout additional, foc;
    String token;
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

    ScrollView bottomvalue;
    String sh_vale_session_token,
            sh_vale_email,
            sh_vale_firstname,
            sh_vale_lastname,
            sh_vale_password,
            sh_vale_residential_phone,
            sh_vale_mobile,
            sh_vale_dob,
            sh_vale_gender,
            sh_vale_address,
            sh_vale_city,
            sh_vale_state,
            sh_vale_zipcode,
            sh_vale_image_url, localimageencoder;
    ImageView profileimage;

    private Uri fileUri;
    ImageView user_icon;
    public static final String EXTRA_IS_TRANSPARENT = "is_transparent";
    private boolean isTransparent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isTransparent = getIntent().getBooleanExtra(EXTRA_IS_TRANSPARENT, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_update);

        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setIcon(R.drawable.navicon);
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        SpannableStringBuilder SS = new SpannableStringBuilder(Html.fromHtml("<font color='#3c5899'><small><b>PROFILE UPDATE</b></small> </font>"));
        SS.setSpan(new CustomTypefaceSpan("#3c5899", font2), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);
        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Profileupdate.this);
        sh_vale_session_token = sharedPreferences.getString("Session_token", "");
        token = sh_vale_session_token;
        localimageencoder = sharedPreferences.getString("zxc", "");
        sh_vale_email = sharedPreferences.getString("Email", "");
        sh_vale_firstname = sharedPreferences.getString("Firstname", "");
        sh_vale_lastname = sharedPreferences.getString("Lastname", "");
        sh_vale_password = sharedPreferences.getString("Password", "");
        sh_vale_residential_phone = sharedPreferences.getString("Residential_phone", "");
        sh_vale_mobile = sharedPreferences.getString("Mobile", "");
        sh_vale_dob = sharedPreferences.getString("Dob", "");
        sh_vale_gender = sharedPreferences.getString("Gender", "");
        sh_vale_address = sharedPreferences.getString("Address", "");
        sh_vale_city = sharedPreferences.getString("City", "");
        sh_vale_state = sharedPreferences.getString("State", "");
        sh_vale_zipcode = sharedPreferences.getString("Zipcode", "");
        sh_vale_image_url = sharedPreferences.getString("Image_url", "");


        additional = (LinearLayout) findViewById(R.id.additional_information_view);
        bottomvalue = (ScrollView) findViewById(R.id.bottomvalue);
        additional.setVisibility(View.GONE);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        tv = (TextView) findViewById(R.id.dss);
        tv.setTypeface(tf);
        user_icon = (CircleImageView) findViewById(R.id.update_image);
        Log.d("tag", "<-----ImagePath------>" + sh_vale_image_url);
        profileimage = (ImageView) findViewById(R.id.update_image);
        String encodedImage = sharedPreferences.getString("zxc", "");
        if (!encodedImage.equals("")) {
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            user_icon.setImageBitmap(decodedByte);
        }


        btn_update = (Button) findViewById(R.id.btn_update);
        aditionalinformation = (TextView) findViewById(R.id.additional_information);

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Editext intiate>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        firstname = (EditText) findViewById(R.id.First_Name);
        lastname = (EditText) findViewById(R.id.Last_Name);
        residentialphone = (EditText) findViewById(R.id.residential_landline);
        emailaddress = (EditText) findViewById(R.id.email_address);
        confirmemail = (EditText) findViewById(R.id.confirm_email_address);
        phone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.Password);
        confirmpassword = (EditText) findViewById(R.id.Confirm_Password);
        dateofbith = (EditText) findViewById(R.id.date_of_birth);
        gender = (EditText) findViewById(R.id.gender);
        address = (EditText) findViewById(R.id.address);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        zipcode = (EditText) findViewById(R.id.zipcode);
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Editext intiate>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        firstname.setTypeface(tf);
        lastname.setTypeface(tf);
        residentialphone.setTypeface(tf);
        emailaddress.setTypeface(tf);
        confirmemail.setTypeface(tf);
        password.setTypeface(tf);
        confirmpassword.setTypeface(tf);
        phone.setTypeface(tf);
        aditionalinformation.setTypeface(tf);
        dateofbith.setTypeface(tf);
        gender.setTypeface(tf);
        address.setTypeface(tf);
        city.setTypeface(tf);
        state.setTypeface(tf);
        zipcode.setTypeface(tf);
        btn_update.setTypeface(tf);

        Log.d("tag", "sh_vale_firstname" + sh_vale_firstname);
        Log.d("tag", "sh_vale_lastname" + sh_vale_lastname);
        Log.d("tag", "sh_vale_residential_phone" + edt_residentialphone);
        Log.d("tag", "sh_vale_email" + sh_vale_email);
        Log.d("tag", "sh_vale_password" + sh_vale_password);
        Log.d("tag", "sh_vale_mobile" + sh_vale_mobile);
        Log.d("tag", "sh_vale_dob" + edt_dateofbith);
        Log.d("tag", "sh_vale_gender" + sh_vale_gender);
        Log.d("tag", "sh_vale_address" + sh_vale_address);
        Log.d("tag", "sh_vale_city" + sh_vale_city);
        Log.d("tag", "sh_vale_state" + sh_vale_state);
        Log.d("tag", "sh_vale_zipcode" + sh_vale_zipcode);
        Log.d("tag", "sh_vale_image_url" + sh_vale_image_url);
        if (sh_vale_firstname.equals("null")) {
            firstname.setHint("First Name");
        } else {
            firstname.setText(sh_vale_firstname);
        }

        if (sh_vale_lastname.equals("null")) {
            lastname.setHint("Last Name");
        } else {
            lastname.setText(sh_vale_lastname);
        }
        if (sh_vale_residential_phone.equals("null")) {
            residentialphone.setHint("Residential Phone");
        } else {
            residentialphone.setText(sh_vale_residential_phone);
        }
        if (sh_vale_email.equals("null")) {
            emailaddress.setHint("Email Address");
        } else {

            emailaddress.setText(sh_vale_email);
            emailaddress.setFocusable(false);
            emailaddress.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            emailaddress.setClickable(false); // user navigates with wheel and selects widget

        }
        if (sh_vale_email.equals("null")) {
            confirmemail.setHint("Confirm Email Address");
        } else {
            confirmemail.setText(sh_vale_email);
        }
        if (sh_vale_mobile.equals("null")) {
            phone.setHint("Mobile");
        } else {
            phone.setText(sh_vale_mobile);
        }
        if (sh_vale_password.equals("null")) {
            password.setHint("password");
        } else {
            password.setText(sh_vale_password);
            password.setFocusable(false);
            password.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            password.setClickable(false); // user navigates with wheel and selects widget

        }
        if (sh_vale_password.equals("null")) {
            confirmpassword.setHint("Confirm password");
        } else {
            confirmpassword.setText(sh_vale_password);
        }


        if (sh_vale_dob.equals("null")) {
            dateofbith.setHint("Date of Birth");
        } else {
            dateofbith.setText(sh_vale_dob);
        }
        if (sh_vale_gender.equals("null")) {
            gender.setHint("Gender");
        } else {
            gender.setText(sh_vale_gender);
        }
        if (sh_vale_address.equals("null")) {
            address.setHint("Address");
        } else {
            address.setText(sh_vale_address);
        }
        if (sh_vale_city.equals("null")) {
            city.setHint("City");
        } else {
            city.setText(sh_vale_city);
        }
        if (sh_vale_state.equals("null")) {
            state.setHint("State");
        } else {
            state.setText(sh_vale_state);
        }
        if (sh_vale_zipcode.equals("null")) {
            zipcode.setHint("Zipcode");
        } else {
            zipcode.setText(sh_vale_zipcode);
        }
        /*if (sh_vale_image_url.equals("null")){
            firstname.setHint("Zipcode");
        }else {
            firstname.setText(sh_vale_image_url);
        }*/
        flz = false;
        dateofbith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //******************************************************Better pickers ******************************************************************************************************
                FragmentManager fm = getSupportFragmentManager();
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                CalendarDatePickerDialogFragment calendarDatePickerDialogFragment = CalendarDatePickerDialogFragment
                        .newInstance(Profileupdate.this, year, month, day);

                calendarDatePickerDialogFragment.setDateRange(null, new MonthAdapter.CalendarDay(year, month, day));
                calendarDatePickerDialogFragment.setThemeDark(true);
                calendarDatePickerDialogFragment.show(fm, FRAG_TAG_DATE_PICKER);
//******************************************************calendar old ******************************************************************************************************

            }
        });
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = {"Male", "Female"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Profileupdate.this);
                builder.setCancelable(false);
                builder.setItems(options, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Male")) {

                            gender.setText("M");

                        } else if (options[item].equals("Female")) {
                            gender.setText("F");
                        }
                    }
                });
                builder.show();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                edt_firstname = firstname.getText().toString();
                edt_lastname = lastname.getText().toString();
                edt_residentialphone = residentialphone.getText().toString();
                edt_emailaddress = emailaddress.getText().toString();
                edt_password = password.getText().toString();
                edt_phone = phone.getText().toString();
                edt_dateofbith = dateofbith.getText().toString();
                edt_gender = gender.getText().toString();
                edt_address = address.getText().toString();
                edt_city = city.getText().toString();
                edt_state = state.getText().toString();
                edt_zipcode = zipcode.getText().toString();
                if (Util.Operations.isOnline(Profileupdate.this)) {
                    new MyActivityAsync(edt_firstname,
                            edt_lastname,
                            edt_residentialphone,
                            edt_phone,
                            edt_dateofbith,
                            edt_gender,
                            edt_address,
                            edt_city,
                            edt_state,
                            edt_zipcode).execute();


                } else {

                    new SweetAlertDialog(Profileupdate
                            .this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("ALERT")
                            .setContentText("No Internet Connectivity")
                            .show();

                }


            }
        });
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> profile image >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = {"Change photo", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Profileupdate.this);
                builder.setCancelable(false);
                builder.setItems(options, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Change photo")) {
                            selectImage();
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();


            }
        });


        //>>>>>>>>>>>>>>>>>>>>>>>>>> additional information >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        aditionalinformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (additional.getVisibility() == View.VISIBLE) {
                    additional.setVisibility(View.GONE);
                } else {
                    additional.setVisibility(View.VISIBLE);
                    if (sh_vale_dob.equals("null")) {
                        dateofbith.setHint("Date of Birth");
                    } else {
                        dateofbith.setText(sh_vale_dob);
                    }
                    if (sh_vale_gender.equals("null")) {
                        gender.setHint("Gender");
                    } else {
                        gender.setText(sh_vale_gender);
                    }
                    if (sh_vale_address.equals("null")) {
                        address.setHint("Address");
                    } else {
                        address.setText(sh_vale_address);
                    }
                    if (sh_vale_city.equals("null")) {
                        city.setHint("City");
                    } else {
                        city.setText(sh_vale_city);
                    }
                    if (sh_vale_state.equals("null")) {
                        state.setHint("State");
                    } else {
                        state.setText(sh_vale_state);
                    }
                    if (sh_vale_zipcode.equals("null")) {
                        zipcode.setHint("Zipcode");
                    } else {
                        zipcode.setText(sh_vale_zipcode);
                    }
                    bottomvalue.post(new Runnable() {
                        @Override
                        public void run() {
                            bottomvalue.scrollTo(0, bottomvalue.getBottom());
                            bottomvalue.fullScroll(bottomvalue.FOCUS_DOWN);
                        }
                    });

                }
            }
        });


        //>>>>>>>>>>>>>>>>>>>>>>>>>> additional information >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        setStatusBar();

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    protected void setStatusBar() {
        if (isTransparent) {
            StatusBarUtil.setTransparent(this);
        } else {
            StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
        }
    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Profileupdate.this);
        builder.setTitle("Add Photo!");
        builder.setCancelable(false);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    File mydir = new File(Environment.getExternalStorageDirectory() + "/EMX/user/profile/");
                    if (!mydir.exists())
                        mydir.mkdirs();
                    else
                        Log.d("error", "dir. already exists");
                    File image = new File(mydir, "devices" + timeStamp + ".png");
                    fileUri = Uri.fromFile(image);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
                    startActivityForResult(intent, 100);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");

                    try {
                        intent.setType("*/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT).setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Select File"), MY_INTENT_CLICK);
                    } catch (ActivityNotFoundException e) {
                    }
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            flz = true;
            if (requestCode == 100) {
                selectedImagePath = ImageFilePath.getPath(Profileupdate.this, fileUri);
                Log.d("tag", "selectedImagePath =>>>>>>" + selectedImagePath);
                profileimage.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath));
                Log.d("tag", "path" + selectedImagePath);


            } else {
                if (null == data) return;
                Uri selectedImageUri = data.getData();
                selectedImagePath = ImageFilePath.getPath(Profileupdate.this, selectedImageUri);
                Log.d("tag", "selectedImagePath =>>>>>>" + selectedImagePath);
                try {
                    Bitmap resized = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(selectedImagePath), 120, 120, true);
                    profileimage.setImageBitmap(resized);
                    Log.d("tag", "path" + resized);
                } catch (Exception e) {

                }


            }

        } else if (resultCode == RESULT_CANCELED) {

        } else {
            // failed to capture image
            Toast.makeText(getApplicationContext(), "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int month, int dayOfMonth) {


        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String format = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        Log.d("tag", "date " + format);
        dateofbith.setText(format);

        //  date_of_purches1.setText(getString(R.string.calendar_date_picker_result_values, year, monthOfYear+1, dayOfMonth));

    }

    @Override
    public void onResume() {
        // Example of reattaching to the fragment
        super.onResume();
        CalendarDatePickerDialogFragment calendarDatePickerDialogFragment = (CalendarDatePickerDialogFragment) getSupportFragmentManager()
                .findFragmentByTag(FRAG_TAG_DATE_PICKER);
        if (calendarDatePickerDialogFragment != null) {
            calendarDatePickerDialogFragment.setOnDateSetListener(this);
        }
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> field uploaded process>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> field uploaded process>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Profileupdate.this);
            dialog.setMessage("Uploading...");
            dialog.setCancelable(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

        }

        @Override
        protected String doInBackground(Void... params) {

            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;
            //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

            try {

                HttpClient client = new DefaultHttpClient();
                HttpPost postMethod = new HttpPost(CONFIG.URL + "/api/upload");
                postMethod.addHeader("session_token ", token);
                if (selectedImagePath.equals("")) {
                    Toast.makeText(getApplicationContext(), "No Photo change", Toast.LENGTH_LONG).show();
                } else {


                    File file = new File(selectedImagePath);
                    MultipartEntity entity = new MultipartEntity();
                    FileBody contentFile = new FileBody(file);
                    entity.addPart("fileUpload", contentFile);
                    postMethod.setEntity(entity);
                    //postMethod.addHeader();
                    HttpResponse response = client.execute(postMethod);
                    HttpEntity r_entity = response.getEntity();
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == 200) {
                        // Server response
                        responseString = EntityUtils.toString(r_entity);
                        /////////////////////////////
                        JSONObject result1 = new JSONObject(responseString);
                        String status = result1.getString("status");
                        String updatedurl = result1.getString("url");
                        if (status.equals("success")) {
                            SharedPreferences s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor edit = s_pref.edit();
                            edit.remove("Image_url");
                            edit.putString("Image_url", updatedurl);
                            InputStream inputStream = new java.net.URL(updatedurl).openStream();
                            Bitmap bm = BitmapFactory.decodeStream(inputStream);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                            byte[] b = baos.toByteArray();
                            String encodedString = Base64.encodeToString(b, Base64.DEFAULT);
                            edit.remove("zxc");
                            edit.putString("zxc", encodedString);
                            edit.apply();
                            Log.d("tag", "new zxc>" + encodedString);
                            Log.d("tag", "new Image_url>" + updatedurl);
                        }
                    } else {
                        responseString = "Error occurred! Http Status Code: " + statusCode;
                    }
                }
            } catch (Exception e) {
                responseString = e.toString();
            }
            return responseString;

        }

        @Override
        protected void onPostExecute(String responseString) {
            Log.e("TAG", "Response from server: " + responseString);
            super.onPostExecute(responseString);
            dialog.dismiss();
            if (responseString != null && responseString.equals("null")) {
                Toast.makeText(getApplicationContext(), "No Photo change", Toast.LENGTH_LONG).show();
            }else {
                Intent i = new Intent(Profileupdate.this, MainActivity.class);
                startActivity(i);
            }


            if (responseString == "") {

                new SweetAlertDialog(Profileupdate.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();
            } else {

                try {
                    JSONObject result1 = new JSONObject(responseString);
                    String status = result1.getString("status");
                    String updatedurl = result1.getString("url");
                    Log.d("tag", "new Image_url>" + updatedurl);
                    if (status.equals("success")) {
                        SharedPreferences s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor edit = s_pref.edit();
                        edit.remove("Image_url");
                        edit.putString("Image_url", updatedurl);
                        Intent i = new Intent(Profileupdate.this, MainActivity.class);
                        startActivity(i);

                    } else {
                        Intent i = new Intent(Profileupdate.this, MainActivity.class);
                        startActivity(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }

    }

    private class MyActivityAsync extends AsyncTask<String, Void, String> {
        String edt_firstname, edt_lastname, edt_residentialphone, edt_phone, edt_dateofbith, edt_gender, edt_address, edt_city, edt_state, edt_zipcode;

        public MyActivityAsync(String edt_firstname,
                               String edt_lastname,
                               String edt_residentialphone,
                               String edt_phone,
                               String edt_dateofbith,
                               String edt_gender,
                               String edt_address,
                               String edt_city,
                               String edt_state,
                               String edt_zipcode) {
            this.edt_firstname = edt_firstname;
            this.edt_lastname = edt_lastname;
            this.edt_residentialphone = edt_residentialphone;
            this.edt_phone = edt_phone;
            this.edt_dateofbith = edt_dateofbith;
            this.edt_gender = edt_gender;
            this.edt_address = edt_address;
            this.edt_city = edt_city;
            this.edt_state = edt_state;
            this.edt_zipcode = edt_zipcode;

        }

        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = new SweetAlertDialog(Profileupdate.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.getWindow().setLayout(1800, 400);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("firstname", edt_firstname);
                jsonObject.accumulate("lastname", edt_lastname);
                jsonObject.accumulate("residential_phone", edt_residentialphone);
                jsonObject.accumulate("mobile", edt_phone);
                jsonObject.accumulate("dob", edt_dateofbith);
                jsonObject.accumulate("gender", edt_gender);
                jsonObject.accumulate("address", edt_address);
                jsonObject.accumulate("city", edt_city);
                jsonObject.accumulate("state", edt_state);
                jsonObject.accumulate("zipcode", edt_zipcode);
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return jsonStr = HttpUtils.makeRequest1(CONFIG.URL + "/profileupdate", json, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.d("tag", "<-----Json result---->" + jsonStr);
            super.onPostExecute(jsonStr);
            pDialog.dismiss();
            if (jsonStr == "") {

                new SweetAlertDialog(Profileupdate.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("ALERT")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();
            } else {

                try {
                    JSONObject result = new JSONObject(jsonStr);
                    String status = result.getString("status");
                    if (status.equals("success")) {
                        SharedPreferences s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor edit = s_pref.edit();
                        edit.remove("Firstname");
                        edit.remove("Lastname");
                        edit.remove("Password");
                        edit.remove("Residential_phone");
                        edit.remove("Mobile");
                        edit.remove("Dob");
                        edit.remove("Gender");
                        edit.remove("Address");
                        edit.remove("City");
                        edit.remove("State");
                        edit.remove("Zipcode");
                        edit.putString("Firstname", edt_firstname);
                        edit.putString("Lastname", edt_lastname);
                        edit.putString("Password", edt_password);
                        edit.putString("Residential_phone", edt_residentialphone);
                        edit.putString("Mobile", edt_phone);
                        edit.putString("Dob", edt_dateofbith);
                        edit.putString("Gender", edt_gender);
                        edit.putString("Address", edt_address);
                        edit.putString("City", edt_city);
                        edit.putString("State", edt_state);
                        edit.putString("Zipcode", edt_zipcode);
                        //edit.putString("Image_url", selectedImagePath);
                        Log.d("tag1", "edt_firstname" + edt_firstname);
                        Log.d("tag1", "edt_lastname" + edt_lastname);
                        Log.d("tag1", "edt_residentialphone" + edt_residentialphone);
                        Log.d("tag1", "edt_emailaddress" + edt_emailaddress);
                        Log.d("tag1", "edt_password" + edt_password);
                        Log.d("tag1", "edt_phone" + edt_phone);
                        Log.d("tag1", "edt_dateofbith" + edt_dateofbith);
                        Log.d("tag1", "edt_gender" + edt_gender);
                        Log.d("tag1", "edt_address" + edt_address);
                        Log.d("tag1", "edt_city" + edt_city);
                        Log.d("tag1", "edt_state" + edt_state);
                        Log.d("tag1", "edt_zipcode" + edt_zipcode);
                        Log.d("tag", "Image_url" + selectedImagePath);
                        edit.commit();
                        Log.d("tag", "selectedImagePath" + selectedImagePath);

                        if (selectedImagePath != null && selectedImagePath.equals("null")) {
                            Intent i = new Intent(Profileupdate.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            new UploadFileToServer().execute();
                        }
                    } else {

                        Toast.makeText(getApplicationContext(), "Nothing to updated", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }
    }


}


//############################################# upload image process online  ###################################################

