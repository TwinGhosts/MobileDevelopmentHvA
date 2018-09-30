package com.studentportal.twinghosts.studentportal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ShowPortalActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Basic init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_portal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Custom actions
        webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());

        if(getIntent().getExtras() != null) {
            String url = getIntent().getExtras().getString("web_url");
            System.out.println(url);
            if(URLUtil.isValidUrl(url)) {
                webView.loadUrl(url);
            } else {
                Toast.makeText(this, "Error: Couldn't load url", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
