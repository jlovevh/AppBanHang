package com.tvt.projectcuoikhoa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.model.News;

import java.util.List;

public class NewsTechnologyAdapter extends BaseAdapter {

    private Context context;

    private List<News> arrNews;

    public NewsTechnologyAdapter(Context context, List<News> arrNews) {
        this.context = context;
        this.arrNews = arrNews;
    }

    public void setData(List<News> arrNews) {
        this.arrNews.clear();
        this.arrNews = arrNews;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return arrNews.size();
    }

    @Override
    public Object getItem(int position) {
        return arrNews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.item_tin_cong_nghe, parent, false);
            holder.tvTitle = convertView.findViewById(R.id.tv_title_tintuc);
            holder.imgTinTuc = convertView.findViewById(R.id.img_item_tintuc);

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        News news = arrNews.get(position);

        Picasso.with(context).load(news.getAnhtieude()).error(R.drawable.iu).into(holder.imgTinTuc);

        holder.tvTitle.setText(news.getTieude());

        return convertView;
    }

    public class ViewHolder {
        TextView tvTitle;
        ImageView imgTinTuc;
    }
}
