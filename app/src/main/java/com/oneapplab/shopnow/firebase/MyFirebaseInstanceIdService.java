package com.oneapplab.shopnow.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.oneapplab.shopnow.sharedpreference.SharedPrefManagerToken;


/**
 * Created by haider on 27-03-2017.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG="MyFirebaseIdService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        storeToken(refreshedToken);
    }
    public void storeToken(String token){
        SharedPrefManagerToken.getInstance(getApplicationContext()).saveDeviceToken(token);

    }
}
