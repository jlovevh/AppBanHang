package com.tvt.projectcuoikhoa.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.api.APIUtils;
import com.tvt.projectcuoikhoa.database.FBSqliteOpenHelper;
import com.tvt.projectcuoikhoa.model.User;
import com.tvt.projectcuoikhoa.model.UserFB;
import com.tvt.projectcuoikhoa.utils.Connectivity;
import com.tvt.projectcuoikhoa.utils.Constant;
import com.tvt.projectcuoikhoa.utils.SharepreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 123;
    public static final String FB_NAME = "fbLogin";
    private Button btnLogin;
    private LoginButton loginButton;
    private TextView tvSignUp;
    public static CallbackManager callbackManager;
    private ProgressDialog dialog;
    private MaterialDialog materialDialog;
    private SignInButton signInButton;
    private EditText edtEmail, edtPass;
    private ProgressDialog progressDialogLogin;
    private CheckBox checkBox;
    private SharepreferenceUtils sharepreferenceUtils;
    private Intent intent;
    private LoginManager loginManager;
    private TextView tvResetPassword;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @SuppressLint("StaticFieldLeak")
    public static GoogleSignInClient googleSignInClient;


    private Collection<String> permissions = Arrays.asList("public_profile ", "email", "user_birthday", "user_friends", "user_gender");
    public static String key = "KeyCCnhe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        Connectivity.isConnected(this);
        Connectivity.isConnectedMobile(this);
        Connectivity.isConnectedWifi(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        initViews();
        callbackManager = CallbackManager.Factory.create();


        printKeyHash(this);

        setGooglePlusButtonText(signInButton, "Sign in with Google");
        googleLogin();


        sharepreferenceUtils = SharepreferenceUtils.newInstance(LoginActivity.this);
        sharepreferenceUtils.getSharepreference(LoginActivity.this);
        edtEmail.setText(sharepreferenceUtils.getEmail());
        edtPass.setText(sharepreferenceUtils.getPassWord());
        if (isLoggedIn()) {
            sharedPreferences = getSharedPreferences(FB_NAME, MODE_PRIVATE);
            if (!TextUtils.isEmpty(sharedPreferences.getString("id", ""))) {
                String id = sharedPreferences.getString("id", "");
                String name = sharedPreferences.getString("name", "");
                String email = sharedPreferences.getString("email", "");
                String url = sharedPreferences.getString("url", "");
                intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("url", url);
                intent.putExtra("id", id);
                intent.putExtra(key, 2);
                startActivity(intent);
                finish();
            }

        }


    }


    private void initViews() {
        btnLogin = findViewById(R.id.btn_login);
        loginButton = findViewById(R.id.loginButton);
        btnLogin.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        signInButton = findViewById(R.id.sign_in_button);
        tvSignUp = findViewById(R.id.tv_sign_up);
        edtEmail = findViewById(R.id.email);
        edtPass = findViewById(R.id.password);
        checkBox = findViewById(R.id.checkBox);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        tvResetPassword = findViewById(R.id.tv_reset_password);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        tvResetPassword.setOnClickListener(this);
    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                tv.setTextSize(16);
                return;
            }
        }
    }

    private void googleLogin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);


        updateUI(account);


    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            String name = account.getDisplayName();
            String email = account.getEmail();
            String id = account.getId();
            String url = null;
            if (account.getPhotoUrl() == null) {
                url = Constant.URL_DEFAULT_PROFILE_GOOGLE;
            } else {
                url = account.getPhotoUrl().toString();
            }
            intent.putExtra("nameGG", name);
            intent.putExtra("emailGG", email);
            intent.putExtra("url_gg", url);
            intent.putExtra("idGG", id);
            intent.putExtra(key, 3);
            Log.d("Googless", "INfor: " + name + email + url + id);
            startActivity(intent);
        }


    }

    @SuppressLint("PackageManagerGetSignatures")
    private void printKeyHash(LoginActivity context) {

        PackageInfo packageInfo;
        String packageName = context.getApplicationContext().getPackageName();

        //Retriving package info
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                Log.d(Constant.TAG, "KeyHash" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
            materialDialog.dismiss();
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(Constant.TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (edtEmail.getText().toString().isEmpty() || edtPass.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Bạn chưa nhập thông tin tài khoản", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(edtEmail.getText().toString(), edtPass.getText().toString());
                }

                break;
            case R.id.loginButton:
                loginFB();

                break;

            case R.id.sign_in_button:
                signIn();
                break;

            case R.id.tv_sign_up:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_reset_password:
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
                break;

        }

    }

    private void loginUser(final String email, final String pass) {

        progressDialogLogin = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        progressDialogLogin.setMessage("Loading...Please wait");

        APIUtils.getJsonReponse().getAllUser(email, pass).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if (response.isSuccessful()) {
                    List<User> users = response.body();

                    Log.e("User", "User: " + users.size());
                    User user = users.get(0);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("user", user);
                    intent.putExtra(key, 1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_left, 0);
                    if (checkBox.isChecked()) {

                        sharepreferenceUtils.saveEmail(email);
                        sharepreferenceUtils.savePassWord(pass);
                        checkBox.setChecked(true);
                    } else {
                        checkBox.setChecked(false);
                        sharepreferenceUtils.saveEmail("");
                        sharepreferenceUtils.savePassWord("");
                    }
                    progressDialogLogin.dismiss();
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Email hoặc mật khẩu không chính xác.Xin vui lòng nhập lại", Toast.LENGTH_LONG).show();
                Log.d("TAGGGG", "MESS: " + t.getMessage());
            }
        });

    }

    private void loginFB() {
//       loginManager = LoginManager.getInstance();
//       loginManager.logInWithReadPermissions(this, permissions);
        loginButton.setReadPermissions(Arrays.asList("public_profile ", "email"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override

            public void onSuccess(LoginResult loginResult) {

                dialog = new ProgressDialog(LoginActivity.this);
                dialog.setMessage("Loading data...Please wait");
                dialog.show();
                String accesstoken = loginResult.getAccessToken().getToken();
                sharedPreferences = getSharedPreferences(FB_NAME, MODE_PRIVATE);
                editor = sharedPreferences.edit();
                Log.d("dklj", "token: " + accesstoken);
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        dialog.dismiss();
                        getFacebookData(object);
//                        Log.d(Constant.TAG, "reponse: " + response.toString());


                    }
                });


                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email,name");
//                ,birthday,friends
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e(Constant.TAG, "Error FB: " + error.toString());
            }
        });
    }

    private void signIn() {
        materialDialog = new MaterialDialog.Builder(this)
                .content(R.string.please_wait)
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .show();
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }


    public static boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Profile profile = Profile.getCurrentProfile();
        //Attempts to recognize if the user has logged in before
        return accessToken != null && profile != null;
    }

    private void getFacebookData(JSONObject object) {
        String friends, birthday, email, name, url;

        try {


            String id = object.getString("id");
//            birthday = object.getString("age_range");
//            friends = object.getJSONObject("friends").getJSONObject("summary").getString("total_count");
            email = object.getString("email");
            name = object.getString("name");
            //     String address=object.getString("address");
            if (email == null) {
                Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
            }
            URL profile_picture = new URL("https://graph.facebook.com/" + id + "/picture?type=large");
            url = profile_picture.toString();


            editor.putString("id", id);
            editor.putString("name", name);
            editor.putString("email", email);
            editor.putString("url", url);
            editor.apply();
            // Log.d("FACEBOOOKK",birthday+" : "+friends+" : "+address);
            intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("email", email);
            intent.putExtra("url", url);
            intent.putExtra("id", id);
            intent.putExtra(key, 2);
            startActivity(intent);

        } catch (JSONException | MalformedURLException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(Constant.TAG, connectionResult + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
