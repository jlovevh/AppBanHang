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
import com.tvt.projectcuoikhoa.model.News;
import com.tvt.projectcuoikhoa.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TTTinMoiFragment extends Fragment {

    private List<News> arrTinMoi;
    private RecyclerViewTinTucAdapter adapter;

    @SuppressLint("StaticFieldLeak")
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_tt_tin_moi, container, false);
        final ProgressDialog progressDialog=new ProgressDialog(getContext(),R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerTinMoi);
        arrTinMoi=new ArrayList<>();
        APIUtils.getJsonReponse().getTinMoi().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(@NonNull Call<List<News>> call, @NonNull Response<List<News>> response) {
                progressDialog.dismiss();
                arrTinMoi=response.body();

                adapter.setData(arrTinMoi);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<News>> call, @NonNull Throwable t) {
                Log.d(Constant.TAG, "error" + call.toString());
            }
        });

        adapter=new RecyclerViewTinTucAdapter(getActivity(),arrTinMoi);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration dividerItemDecoration =new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        return view;
    }

}
