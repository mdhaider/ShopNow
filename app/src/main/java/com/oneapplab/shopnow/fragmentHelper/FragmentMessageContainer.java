package com.oneapplab.shopnow.fragmentHelper;

import android.os.Parcel;
import android.os.Parcelable;

import com.oneapplab.shopnow.baseDataContainer.BaseDataContainer;
import com.oneapplab.shopnow.enums.FragmentEnum;


/**
 * Created by haider on 09-02-2017.
 */

public class FragmentMessageContainer implements Parcelable {
    private FragmentEnum goingTo;
    private FragmentEnum comingFrom;

    public FragmentEnum getGoingTo() {
        return goingTo;
    }

    public FragmentEnum getComingFrom() {
        return comingFrom;
    }

    public BaseDataContainer getData() {
        return data;
    }

    private BaseDataContainer data;
    private boolean isComingback;

    public void setComingData(BaseDataContainer comingData) {
        this.comingData = comingData;
    }

    public void setComeback(boolean comingback) {
        isComingback = comingback;
    }

    private BaseDataContainer comingData;

    public boolean isComingback() {
        return isComingback;
    }

    public BaseDataContainer getComingData() {
        return comingData;
    }

    public FragmentMessageContainer(FragmentEnum goingTo, FragmentEnum comingFrom, BaseDataContainer data, boolean isComingback, BaseDataContainer comingData) {
        this.goingTo = goingTo;
        this.comingFrom = comingFrom;
        this.data = data;
        this.isComingback = isComingback;
        this.comingData = comingData;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.goingTo == null ? -1 : this.goingTo.ordinal());
        dest.writeInt(this.comingFrom == null ? -1 : this.comingFrom.ordinal());
        dest.writeParcelable(this.data, flags);
        dest.writeByte(this.isComingback ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.comingData, flags);
    }

    protected FragmentMessageContainer(Parcel in) {
        int tmpGoingTo = in.readInt();
        this.goingTo = tmpGoingTo == -1 ? null : FragmentEnum.values()[tmpGoingTo];
        int tmpComingFrom = in.readInt();
        this.comingFrom = tmpComingFrom == -1 ? null : FragmentEnum.values()[tmpComingFrom];
        this.data = in.readParcelable(BaseDataContainer.class.getClassLoader());
        this.isComingback = in.readByte() != 0;
        this.comingData = in.readParcelable(BaseDataContainer.class.getClassLoader());
    }

    public static final Creator<FragmentMessageContainer> CREATOR = new Creator<FragmentMessageContainer>() {
        @Override
        public FragmentMessageContainer createFromParcel(Parcel source) {
            return new FragmentMessageContainer(source);
        }

        @Override
        public FragmentMessageContainer[] newArray(int size) {
            return new FragmentMessageContainer[size];
        }
    };
}
