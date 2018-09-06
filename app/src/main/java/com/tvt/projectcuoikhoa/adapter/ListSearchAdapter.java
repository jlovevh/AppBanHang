package com.tvt.projectcuoikhoa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.model.Phone;
import com.tvt.projectcuoikhoa.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class ListSearchAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<SanPham> arrSP;
    private List<SanPham> arrSPFilter;

    private ListSearchListener listSearchListener;

    public void setListSearchListener(ListSearchListener listSearchListener) {
        this.listSearchListener = listSearchListener;
    }

    public ListSearchAdapter(Context context, List<SanPham> arrSP, ListSearchListener listSearchListener) {
        this.context = context;
        this.arrSP = arrSP;
        this.arrSPFilter = arrSP;
        this.listSearchListener = listSearchListener;
    }

    @Override
    public int getCount() {
        return arrSPFilter.size();
    }

    @Override
    public Object getItem(int position) {
        return arrSPFilter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {

        TextView tvName, tvGia;
        ImageView imgAnh;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);

            holder.imgAnh = convertView.findViewById(R.id.img_search);
            holder.tvName = convertView.findViewById(R.id.name_search);
            holder.tvGia = convertView.findViewById(R.id.gia_sp);
            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }


        final SanPham sanPham = arrSPFilter.get(position);

        holder.tvGia.setText(sanPham.getGiaSp());
        Picasso.with(context).load(sanPham.getAnhSp()).into(holder.imgAnh);
        holder.tvName.setText(sanPham.getTenSp());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listSearchListener.onSearchSelected(sanPham, position);
            }
        });
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    arrSPFilter = arrSP;
                } else {
                    List<SanPham> filteredList = new ArrayList<>();
                    for (SanPham row : arrSP) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTenSp().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    arrSPFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = arrSPFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                arrSPFilter = (ArrayList<SanPham>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ListSearchListener {

        void onSearchSelected(SanPham sanPham, int position);
    }
}
