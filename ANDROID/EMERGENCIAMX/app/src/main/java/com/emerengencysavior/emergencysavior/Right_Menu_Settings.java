package com.emerengencysavior.emergencysavior;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.sloop.fonts.FontsManager;

/**
 * Created by RSA on 08-05-2016.
 */
public class Right_Menu_Settings extends AppCompatActivity {
    public static final String EXTRA_IS_TRANSPARENT = "is_transparent";
    private boolean isTransparent;
    private CardView mCardViewTop, mCardViewBottom;
    private Context context;
    boolean charging = false;
    private TextView txt_battery_status;
    Typeface tf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isTransparent = getIntent().getBooleanExtra(EXTRA_IS_TRANSPARENT, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.right_menu_settings);


        FontsManager.initFormAssets(this, "fonts/ques.otf");       //initialization
        FontsManager.changeFonts(this);

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> A C T I O N B A R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        SpannableStringBuilder SS = new SpannableStringBuilder(Html.fromHtml("<font color='#3c5899'><small><b>SETTINGS</b></small> </font>"));
        SS.setSpan(new CustomTypefaceSpan("#3c5899", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> A C T I O N B A R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        txt_battery_status = (TextView) findViewById(R.id.textView1);
        registerReceiver(mbcr, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        mCardViewBottom = (CardView) findViewById(R.id.card_view);
        mCardViewTop = (CardView) findViewById(R.id.card_view_switch);
        mCardViewBottom.setCardBackgroundColor(Color.parseColor("#B3FFFFFF"));
        mCardViewTop.setCardBackgroundColor(Color.parseColor("#B3FFFFFF"));
        setStatusBar();
    }

    protected void setStatusBar() {
        if (isTransparent) {
            StatusBarUtil.setTransparent(this);
        } else {
            StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
        }
    }
    private BroadcastReceiver mbcr = new BroadcastReceiver() {
        public void onReceive(Context c, Intent i) {
            int level = i.getIntExtra("level", 0);
            BatteryIndicatorGauge batteryindicator = (BatteryIndicatorGauge) findViewById(R.id.batteryindicator);
            batteryindicator.setOrientation(1);
            batteryindicator.setValue(level, 1000, 300);

            txt_battery_status.setText("Batterylevel:" + Integer.toString(level) + "%");


        }
    };

}

