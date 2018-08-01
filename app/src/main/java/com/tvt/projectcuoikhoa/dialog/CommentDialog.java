package com.tvt.projectcuoikhoa.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.api.APIUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentDialog extends Dialog implements View.OnClickListener {

    private EditText edtName, edtEmail, edtPhone;
    private TextView tvEmty;
    private String idSp, binhluan;

    public CommentDialog(@NonNull Context context, String idSp, String binhluan) {
        super(context);
        this.idSp = idSp;
        this.binhluan = binhluan;

    }

    public CommentDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CommentDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_comment);
        assert getWindow() != null;
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button btnSendComment = findViewById(R.id.btn_comment);
        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtPhone = findViewById(R.id.edt_phone);
        tvEmty = findViewById(R.id.tv_emty_comment);
        btnSendComment.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_comment:
                if (edtName.getText().toString().isEmpty()) {
                    tvEmty.setVisibility(View.VISIBLE);
                    tvEmty.setText("Vui lòng nhập họ tên");
                } else if (edtEmail.getText().toString().isEmpty()) {
                    tvEmty.setVisibility(View.VISIBLE);
                    tvEmty.setText("Vui lòng nhập email");
                } else if (edtPhone.getText().toString().isEmpty()) {
                    tvEmty.setVisibility(View.VISIBLE);
                    tvEmty.setText("Vui lòng nhập số điện thoại");
                } else {

                    tvEmty.setVisibility(View.GONE);
                    tvEmty.setText("");
                    APIUtils.getJsonReponse().postComment(idSp, edtName.getText().toString(), edtEmail.getText().toString(), edtPhone.getText().toString(), binhluan).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            dismiss();
                            String mess = response.body();
                            Log.d("MESSSA", "MES: " + mess);
                            if (response.isSuccessful()) {
                                if (mess != null) {
                                    if (mess.equals("success")) {
                                        Toast.makeText(getContext(), "Gửi bình luận thành công", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;


        }
    }

}
