package sqindia.net.brighterdayz;
 
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Toast;

public class SampleExpandableListAdapter extends BaseExpandableListAdapter {
	public Context context;

    private LayoutInflater vi;
    private String[][] data;
    int _objInt;
    public static Boolean checked[] = new Boolean[1];
  Typeface tf;
   

    private static final int GROUP_ITEM_RESOURCE = R.layout.group_item;
    private static final int CHILD_ITEM_RESOURCE = R.layout.child_item;
    public String[]check_string_array;
    
    public SampleExpandableListAdapter(Context context, Activity activity, String[][] data) {
        this.data = data;
        this.context = context;
        vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         tf = Typeface.createFromAsset(context.getAssets(),"rr.ttf");

        _objInt = data.length;
        check_string_array = new String[_objInt];
        //popolaCheckMap();
    }
    /*public void popolaCheckMap(){

    	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);  
    	String buffer = null;
        
        for(int i=0; i<_objInt; i++){
        	buffer = settings.getString(String.valueOf((int)i),"false");
        	if(buffer.equals("false"))
        		checkboxMap.put((long)i, false);
        	else checkboxMap.put((long)i, true);
        }
    }
    */
   /* public class CheckListener implements OnCheckedChangeListener{

        long pos;

        public void setPosition(long p){
            pos = p;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                boolean isChecked) {
        	Log.i("checkListenerChanged", String.valueOf(pos)+":"+String.valueOf(isChecked));
             
            if(isChecked == true) check_string_array[(int)pos] = "true";
            else				  check_string_array[(int)pos] = "false";
           // save checkbox state of each group
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);                 
            SharedPreferences.Editor preferencesEditor = settings.edit(); 
            preferencesEditor.putString(String.valueOf((int)pos), check_string_array[(int)pos]);
            preferencesEditor.commit(); 
        }
    }
*/    public String getChild(int groupPosition, int childPosition) {
        return data[groupPosition][childPosition];
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public int getChildrenCount(int groupPosition) {
        return data[groupPosition].length;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


        View v = convertView;
        String child = getChild(groupPosition, childPosition);
        int id_res = 0; 
        if(groupPosition == 0){ 
        /*	if(childPosition == 0) id_res = R.drawable.s5;
        	if(childPosition == 1) id_res = R.drawable.s4;
        	if(childPosition == 2) id_res = R.drawable.phone_contacts;*/
        }
        else if(groupPosition == 1){ 
        	/*if(childPosition == 0) id_res = R.drawable.s5;
        	if(childPosition == 1) id_res = R.drawable.s4;*/
        }
        else if(groupPosition == 2){ 
        	/*if(childPosition == 0) id_res = R.drawable.s5;
        	if(childPosition == 1) id_res = R.drawable.s5;
        	if(childPosition == 2) id_res = R.drawable.s5;*/
        }
        
        if (child != null) {
            v = vi.inflate(CHILD_ITEM_RESOURCE, null);
            ViewHolder holder = new ViewHolder(v);
            holder.text.setText(Html.fromHtml(child));
            holder.text.setTypeface(tf);

            //holder.imageview.setImageResource(id_res);
        }

      /*  v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,"dial phone",Toast.LENGTH_LONG).show();
            }
        });
*/





        return v;
    }

    public String getGroup(int groupPosition) {
        return "group-" + groupPosition;
    }

    public int getGroupCount() {
        return data.length;
    }


    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = convertView;
        String group = null;
        int id_res = 0;
        long group_id = getGroupId(groupPosition);
        if(group_id == 0){
        	group = "JAMAICA(KINGSTON)";
        	id_res = R.drawable.s2;
        }
        else if(group_id == 1){
        	group = "JAMAICA(MONTEGO BAY)"; 
        	id_res = R.drawable.s2;
        }
        else if(group_id == 2){
        	group = "TRINIDAD(PORT-OF-SPAIN)";
        	id_res = R.drawable.s3;
        }
        else if(group_id == 3){
        	group = "GUYANA(GEORGETOWN)";
        	id_res = R.drawable.g;
        }
        else if(group_id == 4){
        	group = "GRENADA(ST GEORGES)";
        	id_res = R.drawable.grenada;
        }
        

        else if(group_id == 5){
        	group = "DOMINICA(ROSEAU)";
        	id_res = R.drawable.dominica;
        }
        

        else if(group_id == 6){
        	group = "BARBADOS(BRIDGETOWN)";
        	id_res = R.drawable.barbados;
        }
        
        else if(group_id == 7){
        	group = "ST KITTS(BASSETERRE)";
        	id_res = R.drawable.st_kitts;
        }
        
        else if(group_id == 8){
        	group = "ST.LUCIA";
        	id_res = R.drawable.st_lucia;
        }
        
        else if(group_id == 9){
        	group = "ST.VINCENT";
        	id_res = R.drawable.st_vincent;
        }

        
        if (group != null) {
            v = vi.inflate(GROUP_ITEM_RESOURCE, null);
            ViewHolder holder = new ViewHolder(v);

            holder.text.setText(Html.fromHtml(group));
            holder.imageview.setImageResource(id_res);
            holder.text.setTypeface(tf);
/*
            holder.text.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {
                  //  Intent intent = new Intent(Intent.ACTION_CALL);

                }
            });
*/
        }
        return v;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public boolean hasStableIds() {
        return true;
    }
} 