package com.example.lars.restaurant;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity implements CategoriesRequest.Callback {

    //set context and initialize categories
    Context context = this;
    private ArrayList<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        //request categories
        CategoriesRequest request = new CategoriesRequest(this);
        request.getCategories(this);
    }

    @Override
    public void gotCategories(ArrayList<String> categories) {

        //fill listview with categories and set onclicklistener
        CategoryListAdapter adapter = new CategoryListAdapter(context, R.layout.category_list_item, categories);
        ListView listView = findViewById(R.id.categoryListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ListItemClickListener());

    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            //on click it goes to the MenuActivity with the selected category
            Intent intent = new Intent(CategoriesActivity.this, MenuActivity.class);
            intent.putExtra("category", categories.get(i));
            startActivity(intent);
        }
    }

    @Override
    public void gotCategoriesError(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    //adapter for filling the listview with categories
    public class CategoryListAdapter extends ArrayAdapter<String> {

        TextView categoryListItemText;

        private CategoryListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> list) {
            super(context, resource, list);
            categories = list;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_list_item, parent, false);
            }
            categoryListItemText = convertView.findViewById(R.id.categoryListItemText);
            categoryListItemText.setText(categories.get(position));
            return convertView;
        }
    }
}
