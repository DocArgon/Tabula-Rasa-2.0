package com.wat.tabularasa20.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.wat.tabularasa20.R;
import com.wat.tabularasa20.data.ProductListAdapter;
import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity implements ProductListAdapter.ItemClickListener, TextWatcher, View.OnClickListener {

    ProductListAdapter adapter = null;
    RecyclerView recyclerView = null;
    ProductListAdapter.SortOrder sortOrder = ProductListAdapter.SortOrder.ASC;
    ArrayList<ProductListAdapter.ProductListDescription> products = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_browse);

        Button back = findViewById(R.id.productsBrowseButtonBack);
        EditText filter = findViewById(R.id.productsBrowseEditTextSearchText);
        ImageButton sort = findViewById(R.id.productsBrowseImageButtonSort);
        recyclerView = findViewById(R.id.productsBrowseRecyclerViewMessagesList);

        back.setOnClickListener(v -> finish());

        products = new ArrayList<>();
        products.add(new ProductListAdapter.ProductListDescription("Book 1"));
        products.add(new ProductListAdapter.ProductListDescription("Book 2"));
        products.add(new ProductListAdapter.ProductListDescription("Book 30"));
        products.add(new ProductListAdapter.ProductListDescription("Book 40"));
        products.add(new ProductListAdapter.ProductListDescription("Book 500"));
        products.add(new ProductListAdapter.ProductListDescription("Book 600"));
        products.add(new ProductListAdapter.ProductListDescription("Book 7000"));
        products.add(new ProductListAdapter.ProductListDescription("Book 8000"));
        products.add(new ProductListAdapter.ProductListDescription("Book over 9000", true));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductListAdapter(this, products);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        filter.addTextChangedListener(this);
        sort.setOnClickListener(this);
    }

    // Akcja elementu listy
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "Dotknięto " + adapter.getItem(position).desctiption + ", ulubiony " + adapter.getItem(position).favourite, Toast.LENGTH_SHORT).show();
    }

    // Akcja pola filtrowania
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    @Override
    public void afterTextChanged(Editable editable) {}
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        adapter = new ProductListAdapter(this,
            ProductListAdapter.filter(charSequence.toString(), ProductListAdapter.sort(sortOrder, products)));
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    // Akcja rzycisku sortowania
    @Override
    public void onClick(View view) {
        sortOrder = sortOrder == ProductListAdapter.SortOrder.ASC ? ProductListAdapter.SortOrder.DESC : ProductListAdapter.SortOrder.ASC;
        ((ImageButton)view).setImageResource(
            sortOrder == ProductListAdapter.SortOrder.ASC ? android.R.drawable.arrow_down_float : android.R.drawable.arrow_up_float);
        ArrayList<ProductListAdapter.ProductListDescription> filtered = new ArrayList<>(); // tylko obecnie pokazane elementy
        for (int i = 0; i < adapter.getItemCount(); i++)
            filtered.add(adapter.getItem(i));
        adapter = new ProductListAdapter(this, ProductListAdapter.sort(sortOrder, filtered));
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }
}
