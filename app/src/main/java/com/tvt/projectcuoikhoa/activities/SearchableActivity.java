package com.tvt.projectcuoikhoa.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.adapter.ListSearchAdapter;
import com.tvt.projectcuoikhoa.adapter.RecyclerPhoneAdapter;
import com.tvt.projectcuoikhoa.api.APIUtils;
import com.tvt.projectcuoikhoa.fragment.HomeFragment;
import com.tvt.projectcuoikhoa.model.Phone;
import com.tvt.projectcuoikhoa.model.SanPham;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchableActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, ListSearchAdapter.ListSearchListener {

    public static final String JARGON = "JARGON";
    private SearchView searchView;
    private ListView listView;
    private ListSearchAdapter adapter;
    private List<SanPham> arrSP;
    private ImageView imgBackSearch, cart;
    private TextView counttxt_cart_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);


        imgBackSearch = findViewById(R.id.img_back_search);
        counttxt_cart_search = findViewById(R.id.counttxt_cart_search);
        counttxt_cart_search.setText(String.valueOf(HomeFragment.arrCart.size()));
        cart = findViewById(R.id.cart_search);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchableActivity.this, ShoppingCartActivity.class));
                overridePendingTransition(R.anim.anim_enter2, 0);
            }
        });
        imgBackSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.anim_exit2);
            }
        });
        arrSP = new ArrayList<>();
        listView = findViewById(R.id.listView);
        adapter = new ListSearchAdapter(SearchableActivity.this, arrSP, this);
        listView.setAdapter(adapter);


        APIUtils.getJsonReponse().getAllSanPham().enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                List<SanPham> arr = response.body();


                arrSP.clear();
                if (arr != null) {
                    arrSP.addAll(arr);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {

            }
        });

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = findViewById(R.id.search_view);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);
        searchView.setBackgroundColor(getResources().getColor(android.R.color.white));
        searchView.setPadding(0, 20, 0, 20);
        searchView.setQueryHint("Search all products");
//        ImageView searchViewIcon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
//        ViewGroup linearLayoutSearchView =
//                (ViewGroup) searchViewIcon.getParent();
//        linearLayoutSearchView.removeView(searchViewIcon);
//        linearLayoutSearchView.addView(searchViewIcon);
//        searchViewIcon.setAdjustViewBounds(true);
//        searchViewIcon.setMaxWidth(0);
//        searchViewIcon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        searchViewIcon.setImageDrawable(null);
        searchView.setMaxWidth(Integer.MAX_VALUE);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        //  searchView.setPadding(0,30,0,30);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.getFilter().filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        adapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public void onSearchSelected(SanPham sanPham, int position) {

//        if (Integer.parseInt(sanPham.getDanhmucId()) == 1) {
//            Intent intent = new Intent(SearchableActivity.this, DetailPhoneActivity.class);
//            intent.putExtra("id", sanPham.getIdSp());
//            startActivity(intent);
//        } else if (Integer.parseInt(sanPham.getDanhmucId()) == 2) {
//            Intent intent = new Intent(SearchableActivity.this, DetailLaptopActivity.class);
//            intent.putExtra("id", sanPham.getIdSp());
//            startActivity(intent);
//        } else if (Integer.parseInt(sanPham.getDanhmucId()) == 3) {
//            Intent intent = new Intent(SearchableActivity.this, DetailTabletActivity.class);
//            intent.putExtra("id", sanPham.getIdSp());
//            startActivity(intent);
//        }

        Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();

    }
}
