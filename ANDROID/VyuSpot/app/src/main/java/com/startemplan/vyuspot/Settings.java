package com.startemplan.vyuspot;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import android.preference.SwitchPreference;
import android.preference.TwoStatePreference;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Created by Salman on 2/22/2016.
 */
public class Settings extends PreferenceActivity {

    PreferenceManager prefm = getPreferenceManager();
    public TwoStatePreference switchi;

    GoogleCloudMessaging gcmg;
    Register reg = new Register();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

        SwitchPreference pushstatus = (SwitchPreference) findPreference("push");

        switchi = (TwoStatePreference) findPreference("push");

        switchi.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {


                boolean status = ((Boolean) newValue).booleanValue();

          /*      if (prefm.getSharedPreferences().getBoolean("push", true)){
                    // Your switch is on
                    Toast.makeText(getApplicationContext(),"Notification Enabled",Toast.LENGTH_LONG).show();
                } else {
                    // Your switch is off
                    Toast.makeText(getApplicationContext(),"Notification Disabled",Toast.LENGTH_LONG).show();
                }
*/
                if(status)
                {

                   // Toast.makeText(getApplicationContext(),"status Enabled",Toast.LENGTH_LONG).show();
                    enableBroadcastReceiver();
                }

                else
                {
                    //Toast.makeText(getApplicationContext(), "stsatus Disabled", Toast.LENGTH_LONG).show();

                    disableBroadcastReceiver();





                }
                return true;
            }
        });





    }



    public void disableBroadcastReceiver(){
        ComponentName receiver = new ComponentName(this, GcmBroadcastReceiver.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        Toast.makeText(this, "Notification Disabled", Toast.LENGTH_SHORT).show();
    }


    public void enableBroadcastReceiver(){
        ComponentName receiver = new ComponentName(this, GcmBroadcastReceiver.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        Toast.makeText(this, "Notification Enabled", Toast.LENGTH_SHORT).show();
    }





}
