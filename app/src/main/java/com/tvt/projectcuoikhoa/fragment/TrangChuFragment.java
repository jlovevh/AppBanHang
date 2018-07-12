package com.tvt.projectcuoikhoa.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.adapter.BannerQcAdapter;
import com.tvt.projectcuoikhoa.adapter.RecyclerLaptopNewAdapter;
import com.tvt.projectcuoikhoa.adapter.RecyclerPhoneHotAdapter;
import com.tvt.projectcuoikhoa.api.APIUtils;
import com.tvt.projectcuoikhoa.helper.GridDividerDecoration;
import com.tvt.projectcuoikhoa.model.BannerQc;
import com.tvt.projectcuoikhoa.model.LaptopNew;
import com.tvt.projectcuoikhoa.model.PhoneHot;
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
public class TrangChuFragment extends Fragment {

    private BannerQcAdapter adapter;
    private List<BannerQc> bannerQcList;


    private RecyclerPhoneHotAdapter adapterPhone;
    private List<PhoneHot> phoneHotList;
    private RecyclerView recyclerView;
    private TextView tvMore;

    private RecyclerLaptopNewAdapter adapterLaptop;
    private List<LaptopNew> arrLaptop;
    private RecyclerView recyclerViewLaptop;
    private TextView tvAll;

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
        final ViewPager viewPager = view.findViewById(R.id.view_pager3);
        CircleIndicator indicator = view.findViewById(R.id.indicator);
        recyclerView = view.findViewById(R.id.recyclerPhoneHOt);
        tvMore = view.findViewById(R.id.tv_more);
        recyclerViewLaptop = view.findViewById(R.id.recyclerLaptopNew);

        bannerQcList = new ArrayList<>();

        APIUtils.getJsonReponse().getAllBanner().enqueue(new Callback<List<BannerQc>>() {
            @Override
            public void onResponse(@NonNull Call<List<BannerQc>> call, @NonNull Response<List<BannerQc>> response) {
//                Log.d(Constant.TAG, response.toString());

                bannerQcList = response.body();
                adapter = new BannerQcAdapter(getContext(), bannerQcList);
                viewPager.setAdapter(adapter);
//                    adapter.setData(bannerQcList);


//                Log.d(Constant.TAG, "Size: " + bannerQcList.size());


            }

            @Override
            public void onFailure(@NonNull Call<List<BannerQc>> call, @NonNull Throwable t) {
                Log.d(Constant.TAG, call.toString());
            }
        });
        indicator.setViewPager(viewPager);


        getPhoneHot();
        getLaptopNew();

        return view;
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

}
