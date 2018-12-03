package com.sqindia.www.autoparts360.Fragment.upload;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sqindia.www.autoparts360.Activity.DashboardActivity;
import com.sqindia.www.autoparts360.Activity.UploadCarParts;
import com.sqindia.www.autoparts360.R;
import com.sqindia.www.autoparts360.Utils.Utils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class LightsFragment extends Fragment implements View.OnClickListener{

    ImageView head_pic1_iv,head_pic2_iv,brake_pic1_iv,brake_pic2_iv,headlight_pic1_iv,headlight_pic2_iv,others_pic1_iv,others_pic2_iv;
    ImageView head_plus1,head_plus2,brake_plus1,brake_plus2,headlight_plus1,headlight_plus2,others_plus1,others_plus2;
    CheckBox chk_head1,chk_head2,chk_brake1,chk_brake2,chk_headlight1,chk_headlight2,chk_others1,chk_others2;
    Dialog cameraDialog;
    LinearLayout lnr_takepic, lnr_takegallery;
    Bitmap bitmap;
    String selectedImagePath;
    public static TextView lights_box1,lights_box2,lights_box3;
    public static EditText lights_others;
    String collect_carmake,collect_caryear,collect_carbrand,collect_carmodel,collect_carmileage,collect_carprice,collect_carlocation,collect_vinno;

    SharedPreferences sharedPrefces;
    SharedPreferences.Editor edit;
    String path_engine1,path_engine2,path_ac1,path_ac2,path_emission1,path_emission2,path_fuel1,path_fuel2,path_engine_other1,path_engine_other2;
    String path_doors1,path_doors2,path_bumpers1,path_bumpers2,path_hoods1,path_hoods2,path_body_other1,path_body_other2;
    String path_automatic1,path_automatic2,path_manual1,path_manual2,path_trans_other1,path_trans_other2;
    String path_seat1,path_seat2,path_steering1,path_steering2,path_dash1,path_dash2,path_radio1,path_radio2,path_interior_other1,path_interior_other2;
    String path_brake1,path_brake2,path_suspension1,path_suspension2,path_rims1,path_rims2,path_wheels1,path_wheels2,path_wheel_other1,path_wheel_other2;
    String path_oil1,path_oil2,path_ignition1,path_ignition2,path_switch1,path_switch2,path_motor1,path_motor2,path_electrical_other1,path_electrical_other2;
    String path_head1,path_head2,path_brakelig1,path_brakelig2,path_headlight1,path_headlight2,path_light_other1,path_light_other2;
    String path_rehead1,path_rehead2,path_rebrakelig1,path_rebrakelig2,path_reheadlight1,path_reheadlight2,path_relight_other1,path_relight_other2;
    String userid;

    protected static final int TAKE_HEAD_PIC1 = 1;
    protected static final int TAKE_HEAD_PIC2 = 2;
    protected static final int SELECT_HEAD_PIC1 = 3;
    protected static final int SELECT_HEAD_PIC2 = 4;


    protected static final int TAKE_BRAKE_PIC1 = 5;
    protected static final int TAKE_BRAKE_PIC2 = 6;
    protected static final int SELECT_BRAKE_PIC1 = 7;
    protected static final int SELECT_BRAKE_PIC2 = 8;

    protected static final int TAKE_HEADLIGHT_PIC1 = 9;
    protected static final int TAKE_HEADLIGHT_PIC2 = 10;
    protected static final int SELECT_HEADLIGHT_PIC1 = 11;
    protected static final int SELECT_HEADLIGHT_PIC2 = 12;


    protected static final int TAKE_OTHERS_PIC1 = 17;
    protected static final int TAKE_OTHERS_PIC2 = 18;
    protected static final int SELECT_OTHERS_PIC1 = 19;
    protected static final int SELECT_OTHERS_PIC2 = 20;



    String collect_engine_heads,collect_body_heads,collect_transmission_heads,collect_interior_heads,collect_rimwheel_heads,collect_electricals_heads,collect_light_heads;
    String collect_car1,collect_car2,collect_vin1,collect_vin2;



    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lights, container, false);

        sharedPrefces = PreferenceManager.getDefaultSharedPreferences(getActivity());
        edit = sharedPrefces.edit();

        UploadCarParts.upload_part_btn.setEnabled(true);

        lights_box1=view.findViewById(R.id.lights_box1);
        lights_box2=view.findViewById(R.id.lights_box2);
        lights_box3=view.findViewById(R.id.lights_box3);
        lights_others=view.findViewById(R.id.lights_others);

        //@@@@@@@@@@@@@@@@ HEAD LAMPS FindviewbyId
        head_pic1_iv=view.findViewById(R.id.head_pic1_iv);
        head_plus1=view.findViewById(R.id.head_plus1);
        chk_head1=view.findViewById(R.id.chk_head1);
        head_pic2_iv=view.findViewById(R.id.head_pic2_iv);
        head_plus2=view.findViewById(R.id.head_plus2);
        chk_head2=view.findViewById(R.id.chk_head2);


        //@@@@@@@@@@@@@@@@ BRAKE LIGHTS FindviewbyId
        brake_pic1_iv=view.findViewById(R.id.brake_pic1_iv);
        brake_plus1=view.findViewById(R.id.brake_plus1);
        chk_brake1=view.findViewById(R.id.chk_brake1);
        brake_pic2_iv=view.findViewById(R.id.brake_pic2_iv);
        brake_plus2=view.findViewById(R.id.brake_plus2);
        chk_brake2=view.findViewById(R.id.chk_brake2);



        //@@@@@@@@@@@@@@@@ HEADLIGHT FindviewbyId
        headlight_pic1_iv=view.findViewById(R.id.headlight_pic1_iv);
        headlight_plus1=view.findViewById(R.id.headlight_plus1);
        chk_headlight1=view.findViewById(R.id.chk_headlight1);
        headlight_pic2_iv=view.findViewById(R.id.headlight_pic2_iv);
        headlight_plus2=view.findViewById(R.id.headlight_plus2);
        chk_headlight2=view.findViewById(R.id.chk_headlight2);


        //@@@@@@@@@@@@@@@@ OTHERS FindviewbyId
        others_pic1_iv=view.findViewById(R.id.others_pic1_iv);
        others_pic2_iv=view.findViewById(R.id.others_pic2_iv);
        others_plus1=view.findViewById(R.id.others_plus1);
        others_plus2=view.findViewById(R.id.others_plus2);
        chk_others1=view.findViewById(R.id.chk_others1);
        chk_others2=view.findViewById(R.id.chk_others2);



        //HEAD LAMPS Checkbox1 Condition--------------------------------------------------->
        chk_head1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!chk_head1.isChecked()){
                    chk_head1.setChecked(false);
                    chk_head1.setVisibility(View.GONE);
                    head_pic1_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    head_pic1_iv.setEnabled(true);
                    head_plus1.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_head1.setChecked(true);
                    chk_head1.setVisibility(View.VISIBLE);
                    head_pic1_iv.setEnabled(false);
                    head_plus1.setVisibility(View.GONE);
                }

            }
        });



        //HEAD LAMPS Checkbox2 Condition--------------------------------------------------->
        chk_head2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(!chk_head2.isChecked()){
                    chk_head2.setChecked(false);
                    chk_head2.setVisibility(View.GONE);
                    head_pic2_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    head_pic2_iv.setEnabled(true);
                    head_plus2.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_head2.setChecked(true);
                    chk_head2.setVisibility(View.VISIBLE);
                    head_pic2_iv.setEnabled(false);
                    head_plus2.setVisibility(View.GONE);
                }
            }
        });





        //BRAKE LIGHTS Checkbox1 Condition--------------------------------------------------->
        chk_brake1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(!chk_brake1.isChecked()){
                    chk_brake1.setChecked(false);
                    chk_brake1.setVisibility(View.GONE);
                    brake_pic1_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    brake_pic1_iv.setEnabled(true);
                    brake_plus1.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_brake1.setChecked(true);
                    chk_brake1.setVisibility(View.VISIBLE);
                    brake_pic1_iv.setEnabled(false);
                    brake_plus1.setVisibility(View.GONE);
                }
            }
        });




        //BRAKE LIGHTS Checkbox2 Condition--------------------------------------------------->
        chk_brake2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(!chk_brake2.isChecked()){
                    chk_brake2.setChecked(false);
                    chk_brake2.setVisibility(View.GONE);
                    brake_pic2_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    brake_pic2_iv.setEnabled(true);
                    brake_plus2.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_brake2.setChecked(true);
                    chk_brake2.setVisibility(View.VISIBLE);
                    brake_pic2_iv.setEnabled(false);
                    brake_plus2.setVisibility(View.GONE);
                }

            }
        });

        //HEADLIGHT Checkbox1 Condition--------------------------------------------------->
        chk_headlight1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(!chk_headlight1.isChecked()){
                    chk_headlight1.setChecked(false);
                    chk_headlight1.setVisibility(View.GONE);
                    headlight_pic1_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    headlight_pic1_iv.setEnabled(true);
                    head_plus1.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_headlight1.setChecked(true);
                    chk_headlight1.setVisibility(View.VISIBLE);
                    headlight_pic1_iv.setEnabled(false);
                    head_plus1.setVisibility(View.GONE);
                }
            }
        });


        //HEADLIGHT Checkbox2 Condition--------------------------------------------------->
        chk_headlight2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(!chk_headlight2.isChecked()){
                    chk_headlight2.setChecked(false);
                    chk_headlight2.setVisibility(View.GONE);
                    headlight_pic2_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    headlight_pic2_iv.setEnabled(true);
                    head_plus1.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_headlight2.setChecked(true);
                    chk_headlight2.setVisibility(View.VISIBLE);
                    headlight_pic2_iv.setEnabled(false);
                    head_plus2.setVisibility(View.GONE);
                }
            }
        });


        //Others Checkbox1 Condition--------------------------------------------------->
        chk_others1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(!chk_others1.isChecked()){
                    chk_others1.setChecked(false);
                    chk_others1.setVisibility(View.GONE);
                    others_pic1_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    others_pic1_iv.setEnabled(true);
                    others_plus1.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_others1.setChecked(true);
                    chk_others1.setVisibility(View.VISIBLE);
                    others_pic1_iv.setEnabled(false);
                    others_plus1.setVisibility(View.GONE);
                }

            }
        });




        //Others Checkbox2 Condition--------------------------------------------------->
        chk_others2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(!chk_others2.isChecked()){
                    chk_others2.setChecked(false);
                    chk_others2.setVisibility(View.GONE);
                    others_pic2_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    others_pic2_iv.setEnabled(true);
                    others_plus2.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_others2.setChecked(true);
                    chk_others2.setVisibility(View.VISIBLE);
                    others_pic2_iv.setEnabled(false);
                    others_plus2.setVisibility(View.GONE);
                }

            }
        });



        head_pic1_iv.setOnClickListener(this);
        head_pic2_iv.setOnClickListener(this);
        brake_pic1_iv.setOnClickListener(this);
        brake_pic2_iv.setOnClickListener(this);
        headlight_pic1_iv.setOnClickListener(this);
        headlight_pic2_iv.setOnClickListener(this);
        others_pic1_iv.setOnClickListener(this);
        others_pic2_iv.setOnClickListener(this);


        UploadCarParts.upload_part_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                edit = sharedPreferences.edit();
                userid=sharedPreferences.getString("user_id","");
                collect_carmake = sharedPreferences.getString("store_carmake", "");
                collect_caryear=sharedPreferences.getString("store_caryear","");
                collect_carbrand=sharedPreferences.getString("store_carbrand","");
                collect_carmodel=sharedPreferences.getString("store_carmodel","");
                collect_carmileage=sharedPreferences.getString("store_carmileage","");
                collect_carprice=sharedPreferences.getString("store_carprice","");
                collect_carlocation=sharedPreferences.getString("store_carlocation","");
                collect_vinno=sharedPreferences.getString("store_vinno","");

                //head value:
                collect_engine_heads = sharedPreferences.getString("engine_heads", "");
                collect_body_heads=sharedPreferences.getString("body_heads","");
                collect_transmission_heads=sharedPreferences.getString("transmission_heads","");
                collect_interior_heads=sharedPreferences.getString("interior_heads","");
                collect_rimwheel_heads=sharedPreferences.getString("rimwheel_heads","");
                collect_electricals_heads=sharedPreferences.getString("electrical_heads","");
                collect_light_heads=sharedPreferences.getString("lights_heads","");




                Log.e("tag","userId----->"+userid);
                /*Log.e("tag","carmake----->"+collect_carmake);
                Log.e("tag","caryear----->"+collect_caryear);
                Log.e("tag","carbrand---->"+collect_carmake);
                Log.e("tag","carmodel---->"+collect_carmodel);
                Log.e("tag","carmileage-->"+collect_carmileage);
                Log.e("tag","carprice---->"+collect_carprice);
                Log.e("tag","carlocation-->"+collect_carlocation);*/
                Log.e("tag","collect_engine_heads--->"+collect_engine_heads);
                Log.e("tag","collect_body_heads"+collect_body_heads);
                Log.e("tag","collect_transmission_heads-->"+collect_transmission_heads);
                Log.e("tag","collect_interior_heads---->"+collect_interior_heads);
                Log.e("tag","collect_rimwheel_heads-->"+collect_rimwheel_heads);
                Log.e("tag","collect_electricals_heads--->"+collect_electricals_heads);
                Log.e("tag","collect_light_heads---->"+collect_light_heads);
              //  Log.e("tag","print image---------"+ collect_car1);



                //image value:
                collect_car1=sharedPreferences.getString("store_car1","");
                collect_car2=sharedPreferences.getString("store_car2","");
                collect_vin1=sharedPreferences.getString("store_vin1","");
                collect_vin2=sharedPreferences.getString("store_vin2","");


                //get image engine path value:
                path_engine1=sharedPreferences.getString("store_engine1","");
                path_engine2=sharedPreferences.getString("store_engine2","");
                path_ac1=sharedPreferences.getString("store_ac1","");
                path_ac2=sharedPreferences.getString("store_ac2","");
                path_emission1=sharedPreferences.getString("store_emmission1","");
                path_emission2=sharedPreferences.getString("store_emmission2","");
                path_fuel1=sharedPreferences.getString("store_fuel1","");
                path_fuel2=sharedPreferences.getString("store_fuel2","");
                path_engine_other1=sharedPreferences.getString("store_engineother1","");
                path_engine_other2=sharedPreferences.getString("store_engineother2","");


                Log.e("tag","Engine Heading----->"+collect_engine_heads);
                Log.e("tag","Engine 1 ----->"+path_engine1);
                Log.e("tag","Engine 2 ----->"+path_engine2);
                Log.e("tag","Engine 3 ----->"+path_ac1);
                Log.e("tag","Engine 4 ----->"+path_ac2);
                Log.e("tag","Engine 5 ----->"+path_emission1);
                Log.e("tag","Engine 6 ----->"+path_emission2);
                Log.e("tag","Engine 7 ----->"+path_fuel1);
                Log.e("tag","Engine 8 ----->"+path_fuel2);

                //get image Body path value:
                path_doors1=sharedPreferences.getString("store_doors1","");
                path_doors2=sharedPreferences.getString("store_doors2","");
                path_bumpers1=sharedPreferences.getString("store_bumpers1","");
                path_bumpers2=sharedPreferences.getString("store_bumpers2","");
                path_hoods1=sharedPreferences.getString("store_hoods1","");
                path_hoods2=sharedPreferences.getString("store_hoods2","");
                path_body_other1=sharedPreferences.getString("store_bodyother1","");
                path_body_other2=sharedPreferences.getString("store_bodyother2","");


               /* Log.e("tag","Body Heading----->"+collect_body_heads);
                Log.e("tag","Body doors1 ----->"+path_doors1);
                Log.e("tag","Body doors2 ----->"+path_doors2);
                Log.e("tag","Body Bumpers1 ----->"+path_bumpers1);
                Log.e("tag","Body Bumpers2 ----->"+path_bumpers2);
                Log.e("tag","Body Hoods1 ----->"+path_hoods1);
                Log.e("tag","Body Hoods2 ----->"+path_hoods2);
*/

                //get image Transmission path value:
                path_automatic1=sharedPreferences.getString("store_automatic1","");
                path_automatic2=sharedPreferences.getString("store_automatic2","");
                path_manual1=sharedPreferences.getString("store_manual1","");
                path_manual2=sharedPreferences.getString("store_manual2","");
                path_trans_other1=sharedPreferences.getString("store_transother1","");
                path_trans_other2=sharedPreferences.getString("store_transother1","");

                /*Log.e("tag","Transmission Heading----->"+collect_transmission_heads);
                Log.e("tag","Transmission Automatic1 ----->"+path_automatic1);
                Log.e("tag","Transmission Automatic2 ----->"+path_automatic2);
                Log.e("tag","Transmission Manual1 ----->"+path_manual1);
                Log.e("tag","Transmission Manual2 ----->"+path_manual2);*/



                //get image Interior path value:
                path_seat1=sharedPreferences.getString("store_seat1","");
                path_seat2=sharedPreferences.getString("store_seat2","");
                path_steering1=sharedPreferences.getString("store_steering1","");
                path_steering2=sharedPreferences.getString("store_steering2","");
                path_dash1=sharedPreferences.getString("store_dash1","");
                path_dash2=sharedPreferences.getString("store_dash2","");
                path_radio1=sharedPreferences.getString("store_radio1","");
                path_radio2=sharedPreferences.getString("store_radio2","");
                path_interior_other1=sharedPreferences.getString("store_interiorother1","");
                path_interior_other2=sharedPreferences.getString("store_interiorother2","");


               /* Log.e("tag","Interior Heading----->"+collect_body_heads);
                Log.e("tag","Interior seat1 ----->"+path_seat1);
                Log.e("tag","Interior seat2 ----->"+path_seat2);
                Log.e("tag","Interior Steering1 ----->"+path_steering1);
                Log.e("tag","Interior Steering2 ----->"+path_steering2);
                Log.e("tag","Interior Hoods1 ----->"+path_dash1);
                Log.e("tag","Interior Hoods2 ----->"+path_dash2);
                Log.e("tag","Interior Hoods1 ----->"+path_radio1);
                Log.e("tag","Interior Hoods2 ----->"+path_radio2);*/



                //get image RIM WHEEL path value:
                path_brake1=sharedPreferences.getString("store_brake1","");
                path_brake2=sharedPreferences.getString("store_brake2","");
                path_suspension1=sharedPreferences.getString("store_suspension1","");
                path_suspension2=sharedPreferences.getString("store_suspension2","");
                path_rims1=sharedPreferences.getString("store_rims1","");
                path_rims2=sharedPreferences.getString("store_rims2","");
                path_wheels1=sharedPreferences.getString("store_wheel1","");
                path_wheels2=sharedPreferences.getString("store_wheel2","");
                path_wheel_other1=sharedPreferences.getString("store_wheelother1","");
                path_interior_other2=sharedPreferences.getString("store_wheelother1","");



                //get image Electricals path value:
                path_oil1=sharedPreferences.getString("store_oil1","");
                path_oil2=sharedPreferences.getString("store_oil2","");
                path_ignition1=sharedPreferences.getString("store_ignition1","");
                path_ignition2=sharedPreferences.getString("store_ignition2","");
                path_switch1=sharedPreferences.getString("store_switch1","");
                path_switch2=sharedPreferences.getString("store_switch2","");
                path_motor1=sharedPreferences.getString("store_motor1","");
                path_motor2=sharedPreferences.getString("store_motor2","");
                path_electrical_other1=sharedPreferences.getString("store_electrical_other1","");
                path_electrical_other2=sharedPreferences.getString("store_electrical_other2","");




                /*Log.e("tag","Electricals Heading----->"+collect_electricals_heads);
                Log.e("tag","Electricals oil1 ----->"+path_oil1);
                Log.e("tag","Electricals oil2 ----->"+path_oil2);
                Log.e("tag","Electricals cylinder1 ----->"+path_ignition1);
                Log.e("tag","Electricals cylinder2 ----->"+path_ignition2);
                Log.e("tag","Electricals switch1 ----->"+path_switch1);
                Log.e("tag","Electricals switch2 ----->"+path_switch2);
                Log.e("tag","Electricals motor1 ----->"+path_motor1);
                Log.e("tag","Electricals motor2 ----->"+path_motor2);*/




                //get image Lights path value:
                path_rehead1=sharedPreferences.getString("store_headlamp1","");
                path_rehead2=sharedPreferences.getString("store_headlamp2","");
                path_rebrakelig1=sharedPreferences.getString("store_brakelight1","");
                path_rebrakelig2=sharedPreferences.getString("store_brakelight2","");
                path_reheadlight1=sharedPreferences.getString("store_brakelight1","");
                path_reheadlight2=sharedPreferences.getString("store_brakelight1","");
                path_relight_other1=sharedPreferences.getString("store_lightother1","");
                path_relight_other2=sharedPreferences.getString("store_lightother1","");



               /* Log.e("tag","Lights Heading----->"+collect_light_heads);
                Log.e("tag","LIGHTS head1 ----->"+path_rehead1);
                Log.e("tag","LIGHTS head2 ----->"+path_rehead2);
                Log.e("tag","LIGHTS brake1 ----->"+path_rebrakelig1);
                Log.e("tag","LIGHTS brake2 ----->"+path_rebrakelig2);
                Log.e("tag","LIGHTS assembly1 ----->"+path_reheadlight1);
                Log.e("tag","LIGHTS assembly2 ----->"+path_reheadlight2);*/



                if (Utils.Operations.isOnline(getActivity())) {

                    UploadCarParts.upload_part_btn.setEnabled(false);
                    new upload_car_parts().execute();
                }
                else {
                    Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // HEAD LAMPS PIC ONE
            case R.id.head_pic1_iv:

                cameraDialog = new Dialog(getActivity());
                cameraDialog.setContentView(R.layout.camera_dialog);
                lnr_takepic = cameraDialog.findViewById(R.id.lnr_takepic);
                lnr_takegallery = cameraDialog.findViewById(R.id.lnr_takegallery);

                // if button is clicked, close the custom dialog
                lnr_takepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();
                      if(checkPermission()) {
                          Intent intent = new Intent(
                                  MediaStore.ACTION_IMAGE_CAPTURE);
                          File f = new File(android.os.Environment
                                  .getExternalStorageDirectory(), "temp.jpg");
                          intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                  Uri.fromFile(f));
                          startActivityForResult(intent,
                                  TAKE_HEAD_PIC1);
                      }

                    }
                });


                lnr_takegallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();

                        if (checkPermission()) {
                            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            pickIntent.setType("image/*");
                            startActivityForResult(pickIntent, SELECT_HEAD_PIC1);
                        }


                    }
                });
                cameraDialog.show();

                break;


            // HEAD LAMPS PIC TWO
            case R.id.head_pic2_iv:
                cameraDialog = new Dialog(getActivity());
                cameraDialog.setContentView(R.layout.camera_dialog);
                lnr_takepic = cameraDialog.findViewById(R.id.lnr_takepic);
                lnr_takegallery = cameraDialog.findViewById(R.id.lnr_takegallery);

                // if button is clicked, close the custom dialog
                lnr_takepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();
                        if(checkPermission()) {
                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(android.os.Environment
                                    .getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(f));

                            startActivityForResult(intent,
                                    TAKE_HEAD_PIC2);
                        }
                    }
                });


                lnr_takegallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();

                        if (checkPermission()) {
                            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            pickIntent.setType("image/*");
                            startActivityForResult(pickIntent, SELECT_HEAD_PIC2);
                        }
                    }
                });
                cameraDialog.show();
                break;







            // BRAKE PIC ONE
            case R.id.brake_pic1_iv:
                cameraDialog = new Dialog(getActivity());
                cameraDialog.setContentView(R.layout.camera_dialog);
                lnr_takepic = cameraDialog.findViewById(R.id.lnr_takepic);
                lnr_takegallery = cameraDialog.findViewById(R.id.lnr_takegallery);

                // if button is clicked, close the custom dialog
                lnr_takepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();
                        if(checkPermission()) {
                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(android.os.Environment
                                    .getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(f));

                            startActivityForResult(intent,
                                    TAKE_BRAKE_PIC1);
                        }
                    }
                });


                lnr_takegallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();

                        if (checkPermission()) {
                            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            pickIntent.setType("image/*");
                            startActivityForResult(pickIntent, SELECT_BRAKE_PIC1);
                        }
                    }
                });
                cameraDialog.show();

                break;






            // BRAKE PIC TWO
            case R.id.brake_pic2_iv:
                cameraDialog = new Dialog(getActivity());
                cameraDialog.setContentView(R.layout.camera_dialog);
                lnr_takepic = cameraDialog.findViewById(R.id.lnr_takepic);
                lnr_takegallery = cameraDialog.findViewById(R.id.lnr_takegallery);

                // if button is clicked, close the custom dialog
                lnr_takepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();
                        if(checkPermission()) {
                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(android.os.Environment
                                    .getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(f));

                            startActivityForResult(intent,
                                    TAKE_BRAKE_PIC2);
                        }
                    }
                });


                lnr_takegallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();

                        if (checkPermission()) {
                            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            pickIntent.setType("image/*");
                            startActivityForResult(pickIntent, SELECT_BRAKE_PIC2);
                        }
                    }
                });
                cameraDialog.show();

                break;






            // HEADLIGHTS PIC ONE
            case R.id.headlight_pic1_iv:
                cameraDialog = new Dialog(getActivity());
                cameraDialog.setContentView(R.layout.camera_dialog);
                lnr_takepic = cameraDialog.findViewById(R.id.lnr_takepic);
                lnr_takegallery = cameraDialog.findViewById(R.id.lnr_takegallery);

                // if button is clicked, close the custom dialog
                lnr_takepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();
                        if(checkPermission()) {
                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(android.os.Environment
                                    .getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(f));

                            startActivityForResult(intent,
                                    TAKE_HEADLIGHT_PIC1);
                        }
                    }
                });


                lnr_takegallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();

                        if (checkPermission()) {
                            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            pickIntent.setType("image/*");
                            startActivityForResult(pickIntent, SELECT_HEADLIGHT_PIC1);
                        }


                    }
                });
                cameraDialog.show();

                break;





            // HEADLIGHTS PIC TWO
            case R.id.headlight_pic2_iv:
                cameraDialog = new Dialog(getActivity());
                cameraDialog.setContentView(R.layout.camera_dialog);
                lnr_takepic = cameraDialog.findViewById(R.id.lnr_takepic);
                lnr_takegallery = cameraDialog.findViewById(R.id.lnr_takegallery);

                // if button is clicked, close the custom dialog
                lnr_takepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();
                        if(checkPermission()) {
                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(android.os.Environment
                                    .getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(f));

                            startActivityForResult(intent,
                                    TAKE_HEADLIGHT_PIC2);
                        }
                    }
                });


                lnr_takegallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();

                        if (checkPermission()) {
                            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            pickIntent.setType("image/*");
                            startActivityForResult(pickIntent, SELECT_HEADLIGHT_PIC2);
                        }

                    }
                });
                cameraDialog.show();

                break;


            // OTHERS PIC ONE
            case R.id.others_pic1_iv:
                cameraDialog = new Dialog(getActivity());
                cameraDialog.setContentView(R.layout.camera_dialog);
                lnr_takepic = cameraDialog.findViewById(R.id.lnr_takepic);
                lnr_takegallery = cameraDialog.findViewById(R.id.lnr_takegallery);

                // if button is clicked, close the custom dialog
                lnr_takepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();
                        if(checkPermission()) {
                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(android.os.Environment
                                    .getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(f));

                            startActivityForResult(intent,
                                    TAKE_OTHERS_PIC1);
                        }
                    }
                });


                lnr_takegallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();

                        if (checkPermission()) {
                            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            pickIntent.setType("image/*");
                            startActivityForResult(pickIntent, SELECT_OTHERS_PIC1);
                        }
                    }
                });
                cameraDialog.show();
                break;



            // OTHERS PIC TWO
            case R.id.others_pic2_iv:
                cameraDialog = new Dialog(getActivity());
                cameraDialog.setContentView(R.layout.camera_dialog);
                lnr_takepic = cameraDialog.findViewById(R.id.lnr_takepic);
                lnr_takegallery = cameraDialog.findViewById(R.id.lnr_takegallery);

                // if button is clicked, close the custom dialog
                lnr_takepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();
                        if(checkPermission()) {
                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(android.os.Environment
                                    .getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(f));
                            startActivityForResult(intent,
                                    TAKE_OTHERS_PIC2);
                        }
                    }
                });


                lnr_takegallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();

                        if (checkPermission()) {
                            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            pickIntent.setType("image/*");
                            startActivityForResult(pickIntent, SELECT_OTHERS_PIC2);
                        }
                    }
                });
                cameraDialog.show();
                break;

            default:
                break;
        }
    }




    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if ((result == PackageManager.PERMISSION_GRANTED) && (result1 == PackageManager.PERMISSION_GRANTED) && (result2 == PackageManager.PERMISSION_GRANTED)) {
            return true;
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
            return false;

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = null;
        selectedImagePath = null;

        //********************HEAD LAMPS PIC 1 -------TAKE
        if (resultCode == RESULT_OK && requestCode == TAKE_HEAD_PIC1) {
            File f = new File(Environment.getExternalStorageDirectory()
                    .toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }

            if (!f.exists()) {
                return;
            }

            try {

                path_head1=f.getAbsolutePath();
                edit.putString("store_headlamp1",path_head1);
                edit.commit();


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }





        //********************HEAD LAMPS PIC 2 -------TAKE
        else if (resultCode == RESULT_OK && requestCode == TAKE_HEAD_PIC2){
            File f = new File(Environment.getExternalStorageDirectory()
                    .toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }

            if (!f.exists()) {
                Toast.makeText(getActivity(),
                        "Error while capturing image", Toast.LENGTH_LONG).show();
                return;
            }

            try {

                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
                int rotate = 0;
                try {
                    ExifInterface exif = new ExifInterface(f.getAbsolutePath());
                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotate = 270;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotate = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotate = 90;
                            break;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);
                head_pic2_iv.setImageBitmap(bitmap);
                chk_head2.setChecked(true);
                chk_head2.setVisibility(View.VISIBLE);
                head_plus2.setVisibility(View.GONE);

                path_head2=f.getAbsolutePath();
                edit.putString("store_headlamp2", path_head2);
                edit.commit();

                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



        }








        //********************HEAD LAMPS PIC 1 -------SELECT

        else if (resultCode == RESULT_OK && requestCode == SELECT_HEAD_PIC1){


            try {
                Uri selectedMediaUri = data.getData();
                path_head1 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    head_pic1_iv.setImageBitmap(bitmap);
                    chk_head1.setChecked(true);
                    chk_head1.setVisibility(View.VISIBLE);
                    head_plus1.setVisibility(View.GONE);

                    edit.putString("store_headlamp1", path_head1);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }


        }








        //********************HEAD LAMPS PIC 2 -------TAKE
        else if (resultCode == RESULT_OK && requestCode == SELECT_HEAD_PIC2){
            try {
                Uri selectedMediaUri = data.getData();
                path_head2 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    head_pic2_iv.setImageBitmap(bitmap);
                    chk_head2.setChecked(true);
                    chk_head2.setVisibility(View.VISIBLE);
                    head_plus2.setVisibility(View.GONE);

                    edit.putString("store_headlamp2", path_head2);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }
        }







        //********************BRAKE PIC 1 -------TAKE

        if (resultCode == RESULT_OK && requestCode == TAKE_BRAKE_PIC1) {

            File f = new File(Environment.getExternalStorageDirectory()
                    .toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }

            if (!f.exists()) {
                Toast.makeText(getActivity(),
                        "Error while capturing image", Toast.LENGTH_LONG).show();
                return;
            }

            try {

                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
                int rotate = 0;
                try {
                    ExifInterface exif = new ExifInterface(f.getAbsolutePath());
                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotate = 270;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotate = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotate = 90;
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);
                brake_pic1_iv.setImageBitmap(bitmap);
                chk_brake1.setChecked(true);
                chk_brake1.setVisibility(View.VISIBLE);
                brake_plus1.setVisibility(View.GONE);


                path_brakelig1=f.getAbsolutePath();
                edit.putString("store_brakelight1", path_brakelig1);
                edit.commit();

                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }




        }








        //********************BRAKE PIC 2 -------TAKE
        else if (resultCode == RESULT_OK && requestCode == TAKE_BRAKE_PIC2){

            File f = new File(Environment.getExternalStorageDirectory()
                    .toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }

            if (!f.exists()) {
                Toast.makeText(getActivity(),
                        "Error while capturing image", Toast.LENGTH_LONG).show();
                return;
            }

            try {

                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
                int rotate = 0;
                try {
                    ExifInterface exif = new ExifInterface(f.getAbsolutePath());
                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotate = 270;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotate = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotate = 90;
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);
                brake_pic2_iv.setImageBitmap(bitmap);
                chk_brake2.setChecked(true);
                chk_brake2.setVisibility(View.VISIBLE);
                brake_plus2.setVisibility(View.GONE);


                path_brakelig2=f.getAbsolutePath();
                edit.putString("store_brakelight2", path_brakelig2);
                edit.commit();

                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



        }









        //********************BRAKE PIC 1 -------SELECT
        else if (resultCode == RESULT_OK && requestCode == SELECT_BRAKE_PIC1){


            try {
                Uri selectedMediaUri = data.getData();
                path_brakelig1 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    brake_pic1_iv.setImageBitmap(bitmap);
                    chk_brake1.setChecked(true);
                    chk_brake1.setVisibility(View.VISIBLE);
                    brake_plus1.setVisibility(View.GONE);

                    edit.putString("store_brakelight1", path_brakelig1);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }


        }








        //********************BRAKE PIC 2 -------SELECT
        else if (resultCode == RESULT_OK && requestCode == SELECT_BRAKE_PIC2){
            try {
                Uri selectedMediaUri = data.getData();
                path_brakelig2 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    brake_pic2_iv.setImageBitmap(bitmap);
                    chk_brake2.setChecked(true);
                    chk_brake2.setVisibility(View.VISIBLE);
                    brake_plus2.setVisibility(View.GONE);

                    edit.putString("store_brakelight2", path_brakelig2);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }
        }







        //********************HEAD LIGHT PIC 1 -------TAKE
        if (resultCode == RESULT_OK && requestCode == TAKE_HEADLIGHT_PIC1) {

            File f = new File(Environment.getExternalStorageDirectory()
                    .toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }

            if (!f.exists()) {
                Toast.makeText(getActivity(),
                        "Error while capturing image", Toast.LENGTH_LONG).show();
                return;
            }

            try {

                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
                int rotate = 0;
                try {
                    ExifInterface exif = new ExifInterface(f.getAbsolutePath());
                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotate = 270;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotate = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotate = 90;
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);

                headlight_pic1_iv.setImageBitmap(bitmap);
                chk_headlight1.setChecked(true);
                chk_headlight1.setVisibility(View.VISIBLE);
                head_plus1.setVisibility(View.GONE);

                path_headlight1=f.getAbsolutePath();
                edit.putString("store_headlight1", path_headlight1);
                edit.commit();


                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }






        //********************HEAD LIGHT PIC 2 -------TAKE
        if (resultCode == RESULT_OK && requestCode == TAKE_HEADLIGHT_PIC2) {

            File f = new File(Environment.getExternalStorageDirectory()
                    .toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }

            if (!f.exists()) {
                Toast.makeText(getActivity(),
                        "Error while capturing image", Toast.LENGTH_LONG).show();
                return;
            }

            try {

                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
                int rotate = 0;
                try {
                    ExifInterface exif = new ExifInterface(f.getAbsolutePath());
                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotate = 270;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotate = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotate = 90;
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);


                headlight_pic2_iv.setImageBitmap(bitmap);
                chk_headlight2.setChecked(true);
                chk_headlight2.setVisibility(View.VISIBLE);
                head_plus2.setVisibility(View.GONE);


                path_headlight2=f.getAbsolutePath();
                edit.putString("store_headlight2", path_headlight2);
                edit.commit();

                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }




        }






        //********************HEAD LIGHT PIC 1 -------SELECT
        else if (resultCode == RESULT_OK && requestCode == SELECT_HEADLIGHT_PIC1){


            try {
                Uri selectedMediaUri = data.getData();
                path_headlight1 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    headlight_pic1_iv.setImageBitmap(bitmap);
                    chk_headlight1.setChecked(true);
                    chk_headlight1.setVisibility(View.VISIBLE);
                    head_plus1.setVisibility(View.GONE);

                    edit.putString("store_headlight1", path_headlight1);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }
        }








        //********************HEAD LIGHT PIC 2 -------SELECT
        else if (resultCode == RESULT_OK && requestCode == SELECT_HEADLIGHT_PIC2){
            try {
                Uri selectedMediaUri = data.getData();
                path_headlight2 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    headlight_pic2_iv.setImageBitmap(bitmap);
                    chk_headlight2.setChecked(true);
                    chk_headlight2.setVisibility(View.VISIBLE);
                    head_plus2.setVisibility(View.GONE);

                    edit.putString("store_headlight2", path_headlight2);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }
        }



        //********************OTHERS PIC 1 -------TAKE
        if (resultCode == RESULT_OK && requestCode == TAKE_OTHERS_PIC1) {

            File f = new File(Environment.getExternalStorageDirectory()
                    .toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }

            if (!f.exists()) {
                Toast.makeText(getActivity(),
                        "Error while capturing image", Toast.LENGTH_LONG).show();
                return;
            }

            try {

                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
                int rotate = 0;
                try {
                    ExifInterface exif = new ExifInterface(f.getAbsolutePath());
                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotate = 270;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotate = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotate = 90;
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);
                others_pic1_iv.setImageBitmap(bitmap);
                chk_others1.setChecked(true);
                chk_others1.setVisibility(View.VISIBLE);
                others_plus1.setVisibility(View.GONE);



                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }






        //********************OTHERS PIC 2 -------TAKE
        if (resultCode == RESULT_OK && requestCode == TAKE_OTHERS_PIC2) {

            File f = new File(Environment.getExternalStorageDirectory()
                    .toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }

            if (!f.exists()) {
                Toast.makeText(getActivity(),
                        "Error while capturing image", Toast.LENGTH_LONG).show();
                return;
            }

            try {

                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
                int rotate = 0;
                try {
                    ExifInterface exif = new ExifInterface(f.getAbsolutePath());
                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotate = 270;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotate = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotate = 90;
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);


                others_pic2_iv.setImageBitmap(bitmap);
                chk_others2.setChecked(true);
                chk_others2.setVisibility(View.VISIBLE);
                others_plus2.setVisibility(View.GONE);



                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }






        //********************OTHERS PIC 1 -------SELECT
        else if (resultCode == RESULT_OK && requestCode == SELECT_OTHERS_PIC1){

            try {
                Uri selectedMediaUri = data.getData();
                path_light_other1 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    others_pic1_iv.setImageBitmap(bitmap);
                    chk_others1.setChecked(true);
                    chk_others1.setVisibility(View.VISIBLE);
                    others_plus1.setVisibility(View.GONE);

                    edit.putString("store_lightother1", path_light_other1);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }

        }



        //********************OTHERS PIC 2 -------SELECT
        else if (resultCode == RESULT_OK && requestCode == SELECT_OTHERS_PIC2){
            try {
                Uri selectedMediaUri = data.getData();
                path_light_other2 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    others_pic2_iv.setImageBitmap(bitmap);
                    chk_others2.setChecked(true);
                    chk_others2.setVisibility(View.VISIBLE);
                    others_plus2.setVisibility(View.GONE);

                    edit.putString("store_lightother2", path_light_other2);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }
        }




    }




    public class upload_car_parts extends AsyncTask<String, Void, String> {

        private ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Car Parts Uploaded..., please Wait.");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String json = "", jsonStr = "";
            try {
                String responseString = null;
                HttpClient httpclient = new DefaultHttpClient();
                //HttpPost httppost = new HttpPost("http://192.168.1.9/autoparts360/api/add_car_details");
                HttpPost httppost = new HttpPost("http://104.197.80.225/autoparts360/api/add_car_details");
                httppost.setHeader("user-id", userid);
                httppost.setHeader("car-make", collect_carmake);
                httppost.setHeader("car-brand", collect_carbrand);
                httppost.setHeader("car-model", collect_carmodel);
                httppost.setHeader("car-year", collect_caryear);
                httppost.setHeader("car-price","$"+collect_carprice);
                httppost.setHeader("car-location", collect_carlocation);
                httppost.setHeader("car-vin-no",collect_vinno);
                httppost.setHeader("car-mileage", collect_carmileage);
                //httppost.setHeader("engine", collect_engine_heads);
                httppost.setHeader("body", collect_body_heads);
                httppost.setHeader("transmission", collect_transmission_heads);
                httppost.setHeader("interior",collect_interior_heads);
                httppost.setHeader("wheel", collect_rimwheel_heads);
                httppost.setHeader("electrical", collect_electricals_heads);
                //httppost.setHeader("light", collect_light_heads);
                HttpResponse response = null;
                HttpEntity r_entity = null;


                MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

                //Send main page images: Testing OK for server
                if(!collect_car1.equals(""))
                {
                    entity.addPart("car_image[]", new FileBody(new File(collect_car1), "image/jpeg"));
                }
                if(!collect_car2.equals(""))
                {
                    entity.addPart("car_image[]", new FileBody(new File(collect_car2), "image/jpeg"));
                }

                if(!collect_vin1.equals(""))
                {
                    entity.addPart("car_vin_image[]", new FileBody(new File(collect_vin1), "image/jpeg"));
                }

                if(!collect_vin2.equals(""))
                {
                    entity.addPart("car_vin_image[]", new FileBody(new File(collect_vin2), "image/jpeg"));
                }








/*




*/








                // upload Body tab: Testing OK for local    ************************************* 2
                if(!path_doors1.equals(""))
                {
                    entity.addPart("doors[]", new FileBody(new File(path_doors1), "image/jpeg"));
                }
                if(!path_doors2.equals(""))
                {
                    entity.addPart("doors[]", new FileBody(new File(path_doors2), "image/jpeg"));
                }
                if(!path_bumpers1.equals(""))
                {
                    entity.addPart("bumpers[]", new FileBody(new File(path_bumpers1), "image/jpeg"));
                }
                if(!path_bumpers2.equals(""))
                {
                    entity.addPart("bumpers[]", new FileBody(new File(path_bumpers2), "image/jpeg"));
                }
                if(!path_hoods1.equals(""))
                {
                    entity.addPart("hoods[]", new FileBody(new File(path_hoods1), "image/jpeg"));
                }
                if(!path_hoods2.equals(""))
                {
                    entity.addPart("hoods[]", new FileBody(new File(path_hoods2), "image/jpeg"));
                }



                //upload Transmission Tab:  Testing OK for local    *************************************  3
                if(!path_automatic1.equals(""))
                {
                    entity.addPart("automatic[]", new FileBody(new File(path_automatic1), "image/jpeg"));
                }
                if(!path_automatic2.equals(""))
                {
                    entity.addPart("automatic[]", new FileBody(new File(path_automatic2), "image/jpeg"));
                }
                if(!path_manual1.equals(""))
                {
                    entity.addPart("manual[]", new FileBody(new File(path_manual1), "image/jpeg"));
                }
                if(!path_manual2.equals(""))
                {
                    entity.addPart("manual[]", new FileBody(new File(path_manual2), "image/jpeg"));
                }








                //upload Interior tab: Testing OK for local     *************************************  4

                if(!path_seat1.equals(""))
                {
                    entity.addPart("seats[]", new FileBody(new File(path_seat1), "image/jpeg"));
                }
                if(!path_seat2.equals(""))
                {
                    entity.addPart("seats[]", new FileBody(new File(path_seat2), "image/jpeg"));
                }
                if(!path_steering1.equals(""))
                {
                    entity.addPart("steering[]", new FileBody(new File(path_steering1), "image/jpeg"));
                }
                if(!path_steering2.equals(""))
                {
                    entity.addPart("steering[]", new FileBody(new File(path_steering2), "image/jpeg"));
                }
                if(!path_dash1.equals(""))
                {
                    entity.addPart("dashboard[]", new FileBody(new File(path_dash1), "image/jpeg"));
                }
                if(!path_dash2.equals(""))
                {
                    entity.addPart("dashboard[]", new FileBody(new File(path_dash2), "image/jpeg"));
                }
                if(!path_radio1.equals(""))
                {
                    entity.addPart("radios[]", new FileBody(new File(path_radio1), "image/jpeg"));
                }
                if(!path_radio2.equals(""))
                {
                    entity.addPart("radios[]", new FileBody(new File(path_radio2), "image/jpeg"));
                }








                //upload Wheel tab:  Testing OK for local    *************************************   5
                if(!path_brake1.equals(""))
                {
                    entity.addPart("brake[]", new FileBody(new File(path_brake1), "image/jpeg"));
                }
                if(!path_brake2.equals(""))
                {
                    entity.addPart("brake[]", new FileBody(new File(path_brake2), "image/jpeg"));
                }
                if(!path_suspension1.equals(""))
                {
                    entity.addPart("suspensions[]", new FileBody(new File(path_suspension1), "image/jpeg"));
                }
                if(!path_suspension2.equals(""))
                {
                    entity.addPart("suspensions[]", new FileBody(new File(path_suspension2), "image/jpeg"));
                }
                if(!path_rims1.equals(""))
                {
                    entity.addPart("rims[]", new FileBody(new File(path_rims1), "image/jpeg"));
                }
                if(!path_rims2.equals(""))
                {
                    entity.addPart("rims[]", new FileBody(new File(path_rims2), "image/jpeg"));
                }
                if(!path_wheels1.equals(""))
                {
                    entity.addPart("wheel[]", new FileBody(new File(path_wheels1), "image/jpeg"));
                }
                if(!path_wheels2.equals(""))
                {
                    entity.addPart("wheel[]", new FileBody(new File(path_wheels2), "image/jpeg"));
                }





                //upload Electricals tab:  Testing OK for Server    *************************************   6

                if(!path_oil1.equals(""))
                {
                    entity.addPart("oil_pressure_switch[]", new FileBody(new File(path_oil1), "image/jpeg"));
                }
                if(!path_oil2.equals(""))
                {
                    entity.addPart("oil_pressure_switch[]", new FileBody(new File(path_oil2), "image/jpeg"));
                }
                if(!path_ignition1.equals(""))
                {
                    entity.addPart("ignition_lock_cylinder[]", new FileBody(new File(path_ignition1), "image/jpeg"));
                }
                if(!path_ignition2.equals(""))
                {
                    entity.addPart("ignition_lock_cylinder[]", new FileBody(new File(path_ignition2), "image/jpeg"));
                }
                if(!path_switch1.equals(""))
                {
                    entity.addPart("ignition_switch[]", new FileBody(new File(path_switch1), "image/jpeg"));
                }
                if(!path_switch2.equals(""))
                {
                    entity.addPart("ignition_switch[]", new FileBody(new File(path_switch2), "image/jpeg"));
                }
                if(!path_motor1.equals(""))
                {
                    entity.addPart("window_lift_motor[]", new FileBody(new File(path_motor1), "image/jpeg"));
                }
                if(!path_motor2.equals(""))
                {
                    entity.addPart("window_lift_motor[]", new FileBody(new File(path_motor2), "image/jpeg"));
                }





               //upload Lights tab:  Testing NOT OK for local    ************************************* 7
                if(!path_rehead1.equals(""))
                {
                    entity.addPart("head_lamps[]", new FileBody(new File(path_rehead1), "image/jpeg"));
                }
                if(!path_rehead2.equals(""))
                {
                    entity.addPart("head_lamps[]", new FileBody(new File(path_rehead2), "image/jpeg"));
                }
                if(!path_rebrakelig1.equals(""))
                {
                    entity.addPart("brake_lights[]", new FileBody(new File(path_rebrakelig1), "image/jpeg"));
                }
                if(!path_rebrakelig2.equals(""))
                {
                    entity.addPart("brake_lights[]", new FileBody(new File(path_rebrakelig2), "image/jpeg"));
                }
                if(!path_reheadlight1.equals(""))
                {
                    entity.addPart("headlight_assembly[]", new FileBody(new File(path_reheadlight1), "image/jpeg"));
                }
                if(!path_reheadlight2.equals(""))
                {
                    entity.addPart("headlight_assembly[]", new FileBody(new File(path_reheadlight2), "image/jpeg"));
                }







                httppost.setEntity(entity);
                try {
                    try {
                        response = httpclient.execute(httppost);
                    } catch (Exception e) {
                        Log.e("tag", "ds:" + e.toString());
                    }
                    try {
                        r_entity = response.getEntity();
                    } catch (Exception e) {
                        Log.e("tag", "dsa:" + e.toString());
                    }

                    int statusCode = response.getStatusLine().getStatusCode();
                    Log.e("tag", response.getStatusLine().toString());
                    if (statusCode == 200) {
                        responseString = EntityUtils.toString(r_entity);
                        Log.e("tag", "rssss" + responseString);
                        // return success;

                    } else {
                        responseString = "Error occurred! Http Status Code: "
                                + statusCode;
                        Log.e("tag3", responseString);
                    }
                } catch (ClientProtocolException e) {
                    responseString = e.toString();
                    Log.e("tag44", responseString);
                } catch (IOException e) {
                    responseString = e.toString();
                    Log.e("tag45", responseString);
                }
                return responseString;
            } catch (Exception e) {
                Log.e("tag_InputStream0", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("tag","print response==>"+s);


            if (dialog.isShowing())
            {
                dialog.dismiss();
                edit.remove("store_carmake");
                edit.remove("store_caryear");
                edit.remove("store_carbrand");
                edit.remove("store_carmodel");
                edit.remove("store_carmileage");
                edit.remove("store_carprice");
                edit.remove("store_carlocation");
                edit.remove("store_vinno");
                edit.remove("engine_heads");
                edit.remove("body_heads");
                edit.remove("transmission_heads");
                edit.remove("interior_heads");
                edit.remove("electrical_heads");
                edit.remove("lights_heads");
                edit.remove("store_car1");
                edit.remove("store_car2");
                edit.remove("store_vin1");
                edit.remove("store_vin2");


                //remove all headings:
                edit.remove("engine_heads");
                edit.remove("body_heads");
                edit.remove("transmission_heads");
                edit.remove("interior_heads");
                edit.remove("rimwheel_heads");
                edit.remove("electrical_heads");
                edit.remove("lights_heads");

                //remove engine fields:
                edit.remove("store_engine1");
                edit.remove("store_engine2");
                edit.remove("store_ac1");
                edit.remove("store_ac2");
                edit.remove("store_emmission1");
                edit.remove("store_emmission2");
                edit.remove("store_fuel1");
                edit.remove("store_fuel2");
                edit.remove("store_engineother1");
                edit.remove("store_engineother2");

                //remove body fields:
                edit.remove("store_doors1");
                edit.remove("store_doors2");
                edit.remove("store_bumpers1");
                edit.remove("store_bumpers2");
                edit.remove("store_hoods1");
                edit.remove("store_hoods2");
                edit.remove("store_bodyother1");
                edit.remove("store_bodyother2");

                //remove transmission fields:
                edit.remove("store_automatic1");
                edit.remove("store_automatic2");
                edit.remove("store_manual1");
                edit.remove("store_manual2");
                edit.remove("store_transother1");
                edit.remove("store_transother1");

                //remove Interior fields:
                edit.remove("store_seat1");
                edit.remove("store_seat2");
                edit.remove("store_steering1");
                edit.remove("store_steering2");
                edit.remove("store_dash1");
                edit.remove("store_dash2");
                edit.remove("store_radio1");
                edit.remove("store_radio2");
                edit.remove("store_interiorother1");
                edit.remove("store_interiorother2");

                //remove RIM/Wheel fields:
                edit.remove("store_brake1");
                edit.remove("store_brake2");
                edit.remove("store_suspension1");
                edit.remove("store_suspension2");
                edit.remove("store_rims1");
                edit.remove("store_rims2");
                edit.remove("store_wheel1");
                edit.remove("store_wheel2");
                edit.remove("store_wheelother1");
                edit.remove("store_wheelother1");

                //remove Electricals fields:
                edit.remove("store_oil1");
                edit.remove("store_oil2");
                edit.remove("store_ignition1");
                edit.remove("store_ignition2");
                edit.remove("store_switch1");
                edit.remove("store_switch2");
                edit.remove("path_motor1");
                edit.remove("path_motor2");
                edit.remove("store_electrical_other1");
                edit.remove("store_electrical_other2");


                //remove Lights fields:
                edit.remove("store_headlamp1");
                edit.remove("store_headlamp2");
                edit.remove("store_brakelight1");
                edit.remove("store_brakelight2");
                edit.remove("store_headlight1");
                edit.remove("store_headlight2");
                edit.remove("store_lightother1");
                edit.remove("store_lightother2");
                edit.commit();
            }

            try {
                if (s.length() > 0) {
                    try {
                        JSONObject jo = new JSONObject(s.toString());
                        String success = jo.getString("status");
                        Log.e("tag","print g1==>"+success);

                        String message=jo.getString("message");

                        if (success.equals("true")) {
                            Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getActivity(), DashboardActivity.class);
                            startActivity(i);

                        } else {
                            Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e)
            {
            }
        }
    }




//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ SEND HEADER AND IMAGE IN MULTIPART @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    /*public class PostMethodDemo extends AsyncTask<String, Void, Boolean> {
//        String server_response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

//            Toast.makeText(getApplicationContext(), "error",
//            Toast.LENGTH_LONG).show();


        }

        @Override
        protected Boolean doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection = null;

            try {

//                 url = new URL("http://10.0.14.22:8080/gavs/api/print/card");
                url = new URL("http://192.168.1.18/autolane360/api/add_car_details");
                //url = new URL("http://192.168.1.143:8081/gavs/api/print/card");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());


                //uploadBitmap(photo);
                try {


                    JSONObject obj = new JSONObject();






                    obj.put("car-make", collect_carmake);
                    obj.put("car-brand", collect_carbrand);
                    obj.put("car-year", collect_caryear);
                    obj.put("car-location", collect_carlocation);
                    obj.put("car-vin-no", collect_vinno);
                    obj.put("car-mileage", collect_carmileage);
                    obj.put("engine", collect_engine_heads);
                    obj.put("body", collect_body_heads);
                    obj.put("transmission", collect_transmission_heads);

                    obj.put("interior", collect_interior_heads);
                    obj.put("wheel", collect_rimwheel_heads);
                    obj.put("electrical", collect_electricals_heads);
                    obj.put("light", collect_light_heads);
                    obj.put("car_image[]", collect_enginecar1);
                    obj.put("car_vin_image[]", collect_enginecar1);
                    obj.put("engine_pics[]", collect_enginecar1);

                    wr.writeBytes(obj.toString());
                    Log.e("JSON Input", obj.toString());
                    wr.flush();
                    wr.close();


                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                urlConnection.connect();

                int responseCode = urlConnection.getResponseCode();


                if (responseCode == HttpURLConnection.HTTP_OK) {

                    return true;
                  *//*  Toast.makeText(getApplicationContext(), "Server connected",
                            Toast.LENGTH_LONG).show();*//*
//                    server_response = readStream(urlConnection.getInputStream());
                }else {

                    Toast.makeText(getActivity(),"Server not connected",Toast.LENGTH_LONG).show();
                    return false;
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("connect", "Error : "+e.getLocalizedMessage(), e);
                Toast.makeText(getActivity(), "Error : "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                return false;

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Error : "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                return false;
            }

        }

        @Override
        protected void onPostExecute(Boolean s) {
            super.onPostExecute(s);


            if(s) {

                Toast.makeText(getActivity(), "Process completed", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                    }
                }, 5000);
            }

        }
    }

    public static String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }*/



/*//upload engine tab:
                if(path_engine1!=null)
    {
        entity.addPart("engine_pics[]", new FileBody(new File(path_engine1), "image/jpeg"));
    }
                if(path_engine2!=null)
    {
        entity.addPart("engine_pics[]", new FileBody(new File(path_engine2), "image/jpeg"));
    }

                if(path_ac1!=null)
    {
        entity.addPart("ac_pics[]", new FileBody(new File(path_ac1), "image/jpeg"));
    }
                if(path_ac2!=null)
    {
        entity.addPart("ac_pics[]", new FileBody(new File(path_ac2), "image/jpeg"));
    }
                if(path_emission1!=null)
    {
        entity.addPart("emmission_exhausts_pics[]", new FileBody(new File(path_emission1), "image/jpeg"));
    }
                if(path_emission2!=null)
    {
        entity.addPart("emmission_exhausts_pics[]", new FileBody(new File(path_emission2), "image/jpeg"));
    }
                if(path_fuel1!=null)
    {
        entity.addPart("fuel_delivery_pics[]", new FileBody(new File(path_fuel1), "image/jpeg"));
    }
                if(path_fuel2!=null) {
        entity.addPart("fuel_delivery_pics[]", new FileBody(new File(path_fuel2), "image/jpeg"));
    }*/
}