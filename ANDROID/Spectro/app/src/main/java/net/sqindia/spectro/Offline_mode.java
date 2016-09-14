package net.sqindia.spectro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sloop.fonts.FontsManager;

/**
 * Created by $Krishna on 31-08-2016.
 */
public class Offline_mode extends Activity {
    LinearLayout bt_back;
    TextView txt_date,txt_viewall,txt_time,txt_reg,txt_latlon;
    ListView listvie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_activity);
        FontsManager.initFormAssets(this, "fonts/avenir.otf");       //initialization
        FontsManager.changeFonts(this);
        bt_back = (LinearLayout) findViewById(R.id.layout_offback);
        txt_date = (TextView) findViewById(R.id.txt_offdate);
        txt_viewall = (TextView) findViewById(R.id.txt_viewall);
        txt_reg = (TextView) findViewById(R.id.txt_offregvalue);
        txt_latlon = (TextView) findViewById(R.id.txt_offlatlong);
        listvie = (ListView) findViewById(R.id.offlist1);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Offline_mode.this, Dashboard.class);
                startActivity(i);
                finish();
            }
        });
    }
}
