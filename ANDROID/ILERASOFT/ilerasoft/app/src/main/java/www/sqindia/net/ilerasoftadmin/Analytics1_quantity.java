package www.sqindia.net.ilerasoftadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.cleveroad.slidingtutorial.PageFragment;
import com.cleveroad.slidingtutorial.TransformItem;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Ramya on 26-02-2016.
 */


public class Analytics1_quantity extends PageFragment {
    PieChart mChart, mChart1, mChart2, mChart3;
    int code;
    String value;
    TextView tv;
    static EditText from, to;
    static String dateFrom, dateTo,categoryName,hospitalName;
    CalendarDatePickerDialogFragment cdp = null;
    public boolean flag = true;
    public static String URL_DEPARTMENT_DATA = "http://sqindia01.cloudapp.net/cologix/api/v1/dashboard/hospitalData";
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
    private long date;
    SweetAlertDialog pDialog;
    ArrayList<String> ValsEfficiency;
    public ArrayList<Integer> efficiencyList = new ArrayList<Integer>();
    ArrayList<Entry> entriesEfficiency;
    ArrayList<String> labelsEfficiency;
    ArrayList<String> ValsEfficiencyImport;
    public ArrayList<Integer> efficiencyListImport = new ArrayList<Integer>();
    ArrayList<Entry> entriesEfficiencyImport;
    ArrayList<String> labelsEfficiencyImport;


    @Override
    protected int getLayoutResId()
    {

        return R.layout.analytics;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.analytics_qty, container, false);
        labelsEfficiency = new ArrayList<String>();
        entriesEfficiency = new ArrayList<>();
        ValsEfficiency = new ArrayList<String>();

