package com.tvt.projectcuoikhoa.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.oob.SignUp;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.api.APIUtils;
import com.tvt.projectcuoikhoa.api.JsonReponse;
import com.tvt.projectcuoikhoa.utils.Constant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1010;
    private static final int PERMISSION_MULTI_REQUEST = 111;
    private static final int REQUEST_CODE_GALLERY = 123;
    private ImageView imgUser;
    private String realPath = "";
    private Button btnSignUp;
    private EditText edtUser, edtPass, edtName, edtPhone, edtEmail, edtAddress;
    private String username, password, name, phone, email, address, filename;
    private Camera mCameraDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
//        try {
//            mCameraDevice = android.hardware.Camera.open();
//        } catch (RuntimeException e) {
//            Log.e(Constant.TAG, "fail to connect Camera", e);
//            // Throw exception
//        }
        initPermisstion();
        initViews();

        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new MaterialDialog.Builder(SignUpActivity.this)
                        .title(R.string.choice)
                        .items(R.array.choice)
                        .itemsCallbackSingleChoice(1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                if (which == 0) {
                                    showGallery();
                                } else {
                                    showCamera();
                                }
                                return true;
                            }
                        })
                        .positiveText(R.string.choice2)
                        .show();

            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = edtUser.getText().toString();
                password = edtPass.getText().toString();
                name = edtName.getText().toString();
                email = edtEmail.getText().toString();
                address = edtAddress.getText().toString();
                phone = edtPhone.getText().toString();

                if (username.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Username is required.Please add user name !", Toast.LENGTH_SHORT).show();

                } else if (password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Password is required.Please insert password !", Toast.LENGTH_SHORT).show();


                } else if (name.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please add name !", Toast.LENGTH_SHORT).show();

                } else if (email.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Enter valid email! !", Toast.LENGTH_SHORT).show();

                } else if (phone.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please insert phone number !", Toast.LENGTH_SHORT).show();

                } else if (address.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please insert address !", Toast.LENGTH_SHORT).show();
                } else {
                    uploadImage();
                }

            }
        });
    }

    private void showCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private void showGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    private void uploadImage() {
        File file = new File(realPath);
        String file_path = file.getAbsolutePath();
        Log.d("AAA", "file_pathF: " + file_path);
        String[] arrNameFile = file_path.split("\\.");
        file_path = arrNameFile[0] + System.currentTimeMillis() + "." + arrNameFile[1];
        filename = file_path.substring(file_path.lastIndexOf("/") + 1);
        Log.d("AAA", "file_path: " + file_path);
        Log.d("AAA", "name: " + filename);

        final RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);

        JsonReponse jsonReponse = APIUtils.getJsonReponse();
        Call<String> callback = jsonReponse.postImageUser(body);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String message = response.body();
                    Log.d("AAA", "Message: " + message);
                    assert message != null;
                    if (message.equals("success")) {
                        register();
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("AAA", "Error: " + t.getMessage());
            }
        });
    }

    private void register() {
        String image = Constant.BASE_URL + "image/" + filename;
        Log.e("images", image);
        APIUtils.getJsonReponse().signUp(username, password, name, email, Integer.parseInt(phone), address, image).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String message = response.body();
                Log.d("AAA", "Message2: " + message);
                if (message.equals("success")) {
                    Toast.makeText(SignUpActivity.this, "Register successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SignUpActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("AAA", "Error2: " + t.getMessage());
            }
        });
    }


    private void initPermisstion() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) + checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) || shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {

                    Toast.makeText(this, "Please Grant Permissions to upload profile photo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permisson don't granted and dont show dialog again ", Toast.LENGTH_SHORT).show();
                }

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSION_MULTI_REQUEST);
            }
        }
    }

    private void initViews() {
        imgUser = findViewById(R.id.img_user);
        btnSignUp = findViewById(R.id.btnSignUp);
        edtUser = findViewById(R.id.edt_user);
        edtPass = findViewById(R.id.pass);
        edtAddress = findViewById(R.id.edtAddress);
        edtEmail = findViewById(R.id.edtemail);
        edtPhone = findViewById(R.id.edtPhone);
        edtName = findViewById(R.id.name);
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case REQUEST_CODE_GALLERY:
                    InputStream inputStream = null;
                    Uri uri = data.getData();
                    realPath = getRealPathFromURI(uri);
                    try {
                        if (uri != null) {
                            inputStream = getContentResolver().openInputStream(uri);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imgUser.setImageBitmap(bitmap);
                    break;

                case CAMERA_REQUEST:
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    imgUser.setImageBitmap(photo);
                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    Uri tempUri = getImageUri(getApplicationContext(), photo);

                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    realPath = getRealPathFromURI2(tempUri);
                    break;
            }

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI2(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_MULTI_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Bạn đã có quyền", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Bạn chưa cấp quyền ", Toast.LENGTH_LONG).show();
            }

            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
//                Intent cameraIntent = new
//                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}


