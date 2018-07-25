package com.tvt.projectcuoikhoa.activities;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.adapter.ShoppingCartAdapter;
import com.tvt.projectcuoikhoa.fragment.HomeFragment;
import com.tvt.projectcuoikhoa.helper.MyDividerItemDecoration;
import com.tvt.projectcuoikhoa.model.Cart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShoppingCartActivity extends AppCompatActivity {

    @BindView(R.id.recyclerCart)
    RecyclerView recyclerView;
    @BindView(R.id.img_back_phone_cart)
    ImageView imageBack;
    @BindView(R.id.constraintLayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.counttxt_cart)
    TextView textView;

    @OnClick(R.id.button2)
    void backHome() {
        Intent intent = new Intent(ShoppingCartActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.img_back_phone_cart)
    void back() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        if (HomeFragment.arrCart.size() == 0) {
            constraintLayout.setVisibility(View.VISIBLE);
        }
        textView.setText(String.valueOf(HomeFragment.arrCart.size()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 1));
        ShoppingCartAdapter adapter = new ShoppingCartAdapter(this, HomeFragment.arrCart);
        recyclerView.setAdapter(adapter);


        if (HomeFragment.arrCart.size() == 2) {
            Log.d("SSIZES", "Size2: " + HomeFragment.arrCart.size());
            Log.d("SSIZES", "Name2: " + HomeFragment.arrCart.get(1).getName());
            Log.d("SSIZES", "soluong2: " + HomeFragment.arrCart.get(1).getSoluong());
            Log.d("SSIZES", "gia2: " + HomeFragment.arrCart.get(1).getPrice());
            Log.d("SSIZES", "user_id2: " + HomeFragment.arrCart.get(1).getId_user());
        } else if (HomeFragment.arrCart.size() == 1) {
            Log.d("SSIZES", "Size: " + HomeFragment.arrCart.size());
            Log.d("SSIZES", "Name: " + HomeFragment.arrCart.get(0).getName());
            Log.d("SSIZES", "soluong: " + HomeFragment.arrCart.get(0).getSoluong());
            Log.d("SSIZES", "gia: " + HomeFragment.arrCart.get(0).getPrice());
            Log.d("SSIZES", "user_id: " + HomeFragment.arrCart.get(0).getId_user());
        }
    }
}
