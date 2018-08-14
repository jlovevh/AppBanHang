package com.tvt.projectcuoikhoa.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.Html;
import android.transition.Fade;
import android.transition.Visibility;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.adapter.ListCommentAdapter;
import com.tvt.projectcuoikhoa.adapter.ListRatingAdapter;
import com.tvt.projectcuoikhoa.api.APIUtils;
import com.tvt.projectcuoikhoa.api.PostAndUpdateCartToService;
import com.tvt.projectcuoikhoa.database.ShoppingCartHelper;
import com.tvt.projectcuoikhoa.dialog.CommentDialog;
import com.tvt.projectcuoikhoa.fragment.HomeFragment;
import com.tvt.projectcuoikhoa.model.Cart;
import com.tvt.projectcuoikhoa.model.Comment;
import com.tvt.projectcuoikhoa.model.Phone;
import com.tvt.projectcuoikhoa.model.Rating;
import com.tvt.projectcuoikhoa.utils.NumberFormatCurency;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPhoneActivity extends AppCompatActivity implements View.OnClickListener {
    private String name, price, status, image, evaluation, promo1, promo2, promo3, tag, createAt, gioithieu, manhinh,
            cameraTruoc, cameraSau, ram, storage, cpu, gpu, dlpin, hdh, detail, tenDanhMuc;
    private String urlBanner;
    private List<String> arrString;
    public static String id;
    private int stars;
    private LinearLayout linearLayoutSend, linearLayoutInfor;
    private TextView tvRating, tvEmty, tvSumStar5, tvSumStar4, tvSumStar3, tvSumStar2, tvSumStar1,
            tvSumDanhGia, tv_average_rating, tv_name_rating;
    private ProgressBar progressBar5, progressBar4, progressBar3, progressBar2, progressBar1;
    private RatingBar ratingBar, average_rating;
    private RatingBar ratingBarSum;
    private TextView tvSum;
    private Button btnSenDanhGia;
    private EditText edtEmailInfor, edtHotenInfor, edtPhone, edtNhanXet;
    private List<Rating> arrRating = new ArrayList<>();
    private List<Rating> arrRatingR = new ArrayList<>();
    private List<Comment> arrComment = new ArrayList<>();
    private List<Comment> arrCommentCon = new ArrayList<>();
    private ShoppingCartHelper shoppingCartHelper;
    private ListView listView, listViewComment;
    private ListRatingAdapter adapter;
    private EditText edtComment;
    private Button btnSendComment;
    private ListCommentAdapter adapterComment;


    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_promo1)
    TextView tvPromo1;
    @BindView(R.id.tv_promo2)
    TextView tvPromo2;
    @BindView(R.id.tv_promo3)
    TextView tvPromo3;
    @BindView(R.id.tv_manhinh_phone)
    TextView tvManHinh;
    @BindView(R.id.tv_camera_truoc_phone)
    TextView tvCameraTruoc;
    @BindView(R.id.tv_camera_sau_phone)
    TextView tvCameraSau;
    @BindView(R.id.tv_ram_phone)
    TextView tvRam;
    @BindView(R.id.tv_bnt_phone)
    TextView tvStorage;
    @BindView(R.id.tv_cpu_phone)
    TextView tvCpu;
    @BindView(R.id.tv_gpu_phone)
    TextView tvGpu;
    @BindView(R.id.tv_hdh_phone)
    TextView tvHdh;
    @BindView(R.id.tv_dlpin_phone)
    TextView tvDlPin;
    @BindView(R.id.slider)
    SliderLayout sliderLayout;
    @BindView(R.id.tv_all_cauhinh_phone)
    TextView tvAllCauHinh;
    @BindView(R.id.tv_name_phone_detail)
    TextView tvNameDetail;
    @BindView(R.id.tv_name_phone2)
    TextView tvNamePhone;
    @BindView(R.id.layout)
    LinearLayout linearLayout;
    @BindView(R.id.tv_ten_danh_muc)
    TextView tvTenDm;
    @BindView(R.id.img_back_phone)
    ImageView imgBack;
    @BindView(R.id.tv_name_toolbar_phone)
    TextView tvNameToolbar;
    @BindView(R.id.cart_phone)
    ImageView imgCart;
    @BindView(R.id.counttxt)
    TextView tvCount;
    public static List<Phone> arr = new ArrayList<>();
    @BindView(R.id.btn_send)
    Button btnSend;

    @OnClick(R.id.tv_all_cauhinh_phone)
    void submit() {
        Intent intent = new Intent(DetailPhoneActivity.this, DetailConfigPhoneActivity.class);
        intent.putExtra("cauhinhchitiet", detail);
        startActivity(intent);
    }

    @OnClick(R.id.img_back_phone)
    void back() {
        finish();
    }

    @OnClick(R.id.btn_buy_phone)
    void buyPhone() {

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_number_product, null);
        final Spinner spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.number));
        spinner.setAdapter(arrayAdapter);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!spinner.getSelectedItem().toString().equals("Please select the number of products...")) {

                            addItemToCard(spinner);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void addItemToCard(final Spinner spinner) {
        Animation animation = AnimationUtils.loadAnimation(DetailPhoneActivity.this, R.anim.shake_anim_image);
        imgCart.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (HomeFragment.arrCart.size() > 0) {
                    boolean exists = false;
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    for (int i = 0; i < HomeFragment.arrCart.size(); i++) {
                        if (HomeFragment.arrCart.get(i).getId_sp() == Integer.parseInt(id)) {
                            HomeFragment.arrCart.get(i).setSoluong(sl + HomeFragment.arrCart.get(i).getSoluong());
                            if (HomeFragment.arrCart.get(i).getSoluong() >= 10) {
                                HomeFragment.arrCart.get(i).setSoluong(10);
                                HomeFragment.arrCart.get(i).setPrice(10 * Integer.parseInt(price));
                                Toast.makeText(DetailPhoneActivity.this, "Mỗi sản phẩm chỉ mua tối đa 10 sản phẩm", Toast.LENGTH_SHORT).show();
                            }
                            HomeFragment.arrCart.get(i).setPrice(HomeFragment.arrCart.get(i).getSoluong() * Integer.parseInt(price));
                            exists = true;

                            Log.d("SSSSSSSSSSSSSSSSS", "Phone2: " + HomeFragment.arrCart.get(i).getSoluong());
                        }

                        //update history cart by sqlite
//                        switch (MainActivity.key){
//                            case 1:
//                                shoppingCartHelper.updateColumn(new Cart(Integer.parseInt(id), image, HomeFragment.arrCart.get(i).getPrice(), name, MainActivity.id, HomeFragment.arrCart.get(i).getSoluong()));
//                                break;
//                            case 2:
//                                shoppingCartHelper.updateColumn(new Cart(Integer.parseInt(id), image, HomeFragment.arrCart.get(i).getPrice(), name, MainActivity.idFB, HomeFragment.arrCart.get(i).getSoluong()));
//                                break;
//                            case 3:
//                                shoppingCartHelper.updateColumn(new Cart(Integer.parseInt(id), image, HomeFragment.arrCart.get(i).getPrice(), name, MainActivity.idGG, HomeFragment.arrCart.get(i).getSoluong()));
//                                break;
//                        }

                        PostAndUpdateCartToService.updateCart(HomeFragment.arrCart.get(i).getSoluong(), HomeFragment.arrCart.get(i).getPrice(), HomeFragment.arrCart.get(i).getId_sp());

                    }
                    if (exists == false) {

                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        int giamoi = soluong * Integer.parseInt(price);
                        if (MainActivity.key == 1) {
                            Cart cart = new Cart(Integer.parseInt(id), image, giamoi, name, MainActivity.id, soluong);
                            HomeFragment.arrCart.add(cart);
                            PostAndUpdateCartToService.postCartToService(cart);
                            Log.d("SSSSSSSSSSSSSSSSS", "PHONE3: " + cart.getSoluong());
                            //   shoppingCartHelper.insertCart(cart);
                            tvCount.setText(String.valueOf(HomeFragment.arrCart.size()));
                        } else if (MainActivity.key == 2) {
                            Cart cart = new Cart(Integer.parseInt(id), image, giamoi, name, MainActivity.idFB, soluong);
                            HomeFragment.arrCart.add(cart);
                            //    shoppingCartHelper.insertCart(cart);
                            PostAndUpdateCartToService.postCartToService(cart);
                            tvCount.setText(String.valueOf(HomeFragment.arrCart.size()));
                        } else if (MainActivity.key == 3) {
                            Cart cart = new Cart(Integer.parseInt(id), image, giamoi, name, MainActivity.idGG, soluong);
                            HomeFragment.arrCart.add(cart);
                            //    shoppingCartHelper.insertCart(cart);
                            PostAndUpdateCartToService.postCartToService(cart);
                            tvCount.setText(String.valueOf(HomeFragment.arrCart.size()));
                        }
//
                    }
                } else {
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    int giamoi = sl * Integer.parseInt(price);
                    if (MainActivity.key == 1) {
                        Cart cart = new Cart(Integer.parseInt(id), image, giamoi, name, MainActivity.id, sl);
                        HomeFragment.arrCart.add(cart);
                        PostAndUpdateCartToService.postCartToService(cart);
                        Log.d("SSSSSSSSSSSSSSSSS", "PHONE1: " + cart.getSoluong());
                        //   shoppingCartHelper.insertCart(cart);
                        tvCount.setText(String.valueOf(HomeFragment.arrCart.size()));
                    } else if (MainActivity.key == 2) {
                        Cart cart = new Cart(Integer.parseInt(id), image, giamoi, name, MainActivity.idFB, sl);
                        HomeFragment.arrCart.add(cart);
                        PostAndUpdateCartToService.postCartToService(cart);
                        //        shoppingCartHelper.insertCart(cart);
                        tvCount.setText(String.valueOf(HomeFragment.arrCart.size()));
                    } else if (MainActivity.key == 3) {
                        Cart cart = new Cart(Integer.parseInt(id), image, giamoi, name, MainActivity.idGG, sl);
                        HomeFragment.arrCart.add(cart);
                        PostAndUpdateCartToService.postCartToService(cart);
                        //         shoppingCartHelper.insertCart(cart);
                        tvCount.setText(String.valueOf(HomeFragment.arrCart.size()));
                    }

                }
                Intent intent = new Intent(DetailPhoneActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
            }
        }, 2000);


    }


    @OnClick(R.id.cart_phone)
    void anim() {

        Intent intent = new Intent(DetailPhoneActivity.this, ShoppingCartActivity.class);
        startActivity(intent);

    }


    @SuppressLint({"SetTextI18n", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_phone);
        ButterKnife.bind(this);
        initViews();
        // adapter=new ListRatingAdapter(this,arrRating);
        tvCount.setText(String.valueOf(HomeFragment.arrCart.size()));
        btnSend.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View v) {

                if (btnSend.getText().toString().equals("Gửi đánh giá của bạn")) {
                    linearLayoutSend.setVisibility(View.VISIBLE);
                    tvRating.setVisibility(View.GONE);
                    btnSend.setText("Đóng lại");
                    ratingBar.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                float touchPositionX = event.getX();
                                float width = ratingBar.getWidth();
                                float starsf = (touchPositionX / width) * 5.0f;
                                stars = (int) starsf + 1;
                                ratingBar.setRating(stars);
                                tvRating.setVisibility(View.VISIBLE);
                                linearLayoutInfor.setVisibility(View.VISIBLE);
                                switch (stars) {
                                    case 1:
                                        tvRating.setText("Không thích");
                                        break;
                                    case 2:
                                        tvRating.setText("Tạm được");
                                        break;
                                    case 3:
                                        tvRating.setText("Bình thường");
                                        break;
                                    case 4:
                                        tvRating.setText("Rất tốt");
                                        break;
                                    case 5:
                                        tvRating.setText("Tuyệt vời");
                                        break;

                                }


                                v.setPressed(false);
                            }
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                v.setPressed(true);
                            }

                            if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                                v.setPressed(false);
                            }


                            return true;
                        }
                    });
                } else {
                    linearLayoutSend.setVisibility(View.GONE);
                    btnSend.setText("Gửi đánh giá của bạn");
                    linearLayoutInfor.setVisibility(View.GONE);
                }
            }
        });
        Intent intent = getIntent();
        int idSp = intent.getIntExtra("idsp", 0);

