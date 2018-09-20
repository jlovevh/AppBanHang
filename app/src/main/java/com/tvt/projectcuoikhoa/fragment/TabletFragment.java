package com.tvt.projectcuoikhoa.fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.activities.DetailTabletActivity;
import com.tvt.projectcuoikhoa.adapter.RecyclerTabletAdapter;
import com.tvt.projectcuoikhoa.api.APIUtils;
import com.tvt.projectcuoikhoa.helper.GridDividerDecoration;
import com.tvt.projectcuoikhoa.model.Rating;
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
public class TabletFragment extends Fragment implements RecyclerTabletAdapter.onItemClickRecyclerTablet, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private RecyclerTabletAdapter adapter;
    private List<Tablet> arrTablet =new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog dialog;
    public List<Rating> arrRating = new ArrayList<>();
    public List<Rating> arrRatingByIDSp = new ArrayList<>();
    public Rating rating;


    @SuppressLint("ValidFragment")
    private TabletFragment() {
        // Required empty public constructor
    }

    public static TabletFragment newInstance() {
        Bundle args = new Bundle();
        TabletFragment fragment = new TabletFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_tablet, container, false);
        recyclerView=view.findViewById(R.id.recyclerTablet);
        dialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
        dialog.setMessage("Loading...");
        dialog.show();
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshTablet);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.addItemDecoration(new GridDividerDecoration(getContext()));

        loadData();
        getRatingTablet();

        adapter=new RecyclerTabletAdapter(getActivity(),arrTablet);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickRecyclerTablet(this);
        return view;
    }

    private void getRatingTablet() {
        APIUtils.getJsonReponse().getRatingTablet().enqueue(new Callback<List<Rating>>() {
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
    public void onClickItem(View view, int position) {


        Intent intent = new Intent(getContext(), DetailTabletActivity.class);
        Bundle bundle = new Bundle();
        Tablet tablet = arrTablet.get(position);
        bundle.putString("id", tablet.getId());
        bundle.putString("name", tablet.getName());
        bundle.putString("price", tablet.getPrice());
        bundle.putString("status", tablet.getStatus());
        bundle.putString("image", tablet.getImage());
        bundle.putString("evaluation", tablet.getEvaluation());
        bundle.putString("promo1", tablet.getPromo1());
        bundle.putString("promo2", tablet.getPromo2());
        bundle.putString("promo3", tablet.getPromo3());
        bundle.putString("tag", tablet.getTag());
        bundle.putString("createAt", tablet.getCreateAt());
        bundle.putString("gioithieu", tablet.getGioithieu());
        bundle.putString("manhinh", tablet.getManhinh());
        bundle.putString("cameraT", tablet.getCameraTruoc());
        bundle.putString("cameraS", tablet.getCameraSau());
        bundle.putString("ram", tablet.getRam());
        bundle.putString("bonhotrong", tablet.getBonhotrong());
        bundle.putString("cpu", tablet.getCpu());
        bundle.putString("gpu", tablet.getGpu());
        bundle.putString("ketnoi", tablet.getKetnoi());
        bundle.putString("detail", tablet.getChitietcauhinh());
        bundle.putString("urlBanner", tablet.getUrlBanner());
        bundle.putString("tendanhmuc", tablet.getTendanhmuc());


        //    Log.d("SIÁZASA","SIZE: "+arrRatingByIDSp.size());
        bundle.putParcelableArrayList("ratingTablet", (ArrayList<? extends Parcelable>) arrRating);
        bundle.putParcelableArrayList("commentTablet", (ArrayList<? extends Parcelable>) HomeFragment.arrCommentTablet);

        intent.putExtra("bundle", bundle);
        getActivity().startActivity(intent);

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        loadData();

    }

    private void loadData() {
        APIUtils.getJsonReponse().getAllTablet().enqueue(new Callback<List<Tablet>>() {
            @Override
            public void onResponse(@NonNull Call<List<Tablet>> call, @NonNull Response<List<Tablet>> response) {
                dialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                arrTablet = response.body();
                adapter.setData(arrTablet);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(@NonNull Call<List<Tablet>> call, @NonNull Throwable t) {
                Log.e(Constant.TAG, "error: " + call.toString());
            }
        });
    }
}
