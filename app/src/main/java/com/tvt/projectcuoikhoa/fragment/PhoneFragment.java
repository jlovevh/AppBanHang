package com.tvt.projectcuoikhoa.fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.activities.DetailPhoneActivity;
import com.tvt.projectcuoikhoa.adapter.RecyclerPhoneAdapter;
import com.tvt.projectcuoikhoa.api.APIUtils;
import com.tvt.projectcuoikhoa.helper.EndlessRecyclerOnScrollListener;
import com.tvt.projectcuoikhoa.helper.GridDividerDecoration;
import com.tvt.projectcuoikhoa.helper.ItemClickListener;
import com.tvt.projectcuoikhoa.model.Phone;
import com.tvt.projectcuoikhoa.model.Rating;
import com.tvt.projectcuoikhoa.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneFragment extends Fragment implements ItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @SuppressLint("StaticFieldLeak")
    public static RecyclerPhoneAdapter adapter;
    private List<Phone> arrPhone = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int index = 1;
    public List<Rating> arrRating = new ArrayList<>();
    public Rating rating;
    private SearchView searchView;

    public static PhoneFragment newInstance() {
        return new PhoneFragment();
    }

    @SuppressLint("ValidFragment")
    private PhoneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_phone, container, false);
        progressDialog = new ProgressDialog(getContext(), R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        recyclerView = view.findViewById(R.id.recyclerPhone);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshPhone);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        // LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new GridDividerDecoration(getActivity()));

        setHasOptionsMenu(true);
        loadData();
        getRatingPhone();
        adapter = new RecyclerPhoneAdapter(getContext(), arrPhone);

        adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);


        adapter.setLoadMoreListener(new RecyclerPhoneAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        index++;
                        loadMore(index);

                    }
                }, 5000);
            }
        });
//        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(final int current_page) {
//                loadData();
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        loadMore(current_page);
//                    }
//                },5000);
//            }
//        });
        return view;
    }

    private void refreshData(int index) {
        APIUtils.getJsonReponse().getALLPhone(index).enqueue(new Callback<List<Phone>>() {
            @Override
            public void onResponse(Call<List<Phone>> call, Response<List<Phone>> response) {
                if (response.isSuccessful()) {
                    swipeRefreshLayout.setRefreshing(false);

                    arrPhone.addAll(response.body());
                    Log.d("SÁIASADAD", "SIZE3: " + arrPhone.size());

                } else {
                    Log.e("Lỗi", "Load more error");
                }
            }

            @Override
            public void onFailure(Call<List<Phone>> call, Throwable t) {

            }
        });

    }


    private void loadMore(final int index) {
        arrPhone.add(null);
        adapter.notifyItemChanged(arrPhone.size() - 1);
        APIUtils.getJsonReponse().getALLPhone(index).enqueue(new Callback<List<Phone>>() {
            @Override
            public void onResponse(Call<List<Phone>> call, Response<List<Phone>> response) {
                if (response.isSuccessful()) {
                    swipeRefreshLayout.setRefreshing(false);
                    arrPhone.remove(arrPhone.size() - 1);
                    adapter.notifyItemRemoved(arrPhone.size());
                    List<Phone> result = response.body();

                    if (result.size() > 0) {
                        arrPhone.addAll(result);
                        Log.d("SÁIASADAD", "SIZE2: " + arrPhone.size());

                    } else {
                        adapter.setMoreDataAvailable(false);
                        refreshData(index);
                        Toast.makeText(getContext(), "No more data", Toast.LENGTH_SHORT).show();
                    }


                    adapter.notifyDatasetChange();
                } else {
                    Log.e("Lỗi", "Load more error");
                }
            }

            @Override
            public void onFailure(Call<List<Phone>> call, Throwable t) {

            }
        });

        swipeRefreshLayout.setOnRefreshListener(this);

    }

    private void loadData() {
        APIUtils.getJsonReponse().getALLPhone(index).enqueue(new Callback<List<Phone>>() {
            @Override
            public void onResponse(@NonNull Call<List<Phone>> call, @NonNull Response<List<Phone>> response) {
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                arrPhone.addAll(response.body());
                Log.d("SÁIASADAD", "SIZE1: " + arrPhone.size());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<Phone>> call, @NonNull Throwable t) {
                Log.d(Constant.TAG, "Error" + call.toString());
            }
        });
    }

    private void getRatingPhone() {
        APIUtils.getJsonReponse().getRatingPhone().enqueue(new Callback<List<Rating>>() {
            @Override
            public void onResponse(Call<List<Rating>> call, Response<List<Rating>> response) {
                arrRating = response.body();

            }

            @Override
            public void onFailure(Call<List<Rating>> call, Throwable t) {

            }
        });


    }


    @Override
    public void onItemClick(View view, int position) {

        Intent intent = new Intent(getContext(), DetailPhoneActivity.class);
        Bundle bundle = new Bundle();
        Phone phone = arrPhone.get(position);
        bundle.putString("id", phone.getId());
        bundle.putString("name", phone.getName());
        bundle.putString("price", phone.getPrice());
        bundle.putString("status", phone.getStatus());
        bundle.putString("image", phone.getImage());
        bundle.putString("evaluation", phone.getEvaluation());
        bundle.putString("promo1", phone.getPromo1());
        bundle.putString("promo2", phone.getPromo2());
        bundle.putString("promo3", phone.getPromo3());
        bundle.putString("tag", phone.getTag());
        bundle.putString("createAt", phone.getCreateAt());
        bundle.putString("gioithieu", phone.getGioithieu());
        bundle.putString("manhinh", phone.getManhinh());
        bundle.putString("cameraT", phone.getCameraTruoc());
        bundle.putString("cameraS", phone.getCameraSau());
        bundle.putString("ram", phone.getRam());
        bundle.putString("bonhotrong", phone.getBonhotrong());
        bundle.putString("cpu", phone.getCpu());
        bundle.putString("gpu", phone.getGpu());
        bundle.putString("dlpin", phone.getDungluongpin());
        bundle.putString("hdh", phone.getHedieuhanh());
        bundle.putString("detail", phone.getChitietcauhinh());
        bundle.putString("urlBanner", phone.getUrlBanner());
        bundle.putString("tendanhmuc", phone.getTendanhmuc());
        bundle.putParcelableArrayList("ratingPhone", (ArrayList<? extends Parcelable>) arrRating);
        bundle.putParcelableArrayList("commentPhone", (ArrayList<? extends Parcelable>) HomeFragment.arrComment);

        intent.putExtra("bundle", bundle);
        getActivity().startActivity(intent);

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);

        refreshData(index);
        getRatingPhone();
    }


}
