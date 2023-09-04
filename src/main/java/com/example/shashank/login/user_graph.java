package com.example.shashank.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class user_graph extends AppCompatActivity {

    WebView webview;
    public String url = "http://192.168.0.129:5500/";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        webview = findViewById(R.id.webView);
        // WebView에 대한 설정
        // 자바스크립트를 허용하는 설정
        webview.getSettings().setJavaScriptEnabled(true);
        // URL 로드
        webview.loadUrl(url);
        // 웹을 더 쾌적하게 돌리기 위한 설정
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClientClass());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavi1);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.data:
                        setFrag(0);
                        break;
                    case R.id.nodegraph:
                        setFrag(1);
                        break;
                    case R.id.bargraph:
                        setFrag(2);
                        break;
                }
                return true;
            }
        });
        ImageButton logout = findViewById(R.id.button5);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(user_graph.this, DashboardActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(user_graph.this, "move", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webview != null && webview.canGoBack()) {
                webview.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private static class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void setFrag(int n) {
        switch (n) {
            case 0:
                //Intent intent = new Intent(user_graph.this, user_graph.class);
                //startActivity(intent);
                break;
            case 1:
                Intent intent1 = new Intent(user_graph.this, graph_activity_web.class);
                startActivity(intent1);
                break;
            case 2:
                Intent intent2 = new Intent(user_graph.this, bargraph_web.class);
                startActivity(intent2);
                break;
        }
    }
}