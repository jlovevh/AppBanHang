package com.tvt.projectcuoikhoa.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.activities.MainActivity;
import com.tvt.projectcuoikhoa.activities.SignUpActivity;
import com.tvt.projectcuoikhoa.api.APIUtils;
import com.tvt.projectcuoikhoa.api.JsonReponse;
import com.tvt.projectcuoikhoa.model.User;
import com.tvt.projectcuoikhoa.utils.Constant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.tvt.projectcuoikhoa.activities.SignUpActivity.CAMERA_REQUEST;
import static com.tvt.projectcuoikhoa.activities.SignUpActivity.REQUEST_CODE_GALLERY;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements View.OnClickListener {

    private ImageView imgUser;
    private EditText tvName, tvEmail;
    private EditText edtUser, edtPass, edtPhone, edtAddress;
    private Button btnEdit;
    private String filename, realPath = "";
    private String urlU, id;
    private String image = null;
    private boolean isClick = false;

    public static AccountFragment newInstance() {
        Bundle args = new Bundle();
        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @SuppressLint("ValidFragment")
    private AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tai_khoan, container, false);
        initViews(view);
        if (MainActivity.key == 1) {
            Intent intentU = getActivity().getIntent();
            User user = (User) intentU.getSerializableExtra("user");
            String nameUser = user.getName();
            String emailUser = user.getEmail();
            id = user.getId();
            urlU = user.getImage();
            Picasso.with(getContext()).load(urlU).error(R.mipmap.ic_launcher).into(imgUser);
            tvName.setText(nameUser);
            tvEmail.setText(emailUser);
            edtUser.setText(user.getUsername());
            edtPass.setText(user.getPassword());
            edtPhone.setText(user.getPhone());
            edtAddress.setText(user.getAddress());
            btnEdit.setText("SỬA");

            Log.d("TaFAdadad", "AAA: " + nameUser + emailUser + urlU);
            imgUser.setOnClickListener(this);
            btnEdit.setOnClickListener(this);
        } else if (MainActivity.key == 2) {
            Toast.makeText(getContext(), "Đây không phảo tài khoản User", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void initViews(View view) {
        imgUser = view.findViewById(R.id.img_user1);
        tvName = view.findViewById(R.id.name_user);
        tvEmail = view.findViewById(R.id.email_user);
        edtUser = view.findViewById(R.id.edtUserName);
        edtPass = view.findViewById(R.id.edtPassword);
        edtPhone = view.findViewById(R.id.edtsdt);
        edtAddress = view.findViewById(R.id.edtDiachi);
        btnEdit = view.findViewById(R.id.btnEdit);
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    public String getRealPathFromURI2(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_user1:
                isClick = true;
                new MaterialDialog.Builder(getContext())
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
                break;
            case R.id.btnEdit:
                if (btnEdit.getText().toString().equals("SỬA")) {
                    btnEdit.setText("UPDATE");
                    edtAddress.setEnabled(true);
                    edtPhone.setEnabled(true);
                    edtPass.setEnabled(true);
                    edtUser.setEnabled(true);
                    tvEmail.setEnabled(true);
                    tvName.setEnabled(true);

                } else {
                    if (isClick == true) {
                        uploadImage();

                    } else {
                        APIUtils.getJsonReponse().updateUser(id, tvName.getText().toString(), tvEmail.getText().toString()
                                , edtUser.getText().toString(), edtPass.getText().toString(), edtPhone.getText().toString(),
                                edtAddress.getText().toString(), urlU).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String mess = response.body();
                                Log.d("BBB", "mesasadad: " + mess);
                                if (mess.equals("ok")) {
                                    Toast.makeText(getContext(), "Update user thành công", Toast.LENGTH_SHORT).show();
//                                    Fragment fragment=HomeFragment.newInstance();
//                                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                                    fragmentTransaction.replace(R.id.layout, fragment);
//                                    fragmentTransaction.addToBackStack(null);
//                                    fragmentTransaction.commit();
                                } else {
                                    Toast.makeText(getContext(), "Update user thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                    }
                    edtAddress.setEnabled(false);
                    edtPhone.setEnabled(false);
                    edtPass.setEnabled(false);
                    edtUser.setEnabled(false);
                    tvEmail.setEnabled(false);
                    tvName.setEnabled(false);
                    btnEdit.setText("SỬA");

                }

                break;
        }

    }

    private void showGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    private void showCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void uploadImage() {


        File file = new File(realPath);
        String file_path = file.getAbsolutePath();
        Log.d("BBB", "file_pathF: " + file_path);
        String[] arrNameFile = file_path.split("\\.");
        file_path = arrNameFile[0] + System.currentTimeMillis() + "." + arrNameFile[1];
        filename = file_path.substring(file_path.lastIndexOf("/") + 1);
        Log.d("BBB", "file_path: " + file_path);
        Log.d("BBB", "name: " + filename);

        final RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);

        JsonReponse jsonReponse = APIUtils.getJsonReponse();
        Call<String> callback = jsonReponse.postImageUser(body);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String message = response.body();
                    Log.d("BBB", "Message: " + message);
                    assert message != null;
                    if (message.equals("success")) {
                        updateUser();
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("BBB", "Error: " + t.getMessage());
            }
        });


    }

    private void updateUser() {
        image = Constant.BASE_URL + "image/" + filename;
        Log.d("BBB", "urlImage:" + image);
        String name_anh_cu = urlU.substring(urlU.lastIndexOf("/"));
        Log.d("BBB", "nameCu: " + name_anh_cu);
        APIUtils.getJsonReponse().updateUser(id, tvName.getText().toString(), tvEmail.getText().toString()
                , edtUser.getText().toString(), edtPass.getText().toString(), edtPhone.getText().toString(),
                edtAddress.getText().toString(), image).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String mess = response.body();
                Log.d("BBB", "mesasadad: " + mess);
                if (mess.equals("ok")) {
                    Toast.makeText(getContext(), "Update user thành công", Toast.LENGTH_SHORT).show();
//                        Fragment fragment=HomeFragment.newInstance();
//                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                        fragmentTransaction.replace(R.id.layout, fragment);
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.commit();
                } else {
                    Toast.makeText(getContext(), "Update user thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case REQUEST_CODE_GALLERY:
                    InputStream inputStream = null;
                    Uri uri = data.getData();
                    realPath = getRealPathFromURI(uri);
                    try {
                        if (uri != null) {
                            inputStream = getActivity().getContentResolver().openInputStream(uri);
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
                    Uri tempUri = getImageUri(getContext(), photo);

                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    realPath = getRealPathFromURI2(tempUri);
                    break;
            }

        }
    }
}
