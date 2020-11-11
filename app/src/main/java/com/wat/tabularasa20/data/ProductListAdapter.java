package com.wat.tabularasa20.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.wat.tabularasa20.R;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

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

    private List<ProductListDescription> data;
    private LayoutInflater inflater;
    private ItemClickListener itemClickListener;

    public ProductListAdapter(Context context, List<ProductListDescription> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_product_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String description = data.get(position).desctiption;
        boolean favourite = data.get(position).favourite;
        holder.descriptionTextView.setText(description);
        holder.favouriteCheckbox.setChecked(favourite);
        holder.favouriteCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            data.get(position).favourite = isChecked;
        });
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


}
