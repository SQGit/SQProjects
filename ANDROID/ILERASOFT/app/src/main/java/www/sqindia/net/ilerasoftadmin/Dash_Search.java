package www.sqindia.net.ilerasoftadmin;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.imanoweb.calendarview.CalendarListener;
import com.imanoweb.calendarview.CustomCalendarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by TekCampuz on 12/11/2015.
 */
public class Dash_Search extends Fragment {
    public static String URL_REGISTER = "http://sqindia01.cloudapp.net/cologix/api/v1/equipment/getCategoriesByName";
    public static String URL_BOOKNOW = "http://sqindia01.cloudapp.net/cologix/api/v1/booking/create";
    EditText eqName, from_date, End_Date, From_Time, End_Time, hospital_name;
    CustomCalendarView calendarView;
    EqupimentJsonAdater eja;
    ArrayList<HashMap<String, String>> contactList;
    Context context;
    String test = "x";
    String token;
    static final int DATE_DIALOG_ID = 0;
    static String NAME = "categoryName";
    static String ADD = "categoryDescription";
    static String ID = "categoryId";
    private int mYear;
    private int mMonth;
    private int mDay;
    ListView list;
    String equipmentName, hospitalName, categoryId, bookFrom, bookTo, bookedQuantity;
    private PopupWindow pwindo;
    View root;
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.dash_search, container, false);
        Bundle args = getArguments();
        equipmentName = args.getString("YourKey");

        Log.d("tag", "<-----equipmentNNNNNNNNNNName------>" + equipmentName);

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        token = sharedPreferences.getString("TOKEN", "");
        hospitalName = sharedPreferences.getString("hospitalName", "");
        contactList = new ArrayList<HashMap<String, String>>();
        list = (ListView) root.findViewById(R.id.list);

        new SearchAsync().execute();


        return root;

    }

    class SearchAsync extends AsyncTask<String, Void, String> {
        public SearchAsync() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("tag", "s1");
        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";

            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("categoryName", equipmentName);
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest1(URL_REGISTER, json, token);


            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }


            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("tag", "<-----RESULTTTTTTT---->" + s);
            super.onPostExecute(s);
            try {
                JSONObject jo = new JSONObject(s);


                String status = jo.getString("status");
                String msg = jo.getString("message");
                Log.d("tag", "<-----Status----->" + status);

                if (status.equals("SUCCESS")) {
                    JSONObject data1 = jo.getJSONObject("data");

                    JSONArray ja = data1.getJSONArray("categories");


                    for (int count = 0; count < ja.length(); count++) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        data1 = ja.getJSONObject(count);

                        map.put("categoryId", data1.getString("categoryId"));
                        map.put("categoryName", data1.getString("categoryName"));
                        map.put("categoryDescription", data1.getString("categoryDescription"));
                        //map.put("brandName", data1.getString("brandName"));


                        contactList.add(map);
                    }

                    eja = new EqupimentJsonAdater(getActivity(), contactList);


                    list.setAdapter(eja);

                } else if (status.equals("FAILED")) {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("MESSAGE")
                            .setContentText("" + msg)
                            .show();

                } else

                {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("oops")
                            .setContentText("" + msg)
                            .show();
                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }


    }

    public class EqupimentJsonAdater extends BaseAdapter {

        // Declare Variables
        Context context;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> data;
        HashMap<String, String> resultp = new HashMap<String, String>();
        FragmentActivity activity;


        public EqupimentJsonAdater(Context context, ArrayList<HashMap<String, String>> arraylist) {
            this.context = context;
            data = arraylist;

        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            // Declare Variables
            TextView name;
            TextView add;
            TextView brand;
            ImageView bookn_now;
            String categoryId, bookFrom, bookTo, bookedQuantity;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.list_item, parent, false);
            resultp = data.get(position);
            name = (TextView) itemView.findViewById(R.id.tv1);
            brand = (TextView) itemView.findViewById(R.id.tv3);
            add = (TextView) itemView.findViewById(R.id.tv2);
            bookn_now = (ImageView) itemView.findViewById(R.id.idd);
            name.setText(resultp.get(Dash_Search.NAME));
            add.setText(resultp.get(Dash_Search.ADD));
            bookn_now.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
                    dialog.setContentView(R.layout.addevent);

                    final String categoryId, bookFrom, bookTo, bookedQuantity;
                    final String c_id, book, to, bookq;

                    Button submit;
                    hospital_name = (EditText) dialog.findViewById(R.id.HospitalName);
                    submit = (Button) dialog.findViewById(R.id.submited);
                    eqName = (EditText) dialog.findViewById(R.id.equipment_Name);
                    from_date = (EditText) dialog.findViewById(R.id.From_Date);
                    End_Date = (EditText) dialog.findViewById(R.id.End_Date);
                    From_Time = (EditText) dialog.findViewById(R.id.From_Time);
                    End_Time = (EditText) dialog.findViewById(R.id.End_Time);


                    eqName.setText(data.get(position).get(Dash_Search.NAME));
                    hospital_name.setText(hospitalName);
                    c_id = data.get(position).get(Dash_Search.ID);
                    bookFrom = from_date + "00:00:00";
                    bookTo = End_Date + "00:00:00";
                    bookedQuantity = "2";
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.show();
                /*       if ((hospital_name.getText().toString().equals("")) &&
                                    (eqName.getText().toString().equals("")) &&
                                    (from_date.getText().toString().equals("")) &&
                                    (End_Date.getText().toString().equals("")) &&
                                    (From_Time.getText().toString().equals("")) &&
                                    (End_Time.getText().toString().equals("")))
                            {
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("ALERT MESSAGE!!!")
                                        .setContentText("Fields Invalid...")
                                        .show();
                            } else {
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("SUCCESS MESSAGE!!!")
                                        .setContentText("Equipment Booked Succesfully...")

                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                Intent i = new Intent(getActivity(), Login.class);
                                                startActivity(i);
                                            }
                                        })
                                        .show();

                            }*/

                      new MyActivityAsync(c_id, bookFrom, bookTo, bookedQuantity).execute();

                        }
                    });
                    From_Time.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {


                            return false;
                        }
                    });
                    End_Time.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {

                            return false;
                        }
                    });

                    End_Date.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            final Calendar c = Calendar.getInstance();
                            int hour = c.get(Calendar.HOUR_OF_DAY);
                            int minute = c.get(Calendar.MINUTE);

                            final Dialog d = new Dialog(v.getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                            d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                            d.requestWindowFeature(Window.FEATURE_NO_TITLE); //before

                            d.setContentView(R.layout.calender);
                            d.getCurrentFocus();
                            d.getWindow().setLayout(400, 400);
                            d.getWindow().setGravity(Gravity.BOTTOM);
                            d.show();


                            calendarView = (CustomCalendarView) d.findViewById(R.id.calendar_view);
                            Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());
                            calendarView.setFirstDayOfWeek(Calendar.MONDAY);
                            calendarView.setShowOverflowDate(false);
                            calendarView.refreshCalendar(currentCalendar);

                            calendarView.setCalendarListener(new CalendarListener() {
                                @Override
                                public void onDateSelected(Date date) {

                                    d.dismiss();
                                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                    End_Date.setText(df.format(date));

                                }

                                @Override
                                public void onMonthChanged(Date date) {
                                    SimpleDateFormat df = new SimpleDateFormat("MM-yyyy");

                                }
                            });
                            d.show();
                            return false;
                        }
                    });
                    from_date.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            final Dialog d = new Dialog(v.getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                            d.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
                            d.setContentView(R.layout.calender);
                            d.getCurrentFocus();
                            d.getWindow().setLayout(400, 400);
                            calendarView = (CustomCalendarView) d.findViewById(R.id.calendar_view);
                            Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());
                            calendarView.setFirstDayOfWeek(Calendar.MONDAY);
                            calendarView.setShowOverflowDate(false);
                            calendarView.refreshCalendar(currentCalendar);
                            calendarView.setCalendarListener(new CalendarListener() {
                                @Override
                                public void onDateSelected(Date date) {

                                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                    from_date.setText(df.format(date));
                                    d.dismiss();

                                }

                                @Override
                                public void onMonthChanged(Date date) {
                                    SimpleDateFormat df = new SimpleDateFormat("MM-yyyy");
                                    d.dismiss();
                                }
                            });
                            d.show();
                            return false;
                        }
                    });


                    Button dialogButton = (Button) dialog.findViewById(R.id.submited);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           // dialog.dismiss();
                        }
                    });

                    dialog.show();


                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                }
            });


            return itemView;
        }

        class MyActivityAsync extends AsyncTask<String, Void, String> {
            final String categoryId, bookFrom, bookTo, bookedQuantity;

            // hospital_name, user_id, password,email_id,phone_no

            public MyActivityAsync(String categoryId, String bookFrom, String bookTo, String bookedQuantity) {
                this.categoryId = categoryId;
                this.bookFrom = bookFrom;
                this.bookTo = bookTo;
                this.bookedQuantity = bookedQuantity;

            }

            protected void onPreExecute() {
                super.onPreExecute();
                Log.d("tag", "s1");

          /*  pDialog = new SweetAlertDialog(RegisterForm.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.getWindow().setLayout(1800, 400);
            pDialog.show();*/
            }

            @Override
            protected String doInBackground(String... params) {
                // TODO Auto-generated method stub

                String json = "", jsonStr = "";

                try {
                    String categoryId, bookFrom, bookTo, bookedQuantity;

                    // 3. build jsonObject
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("categoryId", "4");
                    jsonObject.accumulate("bookFrom", "2015-01-08 00:00:00");
                    jsonObject.accumulate("bookTo", "2016-01-15 00:00:00");
                    jsonObject.accumulate("bookedQuantity", "2");

                    //{"hospitalId":onew,"hospitalAddress":"smaple Addres","userName":"mufeed_staff","password":"mufeed123","email":"mufeedk@gmail.com","phone":"12213"}
                    // 4. convert JSONObject to JSON to String
                    json = jsonObject.toString();
                    return jsonStr = HttpUtils.makeRequest(URL_BOOKNOW, json);
                } catch (Exception e) {
                    Log.d("InputStream", e.getLocalizedMessage());
                }

                return null;

            }

            @Override
            protected void onPostExecute(String s) {
                Log.d("tag", "<-----rerseres---->" + s);
                super.onPostExecute(s);

                try {
                    JSONObject jo = new JSONObject(s);

                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                    Log.d("tag", "<-----Status----->" + status);


                    if (status.equals("SUCCESS")) {

                        new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("SUCCESS MESSAGE!!!")
                                .setContentText("Equipment Booked Succesfully...")

                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent i = new Intent(getActivity(), Login.class);
                                        startActivity(i);
                                    }
                                })
                                .show();


                    } else {

                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("ALERT MESSAGE!!!")
                                .setContentText("Equipment Not Booked..")
                                .show();


                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


}