package com.startemplan.vyuspot;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devspark.robototextview.widget.RobotoTextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Salman on 2/9/2016.
 *
 *
 */
public class View_Needs_Adapter extends BaseAdapter{



    // Declare Variables
    Context context;
    LayoutInflater inflater= null;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();
    LinearLayout lll;
    RobotoTextView txtstatus;
    LinearLayout lin;
    TextView tv,name,location,landmark,number,needs,cared;
    View status;
   // TextView txtstatus;

    public String sname,slocation,slandmark,snumber,sneeds,sspotid,scare1=null,scare2=null,caredby=null;


    public View_Needs_Adapter(Context context, ArrayList<HashMap<String, String>> arraylist) {

        this.context = context;
        data = arraylist;


    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

   /* static class ViewHolder {
        TextView tv,name,location,landmark,number,needs,cared;
    }
*/


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public View getView(final int position, View convertView, ViewGroup parent) {


       // ViewHolder holder = new ViewHolder();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.view_needs_list,parent,false);


        status = itemView.findViewById(R.id.statusview);
        //txtstatus = (TextView) itemView.findViewById(R.id.statustext);
        txtstatus = (RobotoTextView) itemView.findViewById(R.id.statustext);

        lin = (LinearLayout) itemView.findViewById(R.id.ll);


/*
        if(resultp.get("care1") == null){
            scare1="null";
        }
        else if(resultp.get("care2") == null){
            scare2 = "null";
        }
        else {
            scare1 = resultp.get("care1");
            scare2 = resultp.get("care2");
        }

        //Log.img_map("tag",scare1);
        //Log.img_map("tag",scare2);

        if(scare1=="null" || scare2 =="null"){
            caredby="";
        }
        else{
            caredby = scare1+" ("+scare2+")";
        }
*/
    /*    scare1 = resultp.get("care1");
        scare2 = resultp.get("care2");

        if(scare1 == null){
            caredby=null;
        }
        else{
            caredby = scare1+" ("+scare2+")";
        }

        if(caredby == "nullnull"){
            caredby= null;
        }*/



//        Log.img_map("tag",caredby)









        scare1 = resultp.get("care1");

        String ss = resultp.get("Name");
        //Log.img_map("tag",ss);


      if (ss =="guna") {
            //itemView.setBackgroundResource(R.color.blue);
            //Toast.makeText(context,"Varun(9854752445) Takes responsibility of this person",Toast.LENGTH_LONG).show();
            itemView.setEnabled(false);

          status.setBackgroundColor(context.getResources().getColor(R.color.ras1));

         /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
              status.setBackground(context.getResources().getDrawable(R.drawable.sts));
          }
          else {
              status.setBackgroundColor(context.getResources().getColor(R.color.shadow_start_color));
          }*/
          txtstatus.setText("Took Care");
           // lin.setBackground(context.getResources().getDrawable(R.color.sad));
        } else {

          status.setBackgroundColor(context.getResources().getColor(R.color.ras));

          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
             // status.setBackground(context.getResources().getDrawable(R.drawable.sts2));
          }
          txtstatus.setText("Not Taken");
            //itemView.setBackgroundResource(R.color.navy);
        }



        resultp = data.get(position);




        name = (TextView) itemView.findViewById(R.id.tname);
        location = (TextView) itemView.findViewById(R.id.tloc);
        landmark = (TextView) itemView.findViewById(R.id.tland);
        number = (TextView) itemView.findViewById(R.id.tnum);
        needs = (TextView) itemView.findViewById(R.id.tneeds);
        cared = (TextView) itemView.findViewById(R.id.tcare);


           // itemView.setTag(holder);

            name.setText(resultp.get("Name"));
            location.setText(resultp.get("Location"));
            landmark.setText(resultp.get("Landmark"));
            number.setText(resultp.get("ContactNo"));
            needs.setText((resultp.get("Needs")));
            cared.setText(resultp.get("care1")+" ("+resultp.get("care2")+")");


        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {









             //   resultp = data.get(position);

                sname = resultp.get("Name");
                slocation = resultp.get("Location");
                slandmark = resultp.get("Landmark");
                snumber = resultp.get("ContactNo");
                sneeds = resultp.get("Needs");
                sspotid = resultp.get("spotid");
                scare1 = resultp.get("care1");
                scare2 = resultp.get("care2");

                Log.e("tag", scare1);
                Log.e("tag", scare2);
                Log.e("tag", sspotid);
                Log.e("tag", sname);


                Intent goV = new Intent(context, View_Needs_User.class);
                goV.putExtra("a1", sname);
                goV.putExtra("a2", slocation);
                goV.putExtra("a3", slandmark);
                goV.putExtra("a4", snumber);
                goV.putExtra("a5", sneeds);

                goV.putExtra("spot", sspotid);
                goV.putExtra("b1", scare1);
                goV.putExtra("b2", scare2);

                context.startActivity(goV);


            }
            });


        return itemView;
    }
}