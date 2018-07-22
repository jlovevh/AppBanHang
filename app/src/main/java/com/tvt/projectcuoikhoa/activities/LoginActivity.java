package com.tvt.projectcuoikhoa.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
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
import com.tvt.projectcuoikhoa.model.User;
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
    private Button btnLogin, btnLoginFB;
    private TextView tvSignUp;
    private CallbackManager callbackManager;
    public static LoginManager loginManager;
    private ProgressDialog dialog, progressDialog;
    private SignInButton signInButton;
    private GoogleApiClient googleApiClient;
    public static boolean isLogin = false;
    private EditText edtEmail, edtPass;
    private ProgressDialog progressDialogLogin;
    private CheckBox checkBox;
    private SharepreferenceUtils sharepreferenceUtils;
    @SuppressLint("StaticFieldLeak")
    public static GoogleSignInClient googleSignInClient;
//    , "user_birthday", "user_friends"

    private Collection<String> permissions = Arrays.asList("public_profile ", "email");
    public static String key = "KeyCCnhe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLoggedInFB();
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



    }


    private void initViews() {
        btnLogin = findViewById(R.id.btn_login);
        btnLoginFB = findViewById(R.id.btn_loginFB);
        btnLogin.setOnClickListener(this);
        btnLoginFB.setOnClickListener(this);
        signInButton = findViewById(R.id.sign_in_button);
        tvSignUp = findViewById(R.id.tv_sign_up);
        edtEmail = findViewById(R.id.email);
        edtPass = findViewById(R.id.password);
        checkBox = findViewById(R.id.checkBox);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                tv.setTextSize(18);
                return;
            }
        }
    }

    private void googleLogin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestEmail()
                .build();
//        googleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);


        updateUI(account);


    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            String name = account.getDisplayName();
            String email = account.getEmail();
            String url = null;
            if (account.getPhotoUrl() == null) {
                url = Constant.URL_DEFAULT_PROFILE_GOOGLE;
            } else {
                url = account.getPhotoUrl().toString();
            }
            intent.putExtra("nameGG", name);
            intent.putExtra("emailGG", email);
            intent.putExtra("url_gg", url);
            intent.putExtra(key, 3);
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
            progressDialog.dismiss();
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
            case R.id.btn_loginFB:
                loginFB();

                break;

            case R.id.sign_in_button:
                signIn();
                break;

            case R.id.tv_sign_up:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
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
//                intent.putExtra("nameU", "Nene");
//                intent.putExtra("emailU", "neneIloveyou@gmail.com");
//                intent.putExtra("urlU", Constant.URL_DEFAULT_PROFILE_GOOGLE);
                    intent.putExtra("nameU", user.getName());
                    intent.putExtra("emailU", user.getEmail());
                    intent.putExtra("urlU", user.getImage());
                    intent.putExtra(key, 1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
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
            }
        });

    }

    private void loginFB() {
        loginManager = LoginManager.getInstance();
        loginManager.logInWithReadPermissions(this, permissions);

        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                dialog = new ProgressDialog(LoginActivity.this);
                dialog.setMessage("Retrieving data...");
                dialog.show();
                String accesstoken = loginResult.getAccessToken().getToken();
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        dialog.dismiss();
                        getFacebookData(object);
//                        Log.d(Constant.TAG, "reponse: " + response.toString());


                    }
                });


                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email,name,birthday,friends");
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
        progressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Retrieving data...");
        progressDialog.show();
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }


    public void isLoggedInFB() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }
    }

    private void getFacebookData(JSONObject object) {
        String id = null, name = null, email, friends, birthday, url;
        try {
            id = object.getString("id");
//            birthday = object.getString("birthday");
//            friends = object.getJSONObject("friends").getJSONObject("summary").getString("total_count");
            email = object.getString("email");
            name = object.getString("name");
            URL profile_picture = new URL("https://graph.facebook.com/" + id + "/picture?type=large");
            url = profile_picture.toString();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("email", email);
            intent.putExtra("url", url);
            intent.putExtra(key, 2);
//            Log.d(Constant.TAG, "url: " + url);
//            Log.d(Constant.TAG, "name: " + name);
//            Log.d(Constant.TAG, "email: " + email);
//            Log.d(Constant.TAG, "birthday: " + birthday);
//            Log.d(Constant.TAG, "friends: " + friends);
            startActivity(intent);

        } catch (JSONException | MalformedURLException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(Constant.TAG, connectionResult + "");
    }
}
