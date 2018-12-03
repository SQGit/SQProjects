package com.sqindia.www.autoparts360.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sqindia.www.autoparts360.Font.FontsOverride;
import com.sqindia.www.autoparts360.R;


public class DashboardActivity extends Activity {


    LinearLayout upload_parts,view_parts;
    public static Dialog create_dialog;
    TextView head_logout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        View v1 = getWindow().getDecorView().getRootView();
        FontsOverride.overrideFonts(DashboardActivity.this, v1);


        upload_parts=findViewById(R.id.upload_parts);
        view_parts=findViewById(R.id.view_parts);
        head_logout=findViewById(R.id.head_logout);

        upload_parts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),PartsEntryActivity.class);
                startActivity(i);
                finish();
            }
        });

        view_parts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),ViewCarPartsActivity.class);
                startActivity(i);
                finish();
            }
        });


        head_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(DashboardActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_logout);

                Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/helevetical.ttf");

                Button cancel_btn = (Button) dialog.findViewById(R.id.cancel_btn);
                Button ok_btn = (Button) dialog.findViewById(R.id.ok_btn);
                TextView tv=(TextView)dialog.findViewById(R.id.tv) ;

                cancel_btn.setTypeface(tf);
                ok_btn.setTypeface(tf);
                tv.setTypeface(tf);


                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                ok_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor edit = shared.edit();
                        edit.putString("status","false") ;
                        edit.commit();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });



    }
}
