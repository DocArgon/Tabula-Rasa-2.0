package com.wat.tabularasa20.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.wat.tabularasa20.R;
import com.wat.tabularasa20.data.ProductListAdapter;
import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity implements ProductListAdapter.ItemClickListener {

    ProductListAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_browse);

        Button back = findViewById(R.id.messagesBrowseButtonBack);
        back.setOnClickListener(v -> finish());

        ArrayList<ProductListAdapter.ProductListDescription> animalNames = new ArrayList<>();
        animalNames.add(new ProductListAdapter.ProductListDescription("Book 1"));
        animalNames.add(new ProductListAdapter.ProductListDescription("Book 2"));
        animalNames.add(new ProductListAdapter.ProductListDescription("Book 30"));
        animalNames.add(new ProductListAdapter.ProductListDescription("Book 40"));
        animalNames.add(new ProductListAdapter.ProductListDescription("Book 500"));
        animalNames.add(new ProductListAdapter.ProductListDescription("Book 600"));
        animalNames.add(new ProductListAdapter.ProductListDescription("Book 7000"));
        animalNames.add(new ProductListAdapter.ProductListDescription("Book 8000"));
        animalNames.add(new ProductListAdapter.ProductListDescription("Book over 9000", true));

        RecyclerView recyclerView = findViewById(R.id.messagesBrowseRecyclerViewMessagesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductListAdapter(this, animalNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "Dotknięto " + adapter.getItem(position).desctiption + ", ulubiony " + adapter.getItem(position).favourite, Toast.LENGTH_SHORT).show();
    }
}
