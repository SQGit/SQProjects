package www.sqindia.net.ilerasoftadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.LegendRenderer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by Ramya on 29-02-2016.
 */
public class Analytics2_bar extends PageFragment implements CircleDisplay.SelectionListener, OnChartValueSelectedListener, CalendarDatePickerDialogFragment.OnDateSetListener {
    ArrayList<String> MostEfficiency;
    ArrayList<String> LeastEfficiency;
    public ArrayList<Integer> efficiencyList = new ArrayList<Integer>();
    ArrayList<Entry> entriesEfficiency;
    ArrayList<String> labelsEfficiency;
    ArrayList<String> least_labelsEfficiency;
    SweetAlertDialog pDialog;
    ArrayList<String> xAxis, newqq;
    BarChart chart1, chart2;
    List<Float> flots = new ArrayList<Float>();
    int i,hospitalId;
    String departmentName,token;
    ScatterData data;
    private PieChartView chart;
    private int maxNumberOfLines = 4;
    private int numberOfPoints = 12;
    ScatterChart scatterChart;
    float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
    String Value;
    ScatterDataSet dataset;
    public static String URL_DEPARTMENT_DATA = "http://sqindia01.cloudapp.net/cologix/api/v1/dashboard/departmentData";
    TextWatcher textWatcher;
    EditText from, to;
    public boolean flag = true;
    static String dateFrom, dateTo;
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

    @Override
    protected int getLayoutResId() {
        return R.layout.analytics;

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page_bar, container, false);
        entriesEfficiency = new ArrayList<Entry>();
        labelsEfficiency = new ArrayList<String>();
        least_labelsEfficiency = new ArrayList<>();
        newqq = new ArrayList<String>();
        MostEfficiency = new ArrayList<String>();
        LeastEfficiency = new ArrayList<String>();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        token= sharedPreferences.getString("TOKEN", "");

        Analytics_Dash.from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment().setOnDateSetListener(Analytics2_bar.this);

