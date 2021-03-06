package com.oneapplab.shopnow.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by haider on 23-03-2017.
 */

public class SharedPrefManagerToken {

    private static final String SHARED_PREF_NAME = "FCMSharedPrefShpNow";
    private static final String TAG_TOKEN = "tagtoken";

    private static SharedPrefManagerToken mInstance;
    private static Context mCtx;

    private SharedPrefManagerToken(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManagerToken getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManagerToken(context);
        }
        return mInstance;
    }

    //this method will save the device token to shared preferences
    public boolean saveDeviceToken(String token){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_TOKEN, token);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getDeviceToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_TOKEN, null);
    }


}
