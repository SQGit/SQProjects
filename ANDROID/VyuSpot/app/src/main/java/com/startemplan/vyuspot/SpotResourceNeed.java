package com.startemplan.vyuspot;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.balysv.materialripple.MaterialRippleLayout;
import com.rey.material.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class SpotResourceNeed extends Activity {


    static final int DIALOG_ERROR_CONNECTION = 1;
    public static String URL_REGISTER = "http://sqindia01.cloudapp.net/vyuspot/api/v1/spot/add";
    List<String> resource_type = new ArrayList<String>();
    Spinner spin;
    ImageButton loc;
    Button send, lcbut;
    GpsT gps;
    String Name, Locationname, locationAddress, Landmark, Tag, sp, latitide, longitude,contactno,typess;
    double dlatitude, dlongitude;
    TextView tltlg, tlatlong, typed,txtv;
    MaterialRippleLayout mtp;
    SweetAlertDialog pDialog;
    ImageButton fbb,fbs;
    String token;
    com.rey.material.widget.EditText mnam,mloc,mland,mtag,mcont;

    private PopupWindow pw;
    private boolean expanded; 		//to  store information whether the selected values are displayed completely or in shortened representatn
    public static boolean[] checkSelected;

    protected Button selectresourceButton;

    protected CharSequence[] resource = { "FOOD", "BISCUIT", "MILK", "WATER", "MONEY", "MAT","BEDSHEET","OTHER" };

    protected ArrayList<CharSequence> selectedresource = new ArrayList<CharSequence>();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.spot_resource_needs);
        Log.d("tag", "accident");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SpotResourceNeed.this);
        token= sharedPreferences.getString("token", "");
        Log.d("tag", token);



        if(token.length() < 5 )  {

            new SweetAlertDialog(SpotResourceNeed.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Warning")
                    .setContentText("You Have to Register First!!!")
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            sweetAlertDialog.cancel();
                            Intent goD = new Intent(getApplicationContext(), Register.class);
                            startActivity(goD);


                        }
                    })
                    .show();

        }








        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        mtp = (MaterialRippleLayout) findViewById(R.id.ripple);
        lcbut = (Button) findViewById(R.id.loc_but);
        tltlg = (TextView) findViewById(R.id.ltlg);
        tlatlong = (TextView) findViewById(R.id.latlongt);
        typed = (TextView) findViewById(R.id.typed);

        mnam = (EditText) findViewById(R.id.namem);
        mloc = (EditText) findViewById(R.id.locatm);
        mland = (EditText) findViewById(R.id.landm);
        mtag = (EditText) findViewById(R.id.tagm);
        mcont = (EditText) findViewById(R.id.cont);

        //fbs= (ImageButton) findViewById(R.id.fbss);
        fbb = (ImageButton) findViewById(R.id.fbs);


        // spin = (Spinner) findViewById(R.id.spinner);
        send = (Button) findViewById(R.id.send);

        // textv = findViewById(R.layout.spintext.)

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/robol.ttf");


        View inflatedView = getLayoutInflater().inflate(R.layout.spintext, null);
        TextView text = (TextView) inflatedView.findViewById(R.id.stext);
        text.setTypeface(tf);




        send.setTypeface(tf);
        lcbut.setTypeface(tf);
        tltlg.setTypeface(tf);
        tlatlong.setTypeface(tf);
        mloc.setTypeface(tf);
        mnam.setTypeface(tf);
        mtag.setTypeface(tf);
        mland.setTypeface(tf);
        mcont.setTypeface(tf);
        typed.setTypeface(tf);


        resource_type.add("FOOD");
        resource_type.add("BISCUIT");
        resource_type.add("MAT");
        resource_type.add("MILK");
        resource_type.add("WATER");
        resource_type.add("MONEY");
        //ArrayAdapter<String> res = new ArrayAdapter<String>(getApplicationContext(), R.layout.spintext, R.id.stext, resource_type);
        //spin.setAdapter(res);





        String[] countries = {"flood","drought","fire","chennaifloods","chennaiflood","sunami","chennaiflood","chennaidrought","chennai","kadalur","vilupuram","thanjavur","nagapatinam","pondicheri"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(),R.layout.spintext, R.id.stext,countries);
        mtag.setAdapter(adapter1);



        selectresourceButton = (Button) findViewById(R.id.select_resource);

        selectresourceButton.setTypeface(tf);

        selectresourceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch(v.getId()) {

                    case R.id.select_resource:

                        // TODO: Show the resource dialog

                        showSelectresourceDialog();

                        break;

                    default:

                        break;

                }
            }
        });




        fbb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Name = mnam.getText().toString();
                Locationname = mloc.getText().toString();
                Landmark = mland.getText().toString();
                Tag = mtag.getText().toString();
                latitide = String.valueOf(dlatitude);
                longitude = String.valueOf(dlongitude);
                contactno = mcont.getText().toString();

                String share = Name +" "+ Locationname + " Near " + Landmark + " " + "#" + Tag + " " + contactno + " #vyuspot"+" "+"#startemplan";


                String tweetUrl = String.format("https://twitter.com/intent/tweet?text=%s&url=%s",
                        urlEncode("HelpNeeded by "),
                        urlEncode(share));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));

                List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
                for (ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter")) {
                        intent.setPackage(info.activityInfo.packageName);
                    }
                }

                startActivity(intent);
                finish();


            }
        });





       /* fbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Name = mnam.getText().toString();
                Locationname = mloc.getText().toString();
                Landmark = mland.getText().toString();
                Tag = mtag.getText().toString();
                latitide = String.valueOf(dlatitude);
                longitude = String.valueOf(dlongitude);
                contactno = mcont.getText().toString();



                String share =  Locationname +"Nearby "+ Landmark +" "+ Tag + "Contact: " +contactno +"@ " + latitide +longitude +"#vyuspot"+"\n"+"#startemplan" ;



        *//*        String tweetUrl = String.format("https://www.facebook.com/sharer/sharer.php?u=",
                        urlEncode("Accident @"),
                        urlEncode(share));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));

                List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
                for (ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                        intent.setPackage(info.activityInfo.packageName);
                    }
                }

                startActivity(intent);*//*



                String tweetUrl = String.format("http://startemplan.com/",
                        urlEncode("Accident @"),
                        urlEncode(share));


                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, tweetUrl);
                boolean facebookAppFound = false;
                List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
                for (ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                        intent.setPackage(info.activityInfo.packageName);
                        facebookAppFound = true;
                        break;
                    }
                }

                if (!facebookAppFound) {
                    String sharerUrl = "" + tweetUrl;
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                }

                startActivity(intent);




              *//*  String tweetUrl = String.format("https://www.facebook.com/sharer/sharer.php?u=",
                        urlEncode("Accident@"),
                        urlEncode(share));
                Intent intent = new Intent(Intent.ACTION_SEND, Uri.parse(tweetUrl));


// See if official Facebook app is found
                boolean facebookAppFound = false;
                List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
                for (ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                        intent.setPackage(info.activityInfo.packageName);
                        facebookAppFound = true;
                        break;
                    }
                }

// As fallback, launch sharer.php in img_emg browser
                if (!facebookAppFound) {
                    String sharerUrl = tweetUrl;
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));
                }


                startActivity(intent);
*//*

            }
        });
*/









































        if (!isOnline(this)) {
            showDialog(DIALOG_ERROR_CONNECTION); //displaying the created dialog.
        } else {
            //Internet available. Do what's required when internet is available.


            gps = new GpsT(SpotResourceNeed.this);
            if (gps.canGetLocation()) {
                dlatitude = gps.getLatitude();
                dlongitude = gps.getLongitude();
                String lati = String.valueOf(dlatitude);
                String longi = String.valueOf(dlongitude);
                // String aa = gps.getAddress();
//            Log.img_view("tag", aa);
                String Addr = gps.getAddress();
                mloc.setText(Addr);
                tlatlong.setText("Latitude:" + lati + "\nLongitude:\t" + longi);
            } else {
                SplashScreen sps = new SplashScreen();
                sps.buildAlertMessageNoGps();
                //gps.showSettingsAlert();
            }


        }



        mtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gps = new GpsT(SpotResourceNeed.this);
                dlatitude = gps.getLatitude();
                dlongitude = gps.getLongitude();


                Name = mnam.getText().toString();
                Locationname = mloc.getText().toString();
                Landmark = mland.getText().toString();
                //sp = spin.getSelectedItem().toString();

               /* if(typess == null)
                {
                     Toast.makeText(getApplicationContext(), "Enter All Details", Toast.LENGTH_LONG).show();
                }
                else
                {*/
                    sp = typess;
                //}
                //Log.img_view("tt1",sp);
                Tag = mtag.getText().toString();
                latitide = String.valueOf(dlatitude);
                longitude = String.valueOf(dlongitude);

                contactno = mcont.getText().toString();



                if (!isOnline(SpotResourceNeed.this)) {


                    showDialog(DIALOG_ERROR_CONNECTION);


                } else {


                    if (Utils.Operations.isOnline(SpotResourceNeed.this)) {

                        if (!Name.isEmpty() && !Locationname.isEmpty() && !Landmark.isEmpty() && !Tag.isEmpty() && !contactno.isEmpty()) {


                            new MyActivityAsync(Name, Locationname, Landmark, sp, Tag, latitide, longitude, contactno).execute();


                        } else {

                            new SweetAlertDialog(SpotResourceNeed.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Alert")
                                    .setContentText("Please enter your details! ")
                                    .setConfirmText("OK")
                                    .show();
                        }

                    } else {

                        new SweetAlertDialog(SpotResourceNeed.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops!")
                                .setContentText("Check your internet or try again")
                                .setConfirmText("OK")
                                .show();

                    }
                }
            }
        });


        lcbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gps = new GpsT(SpotResourceNeed.this);

                if (gps.canGetLocation()) {

                    dlatitude = gps.getLatitude();
                    dlongitude = gps.getLongitude();

                    String lati = String.valueOf(dlatitude);
                    String longi = String.valueOf(dlongitude);

                    tlatlong.setText("Latitude:\t" + lati + "\nLongitude:\t" + longi);


                } else {
                    gps.showSettingsAlert();
                }
            }
        });



    }

    public boolean isOnline(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni != null && ni.isConnected())
            return true;
        else
            return false;
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DIALOG_ERROR_CONNECTION:
                AlertDialog.Builder errorDialog = new AlertDialog.Builder(this);
                errorDialog.setTitle("Error");
                errorDialog.setMessage("No internet connection.");

                errorDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        //finish();
                    }
                });

                errorDialog.setNeutralButton("Cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent goD = new Intent(getApplicationContext(), Dashboard.class);
                                startActivity(goD);
                                //finish();
                                //dialog.dismiss();
                            }
                        });

                AlertDialog errorAlert = errorDialog.create();
                return errorAlert;

            default:
                break;
        }
        return dialog;
    }


    class MyActivityAsync extends AsyncTask<String, Void, String> {


        String Name, Locationname, Landmark, sp, Tag, latitide, longitude,contact;

        public MyActivityAsync(String Name, String Locationname, String Landmark, String sp, String Tag, String latitide, String longitude,String cont) {

            this.Name = Name;
            this.Locationname = Locationname;
            this.Landmark = Landmark;
            this.sp = sp;
            this.Tag = Tag;
            this.latitide = latitide;
            this.longitude = longitude;
            this.contact = cont;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("tag", "s1");

            pDialog = new SweetAlertDialog(SpotResourceNeed.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            String json = "", jsonStr = "";

            try {

                //{"name":"Binu","location":"perungalathur","type":"RESOURCE_NEEDED","landmark":"near by pass","typeOfThingsNeeded":"House","hashTags":"#helpneeded","latitide":"12.9053567","longitude":"80.0952727"}


                // 3. build jsonObject
                JSONObject jsonObject = new JSONObject();

                jsonObject.accumulate("categoryId", "3");
                jsonObject.accumulate("name", Name);
                jsonObject.accumulate("location", Locationname);
                jsonObject.accumulate("landmark", Landmark);
                jsonObject.accumulate("resourceNeeded", sp);
                jsonObject.accumulate("hashTags", Tag);
                //jsonObject.accumulate("type", "RESOURCE_NEEDED");
                jsonObject.accumulate("latitide", latitide);
                jsonObject.accumulate("longitude", longitude);
                jsonObject.accumulate("contactNumber", contact);
                Log.d("ttage",Name+Locationname+Landmark+sp+Tag+latitide+longitude+contact);
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest(URL_REGISTER, json,token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("tag", "<-----rerseres---->" + s);
            super.onPostExecute(s);

            pDialog.dismiss();

            try {
                JSONObject jo = new JSONObject(s);

                String status = jo.getString("status");
                String msg = jo.getString("message");
                Log.d("tag", "<-----Status----->" + status);


                if (status.equals("SUCCESS")) {

                    new SweetAlertDialog(SpotResourceNeed.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Message Alert")
                            .setContentText("Spot Added Sucessfully")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.cancel();
                                    finish();
                                    /*Intent goN = new Intent(getApplicationContext(), SpotResourceNeed.class);
                                    startActivity(goN);*/
                                }
                            })
                            .show();


                    //Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                } else {

                    new SweetAlertDialog(SpotResourceNeed.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops!")
                            .setContentText("Check your internet or try again")
                            .setConfirmText("OK")
                            .show();
                    //  Toast.makeText(getActivity(), "Check your internet or try again", Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }
 /*   @Override
    protected void onPause() {
        finish();
        super.onPause();

    }
*/




    protected void showSelectresourceDialog() {

        boolean[] checkedresource = new boolean[resource.length];

        int count = resource.length;

        for(int i = 0; i < count; i++)

            checkedresource[i] = selectedresource.contains(resource[i]);

        DialogInterface.OnMultiChoiceClickListener resourceDialogListener = new DialogInterface.OnMultiChoiceClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                if(isChecked)

                    selectedresource.add(resource[which]);

                else

                    selectedresource.remove(resource[which]);

                onChangeSelectedresource();

            }

        };

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select Your Needs");

        builder.setMultiChoiceItems(resource, checkedresource, resourceDialogListener);
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();


        dialog.show();

    }

    protected void onChangeSelectedresource() {

        StringBuilder stringBuilder = new StringBuilder();

        for(CharSequence colour : selectedresource)

            stringBuilder.append(colour + ",");

        typess = String.valueOf(stringBuilder);
        Log.d("tt", typess);

        selectresourceButton.setText(stringBuilder.toString());

    }



    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            Log.d("tag", "UTF-8 should always be supported");
            throw new RuntimeException("URLEncoder.encode() failed for " + s);
        }
    }






    private void initialize() {
        //data source for drop-down list
        final ArrayList<String> items = new ArrayList<String>();
        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3");
        items.add("Item 4");
        items.add("Item 5");

        checkSelected = new boolean[items.size()];
        //initialize all values of list to 'unselected' initially
        for (int i = 0; i < checkSelected.length; i++) {
            checkSelected[i] = false;
        }

	/*SelectBox is the TextView where the selected values will be displayed in the form of "Item 1 & 'n' more".
    	 * When this selectBox is clicked it will display all the selected values
    	 * and when clicked again it will display in shortened representation as before.
    	 * */
        final TextView tv = (TextView) findViewById(R.id.SelectBox);
        tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!expanded) {
                    //display all selected values
                    String selected = "";
                    int flag = 0;
                    for (int i = 0; i < items.size(); i++) {
                        if (checkSelected[i] == true) {
                            selected += items.get(i);
                            selected += ", ";
                            flag = 1;
                        }
                    }
                    if (flag == 1)
                        tv.setText(selected);
                    expanded = true;
                } else {
                    //display shortened representation of selected values
                    tv.setText(DropDownListAdapter.getSelected());
                    expanded = false;
                }
            }
        });

        //onClickListener to initiate the dropDown list
        Button createButton = (Button) findViewById(R.id.create);
        createButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                initiatePopUp(items, tv);
            }
        });


    }
    private void initiatePopUp(ArrayList<String> items, TextView tv){
        LayoutInflater inflater = (LayoutInflater)SpotResourceNeed.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //get the pop-up window i.img_map.  drop-down layout
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.pop_up_window, (ViewGroup)findViewById(R.id.PopUpView));

        //get the view to which drop-down layout is to be anchored
        RelativeLayout layout1 = (RelativeLayout)findViewById(R.id.relativeLayout1);
        pw = new PopupWindow(layout, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        //Pop-up window background cannot be null if we want the pop-up to listen touch events outside its window
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setTouchable(true);

        //let pop-up be informed about touch events outside its window. This  should be done before setting the content of pop-up
        pw.setOutsideTouchable(true);
        pw.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        //dismiss the pop-up i.img_map. drop-down when touched anywhere outside the pop-up
        pw.setTouchInterceptor(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    pw.dismiss();
                    return true;
                }
                return false;
            }
        });

        //provide the source layout for drop-down
        pw.setContentView(layout);

        //anchor the drop-down to bottom-left corner of 'layout1'
        pw.showAsDropDown(layout1);

        //populate the drop-down list
        final ListView list = (ListView) layout.findViewById(R.id.dropDownList);
        DropDownListAdapter adapter = new DropDownListAdapter(this, items, tv);
        list.setAdapter(adapter);
    }
}



















