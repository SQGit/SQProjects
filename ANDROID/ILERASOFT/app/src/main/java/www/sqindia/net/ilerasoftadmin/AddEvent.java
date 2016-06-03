package www.sqindia.net.ilerasoftadmin;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TimePicker;
import android.widget.Toast;

import com.imanoweb.calendarview.CalendarListener;
import com.imanoweb.calendarview.CustomCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Created by ANANDH on 17-12-2015.
 */
public class AddEvent extends Activity {


    CustomCalendarView calendarView;

    Button Submit;
    private Context context;

    static final int DIAlog_ID = 0;
    int hour_x;
    int minitus_x;
    EditText hospital_Name, Equipment_Name, from_Date, from_time, to_date, to_time;
    String hospital_name, Equipment_name, from_date, from_Time, to_Date, to_Time,Receiving_Hospital_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addevent);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AddEvent.this);
        Receiving_Hospital_name= sharedPreferences.getString("hospital_name", "");




        hospital_Name = (EditText) findViewById(R.id.HospitalName);


        hospital_Name.setEnabled(false);
        hospital_Name.setFocusableInTouchMode(false);
        hospital_Name.setFocusable(true);
        hospital_Name.setFocusableInTouchMode(false);
        hospital_Name.setText("SRM Hospital");


        Equipment_Name = (EditText) findViewById(R.id.equipment_Name);
        from_Date = (EditText) findViewById(R.id.From_Date);
        from_time = (EditText) findViewById(R.id.From_Time);
        to_date = (EditText) findViewById(R.id.End_Date);
        to_time = (EditText) findViewById(R.id.End_Time);
        Submit = (Button) findViewById(R.id.submited);
        from_Date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                 initiatePopupWindow();
                 }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    public void showTimepicerDialog() {

        from_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                showDialog(DIAlog_ID);
            }
        });
    }


    @Override
    protected Dialog onCreateDialog(int id) {


        if (id == DIAlog_ID)
            return new TimePickerDialog(AddEvent.this, KTimepickerDialog, hour_x, minitus_x, false);
            return null;
    }
    protected TimePickerDialog.OnTimeSetListener KTimepickerDialog = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour_x = hourOfDay;
            minitus_x = minute;
            }
    };
    private PopupWindow pwindo;
    private void initiatePopupWindow() {
        try {
            LayoutInflater inflater = (LayoutInflater) AddEvent.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.calender, (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, 300, 370, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            calendarView = (CustomCalendarView) findViewById(R.id.calendar_view);
            Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());
            calendarView.setFirstDayOfWeek(Calendar.MONDAY);
            calendarView.setShowOverflowDate(false);
            calendarView.refreshCalendar(currentCalendar);
            calendarView.setCalendarListener(new CalendarListener() {
                @Override
                public void onDateSelected(Date date) {
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    Toast.makeText(AddEvent.this, df.format(date), Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onMonthChanged(Date date) {
                    SimpleDateFormat df = new SimpleDateFormat("MM-yyyy");
                    Toast.makeText(AddEvent.this, df.format(date), Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




