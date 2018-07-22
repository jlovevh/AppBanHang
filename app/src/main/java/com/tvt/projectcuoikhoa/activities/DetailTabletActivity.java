package com.tvt.projectcuoikhoa.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.tvt.projectcuoikhoa.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailTabletActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener {

    private String id, name, price, status, image, evaluation, promo1, promo2, promo3, tag, createAt, gioithieu, manhinh,
            cameraTruoc, cameraSau, ram, storage, cpu, gpu, ketNoi, detail, tenDanhMuc;
    private String urlBanner;
    private List<String> arrString;

    @BindView(R.id.tv_name_tab)
    TextView tvName;
    @BindView(R.id.tv_price_tab)
    TextView tvPrice;
    @BindView(R.id.tv_promo1_tab)
    TextView tvPromo1;
    @BindView(R.id.tv_promo2_tab)
    TextView tvPromo2;
    @BindView(R.id.tv_promo3_tab)
    TextView tvPromo3;
    @BindView(R.id.tv_manhinh_tab)
    TextView tvManHinh;
    @BindView(R.id.tv_camera_truoc_tab)
    TextView tvCameraTruoc;
    @BindView(R.id.tv_camera_sau_tab)
    TextView tvCameraSau;
    @BindView(R.id.tv_ram_tab)
    TextView tvRam;
    @BindView(R.id.tv_bnt_tab)
    TextView tvStorage;
    @BindView(R.id.tv_cpu_tab)
    TextView tvCpu;
    @BindView(R.id.tv_gpu_tab)
    TextView tvGpu;
    @BindView(R.id.tv_ketnoi_tab)
    TextView tvKetNoi;
    @BindView(R.id.slider4)
    SliderLayout sliderLayout;
    @BindView(R.id.custom_indicator4)
    PagerIndicator pagerIndicator;
    @BindView(R.id.tv_all_cauhinh_tab)
    TextView tvAllCauHinh;
    @BindView(R.id.tv_name_tab_detail)
    TextView tvNameDetail;
    @BindView(R.id.tv_name_tablet2)
    TextView tvNamePhone;
    @BindView(R.id.layout3)
    LinearLayout linearLayout;
    @BindView(R.id.tv_ten_danh_muc_tablet)
    TextView tvTenDm;
    @BindView(R.id.name_tablet)
    TextView nameUrl;
    @BindView(R.id.img_back_tablet)
    ImageView imgBack;
    @BindView(R.id.tv_name_toolbar_tablet)
    TextView tvNameToolbar;

    @OnClick(R.id.tv_all_cauhinh_tab)
    void submit() {
        Intent intent = new Intent(DetailTabletActivity.this, DetailConfigTabletActivity.class);

        intent.putExtra("cauhinhchitiet", detail);
        startActivity(intent);
    }

    @OnClick(R.id.img_back_tablet)
    void back() {
        finish();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_tablet);

        ButterKnife.bind(this);

        getIntents();
        tvName.setText(name);
        tvCameraSau.setText(cameraSau);
        tvPrice.setText(price);
        tvPromo1.setText(promo1);
        tvPromo2.setText(promo2);
        tvPromo3.setText(promo3);
        tvManHinh.setText(manhinh);
        tvCameraTruoc.setText(cameraTruoc);
        tvRam.setText(ram);
        tvStorage.setText(storage);
        tvCpu.setText(cpu);
        tvKetNoi.setText(ketNoi);
        tvGpu.setText(gpu);
        tvNameDetail.setText(getResources().getString(R.string.detail) + " " + name);
        tvNamePhone.setText(getResources().getString(R.string.detail2) + " " + name);
        tvTenDm.setText(tenDanhMuc + "/");
        nameUrl.setText(name);
        tvNameToolbar.setText(name);
        addWebView();


        sliderLayout.setPresetTransformer(SliderLayout.Transformer.ZoomIn);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
        sliderLayout.addOnPageChangeListener(this);
        sliderLayout.setCustomIndicator(pagerIndicator);

        for (String url : arrString) {
//            TextSliderView textSliderView = new TextSliderView(this);
//            // initialize a SliderLayout
//            textSliderView
//                    .image(url)
//                    .setOnSliderClickListener(this);

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


        LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvMore.setLayoutParams(rl);
        tvMore.setText("Đọc thêm");
        tvMore.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
        Drawable img = getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
        tvMore.setPadding(10, 10, 10, 10);
        tvMore.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
        linearLayout.addView(webView);
        linearLayout.addView(tvMore);


        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDimensions(webView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                linearLayout.removeView(tvMore);

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
        ketNoi = bundle.getString("ketnoi");

        detail = bundle.getString("detail");
        urlBanner = bundle.getString("urlBanner");
        tenDanhMuc = bundle.getString("tendanhmuc");

        arrString = new ArrayList<>();

        String[] urlBaner = urlBanner.split(";");
        Collections.addAll(arrString, urlBaner);
        Log.v("UrlBanner", "Size: " + arrString.size());


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


}

