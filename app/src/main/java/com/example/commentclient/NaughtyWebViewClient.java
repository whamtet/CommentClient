package com.example.commentclient;

import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;
import androidx.webkit.WebViewAssetLoader;

import java.util.HashSet;
import java.util.Set;

public class NaughtyWebViewClient extends WebViewClient {
    private final WebViewAssetLoader assetLoader;
    private final Set<String> blacklist = new HashSet<>();

    public NaughtyWebViewClient(WebViewAssetLoader loader) {
        assetLoader = loader;
    }

    public void addToBlackList(String s) {
        blacklist.add(s);
    }

    @Override
    @RequiresApi(21)
    public WebResourceResponse shouldInterceptRequest(WebView view,
                                                      WebResourceRequest request) {
        WebResourceResponse response = assetLoader.shouldInterceptRequest(request.getUrl());
        if (response != null) {
            return response;
        }
        String host = request.getUrl().getHost();
        if (blacklist.contains(host)) {
            // todo!!!
        }
        Log.i("fuck", request.getUrl().getHost());
        return null;
    }
}
