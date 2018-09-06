package com.tvt.projectcuoikhoa.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;
import com.squareup.picasso.Picasso;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.model.Product;
import com.tvt.projectcuoikhoa.model.SanPham;
import com.tvt.projectcuoikhoa.utils.NumberFormatCurency;

import java.util.ArrayList;
import java.util.List;

public class CustomSuggestionsAdapter extends SuggestionsAdapter<Product, CustomSuggestionsAdapter.SuggestionHolder> {

    private Context context;
    private itemSuggestionOnClick itemSuggestionOnClick;
    private List<Product> arrProduct;

    public void setItemSuggestionOnClick(CustomSuggestionsAdapter.itemSuggestionOnClick itemSuggestionOnClick) {
        this.itemSuggestionOnClick = itemSuggestionOnClick;
    }

    public CustomSuggestionsAdapter(LayoutInflater inflater, Context context) {
        super(inflater);
        this.context = context;
    }


    @Override
    public void onBindSuggestionHolder(Product product, SuggestionHolder holder, final int position) {
        int price = Integer.parseInt(product.getPrice());
        String str1 = NumberFormatCurency.numBerForMat(price);
        holder.tvGia.setText(str1);
        Picasso.with(context).load(product.getImage()).into(holder.imgAnh);
        holder.tvName.setText(product.getName());


    }

    @Override
    public int getSingleViewHeight() {
        return 100;
    }

    @Override
    public void clearSuggestions() {
        super.clearSuggestions();
        suggestions.clear();
    }

    public CustomSuggestionsAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public void addSuggestion(Product r) {
        super.addSuggestion(r);
    }

    @Override
    public void setSuggestions(List<Product> suggestions) {
        super.setSuggestions(suggestions);
    }

    @Override
    public void deleteSuggestion(int position, Product r) {
        super.deleteSuggestion(position, r);
    }

    @Override
    public List<Product> getSuggestions() {
        return super.getSuggestions();
    }

    @Override
    public int getMaxSuggestionsCount() {
        return super.getMaxSuggestionsCount();
    }

    @Override
    public void setMaxSuggestionsCount(int maxSuggestionsCount) {
        super.setMaxSuggestionsCount(maxSuggestionsCount);
    }

    @Override
    protected LayoutInflater getLayoutInflater() {
        return super.getLayoutInflater();
    }

    @Override
    public void onBindViewHolder(SuggestionHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getListHeight() {
        return super.getListHeight();
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @NonNull
    @Override
    public SuggestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.item_search, parent, false);
        return new SuggestionHolder(view);
    }

    public class SuggestionHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvGia;
        ImageView imgAnh;

        public SuggestionHolder(View itemView) {
            super(itemView);

            imgAnh = itemView.findViewById(R.id.img_search);
            tvName = itemView.findViewById(R.id.name_search);
            tvGia = itemView.findViewById(R.id.gia_sp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product product = suggestions.get(getAdapterPosition());
                    itemSuggestionOnClick.onClickItemSuggesstion(product);
                }
            });
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                String term = constraint.toString();
                if (term.isEmpty())
                    suggestions = suggestions_clone;
                else {
                    suggestions = new ArrayList<>();
                    for (Product item : suggestions_clone)
                        if (item.getName().toLowerCase().contains(term.toLowerCase()))
                            suggestions.add(item);
                }
                results.values = suggestions;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                suggestions = (ArrayList<Product>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface itemSuggestionOnClick {

        void onClickItemSuggesstion(Product product);
    }
}
