package com.tvt.projectcuoikhoa.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.activities.DetailPhoneActivity;
import com.tvt.projectcuoikhoa.activities.MainActivity;
import com.tvt.projectcuoikhoa.activities.ShoppingCartActivity;
import com.tvt.projectcuoikhoa.api.PostAndUpdateCartToService;
import com.tvt.projectcuoikhoa.fragment.HomeFragment;
import com.tvt.projectcuoikhoa.model.Cart;
import com.tvt.projectcuoikhoa.utils.Constant;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Cart cart = arrCart.get(position);
        holder.tvName.setText(cart.getName());
        holder.tvPrice.setText(NumberFormatCurency.numBerForMat(cart.getPrice()));
        Glide.with(context).load(cart.getUrl_image()).error(R.mipmap.ic_launcher).into(holder.img);
        holder.elegantNumberButton.setNumber(String.valueOf(cart.getSoluong()));
        int total = 0;
        for (int i = 0; i < HomeFragment.arrCart.size(); i++) {
            total += HomeFragment.arrCart.get(i).getPrice();
            Log.d("SSIZES", "Total: " + total);
        }
        ShoppingCartActivity.tvSumMoney.setText(NumberFormatCurency.numBerForMat(total));
        holder.elegantNumberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                int slht = HomeFragment.arrCart.get(position).getSoluong();
                int giahientai = HomeFragment.arrCart.get(position).getPrice();
                HomeFragment.arrCart.get(position).setSoluong(newValue);
                int giamoi = (giahientai * newValue) / oldValue;
                HomeFragment.arrCart.get(position).setPrice(giamoi);
                holder.tvPrice.setText(NumberFormatCurency.numBerForMat(giamoi));
                PostAndUpdateCartToService.updateCart(HomeFragment.arrCart.get(position).getSoluong(), giamoi, HomeFragment.arrCart.get(position).getId_sp());
                int tong = 0;
                for (int i = 0; i < arrCart.size(); i++) {
                    tong += arrCart.get(i).getPrice();
                }
                ShoppingCartActivity.tvSumMoney.setText(NumberFormatCurency.numBerForMat(tong));
            }
        });

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
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    new AlertDialog.Builder(context)
                            .setTitle("Thông báo")
                            .setMessage("Bạn có muốn xóa sản phẩm này không?")
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //  MainActivity.shoppingCartHelper.delete(arrCart.get(getAdapterPosition()).getId_sp());


                                    if (HomeFragment.arrCart.size() < 0) {
                                        ShoppingCartActivity.linearLayout.setVisibility(View.VISIBLE);
                                        ShoppingCartActivity.textView.setText("0");
                                        ShoppingCartActivity.tvSumMoney.setText("0 đ");
                                    } else {
                                        ShoppingCartActivity.deleteCartByUserId(arrCart.get(0).getId_user());
                                        arrCart.remove(getAdapterPosition());
                                        notifyDataSetChanged();

                                    }

                                }
                            })
                            .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();

                    return true;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;

                    intent = new Intent(context, DetailPhoneActivity.class);
                    intent.putExtra("idsp", arrCart.get(getAdapterPosition()).getId_sp());
                    context.startActivity(intent);

                }
            });
        }
    }
}
