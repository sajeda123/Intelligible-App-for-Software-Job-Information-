package com.cse.cou.praptimoni.softwarefirmsbd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebsiteLink extends AppCompatActivity {
WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_link);

        getSupportActionBar().hide();
        webView=findViewById(R.id.webview);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        String url=getIntent().getStringExtra("link").replace("Website : ","");
        Log.d("sssss", "onCreate: "+url);
        webView.loadUrl(url);
    }
}
