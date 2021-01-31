package com.caveryschool.waistwatcher;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import java.lang.reflect.Method;

public class WebAppInterface {
    private Context context;

    public WebAppInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void goToMainScreen(){
        Intent mainScreen = new Intent(this.context,MainActivity.class);
        this.context.startActivity(mainScreen);
    }
}
