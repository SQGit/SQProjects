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
import android.widget.EditText;
import android.widget.TextView;

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
 * Created by RSA on 10-08-2016.
 */
public class Edit_Profile extends DetailActivity implements AudioPlayer.Listener, View.OnClickListener {

    public static final String EXTRA_IS_TRANSPARENT = "is_transparent";
    private boolean isTransparent;
    TextView customtittle;
    private Session speechSession;
    private Transaction ttsTransaction;
    SharedPreferences sharedPreferences;
    SharedPreferences s_pref;

    Button btn_update;
    Intent intent;
    EditText edt_First_Name, edt_Last_Name, edt_Middle_Name, edt_Phone_number, edt_Address, edt_Favorite_Restaturant, edt_Spouse_first_name,
            edt_Favorite_friend, edt_Number_of_children, edt_Child_name, edt_Workplace, edt_Work_location, edt_Favorite_sport, edt_Favorite_pastime, edt_Birthday, edt_Authorization_code;
    String str_First_Name, str_Last_Name, str_Middle_Name, str_Phone_number, str_Address, str_Favorite_Restaturant, str_Spouse_first_name,
            str_Favorite_friend, str_Number_of_children, str_Child_name, str_Workplace, str_Work_location, str_Favorite_sport, str_Favorite_pastime, str_Birthday, str_Authorization_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isTransparent = getIntent().getBooleanExtra(EXTRA_IS_TRANSPARENT, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Suissnord.otf");
        FontsManager.initFormAssets(this, "fonts/ques.otf");       //initialization
        FontsManager.changeFonts(this);
        customtittle = (TextView) findViewById(R.id.custom_tittle);
        customtittle.setTypeface(tf);
        setStatusBar();

        speechSession = Session.Factory.session(this, Configuration.SERVER_URI, Configuration.APP_KEY);

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

        btn_update= (Button) findViewById(R.id.btn_update);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        str_First_Name = sharedPreferences.getString("str_First_Name", "");
        str_Last_Name = sharedPreferences.getString("str_Last_Name", "");
        str_Middle_Name = sharedPreferences.getString("str_Middle_Name", "");
        str_Phone_number = sharedPreferences.getString("str_Phone_number", "");
        str_Address = sharedPreferences.getString("str_Address", "");
        str_Favorite_Restaturant = sharedPreferences.getString("str_Favorite_Restaturant", "");
        str_Spouse_first_name = sharedPreferences.getString("str_Spouse_first_name", "");
        str_Favorite_friend = sharedPreferences.getString("str_Favorite_friend", "");
        str_Number_of_children = sharedPreferences.getString("str_Number_of_children", "");
        str_Child_name = sharedPreferences.getString("str_Child_name", "");
        str_Workplace = sharedPreferences.getString("str_Workplace", "");
        str_Work_location = sharedPreferences.getString("str_Work_location", "");
        str_Favorite_sport = sharedPreferences.getString("str_Favorite_sport", "");
        str_Favorite_pastime = sharedPreferences.getString("str_Favorite_pastime", "");
        str_Birthday = sharedPreferences.getString("str_Birthday", "");
        str_Authorization_code = sharedPreferences.getString("str_Authorization_code", "");

        edt_First_Name.setText(str_First_Name);
        edt_Last_Name.setText(str_Last_Name);
        edt_Middle_Name.setText(str_Middle_Name);
        edt_Phone_number.setText(str_Phone_number);
        edt_Address.setText(str_Address);
        edt_Favorite_Restaturant.setText(str_Favorite_Restaturant);
        edt_Spouse_first_name.setText(str_Spouse_first_name);
        edt_Favorite_friend.setText(str_Favorite_friend);
        edt_Number_of_children.setText(str_Number_of_children);
        edt_Child_name.setText(str_Child_name);
        edt_Workplace.setText(str_Workplace);
        edt_Work_location.setText(str_Work_location);
        edt_Favorite_sport.setText(str_Favorite_sport);
        edt_Favorite_pastime.setText(str_Favorite_pastime);
        edt_Birthday.setText(str_Birthday);
        edt_Authorization_code.setText(str_Authorization_code);


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


    @Override
    public void onClick(View v) {

        s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = s_pref.edit();
        switch (v.getId()) {
            case R.id.edt_first_name:
                Log.d("tag", "firstname");
                synthesizetts("Please Say Your First Name");
                edit.putString("ttsvalue", "0");
                edit.apply();
                // do your code
                break;
            case R.id.edt_last_name:
                synthesizetts("Please Say Your Last Name");
                edit.putString("ttsvalue", "1");
                edit.apply();
                Log.d("tag", "edt_last_name");
                break;
            case R.id.edt_middle_name:
                synthesizetts("Please Say Your Middle Name");
                edit.putString("ttsvalue", "2");
                edit.apply();
                Log.d("tag", "edt_middle_name");
                break;
            case R.id.edt_phone_number:
                synthesizetts("Please Say Your Phone Number");
                edit.putString("ttsvalue", "3");
                edit.apply();
                Log.d("tag", "edt_phone_number");
                // do your code
                break;
            case R.id.edt_address:
                synthesizetts("Please Say Your Address");
                edit.putString("ttsvalue", "4");
                edit.apply();
                Log.d("tag", "edt_address");
                break;

            case R.id.edt_favorite_restaturant:
                synthesizetts("Please Say Your Favorite Restaturant");
                edit.putString("ttsvalue", "5");
                edit.apply();
                Log.d("tag", "edt_favorite_restaturant");
                break;
            case R.id.edt_spouse_first_name:
                synthesizetts("Please Say Your Spouse First Name");
                edit.putString("ttsvalue", "6");
                edit.apply();
                Log.d("tag", "edt_spouse_first_name");
                // do your code
                break;

            case R.id.edt_favorite_friend:
                synthesizetts("Please Say Your Favorite Friend ");
                edit.putString("ttsvalue", "7");
                edit.apply();
                Log.d("tag", "edt_favorite_friend");
                break;

            case R.id.edt_number_of_children:

                synthesizetts("Please Say Your Number Of children");
                edit.putString("ttsvalue", "8");
                edit.apply();
                Log.d("tag", "edt_number_of_children");
                break;

            case R.id.edt_child_name:

                synthesizetts("Please Say Your Child Name");
                edit.putString("ttsvalue", "9");
                edit.apply();
                Log.d("tag", "edt_child_name");
                // do your code
                break;

            case R.id.edt_Workplace:
                synthesizetts("Please Say Your WorkPlace");
                edit.putString("ttsvalue", "10");
                edit.apply();
                Log.d("tag", "edt_Workplace");
                break;
            case R.id.edt_Work_location:

                synthesizetts("Please Say Your WorkLocation");
                edit.putString("ttsvalue", "11");
                edit.apply();
                Log.d("tag", "edt_Work_location");
                break;
            case R.id.edt_favorite_sport:

                synthesizetts("Please Say Your Favorite Sport");
                edit.putString("ttsvalue", "12");
                edit.apply();
                Log.d("tag", "edt_favorite_sport");
                // do your code
                break;

            case R.id.edt_favorite_pastime:

                synthesizetts("Please Say Your Favorite Pastime");
                edit.putString("ttsvalue", "13");
                edit.apply();
                Log.d("tag", "edt_favorite_pastime");
                break;

            case R.id.edt_birthday:

                synthesizetts("Please Say Your Birthday");
                edit.putString("ttsvalue", "14");
                edit.apply();
                Log.d("tag", "edt_birthday");
                break;
            case R.id.edt_Authorization_code:

                synthesizetts("Please Say Your Authrization Code");
                edit.putString("ttsvalue", "15");
                edit.apply();
                Log.d("tag", "edt_Authorization_code");
                break;

            case R.id.btn_update:

                intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
                break;

            default:
                break;
        }

    }

    //onsysthesis

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

    //onsysthesis


    @Override
    public void onBeginPlaying(AudioPlayer audioPlayer, Audio audio) {

    }

    @Override
    public void onFinishedPlaying(AudioPlayer audioPlayer, Audio audio) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String value = sharedPreferences.getString("ttsvalue", "");


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
        }
    }
    // on reconize
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
                    edit.apply();
                 //   messagedialog();
                    break;
                case "1":

                    edt_Last_Name.setText(recognition.getText());

                    str_Last_Name = edt_Last_Name.getText().toString();
                    edit.remove("str_Last_Name");
                    edit.putString("str_Last_Name", str_Last_Name);
                    edit.apply();
              //      messagedialog();
                    break;
                case "2":

                    edt_Middle_Name.setText(recognition.getText());
                    str_Middle_Name = edt_Middle_Name.getText().toString();
                    edit.remove("str_Middle_Name");
                    edit.putString("str_Middle_Name", str_Middle_Name);
                    edit.apply();
              //      messagedialog();
                    break;
                case "3":

                    edt_Phone_number.setText(recognition.getText());
                    str_Phone_number = edt_Phone_number.getText().toString();
                    edit.remove("str_Phone_number");
                    edit.putString("str_Phone_number", str_Phone_number);
                    edit.apply();
              //      messagedialog();
                    break;
                case "4":

                    edt_Address.setText(recognition.getText());
                    str_Address = edt_Address.getText().toString();
                    edit.remove("str_Address");
                    edit.putString("str_Address", str_Address);
                    edit.apply();
              //      messagedialog();
                    break;
                case "5":

                    edt_Favorite_Restaturant.setText(recognition.getText());
                    str_Favorite_Restaturant = edt_Favorite_Restaturant.getText().toString();
                    edit.remove("str_Favorite_Restaturant");
                    edit.putString("str_Favorite_Restaturant", str_Favorite_Restaturant);
                    edit.apply();
                //    messagedialog();
                    break;
                case "6":

                    edt_Spouse_first_name.setText(recognition.getText());
                    str_Spouse_first_name = edt_Spouse_first_name.getText().toString();
                    edit.remove("str_Spouse_first_name");
                    edit.putString("str_Spouse_first_name", str_Spouse_first_name);
                    edit.apply();
               //     messagedialog();
                    break;
                case "7":

                    edt_Favorite_friend.setText(recognition.getText());
                    str_Favorite_friend = edt_Favorite_friend.getText().toString();
                    edit.remove("str_Favorite_friend");
                    edit.putString("str_Favorite_friend", str_Favorite_friend);
                    edit.apply();
                 //   messagedialog();
                    break;
                case "8":

                    edt_Number_of_children.setText(recognition.getText());
                    str_Number_of_children = edt_Number_of_children.getText().toString();
                    edit.remove("str_Number_of_children");
                    edit.putString("str_Number_of_children", str_Number_of_children);
                    edit.apply();
                //    messagedialog();
                    break;
                case "9":

                    edt_Child_name.setText(recognition.getText());
                    str_Child_name = edt_Child_name.getText().toString();
                    edit.remove("str_Child_name");
                    edit.putString("str_Child_name", str_Child_name);
                    edit.apply();
                //    messagedialog();
                    break;
                case "10":

                    edt_Workplace.setText(recognition.getText());
                    str_Workplace = edt_Workplace.getText().toString();
                    edit.remove("str_Workplace");
                    edit.putString("str_Workplace", str_Workplace);
                    edit.apply();
               //     messagedialog();
                    break;
                case "11":

                    edt_Work_location.setText(recognition.getText());
                    str_Work_location = edt_Work_location.getText().toString();
                    edit.remove("str_Work_location");
                    edit.putString("str_Work_location", str_Work_location);
                    edit.apply();
                //    messagedialog();
                    break;
                case "12":

                    edt_Favorite_sport.setText(recognition.getText());
                    str_Favorite_sport = edt_Favorite_sport.getText().toString();
                    edit.remove("str_Favorite_sport");
                    edit.putString("str_Favorite_sport", str_Favorite_sport);
                    edit.apply();
                 //   messagedialog();
                    break;
                case "13":

                    edt_Favorite_pastime.setText(recognition.getText());
                    str_Favorite_pastime = edt_Favorite_pastime.getText().toString();
                    edit.remove("str_Favorite_pastime");
                    edit.putString("str_Favorite_pastime", str_Favorite_pastime);
                    edit.apply();
               //     messagedialog();
                    break;
                case "14":

                    edt_Birthday.setText(recognition.getText());
                    str_Birthday = edt_Birthday.getText().toString();
                    edit.remove("str_Birthday");
                    edit.putString("str_Birthday", str_Birthday);
                    edit.apply();
                //    messagedialog();
                    break;
                case "15":

                    edt_Authorization_code.setText(recognition.getText());
                    str_Authorization_code = edt_Authorization_code.getText().toString();
                    edit.remove("str_Authorization_code");
                    edit.putString("str_Authorization_code", str_Authorization_code);
                    edit.apply();
                //    messagedialog();
                    break;
                default:

                 //   messagedialog();
            }

        }
        @Override
        public void onSuccess(Transaction transaction, String s) {

        }

        @Override
        public void onError(Transaction transaction, String s, TransactionException e) {
            Log.d("tag", "onError" + e.getMessage() + "" + s);
            String exception = e.getMessage();

           Errordialog(exception, s);
        }
    };


    private void Errordialog(String exception, final String s) {
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
        custom_btn_ok.setVisibility(View.GONE);

        custom_btn_try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                synthesizetts(s);
                s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = s_pref.edit();
                edit.remove("ttsvalue");
                edit.apply();

            }
        });
       /* custom_btn_ok.setOnClickListener(new View.OnClickListener() {
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
        });*/
        dialog.show();

    }



    // on reconize
}
