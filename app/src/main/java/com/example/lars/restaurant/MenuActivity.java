package com.example.lars.restaurant;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements MenuItemsRequest.Callback {

    private ArrayList<MenuItem> menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        MenuItemsRequest request = new MenuItemsRequest(this);
        Bundle extras = getIntent().getExtras();
        request.getMenuItems(this, extras.getString("category"));
    }

    @Override
    public void gotMenuItems(ArrayList<MenuItem> menuItems) {
        MenuItemsListAdapter adapter = new MenuItemsListAdapter(this, R.layout.menu_list_item, menuItems);
        ListView listView = findViewById(R.id.menuListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new MenuItemClickListener());
    }

    @Override
    public void gotMenuItemsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private class MenuItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(MenuActivity.this, MenuItemActivity.class);
            Log.d("resto", String.valueOf(menuItems.get(i)));
            intent.putExtra("menuItem", menuItems.get(i));
            startActivity(intent);
        }
    }

    public class MenuItemsListAdapter extends ArrayAdapter<MenuItem> {

        TextView menuListName, menuListPrice;
        ImageView menuListImage;

        private MenuItemsListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MenuItem> list) {
            super(context, resource, list);
            menuItems = list;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.menu_list_item, parent, false);
            }
            menuListName = convertView.findViewById(R.id.menuListName);
            menuListPrice = convertView.findViewById(R.id.menuListPrice);
            menuListImage = convertView.findViewById(R.id.menuListImage);
            Log.d("restaurantMenuName", String.valueOf(menuItems));
            Log.d("restaurantMenuName", String.valueOf(menuItems.get(position).getName()));
            menuListName.setText(menuItems.get(position).getName());
            menuListPrice.setText("€ "+ menuItems.get(position).getPrice());
            Picasso.with(getContext()).load(menuItems.get(position).getImageURL()).into(menuListImage);
            return convertView;
        }
    }
}
