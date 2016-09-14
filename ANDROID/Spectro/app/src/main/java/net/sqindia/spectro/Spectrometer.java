package net.sqindia.spectro;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jaeger.library.StatusBarUtil;
import com.sloop.fonts.FontsManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;


/**
 * Created by $Krishna on 29-08-2016.
 */
public class Spectrometer extends Activity {
    protected LocationManager locationManager;
    public static final String EXTRA_IS_TRANSPARENT = "is_transparent";
    private boolean isTransparent;

    TextView tv_time, tv_reg, tv_latlong, tv_mass, tv_date, tv_title, tv_webtime, tv_reg136;
    ListView list1;
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
    int year;
    int month;
    int day;
    String stringLatitude;
    String stringLongitude;
    LinearLayout btn_back, btn_refresh;
    String aadsf, sreg_value, slat, slong, sdate, stime, reg, alat, along, atime;
    ImageView dsh_spec,dsh_refresh;
    Animation animation;
    GPSTracker gpsTracker;
    ArrayList<String> latia;
    ArrayList<String> longia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spectrometer);
        FontsManager.initFormAssets(this, "fonts/avenir.otf");       //initialization
        FontsManager.changeFonts(this);
        // locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (Spectrometer) this);

        latia = new ArrayList<>();
        longia = new ArrayList<>();

        oslist = new ArrayList<HashMap<String, String>>();
        tv_time = (TextView) findViewById(R.id.txt_time);
        tv_reg = (TextView) findViewById(R.id.txt_regvalue);
        tv_latlong = (TextView) findViewById(R.id.txt_latlong);
        tv_mass = (TextView) findViewById(R.id.txt_mass);
        tv_date = (TextView) findViewById(R.id.txt_date);
        tv_title = (TextView) findViewById(R.id.spectro);

        btn_back = (LinearLayout) findViewById(R.id.layout_back);
        btn_refresh = (LinearLayout) findViewById(R.id.layout_refresh);
        tv_webtime = (TextView) findViewById(R.id.txt_webtime);
        tv_reg136 = (TextView) findViewById(R.id.text_regval);
        dsh_spec = (ImageView) findViewById(R.id.spec_db);

        final RotateAnimation rotate = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setInterpolator(new LinearInterpolator());
      //  rotate.setRepeatCount(Animation.INFINITE);
      //  rotate.setRepeatMode(Animation.REVERSE);
        dsh_refresh = (ImageView) findViewById(R.id.refresh);


