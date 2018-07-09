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
import com.tvt.projectcuoikhoa.adapter.TinTucDanhGiaAdapter;
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
public class TTDanhGiaFragment extends Fragment {

    private List<TinTuc> arrDanhGia;
    private TinTucDanhGiaAdapter adapter;
    private RecyclerView recyclerView;

    @SuppressLint("StaticFieldLeak")
    private static TTDanhGiaFragment TTDanhGiaFragment;

    public static com.tvt.projectcuoikhoa.fragment.TTDanhGiaFragment getTTDanhGiaFragment(){
        if(TTDanhGiaFragment==null){
            TTDanhGiaFragment=new TTDanhGiaFragment();
        }
        return TTDanhGiaFragment;
    }
    @SuppressLint("ValidFragment")
    private TTDanhGiaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_tt_danh_gia, container, false);
        final ProgressDialog progressDialog=new ProgressDialog(getContext(),R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        recyclerView = view.findViewById(R.id.recyclerDanhGia);
        arrDanhGia=new ArrayList<>();
        APIUtils.getJsonReponse().getDanhGia().enqueue(new Callback<List<TinTuc>>() {
            @Override
            public void onResponse(@NonNull Call<List<TinTuc>> call, @NonNull Response<List<TinTuc>> response) {
                progressDialog.dismiss();
                arrDanhGia=response.body();
                adapter.setData(arrDanhGia);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<TinTuc>> call, @NonNull Throwable t) {
                Log.d(Const.TAG,"error"+call.toString());            }
        });

        adapter=new TinTucDanhGiaAdapter(getActivity(),arrDanhGia);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration dividerItemDecoration =new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        return view;
    }

}
