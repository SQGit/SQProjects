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

public class TransmissionFragment extends Fragment implements View.OnClickListener{

    ImageView automatic_pic1_iv,automatic_pic2_iv,manual_pic1_iv,manual_pic2_iv,others_pic1_iv,others_pic2_iv;
    ImageView automatic_plus1,automatic_plus2,manual_plus1,manual_plus2,others_plus1,others_plus2;
    CheckBox chk_automatic1,chk_automatic2,chk_manual1,chk_manual2,chk_others1,chk_others2;
    Dialog cameraDialog;
    LinearLayout lnr_takepic, lnr_takegallery;
    Bitmap bitmap;
    String selectedImagePath;
    public static TextView transmission_box1,transmission_box2;
    public static EditText transmission_others;
    String path_automatic1,path_automatic2,path_manual1,path_manual2,path_trans_other1,path_trans_other2;
    SharedPreferences sharedPrefces;
    SharedPreferences.Editor edit;


    protected static final int TAKE_AUTOMATIC_PIC1 = 1;
    protected static final int TAKE_AUTOMATIC_PIC2 = 2;
    protected static final int SELECT_AUTOMATIC_PIC1 = 3;
    protected static final int SELECT_AUTOMATIC_PIC2 = 4;


    protected static final int TAKE_MANUAL_PIC1 = 5;
    protected static final int TAKE_MANUAL_PIC2 = 6;
    protected static final int SELECT_MANUAL_PIC1 = 7;
    protected static final int SELECT_MANUAL_PIC2 = 8;


