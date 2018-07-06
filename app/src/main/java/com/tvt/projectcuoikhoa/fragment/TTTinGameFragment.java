package com.tvt.projectcuoikhoa.fragment;


import android.annotation.SuppressLint;
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
import com.tvt.projectcuoikhoa.utils.Const;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TTTinGameFragment extends Fragment {

    private List<TinTuc> arrTinGame ;
    private RecyclerView recyclerView;
    private RecyclerViewTinTucAdapter adapter;

    public static TTTinGameFragment TTTinVatFragment;
    public static TTTinGameFragment newInstance(){
        return new TTTinGameFragment();
    }
    @SuppressLint("ValidFragment")
    private TTTinGameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_tt_tin_game, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerTinGame);
        arrTinGame=new ArrayList<>();
        APIUtils.getJsonReponse().getTinGame().enqueue(new Callback<List<TinTuc>>() {
            @Override
            public void onResponse(@NonNull Call<List<TinTuc>> call, @NonNull Response<List<TinTuc>> response) {
                arrTinGame=response.body();

                adapter.setData(arrTinGame);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<TinTuc>> call, @NonNull Throwable t) {
                Log.d(Const.TAG,"error"+call.toString());            }
        });

        adapter=new RecyclerViewTinTucAdapter(getActivity(),arrTinGame);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration dividerItemDecoration =new DividerItemDecoration(Objects.requireNonNull(getActivity()),LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        return view;
    }

}
