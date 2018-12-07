package com.example.a11699.officeapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.a11699.officeapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Daiyoucehua extends AppCompatActivity {
@BindView(R.id.cehua_WebView)
    WebView cehua_WebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daiyoucehua);
        ButterKnife.bind(this);
        WebSettings webSettings=cehua_WebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        cehua_WebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        cehua_WebView.loadUrl("file:///android_asset/index.html");//加载自己的网站
    }
}
