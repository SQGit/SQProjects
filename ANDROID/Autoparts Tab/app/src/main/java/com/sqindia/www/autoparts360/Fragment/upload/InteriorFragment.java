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

public class InteriorFragment extends Fragment implements View.OnClickListener{
    ImageView seats_pic1_iv,seats_pic2_iv,steering_pic1_iv,steering_pic2_iv,dashboards_pic1_iv,dashboards_pic2_iv,radios_pic1_iv,radios_pic2_iv,others_pic1_iv,others_pic2_iv;
    ImageView seats_plus1,seats_plus2,steering_plus1,steering_plus2,dashboard_plus1,dashboard_plus2,radios_plus1,radios_plus2,others_plus1,others_plus2;
    CheckBox chk_seats1,chk_seats2,chk_steering1,chk_steering2,chk_dashboard1,chk_dashboard2,chk_radio1,chk_radio2,chk_others1,chk_others2;
    Dialog cameraDialog;
    LinearLayout lnr_takepic, lnr_takegallery;
    Bitmap bitmap;
    String selectedImagePath;

    public static TextView interior_box1,interior_box2,interior_box3,interior_box4;
    public static EditText interior_others;


    SharedPreferences sharedPrefces;
    SharedPreferences.Editor edit;
    String path_seat1,path_seat2,path_steering1,path_steering2,path_dash1,path_dash2,path_radio1,path_radio2,path_interior_other1,path_interior_other2;

    protected static final int TAKE_SEETS_PIC1 = 1;
    protected static final int TAKE_SEETS_PIC2 = 2;
    protected static final int SELECT_SEETS_PIC1 = 3;
    protected static final int SELECT_SEETS_PIC2 = 4;


    protected static final int TAKE_STEERING_PIC1 = 5;
    protected static final int TAKE_STEERING_PIC2 = 6;
    protected static final int SELECT_STEERING_PIC1 = 7;
    protected static final int SELECT_STEERING_PIC2 = 8;

    protected static final int TAKE_DASHBOARD_PIC1 = 9;
    protected static final int TAKE_DASHBOARD_PIC2 = 10;
    protected static final int SELECT_DASHBOARD_PIC1 = 11;
    protected static final int SELECT_DASHBOARD_PIC2 = 12;


    protected static final int TAKE_RADIO_PIC1 = 13;
    protected static final int TAKE_RADIO_PIC2 = 14;
    protected static final int SELECT_RADIO_PIC1 = 15;
    protected static final int SELECT_RADIO_PIC2 = 16;


