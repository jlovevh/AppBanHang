package com.tvt.projectcuoikhoa.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.helper.ItemClickListener;
import com.tvt.projectcuoikhoa.model.Phone;
import com.tvt.projectcuoikhoa.utils.NumberFormatCurency;
import com.tvt.projectcuoikhoa.helper.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class RecyclerPhoneHotAdapter extends RecyclerView.Adapter<RecyclerPhoneHotAdapter.ViewHolder> {

    private static Activity context;
    private List<Phone> phoneHotList;


    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public RecyclerPhoneHotAdapter(Activity context, List<Phone> phoneHotList) {
        this.context = context;
        this.phoneHotList = phoneHotList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_phone_hot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Phone phone = phoneHotList.get(position);
        holder.tvName.setText(phone.getName());
        int price = Integer.parseInt(phone.getPrice());
        String str1 = NumberFormatCurency.numBerForMat(price);
        holder.tvPrice.setText(str1);
        Glide.with(context).load(phone.getAnhkhuyenmai()).error(R.mipmap.ic_launcher).into(holder.img_phone);
        holder.tvStatus.setText(phone.getStatus());
    }

    @Override
    public int getItemCount() {
        return phoneHotList.size();
    }

    public void setData(List<Phone> phoneHotList) {
        this.phoneHotList.clear();
        this.phoneHotList.addAll(phoneHotList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_phone;
        private TextView tvName, tvPrice, tvStatus;


        public ViewHolder(View itemView) {
            super(itemView);

            img_phone = itemView.findViewById(R.id.img_phone);
            tvName = itemView.findViewById(R.id.tv_name_phone);
            tvPrice = itemView.findViewById(R.id.tv_price_phone);
            tvStatus = itemView.findViewById(R.id.tv_status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, getAdapterPosition());

                }
            });
        }
    }


}
