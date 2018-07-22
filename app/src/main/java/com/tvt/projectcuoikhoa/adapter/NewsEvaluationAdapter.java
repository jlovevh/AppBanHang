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
import com.tvt.projectcuoikhoa.model.News;

import java.util.List;

public class NewsEvaluationAdapter extends RecyclerView.Adapter<NewsEvaluationAdapter.ViewHolder> {


    private Context context;
    private List<News> arrNews;

    private ItemClickListener itemClickListener;


    public NewsEvaluationAdapter(Context context, List<News> arrNews) {
        this.context = context;
        this.arrNews = arrNews;
    }

    public void setData(List<News> arrNews) {
        this.arrNews.clear();
        this.arrNews.addAll(arrNews);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_tin_tuc_danhgia, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(context).load(arrNews.get(position).getAnhtieude()).error(R.mipmap.ic_launcher).into(holder.img);
        holder.tvTitle.setText(arrNews.get(position).getTieude());


    }

    @Override
    public int getItemCount() {
        return arrNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView tvTitle;

        private ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_tin_tuc_danhgia);
            tvTitle = itemView.findViewById(R.id.tv_title_tin_tuc_danhgia);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, getAdapterPosition());

                }
            });
        }


    }


    public void setItemClickListener(ItemClickListener ItemClickListener) {
        this.itemClickListener = ItemClickListener;
    }
}
