package com.tvt.projectcuoikhoa.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.tvt.projectcuoikhoa.R;

public class NewsEvaluationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ShareDialog shareDialog;
    private ShareLinkContent shareLinkContent;
    private String title, mota, urlAnh, baiviet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tintuc_danh_gia);
        toolbar = findViewById(R.id.toolbar_news);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView tvCreate = (TextView) findViewById(R.id.content_create);
        TextView tvTitle = (TextView) findViewById(R.id.content_title);

        WebView webView = (WebView) findViewById(R.id.web_view);
        shareDialog = new ShareDialog(this);
        Intent intent = getIntent();

        title = intent.getStringExtra("title");
        baiviet = intent.getStringExtra("baiviet");
        String create = intent.getStringExtra("create");
        String tendanhmuc = intent.getStringExtra("tendanhmuc");
        mota = intent.getStringExtra("mota");
        urlAnh = intent.getStringExtra("image");
        toolbar.setTitle("Tin Tức " + tendanhmuc);
        tvTitle.setText(title);
        tvCreate.setText(create);
        webView.setWebViewClient(new WebViewClient());
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadDataWithBaseURL(null, "<style>img{display: inline; height: auto; max-width: 100%;}</style>" + baiviet, "text/html", "charset=utf-8", null);
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
                            .setContentUrl(Uri.parse(baiviet))
                            .build();
                }
                shareDialog.show(shareLinkContent, ShareDialog.Mode.AUTOMATIC);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
