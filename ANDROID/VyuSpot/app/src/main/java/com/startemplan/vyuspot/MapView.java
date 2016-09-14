package com.startemplan.vyuspot;

import android.app.ActionBar;
import android.app.Activity;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.rey.material.widget.Spinner;
import com.rey.material.widget.TabPageIndicator;

import java.util.ArrayList;


public class MapView extends TabActivity {
    Spinner spin;
    String[] sting;
    MaterialRippleLayout mtp;

    TabPageIndicator tabs;
    CharSequence Titles[] = {"List", "Events"};
    int Numboftabs = 2;
    int getspin =0;
    public String spintxt;

    public  static String emgurl = "http://sqindia01.cloudapp.net/vyuspot/api/v1/spot/listEmergency";
    public  static String acciurl = "http://sqindia01.cloudapp.net/vyuspot/api/v1/spot/listAccidents";
    public  static String recurl = "http://sqindia01.cloudapp.net/vyuspot/api/v1/spot/listRecoveryNeeded";


    FragmentManager ff;
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapview_list);

        mtp = (MaterialRippleLayout) findViewById(R.id.ripple);

        spin = (Spinner) findViewById(R.id.spinner);
        sting = new String[]{"Emergency", "Accident", "Recovery",};
        Spinner_Adapter adapter = new Spinner_Adapter(getApplicationContext(), R.layout.row_spn_dropdown, sting);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        spin.setAdapter(adapter);


        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        TabHost.TabSpec tab1 = tabHost.newTabSpec("ListView");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("MapView");


        tab1.setIndicator("Map");
        Intent igom = new Intent(MapView.this, MapView_Map.class);
        igom.putExtra("service", emgurl);
        tab1.setContent(igom);

        tab2.setIndicator("List");
        Intent igo = new Intent(MapView.this, MapView_List.class);
        igo.putExtra("service", emgurl);
        tab2.setContent(igo);

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);

        // TabHost.TabSpec tab1 = tabHost.newTabSpec("ListView");
        //TabHost.TabSpec tab2 = tabHost.newTabSpec("MapView");

        spin.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                getspin = spin.getSelectedItemPosition();
                spintxt = spin.getSelectedItem().toString();
                Log.d("tag", "va" + getspin);


                switch (getspin) {

                    case 0: {
                        Log.d("tag", "inside" + "spin1");

                        tabHost = (TabHost) findViewById(android.R.id.tabhost);


                        tabHost.clearAllTabs();


                       // tabHost.getTabWidget().removeView(tabHost.getTabContentView().getChildAt(0));
                        TabHost.TabSpec tab1 = tabHost.newTabSpec("emg_ListView");
                        TabHost.TabSpec tab2 = tabHost.newTabSpec("emg_MapView");


                        tab1.setIndicator("Map");
                        Intent igom = new Intent(MapView.this, MapView_Map.class);
                        igom.putExtra("service", emgurl);
                        tab1.setContent(igom);

                        tab2.setIndicator("List");
                        Intent igo = new Intent(MapView.this, MapView_List.class);
                        igo.putExtra("service", emgurl);
                        tab2.setContent(igo);

                        tabHost.addTab(tab1);
                        tabHost.addTab(tab2);
                        break;

                    }
                    case 1: {
                        Log.d("tag", "inside" + "spin2");

                        tabHost = (TabHost) findViewById(android.R.id.tabhost);


                        tabHost.clearAllTabs();


                        TabHost.TabSpec tab3 = tabHost.newTabSpec("ListView1");
                        TabHost.TabSpec tab4 = tabHost.newTabSpec("MapView1");
                        //tabHost.getTabWidget().removeView(tabHost.getTabWidget().getChildTabViewAt(0));

                        tab3.setIndicator("Map");
                        Intent igom1 = new Intent(MapView.this, MapView_Map.class);
                        igom1.putExtra("service", acciurl);
                        tab3.setContent(igom1);

                        tab4.setIndicator("List");
                        Intent igo1 = new Intent(MapView.this, MapView_List.class);
                        igo1.putExtra("service", acciurl);
                        tab4.setContent(igo1);

                        tabHost.addTab(tab3);
                        tabHost.addTab(tab4);
                        break;

                    }
                    case 2: {

                        Log.d("tag", "inside" + "spin3");


                        tabHost = (TabHost) findViewById(android.R.id.tabhost);


                        tabHost.clearAllTabs();


                        TabHost.TabSpec tab5 = tabHost.newTabSpec("ListView2");
                        TabHost.TabSpec tab6 = tabHost.newTabSpec("MapView2");


                        tab5.setIndicator("Map");
                        Intent igom2 = new Intent(MapView.this, MapView_Map.class);
                        igom2.putExtra("service", recurl);
                        tab5.setContent(igom2);

                        tab6.setIndicator("List");
                        Intent igo2 = new Intent(MapView.this, MapView_List.class);
                        igo2.putExtra("service", recurl);
                        tab6.setContent(igo2);

                        tabHost.addTab(tab5);
                        tabHost.addTab(tab6);
                        break;

                    }

                }


            }
        });


        mtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();


            }
        });


        // getspin = spin.getSelectedItemPosition();

