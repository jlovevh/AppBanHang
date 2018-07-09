package com.tvt.projectcuoikhoa.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.utils.Const;

public class ContentActivity extends AppCompatActivity {

    private TextView tvTitle,tvCreate;
    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        tvCreate=(TextView) findViewById(R.id.content_create);
        tvTitle=(TextView) findViewById(R.id.content_title);

        webView=(WebView) findViewById(R.id.web_view_tin_tuc);

        Intent intent =getIntent();

         String title=intent.getStringExtra("title");
        String baiviet=intent.getStringExtra("baiviet");
        String create=intent.getStringExtra("create");

        tvTitle.setText(title);
        tvCreate.setText(create);
        Log.d(Const.TAG,baiviet);
        webView.setWebViewClient(new WebViewClient());
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadData(baiviet,"text/html;charset=UTF-8",null);
    }
}
