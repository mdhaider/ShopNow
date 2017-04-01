package com.oneapplab.shopnow.utilService;

/**
 * Created by haider on 09-02-2017.
 */

public class GlobalConstant {
    private static GlobalConstant ourInstance = new GlobalConstant();

    public static GlobalConstant getInstance() {
        return ourInstance;
    }

    private GlobalConstant() {
    }


    public String getFragmentMessageConatinerKey() {
        return "fragmentMessageConatinerKey";
    }

}
