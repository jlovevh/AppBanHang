package com.tvt.projectcuoikhoa.fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.adapter.RecyclerLapTopAdapter;
import com.tvt.projectcuoikhoa.api.APIUtils;
import com.tvt.projectcuoikhoa.model.LapTop;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LapTopFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<LapTop> arrLapTop;
    private RecyclerLapTopAdapter adapter;
    private ProgressDialog dialog;
    @SuppressLint("ValidFragment")
    private LapTopFragment() {
        // Required empty public constructor
    }
    public static Fragment newInstance() {

        return new LapTopFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_lap_top, container, false);
        dialog=new ProgressDialog(getActivity(),R.style.AppCompatAlertDialogStyle);
        dialog.setMessage("Loading...");
        dialog.show();
        recyclerView=view.findViewById(R.id.recyclerLaptop);
        arrLapTop=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));



        APIUtils.getJsonReponse().getALLLapTop().enqueue(new Callback<List<LapTop>>() {
            @Override
            public void onResponse(@NonNull Call<List<LapTop>> call,@NonNull Response<List<LapTop>> response) {
                dialog.dismiss();
                arrLapTop=response.body();
                adapter.setData(arrLapTop);
            }

            @Override
            public void onFailure(@NonNull Call<List<LapTop>> call,@NonNull Throwable t) {

            }
        });

        adapter=new RecyclerLapTopAdapter(getActivity(),arrLapTop);
        recyclerView.setAdapter(adapter);
        return view;
    }


}
