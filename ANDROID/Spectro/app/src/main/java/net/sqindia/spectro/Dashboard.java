package net.sqindia.spectro;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.sloop.fonts.FontsManager;



/**
 * Created by $Krishna on 27-08-2016.
 */
public class Dashboard extends Activity {

    LinearLayout add_instrument,btn_chemical,btn_instrument,btn_offlinemode;
    ImageButton btn_start;
    String drug,inst;
    ImageView dsh_icon;
    TextView txtview,txtview1,tv_dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        FontsManager.initFormAssets(this, "fonts/avenir.otf");       //initialization
        FontsManager.changeFonts(this);

       // add_instrument = (LinearLayout) findViewById(R.id.layout_add);
        btn_chemical = (LinearLayout) findViewById(R.id.dsh_linear1);
        btn_instrument = (LinearLayout) findViewById(R.id.dsh_linear2);
        btn_offlinemode = (LinearLayout) findViewById(R.id.dsh_linear3);
        btn_start = (ImageButton) findViewById(R.id.btn_start);

        txtview = (TextView) findViewById(R.id.meth);
        txtview1 = (TextView) findViewById(R.id.txtview1);
        tv_dashboard = (TextView) findViewById(R.id.dashboard);

        RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(5000);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setRepeatMode(Animation.REVERSE);
        dsh_icon = (ImageView) findViewById(R.id.db_ic);
        dsh_icon.startAnimation(rotate);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/helvitica.ttf");
        tv_dashboard.setTypeface(tf);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, Spectrometer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
                //overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                //slide from right to left
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

              /*  Intent reg_intent = new Intent(getApplicationContext(), Register.class);


                ActivityOptions options = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    options = ActivityOptions.makeScaleUpAnimation(tv_sub_header, 0,
                            0, tv_sub_header.getWidth(),
                            tv_sub_header.getHeight());
                    startActivity(reg_intent, options.toBundle());
                } else {
                    reg_intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(reg_intent);
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                }
                finish();*/

            }
        });




        btn_chemical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chemical();
            }
        });
        btn_instrument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instrument();
            }
        });

    }

    private void chemical() {

        LayoutInflater layoutInflater = LayoutInflater.from(Dashboard.this);
        View promptView = layoutInflater.inflate(R.layout.chemical, null);
        final AlertDialog alertD = new AlertDialog.Builder(Dashboard.this).create();
        alertD.setCancelable(true);
        Window window = alertD.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final TextView tv_chemical = (TextView) promptView.findViewById(R.id.txtv);
        final RadioGroup radioGroup =(RadioGroup) promptView.findViewById(R.id.radioGroup2);
        final RadioButton rb_meth = (RadioButton) promptView.findViewById(R.id.radio1);
        final RadioButton rb_amphe = (RadioButton) promptView.findViewById(R.id.radio2);
        final RadioButton rb_benzo = (RadioButton) promptView.findViewById(R.id.radio3);
        final RadioButton rb_coca = (RadioButton) promptView.findViewById(R.id.radio4);
        final RadioButton rb_method = (RadioButton) promptView.findViewById(R.id.radio5);
        final RadioButton rb_heroin = (RadioButton) promptView.findViewById(R.id.radio6);

        Typeface tff = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/helvitica.ttf");
        tv_chemical.setTypeface(tff);
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/avenir.otf");
        rb_meth.setTypeface(tf);
        rb_amphe.setTypeface(tf);
        rb_benzo.setTypeface(tf);
        rb_coca.setTypeface(tf);
        rb_heroin.setTypeface(tf);
        rb_method.setTypeface(tf);

        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (alertD.isShowing()) {
                    alertD.dismiss();
                }
            }
        };


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    drug = "Methamphetamines";


                } else if (checkedId == R.id.radio2) {
                    drug = "Amphetamines";
                }
                else if (checkedId == R.id.radio3) {
                    drug = "Benzodiazepines";
                }
                else if (checkedId == R.id.radio4) {
                    drug = "Cocaine";
                }
                else if (checkedId == R.id.radio5) {
                    drug = "Methadone";
                }
                else if (checkedId == R.id.radio6) {
                    drug = "Heroin";
                }
                Log.e("TAG","asd"+drug);
                //alertD.dismiss();
                handler.postDelayed(runnable,700);
                txtview.setText(drug);
            }

        });
        alertD.setView(promptView);
        alertD.show();
    }

    private void instrument() {

        LayoutInflater layoutInflater = LayoutInflater.from(Dashboard.this);
        View promptView = layoutInflater.inflate(R.layout.instrument, null);
        final AlertDialog alertD = new AlertDialog.Builder(Dashboard.this).create();
        alertD.setCancelable(true);
        Window window = alertD.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final TextView tv_instrument = (TextView) promptView.findViewById(R.id.txt_instrument);
        final RadioGroup radioGroup =(RadioGroup) promptView.findViewById(R.id.radioGroup1);
        final RadioButton rb_mks = (RadioButton) promptView.findViewById(R.id.inst_radio1);
        final RadioButton rb_instrument1 = (RadioButton) promptView.findViewById(R.id.inst_radio2);
        final RadioButton rb_instrument2 = (RadioButton) promptView.findViewById(R.id.inst_radio3);

        Typeface tff = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/helvitica.ttf");
        tv_instrument.setTypeface(tff);
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/avenir.otf");
        rb_mks.setTypeface(tf);
        rb_instrument1.setTypeface(tf);
        rb_instrument2.setTypeface(tf);


        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (alertD.isShowing()) {
                    alertD.dismiss();
                }
            }
        };


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.inst_radio1) {
                    inst = "Mks vision 2000p-Mass Spectrometer";


                } else if (checkedId == R.id.inst_radio2) {
                    inst = "Instrument 2";
                }
                else if (checkedId == R.id.inst_radio3) {
                    inst = "Instrument 3";
                }
                Log.e("TAG","asd"+inst);
                //alertD.dismiss();
                handler.postDelayed(runnable,700);
                txtview1.setText(inst);
            }

        });
        alertD.setView(promptView);
        alertD.show();
    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Dashboard.this);

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Exit...");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want exit");

        // Setting Icon to Dialog

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

               // finish();
                // Write your code here to invoke YES event
               // Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                Intent exit = new Intent(Intent.ACTION_MAIN);
                exit.setAction(Intent.ACTION_MAIN);
                exit.addCategory(Intent.CATEGORY_HOME);
                exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(exit);
                finish();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();

        // super.onBackPressed();
    }

}
