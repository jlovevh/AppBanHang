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
import com.tvt.projectcuoikhoa.adapter.RecyclerPhoneAdapter;
import com.tvt.projectcuoikhoa.api.APIUtils;
import com.tvt.projectcuoikhoa.helper.GridDividerDecoration;
import com.tvt.projectcuoikhoa.model.Phone;
import com.tvt.projectcuoikhoa.utils.Const;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneFragment extends Fragment {

    private RecyclerPhoneAdapter adapter;
    private List<Phone> arrPhone =new ArrayList<>();
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;

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



        APIUtils.getJsonReponse().getALLPhone().enqueue(new Callback<List<Phone>>() {
            @Override
            public void onResponse(@NonNull Call<List<Phone>> call,@NonNull Response<List<Phone>> response) {
                progressDialog.dismiss();
                arrPhone=response.body();
                adapter.setData(arrPhone);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<Phone>> call,@NonNull Throwable t) {
                Log.d(Const.TAG,"Error" +call.toString());
            }
        });
        adapter=new RecyclerPhoneAdapter(getContext(),arrPhone);
        recyclerView.setAdapter(adapter);
        return view;
    }

}
