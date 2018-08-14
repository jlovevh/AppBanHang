package com.tvt.projectcuoikhoa.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.activities.NewsActivity;
import com.tvt.projectcuoikhoa.model.News;
import com.tvt.projectcuoikhoa.helper.ItemClickListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecyclerViewTinTucAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private Context context;
    private List<News> arrNews;

    public RecyclerViewTinTucAdapter(Context context, List<News> arrNews) {
        this.context = context;
        this.arrNews = arrNews;
    }

    public void setData(List<News> arrNews) {
        this.arrNews.clear();
        this.arrNews.addAll(arrNews);
        this.notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return arrNews.size();
    }


    public class ViewHolderItem extends RecyclerView.ViewHolder implements View.OnClickListener {


        int view_type;
        ImageView imgTinTuc;
        TextView tvTitle, tvCreateAt;

        private ItemClickListener ItemClickListener;


        ViewHolderItem(View itemView) {
            super(itemView);
            imgTinTuc = itemView.findViewById(R.id.img_item_tin_tuc);
            tvTitle = itemView.findViewById(R.id.tv_title_tin_tuc);
            tvCreateAt = itemView.findViewById(R.id.tv_create_at);
            itemView.setOnClickListener(this);

        }

        public void setItemClickListener(ItemClickListener ItemClickListener) {
            this.ItemClickListener = ItemClickListener;
        }

        @Override
        public void onClick(View v) {
            ItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    private class ViewHolderHeader extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgHeader;
        TextView titleHeader;

        private ItemClickListener ItemClickListener;

        ViewHolderHeader(View itemView) {
            super(itemView);
            imgHeader = itemView.findViewById(R.id.img_header_tin_tuc);
            titleHeader = itemView.findViewById(R.id.tv_title_header_tin_tuc);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener ItemClickListener) {
            this.ItemClickListener = ItemClickListener;
        }

        @Override
        public void onClick(View v) {
            ItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_header_tin_tuc, parent, false);

            return new ViewHolderHeader(view);
        } else if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_tin_tuc, parent, false);

            return new ViewHolderItem(view);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

    }

    private News getItem(int position) {
        return this.arrNews.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderHeader) {
            final News tinMoi = arrNews.get(position);
            ViewHolderHeader holderHeader = (ViewHolderHeader) holder;
            Picasso.with(context).load(tinMoi.getAnhtieude()).error(R.mipmap.ic_launcher).into(holderHeader.imgHeader);
            holderHeader.titleHeader.setText(tinMoi.getTieude());

            holderHeader.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(context, NewsActivity.class);
                    intent.putExtra("title",tinMoi.getTieude());
                    intent.putExtra("image", tinMoi.getAnhtieude());
                    intent.putExtra("baiviet",tinMoi.getBaiviet());
                    intent.putExtra("create",tinMoi.getCreateAt());
                    intent.putExtra("mota", tinMoi.getMota());
                    intent.putExtra("tendanhmuc", tinMoi.getTendanhmuctintuc());
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof ViewHolderItem) {

            final News tinMoi = arrNews.get(position);
            ViewHolderItem holderItem = (ViewHolderItem) holder;
            Picasso.with(context).load(tinMoi.getAnhtieude()).error(R.mipmap.ic_launcher).into(holderItem.imgTinTuc);
            holderItem.tvTitle.setText(tinMoi.getTieude());
            holderItem.tvCreateAt.setText(tinMoi.getCreateAt());

            Timestamp timestamp = null;
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);
                Date parsedDate = dateFormat.parse(tinMoi.getCreateAt());
                timestamp = new java.sql.Timestamp(parsedDate.getTime());
            } catch (Exception e) { //this generic but you can control another types of exception
                // look the origin of excption
            }

            holderItem.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(context, NewsActivity.class);
                    intent.putExtra("title",tinMoi.getTieude());
                    intent.putExtra("image", tinMoi.getAnhtieude());
                    intent.putExtra("baiviet",tinMoi.getBaiviet());
                    intent.putExtra("create",tinMoi.getCreateAt());
                    intent.putExtra("mota", tinMoi.getMota());
                    intent.putExtra("tendanhmuc", tinMoi.getTendanhmuctintuc());
                    context.startActivity(intent);
                }
            });



        }
    }


    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }


}
