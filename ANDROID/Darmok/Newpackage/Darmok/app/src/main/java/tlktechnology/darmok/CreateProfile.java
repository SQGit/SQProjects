package tlktechnology.darmok;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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
        options.setLanguage(new Language("eng-IND"));
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
                recognize();
                break;
        }
    }
    private void recognize() {
        Transaction.Options options = new Transaction.Options();
        options.setRecognitionType(RecognitionType.DICTATION);
        options.setDetection(DetectionType.Short);
        options.setLanguage(new Language("eng-IND"));
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
                    synthesizetts("Please say Last Name");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "1");
                    edit.apply();
                    break;
                case "1":
                    edt_Last_Name.setText(recognition.getText());
                    synthesizetts("Please say Middle Name");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "2");
                    edit.apply();
                    break;
                case "2":
                    edt_Middle_Name.setText(recognition.getText());
                    synthesizetts("Please say Phone Number");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "3");
                    edit.apply();
                    break;
                case "3":
                    edt_Phone_number.setText(recognition.getText());
                    synthesizetts("Please Say Address");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "4");
                    edit.apply();
                    break;
                case "4":
                    edt_Address.setText(recognition.getText());
                    synthesizetts("Please Say Favorite Restaturant");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "5");
                    edit.apply();
                    break;
                case "5":
                    edt_Favorite_Restaturant.setText(recognition.getText());
                    synthesizetts("Please Say Spouse First Name ");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "6");
                    edit.apply();
                    break;

                case "6":
                    edt_Spouse_first_name.setText(recognition.getText());
                    synthesizetts("Please Say Favorite Friend ");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "7");
                    edit.apply();
                    break;
                case "7":
                    edt_Favorite_friend.setText(recognition.getText());
                    synthesizetts("Please Say Number of Children ");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "8");
                    edit.apply();
                    break;

                case "8":
                    edt_Number_of_children.setText(recognition.getText());
                    synthesizetts("Please Say Child Name");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "9");
                    edit.apply();
                    break;

                case "9":
                    edt_Child_name.setText(recognition.getText());
                    synthesizetts("Please Say WorkPlace");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "10");
                    edit.apply();
                    break;
                case "10":
                    edt_Favorite_friend.setText(recognition.getText());
                    synthesizetts("Please Say WorkLocation");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "11");
                    edit.apply();
                    break;
                case "11":
                    edt_Favorite_sport.setText(recognition.getText());
                    synthesizetts("Please Say Favorite Spot");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "12");
                    edit.apply();
                    break;
                case "12":
                    edt_Favorite_pastime.setText(recognition.getText());
                    synthesizetts("Please Say Favorite Pastime");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "13");
                    edit.apply();
                    break;
                case "13":
                    edt_Birthday.setText(recognition.getText());
                    synthesizetts("Please Say Birthday");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "14");
                    edit.apply();
                    break;
                case "14":
                    edt_Authorization_code.setText(recognition.getText());
                    synthesizetts("Please Say Authorization Code");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "15");
                    edit.apply();
                    break;
                case "15":
                    synthesizetts("Please Say Authorization Code");
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "15");
                    edit.apply();
                    break;
            }


/*

            if (positive.contains(recognition.getText())) {
                intent = new Intent(getBaseContext(), CreateProfile.class);
                startActivity(intent);
            } else if (Nagative.contains(recognition.getText())) {
                onDestroy();
            } else {
                synthesizetts("Please say Yes \t or \t No");
            }*/
        }

        @Override
        public void onSuccess(Transaction transaction, String s) {
            Log.d("tag", "onSuccess" + s);
        }

        @Override
        public void onError(Transaction transaction, String s, TransactionException e) {
            Log.d("tag", "onError" + e.getMessage() + "" + s);
            String exception = e.getMessage();

           // Errordialog(exception, s);
        }
    };
}
