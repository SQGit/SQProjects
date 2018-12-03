package com.sqindia.www.autoparts360.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sqindia.www.autoparts360.Adapter.PartsListAdapter;
import com.sqindia.www.autoparts360.Font.FontsOverride;
import com.sqindia.www.autoparts360.Model.PartsList;
import com.sqindia.www.autoparts360.R;
import com.sqindia.www.autoparts360.Utils.Config;
import com.sqindia.www.autoparts360.Utils.HttpUtils;
import com.sqindia.www.autoparts360.Utils.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CarPartsList extends Activity {

    RecyclerView carlist_recyclertview;
    PartsListAdapter partsListAdapter;
    PartsList partsList;
    private List<PartsList> carPartsLists;
    LinearLayout back,noparts_found;
    Dialog loader_dialog;
    Intent intent;
    String part_id;
    TextView carmake_tv,carmodel_tv,caryear_tv,carprice_tv,carlocation_tv,carmileage_tv,status_tv;
    ImageView carpic_iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carpartslist);

        View v1 = getWindow().getDecorView().getRootView();
        FontsOverride.overrideFonts(CarPartsList.this, v1);

        carlist_recyclertview=findViewById(R.id.carlist_recyclertview);
        back=findViewById(R.id.back);
        noparts_found=findViewById(R.id.noparts_found);
        carmake_tv=findViewById(R.id.carmake_tv);
        carmodel_tv=findViewById(R.id.carmodel_tv);
        caryear_tv=findViewById(R.id.caryear_tv);
        carprice_tv=findViewById(R.id.carprice_tv);
        carlocation_tv=findViewById(R.id.carlocation_tv);
        carmileage_tv=findViewById(R.id.carmileage_tv);
        carpic_iv=findViewById(R.id.carpic_iv);
        status_tv=findViewById(R.id.status_tv);

        carPartsLists=new ArrayList<>();
        intent=getIntent();
        part_id=intent.getStringExtra("show_carpart_id");
        Log.e("tag","OH GOS--------->"+part_id);





        noparts_found.setVisibility(View.GONE);

        if (Util.Operations.isOnline(CarPartsList.this)) {
            new getPartsLists().execute();

        }
        else
        {
            Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
        }



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),ViewCarPartsActivity.class);
                startActivity(i);
                finish();
            }
        });


    }




    //@@@@@@@@@@@@@@@@@@@@@@@@ CALL VIEW  PARTS LIST API
    public class getPartsLists extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("car_id",part_id);
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest(Config.GET_CAR_FULL_DETAILS,json);

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jo = new JSONObject(s);
                String status = jo.getString("status");

                if (status.equals("true")) {
                    JSONObject jsonObject=jo.getJSONObject("car_details");




                        /*partsList.carlist_make=jsonObject.getString("car_make");
                        partsList.carlist_brand=jsonObject.getString("car_brand");
                        partsList.carlist_model=jsonObject.getString("car_model");
                        partsList.carlist_year=jsonObject.getString("car_year");
                        partsList.carlist_mileage=jsonObject.getString("car_mileage");
                        Log.e("tag","checking"+jsonObject.getString("car_mileage"));
                        partsList.carlist_price=jsonObject.getString("car_price");
                        partsList.carlist_location=jsonObject.getString("car_location");
                        partsList.carlist_car_image1=jsonObject.getString("car_image1");
                        partsList.carlist_status=jsonObject.getString("car_status");*/



                   carmake_tv.setText(jsonObject.getString("car_brand"));
                    carmodel_tv.setText(jsonObject.getString("car_model"));
                    caryear_tv.setText(jsonObject.getString("car_year"));
                    carmileage_tv.setText(jsonObject.getString("car_mileage"));
                    carprice_tv.setText(jsonObject.getString("car_price"));
                    carlocation_tv.setText(jsonObject.getString("car_location"));
                    status_tv.setText(jsonObject.getString("car_status"));
                    String carpic_path= "http://104.197.80.225/autoparts360/assets/img/car_images/"+ jsonObject.getString("car_image1");
                    Glide.with(getApplicationContext()).load(carpic_path)
                            .into(carpic_iv);



                    partsList = new PartsList();

                    JSONArray jsonArray=jo.getJSONArray("part_details");

                    for(int l=0;l<jsonArray.length();l++)
                    {
                        JSONObject object =jsonArray.getJSONObject(l);
                        partsList.carlist_partid=object.getString("part_id");
                        partsList.carlist_maincat=object.getString("main_cat");
                        Log.e("tag","1"+object.getString("main_cat"));
                        partsList.carlist_subcat=object.getString("sub_cat");
                        partsList.carlist_image1=object.getString("image1");
                        partsList.carlist_image2=object.getString("image2");
                        carPartsLists.add(partsList);
                    }



                    // Setup and Handover data to recyclerview

                    LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                    carlist_recyclertview.setLayoutManager(manager);
                    carlist_recyclertview.setHasFixedSize(true);
                    partsListAdapter = new PartsListAdapter(CarPartsList.this, carPartsLists);
                    carlist_recyclertview.setAdapter(partsListAdapter);



                } else {

                }
            } catch (Exception e) {

            }


        }

    }

}
