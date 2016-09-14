package com.startemplan.vyuspot;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.balysv.materialripple.MaterialRippleLayout;

/**
 * Created by Salman on 5/20/2016.
 */
public class Admin_CreateGroup extends Activity {

    MaterialRippleLayout mtp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creategroup);



        mtp = (MaterialRippleLayout) findViewById(R.id.ripple);

        mtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
