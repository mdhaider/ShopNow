package com.oneapplab.shopnow.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.digits.sdk.android.Digits;
import com.oneapplab.shopnow.R;
import com.oneapplab.shopnow.fragments.LoginFragment;
import com.oneapplab.shopnow.fragments.ResetPasswordFragment;
import com.oneapplab.shopnow.fragments.SignUpFragment;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import io.fabric.sdk.android.Fabric;

public class LoginActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "xCY2q3J2DxDZTf69gjPxO4I8b";
    private static final String TWITTER_SECRET = "KBlLvk3XpaykUly7zmV3MSsSjYPgjFto2kHB5JXKTY1VrgEOt8";

    private LoginFragment fragmentLogin;
    private ResetPasswordFragment fragmentResetPassword;
    private SignUpFragment fragmentSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().build());
        setContentView(R.layout.activity_login);
        if (savedInstanceState == null) {
            fragmentLogin = LoginFragment.newInstance();
            fragmentResetPassword = ResetPasswordFragment.newInstance();
            fragmentSignUp = SignUpFragment.newInstance();

        }
        displayFragmentA();
    }

    protected void displayFragmentA() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragmentLogin.isAdded()) { // if the fragment is already in container
            ft.show(fragmentLogin);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.content_frame, fragmentLogin, "A");
        }
        // Hide fragment B
        if (fragmentResetPassword.isAdded()) {
            ft.hide(fragmentResetPassword);
        }
        if (fragmentSignUp.isAdded()) {
            ft.hide(fragmentSignUp);
        }


        // Commit changes
        ft.commit();


    }
}