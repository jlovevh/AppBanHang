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
import com.tvt.projectcuoikhoa.model.LapTop;
import com.tvt.projectcuoikhoa.utils.NumberFormatCurency;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class RecyclerLaptopNewAdapter extends RecyclerView.Adapter<RecyclerLaptopNewAdapter.ViewHolder> {

    private Context context;
    private List<LapTop> arrLapTop;

    private ItemClickListenerLapTop itemClickListener;

    public void setItemClickListener(ItemClickListenerLapTop itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public RecyclerLaptopNewAdapter(Context context, List<LapTop> arrLapTop) {
        this.context = context;
        this.arrLapTop = arrLapTop;
    }

    public void setData(List<LapTop> arrLapTop) {

        this.arrLapTop.addAll(arrLapTop);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_laptop_new, parent, false);
        return new RecyclerLaptopNewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        LapTop lapTop = arrLapTop.get(position);

        Glide.with(context).load(lapTop.getImage()).error(R.mipmap.ic_launcher).into(holder.img_laptop);

        holder.tvName.setText(lapTop.getName());
        int price = Integer.parseInt(lapTop.getPrice());
        String str1 = NumberFormatCurency.numBerForMat(price);
        holder.tvPrice.setText(str1);
        holder.tvOCung.setText("Ổ CỨNG " + lapTop.getOcung());
        holder.tvRam.setText("RAM " + lapTop.getRam());
        holder.tvStatus.setText(lapTop.getStatus());
        if (lapTop.getStatus().equals("Giá rẻ online")) {
            holder.tvStatus.setBackgroundResource(R.color.green);

        } else if (lapTop.getStatus().equals("Trả góp 0%")) {

            holder.tvStatus.setBackgroundResource(android.R.color.holo_orange_dark);
        }

    }

    @Override
    public int getItemCount() {
        return arrLapTop.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_laptop;
        private TextView tvName, tvPrice, tvStatus, tvOCung, tvRam;

        public ViewHolder(View itemView) {
            super(itemView);
            tvStatus = itemView.findViewById(R.id.tv_status_laptop_new);
            img_laptop = itemView.findViewById(R.id.img_laptop_new);
            tvName = itemView.findViewById(R.id.tv_name_laptop_new);
            tvPrice = itemView.findViewById(R.id.tv_price_laptop_new);
            tvOCung = itemView.findViewById(R.id.tv_ocung);
            tvRam = itemView.findViewById(R.id.tv_ram);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClickLaptop(v, getAdapterPosition());
                }
            });
        }
    }

    public interface ItemClickListenerLapTop {

        void onItemClickLaptop(View view, int position);
    }
}
