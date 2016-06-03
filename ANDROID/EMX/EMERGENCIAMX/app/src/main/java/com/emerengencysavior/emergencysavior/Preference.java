package com.emerengencysavior.emergencysavior;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.widget.CompoundButton;

import com.jaeger.library.StatusBarUtil;
import com.kyleduo.switchbutton.SwitchButton;
import com.sloop.fonts.FontsManager;

import static com.emerengencysavior.emergencysavior.R.id.switch_audio;

/**
 * Created by RSA on 26-05-2016.
 */
public class Preference extends AppCompatActivity {

    SwitchButton switchButton_audio, switchButton_gps, switchButton_message, switchButton_email, switchButton_call_911, switch_safe_walk_duration;
    public static final String EXTRA_IS_TRANSPARENT = "is_transparent";
    private boolean isTransparent;
    Typeface tf;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    String str_audio, str_gps, str_message, str_email, str_call_911;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isTransparent = getIntent().getBooleanExtra(EXTRA_IS_TRANSPARENT, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        FontsManager.initFormAssets(this, "fonts/ques.otf");       //initialization
        FontsManager.changeFonts(this);

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> F O N T S I N T I A L Z A T I O N >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> A C T I O N B A R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        SpannableStringBuilder SS = new SpannableStringBuilder(Html.fromHtml("<font color='#3c5899'><small><b>EMERGENCY EVENT</b></small> </font>"));
        SS.setSpan(new CustomTypefaceSpan("#3c5899", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> A C T I O N B A R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        switchButton_audio = (SwitchButton) findViewById(switch_audio);
        switchButton_gps = (SwitchButton) findViewById(R.id.switch_gps);
        switchButton_message = (SwitchButton) findViewById(R.id.switch_message);
        switchButton_email = (SwitchButton) findViewById(R.id.switch_email);
        switchButton_call_911 = (SwitchButton) findViewById(R.id.switch_call_911);
        switch_safe_walk_duration = (SwitchButton) findViewById(R.id.switch_safe_walk_duration);

        setStatusBar();

        switchButton_gps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showSettingsAlert();
            }
        });


        onchanged();


    }

    private void onchanged() {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        str_gps = sharedpreferences.getString("gpsstatus", "");

        if (str_gps.equals("true")) {
            switchButton_gps.setChecked(true);
        } else {
            switchButton_gps.setChecked(false);
        }

    }

    protected void setStatusBar() {
        if (isTransparent) {
            StatusBarUtil.setTransparent(this);
        } else {
            StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
        }
    }


    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Preference.this);
        alertDialog.setTitle("GPS is settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedpreferences.edit();
                edit.remove("gpsstatus");
                edit.putString("gpsstatus", "true");
                edit.commit();
                onchanged();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedpreferences.edit();
                edit.remove("gpsstatus");
                edit.putString("gpsstatus", "false");
                edit.commit();
                dialog.dismiss();
                onchanged();


            }
        });
        alertDialog.show();
    }




}
