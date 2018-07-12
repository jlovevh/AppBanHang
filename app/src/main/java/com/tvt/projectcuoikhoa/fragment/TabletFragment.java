package com.tvt.projectcuoikhoa.fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.adapter.RecyclerTabletAdapter;
import com.tvt.projectcuoikhoa.api.APIUtils;
import com.tvt.projectcuoikhoa.helper.GridDividerDecoration;
import com.tvt.projectcuoikhoa.model.Tablet;
import com.tvt.projectcuoikhoa.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabletFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerTabletAdapter adapter;
    private List<Tablet> arrTablet =new ArrayList<>();


    @SuppressLint("ValidFragment")
    private TabletFragment() {
        // Required empty public constructor
    }
    public static Fragment newInstance() {
        return new TabletFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_tablet, container, false);
        recyclerView=view.findViewById(R.id.recyclerTablet);
        final ProgressDialog dialog=new ProgressDialog(getActivity(),R.style.AppCompatAlertDialogStyle);
        dialog.setMessage("Loading...");
        dialog.show();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.addItemDecoration(new GridDividerDecoration(getContext()));


        APIUtils.getJsonReponse().getAllTablet().enqueue(new Callback<List<Tablet>>() {
            @Override
            public void onResponse(@NonNull Call<List<Tablet>> call,@NonNull Response<List<Tablet>> response) {
                dialog.dismiss();
                arrTablet=response.body();
                adapter.setData(arrTablet);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(@NonNull Call<List<Tablet>> call,@NonNull Throwable t) {
                Log.e(Constant.TAG, "error: " + call.toString());
            }
        });
        adapter=new RecyclerTabletAdapter(getActivity(),arrTablet);
        recyclerView.setAdapter(adapter);
        return view;
    }


}
