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

public class MenuItemsRequest implements Response.Listener<JSONObject>, Response.ErrorListener{

    //initialisation
    Context context;
    Callback callback;
    String menuCategory;

    public MenuItemsRequest(Context context) {
        this.context = context;
    }

    public interface Callback {
        void gotMenuItems(ArrayList<MenuItem> menuItems);
        void gotMenuItemsError(String message);
    }

    public void getMenuItems(Callback activity, String category) {

        //sends request for getting the menu items
        callback = activity;
        menuCategory = category;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://resto.mprog.nl/menu", null, this, this);
        queue.add(jsonObjectRequest);
    }

    public void onErrorResponse(VolleyError error) {
        Log.d("restaurantOnErrorResponse", "reached");
        callback.gotMenuItemsError(error.getMessage());
    }

    public void onResponse(JSONObject response) {
        try {
            //retrieves the json request
            String tempName, tempDescription, tempURL;
            int tempPrice;
            JSONArray jsonArray = response.getJSONArray("items");
            ArrayList<MenuItem> data = new ArrayList<>(6);

            //formats the json to an arraylist of MenuItems
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.getJSONObject(i).getString("category").equals(menuCategory)) {
                    tempName = jsonArray.getJSONObject(i).getString("name");
                    tempDescription = jsonArray.getJSONObject(i).getString("description");
                    tempURL = jsonArray.getJSONObject(i).getString("image_url");
                    tempPrice = jsonArray.getJSONObject(i).getInt("price");
                    data.add(new MenuItem(tempName, tempDescription, tempURL, menuCategory, tempPrice));
                }
            }
            callback.gotMenuItems(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
