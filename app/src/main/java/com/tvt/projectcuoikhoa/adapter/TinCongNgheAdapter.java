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
import com.tvt.projectcuoikhoa.model.TinCongNghe;
import com.tvt.projectcuoikhoa.model.TinTuc;

import java.util.List;

public class TinCongNgheAdapter extends BaseAdapter {

    private Context context;

    private List<TinCongNghe> arrTinTuc;

    public TinCongNgheAdapter(Context context, List<TinCongNghe> arrTinTuc) {
        this.context = context;
        this.arrTinTuc = arrTinTuc;
    }

    public void setData(List<TinCongNghe> arrTinTuc) {
        this.arrTinTuc.clear();
        this.arrTinTuc = arrTinTuc;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return arrTinTuc.size();
    }

    @Override
    public Object getItem(int position) {
        return arrTinTuc.get(position);
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

        TinCongNghe tinTuc = arrTinTuc.get(position);

        Picasso.with(context).load(tinTuc.getAnhtieude()).error(R.drawable.iu).into(holder.imgTinTuc);

        holder.tvTitle.setText(tinTuc.getTieude());

        return convertView;
    }

    public class ViewHolder {

        TextView tvTitle;
        ImageView imgTinTuc;
    }
}
