package com.tvt.projectcuoikhoa.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
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
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailLaptopActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener {

    private String id, name, price, status, image, evaluation, promo1, promo2, promo3, tag, createAt, gioithieu, manhinh,
            oCung, ram, cardManHinh, cpu, congKetNoi, trongLuong, hdh, detail, tenDanhMuc, urlBanner;

    private List<String> arrString;
    private int count = 0;
    @BindView(R.id.tv_name_lap)
    TextView tvName;
    @BindView(R.id.tv_price_lap)
    TextView tvPrice;
    @BindView(R.id.tv_promo1_lap)
    TextView tvPromo1;
    @BindView(R.id.tv_promo2_lap)
    TextView tvPromo2;
    @BindView(R.id.tv_promo3_lap)
    TextView tvPromo3;
    @BindView(R.id.tv_manhinh_laptop2)
    TextView tvManHinh;
    @BindView(R.id.tv_ocung_laptop)
    TextView tvOcung;
    @BindView(R.id.tv_dohoa)
    TextView tvDoHoa;
    @BindView(R.id.tv_ram_lap)
    TextView tvRam;
    @BindView(R.id.tv_ketnoi)
    TextView tvKetNoi;
    @BindView(R.id.tv_cpu_lap)
    TextView tvCpu;
    @BindView(R.id.tv_hdh_lap)
    TextView tvHdh;
    @BindView(R.id.tv_tl_lap)
    TextView tvTrongLuong;
    @BindView(R.id.tv_name_laptop_detail)
    TextView tvNameDetail;
    @BindView(R.id.tv_name_laptop2)
    TextView tvNamePhone;
    @BindView(R.id.tv_ten_danh_muc_lap)
    TextView tvTenDm;
    @BindView(R.id.name_lap)
    TextView nameUrl;
    @BindView(R.id.tv_name_toolbar)
    TextView nameToolbar;
    @BindView(R.id.slider2)
    SliderLayout sliderLayout;
    @BindView(R.id.custom_indicator2)
    PagerIndicator pagerIndicator;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_all_cauhinh_lap)
    TextView tvMore;
    @BindView(R.id.layout2)
    LinearLayout linearLayout;
    @BindView(R.id.cart_lap)
    ImageView imgCart;
    @BindView(R.id.counttxt_lap)
    TextView tvCount;
    @BindView(R.id.btn_buy_lap)
    Button btnBuy;



    @OnClick(R.id.img_back)
    void onClick() {
        finish();
    }

    @OnClick(R.id.cart_lap)
    void gotoCart() {
        Intent intent = new Intent(DetailLaptopActivity.this, ShoppingCartActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.tv_all_cauhinh_lap)
    void loadMore() {
        Intent intent = new Intent(DetailLaptopActivity.this, DetailConfigLaptopActivity.class);

        intent.putExtra("cauhinhchitiet", detail);
        startActivity(intent);
    }

    @OnClick(R.id.btn_buy_lap)
    void buyLaptop() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake_anim_image);
        imgCart.startAnimation(animation);
        final int sl = ++count;
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
                                Toast.makeText(DetailLaptopActivity.this, "Mỗi sản phẩm chỉ mua tối đa 10 sản phẩm", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(DetailLaptopActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
            }
        }, 2000);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laptop);
        ButterKnife.bind(this);
        tvCount.setText(String.valueOf(HomeFragment.arrCart.size()));
        getIntents();

        tvName.setText(name);
        tvDoHoa.setText(cardManHinh);
        tvPrice.setText(NumberFormatCurency.numBerForMat(Integer.parseInt(price)));
        tvPromo1.setText(promo1);
        tvPromo2.setText(promo2);
        tvPromo3.setText(promo3);
        tvManHinh.setText(manhinh);
        tvOcung.setText(oCung);
        tvRam.setText(ram);
        tvTrongLuong.setText(trongLuong);
        tvCpu.setText(cpu);
        tvHdh.setText(hdh);
        tvKetNoi.setText(congKetNoi);
        tvNameDetail.setText(getResources().getString(R.string.detail) + " " + name);
        tvNamePhone.setText(getResources().getString(R.string.detail2) + " " + name);
        tvTenDm.setText(tenDanhMuc + "/");
        nameUrl.setText(name);
        nameToolbar.setText(name);


        sliderLayout.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
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

        addWebView();
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
        oCung = bundle.getString("ocung");
        cardManHinh = bundle.getString("dohoa");
        ram = bundle.getString("ram");
        congKetNoi = bundle.getString("ketnoi");
        cpu = bundle.getString("cpu");
        trongLuong = bundle.getString("tl");
        hdh = bundle.getString("hdh");
        detail = bundle.getString("detail");
        urlBanner = bundle.getString("urlBanner");
        tenDanhMuc = bundle.getString("tendanhmuc");

        arrString = new ArrayList<>();

        String[] urlBaner = urlBanner.split(";");
        Collections.addAll(arrString, urlBaner);
        Log.v("UrlBanner", "Size: " + arrString.size());
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
                        linearLayout.removeView(tvMore);
//                        tvMore.setText("Rút Gọn");
//                        tvMore.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                setDimensions(webView, ViewGroup.LayoutParams.MATCH_PARENT, 600);
////                linearLayout.removeView(tvMore);
//                                tvMore.setText("Đọc thêm");
//                            }
//                        });
                    }
                });
            }
        });


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

    private void setDimensions(View view, int width, int height) {
        android.view.ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sliderLayout.stopAutoCycle();
    }
}
