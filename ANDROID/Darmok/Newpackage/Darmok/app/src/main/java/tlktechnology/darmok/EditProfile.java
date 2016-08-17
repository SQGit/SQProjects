package tlktechnology.darmok;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.sloop.fonts.FontsManager;

/**
 * Created by RSA on 12-08-2016.
 */
public class EditProfile extends DetailActivity implements View.OnClickListener {


    public static final String EXTRA_IS_TRANSPARENT = "is_transparent";
    private boolean isTransparent;
    TextView customtittle;
    Button btn_update;
    Intent intent;
    SharedPreferences sharedPreferences;
    SharedPreferences s_pref;
    EditText edt_First_Name, edt_Last_Name, edt_Middle_Name, edt_Phone_number, edt_Address, edt_Favorite_Restaturant, edt_Spouse_first_name,
            edt_Favorite_friend, edt_Number_of_children, edt_Child_name, edt_Workplace, edt_Work_location, edt_Favorite_sport, edt_Favorite_pastime, edt_Birthday, edt_Authorization_code;
    String str_First_Name, str_Last_Name, str_Middle_Name, str_Phone_number, str_Address, str_Favorite_Restaturant, str_Spouse_first_name,
            str_Favorite_friend, str_Number_of_children, str_Child_name, str_Workplace, str_Work_location, str_Favorite_sport, str_Favorite_pastime, str_Birthday, str_Authorization_code;
    UserDataBaseAdapter userDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isTransparent = getIntent().getBooleanExtra(EXTRA_IS_TRANSPARENT, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        // get Instance of Database Adapter
        userDataBaseAdapter = new UserDataBaseAdapter(this);
        userDataBaseAdapter = userDataBaseAdapter.open();


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Suissnord.otf");
        FontsManager.initFormAssets(this, "fonts/ques.otf");       //initialization
        FontsManager.changeFonts(this);
        customtittle = (TextView) findViewById(R.id.custom_tittle);
        customtittle.setTypeface(tf);

        //
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
        btn_update = (Button) findViewById(R.id.btn_update);
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

    // on click view
    @Override
    public void onClick(View v) {
        s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = s_pref.edit();
        switch (v.getId()) {
            case R.id.edt_first_name:
                str_First_Name = edt_First_Name.getText().toString();
                break;
            case R.id.edt_last_name:
                str_Last_Name = edt_Last_Name.getText().toString();
                break;
            case R.id.edt_middle_name:
                str_Middle_Name = edt_Middle_Name.getText().toString();
                break;
            case R.id.edt_phone_number:
                str_Phone_number = edt_Phone_number.getText().toString();
                break;
            case R.id.edt_address:
                str_Address = edt_Address.getText().toString();
                break;
            case R.id.edt_favorite_restaturant:
                str_Favorite_Restaturant = edt_Favorite_Restaturant.getText().toString();
                break;
            case R.id.edt_spouse_first_name:
                str_Spouse_first_name = edt_Spouse_first_name.getText().toString();
                break;
            case R.id.edt_favorite_friend:
                str_Favorite_friend = edt_Favorite_friend.getText().toString();
                break;
            case R.id.edt_number_of_children:
                str_Number_of_children = edt_Number_of_children.getText().toString();
                break;
            case R.id.edt_child_name:
                str_Child_name = edt_Child_name.getText().toString();
                break;
            case R.id.edt_Workplace:
                str_Workplace = edt_Workplace.getText().toString();
                break;
            case R.id.edt_Work_location:
                str_Work_location = edt_Work_location.getText().toString();
                break;
            case R.id.edt_favorite_sport:
                str_Favorite_sport = edt_Favorite_sport.getText().toString();
                break;
            case R.id.edt_favorite_pastime:
                str_Favorite_pastime = edt_Favorite_pastime.getText().toString();
                break;
            case R.id.edt_birthday:
                str_Birthday = edt_Birthday.getText().toString();
                break;
            case R.id.edt_Authorization_code:

                str_Authorization_code = edt_Authorization_code.getText().toString();
                edit.remove("str_Authorization_code");
                edit.putString("str_Authorization_code", str_Authorization_code);
                edit.apply();
                break;
            case R.id.btn_update:

                str_First_Name = edt_First_Name.getText().toString();
                str_Last_Name = edt_Last_Name.getText().toString();
                str_Middle_Name = edt_Middle_Name.getText().toString();
                str_Phone_number = edt_Phone_number.getText().toString();
                str_Address = edt_Address.getText().toString();
                str_Favorite_Restaturant = edt_Favorite_Restaturant.getText().toString();
                str_Spouse_first_name = edt_Spouse_first_name.getText().toString();
                str_Favorite_friend = edt_Favorite_friend.getText().toString();
                str_Number_of_children = edt_Number_of_children.getText().toString();
                str_Child_name = edt_Child_name.getText().toString();
                str_Workplace = edt_Workplace.getText().toString();
                str_Work_location = edt_Work_location.getText().toString();
                str_Favorite_sport = edt_Favorite_sport.getText().toString();
                str_Favorite_pastime = edt_Favorite_pastime.getText().toString();
                str_Birthday = edt_Birthday.getText().toString();
                str_Authorization_code = edt_Authorization_code.getText().toString();

                Log.d("tag", "str_First_Name =" + str_First_Name);
                Log.d("tag", "str_Last_Name =" + str_Last_Name);
                Log.d("tag", "str_Middle_Name =" + str_Middle_Name);
                Log.d("tag", "str_Phone_number =" + str_Phone_number);


                Log.d("tag", "str_Address =" + str_Address);
                Log.d("tag", "str_Favorite_Restaturant =" + str_Favorite_Restaturant);
                Log.d("tag", "str_Spouse_first_name =" + str_Spouse_first_name);
                Log.d("tag", "str_Favorite_friend =" + str_Favorite_friend);
                Log.d("tag", "str_Number_of_children =" + str_Number_of_children);

                Log.d("tag", "str_Child_name =" + str_Child_name);
                Log.d("tag", "str_Workplace =" + str_Workplace);
                Log.d("tag", "str_Work_location =" + str_Work_location);
                Log.d("tag", "str_Favorite_sport =" + str_Favorite_sport);
                Log.d("tag", "str_Favorite_pastime =" + str_Favorite_pastime);

                Log.d("tag", "str_Birthday =" + str_Birthday);
                Log.d("tag", "str_Authorization_code =" + str_Authorization_code);

                userDataBaseAdapter.insertentry(str_First_Name, str_Last_Name, str_Middle_Name, str_Phone_number, str_Address, str_Favorite_Restaturant, str_Spouse_first_name, str_Favorite_friend, str_Number_of_children, str_Child_name, str_Workplace, str_Work_location, str_Favorite_sport, str_Favorite_pastime, str_Birthday, str_Authorization_code);

                intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
                break;
            default:
        }
    }

    // on click view

}
