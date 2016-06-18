package com.emergencysavior.emergencysaviorwatch;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;

/**
 * Created by RSA on 15-06-2016.
 */
public class Preference extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "tag";
    SwitchButton switchButton_audio, switchButton_gps, switchButton_message, switchButton_email, switchButton_call_911, switch_safe_walk_duration, switch_real_time_duration;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.preference_activity, container, false);
        switchButton_audio = (SwitchButton) v.findViewById(R.id.switch_audio);
        switchButton_gps = (SwitchButton) v.findViewById(R.id.switch_gps);
        switchButton_message = (SwitchButton) v.findViewById(R.id.switch_message);
        switchButton_email = (SwitchButton) v.findViewById(R.id.switch_email);
        switchButton_call_911 = (SwitchButton) v.findViewById(R.id.switch_call_911);
        switchButton_audio.setOnCheckedChangeListener(this);
        switchButton_gps.setOnCheckedChangeListener(this);
        switchButton_message.setOnCheckedChangeListener(this);
        switchButton_email.setOnCheckedChangeListener(this);
        switchButton_call_911.setOnCheckedChangeListener(this);

        onlinechnaged();
        return v;
    }

    private void onlinechnaged() {

        SharedPreferences pref = getActivity().getPreferences(0);
        String audiostatus = pref.getString("audiostatus", "empty");
        String gpsstatus = pref.getString("gpsstatus", "empty");
        String messagestatus = pref.getString("messagestatus", "empty");
        String emailstatus = pref.getString("emailstatus", "empty");
        String call911status = pref.getString("call911status", "empty");

        Log.d("tag", "str_audio==>" + audiostatus);
        Log.d("tag", "str_gps==>" + gpsstatus);
        Log.d("tag", "str_message==>" + messagestatus);
        Log.d("tag", "str_email==>" + emailstatus);
        Log.d("tag", "str_call_911==>" + call911status);

        if (audiostatus.equals("true")) {
            switchButton_audio.setChecked(true);
        } else {
            switchButton_audio.setChecked(false);
        }
        if (gpsstatus.equals("true")) {
            switchButton_gps.setChecked(true);
        } else {
            switchButton_gps.setChecked(false);
        }
        if (messagestatus.equals("true")) {
            switchButton_message.setChecked(true);
        } else {
            switchButton_message.setChecked(false);
        }
        if (emailstatus.equals("true")) {
            switchButton_email.setChecked(true);
        } else {
            switchButton_email.setChecked(false);
        }
        if (call911status.equals("true")) {
            switchButton_call_911.setChecked(true);
        } else {
            switchButton_call_911.setChecked(false);
        }


    }

    public static Preference newInstance(String text) {
        Preference f = new Preference();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {

            case R.id.switch_audio:

                if (isChecked) {
                    SharedPreferences pref = getActivity().getPreferences(0);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.remove("audiostatus");
                    edit.putString("audiostatus", "" + isChecked);
                    Log.d("tag", "str_audio==>" + isChecked);

                    edit.apply();

                } else {
                    SharedPreferences pref = getActivity().getPreferences(0);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.remove("audiostatus");
                    edit.putString("audiostatus", "" + isChecked);
                    edit.apply();
                }

                Toast.makeText(getActivity(), "call 911", Toast.LENGTH_LONG).show();
                break;
            case R.id.switch_gps:
                if (isChecked) {

                    SharedPreferences pref = getActivity().getPreferences(0);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.remove("gpsstatus");
                    edit.putString("gpsstatus", "" + isChecked);
                    Log.d("tag", "str_gps==>" + isChecked);

                    edit.apply();
                    showSettingsAlert();

                } else {
                    SharedPreferences pref = getActivity().getPreferences(0);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.remove("gpsstatus");
                    edit.putString("gpsstatus", "" + isChecked);
                    edit.apply();

                }
                break;
            case R.id.switch_message:
                if (isChecked) {
                    SharedPreferences pref = getActivity().getPreferences(0);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.remove("messagestatus");
                    edit.putString("messagestatus", "" + isChecked);
                    edit.apply();
                    Log.d("tag", "str_message==>" + isChecked);


                } else {
                    SharedPreferences pref = getActivity().getPreferences(0);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.remove("messagestatus");
                    edit.putString("messagestatus", "" + isChecked);
                    edit.apply();

                }

                break;
            case R.id.switch_email:
                if (isChecked) {
                    SharedPreferences pref = getActivity().getPreferences(0);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.remove("emailstatus");
                    edit.putString("emailstatus", "" + isChecked);
                    edit.apply();
                    Log.d("tag", "str_email==>" + isChecked);


                } else {
                    SharedPreferences pref = getActivity().getPreferences(0);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.remove("emailstatus");
                    edit.putString("emailstatus", "" + isChecked);
                    edit.apply();
                }
                break;
            case R.id.switch_call_911:
                if (isChecked) {

                    SharedPreferences pref = getActivity().getPreferences(0);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.remove("call911status");
                    edit.putString("call911status", "" + isChecked);
                    edit.apply();
                    Log.d("tag", "str_call_911==>" + isChecked);
                } else {

                    SharedPreferences pref = getActivity().getPreferences(0);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.remove("call911status");
                    edit.putString("call911status", "" + isChecked);
                    edit.apply();
                }
                break;
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                dialog.cancel();
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

}

