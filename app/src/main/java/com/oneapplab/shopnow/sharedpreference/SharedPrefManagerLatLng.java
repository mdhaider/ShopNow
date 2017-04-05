package com.oneapplab.shopnow.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by haider on 23-03-2017.
 */

public class SharedPrefManagerLatLng {

    private static final String SHARED_PREF_NAME = "SharedPrefLat";
    private static final String TAG_TOKEN = "lattoken";
    private static final String TAG_TOKE = "lngtoken";

    private static SharedPrefManagerLatLng mInstance;
    private static Context mCtx;

    private SharedPrefManagerLatLng(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManagerLatLng getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManagerLatLng(context);
        }
        return mInstance;
    }

    //this method will save the device token to shared preferences
    public boolean saveLatLng(Float lat, Float lng) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(TAG_TOKEN, lat);
        editor.putFloat(TAG_TOKE, lng);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public Float getLat() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(TAG_TOKEN, 0.0f);
    }

    public Float getLng() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(TAG_TOKE, 0.0f);


    }
}