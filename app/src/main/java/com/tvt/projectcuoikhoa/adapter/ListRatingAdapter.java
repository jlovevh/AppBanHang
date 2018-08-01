package com.tvt.projectcuoikhoa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.model.Rating;

import java.util.List;

public class ListRatingAdapter extends BaseAdapter {

    private Context context;
    private List<Rating> arrRating;

    public ListRatingAdapter(Context context, List<Rating> arrRating) {
        this.context = context;
        this.arrRating = arrRating;
    }

    public void setData(List<Rating> arrRating) {
        this.arrRating.clear();
        this.arrRating.addAll(arrRating);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return arrRating.size();
    }

    @Override
    public Object getItem(int position) {
        return arrRating.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView tvKH, tvCreate, tvNhanxet;
        RatingBar ratingBar;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_rating, parent, false);
            holder.ratingBar = convertView.findViewById(R.id.item_rating);
            holder.tvCreate = convertView.findViewById(R.id.tv_create);
            holder.tvKH = convertView.findViewById(R.id.tv_khachhang);
            holder.tvNhanxet = convertView.findViewById(R.id.tv_nhanxet);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        Rating rating = arrRating.get(position);
        holder.tvCreate.setText(rating.getCreateAt());
        holder.tvKH.setText(rating.getTenkhachhang());
        holder.tvNhanxet.setText(rating.getNhanxet());
        holder.ratingBar.setRating(Float.parseFloat(rating.getDanhgia()));
        return convertView;
    }
}