/*
        if (getspin == 0) {
            Log.img_view("tag", "inside" + "spin1");
            tabHost.clearAllTabs();
            tab1.setIndicator("Map");
            Intent igom = new Intent(this, MapView_Map.class);
            igom.putExtra("tag", "emgmap");
            tab1.setContent(igom);

            tab2.setIndicator("List");
            Intent igo = new Intent(this, MapView_List.class);
            igo.putExtra("tag", "emglist");
            tab2.setContent(igo);

            tabHost.addTab(tab1);
            tabHost.addTab(tab2);

        } else if (getspin == 1) {
            Log.img_view("tag", "inside" + "spin2");

            tabHost.clearAllTabs();

            tab1.setIndicator("Map");
            Intent igom1 = new Intent(this, MapView_Map.class);
            igom1.putExtra("tag", "accidentmap");
            tab1.setContent(igom1);

            tab2.setIndicator("List");
            Intent igo1 = new Intent(this, MapView_List.class);
            igo1.putExtra("tag", "accidentlist");
            tab2.setContent(igo1);

            tabHost.addTab(tab1);
            tabHost.addTab(tab2);

        } else if (getspin == 2) {

            Log.img_view("tag", "inside" + "spin3");
            tabHost.clearAllTabs();
            tab1.setIndicator("Map");
            Intent igom2 = new Intent(this, MapView_Map.class);
            igom2.putExtra("tag", "recoverymap");
            tab1.setContent(igom2);

            tab2.setIndicator("List");
            Intent igo2 = new Intent(this, MapView_List.class);
            igo2.putExtra("tag", "recoverylist");
            tab2.setContent(igo2);

            tabHost.addTab(tab1);
            tabHost.addTab(tab2);

        }




        switch (spintxt) {

            case "Emergency": {
                Log.img_view("tag", "inside" + "spin1");

                tabHost.invalidate();
                tabHost.clearAllTabs();
                tab1.setIndicator("Map");
                Intent igom = new Intent(this, MapView_Map.class);
                igom.putExtra("tag", "emgmap");
                tab1.setContent(igom);

                tab2.setIndicator("List");
                Intent igo = new Intent(this, MapView_List.class);
                igo.putExtra("tag", "emglist");
                tab2.setContent(igo);

                tabHost.addTab(tab1);
                tabHost.addTab(tab2);
                break;

            }
            case "Accident": {
                Log.img_view("tag", "inside" + "spin2");
                tabHost.invalidate();

                tabHost.clearAllTabs();

                tab1.setIndicator("Map");
                Intent igom1 = new Intent(this, MapView_Map.class);
                igom1.putExtra("tag", "accidentmap");
                tab1.setContent(igom1);

                tab2.setIndicator("List");
                Intent igo1 = new Intent(this, MapView_List.class);
                igo1.putExtra("tag", "accidentlist");
                tab2.setContent(igo1);

                tabHost.addTab(tab1);
                tabHost.addTab(tab2);
                break;

            }
            case "Recovery": {

                Log.img_view("tag", "inside" + "spin3");


                tabHost.invalidate();
                tabHost.clearAllTabs();
                tab1.setIndicator("Map");
                Intent igom2 = new Intent(this, MapView_Map.class);
                igom2.putExtra("tag", "recoverymap");
                tab1.setContent(igom2);

                tab2.setIndicator("List");
                Intent igo2 = new Intent(this, MapView_List.class);
                igo2.putExtra("tag", "recoverylist");
                tab2.setContent(igo2);

                tabHost.addTab(tab1);
                tabHost.addTab(tab2);
                break;

            }

        }

        */








/*        switch (getspin) {

            case 0: {
                Log.img_view("tag", "inside" + "spin1");

                tabHost.invalidate();
                tabHost.clearAllTabs();
                tab1.setIndicator("Map");
                Intent igom = new Intent(this, MapView_Map.class);
                igom.putExtra("tag", "emgmap");
                tab1.setContent(igom);

                tab2.setIndicator("List");
                Intent igo = new Intent(this, MapView_List.class);
                igo.putExtra("tag", "emglist");
                tab2.setContent(igo);

                tabHost.addTab(tab1);
                tabHost.addTab(tab2);
                break;

            }
            case 1: {
                Log.img_view("tag", "inside" + "spin2");
                tabHost.invalidate();

                tabHost.clearAllTabs();

                tab1.setIndicator("Map");
                Intent igom1 = new Intent(this, MapView_Map.class);
                igom1.putExtra("tag", "accidentmap");
                tab1.setContent(igom1);

                tab2.setIndicator("List");
                Intent igo1 = new Intent(this, MapView_List.class);
                igo1.putExtra("tag", "accidentlist");
                tab2.setContent(igo1);

                tabHost.addTab(tab1);
                tabHost.addTab(tab2);
                break;

            }
            case 2: {

                Log.img_view("tag", "inside" + "spin3");


                tabHost.invalidate();
                tabHost.clearAllTabs();
                tab1.setIndicator("Map");
                Intent igom2 = new Intent(this, MapView_Map.class);
                igom2.putExtra("tag", "recoverymap");
                tab1.setContent(igom2);

                tab2.setIndicator("List");
                Intent igo2 = new Intent(this, MapView_List.class);
                igo2.putExtra("tag", "recoverylist");
                tab2.setContent(igo2);

                tabHost.addTab(tab1);
                tabHost.addTab(tab2);
                break;

            }

        }*/
        //tabHost.addTab(tab1);
        //tabHost.addTab(tab2);



        /*tab1.setIndicator("List");
        Intent igo = new Intent(this, MapView_List.class);
        igo.putExtra("tag", "firstvalue");
        tab1.setContent(igo);*/


        //tab1.setContent(new Intent(this,MapView_List.class));

   /*     tab2.setIndicator("Map");
        tab2.setContent(new Intent(this, MapView_Map.class));*/


    }


    public class Spinner_Adapter extends ArrayAdapter<String> {

        String[] objs;

        public Spinner_Adapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
            this.objs = objects;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row_spn_dropdown, parent, false);

/*
            Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),
                    "Fonts/robol.ttf");*/


            TextView label = (TextView) row.findViewById(R.id.row_spn_tv);

            //  label.setTypeface(tf);

            label.setText(objs[position]);


            return row;
        }
    }


    public void intentProcess() {

    }


}
