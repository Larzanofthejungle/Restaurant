package com.example.lars.restaurant;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoriesRequest implements Response.Listener<JSONObject>, Response.ErrorListener{

    Context context;
    Callback callback;

    public CategoriesRequest(Context context) {
        this.context = context;
    }

    public interface Callback {
        void gotCategories(ArrayList<String> categories);
        void gotCategoriesError(String message);
    }

    public void getCategories(Callback activity) {
        Log.d("getCategories", "reached");
        Log.d("callback", String.valueOf(activity));
        callback = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://resto.mprog.nl/categories", null, this, this);
        queue.add(jsonObjectRequest);
    }

    public void onErrorResponse(VolleyError error) {
        Log.d("onErrorResponse", "reached");
        callback.gotCategoriesError(error.getMessage());
    }

    public void onResponse(JSONObject response) {
        try {
            Log.d("onResponse", "reached");
            JSONArray jsonArray = response.getJSONArray("categories");
            ArrayList<String> data = new ArrayList<>(2);
            for (int i = 0; i < jsonArray.length(); i++) {
                Log.d("json",jsonArray.getString(i));
                data.add(jsonArray.getString(i));
            }
            Log.d("categories", String.valueOf(data));
            Log.d("callback", String.valueOf(callback));
            callback.gotCategories(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