/*
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                dsh_spec.animate().rotationBy(360).withEndAction(this).setDuration(8000).setInterpolator(new LinearInterpolator()).start();
            }
        };
        dsh_spec.animate().rotationBy(360).withEndAction(runnable).setDuration(8000).setInterpolator(new LinearInterpolator()).start();
*/
        animation = AnimationUtils.loadAnimation(Spectrometer.this, R.anim.rotate_around_center_point);
        dsh_spec.startAnimation(animation);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/helvitica.ttf");
        tv_title.setTypeface(tf);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        aadsf = String.valueOf(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(""));
        Log.e("tag", "qwert" + aadsf);
        tv_date.setText(aadsf);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sreg_value = sharedPreferences.getString("s1", "");
        stime = sharedPreferences.getString("s0", "");
        sdate = sharedPreferences.getString("s2", "");
        slat = sharedPreferences.getString("s3", "");
        slong = sharedPreferences.getString("s4", "");

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Spectrometer.this, Dashboard.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
                //overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
                //slide from left to right
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAsynchronousTask();
                Toast.makeText(Spectrometer.this, "Refresh", Toast.LENGTH_SHORT).show();
                dsh_refresh.startAnimation(rotate);
            }
        });

        // check internet
        if (Util.Operations.isOnline(Spectrometer.this)) {
            callAsynchronousTask();
        } else {
            Toast.makeText(getApplicationContext(), "Please check your Network", Toast.LENGTH_LONG).show();
        }

    }

    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        final Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {

                            int count = getPreferences(MODE_PRIVATE).getInt("count_key", 0);
                            if (count >= 100) {

                                System.out.println("The count  value if" + count);
                                getPreferences(MODE_PRIVATE).edit().putInt("count_key", count).apply();
                                timer.cancel();
                                getPreferences(MODE_PRIVATE).edit().putInt("count_key", 0).apply();

                            } else {
                                System.out.println("The count  value else" + count);
                                count++;
                                getPreferences(MODE_PRIVATE).edit().putInt("count_key", count).apply();

                                new Myspectroasync().execute();
                                gpsTracker = new GPSTracker(Spectrometer.this);

                                if (gpsTracker.canGetLocation()) {
                                    stringLatitude = String.valueOf(gpsTracker.latitude);
                                    Log.e("map", "LAT:" + stringLatitude);

                                    stringLongitude = String.valueOf(gpsTracker.longitude);
                                    Log.e("map", "LONG:" + stringLongitude);
                                } else {
                                    Log.e("TAG", "MAP:" + gpsTracker);
                                }


                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 5000);
    }

    private class Myspectroasync extends AsyncTask<String, Void, String> {
        public Myspectroasync() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest("http://192.168.0.105:8080/gas_test/gas/index", json);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("tag", "Response from the server" + s);
            super.onPostExecute(s);
            if (s != null && s.equals("null")) {
                Log.d("TAG1", "Response" + s);
                //  Toast.makeText(getApplicationContext(), "Null value return", Toast.LENGTH_LONG).show();
            } else {
                try {

                    //  Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();
                    JSONObject result = new JSONObject(s);
                    String time = result.getString("time");
                    String row_0 = result.getString("row_0");
                    String row_1 = result.getString("row_1");
                    String row_2 = result.getString("row_2");
                    String row_3 = result.getString("row_3");
                    final String lat = stringLatitude;
                    final String lon = stringLongitude;
                    String latlong = lat + "," + lon;
                    Double doube = Double.valueOf(row_2.substring(15, row_2.length()));
                    doube = doube*103.56;
                    final HashMap<String, String> map = new HashMap<String, String>();
                    map.put("time", time.substring(11, time.length()));
                    map.put("row_0", row_0.substring(15, row_0.length()));
                    map.put("row_1", row_1.substring(15, row_1.length()));
                   // map.put("row_2", row_2.substring(15, row_2.length()));
                    map.put("row_2",String.valueOf(doube));
                    map.put("latm", lat);
                    map.put("lonm", lon);
                    oslist.add(map);
                    list1 = (ListView) findViewById(R.id.list1);
                    Log.e("map1", "latlong" + latlong);

                    latia.add(lat);
                    longia.add(lon);

                    SimpleAdapter k = new SimpleAdapter(Spectrometer.this, oslist, R.layout.list_view, new String[]{"time", "row_2", "latm", "lonm"}, new int[]{R.id.txt_webtime, R.id.text_regval, R.id.latitu, R.id.longitu}) {
                        @Override
                        public View getView(final int position, View convertView, ViewGroup parent) {
                            View v = super.getView(position, convertView, parent);
                            if (position % 2 == 1) {
                                v.setBackgroundColor(Color.parseColor("#B2E9F0"));
                            } else {
                                v.setBackgroundColor(Color.parseColor("#CBF9FE"));
                            }
                            ImageView lct = (ImageView) v.findViewById(R.id.locate);
                            TextView tv_lat = (TextView) v.findViewById(R.id.latitu);
                            TextView tv_lng = (TextView) v.findViewById(R.id.longitu);
                            TextView tv_reg = (TextView) v.findViewById(R.id.text_regval);
                            TextView tv_time = (TextView) v.findViewById(R.id.txt_webtime);

                            reg = tv_reg.getText().toString();

                            if(Double.valueOf(reg)> 0.383172){
                                tv_reg.setTextColor(getResources().getColor(R.color.red));
                            }




                            atime = tv_time.getText().toString();
                            alat = tv_lat.getText().toString();
                            along = tv_lng.getText().toString();
                            new Updatespectroasync(reg, alat, along, aadsf, atime).execute();




                            lct.setOnClickListener(new View.OnClickListener() {
                                double latit;
                                double lng;
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    String s = oslist.get(position).get("time");
                                    Log.e("tag", "timeee" + s);
                                    String latitude = oslist.get(position).get("latm");
                                    String longitude = oslist.get(position).get("lonm");
                                    // Log.e("tag","lat"+latitude);
                                    // Log.e("tag","lon"+longitude);
                                    latit = Double.parseDouble(latitude);
                                    // lng = Double.parseDouble(longitude.getBytes().toString());
                                    lng = Double.parseDouble(longitude);
                                    Log.e("tag", "lon" + latit);
                                    //  Log.e("TAG","Time"+s);
                                    Intent intent = new Intent(Spectrometer.this, MapDemoActivity.class);
                                    // Passing latitude and longitude to the MapActiv
                                    intent.putExtra("lat", latit);
                                    intent.putExtra("lng", lng);
                                    intent.putExtra("ti", s);
                                    intent.putExtra("reg", Double.valueOf(reg));
                                    intent.putStringArrayListExtra("lata",latia);
                                    intent.putStringArrayListExtra("lona",longia);

                                    startActivity(intent);
                                }
                            });

                            return v;
                        }


                    };
                    list1.setAdapter(k);
                    Log.d("tag", "Response from the server" + s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class Updatespectroasync extends AsyncTask<String, Void, String> {
        String str_reg, str_lat, str_long, str_date, str_time;

        public Updatespectroasync(String reg, String alat, String along, String aadsf, String atime) {
            this.str_reg = reg;
            this.str_lat = alat;
            this.str_long = along;
            this.str_date = aadsf;
            this.str_time = atime;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("reg_value", str_reg);
                jsonObject.put("lat", str_lat);
                jsonObject.put("lan", str_long);
                jsonObject.put("date", str_date);
                jsonObject.put("time", str_time);
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest("http://192.168.0.105:8080/gas_test/gas/update_data", json);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("tag", "updateresult" + s);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Spectrometer.this, Dashboard.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        //overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
        //slide from left to right
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }
}
