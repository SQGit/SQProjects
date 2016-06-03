package www.sqindia.net.ilerasoftadmin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.imanoweb.calendarview.CustomCalendarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by RSA on 1/9/2016.
 */
public class AvailableFragment extends Fragment implements MonthLoader.MonthChangeListener,
        WeekView.EventClickListener, WeekView.EventLongPressListener, WeekView.EmptyViewClickListener, CalendarDatePickerDialogFragment.OnDateSetListener {
    ArrayList<String> worldlist2;
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_WEEK_VIEW;
    private WeekView mWeekView;
    public boolean flag=true;
    public static String URL_REGISTER1 = "http://sqindia01.cloudapp.net/cologix/api/v1/booking/getAvailability";
    public static String URL_REGISTER = "http://sqindia01.cloudapp.net/cologix/api/v1/equipment/getCategories";
    public static String URL_REGISTER2 = "http://sqindia01.cloudapp.net/cologix/api/v1/equipment/getCategoryDetailsByID";
    static String CategoryNAME = "categoryName";
    public String token, categoryId;
    JSONObject jsonObject;
    SweetAlertDialog pDialog;
    public List<WeekViewEvent> MYHERO = new ArrayList<WeekViewEvent>();
    JSONObject jsonobject;
    JSONArray jsonarray;
    View v;
    ArrayList<CategoryList> CaL;
    CustomCalendarView calendarView;
    private SimpleCursorAdapter mAdapter;
    ArrayList<HashMap<String, String>> contactList;
    CategoryJsonAdater eja;
    List<String> countryList;
    List<SearchSuggestion> ss;
    Dialog d;
    ArrayAdapter<String> adapter;
    String dateFrom, dateTo,str,available_tv;
    static String categoryName;
    SimpleCursorAdapter1 sa;
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
    private Handler mUiHandler = new Handler();
    private List<FloatingActionMenu> menus = new ArrayList<>();
    ImageView img;
    int loader;
    TextView categoryName_tv,status_tv,date_tv,time_tv,desc_tv,month_year;
    String bookingRefId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.schedule_calendar, container, false);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        token = sharedPreferences.getString("TOKEN", "");
        new Category().execute();
        final String[] from = new String[]{"categoryName"};
        final int[] to = new int[]{android.R.id.text1};
        mAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        Schedule_Test.ll.setVisibility(View.VISIBLE);
        img = (ImageView)root.findViewById(R.id.img);
        categoryName_tv=(TextView)root.findViewById(R.id.tv1);
        status_tv=(TextView)root.findViewById(R.id.tv2);
        date_tv=(TextView)root.findViewById(R.id.tv3);
        time_tv=(TextView)root.findViewById(R.id.tv4);
        desc_tv=(TextView)root.findViewById(R.id.tv_desc);
        status_tv.setVisibility(View.INVISIBLE);
        categoryName_tv.setVisibility(View.INVISIBLE);
        desc_tv.setVisibility(View.INVISIBLE);
        date_tv.setVisibility(View.INVISIBLE);
        time_tv.setVisibility(View.INVISIBLE);
        v=(View)root.findViewById(R.id.view);
        v.setVisibility(View.INVISIBLE);
        month_year = (TextView) root.findViewById(R.id.month_year);

        loader = R.drawable.d;
        ImageLoader imgLoader = new ImageLoader(getActivity());
        contactList = new ArrayList<HashMap<String, String>>();
        /*############################################### FAB ######################################################## */
        final FloatingActionMenu menu1 = (FloatingActionMenu) root.findViewById(R.id.menu1);
        final FloatingActionButton programFab1 = new FloatingActionButton(getActivity());
        programFab1.setButtonSize(FloatingActionButton.SIZE_MINI);
        programFab1.setLabelText("status");
        programFab1.setImageResource(R.drawable.ic_menu);
        menu1.addMenuButton(programFab1);
        final FloatingActionButton programFab3 = new FloatingActionButton(getActivity());
        programFab3.setButtonSize(FloatingActionButton.SIZE_MINI);
        programFab3.setLabelText("Cancellation");
        programFab3.setImageResource(R.drawable.ic_menu);
        menu1.addMenuButton(programFab3);
        programFab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getActivity(), TrackingStatus.class);
                startActivity(i);
            }
        });
        programFab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TrackingCancel.class);
                startActivity(i);

            }
        });
        menu1.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menu1.isOpened())
                {

                }

                menu1.toggle(true);
            }
        });
        ContextThemeWrapper context = new ContextThemeWrapper(getActivity(), R.style.MenuButtonsStyle);
        FloatingActionButton programFab2 = new FloatingActionButton(context);
        programFab2.setLabelText("Programmatically added button");
        programFab2.setImageResource(R.drawable.ic_menu);
        menus.add(menu1);
        FloatingActionButton programFab4 = new FloatingActionButton(context);
        programFab4.setLabelText("Programmatically added button");
        programFab4.setImageResource(R.drawable.ic_menu);
        menus.add(menu1);
        menu1.hideMenuButton(false);
        int delay = 400;
        for (final FloatingActionMenu menu : menus) {
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    menu.showMenuButton(true);
                }
            }, delay);
            delay += 150;
        }
        menu1.setClosedOnTouchOutside(true);
        countryList = new ArrayList<String>();

        Schedule_Test.radioGroupcheck.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setupDateTimeInterpreter(checkedId == R.id.action_week_view);
                switch (checkedId) {
                    case R.id.radio0:
                        if (mWeekViewType != TYPE_DAY_VIEW)
                        {
                            mWeekViewType = TYPE_DAY_VIEW;
                            mWeekView.setNumberOfVisibleDays(1);
                            mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                            mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 17, getResources().getDisplayMetrics()));
                            mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                        }

                        break;

                    case R.id.radio1:


                        if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                            // item.setChecked(!item.isChecked());
                            mWeekViewType = TYPE_THREE_DAY_VIEW;
                            mWeekView.setNumberOfVisibleDays(3);
                            // Lets change some dimensions to best fit the view.
                            mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                            mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics()));
                            mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                        }
                        // do operations specific to this selection
                        break;

                    case R.id.radio2:
                        if (mWeekViewType != TYPE_WEEK_VIEW) {
                            // item.setChecked(!item.isChecked());
                            mWeekViewType = TYPE_WEEK_VIEW;
                            mWeekView.setNumberOfVisibleDays(7);

                            // Lets change some dimensions to best fit the view.
                            mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                            mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13, getResources().getDisplayMetrics()));
                            mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                        }
                        // do operations specific to this selection
                        break;
                    default:
                        break;
                }

            }
        });


        // the week view. This is optional.
        mWeekView = (WeekView) root.findViewById(R.id.weekView);
        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);
        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);
        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);
        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter(false);
        mWeekView.setEmptyViewClickListener(this);
