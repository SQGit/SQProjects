package com.sqindia.www.autoparts360.Adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.sqindia.www.autoparts360.Activity.CarPartsList;
import com.sqindia.www.autoparts360.Activity.TransformationActivity;
import com.sqindia.www.autoparts360.Model.PartsList;
import com.sqindia.www.autoparts360.R;
import com.sqindia.www.autoparts360.Utils.Config;
import com.sqindia.www.autoparts360.Utils.HttpUtils;
import com.sqindia.www.autoparts360.Utils.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by Guna on 29-11-2017.
 */

public class PartsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    public LayoutInflater inflater;
    private List<PartsList> partsLists;

    String path_carimg1,path_carimg2,path_vinimg1,path_vinimg2,car_status,get_carid;

    public PartsListAdapter(CarPartsList carPartsList, List<PartsList> carPartsLists) {

        this.context=carPartsList;
        this.partsLists=carPartsLists;
        inflater = LayoutInflater.from(context);

    }





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.viewparts_row, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        // Get current position of item in recyclerview to bind data and assign values from list
        final MyHolder myHolder= (MyHolder) holder;
        final PartsList current=partsLists.get(position);


        myHolder.maincat_tv.setText(current.carlist_maincat);
        myHolder.subcat_tv.setText(current.carlist_subcat);
        Log.e("tag","jjjjjj---->"+current.carlist_image2);


        String carpic_path1=  current.carlist_image1;
        String carpic_path2=  current.carlist_image2;

        if(!carpic_path1.equals(""))
        {
            Glide.with(context).load("http://104.197.80.225/autoparts360/assets/img/car_images/"+carpic_path1)
                    .into(myHolder.pic1_iv);
        }

        if(!carpic_path2.equals(""))
        {
            Glide.with(context).load("http://104.197.80.225/autoparts360/assets/img/car_images/"+carpic_path2)
                    .into(myHolder.pic2_iv);
        }

    }


    @Override
    public int getItemCount() {
        return partsLists.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }



    public class MyHolder extends RecyclerView.ViewHolder {

        public TextView maincat_tv,subcat_tv;
        ImageView pic1_iv,pic2_iv;



        // create constructor to get widget reference
        public MyHolder(final View itemView) {
            super(itemView);
            Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/helevetical.ttf");
            maincat_tv = (TextView) itemView.findViewById(R.id.maincat_tv);
            subcat_tv = (TextView) itemView.findViewById(R.id.subcat_tv);
            pic1_iv = itemView.findViewById(R.id.pic1_iv);
            pic2_iv = itemView.findViewById(R.id.pic2_iv);
        }
    }




}
