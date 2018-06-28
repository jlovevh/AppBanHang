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
public class TTTinVatFragment extends Fragment {

    public static TTTinVatFragment TTTinVatFragment;
    public static TTTinVatFragment newInstance(){
        return new TTTinVatFragment();
    }
    @SuppressLint("ValidFragment")
    private TTTinVatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tin_vat, container, false);
    }

}
