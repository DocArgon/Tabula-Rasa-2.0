package com.wat.tabularasa20.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    private ArrayList<ProductListDescription> data;
    private LayoutInflater inflater;
    private ItemClickListener itemClickListener;

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
        String description = data.get(position).desctiption;
        boolean favourite = data.get(position).favourite;
        holder.descriptionTextView.setText(description);
        holder.favouriteCheckbox.setChecked(favourite);
        holder.favouriteCheckbox.setOnCheckedChangeListener((v, isChecked) -> data.get(position).favourite = isChecked);
    }

    /**
     * Klasa obsługi widoku elementu listy
      */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView descriptionTextView;
        CheckBox favouriteCheckbox;

        ViewHolder(View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.recyclerviewProductListTextViewDescription);
            favouriteCheckbox = itemView.findViewById(R.id.recyclerviewProductListChceckboxFavourite);
            itemView.setOnClickListener(this);
        }

        /**
         * Akcja elementu listy
         */
        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onItemClick(view, getAdapterPosition());
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
    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * Metoda filtrująca listę przoduktów
     * @param descrFilter tekst filtrujący
     * @param data lista do przefiltrowania
     * @return przefiltrowana lista
     */
    public static ArrayList<ProductListDescription> filter (String descrFilter, List<ProductListDescription> data) {
        List<ProductListDescription> filtered = new ArrayList<>(data);
        Predicate<ProductListDescription> predicate = product -> product.desctiption.toLowerCase().contains(descrFilter.toLowerCase());
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
        sorted.sort((lhs, rhs) -> lhs.desctiption.compareTo(rhs.desctiption));
        if (sortOrder == SortOrder.DESC)
            Collections.reverse(sorted);
        return sorted;
    }
}