        tv = (TextView) v.findViewById(R.id.tvv);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        SimpleDateFormat datef = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateandTime = datef.format(new Date(date));
        Calendar ca = Calendar.getInstance();
        int m = ca.get(Calendar.MONTH);
        int y = ca.get(Calendar.YEAR);
        dateFrom = y + "-" + (m + 1) + "-" + 1;


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {

            dateTo = format.format(ca.getTime());
            Log.d("tag", "<---fromQUANTITYYYYYYY---->" + dateFrom + "<----QQQQQQQTOOOOOOO-->" + dateTo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Analytics_Dash.from.setText(dateFrom);
        Analytics_Dash.to.setText(dateTo);

        Log.d("tag", "dateeee" + Analytics_Dash.dateFrom);
        code = sharedPreferences.getInt("i", 0);
        value = sharedPreferences.getString("value", "");
        tv.setText(value + " " + "MASTER DASHBOARD");
        Log.d("tag", "Code" + code);
        mChart1 = (PieChart) v.findViewById(R.id.chart);
        mChart = (PieChart) v.findViewById(R.id.chart1);
        mChart2 = (PieChart) v.findViewById(R.id.chart2);

        mChart3 = (PieChart) v.findViewById(R.id.chart3);
        new Quantity().execute();

        //generateData1();
        //generateData2();
        //generateData3();

    /*    ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(34, 0));
        entries.add(new Entry(25, 1));
        entries.add(new Entry(35, 2));
        entries.add(new Entry(72, 3));
        entries.add(new Entry(18, 4));
        //entries.add(new Entry(30, 4));


        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Diagnostic ");
        labels.add("Therapeutic  ");
        labels.add("Surgical ");
        labels.add("Durable  ");
        labels.add("BioMedical  ");
        PieDataSet dataset = new PieDataSet(entries, "");
        dataset.setSliceSpace(3f);
        dataset.setValueTextSize(20);
        dataset.setValueTextColor(Color.WHITE);*/
      /*  switch (code) {
            case 0: {
                dataset.setColors(ColorSample.RED_COLORS);
                break;
            }
            case 1: {
                dataset.setColors(ColorSample.TEAL_COLORS);
                break;
            }
            case 2: {
                dataset.setColors(ColorSample.ORANGE_COLORS);
                break;
            }
            case 3: {
                dataset.setColors(ColorSample.BLUE_COLORS);
                break;
            }

            default: {
                dataset.setColors(ColorSample.JOYFUL_COLORS);
                break;
            }
        }

        PieData data = new PieData(labels, dataset);
        // data.setValueFormatter(new YourValueFormatter());

       // data.setValueFormatter(new PercentFormatter());

        mChart.setData(data);

        mChart.setCenterText("30");
        mChart.setCenterTextSize(40);
        mChart.setDescription("");
        mChart.setDrawSliceText(false);
        mChart.setUsePercentValues(false);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART_CENTER);
        l.setXEntrySpace(5);
        l.setYEntrySpace(8);
        l.setTextSize(17);
        l.setTextColor(Color.BLACK);
        l.setComputedLabels(labels);
*/
        return v;
    }


    private void generateData1() {
        ArrayList<Entry> entries1 = new ArrayList<>();
        entries1.add(new Entry(3, 0));
        entries1.add(new Entry(1, 1));
        entries1.add(new Entry(1, 2));


        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Orthopedic");
        labels.add("Radiology");
        labels.add("Maternity");


        PieDataSet dataset = new PieDataSet(entries1, "");
        dataset.setSliceSpace(3f);
        dataset.setValueTextSize(15);
        dataset.setValueTextColor(Color.WHITE);

        switch (code) {
            case 0: {
                dataset.setColors(ColorSample.PINK_COLORS);
                break;
            }
            case 1: {
                dataset.setColors(ColorSample.TEAL_COLORS);
                break;
            }
            case 2:
            {
                dataset.setColors(ColorSample.ORANGE_COLORS);
                break;
            }
            case 3:
            {
                dataset.setColors(ColorSample.BLUE_COLORS);
                break;
            }

            default: {
                dataset.setColors(ColorSample.JOYFUL_COLORS);
                break;
            }
        }
        PieData data = new PieData(labels, dataset);
        mChart1.setData(data);
        mChart1.setCenterText("5");
        mChart1.setCenterTextSize(40);
        mChart1.setDescription("");
        mChart1.setDrawSliceText(false);

        data.setValueFormatter(new PercentFormatter());

        Legend l = mChart1.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setTextSize(18);

        l.setTextColor(Color.BLACK);

        l.setComputedLabels(labels);
        l.setWordWrapEnabled(true);
        mChart1.getLegend().setWordWrapEnabled(true);
        mChart1.getLegend().setEnabled(false);

        Analytics_Dash.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Analytics_Dash.class);
                startActivity(i);


            }
        });
    }

    private void generateData3() {
        ArrayList<Entry> entries1 = new ArrayList<>();
        entries1.add(new Entry(2, 0));
        entries1.add(new Entry(2, 1));
        entries1.add(new Entry(6, 2));


        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Diagnostic ");
        labels.add("Therapeutic  ");
        labels.add("Surgical ");

        PieDataSet dataset = new PieDataSet(entries1, "");
        dataset.setSliceSpace(3f);
        dataset.setValueTextSize(15);
        dataset.setValueTextColor(Color.WHITE);

        switch (code) {
            case 0: {
                dataset.setColors(ColorSample.PINK_COLORS);
                break;
            }
            case 1: {
                dataset.setColors(ColorSample.TEAL_COLORS);
                break;
            }
            case 2: {
                dataset.setColors(ColorSample.ORANGE_COLORS);
                break;
            }
            case 3: {
                dataset.setColors(ColorSample.BLUE_COLORS);
                break;
            }

            default: {
                dataset.setColors(ColorSample.JOYFUL_COLORS);
                break;
            }
        }
        PieData data = new PieData(labels, dataset);
        mChart2.setData(data);
        mChart2.setCenterText("10");
        mChart2.setCenterTextSize(40);
        mChart2.setDescription("");
        mChart2.setDrawSliceText(false);

        data.setValueFormatter(new PercentFormatter());

        Legend l = mChart2.getLegend();
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART_CENTER);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(3f);
        l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        l.setTextSize(18);
        l.setTextColor(Color.BLACK);

        l.setComputedLabels(labels);
        mChart2.getLegend().setWordWrapEnabled(true);

    }

    private void generateData2() {
        ArrayList<Entry> entries1 = new ArrayList<>();
        entries1.add(new Entry(5, 0));
        entries1.add(new Entry(1, 1));
        entries1.add(new Entry(1, 2));


        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Diagnostic ");
        labels.add("Therapeutic  ");
        labels.add("Surgical ");


        PieDataSet dataset = new PieDataSet(entries1, "");
        switch (code) {
            case 0: {
                dataset.setColors(ColorSample.PINK_COLORS);
                break;
            }
            case 1: {
                dataset.setColors(ColorSample.TEAL_COLORS);
                break;
            }
            case 2: {
                dataset.setColors(ColorSample.ORANGE_COLORS);
                break;
            }
            case 3: {
                dataset.setColors(ColorSample.BLUE_COLORS);
                break;
            }

            default: {
                dataset.setColors(ColorSample.JOYFUL_COLORS);
                break;
            }
        }
        PieData data = new PieData(labels, dataset);
        dataset.setSliceSpace(3f);
        dataset.setValueTextSize(15);
        data.setValueFormatter(new PercentFormatter());
        dataset.setValueTextColor(Color.WHITE);

        mChart3.setData(data);
        mChart3.setCenterText("7");
        mChart3.setCenterTextSize(40);
        mChart3.setDescription("");
        mChart3.setDrawSliceText(false);


        Legend l = mChart3.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(3f);
        l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        l.setTextSize(18);
        l.setTextColor(Color.BLACK);

        l.setComputedLabels(labels);
        mChart3.getLegend().setWordWrapEnabled(true);


    }


    protected TransformItem[] provideTransformItems() {
        return new TransformItem[]
                {
                };
    }

    public void onDateSet(CalendarDatePickerDialogFragment calendarDatePickerDialogFragment, int year, int month, int dayOfMonth) {

        Log.d("tag","<-------flag----------->"+flag);

        if (flag)
        {
            dateFrom = year + "-" + (month + 1) + "-" + dayOfMonth;
            Log.d("tag", "<---datefrom---->" + dateFrom);


            Analytics_Dash.from.setText(year + "-" + (month + 1) + "-" + dayOfMonth);


        }
        else
        {
            dateTo = year + "-" + (month + 1) + "-" + dayOfMonth;
            Log.d("tag", "<---date---->" + dateTo);

            Analytics_Dash.to.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
            //new efficiencyScore().execute();
            //efficiencyChart.invalidate();


        }

    }

    public class Quantity extends AsyncTask<String, Void, String> {
        public Quantity() {
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
                jsonObject.accumulate("hospitalId", "1");
                jsonObject.accumulate("dateFrom", "2014-02-1");
                jsonObject.accumulate("dateTo", "2016-12-30");
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest1(URL_DEPARTMENT_DATA, json, "1a89bea7c711254e48dae75034fe1bf0");
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.d("tag", "<-----QTYYYYYYY---->" + jsonStr);
            super.onPostExecute(jsonStr);
            pDialog.dismiss();
          try {
                JSONObject jo = new JSONObject(jsonStr);
                JSONArray efficiency_ary = jo.getJSONArray("quantity");


              for (int i = 0; i < efficiency_ary.length(); i++)
              {
                  JSONObject joo = efficiency_ary.getJSONObject(i);

                  ValsEfficiency.add(joo.getString("categoryName"));
                  categoryName = joo.getString("categoryName");
                  int count = joo.getInt("quantity");
                  int k = joo.getInt("quantity");
                  efficiencyList.add(k);
                  entriesEfficiency.add(new Entry(efficiencyList.get(i), i));
                  labelsEfficiency.add(ValsEfficiency.get(i));
                  Log.d("tag", "xval" + efficiencyList.get(i));

              }


              Legend l = mChart1.getLegend();
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

              mChart1.getLegend().setWordWrapEnabled(true);
              PieDataSet dataset = new PieDataSet(entriesEfficiency, "");
              dataset.setSliceSpace(3f);
              dataset.setValueTextSize(20);
              dataset.setValueTextColor(Color.WHITE);
              dataset.setColors(ColorSample.JOYFUL_COLORS);
              PieData data = new PieData(labelsEfficiency, dataset);
              //data.setValueFormatter(new PercentFormatter());
              Log.d("tag", "start22222");

              mChart1.setData(data);
              mChart1.setCenterText("40%");
              mChart1.setCenterTextSize(40);
              mChart1.setDescription("");
              mChart1.setDrawSliceText(false);
              mChart1.setUsePercentValues(false);


              //mChart1.setOnChartValueSelectedListener(Analytics.this);
              mChart1.invalidate();


              JSONArray booked_ja = jo.getJSONArray("import");
              for (int j = 0; j < booked_ja.length(); j++)
              {
                  JSONObject joob = booked_ja.getJSONObject(j);

                  ValsEfficiencyImport.add(joob.getString("hospitalName"));
                  hospitalName = joob.getString("hospitalName");
                  //count = joob.getInt("percentage");
                  int k = joob.getInt("percentage");
                  efficiencyListImport.add(k);
                  entriesEfficiencyImport.add(new Entry(efficiencyListImport.get(j), j));
                  labelsEfficiencyImport.add(ValsEfficiencyImport.get(j));
                  Log.d("tag", "xval" + labelsEfficiencyImport + "<--Labels Booked---->" + j);

              }


          } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.d("tag", "<-error->" + e.getMessage());

            }


        }
    }
}