package com.tvt.projectcuoikhoa.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.fragment.HomeFragment;
import com.tvt.projectcuoikhoa.model.Cart;
import com.tvt.projectcuoikhoa.utils.NumberFormatCurency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailPhoneActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener {
    private String id, name, price, status, image, evaluation, promo1, promo2, promo3, tag, createAt, gioithieu, manhinh,
            cameraTruoc, cameraSau, ram, storage, cpu, gpu, dlpin, hdh, detail, tenDanhMuc;
    private String urlBanner;
    private List<String> arrString;
    private boolean isMore;
    private int count = 0;
    private int sl;

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
    @BindView(R.id.name_phone)
    TextView nameUrl;
    @BindView(R.id.img_back_phone)
    ImageView imgBack;
    @BindView(R.id.tv_name_toolbar_phone)
    TextView tvNameToolbar;
    @BindView(R.id.cart_phone)
    ImageView imgCart;
    @BindView(R.id.counttxt)
    TextView tvCount;

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
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake_anim_image);
        imgCart.startAnimation(animation);
        sl = ++count;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (HomeFragment.arrCart.size() > 0) {
                    boolean exists = false;
                    for (int i = 0; i < HomeFragment.arrCart.size(); i++) {
                        if (HomeFragment.arrCart.get(i).getId_sp() == Integer.parseInt(id)) {
                            HomeFragment.arrCart.get(i).setSoluong(sl);
                            if (HomeFragment.arrCart.get(i).getSoluong() >= 10) {
                                HomeFragment.arrCart.get(i).setSoluong(10);
                                HomeFragment.arrCart.get(i).setPrice(10 * Integer.parseInt(price));
                                Toast.makeText(DetailPhoneActivity.this, "Mỗi sản phẩm chỉ mua tối đa 10 sản phẩm", Toast.LENGTH_SHORT).show();
                            }
                            HomeFragment.arrCart.get(i).setPrice(HomeFragment.arrCart.get(i).getSoluong() * Integer.parseInt(price));
                            exists = true;
                        }

                    }
                    if (exists == false) {
                        if (MainActivity.key == 1) {
                            HomeFragment.arrCart.add(new Cart(Integer.parseInt(id), image, Integer.parseInt(price), name, Integer.parseInt(MainActivity.id), sl));
                            tvCount.setText(String.valueOf(HomeFragment.arrCart.size()));
                        } else if (MainActivity.key == 2) {
                            HomeFragment.arrCart.add(new Cart(Integer.parseInt(id), image, Integer.parseInt(price), name, 0, sl));
                            tvCount.setText(String.valueOf(HomeFragment.arrCart.size()));
                        } else if (MainActivity.key == 3) {
                            HomeFragment.arrCart.add(new Cart(Integer.parseInt(id), image, Integer.parseInt(price), name, 0, sl));
                            tvCount.setText(String.valueOf(HomeFragment.arrCart.size()));
                        }
                    }
                } else {
                    if (MainActivity.key == 1) {
                        HomeFragment.arrCart.add(new Cart(Integer.parseInt(id), image, Integer.parseInt(price), name, Integer.parseInt(MainActivity.id), sl));
                        tvCount.setText(String.valueOf(HomeFragment.arrCart.size()));
                    } else if (MainActivity.key == 2) {
                        HomeFragment.arrCart.add(new Cart(Integer.parseInt(id), image, Integer.parseInt(price), name, 0, sl));
                        tvCount.setText(String.valueOf(HomeFragment.arrCart.size()));
                    } else if (MainActivity.key == 3) {
                        HomeFragment.arrCart.add(new Cart(Integer.parseInt(id), image, Integer.parseInt(price), name, 0, sl));
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
        tvCount.setText(String.valueOf(HomeFragment.arrCart.size()));
        getIntents();
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
        tvNameDetail.setText(getResources().getString(R.string.detail) + " " + name);
        tvNamePhone.setText(getResources().getString(R.string.detail2) + " " + name);
        tvTenDm.setText(tenDanhMuc + "/");
        nameUrl.setText(name);
        tvNameToolbar.setText(name);
        addWebView();


        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
        sliderLayout.addOnPageChangeListener(this);
        sliderLayout.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));

        for (String url : arrString) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(this);
            defaultSliderView.image(url);
            sliderLayout.addSlider(defaultSliderView);
        }


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

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                tvMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setDimensions(webView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                linearLayout.removeView(tvMore);
                        tvMore.setText("Rút Gọn");
                        isMore = true;
                        tvMore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setDimensions(webView, ViewGroup.LayoutParams.MATCH_PARENT, 600);
//                linearLayout.removeView(tvMore);
                                tvMore.setText("Đọc thêm");
                                isMore = false;
                            }
                        });
                    }
                });
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
//        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
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
        arrString = new ArrayList<>();
        String[] urlBaner = urlBanner.split(";");
        Collections.addAll(arrString, urlBaner);
//        Log.v("UrlBanner", "Size: " + arrString.size());


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        sliderLayout.stopAutoCycle();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

//                Intent intent = new Intent(DetailPhoneActivity.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
