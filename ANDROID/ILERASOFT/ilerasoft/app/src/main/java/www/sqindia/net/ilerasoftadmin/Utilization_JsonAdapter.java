package www.sqindia.net.ilerasoftadmin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ANANDH on 24-12-2015.
 */
public class Utilization_JsonAdapter extends BaseAdapter {

    SweetAlertDialog pDialog;
    public static String URL_REGISTER = "http://sqindia01.cloudapp.net/cologix/api/v1/booking/cancel";
    String token;
    JSONObject jsonObject, jsonKey;
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();
    public Utilization_JsonAdapter(Context context, ArrayList<HashMap<String, String>> arraylist) {
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
        TextView average_utilization;

        final String bookingId;
        final String Status;



        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.utilization_data, parent, false);

        resultp = data.get(position);
        equipment_name = (TextView) itemView.findViewById(R.id.equipment_Id);
        average_utilization = (TextView) itemView.findViewById(R.id.Average_utilization);

        Log.d("tag", "<-----equipment_name----->" + equipment_name);
        Log.d("tag", "<-----Status----->" + average_utilization);
        equipment_name.setText(resultp.get(Utilization.categoryNAME));
        average_utilization.setText(resultp.get(Utilization.percentage));
        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


            }
        });
        return itemView;
    }


}
