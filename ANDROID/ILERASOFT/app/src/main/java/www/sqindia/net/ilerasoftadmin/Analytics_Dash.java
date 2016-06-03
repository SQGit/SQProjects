package www.sqindia.net.ilerasoftadmin;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Html;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by Ramya on 29-02-2016.
 */

public class Analytics_Dash extends AppCompatActivity implements CalendarDatePickerDialogFragment.OnDateSetListener {
    static EditText from, to;
    public boolean flag = true;
    static String dateFrom, dateTo;
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
    static TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.analytics_dash);
        from = (EditText) findViewById(R.id.From_Date);
        to = (EditText) findViewById(R.id.To_Date);
        tv = (TextView) findViewById(R.id.back_iv);

        if (savedInstanceState == null)
        {
            replaceTutorialFragment();
        }

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), InventoryControl.class);
                startActivity(i);
            }
        });



    }


    public void replaceTutorialFragment()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new Analytics());
        fragmentTransaction.commit();
    }


    @Override
    public void onResume()
    {
        super.onResume();
        replaceTutorialFragment();

        CalendarDatePickerDialogFragment calendarDatePickerDialogFragment = (CalendarDatePickerDialogFragment) getSupportFragmentManager()
                .findFragmentByTag(FRAG_TAG_DATE_PICKER);
        if (calendarDatePickerDialogFragment != null) {
            calendarDatePickerDialogFragment.setOnDateSetListener(this);
        }


    }


    public void onDateSet(CalendarDatePickerDialogFragment calendarDatePickerDialogFragment, int year, int month, int dayOfMonth) {
        if (flag)
        {
            dateFrom = year + "-" + (month + 1) + "-" + dayOfMonth;
            Log.d("tag", "<---datefrom---->" + dateFrom);
            from.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
        }
        else
        {
            dateTo = year + "-" + (month + 1) + "-" + dayOfMonth;
            to.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
        }
    }
}
