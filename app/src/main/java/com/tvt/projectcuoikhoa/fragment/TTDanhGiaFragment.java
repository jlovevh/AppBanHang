package com.tvt.projectcuoikhoa.fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.activities.NewsEvaluationActivity;
import com.tvt.projectcuoikhoa.adapter.NewsEvaluationAdapter;
import com.tvt.projectcuoikhoa.api.APIUtils;
import com.tvt.projectcuoikhoa.helper.ItemClickListener;
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
public class TTDanhGiaFragment extends Fragment implements ItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private List<News> arrDanhGia;
    private NewsEvaluationAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog progressDialog;

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
        progressDialog = new ProgressDialog(getContext(), R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshNews2);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = view.findViewById(R.id.recyclerDanhGia);
        arrDanhGia=new ArrayList<>();
        loadData();

        adapter = new NewsEvaluationAdapter(getActivity(), arrDanhGia);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration dividerItemDecoration =new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), NewsEvaluationActivity.class);
        intent.putExtra("title", arrDanhGia.get(position).getTieude());
        intent.putExtra("baiviet", arrDanhGia.get(position).getBaiviet());
        intent.putExtra("create", arrDanhGia.get(position).getCreateAt());
        intent.putExtra("tendanhmuc", arrDanhGia.get(position).getTendanhmuctintuc());
        intent.putExtra("image", arrDanhGia.get(position).getAnhtieude());
        intent.putExtra("mota", arrDanhGia.get(position).getMota());
        getActivity().startActivity(intent);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        loadData();
    }

    private void loadData() {
        APIUtils.getJsonReponse().getDanhGia().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(@NonNull Call<List<News>> call, @NonNull Response<List<News>> response) {
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                arrDanhGia = response.body();
                adapter.setData(arrDanhGia);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<News>> call, @NonNull Throwable t) {
                Log.d(Constant.TAG, "error" + call.toString());
            }
        });
    }
}
