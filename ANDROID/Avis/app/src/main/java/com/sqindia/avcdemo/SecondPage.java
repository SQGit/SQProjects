package com.sqindia.avcdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.github.gcacace.signaturepad.views.SignaturePad;
import com.ngx.BluetoothPrinter;
import com.sloop.fonts.FontsManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Ramya on 17-07-2017.
 */

public class SecondPage extends Activity{
    TextView back_tv;
    Spinner spn;
    ArrayList<String> list;
    ArrayList<String> list_color;
    ListView listView;
    static EditText ramptotal,abgrepair;
    Intent intent = getIntent();
    HashMap<String,String> aa;
    SignaturePad mSignaturePad;
    static ImageView img_sticker;
    LinearLayout ll;
    Button submit_btn,btn_turnback;
    private ArrayList<Model> finalexception;
    private BluetoothPrinter mBtp = BluetoothPrinterMain.mBtp;
    AlertDialog dialog;
    ImageView img_close;
    Typeface tf;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avis_page_list);

        FontsManager.initFormAssets(SecondPage.this, "fonts/areal.ttf");
        FontsManager.changeFonts(this);

        back_tv = (TextView) findViewById(R.id.back_tv);

        ramptotal = (EditText) findViewById(R.id.ramp_et);
        abgrepair = (EditText) findViewById(R.id.abgrepair);
        spn = (Spinner) findViewById(R.id.spn1);
        listView = (ListView) findViewById(R.id.listview);
        btn_turnback=(Button)findViewById(R.id.btn_turnback);

        finalexception = new ArrayList<Model>();

        listView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        //dot=(ImageView)findViewById(R.id.dot);
        //btn_sticker=(Button)findViewById(R.id.btn_sticker);
        ll=(LinearLayout)findViewById(R.id.ll);
        submit_btn=(Button)findViewById(R.id.submit_btn);
        final TextView tv_sign_hint = (TextView) findViewById(R.id.tv_sign_hint);
        mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);
        final TextView tv_clear = (TextView) findViewById(R.id.tv_sign_clear);
        img_sticker=(ImageView)findViewById(R.id.img_sticker);
        img_sticker.setVisibility(View.INVISIBLE);

        String[] Repair = new String[]{"yes", "no"};
        list = new ArrayList<>();
        list_color = new ArrayList<>();

        FinalInspection(); //Extra Final Inspection


        btn_turnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(SecondPage.this);
                dialog.setContentView(R.layout.turnback);
                dialog.setCanceledOnTouchOutside(false);

                img_close=(ImageView)dialog.findViewById(R.id.img_close);
                LinearLayout lnr_close=(LinearLayout)dialog.findViewById(R.id.lnr_closee);



                lnr_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();
            }
        });

        ItemDetailsWrapper wrap = (ItemDetailsWrapper) getIntent().getSerializableExtra("obj");
        ItemDetailsWrapper wrap1 =(ItemDetailsWrapper)getIntent().getSerializableExtra("color");
        list = wrap.getItemDetails();
        list_color=wrap1.getItemDetails();


        ListView lview10 = (ListView) findViewById(R.id.listview10);
        FinalInspectionAdapter adapter10 = new FinalInspectionAdapter(this, finalexception);
        lview10.setAdapter(adapter10);
        adapter10.notifyDataSetChanged();

        Log.e("tag","<-------------- GET COLOR ----------------->"+list_color);

        if(list.size()>0)
        {
            ll.setVisibility(View.GONE);
        }


           //*************************** CHANGE COLOR CODE *****************************
         Log.e("tag", "333------------------>" + aa);
         String str="";
         String[] time = new String[list.size()];
         time = list.toArray(time);
         //String[] strList = time.split(",");// here give an error at **time.split(",")**
         for(String splstr: time )
         {
         str=splstr.toString();
         Log.e("tag","guna"+str);

         String substring = str.substring(Math.max(str.length() - 7, 0));
         Log.e("tag","guna11"+substring);

        }




        Log.e("tag", "41325454------------------>" + list);
        Log.e("tag", "41325454------------------>" + list.size());
        Log.e("tag", "4132545400000------------------>" + list_color);
        Log.e("tag", "4132545400000------------------>" + list_color.size());

        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                tv_sign_hint.setText("");
            }

            @Override
            public void onSigned() {
            }

            @Override
            public void onClear() {
                tv_sign_hint.setText("Sign here");
            }
        });

        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignaturePad.clear();
                tv_sign_hint.setText("Final Inspection Approver(Sign)");
                Log.e("tag", "sigpath" + tv_clear);
            }
        });
        back_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // startActivity(new Intent(SecondPage.this, MainActivity.class));
                //overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
            }
        });


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("tag","1234"+PanelIssueAdapter.sticker);


                if(!list.isEmpty()) {
                    if(PanelIssueAdapter.sticker != null){
                        if(!PanelIssueAdapter.sticker.equals("disable")){
                            if (!mSignaturePad.isEmpty()) {
                                Toast.makeText(getApplicationContext(),"Submitted Successfully",Toast.LENGTH_LONG).show();
                                Intent new_car=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(new_car);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Signature can not be blank", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Please put the Cost of Damage",Toast.LENGTH_LONG).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Please put the Cost of Damage",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Select any damage from this list",Toast.LENGTH_LONG).show();
                }

            }
        });

        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(list);
        list.clear();
        list.addAll(hashSet);



        PanelIssueAdapter adapter1 = new PanelIssueAdapter(this, list,list_color);
        listView.setAdapter(adapter1);

        setListViewHeightBasedOnChildren(listView);


    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LinearLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null)
        {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    private void FinalInspection() {

        Model item1, item2, item3, item4,item5;

        item1 = new Model("1", "Verified All Previous repair for Quality", "Fruits", "₹. 200");
        finalexception.add(item1);

        item2 = new Model("2", "IQ & VR Devices Removed", "Vegetable", "₹. 50");
        finalexception.add(item2);


        item3 = new Model("3", "Recalls all Clear/Performed", "Fruits", "₹. 100");
        finalexception.add(item3);

        item4 = new Model("4", "Checked for Hail Damage", "Vegetable", "₹. 50");
        finalexception.add(item4);

        item5 = new Model("5", "Gas Level(to get to ramplauction at 14)", "Vegetable", "₹. 80");
        finalexception.add(item5);


    }
}
