package com.tvt.projectcuoikhoa.adapter;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.List;

public class RecyclerLapTopAdapter extends RecyclerView.Adapter<RecyclerLapTopAdapter.ViewHolder> {

    private Context context;
    private List<LapTop> arrLapTop=new ArrayList<>();

    public RecyclerLapTopAdapter(Context context, List<LapTop> arrLapTop) {
        this.context = context;
        this.arrLapTop = arrLapTop;
    }

    public void setData(List<LapTop> arrLapTop){

        this.arrLapTop.clear();
        this.arrLapTop.addAll(arrLapTop);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_laptop,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        LapTop lapTop =arrLapTop.get(position);

        Glide.with(context).load(lapTop.getImage()).error(R.mipmap.ic_launcher).into(holder.img_laptop);

        holder.tvName.setText(lapTop.getName());
        holder.tvPrice.setText(lapTop.getPrice());
        holder.tvScreen.setText("Màn Hình: "+lapTop.getManhinh());
        holder.tvRam.setText("Ram: "+lapTop.getRam());
        holder.tvVGA.setText("VGA: "+lapTop.getDohoa());
        holder.tvHDH.setText("HĐH: "+lapTop.getHedieuhanh());
        holder.tvCpu.setText("CPU: "+lapTop.getCpu());
        holder.tvTrongLuong.setText("Nặng: "+lapTop.getTrongluong());

    }

    @Override
    public int getItemCount() {
        return arrLapTop.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView img_laptop;
        private TextView tvName,tvPrice,tvScreen,tvCpu,tvRam,tvVGA,tvHDH,tvTrongLuong;
        public ViewHolder(View itemView) {
            super(itemView);
            img_laptop=itemView.findViewById(R.id.img_laptop);
            tvName=itemView.findViewById(R.id.tv_name_laptop);
            tvPrice=itemView.findViewById(R.id.tv_price_laptop);
            tvScreen=itemView.findViewById(R.id.tv_manhinh_laptop);
            tvCpu=itemView.findViewById(R.id.tv_cpu_laptop);
            tvRam=itemView.findViewById(R.id.tv_ram_laptop);
            tvHDH=itemView.findViewById(R.id.tv_hdh_laptop);
            tvVGA=itemView.findViewById(R.id.tv_vga_laptop);
            tvTrongLuong=itemView.findViewById(R.id.tv_tl_laptop);
        }
    }
}
