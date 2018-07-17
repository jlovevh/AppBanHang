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

import java.util.ArrayList;
import java.util.List;

public class RecyclerPhoneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<Phone> arrPhone;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    //    private final int TYPE_PHONE = 0;
//    private final int TYPE_LOAD = 1;
//    private OnLoadMoreListener loadMoreListener;
//    private boolean isLoading = true;
//    private int visiableItem=6;
//    private int previousTotal = 0;
//    private int firstVisibleItem,visibleItemCount,totalItemCount,lastVisibleItem;
//    private  int current_page=1;


    public RecyclerPhoneAdapter(RecyclerView recyclerView, Context context, List<Phone> arrPhone) {
        this.context = context;
        this.arrPhone = arrPhone;

//        final GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                firstVisibleItem=gridLayoutManager.findLastVisibleItemPosition();
//                visibleItemCount=recyclerView.getChildCount();
//                totalItemCount=gridLayoutManager.getItemCount();
//                if (isLoading){
//                    if(totalItemCount>previousTotal){
//                        isLoading=false;
//                        previousTotal=totalItemCount;
//                    }
//                }
//
//                if(!isLoading && (totalItemCount-visibleItemCount)<=(firstVisibleItem+visiableItem)){
//                    current_page++;
//                    loadMoreListener.onLoadMore(current_page);
//                    isLoading=true;
//                }
//
//            }
//        });
    }

    public void setData(List<Phone> arrPhone){

        this.arrPhone.clear();
        this.arrPhone.addAll(arrPhone);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
//        if(viewType==TYPE_PHONE){
        return new ViewHolder(inflater.inflate(R.layout.item_phone, parent, false));
//        }else{
//            return new ViewHolder(inflater.inflate(R.layout.item_load_more,parent,false));
//        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {

            ViewHolder itemHolder = (ViewHolder) holder;
            Phone phone = arrPhone.get(position);
            itemHolder.tvName.setText(phone.getName());
            itemHolder.tvPrice.setText(phone.getPrice());
            Glide.with(context).load(phone.getImage()).error(R.mipmap.ic_launcher).into(itemHolder.img_phone);
            itemHolder.tvStatus.setText(phone.getStatus());
        }
//        else  if(holder instanceof LoadMoreHolder){
//            LoadMoreHolder loadMoreHolder= (LoadMoreHolder) holder;
//            loadMoreHolder.progressBar.setIndeterminate(true);
//
//        }

    }

//    public void setLoaded() {
//        isLoading = false;
//    }

//    @Override
//    public int getItemViewType(int position) {
//      return arrPhone.get(position) ==null ? TYPE_LOAD:TYPE_PHONE;
//    }

//    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
//        this.loadMoreListener = loadMoreListener;
//    }

    @Override
    public int getItemCount() {
        return arrPhone.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView img_phone;
        private TextView tvName,tvPrice,tvStatus;


        public ViewHolder(View itemView) {
            super(itemView);

            img_phone=itemView.findViewById(R.id.img_phone);
            tvName=itemView.findViewById(R.id.tv_name_phone);
            tvPrice=itemView.findViewById(R.id.tv_price_phone);
            tvStatus=itemView.findViewById(R.id.tv_status);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }
    }

//    public class LoadMoreHolder extends  RecyclerView.ViewHolder{
//        private ProgressBar progressBar;
//
//        public LoadMoreHolder(View itemView) {
//            super(itemView);
//            progressBar=itemView.findViewById(R.id.load_more);
//        }
//    }
//
//    public interface OnLoadMoreListener {
//        void onLoadMore(int page);
//    }

}
