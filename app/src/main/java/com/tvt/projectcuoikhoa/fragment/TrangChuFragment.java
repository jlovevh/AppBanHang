package com.tvt.projectcuoikhoa.fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.activities.ChiTietDienThoaiActivity;
import com.tvt.projectcuoikhoa.activities.ChiTietLaptopActivity;
import com.tvt.projectcuoikhoa.activities.ChiTietTabletActivity;
import com.tvt.projectcuoikhoa.activities.MainActivity;
import com.tvt.projectcuoikhoa.activities.TinTucActivity;
import com.tvt.projectcuoikhoa.activities.TinTucDanhGiaActivity;
import com.tvt.projectcuoikhoa.adapter.BannerQcAdapter;
import com.tvt.projectcuoikhoa.adapter.RecyclerLaptopNewAdapter;
import com.tvt.projectcuoikhoa.adapter.RecyclerPhoneHotAdapter;
import com.tvt.projectcuoikhoa.adapter.RecyclerTabletAdapter;
import com.tvt.projectcuoikhoa.adapter.RecyclerTabletNewAdapter;
import com.tvt.projectcuoikhoa.adapter.TinCongNgheAdapter;
import com.tvt.projectcuoikhoa.adapter.ViewPagerAdapter;
import com.tvt.projectcuoikhoa.api.APIUtils;
import com.tvt.projectcuoikhoa.helper.GridDividerDecoration;
import com.tvt.projectcuoikhoa.helper.ItemClickListener;
import com.tvt.projectcuoikhoa.helper.MyDividerItemDecoration;
import com.tvt.projectcuoikhoa.model.BannerQc;
import com.tvt.projectcuoikhoa.model.LaptopNew;
import com.tvt.projectcuoikhoa.model.PhoneHot;
import com.tvt.projectcuoikhoa.model.Tablet;
import com.tvt.projectcuoikhoa.model.TinCongNghe;
import com.tvt.projectcuoikhoa.model.TinTuc;
import com.tvt.projectcuoikhoa.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class TrangChuFragment extends Fragment implements ItemClickListener, RecyclerLaptopNewAdapter.ItemClickListenerLapTop, RecyclerTabletNewAdapter.ItemClickListenerTablet {

    private BannerQcAdapter adapter;
    private List<BannerQc> bannerQcList;
    private ViewPager viewpager;
    private CircleIndicator indicator;


    private RecyclerPhoneHotAdapter adapterPhone;
    private List<PhoneHot> phoneHotList;
    private RecyclerView recyclerView;
    private TextView tvMore;

    private RecyclerLaptopNewAdapter adapterLaptop;
    private List<LaptopNew> arrLaptop;
    private RecyclerView recyclerViewLaptop;
    private TextView tvAll;


    private RecyclerTabletNewAdapter adapterTablet;
    private List<Tablet> arrTablet;
    private RecyclerView recyclerViewTablet;
    private TextView tvMoreTablet;


    private ListView listView;
    private List<TinCongNghe> arrTinTuc;
    private TinCongNgheAdapter adapterTinCongNghe;
    private TextView tvMoreTinTuc;

    private ProgressDialog dialog;

    public static TrangChuFragment newInstance() {
        return new TrangChuFragment();
    }

    private TrangChuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);
        dialog = new ProgressDialog(getContext(), R.style.AppCompatAlertDialogStyle);
        dialog.setMessage("Loading...");
        dialog.show();
        initViews(view);
        bannerQcList = new ArrayList<>();
        adapter = new BannerQcAdapter(getContext(), bannerQcList);
        viewpager.setAdapter(adapter);
        indicator.setViewPager(viewpager);
        adapter.registerDataSetObserver(indicator.getDataSetObserver());
        getViewPager();
        getPhoneHot();
        getLaptopNew();
        getTabletNews();
        getTinCongNghe();

        adapterPhone.setItemClickListener(this);
        adapterTablet.setItemClickListener(this);
        adapterLaptop.setItemClickListener(this);

        return view;
    }

    private void getTinCongNghe() {
        arrTinTuc = new ArrayList<>();
        adapterTinCongNghe = new TinCongNgheAdapter(getContext(), arrTinTuc);

        APIUtils.getJsonReponse().getTinCongNghe().enqueue(new Callback<List<TinCongNghe>>() {
            @Override
            public void onResponse(Call<List<TinCongNghe>> call, Response<List<TinCongNghe>> response) {
                dialog.dismiss();
                arrTinTuc = response.body();
                Log.d(Constant.TAG, "Size: " + arrTinTuc.size());
                adapterTinCongNghe.setData(arrTinTuc);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapterTinCongNghe);
            }

            @Override
            public void onFailure(Call<List<TinCongNghe>> call, Throwable t) {
                Log.e(Constant.TAG, "error: " + t.getMessage());
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TinCongNghe tinTuc = arrTinTuc.get(position);
                if (tinTuc.getDmtt_id() == 6) {
                    Intent intent = new Intent(getContext(), TinTucDanhGiaActivity.class);
                    intent.putExtra("title", tinTuc.getTieude());
                    intent.putExtra("baiviet", tinTuc.getBaiviet());
                    intent.putExtra("create", tinTuc.getCreateAt());
                    getContext().startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), TinTucActivity.class);
                    intent.putExtra("title", tinTuc.getTieude());
                    intent.putExtra("baiviet", tinTuc.getBaiviet());
                    intent.putExtra("create", tinTuc.getCreateAt());
                    getContext().startActivity(intent);
                }
            }
        });

        tvMoreTinTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = TinTucFragment.newInstance();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.layout, fragment);
                fragmentTransaction.addToBackStack(null);
                MainActivity.bottomNavigationView.getMenu().getItem(1).setChecked(true);
                fragmentTransaction.commit();
            }
        });
    }

    private void initViews(View view) {
        viewpager = (ViewPager) view.findViewById(R.id.view_pager3);
        indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        recyclerView = view.findViewById(R.id.recyclerPhoneHOt);
        tvMore = view.findViewById(R.id.tv_more);
        recyclerViewTablet = view.findViewById(R.id.recyclerTabletMoiNhat);
        recyclerViewLaptop = view.findViewById(R.id.recyclerLaptopNew);
        tvMoreTablet = view.findViewById(R.id.tv_more_tablet);
        tvAll = view.findViewById(R.id.tv_all_laptop);
        tvMoreTablet = view.findViewById(R.id.tv_more_tablet);
        listView = view.findViewById(R.id.list_item_tin_cong_nghe);
        tvMoreTinTuc = view.findViewById(R.id.tv_more_tin_tuc);


    }

    private void getViewPager() {
        APIUtils.getJsonReponse().getAllBanner().enqueue(new Callback<List<BannerQc>>() {
            @Override
            public void onResponse(@NonNull Call<List<BannerQc>> call, @NonNull Response<List<BannerQc>> response) {
//                Log.d(Constant.TAG, response.toString());
                dialog.dismiss();
                bannerQcList.clear();
                bannerQcList = response.body();
                adapter.setData(bannerQcList);
                adapter.notifyDataSetChanged();

//                Log.d(Constant.TAG, "Size: " + bannerQcList.size());

            }

            @Override
            public void onFailure(@NonNull Call<List<BannerQc>> call, @NonNull Throwable t) {
                Log.d(Constant.TAG, t.getMessage());
            }
        });
    }

    private void getTabletNews() {
        arrTablet = new ArrayList<>();
        recyclerViewTablet.setHasFixedSize(true);
        recyclerViewTablet.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTablet.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.HORIZONTAL, 2));
        adapterTablet = new RecyclerTabletNewAdapter(getContext(), arrTablet);
        APIUtils.getJsonReponse().getTabletNew().enqueue(new Callback<List<Tablet>>() {
            @Override
            public void onResponse(Call<List<Tablet>> call, Response<List<Tablet>> response) {
                dialog.dismiss();
                arrTablet.clear();
                arrTablet = response.body();
                adapterTablet.setData(arrTablet);
            }

            @Override
            public void onFailure(Call<List<Tablet>> call, Throwable t) {
                Log.e(Constant.TAG, "error" + t.getMessage());
            }
        });
        recyclerViewTablet.setAdapter(adapterTablet);

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
        arrLaptop = new ArrayList<>();
        recyclerViewLaptop.setHasFixedSize(true);
        recyclerViewLaptop.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewLaptop.addItemDecoration(new android.support.v7.widget.DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        adapterLaptop = new RecyclerLaptopNewAdapter(getContext(), arrLaptop);
        APIUtils.getJsonReponse().getLaptopNew().enqueue(new Callback<List<LaptopNew>>() {
            @Override
            public void onResponse(Call<List<LaptopNew>> call, Response<List<LaptopNew>> response) {
                dialog.dismiss();
                arrLaptop.clear();
                arrLaptop = response.body();
                adapterLaptop.setData(arrLaptop);
            }

            @Override
            public void onFailure(Call<List<LaptopNew>> call, Throwable t) {
                Log.d(Constant.TAG, "Error" + call.toString());
            }
        });

        recyclerViewLaptop.setAdapter(adapterLaptop);
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
        phoneHotList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new GridDividerDecoration(getActivity()));
        adapterPhone = new RecyclerPhoneHotAdapter(getContext(), phoneHotList);
        APIUtils.getJsonReponse().getPhoneHot().enqueue(new Callback<List<PhoneHot>>() {
            @Override
            public void onResponse(Call<List<PhoneHot>> call, Response<List<PhoneHot>> response) {
                dialog.dismiss();
                phoneHotList = response.body();
                Log.d("Phone", "Size " + phoneHotList.size());
                phoneHotList.remove(5);
                phoneHotList.remove(4);
                adapterPhone.setData(phoneHotList);
                ;
            }

            @Override
            public void onFailure(Call<List<PhoneHot>> call, Throwable t) {
                Log.d(Constant.TAG, "Error" + call.toString());
            }
        });


        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APIUtils.getJsonReponse().getPhoneHot().enqueue(new Callback<List<PhoneHot>>() {
                    @Override
                    public void onResponse(Call<List<PhoneHot>> call, Response<List<PhoneHot>> response) {

                        List<PhoneHot> arr = response.body();
                        adapterPhone.setData(arr);
                        adapterPhone.notifyDataSetChanged();
                        tvMore.setText("Xem tất cả");
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
                        ;
                    }

                    @Override
                    public void onFailure(Call<List<PhoneHot>> call, Throwable t) {
                        Log.d(Constant.TAG, "Error" + call.toString());
                    }
                });
            }
        });

        recyclerView.setAdapter(adapterPhone);
    }

    @Override
    public void onItemClick(View view, int position) {

        Intent intent = new Intent(getContext(), ChiTietDienThoaiActivity.class);

        getContext().startActivity(intent);


    }

    @Override
    public void onItemClickLaptop(View view, int position) {
        Intent intent = new Intent(getContext(), ChiTietLaptopActivity.class);

        getContext().startActivity(intent);
    }

    @Override
    public void onItemClickTablet(View view, int position) {
        Intent intent = new Intent(getContext(), ChiTietTabletActivity.class);

        getContext().startActivity(intent);
    }
}
