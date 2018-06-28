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
public class TTMeoHayFragment extends Fragment {


    private static TTMeoHayFragment meoHayFragment;

    public static TTMeoHayFragment getmeoHayFragment(){

        if(meoHayFragment==null){

            meoHayFragment=new TTMeoHayFragment();
        }

        return meoHayFragment;
    }


    @SuppressLint("ValidFragment")
    private TTMeoHayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ttmeo_hay, container, false);
    }

}
