package com.tvt.projectcuoikhoa.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.helper.ItemClickListener;
import com.tvt.projectcuoikhoa.model.Phone;
import com.tvt.projectcuoikhoa.utils.NumberFormatCurency;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//public class RecyclerPhoneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    private Context context;
//
//    private List<Phone> arrPhone;
//
//    private ItemClickListener itemClickListener;
//
//    public void setItemClickListener(ItemClickListener itemClickListener) {
//        this.itemClickListener = itemClickListener;
//    }
//
//    private final int TYPE_PHONE = 0;
//    private final int TYPE_LOAD = 1;
//
//
//    public RecyclerPhoneAdapter(Context context, List<Phone> arrPhone) {
//        this.context = context;
//        this.arrPhone = arrPhone;
//
//
//    }
//
//    public void setData(List<Phone> arrPhone) {
//        this.arrPhone.clear();
//        this.arrPhone.addAll(arrPhone);
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        if (viewType == TYPE_PHONE) {
//            return new ViewHolder(inflater.inflate(R.layout.item_phone, parent, false));
//        }
//
//        return new ViewHolder(inflater.inflate(R.layout.item_load_more, parent, false));
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof ViewHolder) {
//
//            ViewHolder itemHolder = (ViewHolder) holder;
//            Phone phone = arrPhone.get(position);
//            itemHolder.tvName.setText(phone.getName());
//            int price = Integer.parseInt(phone.getPrice());
//            String str1 = NumberFormatCurency.numBerForMat(price);
//            itemHolder.tvPrice.setText(str1);
//            Glide.with(context).load(phone.getImage()).error(R.mipmap.ic_launcher).into(itemHolder.img_phone);
//            itemHolder.tvStatus.setText(phone.getStatus());
//        } else if (holder instanceof LoadMoreHolder) {
//            LoadMoreHolder loadMoreHolder = (LoadMoreHolder) holder;
//            loadMoreHolder.progressBar.setIndeterminate(true);
//            loadMoreHolder.progressBar.setVisibility(View.VISIBLE);
//
//        }
//
//    }
//
//
//    @Override
//    public int getItemViewType(int position) {
//        return arrPhone.get(position) == null ? TYPE_LOAD : TYPE_PHONE;
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return arrPhone.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        private ImageView img_phone;
//        private TextView tvName, tvPrice, tvStatus;
//
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//
//            img_phone = itemView.findViewById(R.id.img_phone);
//            tvName = itemView.findViewById(R.id.tv_name_phone);
//            tvPrice = itemView.findViewById(R.id.tv_price_phone);
//            tvStatus = itemView.findViewById(R.id.tv_status);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    itemClickListener.onItemClick(v, getAdapterPosition());
//                }
//            });
//        }
//    }
//
//    public class LoadMoreHolder extends RecyclerView.ViewHolder {
//        private ProgressBar progressBar;
//
//        public LoadMoreHolder(View itemView) {
//            super(itemView);
//            progressBar = itemView.findViewById(R.id.load_more);
//        }
//    }
//
//
//}

public class RecyclerPhoneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final int VIEW_ITEM = 0;
    public final int VIEW_PROG = 1;
    static Context context;
    private List<Phone> mDataset;
    public OnLoadMoreListener loadMoreListener;
    private static ItemClickListener itemClickListener;
    boolean isLoading = false, isMoreDataAvailable = true;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setData(List<Phone> arr) {
        this.mDataset.addAll(arr)
        ;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_phone;
        private TextView tvName, tvPrice, tvStatus;

        public ViewHolder(View v) {
            super(v);
            img_phone = v.findViewById(R.id.img_phone);
            tvName = v.findViewById(R.id.tv_name_phone);
            tvPrice = v.findViewById(R.id.tv_price_phone);
            tvStatus = v.findViewById(R.id.tv_status);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressViewHolder(View v) {
            super(v);
        }
    }

    public RecyclerPhoneAdapter(Context context, List<Phone> mDataset) {
        this.context = context;
        this.mDataset = mDataset;
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    public void notifyDatasetChanged() {
        notifyDataSetChanged();
        isLoading = false;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_phone, parent, false);

            return new ViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_load_more, parent, false);

            return new ProgressViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder itemHolder = (ViewHolder) holder;
            Phone phone = mDataset.get(position);
            itemHolder.tvName.setText(phone.getName());
            int price = Integer.parseInt(phone.getPrice());
            String str1 = String.valueOf(price);
            itemHolder.tvPrice.setText(str1);
            Glide.with(context).load(phone.getImage()).error(R.mipmap.ic_launcher).into(itemHolder.img_phone);
            itemHolder.tvStatus.setText(phone.getStatus());
        }

        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {

            isLoading = true;
            loadMoreListener.onLoadMore();
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}
