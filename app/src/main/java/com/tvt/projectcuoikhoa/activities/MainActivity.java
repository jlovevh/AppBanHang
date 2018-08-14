package com.tvt.projectcuoikhoa.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
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
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;
import com.squareup.picasso.Picasso;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.adapter.CustomSuggestionsAdapter;
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
import com.tvt.projectcuoikhoa.model.LapTop;
import com.tvt.projectcuoikhoa.model.Phone;
import com.tvt.projectcuoikhoa.model.Product;
import com.tvt.projectcuoikhoa.model.Tablet;
import com.tvt.projectcuoikhoa.model.User;
import com.tvt.projectcuoikhoa.model.UserFB;
import com.tvt.projectcuoikhoa.utils.SharepreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener, MaterialSearchBar.OnSearchActionListener, CustomSuggestionsAdapter.itemSuggestionOnClick {

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
    private List<Phone> arrPhone = new ArrayList<>();
    private List<LapTop> arrLaptop = new ArrayList<>();
    private List<Tablet> arrTablet = new ArrayList<>();

    private MaterialSearchBar searchBar;

    private CustomSuggestionsAdapter adapter;
    private List<Product> arrProduct = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        getHistoryCart();

//        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
//        drawerToggle.syncState();
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

//        load default fragment
        loadFragment(HomeFragment.newInstance());
//        fragment = HomeFragment.newInstance();
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.layout, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        navigationView.setNavigationItemSelectedListener(this);


        getDataSearch();
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        adapter = new CustomSuggestionsAdapter(inflater, this);


        adapter.setSuggestions(arrProduct);
        searchBar.setCustomSuggestionAdapter(adapter);
        //searchBar.setLastSuggestions(arrProduct);
        adapter.setItemSuggestionOnClick(MainActivity.this);
        searchBar.setMaxSuggestionCount(2);

        searchBar.setOnSearchActionListener(this);
        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //  searchBar.hideSuggestionsList();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("LOG_TAG", getClass().getSimpleName() + " text changed " + searchBar.getText());
                // send the entered text to our filter and let it manage everything
                // searchBar.showSuggestionsList();
                adapter.getFilter().filter(searchBar.getText());
                adapter.setItemSuggestionOnClick(MainActivity.this);


            }

            @Override
            public void afterTextChanged(Editable s) {
                //adapter.getFilter().filter(searchBar.getText());
            }
        });


    }

    private void getDataSearch() {

        APIUtils.getJsonReponse().getPhoneSearch().enqueue(new Callback<List<Phone>>() {
            @Override
            public void onResponse(Call<List<Phone>> call, Response<List<Phone>> response) {
                arrPhone = response.body();
                Log.d("SearchPhone", "SIZE: " + arrPhone.size());
                for (int i = 0; i < arrPhone.size(); i++) {

                    arrProduct.add(new Product(arrPhone.get(i).getId(), arrPhone.get(i).getName(), arrPhone.get(i).getImage(), arrPhone.get(i).getPrice(), arrPhone.get(i).getTendanhmuc()));
                }
                Log.d("SearchPhone", "SIZEProduct: " + arrProduct.size());

            }

            @Override
            public void onFailure(Call<List<Phone>> call, Throwable t) {
                Log.e("Search", "erro0r" + t.getMessage());
            }
        });


        APIUtils.getJsonReponse().getlaptopSearch().enqueue(new Callback<List<LapTop>>() {
            @Override
            public void onResponse(Call<List<LapTop>> call, Response<List<LapTop>> response) {
                arrLaptop = response.body();
                Log.d("SearchPhone", "SIZELap: " + arrLaptop.size());
                for (int i = 0; i < arrLaptop.size(); i++) {
                    Product product = new Product(arrLaptop.get(i).getId(), arrLaptop.get(i).getName(), arrLaptop.get(i).getImage(), arrLaptop.get(i).getPrice(), arrLaptop.get(i).getTendanhmuc());
                    List<Product> arr = new ArrayList<>();
                    arr.add(product);
                    arrProduct.addAll(arr);
                }
                Log.d("SearchPhone", "SIZEProductLap: " + arrProduct.size());


            }

            @Override
            public void onFailure(Call<List<LapTop>> call, Throwable t) {

            }
        });

        APIUtils.getJsonReponse().getTabletSearch().enqueue(new Callback<List<Tablet>>() {
            @Override
            public void onResponse(Call<List<Tablet>> call, Response<List<Tablet>> response) {
                arrTablet = response.body();
                Log.d("SearchPhone", "SIZETablet: " + arrTablet.size());
                for (int i = 0; i < arrTablet.size(); i++) {
                    Product product = new Product(arrTablet.get(i).getId(), arrTablet.get(i).getName(), arrTablet.get(i).getImage(), arrTablet.get(i).getPrice(), arrTablet.get(i).getTendanhmuc());
                    List<Product> arr = new ArrayList<>();
                    arr.add(product);
                    arrProduct.addAll(arr);
                }

                Log.d("SearchPhone", "SIZEProductTab: " + arrProduct.size());
            }

            @Override
            public void onFailure(Call<List<Tablet>> call, Throwable t) {

            }
        });

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
                                SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.FB_NAME, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("id", "");
                                editor.putString("name", "");
                                editor.putString("email", "");
                                editor.putString("url", "");
                                editor.apply();
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
                sharepreferenceUtils.savePassWord("");
                sharepreferenceUtils.saveEmail("");
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
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);


        View navHeaderView = navigationView.getHeaderView(0);
        tvEmail = navHeaderView.findViewById(R.id.email_header);
        tvName = navHeaderView.findViewById(R.id.name_header);
        imgHeader = navHeaderView.findViewById(R.id.img_header);
        sharepreferenceUtils = SharepreferenceUtils.newInstance(MainActivity.this);
        searchBar = findViewById(R.id.searchBar);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.bnav_home:
                Fragment fragment = HomeFragment.newInstance();
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
                overridePendingTransition(0, R.anim.anim_exit);
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