                flag = true;
                cdp.setThemeDark(true);
                cdp.show(getActivity().getSupportFragmentManager(),
                        FRAG_TAG_DATE_PICKER);


            }
        });
        Analytics_Dash.to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment().setOnDateSetListener(Analytics2_bar.this);

                flag = false;
                cdp.setThemeDark(true);
                cdp.show(getActivity().getSupportFragmentManager(),
                        FRAG_TAG_DATE_PICKER);
                String s = this.getClass().getName();
                Log.d("tag", "dsff" + s);


            }

        });

        Analytics_Dash.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Analytics_Dash.class);
                startActivity(i);


            }
        });
        chart1 = (BarChart) v.findViewById(R.id.chart1);
        chart2 = (BarChart) v.findViewById(R.id.chart2);
        scatterChart = (ScatterChart) v.findViewById(R.id.chart12);
        generateData();
        // generateData1();
        //circleView();
        generateLine();
        BarData data = new BarData(getXAxisValues(), getDataSet());
        chart1.setData(data);

        chart1.setDescription("");
        chart1.animateXY(1000, 1000);
        chart1.invalidate();
        chart1.getLegend().setWordWrapEnabled(true);
        chart1.getLegend().setEnabled(false);
        chart1.setOnChartValueSelectedListener(this);
        return v;

    }

    private void generateLine() {
        // creating list of entry

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(12f, 3));
        entries.add(new Entry(18f, 4));
        entries.add(new Entry(9f, 5));
        entries.add(new Entry(15f, 5));
        dataset = new ScatterDataSet(entries, "# of Calls");
        dataset.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        dataset.setColors(ColorSample.JOYFUL_COLORS);
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Sunday");
        labels.add("Monday");
        labels.add("Tuesday");
        labels.add("Wednesday");
        labels.add("Thursday");
        labels.add("Friday");
        labels.add("Saturday");
        data = new ScatterData(labels, dataset);
        scatterChart.setData(data);
        scatterChart.setDescription("");
        Legend l = scatterChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(4);
        l.setYEntrySpace(4);
        l.setTextSize(12);
        l.setTextColor(Color.BLACK);

        l.setWordWrapEnabled(true);

        scatterChart.getLegend().setEnabled(false);


    }


    private void generateData() {
        BarData data = new BarData(getXAxisValues1(), getDataSet1());
        chart2.setData(data);

        chart2.setDescription("");
        chart2.animateXY(1000, 1000);
        chart2.invalidate();
        chart2.getLegend().setWordWrapEnabled(true);
        chart2.getLegend().setEnabled(false);


    }


    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(56, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(48, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(46, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(45, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(43, 4); // May
        valueSet1.add(v1e5);

     /*   BarEntry v1e6 = new BarEntry(43, 5); // Jun
        valueSet1.add(v1e6);
        BarEntry v1e7 = new BarEntry(43, 6); // Jun
        valueSet1.add(v1e7);*/


        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "");
        barDataSet1.setColors(ColorSample.JOYFUL_COLORS);
        barDataSet1.setBarSpacePercent(50f);
        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        XAxis xAxis = chart1.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        return dataSets;
    }

    private ArrayList<BarDataSet> getDataSet1() {
        ArrayList<BarDataSet> dataSets1 = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(50, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(44, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(40, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(38, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(35, 4); // May
        valueSet1.add(v1e5);
     /*   BarEntry v1e6 = new BarEntry(30, 5); // Jun
        valueSet1.add(v1e6);
        BarEntry v1e7 = new BarEntry(28, 6); // Jun
        valueSet1.add(v1e7);*/



        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "");
        barDataSet1.setColors(ColorTemplate.JOYFUL_COLORS);
        barDataSet1.setBarSpacePercent(50f);
        dataSets1 = new ArrayList<>();
        dataSets1.add(barDataSet1);

        XAxis xAxis = chart2.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);
        return dataSets1;
    }

    private ArrayList<String> getXAxisValues() {
        xAxis = new ArrayList<>();

        xAxis.add("Psychiatry");
        xAxis.add("Cardiology");
        xAxis.add("Ear&Nose");
        xAxis.add("Outpatient");
        xAxis.add("Aneasthetics");
       /* xAxis.add("Haematology");
        xAxis.add("Orthopedic");*/



        return xAxis;
    }


    private ArrayList<String> getXAxisValues1() {
        xAxis = new ArrayList<>();

        xAxis.add("Psychiatry");
        xAxis.add("Cardiology");
        xAxis.add("Ear&Nose");
        xAxis.add("Outpatient");
        xAxis.add("Aneasthetics");
       /* xAxis.add("Haematology");
        xAxis.add("Orthopedic");*/
/*        xAxis.add("Inpatient");
        xAxis.add("Urology");
        xAxis.add("Gyneacology");*/


        return xAxis;
    }
    protected TransformItem[] provideTransformItems() {
        return new TransformItem[]
                {
                };
    }


    @Override
    public void onSelectionUpdate(float val, float maxval) {
        Log.i("Main", "Selection update: " + val + ", max: " + maxval);
    }

    @Override
    public void onValueSelected(float val, float maxval) {
        Log.i("Main", "Selection complete: " + val + ", max: " + maxval);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Value = chart1.getData().getXVals().get(e.getXIndex());
        i = e.getXIndex();
        Log.d("tag", "iiiiiiiiii" + i);
        RectF bounds = chart1.getBarBounds((BarEntry) e);
        PointF position = chart1.getPosition(e, YAxis.AxisDependency.LEFT);

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(12f, 3));
        entries.add(new Entry(18f, 4));
       /* entries.add(new Entry(9f, 5));
        entries.add(new Entry(15f, 5));
*/

        switch (i) {
            case 0: {
                Log.d("tag", "0000000000" + i);
                data.clearValues();
                dataset = new ScatterDataSet(entries, "# of Calls");
                dataset.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
                dataset.setColors(ColorSample.RED_COLORS);
                break;
            }
            case 1: {
                Log.d("tag", "1111111111" + i);
                dataset.resetColors();
                data.clearValues();

                dataset = new ScatterDataSet(entries, "# of Calls");
                dataset.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
                Log.d("tag", "22" + i);

                dataset.resetColors();
                Log.d("tag", "33" + i);

                dataset.setColors(ColorSample.TEAL_COLORS);
                Log.d("tag", "44" + i);

                break;
            }
            case 2: {
                Log.d("tag", "2222222222" + i);
                dataset = new ScatterDataSet(entries, "# of Calls");
                dataset.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
                dataset.resetColors();

                dataset.setColors(ColorSample.ORANGE_COLORS);
                break;
            }
            case 3: {
                Log.d("tag", "333333333" + i);
                dataset = new ScatterDataSet(entries, "# of Calls");
                dataset.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
                dataset.setColors(ColorSample.BLUE_COLORS);
                break;
            }
            case 4: {
                Log.d("tag", "444444444" + i);

                // dataset.setColors(ColorSample.RED_COLORS);
                break;
            }
            case 5: {
                // dataset.setColors(ColorSample.TEAL_COLORS);
                break;
            }
            case 6: {
                //dataset.setColors(ColorSample.ORANGE_COLORS);
                break;
            }
            default: {
                dataset = new ScatterDataSet(entries, "# of Calls");
                dataset.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
                dataset.setColors(ColorSample.JOYFUL_COLORS);
                break;
            }
        }

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Sunday");
        labels.add("Monday");
        labels.add("Tuesday");
        labels.add("Wednesday");
        labels.add("Thursday");
        labels.add("Friday");
        labels.add("Saturday");
        ScatterData data = new ScatterData(labels, dataset);
        scatterChart.setData(data);
        scatterChart.setDescription("");
        Legend l = scatterChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(5);
        l.setYEntrySpace(5);
        l.setTextSize(20);
        l.setTextColor(Color.BLACK);

        l.setWordWrapEnabled(true);

        scatterChart.getLegend().setEnabled(false);
        scatterChart.invalidate();

    }


    @Override
    public void onNothingSelected() {
        dataset.resetColors();

        dataset.setColors(ColorSample.JOYFUL_COLORS);

        scatterChart.invalidate();

    }


    public class depatrmentData extends AsyncTask<String, Void, String> {
        public depatrmentData() {
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

            chart1.clear();
            chart1.invalidate();
            chart2.clear();
            chart2.invalidate();

        }

        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("hospitalId", hospitalId);
                jsonObject.accumulate("dateFrom", "2014-02-1");
                jsonObject.accumulate("dateTo", "2016-04-27");
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest1(URL_DEPARTMENT_DATA, json, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.d("tag", "<-----RESULTBBBBB---->" + jsonStr);
            super.onPostExecute(jsonStr);
            pDialog.dismiss();
            try {
                JSONObject jo = new JSONObject(jsonStr);
                JSONObject most = jo.getJSONObject("efficiency");

                JSONArray jsonarray = most.getJSONArray("most");
                Log.d("tag", "<array>" + jsonarray.length());

                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject ja = jsonarray.getJSONObject(i);

                    MostEfficiency.add(ja.getString("drptName"));
                    departmentName = ja.getString("drptName");

                    labelsEfficiency.add(MostEfficiency.get(i));

                }

                BarData data = new BarData(getXAxisValues(), getDataSet1());
                chart1.setData(data);
                chart1.setDescription("");
                chart1.animateXY(1000, 1000);
                chart1.invalidate();
                chart1.getLegend().setWordWrapEnabled(true);
                chart1.getLegend().setEnabled(false);
                chart1.setOnChartValueSelectedListener(Analytics2_bar.this);

                JSONArray least_ary = most.getJSONArray("least");
                for (int i = 0; i < least_ary.length(); i++) {
                    JSONObject ja = least_ary.getJSONObject(i);
                    LeastEfficiency.add(ja.getString("drptName"));
                    departmentName = ja.getString("drptName");
                    least_labelsEfficiency.add(LeastEfficiency.get(i));

                }

                BarData data1 = new BarData(getXAxisValues1(), getDataSet1());
                chart2.setData(data1);
                chart2.setDescription("");
                chart2.animateXY(1000, 1000);
                chart2.invalidate();
                chart2.getLegend().setWordWrapEnabled(true);
                chart2.getLegend().setEnabled(false);

                chart2.getLegend().setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
                chart2.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.d("tag", "<-error->" + e.getMessage());

            }


        }
    }

    public void onDateSet(CalendarDatePickerDialogFragment calendarDatePickerDialogFragment, int year, int month, int dayOfMonth) {
        if (flag) {
            dateFrom = year + "-" + (month + 1) + "-" + dayOfMonth;
            Log.d("tag", "<---datefrom---->" + dateFrom);


            Analytics_Dash.from.setText(year + "-" + (month + 1) + "-" + dayOfMonth);


        } else {
            dateTo = year + "-" + (month + 1) + "-" + dayOfMonth;

            Analytics_Dash.to.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
            // new efficiencyScore().execute();


            //chart2.clearValues();
            labelsEfficiency.clear();
            least_labelsEfficiency.clear();

            //new depatrmentData().execute();

        }
    }
}