/*
        mWeekView.setEmptyViewClickListener(new WeekView.EmptyViewClickListener() {
            @Override
            public void onEmptyViewClicked(Calendar calendar) {

            }
        });
        mWeekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent weekViewEvent, RectF rectF)
            {

            }


        });*/


        Schedule_Test.from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment().setOnDateSetListener(AvailableFragment.this);
                flag=true;
                cdp.setThemeDark(true);
                cdp.show(getChildFragmentManager(),
                        FRAG_TAG_DATE_PICKER);
                Schedule_Test.to.setText("");



            }
        });
        Schedule_Test.to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(AvailableFragment.this);
                flag=false;
                cdp.setThemeDark(true);

                cdp.show(getChildFragmentManager(), FRAG_TAG_DATE_PICKER);


            }

        });
        return root;
    }





    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        //  inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    // ----------------------------------------------------------------------------------------------------------------------------------
    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat month_date = new SimpleDateFormat("MMMM yyyy");
                String month_name = month_date.format(date.getTime());

                month_year.setText(month_name/*+" "+Year_name*/);
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }


    //-----------------------------------------------------------------------------------------------------------------------------------
    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {


        // Populate the week view with some events.
       /*List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();*/
        for (int i = 0; i < MYHERO.size(); i++) {
            Log.d("tag", "<-----event in onMonthChange----->" + MYHERO.get(i).getName() + " " + MYHERO.get(i).getStartTime().toString());
        }
        List<WeekViewEvent> zz = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent ev : MYHERO) {
            if (ev.getStartTime().get(Calendar.MONTH) == (newMonth - 1) && ev.getStartTime().get(Calendar.YEAR) == newYear) {
                zz.add(ev);
                Log.d("tag", "<-----event in loop----->" + newMonth + " : " + ev.getStartTime().get(Calendar.MONTH) + " =>" + ev.getName() + " " + ev.getStartTime().toString());
            }
        }
        Log.d("tag", "<-----event in call----->" + newMonth + " : " + newYear);
        return zz;

        //return MYHERO;
    }

    @Override
    public void onEventClick(WeekViewEvent weekViewEvent, RectF rectF) {

        //Toast.makeText(getActivity(), "Clicked " + rectF, Toast.LENGTH_SHORT).show();
       // weekViewEvent.getName();
        Log.d("tag", "<------------>" + weekViewEvent.getId());
        Log.d("tag", "<------------>" + weekViewEvent.getName());
         str=weekViewEvent.getName();

       // String str = "This is String";
        String[] splited = str.split("\\s+");

         available_tv=splited[0];
        String split_second=splited[1];
        String split_three=splited[2];
        Log.d("tag","<------SPLITTED------>"+available_tv +"splitted two"+split_second);

        new MyActivityAsync1().execute();


    }

    @Override
    public void onEventLongPress(WeekViewEvent weekViewEvent, RectF rectF) {

    }

    @Override
    public void onEmptyViewClicked(Calendar time) {


        Calendar endTime = (Calendar) time.clone();
        endTime.add(Calendar.HOUR, 1);

        WeekViewEvent event = new WeekViewEvent(20, "New event", time, endTime);


    }



    @Override
    public void onDateSet(CalendarDatePickerDialogFragment calendarDatePickerDialogFragment, int year, int month, int dayOfMonth) {
        if(flag)
        {
            dateFrom=year+"-"+(month+1)+"-"+dayOfMonth;
            Log.d("tag","<---datefrom---->"+dateFrom);


            Schedule_Test.from.setText(year + "-" + (month + 1) + "-" + dayOfMonth);


        }else {
            dateTo=year+"-"+(month+1)+"-"+dayOfMonth;

            Schedule_Test.to.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
            new MyActivityAsync(categoryId,dateFrom,dateTo).execute();



        }
    }
    @Override
    public void onResume() {
        // Example of reattaching to the fragment
        super.onResume();
        CalendarDatePickerDialogFragment calendarDatePickerDialogFragment = (CalendarDatePickerDialogFragment) getChildFragmentManager()
                .findFragmentByTag(FRAG_TAG_DATE_PICKER);
        if (calendarDatePickerDialogFragment != null) {
            calendarDatePickerDialogFragment.setOnDateSetListener(this);
        }
    }

    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Searchview $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    //##################################################### category_search  online ##########################################################
    class Category extends AsyncTask<String, Void, String> {

        public Category() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("tag", "s1");
        }

        protected String doInBackground(String... params) {
            CaL = new ArrayList<CategoryList>();
            // Create an array to populate the spinner
            worldlist2 = new ArrayList<String>();
            String json = "", jsonStr = "";
            try {

                JSONObject jsonObject = new JSONObject();
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
            String msg;
            try {
                JSONObject jo = new JSONObject(s);


                String status = jo.getString("status");
                msg = jo.getString("message");
                Log.d("tag", "<-----Status----->" + status);
                JSONObject data1 = jo.getJSONObject("data");
                jsonarray = data1.getJSONArray("categories");

                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);

                    CategoryList clll = new CategoryList();

                    clll.setCategoryId(jsonobject.optString("categoryId"));
                    clll.setCategoryName(jsonobject.optString("categoryName"));
                    CaL.add(clll);

                    // Populate spinner with country names

                    worldlist2.add(jsonobject.optString("categoryName"));
                    Log.d("tag", "<----worldlist----->" + worldlist2);

                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Schedule_Test.mySpinner2 = (Spinner)findViewById(R.id.my_spinner1);
            // Spinner adapter
            Schedule_Test.mySpinner2.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, worldlist2));
            Schedule_Test.mySpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    // TODO Auto-generated method stub
                    // String selectedItemText = (String) arg0.getItemAtPosition(position);

                    if (position > -1) {
                        categoryId = CaL.get(position).getCategoryId();
                        categoryName=CaL.get(position).getCategoryName();
                        Log.d("tag", "<----categoryId----->" + categoryId);
                        Schedule_Test.from.setText("");
                        Schedule_Test.to.setText("");
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });

        }
    }


    //##################################################### category_search  online ##########################################################

