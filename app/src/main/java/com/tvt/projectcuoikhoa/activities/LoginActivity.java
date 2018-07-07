package com.tvt.projectcuoikhoa.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.tvt.projectcuoikhoa.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 123;
    private Button btnLogin, btnLoginFB;
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    private ProgressDialog dialog;
    private Intent intent;
    private SignInButton signInButton;
    private LoginButton loginButton;

    private GoogleApiClient googleApiClient;
    @SuppressLint("StaticFieldLeak")
    public static GoogleSignInClient googleSignInClient;

    private Collection<String> permissions = Arrays.asList("public_profile ", "email", "user_birthday", "user_friends");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        initViews();
        callbackManager = CallbackManager.Factory.create();

        printKeyHash(this);
//        if (AccessToken.getCurrentAccessToken() != null) {
//                btnLoginFB.setText("Hello");
//        }

//        isLoggedInFB();
        googleLogin();


    }

    private void initViews() {
        btnLogin = findViewById(R.id.btn_login);
        btnLoginFB = findViewById(R.id.btn_loginFB);
        loginButton = findViewById(R.id.login_button);
        btnLogin.setOnClickListener(this);
        btnLoginFB.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
    }

    private void googleLogin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
//        googleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account != null) {
            updateUI(account);
        }


    }

    private void updateUI(GoogleSignInAccount account) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        String name = account.getDisplayName();
        String email = account.getEmail();
        String url = Objects.requireNonNull(account.getPhotoUrl()).toString();

        intent.putExtra("nameGG", name);
        intent.putExtra("emailGG", email);
        intent.putExtra("url_gg", url);

        startActivity(intent);


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

                Log.d(Const.TAG, "KeyHash" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
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
            Log.w(Const.TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.btn_login:
                intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_loginFB:
                loginFB2();
                break;

            case R.id.sign_in_button:
                signIn();
                break;

            case R.id.login_button:
//                loginFB();
                break;

        }

    }

    private void loginFB2() {
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
                        Log.d(Const.TAG,"reponse: " +response.toString());


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

            }
        });
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void loginFB() {
        loginButton.setReadPermissions(Arrays.asList("public_profile ", "email", "user_birthday", "user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
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
//                            Log.d(Const.TAG,"reponse: " +response.toString());


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
                Toast.makeText(LoginActivity.this, "" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void isLoggedInFB() {

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
    }

    private void getFacebookData(JSONObject object) {
        String id = null, name = null, email, friends, birthday, url;
        try {
            id = object.getString("id");
            birthday = object.getString("birthday");
            friends = object.getJSONObject("friends").getJSONObject("summary").getString("total_count");
            email = object.getString("email");
            name = object.getString("name");
            URL profile_picture = new URL("https://graph.facebook.com/" + id + "/picture?type=large");
            url = profile_picture.toString();
            Intent intentFB = new Intent(LoginActivity.this, MainActivity.class);
            intentFB.putExtra("name", name);
            intentFB.putExtra("email", email);
            intentFB.putExtra("url", url);
            Log.d(Const.TAG, "url: " + url);
            Log.d(Const.TAG, "id: " + id);
            Log.d(Const.TAG, "name: " + name);
            Log.d(Const.TAG, "email: " + email);
            Log.d(Const.TAG, "birthday: " + birthday);
            Log.d(Const.TAG, "friends: " + friends);
            startActivity(intentFB);

        } catch (JSONException | MalformedURLException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(Const.TAG, connectionResult + "");
    }
}
