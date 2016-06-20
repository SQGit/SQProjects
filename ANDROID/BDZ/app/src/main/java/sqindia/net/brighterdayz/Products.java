package sqindia.net.brighterdayz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by sagayam on 19-03-2016.
 */
public class Products extends Activity {

    private ExpandableListView expandableListView;
    ImageView fb,twi,ins,pin;
    LinearLayout back;
    TextView prod_1,barrels,crates,card,prod_2,prod_3,prod_4,prod_5,prod_6,prod_7,prod_8,prod_9,prod_10,prod_11,prod_12,prod_13,prod_14,prod_15,prod_16,prod_17,prod_18,prod_19, copyright;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.products_db);


        barrels = (TextView) findViewById(R.id.products_barrels);
        crates = (TextView) findViewById(R.id.products_crates);
        card = (TextView) findViewById(R.id.products_cardboard);
        prod_1 = (TextView) findViewById(R.id.products8_1);
        prod_2 = (TextView) findViewById(R.id.products1);
       // prod_3 = (TextView) findViewById(R.id.products3);
       // prod_4 = (TextView) findViewById(R.id.products4);
       // prod_5 = (TextView) findViewById(R.id.products5);
       // prod_6 = (TextView) findViewById(R.id.products6);
        prod_7 = (TextView) findViewById(R.id.products7);
        prod_8 = (TextView) findViewById(R.id.products8);
        prod_9 = (TextView) findViewById(R.id.products9);
        prod_10 = (TextView) findViewById(R.id.products10);
        prod_11 = (TextView) findViewById(R.id.products11);
        prod_12 = (TextView) findViewById(R.id.products12);
        prod_13 = (TextView) findViewById(R.id.products13);
        prod_14 = (TextView) findViewById(R.id.products14);
        prod_15 = (TextView) findViewById(R.id.products15);
        prod_16 = (TextView) findViewById(R.id.products16);
        prod_17 = (TextView) findViewById(R.id.products17);
        prod_18 = (TextView) findViewById(R.id.products18);
        prod_19 = (TextView) findViewById(R.id.products19);
  //      copyright = (TextView) findViewById(R.id.txt_copyright);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "rr.ttf");


        prod_1.setTypeface(tf);
        prod_2.setTypeface(tf);
       // prod_3.setTypeface(tf);
       // prod_4.setTypeface(tf);
       // prod_5.setTypeface(tf);
       // prod_6.setTypeface(tf);
        prod_7.setTypeface(tf);
        prod_8.setTypeface(tf);
        prod_9.setTypeface(tf);
        prod_10.setTypeface(tf);
        prod_11.setTypeface(tf);
        prod_12.setTypeface(tf);
        prod_13.setTypeface(tf);
        prod_14.setTypeface(tf);
        prod_15.setTypeface(tf);
        prod_16.setTypeface(tf);
        prod_17.setTypeface(tf);
        prod_18.setTypeface(tf);
        prod_19.setTypeface(tf);
       // copyright.setTypeface(tf);
        barrels.setTypeface(tf);
        crates.setTypeface(tf);
        card.setTypeface(tf);







        back = (LinearLayout) findViewById(R.id.Lproducts);
      /*  fb=(ImageView) findViewById(R.id.imageView_fb);
        twi=(ImageView)findViewById(R.id.imageView_twi);
        ins=(ImageView)findViewById(R.id.imageView_ins);
        pin=(ImageView)findViewById(R.id.imageView_pin);
*/
      back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent k = new Intent(Products.this, Home.class);
                startActivity(k);

            }
        });


    }
    @Override
    public void onBackPressed() {

        Intent i = new Intent(Products.this,Home.class);
        startActivity(i);
        super.onBackPressed();
    }
}
