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

import java.util.HashSet;
import java.util.Set;

/**
 * Created by RSA on 06-08-2016.
 */
public class EditProfile extends DetailActivity implements AudioPlayer.Listener {


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

    Set<String> first_name = new HashSet<String>();
    Set<String> last_name = new HashSet<String>();
    Set<String> middle_name = new HashSet<String>();
    Set<String> phone_number = new HashSet<String>();
    Set<String> address = new HashSet<String>();
    Set<String> favorite_restaturant = new HashSet<String>();
    Set<String> spouse_first_name = new HashSet<String>();
    Set<String> favorite_friend = new HashSet<String>();
    Set<String> number_of_children = new HashSet<String>();
    Set<String> child_name = new HashSet<String>();
    Set<String> workplace = new HashSet<String>();
    Set<String> work_location = new HashSet<String>();
    Set<String> favorite_sport = new HashSet<String>();
    Set<String> favorite_pastime = new HashSet<String>();
    Set<String> bithday = new HashSet<String>();
    Set<String> authorization_Code = new HashSet<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);


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



        speechSession = Session.Factory.session(this, Configuration.SERVER_URI, Configuration.APP_KEY);
        synthesizetts("Please Say Which one edit");
        s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = s_pref.edit();
        edit.putString("ttsvalue", "0");
        edit.apply();


        first_name.add("First Name");
        first_name.add("first Name");
        first_name.add("First name");
        first_name.add("first Name");
        first_name.add("FirstName");
        first_name.add("firstName");
        first_name.add("Firstname");
        first_name.add("firstName");


        last_name.add("Last Name");
        last_name.add("Last name");
        last_name.add("last Name");
        last_name.add("last name");
        last_name.add("LastName");
        last_name.add("Lastname");
        last_name.add("lastName");
        last_name.add("lastname");



        middle_name.add("Middle Name");
        middle_name.add("Middle name");
        middle_name.add("middle Name");
        middle_name.add("middle name");
        middle_name.add("MiddleName");
        middle_name.add("Middlename");
        middle_name.add("middleName");
        middle_name.add("middlename");


        phone_number.add("Phone Number");
        phone_number.add("Phone number");
        phone_number.add("phone number");
        phone_number.add("phone Number");
        phone_number.add("PhoneNumber");
        phone_number.add("Phonenumber");
        phone_number.add("phonenumber");
        phone_number.add("phoneNumber");

        address.add("Address");
        address.add("address");

        favorite_restaturant.add("Favorite Restaturant");
        favorite_restaturant.add("favorite restaturant");
        favorite_restaturant.add("Favorite restaturant");
        favorite_restaturant.add("favorite Restaturant");
        favorite_restaturant.add("FavoriteRestaturant");
        favorite_restaturant.add("favoriterestaturant");
        favorite_restaturant.add("Favoriterestaturant");
        favorite_restaturant.add("favoriteRestaturant");


        spouse_first_name.add("Spouse First Name");
        spouse_first_name.add("spouse first name");
        spouse_first_name.add("Spouse First name");
        spouse_first_name.add("Spouse first name");
        spouse_first_name.add("spouse First Name");
        spouse_first_name.add("SpouseFirstName");
        spouse_first_name.add("spousefirs name");
        spouse_first_name.add("SpouseFirstname");
        spouse_first_name.add("Spousefirstname");
        spouse_first_name.add("spouseFirstName");

        favorite_friend.add("favorite friend");
        favorite_friend.add("Favorite Friend");
        favorite_friend.add("Favorite friend");
        favorite_friend.add("favorite Friend");
        favorite_friend.add("favoritefriend");
        favorite_friend.add("FavoriteFriend");
        favorite_friend.add("Favoritefriend");
        favorite_friend.add("favoriteFriend");

        number_of_children.add("number of children");
        number_of_children.add("Number Of Children");
        number_of_children.add("Number of children");
        number_of_children.add("Number Of children");
        number_of_children.add("Number Of Children");
        number_of_children.add("NumberOfChildren");
        number_of_children.add("Numberofchildren");
        number_of_children.add("NumberOfchildren");
        number_of_children.add("NumberOfChildren");


        child_name.add("child name");
        child_name.add("child Name");
        child_name.add("Child Name");
        child_name.add("Child name");
        child_name.add("childname");
        child_name.add("childName");
        child_name.add("ChildName");
        child_name.add("Childname");


        workplace.add("work place");
        workplace.add("Work Place");
        workplace.add("work place");
        workplace.add("Work Place");
        workplace.add("workplace");
        workplace.add("WorkPlace");
        workplace.add("workplace");
        workplace.add("WorkPlace");


        work_location.add("work location");
        work_location.add("work Location");
        work_location.add("Work location");
        work_location.add("Work location");
        work_location.add("worklocation");
        work_location.add("workLocation");
        work_location.add("Worklocation");
        work_location.add("Worklocation");

        favorite_sport.add("Favorite Sport");
        favorite_sport.add("favorite sport");
        favorite_sport.add("favorite Sport");
        favorite_sport.add("Favorite Sport");
        favorite_sport.add("FavoriteSport");
        favorite_sport.add("favoritesport");
        favorite_sport.add("favoriteSport");
        favorite_sport.add("FavoriteSport");

        favorite_pastime.add("Favorite Pastime");
        favorite_pastime.add("favorite pastime");
        favorite_pastime.add("favorite Pastime");
        favorite_pastime.add("Favorite Pastime");
        favorite_pastime.add("FavoritePastime");
        favorite_pastime.add("favoritepastime");
        favorite_pastime.add("favoritePastime");
        favorite_pastime.add("FavoritePastime");


        bithday.add("bithday");
        bithday.add("Bithday");

        bithday.add("Authorization Code");
        bithday.add("authorization code");
        bithday.add("authorization Code");
        bithday.add("Authorization code");
        bithday.add("code");
        bithday.add("AuthorizationCode");
        bithday.add("authorizationcode");
        bithday.add("authorizationCode");
        bithday.add("Authorizationcode");

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

                    if (first_name.contains(recognition.getText())) {
                        edt_First_Name.setText("");
                        edit.remove("ttsvalue");
                        edit.putString("ttsvalue", "0");
                        edit.apply();
                        recognize_onEdit();


                    }
                    if (last_name.contains(recognition.getText())) {
                        edt_Last_Name.setText("");
                        edit.remove("ttsvalue");
                        edit.putString("ttsvalue", "1");
                        edit.apply();
                        recognize_onEdit();
                    }
                    if (middle_name.contains(recognition.getText())) {
                        edt_Middle_Name.setText("");
                        edit.remove("ttsvalue");
                        edit.putString("ttsvalue", "2");
                        edit.apply();
                        recognize_onEdit();
                    }
                    if (phone_number.contains(recognition.getText())) {
                        edt_Phone_number.setText("");
                        edit.remove("ttsvalue");
                        edit.putString("ttsvalue", "3");
                        edit.apply();
                        recognize_onEdit();
                    }
                    if (address.contains(recognition.getText())) {
                        edt_Address.setText("");
                        edit.remove("ttsvalue");
                        edit.putString("ttsvalue", "4");
                        edit.apply();
                        recognize_onEdit();

                    }
                    if (favorite_restaturant.contains(recognition.getText())) {
                        edt_Favorite_Restaturant.setText("");
                        edit.remove("ttsvalue");
                        edit.putString("ttsvalue", "5");
                        edit.apply();
                        recognize_onEdit();


                    }
                    if (spouse_first_name.contains(recognition.getText())) {
                        edt_Spouse_first_name.setText("");
                        edit.remove("ttsvalue");
                        edit.putString("ttsvalue", "6");
                        edit.apply();
                        recognize_onEdit();
                    }
                    if (favorite_friend.contains(recognition.getText())) {
                        edt_Favorite_friend.setText("");
                        edit.remove("ttsvalue");
                        edit.putString("ttsvalue", "7");
                        edit.apply();
                        recognize_onEdit();
                    }
                    if (number_of_children.contains(recognition.getText())) {
                        edt_Number_of_children.setText("");
                        edit.remove("ttsvalue");
                        edit.putString("ttsvalue", "8");
                        edit.apply();
                        recognize_onEdit();
                    }
                    if (child_name.contains(recognition.getText())) {
                        edt_Child_name.setText("");
                        edit.remove("ttsvalue");
                        edit.putString("ttsvalue", "9");
                        edit.apply();
                        recognize_onEdit();
                    }
                    if (workplace.contains(recognition.getText())) {
                        edt_Workplace.setText("");
                        edit.remove("ttsvalue");
                        edit.putString("ttsvalue", "10");
                        edit.apply();
                        recognize_onEdit();
                    }
                    if (work_location.contains(recognition.getText())) {
                        edt_Work_location.setText("");
                        edit.remove("ttsvalue");
                        edit.putString("ttsvalue", "11");
                        edit.apply();
                        recognize_onEdit();
                    }
                    if (favorite_sport.contains(recognition.getText())) {

                        edt_Favorite_sport.setText("");
                        edit.remove("ttsvalue");
                        edit.putString("ttsvalue", "12");
                        edit.apply();
                        recognize_onEdit();
                    }
                    if (favorite_pastime.contains(recognition.getText())) {

                        edt_Favorite_pastime.setText("");
                        edit.remove("ttsvalue");
                        edit.putString("ttsvalue", "12");
                        edit.apply();
                        recognize_onEdit();
                    }
                    if (bithday.contains(recognition.getText())) {

                        edt_Birthday.setText("");
                        edit.remove("ttsvalue");
                        edit.putString("ttsvalue", "13");
                        edit.apply();
                        recognize_onEdit();
                    }
                    if (authorization_Code.contains(recognition.getText())) {

                        edt_Authorization_code.setText("");
                        edit.remove("ttsvalue");
                        edit.putString("ttsvalue", "13");
                        edit.apply();
                        recognize_onEdit();
                    }
                    break;
                default:
                    messagedialog();
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


    // on edit more details
    private void recognize_onEdit() {
        Transaction.Options options = new Transaction.Options();
        options.setRecognitionType(RecognitionType.DICTATION);
        options.setDetection(DetectionType.Short);
        options.setLanguage(new Language("eng-USA"));
        Transaction recoTransaction = speechSession.recognize(options, Onedit_recoListener);
    }

    private Transaction.Listener Onedit_recoListener = new Transaction.Listener() {
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
                    messagedialog();
                    break;
                case "1":

                    edt_Last_Name.setText(recognition.getText());

                    str_Last_Name = edt_Last_Name.getText().toString();
                    edit.remove("str_Last_Name");
                    edit.putString("str_Last_Name", str_Last_Name);
                    edit.apply();
                    messagedialog();
                    break;
                case "2":

                    edt_Middle_Name.setText(recognition.getText());
                    str_Middle_Name = edt_Middle_Name.getText().toString();
                    edit.remove("str_Middle_Name");
                    edit.putString("str_Middle_Name", str_Middle_Name);
                    edit.apply();
                    messagedialog();
                    break;
                case "3":

                    edt_Phone_number.setText(recognition.getText());
                    str_Phone_number = edt_Phone_number.getText().toString();
                    edit.remove("str_Phone_number");
                    edit.putString("str_Phone_number", str_Phone_number);
                    edit.apply();
                    messagedialog();
                    break;
                case "4":

                    edt_Address.setText(recognition.getText());
                    str_Address = edt_Address.getText().toString();
                    edit.remove("str_Address");
                    edit.putString("str_Address", str_Address);
                    edit.apply();
                    messagedialog();
                    break;
                case "5":

                    edt_Favorite_Restaturant.setText(recognition.getText());
                    str_Favorite_Restaturant = edt_Favorite_Restaturant.getText().toString();
                    edit.remove("str_Favorite_Restaturant");
                    edit.putString("str_Favorite_Restaturant", str_Favorite_Restaturant);
                    edit.apply();
                    messagedialog();
                    break;
                case "6":

                    edt_Spouse_first_name.setText(recognition.getText());
                    str_Spouse_first_name = edt_Spouse_first_name.getText().toString();
                    edit.remove("str_Spouse_first_name");
                    edit.putString("str_Spouse_first_name", str_Spouse_first_name);
                    edit.apply();
                    messagedialog();
                    break;
                case "7":

                    edt_Favorite_friend.setText(recognition.getText());
                    str_Favorite_friend = edt_Favorite_friend.getText().toString();
                    edit.remove("str_Favorite_friend");
                    edit.putString("str_Favorite_friend", str_Favorite_friend);
                    edit.apply();
                    messagedialog();
                    break;
                case "8":

                    edt_Number_of_children.setText(recognition.getText());
                    str_Number_of_children = edt_Number_of_children.getText().toString();
                    edit.remove("str_Number_of_children");
                    edit.putString("str_Number_of_children", str_Number_of_children);
                    edit.apply();
                    messagedialog();
                    break;
                case "9":

                    edt_Child_name.setText(recognition.getText());
                    str_Child_name = edt_Child_name.getText().toString();
                    edit.remove("str_Child_name");
                    edit.putString("str_Child_name", str_Child_name);
                    edit.apply();
                    messagedialog();
                    break;
                case "10":

                    edt_Workplace.setText(recognition.getText());
                    str_Workplace = edt_Workplace.getText().toString();
                    edit.remove("str_Workplace");
                    edit.putString("str_Workplace", str_Workplace);
                    edit.apply();
                    messagedialog();
                    break;
                case "11":

                    edt_Work_location.setText(recognition.getText());
                    str_Work_location = edt_Work_location.getText().toString();
                    edit.remove("str_Work_location");
                    edit.putString("str_Work_location", str_Work_location);
                    edit.apply();
                    messagedialog();
                    break;
                case "12":

                    edt_Favorite_sport.setText(recognition.getText());
                    str_Favorite_sport = edt_Favorite_sport.getText().toString();
                    edit.remove("str_Favorite_sport");
                    edit.putString("str_Favorite_sport", str_Favorite_sport);
                    edit.apply();
                    messagedialog();
                    break;
                case "13":

                    edt_Favorite_pastime.setText(recognition.getText());
                    str_Favorite_pastime = edt_Favorite_pastime.getText().toString();
                    edit.remove("str_Favorite_pastime");
                    edit.putString("str_Favorite_pastime", str_Favorite_pastime);
                    edit.apply();
                    messagedialog();
                    break;
                case "14":

                    edt_Birthday.setText(recognition.getText());
                    str_Birthday = edt_Birthday.getText().toString();
                    edit.remove("str_Birthday");
                    edit.putString("str_Birthday", str_Birthday);
                    edit.apply();
                    messagedialog();
                    break;
                case "15":

                    edt_Authorization_code.setText(recognition.getText());
                    str_Authorization_code = edt_Authorization_code.getText().toString();
                    edit.remove("str_Authorization_code");
                    edit.putString("str_Authorization_code", str_Authorization_code);
                    edit.apply();
                    messagedialog();
                    break;
                default:
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

    /// on edit more details


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
                synthesizetts("Please Say Which one edit");
                s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = s_pref.edit();
                edit.putString("ttsvalue", "0");
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
                ttsTransaction.cancel();
                ttsTransaction.stopRecording();
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);


            }
        });
        dialog.show();

    }


    // message warning details


    // information details
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
        custom_body.setText("Please press Again Edit or Done");
        custom_btn_edit.setText("Again Edit");
        custom_btn_ok.setText("Done");


        custom_btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                synthesizetts("Please Say Which one edit");
                s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = s_pref.edit();
                edit.putString("ttsvalue", "0");
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
                intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
                ttsTransaction.cancel();
                ttsTransaction.stopRecording();


            }
        });
        dialog.show();

    }

    @Override
    public void onBackPressed() {

        intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    //message details


    //information details
}
