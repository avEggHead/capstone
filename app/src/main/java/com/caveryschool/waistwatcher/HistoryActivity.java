package com.caveryschool.waistwatcher;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

// I might use this for data display in the future.

public class HistoryActivity extends AppCompatActivity {
    private DatabaseManager _databaseManager;

    private String htmlContent = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<title>Sample Page</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "<h2>History</h2>\n" +
            "\n" +
            "<img onclick=\"myFunction()\" >Go Back</img>\n" +
            "\n" +
            "<script>\n" +
            "function myFunction (){\n" +
            "    Android.goToMainScreen();\n" +
            "}\n" +
            "</script>\n" +
            "\n" +
            "</body>\n" +
            "</html>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.loadData(htmlContent, "text/html", "utf-8");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(this),"Android");
        this._databaseManager = new DatabaseManager(this);
    }

    public void goBack(View view){
        this.finish();
    }
}
