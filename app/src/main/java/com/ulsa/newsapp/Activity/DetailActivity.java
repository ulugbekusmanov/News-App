package com.ulsa.newsapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ulsa.newsapp.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.webview)
    WebView webView;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        initDialog();
        alertDialog.show();
        webView.getSettings().setJavaScriptEnabled(false);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                alertDialog.dismiss();
            }
        });
        if (getIntent() != null) {
            if (getIntent().hasExtra("idweb")) {
                webView.loadUrl(getIntent().getStringExtra("idweb"));
            } else if (getIntent().hasExtra("weburl")) {
                webView.loadUrl(getIntent().getStringExtra("weburl"));
            } else {
                Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
            }


        }


    }

    public void initDialog() {
        alertDialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(this)
                .setMessage("Loading...")
                .build();
    }


}
