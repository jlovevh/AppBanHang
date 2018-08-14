package com.tvt.projectcuoikhoa.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.api.APIUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmail, edtVerifiCode, edtPass, edtConfimPass;
    private LinearLayout linearLayout;
    private Button btnGetCode, btnChangePass;
    private TextView tv_kq, tv_kq2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        initViews();
        btnGetCode.setOnClickListener(this);
        btnChangePass.setOnClickListener(this);
    }

    private void initViews() {

        edtEmail = findViewById(R.id.edt_input_email);
        edtConfimPass = findViewById(R.id.edt_confirm_pass);
        edtVerifiCode = findViewById(R.id.edt_verfied_code);
        edtPass = findViewById(R.id.edt_new_pass);
        linearLayout = findViewById(R.id.linear_change_pass);
        btnGetCode = findViewById(R.id.btn_get_code);
        btnChangePass = findViewById(R.id.btn_change_password);
        tv_kq = findViewById(R.id.tv_kq);
        tv_kq2 = findViewById(R.id.tv_kq2);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_get_code:
                if (edtEmail.getText().toString().isEmpty()) {
                    tv_kq.setText("Bạn chưa nhập email");
                } else {
                    APIUtils.getJsonReponse().getCodeFromEmail(edtEmail.getText().toString()).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String message = response.body();
                            Log.d("MEssss", "MEss1:" + message);
                            assert message != null;
                            if (message.equals("failed")) {

                                tv_kq.setText("Email chưa được đăng ký.Vui lòng đăng ký tài khoản khác");
                            } else {

                                tv_kq.setText("Một mã đã được gửi vào email của bạn.Xin vui lòng check email của bạn để thay đổi mật khẩu");
                                linearLayout.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("MEssss", "error:" + t.getMessage());
                        }
                    });
                }

                break;

            case R.id.btn_change_password:
                String newpass = edtPass.getText().toString();
                String comfirmpass = edtConfimPass.getText().toString();
                if (edtVerifiCode.getText().toString().isEmpty()) {
                    tv_kq2.setText("Bạn chưa nhập verified code");
                } else if (edtPass.getText().toString().isEmpty()) {
                    tv_kq2.setText("Bạn chưa nhập new password");
                } else if (edtConfimPass.getText().toString().isEmpty()) {
                    tv_kq2.setText("Bạn chưa nhập confirm password");
                } else if (!newpass.equals(comfirmpass)) {
                    tv_kq2.setText("Password và confirm password không giống nhau.Xin vui lòng nhập lại");
                } else {
                    APIUtils.getJsonReponse().checkotpAndUpdatePassword(edtEmail.getText().toString(), newpass, comfirmpass, edtVerifiCode.getText().toString()).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String mess = response.body();
                            Log.d("MEssss", "MEss:" + mess);
                            assert mess != null;
                            if (mess.equals("success")) {
                                Toast.makeText(ResetPasswordActivity.this, "Change password successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                            } else {

                                tv_kq2.setText("Cập nhật mật khẩu thất bại do verified code wrong");
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });

                }
                break;
        }

    }
}
