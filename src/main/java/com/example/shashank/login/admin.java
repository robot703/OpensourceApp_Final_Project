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

public class admin extends AppCompatActivity {
    WebView webview;
    public String url = "http://192.168.0.129:8001/";

    @SuppressLint({"SetJavaScriptEnabled", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manual);

        webview = findViewById(R.id.webView5);
        // WebView에 대한 설정
        // 자바스크립트를 허용하는 설정
        webview.getSettings().setJavaScriptEnabled(true);
        // URL 로드
        webview.loadUrl(url);
        // 웹을 더 쾌적하게 돌리기 위한 설정
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new bargraph_web.WebViewClientClass());


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavi6);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.teamGraph:
                        setFrag(2);
                        break;
                }
                return true;
            }
        });

        ImageButton logout = findViewById(R.id.button6);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(admin.this, "move", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void setFrag(int n) {
        switch (n) {
            case 1:
                break;
            case 2:
                Intent intent = new Intent(admin.this, admin.class);
                startActivity(intent);
                break;
            case 3:
                // Add code to handle other menu items if needed
                break;
        }
    }
}





