package com.startemplan.vyuspot;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.rey.material.widget.Button;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Salman on 5/20/2016.
 */
public class Admin_ManageGroup extends Activity {

    Button btn_emg, btn_acc, btn_rec, btn_del, btn_snd, btn_giv;
    ImageView btn_menu;
    Admin_ManageGroup_Adpater group_adapt;
    ListView listview;
    LinearLayout bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managegroup);

        btn_emg = (Button) findViewById(R.id.button_emg);
        btn_acc = (Button) findViewById(R.id.button_acci);
        btn_rec = (Button) findViewById(R.id.button_reco);
        btn_del = (Button) findViewById(R.id.button_delete);
        btn_snd = (Button) findViewById(R.id.button_send);
        btn_giv = (Button) findViewById(R.id.button_given);
        btn_menu = (ImageView) findViewById(R.id.button_menu);
        listview = (ListView) findViewById(R.id.listview);

        bottom = (LinearLayout) findViewById(R.id.bottom);


        btn_giv.setVisibility(View.INVISIBLE);
        btn_emg.setBackgroundColor(getResources().getColor(R.color.tab_br));
        btn_acc.setBackgroundColor(getResources().getColor(R.color.tab_or));
        btn_rec.setBackgroundColor(getResources().getColor(R.color.tab_or));
        bottom.setWeightSum(2);


        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 0.0f);

        btn_giv.setLayoutParams(param);


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/robol.ttf");
        btn_emg.setTypeface(tf);
        btn_acc.setTypeface(tf);
        btn_rec.setTypeface(tf);
        btn_del.setTypeface(tf);
        btn_snd.setTypeface(tf);
        btn_giv.setTypeface(tf);


        group_adapt = new Admin_ManageGroup_Adpater(getApplicationContext());
        listview.setAdapter(group_adapt);


        btn_emg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_emg.setBackgroundColor(getResources().getColor(R.color.tab_br));
                btn_acc.setBackgroundColor(getResources().getColor(R.color.tab_or));
                btn_rec.setBackgroundColor(getResources().getColor(R.color.tab_or));
                bottom.setWeightSum(2);

                btn_giv.setVisibility(View.INVISIBLE);

                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                param.weight = 0;
                btn_giv.setLayoutParams(param);

            }
        });

        btn_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_emg.setBackgroundColor(getResources().getColor(R.color.tab_or));
                btn_acc.setBackgroundColor(getResources().getColor(R.color.tab_br));
                btn_rec.setBackgroundColor(getResources().getColor(R.color.tab_or));
                bottom.setWeightSum(2);
                btn_giv.setVisibility(View.INVISIBLE);

                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 0.0f);
                btn_giv.setLayoutParams(param);
            }
        });


        btn_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_emg.setBackgroundColor(getResources().getColor(R.color.tab_or));
                btn_acc.setBackgroundColor(getResources().getColor(R.color.tab_or));
                btn_rec.setBackgroundColor(getResources().getColor(R.color.tab_br));
                bottom.setWeightSum(3);
                btn_giv.setVisibility(View.VISIBLE);


                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                param.weight = 1;
                btn_giv.setLayoutParams(param);

            }
        });


        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // openContextMenu(v);


                PopupMenu popup = new PopupMenu(Admin_ManageGroup.this, btn_menu);

                popup.getMenuInflater().inflate(R.menu.grp_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {


                            case R.id.item1: {

                                return true;
                            }
                            case R.id.item2: {

                                return true;
                            }

                            default: {
                                return true;
                            }


                        }


                    }
                });

                popup.show();
            }
        });
    }


/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.grp_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.item1: {

            }
            case R.id.item2: {

            }

            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
*/


 /*   @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        //getMenuInflater().inflate(R.menu.grp_menu, menu);
       // super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.grp_menu, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {


        switch (item.getItemId()) {


            case R.id.item1: {

            }
            case R.id.item2: {

            }

            default: {
                return super.onOptionsItemSelected(item);
            }
        }


    }*/











    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Context Menu");
        menu.add(0, v.getId(), 0, "Action 1");
        menu.add(0, v.getId(), 0, "Action 2");
        menu.add(0, v.getId(), 0, "Action 3");
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:

                return true;
            case R.id.item2:

                return true;
            default:
                return super.onContextItemSelected(item);

        }


    }
























}