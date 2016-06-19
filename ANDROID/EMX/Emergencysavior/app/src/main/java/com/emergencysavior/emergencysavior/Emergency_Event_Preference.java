package com.emergencysavior.emergencysavior;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import com.jaeger.library.StatusBarUtil;
import com.kyleduo.switchbutton.SwitchButton;
import com.sloop.fonts.FontsManager;

/**
 * Created by RSA on 23-03-2016.
 */
public class Emergency_Event_Preference extends AppCompatActivity {
    SwitchButton switchButton_audio, switchButton_gps, switchButton_message, switchButton_email, switchButton_call_911, switch_safe_walk_duration, switch_real_time_duration;
    Button submit;
    String str_audio, str_gps, str_message, str_email, str_call_911;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    public static final String EXTRA_IS_TRANSPARENT = "is_transparent";
    private boolean isTransparent;
    Typeface tf;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        isTransparent = getIntent().getBooleanExtra(EXTRA_IS_TRANSPARENT, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_event);
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
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> I D E N T I F Y >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        switchButton_audio = (SwitchButton) findViewById(R.id.switch_audio);
        switchButton_gps = (SwitchButton) findViewById(R.id.switch_gps);
        switchButton_message = (SwitchButton) findViewById(R.id.switch_message);
        switchButton_email = (SwitchButton) findViewById(R.id.switch_email);
        switchButton_call_911 = (SwitchButton) findViewById(R.id.switch_call_911);
        switch_safe_walk_duration = (SwitchButton) findViewById(R.id.switch_safe_walk_duration);
        switch_real_time_duration = (SwitchButton) findViewById(R.id.switch_real_time_duration);
        submit = (Button) findViewById(R.id.btn_submit);
        SharedPreferences settings = getSharedPreferences(MyPREFERENCES, 0);
        if (settings.getBoolean("my_first_time", true)) {
            switchButton_call_911.setChecked(true);
            switchButton_audio.setChecked(true);
            switchButton_gps.setChecked(true);
            switchButton_message.setChecked(true);
            switchButton_email.setChecked(true);
            settings.edit().putBoolean("my_first_time", false).apply();

        } else {
            onchecked();
        }

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  I D E N T I F Y >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        switchButton_audio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor edit = sharedpreferences.edit();
                    edit.remove("audiostatus");
                    edit.putString("audiostatus", "" + isChecked);
                    edit.commit();

                } else {
                    SharedPreferences.Editor edit = sharedpreferences.edit();
                    edit.remove("audiostatus");
                    edit.putString("audiostatus", "" + isChecked);
                    edit.commit();
                }

            }
        });
        switchButton_gps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor edit = sharedpreferences.edit();
                    edit.remove("gpsstatus");
                    edit.putString("gpsstatus", "" + isChecked);
                    edit.commit();

                } else {
                    SharedPreferences.Editor edit = sharedpreferences.edit();
                    edit.remove("gpsstatus");
                    edit.putString("gpsstatus", "" + isChecked);
                    edit.commit();
                }

            }
        });
        switchButton_message.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor edit = sharedpreferences.edit();
                    edit.remove("messagestatus");
                    edit.putString("messagestatus", "" + isChecked);
                    edit.commit();

                } else {
                    SharedPreferences.Editor edit = sharedpreferences.edit();
                    edit.remove("messagestatus");
                    edit.putString("messagestatus", "" + isChecked);
                    edit.commit();
                }


            }
        });
        switchButton_email.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor edit = sharedpreferences.edit();
                    edit.remove("emailstatus");
                    edit.putString("emailstatus", "" + isChecked);
                    edit.commit();

                } else {
                    SharedPreferences.Editor edit = sharedpreferences.edit();
                    edit.remove("emailstatus");
                    edit.putString("emailstatus", "" + isChecked);
                    edit.commit();
                }

            }
        });
        switchButton_call_911.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor edit = sharedpreferences.edit();
                    edit.remove("call911status");
                    edit.putString("call911status", "" + isChecked);
                    edit.commit();

                } else {
                    SharedPreferences.Editor edit = sharedpreferences.edit();
                    edit.remove("call911status");
                    edit.putString("call911status", "" + isChecked);
                    edit.commit();
                }

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                str_audio = sharedpreferences.getString("audiostatus", "");
                str_gps = sharedpreferences.getString("gpsstatus", "");
                str_message = sharedpreferences.getString("messagestatus", "");
                str_email = sharedpreferences.getString("emailstatus", "");
                str_call_911 = sharedpreferences.getString("call911status", "");

                Log.d("tag", "str_audio==>" + str_audio);
                Log.d("tag", "str_gps==>" + str_gps);
                Log.d("tag", "str_message==>" + str_message);
                Log.d("tag", "str_email==>" + str_email);
                Log.d("tag", "str_call_911==>" + str_call_911);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);


            }
        });

        setStatusBar();
    }

    protected void setStatusBar() {
        if (isTransparent) {
            StatusBarUtil.setTransparent(this);
        } else {
            StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
        }
    }

    private void onchecked() {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        str_audio = sharedpreferences.getString("audiostatus", "");
        str_gps = sharedpreferences.getString("gpsstatus", "");
        str_message = sharedpreferences.getString("messagestatus", "");
        str_email = sharedpreferences.getString("emailstatus", "");
        str_call_911 = sharedpreferences.getString("call911status", "");


        if (str_audio.equals("true")) {
            switchButton_audio.setChecked(true);
        } else {
            switchButton_audio.setChecked(false);
        }
        if (str_message.equals("true")) {
            switchButton_message.setChecked(true);
        } else {
            switchButton_message.setChecked(false);
        }
        if (str_gps.equals("true")) {
            switchButton_gps.setChecked(true);
        } else {
            switchButton_gps.setChecked(false);
        }
        if (str_email.equals("true")) {
            switchButton_email.setChecked(true);
        } else {
            switchButton_email.setChecked(false);
        }
        if (str_call_911.equals("true")) {
            switchButton_call_911.setChecked(true);
        } else {
            switchButton_call_911.setChecked(false);
        }
    }


}
