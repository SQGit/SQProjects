package www.sqindia.net.ilerasoftadmin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cleveroad.slidingtutorial.PageFragment;
import com.cleveroad.slidingtutorial.TransformItem;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Ramya on 26-02-2016.
 */
public class Analytics extends PageFragment implements OnChartValueSelectedListener, CalendarDatePickerDialogFragment.OnDateSetListener {
    PieChart efficiencyChart, trackedChart, mChart2, bookedChart;
    String xAxisValue, hospitalName;
    public static String URL_EFFICIENCYSCORE = "http://sqindia01.cloudapp.net/cologix/api/v1/dashboard/systemEfficiency";
    ArrayList<Entry> yVals1;
    ArrayList<String> ValsEfficiency;
    ArrayList<String> ValsBooked;
    ArrayList<String> ValsTraked;
    public ArrayList<Integer> xValsss = new ArrayList<Integer>();
    CalendarDatePickerDialogFragment cdp = null;
    public ArrayList<Integer> efficiencyList = new ArrayList<Integer>();
    public ArrayList<Integer> bookedList = new ArrayList<Integer>();
    public ArrayList<Integer> trackedList = new ArrayList<Integer>();
    Integer count;
    ArrayList<Entry> entriesEfficiency;
    ArrayList<Entry> entriesBooked;
    ArrayList<Entry> entriesTracked;
    ArrayList<String> labelsEfficiency;
    ArrayList<String> labelsBooked;
    ArrayList<String> labelsTracked;
    public boolean flag = true;
    static String dateFrom, dateTo;
    private long date;
    String currentDate;
    SweetAlertDialog pDialog;
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

    @Override
    protected int getLayoutResId() {
        return R.layout.analytics;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page1, container, false);
        efficiencyChart = (PieChart) v.findViewById(R.id.chart);
        trackedChart = (PieChart) v.findViewById(R.id.chart1);
        mChart2 = (PieChart) v.findViewById(R.id.chart2);

        bookedChart = (PieChart) v.findViewById(R.id.chart3);



        Analytics_Dash.from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment().setOnDateSetListener(Analytics.this);

