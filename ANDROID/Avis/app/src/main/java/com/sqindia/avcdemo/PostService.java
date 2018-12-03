package com.sqindia.avcdemo;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

/**
 * Created by Ramya on 01-09-2017.
 */

public class PostService {
    public static JSONObject getVin(String url) throws JSONException {
        InputStream is = null;
        String result = "";
        JSONObject jArray = null;

        // Download JSON data from URL
        try {


            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            //  HttpPost httppost = new HttpPost(url);
            httpGet.setHeader("X-Originating-Ip", "103.48.181.209");
            httpGet.setHeader("Accept", "application/json");
            HttpResponse response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

        } catch (Exception e) {
            Log.e("tag", "Error in http connection " + e.toString());
            result = "sam";
            is = null;

        }


        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e("tag", "Error converting result " + e.toString());
            result = "sam";
        }

        try {

            jArray = new JSONObject(result);
        } catch (JSONException e) {
            Log.e("tag", result);
            Log.e("tag", jArray.toString());
            Log.e("tag", "Error parsing data " + e.toString());


        }

        return jArray;


    }


    public static String makeRequest(String url, String json) {

        InputStream is = null;

        try {

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
           // httpPost.setHeader("apikey", "1eo7u4tig9704k2humvdywwnb4hnl2xa1jbrh7go");

            HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);

            Log.e("tag_", "stsL_" + httpResponse.getStatusLine());
            Log.e("tag_", "stsL_" + httpResponse.getStatusLine().getReasonPhrase());
            Log.e("tag_", "stsL_" + httpResponse.getStatusLine().getStatusCode());


            // receive response as inputStream
            InputStream inputStream = httpResponse.getEntity().getContent();
            // convert inputstream to string
            if (inputStream != null) {
                String result = convertInputStreamToString(inputStream);
              //  Log.e(TAG, "output-->" + result);
                return result;
            } else {
              //  Log.e(TAG, "output-->" + inputStream);

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();

        System.out.println(" OUTPUT -->" + result);


        return result;

    }
}
