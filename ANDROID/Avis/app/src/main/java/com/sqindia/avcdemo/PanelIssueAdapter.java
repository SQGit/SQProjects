package com.sqindia.avcdemo;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author anfer
 */
public class PanelIssueAdapter extends BaseAdapter {
    public ArrayList<String> productList;
    public ArrayList<String> colorlist;
    Typeface tf;
    Activity activity;
    ViewHolder holder;
    static final String[] UnderCarriageFront = new String[]{"Repair", "Not Repair"};
    CustomButtonListener customButtonListener;
    public ArrayList quantity = new ArrayList();
    int sum = 0;
    int sum1 = 0;
        int quantityet;
    static String sticker;

    public PanelIssueAdapter(Activity activity, ArrayList<String> productList,ArrayList<String> colorlist) {
            super();
            this.activity = activity;
            this.productList = productList;
            this.colorlist=colorlist;

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

    public void setCustomButtonListener(CustomButtonListener customButtonListner) {
        this.customButtonListener = customButtonListner;
    }

    private class ViewHolder {
        MyCustomEditTextListener mycustom;

        public ViewHolder(MyCustomEditTextListener mycustom) {
            this.mycustom = mycustom;
        }

        TextView mSNo;
        EditText mProduct, ramp_et, abgrepair_et;
        TextView mCategory;
        Spinner mPrice;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        LayoutInflater inflater = activity.getLayoutInflater();
        tf = Typeface.createFromAsset(activity.getAssets(), "fonts/areal.ttf");

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.avis_page_second, null);
            holder = new ViewHolder(new MyCustomEditTextListener());

            holder.mProduct = (EditText) convertView.findViewById(R.id.panalissue_tv);
            holder.ramp_et = (EditText) convertView.findViewById(R.id.ramp_et);
            holder.abgrepair_et = (EditText) convertView.findViewById(R.id.abget);
            holder.mPrice = (Spinner) convertView.findViewById(R.id.spn1);


            holder.mProduct.setTypeface(tf);
            holder.ramp_et.setTypeface(tf);
            holder.abgrepair_et.setTypeface(tf);


            String str=productList.get(position);

            Log.e("tag","@@@@@@@@@@@@@@@@------GUNA>"+str);


                String[] splited=str.split("#");

                String content=splited[0];
                String color=splited[1];
                Log.e("tag","@@@@@@@@@@@@@@@@1------>"+content);
                Log.e("tag","@@@@@@@@@@@@@@@@2------>"+color);


                holder.mProduct.setText(content);
                //String str_colo=colorlist.get(position);

                //############################## SET COLOR BACKGROUND ##########################
                if(color.equals("colorone"))
                {
                    holder.mProduct.setBackgroundColor(Color.parseColor("#00285F"));
                    holder.mProduct.setTextColor(Color.WHITE);
                }

                if(color.equals("colortwo"))
                {
                    holder.mProduct.setBackgroundColor(Color.parseColor("#0097A7"));
                    holder.mProduct.setTextColor(Color.WHITE);
                }

                if(color.equals("colorthree"))
                {
                    holder.mProduct.setBackgroundColor(Color.parseColor("#F57F17"));
                    holder.mProduct.setTextColor(Color.WHITE);
                }


                if(color.equals("colorfour"))
                {
                    holder.mProduct.setBackgroundColor(Color.parseColor("#4CAF50"));
                    holder.mProduct.setTextColor(Color.WHITE);
                }

                if(color.equals("colorfive"))
                {
                    holder.mProduct.setBackgroundColor(Color.parseColor("#0D47A1"));
                    holder.mProduct.setTextColor(Color.WHITE);
                }

                if(color.equals("colorsix"))
                {
                    holder.mProduct.setBackgroundColor(Color.parseColor("#37474F"));
                    holder.mProduct.setTextColor(Color.WHITE);
                }


                if(color.equals("colorseven"))
                {
                    holder.mProduct.setBackgroundColor(Color.parseColor("#004D40"));
                    holder.mProduct.setTextColor(Color.WHITE);
                }

                if(color.equals("coloreight"))
                {
                    holder.mProduct.setBackgroundColor(Color.parseColor("#1B5E20"));
                    holder.mProduct.setTextColor(Color.WHITE);
                }


                if(color.equals("colornine"))
                {
                    holder.mProduct.setBackgroundColor(Color.parseColor("#4E342E"));
                    holder.mProduct.setTextColor(Color.WHITE);
                }

                if(color.equals("colorten"))
                {
                    holder.mProduct.setBackgroundColor(Color.parseColor("#AD1457"));
                    holder.mProduct.setTextColor(Color.WHITE);
                }




            holder.ramp_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    Log.e("tag", "----before----->" + s);

                    int val = 0;
                    if (s.length() > 0) {
                        //  Log.e("tag", "----before----->" + s);
                        val = Integer.parseInt(s.toString());
                        sum -= val;
                        if (sum < 0)
                            sum = 0;
                    }


                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.e("tag", "----after----->" + s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // SecondPage.ramptotal.setText("$ " + sum);
                    Log.e("tag", "----sum----->" + sum);

                        String qtyString = s.toString().replace("$", "").trim();
                        quantityet = qtyString.equals("") ? 0 : Integer.valueOf(qtyString);
                        sum = sum + quantityet;
                        SecondPage.ramptotal.setText("$ " + sum);

                }
            });


            holder.abgrepair_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    Log.e("tag", "----before----->" + s);

                    int val = 0;
                    if (s.length() > 0) {
                        //  Log.e("tag", "----before----->" + s);
                        val = Integer.parseInt(s.toString());
                        Log.e("tag", "----val----->" + val);
                        sum1 -= val;
                        Log.e("tag", "----sum----->" + sum1);
                        if (sum1 < 0)
                            sum1 = 0;
                    }


                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.e("tag", "----after----->" + s.toString());

                    if(s.toString().equals("$ 0"))
                    {
                        SecondPage.img_sticker.setVisibility(View.INVISIBLE);
                        sticker="disable";
                    }
                    else if(s.toString().equals(""))
                    {
                        SecondPage.img_sticker.setVisibility(View.INVISIBLE);
                        sticker="disable";
                    }
                    else
                    {
                        SecondPage.img_sticker.setVisibility(View.VISIBLE);
                        sticker="enable";
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {
                    SecondPage.ramptotal.setText("$ " + sum);
                    Log.e("tag", "----sum----->" + sum1);

                    String qtyString = s.toString().replace("$", "").trim();
                    quantityet = qtyString.equals("") ? 0 : Integer.valueOf(qtyString);
                    sum1 = sum1 + quantityet;
                    SecondPage.abgrepair.setText("$ " + sum1);



                }


            });

            final CustomAdapter arrayAdapter = new CustomAdapter(activity, android.R.layout.simple_spinner_item, UnderCarriageFront) {
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
                    tv.setTypeface(tf);
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
                    tv.setTextSize(12);
                    tv.setPadding(0, 0, 0, 0);

                    tv.setTypeface(tf);
                    if (position == 0) {
                        tv.setTextColor(Color.BLACK);
                    } else {
                        tv.setTextColor(Color.BLACK);
                    }
                    return view;
                }
            };


            holder.mPrice.setAdapter(arrayAdapter);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;


        public void updatePosition(int pos) {
            position = pos;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() > 0) {

                if (editable.length() == 1) {

                    if (!editable.toString().equals(".")) {

                        // lineItems.get(position).setQty(Double.parseDouble(editable.toString()));
                    } else {

                        //  lineItems.get(position).setQty(0);
                    }
                } else {

                    //  lineItems.get(position).setQty(Double.parseDouble(editable.toString()));
                }
            } else {

                //   lineItems.get(position).setQty(0);
            }
        }
    }

}