                flag = true;
                cdp.setThemeDark(true);
                cdp.show(getActivity().getSupportFragmentManager(),
                        FRAG_TAG_DATE_PICKER);


            }
        });
        Analytics_Dash.to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment().setOnDateSetListener(Analytics.this);

                flag = false;
                cdp.setThemeDark(true);
                cdp.show(getActivity().getSupportFragmentManager(),
                        FRAG_TAG_DATE_PICKER);
                // chart1.clearValues();


            }

        });

        SimpleDateFormat datef = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateandTime = datef.format(new Date(date));

        //TextView set_date = (TextView) findViewById(R.id.set_date);
        Calendar ca = Calendar.getInstance();
        int m = ca.get(Calendar.MONTH);
        int y = ca.get(Calendar.YEAR);
        dateFrom = y + "-" + (m + 1) + "-" + 1;


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //Analytics_Dash.from.setText(format.format(new Date(date)));
            // Analytics_Dash.from.setText(format.format(ca.getTime()));
            dateTo = format.format(ca.getTime());
            Log.d("tag", "<---from---->" + dateFrom + "<----to-->" + dateTo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Analytics_Dash.from.setText(dateFrom);
        Analytics_Dash.to.setText(dateTo);
        yVals1 = new ArrayList<Entry>();

        ValsEfficiency = new ArrayList<String>();
        ValsBooked = new ArrayList<String>();
        ValsTraked = new ArrayList<String>();

        entriesEfficiency = new ArrayList<>();
        entriesBooked = new ArrayList<>();
        entriesTracked = new ArrayList<>();

        labelsEfficiency = new ArrayList<String>();
        labelsBooked = new ArrayList<String>();
        labelsTracked = new ArrayList<String>();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("");
        editor.putString("analytics", "analytics");
        editor.commit();



       new efficiencyScore().execute();


        return v;
    }

    private void BookedEquipment() {
        PieDataSet dataset = new PieDataSet(entriesBooked, "");
        dataset.setSliceSpace(3f);
        dataset.setValueTextSize(10);
        dataset.setValueTextColor(Color.WHITE);
        dataset.setColors(ColorSample.JOYFUL_COLORS);
        PieData data = new PieData(labelsBooked, dataset);
        data.setValueFormatter(new PercentFormatter());
        Log.d("tag", "<----ramya---->" + entriesBooked + labelsBooked);
        bookedChart.setData(data);
        bookedChart.setCenterTextSize(10);
        bookedChart.setDescription("");
        bookedChart.setDrawSliceText(false);
        bookedChart.invalidate();
        Legend l = bookedChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(3f);
        l.setYEntrySpace(3f);
        l.setYOffset(3f);
        l.setDirection(Legend.LegendDirection.RIGHT_TO_LEFT);
        l.setTextSize(7);
        l.setTextColor(Color.BLACK);
        bookedChart.getLegend().setWordWrapEnabled(true);
    }


    protected TransformItem[] provideTransformItems() {
        return new TransformItem[]{

        };
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        xAxisValue = efficiencyChart.getData().getXVals().get(e.getXIndex());
        int i = e.getXIndex();
        int j = efficiencyChart.getDataSetIndexForIndex(e.getXIndex());

        Log.d("tag", "iiiiiiiiii" + i);
        efficiencyChart.getSolidColor();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("");
        editor.putString("analytics", "analyticstab");
        editor.putString("value", xAxisValue);

        editor.putInt("i", i);
        editor.commit();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new CustomPresentationPagerFragment1());

        fragmentTransaction.commit();

    }

    @Override
    public void onNothingSelected() {

    }


    public class efficiencyScore extends AsyncTask<String, Void, String> {
        public efficiencyScore() {
            String json = "", jsonStr = "";
        }

        protected void onPreExecute()
        {
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
                // {"dateFrom":"2015-12-20","dateTo":"2015-12-30"}
                jsonObject.accumulate("dateFrom", dateFrom);
                jsonObject.accumulate("dateTo", dateTo);
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest1(URL_EFFICIENCYSCORE, json, "1a89bea7c711254e48dae75034fe1bf0");
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.d("tag", "<-----RESULT---->" + jsonStr);
            pDialog.dismiss();
            super.onPostExecute(jsonStr);
            try {
                JSONObject jo = new JSONObject(jsonStr);

                JSONArray jsonarray = jo.getJSONArray("efficiency");
                Log.d("tag", "<array>" + jsonarray.length());

                for (int i = 0; i < jsonarray.length(); i++)
                {
                    JSONObject joo = jsonarray.getJSONObject(i);

                    ValsEfficiency.add(joo.getString("hospitalName"));
                    hospitalName = joo.getString("hospitalName");
                    count = joo.getInt("efficiency");
                    int k = joo.getInt("efficiency");
                    efficiencyList.add(k);
                    entriesEfficiency.add(new Entry(efficiencyList.get(i), i));
                    labelsEfficiency.add(ValsEfficiency.get(i));
                    Log.d("tag", "xval" + efficiencyList.get(i));

                }

                JSONArray booked_ja = jo.getJSONArray("booked");
                for (int j = 0; j < booked_ja.length(); j++)
                {
                    JSONObject joob = booked_ja.getJSONObject(j);

                    ValsBooked.add(joob.getString("hospitalName"));
                    hospitalName = joob.getString("hospitalName");
                    ///count = joob.getInt("percentage");
                    int k = joob.getInt("percentage");
                    bookedList.add(k);
                    entriesBooked.add(new Entry(bookedList.get(j), j));
                    labelsBooked.add(ValsBooked.get(j));
                    Log.d("tag", "xval" + labelsBooked + "<--Labels Booked---->" + j);

                }

                JSONArray tracked_ja = jo.getJSONArray("tracked");
                for (int j = 0; j < tracked_ja.length(); j++)
                {
                    JSONObject joob = tracked_ja.getJSONObject(j);

                    ValsTraked.add(joob.getString("hospitalName"));
                    hospitalName = joob.getString("hospitalName");
                    ///count = joob.getInt("percentage");
                    int k = joob.getInt("quantity");
                    trackedList.add(k);
                    entriesTracked.add(new Entry(trackedList.get(j), j));
                    labelsTracked.add(ValsTraked.get(j));
                    Log.d("tag", "xval" + labelsBooked + "<--Labels Booked---->" + j);

                }

                Log.d("tag", "----entries booked-------" + entriesBooked);
                Legend l = efficiencyChart.getLegend();
                l.setPosition(Legend.LegendPosition.LEFT_OF_CHART_CENTER);
                l.setMaxSizePercent(3);
                l.setXEntrySpace(5f);
                l.setFormToTextSpace(5f);
                l.setFormSize(10f);
                l.setYEntrySpace(5f);
                l.setXOffset(5f);
                l.setYOffset(5f);
                l.setTextSize(10);
                l.setTextColor(Color.BLACK);

                l.setWordWrapEnabled(true);

                l.setComputedLabels(labelsEfficiency);
                l.setWordWrapEnabled(true);

                efficiencyChart.getLegend().setWordWrapEnabled(true);
                PieDataSet dataset = new PieDataSet(entriesEfficiency, "");
                dataset.setSliceSpace(3f);
                dataset.setValueTextSize(20);
                dataset.setValueTextColor(Color.WHITE);
                dataset.setColors(ColorSample.JOYFUL_COLORS);
                PieData data = new PieData(labelsEfficiency, dataset);
                data.setValueFormatter(new PercentFormatter());
                Log.d("tag", "start22222");

                efficiencyChart.setData(data);
                efficiencyChart.setCenterText("40%");
                efficiencyChart.setCenterTextSize(40);
                efficiencyChart.setDescription("");
                efficiencyChart.setDrawSliceText(false);
                efficiencyChart.setOnChartValueSelectedListener(Analytics.this);
                efficiencyChart.invalidate();

                BookedEquipment();
                TotalEquipment();
                // generateData3();

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.d("tag", "<-error->" + e.getMessage());

            }


        }
    }

    private void TotalEquipment() {

        PieDataSet dataset = new PieDataSet(entriesTracked, "");
        dataset.setSliceSpace(3f);
        dataset.setValueTextSize(10);
        dataset.setValueTextColor(Color.WHITE);
        dataset.setColors(ColorSample.CYAN_COLORS);
        PieData data = new PieData(labelsTracked, dataset);
        data.setValueFormatter(new PercentFormatter());

        trackedChart.setData(data);
        trackedChart.setCenterText("50%");
        trackedChart.setCenterTextSize(12);
        trackedChart.setDescription("");
        trackedChart.setDrawSliceText(false);
        // mChart1.setOnChartValueSelectedListener(Analytics.this);
        trackedChart.invalidate();

        Legend l = trackedChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
        l.setXEntrySpace(3f);
        l.setYEntrySpace(3f);
        l.setXOffset(3f);
        l.setYOffset(3f);
        l.setTextSize(13);
        l.setTextColor(Color.BLACK);
        // l.setComputedLabels(labelsBooked);
        l.setWordWrapEnabled(true);
        trackedChart.getLegend().setWordWrapEnabled(true);
    }

    private void generateData3() {

        // int i = 5, j = 9;
        ArrayList<Entry> entries1 = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();

        entries1.add(new Entry(40, 0));
        entries1.add(new Entry(50, 1));
        entries1.add(new Entry(30, 2));
        entries1.add(new Entry(41, 3));


        labels.add("Hospital 1");
        labels.add("Hospital 2");
        labels.add("Hospital 3");
        labels.add("Hospital 4");

        PieDataSet dataset = new PieDataSet(entries1, "");
        dataset.setSliceSpace(3f);
        dataset.setValueTextSize(10);
        dataset.setValueTextColor(Color.WHITE);
        dataset.setColors(ColorSample.TEAL_COLORS);
        PieData data = new PieData(labels, dataset);
        mChart2.setData(data);
        mChart2.setCenterText("10");
        mChart2.setCenterTextSize(13);
        mChart2.setDescription("");
        mChart2.setDrawSliceText(false);

        data.setValueFormatter(new PercentFormatter());

        Legend l = mChart2.getLegend();
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART_CENTER);
        l.setXEntrySpace(3f);
        l.setYEntrySpace(3f);
        l.setYOffset(3f);
        l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        l.setTextSize(10);
        l.setTextColor(Color.BLACK);

        l.setComputedLabels(labels);
        mChart2.getLegend().setWordWrapEnabled(true);

    }

    public void onDateSet(CalendarDatePickerDialogFragment calendarDatePickerDialogFragment, int year, int month, int dayOfMonth) {
        if (flag)
        {
            dateFrom = year + "-" + (month + 1) + "-" + dayOfMonth;
            Log.d("tag", "<---datefrom---->" + dateFrom);


            Analytics_Dash.from.setText(year + "-" + (month + 1) + "-" + dayOfMonth);


        } else {
            dateTo = year + "-" + (month + 1) + "-" + dayOfMonth;

            Analytics_Dash.to.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
          /*  labelsEfficiency.clear();
            efficiencyList.clear();
            entriesEfficiency.clear();
            ValsEfficiency.clear();
            efficiencyChart.removeAllViews();*/
          //  efficiencyChart.invalidate();
            new efficiencyScore().execute();

            Log.d("tag", "effffffffffff");

        }
    }


}