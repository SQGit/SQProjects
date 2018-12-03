package com.sqindia.www.autoparts360.Adapter;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
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
import com.sqindia.www.autoparts360.Activity.ViewCarPartsActivity;
import com.sqindia.www.autoparts360.Model.PhotoItem;
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

public class ViewPartsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    public LayoutInflater inflater;
    private List<PhotoItem> photoItems;

    String path_carimg1,path_carimg2,path_vinimg1,path_vinimg2,car_status,get_carid,del_car_id;

    public ViewPartsAdapter(ViewCarPartsActivity viewCarPartsActivity, List<PhotoItem> photoItems) {

        this.context=viewCarPartsActivity;
        this.photoItems=photoItems;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_parts_row, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        // Get current position of item in recyclerview to bind data and assign values from list
        final MyHolder myHolder= (MyHolder) holder;
        final PhotoItem current=photoItems.get(position);
        Log.e("tag","reading photo"+photoItems.get(position));

        Log.e("tag","jjjjjjfirst---->"+position);

        myHolder.carmake_val.setText(current.car_location);
        myHolder.caryaer_val.setText(current.car_year);
        myHolder.carbrand_val.setText(current.car_brand);
        myHolder.carloc_val.setText(current.car_location);
        myHolder.model_valtv.setText(current.car_model);
        myHolder.mileage_val.setText(current.car_mileage);
        myHolder.price_tv.setText(current.car_price);
        car_status=current.car_status;

        path_carimg1=current.car_image1;
        path_carimg2=current.car_image2;

        myHolder.del_iv.setVisibility(View.INVISIBLE);

        if(car_status.equals("pending"))
        {
            myHolder.carstatus_tv.setText("Pending");
            myHolder.status_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.orange_stand));
            myHolder.carstatus_tv.setTextColor(context.getResources().getColor(R.color.colorPrimary));

            myHolder.del_iv.setVisibility(View.VISIBLE);
            myHolder.del_iv.setEnabled(true);

        }
        else if(car_status.equals("approved"))
        {
            myHolder.carstatus_tv.setText("Approved");
            myHolder.status_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.green_stand));
            myHolder.carstatus_tv.setTextColor(context.getResources().getColor(R.color.green_stand_text));
        }
        else if(car_status.equals("shipped"))
        {
            myHolder.carstatus_tv.setText("Shipped");
            myHolder.status_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.blue_stand));
            myHolder.carstatus_tv.setTextColor(context.getResources().getColor(R.color.blue_stand_text));
        }


        myHolder.del_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PhotoItem current=photoItems.get(position);
                del_car_id=current.car_id;
                Log.e("tag","delete car id"+del_car_id);
                new deleteParts().execute();
            }
        });



        if(path_carimg1!= null)
        {
            path_carimg1= "http://104.197.80.225/autoparts360/assets/img/car_images/"+ current.car_image1;
        }
        else
        {
            path_carimg2= "http:104.197.80.225/autoparts360/assets/img/car_images/"+ current.car_image2;
        }




         path_vinimg1=current.vin_image1;
         path_vinimg2=current.vin_image2;



        Glide.with(context).load(path_carimg1)
                .into(myHolder.carimg_iv);



        myHolder.carimg_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PhotoItem current=photoItems.get(position);
                path_carimg1= current.car_image1;
                path_carimg2=  current.car_image2;
                path_vinimg1=current.vin_image1;
                path_vinimg2=current.vin_image2;

                Intent img_anim=new Intent(context,TransformationActivity.class);
                img_anim.putExtra("pull_car1",path_carimg1);
                img_anim.putExtra("pull_car2",path_carimg2);
                img_anim.putExtra("pull_vin1",path_vinimg1);
                img_anim.putExtra("pull_vin2",path_vinimg2);
                context.startActivity(img_anim);
            }
        });



        myHolder.carparts_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final PhotoItem current=photoItems.get(position);
                get_carid=current.car_id;

                Intent show=new Intent(context, CarPartsList.class);
                show.putExtra("show_carpart_id",get_carid);

                context.startActivity(show);


            }
        });

    }


    @Override
    public int getItemCount() {
        return photoItems.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }



    public class MyHolder extends RecyclerView.ViewHolder {

        public TextView carmake_head,carmake_val,caryear_head,caryaer_val,carbrand_head,carbrand_val,carloc_head,carloc_val,carparts_tv,
                model_headtv,model_valtv,mileage_head,mileage_val,price_tv,carstatus_tv;
        public ImageView carimg_iv,status_iv,del_iv;


        // create constructor to get widget reference
        public MyHolder(final View itemView) {
            super(itemView);
            Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/helevetical.ttf");
            carmake_head = (TextView) itemView.findViewById(R.id.carmake_head);
            carmake_val = (TextView) itemView.findViewById(R.id.carmake_val);
            caryear_head=itemView.findViewById(R.id.caryear_head);
            caryaer_val = (TextView) itemView.findViewById(R.id.caryaer_val);
            carbrand_head = (TextView) itemView.findViewById(R.id.carbrand_head);
            carbrand_val=itemView.findViewById(R.id.carbrand_val);
            carloc_head=itemView.findViewById(R.id.carloc_head);
            carloc_val=itemView.findViewById(R.id.carloc_val);
            carparts_tv=itemView.findViewById(R.id.carparts_tv);
            carimg_iv=itemView.findViewById(R.id.carimg_iv);
            model_headtv=itemView.findViewById(R.id.model_headtv);
            model_valtv=itemView.findViewById(R.id.model_valtv);
            mileage_head=itemView.findViewById(R.id.mileage_head);
            mileage_val=itemView.findViewById(R.id.mileage_val);
            price_tv=itemView.findViewById(R.id.price_tv);
            carstatus_tv=itemView.findViewById(R.id.carstatus_tv);
            status_iv=itemView.findViewById(R.id.status_iv);
            del_iv=itemView.findViewById(R.id.del_iv);


            carmake_head.setTypeface(tf);
            carmake_val.setTypeface(tf);
            caryear_head.setTypeface(tf);
            caryaer_val.setTypeface(tf);
            carbrand_head.setTypeface(tf);
            carbrand_val.setTypeface(tf);
            carloc_head.setTypeface(tf);
            carloc_val.setTypeface(tf);
            carparts_tv.setTypeface(tf);
            model_headtv.setTypeface(tf);
            model_valtv.setTypeface(tf);
            mileage_head.setTypeface(tf);
            mileage_val.setTypeface(tf);
            price_tv.setTypeface(tf);
            carstatus_tv.setTypeface(tf);
        }
    }


    //@@@@@@@@@@@@@@@@@@@@@@@@ CALL VIEW MACHINE API
    public class deleteParts extends AsyncTask<String, Void, String> {
        private ProgressDialog dialog;


        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setMessage("Loading..., please wait.");
            dialog.show();


        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("car_id",del_car_id);
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest(Config.DEL_PARTS,json);

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);



            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            try {
                JSONObject jo = new JSONObject(s);
                String status = jo.getString("status");

                if (status.equals("success")) {

                    String msg=jo.getString("msg");
                    Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
                    Intent i=new Intent(context,ViewCarPartsActivity.class);
                    context.startActivity(i);





                } else {

                }
            } catch (Exception e) {

            }


        }

    }




}
