package com.tvt.projectcuoikhoa.fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.activities.DetailLaptopActivity;
import com.tvt.projectcuoikhoa.adapter.RecyclerLapTopAdapter;
import com.tvt.projectcuoikhoa.api.APIUtils;
import com.tvt.projectcuoikhoa.helper.MyDividerItemDecoration;
import com.tvt.projectcuoikhoa.model.LapTop;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LapTopFragment extends Fragment implements RecyclerLapTopAdapter.itemOnClickListenerLaptop {

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
        recyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 2));




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
        adapter.setItemOnClickListenerLaptop(this);
        return view;
    }


    @Override
    public void onClickItemRecyclerView(View view, int position) {

        Intent intent = new Intent(getContext(), DetailLaptopActivity.class);
        Bundle bundle = new Bundle();
        LapTop lapTop = arrLapTop.get(position);
        bundle.putString("id", lapTop.getId());
        bundle.putString("name", lapTop.getName());
        bundle.putString("price", lapTop.getPrice());
        bundle.putString("status", lapTop.getStatus());
        bundle.putString("image", lapTop.getImage());
        bundle.putString("evaluation", lapTop.getEvaluation());
        bundle.putString("promo1", lapTop.getPromo1());
        bundle.putString("promo2", lapTop.getPromo2());
        bundle.putString("promo3", lapTop.getPromo3());
        bundle.putString("tag", lapTop.getTag());
        bundle.putString("createAt", lapTop.getCreateAt());
        bundle.putString("gioithieu", lapTop.getGioithieu());
        bundle.putString("manhinh", lapTop.getManhinh());
        bundle.putString("ocung", lapTop.getOcung());
        bundle.putString("dohoa", lapTop.getDohoa());
        bundle.putString("ram", lapTop.getRam());
        bundle.putString("ketnoi", lapTop.getKetnoi());
        bundle.putString("cpu", lapTop.getCpu());
        bundle.putString("tl", lapTop.getTrongluong());
        bundle.putString("hdh", lapTop.getHedieuhanh());
        bundle.putString("detail", lapTop.getChitietcauhinh());
        bundle.putString("urlBanner", lapTop.getUrlBanner());
        bundle.putString("tendanhmuc", lapTop.getTendanhmuc());

        intent.putExtra("bundle", bundle);
        getActivity().startActivity(intent);

    }
}
