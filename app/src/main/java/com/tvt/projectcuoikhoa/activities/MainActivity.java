package com.tvt.projectcuoikhoa.activities;

import android.app.ProgressDialog;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.adapter.ViewPagerAdapter;
import com.tvt.projectcuoikhoa.api.APIUtils;
import com.tvt.projectcuoikhoa.database.FBSqliteOpenHelper;
import com.tvt.projectcuoikhoa.fragment.AccountFragment;
import com.tvt.projectcuoikhoa.fragment.HomeFragment;
import com.tvt.projectcuoikhoa.fragment.LapTopFragment;
import com.tvt.projectcuoikhoa.fragment.TabletFragment;
import com.tvt.projectcuoikhoa.fragment.PhoneFragment;
import com.tvt.projectcuoikhoa.fragment.NewsFragment;
import com.tvt.projectcuoikhoa.helper.BottomNavigationBehavior;
import com.tvt.projectcuoikhoa.helper.BottomNavigationViewHelper;
import com.tvt.projectcuoikhoa.model.User;
import com.tvt.projectcuoikhoa.model.UserFB;
import com.tvt.projectcuoikhoa.utils.SharepreferenceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    public static BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ViewPagerAdapter adapter;
    private TextView tvName, tvEmail;
    private ImageView imgHeader;
    private SharepreferenceUtils sharepreferenceUtils;
    private boolean doubleBackToExitPressedOnce = false;
    private String name, url, email;
    public static String id;
    private FBSqliteOpenHelper sqliteOpenHelper;
    public static int key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        Intent intent = getIntent();

        key = intent.getIntExtra(LoginActivity.key, 0);
//        Log.d(Constant.TAG, "KeyU: " + key);

        if (key == 1) {
            getIntents();
        } else if (key == 2) {
            getIntentsFB();
        } else if (key == 3) {
            getIntentsGG();
        }
// else {
//            final List<UserFB> arr=sqliteOpenHelper.getFBUser();
//            tvEmail.setText(arr.get(0).getEmail());
//            tvName.setText(arr.get(0).getName());
//            Picasso.with(this).load(arr.get(0).getUrl()).placeholder(R.drawable.iu).error(R.mipmap.ic_launcher).into(imgHeader);
//            imgHeader.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
//                            .setTitle("Thông báo")
//                            .setMessage("Bạn có muốn đăng xuất không?")
//                            .setNegativeButton("Có", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    LoginManager.getInstance().logOut();
//                                    sqliteOpenHelper.deleteTable(1);
//                                    String name = "Lee Ji-eun";
//                                    tvName.setText(name);
//                                    String email = "I love IU";
//                                    tvEmail.setText(email);
//                                    imgHeader.setImageResource(R.drawable.iu);
//
//                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                                    startActivity(intent);
//
//                                }
//                            })
//                            .setPositiveButton("Không", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//
//                                }
//                            });
//
//                    builder.show();
//
//
//                }
//            });
//        }


//        UserFB user =new UserFB(id,name,email,url);
//        sqliteOpenHelper.insert(user);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerToggle.syncState();
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);


        //load default fragment
        loadFragment(HomeFragment.newInstance());


        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        navigationView.setNavigationItemSelectedListener(this);

    }


    private void getIntentsFB() {

        Intent intentFB = getIntent();
        name = intentFB.getStringExtra("name");
        url = intentFB.getStringExtra("url");
        email = intentFB.getStringExtra("email");
        id = intentFB.getStringExtra("id");
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
                                String name = "Lee Ji-eun";
                                tvName.setText(name);
                                String email = "I love IU";
                                tvEmail.setText(email);
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
        name = intent.getStringExtra("nameGG");
        url = intent.getStringExtra("url_gg");
        email = intent.getStringExtra("emailGG");
        tvEmail.setText(email);
        tvName.setText(name);
        Picasso.with(this).load(url).error(R.drawable.iu).into(imgHeader);

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
                                        String name = "Lee Ji-eun";
                                        tvName.setText(name);
                                        String email = "I love IU";
                                        tvEmail.setText(email);
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
        name = intentU.getStringExtra("nameU");
        url = intentU.getStringExtra("urlU");
        email = intentU.getStringExtra("emailU");
        id = intentU.getStringExtra("idU");

        tvName.setText(name);
        tvEmail.setText(email);
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
                                String name = "Lee Ji-eun";
                                tvName.setText(name);
                                String email = "I love IU";
                                tvEmail.setText(email);
                                imgHeader.setImageResource(R.drawable.iu);
                                logOut();
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

            private void logOut() {
                sharepreferenceUtils.savePassWord("");
                sharepreferenceUtils.saveEmail("");
            }
        });
    }

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

        View navHeaderView = navigationView.getHeaderView(0);
        tvEmail = navHeaderView.findViewById(R.id.email_header);
        tvName = navHeaderView.findViewById(R.id.name_header);
        imgHeader = navHeaderView.findViewById(R.id.img_header);
        sharepreferenceUtils = SharepreferenceUtils.newInstance(MainActivity.this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {

            case R.id.bnav_home:
                fragment = HomeFragment.newInstance();
                loadFragment(fragment);
                break;
            case R.id.bnav_sanpham:

                fragment = PhoneFragment.newInstance();
                loadFragment(fragment);
                break;

            case R.id.bnav_news:

                fragment = NewsFragment.newInstance();
                loadFragment(fragment);
                break;

            case R.id.bnav_account:

                fragment = AccountFragment.newInstance();
                loadFragment(fragment);
                break;
            case R.id.nav_home:
                fragment = HomeFragment.newInstance();
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
                Intent intent = new Intent(MainActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
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

    @Override
    protected void onPause() {
        super.onPause();
        LoginManager.getInstance().logOut();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LoginManager.getInstance().logOut();
    }
}
