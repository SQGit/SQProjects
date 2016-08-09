package tlktechnology.darmok;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nuance.speechkit.Audio;
import com.nuance.speechkit.AudioPlayer;
import com.nuance.speechkit.DetectionType;
import com.nuance.speechkit.Language;
import com.nuance.speechkit.Recognition;
import com.nuance.speechkit.RecognitionType;
import com.nuance.speechkit.Session;
import com.nuance.speechkit.Transaction;
import com.nuance.speechkit.TransactionException;
import com.sloop.fonts.FontsManager;

/**
 * Created by RSA on 06-08-2016.
 */
public class CreateProfile extends DetailActivity implements AudioPlayer.Listener {
    TextView customtittle;
    private Session speechSession;
    private Transaction ttsTransaction;
    SharedPreferences sharedPreferences;
    SharedPreferences s_pref;
    Intent intent;
    EditText edt_First_Name, edt_Last_Name, edt_Middle_Name, edt_Phone_number, edt_Address, edt_Favorite_Restaturant, edt_Spouse_first_name,
            edt_Favorite_friend, edt_Number_of_children, edt_Child_name, edt_Workplace, edt_Work_location, edt_Favorite_sport, edt_Favorite_pastime, edt_Birthday, edt_Authorization_code;
    String str_First_Name, str_Last_Name, str_Middle_Name, str_Phone_number, str_Address, str_Favorite_Restaturant, str_Spouse_first_name,
            str_Favorite_friend, str_Number_of_children, str_Child_name, str_Workplace, str_Work_location, str_Favorite_sport, str_Favorite_pastime, str_Birthday, str_Authorization_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_profile);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Suissnord.otf");
        FontsManager.initFormAssets(this, "fonts/ques.otf");       //initialization
        FontsManager.changeFonts(this);
        customtittle = (TextView) findViewById(R.id.custom_tittle);
        customtittle.setTypeface(tf);


        edt_First_Name = (EditText) findViewById(R.id.edt_first_name);
        edt_Last_Name = (EditText) findViewById(R.id.edt_last_name);
        edt_Middle_Name = (EditText) findViewById(R.id.edt_middle_name);
        edt_Phone_number = (EditText) findViewById(R.id.edt_phone_number);
        edt_Address = (EditText) findViewById(R.id.edt_address);
        edt_Favorite_Restaturant = (EditText) findViewById(R.id.edt_favorite_restaturant);
        edt_Spouse_first_name = (EditText) findViewById(R.id.edt_spouse_first_name);
        edt_Favorite_friend = (EditText) findViewById(R.id.edt_favorite_friend);
        edt_Number_of_children = (EditText) findViewById(R.id.edt_number_of_children);
        edt_Child_name = (EditText) findViewById(R.id.edt_child_name);
        edt_Workplace = (EditText) findViewById(R.id.edt_Workplace);
        edt_Work_location = (EditText) findViewById(R.id.edt_Work_location);
        edt_Favorite_sport = (EditText) findViewById(R.id.edt_favorite_sport);
        edt_Favorite_pastime = (EditText) findViewById(R.id.edt_favorite_pastime);
        edt_Birthday = (EditText) findViewById(R.id.edt_birthday);
        edt_Authorization_code = (EditText) findViewById(R.id.edt_Authorization_code);


        speechSession = Session.Factory.session(this, Configuration.SERVER_URI, Configuration.APP_KEY);
        synthesizetts("Please Say Your First Name");
        s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = s_pref.edit();
        edit.putString("ttsvalue", "0");
        edit.apply();


    }

    private void synthesizetts(String tts) {
        speechSession.getAudioPlayer().setListener(this);
        Transaction.Options options = new Transaction.Options();
        options.setLanguage(new Language("eng-USA"));
        ttsTransaction = speechSession.speakString(tts, options, new Transaction.Listener() {
            @Override
            public void onAudio(Transaction transaction, Audio audio) {

                Log.d("tag", "onAudio");

            }

            @Override
            public void onSuccess(Transaction transaction, String s) {

                Log.d("tag", "onSuccess");

            }

            @Override
            public void onError(Transaction transaction, String s, TransactionException e) {

                Log.d("tag", "onError" + s);
            }
        });
    }


    @Override
    public void onBeginPlaying(AudioPlayer audioPlayer, Audio audio) {

    }

    @Override
    public void onFinishedPlaying(AudioPlayer audioPlayer, Audio audio) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String value = sharedPreferences.getString("ttsvalue", "0");
        switch (value) {
            case "0":
                recognize();
                break;
            case "1":
                recognize();
                break;
            case "2":
                recognize();
                break;
            case "3":
                recognize();
                break;
            case "4":
                recognize();
                break;
            case "5":
                recognize();
                break;
            case "6":
                recognize();
                break;
            case "7":
                recognize();
                break;
            case "8":
                recognize();
                break;
            case "9":
                recognize();
                break;
            case "10":
                recognize();
                break;
            case "11":
                recognize();
                break;
            case "12":
                recognize();
                break;
            case "13":
                recognize();
                break;
            case "14":
                recognize();
                break;
            case "15":
                recognize();
                break;
            case "16":

                messagedialog();
                break;
        }
    }

    private void recognize() {
        Transaction.Options options = new Transaction.Options();
        options.setRecognitionType(RecognitionType.DICTATION);
        options.setDetection(DetectionType.Short);
        options.setLanguage(new Language("eng-USA"));

        Transaction recoTransaction = speechSession.recognize(options, recoListener);
    }

    private Transaction.Listener recoListener = new Transaction.Listener() {
        @Override
        public void onStartedRecording(Transaction transaction) {

            Log.d("tag", "onStartedRecording");
        }

        @Override
        public void onFinishedRecording(Transaction transaction) {
            Log.d("tag", "onFinishedRecording");
        }

        @Override
        public void onRecognition(Transaction transaction, Recognition recognition) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String value = sharedPreferences.getString("ttsvalue", "");
            Log.d("tag", "value" + value);
            s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor edit = s_pref.edit();

            switch (value) {

                case "0":
                    edt_First_Name.setText(recognition.getText());
                    str_First_Name = edt_First_Name.getText().toString();
                    edit.remove("str_First_Name");
                    edit.putString("str_First_Name", str_First_Name);
                    edt_Last_Name.requestFocus();
                    synthesizetts("Please say Last Name");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "1");
                    edit.apply();
                    break;
                case "1":
                    edt_Last_Name.setText(recognition.getText());
                    str_Last_Name = edt_Last_Name.getText().toString();
                    edit.remove("str_Last_Name");
                    edit.putString("str_Last_Name", str_Last_Name);
                    edt_Middle_Name.requestFocus();
                    synthesizetts("Please say Middle Name");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "2");
                    edit.apply();
                    break;
                case "2":
                    edt_Middle_Name.setText(recognition.getText());
                    str_Middle_Name = edt_Middle_Name.getText().toString();
                    edit.remove("str_Middle_Name");
                    edit.putString("str_Middle_Name", str_Middle_Name);
                    edt_Phone_number.requestFocus();
                    synthesizetts("Please say Phone Number");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "3");
                    edit.apply();
                    break;
                case "3":
                    edt_Phone_number.setText(recognition.getText());
                    str_Phone_number = edt_Phone_number.getText().toString();
                    edit.remove("str_Phone_number");
                    edit.putString("str_Phone_number", str_Phone_number);
                    edt_Address.requestFocus();
                    synthesizetts("Please Say Address");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "4");
                    edit.apply();
                    break;
                case "4":
                    edt_Address.setText(recognition.getText());
                    str_Address = edt_Address.getText().toString();
                    edit.remove("str_Address");
                    edit.putString("str_Address", str_Address);
                    edt_Favorite_Restaturant.requestFocus();
                    synthesizetts("Please Say Favorite Restaturant");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "5");
                    edit.apply();
                    break;
                case "5":
                    edt_Favorite_Restaturant.setText(recognition.getText());
                    str_Favorite_Restaturant = edt_Favorite_Restaturant.getText().toString();
                    edit.remove("str_Favorite_Restaturant");
                    edit.putString("str_Favorite_Restaturant", str_Favorite_Restaturant);
                    edt_Spouse_first_name.requestFocus();
                    synthesizetts("Please Say Spouse First Name ");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "6");
                    edit.apply();
                    break;

                case "6":
                    edt_Spouse_first_name.setText(recognition.getText());
                    str_Spouse_first_name = edt_Spouse_first_name.getText().toString();
                    edit.remove("str_Spouse_first_name");
                    edit.putString("str_Spouse_first_name", str_Spouse_first_name);
                    edt_Favorite_friend.requestFocus();
                    synthesizetts("Please Say Favorite Friend ");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "7");
                    edit.apply();
                    break;
                case "7":
                    edt_Favorite_friend.setText(recognition.getText());
                    str_Favorite_friend = edt_Favorite_friend.getText().toString();
                    edit.remove("str_Favorite_friend");
                    edit.putString("str_Favorite_friend", str_Favorite_friend);
                    edt_Number_of_children.requestFocus();
                    synthesizetts("Please Say Number of Children ");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "8");
                    edit.apply();
                    break;

                case "8":
                    edt_Number_of_children.setText(recognition.getText());
                    str_Number_of_children = edt_Number_of_children.getText().toString();
                    edit.remove("str_Number_of_children");
                    edit.putString("str_Number_of_children", str_Number_of_children);
                    edt_Child_name.requestFocus();
                    synthesizetts("Please Say Child Name");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "9");
                    edit.apply();
                    break;

                case "9":
                    edt_Child_name.setText(recognition.getText());
                    str_Child_name = edt_Child_name.getText().toString();
                    edit.remove("str_Child_name");
                    edit.putString("str_Child_name", str_Child_name);
                    edt_Workplace.requestFocus();
                    synthesizetts("Please Say WorkPlace");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "10");
                    edit.apply();
                    break;
                case "10":
                    edt_Workplace.setText(recognition.getText());
                    str_Workplace = edt_Workplace.getText().toString();
                    edit.remove("str_Workplace");
                    edit.putString("str_Workplace", str_Workplace);
                    edt_Work_location.requestFocus();
                    synthesizetts("Please Say WorkLocation");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "11");
                    edit.apply();
                    break;
                case "11":
                    edt_Work_location.setText(recognition.getText());
                    str_Work_location = edt_Work_location.getText().toString();
                    edit.remove("str_Work_location");
                    edit.putString("str_Work_location", str_Work_location);
                    edt_Favorite_sport.requestFocus();
                    synthesizetts("Please Say Favorite Spot");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "12");
                    edit.apply();
                    break;
                case "12":
                    edt_Favorite_sport.setText(recognition.getText());
                    str_Favorite_sport = edt_Favorite_sport.getText().toString();
                    edit.remove("str_Favorite_sport");
                    edit.putString("str_Favorite_sport", str_Favorite_sport);
                    edt_Favorite_pastime.requestFocus();
                    synthesizetts("Please Say Favorite Pastime");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "13");
                    edit.apply();
                    break;
                case "13":
                    edt_Favorite_pastime.setText(recognition.getText());
                    str_Favorite_pastime = edt_Favorite_pastime.getText().toString();
                    edit.remove("str_Favorite_pastime");
                    edit.putString("str_Favorite_pastime", str_Favorite_pastime);
                    edt_Birthday.requestFocus();
                    synthesizetts("Please Say your  Birthday");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "14");
                    edit.apply();
                    break;
                case "14":
                    edt_Birthday.setText(recognition.getText());

                    str_Birthday = edt_Birthday.getText().toString();
                    edit.remove("str_Birthday");
                    edit.putString("str_Birthday", str_Birthday);


                    edt_Authorization_code.requestFocus();
                    synthesizetts("Please Say Authorization Code");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "15");
                    edit.apply();
                    break;
                case "15":
                    edt_Authorization_code.setText(recognition.getText());

                    str_Authorization_code = edt_Authorization_code.getText().toString();
                    edit.remove("str_Authorization_code");
                    edit.putString("str_Authorization_code", str_Authorization_code);

                    edt_Authorization_code.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    synthesizetts("Here is the information I think I received from you. If you are not satisfied, go ahead and edit the form.Please press Edit or Done Button");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "16");
                    edit.apply();
                    break;

            }

        }

        @Override
        public void onSuccess(Transaction transaction, String s) {
            Log.d("tag", "onSuccess" + s);
        }

        @Override
        public void onError(Transaction transaction, String s, TransactionException e) {
            Log.d("tag", "onError" + e.getMessage() + "" + s);
            String exception = e.getMessage();

             Errordialog(exception, s);
        }
    };


    // message details
    private void messagedialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.customdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        TextView customtittle, custom_body;
        customtittle = (TextView) dialog.findViewById(R.id.custom_tittle);
        custom_body = (TextView) dialog.findViewById(R.id.custom_body);
        Button custom_btn_edit = (Button) dialog.findViewById(R.id.custom_btn_edit);
        Button custom_btn_ok = (Button) dialog.findViewById(R.id.custom_btn_submit);

        customtittle.setText("Confirmation");
        custom_body.setText("Please press Edit or Done");
        custom_btn_edit.setText("Edit");
        custom_btn_ok.setText("OK");


        custom_btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = s_pref.edit();
                edit.remove("ttsvalue");
                edit.apply();
                ttsTransaction.cancel();
                ttsTransaction.stopRecording();
                intent = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(intent);

            }
        });
        custom_btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = s_pref.edit();
                edit.remove("ttsvalue");
                edit.apply();

                intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
                //  synthesizetts("Thank You For Using Darmok");
                ttsTransaction.cancel();
                ttsTransaction.stopRecording();
                finish();

            }
        });
        dialog.show();

    }


    //message details


    // message warning details

    private void Errordialog(String exception, String s) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.customdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        TextView customtittle, custom_body;
        customtittle = (TextView) dialog.findViewById(R.id.custom_tittle);
        custom_body = (TextView) dialog.findViewById(R.id.custom_body);
        Button custom_btn_try_again = (Button) dialog.findViewById(R.id.custom_btn_edit);
        Button custom_btn_ok = (Button) dialog.findViewById(R.id.custom_btn_submit);
        customtittle.setText(exception);
        custom_body.setText(s);

        custom_btn_try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String value = sharedPreferences.getString("ttsvalue", "");
                Log.d("tag", "value" + value);
                s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = s_pref.edit();
                edit.putString("ttsvalue", value);
                edit.apply();
                synthesizetts("Please Say again");

            }
        });
        custom_btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = s_pref.edit();
                edit.remove("ttsvalue");
                edit.apply();
                ttsTransaction.cancel();
                ttsTransaction.stopRecording();
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);


            }
        });
        dialog.show();

    }




    // message warning details

}
