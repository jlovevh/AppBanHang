package com.tvt.projectcuoikhoa.fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.activities.ChiTietDienThoaiActivity;
import com.tvt.projectcuoikhoa.adapter.RecyclerPhoneAdapter;
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

    private RecyclerPhoneAdapter adapter;
    private List<Phone> arrPhone =new ArrayList<>();
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private int page = 1;

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
        recyclerView.addItemDecoration(new GridDividerDecoration(getActivity()));

        loadMoreData(page);

        adapter = new RecyclerPhoneAdapter(recyclerView, getContext(), arrPhone);
        adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);

//        adapter.setLoadMoreListener(this);
        return view;
    }


//    @Override
//    public void onLoadMore(final int page) {
//      if(arrPhone.size()<=6){
//          arrPhone.add(null);
//          adapter.notifyItemInserted(arrPhone.size()-1);
//          new Handler().postDelayed(new Runnable() {
//              @Override
//              public void run() {
//                  int index=arrPhone.size()-1;
//                  loadMoreData(page);
//              }
//          },5000);
//      }
//    }

    private void loadMoreData(int page) {

        APIUtils.getJsonReponse().getALLPhone().enqueue(new Callback<List<Phone>>() {
            @Override
            public void onResponse(@NonNull Call<List<Phone>> call, @NonNull Response<List<Phone>> response) {
                progressDialog.dismiss();
//                arrPhone.remove(arrPhone.size());
//                adapter.notifyItemRemoved(arrPhone.size());
                arrPhone=response.body();
                adapter.setData(arrPhone);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<Phone>> call, @NonNull Throwable t) {
                Log.d(Constant.TAG, "Error" + call.toString());
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

        Intent intent = new Intent(getContext(), ChiTietDienThoaiActivity.class);

        getActivity().startActivity(intent);

    }
}
