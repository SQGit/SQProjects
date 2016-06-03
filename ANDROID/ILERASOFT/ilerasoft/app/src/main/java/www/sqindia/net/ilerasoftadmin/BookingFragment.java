package www.sqindia.net.ilerasoftadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by RSA on 1/9/2016.
 */
public class BookingFragment extends Fragment implements MonthLoader.MonthChangeListener,
        WeekView.EventClickListener, WeekView.EventLongPressListener, WeekView.EmptyViewClickListener {
    String ImagePath;
    ImageView img;
    int loader;
    TextView categoryName_tv,status_tv,date_tv,time_tv,desc_tv;
    String bookingRefId;
    public static String URL_REGISTER1= "http://sqindia01.cloudapp.net/cologix/api/v1/booking/getByRefId";

    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private WeekView mWeekView;
    public static String URL_REGISTER = "http://sqindia01.cloudapp.net/cologix/api/v1/booking/getAll";
    String token;
    JSONObject jsonObject;
    SweetAlertDialog pDialog;
    public List<WeekViewEvent> MYHERO = new ArrayList<WeekViewEvent>();
   // RadioButton tab1, tab2;
    TextView month_year;


    private Handler mUiHandler = new Handler();
    private List<FloatingActionMenu> menus = new ArrayList<>();
    // FloatingActionButton fab;


    static String category_Description ,category_Image,categoryId;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.schedule_calendar, container, false);
        Schedule_Test.ll.setVisibility(View.INVISIBLE);

        month_year = (TextView) root.findViewById(R.id.month_year);
        //ImagePath=BookingFragment.category_Image;
        img = (ImageView)root.findViewById(R.id.img);
        categoryName_tv=(TextView)root.findViewById(R.id.tv1);
        status_tv=(TextView)root.findViewById(R.id.tv2);
        date_tv=(TextView)root.findViewById(R.id.tv3);
        time_tv=(TextView)root.findViewById(R.id.tv4);
        desc_tv=(TextView)root.findViewById(R.id.tv_desc);

         loader = R.drawable.d;
        ImageLoader imgLoader = new ImageLoader(getActivity());

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
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TrackingStatus.class);
                startActivity(i);

                // Toast.makeText(getActivity(), programFab1.getLabelText(), Toast.LENGTH_SHORT).show();
            }
        });
        programFab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getActivity(), TrackingCancel.class);
                startActivity(i);

            }
        });
        menu1.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menu1.isOpened())
                {
                    //Toast.makeText(getActivity(), menu1.getMenuButtonLabelText(), Toast.LENGTH_SHORT).show();
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
    /*############################################### FAB ######################################################## */
        Schedule_Test.radioGroupcheck.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                                     @Override
                                                                     public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                                         setupDateTimeInterpreter(checkedId == R.id.action_week_view);
                                                                         switch (checkedId) {
                                                                             case R.id.radio0:
                                                                                 if (mWeekViewType != TYPE_DAY_VIEW) {
                                                                                     //checkedId.setChecked(!radio0.isChecked());
                                                                                     mWeekViewType = TYPE_DAY_VIEW;
                                                                                     mWeekView.setNumberOfVisibleDays(1);
                                                                                     // Lets change some dimensions to best fit the view.
                                                                                     mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                                                                                     mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 17, getResources().getDisplayMetrics()));
                                                                                     mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                                                                                 }
                                                                                 // do operations specific to this selection
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
                                                                                     mWeekView.setTodayBackgroundColor(Color.BLUE);
                                                                                     mWeekView.getDateTimeInterpreter();
                                                                                     mWeekView.getFirstVisibleDay();

                                                                                     // Lets change some dimensions to best fit the view.
                                                                                     mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                                                                                     mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13, getResources().getDisplayMetrics()));
                                                                                     mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));

                                                                                 }
                                                                                 // do operations specific to this selection
                                                                                 break;
                                                                             default:
                                                                                 break;
                                                                         }

                                                                     }
                                                                 }

        );



               /* Content_tab fragment2 = new Content_tab();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place_tab, fragment2);
                fragmentTransaction.commit();
*/
        /*tab2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Tab_two fragment = new Tab_two();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place_tab, fragment);
                fragmentTransaction.commit();

            }


        });

        tab1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Tab_one fragment2 = new Tab_one();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place_tab, fragment2);
                fragmentTransaction.commit();

            }


        });*/



        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        token = sharedPreferences.getString("TOKEN", "");
        new MyActivityAsync().execute();
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


        return root;


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class MyActivityAsync extends AsyncTask<String, Void, String> {
        public MyActivityAsync() {
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
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest1(URL_REGISTER, json, token);
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
                String msg = jo.getString("message");
                Log.d("tag", "<-----Status----->" + status);
                // JSONObject data = jo.getJSONObject("data");
                if (status.equals("SUCCESS"))
                {
                    JSONObject data = jo.getJSONObject("data");
                    JSONArray jCastArr = data.getJSONArray("bookings");
                    Log.d("tag", "<-----Send sessionToken----->" + token);
                    try {
                        MYHERO.clear();
                        for (int i = 0; i < jCastArr.length(); i++) {
                            JSONObject mydata = jCastArr.getJSONObject(i);
                            category_Description=mydata.getString("categoryDescription");
                            category_Image=mydata.getString("categoryImage");

                            //categoryId=mydata.getString("categoryId");

                            SimpleDateFormat htf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Calendar bookFrom = Calendar.getInstance();
                            Calendar bookTo = Calendar.getInstance();
                            bookFrom.setTime(htf.parse((String) mydata.getString("bookFrom")));
                            bookTo.setTime(htf.parse((String) mydata.getString("bookTo")));
                            WeekViewEvent event = new WeekViewEvent(20, (String) mydata.getString("bookingReferenceId") ,(String) mydata.getString("categoryName")+ String.format("%02d:%02d %s/%d", bookFrom.get(Calendar.HOUR_OF_DAY), bookFrom.get(Calendar.MINUTE), bookFrom.get(Calendar.MONTH) + 1, bookFrom.get(Calendar.DAY_OF_MONTH)), bookFrom, bookTo);
                            switch ((String) mydata.getString("status")) {
                                case "STATUS_BOOKED":
                                    event.setColor(getResources().getColor(R.color.event_color_01));
                                    break;
                                default:
                                    event.setColor(getResources().getColor(R.color.event_color_02));
                                    break;
                            }
                            switch ((String) mydata.getString("categoryName")) {
                                case "ECG":
                                    event.setColor(getResources().getColor(R.color.event_color_03));
                                   // event.setName("ECG");
                                    break;
                                case "X-RAY":
                                    event.setColor(getResources().getColor(R.color.mayrun));
                                    break;
                                case "ULTRASOUND":
                                    event.setColor(getResources().getColor(R.color.darkgreen));
                                    break;
                                case "ECHOCARDIOGRAPHY":
                                    event.setColor(getResources().getColor(R.color.event_color_04));
                                case "RADIOGRAPHIC SYSTEM DIGITAL":
                                    event.setColor(getResources().getColor(R.color.event_color_04));
                                case "MOBILE X-RAY":
                                    event.setColor(getResources().getColor(R.color.event_color_04));
                                    //MOBILE X-RAY
                                default:
                                    event.setColor(getResources().getColor(R.color.event_color_02));
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


                } else {
                    pDialog.dismiss();

                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("WARNING MESSAGE!!!")
                            .setContentText("No Event Found")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.cancel();
                                }
                            })
                            .show();

                }


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.d("tag", "<-----zz----->" + e.getMessage());
            }

        }


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
    }



    @Override
    public void onEventClick(WeekViewEvent weekViewEvent, RectF rectF) {

        Log.d("tag", "<-----category_Description----->" + category_Description);
        Log.d("tag", "<-----categoryId---->" +weekViewEvent.getName() );
            categoryId=weekViewEvent.getName();
        DisplayDetails();


    }

    private void DisplayDetails()
    {

        //imgLoader.DisplayImage(ImagePath, loader, img);

        //bookingRefId=BookingFragment.categoryId;
        Log.d("tag","categoryId"+categoryId);

        new MyActivityAsync1().execute();


    }

    @Override
    public void onEventLongPress(WeekViewEvent weekViewEvent, RectF rectF) {

    }

    @Override
    public void onEmptyViewClicked(Calendar time) {

        //  Toast.makeText(getActivity(), "Empty view long pressed: " + time, Toast.LENGTH_SHORT).show();
        Calendar endTime = (Calendar) time.clone();
        endTime.add(Calendar.HOUR, 1);

        WeekViewEvent event = new WeekViewEvent(20, "New event", time, endTime);
        Log.d("tag", "<-----emptyview----->" + event);



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
                jsonObject.accumulate("bookingRefId", categoryId);
                //jsonObject.accumulate("categoryId",categoryId);
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest1(URL_REGISTER1, json, token);
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
                String msg = jo.getString("message");
                Log.d("tag", "<-----<<<<<<<<<Status>>>>>>>----->" + status);

                JSONObject data1 = jo.getJSONObject("data");
                Log.d("tag", "<-----Status----->" + data1);

                JSONArray jsonarray = data1.getJSONArray("bookings");

                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject joo = jsonarray.getJSONObject(i);

                    String categoryName = joo.getString("categoryName");
                    String categoryDescription = joo.getString("categoryDescription");
                    String bookFrom = joo.getString("bookFrom");
                    String categoryImage = joo.getString("categoryImage");

                    String status1 = joo.getString("status");
                    ImageLoader imgLoader = new ImageLoader(getActivity());
                    imgLoader.DisplayImage(categoryImage, loader, img);
                    categoryName_tv.setText(categoryName);
                    status_tv.setText("BOOKED");

                    desc_tv.setText(categoryDescription);

                    StringTokenizer tk = new StringTokenizer(bookFrom);

                    String date1 = tk.nextToken();  // <---  yyyy-mm-dd
                    String time = tk.nextToken();
                    date_tv.setText(date1);
                    time_tv.setText(time);
                    Log.d("tag", "<-----date----->" + date1  +"time"+time);
                }




                //String categoryId = joo.getString("categoryId");




            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.d("tag", "<-----zz----->" + e.getMessage());
            }

        }


    }

}
