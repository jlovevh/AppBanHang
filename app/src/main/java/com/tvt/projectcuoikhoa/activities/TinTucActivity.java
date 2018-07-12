package com.tvt.projectcuoikhoa.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.tvt.projectcuoikhoa.R;

public class TinTucActivity extends AppCompatActivity {

    private TextView tvTitle, tvCreate;
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tintuc);
        tvCreate = (TextView) findViewById(R.id.content_create);
        tvTitle = (TextView) findViewById(R.id.content_title);

        webView = (WebView) findViewById(R.id.web_view_tin_tuc);

        Intent intent = getIntent();


        String title = intent.getStringExtra("title");
        String create = intent.getStringExtra("create");


        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setPluginState(WebSettings.PluginState.ON);

        webView.setWebViewClient(new WebViewClient());
        String baiviet = intent.getStringExtra("baiviet");
        tvTitle.setText(title);
        tvCreate.setText(create);
        webView.loadDataWithBaseURL(null, "<style>img{display: inline; height: auto; max-width: 100%;}</style>" + baiviet, "text/html", "charset=utf-8", null);
    }
}
