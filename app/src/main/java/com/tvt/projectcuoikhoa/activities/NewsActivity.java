package com.tvt.projectcuoikhoa.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;
import com.tvt.projectcuoikhoa.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewsActivity extends AppCompatActivity {

    private TextView tvTitle, tvCreate;
    private WebView webView;
    private Toolbar toolbar;
    private String urlAnh, title, mota;
    private ShareDialog shareDialog;
    private ShareLinkContent shareLinkContent;
    private SharePhoto sharePhoto;
    private SharePhotoContent sharePhotoContent;
    private ShareVideo shareVideo;
    private ShareVideoContent shareVideoContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tintuc);
        tvCreate = (TextView) findViewById(R.id.content_create);
        tvTitle = (TextView) findViewById(R.id.content_title);
        toolbar = findViewById(R.id.toolbar_new);
        webView = (WebView) findViewById(R.id.web_view_tin_tuc);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        shareDialog = new ShareDialog(NewsActivity.this);
        Intent intent = getIntent();


        title = intent.getStringExtra("title");
        String create = intent.getStringExtra("create");
        String tendanhmuc = intent.getStringExtra("tendanhmuc");
        urlAnh = intent.getStringExtra("image");
        mota = intent.getStringExtra("mota");
        Log.d("URLANH", "URL: " + urlAnh);

        toolbar.setTitle("Tin Tức " + tendanhmuc);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setPluginState(WebSettings.PluginState.ON);

        webView.setWebViewClient(new WebViewClient());
        String baiviet = intent.getStringExtra("baiviet");
        tvTitle.setText(title);
        tvCreate.setText(create);
        webView.loadDataWithBaseURL(
                null, "<style>img{display: inline; height: auto; max-width: 100%;}</style>" + baiviet, "text/html", "charset=utf-8", null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.share:
                if (ShareDialog.canShow(ShareLinkContent.class))

                {
                    shareLinkContent = new ShareLinkContent.Builder()
                            .setQuote("Connect on a global scale.")
                            .setContentTitle(title)
                            .setContentDescription(mota)
                            .setContentUrl(Uri.parse(urlAnh))
                            .build();
                }
                shareDialog.show(shareLinkContent, ShareDialog.Mode.AUTOMATIC);

//                                  Bitmap image = getBitmapFromURL(urlAnh);
//                sharePhoto = new SharePhoto.Builder()
//                       .setBitmap(image)
//                        .setImageUrl(Uri.parse(urlAnh))
//                        .setCaption(title)
//
//                       .build();
//                sharePhotoContent = new SharePhotoContent.Builder()
//                        .addPhoto(sharePhoto)
//                        .build();
//
//                shareDialog.show(sharePhotoContent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
