package com.startemplan.vyuspot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.devspark.robototextview.widget.RobotoTextView;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Salman on 2/24/2016.
 */
public class Getter_Setter_Adapter1 extends BaseAdapter {


    Context c1;
    ArrayList<Getter_Setter> arrayList;
    TextView name, location, landmark, number, needs, cared;
    View status;
    RobotoTextView txtstatus;
    String token;


    public Getter_Setter_Adapter1(Context c1, ArrayList<Getter_Setter> list) {
        this.c1 = c1;
        arrayList = list;

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v2, ViewGroup parent) {

        final Getter_Setter getset = arrayList.get(position);

        LayoutInflater inflat = (LayoutInflater) c1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        v2 = inflat.inflate(R.layout.view_needs_list1, null);

       /* SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c1);
        token= sharedPreferences.getString("token","");
        Log.img_view("tag",token);*/







       // status = v2.findViewById(R.id.statusview);
       // txtstatus = (RobotoTextView) v2.findViewById(R.id.statustext);

        name = (TextView) v2.findViewById(R.id.tname);
        location = (TextView) v2.findViewById(R.id.tloc);
        landmark = (TextView) v2.findViewById(R.id.tland);
        number = (TextView) v2.findViewById(R.id.tnum);
        needs = (TextView) v2.findViewById(R.id.tneeds);
        cared = (TextView) v2.findViewById(R.id.tcare);


        name.setText(getset.get_NAME());
        location.setText(getset.get_LOCATION());
        landmark.setText(getset.get_LANDMARK());
        number.setText(getset.get_NUMBER());
        needs.setText(getset.get_NEED());

/*        if(getset.get_CARE1() =="null")
        {
            String care ="";
            cared.setText(care);
        }
        else {
            cared.setText(getset.get_CAREDBY());
        }




        String as = getset.get_NAME();
        Log.img_map("tagname",as);


        String car = getset.get_CARE1();
        Log.img_map("tagcar",car);*/

  /*      if (car !="null") {

            v2.setEnabled(false);
            txtstatus.setText("Took Care");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                status.setBackground(c1.getResources().getDrawable(R.drawable.sts));
            }
            else {
                status.setBackgroundColor(c1.getResources().getColor(R.color.blue));
            }


        }

        else {

            txtstatus.setText("Not Taken");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                status.setBackground(c1.getResources().getDrawable(R.drawable.sts2));
            }
            else{
                status.setBackgroundColor(c1.getResources().getColor(R.color.navy));
            }


        }*/


      /*  v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.img_map("tag", getset.get_SPOTID()+ getset.get_NAME() + getset.get_LOCATION() + getset.get_LANDMARK() + getset.get_NEED() + getset.get_NUMBER() + getset.get_SPOTID() + getset.get_CAREDBY());

                String aa,bb,cc,dd,ee,ff,gg;

                aa = getset.get_SPOTID();
                bb = getset.get_NAME();
                cc = getset.get_LOCATION();
                dd = getset.get_LANDMARK();
                ee = getset.get_NUMBER();
                ff = getset.get_NEED();
                gg = getset.get_CAREDBY();



                if(token.length() < 5 )  {

                    new SweetAlertDialog(c1, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Warning")
                            .setContentText("You Have to Register First!!!")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.cancel();
                                    Intent goD = new Intent(c1, Register.class);
                                    c1.startActivity(goD);


                                }
                            })
                            .show();

                }


                else {

                    Intent goV = new Intent(c1, View_Needs_User.class);
                    goV.putExtra("a1", bb);
                    goV.putExtra("a2", cc);
                    goV.putExtra("a3", dd);
                    goV.putExtra("a4", ee);
                    goV.putExtra("a5", ff);

                    goV.putExtra("spot", aa);
                    goV.putExtra("b1", gg);

                    c1.startActivity(goV);
                }


            }
        });*/


        return v2;
    }
}
