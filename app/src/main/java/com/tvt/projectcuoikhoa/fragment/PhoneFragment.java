package com.tvt.projectcuoikhoa.fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.tvt.projectcuoikhoa.activities.DetailPhoneActivity;
import com.tvt.projectcuoikhoa.adapter.RecyclerPhoneAdapter2;
import com.tvt.projectcuoikhoa.api.APIUtils;
import com.tvt.projectcuoikhoa.helper.GridDividerDecoration;
import com.tvt.projectcuoikhoa.helper.ItemClickListener;
import com.tvt.projectcuoikhoa.model.Phone;
import com.tvt.projectcuoikhoa.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneFragment extends Fragment implements ItemClickListener {

    private RecyclerPhoneAdapter2 adapter;
    private List<Phone> arrPhone =new ArrayList<>();
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private int current_page = 1;

    public  static PhoneFragment newInstance(){
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
        View view =inflater.inflate(R.layout.fragment_phone, container, false);
        progressDialog=new ProgressDialog(getContext(),R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        recyclerView=view.findViewById(R.id.recyclerPhone);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
//        recyclerView.addItemDecoration(new GridDividerDecoration(getActivity()));


        loadData();
        adapter = new RecyclerPhoneAdapter2(getContext(), arrPhone);

        adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);

//        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(new GridLayoutManager(getContext(),2)) {
//            @Override
//            public void onLoadMore(final int current_page) {
//                if(arrPhone.size()<=6){
//
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            recyclerView.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    arrPhone.add(null);
//                                    adapter.notifyItemInserted(arrPhone.size()-1);
//                                    Log.d("aaaaaaa","ArrPhone: " +arrPhone.size());
//                                }
//                            });
//
//                            loadMoreData(current_page);
//                        }
//                    },5000);
//                }
//            }
//        });
        return view;
    }

    private void loadData() {
        APIUtils.getJsonReponse().getALLPhone().enqueue(new Callback<List<Phone>>() {
            @Override
            public void onResponse(@NonNull Call<List<Phone>> call, @NonNull Response<List<Phone>> response) {
                progressDialog.dismiss();
//                arrPhone.remove(arrPhone.size());
//                adapter.notifyItemRemoved(arrPhone.size());
                arrPhone=response.body();

//                Log.d(Constant.TAG,"Size: "+arrPhone.size());
                adapter.setData(arrPhone);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<Phone>> call, @NonNull Throwable t) {
                Log.d(Constant.TAG, "Error" + call.toString());
            }
        });
    }


//    private void loadMoreData(int page) {
//
//        APIUtils.getJsonReponse().getALLPhone().enqueue(new Callback<List<Phone>>() {
//            @Override
//            public void onResponse(@NonNull Call<List<Phone>> call, @NonNull Response<List<Phone>> response) {
//                recyclerView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        arrPhone.remove(arrPhone.size()-1);
//                        adapter.notifyItemRemoved(arrPhone.size());
//                    }
//                });
//                arrPhone=response.body();
//                adapter.setData(arrPhone);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<List<Phone>> call, @NonNull Throwable t) {
//                Log.d(Constant.TAG, "Error" + call.toString());
//            }
//        });
//    }



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

        intent.putExtra("bundle", bundle);
        getActivity().startActivity(intent);

    }
}
