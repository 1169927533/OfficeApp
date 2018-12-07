package com.example.a11699.officeapp.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.a11699.officeapp.MainActivity;
import com.example.a11699.officeapp.R;

import java.nio.Buffer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Ac_Login extends AppCompatActivity {
    @BindView(R.id.web_view)
    LinearLayout webView_Layout;
    WebView webView;

    @BindView(R.id.ac_an_js)
    Button ac_an_js;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_login);
        ButterKnife.bind(this);
        initview();
    }

    @OnClick(R.id.ac_an_js)
    void fcac_an_js() {
        webView.post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                //android调用js的方法
                webView.loadUrl("javascript:an_js('" + 5 + "')");
                webView.evaluateJavascript("javascript:an_js('" + 5 + "')", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Toast.makeText(Ac_Login.this,s,Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    void initview() {
        webView = new WebView(this);
        WebSettings webSettings = webView.getSettings();
        //设置与js的交互
        webSettings.setJavaScriptEnabled(true);
        //设置允许js弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView_Layout.addView(webView);
        webView.loadUrl("file:///android_asset/index.html");//加载自己的网站

        //JS通过WebView调用 Android 代码
        webView.addJavascriptInterface(new WebAppInterface(this), "test");
        //*******************************************************

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                //加载的进度条
            }


            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                //獲取webview的标题
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
                //获取图标
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                //js弹框
                AlertDialog.Builder b = new AlertDialog.Builder(Ac_Login.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);

            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //需要设置在当前WebView中显示网页，才不会跳到默认的浏览器进行显示
                return super.shouldOverrideUrlLoading(view, request);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //加载完成
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                //拦截url
            }
        });
    }


    public class WebAppInterface {
        Context context;

        public WebAppInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void js_an_method() {
            Toast.makeText(Ac_Login.this,"js调用android方法",Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.clearCache(true); //清空缓存
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                if (webView_Layout != null) {
                    webView_Layout.removeView(webView);
                }
                webView.removeAllViews();
                webView.destroy();
            } else {
                webView.removeAllViews();
                webView.destroy();
                if (webView_Layout != null) {
                    webView_Layout.removeView(webView);
                }
            }
            webView = null;
        }
    }
}
