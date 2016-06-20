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
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.SignInAccount;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by sagayam on 12-03-2016.
 */
public class Login_testing1 extends Activity{

    EditText uname,pwd ;
    Button bt_signin;
    TextView Reg,login,ad,or;

    Button btnSignIn,btnSignUp;
    LoginDataBaseAdapter1 loginDataBaseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        uname = (EditText) findViewById(R.id.TFusername);
        pwd = (EditText) findViewById(R.id.TFpassword);
        bt_signin = (Button) findViewById(R.id.button_signin);
        Reg = (TextView) findViewById(R.id.Bregister);
       /* login = (TextView) findViewById(R.id.textView);*/
        ad = (TextView) findViewById(R.id.textView5);
        //or = (TextView) findViewById(R.id.Lor);


        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "rr.ttf");


        uname.setTypeface(tf);
        pwd.setTypeface(tf);
        bt_signin.setTypeface(tf);
        Reg.setTypeface(tf);
        //  login.setTypeface(tf);
        ad.setTypeface(tf);
        // or.setTypeface(tf);


                // create a instance of SQLite Database
                loginDataBaseAdapter=new LoginDataBaseAdapter1(this);
                loginDataBaseAdapter=loginDataBaseAdapter.open();

                // Get The Refference Of Buttons
             //   btnSignIn=(Button)findViewById(R.id.Bsignin);
           //     Reg= (TextView) findViewById(R.id.Bregister);

                // Set OnClick Listener on SignUp button
                Reg.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        /// Create Intent for SignUpActivity  abd Start The Activity
                        Intent intentSignUP = new Intent(getApplicationContext(), Register_testing1.class);
                        startActivity(intentSignUP);
                    }
                });


            // Methos to handleClick Event of Sign In Button
            /*public void signIn(View V)
            {
                final Dialog dialog = new Dialog(Login_testing1.this);
                dialog.setContentView(R.layout.login);
          //      dialog.setTitle("Login");
*/
                // get the Refferences of views
              final  EditText editTextUserName=(EditText)findViewById(R.id.TFusername);
              final  EditText editTextPassword=(EditText)findViewById(R.id.TFpassword);

             //  Button bt_signin=(Button)findViewById(R.id.button_signin);

                // Set On ClickListener
              // get The User name and Password


        bt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();

                if((userName.equals("")) && (password.equals(""))){
                    Toast.makeText(Login_testing1.this, "Fields are invalid", Toast.LENGTH_LONG).show();
                }
                else {
                    String storedPassword = loginDataBaseAdapter.getSinlgeEntry(userName);

                    // check if the Stored password matches with  Password entered by user
                    if (password.equals(storedPassword)) {
                        //  Toast.makeText(Login_testing1.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
                        // dialog.dismiss();

                        Intent intent = new Intent(Login_testing1.this, Home.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(Login_testing1.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
                    }
                }
                //     Toast.makeText(Login_testing1.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
                // fetch the Password form database for respective user name

            }
        });

    }  //   dialog.show();



        @Override
        protected void onDestroy () {
        super.onDestroy();
        // Close The Database
        loginDataBaseAdapter.close();
    }

        @Override
        public void onBackPressed ()
        {
       // super.onBackPressed();
        new SweetAlertDialog(Login_testing1.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Exit")
                .setConfirmText("Yes,Exit it!")
                .setContentText("Are you sure? Exit this Application")
                .setCancelText("No,cancel!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog)
                    {
                        Intent i1 = new Intent(Intent.ACTION_MAIN);
                        i1.setAction(Intent.ACTION_MAIN);
                        i1.addCategory(Intent.CATEGORY_HOME);
                        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i1);
                        finish();
                        finish();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                    }
                })
                .show();

    }



    }



