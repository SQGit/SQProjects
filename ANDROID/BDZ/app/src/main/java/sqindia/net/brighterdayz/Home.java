package sqindia.net.brighterdayz;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**

 */
public class Home extends Activity {
    LinearLayout products, shipping, Aboutus, quote, contacts, ouroffers, logout;
    TextView d_products, d_shipping, d_aboutus, d_quote, d_contacts, d_offers, txt_logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Home.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("pus", "");
        editor.commit();


        d_products = (TextView) findViewById(R.id.textView);
        d_shipping = (TextView) findViewById(R.id.textView1);
        d_aboutus = (TextView) findViewById(R.id.textView3);
        d_quote = (TextView) findViewById(R.id.textView4);
        d_contacts = (TextView) findViewById(R.id.textView5);
        d_offers = (TextView) findViewById(R.id.textView6);
        txt_logout = (TextView) findViewById(R.id.tvlogout);


        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "rr.ttf");

        d_products.setTypeface(tf);
        d_shipping.setTypeface(tf);
        d_offers.setTypeface(tf);
        d_contacts.setTypeface(tf);
        d_quote.setTypeface(tf);
        d_aboutus.setTypeface(tf);
        txt_logout.setTypeface(tf);


        logout = (LinearLayout) findViewById(R.id.linear_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show(*/

                new SweetAlertDialog(Home.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Logout")
                        .setConfirmText("Yes,Logout")
                        .setContentText("Do you want to Logout ?")
                        .setCancelText("No,cancel")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent i = new Intent(Home.this, Login_testing1.class);
                                startActivity(i);
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
        });


        contacts = (LinearLayout) findViewById(R.id.linear7);
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

         //       Intent h = new Intent(Intent.ACTION_DIAL);
         //       h.setData(Uri.parse("tel:020 339 77856"));
         //       startActivity(h);

                Intent k =new Intent(Home.this,Contacts.class);
                startActivity(k);
                finish();
            }
        });


        quote = (LinearLayout) findViewById(R.id.linear6);
        quote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Home.this, Quote.class);
                startActivity(i);
                finish();

            }
        });


        shipping = (LinearLayout) findViewById(R.id.linear4);
        shipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Home.this, Shipping.class);
                startActivity(i);
                finish();

            }
        });


        products = (LinearLayout) findViewById(R.id.linear3);
        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Home.this, Products.class);
                startActivity(i);
                finish();

            }
        });


        Aboutus = (LinearLayout) findViewById(R.id.linear5);
        Aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Home.this, AboutUs.class);
                startActivity(i);
                finish();

            }
        });
        ouroffers = (LinearLayout) findViewById(R.id.linear8);
        ouroffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, OurOffers.class);
                startActivity(i);

            }
        });
    }



    @Override
    public void onBackPressed() {

        Intent i = new Intent(Home.this,Login_testing1.class);
        startActivity(i);

       // super.onBackPressed();
    }
}

