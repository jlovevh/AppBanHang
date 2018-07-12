package com.tvt.projectcuoikhoa.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.tvt.projectcuoikhoa.R;

public class TinTucDanhGiaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tintuc_danh_gia);

        TextView tvCreate = (TextView) findViewById(R.id.content_create);
        TextView tvTitle = (TextView) findViewById(R.id.content_title);

        WebView webView = (WebView) findViewById(R.id.web_view);

        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String baiviet = intent.getStringExtra("baiviet");
        String create = intent.getStringExtra("create");
        tvTitle.setText(title);
        tvCreate.setText(create);
        webView.setWebViewClient(new WebViewClient());
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadDataWithBaseURL(null, "<style>img{display: inline; height: auto; max-width: 100%;}</style>" + baiviet, "text/html", "charset=utf-8", null);
    }
}
