package www.sqindia.net.ilerasoftadmin;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.github.clans.fab.FloatingActionMenu;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ANANDH on 12-12-2015.
 */
public class AddEquipmentForm extends AppCompatActivity implements CalendarDatePickerDialogFragment.OnDateSetListener {
    ArrayList<String> worldlist, worldlist1, worldlist2;
    ArrayList<WorldPopulation> world;
    ArrayList<MakeList> cl;
    ArrayList<BrandList> bl;
    ArrayList<CategoryList> CaL;
    TextView back;
    JSONObject jsonobject;
    JSONArray jsonarray;
    Button submit;
    public static String URL_REGISTER = "http://sqindia01.cloudapp.net/cologix/api/v1/equipment/getMakes";
    public static String URL_REGISTER1 = "http://sqindia01.cloudapp.net/cologix/api/v1/equipment/getCategories";
    public static String URL_REGISTER_BRAND = "http://sqindia01.cloudapp.net/cologix/api/v1/equipment/getBrands";
    String equipmentName, equipmentDescription, categoryId, brandId, makeId, equipmentId, date_of_purches, S_image_Browse;
    public static String Seession;
    ArrayList<HashMap<String, String>> contactList;
    String token, price, imagepath, selected_date;
    public EditText equipment_name, price_name, image_Browse, decription, date_of_purches1;
    public Spinner mySpinner1, mySpinner2, mySpinner;
    public String select_make_id, select_category_id, select_Brand_id;

    //################################################FAB BUTTON INTIALS##########################################################
    private Handler mUiHandler = new Handler();
    private List<FloatingActionMenu> menus = new ArrayList<>();

    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ image Process $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    private static final int MY_INTENT_CLICK = 302;
    String selectedImagePath;
    private ProgressDialog dialog;
    private SweetAlertDialog pDialog;

    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ image Process $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    //###########################################################################################################
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // final Bundle state = savedInstanceState;
        setContentView(R.layout.addequipmentform);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'><big><b>ADD EQUIPMENT</b></big> </font>"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xcececece));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.bck_arrow);

        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        image_Browse = (EditText) findViewById(R.id.image_browse);
        decription = (EditText) findViewById(R.id.decription);
        submit = (Button) findViewById(R.id.sub_id);
        equipment_name = (EditText) findViewById(R.id.eq_id);
        price_name = (EditText) findViewById(R.id.price_id);
        date_of_purches1 = (EditText) findViewById(R.id.date_of_purcheses);
        mySpinner1 = (Spinner) findViewById(R.id.my_spinner2);
        mySpinner2 = (Spinner) findViewById(R.id.my_spinner1);
        mySpinner = (Spinner) findViewById(R.id.my_spinner);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AddEquipmentForm.this);
        token = sharedPreferences.getString("TOKEN", "");
        new Make().execute();
        new Category().execute();
        new Brand().execute();

        image_Browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        date_of_purches1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

//******************************************************Better pickers ******************************************************************************************************
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment().setOnDateSetListener(AddEquipmentForm.this);
                cdp.setThemeDark(true);
                cdp.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
