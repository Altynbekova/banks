package com.altynbekova.banks.util;

import android.util.Log;

import com.altynbekova.banks.model.Bank;
import com.yandex.mapkit.geometry.Point;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Util {
    public static final String TAG = "Banks Map";
    public static List<Bank> parseResponse(ResponseBody body) {
        List<Bank> result = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(body.string());
            JSONArray suggestions = jsonObject.getJSONArray("suggestions");
            for (int i = 0; i < suggestions.length(); i++) {
                JSONObject suggestion = (JSONObject) suggestions.get(i);
                String bankName = suggestion.getString("value");
                JSONObject addressObject = suggestion
                        .getJSONObject("data")
                        .getJSONObject("address");
                String bankAddress = addressObject.getString("value");
                JSONObject addressData = addressObject.getJSONObject("data");
                String lat = addressData.getString("geo_lat");
                String lon = addressData.getString("geo_lon");
                String status = suggestion
                        .getJSONObject("data")
                        .getJSONObject("state")
                        .getString("status");

                result.add(new Bank(
                        bankName,
                        bankAddress,
                        status,
                        new Point(Float.parseFloat(lat), Float.parseFloat(lon))));
            }
        } catch (JSONException | IOException e) {
            Log.e(TAG, "parseResponse: cannot parse response", e);
        }

        return result;
    }
}
