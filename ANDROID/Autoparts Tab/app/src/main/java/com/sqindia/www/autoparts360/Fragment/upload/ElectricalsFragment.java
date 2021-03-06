package com.sqindia.www.autoparts360.Fragment.upload;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

import com.sqindia.www.autoparts360.R;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class ElectricalsFragment extends Fragment implements View.OnClickListener{

    ImageView oil_pic1_iv,oil_pic2_iv,ignition_pic1_iv,ignition_pic2_iv,switch_pic1_iv,switch_pic2_iv,window_pic1_iv,window_pic2_iv,others_pic1_iv,others_pic2_iv;
    ImageView oil_plus1,oil_plus2,ignition_plus1,ignition_plus2,switch_plus1,switch_plus2,window_plus1,window_plus2,others_plus1,others_plus2;
    CheckBox chk_oil1,chk_oil2,chk_ignition1,chk_ignition2,chk_switch1,chk_switch2,chk_window1,chk_window2,chk_others1,chk_others2;
    Dialog cameraDialog;
    LinearLayout lnr_takepic, lnr_takegallery;
    Bitmap bitmap;
    String selectedImagePath;
    public static TextView electrical_box1,electrical_box2,electrical_box3,electrical_box4;
    public static EditText electrical_others;

    SharedPreferences sharedPrefces;
    SharedPreferences.Editor edit;
    String path_oil1,path_oil2,path_ignition1,path_ignition2,path_switch1,path_switch2,path_motor1,path_motor2,path_electrical_other1,path_electrical_other2;


    protected static final int TAKE_OIL_PIC1 = 1;
    protected static final int TAKE_OIL_PIC2 = 2;
    protected static final int SELECT_OIL_PIC1 = 3;
    protected static final int SELECT_OIL_PIC2 = 4;


    protected static final int TAKE_IGNITION_PIC1 = 5;
    protected static final int TAKE_IGNITION_PIC2 = 6;
    protected static final int SELECT_IGNITION_PIC1 = 7;
    protected static final int SELECT_IGNITION_PIC2 = 8;

    protected static final int TAKE_SWITCH_PIC1 = 9;
    protected static final int TAKE_SWITCH_PIC2 = 10;
    protected static final int SELECT_SWITCH_PIC1 = 11;
    protected static final int SELECT_SWITCH_PIC2 = 12;


    protected static final int TAKE_WINDOW_PIC1 = 13;
    protected static final int TAKE_WINDOW_PIC2 = 14;
    protected static final int SELECT_WINDOW_PIC1 = 15;
    protected static final int SELECT_WINDOW_PIC2 = 16;


    protected static final int TAKE_OTHERS_PIC1 = 17;
    protected static final int TAKE_OTHERS_PIC2 = 18;
    protected static final int SELECT_OTHERS_PIC1 = 19;
    protected static final int SELECT_OTHERS_PIC2 = 20;


    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_electric, container, false);
        sharedPrefces = PreferenceManager.getDefaultSharedPreferences(getActivity());
        edit = sharedPrefces.edit();



        electrical_box1=view.findViewById(R.id.electrical_box1);
        electrical_box2=view.findViewById(R.id.electrical_box2);
        electrical_box3=view.findViewById(R.id.electrical_box3);
        electrical_box4=view.findViewById(R.id.electrical_box4);
        electrical_others=view.findViewById(R.id.electrical_others);


        //@@@@@@@@@@@@@@@@ OIL FindviewbyId
        oil_pic1_iv=view.findViewById(R.id.oil_pic1_iv);
        oil_pic2_iv=view.findViewById(R.id.oil_pic2_iv);
        oil_plus1=view.findViewById(R.id.oil_plus1);
        oil_plus2=view.findViewById(R.id.oil_plus2);
        chk_oil1=view.findViewById(R.id.chk_oil1);
        chk_oil2=view.findViewById(R.id.chk_oil2);



        //@@@@@@@@@@@@@@@@ IGNITION LOCK FindviewbyId
        ignition_pic1_iv=view.findViewById(R.id.ignition_pic1_iv);
        ignition_plus1=view.findViewById(R.id.ignition_plus1);
        chk_ignition1=view.findViewById(R.id.chk_ignition1);
        ignition_pic2_iv=view.findViewById(R.id.ignition_pic2_iv);
        ignition_plus2=view.findViewById(R.id.ignition_plus2);
        chk_ignition2=view.findViewById(R.id.chk_ignition2);


        //@@@@@@@@@@@@@@@@ IGNITION SWITCH FindviewbyId
        switch_pic1_iv=view.findViewById(R.id.switch_pic1_iv);
        switch_plus1=view.findViewById(R.id.switch_plus1);
        chk_switch1=view.findViewById(R.id.chk_switch1);
        switch_pic2_iv=view.findViewById(R.id.switch_pic2_iv);
        switch_plus2=view.findViewById(R.id.switch_plus2);
        chk_switch2=view.findViewById(R.id.chk_switch2);



        //@@@@@@@@@@@@@@@@ LIFT MOTOR FindviewbyId
        window_pic1_iv=view.findViewById(R.id.window_pic1_iv);
        window_plus1=view.findViewById(R.id.window_plus1);
        chk_window1=view.findViewById(R.id.chk_window1);
        window_pic2_iv=view.findViewById(R.id.window_pic2_iv);
        window_plus2=view.findViewById(R.id.window_plus2);
        chk_window2=view.findViewById(R.id.chk_window2);



        //@@@@@@@@@@@@@@@@ OTHERS FindviewbyId
        others_pic1_iv=view.findViewById(R.id.others_pic1_iv);
        others_pic2_iv=view.findViewById(R.id.others_pic2_iv);
        others_plus1=view.findViewById(R.id.others_plus1);
        others_plus2=view.findViewById(R.id.others_plus2);
        chk_others1=view.findViewById(R.id.chk_others1);
        chk_others2=view.findViewById(R.id.chk_others2);


        oil_pic1_iv.setOnClickListener(this);
        oil_pic2_iv.setOnClickListener(this);
        ignition_pic1_iv.setOnClickListener(this);
        ignition_pic2_iv.setOnClickListener(this);
        switch_pic1_iv.setOnClickListener(this);
        switch_pic2_iv.setOnClickListener(this);
        window_pic1_iv.setOnClickListener(this);
        window_pic2_iv.setOnClickListener(this);
        others_pic1_iv.setOnClickListener(this);
        others_pic2_iv.setOnClickListener(this);


        //OIL PRESSURE Checkbox1 Condition--------------------------------------------------->
        chk_oil1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(!chk_oil1.isChecked()){
                    chk_oil1.setChecked(false);
                    chk_oil1.setVisibility(View.GONE);
                    oil_pic1_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    oil_pic1_iv.setEnabled(true);
                    oil_plus1.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_oil1.setChecked(true);
                    chk_oil1.setVisibility(View.VISIBLE);
                    oil_pic1_iv.setEnabled(false);
                    oil_plus1.setVisibility(View.GONE);
                }
            }
        });



        //OIL PRESSURE Checkbox2 Condition--------------------------------------------------->
        chk_oil2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(!chk_oil2.isChecked()){
                    chk_oil2.setChecked(false);
                    chk_oil2.setVisibility(View.GONE);
                    oil_pic2_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    oil_pic2_iv.setEnabled(true);
                    oil_plus2.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_oil2.setChecked(true);
                    chk_oil2.setVisibility(View.VISIBLE);
                    oil_pic2_iv.setEnabled(false);
                    oil_plus2.setVisibility(View.GONE);
                }
            }
        });




        //IGNITION Checkbox1 Condition--------------------------------------------------->
        chk_ignition1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(!chk_ignition1.isChecked()){
                    chk_ignition1.setChecked(false);
                    chk_ignition1.setVisibility(View.GONE);
                    ignition_pic1_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    ignition_pic1_iv.setEnabled(true);
                    ignition_plus1.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_ignition1.setChecked(true);
                    chk_ignition1.setVisibility(View.VISIBLE);
                    ignition_pic1_iv.setEnabled(false);
                    ignition_plus1.setVisibility(View.GONE);
                }

            }
        });




        //IGNITION Checkbox2 Condition--------------------------------------------------->
        chk_ignition2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(!chk_ignition2.isChecked()){
                    chk_ignition2.setChecked(false);
                    chk_ignition2.setVisibility(View.GONE);
                    ignition_pic2_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    ignition_pic2_iv.setEnabled(true);
                    ignition_plus2.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_ignition2.setChecked(true);
                    chk_ignition2.setVisibility(View.VISIBLE);
                    ignition_pic2_iv.setEnabled(false);
                    ignition_plus2.setVisibility(View.GONE);
                }

            }
        });



        //SWITCH Checkbox1 Condition--------------------------------------------------->
        chk_switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(!chk_switch1.isChecked()){
                    chk_switch1.setChecked(false);
                    chk_switch1.setVisibility(View.GONE);
                    switch_pic1_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    switch_pic1_iv.setEnabled(true);
                    switch_plus1.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_switch1.setChecked(true);
                    chk_switch1.setVisibility(View.VISIBLE);
                    switch_pic1_iv.setEnabled(false);
                    switch_plus1.setVisibility(View.GONE);
                }

            }
        });


        //SWITCH Checkbox2 Condition--------------------------------------------------->
        chk_switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(!chk_switch2.isChecked()){
                    chk_switch2.setChecked(false);
                    chk_switch2.setVisibility(View.GONE);
                    switch_pic2_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    switch_pic2_iv.setEnabled(true);
                    switch_plus2.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_switch2.setChecked(true);
                    chk_switch2.setVisibility(View.VISIBLE);
                    switch_pic2_iv.setEnabled(false);
                    switch_plus2.setVisibility(View.GONE);
                }

            }
        });


        //WINDOW LIFT Checkbox1 Condition--------------------------------------------------->
        chk_window1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!chk_window1.isChecked()){
                    chk_window1.setChecked(false);
                    chk_window1.setVisibility(View.GONE);
                    window_pic1_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    window_pic1_iv.setEnabled(true);
                    window_plus1.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_window1.setChecked(true);
                    chk_window1.setVisibility(View.VISIBLE);
                    window_pic1_iv.setEnabled(false);
                    window_plus1.setVisibility(View.GONE);
                }
            }
        });


        //WINDOW LIFT Checkbox2 Condition--------------------------------------------------->
        chk_window2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!chk_window2.isChecked()){
                    chk_window2.setChecked(false);
                    chk_window2.setVisibility(View.GONE);
                    window_pic2_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    window_pic2_iv.setEnabled(true);
                    window_plus2.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_window2.setChecked(true);
                    chk_window2.setVisibility(View.VISIBLE);
                    window_pic2_iv.setEnabled(false);
                    window_plus2.setVisibility(View.GONE);
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
                else {
                    chk_others2.setChecked(true);
                    chk_others2.setVisibility(View.VISIBLE);
                    others_pic2_iv.setEnabled(false);
                    others_plus2.setVisibility(View.GONE);
                }
            }
        });


        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            // OIL PRESSEURE PIC ONE
            case R.id.oil_pic1_iv:

                cameraDialog = new Dialog(getActivity());
                cameraDialog.setContentView(R.layout.camera_dialog);
                lnr_takepic = cameraDialog.findViewById(R.id.lnr_takepic);
                lnr_takegallery = cameraDialog.findViewById(R.id.lnr_takegallery);

                // if button is clicked, close the custom dialog
                lnr_takepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();

                        if (checkPermission()) {
                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(android.os.Environment
                                    .getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(f));


                            startActivityForResult(intent,
                                    TAKE_OIL_PIC1);
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
                            startActivityForResult(pickIntent, SELECT_OIL_PIC1);
                        }


                    }
                });
                cameraDialog.show();

                break;


            // OIL PRESSEURE PIC TWO
            case R.id.oil_pic2_iv:
                cameraDialog = new Dialog(getActivity());
                cameraDialog.setContentView(R.layout.camera_dialog);
                lnr_takepic = cameraDialog.findViewById(R.id.lnr_takepic);
                lnr_takegallery = cameraDialog.findViewById(R.id.lnr_takegallery);

                // if button is clicked, close the custom dialog
                lnr_takepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();

                        if (checkPermission()) {
                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(android.os.Environment
                                    .getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(f));

                            startActivityForResult(intent,
                                    TAKE_OIL_PIC2);
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
                            startActivityForResult(pickIntent, SELECT_OIL_PIC2);
                        }
                    }
                });
                cameraDialog.show();

                break;







            // IGNITION LOCK PIC ONE
            case R.id.ignition_pic1_iv:
                cameraDialog = new Dialog(getActivity());
                cameraDialog.setContentView(R.layout.camera_dialog);
                lnr_takepic = cameraDialog.findViewById(R.id.lnr_takepic);
                lnr_takegallery = cameraDialog.findViewById(R.id.lnr_takegallery);

                // if button is clicked, close the custom dialog
                lnr_takepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();


                        if (checkPermission()) {
                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(android.os.Environment
                                    .getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(f));

                            startActivityForResult(intent,
                                    TAKE_IGNITION_PIC1);
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
                            startActivityForResult(pickIntent, SELECT_IGNITION_PIC1);
                        }

                    }
                });
                cameraDialog.show();

                break;






            // IGNITION LOCK PIC TWO
            case R.id.ignition_pic2_iv:
                cameraDialog = new Dialog(getActivity());
                cameraDialog.setContentView(R.layout.camera_dialog);
                lnr_takepic = cameraDialog.findViewById(R.id.lnr_takepic);
                lnr_takegallery = cameraDialog.findViewById(R.id.lnr_takegallery);

                // if button is clicked, close the custom dialog
                lnr_takepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();
                        if (checkPermission()) {
                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(android.os.Environment
                                    .getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(f));

                            startActivityForResult(intent,
                                    TAKE_IGNITION_PIC2);
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
                            startActivityForResult(pickIntent, SELECT_IGNITION_PIC2);
                        }

                    }
                });
                cameraDialog.show();
                break;






            // IGNITION SWITCH PIC ONE
            case R.id.switch_pic1_iv:
                cameraDialog = new Dialog(getActivity());
                cameraDialog.setContentView(R.layout.camera_dialog);
                lnr_takepic = cameraDialog.findViewById(R.id.lnr_takepic);
                lnr_takegallery = cameraDialog.findViewById(R.id.lnr_takegallery);

                // if button is clicked, close the custom dialog
                lnr_takepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();
                        if (checkPermission()) {
                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(android.os.Environment
                                    .getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(f));
                            startActivityForResult(intent,
                                    TAKE_SWITCH_PIC1);
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
                            startActivityForResult(pickIntent, SELECT_SWITCH_PIC1);
                        }


                    }
                });
                cameraDialog.show();

                break;





            // IGNITION SWITCH PIC TWO
            case R.id.switch_pic2_iv:
                cameraDialog = new Dialog(getActivity());
                cameraDialog.setContentView(R.layout.camera_dialog);
                lnr_takepic = cameraDialog.findViewById(R.id.lnr_takepic);
                lnr_takegallery = cameraDialog.findViewById(R.id.lnr_takegallery);

                // if button is clicked, close the custom dialog
                lnr_takepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();
                        if (checkPermission()) {
                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(android.os.Environment
                                    .getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(f));

                            startActivityForResult(intent,
                                    TAKE_SWITCH_PIC2);
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
                            startActivityForResult(pickIntent, SELECT_SWITCH_PIC2);
                        }


                    }
                });
                cameraDialog.show();

                break;





            // WINDOWS PIC TWO
            case R.id.window_pic1_iv:
                cameraDialog = new Dialog(getActivity());
                cameraDialog.setContentView(R.layout.camera_dialog);
                lnr_takepic = cameraDialog.findViewById(R.id.lnr_takepic);
                lnr_takegallery = cameraDialog.findViewById(R.id.lnr_takegallery);

                // if button is clicked, close the custom dialog
                lnr_takepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();

                        if (checkPermission()) {
                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(android.os.Environment
                                    .getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(f));
                            startActivityForResult(intent,
                                    TAKE_WINDOW_PIC1);
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
                            startActivityForResult(pickIntent, SELECT_WINDOW_PIC1);
                        }
                    }
                });
                cameraDialog.show();
                break;




            // WINDOWS PIC TWO
            case R.id.window_pic2_iv:
                cameraDialog = new Dialog(getActivity());
                cameraDialog.setContentView(R.layout.camera_dialog);
                lnr_takepic = cameraDialog.findViewById(R.id.lnr_takepic);
                lnr_takegallery = cameraDialog.findViewById(R.id.lnr_takegallery);

                // if button is clicked, close the custom dialog
                lnr_takepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraDialog.dismiss();

                        if (checkPermission()) {
                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(android.os.Environment
                                    .getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(f));

                            startActivityForResult(intent,
                                    TAKE_WINDOW_PIC2);

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
                            startActivityForResult(pickIntent, SELECT_WINDOW_PIC2);
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
                        if (checkPermission()) {
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
                        if (checkPermission()) {
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
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


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

        //********************OIL PIC 1 -------TAKE
        if (resultCode == RESULT_OK && requestCode == TAKE_OIL_PIC1) {

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
                oil_pic1_iv.setImageBitmap(bitmap);
                chk_oil1.setChecked(true);
                chk_oil1.setVisibility(View.VISIBLE);
                oil_plus1.setVisibility(View.GONE);

                path_oil1=f.getAbsolutePath();
                edit.putString("store_oil1",path_oil1);
                edit.commit();


                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }




        }








        //********************OIL PIC 2 -------TAKE
        else if (resultCode == RESULT_OK && requestCode == TAKE_OIL_PIC2){

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
                oil_pic2_iv.setImageBitmap(bitmap);
                chk_oil2.setChecked(true);
                chk_oil2.setVisibility(View.VISIBLE);
                oil_plus2.setVisibility(View.GONE);

                path_oil2=f.getAbsolutePath();
                edit.putString("store_oil2",path_oil2);
                edit.commit();

                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



        }








        //********************OIL PIC 1 -------SELECT

        else if (resultCode == RESULT_OK && requestCode == SELECT_OIL_PIC1){


            try {
                Uri selectedMediaUri = data.getData();
                path_oil1 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    oil_pic1_iv.setImageBitmap(bitmap);
                    chk_oil1.setChecked(true);
                    chk_oil1.setVisibility(View.VISIBLE);
                    oil_plus1.setVisibility(View.GONE);
                    edit.putString("store_oil1", path_oil1);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }


        }








        //********************OIL PIC 2 -------TAKE
        else if (resultCode == RESULT_OK && requestCode == SELECT_OIL_PIC2){
            try {
                Uri selectedMediaUri = data.getData();
                path_oil2 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    oil_pic2_iv.setImageBitmap(bitmap);
                    chk_oil2.setChecked(true);
                    chk_oil2.setVisibility(View.VISIBLE);
                    oil_plus2.setVisibility(View.GONE);
                    edit.putString("store_oil2", path_oil2);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }
        }






        //********************IGNITION PIC 1 -------TAKE

        if (resultCode == RESULT_OK && requestCode == TAKE_IGNITION_PIC1) {

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
                ignition_pic1_iv.setImageBitmap(bitmap);
                chk_ignition1.setChecked(true);
                chk_ignition1.setVisibility(View.VISIBLE);
                ignition_plus1.setVisibility(View.GONE);

                path_ignition1=f.getAbsolutePath();
                edit.putString("store_ignition1",path_ignition1);
                edit.commit();


                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }




        }








        //********************IGNITION PIC 2 -------TAKE
        else if (resultCode == RESULT_OK && requestCode == TAKE_IGNITION_PIC2){

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
                ignition_pic2_iv.setImageBitmap(bitmap);
                chk_ignition2.setChecked(true);
                chk_ignition2.setVisibility(View.VISIBLE);
                ignition_plus2.setVisibility(View.GONE);

                path_ignition2=f.getAbsolutePath();
                edit.putString("store_ignition2",path_ignition2);
                edit.commit();
                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }









        //********************IGNITION PIC 1 -------SELECT
        else if (resultCode == RESULT_OK && requestCode == SELECT_IGNITION_PIC1){


            try {
                Uri selectedMediaUri = data.getData();
                path_ignition1 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    ignition_pic1_iv.setImageBitmap(bitmap);
                    chk_ignition1.setChecked(true);
                    chk_ignition1.setVisibility(View.VISIBLE);
                    ignition_plus1.setVisibility(View.GONE);
                    edit.putString("store_ignition1", path_ignition1);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }

        }








        //********************IGNITION PIC 2 -------SELECT
        else if (resultCode == RESULT_OK && requestCode == SELECT_IGNITION_PIC2){
            try {
                Uri selectedMediaUri = data.getData();
                path_ignition2 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    ignition_pic2_iv.setImageBitmap(bitmap);
                    chk_ignition2.setChecked(true);
                    chk_ignition2.setVisibility(View.VISIBLE);
                    ignition_plus2.setVisibility(View.GONE);
                    edit.putString("store_ignition2", path_ignition2);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }
        }







        //********************SWITCH PIC 1 -------TAKE
        if (resultCode == RESULT_OK && requestCode == TAKE_SWITCH_PIC1) {

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

                switch_pic1_iv.setImageBitmap(bitmap);
                chk_switch1.setChecked(true);
                chk_switch1.setVisibility(View.VISIBLE);
                switch_plus1.setVisibility(View.GONE);

                path_switch1=f.getAbsolutePath();
                edit.putString("store_switch1",path_switch1);
                edit.commit();


                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }






        //********************SWITCH PIC 2 -------TAKE
        if (resultCode == RESULT_OK && requestCode == TAKE_SWITCH_PIC2) {

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


                switch_pic2_iv.setImageBitmap(bitmap);
                chk_switch2.setChecked(true);
                chk_switch2.setVisibility(View.VISIBLE);
                switch_plus2.setVisibility(View.GONE);


                path_switch2=f.getAbsolutePath();
                edit.putString("store_switch2",path_switch2);
                edit.commit();

                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }




        }






        //********************SWITCH PIC 1 -------SELECT
        else if (resultCode == RESULT_OK && requestCode == SELECT_SWITCH_PIC1){

            try {
                Uri selectedMediaUri = data.getData();
                path_switch1 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    switch_pic1_iv.setImageBitmap(bitmap);
                    chk_switch1.setChecked(true);
                    chk_switch1.setVisibility(View.VISIBLE);
                    switch_plus1.setVisibility(View.GONE);
                    edit.putString("store_switch1", path_switch1);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }

        }








        //********************SWITCH PIC 2 -------SELECT
        else if (resultCode == RESULT_OK && requestCode == SELECT_SWITCH_PIC2){
            try {
                Uri selectedMediaUri = data.getData();
                path_switch2 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    switch_pic2_iv.setImageBitmap(bitmap);
                    chk_switch2.setChecked(true);
                    chk_switch2.setVisibility(View.VISIBLE);
                    switch_plus2.setVisibility(View.GONE);
                    edit.putString("store_switch2", path_switch2);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }
        }







        //********************WINDOW PIC 1 -------TAKE
        if (resultCode == RESULT_OK && requestCode == TAKE_WINDOW_PIC1) {

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
                window_pic1_iv.setImageBitmap(bitmap);
                chk_window1.setChecked(true);
                chk_window1.setVisibility(View.VISIBLE);
                window_plus1.setVisibility(View.GONE);

                path_motor1=f.getAbsolutePath();
                edit.putString("store_motor1",path_motor1);
                edit.commit();

                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }






        //********************WINDOW PIC 2 -------TAKE
        if (resultCode == RESULT_OK && requestCode == TAKE_WINDOW_PIC2) {

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


                window_pic2_iv.setImageBitmap(bitmap);
                chk_window2.setChecked(true);
                chk_window2.setVisibility(View.VISIBLE);
                window_plus2.setVisibility(View.GONE);


                path_motor2=f.getAbsolutePath();
                edit.putString("store_motor2",path_motor2);
                edit.commit();

                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }




        }






        //********************WINDOW PIC 1 -------SELECT
        else if (resultCode == RESULT_OK && requestCode == SELECT_WINDOW_PIC1){


            try {
                Uri selectedMediaUri = data.getData();
                path_motor1 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    window_pic1_iv.setImageBitmap(bitmap);
                    chk_window1.setChecked(true);
                    chk_window1.setVisibility(View.VISIBLE);
                    window_plus1.setVisibility(View.GONE);
                    edit.putString("store_motor1", path_motor1);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }

        }



        //********************WINDOW PIC 2 -------SELECT
        else if (resultCode == RESULT_OK && requestCode == SELECT_WINDOW_PIC2){
            try {
                Uri selectedMediaUri = data.getData();
                path_motor2 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    window_pic2_iv.setImageBitmap(bitmap);
                    chk_window2.setChecked(true);
                    chk_window2.setVisibility(View.VISIBLE);
                    window_plus2.setVisibility(View.GONE);
                    edit.putString("store_motor2", path_motor2);
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
                path_electrical_other1 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    others_pic1_iv.setImageBitmap(bitmap);
                    chk_others1.setChecked(true);
                    chk_others1.setVisibility(View.VISIBLE);
                    others_plus1.setVisibility(View.GONE);
                    edit.putString("store_electrical_other1", path_electrical_other1);
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
                path_electrical_other2 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    others_pic2_iv.setImageBitmap(bitmap);
                    chk_others2.setChecked(true);
                    chk_others2.setVisibility(View.VISIBLE);
                    others_plus2.setVisibility(View.GONE);
                    edit.putString("store_electrical_other2", path_electrical_other2);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }

        }
    }
}