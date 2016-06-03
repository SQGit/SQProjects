package www.sqindia.net.ilerasoftadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by RSA on 2/6/2016.
 */
public class SimpleCursorAdapter1  extends BaseAdapter {


    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;

    HashMap<String, String> resultp = new HashMap<String, String>();

    public SimpleCursorAdapter1(Context context, ArrayList<HashMap<String, String>> arraylist) {
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

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.test1, parent, false);

        resultp = data.get(position);
        String strDateTimeBoj=resultp.get(AvailableFragment.CategoryNAME);


        equipment_name = (TextView) itemView.findViewById(R.id.textView1);


        equipment_name.setText(resultp.get(TrackingStatus.CategoryNAME));




        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


            }
        });
        return itemView;
    }



}