package sqindia.net.brighterdayz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by sagayam on 12-03-2016.
 */
public class Register_testing1 extends Activity{

    EditText unam,email,pswd,name,phone,cpswd;
    Button SignUp;
    TextView Login,Reg;


    EditText editTextUserName,editTextPassword,editTextConfirmPassword;
    Button btnCreateAccount;

    LoginDataBaseAdapter1 loginDataBaseAdapter;


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



                // get Instance  of Database Adapter
                loginDataBaseAdapter=new LoginDataBaseAdapter1(this);
                loginDataBaseAdapter=loginDataBaseAdapter.open();
/*
                // Get Refferences of Views
                unam=(EditText)findViewById(R.id.editTextUserName);
                editTextPassword=(EditText)findViewById(R.id.editTextPassword);
                editTextConfirmPassword=(EditText)findViewById(R.id.editTextConfirmPassword);

                btnCreateAccount=(Button)findViewById(R.id.Bsignup);*/
                SignUp.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        String fname=name.getText().toString();
                        String userName=unam.getText().toString();
                        String password=pswd.getText().toString();
                        String confirmPassword=cpswd.getText().toString();
                        String emails=email.getText().toString();
                        String phoneNo=phone.getText().toString();

                        if ((!fname.isEmpty())&& (!userName.isEmpty())&& (!password.isEmpty())&&(!confirmPassword.isEmpty())&&(!emails.isEmpty())&&(!phoneNo.isEmpty())){


                            if(password.equals(confirmPassword))
                            {
                                if(android.util.Patterns.EMAIL_ADDRESS.matcher(emails).matches()){
                                   if (phoneNo.length() == 10){
                                       loginDataBaseAdapter.insertEntry(fname,userName, password,confirmPassword,emails,phoneNo);
                                       Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();

                                       Intent i=new Intent(Register_testing1.this,Login_testing1.class);
                                       startActivity(i);
                                   }
                                    else {
                                       Toast.makeText(getApplicationContext(), "Enter a valid Mobile no.", Toast.LENGTH_LONG).show();
                                   }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Enter a valid email", Toast.LENGTH_LONG).show();
                                }


                            }else {
                                Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                            }

                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Fields are Invalid ", Toast.LENGTH_LONG).show();
                        }


                    }
                });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent ii=new Intent(Register_testing1.this,Login_testing1.class);
                startActivity(ii);
            }
        });


    }

            @Override
            protected void onDestroy() {
                // TODO Auto-generated method stub
                super.onDestroy();

                loginDataBaseAdapter.close();
            }
        }

