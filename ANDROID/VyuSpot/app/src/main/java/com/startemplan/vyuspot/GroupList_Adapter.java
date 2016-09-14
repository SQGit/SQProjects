package com.startemplan.vyuspot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Salman on 5/20/2016.
 */
public class GroupList_Adapter extends BaseAdapter {


    Context c1;


    public GroupList_Adapter(Context c){
        this.c1= c;
    }

    @Override
    public int getCount() {
        return 5;
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

        LayoutInflater inflat = (LayoutInflater) c1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflat.inflate(R.layout.grouplist_adapter, null);

        return  view;

    }
}
