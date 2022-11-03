package com.tqmatvey.wifipineapple;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.view.KeyEvent;
import android.webkit.WebViewClient;


public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeWebView();
    }

    public void initializeWebView(){
        CustomWebViewClient client = new CustomWebViewClient(this);
        //webView.getSettings().setDomStorageEnabled(true);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(client);
        WebSettings settings = webView.getSettings();
        setupWebViewSettings(webView, true);
        webView.loadUrl("http://172.16.42.1:1471/");
    }

    public void setupWebViewSettings(WebView webView,boolean DesktopMode) {
        String newUserAgent = webView.getSettings().getUserAgentString();
        if (DesktopMode) {
            try {
                String ua = webView.getSettings().getUserAgentString();
                String androidOSString = webView.getSettings().getUserAgentString().substring(ua.indexOf("("), ua.indexOf(")") + 1);
                newUserAgent = webView.getSettings().getUserAgentString().replace(androidOSString, "(X11; Linux x86_64)");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            newUserAgent = null;
        }

        webView.setInitialScale(1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(DesktopMode);
        webView.getSettings().setLoadWithOverviewMode(DesktopMode);
        webView.reload();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK && this.webView.canGoBack()){
            this.webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

class CustomWebViewClient extends WebViewClient{
    private Activity activity;

    @Override
    public void onLoadResource(WebView view, String url) {
        view.evaluateJavascript("document.querySelector('meta[name=\"viewport\"]').setAttribute('content', 'width=712, initial-scale=' + (document.documentElement.clientWidth / 712));", null);
    }

    public CustomWebViewClient(Activity activity){
        this.activity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request){
        return false;
    }
}