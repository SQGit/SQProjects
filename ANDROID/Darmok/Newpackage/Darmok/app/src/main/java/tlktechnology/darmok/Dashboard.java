package tlktechnology.darmok;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
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
public class Dashboard extends DetailActivity implements AudioPlayer.Listener {

    public static final String EXTRA_IS_TRANSPARENT = "is_transparent";
    private boolean isTransparent;

    TextView customtittle;
    private Session speechSession;
    private Transaction ttsTransaction;
    SharedPreferences sharedPreferences;
    SharedPreferences s_pref;
    Intent intent;
    boolean flag = true;
    Button btn_query_darmok, btn_admin_mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isTransparent = getIntent().getBooleanExtra(EXTRA_IS_TRANSPARENT, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Suissnord.otf");
        FontsManager.initFormAssets(this, "fonts/MONTSERRAT-REGULAR.OTF");       //initialization
        FontsManager.changeFonts(this);
        customtittle = (TextView) findViewById(R.id.custom_tittle);
        customtittle.setTypeface(tf);

        btn_query_darmok = (Button) findViewById(R.id.custom_btn_query_darmok);
        btn_admin_mode = (Button) findViewById(R.id.custom_btn_admin_mode);

        speechSession = Session.Factory.session(this, Configuration.SERVER_URI, Configuration.APP_KEY);
        synthesizetts("welcome, I am Darmok, what can I do for you? Please touch any one mode");
        s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = s_pref.edit();
        edit.putString("ttsvalue", "0");
        edit.apply();
        btn_query_darmok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    Log.d("ins", "called");
                    flag = false;

                    btn_query_darmok.setText("Listening..");
                    s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor edit = s_pref.edit();
                    edit.remove("ttsvalue");
                    edit.putString("ttsvalue", "1");
                    edit.apply();
                    btn_admin_mode.setEnabled(false);
                    Toast.makeText(getApplicationContext(),"Under Progress",Toast.LENGTH_LONG).show();
                } else {
                    flag = true;
                    s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor edit = s_pref.edit();
                    edit.remove("ttsvalue");
                    edit.apply();
                    btn_query_darmok.setText("Query Darmok");
                    btn_admin_mode.setEnabled(true);
                }
            }
        });
        btn_admin_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = s_pref.edit();
                edit.remove("ttsvalue");
                edit.putString("ttsvalue", "2");
                edit.apply();

                synthesizetts("Please Say Darmok Admin function");
            }
        });

        setStatusBar();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    protected void setStatusBar() {
        if (isTransparent) {
            StatusBarUtil.setTransparent(this);
        } else {
            StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
        }
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
                // recognize();
                break;
            case "1":
               // recognize();
                break;
            case "2":
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
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String value = sharedPreferences.getString("ttsvalue", "");
            Log.d("tag", "value" + value);
            s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor edit = s_pref.edit();

            switch (value){
                case "0":
                    break;
                case "1":
                    Toast.makeText(getApplicationContext(),"On Progress",Toast.LENGTH_LONG).show();
                    break;
                case  "2":
                    if (recognition.getText().equals("Arch")){

                        intent = new Intent(getApplicationContext(),AdminMode.class);
                        startActivity(intent);


                    }else {
                        String s="Please say correct Darmok Admin FUNCTION ?Please try again";
                        String tittle="Alert";
                        Errordialog(tittle, s);
                    }
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
    private void Errordialog(String tittle, String s) {
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
        customtittle.setText(tittle);
        custom_body.setText(s);

        custom_btn_try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = s_pref.edit();
                edit.remove("ttsvalue");
                edit.putString("ttsvalue", "2");
                edit.apply();
                synthesizetts("Please Say Darmok Admin function");

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
                intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);


            }
        });
        dialog.show();

    }

    @Override
    public void onBackPressed() {

        intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}