//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Searchview $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$


    public class CategoryJsonAdater extends BaseAdapter {

        // Declare Variables
        Context context;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> data;
        HashMap<String, String> resultp = new HashMap<String, String>();
        FragmentActivity activity;


        public CategoryJsonAdater(Context context, ArrayList<HashMap<String, String>> arraylist) {
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


            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                }
            });


            return itemView;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Schedule_Test.ll.setVisibility(View.GONE);

    }

    class MyActivityAsync extends AsyncTask<String, Void, String> {
        String categoryId, dateFrom, dateTo;


        public MyActivityAsync(String categoryId, String dateFrom, String dateTo) {
            this.categoryId = categoryId;
            this.dateFrom = dateFrom;
            this.dateTo = dateTo;

            String json = "", jsonStr = "";
        }

        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.getWindow().setLayout(1800, 400);
            pDialog.show();


        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";
            try {


                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("categoryId", categoryId);
                jsonObject.accumulate("dateFrom", dateFrom);
                jsonObject.accumulate("dateTo", dateTo);

                json = jsonObject.toString();

                return jsonStr = HttpUtils.makeRequest1(URL_REGISTER1, json, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.d("tag", "<-----rerseres---->" + jsonStr);
            super.onPostExecute(jsonStr);
            pDialog.dismiss();


            try {
                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                //String msg = jo.getString("message");
                Log.d("tag", "<-----Status----->" + status);
                //      JSONObject data = jo.getJSONObject("data");
                //   {"status":"SUCCESS","message":"","data":{"bookings":[{"bookingCount":"0","available":"18","db_date":"2015-12-25"}]}}
                if (status.equals("SUCCESS")) {
                    JSONObject data = jo.getJSONObject("data");
                    JSONArray jCastArr = data.getJSONArray("bookings");
                    try {
                        MYHERO.clear();
                        for (int i = 0; i < jCastArr.length(); i++) {
                            JSONObject mydata = jCastArr.getJSONObject(i);
                            SimpleDateFormat htf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Calendar db_datef = Calendar.getInstance();
                            Calendar db_datee = Calendar.getInstance();
                            db_datef.setTime(htf.parse((String) mydata.getString("db_date") + " 00:00:00"));
                            db_datee.setTime(htf.parse((String) mydata.getString("db_date") + " 23:59:59"));
                            int tint = Integer.parseInt((String) mydata.getString("available"));
                            String tmp = tint == 0 ? "No availability" : (String) mydata.getString("available") + " Available";
                            WeekViewEvent event = new WeekViewEvent(20, tmp + String.format("  %02d:%02d %s/%d", db_datef.get(Calendar.HOUR_OF_DAY), db_datef.get(Calendar.MINUTE), db_datef.get(Calendar.MONTH) + 1, db_datef.get(Calendar.DAY_OF_MONTH)), db_datef, db_datee);


                            //WeekViewEvent event = new WeekViewEvent(20, (String) mydata.getString("categoryId") ,(String) mydata.getString("categoryName")+ String.format("%02d:%02d %s/%d", bookFrom.get(Calendar.HOUR_OF_DAY), bookFrom.get(Calendar.MINUTE), bookFrom.get(Calendar.MONTH) + 1, bookFrom.get(Calendar.DAY_OF_MONTH)), bookFrom, bookTo);
                            switch (tint) {
                                case 0:
                                    event.setColor(getResources().getColor(R.color.event_color_02));
                                    break;
                                default:
                                    event.setColor(getResources().getColor(R.color.event_color_03));
                                    break;
                            }


                            MYHERO.add(event);
                            Log.d("tag", "<-----event in onPostExecute----->" + event.getName() + " " + event.getStartTime().toString());
                        }
                    } catch (Exception e) {
                        Log.d("tag", "<-----zz----->" + e.getMessage());
                    }
                    mWeekView.notifyDatasetChanged();
                    pDialog.dismiss();
                } else
                {
                    pDialog.dismiss();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("WARNING MESSAGE!!!")
                            .setContentText("No Event Found")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                            {
                                @Override
                                public void onClick(SweetAlertDialog sDialog)
                                {
                                    sDialog.cancel();
                                }
                            })
                            .show();
                }
            } catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.d("tag", "<-----zz----->" + e.getMessage());
            }

        }

    }

    class MyActivityAsync1 extends AsyncTask<String, Void, String> {
        public MyActivityAsync1() {
            String json = "", jsonStr = "";
        }

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.getWindow().setLayout(1800, 400);
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("categoryId", categoryId);
                //jsonObject.accumulate("categoryId",categoryId);
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest1(URL_REGISTER2, json, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.d("tag", "<-----RESULT---->" + jsonStr);
            super.onPostExecute(jsonStr);
            pDialog.dismiss();


            try {
                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                //String msg = jo.getString("message");
                Log.d("tag", "<-----<<<<<<<<<Status>>>>>>>----->" + status);

                JSONObject data1 = jo.getJSONObject("data");
                Log.d("tag", "<-----Status----->" + data1);
                JSONObject cd = data1.getJSONObject("categoryDetails");

               // JSONArray jsonarray = data1.getJSONArray("categoryDetails");
                //JSONObject

             //   for (int i = 0; i < jsonarray.length(); i++) {
                   // JSONObject joo = jsonarray.getJSONObject(i);

                    String categoryName = cd.getString("categoryName");
                    String categoryDescription = cd.getString("categoryDescription");
                    //String bookFrom = joo.getString("bookFrom");
                    String categoryImage = cd.getString("categoryImage");

                    //String status1 = joo.getString("status");
                    ImageLoader imgLoader = new ImageLoader(getActivity());
                    imgLoader.DisplayImage(categoryImage, loader, img);
                    categoryName_tv.setText(categoryName);
                status_tv.setText(available_tv + " AVAILABLE");
                date_tv.setText(dateFrom);
                time_tv.setText(dateTo);
                    desc_tv.setText(categoryDescription);

                status_tv.setVisibility(View.VISIBLE);
                categoryName_tv.setVisibility(View.VISIBLE);
                desc_tv.setVisibility(View.VISIBLE);
                date_tv.setVisibility(View.VISIBLE);
                time_tv.setVisibility(View.VISIBLE);
                v.setVisibility(View.VISIBLE);





                //String categoryId = joo.getString("categoryId");




            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.d("tag", "<-----zz----->" + e.getMessage());
            }

        }


    }
}
