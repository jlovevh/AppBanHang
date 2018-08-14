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

import java.util.List;

//public class RecyclerLapTopAdapter extends RecyclerView.Adapter<RecyclerLapTopAdapter.ViewHolder> {
//
//    private Context context;
//    private List<LapTop> arrLapTop;
//    private itemOnClickListenerLaptop itemOnClickListenerLaptop;
//
//    public void setItemOnClickListenerLaptop(RecyclerLapTopAdapter.itemOnClickListenerLaptop itemOnClickListenerLaptop) {
//        this.itemOnClickListenerLaptop = itemOnClickListenerLaptop;
//    }
//
//    public RecyclerLapTopAdapter(Context context, List<LapTop> arrLapTop) {
//        this.context = context;
//        this.arrLapTop = arrLapTop;
//    }
//
//    public void setData(List<LapTop> arrLapTop) {
//
//        this.arrLapTop.clear();
//        this.arrLapTop.addAll(arrLapTop);
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(context).inflate(R.layout.item_laptop, parent, false);
//        return new ViewHolder(view);
//    }
//
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//        LapTop lapTop = arrLapTop.get(position);
//
//        Glide.with(context).load(lapTop.getImage()).error(R.mipmap.ic_launcher).into(holder.img_laptop);
//
//        holder.tvName.setText(lapTop.getName());
//        int price = Integer.parseInt(lapTop.getPrice());
//        String str1 = NumberFormatCurency.numBerForMat(price);
//        holder.tvPrice.setText(str1);
//        holder.tvScreen.setText(lapTop.getManhinh());
//        holder.tvRam.setText(lapTop.getRam());
//        holder.tvVGA.setText(lapTop.getDohoa());
//        holder.tvHDH.setText(lapTop.getHedieuhanh());
//        holder.tvCpu.setText(lapTop.getCpu());
//        holder.tvTrongLuong.setText(lapTop.getTrongluong());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return arrLapTop.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        private ImageView img_laptop;
//        private TextView tvName, tvPrice, tvScreen, tvCpu, tvRam, tvVGA, tvHDH, tvTrongLuong;
//
//        private ViewHolder(View itemView) {
//            super(itemView);
//            img_laptop = itemView.findViewById(R.id.img_laptop);
//            tvName = itemView.findViewById(R.id.tv_name_laptop);
//            tvPrice = itemView.findViewById(R.id.tv_price_laptop);
//            tvScreen = itemView.findViewById(R.id.tv_manhinh_laptop);
//            tvCpu = itemView.findViewById(R.id.tv_cpu_laptop);
//            tvRam = itemView.findViewById(R.id.tv_ram_laptop);
//            tvHDH = itemView.findViewById(R.id.tv_hdh_laptop);
//            tvVGA = itemView.findViewById(R.id.tv_vga_laptop);
//            tvTrongLuong = itemView.findViewById(R.id.tv_tl_laptop);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    itemOnClickListenerLaptop.onClickItemRecyclerView(v, getAdapterPosition());
//                }
//            });
//        }
//    }
//
//    public interface itemOnClickListenerLaptop {
//
//        void onClickItemRecyclerView(View view, int position);
//    }
//
//}

public class RecyclerLapTopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_ITEM = 0;
    private final int VIEW_PROG = 1;
    private OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;


    private Context context;
    private List<LapTop> arrLapTop;
    private static itemOnClickListenerLaptop itemOnClickListenerLaptop;

    public void setItemOnClickListenerLaptop(RecyclerLapTopAdapter.itemOnClickListenerLaptop itemOnClickListenerLaptop) {
        this.itemOnClickListenerLaptop = itemOnClickListenerLaptop;
    }

    public RecyclerLapTopAdapter(Context context, List<LapTop> arrLapTop) {
        this.context = context;
        this.arrLapTop = arrLapTop;
    }

    public void setData(List<LapTop> arrLapTop) {

        this.arrLapTop.clear();
        this.arrLapTop.addAll(arrLapTop);
        notifyDataSetChanged();
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    public void notifyDatasetChange() {
        notifyDataSetChanged();
        isLoading = false;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laptop, parent, false);

            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more, parent, false);

            return new ProgressViewHolder(view);
        }

    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {

            ViewHolder viewHolder = (ViewHolder) holder;
            LapTop lapTop = arrLapTop.get(position);

            Glide.with(context).load(lapTop.getImage()).error(R.mipmap.ic_launcher).into(viewHolder.img_laptop);

            viewHolder.tvName.setText(lapTop.getName());
            int price = Integer.parseInt(lapTop.getPrice());
            String str1 = NumberFormatCurency.numBerForMat(price);
            viewHolder.tvPrice.setText(str1);
            viewHolder.tvScreen.setText(lapTop.getManhinh());
            viewHolder.tvRam.setText(lapTop.getRam());
            viewHolder.tvVGA.setText(lapTop.getDohoa());
            viewHolder.tvHDH.setText(lapTop.getHedieuhanh());
            viewHolder.tvCpu.setText(lapTop.getCpu());
            viewHolder.tvTrongLuong.setText(lapTop.getTrongluong());
        }
        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {

            isLoading = true;
            loadMoreListener.onLoadMore();
        }


    }


    @Override
    public int getItemCount() {
        return arrLapTop.size();
    }

    @Override
    public int getItemViewType(int position) {
        return arrLapTop.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_laptop;
        private TextView tvName, tvPrice, tvScreen, tvCpu, tvRam, tvVGA, tvHDH, tvTrongLuong;

        private ViewHolder(View itemView) {
            super(itemView);
            img_laptop = itemView.findViewById(R.id.img_laptop);
            tvName = itemView.findViewById(R.id.tv_name_laptop);
            tvPrice = itemView.findViewById(R.id.tv_price_laptop);
            tvScreen = itemView.findViewById(R.id.tv_manhinh_laptop);
            tvCpu = itemView.findViewById(R.id.tv_cpu_laptop);
            tvRam = itemView.findViewById(R.id.tv_ram_laptop);
            tvHDH = itemView.findViewById(R.id.tv_hdh_laptop);
            tvVGA = itemView.findViewById(R.id.tv_vga_laptop);
            tvTrongLuong = itemView.findViewById(R.id.tv_tl_laptop);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemOnClickListenerLaptop.onClickItemRecyclerView(v, getAdapterPosition());
                }
            });
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {

        public ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface itemOnClickListenerLaptop {

        void onClickItemRecyclerView(View view, int position);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }



}
