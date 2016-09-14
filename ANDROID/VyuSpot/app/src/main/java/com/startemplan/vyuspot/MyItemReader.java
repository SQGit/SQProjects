/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.startemplan.vyuspot;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyItemReader extends MapBase {

    double lat,lng;
    LatLng ltlg;

    /*
     * This matches only once in whole input,
     * so Scanner.next returns whole InputStream as img_emg String.
     * http://stackoverflow.com/a/5445161/2183804
     */
    private static final String REGEX_INPUT_BOUNDARY_BEGINNING = "\\A";

    public List<MyItem> read(InputStream inputStream) throws JSONException {
        List<MyItem> items = new ArrayList<MyItem>();
        String json = new Scanner(inputStream).useDelimiter(REGEX_INPUT_BOUNDARY_BEGINNING).next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
           lat = object.getDouble("lat");
            lng = object.getDouble("lng");
            items.add(new MyItem(lat, lng));
            //startProcess();
          }
        return items;
    }

    @Override
    public void startProcess() {
        ltlg = new LatLng(lat,lng);
        Log.d("tag", "" + String.valueOf(lat) + " " + String.valueOf(lng));
        getMap().addMarker(new MarkerOptions().position(ltlg).title("Marker in Sydney"));
    }
}
