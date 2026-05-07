package com.altynbekova.banks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

public class Util {
    public static List<Bank> parseResponse(ResponseBody body) {
        List<Bank> result = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(body.string());
            JSONArray suggestions = jsonObject.getJSONArray("suggestions");
            for (int i = 0; i < suggestions.length(); i++) {

            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
