package com.oneapplab.shopnow.fragmentHelper;

/*
Copyright © 2016 Ariba. All rights reserved.
*/


import android.os.Parcel;
import android.os.Parcelable;

import com.oneapplab.shopnow.baseDataContainer.BaseDataContainer;


public class StringContainer extends BaseDataContainer {

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    String msg;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.msg);
    }

    public StringContainer(String msg) {
        this.msg = msg;
    }

    protected StringContainer(Parcel in) {
        super(in);
        this.msg = in.readString();
    }

    public static final Parcelable.Creator<StringContainer> CREATOR = new Parcelable.Creator<StringContainer>() {
        @Override
        public StringContainer createFromParcel(Parcel source) {
            return new StringContainer(source);
        }

        @Override
        public StringContainer[] newArray(int size) {
            return new StringContainer[size];
        }
    };
}
