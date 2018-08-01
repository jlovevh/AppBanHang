package com.tvt.projectcuoikhoa.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.tvt.projectcuoikhoa.R;

public class SearchableActivity extends AppCompatActivity {

    public static final String JARGON = "JARGON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        Bundle appData = getIntent().getBundleExtra(SearchManager.APP_DATA);
        if (appData != null) {
            boolean jargon = appData.getBoolean(SearchableActivity.JARGON);
            Log.d("SIAADAF", "msg: " + jargon);
        }

        handleIntent(getIntent());
    }

    private void doMySearch(String query) {
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("SIAADAF", "msg: " + query);
            doMySearch(query);
        }
    }


}
