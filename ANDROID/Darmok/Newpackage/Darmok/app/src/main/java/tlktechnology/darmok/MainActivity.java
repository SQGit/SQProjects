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
import android.widget.Button;
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

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends DetailActivity implements AudioPlayer.Listener {
    TextView customtittle;
    private Session speechSession;
    private Transaction ttsTransaction;
    SharedPreferences sharedPreferences;
    SharedPreferences s_pref;
    Intent intent;
    Set<String> positive = new HashSet<String>();
    Set<String> Nagative = new HashSet<String>();

    Button btn_start;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Suissnord.otf");
        FontsManager.initFormAssets(this, "fonts/ques.otf");       //initialization
        FontsManager.changeFonts(this);
        customtittle = (TextView) findViewById(R.id.custom_tittle);
        customtittle.setTypeface(tf);


        ///////////////////////////////////
        positive.add("Yes");
        positive.add("yes");
        positive.add("yes yes yes");
        positive.add("Yes Yes Yes");
        positive.add("Yes yes yes");
        positive.add("Yes yes");

        ///////////////////////////////////
        Nagative.add("NO");
        Nagative.add("no");
        Nagative.add("No No");
        Nagative.add("no no");
        Nagative.add("No No No");
        Nagative.add("no no no");
        ///////////////////////////////////

        btn_start = (Button) findViewById(R.id.custom_btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {

                    Log.d("ins", "called");
                    flag = false;
                    btn_start.setText("Listening..");
                    speechSession = Session.Factory.session(getApplicationContext(), Configuration.SERVER_URI, Configuration.APP_KEY);
                    synthesizetts("Welcome My name is Darmok. Do you want to create a profile? Yes or No");
                    s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor edit = s_pref.edit();
                    edit.putString("ttsvalue", "0");
                    edit.apply();

                } else {
                    flag = true;
                    btn_start.setText("Start");
                    ttsTransaction.stopRecording();
                    ttsTransaction.cancel();


                }

            }
        });


       /* speechSession = Session.Factory.session(this, Configuration.SERVER_URI, Configuration.APP_KEY);
        synthesizetts("Welcome My name is Darmok. Do you want to create a profile? Yes or No");
        s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = s_pref.edit();
        edit.putString("ttsvalue", "0");
        edit.apply();*/
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

                Log.d("tag", "onError" + e.getMessage() + "" + s);
                String exception = e.getMessage();

                Errordialog(exception, s);
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
            Log.d("tag", "onRecognition"+recognition.getText());

            if (positive.contains(recognition.getText())) {
                intent = new Intent(getBaseContext(), CreateProfile.class);
                startActivity(intent);
            } else if (Nagative.contains(recognition.getText())) {
                onDestroy();
            } else {
                synthesizetts("Please say Yes \t or \t No");
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
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String value = sharedPreferences.getString("ttsvalue", "0");
                synthesizetts("Welcome My name is Darmok. Do you want to create a profile? Yes or No");
                s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = s_pref.edit();
                edit.putString("ttsvalue", value);
                edit.apply();

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
                //  synthesizetts("Thank You For Using Darmok");
                ttsTransaction.cancel();
                ttsTransaction.stopRecording();
                finish();

            }
        });
        dialog.show();

    }

    @Override
    public void onBackPressed() {

        finish();
        onDestroy();
    }
}
