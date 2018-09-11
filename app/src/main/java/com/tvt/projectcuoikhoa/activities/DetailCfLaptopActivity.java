package com.tvt.projectcuoikhoa.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tvt.projectcuoikhoa.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailCfLaptopActivity extends AppCompatActivity {


    @BindView(R.id.web_view_detail_cauhinh_lap)
    WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_config_laptop);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String detail = intent.getStringExtra("cauhinhchitiet");

        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        webView.setWebViewClient(new WebViewClient());

        webView.loadDataWithBaseURL(null, detail, "text/html", "charset=utf-8", null);
    }
}