    protected static final int TAKE_OTHERS_PIC1 = 17;
    protected static final int TAKE_OTHERS_PIC2 = 18;
    protected static final int SELECT_OTHERS_PIC1 = 19;
    protected static final int SELECT_OTHERS_PIC2 = 20;



    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transmission, container, false);
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());


        sharedPrefces = PreferenceManager.getDefaultSharedPreferences(getActivity());
        edit = sharedPrefces.edit();


        transmission_box1=view.findViewById(R.id.transmission_box1);
        transmission_box2=view.findViewById(R.id.transmission_box2);
        transmission_others=view.findViewById(R.id.transmission_others);


        //@@@@@@@@@@@@@@@@ Engine FindviewbyId
        automatic_pic1_iv=view.findViewById(R.id.automatic_pic1_iv);
        automatic_pic2_iv=view.findViewById(R.id.automatic_pic2_iv);
        automatic_plus1=view.findViewById(R.id.automatic_plus1);
        automatic_plus2=view.findViewById(R.id.automatic_plus2);
        chk_automatic1=view.findViewById(R.id.chk_automatic1);
        chk_automatic2=view.findViewById(R.id.chk_automatic2);


        //@@@@@@@@@@@@@@@@ A/C FindviewbyId
        manual_pic1_iv=view.findViewById(R.id.manual_pic1_iv);
        manual_pic2_iv=view.findViewById(R.id.manual_pic2_iv);
        manual_plus1=view.findViewById(R.id.manual_plus1);
        manual_plus2=view.findViewById(R.id.manual_plus2);
        chk_manual1=view.findViewById(R.id.chk_manual1);
        chk_manual2=view.findViewById(R.id.chk_manual2);


        //@@@@@@@@@@@@@@@@ OTHERS FindviewbyId
        others_pic1_iv=view.findViewById(R.id.others_pic1_iv);
        others_pic2_iv=view.findViewById(R.id.others_pic2_iv);
        others_plus1=view.findViewById(R.id.others_plus1);
        others_plus2=view.findViewById(R.id.others_plus2);
        chk_others1=view.findViewById(R.id.chk_others1);
        chk_others2=view.findViewById(R.id.chk_others2);


        automatic_pic1_iv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.camera_box));
        automatic_pic2_iv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.camera_box));
        manual_pic1_iv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.camera_box));
        manual_pic2_iv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.camera_box));
        others_pic1_iv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.camera_box));
        others_pic2_iv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.camera_box));


        chk_automatic1.setVisibility(View.GONE);
        chk_automatic2.setVisibility(View.GONE);
        chk_manual1.setVisibility(View.GONE);
        chk_manual2.setVisibility(View.GONE);
        chk_others1.setVisibility(View.GONE);
        chk_others2.setVisibility(View.GONE);




        //AUTOMATIC Checkbox1 Condition--------------------------------------------------->
        chk_automatic1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!chk_automatic1.isChecked()){
                    chk_automatic1.setChecked(false);
                    chk_automatic1.setVisibility(View.GONE);
                    automatic_pic1_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    automatic_pic1_iv.setEnabled(true);
                    automatic_plus1.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_automatic1.setChecked(true);
                    chk_automatic1.setVisibility(View.VISIBLE);
                    automatic_pic1_iv.setEnabled(false);
                    automatic_plus1.setVisibility(View.GONE);
                }
            }
        });



        //AUTOMATIC Checkbox2 Condition--------------------------------------------------->
        chk_automatic2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!chk_automatic2.isChecked()){
                    chk_automatic2.setChecked(false);
                    chk_automatic2.setVisibility(View.GONE);
                    automatic_pic2_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    automatic_pic2_iv.setEnabled(true);
                    automatic_plus2.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_automatic2.setChecked(true);
                    chk_automatic2.setVisibility(View.VISIBLE);
                    automatic_pic2_iv.setEnabled(false);
                    automatic_plus2.setVisibility(View.GONE);
                }
            }
        });





        //MANUAL Checkbox1 Condition--------------------------------------------------->
        chk_manual1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(!chk_manual1.isChecked()){
                    chk_manual1.setChecked(false);
                    chk_manual1.setVisibility(View.GONE);
                    manual_pic1_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    manual_pic1_iv.setEnabled(true);
                    manual_plus1.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_manual1.setChecked(true);
                    chk_manual1.setVisibility(View.VISIBLE);
                    manual_pic1_iv.setEnabled(false);
                    manual_plus1.setVisibility(View.GONE);
                }

            }
        });




        //BUMPERS Checkbox2 Condition--------------------------------------------------->
        chk_manual2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(!chk_manual2.isChecked()){
                    chk_manual2.setChecked(false);
                    chk_manual2.setVisibility(View.GONE);
                    manual_pic2_iv.setImageDrawable(getResources().getDrawable(R.drawable.camera_box));
                    manual_pic2_iv.setEnabled(true);
                    manual_plus2.setVisibility(View.VISIBLE);
                }
                else
                {
                    chk_manual2.setChecked(true);
                    chk_manual2.setVisibility(View.VISIBLE);
                    manual_pic2_iv.setEnabled(false);
                    manual_plus2.setVisibility(View.GONE);
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



        automatic_pic1_iv.setOnClickListener(this);
        automatic_pic2_iv.setOnClickListener(this);
        manual_pic1_iv.setOnClickListener(this);
        manual_pic2_iv.setOnClickListener(this);
        others_pic1_iv.setOnClickListener(this);
        others_pic2_iv.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // AUTOMATIC PIC ONE
            case R.id.automatic_pic1_iv:

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
                                    TAKE_AUTOMATIC_PIC1);
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
                            startActivityForResult(pickIntent, SELECT_AUTOMATIC_PIC1);
                        }

                    }
                });
                cameraDialog.show();

                break;


            // AUTOMATIC PIC TWO
            case R.id.automatic_pic2_iv:
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
                                    TAKE_AUTOMATIC_PIC2);
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
                            startActivityForResult(pickIntent, SELECT_AUTOMATIC_PIC2);
                        }
                    }
                });
                cameraDialog.show();
                break;







            // MANUAL PIC ONE
            case R.id.manual_pic1_iv:
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
                                    TAKE_MANUAL_PIC1);
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
                            startActivityForResult(pickIntent, SELECT_MANUAL_PIC1);
                        }
                    }
                });
                cameraDialog.show();

                break;






            // MANUAL PIC TWO
            case R.id.manual_pic2_iv:
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
                                    TAKE_MANUAL_PIC2);
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
                            startActivityForResult(pickIntent, SELECT_MANUAL_PIC2);
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

        //********************AUTOMATIC PIC 1 -------TAKE
        if (resultCode == RESULT_OK && requestCode == TAKE_AUTOMATIC_PIC1) {

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
                automatic_pic1_iv.setImageBitmap(bitmap);
                chk_automatic1.setChecked(true);
                chk_automatic1.setVisibility(View.VISIBLE);
                automatic_plus1.setVisibility(View.GONE);

                path_automatic1=f.getAbsolutePath();
                edit.putString("store_automatic1",path_automatic1);
                edit.commit();




                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }




        }








        //********************AUTOMATIC PIC 2 -------TAKE
        else if (resultCode == RESULT_OK && requestCode == TAKE_AUTOMATIC_PIC2){

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
                automatic_pic2_iv.setImageBitmap(bitmap);
                chk_automatic2.setChecked(true);
                chk_automatic2.setVisibility(View.VISIBLE);
                automatic_plus2.setVisibility(View.GONE);

                path_automatic2=f.getAbsolutePath();
                edit.putString("store_automatic2",path_automatic2);
                edit.commit();
                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



        }








        //********************AUTOMATIC PIC 1 -------SELECT

        else if (resultCode == RESULT_OK && requestCode == SELECT_AUTOMATIC_PIC1){

            try {
                Uri selectedMediaUri = data.getData();
                path_automatic1 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    automatic_pic1_iv.setImageBitmap(bitmap);
                    chk_automatic1.setChecked(true);
                    chk_automatic1.setVisibility(View.VISIBLE);
                    automatic_plus1.setVisibility(View.GONE);
                    edit.putString("store_automatic1", path_automatic1);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }



        }








        //********************AUTOMATIC PIC 2 -------SELECT
        else if (resultCode == RESULT_OK && requestCode == SELECT_AUTOMATIC_PIC2){


            try {
                Uri selectedMediaUri = data.getData();
                path_automatic2 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    automatic_pic2_iv.setImageBitmap(bitmap);
                    chk_automatic2.setChecked(true);
                    chk_automatic2.setVisibility(View.VISIBLE);
                    automatic_plus2.setVisibility(View.GONE);
                    edit.putString("store_automatic2", path_automatic2);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }

        }




















        //********************MANUAL PIC 1 -------TAKE

        if (resultCode == RESULT_OK && requestCode == TAKE_MANUAL_PIC1) {

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
                manual_pic1_iv.setImageBitmap(bitmap);
                chk_manual1.setChecked(true);
                chk_manual1.setVisibility(View.VISIBLE);
                manual_plus1.setVisibility(View.GONE);


                path_manual1=f.getAbsolutePath();
                edit.putString("store_manual1",path_manual1);
                edit.commit();

                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }




        }








        //********************MANUAL PIC 2 -------TAKE
        else if (resultCode == RESULT_OK && requestCode == TAKE_MANUAL_PIC2){

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
                manual_pic2_iv.setImageBitmap(bitmap);
                chk_manual2.setChecked(true);
                chk_manual2.setVisibility(View.VISIBLE);
                manual_plus2.setVisibility(View.GONE);

                path_manual2=f.getAbsolutePath();
                edit.putString("store_manual2",path_manual2);
                edit.commit();

                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



        }









        //********************MANUAL PIC 1 -------SELECT
        else if (resultCode == RESULT_OK && requestCode == SELECT_MANUAL_PIC1){


            try {
                Uri selectedMediaUri = data.getData();
                path_manual1 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    manual_pic1_iv.setImageBitmap(bitmap);
                    chk_manual1.setChecked(true);
                    chk_manual1.setVisibility(View.VISIBLE);
                    manual_plus1.setVisibility(View.GONE);
                    edit.putString("store_manual1", path_manual1);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }
        }








        //********************MANUAL PIC 2 -------SELECT
        else if (resultCode == RESULT_OK && requestCode == SELECT_MANUAL_PIC2){
            try {
                Uri selectedMediaUri = data.getData();
                path_manual2 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    manual_pic2_iv.setImageBitmap(bitmap);
                    chk_manual2.setChecked(true);
                    chk_manual2.setVisibility(View.VISIBLE);
                    manual_plus2.setVisibility(View.GONE);
                    edit.putString("store_manual2", path_manual2);
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
                path_trans_other2 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    others_pic1_iv.setImageBitmap(bitmap);
                    chk_others1.setChecked(true);
                    chk_others1.setVisibility(View.VISIBLE);
                    others_plus1.setVisibility(View.GONE);
                    edit.putString("store_transother1", path_trans_other1);
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
                path_trans_other2 = GetFilePathFromDevice.getPath(getActivity(), selectedMediaUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                    others_pic2_iv.setImageBitmap(bitmap);
                    chk_others2.setChecked(true);
                    chk_others2.setVisibility(View.VISIBLE);
                    others_plus2.setVisibility(View.GONE);
                    edit.putString("store_transother2", path_trans_other2);
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {

            }

        }




    }
}