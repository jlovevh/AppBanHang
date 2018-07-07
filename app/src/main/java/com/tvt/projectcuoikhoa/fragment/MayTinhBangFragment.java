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
public class MayTinhBangFragment extends Fragment {


    @SuppressLint("ValidFragment")
    private MayTinhBangFragment() {
        // Required empty public constructor
    }
    public static Fragment newInstance() {
        return new MayTinhBangFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_may_tinh_bang, container, false);
    }


}
