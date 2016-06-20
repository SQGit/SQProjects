package sqindia.net.brighterdayz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Quote extends Activity {


	Typeface tf;
	String[] search_items;
	LinearLayout back;
	ImageView fb, twi, ins, pin,dashb;
	String shippingFrom, destination, fullName, Email, PhoneNo, Address, PostalCode, Subject, Quantity, storeval;
	EditText ship, name, email_id, contact_no, address, zipcode, subject, size_qty;
	Button btn_submit;
	TextView quote_0, quote_1, quote_2, quote_3, quote_4, quote_5, quote_6, quote_7, quote_8, quote_9, quote_10, quote_11, quote_12, quote_13, quote_14, quote_15, quote_16, quote_17, quote_18, copyright;


	boolean state;
	Spinner s;
	int flag = 0;
	String[] params = new String[9];
	//Spinner s;
	ArrayAdapter<String> des;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		state = false;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.quote);
		//tf = Typeface.createFromAsset(getAssets(), "fonts/ques.otf");


		String[] search_items = {"ROUTE 1-KINGSTON WHARF", "ROUTE 2-MONTEGO BAY WHARF", "ROUTE 3-HOME DELIVERY(*certain areas and items excluded)", "Jamaica(Kingston)", "Jamaica(Montego Bay)", "Trinidad(Port-Of-Spain)", "Guyana(Georgetown)",
				"Grenada(St Georges)", "Dominica(Roseau)", "Barbados(Bridgetown)", "St Kitts(Basseterre)", "St Lucia", "St Vincent"};

		btn_submit = (Button) findViewById(R.id.B_quote);
		ship = (EditText) findViewById(R.id.et_quote1);
		s = (Spinner) findViewById(R.id.spn);
		name = (EditText) findViewById(R.id.et_quote2);
		email_id = (EditText) findViewById(R.id.et_quote3);
		contact_no = (EditText) findViewById(R.id.et_quote4);
		address = (EditText) findViewById(R.id.et_quote5);
		zipcode = (EditText) findViewById(R.id.et_quote6);
		subject = (EditText) findViewById(R.id.et_quote7);
		size_qty = (EditText) findViewById(R.id.et_quote8);


		back = (LinearLayout) findViewById(R.id.Lcontact_us);
		dashb = (ImageView) findViewById(R.id.imageView51);


		quote_0 = (TextView) findViewById(R.id.quote);
	/*	quote_1 = (TextView) findViewById(R.id.quote1);
		quote_2 = (TextView) findViewById(R.id.quote2);
		quote_3 = (TextView) findViewById(R.id.quote3);
		quote_4 = (TextView) findViewById(R.id.quote4);
		quote_5 = (TextView) findViewById(R.id.quote5);
		quote_6 = (TextView) findViewById(R.id.quote6);
		quote_7 = (TextView) findViewById(R.id.quote7);
		quote_8 = (TextView) findViewById(R.id.quote8);
	*/	quote_9 = (TextView) findViewById(R.id.quote9);
		quote_10 = (TextView) findViewById(R.id.quote10);
		quote_11 = (TextView) findViewById(R.id.quote11);
		quote_12 = (TextView) findViewById(R.id.quote12);
		quote_13 = (TextView) findViewById(R.id.quote13);
		quote_14 = (TextView) findViewById(R.id.quote14);
		quote_15 = (TextView) findViewById(R.id.quote15);
		quote_16 = (TextView) findViewById(R.id.quote16);
		quote_17 = (TextView) findViewById(R.id.quote17);
		quote_18 = (TextView) findViewById(R.id.quote18);
