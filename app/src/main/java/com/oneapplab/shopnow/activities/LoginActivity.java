package com.oneapplab.shopnow.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.digits.sdk.android.Digits;
import com.oneapplab.shopnow.R;
import com.oneapplab.shopnow.fragments.LoginFragment;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;

public class LoginActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "xCY2q3J2DxDZTf69gjPxO4I8b";
    private static final String TWITTER_SECRET = "KBlLvk3XpaykUly7zmV3MSsSjYPgjFto2kHB5JXKTY1VrgEOt8";

    private LoginFragment fragmentLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().build());
        setContentView(R.layout.activity_login);

       fragmentLogin = LoginFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragmentLogin).commit();
    }


}