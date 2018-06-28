package com.tvt.projectcuoikhoa.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.adapter.ViewPagerAdapter;
import com.tvt.projectcuoikhoa.fragment.SanPhamFragment;
import com.tvt.projectcuoikhoa.fragment.TaiKhoanFragment;
import com.tvt.projectcuoikhoa.fragment.TinTucFragment;
import com.tvt.projectcuoikhoa.fragment.TrangChuFragment;
import com.tvt.projectcuoikhoa.helper.BottomNavigationBehavior;
import com.tvt.projectcuoikhoa.helper.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();


        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerToggle.syncState();
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        loadFragment(TrangChuFragment.newInstance());

//        setupViewPager(viewPager);

//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                MenuItem menuItem = null;
//
//                bottomNavigationView.getMenu().getItem(position).setChecked(true);
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

//    private void setupViewPager(ViewPager viewPager) {
//        adapter=new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(TrangChuFragment.newInstance());
//        adapter.addFragment(SanPhamFragment.newInstance());
//        adapter.addFragment(TinTucFragment.newInstance());
//        adapter.addFragment(TaiKhoanFragment.newInstance());
//        viewPager.setAdapter(adapter);
//    }


    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.draw_layout);
        navigationView = findViewById(R.id.nav_layout);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setSupportActionBar(toolbar);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.nav_home:

                Fragment fragment=TrangChuFragment.newInstance();
                loadFragment(fragment);
                break;
            case R.id.nav_sanpham:

                Fragment fragment1=SanPhamFragment.newInstance();
                loadFragment(fragment1);
                break;

            case R.id.nav_news:

                Fragment fragment2=TinTucFragment.newInstance();
                loadFragment(fragment2);
                break;

            case R.id.nav_account:

                Fragment fragment3=TaiKhoanFragment.newInstance();
                loadFragment(fragment3);
                break;

        }
        return true;
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.layout,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {


            case R.id.action_giohang:
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);


        SearchView searchView = (SearchView) findViewById(R.id.search_view);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });


        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }
}
