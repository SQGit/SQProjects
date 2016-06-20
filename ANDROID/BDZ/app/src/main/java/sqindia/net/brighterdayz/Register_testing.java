package sqindia.net.brighterdayz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sagayam on 12-03-2016.
 */
public class Register_testing extends Activity{

    EditText unam,email,pswd,name,phone,cpswd;
    Button SignUp;
    TextView Login,Reg;
    LoginDataBase loginDataBase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        unam = (EditText) findViewById(R.id.Rusername);
        email = (EditText) findViewById(R.id.Remail);
        pswd = (EditText) findViewById(R.id.Rpassword);
        name = (EditText) findViewById(R.id.Rname);
        phone = (EditText) findViewById(R.id.Rphone);
        cpswd = (EditText) findViewById(R.id.RCpassword);


        SignUp = (Button) findViewById(R.id.Bsignup);
        Login = (TextView) findViewById(R.id.Blogin);
    //    Reg = (TextView) findViewById(R.id.register);


        Typeface tf =Typeface.createFromAsset(getApplicationContext().getAssets(), "rr.ttf");
            unam.setTypeface(tf);
            email.setTypeface(tf);
            pswd.setTypeface(tf);
            name.setTypeface(tf);
            phone.setTypeface(tf);
            cpswd.setTypeface(tf);

        SignUp.setTypeface(tf);
        Login.setTypeface(tf);
      //  Reg.setTypeface(tf);


        loginDataBase = new LoginDataBase(this);
        loginDataBase = loginDataBase.open();



/*
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Register_testing.this,Home.class);
                startActivity(i);
                finish();

            }
        });
*/


/*

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Register_testing.this,Login_testing.class);
                startActivity(i);
                finish();
            }
        });
*/


        SignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String first_name=name.getText().toString();
                String user_name=unam.getText().toString();
                String pwd=pswd.getText().toString();
                String re_pwd=cpswd.getText().toString();
                String email_ed=email.getText().toString();
                String phone_no=phone.getText().toString();

                if(first_name.equals("")||user_name.equals("")||pwd.equals("")||re_pwd.equals("")||email_ed.equals("")||phone_no.equals(""))

                {
                    Toast.makeText(getApplicationContext(), "Fill All Fields", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!pwd.equals(re_pwd))
                {
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    // Save the Data in Database
                    loginDataBase.insertEntry(first_name, user_name, pwd, re_pwd, email_ed, phone_no);

                    // reg_btn.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
				    /*Log.d("PASSWORD",Pass);
				    Log.d("RE PASSWORD",Repass);
				    Log.d("SECURITY HINT",Secu);
				    */
                    Intent i=new Intent(Register_testing.this,Home.class);
                    startActivity(i);

                }
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent ii=new Intent(Register_testing.this,Login_testing.class);
                startActivity(ii);
            }
        });


    }



}

