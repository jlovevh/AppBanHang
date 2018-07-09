package com.tvt.projectcuoikhoa.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.adapter.ViewPagerAdapter;
import com.tvt.projectcuoikhoa.fragment.LapTopFragment;
import com.tvt.projectcuoikhoa.fragment.TabletFragment;
import com.tvt.projectcuoikhoa.fragment.PhoneFragment;
import com.tvt.projectcuoikhoa.fragment.TaiKhoanFragment;
import com.tvt.projectcuoikhoa.fragment.TinTucFragment;
import com.tvt.projectcuoikhoa.fragment.TrangChuFragment;
import com.tvt.projectcuoikhoa.helper.BottomNavigationBehavior;
import com.tvt.projectcuoikhoa.helper.BottomNavigationViewHelper;
import com.tvt.projectcuoikhoa.utils.Const;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ViewPagerAdapter adapter;
    private TextView tvName, tvEmail;
    private ImageView imgHeader;
    private String name, email, url, nameGG, emailGG, urlGG, nameU, emailU, urlU;
    private boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();


        getIntents();
        getIntentsFB();
        getIntentsGG();


        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerToggle.syncState();
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);


        //load default fragment
        loadFragment(TrangChuFragment.newInstance());


        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        navigationView.setNavigationItemSelectedListener(this);

    }


    private void getIntentsFB() {

        Intent intentFB = getIntent();
        name = intentFB.getStringExtra("name");
        url = intentFB.getStringExtra("url");
        email = intentFB.getStringExtra("email");

        Log.d(Const.TAG, "ccc: " + name);
        Log.d(Const.TAG, "ccc: " + email);
        Log.d(Const.TAG, "ccc: " + url);

        tvEmail.setText(email);
        tvName.setText(name);
        Picasso.with(this).load(url).placeholder(R.drawable.iu).error(R.mipmap.ic_launcher).into(imgHeader);

        imgHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Thông báo")
                        .setMessage("Bạn có muốn đăng xuất không?")
                        .setNegativeButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LoginManager.getInstance().logOut();
                                tvName.setText("Lee Ji-eun");
                                tvEmail.setText("I love IU");
                                imgHeader.setImageResource(R.drawable.iu);
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });

                builder.show();


            }
        });
    }

    private void getIntentsGG() {

        Intent intent = getIntent();
        nameGG = intent.getStringExtra("nameGG");
        urlGG = intent.getStringExtra("url_gg");
        emailGG = intent.getStringExtra("emailGG");

        Log.d(Const.TAG, "nameGG: " + nameGG);
        Log.d(Const.TAG, "urlGG: " + urlGG);
        Log.d(Const.TAG, "emailGG: " + emailGG);
        tvEmail.setText(emailGG);
        tvName.setText(nameGG);
        Picasso.with(this).load(urlGG).error(R.drawable.iu).into(imgHeader);

        imgHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Thông báo")
                        .setMessage("Bạn có muốn đăng xuất không?")
                        .setNegativeButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LoginActivity.googleSignInClient.signOut().addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        tvName.setText("Lee Ji-eun");
                                        tvEmail.setText("I love IU");
                                        imgHeader.setImageResource(R.drawable.iu);
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                });

                            }
                        })
                        .setPositiveButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });

                builder.show();


            }
        });
    }

    private void getIntents() {

        Intent intentU = getIntent();
        nameU = intentU.getStringExtra("nameU");
        urlU = intentU.getStringExtra("urlU");
        emailU = intentU.getStringExtra("emailU");

        Log.d(Const.TAG, "nameU: " + nameU);
        Log.d(Const.TAG, "urlU: " + urlU);
        Log.d(Const.TAG, "emailU: " + emailU);
        tvName.setText(nameU);
        tvEmail.setText(emailU);
        Picasso.with(this).load(urlU).placeholder(R.drawable.iu).error(R.mipmap.ic_launcher).into(imgHeader);
    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(TrangChuFragment.newInstance());
        adapter.addFragment(PhoneFragment.newInstance());
        adapter.addFragment(TinTucFragment.newInstance());
        adapter.addFragment(TaiKhoanFragment.newInstance());
        viewPager.setAdapter(adapter);
    }


    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.draw_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_layout);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setSupportActionBar(toolbar);

        View navHeaderView = navigationView.getHeaderView(0);
        tvEmail = navHeaderView.findViewById(R.id.email_header);
        tvName = navHeaderView.findViewById(R.id.name_header);
        imgHeader = navHeaderView.findViewById(R.id.img_header);


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {

            case R.id.bnav_home:

                fragment = TrangChuFragment.newInstance();
                loadFragment(fragment);
                break;
            case R.id.bnav_sanpham:

                fragment = PhoneFragment.newInstance();
                loadFragment(fragment);
                break;

            case R.id.bnav_news:

                fragment = TinTucFragment.newInstance();
                loadFragment(fragment);
                break;

            case R.id.bnav_account:

                fragment = TaiKhoanFragment.newInstance();
                loadFragment(fragment);
                break;
            case R.id.nav_home:
                fragment = TrangChuFragment.newInstance();
                loadFragment(fragment);
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                break;
            case R.id.nav_phone:
                fragment = PhoneFragment.newInstance();
                loadFragment(fragment);
                bottomNavigationView.getMenu().getItem(1).setChecked(true);

                break;
            case R.id.nav_laptop:

                fragment = LapTopFragment.newInstance();
                loadFragment(fragment);
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                break;
            case R.id.nav_tablet:

                fragment = TabletFragment.newInstance();
                loadFragment(fragment);
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                break;

            case R.id.nav_pk:


                break;
            case R.id.nav_exit:
                finish();
                break;

            case R.id.nav_about:

                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.draw_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.layout, fragment);
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

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }
}
