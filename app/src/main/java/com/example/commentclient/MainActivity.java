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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private String readResource(int id) {
        InputStream is = this.getResources().openRawResource(id);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
        } catch (IOException e) {

        }
        return sb.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebView myWebView = new WebView(this.getApplicationContext());
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        myWebView.getSettings().setDomStorageEnabled(true);

        setContentView(myWebView);
        // add tasks here
        AndroidBridge bridge = new AndroidBridge(myWebView);
//        bridge.addFunction("emails", new GetEmails());
//        bridge.addFunction("share", new ShareImage(this));
        myWebView.addJavascriptInterface(bridge, "androidBridge");
        myWebView.loadUrl("https://www.stuff.co.nz/business/300259522/housing-policy-what-the-changes-mean-for-homeowners-investors-firsthome-buyers-renters-and-bach-owners#comments");

        myWebView.evaluateJavascript(readResource(R.raw.htmx), null);
        myWebView.evaluateJavascript(readResource(R.raw.android), null);
    }

}