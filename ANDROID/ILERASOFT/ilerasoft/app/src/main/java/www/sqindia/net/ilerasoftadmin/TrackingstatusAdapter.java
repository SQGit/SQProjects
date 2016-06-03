package www.sqindia.net.ilerasoftadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by ANANDH on 22-12-2015.
 */
public class TrackingstatusAdapter extends BaseAdapter {


    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;

    HashMap<String, String> resultp = new HashMap<String, String>();

    public TrackingstatusAdapter(Context context, ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;


    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // Declare Variables
        TextView equipment_name;
        TextView Booking_date;
        TextView Booking_Time;

        TextView Reference_id;
        TextView checkin_time,checkin_date;
        TextView checkOut_time,checkout_date;
        Date dateObj=null;
        Date timeObj=null;
        String date,time;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.trackingstsatus, parent, false);

        resultp = data.get(position);
        String strDateTimeBoj=resultp.get(TrackingStatus.BookFrom);

        StringTokenizer tk = new StringTokenizer(strDateTimeBoj);

         date = tk.nextToken();
         time = tk.nextToken();

        equipment_name = (TextView) itemView.findViewById(R.id.equipment_Id);
        Booking_date = (TextView) itemView.findViewById(R.id.Booking_Date);
        Booking_Time = (TextView) itemView.findViewById(R.id.Booking_Time);

        Reference_id = (TextView) itemView.findViewById(R.id.Reference_Id);
        checkin_time = (TextView) itemView.findViewById(R.id.checkin_Time);
        checkOut_time = (TextView) itemView.findViewById(R.id.checkOut_time);
        checkin_date = (TextView) itemView.findViewById(R.id.checkin_Date);
        checkout_date = (TextView) itemView.findViewById(R.id.checkOut_date);
        equipment_name.setText(resultp.get(TrackingStatus.CategoryNAME));
        Booking_date.setText(date);
        Booking_Time.setText(time);
        Reference_id.setText(resultp.get(TrackingStatus.BookingReferenceId));
        String TagInTime=resultp.get(TrackingStatus.TagInTime);
        if (TagInTime.equals("null"))
        {
            checkin_date.setText("Null");
            checkin_time.setText("Null");
        }
        else
        {
            StringTokenizer tk1 = new StringTokenizer(TagInTime);

            date = tk1.nextToken();
            time = tk1.nextToken();
            checkin_date.setText(date);
            checkin_time.setText(time);
        }
        String TagOutTime=resultp.get(TrackingStatus.TagOutTime);
        if (TagOutTime.equals("null"))
        {
            checkout_date.setText("Null");
            checkOut_time.setText("Null");
        }
        else
        {
            StringTokenizer tk1 = new StringTokenizer(TagOutTime);

            date = tk1.nextToken();
            time = tk1.nextToken();
            checkout_date.setText(date);
            checkOut_time.setText(time);
        }

        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


            }
        });
        return itemView;
    }
}