//******************************************************calendar old ******************************************************************************************************
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equipmentName = equipment_name.getText().toString();
                price = price_name.getText().toString();
                equipmentDescription = decription.getText().toString();
                date_of_purches = date_of_purches1.getText().toString();
                S_image_Browse = image_Browse.getText().toString();
                Log.d("TAG", "SELECT" + makeId);
                Log.d("TAG", "SELECT" + categoryId);
                Log.d("TAG", "SELECT" + brandId);

                if (Util.Operations.isOnline(AddEquipmentForm.this)) {
                    if (!equipmentName.isEmpty() && !price.isEmpty() && !date_of_purches.isEmpty() && !S_image_Browse.isEmpty()) {
                        new MyActivityAsync(equipmentName, equipmentDescription, categoryId, brandId, makeId, date_of_purches, price).execute();
                    } else {
                        new SweetAlertDialog(AddEquipmentForm.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("REQUEST ALERT?")
                                .setContentText("Please enter your details!")
                                .setConfirmText("Ok")
                                .show();
                    }

                } else {

                    new SweetAlertDialog(AddEquipmentForm.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("No Internet Connectivity")
                            .setConfirmText("Try Again")
                            .setCancelText("Cancel")
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent i = new Intent(AddEquipmentForm.this, Dashboard.class);
                                    startActivity(i);
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent i = new Intent(AddEquipmentForm.this, AddEquipmentForm.class);
                                    startActivity(i);
                                }
                            })
                            .show();
                }


            }


        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case android.R.id.home:

                Intent i=new Intent(getApplicationContext(),InventoryControl.class);
                startActivity(i);
                break;
            case R.id.action_refresh:
                Intent intent=new Intent(getApplicationContext(),Update_Equipment.class);
                startActivity(intent);

                break;

            default:
                break;
        }
        return true;
    }

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ offline image selected both @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    private void selectImage() {
        image_Browse.setText("");
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddEquipmentForm.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    File mydir = new File(Environment.getExternalStorageDirectory() + "/Ilerasoft/admin/devices/");
                    if (!mydir.exists())
                        mydir.mkdirs();
                    else
                        Log.d("error", "dir. already exists");
                    File image = new File(mydir, "devices" + timeStamp + ".png");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
                    selectedImagePath = Uri.fromFile(image).toString().substring(7);


                    image_Browse.setText(selectedImagePath);
                    try {
                        AddEquipmentForm.this.startActivityForResult(intent, 303);

                    } catch (ActivityNotFoundException e) {
                        // Toast.makeText(activity, R.string.crop__pick_error, Toast.LENGTH_SHORT).show();
                    }
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");

                    try {
                        intent.setType("*/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT).setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Select File"), MY_INTENT_CLICK);
                    } catch (ActivityNotFoundException e) {
                    }
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AddEquipmentForm.this.RESULT_OK) {
            if (requestCode == MY_INTENT_CLICK) {
                if (null == data) return;
                Uri selectedImageUri = data.getData();
                //MEDIA GALLERY

                selectedImagePath = ImageFilePath.getPath(AddEquipmentForm.this, selectedImageUri);
                //  upload_imageview.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath));
                Log.d("tag", "path" + selectedImagePath);
                image_Browse.setText(selectedImagePath);
                //txta.setText("File Path : \n"+selectedImagePath);
            }
        } else if (requestCode == 1 && resultCode == AddEquipmentForm.this.RESULT_OK) {
            File f = new File(Environment.getExternalStorageDirectory().toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }
            Uri path = Uri.fromFile(f);
        }
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int month, int dayOfMonth) {
        date_of_purches1.setText(year + "/" + (month + 1) + "/" + dayOfMonth);

        //  date_of_purches1.setText(getString(R.string.calendar_date_picker_result_values, year, monthOfYear+1, dayOfMonth));

    }

    @Override
    public void onResume() {
        // Example of reattaching to the fragment
        super.onResume();
        CalendarDatePickerDialogFragment calendarDatePickerDialogFragment = (CalendarDatePickerDialogFragment) getSupportFragmentManager()
                .findFragmentByTag(FRAG_TAG_DATE_PICKER);
        if (calendarDatePickerDialogFragment != null) {
            calendarDatePickerDialogFragment.setOnDateSetListener(this);
        }
    }

    //############################################# upload image process online  ###################################################
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
            @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new SweetAlertDialog(AddEquipmentForm.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Registering ...");
            pDialog.setContentText("please wait.......");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

        }

        @Override
        protected String doInBackground(Void... params) {

            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;
            //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

            try {

                HttpClient client = new DefaultHttpClient();
                HttpPost postMethod = new HttpPost("http://sqindia01.cloudapp.net/cologix/api/v1/equipment/addEquipmentImage");
                postMethod.addHeader("equipmentId", equipmentId);
                File file = new File(selectedImagePath);
                MultipartEntity entity = new MultipartEntity();
                FileBody contentFile = new FileBody(file);
                entity.addPart("fileUpload", contentFile);
                postMethod.setEntity(entity);
                HttpResponse response = client.execute(postMethod);
                HttpEntity r_entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: " + statusCode;
                }
            } catch (IOException e) {
                responseString = e.toString();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("TAG", "Response from server: " + s);
            super.onPostExecute(s);
            pDialog.dismiss();


            if(s==""){
                new SweetAlertDialog(AddEquipmentForm.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Internet Connectivity very slow? try again")
                        .setCancelText("Cancel")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent i = new Intent(AddEquipmentForm.this, InventoryControl.class);
                                startActivity(i);
                            }
                        })
                        .setConfirmText("Try again?")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent i = new Intent(AddEquipmentForm.this, AddEquipmentForm.class);
                                startActivity(i);

                            }
                        }).show();

            }else {

                try {
                    JSONObject jo = new JSONObject(s);
                    String ss=jo.getString("status");
                    Log.e("TAG", "Response from server after dissmiss dialog: " + ss);

                    if (ss.equals("SUCCESS")){
                        new SweetAlertDialog(AddEquipmentForm.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Registerd ")
                                .setContentText("Sucessfully")
                                .setConfirmText("Add New")
                                .setCancelText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                        Intent i = new Intent(AddEquipmentForm.this, AddEquipmentForm.class);
                                        startActivity(i);
                                    }
                                })
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent i = new Intent(AddEquipmentForm.this, Dashboard.class);
                                        startActivity(i);
                                    }
                                }).show();

                    }else {
                        new SweetAlertDialog(AddEquipmentForm.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Registering failed")
                                .setContentText("TRY AGAIN")
                                .setConfirmText("TRY AGAIN")
                                .setCancelText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                        Intent i = new Intent(AddEquipmentForm.this, AddEquipmentForm.class);
                                        startActivity(i);
                                    }
                                })
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent i = new Intent(AddEquipmentForm.this, Dashboard.class);
                                        startActivity(i);
                                    }
                                }).show();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }


        }
    }


    //############################################# upload image process online  ###################################################


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    class Make extends AsyncTask<String, Void, String> {
        public Make() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("tag", "s1");
        }

        protected String doInBackground(String... params) {
            cl = new ArrayList<MakeList>();
            // Create an array to populate the spinner
            worldlist1 = new ArrayList<String>();
            String json = "", jsonStr = "";

            try {

                JSONObject jsonObject = new JSONObject();
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest1(URL_REGISTER, json, token);


            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }


            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("tag", "<-----RESULTTTTTTT---->" + s);
            super.onPostExecute(s);
            String msg;
            try {
                JSONObject jo = new JSONObject(s);
                String status = jo.getString("status");
                msg = jo.getString("message");
                Log.d("tag", "<-----Status----->" + status);
                JSONObject data1 = jo.getJSONObject("data");
                jsonarray = data1.getJSONArray("makes");

                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);
                    MakeList cll = new MakeList();
                    cll.setMakeId(jsonobject.optString("makeId"));
                    cll.setMakeName(jsonobject.optString("makeName"));
                    cl.add(cll);
                    worldlist1.add(jsonobject.optString("makeName"));
                    Log.d("tag", "<----worldlist1----->" + worldlist1);

                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Spinner adapter
            mySpinner1.setAdapter(new ArrayAdapter<String>(AddEquipmentForm.this, android.R.layout.simple_spinner_dropdown_item, worldlist1));
            mySpinner1
                    .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> arg0,
                                                   View arg1, int position, long arg3) {
                            // TODO Auto-generated method stub
                            if (position > -1) {
                                makeId = cl.get(position).getMakeId();
                                Log.d("tag", "<----Make----->" + makeId);

                            }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    });

        }
    }

    class Category extends AsyncTask<String, Void, String> {
        public Category() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("tag", "s1");
        }

        protected String doInBackground(String... params) {
            CaL = new ArrayList<CategoryList>();
            // Create an array to populate the spinner
            worldlist2 = new ArrayList<String>();
            String json = "", jsonStr = "";
            try {

                JSONObject jsonObject = new JSONObject();
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest1(URL_REGISTER1, json, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("tag", "<-----RESULTTTTTTT---->" + s);
            super.onPostExecute(s);
            String msg;
            try {
                JSONObject jo = new JSONObject(s);


                String status = jo.getString("status");
                msg = jo.getString("message");
                Log.d("tag", "<-----Status----->" + status);
                JSONObject data1 = jo.getJSONObject("data");
                jsonarray = data1.getJSONArray("categories");

                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);

                    CategoryList clll = new CategoryList();

                    clll.setCategoryId(jsonobject.optString("categoryId"));
                    clll.setCategoryName(jsonobject.optString("categoryName"));
                    CaL.add(clll);

                    // Populate spinner with country names

                    worldlist2.add(jsonobject.optString("categoryName"));
                    Log.d("tag", "<----worldlist----->" + worldlist2);

                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            // Spinner adapter
            mySpinner2
                    .setAdapter(new ArrayAdapter<String>(AddEquipmentForm.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            worldlist2));
            mySpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    // TODO Auto-generated method stub
                    // String selectedItemText = (String) arg0.getItemAtPosition(position);

                    if (position > -1) {
                        categoryId = CaL.get(position).getCategoryId();
                        Log.d("tag", "<----categoryId----->" + categoryId);

                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });

        }
    }


    class Brand extends AsyncTask<String, Void, String> {
        public Brand() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("tag", "s1");
        }

        protected String doInBackground(String... params) {
            bl = new ArrayList<BrandList>();
            // Create an array to populate the spinner
            worldlist = new ArrayList<String>();
            String json = "", jsonStr = "";
            try {

                JSONObject jsonObject = new JSONObject();
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest1(URL_REGISTER_BRAND, json, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("tag", "<-----RESULTTTTTTT---->" + s);
            super.onPostExecute(s);
            String msg;
            try {
                JSONObject jo = new JSONObject(s);


                String status = jo.getString("status");
                msg = jo.getString("message");
                Log.d("tag", "<-----Status----->" + status);
                JSONObject data1 = jo.getJSONObject("data");
                jsonarray = data1.getJSONArray("brands");

                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);

                    BrandList bll = new BrandList();

                    bll.setBrandId(jsonobject.optString("brandId"));
                    bll.setBrandName(jsonobject.optString("brandName"));
                    bl.add(bll);

                    // Populate spinner with country names

                    worldlist.add(jsonobject.optString("brandName"));
                    Log.d("tag", "<----worldlist----->" + worldlist);

                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            // Spinner adapter
            mySpinner
                    .setAdapter(new ArrayAdapter<String>(AddEquipmentForm.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            worldlist));


            // Spinner on item click listener
            mySpinner
                    .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0,
                                                   View arg1, int position, long arg3) {
                            // TODO Auto-generated method stub

                            if (position > -1) {
                                brandId = bl.get(position).getBrandId();
                                Log.d("tag", "<----brandId----->" + brandId);

                            }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    });

        }
    }

    class MyActivityAsync extends AsyncTask<String, Void, String> {

        String equipmentName, equipmentDescription, categoryId, brandId, makeId, date_of_purches, price;

        public MyActivityAsync(String equipmentName,
                               String equipmentDescription,
                               String categoryId,
                               String brandId,
                               String makeId,
                               String date_of_purches,
                               String price

        ) {
            this.equipmentName = equipmentName;
            this.equipmentDescription = equipmentDescription;
            this.categoryId = categoryId;
            this.brandId = brandId;
            this.makeId = makeId;
            this.date_of_purches = date_of_purches;
            this.price = price;


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {


            String json = "", jsonStr = "";

            Log.d("tag", "<-----select---->" + equipmentName);
            Log.d("tag", "<-----select---->" + date_of_purches);
            Log.d("tag", "<-----select---->" + price);
            Log.d("tag", "<-----select---->" + equipmentDescription);
            Log.d("tag", "<-----select---->" + categoryId);
            Log.d("tag", "<-----select---->" + brandId);
            Log.d("tag", "<-----select---->" + makeId);


            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("equipmentName", equipmentName);
                jsonObject.accumulate("price", price);
                jsonObject.accumulate("purchaseDate", date_of_purches);
                jsonObject.accumulate("equipmentDescription", equipmentDescription);
                jsonObject.accumulate("categoryId", categoryId);
                jsonObject.accumulate("brandId", brandId);
                jsonObject.accumulate("makeId", makeId);
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest1("http://sqindia01.cloudapp.net/cologix/api/v1/equipment/add", json, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            Log.d("tag", "<-----rerseres---->" + s);

            super.onPostExecute(s);


            if (s == "") {

                new SweetAlertDialog(AddEquipmentForm.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Internet Connectivity very slow? try again")
                        .setCancelText("Cancel")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent i = new Intent(AddEquipmentForm.this, InventoryControl.class);
                                startActivity(i);
                            }
                        })
                        .setConfirmText("Try again?")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent i = new Intent(AddEquipmentForm.this, AddEquipmentForm.class);
                                startActivity(i);

                            }
                        })
                        .show();
            } else {


                try {
                    JSONObject jo = new JSONObject(s);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                    Log.d("tag", "<-----Status----->" + status);
                    JSONObject data1 = jo.getJSONObject("data");
                    equipmentId = data1.getString("Equipment");
                    Log.d("tag", "<-----equipmentId----->" + equipmentId);

                    //  String testingimagepath=selectedImagePath.toString();

                    if (status.equals("SUCCESS")) {
                        Log.d("tag", "<-----image_Browse----->" + selectedImagePath);
                        new UploadFileToServer().execute();

                    } else {

                        new SweetAlertDialog(AddEquipmentForm.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("IMAGE UPLOAD FAILED?")
                                .setContentText("TRY AGAIN")
                                .setConfirmText("TRY AGAIN")
                                .setCancelText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                        Intent i = new Intent(AddEquipmentForm.this, AddEquipmentForm.class);
                                        startActivity(i);
                                    }
                                })
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent i = new Intent(AddEquipmentForm.this, Dashboard.class);
                                        startActivity(i);
                                    }
                                }).show();


                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // This is the up button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                // overridePendingTransition(R.animator.anim_left, R.animator.anim_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/


}
