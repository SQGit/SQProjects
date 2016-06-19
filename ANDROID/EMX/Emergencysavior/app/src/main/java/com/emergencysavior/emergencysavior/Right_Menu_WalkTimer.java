package com.emergencysavior.emergencysavior;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.sloop.fonts.FontsManager;

/**
 * Created by RSA on 09-05-2016.
 */
public class Right_Menu_WalkTimer extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_IS_TRANSPARENT = "is_transparent";
    private boolean isTransparent;


    int i = -1;


    private Button buttonStartTime, buttonStopTime;
    private EditText edtTimerValue;
    private TextView textViewShowTime; // will show the time
    private CountDownTimer countDownTimer; // built in android class
    // CountDownTimer
    private long totalTimeCountInMilliseconds; // total count down time in
    // milliseconds
    private long timeBlinkInMilliseconds; // start time of start blinking
    private boolean blink; // controls the blinking .. on and off
    CircularProgressBar circ;


    Typeface tf;
    private CardView mCardViewTop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isTransparent = getIntent().getBooleanExtra(EXTRA_IS_TRANSPARENT, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walktimer);
        FontsManager.initFormAssets(this, "fonts/ques.otf");
        FontsManager.changeFonts(this);
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> A C T I O N B A R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");
        SpannableStringBuilder SS = new SpannableStringBuilder(Html.fromHtml("<font color='#3c5899'><small><b>WALK TIMER</b></small> </font>"));
        SS.setSpan(new CustomTypefaceSpan("#3c5899", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> A C T I O N B A R >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        setStatusBar();
        mCardViewTop = (CardView) findViewById(R.id.card_view);
        buttonStartTime = (Button) findViewById(R.id.btnStartTime);
        buttonStopTime = (Button) findViewById(R.id.btnStopTime);
        textViewShowTime = (TextView) findViewById(R.id.tvTimeCount);
        edtTimerValue = (EditText) findViewById(R.id.edtTimerValue);
        mCardViewTop.setCardBackgroundColor(Color.parseColor("#B3FFFFFF"));
        buttonStartTime.setOnClickListener(this);
        buttonStopTime.setOnClickListener(this);
        circ = (CircularProgressBar) findViewById(R.id.circularprogress);
    }

    protected void setStatusBar() {
        if (isTransparent) {
            StatusBarUtil.setTransparent(this);
        } else {
            StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnStartTime) {
//            textViewShowTime.setTextAppearance(getApplicationContext(), R.style.AppTheme);
            setTimer();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtTimerValue.getWindowToken(), 0);
            buttonStopTime.setVisibility(View.VISIBLE);
            buttonStartTime.setVisibility(View.GONE);
            edtTimerValue.setVisibility(View.GONE);
            edtTimerValue.setText("");
            textViewShowTime.setTextColor(Color.RED);
            textViewShowTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
            startTimer();

        } else if (v.getId() == R.id.btnStopTime) {
            countDownTimer.cancel();
            buttonStartTime.setVisibility(View.VISIBLE);
            buttonStopTime.setVisibility(View.GONE);
            edtTimerValue.setVisibility(View.VISIBLE);
        }
    }

    private void setTimer() {
        int time = 0;
        if (!edtTimerValue.getText().toString().equals("")) {
            time = Integer.parseInt(edtTimerValue.getText().toString());
        } else
            Toast.makeText(Right_Menu_WalkTimer.this, "Please Enter Minutes...",
                    Toast.LENGTH_LONG).show();
        totalTimeCountInMilliseconds = 60 * time * 1000;
        timeBlinkInMilliseconds = 30 * 1000;
        circ.setMax(60 * time);
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 500) {
            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                circ.setProgress((int) (leftTimeInMilliseconds / 1000));
              /*  textViewShowTime.setTextAppearance(getApplicationContext(),
                        R.style.AppTheme);*/
                if (leftTimeInMilliseconds < timeBlinkInMilliseconds) {
                   /* textViewShowTime.setTextAppearance(getApplicationContext(),
                            R.style.AppTheme);*/
                    if (blink) {
                        textViewShowTime.setVisibility(View.VISIBLE);
                    } else {
                        textViewShowTime.setVisibility(View.INVISIBLE);
                    }
                    blink = !blink;
                }

                textViewShowTime.setText(String.format("%02d", seconds / 60) + ":" + String.format("%02d", seconds % 60));
            }

            @Override
            public void onFinish() {
                textViewShowTime.setText("Time up!");
                textViewShowTime.setVisibility(View.VISIBLE);
                buttonStartTime.setVisibility(View.VISIBLE);
                buttonStopTime.setVisibility(View.GONE);
                edtTimerValue.setVisibility(View.VISIBLE);
            }

        }.start();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}