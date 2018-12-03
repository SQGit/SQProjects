package com.sqindia.avcdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 
 * @author anfer
 * 
 */
public class listviewAdapterCheckbox extends BaseAdapter implements AdapterView.OnItemSelectedListener{
	public ArrayList<Model> productList;
	Activity activity;
	int activate_btn=0;

	public listviewAdapterCheckbox(Activity activity, ArrayList<Model> productList) {
		super();
		this.activity = activity;
		this.productList = productList;
	}

	@Override
	public int getCount() {
		return productList.size();
	}

	@Override
	public Object getItem(int position) {
		return productList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ViewHolder {
		TextView mSNo;
		TextView mProduct;
		TextView mCategory;
		CheckBox mPrice;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		LayoutInflater inflater = activity.getLayoutInflater();
		Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/areal.ttf");

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listview_row_checkbox, null);
			holder = new ViewHolder();
			holder.mProduct = (TextView) convertView.findViewById(R.id.product);
			holder.mPrice = (CheckBox) convertView.findViewById(R.id.spinner);

			if (position == 0)
			{
				holder.mProduct.setBackgroundColor(Color.parseColor("#f9fcd0"));
				holder.mProduct.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star, 0, 0, 0);
				holder.mProduct.setTypeface(holder.mProduct.getTypeface(), Typeface.BOLD);
			}

			if (position == 1)
			{
				holder.mProduct.setBackgroundColor(Color.parseColor("#f9fcd0"));
				holder.mProduct.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star, 0, 0, 0);
				holder.mProduct.setTypeface(holder.mProduct.getTypeface(), Typeface.BOLD);
			}

			if (position == 2)
			{
				holder.mProduct.setBackgroundColor(Color.parseColor("#f9fcd0"));
				holder.mProduct.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star, 0, 0, 0);
				holder.mProduct.setTypeface(holder.mProduct.getTypeface(), Typeface.BOLD);
			}

			if (position == 3)
			{
				holder.mProduct.setBackgroundColor(Color.parseColor("#f9fcd0"));
				holder.mProduct.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star, 0, 0, 0);
				holder.mProduct.setTypeface(holder.mProduct.getTypeface(), Typeface.BOLD);
			}
			holder.mPrice.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v)
				{
					if(holder.mPrice.isChecked()){
						String s = productList.get(position).getProduct();
						MainActivity.firstundr.add(s+"#colorten");
						if (s.length() > 0) {
							activate_btn++;
						}
						SparseBooleanArray checked = MainActivity.rightrear_lv.getCheckedItemPositions();

					}else {
                        String find_txt = productList.get(position).getProduct();
                        MainActivity.firstundr.remove(find_txt+"#colorten");

						if (activate_btn > 0)
						{
							activate_btn--;
							if (activate_btn == 0) {
							} else {
							}
						} else {

						}

					}

				}
			});

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Model item = productList.get(position);
		holder.mProduct.setText(item.getProduct().toString());


		return convertView;
	}


	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if(position>0)
		{
			if (view != null) {
				InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
			}
			MainActivity.one_btn.setBackground(activity.getResources().getDrawable(R.drawable.circle_shape_fe));
			String s = productList.get(position).getProduct();
			MainActivity.firstundr.add(s);
		}
	}

	public void onNothingSelected(AdapterView<?> parent)
    {

	}

	static final String[] UnderCarriageFront = new String[] {"Ok","Damage"};

}