package www.sqindia.net.ilerasoftadmin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Ramya on 25-02-2016.
 */
public class Content_tab extends Fragment {
    TextView categoryName_tv,status_tv,date_tv,time_tv,desc_tv;
    SweetAlertDialog pDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.content_tab, container, false);




        return root;
    }
}
