package com.wat.tabularasa20.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.wat.tabularasa20.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Adapter elementu listy
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    /**
     * Interfejs do utworzenia akcji dotknięcia
     */
    public interface RowClickListener {
        void onRowClick(View view, int position);
    }

    /**
     * Interfejs do utworzenia akcji przytrzymania
     */
    public interface RowLongClickListener {
        void onRowLongClick(View view, int position);
    }

    /**
     * Interfejs do utworzenia akcji dotknięcia gwiazdki ulubionych
     */
    public interface FavouriteChangeListener {
        void onFavouriteChange(View v, boolean isChecked, int position);
    }

    private ArrayList<ProductListDescription> data;
    private LayoutInflater inflater;
    private RowClickListener rowClickListener = null;
    private RowLongClickListener rowLongClickListener = null;
    private FavouriteChangeListener favouriteChangeListener = null;

    /**
     * Kierunek sortowania
     */
    public enum SortOrder { ASC, DESC }

    /**
     * Konstruktor klasy
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
        String title = data.get(position).title;
        ProductListDescription.FavouriteStare favourite = data.get(position).favourite;
        String description = data.get(position).description;
        String nick = data.get(position).nick;
        String city = data.get(position).city;
        String author = data.get(position).author;
        String helper = data.get(position).helper;
        int background = data.get(position).background;

        holder.titleTextView.setText(title);
        switch (favourite) {
            case ON:
                holder.favouriteCheckbox.setChecked(true);
                break;
            case OFF:
                holder.favouriteCheckbox.setChecked(false);
                break;
            case HIDDEN:
                holder.favouriteCheckbox.setVisibility(View.GONE);
        }
        holder.descriptionTextView.setText(description);
        holder.nickiTextView.setText(nick);
        holder.cityTextView.setText(city);
        holder.authorTextView.setText(author);
        holder.helperTextView.setText(helper);
        holder.layout.setBackgroundColor(background);
    }

    /**
     * Klasa obsługi widoku elementu listy
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, View.OnLongClickListener {
        TextView titleTextView;
        CheckBox favouriteCheckbox;
        TextView descriptionTextView;
        TextView nickiTextView;
        TextView cityTextView;
        TextView authorTextView;
        TextView helperTextView;
        ConstraintLayout layout;
        View itemView;

        ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            layout = itemView.findViewById(R.id.recyclerviewProductListContainer);
            titleTextView = itemView.findViewById(R.id.recyclerviewProductListTextViewTitle);
            favouriteCheckbox = itemView.findViewById(R.id.recyclerviewProductListChceckboxFavourite);
            descriptionTextView = itemView.findViewById(R.id.recyclerviewProductListTextViewDescription);
            nickiTextView = itemView.findViewById(R.id.recyclerviewProductListTextViewNick);
            cityTextView = itemView.findViewById(R.id.recyclerviewProductListTextViewCity);
            authorTextView = itemView.findViewById(R.id.recyclerviewProductListTextViewAuthor);
            helperTextView = itemView.findViewById(R.id.recyclerviewProductListTextViewHelper);
            itemView.setOnClickListener(this);
            layout.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            layout.setOnLongClickListener(this);
            //favouriteCheckbox.setOnCheckedChangeListener(this);
            favouriteCheckbox.setOnClickListener(this);
        }

        /**
         * Akcja elementu listy i przycisku dodawania do ulubionych
         */
        @Override
        public void onClick(View view) {
            if (rowClickListener != null && (view.getId() == itemView.getId() || view.getId() == layout.getId()))
                rowClickListener.onRowClick(view, getAdapterPosition());
            if (favouriteChangeListener != null && view.getId() == favouriteCheckbox.getId())
                favouriteChangeListener.onFavouriteChange(view, ((CheckBox)view).isChecked(), getAdapterPosition());
        }

        /**
         * Akcja elementu listy na przytrzymanie
         */
        @Override
        public boolean onLongClick(View view) {
            if (rowLongClickListener != null && (view.getId() == itemView.getId() || view.getId() == layout.getId()))
                rowLongClickListener.onRowLongClick(view, getAdapterPosition());
            return false;
        }

        /**
         * Akcja przycisku ulubionych
         * nieużywane ale zostanie gdyby była potrzeba wrócić do tej metody
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
     * Metoda ustawiająca listener elementu listy na dotknięcie
     */
    public void setRowClickListener(RowClickListener itemClickListener) {
        this.rowClickListener = itemClickListener;
    }

    /**
     * Metoda ustawiająca listener elementu listy na przytrzymanie
     */
    public void setRowLongClickListener(RowLongClickListener itemLongClickListener) {
        this.rowLongClickListener = itemLongClickListener;
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
        Predicate<ProductListDescription> predicate = product -> product.title.toLowerCase().contains(descrFilter.toLowerCase());
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
        sorted.sort((lhs, rhs) -> lhs.title.compareTo(rhs.title));
        if (sortOrder == SortOrder.DESC)
            Collections.reverse(sorted);
        return sorted;
    }
}
