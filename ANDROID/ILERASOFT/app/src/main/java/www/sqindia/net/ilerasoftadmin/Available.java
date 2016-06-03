package www.sqindia.net.ilerasoftadmin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Ramya on 24-02-2016.
 */
public class Available extends Fragment {
    TextView categoryName_tv,status_tv,date_tv,time_tv,desc_tv;
    String bookingRefId,token;
    SweetAlertDialog pDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab2, container, false);
        categoryName_tv=(TextView)root.findViewById(R.id.tv1);
        status_tv=(TextView)root.findViewById(R.id.tv2);
        date_tv=(TextView)root.findViewById(R.id.tv3);
        time_tv=(TextView)root.findViewById(R.id.tv4);
        desc_tv=(TextView)root.findViewById(R.id.tv_desc);
        categoryName_tv.setText(AvailableFragment.categoryName);




        return root;
    }
}
