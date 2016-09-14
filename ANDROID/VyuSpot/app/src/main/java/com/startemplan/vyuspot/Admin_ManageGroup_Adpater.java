package com.startemplan.vyuspot;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.rey.material.widget.TextView;

/**
 * Created by Salman on 5/20/2016.
 */
public class Admin_ManageGroup_Adpater extends BaseAdapter {


    Context context;
    TextView tv_data;
    LinearLayout layout;

    public Admin_ManageGroup_Adpater(Context cont) {
        this.context = cont;
    }


    @Override
    public int getCount() {
        return 12;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflat = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflat.inflate(R.layout.managegroup_adapter, null);

        layout = (LinearLayout) view.findViewById(R.id.ll1);


        if(position%2 ==0){
            layout.setBackgroundColor(context.getResources().getColor(R.color.lv_one));
        }
        else {
            layout.setBackgroundColor(context.getResources().getColor(R.color.lv_two));
        }



        tv_data  = (TextView) view.findViewById(R.id.textview_data);


        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/robol.ttf");
        tv_data.setTypeface(tf);

        return view;
    }
}
