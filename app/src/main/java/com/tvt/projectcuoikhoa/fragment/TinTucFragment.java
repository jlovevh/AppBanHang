package com.tvt.projectcuoikhoa.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.adapter.TinTucAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TinTucFragment extends Fragment {

    private TinTucAdapter adapter;


    public static TinTucFragment newInstance() {
        return new TinTucFragment();
    }
    @SuppressLint("ValidFragment")
    private TinTucFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_tin_tuc, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager2);
        TabLayout tabLayout =(TabLayout)view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        setUpViewPager(viewPager);
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.tablayout_text_anim);
        tabLayout.setAnimation(animation);
        ;
        return view;
    }

    private void setUpViewPager(ViewPager viewPager) {
        adapter=new TinTucAdapter(getChildFragmentManager());
        adapter.addFragment(TTTinMoiFragment.getTinMoiFragment(),"TIN MỚI");
        adapter.addFragment(TTDanhGiaFragment.getTTDanhGiaFragment(),"ĐÁNH GIÁ");
        adapter.addFragment(TTMeoHayFragment.getMeoHayFragment(),"MẸO HAY");
        adapter.addFragment(TTThiTruongFragment.getthiTruongFragment(),"THỊ TRƯỜNG");
        adapter.addFragment(TTCuocSongFragment.getttCuocSongFragment(),"CUỘC SỐNG SỐ");
        adapter.addFragment(TTTinGameFragment.newInstance(),"TIN GAME-APP");
        viewPager.setAdapter(adapter);
    }


}
