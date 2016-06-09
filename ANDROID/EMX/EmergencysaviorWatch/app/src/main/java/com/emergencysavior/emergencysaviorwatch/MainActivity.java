package com.emergencysavior.emergencysaviorwatch;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONObject;

import java.util.EnumMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ImageView Barcode, ic_reply;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;
    String uid;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager tManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        uid = tManager.getDeviceId();


        if (Util.Operations.isOnline(MainActivity.this)) {

            new MyAsyncSerialnumber(uid).execute();

        } else {

            Toast.makeText(getApplicationContext(), "Please check your internet connectvity", Toast.LENGTH_LONG).show();
        }


    }

    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        final Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            int count = getPreferences(MODE_PRIVATE).getInt("count_key", 0);
                            if (count >= 10) {
                                System.out.println("The count  value if" + count);
                                getPreferences(MODE_PRIVATE).edit().putInt("count_key", count).apply();
                                timer.cancel();
                                getPreferences(MODE_PRIVATE).edit().putInt("count_key", 0).apply();
                                ic_reply.setVisibility(View.VISIBLE);

                            } else {
                                System.out.println("The count  value else" + count);
                                count++;
                                ic_reply.setVisibility(View.GONE);
                                getPreferences(MODE_PRIVATE).edit().putInt("count_key", count).apply();
                                new MyAsyncSerialnumber(uid).execute();


                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 1000);
    }

    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width,
                          int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width,
                    img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;

    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }



    private class MyAsyncSerialnumber extends AsyncTask<String, Void, String> {

        String uid;

        public MyAsyncSerialnumber(String uid) {

            this.uid = uid;
        }


        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.put("device_id", uid);
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.put("data", jsonObject);
                return jsonStr = HttpUtils.makeRequest(CONFIG.URL + "/qrwear/watch", json);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("tag", "Json result" + result);
            super.onPostExecute(result);
            try {
                if (result.equals("null")){
                    Toast.makeText(getApplicationContext(), "Try again1", Toast.LENGTH_SHORT).
                            show();
                }else if (result.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Try again2", Toast.LENGTH_SHORT).
                            show();
                }else if (CONFIG.isStringNullOrWhiteSpace(result)){
                    Toast.makeText(getApplicationContext(), "Try again3", Toast.LENGTH_SHORT).
                            show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("tag", "<-----Status----->" + e);
            }

           /* if (s.equals("null")) {

                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("ALERT")
                        .setContentText("Internet Connectivity very slow? try again")
                        .setConfirmText("ok")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                callAsynchronousTask();

                            }
                        })
                        .show();
            } else {

                try {
                    JSONObject   result = new JSONObject(s);
                    String status = result.getString("status");
                    String session_token = result.getString("session_token");
                    String first_name = result.getString("first_name");
                    String email = result.getString("email");
                    String message = result.getString("status");

                    Log.d("tag", "status" + status);
                    Log.d("tag", "session_token" + session_token);
                    Log.d("tag", "first_name" + first_name);
                    Log.d("tag", "email" + email);
                    Log.d("tag", "message" + message);

                    if (status.equals("success")) {

                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(intent);

                    } else if (status.equals("notfound")) {
                        Log.d("tag", "User id" + uid);
                        Barcode = (ImageView) findViewById(R.id.barcodeimage);
                        ic_reply = (ImageView) findViewById(R.id.ic_reply);
                        String barcode_data = "" + uid;
                        Bitmap bitmap = null;
                        ImageView iv = Barcode;


                        try {
                            bitmap = encodeAsBitmap(barcode_data, BarcodeFormat.QR_CODE, 240, 200);
                            iv.setImageBitmap(bitmap);

                        } catch (WriterException e) {
                            e.printStackTrace();
                        }
                        ic_reply.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                callAsynchronousTask();
                            }
                        });
                        callAsynchronousTask();


                    } else {

                        Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_SHORT).
                                show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }*/
        }
    }
}