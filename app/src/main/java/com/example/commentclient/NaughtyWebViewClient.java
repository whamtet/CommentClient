package com.example.commentclient;

import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;
import androidx.webkit.WebViewAssetLoader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NaughtyWebViewClient extends WebViewClient {
    private final WebViewAssetLoader assetLoader;
    private final Set<String> blacklist = new HashSet<>();
    private final OkHttpClient client = new OkHttpClient();

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
        String host = request.getUrl().getHost();
        WebResourceResponse assetResponse = assetLoader.shouldInterceptRequest(request.getUrl());
        if (assetResponse != null || host.equals("appassets.androidplatform.net")) {
            return assetResponse;
        }
        if (request.getMethod().equals("GET") && blacklist.contains(host)) {
            Request r = new Request.Builder()
                    .url(request.getUrl().toString())
                    .build();

            try {
                Response response = client.newCall(r).execute();

                String contentType = response.header("content-type").split(";")[0];

                Map<String, String> responseHeaders = new HashMap<>();
                for (String h : response.headers().names()) {
                    responseHeaders.put(h, response.header(h));
                }
                responseHeaders.remove("content-security-policy");
                responseHeaders.remove("x-frame-options");

                return new WebResourceResponse(
                        contentType,
                        response.header("content-encoding", "utf-8"),
                        response.code(),
                        "OK",
                        responseHeaders,
                        response.body().byteStream()
                );
            } catch (Throwable e) {
                Log.i("fuck", e.getMessage());
                return null;
            }
        }
        return null;
    }
}
