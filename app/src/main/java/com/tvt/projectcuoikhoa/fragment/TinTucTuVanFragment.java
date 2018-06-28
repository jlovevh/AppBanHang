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
public class TinTucTuVanFragment extends Fragment {

    private static TinTucTuVanFragment tuVanFragment;

    public static TinTucTuVanFragment gettuVanFragment(){
        if(tuVanFragment==null){
            tuVanFragment=new TinTucTuVanFragment();
        }
        return tuVanFragment;
    }
    @SuppressLint("ValidFragment")
    private TinTucTuVanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tin_tuc_tu_van, container, false);
    }

}
