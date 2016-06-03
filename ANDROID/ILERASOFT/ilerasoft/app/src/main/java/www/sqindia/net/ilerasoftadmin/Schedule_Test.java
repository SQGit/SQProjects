package www.sqindia.net.ilerasoftadmin;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by RSA on 1/9/2016.
 */
public class Schedule_Test extends FragmentActivity {
 private FragmentTabHost mTabHost;
    ActionBar actionBar;
    static LinearLayout ll;
    static RadioGroup radioGroupcheck;

    static EditText from, to;
    static Spinner mySpinner2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule1);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle(Html.fromHtml("<font color='#ffffff'><big><b>BOOK NOW</b></big> </font>"));
        mySpinner2 = (Spinner)findViewById(R.id.my_spinner1);
        //mySpinner2.setPadding(30,-30,10,0);
        from = (EditText) findViewById(R.id.From_Date);
        to = (EditText) findViewById(R.id.To_Date);
        ll = (LinearLayout) findViewById(R.id.ll);
        radioGroupcheck = (RadioGroup) findViewById(R.id.radioGroup1);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        View tabView1 = createTabView(this, "MY BOOKINGS", R.drawable.timer);
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator(tabView1), BookingFragment.class, null);
        View tabView = createTabView1(this, "AVAILABILITY", R.drawable.availability);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator(tabView), AvailableFragment.class, null);

        }
        @Override
        protected void onStart() {
        super.onStart();
        }
        private static View createTabView1(Context context, String tabText, int home_icon) {

        View view = LayoutInflater.from(context).inflate(R.layout.customlayout1, null, true);
        TextView tv = (TextView) view.findViewById(R.id.tabTitleText1);
        tv.setText(tabText);
        return view;
    }
        private static View createTabView(Context context, String tabText, int home_icon) {
        View view = LayoutInflater.from(context).inflate(R.layout.customtab, null, true);
        TextView tv = (TextView) view.findViewById(R.id.tabTitleText);
        tv.setText(tabText);
        return view;
    }
     @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // This is the up button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}