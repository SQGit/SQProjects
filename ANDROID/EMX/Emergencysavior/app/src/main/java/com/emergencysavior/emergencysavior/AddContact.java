package com.emergencysavior.emergencysavior;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by RSA on 05-03-2016.
 */
public class AddContact extends AppCompatActivity {
    public static final String EXTRA_IS_TRANSPARENT = "is_transparent";
    private boolean isTransparent;

    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    public final int PICK_CONTACT = 2015;
    TextView sign;
    EditText Name, Email, Phone;
    Button submit;
    String name, email, phone_number, token,kept,code,code_phone_number;
    SweetAlertDialog pDialog;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isTransparent = getIntent().getBooleanExtra(EXTRA_IS_TRANSPARENT, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcontact);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AddContact.this);
        token = sharedPreferences.getString("Session_token", "");

        ///////////////////////////////// actionbar ///////////////////////////////////////////////////////////////////////////
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        SpannableStringBuilder SS = new SpannableStringBuilder(Html.fromHtml("<font color='#3c5899'><small><b>ADD CONTACTS</b></small> </font>"));
        SS.setSpan(new CustomTypefaceSpan("#3c5899", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);
        ////////////////////////////////////// actionbar ///////////////////////////////////////////////////////////////////////////
        sign = (TextView) findViewById(R.id.dss);
        sign.setTypeface(tf);
        Spinner spinner = (Spinner) findViewById(R.id.phone_code);
        Name = (EditText) findViewById(R.id.Name);
        Phone = (EditText) findViewById(R.id.Phone);
        Email = (EditText) findViewById(R.id.Email);
        submit = (Button) findViewById(R.id.submit);
        sign = (TextView) findViewById(R.id.dss);
        Name.setTypeface(tf);
        Email.setTypeface(tf);
        Phone.setTypeface(tf);
        submit.setTypeface(tf);
        sign.setTypeface(tf);




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.CountryCodes, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Country code");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);


                if (position > 0) {
                    // Notify the selected item text


                 kept = selectedItemText.substring(0, selectedItemText.indexOf(","));



                    if(! CONFIG.isStringNullOrWhiteSpace(kept)){
                        code="+"+kept;
                    }else {
                        code="";
                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("new_variable_name");
            Log.d("tag", "Received" + value);

            if (value.equals("1")) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
            } else {
                Log.d("tag", "something wnet wrong");

            }
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = Name.getText().toString();
                email = Email.getText().toString();
                phone_number=Phone.getText().toString();


                if (! CONFIG.isStringNullOrWhiteSpace(phone_number)){
                    phone_number=phone_number.replace(" ","").replace("(","").replace(")","").replace("-","");
                    code_phone_number = code+phone_number;
                }else {

                    code_phone_number="";
                }



                Log.d("tag","join the phone and country code"+code_phone_number);
                if (Util.Operations.isOnline(AddContact.this)) {
                    if ((! CONFIG.isStringNullOrWhiteSpace(name))&&(! CONFIG.isStringNullOrWhiteSpace(code_phone_number))&&(! CONFIG.isStringNullOrWhiteSpace(email))) {

                        if (! ( phone_number.indexOf("+") ==-1)){
                            Toast.makeText(getApplicationContext(), "Please type the phone No. Without country code", Toast.LENGTH_LONG).show();
                            return;
                        }

                        new MyActivityAsync(name, code_phone_number, email).execute();
                    } else {
                        new SweetAlertDialog(AddContact
                                .this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("INVALID")
                                .setContentText("Please Enter Name and phone")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent intent = new Intent(getApplicationContext(), Contacts.class);
                                        startActivity(intent);
                                    }
                                })
                                .show();


                    }


                } else {

                    new SweetAlertDialog(AddContact
                            .this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("No Internet Connectivity?")
                            .show();

                }
            }
        });

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

        String name,code_phone_number,email;

        public MyActivityAsync(String name,
                               String code_phone_number,
                               String email) {
            this.name=name;
            this.code_phone_number=code_phone_number;
            this.email=email;


        }

        protected void onPreExecute() {


            pDialog = new SweetAlertDialog(AddContact.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Uploading ");
            pDialog.setCancelable(true);
            pDialog.getWindow().setLayout(1800, 400);
            pDialog.show();
        }


        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {


                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("name", name);
                jsonObject.accumulate("mobile", code_phone_number);
                jsonObject.accumulate("email", email);
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return jsonStr = HttpUtils.makeRequest1(CONFIG.URL+"/contactadd", json, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.d("tag", "<-----Contact_upload_result---->" + jsonStr);
            super.onPostExecute(jsonStr);
            pDialog.dismiss();
            if (jsonStr == "") {

                new SweetAlertDialog(AddContact.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();
            } else {

                try {
                    JSONObject result1 = new JSONObject(jsonStr);
                    String status = result1.getString("status");

                    if (status.equals("success")) {


                        Intent i = new Intent(getApplicationContext(), Contacts.class);
                        startActivity(i);


                        // Toast.makeText(getApplicationContext(), "Contact Added", Toast.LENGTH_LONG).show();
                    } else {
                        new SweetAlertDialog(AddContact
                                .this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("INVALID")
                                .setContentText("Contacts Already Exits")
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    }
                                })
                                .show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String contactID = "", contactNumber = "", contactName = "", contactEmail = "";
        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK) {
            Log.d("tag", "Response: " + data.toString());
            Uri uriContact = data.getData();

            ////////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////C O N T A C T - N A M E ////////////////////////
            ;
            {
                // querying contact data store
                Cursor cursor = getContentResolver().query(uriContact, null, null, null, null);

                if (cursor.moveToFirst()) {

                    // DISPLAY_NAME = The display name for the contact.
                    // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

                    contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                }

                cursor.close();

                Log.d("NKBJ", "Contact Name: " + contactName);
            }
            /////////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////C O N T A C T - N U M B E R/////////////////////
            ;
            {
                // getting contacts ID
                Cursor cursorID = getContentResolver().query(uriContact,
                        new String[]{ContactsContract.Contacts._ID},
                        null, null, null);

                if (cursorID.moveToFirst()) {

                    contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
                }

                cursorID.close();

                Log.d("NKJB", "Contact ID: " + contactID);

                // Using the contact ID now we will get contact phone number

                Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                                ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                        new String[]{contactID},
                        null);

                if (cursorPhone.moveToFirst()) {
                    contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }

                cursorPhone.close();

                Log.d("NKJN", "Contact Phone Number: " + contactNumber);
            }
            ///////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////C O N T A C T - E M A I L/////////////////////
            ;
            {
                // getting contacts ID
                Cursor cursorID = getContentResolver().query(uriContact,
                        new String[]{ContactsContract.Contacts._ID},
                        null, null, null);

                if (cursorID.moveToFirst()) {

                    contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
                }

                cursorID.close();

                Log.d("NKJB", "Contact ID: " + contactID);

                // Using the contact ID now we will get contact phone number
                Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Email.DATA},

                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ? AND " +
                                ContactsContract.CommonDataKinds.Email.TYPE + " = " +
                                ContactsContract.CommonDataKinds.Email.TYPE_HOME,

                        new String[]{contactID},
                        null);

                if (cursorPhone.moveToFirst()) {
                    contactEmail = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                }

                cursorPhone.close();

                Log.d("NKJN", "Contact Email: " + contactEmail);
            }
            ///////////////////////////////////////////////////////////////////////////////

            Name.setText(contactName);
            Phone.setText(contactNumber);
            Email.setText(contactEmail);
        }
    }
}

