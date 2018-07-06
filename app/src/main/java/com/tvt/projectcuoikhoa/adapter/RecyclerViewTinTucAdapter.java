package com.tvt.projectcuoikhoa.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.model.TinTuc;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecyclerViewTinTucAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private Context context;
    private List<TinTuc> arrTinTuc;

    public RecyclerViewTinTucAdapter(Context context, List<TinTuc> arrTinTuc) {
        this.context = context;
        this.arrTinTuc = arrTinTuc;
    }

        public void setData(List<TinTuc> arrTinTuc){
        this.arrTinTuc.clear();
        this.arrTinTuc.addAll(arrTinTuc);
        this.notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return arrTinTuc.size();
    }

    public class ViewHolderItem extends RecyclerView.ViewHolder{


        int view_type;
        ImageView imgTinTuc;
        TextView tvTitle,tvCreateAt;



        ViewHolderItem(View itemView) {
            super(itemView);
                imgTinTuc=itemView.findViewById(R.id.img_item_tin_tuc);
                tvTitle=itemView.findViewById(R.id.tv_title_tin_tuc);
                tvCreateAt=itemView.findViewById(R.id.tv_create_at);



        }
    }

    private  class ViewHolderHeader extends RecyclerView.ViewHolder{
        ImageView imgHeader;
        TextView titleHeader;
        ViewHolderHeader(View itemView) {
            super(itemView);
            imgHeader=itemView.findViewById(R.id.img_header_tin_tuc);
            titleHeader=itemView.findViewById(R.id.tv_title_header_tin_tuc);
        }
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==TYPE_HEADER){
            View view = LayoutInflater.from(context).inflate(R.layout.item_header_tin_tuc,parent,false);

            return new ViewHolderHeader(view);
        }
        else if(viewType==TYPE_ITEM){
            View view = LayoutInflater.from(context).inflate(R.layout.item_tin_tuc,parent,false);

            return new ViewHolderItem(view);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

    }

    private TinTuc getItem(int position)
    {
        return this.arrTinTuc.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolderHeader){
            TinTuc tinMoi=arrTinTuc.get(position);
            ViewHolderHeader holderHeader= (ViewHolderHeader) holder;
            Picasso.with(context).load(tinMoi.getAnhtieude()).error(R.mipmap.ic_launcher).into(holderHeader.imgHeader);
            holderHeader.titleHeader.setText(tinMoi.getTieude());
        }else  if(holder instanceof  ViewHolderItem){

            TinTuc tinMoi=arrTinTuc.get(position);
            ViewHolderItem holderItem= (ViewHolderItem) holder;
            Picasso.with(context).load(tinMoi.getAnhtieude()).error(R.mipmap.ic_launcher).into(holderItem.imgTinTuc);
            holderItem.tvTitle.setText(tinMoi.getTieude());
            holderItem.tvCreateAt.setText(tinMoi.getCreateAt());

            Timestamp timestamp = null;
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);
                Date parsedDate = dateFormat.parse(tinMoi.getCreateAt());
                timestamp= new java.sql.Timestamp(parsedDate.getTime());
            } catch(Exception e) { //this generic but you can control another types of exception
                // look the origin of excption
            }



            Log.d("h","f "+timestamp);

            Timestamp timestamp1 =new Timestamp(System.currentTimeMillis());
            Log.d("h","fs "+timestamp1);


           


        }
    }



    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_HEADER;
        }

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position)
    {
        return position == 0;
    }
}
