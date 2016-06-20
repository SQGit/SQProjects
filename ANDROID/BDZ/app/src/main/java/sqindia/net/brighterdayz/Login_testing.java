package sqindia.net.brighterdayz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sagayam on 12-03-2016.
 */
public class Login_testing extends Activity{

    EditText uname,pwd ;
    Button Signin;
    TextView Reg,login,ad,or;
    LoginDataBase loginDataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        uname = (EditText) findViewById(R.id.TFusername);
        pwd = (EditText) findViewById(R.id.TFpassword);
        Signin = (Button) findViewById(R.id.button_signin);
        Reg = (TextView) findViewById(R.id.Bregister);
       /* login = (TextView) findViewById(R.id.textView);*/
        ad = (TextView) findViewById(R.id.textView5);
        //or = (TextView) findViewById(R.id.Lor);


        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "rr.ttf");


        uname.setTypeface(tf);
        pwd.setTypeface(tf);
        Signin.setTypeface(tf);
        Reg.setTypeface(tf);
        //  login.setTypeface(tf);
        ad.setTypeface(tf);
        // or.setTypeface(tf);


        loginDataBase = new LoginDataBase(getApplicationContext());
        loginDataBase.open();


        /*Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Login_testing.this, Home.class);
                startActivity(i);
                finish();

            }

        });*/



        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ii = new Intent(Login_testing.this, Register_testing.class);
                startActivity(ii);
                finish();

            }
        });


        Signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String Password = pwd.getText().toString();
                String UserName=uname.getText().toString();
                Log.d("tag", "s1");
                String storedPassword = loginDataBase.getSinlgeEntry(UserName);
                Log.d("tag", "s2");




                if (Password.equals(storedPassword)) {
                    Toast.makeText(Login_testing.this, "Congrats: Login Successfully", Toast.LENGTH_LONG).show();
                    Intent ii = new Intent(Login_testing.this, Home.class);
                    startActivity(ii);
                } else if (Password.equals("")) {
                    Toast.makeText(Login_testing.this, "Please Enter Your Password", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Login_testing.this, "Username or Password Incorrect", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        loginDataBase.close();
    }


}
