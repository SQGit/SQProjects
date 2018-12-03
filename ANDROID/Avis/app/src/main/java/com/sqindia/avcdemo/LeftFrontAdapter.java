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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author anfer
 */
public class LeftFrontAdapter extends BaseAdapter implements AdapterView.OnItemSelectedListener {
    public ArrayList<Model> productList;
    Activity activity;
    ViewHolder holder;
    int activate_btn = 0;
    HashMap<Integer, String> hmap = new HashMap<Integer, String>();
    String[] DamageList;

    public LeftFrontAdapter(Activity activity, ArrayList<Model> productList) {
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int position, long id)
    {}
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        MainActivity.nine_btn.setBackground(activity.getResources().getDrawable(R.drawable.circle_shape));
    }

    private class ViewHolder {
        TextView mProduct;
        Spinner mBroken, mDamage;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = activity.getLayoutInflater();
        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/areal.ttf");
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_row_gear, null);
            holder = new ViewHolder();
            holder.mProduct = (TextView) convertView.findViewById(R.id.product);

            holder.mBroken = (Spinner) convertView.findViewById(R.id.spinner_broken);
            holder.mDamage = (Spinner) convertView.findViewById(R.id.spinner);
            holder.mBroken.setEnabled(false);




            holder.mDamage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }


                    String itemStatus = parent.getAdapter().getItem(pos).toString();
                    MainActivity.leftFrontAdapterStatusMap.put(position,itemStatus);


                    if (itemStatus.equals("Damage")) {
                        String treadSize=productList.get(position).getTreadSize();
                        if(treadSize.equals("normal"))
                        {
                            String s = productList.get(position).getProduct();
                            String color_code="#4E342E";
                            hmap.put(9, s);
                            MainActivity.firstundr.add(s+"#colornine");
                            MainActivity.colorcode.add("nine");
                            ((View)(((View)view.getParent()).getParent())).findViewById(R.id.spinner_broken).setEnabled(true);
                            ((View)(((View)view.getParent()).getParent())).findViewById(R.id.spinner_broken).setVisibility(View.VISIBLE);

                            if (s.length() > 0) {
                                activate_btn++;
                            }
                            SparseBooleanArray checked = MainActivity.rightrear_lv.getCheckedItemPositions();
                            MainActivity.nine_btn.setBackground(activity.getResources().getDrawable(R.drawable.circle_shape_lf));

                        }
                        else
                        {

                            MainActivity.left_front_teaedsize.setText("16");
                            MainActivity.left_front_teaedsize.setBackgroundColor(Color.TRANSPARENT);
                            String s = productList.get(position).getProduct();
                            String color_code="#4E342E";
                            hmap.put(9, s);
                            MainActivity.firstundr.add(s+"#colornine");
                            MainActivity.colorcode.add("nine");
                            ((View)(((View)view.getParent()).getParent())).findViewById(R.id.spinner_broken).setEnabled(true);
                            ((View)(((View)view.getParent()).getParent())).findViewById(R.id.spinner_broken).setVisibility(View.VISIBLE);

                            if (s.length() > 0) {
                                activate_btn++;
                            }
                            SparseBooleanArray checked = MainActivity.rightrear_lv.getCheckedItemPositions();
                            MainActivity.nine_btn.setBackground(activity.getResources().getDrawable(R.drawable.circle_shape_lf));

                           /* if (MainActivity.left_front_teaedsize.getText().toString().length() > 0) {
                                String s = productList.get(position).getProduct();
                                String color_code="#4E342E";
                                hmap.put(9, s);
                                MainActivity.firstundr.add(s+"#colornine");
                                MainActivity.colorcode.add("nine");
                                ((View)(((View)view.getParent()).getParent())).findViewById(R.id.spinner_broken).setEnabled(true);
                                ((View)(((View)view.getParent()).getParent())).findViewById(R.id.spinner_broken).setVisibility(View.VISIBLE);

                                if (s.length() > 0) {
                                    activate_btn++;
                                }
                                SparseBooleanArray checked = MainActivity.rightrear_lv.getCheckedItemPositions();
                                MainActivity.nine_btn.setBackground(activity.getResources().getDrawable(R.drawable.circle_shape_lf));
                            }
                            else
                            {
                                MainActivity.left_front_teaedsize.requestFocus();
                                Toast.makeText(activity,"Please Insert Tread Size for Left Front",Toast.LENGTH_LONG).show();
                                MainActivity.left_front_teaedsize.setBackgroundColor(Color.parseColor("#f9fcd0"));
                            }*/
                        }
                    }


                    else if(itemStatus.equals("Ok"))
                    {
                        MainActivity.left_front_teaedsize.setText("");
                        MainActivity.left_front_teaedsize.setBackgroundColor(Color.parseColor("#ffffff"));
                        String find_txt = productList.get(position).getProduct();
                        MainActivity.firstundr.remove(find_txt+"#colornine");

                        ((View)(((View)view.getParent()).getParent())).findViewById(R.id.spinner_broken).setEnabled(false);
                        ((View)(((View)view.getParent()).getParent())).findViewById(R.id.spinner_broken).setVisibility(View.INVISIBLE);


                        if (activate_btn > 0)
                        {
                            activate_btn--;
                            if (activate_btn == 0) {
                                MainActivity.nine_btn.setBackground(activity.getResources().getDrawable(R.drawable.circle_shape));
                            } else {
                                MainActivity.nine_btn.setBackground(activity.getResources().getDrawable(R.drawable.circle_shape_lf));
                            }
                        } else {
                            MainActivity.nine_btn.setBackground(activity.getResources().getDrawable(R.drawable.circle_shape));
                        }

                    }


                    else if(itemStatus.equals("Not Ok"))
                    {
                        String s = productList.get(position).getProduct();
                        String color_code="#4E342E";
                        hmap.put(9, s);
                        MainActivity.firstundr.add(s+"#colornine");
                        MainActivity.colorcode.add("nine");
                        ((View)(((View)view.getParent()).getParent())).findViewById(R.id.spinner_broken).setEnabled(true);
                        ((View)(((View)view.getParent()).getParent())).findViewById(R.id.spinner_broken).setVisibility(View.VISIBLE);

                        if (s.length() > 0) {
                            activate_btn++;
                        }
                        SparseBooleanArray checked = MainActivity.rightrear_lv.getCheckedItemPositions();
                        MainActivity.nine_btn.setBackground(activity.getResources().getDrawable(R.drawable.circle_shape_lf));
                    }


                    else {
                        ((View)(((View)view.getParent()).getParent())).findViewById(R.id.spinner_broken).setEnabled(false);
                        ((View)(((View)view.getParent()).getParent())).findViewById(R.id.spinner_broken).setVisibility(View.INVISIBLE);

                        if (activate_btn > 0)
                        {
                            activate_btn--;
                            if (activate_btn == 0) {
                                MainActivity.nine_btn.setBackground(activity.getResources().getDrawable(R.drawable.circle_shape));
                            } else {
                                MainActivity.nine_btn.setBackground(activity.getResources().getDrawable(R.drawable.circle_shape_lf));
                            }
                        } else {
                            MainActivity.nine_btn.setBackground(activity.getResources().getDrawable(R.drawable.circle_shape));
                        }

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

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
                    tv.setTextSize(9);
                    tv.setPadding(2, 1, 0, 1);

                    if (position == 0) {
                        tv.setTextColor(Color.BLACK);
                    } else {
                        tv.setTextColor(Color.BLACK);
                    }

                    return view;
                }
            };
            holder.mBroken.setAdapter(arrayAdapter);

            String status = productList.get(position).getHighlightStatus();

            if (status.equals("true")){
                holder.mProduct.setBackgroundColor(Color.parseColor("#f9fcd0"));
                holder.mProduct.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star, 0, 0, 0);
                holder.mProduct.setTypeface(holder.mProduct.getTypeface(), Typeface.BOLD);

                DamageList = new String[]{
                        "Select","Ok","Not Ok", "Damage"};
                MainActivity.leftFrontAdapterStatusMap.put(position,"");

            }else {


                DamageList = new String[]{
                        "Ok", "Not Ok","Damage"};
                MainActivity.leftFrontAdapterStatusMap.put(position,"Ok");

            }


            final CustomAdapter arrayAdapter1 = new CustomAdapter(activity, android.R.layout.simple_spinner_item, DamageList) {
                @Override
                public boolean isEnabled(int position) {
                    if (position == 0) {
                        //   holder.mBroken.setEnabled(true);
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
                    tv.setTextSize(16);
                    tv.setPadding(5, 5, 5, 5);
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
                    tv.setTextSize(9);
                    tv.setPadding(4, 1, 0, 1);
                    //	tv.setTypeface(opensans);
                    if (position == 0) {
                        tv.setTextColor(Color.BLACK);
                    } else {
                        tv.setTextColor(Color.BLACK);
                    }
                    return view;
                }
            };



            holder.mDamage.setAdapter(arrayAdapter1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Model item = productList.get(position);
        holder.mProduct.setText(item.getProduct().toString());
        return convertView;
    }



    static final String[] UnderCarriageFront = new String[]
            {"Broken","Punctured","Dent", "Scratches", "Broken Glass", "Rip/Tear",};

}