package sqindia.net.brighterdayz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by sagayam on 19-03-2016.
 */
public class OurOffers extends Activity {

    SweetAlertDialog pDialog;

    GoogleCloudMessaging gcmObj;
    Context applicationContext;
    String gcmregId = "", usermail;
    EditText get;
    Button submit;
    TextView txt_gcm,ouroffers;
    LinearLayout back;
    String token;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pushmessageregisteration);

        get = (EditText) findViewById(R.id.email);
        submit = (Button) findViewById(R.id.button);
        txt_gcm = (TextView) findViewById(R.id.gcmapp);
        ouroffers = (TextView) findViewById(R.id.title_offers);
        back= (LinearLayout) findViewById(R.id.Loffers);


        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),"rr.ttf");

        get.setTypeface(tf);
        submit.setTypeface(tf);
        txt_gcm.setTypeface(tf);
        ouroffers.setTypeface(tf);



        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(OurOffers.this);


        token= sharedPreferences.getString("email", "");




        if ((token == "")) {
            Toast.makeText(getApplicationContext(),"Register with email",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),"You Already Registered",Toast.LENGTH_LONG).show();
            finish();
        }








        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usermail = get.getText().toString();
                if (!(usermail.isEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(usermail).matches())) {
                    if (checkPlayServices()) {
                        registerInBackground();

                    }

                } else {
                    get.setError("Enter a valid email address!");
                    get.requestFocus();
                }

            }

        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(OurOffers.this, Home.class);
                startActivity(k);

            }
        });




    }


    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        // When Play services not found in device
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                // Show Error dialog to install Play services
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                //Toast.makeText(
                //  applicationContext,"This device doesn't support Play services, App will not work normally",Toast.LENGTH_LONG).show();
                pDialog.setTitle("App wont work without google play services");
                pDialog.dismiss();

                finish();
            }
            return false;
        } else {
            //Toast.makeText(applicationContext,"This device supports Play services, App will work normally", Toast.LENGTH_LONG).show();
        }
        return true;
    }


    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcmObj == null) {
                        gcmObj = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    gcmregId = gcmObj.register(ApplicationConstants.GOOGLE_PROJ_ID);
                    msg = "Registration ID :" + gcmregId;
                    Log.d("tag", gcmregId);

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                if (!TextUtils.isEmpty(gcmregId)) {

                  /* Toast.makeText(
                            applicationContext,
                            "Registered with GCM Server successfully.\n\n"
                                    + msg, Toast.LENGTH_SHORT).show();
*/
                    Log.d("tag1", gcmregId);


                    storegcmregIdinServer();


                } else {
                    // Toast.makeText(
                    //   applicationContext,
                    // "Reg ID Creation Failed.\n\nEither you haven't enabled Internet or GCM server is busy right now. Make sure you enabled Internet and try registering again after some time."
                    // + msg, Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }


    private void storegcmregIdinServer() {


        new AsyncTask<String, Void, String>() {


            protected void onPreExecute() {
                super.onPreExecute();
                Log.d("tag", "s1");
            }

            @Override
            protected String doInBackground(String... params) {
                // TODO Auto-generated method stub

                String json = "", jsonStr = "";

                try {

                    Map<String, String> hashmapu = new HashMap<String, String>();
                    hashmapu.put("emailId", usermail);
                    hashmapu.put("regId", gcmregId);

                    post(ApplicationConstants.APP_SERVER_URL, hashmapu);

                } catch (Exception e) {
                    Log.d("tagInputStream", e.getLocalizedMessage());
                }

                return null;

            }

            @Override
            protected void onPostExecute(String s) {
                Log.d("tag", usermail + gcmregId);
                Log.d("tag", "<-----rerseres---->" + s);
                //register();
                super.onPostExecute(s);


                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(OurOffers.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("pus", "54321");
                editor.putString("email", "123");
                editor.commit();

                Intent ii = new Intent(getApplicationContext(), GreetingActivity.class);
                ii.putExtra("regId", gcmregId);
                startActivity(ii);
                finish();


            }

        }.execute();

    }


    private static void post(String endpoint, Map<String, String> params)
            throws IOException {

        URL url;
        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("taginvalid url: " + endpoint);
        }
        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        // constructs the POST body using the parameters
        while (iterator.hasNext()) {
            Map.Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=')
                    .append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }
        String body = bodyBuilder.toString();
        Log.d("tag", "Posting '" + body + "' to " + url);
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;
        try {
            Log.d("URL", "> " + url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            // handle the response
            int status = conn.getResponseCode();
            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(OurOffers.this,Home.class);
        startActivity(i);
        super.onBackPressed();
    }

}
