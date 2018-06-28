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
public class TTSapRaMatFragment extends Fragment {



    private static TTSapRaMatFragment sapRaMatFragment;

    public static TTSapRaMatFragment getsapRaMatFragment(){
        if(sapRaMatFragment==null){
            sapRaMatFragment=new TTSapRaMatFragment();
        }
        return sapRaMatFragment;
    }
    @SuppressLint("ValidFragment")
    private TTSapRaMatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sap_ra_mat, container, false);
    }

}
