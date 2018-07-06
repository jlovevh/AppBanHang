package com.tvt.projectcuoikhoa.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.model.TinTuc;

import java.util.List;

public class TinTucDanhGiaAdapter extends  RecyclerView.Adapter<TinTucDanhGiaAdapter.ViewHolder> {



    private Context context;
    private List<TinTuc> arrTinTuc;

    public TinTucDanhGiaAdapter(Context context, List<TinTuc> arrTinTuc) {
        this.context = context;
        this.arrTinTuc = arrTinTuc;
    }

    public void setData(List<TinTuc> arrTinTuc){
        this.arrTinTuc.clear();
        this.arrTinTuc.addAll(arrTinTuc);
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_tin_tuc_danhgia,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(context).load(arrTinTuc.get(position).getAnhtieude()).error(R.mipmap.ic_launcher).into(holder.img);
        holder.tvTitle.setText(arrTinTuc.get(position).getTieude());
    }

    @Override
    public int getItemCount() {
        return arrTinTuc.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        private TextView tvTitle;

        private ViewHolder(View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img_tin_tuc_danhgia);
            tvTitle=itemView.findViewById(R.id.tv_title_tin_tuc_danhgia);
        }
    }
}
