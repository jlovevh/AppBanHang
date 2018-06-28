package com.tvt.projectcuoikhoa.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tvt.projectcuoikhoa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TTTinMoiFragment extends Fragment {

    private static TTTinMoiFragment tinMoiFragment;

    public static TTTinMoiFragment getTinMoiFragment(){

        if(tinMoiFragment==null){

            tinMoiFragment=new TTTinMoiFragment();
        }

        return tinMoiFragment;
    }


    @SuppressLint("ValidFragment")
    private TTTinMoiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tttin_moi, container, false);
    }

}
