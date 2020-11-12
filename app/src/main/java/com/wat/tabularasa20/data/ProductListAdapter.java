package com.wat.tabularasa20.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.wat.tabularasa20.R;
import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> implements Filterable {

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public static class ProductListDescription {
        public String desctiption;
        public boolean favourite;
        public ProductListDescription (String desctiption) {
            this.desctiption = desctiption;
            this.favourite = false;
        }
        public ProductListDescription (String desctiption, boolean favoutite) {
            this.desctiption = desctiption;
            this.favourite = favoutite;
        }
    }

    private ArrayList<ProductListDescription> data;
    private ArrayList<ProductListDescription> dataFiltered;
    private LayoutInflater inflater;
    private ItemClickListener itemClickListener;

    public ProductListAdapter(Context context, ArrayList<ProductListDescription> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        this.dataFiltered = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_product_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String description = data.get(position).desctiption;
        boolean favourite = data.get(position).favourite;
        holder.descriptionTextView.setText(description);
        holder.favouriteCheckbox.setChecked(favourite);
        holder.favouriteCheckbox.setOnCheckedChangeListener((v, isChecked) -> data.get(position).favourite = isChecked);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView descriptionTextView;
        CheckBox favouriteCheckbox;

        ViewHolder(View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.recyclerviewProductListTextViewDescription);
            favouriteCheckbox = itemView.findViewById(R.id.recyclerviewProductListChceckboxFavourite);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public ProductListDescription getItem(int id) {
        return data.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    //*
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                List<ProductListDescription> filteredList = new ArrayList<>();

                if (!charString.isEmpty()) {
                    for (ProductListDescription row : data) {
                        if (row.desctiption.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataFiltered = (ArrayList<ProductListDescription>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    //*/
}
