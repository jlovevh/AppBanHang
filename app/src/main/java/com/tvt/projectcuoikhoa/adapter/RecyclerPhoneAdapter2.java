package com.tvt.projectcuoikhoa.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
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

public class RecyclerPhoneAdapter2 extends RecyclerView.Adapter<RecyclerPhoneAdapter2.ViewHolder> {

    private Context context;
    private List<Phone> arrPhone;
    //  private List<Phone> arrPhoneFilter;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public RecyclerPhoneAdapter2(Context context, List<Phone> arrPhone) {
        this.context = context;
        this.arrPhone = arrPhone;
        //   this.arrPhoneFilter=arrPhone;


    }

    public void setData(List<Phone> arrPhone) {

        this.arrPhone.clear();
        this.arrPhone.addAll(arrPhone);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_phone, parent, false);
        return new ViewHolder(MaterialRippleLayout.on(view)
                .rippleOverlay(true)
                .rippleAlpha(0.2f)
                .rippleColor(0xFF585858)
                .rippleHover(true)
                .create()
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Phone phone = arrPhone.get(position);
        holder.tvName.setText(phone.getName());
        int price = Integer.parseInt(phone.getPrice());
        String str1 = NumberFormatCurency.numBerForMat(price);
        holder.tvPrice.setText(str1);
        Glide.with(context).load(phone.getImage()).error(R.mipmap.ic_launcher).into(holder.img_phone);
        holder.tvStatus.setText(phone.getStatus());
    }

    @Override
    public int getItemCount() {
        return arrPhone.size();
    }


    public void setFilter(ArrayList<Phone> arrayList) {

        //arrayList = new ArrayList<>(); // remove this line
        arrPhone.clear(); // add this so that it will clear old data
        arrPhone.addAll(arrayList);
        notifyDataSetChanged();
    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    arrPhoneFilter = arrPhone;
//                } else {
//                    List<Phone> filteredList = new ArrayList<>();
//                    for (Phone row : arrPhone) {
//
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) ) {
//                            filteredList.add(row);
//                        }
//                    }
//
//                    arrPhoneFilter = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = arrPhoneFilter;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                arrPhoneFilter = (ArrayList<Phone>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_phone;
        private TextView tvName, tvPrice, tvStatus;

        public ViewHolder(View itemView) {
            super(itemView);

            img_phone = itemView.findViewById(R.id.img_phone);
            tvName = itemView.findViewById(R.id.tv_name_phone);
            tvPrice = itemView.findViewById(R.id.tv_price_phone);
            tvStatus = itemView.findViewById(R.id.tv_status);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }
    }
}
