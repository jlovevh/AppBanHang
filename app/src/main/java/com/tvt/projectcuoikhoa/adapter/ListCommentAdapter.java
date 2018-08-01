package com.tvt.projectcuoikhoa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.api.Rename;
import com.tvt.projectcuoikhoa.model.Comment;

import java.util.List;

public class ListCommentAdapter extends BaseAdapter {

    private Context context;
    private List<Comment> arrRating;

    public ListCommentAdapter(Context context, List<Comment> arrRating) {
        this.context = context;
        this.arrRating = arrRating;
    }

    @Override
    public int getCount() {
        return arrRating.size();
    }

    @Override
    public Object getItem(int position) {
        return arrRating.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView tvFisrtName, tvName, tvCreate, tvBinhluan;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);

            holder.tvFisrtName = convertView.findViewById(R.id.tv_first_letter);
            holder.tvBinhluan = convertView.findViewById(R.id.tv_binhluan);
            holder.tvCreate = convertView.findViewById(R.id.tv_create_comment);
            holder.tvName = convertView.findViewById(R.id.tv_name_comment);

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        Comment comment = arrRating.get(position);

        holder.tvName.setText(comment.getTenkhachhang());
        holder.tvCreate.setText(comment.getCreateAt());
        holder.tvBinhluan.setText(comment.getBinhluan());
        holder.tvFisrtName.setText(Rename.printFirst(comment.getTenkhachhang()));
        return convertView;
    }
}
