package sqindia.net.brighterdayz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sagayam on 19-03-2016.
 */
public class Shipping extends Activity {

/*
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View shippingareas = inflater.inflate(R.layout.shipping,container,false);
        return  shippingareas;
    }*/

    LinearLayout back;
    final Context context = this;
    ImageView fb, twi, ins, pin;
    TextView txt_shipping,copyright;


    private static final String[][] data = {{"KINGSTON OFFICE:KINGSLEY SHIPPING(JAMAICA) LTD", "UNIT13B<br>14-16 FIRST STREET NEWPORT<br>WEST KINGSTON13,JAMAICA<br>TEL:(00 1876)758 4596"},
            {"MONTEGO BAY AGENT:<br>FUTURE SHIPPING SERVICE", "HARBOUR WAY HOTEL<br>1 HARBOUR STREET MONTEGO BAY<br>ST.JAMES,JAMAICA<br>TEL:(00 1876)940 6459"},
            {"GORDON GRANT and CO LTD", "16 CHARLES STREET<br> PORT-OF-SPAIN<br>TRINIDAD<br>TEL: 001(868) 625 3784<br>FAX: 001(868) 625 2020"},
            {"RAM SHIPPING AGENCY", "3RD AVENUE<br>DASH GAP<br>BANK HALL<br>ST MICHAEL<br>BARBADOS <br>TEL: 001 (246)436 9581 <br>FAX: 001(246)429 4739<br>EMAIL:customerservices@bdzshipping.com<br>CONTACTS:KEISHA/JULIA/LINDA"},

            {"GITTENS AGENCIES LTD", "THE GARENAGE<br>ST GEORGES<br>GRENADA<br>TEL NO: 001 (473)435 8970<br>FAX NO: 001 (473)435 8972"},
            {"CURTIS FLORANT", "14 CHURCH STREET<br>ROSEAU,DOMINICA<br>FAX NO: 001 (767) 448 7196<br>CURTIS MOBILE: 001 (767) 277 1920<br>EMAIL:gemmaflorant@hotmail.com<br>CONTACT: CURTIS FLORANT"},
            {"RAM SHIPPING AGENCY", "3RD AVENUE, DASH GAP<br>BANK HALL,ST MICHAEL,BARBADOS<br>TEL: 001 (246)436 9581 <br>FAX: 001(246)429 4739 <br>EMAIL:ramshipping@caribsurf.com<br>CONTACTS: KEISHA/JULIA/LINDA"},
            {"S.L.HORSFORD and CO LTD", "P.O. BOX 45<br>MARSHALL HOUSE,BASSETERRE,ST KITTS<br>TEL NO: 001 (869) 465 2616<br>FAX NO: 001 (869) 466 4257<br>CONTACTS: CRYSTAL/GABRELLA/<br>LAVERN HODGE/BRIDGET"},
            {"MINIVIELLE and CHASTANET LT", "P.O. BOX 92<br>BRIDGE STREET<br>CASTRIES<br>ST LUCIA<br>TEL: 001 (758) 458 8252<br>FAX: 001(758) 458 1985"},
            {"COX and RICHARDS AGENCY", "P.O. BOX 1234<br>SHARPE STREET<br>KINGSTOWN<br>ST VINCENT<br>TEL: 001 (784) 457 2547<br>FAX: 001(784) 457 2674"},


    };
    private ExpandableListView expandableListView;

    Button button1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.shipping);
/*
        fb=(ImageView) findViewById(R.id.imageView_fb);
        twi=(ImageView)findViewById(R.id.imageView_twi);
        ins=(ImageView)findViewById(R.id.imageView_ins);
        pin=(ImageView)findViewById(R.id.imageView_pin);
*/        back= (LinearLayout) findViewById(R.id.Lshipping);

        txt_shipping = (TextView) findViewById(R.id.shippingareas);
    //    copyright = (TextView) findViewById(R.id.txt_copyright);


        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),"rr.ttf");


        txt_shipping.setTypeface(tf);
//        copyright.setTypeface(tf);




        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView1);
        Log.d("tag", "s3");
           expandableListView.setAdapter(new SampleExpandableListAdapter(context, this, data));
          Log.d("tag", "s4");


/*
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

               String dd =  parent.getExpandableListAdapter().getChild(groupPosition,childPosition).toString();
                //String ddoi =

                Toast.makeText(context,"dial"+dd,Toast.LENGTH_LONG).show();
                return true;
            }
        });
*/



        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent k = new Intent(Shipping.this, Home.class);
                startActivity(k);

            }
        });

    }
    @Override
    public void onBackPressed() {

        Intent i = new Intent(Shipping.this,Home.class);
        startActivity(i);
        super.onBackPressed();
    }

}
