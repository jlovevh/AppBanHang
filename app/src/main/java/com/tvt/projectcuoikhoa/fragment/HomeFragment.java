package com.tvt.projectcuoikhoa.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.activities.DetailLaptopActivity;
import com.tvt.projectcuoikhoa.activities.DetailPhoneActivity;
import com.tvt.projectcuoikhoa.activities.DetailTabletActivity;
import com.tvt.projectcuoikhoa.activities.MainActivity;
import com.tvt.projectcuoikhoa.activities.NewsActivity;
import com.tvt.projectcuoikhoa.activities.NewsEvaluationActivity;
import com.tvt.projectcuoikhoa.adapter.RecyclerLaptopNewAdapter;
import com.tvt.projectcuoikhoa.adapter.RecyclerPhoneHotAdapter;
import com.tvt.projectcuoikhoa.adapter.RecyclerTabletNewAdapter;
import com.tvt.projectcuoikhoa.adapter.NewsTechnologyAdapter;
import com.tvt.projectcuoikhoa.api.APIUtils;
import com.tvt.projectcuoikhoa.helper.GridDividerDecoration;
import com.tvt.projectcuoikhoa.helper.ItemClickListener;
import com.tvt.projectcuoikhoa.helper.MyDividerItemDecoration;
import com.tvt.projectcuoikhoa.model.BannerQc;
import com.tvt.projectcuoikhoa.model.Cart;
import com.tvt.projectcuoikhoa.model.Comment;
import com.tvt.projectcuoikhoa.model.LapTop;
import com.tvt.projectcuoikhoa.model.News;
import com.tvt.projectcuoikhoa.model.Phone;
import com.tvt.projectcuoikhoa.model.Rating;
import com.tvt.projectcuoikhoa.model.Tablet;
import com.tvt.projectcuoikhoa.utils.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment implements ItemClickListener, RecyclerLaptopNewAdapter.ItemClickListenerLapTop, RecyclerTabletNewAdapter.ItemClickListenerTablet, BaseSliderView.OnSliderClickListener {
    //list banner
    private List<BannerQc> bannerQcList = new ArrayList<>();
    private SliderLayout sliderLayout;
    private PagerIndicator pagerIndicator;
    private SwipeRefreshLayout swipeRefreshLayout;

    //list phone
    private RecyclerPhoneHotAdapter adapterPhone;
    private List<Phone> phoneHotList;
    private RecyclerView recyclerView;
    private TextView tvMore;

    //list laptop
    private RecyclerLaptopNewAdapter adapterLaptop;
    private List<LapTop> arrLaptop;
    private RecyclerView recyclerViewLaptop;
    private TextView tvAll;

    //list tablet
    private RecyclerTabletNewAdapter adapterTablet;
    private List<Tablet> arrTablet;
    private RecyclerView recyclerViewTablet;
    private TextView tvMoreTablet;

    // list news
    private ListView listView;
    private List<News> arrNews;
    private NewsTechnologyAdapter adapterTinCongNghe;
    private TextView tvMoreTinTuc;
    public static List<Cart> arrCart;


    public static List<Rating> arrRating = new ArrayList<>();

    public static List<Rating> arrRatingLaptop = new ArrayList<>();

    public static List<Rating> arrRatingTablet = new ArrayList<>();


    public static List<Comment> arrComment = new ArrayList<>();
    public static List<Comment> arrCommentLap = new ArrayList<>();
    public static List<Comment> arrCommentTablet = new ArrayList<>();

    private static HomeFragment homeFragment;
    private HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);

        initViews(view);
        getPhoneHot();
        getLaptopNew();
        getTabletNews();
        getTinCongNghe();
        getBanner();
        getRatingLaptop();
        getRatingPhone();
        getRatingTablet();

        getCommentPhone();
        getCommentLaptop();
        getCommentTablet();



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrLaptop.clear();
                arrTablet.clear();
                arrNews.clear();
                phoneHotList.clear();
                getPhoneHot();
                getLaptopNew();
                getTabletNews();
                getTinCongNghe();
                getRatingLaptop();
                getRatingPhone();
                getRatingTablet();

            }
        });

        return view;
    }

    private void getCommentPhone() {
        APIUtils.getJsonReponse().getCommentPhone().enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                arrComment = response.body();
                Log.d("SIZESAS", "SIZECOMMENT: " + arrRating.size());
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });
    }

    private void getCommentLaptop() {
        APIUtils.getJsonReponse().getCommentLaptop().enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                arrCommentLap = response.body();
                Log.d("SIZESAS", "SIZECOMMENT: " + arrRating.size());
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });
    }

    private void getCommentTablet() {
        APIUtils.getJsonReponse().getCommentTablet().enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                arrCommentTablet = response.body();
                Log.d("SIZESAS", "SIZECOMMENT: " + arrRating.size());
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });
    }


    private void getBanner() {
        APIUtils.getJsonReponse().getAllBanner().enqueue(new Callback<List<BannerQc>>() {
            @Override
            public void onResponse(@NonNull Call<List<BannerQc>> call, @NonNull Response<List<BannerQc>> response) {
//                Log.d(Constant.TAG, response.toString());
                List<BannerQc> arr = response.body();

                if (arr != null) {
                    bannerQcList.addAll(arr);
                }

                for (BannerQc bannerQc : bannerQcList) {
                    TextSliderView textSliderView = new TextSliderView(getContext());
                    // initialize a SliderLayout
                    textSliderView
                            .description(bannerQc.getName())
                            .image(bannerQc.getUrlBanner())
                            .setOnSliderClickListener(HomeFragment.this);
                    sliderLayout.addSlider(textSliderView);

                }

            }

            @Override
            public void onFailure(@NonNull Call<List<BannerQc>> call, @NonNull Throwable t) {
                Log.d(Constant.TAG, "error" + t.getMessage());
            }
        });

    }

    private void getTinCongNghe() {
        APIUtils.getJsonReponse().getTinCongNghe().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                swipeRefreshLayout.setRefreshing(false);
                arrNews = response.body();
                adapterTinCongNghe.setData(arrNews);

            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Log.e(Constant.TAG, "error: " + t.getMessage());
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = arrNews.get(position);
                if (Integer.parseInt(news.getIdDM()) == 6) {
                    Intent intent = new Intent(getContext(), NewsEvaluationActivity.class);
                    intent.putExtra("title", news.getTieude());
                    intent.putExtra("baiviet", news.getBaiviet());
                    intent.putExtra("create", news.getCreateAt());
                    intent.putExtra("tendanhmuc", news.getTendanhmuctintuc());
                    intent.putExtra("image", news.getAnhtieude());
                    getContext().startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), NewsActivity.class);
                    intent.putExtra("title", news.getTieude());
                    intent.putExtra("baiviet", news.getBaiviet());
                    intent.putExtra("create", news.getCreateAt());
                    intent.putExtra("tendanhmuc", news.getTendanhmuctintuc());
                    intent.putExtra("image", news.getAnhtieude());
                    getContext().startActivity(intent);
                }
            }
        });

        tvMoreTinTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = NewsFragment.newInstance();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.layout, fragment);
                fragmentTransaction.addToBackStack(null);
                MainActivity.bottomNavigationView.getMenu().getItem(1).setChecked(true);
                fragmentTransaction.commit();
            }
        });
    }

    private void initViews(View view) {
        if (arrCart != null) {
        } else {
            arrCart = new ArrayList<>();
        }
        recyclerView = view.findViewById(R.id.recyclerPhoneHOt);
        tvMore = view.findViewById(R.id.tv_more);
        recyclerViewTablet = view.findViewById(R.id.recyclerTabletMoiNhat);
        recyclerViewLaptop = view.findViewById(R.id.recyclerLaptopNew);
        tvMoreTablet = view.findViewById(R.id.tv_more_tablet);
        tvAll = view.findViewById(R.id.tv_all_laptop);
        tvMoreTablet = view.findViewById(R.id.tv_more_tablet);
        listView = view.findViewById(R.id.list_item_tin_cong_nghe);
        tvMoreTinTuc = view.findViewById(R.id.tv_more_tin_tuc);
        sliderLayout = view.findViewById(R.id.slider3);
        pagerIndicator = view.findViewById(R.id.custom_indicator3);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        arrNews = new ArrayList<>();
        arrTablet = new ArrayList<>();
        arrLaptop = new ArrayList<>();
        phoneHotList = new ArrayList<>();

        adapterPhone = new RecyclerPhoneHotAdapter(getActivity(), phoneHotList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new GridDividerDecoration(getActivity()));


        adapterLaptop = new RecyclerLaptopNewAdapter(getContext(), arrLaptop);
        recyclerViewLaptop.setHasFixedSize(true);
        recyclerViewLaptop.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewLaptop.addItemDecoration(new android.support.v7.widget.DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerViewLaptop.setAdapter(adapterLaptop);

        adapterTablet = new RecyclerTabletNewAdapter(getContext(), arrTablet);
        recyclerViewTablet.setHasFixedSize(true);
        recyclerViewTablet.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTablet.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.HORIZONTAL, 2));
        recyclerViewTablet.setAdapter(adapterTablet);

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Tablet);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(8000);
        sliderLayout.setCustomIndicator(pagerIndicator);


        adapterTinCongNghe = new NewsTechnologyAdapter(getContext(), arrNews);
        listView.setAdapter(adapterTinCongNghe);
        adapterPhone.setItemClickListener(this);
        adapterTablet.setItemClickListener(this);
        adapterLaptop.setItemClickListener(this);


    }


    private void getTabletNews() {
        APIUtils.getJsonReponse().getTabletNew().enqueue(new Callback<List<Tablet>>() {
            @Override
            public void onResponse(Call<List<Tablet>> call, Response<List<Tablet>> response) {
                swipeRefreshLayout.setRefreshing(false);
                arrTablet = response.body();
                adapterTablet.setData(arrTablet);
            }

            @Override
            public void onFailure(Call<List<Tablet>> call, Throwable t) {
                Log.e(Constant.TAG, "error" + t.getMessage());
            }
        });


        tvMoreTablet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = TabletFragment.newInstance();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.layout, fragment);
                fragmentTransaction.addToBackStack(null);
                MainActivity.bottomNavigationView.getMenu().getItem(1).setChecked(true);
                fragmentTransaction.commit();
            }
        });
    }

    private void getLaptopNew() {
        APIUtils.getJsonReponse().getLaptopNew().enqueue(new Callback<List<LapTop>>() {
            @Override
            public void onResponse(Call<List<LapTop>> call, Response<List<LapTop>> response) {
                swipeRefreshLayout.setRefreshing(false);
                arrLaptop = response.body();
                adapterLaptop.setData(arrLaptop);
            }

            @Override
            public void onFailure(Call<List<LapTop>> call, Throwable t) {
                Log.d(Constant.TAG, "Error" + call.toString());
            }
        });


        tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = LapTopFragment.newInstance();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.layout, fragment);
                fragmentTransaction.addToBackStack(null);
                MainActivity.bottomNavigationView.getMenu().getItem(1).setChecked(true);
                fragmentTransaction.commit();
            }
        });

    }


    private void getPhoneHot() {

        APIUtils.getJsonReponse().getPhoneHot().enqueue(new Callback<List<Phone>>() {
            @Override
            public void onResponse(Call<List<Phone>> call, Response<List<Phone>> response) {
                swipeRefreshLayout.setRefreshing(false);
                phoneHotList = response.body();
//                Log.d("Phone", "Size " + phoneHotList.size());
                adapterPhone.setData(phoneHotList);

            }

            @Override
            public void onFailure(Call<List<Phone>> call, Throwable t) {
                Log.d(Constant.TAG, "Error" + call.toString());
            }
        });
        recyclerView.setAdapter(adapterPhone);
        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = PhoneFragment.newInstance();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.layout, fragment);
                fragmentTransaction.addToBackStack(null);
                MainActivity.bottomNavigationView.getMenu().getItem(1).setChecked(true);
                fragmentTransaction.commit();
            }
        });

    }

    private void getRatingPhone() {
        APIUtils.getJsonReponse().getRatingPhone().enqueue(new Callback<List<Rating>>() {
            @Override
            public void onResponse(Call<List<Rating>> call, Response<List<Rating>> response) {
                arrRating = response.body();
                Log.d("SIZESAS", "SIZE: " + arrRating.size());
            }

            @Override
            public void onFailure(Call<List<Rating>> call, Throwable t) {

            }
        });
    }

    private void getRatingTablet() {
        APIUtils.getJsonReponse().getRatingTablet().enqueue(new Callback<List<Rating>>() {
            @Override
            public void onResponse(Call<List<Rating>> call, Response<List<Rating>> response) {
                arrRatingTablet = response.body();
                Log.d("SIZESAS", "SIZE: " + arrRatingTablet.size());

            }

            @Override
            public void onFailure(Call<List<Rating>> call, Throwable t) {

            }
        });
    }

    private void getRatingLaptop() {
        APIUtils.getJsonReponse().getRatingLaptop().enqueue(new Callback<List<Rating>>() {
            @Override
            public void onResponse(Call<List<Rating>> call, Response<List<Rating>> response) {
                arrRatingLaptop = response.body();
                Log.d("SIZESAS", "SIZE: " + arrRatingLaptop.size());

            }

            @Override
            public void onFailure(Call<List<Rating>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {


        Rating rating;
        Intent intent = new Intent(getContext(), DetailPhoneActivity.class);
        Bundle bundle = new Bundle();
        Phone phone = phoneHotList.get(position);
        bundle.putString("id", phone.getId());
        bundle.putString("name", phone.getName());
        bundle.putString("price", phone.getPrice());
        bundle.putString("status", phone.getStatus());
        bundle.putString("image", phone.getImage());
        bundle.putString("evaluation", phone.getEvaluation());
        bundle.putString("promo1", phone.getPromo1());
        bundle.putString("promo2", phone.getPromo2());
        bundle.putString("promo3", phone.getPromo3());
        bundle.putString("tag", phone.getTag());
        bundle.putString("createAt", phone.getCreateAt());
        bundle.putString("gioithieu", phone.getGioithieu());
        bundle.putString("manhinh", phone.getManhinh());
        bundle.putString("cameraT", phone.getCameraTruoc());
        bundle.putString("cameraS", phone.getCameraSau());
        bundle.putString("ram", phone.getRam());
        bundle.putString("bonhotrong", phone.getBonhotrong());
        bundle.putString("cpu", phone.getCpu());
        bundle.putString("gpu", phone.getGpu());
        bundle.putString("dlpin", phone.getDungluongpin());
        bundle.putString("hdh", phone.getHedieuhanh());
        bundle.putString("detail", phone.getChitietcauhinh());
        bundle.putString("urlBanner", phone.getUrlBanner());
        bundle.putString("tendanhmuc", phone.getTendanhmuc());

        bundle.putParcelableArrayList("ratingPhone", (ArrayList<? extends Parcelable>) arrRating);
        bundle.putParcelableArrayList("commentPhone", (ArrayList<? extends Parcelable>) arrComment);
        intent.putExtra("bundle", bundle);
//        getActivity().startActivity(intent,
//                ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
        getActivity().startActivity(intent);


    }

    @Override
    public void onItemClickLaptop(View view, int position) {
        Rating rating;


        Intent intent = new Intent(getContext(), DetailLaptopActivity.class);
        Bundle bundle = new Bundle();
        LapTop lapTop = arrLaptop.get(position);
        bundle.putString("id", lapTop.getId());
        bundle.putString("name", lapTop.getName());
        bundle.putString("price", lapTop.getPrice());
        bundle.putString("status", lapTop.getStatus());
        bundle.putString("image", lapTop.getImage());
        bundle.putString("evaluation", lapTop.getEvaluation());
        bundle.putString("promo1", lapTop.getPromo1());
        bundle.putString("promo2", lapTop.getPromo2());
        bundle.putString("promo3", lapTop.getPromo3());
        bundle.putString("tag", lapTop.getTag());
        bundle.putString("createAt", lapTop.getCreateAt());
        bundle.putString("gioithieu", lapTop.getGioithieu());
        bundle.putString("manhinh", lapTop.getManhinh());
        bundle.putString("ocung", lapTop.getOcung());
        bundle.putString("dohoa", lapTop.getDohoa());
        bundle.putString("ram", lapTop.getRam());
        bundle.putString("ketnoi", lapTop.getKetnoi());
        bundle.putString("cpu", lapTop.getCpu());
        bundle.putString("tl", lapTop.getTrongluong());
        bundle.putString("hdh", lapTop.getHedieuhanh());
        bundle.putString("detail", lapTop.getChitietcauhinh());
        bundle.putString("urlBanner", lapTop.getUrlBanner());
        bundle.putString("tendanhmuc", lapTop.getTendanhmuc());

        bundle.putParcelableArrayList("ratingLaptop", (ArrayList<? extends Parcelable>) arrRatingLaptop);
        bundle.putParcelableArrayList("commentlap", (ArrayList<? extends Parcelable>) arrCommentLap);
        intent.putExtra("bundle", bundle);
        getActivity().startActivity(intent);
    }

    @Override
    public void onItemClickTablet(View view, int position) {
        Rating rating;


        Intent intent = new Intent(getContext(), DetailTabletActivity.class);
        Bundle bundle = new Bundle();
        Tablet tablet = arrTablet.get(position);
        bundle.putString("id", tablet.getId());
        bundle.putString("name", tablet.getName());
        bundle.putString("price", tablet.getPrice());
        bundle.putString("status", tablet.getStatus());
        bundle.putString("image", tablet.getImage());
        bundle.putString("evaluation", tablet.getEvaluation());
        bundle.putString("promo1", tablet.getPromo1());
        bundle.putString("promo2", tablet.getPromo2());
        bundle.putString("promo3", tablet.getPromo3());
        bundle.putString("tag", tablet.getTag());
        bundle.putString("createAt", tablet.getCreateAt());
        bundle.putString("gioithieu", tablet.getGioithieu());
        bundle.putString("manhinh", tablet.getManhinh());
        bundle.putString("cameraT", tablet.getCameraTruoc());
        bundle.putString("cameraS", tablet.getCameraSau());
        bundle.putString("ram", tablet.getRam());
        bundle.putString("bonhotrong", tablet.getBonhotrong());
        bundle.putString("cpu", tablet.getCpu());
        bundle.putString("gpu", tablet.getGpu());
        bundle.putString("ketnoi", tablet.getKetnoi());
        bundle.putString("detail", tablet.getChitietcauhinh());
        bundle.putString("urlBanner", tablet.getUrlBanner());
        bundle.putString("tendanhmuc", tablet.getTendanhmuc());

        bundle.putParcelableArrayList("ratingTablet", (ArrayList<? extends Parcelable>) arrRatingTablet);
        bundle.putParcelableArrayList("commentTablet", (ArrayList<? extends Parcelable>) arrCommentTablet);

        intent.putExtra("bundle", bundle);
        getActivity().startActivity(intent);
    }


    @Override
    public void onStop() {
        super.onStop();
        sliderLayout.stopAutoCycle();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
