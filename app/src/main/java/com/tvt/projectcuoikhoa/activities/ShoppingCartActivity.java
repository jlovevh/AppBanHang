package com.tvt.projectcuoikhoa.activities;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.adapter.ShoppingCartAdapter;
import com.tvt.projectcuoikhoa.api.APIUtils;
import com.tvt.projectcuoikhoa.fragment.HomeFragment;
import com.tvt.projectcuoikhoa.helper.MyDividerItemDecoration;
import com.tvt.projectcuoikhoa.model.Cart;
import com.tvt.projectcuoikhoa.model.User;
import com.tvt.projectcuoikhoa.utils.NumberFormatCurency;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCartActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "channelID";
    private ShoppingCartAdapter adapter;
    public static int total;

    private String id, email, name, phone, address, idFB, idGG, nameGG, nameFB, emailFB, emailGG;

    @BindView(R.id.recyclerCart)
    RecyclerView recyclerView;
    @BindView(R.id.img_back_phone_cart)
    ImageView imageBack;

    public static TextView textView;
    @SuppressLint("StaticFieldLeak")
    public static TextView tvSumMoney;
    public static LinearLayout linearLayout;

    @OnClick(R.id.btn_next_buy)
    void backHome2() {
//        Intent intent = new Intent(this, MainActivity.class);// New activity
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_thanh_toan)
    void showInputDialogCustomInvalidation() {
        View view = LayoutInflater.from(this).inflate(R.layout.alert_label_editor, null);
        final EditText edtID, edtName, edtPhone, edtEmail, edtAddress;
        edtID = view.findViewById(R.id.edt_id_user);
        edtName = view.findViewById(R.id.edtName);
        edtAddress = view.findViewById(R.id.edt_address);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPhone = view.findViewById(R.id.edtPhone);
        switch (MainActivity.key) {
            case 1:
                edtID.setText(id);
                edtEmail.setText(email);
                edtName.setText(name);
                edtPhone.setText(phone);
                edtAddress.setText(address);
                break;
            case 2:
                edtID.setText(idFB);
                edtEmail.setText(emailFB);
                edtName.setText(nameFB);
                break;
            case 3:
                edtID.setText(idGG);
                edtEmail.setText(emailGG);
                edtName.setText(nameGG);
                break;
        }

        if (HomeFragment.arrCart.size() <= 0) {
            Toast.makeText(this, "Giỏ hàng của bạn không có sản phẩm nào", Toast.LENGTH_SHORT).show();
        } else {

            new AlertDialog.Builder(this)
                    .setView(view)
                    .setTitle("XIN VUI LÒNG NHẬP CÁC THÔNG TIN CẦN THIẾT")
                    .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (edtID.getText().toString().isEmpty() | edtName.getText().toString().isEmpty() | edtEmail.getText().toString().isEmpty() |
                                    edtAddress.getText().toString().isEmpty() | edtPhone.getText().toString().isEmpty()) {

                                Toast.makeText(ShoppingCartActivity.this, "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            } else {
                                postInformation(edtID.getText().toString(), edtName.getText().toString(), edtEmail.getText().toString()
                                        , edtPhone.getText().toString(), edtAddress.getText().toString());
                            }

                        }
                    }).setCancelable(true)
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create()
                    .show();


        }
    }

    private void postInformation(String id, String name, String email, String phone, String address) {

        APIUtils.getJsonReponse().postInformationCustomer(id, name, email, Integer.parseInt(phone), address).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String message = response.body();
                if (message != null) {
                    Log.d("messagesssssssss", "message: " + message);

                    postChiTietHoadonToService(message);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    private void postChiTietHoadonToService(String message) {
        JSONArray jsonArray = new JSONArray();
        String json = null;
        for (int i = 0; i < HomeFragment.arrCart.size(); i++) {

            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("id_sp", HomeFragment.arrCart.get(i).getId_sp());
                jsonObject.put("id_hoadon", message);
                jsonObject.put("id_user", HomeFragment.arrCart.get(i).getId_user());
                jsonObject.put("image", HomeFragment.arrCart.get(i).getUrl_image());
                jsonObject.put("ten_sp", HomeFragment.arrCart.get(i).getName());
                jsonObject.put("sl_sp", HomeFragment.arrCart.get(i).getSoluong());
                jsonObject.put("tonggia1sanpham", HomeFragment.arrCart.get(i).getPrice());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
            json = jsonArray.toString();
            Log.d("jsonnnnnnnnnnnnnn", json);
        }

        APIUtils.getJsonReponse().postChiTietDonHang(json).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String mess = response.body();
                if (mess != null && mess.equals("susscess")) {
                    showNotification();

                    if (HomeFragment.arrCart.size() > 0) {
                        deleteCartByUserId(HomeFragment.arrCart.get(0).getId_user());
                        Log.d("UserIDasada", "UserID: " + HomeFragment.arrCart.get(0).getId_user());
                        HomeFragment.arrCart.clear();
                        textView.setText("");
                        tvSumMoney.setText("0 đ");
                        linearLayout.setVisibility(View.VISIBLE);
                        adapter.notifyDataSetChanged();

                    }


                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ShoppingCartActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showNotification() {
        NotificationManager notificationManager = null;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("you have successfully ordered")
                .setContentText("Wish you happy shopping")
                .setSmallIcon(R.drawable.ic_shopping_cart_white_24dp)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);


            builder.setChannelId(CHANNEL_ID);
            notificationManager.createNotificationChannel(channel);

        }
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(11, builder.build());

    }

    public static void deleteCartByUserId(String userId) {
        APIUtils.getJsonReponse().deleteCartByIdUser(userId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String mess = response.body();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

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
        if (MainActivity.key == 1) {
            User user = MainActivity.arr.get(0);
            name = user.getName();
            phone = user.getPhone();
            email = user.getEmail();
            id = user.getId();
            address = user.getAddress();
            String address = user.getAddress();

//            Log.d("Intentaaaaaaaaaaa", "ID: " + id + " name: " + name + " email: " + email + "url: " + address + " phone: " + phone);
        } else if (MainActivity.key == 2) {
            nameFB = MainActivity.arrUserFB.get(0);
            emailFB = MainActivity.arrUserFB.get(1);
            idFB = MainActivity.arrUserFB.get(2);
//            Log.d("Intentaaaaaaaaaaa", "IDFB: " + idFB + " nameFB: " + nameFB + " emailFB: " + emailFB);
        } else if (MainActivity.key == 3) {
            nameGG = MainActivity.arrUserGG.get(0);
            emailGG = MainActivity.arrUserGG.get(1);
            idGG = MainActivity.arrUserGG.get(2);
//            Log.d("Intentaaaaaaaaaaa", "IDGG: " + idGG + " nameGG: " + nameGG + " emailGG: " + emailGG);
        }

        tvSumMoney = findViewById(R.id.tv_total_money);
        textView = findViewById(R.id.counttxt_cart);
        linearLayout = findViewById(R.id.constraintLayout);
        ButterKnife.bind(this);
        if (HomeFragment.arrCart.size() <= 0) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.GONE);
        }
        textView.setText(String.valueOf(HomeFragment.arrCart.size()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 1));
        adapter = new ShoppingCartAdapter(this, HomeFragment.arrCart);
        recyclerView.setAdapter(adapter);


    }


    private TextWatcher generalTextWatch(final AlertDialog builder) {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    builder.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                } else {
                    builder.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }

            }
        };
        return textWatcher;
    }


}