    protected static final int TAKE_OTHERS_PIC1 = 17;
    protected static final int TAKE_OTHERS_PIC2 = 18;
    protected static final int SELECT_OTHERS_PIC1 = 19;
    protected static final int SELECT_OTHERS_PIC2 = 20;

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interior, container, false);
        sharedPrefces = PreferenceManager.getDefaultSharedPreferences(getActivity());
        edit = sharedPrefces.edit();



        interior_box1=view.findViewById(R.id.interior_box1);
        interior_box2=view.findViewById(R.id.interior_box2);
        interior_box3=view.findViewById(R.id.interior_box3);
        interior_box4=view.findViewById(R.id.interior_box4);
        interior_others=view.findViewById(R.id.interior_others);

        //@@@@@@@@@@@@@@@@ Seats FindviewbyId
        seats_pic1_iv=view.findViewById(R.id.seats_pic1_iv);
        seats_pic2_iv=view.findViewById(R.id.seats_pic2_iv);
        seats_plus1=view.findViewById(R.id.seats_plus1);
        seats_plus2=view.findViewById(R.id.seats_plus2);
        chk_seats1=view.findViewById(R.id.chk_seats1);
        chk_seats2=view.findViewById(R.id.chk_seats2);



        //@@@@@@@@@@@@@@@@ Steering FindviewbyId
        steering_pic1_iv=view.findViewById(R.id.steering_pic1_iv);
        steering_pic2_iv=view.findViewById(R.id.steering_pic2_iv);
        steering_plus1=view.findViewById(R.id.steering_plus1);
        steering_plus2=view.findViewById(R.id.steering_plus2);
        chk_steering1=view.findViewById(R.id.chk_steering1);
        chk_steering2=view.findViewById(R.id.chk_steering2);



        //@@@@@@@@@@@@@@@@ Dashboard FindviewbyId
        dashboards_pic1_iv=view.findViewById(R.id.dashboards_pic1_iv);
        dashboards_pic2_iv=view.findViewById(R.id.dashboards_pic2_iv);
        dashboard_plus1=view.findViewById(R.id.dashboard_plus1);
        dashboard_plus2=view.findViewById(R.id.dashboard_plus2);
        chk_dashboard1=view.findViewById(R.id.chk_dashboard1);
        chk_dashboard2=view.findViewById(R.id.chk_dashboard2);



        //@@@@@@@@@@@@@@@@ FUEL FindviewbyId
        radios_pic1_iv=view.findViewById(R.id.radios_pic1_iv);
        radios_pic2_iv=view.findViewById(R.id.radios_pic2_iv);
        radios_plus1=view.findViewById(R.id.radios_plus1);
        radios_plus2=view.findViewById(R.id.radios_plus2);
        chk_radio1=view.findViewById(R.id.chk_radio1);
        chk_radio2=view.findViewById(R.id.chk_radio2);


        //@@@@@@@@@@@@@@@@ OTHERS FindviewbyId
        others_pic1_iv=view.findViewById(R.id.others_pic1_iv);
        others_pic2_iv=view.findViewById(R.id.others_pic2_iv);
        others_plus1=view.findViewById(R.id.others_plus1);
        others_plus2=view.findViewById(R.id.others_plus2);
        chk_others1=view.findViewById(R.id.chk_others1);
        chk_others2=view.findViewById(R.id.chk_others2);


        seats_pic1_iv.setOnClickListener(this);
        seats_pic2_iv.setOnClickListener(this);
        steering_pic1_iv.setOnClickListener(this);
        steering_pic2_iv.setOnClickListener(this);
        dashboard_plus1.setOnClickListener(this);
        dashboard_plus2.setOnClickListener(this);
        radios_pic1_iv.setOnClickListener(this);
        radios_pic2_iv.setOnClickListener(this);
        others_pic1_iv.setOnClickListener(this);
        others_pic2_iv.setOnClickListener(this);

        //SEATS Checkbox1 Condition--------------------------------------------------->
        chk_seats1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(!chk_seats1.isChecked()){
                    chk_seats1.setChecked(false);
                    chk_seats1.setVisibility(View.GONE);
                    seats_pic1_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    seats_pic1_iv.setEnabled(true);
                    seats_plus1.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_seats1.setChecked(true);
                    chk_seats1.setVisibility(View.VISIBLE);
                    seats_pic1_iv.setEnabled(false);
                    seats_plus1.setVisibility(View.GONE);
                }
            }
        });



        //SEATS Checkbox2 Condition--------------------------------------------------->
        chk_seats2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(!chk_seats2.isChecked()){
                    chk_seats2.setChecked(false);
                    chk_seats2.setVisibility(View.GONE);
                    seats_pic2_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    seats_pic2_iv.setEnabled(true);
                    seats_plus2.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_seats2.setChecked(true);
                    chk_seats2.setVisibility(View.VISIBLE);
                    seats_pic2_iv.setEnabled(false);
                    seats_plus2.setVisibility(View.GONE);
                }

            }
        });




        //Steering Checkbox1 Condition--------------------------------------------------->
        chk_steering1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(!chk_steering1.isChecked()){
                    chk_steering1.setChecked(false);
                    chk_steering1.setVisibility(View.GONE);
                    steering_pic1_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    steering_pic1_iv.setEnabled(true);
                    steering_plus1.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_steering1.setChecked(true);
                    chk_steering1.setVisibility(View.VISIBLE);
                    steering_pic1_iv.setEnabled(false);
                    steering_plus1.setVisibility(View.GONE);
                }

            }
        });




        //Steering Checkbox2 Condition--------------------------------------------------->
        chk_steering2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!chk_steering2.isChecked()){
                    chk_steering2.setChecked(false);
                    chk_steering2.setVisibility(View.GONE);
                    steering_pic2_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    steering_pic2_iv.setEnabled(true);
                    steering_plus2.setVisibility(View.VISIBLE);
                }
                else {
                    chk_steering2.setChecked(true);
                    chk_steering2.setVisibility(View.VISIBLE);
                    steering_pic2_iv.setEnabled(false);
                    steering_plus2.setVisibility(View.GONE);
                }
            }
        });


        //Dashboards Checkbox1 Condition--------------------------------------------------->
        chk_dashboard1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(!chk_dashboard1.isChecked()){
                    chk_dashboard1.setChecked(false);
                    chk_dashboard1.setVisibility(View.GONE);
                    dashboards_pic1_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    dashboards_pic1_iv.setEnabled(true);
                    dashboard_plus1.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_dashboard1.setChecked(true);
                    chk_dashboard1.setVisibility(View.VISIBLE);
                    dashboards_pic1_iv.setEnabled(false);
                    dashboard_plus1.setVisibility(View.GONE);
                }

            }
        });


        //Dashboards Checkbox2 Condition--------------------------------------------------->
        chk_dashboard2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(!chk_dashboard2.isChecked()){
                    chk_dashboard2.setChecked(false);
                    chk_dashboard2.setVisibility(View.GONE);
                    dashboards_pic2_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    dashboards_pic2_iv.setEnabled(true);
                    dashboard_plus2.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_dashboard2.setChecked(true);
                    chk_dashboard2.setVisibility(View.VISIBLE);
                    dashboards_pic2_iv.setEnabled(false);
                    dashboard_plus2.setVisibility(View.GONE);
                }

            }
        });


        //Radios Checkbox1 Condition--------------------------------------------------->
        chk_radio1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!chk_radio1.isChecked()){
                    chk_radio1.setChecked(false);
                    chk_radio1.setVisibility(View.GONE);
                    radios_pic1_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    radios_pic1_iv.setEnabled(true);
                    radios_plus1.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_radio1.setChecked(true);
                    chk_radio1.setVisibility(View.VISIBLE);
                    radios_pic1_iv.setEnabled(false);
                    radios_plus1.setVisibility(View.GONE);
                }
            }
        });


        //Radios Checkbox2 Condition--------------------------------------------------->
        chk_radio2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!chk_radio2.isChecked()){
                    chk_radio2.setChecked(false);
                    chk_radio2.setVisibility(View.GONE);
                    radios_pic2_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    radios_pic2_iv.setEnabled(true);
                    radios_plus2.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_radio1.setChecked(true);
                    chk_radio1.setVisibility(View.VISIBLE);
                    radios_pic2_iv.setEnabled(false);
                    radios_plus2.setVisibility(View.GONE);
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




    //call Camera Intent Dialog for all Imageview:
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // SEATS PIC ONE
            case R.id.seats_pic1_iv:

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
                                    TAKE_SEETS_PIC1);
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
                            startActivityForResult(pickIntent, SELECT_SEETS_PIC1);
                        }


                    }
                });
                cameraDialog.show();

                break;


            // SEATS PIC TWO
            case R.id.seats_pic2_iv:
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
                                    TAKE_SEETS_PIC2);
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
                            startActivityForResult(pickIntent, SELECT_SEETS_PIC2);
                        }

                    }
                });
                cameraDialog.show();

                break;







            // STEERING PIC ONE
            case R.id.steering_pic1_iv:
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
                                    TAKE_STEERING_PIC1);
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
                            startActivityForResult(pickIntent, SELECT_STEERING_PIC1);
                        }

                    }
                });
                cameraDialog.show();

                break;






            // STEERING PIC TWO
            case R.id.steering_pic2_iv:
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
                                    TAKE_STEERING_PIC2);
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
                            startActivityForResult(pickIntent, SELECT_STEERING_PIC2);
                        }


                    }
                });
                cameraDialog.show();
                break;






            // DASHBOARDS PIC ONE
            case R.id.dashboard_plus1:
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
                                TAKE_DASHBOARD_PIC1);

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
                            startActivityForResult(pickIntent, SELECT_DASHBOARD_PIC1);
                        }



                    }
                });
                cameraDialog.show();

                break;





            // DASHBOARDS PIC TWO
            case R.id.dashboard_plus2:
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
                                    TAKE_DASHBOARD_PIC2);
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
                            startActivityForResult(pickIntent, SELECT_DASHBOARD_PIC2);
                        }



                    }
                });
                cameraDialog.show();

                break;





            // RADIOS PIC TWO
            case R.id.radios_pic1_iv:
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
                                    TAKE_RADIO_PIC1);
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
                            startActivityForResult(pickIntent, SELECT_RADIO_PIC1);
                        }

                    }
                });
                cameraDialog.show();
                break;




            // RADIOS PIC TWO
            case R.id.radios_pic2_iv:
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
                                    TAKE_RADIO_PIC2);
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
                            startActivityForResult(pickIntent, SELECT_RADIO_PIC2);
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

        //********************Seats PIC 1 -------TAKE
        if (resultCode == RESULT_OK && requestCode == TAKE_SEETS_PIC1) {

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
                seats_pic1_iv.setImageBitmap(bitmap);
                chk_seats1.setChecked(true);
                chk_seats1.setVisibility(View.VISIBLE);
                seats_plus1.setVisibility(View.GONE);


                path_seat1=f.getAbsolutePath();
                edit.putString("store_seat1",path_seat1);
                edit.commit();

                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }




        }








        //********************SEATS PIC 2 -------TAKE
        else if (resultCode == RESULT_OK && requestCode == TAKE_SEETS_PIC2){

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
                seats_pic2_iv.setImageBitmap(bitmap);
                chk_seats2.setChecked(true);
                chk_seats2.setVisibility(View.VISIBLE);
                seats_plus2.setVisibility(View.GONE);


                path_seat2=f.getAbsolutePath();
                edit.putString("store_seat2",path_seat2);
                edit.commit();
                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



        }








        //********************SEATS PIC 1 -------SELECT

        else if (resultCode == RESULT_OK && requestCode == SELECT_SEETS_PIC1){


            try {
                Uri selectedMediaUri = data.getData();
                path_seat1 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    seats_pic1_iv.setImageBitmap(bitmap);
                    chk_seats1.setChecked(true);
                    chk_seats1.setVisibility(View.VISIBLE);
                    seats_plus1.setVisibility(View.GONE);
                    edit.putString("store_seat1", path_seat1);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }





        }








        //********************SEATS PIC 2 -------TAKE
        else if (resultCode == RESULT_OK && requestCode == SELECT_SEETS_PIC2){

            try {
                Uri selectedMediaUri = data.getData();
                path_seat2 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    seats_pic2_iv.setImageBitmap(bitmap);
                    chk_seats2.setChecked(true);
                    chk_seats2.setVisibility(View.VISIBLE);
                    seats_plus2.setVisibility(View.GONE);
                    edit.putString("store_seat2", path_seat2);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }
        }




















        //********************STEERING PIC 1 -------TAKE

        if (resultCode == RESULT_OK && requestCode == TAKE_STEERING_PIC1) {

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
                steering_pic1_iv.setImageBitmap(bitmap);
                chk_steering1.setChecked(true);
                chk_steering1.setVisibility(View.VISIBLE);
                steering_plus1.setVisibility(View.GONE);

                path_steering1=f.getAbsolutePath();
                edit.putString("store_steering1",path_steering1);
                edit.commit();


                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }




        }








        //********************STEERING PIC 2 -------TAKE
        else if (resultCode == RESULT_OK && requestCode == TAKE_STEERING_PIC2){

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
                steering_pic2_iv.setImageBitmap(bitmap);
                chk_steering2.setChecked(true);
                chk_steering2.setVisibility(View.VISIBLE);
                steering_plus2.setVisibility(View.GONE);

                path_steering2=f.getAbsolutePath();
                edit.putString("store_steering2",path_steering2);
                edit.commit();
                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }









        //********************STEERING PIC 1 -------SELECT
        else if (resultCode == RESULT_OK && requestCode == SELECT_STEERING_PIC1){



            try {
                Uri selectedMediaUri = data.getData();
                path_steering1 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    steering_pic1_iv.setImageBitmap(bitmap);
                    chk_steering1.setChecked(true);
                    chk_steering1.setVisibility(View.VISIBLE);
                    steering_plus1.setVisibility(View.GONE);
                    edit.putString("store_steering1", path_steering1);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }


        }








        //********************STEERING PIC 2 -------SELECT
        else if (resultCode == RESULT_OK && requestCode == SELECT_STEERING_PIC2){




            try {
                Uri selectedMediaUri = data.getData();
                path_steering2 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    steering_pic2_iv.setImageBitmap(bitmap);
                    chk_steering2.setChecked(true);
                    chk_steering2.setVisibility(View.VISIBLE);
                    steering_plus2.setVisibility(View.GONE);
                    edit.putString("store_steering2", path_steering2);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }

        }







        //********************DASHBOARD PIC 1 -------TAKE
        if (resultCode == RESULT_OK && requestCode == TAKE_DASHBOARD_PIC1) {

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
                dashboards_pic1_iv.setImageBitmap(bitmap);
                chk_dashboard1.setChecked(true);
                chk_dashboard1.setVisibility(View.VISIBLE);
                dashboard_plus1.setVisibility(View.GONE);

                path_dash1=f.getAbsolutePath();
                edit.putString("store_dash1",path_dash1);
                edit.commit();


                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }






        //********************DASHBOARD PIC 2 -------TAKE
        if (resultCode == RESULT_OK && requestCode == TAKE_DASHBOARD_PIC2) {

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


                dashboards_pic2_iv.setImageBitmap(bitmap);
                chk_dashboard2.setChecked(true);
                chk_dashboard2.setVisibility(View.VISIBLE);
                dashboard_plus2.setVisibility(View.GONE);

                path_dash2=f.getAbsolutePath();
                edit.putString("store_dash2",path_dash2);
                edit.commit();


                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }




        }






        //********************DASHBOARD PIC 1 -------SELECT
        else if (resultCode == RESULT_OK && requestCode == SELECT_DASHBOARD_PIC1){


            try {
                Uri selectedMediaUri = data.getData();
                path_dash1 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    dashboards_pic1_iv.setImageBitmap(bitmap);
                    chk_dashboard1.setChecked(true);
                    chk_dashboard1.setVisibility(View.VISIBLE);
                    dashboard_plus1.setVisibility(View.GONE);
                    edit.putString("store_dash1", path_dash1);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }


        }








        //********************DASHBOARD PIC 2 -------SELECT
        else if (resultCode == RESULT_OK && requestCode == SELECT_DASHBOARD_PIC2){



            try {
                Uri selectedMediaUri = data.getData();
                path_dash2 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    dashboards_pic2_iv.setImageBitmap(bitmap);
                    chk_dashboard2.setChecked(true);
                    chk_dashboard2.setVisibility(View.VISIBLE);
                    dashboard_plus2.setVisibility(View.GONE);
                    edit.putString("store_dash2", path_dash2);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }
        }







        //********************RADIOS PIC 1 -------TAKE
        if (resultCode == RESULT_OK && requestCode == TAKE_RADIO_PIC1) {

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
                radios_pic1_iv.setImageBitmap(bitmap);
                chk_radio1.setChecked(true);
                chk_radio1.setVisibility(View.VISIBLE);
                radios_plus1.setVisibility(View.GONE);



                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }






        //********************RADIOS PIC 2 -------TAKE
        if (resultCode == RESULT_OK && requestCode == TAKE_RADIO_PIC2) {

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


                radios_pic2_iv.setImageBitmap(bitmap);
                chk_radio2.setChecked(true);
                chk_radio2.setVisibility(View.VISIBLE);
                radios_plus2.setVisibility(View.GONE);




                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }




        }






        //********************RADIOS PIC 1 -------SELECT
        else if (resultCode == RESULT_OK && requestCode == SELECT_RADIO_PIC1){
            try {
                Uri selectedMediaUri = data.getData();
                path_radio1 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    radios_pic1_iv.setImageBitmap(bitmap);
                    chk_radio1.setChecked(true);
                    chk_radio1.setVisibility(View.VISIBLE);
                    radios_plus1.setVisibility(View.GONE);
                    edit.putString("store_radio1", path_radio1);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }


        }



        //********************RADIOS PIC 2 -------SELECT
        else if (resultCode == RESULT_OK && requestCode == SELECT_RADIO_PIC2){


            try {
                Uri selectedMediaUri = data.getData();
                path_radio2 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    radios_pic2_iv.setImageBitmap(bitmap);
                    chk_radio2.setChecked(true);
                    chk_radio2.setVisibility(View.VISIBLE);
                    radios_plus2.setVisibility(View.GONE);
                    edit.putString("store_radio2", path_radio2);
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
                path_interior_other1 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    others_pic1_iv.setImageBitmap(bitmap);
                    chk_others1.setChecked(true);
                    chk_others1.setVisibility(View.VISIBLE);
                    others_plus1.setVisibility(View.GONE);
                    edit.putString("store_interiorother1", path_interior_other1);
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
                path_interior_other2 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    others_pic2_iv.setImageBitmap(bitmap);
                    chk_others2.setChecked(true);
                    chk_others2.setVisibility(View.VISIBLE);
                    others_plus2.setVisibility(View.GONE);
                    edit.putString("store_interiorother2", path_interior_other2);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }

        }




    }
}