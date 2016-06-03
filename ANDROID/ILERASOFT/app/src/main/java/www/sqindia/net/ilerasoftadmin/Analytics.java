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
    String xAxisValue, hospitalName,average,CenterValue;
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
    public ArrayList<Integer> hospitalIdList = new ArrayList<Integer>();
    Integer count;
    ArrayList<Entry> entriesEfficiency;
    ArrayList<Entry> entriesBooked;
    ArrayList<Entry> entriesTracked;
    ArrayList<String> labelsEfficiency;
    ArrayList<String> labelsBooked;
    ArrayList<String> labelsTracked;
    ArrayList<String> hospital;
    public boolean flag = true;
    static String dateFrom, dateTo;
    private long date;
    static String currentDate, token;
    int hospitalId, hosId;
    SweetAlertDialog pDialog;
    Float averageValue;
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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        token = sharedPreferences.getString("TOKEN", "");
        //hospitalId = sharedPreferences.getString("hospitalId", "");
        // Log.d("tag","<------hospitalId------------------>"+hospitalId+token);
        generateData1();
        generateData2();
        generateData3();

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

        Calendar ca = Calendar.getInstance();
        int m = ca.get(Calendar.MONTH);
        int y = ca.get(Calendar.YEAR);
        dateFrom = y + "-" + (m + 1) + "-" + 1;


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {

            dateTo = format.format(ca.getTime());
            Log.d("tag", "<---from---->" + dateFrom + "<----to-->" + dateTo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Analytics_Dash.from.setText(dateFrom);
        Analytics_Dash.to.setText(dateTo);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(30, 0));
        entries.add(new Entry(27, 1));
        entries.add(new Entry(30, 2));
        entries.add(new Entry(40, 3));


        ArrayList<String> labels = new ArrayList<String>();

        labels.add("Hospital 1");
        labels.add("Hospital 2");
        labels.add("Hospital 3");
        labels.add("Hospital 4");

        PieDataSet dataset = new PieDataSet(entries, "");
        dataset.setSliceSpace(3f);
        dataset.setValueTextSize(16);
        dataset.setValueTextColor(Color.WHITE);
        dataset.setColors(ColorSample.JOYFUL_COLORS);
        PieData data = new PieData(labels, dataset);
        //PieData dat=new PieData(labels1,dataset);`    `
        data.setValueFormatter(new PercentFormatter());

        efficiencyChart.setData(data);
        efficiencyChart.setCenterText("31.75%");
        efficiencyChart.setCenterTextSize(25);
        efficiencyChart.setDescription("");
        efficiencyChart.setDrawSliceText(false);
        efficiencyChart.setOnChartValueSelectedListener(this);


        Legend l = efficiencyChart.getLegend();
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART_CENTER);
        l.setXEntrySpace(5f);
        l.setYEntrySpace(5f);
        l.setXOffset(5f);
        l.setYOffset(5f);
        l.setTextSize(17);
        l.setTextColor(Color.BLACK);

        l.setComputedLabels(labels);
        l.setWordWrapEnabled(true);
        efficiencyChart.getLegend().setWordWrapEnabled(true);


        return v;
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

    private void generateData1() {
        ArrayList<Entry> entries1 = new ArrayList<>();
        entries1.add(new Entry(55, 0));
        entries1.add(new Entry(35, 1));
        entries1.add(new Entry(45, 2));
        entries1.add(new Entry(27, 3));


        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Hospital 1");
        labels.add("Hospital 2");
        labels.add("Hospital 3");
        labels.add("Hospital 4");

        PieDataSet dataset = new PieDataSet(entries1, "");
        dataset.setSliceSpace(3f);
        dataset.setValueTextSize(10);
        dataset.setValueTextColor(Color.WHITE);
        dataset.setColors(ColorSample.CYAN_COLORS);
        PieData data = new PieData(labels, dataset);
        trackedChart.setData(data);
        trackedChart.setCenterText("40.5%");
        trackedChart.setCenterTextSize(15);
        trackedChart.setDescription("");
        trackedChart.setDrawSliceText(false);

        data.setValueFormatter(new PercentFormatter());

        Legend l = trackedChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
        l.getFormToTextSpace();
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);

        l.setXOffset(5f);
        l.setYOffset(5f);
        l.setTextSize(10);
        l.setTextColor(Color.BLACK);

        l.setComputedLabels(labels);
        l.setWordWrapEnabled(true);
        trackedChart.getLegend().setWordWrapEnabled(true);


    }

    private void generateData2() {
        ArrayList<Entry> entries1 = new ArrayList<>();
        entries1.add(new Entry(11, 0));
        entries1.add(new Entry(10, 1));
        entries1.add(new Entry(15, 2));
        entries1.add(new Entry(19, 3));


        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Hospital 1");
        labels.add("Hospital 2");
        labels.add("Hospital 3");
        labels.add("Hospital 4");


        PieDataSet dataset = new PieDataSet(entries1, "");
        dataset.setColors(ColorSample.JOYFUL_COLORS);
        PieData data = new PieData(labels, dataset);
        dataset.setSliceSpace(3f);
        dataset.setValueTextSize(10);
        dataset.setValueTextColor(Color.WHITE);
        data.setValueFormatter(new PercentFormatter());

        bookedChart.setData(data);
        bookedChart.setCenterText("13.75");
        bookedChart.setCenterTextSize(15);
        bookedChart.setDescription("");
        bookedChart.setDrawSliceText(false);


        Legend l = bookedChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
        l.setXEntrySpace(3f);
        l.setYEntrySpace(3f);
        l.setYOffset(3f);
        l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        l.setTextSize(10);
        l.setTextColor(Color.BLACK);

        l.setComputedLabels(labels);
        bookedChart.getLegend().setWordWrapEnabled(true);

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

        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(labels, dataset);
        mChart2.setData(data);
        mChart2.setCenterText("40.25%");
        mChart2.setCenterTextSize(15);
        mChart2.setDescription("");
        mChart2.setDrawSliceText(false);

        data.setValueFormatter(new PercentFormatter());

        Legend l = mChart2.getLegend();
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART_CENTER);
        l.setMaxSizePercent(3);
        l.setFormToTextSpace(5f);
        l.setFormSize(5f);
        l.setXOffset(5f);
        l.setYOffset(5f);
        l.setTextSize(10);
        l.setTextColor(Color.BLACK);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

        mChart2.getLegend().setWordWrapEnabled(true);

    }

    public void onDateSet(CalendarDatePickerDialogFragment calendarDatePickerDialogFragment, int year, int month, int dayOfMonth) {
        if (flag) {
            dateFrom = year + "-" + (month + 1) + "-" + dayOfMonth;
            Log.d("tag", "<---datefrom---->" + dateFrom);


            Analytics_Dash.from.setText(year + "-" + (month + 1) + "-" + dayOfMonth);


        } else {
            dateTo = year + "-" + (month + 1) + "-" + dayOfMonth;

            Analytics_Dash.to.setText(year + "-" + (month + 1) + "-" + dayOfMonth);


            Log.d("tag", "effffffffffff");

        }
    }


}