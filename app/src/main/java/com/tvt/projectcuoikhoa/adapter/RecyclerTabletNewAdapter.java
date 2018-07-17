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
import com.tvt.projectcuoikhoa.helper.ItemClickListener;
import com.tvt.projectcuoikhoa.model.Tablet;

import java.util.ArrayList;
import java.util.List;

public class RecyclerTabletNewAdapter extends RecyclerView.Adapter<RecyclerTabletNewAdapter.ViewHolder> {

    private Context context;
    private List<Tablet> arrTablet;
    private ItemClickListenerTablet itemClickListener;

    public void setItemClickListener(ItemClickListenerTablet itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public RecyclerTabletNewAdapter(Context context, List<Tablet> arrTablet) {
        this.context = context;
        this.arrTablet = arrTablet;
    }

    public void setData(List<Tablet> arrTablet) {

        this.arrTablet.clear();
        this.arrTablet.addAll(arrTablet);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tablet_noibat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Tablet tablet = arrTablet.get(position);

        Picasso.with(context).load(tablet.getImage()).error(R.mipmap.ic_launcher).into(holder.img_tablet);
        holder.tvName.setText(tablet.getName());
        holder.tvPrice.setText(tablet.getPrice());
        holder.tvStatus.setText(tablet.getStatus());

    }

    @Override
    public int getItemCount() {
        return arrTablet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_tablet;
        private TextView tvName, tvPrice, tvStatus;

        public ViewHolder(View itemView) {
            super(itemView);

            img_tablet = itemView.findViewById(R.id.img_tablet);
            tvName = itemView.findViewById(R.id.tv_name_tablet);
            tvPrice = itemView.findViewById(R.id.tv_price_tablet);
            tvStatus = itemView.findViewById(R.id.tv_status_tablet);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClickTablet(v, getAdapterPosition());
                }
            });
        }
    }

    public interface ItemClickListenerTablet {

        void onItemClickTablet(View view, int position);
    }
}