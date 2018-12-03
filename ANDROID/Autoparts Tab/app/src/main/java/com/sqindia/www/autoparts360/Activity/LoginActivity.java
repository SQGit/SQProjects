package com.sqindia.www.autoparts360.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sqindia.www.autoparts360.Font.FontsOverride;
import com.sqindia.www.autoparts360.R;
import com.sqindia.www.autoparts360.Utils.Config;
import com.sqindia.www.autoparts360.Utils.HttpUtils;
import com.sqindia.www.autoparts360.Utils.Util;

import org.json.JSONObject;


public class LoginActivity extends Activity {

    Button login_btn;
    EditText userid_edt,pwd_edt;
    String[] typeofusers_data = {"Individual", "Dealer"};
    //MaterialBetterSpinner users_spn;
    String str_typeusers,str_role,str_uname,str_pwd;
    Typeface helevetical;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View v1 = getWindow().getDecorView().getRootView();
        FontsOverride.overrideFonts(LoginActivity.this, v1);
        helevetical= Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/helevetical.ttf");



        login_btn=findViewById(R.id.login_btn);
        userid_edt=findViewById(R.id.userid_edt);
        pwd_edt=findViewById(R.id.pwd_edt);
       // users_spn=findViewById(R.id.users_spn);




        /*final CustomAdapter shiftAdapter = new CustomAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, typeofusers_data) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return true;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTypeface(helevetical);
                tv.setTextSize(9);
                tv.setPadding(30, 55, 10, 25);
                if (position == 0) {
                    tv.setTextColor(Color.BLACK);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }


            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextSize(15);
                tv.setPadding(10, 20, 0, 20);
                tv.setTypeface(helevetical);
                if (position == 0) {
                    tv.setTextColor(Color.BLACK);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        users_spn.setAdapter(shiftAdapter);


        users_spn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FontsOverride.overrideFonts(LoginActivity.this, view);
                str_typeusers = adapterView.getItemAtPosition(i).toString();
                Log.e("tag", "str_typeusers------>" + str_typeusers);

                if(str_typeusers.equals("Individual"))
                {
                    str_role="individual";
                }
                else if(str_typeusers.equals("Dealer"))
                {
                    str_role="dealer";
                }

            }
        });

*/




        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_uname = userid_edt.getText().toString();
                str_pwd = pwd_edt.getText().toString();


                if (Util.Operations.isOnline(LoginActivity.this)) {

                    if (!str_uname.isEmpty() && !str_pwd.isEmpty()) {
                        new staffLogin_Task().execute();
                    } else {

                        Toast.makeText(getApplicationContext(),"Enter All details",Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(), "Check Internet Connectivity",Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    //LOGIN API CALL: ---------------------------------------------------------------------->
    public class staffLogin_Task extends AsyncTask<String, String, String> {
        private ProgressDialog dialog;
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage("Loading..., please wait.");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";


            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("email", str_uname);
                jsonObject.accumulate("password", str_pwd);
                jsonObject.accumulate("user_type","broker");
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest(Config.WEB_URL_LOGIN, json);

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            try {
                JSONObject jo = new JSONObject(s);
                String status = jo.getString("success");

                Log.e("tag","0---"+status);



                if (status.equals("true")) {
                    JSONObject object=jo.getJSONObject("data");
                    String userid = object.getString("user_id");
                    Log.e("tag","1---"+userid);
                    String user_type = object.getString("user_type");
                    String username = object.getString("name");
                    String email = object.getString("email");
                    String phone = object.getString("phone");
                    String country=object.getString("country");


                    SharedPreferences sharedPrefces = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor edit = sharedPrefces.edit();
                    edit.putString("user_name",username);
                    edit.putString("user_id",userid);
                    edit.putString("status","true") ;
                    edit.commit();


                    Intent i=new Intent(getApplicationContext(),DashboardActivity.class);
                    startActivity(i);
                    finish();




                } else {


                    Toast.makeText(getApplicationContext(),"Please check User credientials",Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {

            }
        }
    }
}
