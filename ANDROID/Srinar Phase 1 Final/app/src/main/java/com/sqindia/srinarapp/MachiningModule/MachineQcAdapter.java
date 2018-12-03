package com.sqindia.srinarapp.MachiningModule;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sqindia.srinarapp.Model.MachineJobList;
import com.sqindia.srinarapp.R;
import com.sqindia.srinarapp.SuperAdmin.DialogShowQcList;

import java.util.List;


/**
 * Created by Guna on 29-11-2017.
 */

public class MachineQcAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    public LayoutInflater inflater;
    private List<MachineJobList> machineJobLists;
    SharedPreferences.Editor editor;
    String get_batchid,get_quantity,session_token,str_dept,str_name,str_role,get_approved;
    Typeface lato;

    public MachineQcAdapter(QcMachineJob qcMachineJob, List<MachineJobList> machineJobLists) {
        this.context=qcMachineJob;
        this.machineJobLists=machineJobLists;
        inflater = LayoutInflater.from(context);
    }





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.qc_machine_job_row, parent, false);

        //token get from Login Activity:
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
        session_token = sharedPreferences.getString("str_sessiontoken", "");
        session_token = sharedPreferences.getString("str_sessiontoken", "");
        str_dept=sharedPreferences.getString("userpermission","");
        str_name=sharedPreferences.getString("emp_name","");
        str_role=sharedPreferences.getString("str_role","");
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        // Get current position of item in recyclerview to bind data and assign values from list
        final MyHolder myHolder= (MyHolder) holder;
        final MachineJobList current=machineJobLists.get(position);
        myHolder.sno_tv.setText(current._id);
        myHolder.shift_tv.setText(current.mac_shift);
        myHolder.machine_tv.setText(current.mac_machine);

        myHolder.operator_tv.setText(current.mac_operator);
        myHolder.part_tv.setText(current.mac_part);
        myHolder.subpart_tv.setText(current.mac_subpart);
        myHolder.approved_tv.setText(current.mac_approved);
        myHolder.rejected_tv.setText(current.mac_rejected);
        myHolder.date_tv.setText(current.mac_date);


       String str_status=current.mac_approved;


       if(myHolder.approved_tv.getText().toString().equals("0"))
       {
           myHolder.status_tv.setText("UnChecked");
           myHolder. status_img.setImageResource(R.drawable.red_check);
       }
       else  if(myHolder.approved_tv.getText().toString().equals(current.mac_quantitycompleted)) {
           myHolder.status_tv.setText("checked");
           myHolder. status_img.setImageResource(R.drawable.green_check);
       }
       else
       {
           myHolder.status_tv.setText("checked");
           myHolder. status_img.setImageResource(R.drawable.yellow_check);
       }


       myHolder.status_img.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               MachineJobList current=machineJobLists.get(position);
               get_batchid = current._id;
               get_quantity = current.mac_quantitycompleted;
               get_approved=current.mac_approved;




               if(!myHolder.approved_tv.getText().toString().equals(current.mac_quantitycompleted))
               {
                   if(str_dept.equals("MCECAC"))
                   {

                       SharedPreferences sharedPrefces = PreferenceManager.getDefaultSharedPreferences(context);
                       SharedPreferences.Editor edit = sharedPrefces.edit();
                       edit.putString("dia_mac_batch_id", get_batchid);
                       edit.putString("dia_mac_pending_qty_mac",get_quantity);
                       edit.putString("dia_mac_approved",get_approved);
                       edit.commit();
                       DialogSignatureMachine cdd = new DialogSignatureMachine((Activity) context);
                       cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                       cdd.show();

                   }
                   if(str_dept.equals("MC"))
                   {

                       SharedPreferences sharedPrefces = PreferenceManager.getDefaultSharedPreferences(context);
                       SharedPreferences.Editor edit = sharedPrefces.edit();
                       edit.putString("dia_mac_batch_id", get_batchid);
                       edit.putString("dia_mac_pending_qty_mac",get_quantity);
                       edit.putString("dia_mac_approved",get_approved);
                       edit.commit();
                       DialogSignatureMachine cdd = new DialogSignatureMachine((Activity) context);
                       cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                       cdd.show();

                   }
                   else if(str_dept.equals("ALL"))
                   {

                       SharedPreferences sharedPrefces = PreferenceManager.getDefaultSharedPreferences(context);
                       SharedPreferences.Editor edit = sharedPrefces.edit();
                       edit.putString("dia_mac_batch_id", get_batchid);
                       edit.putString("dia_mac_pending_qty_mac",get_quantity);
                       edit.putString("dia_mac_approved",get_approved);
                       edit.commit();
                       DialogSignatureMachine cdd = new DialogSignatureMachine((Activity) context);
                       cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                       cdd.show();

                   }
                   else
                   {
                       Toast.makeText(context,"can't accessed for Machining.....",Toast.LENGTH_LONG).show();
                   }
               }
               else
               {
                   Toast.makeText(context,"This part completed and moved to next level...",Toast.LENGTH_LONG).show();
               }





           }
       });



       myHolder.qcstate_img.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               MachineJobList current=machineJobLists.get(position);
               SharedPreferences sharedPrefces = PreferenceManager.getDefaultSharedPreferences(context);
               SharedPreferences.Editor edit = sharedPrefces.edit();
               edit.putString("qc_batchid", current._id);
               edit.commit();

               DialogShowQcList cdd = new DialogShowQcList((Activity) context);
               cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
               cdd.show();
           }
       });


        myHolder.qty_tv.setText(current.mac_quantitycompleted);
        lato = Typeface.createFromAsset(context.getAssets(), "fonts/segoeuil.ttf");
    }


    @Override
    public int getItemCount() {
        return machineJobLists.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }



    public class MyHolder extends RecyclerView.ViewHolder {

        public TextView sno_tv,shift_tv,machine_tv,operator_tv,part_tv,subpart_tv,status_tv,qty_tv,approved_tv,rejected_tv,date_tv;
        public ImageView status_img,qcstate_img;


        // create constructor to get widget reference
        public MyHolder(final View itemView) {
            super(itemView);
            Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/segoeuil.ttf");
            sno_tv = (TextView) itemView.findViewById(R.id.sno_tv);
            shift_tv = (TextView) itemView.findViewById(R.id.shift_tv);
            machine_tv=itemView.findViewById(R.id.machine_tv);

            operator_tv = (TextView) itemView.findViewById(R.id.operator_tv);
            part_tv = (TextView) itemView.findViewById(R.id.part_tv);
            subpart_tv=itemView.findViewById(R.id.subpart_tv);
            status_tv=itemView.findViewById(R.id.status_tv);
            qty_tv=itemView.findViewById(R.id.qty_tv);
            status_img=itemView.findViewById(R.id.status_img);
            approved_tv=itemView.findViewById(R.id.approved_tv);
            rejected_tv=itemView.findViewById(R.id.rejected_tv);
            date_tv=itemView.findViewById(R.id.date_tv);
            qcstate_img=itemView.findViewById(R.id.qcstate_img);


            sno_tv.setTypeface(tf);
            shift_tv.setTypeface(tf);
            machine_tv.setTypeface(tf);
            operator_tv.setTypeface(tf);
            part_tv.setTypeface(tf);
            subpart_tv.setTypeface(tf);
            status_tv.setTypeface(tf);
            qty_tv.setTypeface(tf);
            approved_tv.setTypeface(tf);
            rejected_tv.setTypeface(tf);
            date_tv.setTypeface(tf);


        }
    }
}
