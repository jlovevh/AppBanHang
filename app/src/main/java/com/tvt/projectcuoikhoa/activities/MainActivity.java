package com.tvt.projectcuoikhoa.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
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
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.adapter.RecyclerPhoneAdapter2;
import com.tvt.projectcuoikhoa.adapter.ViewPagerAdapter;
import com.tvt.projectcuoikhoa.api.APIUtils;
import com.tvt.projectcuoikhoa.api.Rename;
import com.tvt.projectcuoikhoa.database.FBSqliteOpenHelper;
import com.tvt.projectcuoikhoa.database.ShoppingCartHelper;
import com.tvt.projectcuoikhoa.fragment.AccountFragment;
import com.tvt.projectcuoikhoa.fragment.HomeFragment;
import com.tvt.projectcuoikhoa.fragment.LapTopFragment;
import com.tvt.projectcuoikhoa.fragment.TabletFragment;
import com.tvt.projectcuoikhoa.fragment.PhoneFragment;
import com.tvt.projectcuoikhoa.fragment.NewsFragment;
import com.tvt.projectcuoikhoa.helper.BottomNavigationBehavior;
import com.tvt.projectcuoikhoa.helper.BottomNavigationViewHelper;
import com.tvt.projectcuoikhoa.model.Cart;
import com.tvt.projectcuoikhoa.model.Phone;
import com.tvt.projectcuoikhoa.model.User;
import com.tvt.projectcuoikhoa.model.UserFB;
import com.tvt.projectcuoikhoa.utils.SharepreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    private Toolbar toolbar;
    public static BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private TextView tvName, tvEmail;
    private ImageView imgHeader;
    private SharepreferenceUtils sharepreferenceUtils;
    private boolean doubleBackToExitPressedOnce = false;
    private SearchView searchView;

    public static ShoppingCartHelper shoppingCartHelper;
    public static int key;
    public static List<User> arr = new ArrayList<>();
    public static List<String> arrUserFB = new ArrayList<>();
    public static List<String> arrUserGG = new ArrayList<>();
    public static String id, idFB, idGG;
    private RecyclerPhoneAdapter2 adapter2;
    private List<Phone> arrPhone = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        getHistoryCart();
        adapter2 = new RecyclerPhoneAdapter2(this, arrPhone);
        APIUtils.getJsonReponse().getALLPhone().enqueue(new Callback<List<Phone>>() {
            @Override
            public void onResponse(Call<List<Phone>> call, Response<List<Phone>> response) {
                arrPhone = response.body();
                Log.d("SIAADAF", "SIZE: " + arrPhone.size());
                adapter2.setData(arrPhone);
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Phone>> call, Throwable t) {

            }
        });

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
//       loadFragment(HomeFragment.newInstance());
        Fragment fragment = HomeFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void getHistoryCart() {
        Intent intent = getIntent();
        key = intent.getIntExtra(LoginActivity.key, 0);
//        Log.d(Constant.TAG, "KeyU: " + key);

        if (key == 1) {
            getIntents();
//            HomeFragment.arrCart= shoppingCartHelper.getCartByIDuser(id);
//            Log.d("SIZEEEEE","USER "+HomeFragment.arrCart.size());
            APIUtils.getJsonReponse().getCartByUserId(id).enqueue(new Callback<List<Cart>>() {
                @Override
                public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                    HomeFragment.arrCart = response.body();
                    Log.d("SIZEEZSD", "SIZE: " + HomeFragment.arrCart.size());
                    for (int i = 0; i < HomeFragment.arrCart.size(); i++) {
                        Log.d("SIZEEZSD", "SIZE: " + HomeFragment.arrCart.get(i).getName());
                        Log.d("SIZEEZSD", "SIZE: " + HomeFragment.arrCart.get(i).getUrl_image());
                        Log.d("SIZEEZSD", "SIZE: " + HomeFragment.arrCart.get(i).getPrice());
                        Log.d("SIZEEZSD", "SIZE: " + HomeFragment.arrCart.get(i).getSoluong());
                    }
                }

                @Override
                public void onFailure(Call<List<Cart>> call, Throwable t) {

                }
            });

        } else if (key == 2) {
            getIntentsFB();
            //     HomeFragment.arrCart= shoppingCartHelper.getCartByIDuser(idFB);
            //     Log.d("SIZEEEEE","FB "+HomeFragment.arrCart.size());
            APIUtils.getJsonReponse().getCartByUserId(idFB).enqueue(new Callback<List<Cart>>() {
                @Override
                public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                    HomeFragment.arrCart = response.body();
                    Log.d("SIZEEZSD", "SIZE: " + HomeFragment.arrCart.size());

                }

                @Override
                public void onFailure(Call<List<Cart>> call, Throwable t) {

                }
            });
        } else if (key == 3) {
            getIntentsGG();
            APIUtils.getJsonReponse().getCartByUserId(idGG).enqueue(new Callback<List<Cart>>() {
                @Override
                public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                    HomeFragment.arrCart = response.body();
                    Log.d("SIZEEZSD", "SIZE: " + HomeFragment.arrCart.size());
                }

                @Override
                public void onFailure(Call<List<Cart>> call, Throwable t) {

                }
            });
            //    HomeFragment.arrCart= shoppingCartHelper.getCartByIDuser(idGG);
            //   Log.d("SIZEEEEE","GG "+HomeFragment.arrCart.size());
        }
    }


    private void getIntentsFB() {

        Intent intentFB = getIntent();
        String name = intentFB.getStringExtra("name");
        String url = intentFB.getStringExtra("url");
        String email = intentFB.getStringExtra("email");
        idFB = intentFB.getStringExtra("id");
        arrUserFB.add(name);
        arrUserFB.add(email);
        arrUserFB.add(idFB);
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
        String nameGG = intent.getStringExtra("nameGG");
        String urlGG = intent.getStringExtra("url_gg");
        String emailGG = intent.getStringExtra("emailGG");
        idGG = intent.getStringExtra("idGG");
        arrUserGG.add(nameGG);
        arrUserGG.add(emailGG);
        arrUserGG.add(idGG);
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
        User user = (User) intentU.getSerializableExtra("user");
        String nameUser = user.getName();
        String emailUser = user.getEmail();
        String urlU = user.getImage();
        id = user.getId();
        arr.add(user);

        tvName.setText(nameUser);
        tvEmail.setText(emailUser);
        Picasso.with(this).load(urlU).placeholder(R.drawable.iu).error(R.mipmap.ic_launcher).into(imgHeader);

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
//                sharepreferenceUtils.savePassWord("");
//                sharepreferenceUtils.saveEmail("");
            }
        });
    }

    private void initViews() {
        shoppingCartHelper = new ShoppingCartHelper(this);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.draw_layout);
        navigationView = findViewById(R.id.nav_layout);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);


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
            case R.id.action_search:
                onSearchRequested();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);
        searchView.setBackgroundColor(getResources().getColor(android.R.color.white));
        searchView.setPadding(0, 20, 0, 20);
        searchView.setQueryHint("Type name");
        ImageView searchViewIcon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        ViewGroup linearLayoutSearchView =
                (ViewGroup) searchViewIcon.getParent();
        linearLayoutSearchView.removeView(searchViewIcon);
        linearLayoutSearchView.addView(searchViewIcon);
        searchViewIcon.setAdjustViewBounds(true);
        searchViewIcon.setMaxWidth(0);

        searchViewIcon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        searchViewIcon.setImageDrawable(null);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setSubmitButtonEnabled(true);
        //  searchView.setPadding(0,30,0,30);


        return true;
    }

    @Override
    public boolean onSearchRequested() {
        Bundle appData = new Bundle();
        appData.putBoolean(SearchableActivity.JARGON, true);
        startSearch(null, false, appData, false);
        return super.onSearchRequested();
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
    protected void onStop() {
        super.onStop();
        LoginManager.getInstance().logOut();
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoginManager.getInstance().logOut();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, "onQueryTextSubmit", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, SearchableActivity.class);
        startActivity(intent);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Toast.makeText(this, "onQueryTextChange", Toast.LENGTH_SHORT).show();
        return false;
    }
}
