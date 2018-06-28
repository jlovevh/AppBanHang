package com.tvt.projectcuoikhoa.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.adapter.BannerQcAdapter;
import com.tvt.projectcuoikhoa.api.APIUtils;
import com.tvt.projectcuoikhoa.model.BannerQc;
import com.tvt.projectcuoikhoa.utils.Const;

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

    public static TrangChuFragment newInstance(){
        return  new TrangChuFragment();
    }

    private TrangChuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_trang_chu, container, false);
        ViewPager viewPager = view.findViewById(R.id.view_pager3);
        bannerQcList=new ArrayList<>();
        adapter=new BannerQcAdapter(getContext(),bannerQcList);

        CircleIndicator indicator =view.findViewById(R.id.indicator);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);

        APIUtils.getBannerService().getAllBanner().enqueue(new Callback<List<BannerQc>>() {
            @Override
            public void onResponse(@NonNull Call<List<BannerQc>> call, @NonNull Response<List<BannerQc>> response) {
                   bannerQcList=response.body();
                    adapter.setData(bannerQcList);


            }

            @Override
            public void onFailure(@NonNull Call<List<BannerQc>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });

        viewPager.setAdapter(adapter);
        return view;
    }

}
