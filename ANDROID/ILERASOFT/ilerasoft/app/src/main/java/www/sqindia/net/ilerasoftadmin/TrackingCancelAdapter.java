package www.sqindia.net.ilerasoftadmin;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ANANDH on 22-12-2015.
 */
public class TrackingCancelAdapter extends BaseAdapter {
    SweetAlertDialog pDialog;
    Button cancelNow;
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    String status;
    ArrayList<HashMap<String, String>> data;
    public static String URL_REGISTER = "http://sqindia01.cloudapp.net/cologix/api/v1/booking/cancel";
    public static String URL_REGISTER1 = "http://sqindia01.cloudapp.net/cologix/api/v1/booking/getAll";

    HashMap<String, String> resultp = new HashMap<String, String>();

    public TrackingCancelAdapter(Context context, ArrayList<HashMap<String, String>> arraylist) {
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
        TextView Reference_id;
        TextView book_time;
        TextView checkOut_time;
        String date,time;

        final String bookingId;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.new_report, parent, false);

        resultp = data.get(position);
        equipment_name = (TextView) itemView.findViewById(R.id.equipment_Id);
        Booking_date = (TextView) itemView.findViewById(R.id.Booking_Date);
        book_time = (TextView) itemView.findViewById(R.id.Booking_time);
        Reference_id = (TextView) itemView.findViewById(R.id.Reference_Id);

        cancelNow = (Button) itemView.findViewById(R.id.cancelnow);

        equipment_name.setText(resultp.get(TrackingCancel.CategoryNAME));

        Reference_id.setText(resultp.get(TrackingCancel.BookingReferenceId));
     //checkin_time.setText(resultp.get(TrackingCancel.TagInTime));
        //checkOut_time.setText(resultp.get(TrackingCancel.TagOutTime));
        bookingId = resultp.get(TrackingCancel.BookingId);
        status = resultp.get(TrackingCancel.Status);
        String strDateTimeBoj=resultp.get(TrackingStatus.BookFrom);

        StringTokenizer tk = new StringTokenizer(strDateTimeBoj);

        date = tk.nextToken();
        time = tk.nextToken();
        Booking_date.setText(date);
        book_time.setText(time);

        if(status.equals("STATUS_CANCELLED"))
        {
            cancelNow.setBackgroundResource(R.drawable.cancelled_btn);
            cancelNow.setEnabled(false);

        }
        else
        {
            cancelNow.setBackgroundResource(R.drawable.cancelnw_btn);

        }


        cancelNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("tag", "booking_id" + bookingId);
                new MyActivityAsync(bookingId).execute();

            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


            }
        });
        return itemView;
    }

    class MyActivityAsync extends AsyncTask<String, Void, String> {
        String bookingId;

        public MyActivityAsync(String bookingId) {
            String json = "", jsonStr = "";
            this.bookingId = bookingId;


        }


        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";
            try {


                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("bookingId", bookingId);
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return jsonStr = HttpUtils.makeRequest1(URL_REGISTER, json, TrackingCancel.token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.d("tag", "<-----rerseres---->" + jsonStr);
            super.onPostExecute(jsonStr);


            try {
                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");
                Log.d("tag", "<-----Status----->" + status);
                //{"status":"SUCCESS","message":"Booking cancelled successfully","data":{"Equipment":"onew"}}


                if (status.equals("SUCCESS"))
                {

                    Log.d("tag", "<-----bookingId----->" + bookingId);
                    new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("SUCCESS MESSAGE!!!")
                            .setConfirmText("Ok")
                            .setContentText("Booking cancelled successfully...")

                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog)
                                {
                                    Intent i=new Intent(context,Dashboard.class);
                                    context.startActivity(i);

                                }
                            })
                            .show();


                }
                else {
                    new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("ERROR MESSAGE!!!")
                            .setCancelText("Ok")
                            .setContentText(msg)


                            .show();

                }


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }


    }


    }


