package com.example.commentclient;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.webkit.WebViewAssetLoader;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebView myWebView = new WebView(this.getApplicationContext());
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        myWebView.getSettings().setDomStorageEnabled(true);

        // add implementation "androidx.webkit:webkit:1.2.0" to build.gradle
        final WebViewAssetLoader assetLoader = new WebViewAssetLoader.Builder()
                .addPathHandler("/assets/", new WebViewAssetLoader.AssetsPathHandler(this))
                .build();
        NaughtyWebViewClient client = new NaughtyWebViewClient(assetLoader);
        client.addToBlackList("stuff.coral.coralproject.net");

        myWebView.setWebViewClient(client);

        setContentView(myWebView);
        // add tasks here
        AndroidBridge bridge = new AndroidBridge(myWebView);
//        bridge.addFunction("emails", new GetEmails());
//        bridge.addFunction("share", new ShareImage(this));
        myWebView.addJavascriptInterface(bridge, "androidBridge");
        myWebView.loadUrl("https://appassets.androidplatform.net/assets/www/index.html");
    }

}