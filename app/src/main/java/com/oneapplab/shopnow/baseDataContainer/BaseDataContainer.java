package com.oneapplab.shopnow.baseDataContainer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by haider on 09-02-2017.
 */

public abstract class BaseDataContainer implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public BaseDataContainer() {
    }

    protected BaseDataContainer(Parcel in) {
    }
}
