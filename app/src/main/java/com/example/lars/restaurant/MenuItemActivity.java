package com.example.lars.restaurant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MenuItemActivity extends AppCompatActivity {

    TextView detailMenuName, detailMenuDescription, detailMenuPrice;
    ImageView detailMenuImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);
        Bundle extras = getIntent().getExtras();
        MenuItem menuItem = (MenuItem) extras.getSerializable("menuItem");
        Log.d("resto", String.valueOf(menuItem));
        detailMenuName = findViewById(R.id.detailMenuName);
        detailMenuImage = findViewById(R.id.detailMenuImage);
        detailMenuDescription = findViewById(R.id.detailMenuDescription);
        detailMenuPrice = findViewById(R.id.detailMenuPrice);
        detailMenuName.setText(menuItem.getName());
        detailMenuDescription.setText(menuItem.getDescription());
        detailMenuPrice.setText("€ "+ menuItem.getPrice());
        Picasso.with(this).load(menuItem.getImageURL()).into(detailMenuImage);
    }
}
