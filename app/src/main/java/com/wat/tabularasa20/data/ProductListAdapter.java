package com.wat.tabularasa20.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.wat.tabularasa20.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    public interface RowClickListener {
        void onRowClick(View view, int position);
    }

    public interface FavouriteChangeListener {
        void onFavouriteChange(View v, boolean isChecked, int position);
    }

    private ArrayList<ProductListDescription> data;
    private LayoutInflater inflater;
    private RowClickListener rowClickListener = null;
    private FavouriteChangeListener favouriteChangeListener = null;

    /**
     * Kierunek sortowania
     */
    public enum SortOrder { ASC, DESC }

    /**
     * Konstruktor klasy
     * @param data
     */
    public ProductListAdapter(Context context, ArrayList<ProductListDescription> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    /**
     * Metoda wykonująca się po utworzeniu adaptera
     */
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_products_browse, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Metoda wykonująca się w czasie wyświetlania
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String description = data.get(position).name;
        boolean favourite = data.get(position).favourite;
        holder.descriptionTextView.setText(description);
        holder.favouriteCheckbox.setChecked(favourite);
        /*
        holder.favouriteCheckbox.setOnCheckedChangeListener((v, isChecked) -> {
            if (favouriteChangeListener != null) favouriteChangeListener.onFavouriteChange(v, isChecked, position);
        }); //data.get(position).favourite = isChecked
        //*/
    }

    /**
     * Klasa obsługi widoku elementu listy
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        TextView descriptionTextView;
        CheckBox favouriteCheckbox;

        ViewHolder(View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.recyclerviewProductListTextViewName);
            favouriteCheckbox = itemView.findViewById(R.id.recyclerviewProductListChceckboxFavourite);
            itemView.setOnClickListener(this);
            favouriteCheckbox.setOnCheckedChangeListener(this);
            // to jest poprawne miejsce ustawienia listenera, ale nie działa
            // przeniesiono do
        }

        /**
         * Akcja elementu listy
         */
        @Override
        public void onClick(View view) {
            if (rowClickListener != null) rowClickListener.onRowClick(view, getAdapterPosition());
        }

        /**
         * Akcja przycisku ulubionych
         */
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (favouriteChangeListener != null) favouriteChangeListener.onFavouriteChange(compoundButton, b, getAdapterPosition());
        }
    }

    /**
     * Getter elementu listy
     * @return wskazany element listy
     */
    public ProductListDescription getItem(int id) {
        return data.get(id);
    }

    /**
     * Getter długości listy
     * @return ilość elementów w liście
     */
    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * Metoda ustawiająca listener elementu listy
     */
    public void setRowClickListener(RowClickListener itemClickListener) {
        this.rowClickListener = itemClickListener;
    }

    /**
     * Metoda ustawiająca listener przycisku ulubionych
     */
    public void setFavouriteChangeListener(FavouriteChangeListener favouriteChangeListener) {
        this.favouriteChangeListener = favouriteChangeListener;
    }


    /**
     * Metoda filtrująca listę przoduktów
     * @param descrFilter tekst filtrujący
     * @param data lista do przefiltrowania
     * @return przefiltrowana lista
     */
    public static ArrayList<ProductListDescription> filter (String descrFilter, List<ProductListDescription> data) {
        List<ProductListDescription> filtered = new ArrayList<>(data);
        Predicate<ProductListDescription> predicate = product -> product.name.toLowerCase().contains(descrFilter.toLowerCase());
        return (ArrayList<ProductListDescription>) filtered.stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * Metoda sortująca listę produktów
     * @param sortOrder kierunek sortowania
     * @param data dane do posortowania
     * @return posortowana lista
     */
    public static ArrayList<ProductListDescription> sort (SortOrder sortOrder, List<ProductListDescription> data) {
        ArrayList<ProductListDescription> sorted = new ArrayList<>(data);
        sorted.sort((lhs, rhs) -> lhs.name.compareTo(rhs.name));
        if (sortOrder == SortOrder.DESC)
            Collections.reverse(sorted);
        return sorted;
    }
}
