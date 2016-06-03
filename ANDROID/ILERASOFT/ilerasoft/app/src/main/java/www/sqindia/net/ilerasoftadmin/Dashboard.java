package www.sqindia.net.ilerasoftadmin;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ANANDH on 12-12-2015.
 */
public class Dashboard extends AppCompatActivity {
    TextView dash_tv, exit;
    public static String Session, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'><big><b>DASHBOARD</b></big> </font>"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xcececece));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.bck_arrow);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);
        token = sharedPreferences.getString("TOKEN", "");
        Log.d("tag", "<Dashboardrecived>" + token);
     /*  exit = (TextView) findViewById(R.id.back_iv);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            new SweetAlertDialog(Dashboard.this, SweetAlertDialog.WARNING_TYPE)

                        .setTitleText("Exit Application?")
                        .setContentText("Are you sure? Exit this Application")
                        .setCancelText("No,cancel!")
                        .setConfirmText("Yes,Exit it!")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                onRestart();
                                Intent i1 = new Intent(Intent.ACTION_MAIN);
                                i1.setAction(Intent.ACTION_MAIN);
                                i1.addCategory(Intent.CATEGORY_HOME);
                                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i1);
                                finish();


                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .show();

            }
        });*/

        dash_tv = (TextView) findViewById(R.id.tv);
        Dash_home hc = new Dash_home();
        android.app.FragmentTransaction fragmentTransaction4 = getFragmentManager().beginTransaction();
        fragmentTransaction4.replace(R.id.fragment_place, hc);
        fragmentTransaction4.commit();
    }

    @Override
    protected void onRestart() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
        finish();
        super.onRestart();

    }

    @Override
    public void onBackPressed() {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // This is the up button
            case android.R.id.home:


                new SweetAlertDialog(Dashboard.this, SweetAlertDialog.WARNING_TYPE)

                        .setTitleText("Exit Application?")
                        .setContentText("Are you sure? Exit this Application")
                        .setCancelText("No,cancel!")
                        .setConfirmText("Yes,Exit it!")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                onRestart();
                                Intent i1 = new Intent(Intent.ACTION_MAIN);
                                i1.setAction(Intent.ACTION_MAIN);
                                i1.addCategory(Intent.CATEGORY_HOME);
                                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i1);
                                finish();


                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