//		copyright = (TextView) findViewById(R.id.txt_copyright);


		tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "rr.ttf");


		btn_submit.setTypeface(tf);
		ship.setTypeface(tf);
		name.setTypeface(tf);
		email_id.setTypeface(tf);
		contact_no.setTypeface(tf);
		address.setTypeface(tf);
		zipcode.setTypeface(tf);
		subject.setTypeface(tf);
		size_qty.setTypeface(tf);
		quote_0.setTypeface(tf);
	/*	quote_1.setTypeface(tf);
		quote_2.setTypeface(tf);
		quote_3.setTypeface(tf);
		quote_4.setTypeface(tf);
		quote_5.setTypeface(tf);
		quote_6.setTypeface(tf);
		quote_7.setTypeface(tf);
		quote_8.setTypeface(tf);
	*/	quote_9.setTypeface(tf);
		quote_10.setTypeface(tf);
		quote_11.setTypeface(tf);
		quote_12.setTypeface(tf);
		quote_13.setTypeface(tf);
		quote_14.setTypeface(tf);
		quote_15.setTypeface(tf);
		quote_16.setTypeface(tf);
		quote_17.setTypeface(tf);
		quote_18.setTypeface(tf);
		//	copyright.setTypeface(tf);

		final List<String> plantsList = new ArrayList<>(Arrays.asList(search_items));


		final CustomAdapter arrayAdapter = new CustomAdapter(this, R.layout.spinner_item_list, plantsList) {
			@Override
			public boolean isEnabled(int position) {
				if (position == 0) {
					// Disable the first item from Spinner
					// First item will be use for hint
					return false;
				} else {
					return true;
				}
			}

			@Override
			public View getDropDownView(int position, View convertView,
										ViewGroup parent) {
				View view = super.getDropDownView(position, convertView, parent);
				TextView tv = (TextView) view;
				tv.setTypeface(tf);
				if (position == 0) {
					tv.setTextColor(Color.GRAY);
				} else {
					tv.setTextColor(Color.GRAY);
				}
				return view;
			}
		};
		s.setAdapter(arrayAdapter);


		btn_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				shippingFrom = ship.getText().toString();
				Log.e("tag", "Shipping" + shippingFrom);
				destination = s.getSelectedItem().toString();
				fullName = name.getText().toString();
				Email = email_id.getText().toString();
				PhoneNo = contact_no.getText().toString();
				Address = address.getText().toString();
				PostalCode = zipcode.getText().toString();
				Subject = subject.getText().toString();
				Quantity = size_qty.getText().toString();


				if (!shippingFrom.isEmpty()) {
					if (!(fullName.isEmpty())) {
						if (!(Email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches())) {
							if (!(PhoneNo.isEmpty() || PhoneNo.length() < 10)) {
								if (!(PostalCode.isEmpty() || PostalCode.length() < 3)) {
									if (!Address.isEmpty()) {
										if (!Subject.isEmpty()) {
											if (!Quantity.isEmpty()) {
												sendMail();
											} else {
												size_qty.setError("Enter the Goods correctly");
												size_qty.requestFocus();
											}
										} else {
											subject.setError("Enter SUBJECT");
											subject.requestFocus();
										}
									} else {
										address.setError("Enter ADDRESS");
										address.requestFocus();
									}
								} else {
									zipcode.setError("Enter Zipcode");
									zipcode.requestFocus();
								}
							} else {
								contact_no.setError("Enter valid phone number");
								contact_no.requestFocus();

							}
						} else {
							email_id.setError("Enter a valid email address!");
							email_id.requestFocus();
						}
					} else {
						name.setError("Enter a Name!");
						name.requestFocus();
					}
				} else {
					ship.setError("Enter Shipping From!");
					ship.requestFocus();
				}


			}


		});





/*		fb=(ImageView) findViewById(R.id.imageView_fb);
		twi=(ImageView)findViewById(R.id.imageView_twi);
		ins=(ImageView)findViewById(R.id.imageView_ins);
    	pin=(ImageView)findViewById(R.id.imageView_pin);
  */
				back.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						Intent k = new Intent(Quote.this, Home.class);
						startActivity(k);

					}
				});

				/*dashb.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent i = new Intent(Quote.this,Home.class);
						startActivity(i);
					}
				});*/

/*

				quote_6.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						Intent h = new Intent(Intent.ACTION_DIAL);
						h.setData(Uri.parse("tel:020 339 77856"));
						startActivity(h);
					}
				});
				quote_7.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						Intent h = new Intent(Intent.ACTION_DIAL);
						h.setData(Uri.parse("tel:075 350 47120"));
						startActivity(h);
					}
				});
*/

	}

	public void sendMail() {
		storeval = "BrighterDayz" + "\n\t\t\t\t\t\t" + "\n\nQuote:" + "\n\n\n Shipping FROM:" + "\t" + shippingFrom + "\n Destination:\t" + destination + "\n Name:\t" + fullName + "\n Email:\t" + Email + "\n Contact No:\t" + PhoneNo + "\n Address:\t" + Address + "\n Zip Code:\t" + PostalCode + "\n Subject:\t" + Subject + "\n Size and Quantity:\t" + Quantity;
		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
				"mailto", "customerservice@bdzshipping.com", null));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
		emailIntent.putExtra(Intent.EXTRA_TEXT, storeval);
		startActivity(Intent.createChooser(emailIntent, "Send email..."));




	}

	@Override
	public void onBackPressed() {

		Intent i = new Intent(Quote.this,Home.class);
		startActivity(i);
		super.onBackPressed();
	}
}