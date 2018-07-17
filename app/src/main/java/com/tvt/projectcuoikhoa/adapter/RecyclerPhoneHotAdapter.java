package com.tvt.projectcuoikhoa.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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
import com.tvt.projectcuoikhoa.model.PhoneHot;

import java.util.List;

public class RecyclerPhoneHotAdapter extends RecyclerView.Adapter<RecyclerPhoneHotAdapter.ViewHolder> {

    private Context context;
    private List<PhoneHot> phoneHotList;


    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public RecyclerPhoneHotAdapter(Context context, List<PhoneHot> phoneHotList) {
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

        PhoneHot phone = phoneHotList.get(position);
        holder.tvName.setText(phone.getName());
        holder.tvPrice.setText(phone.getPrice());
        Glide.with(context).load(phone.getAnhkhuyenmai()).error(R.mipmap.ic_launcher).into(holder.img_phone);
        holder.tvStatus.setText(phone.getStatus());
    }

    @Override
    public int getItemCount() {
        return phoneHotList.size();
    }

    public void setData(List<PhoneHot> phoneHotList) {
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
