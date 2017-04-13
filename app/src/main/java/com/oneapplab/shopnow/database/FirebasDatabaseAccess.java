package com.oneapplab.shopnow.database;

/**
 * Created by haider on 13-04-2017.
 */

public class FirebasDatabaseAccess {
    private static FirebasDatabaseAccess instance= null;
    private static Object mutex= new Object();
    private FirebasDatabaseAccess(){
    }

    public static FirebasDatabaseAccess getInstance(){
        if(instance==null){
            synchronized (mutex){
                if(instance==null) instance= new FirebasDatabaseAccess();
            }
        }
        return instance;
    }


}
