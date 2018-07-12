package com.tvt.projectcuoikhoa.fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.adapter.RecyclerViewTinTucAdapter;
import com.tvt.projectcuoikhoa.api.APIUtils;
import com.tvt.projectcuoikhoa.model.TinTuc;
import com.tvt.projectcuoikhoa.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TTCuocSongFragment extends Fragment {

    private List<TinTuc> arrCuocSong ;
    private RecyclerView recyclerView;
    private RecyclerViewTinTucAdapter adapter;


    private static TTCuocSongFragment ttCuocSongFragment;

    public static TTCuocSongFragment getttCuocSongFragment(){

        if(ttCuocSongFragment==null){

            ttCuocSongFragment=new TTCuocSongFragment();
        }

        return ttCuocSongFragment;
    }


    @SuppressLint("ValidFragment")
    private TTCuocSongFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_tt_cuoc_song, container, false);
        final ProgressDialog progressDialog=new ProgressDialog(getContext(),R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerCuocSong);
        arrCuocSong=new ArrayList<>();
        APIUtils.getJsonReponse().getTinTucCuocSong().enqueue(new Callback<List<TinTuc>>() {
            @Override
            public void onResponse(@NonNull Call<List<TinTuc>> call, @NonNull Response<List<TinTuc>> response) {
               progressDialog.dismiss();
                arrCuocSong=response.body();
                adapter.setData(arrCuocSong);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<TinTuc>> call, @NonNull Throwable t) {
                Log.d(Constant.TAG, "error" + call.toString());
            }
        });

        adapter=new RecyclerViewTinTucAdapter(getActivity(),arrCuocSong);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration dividerItemDecoration =new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        return view;
    }

}
