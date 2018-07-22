package com.tvt.projectcuoikhoa.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> arrFragment=new ArrayList<>();
    private List<String> arrTitle =new ArrayList<>();
    private Context context;

    public NewsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {



        return arrFragment.get(position);
    }

    @Override
    public int getCount() {


        return arrFragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return arrTitle.get(position);
    }
    public  void  addFragment(Fragment fragment,String title){
        arrFragment.add(fragment);
        arrTitle.add(title);
    }



}
