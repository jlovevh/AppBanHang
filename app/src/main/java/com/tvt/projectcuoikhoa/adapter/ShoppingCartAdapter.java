package com.tvt.projectcuoikhoa.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.model.Cart;
import com.tvt.projectcuoikhoa.utils.NumberFormatCurency;

import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {
    private Context context;
    private List<Cart> arrCart;

    public ShoppingCartAdapter(Context context, List<Cart> arrCart) {
        this.context = context;
        this.arrCart = arrCart;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shopping_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart = arrCart.get(position);
        holder.tvName.setText(cart.getName());
        holder.tvPrice.setText(NumberFormatCurency.numBerForMat(cart.getPrice()));
        Glide.with(context).load(cart.getUrl_image()).error(R.mipmap.ic_launcher).into(holder.img);
        holder.elegantNumberButton.setNumber(String.valueOf(cart.getSoluong()));

    }

    @Override
    public int getItemCount() {
        return arrCart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView tvName, tvPrice;
        private ElegantNumberButton elegantNumberButton;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_cart);
            tvName = itemView.findViewById(R.id.tv_name_cart);
            tvPrice = itemView.findViewById(R.id.tv_price_cart);
            elegantNumberButton = itemView.findViewById(R.id.number_button);
        }
    }
}