//        switch (item.getItemId()) {
//            case R.id.action_giohang:
//                Intent intent = new Intent(MainActivity.this, ShoppingCartActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.action_search:
//                Intent intentS = new Intent(MainActivity.this, SearchableActivity.class);
//                startActivity(intentS);
//                break;
//        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.main_menu, menu);


        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.draw_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

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
    public void onSearchStateChanged(boolean enabled) {

        if (!enabled) {
            adapter.setSuggestions(arrProduct);
        }

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        Toast.makeText(this, "" + text, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onButtonClicked(int buttonCode) {

        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_NAVIGATION:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                break;
            case MaterialSearchBar.BUTTON_BACK:
                searchBar.disableSearch();
                break;
        }
    }

    @Override
    public void onClickItemSuggesstion(int position) {
        for (int i = 0; i < arrPhone.size(); i++) {
            if (arrProduct.get(position).getDmSP().equals("Điện Thoại") && Integer.parseInt(arrProduct.get(position).getIdSP()) == Integer.parseInt(arrPhone.get(i).getId())) {
                Intent intent = new Intent(MainActivity.this, DetailPhoneActivity.class);
                Bundle bundle = new Bundle();
                Phone phone = arrPhone.get(i);
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
                bundle.putParcelableArrayList("ratingPhone", (ArrayList<? extends Parcelable>) HomeFragment.arrRating);
                bundle.putParcelableArrayList("commentPhone", (ArrayList<? extends Parcelable>) HomeFragment.arrComment);
                intent.putExtra("bundle", bundle);
                Toast.makeText(this, "" + position + i, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            }
        }


        for (int i = 0; i < arrLaptop.size(); i++) {
            if (arrProduct.get(position).getDmSP().equals("Laptop") && Integer.parseInt(arrProduct.get(position).getIdSP()) == Integer.parseInt(arrLaptop.get(i).getId())) {
                Intent intent = new Intent(this, DetailLaptopActivity.class);
                Bundle bundle = new Bundle();
                LapTop lapTop = arrLaptop.get(position);
                bundle.putString("id", lapTop.getId());
                bundle.putString("name", lapTop.getName());
                bundle.putString("price", lapTop.getPrice());
                bundle.putString("status", lapTop.getStatus());
                bundle.putString("image", lapTop.getImage());
                bundle.putString("evaluation", lapTop.getEvaluation());
                bundle.putString("promo1", lapTop.getPromo1());
                bundle.putString("promo2", lapTop.getPromo2());
                bundle.putString("promo3", lapTop.getPromo3());
                bundle.putString("tag", lapTop.getTag());
                bundle.putString("createAt", lapTop.getCreateAt());
                bundle.putString("gioithieu", lapTop.getGioithieu());
                bundle.putString("manhinh", lapTop.getManhinh());
                bundle.putString("ocung", lapTop.getOcung());
                bundle.putString("dohoa", lapTop.getDohoa());
                bundle.putString("ram", lapTop.getRam());
                bundle.putString("ketnoi", lapTop.getKetnoi());
                bundle.putString("cpu", lapTop.getCpu());
                bundle.putString("tl", lapTop.getTrongluong());
                bundle.putString("hdh", lapTop.getHedieuhanh());
                bundle.putString("detail", lapTop.getChitietcauhinh());
                bundle.putString("urlBanner", lapTop.getUrlBanner());
                bundle.putString("tendanhmuc", lapTop.getTendanhmuc());

                // Log.d("SIÁZASA","SIZE: "+arrRatingByIDSp.size());
                bundle.putParcelableArrayList("ratingLaptop", (ArrayList<? extends Parcelable>) HomeFragment.arrRating);
                bundle.putParcelableArrayList("commentlap", (ArrayList<? extends Parcelable>) HomeFragment.arrCommentLap);

                intent.putExtra("bundle", bundle);
                Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            }
        }

        for (int i = 0; i < arrTablet.size(); i++) {
            if (arrProduct.get(position).getDmSP().equals("Máy Tính Bảng") && Integer.parseInt(arrProduct.get(position).getIdSP()) == Integer.parseInt(arrTablet.get(i).getId())) {
                Intent intent = new Intent(this, DetailTabletActivity.class);
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
                bundle.putParcelableArrayList("ratingTablet", (ArrayList<? extends Parcelable>) HomeFragment.arrRating);
                bundle.putParcelableArrayList("commentTablet", (ArrayList<? extends Parcelable>) HomeFragment.arrCommentTablet);

                intent.putExtra("bundle", bundle);
                Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            }
        }





    }
}