//        if (String.valueOf(idSp).equals(id)){
//            tvName.setText(name);
//            tvCameraSau.setText(cameraSau);
//            tvPrice.setText(NumberFormatCurency.numBerForMat(Integer.parseInt(price)));
//            tvPromo1.setText(promo1);
//            tvPromo2.setText(promo2);
//            tvPromo3.setText(promo3);
//            tvManHinh.setText(manhinh);
//            tvCameraTruoc.setText(cameraTruoc);
//            tvRam.setText(ram);
//            tvStorage.setText(storage);
//            tvCpu.setText(cpu);
//            tvHdh.setText(hdh);
//            tvGpu.setText(gpu);
//            tvDlPin.setText(dlpin);
//            tvNameDetail.setText(getResources().getString(R.string.detail) + " " + name);
//            tvNamePhone.setText(getResources().getString(R.string.detail2) + " " + name);
//            tvTenDm.setText(tenDanhMuc + "/");
//            nameUrl.setText(name);
//            tvNameToolbar.setText(name);
//        }
        // shoppingCartHelper = new ShoppingCartHelper(this);
        tvCount.setText(String.valueOf(HomeFragment.arrCart.size()));
        getIntents();

        getIntentSearch();
        setRating();
        tvName.setText(name);
        tvCameraSau.setText(cameraSau);
        tvPrice.setText(NumberFormatCurency.numBerForMat(Integer.parseInt(price)));
        tvPromo1.setText(promo1);
        tvPromo2.setText(promo2);
        tvPromo3.setText(promo3);
        tvManHinh.setText(manhinh);
        tvCameraTruoc.setText(cameraTruoc);
        tvRam.setText(ram);
        tvStorage.setText(storage);
        tvCpu.setText(cpu);
        tvHdh.setText(hdh);
        tvGpu.setText(gpu);
        tvDlPin.setText(dlpin);
        String nameUper = name.toUpperCase();
        tvNameDetail.setText(getResources().getString(R.string.detail) + " " + nameUper);
        tvNamePhone.setText(nameUper);

        String nameUrl = "<font color='#33b5e5'>" + name + "</font>";
        String ten = "Trang chủ/" + tenDanhMuc + " /";
        tvTenDm.setText(Html.fromHtml(ten + nameUrl));
        tv_name_rating.setText(getResources().getString(R.string.nhanxet) + " " + nameUper);
        tvNameToolbar.setText(name);
        addWebView();


        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
        sliderLayout.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));

        for (String url : arrString) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(this);
            defaultSliderView.image(url);
            sliderLayout.addSlider(defaultSliderView);
        }


    }


    private void getIntentSearch() {

//        Intent intent =getIntent();
//        String idSP=intent.getStringExtra("idSPPhone");
//        Toast.makeText(this, ""+idSP, Toast.LENGTH_SHORT).show();
//        if (Integer.parseInt(id)==Integer.parseInt(idSP)){
//
//        }
    }

    private void setRating() {
        int sumDanhgia = arrRatingR.size();
        int sao5 = 0, sao4 = 0, sao3 = 0, sao2 = 0, sao1 = 0;
        for (int i = 0; i < arrRatingR.size(); i++) {
            if (Integer.parseInt(arrRatingR.get(i).getDanhgia()) == 5) {
                sao5 += 1;
            } else {
                sao5 += 0;
            }


            if (Integer.parseInt(arrRatingR.get(i).getDanhgia()) == 4) {
                sao4 += 1;
            } else {
                sao4 += 0;
            }

            if (Integer.parseInt(arrRatingR.get(i).getDanhgia()) == 3) {
                sao3 += 1;
            } else {
                sao3 += 0;
            }

            if (Integer.parseInt(arrRatingR.get(i).getDanhgia()) == 2) {
                sao2 += 1;
            } else {
                sao2 += 0;
            }

            if (Integer.parseInt(arrRatingR.get(i).getDanhgia()) == 1) {
                sao1 += 1;
            } else {
                sao1 += 0;
            }
        }
        tvSumDanhGia.setText(sumDanhgia + " đánh giá");

        tvSumStar5.setText(sao5 + " đánh giá");
        tvSumStar4.setText(sao4 + " đánh giá");
        tvSumStar3.setText(sao3 + " đánh giá");
        tvSumStar2.setText(sao2 + " đánh giá");
        tvSumStar1.setText(sao1 + " đánh giá");
        double tong = (sao5 * 5) + (sao4 * 4) + (sao3 * 3) + (sao2 * 2) + (sao1);
        double sum = (tong) / (sumDanhgia);
        double a = Math.ceil(sum);
        DecimalFormat df = new DecimalFormat("#.0");
        tv_average_rating.setText(String.valueOf(df.format(a)) + "/5");
        tvSum.setText(sumDanhgia + " đánh giá");
        average_rating.setRating((float) a);
        ratingBarSum.setRating((float) a);
        int progress5 = (int) ((sao5 * 5) / tong * 100);
        int progress4 = (int) ((sao4 * 4) / tong * 100);
        int progress3 = (int) ((sao3 * 3) / tong * 100);
        int progress2 = (int) ((sao2 * 2) / tong * 100);
        int progress1 = (int) ((sao1) / tong * 100);

        progressBar5.setProgress(progress5);
        progressBar4.setProgress(progress4);
        progressBar3.setProgress(progress3);
        progressBar2.setProgress(progress2);
        progressBar1.setProgress(progress1);


    }

    private void initViews() {
        linearLayoutSend = findViewById(R.id.linear_send);
        linearLayoutInfor = findViewById(R.id.linear_infor);
        ratingBar = findViewById(R.id.ratingBar_danh_gia);
        tvRating = findViewById(R.id.tvRating);
        edtEmailInfor = findViewById(R.id.edt_infor_email);
        edtHotenInfor = findViewById(R.id.edt_infor_hoten);
        edtNhanXet = findViewById(R.id.edt_infor_danh_gia);
        edtPhone = findViewById(R.id.edt_infor_sdt);
        btnSenDanhGia = findViewById(R.id.btn_send2);
        tvEmty = findViewById(R.id.tv_emty);
        tvSumStar1 = findViewById(R.id.tv_danhgia_1s);
        tvSumStar2 = findViewById(R.id.tv_danhgia_2s);
        tvSumStar3 = findViewById(R.id.tv_danhgia_3s);
        tvSumStar4 = findViewById(R.id.tv_danhgia_4s);
        tvSumStar5 = findViewById(R.id.tv_danhgia_5s);
        progressBar1 = findViewById(R.id.progress_bar_1s);
        progressBar2 = findViewById(R.id.progress_bar_2s);
        progressBar3 = findViewById(R.id.progress_bar_3s);
        progressBar4 = findViewById(R.id.progress_bar_4s);
        progressBar5 = findViewById(R.id.progress_bar_5s);
        tvSumDanhGia = findViewById(R.id.tv_sum_ela);
        tv_average_rating = findViewById(R.id.tv_average_rating);
        average_rating = findViewById(R.id.average_rating);
        listView = findViewById(R.id.list_rating);
        listViewComment = findViewById(R.id.list_comment);
        edtComment = findViewById(R.id.edt_comment);
        btnSendComment = findViewById(R.id.btn_gui);
        tv_name_rating = findViewById(R.id.tv_name_rating);
        tvSum = findViewById(R.id.sum_danhgia);
        ratingBarSum = findViewById(R.id.sum_rating);
        btnSendComment.setOnClickListener(this);
        btnSenDanhGia.setOnClickListener(this);
    }


    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
    private void addWebView() {

        final WebView webView = new WebView(this);
        webView.setScrollContainer(false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 600);
        webView.setLayoutParams(params);
        final TextView tvMore = new TextView(this);

        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setPluginState(WebSettings.PluginState.ON);

        webView.setWebViewClient(new WebViewClient());
        webView.loadDataWithBaseURL(null, "<style>img{display: inline; height: auto; max-width: 100%;}</style>" + evaluation, "text/html", "charset=utf-8", null);

        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);


        LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvMore.setLayoutParams(rl);
        tvMore.setText("Đọc thêm");
        tvMore.setBackgroundResource(R.color.white);
        tvMore.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
        Drawable img = getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
        tvMore.setPadding(10, 10, 10, 10);
        tvMore.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
        linearLayout.addView(webView);
        linearLayout.addView(tvMore);


        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvMore.getText().equals("Đọc thêm")) {
                    tvMore.setText("Rút Gọn");
                    setDimensions(webView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                } else {
                    tvMore.setText("Đọc thêm");
                    setDimensions(webView, ViewGroup.LayoutParams.MATCH_PARENT, 600);
                }

            }
        });


    }

    private void setDimensions(View view, int width, int height) {
        android.view.ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    private void getIntents() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        id = bundle.getString("id");
        name = bundle.getString("name");
        price = bundle.getString("price");
        status = bundle.getString("status");
        image = bundle.getString("image");
        evaluation = bundle.getString("evaluation");
        promo1 = bundle.getString("promo1");
        promo2 = bundle.getString("promo2");
        promo3 = bundle.getString("promo3");
        tag = bundle.getString("tag");
        createAt = bundle.getString("createAt");
        gioithieu = bundle.getString("gioithieu");
        manhinh = bundle.getString("manhinh");
        cameraTruoc = bundle.getString("cameraT");
        cameraSau = bundle.getString("cameraS");
        ram = bundle.getString("ram");
        storage = bundle.getString("bonhotrong");
        cpu = bundle.getString("cpu");
        gpu = bundle.getString("gpu");
        dlpin = bundle.getString("dlpin");
        hdh = bundle.getString("hdh");
        detail = bundle.getString("detail");
        urlBanner = bundle.getString("urlBanner");
        tenDanhMuc = bundle.getString("tendanhmuc");
//        Toast.makeText(this, ""+tenDanhMuc, Toast.LENGTH_SHORT).show();
        arrComment = bundle.getParcelableArrayList("commentPhone");
        arrString = new ArrayList<>();

        arrRating = bundle.getParcelableArrayList("ratingPhone");
        Log.d("RATINGSIZE", "SIZE: " + arrRating.size());
        if (arrRating != null) {
            getRating(arrRating);
        }

        if (arrComment != null) {
            getComment(arrComment);
        }


        String[] urlBaner = urlBanner.split(";");
        Collections.addAll(arrString, urlBaner);


    }

    private void getComment(List<Comment> arrComment) {
        for (int i = 0; i < arrComment.size(); i++) {
            if (Integer.parseInt(id) == Integer.parseInt(arrComment.get(i).getIdSP())) {
                Comment comment = arrComment.get(i);
                arrCommentCon.add(comment);
            }
        }
        adapterComment = new ListCommentAdapter(this, arrCommentCon);
        listViewComment.setAdapter(adapterComment);
        listViewComment.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        setListViewHeightBasedOnChildren(listViewComment);
    }

    private void getRating(List<Rating> arrRating) {
        for (int i = 0; i < arrRating.size(); i++) {
            if (Integer.parseInt(id) == Integer.parseInt(arrRating.get(i).getIdSP())) {
                Rating rating = arrRating.get(i);
                arrRatingR.add(rating);
            }
        }
        adapter = new ListRatingAdapter(this, arrRatingR);
        listView.setAdapter(adapter);
        listView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        setListViewHeightBasedOnChildren(listView);
    }


    @Override
    protected void onStop() {
        super.onStop();
        sliderLayout.stopAutoCycle();
    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

//                Intent intent = new Intent(DetailPhoneActivity.this, HomeFragment.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void showPopup(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view, Gravity.START);
        popupMenu.inflate(R.menu.menu_share2);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.share_detail:
                        Toast.makeText(DetailPhoneActivity.this, "A", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send2:
                if (edtHotenInfor.getText().toString().isEmpty()) {
                    tvEmty.setText("Vui lòng nhập họ tên");
                } else if (edtNhanXet.getText().toString().isEmpty()) {
                    tvEmty.setText("Vui lòng nhập nội dung đánh giá về sản phẩm");
                } else if (edtEmailInfor.getText().toString().isEmpty()) {
                    tvEmty.setText("Vui lòng nhập email");
                } else if (edtPhone.getText().toString().isEmpty()) {
                    tvEmty.setText("Vui lòng nhập số điện thoại");
                } else {

                    posetRating(id, edtHotenInfor.getText().toString(),
                            edtEmailInfor.getText().toString(), edtPhone.getText().toString(),
                            stars, edtNhanXet.getText().toString());
                    linearLayoutInfor.setVisibility(View.GONE);
                    linearLayoutSend.setVisibility(View.GONE);
                    edtEmailInfor.setText("");
                    edtPhone.setText("");
                    edtNhanXet.setText("");
                    edtHotenInfor.setText("");
                }


                break;

            case R.id.btn_gui:
                if (edtComment.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập nội dung bình luận", Toast.LENGTH_SHORT).show();
                } else {
                    showDialogs(id, edtComment.getText().toString());
                }

                break;
        }
    }

    private void showDialogs(String id, String comment) {

        CommentDialog commentDialog = new CommentDialog(this, id, comment);
        commentDialog.show();
        edtComment.setText("");

    }

    private void posetRating(String idsp, String name_infor, String email, String sdt, int star, String nhanxet) {

        APIUtils.getJsonReponse().postRating(idsp, name_infor, email, sdt, star, nhanxet).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String mess = response.body();
                if (response.isSuccessful()) {
                    if (mess.equals("success")) {
                        Toast.makeText(DetailPhoneActivity.this, "Đánh giá thành công.Vui lòng refresh trang để cập nhật", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailPhoneActivity.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(DetailPhoneActivity.this, "Lỗi!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
