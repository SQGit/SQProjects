package com.sqindia.avcdemo;

import java.util.ArrayList;

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
import android.widget.Spinner;
import android.widget.TextView;

/**
 * @author anfer
 */
public class undercarriageAdapter extends BaseAdapter {
    public ArrayList<Model> productList;
    Activity activity;
    ViewHolder holder;
    //static final String[] UnderCarriageFront = new String[]{"Ok", "Damage"};
    int c = 0;
    String[] DamageList;

    public undercarriageAdapter(Activity activity, ArrayList<Model> productList) {
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
        Spinner mPrice;
    }


    /* @Override
     public void onItemSelected(AdapterView<?> parent, View view,
                                int position, long id) {
         Log.e("tag", "<-------posssss>>>>>>>>>>>>>>>>>>>>>>----------->" + position);
         if (position > 0) {
             MainActivity.five_btn.setBackground(activity.getResources().getDrawable(R.drawable.circle_shape_blue));

           //  String text = holder.mProduct.getSelectedItem().toString();
             Log.e("tag", "<-------posssss11111----------->" + position);

             String s = productList.get(position).getProduct();
             Log.e("tag", "<-------aseddsda11111----------->" + s);


         }
     }
     @Override
     public void onNothingSelected(AdapterView<?> adapterView) {
         MainActivity.five_btn.setBackground(activity.getResources().getDrawable(R.drawable.circle_shape));

     }*/
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();
        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/areal.ttf");

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_row, null);
            holder = new ViewHolder();
            //	holder.mSNo = (TextView) convertView.findViewById(R.id.sNo);
            holder.mProduct = (TextView) convertView.findViewById(R.id.product);
            //	holder.mCategory = (TextView) convertView//
            // 	.findViewById(R.id.category);

            holder.mPrice = (Spinner) convertView.findViewById(R.id.spinner);





            String status = productList.get(position).getHighlightStatus();
            if (status.equals("true")){
                holder.mProduct.setBackgroundColor(Color.parseColor("#f9fcd0"));
                holder.mProduct.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star, 0, 0, 0);
                holder.mProduct.setTypeface(holder.mProduct.getTypeface(), Typeface.BOLD);

                DamageList = new String[]{
                        "Select","Ok", "Not Ok","Damage"};

                MainActivity.underCarriageAdapterStatusMap.put(position,"");

            }
            else {
                DamageList = new String[]{
                        "Ok", "Not Ok","Damage"};
                MainActivity.underCarriageAdapterStatusMap.put(position,"Ok");
            }

            final CustomAdapter arrayAdapter = new CustomAdapter(activity, android.R.layout.simple_spinner_item, DamageList) {
                @Override
                public boolean isEnabled(int position) {
                    if (position == 0) {
                        return true;
                    } else {
                        return true;
                    }
                }

                @Override
                public View getDropDownView(int position, View convertView,
                                            ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    //tv.setTypeface(opensans);
                    tv.setTextSize(16);
                    tv.setPadding(10, 10, 10, 10);
                    if (position == 0) {
                        tv.setTextColor(Color.BLACK);
                    } else {
                        tv.setTextColor(Color.BLACK);
                    }
                    return view;
                }


                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    tv.setTextSize(10);
                    tv.setPadding(4, 0, 0, 0);

                    //	tv.setTypeface(opensans);
                    if (position == 0) {
                        tv.setTextColor(Color.BLACK);
                    } else {
                        tv.setTextColor(Color.BLACK);
                    }
                    return view;
                }
            };


            holder.mPrice.setAdapter(arrayAdapter);


            //spinner.setPaddingSafe(0, 0, 0, 0);
            // ArrayAdapter<String> adapter=new ArrayAdapter<String>(activity,
            // android.R.layout.simple_spinner_item, UnderCarriageFront);
            // adapter.setDropDownViewResource(
            // android.R.layout.simple_spinner_dropdown_item);
            // holder.mPrice.setAdapter(adapter);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Model item = productList.get(position);
        holder.mProduct.setText(item.getProduct().toString());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   String s = productList.get(position).getProduct();
                // Log.e("tag", "<-------aseddsda----------->" + s);
            }
        });






        holder.mPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Log.e("tag", "<-------pos----------->" + pos);
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }


                String itemStatus = parent.getAdapter().getItem(pos).toString();
                Log.e("tag","123 GUNA CHECK"+itemStatus);

                MainActivity.underCarriageAdapterStatusMap.put(position,itemStatus);


                if (itemStatus.equals("Damage")) {
                    String s = productList.get(position).getProduct();
                    Log.e("tag", "<-------aseddsda----------->" + s);
                    Log.e("tag", "<-------dummy----------->" + s);
                    MainActivity.firstundr.add(s+"#colorone");
                    MainActivity.colorcode.add("one");
                    if (s.length() > 0) {
                        c++;
                    }
                    SparseBooleanArray checked = MainActivity.rightrear_lv.getCheckedItemPositions();
                    Log.e("tag", "<-------checked----------->" + checked);
                    MainActivity.one_btn.setBackground(activity.getResources().getDrawable(R.drawable.circle_shape_blue));

                }

                else if(itemStatus.equals("Ok"))
                {
                    Log.e("tag","123 GUNA CHECK2"+itemStatus);
                    String find_txt = productList.get(position).getProduct();
                    Log.e("tag","123 GUNA CHECK4"+find_txt);
                    //List<String> listClone = new ArrayList<String>();
                    MainActivity.firstundr.remove(find_txt+"#colorone");
                    //MainActivity.colorcode.remove("two");


                    if (c > 0)
                    {
                        c--;
                        if (c == 0) {
                            MainActivity.one_btn.setBackground(activity.getResources().getDrawable(R.drawable.circle_shape));
                        } else {
                            MainActivity.one_btn.setBackground(activity.getResources().getDrawable(R.drawable.circle_shape_blue));
                        }
                    } else {
                        MainActivity.one_btn.setBackground(activity.getResources().getDrawable(R.drawable.circle_shape));
                    }

                }


                else if(itemStatus.equals("Not Ok"))
                {
                    String s = productList.get(position).getProduct();
                    Log.e("tag", "<-------aseddsda----------->" + s);
                    Log.e("tag", "<-------dummy----------->" + s);
                    MainActivity.firstundr.add(s+"#colorone");
                    MainActivity.colorcode.add("one");
                    if (s.length() > 0) {
                        c++;
                    }
                    SparseBooleanArray checked = MainActivity.rightrear_lv.getCheckedItemPositions();
                    Log.e("tag", "<-------checked----------->" + checked);
                    MainActivity.one_btn.setBackground(activity.getResources().getDrawable(R.drawable.circle_shape_blue));


                }


                else {
                    Log.e("tag", "<-------checkedaaaa----------->" + c);
                    // c--;
                    Log.e("tag", "<-------checkedqqqq----------->" + c);
                    if (c > 0)
                    {
                        c--;
                        if (c == 0) {
                            MainActivity.one_btn.setBackground(activity.getResources().getDrawable(R.drawable.circle_shape));
                        } else {
                            MainActivity.one_btn.setBackground(activity.getResources().getDrawable(R.drawable.circle_shape_blue));
                        }
                    } else {
                        MainActivity.one_btn.setBackground(activity.getResources().getDrawable(R.drawable.circle_shape));
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        return convertView;
    }


}