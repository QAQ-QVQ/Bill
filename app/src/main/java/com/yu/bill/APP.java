package com.yu.bill;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;

/**
 * CREATED BY DY ON 2020/3/16.
 * TIME BY 17:16.
 **/
public class APP extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
    public static Context getContext(){
        return context;
    }


    @Override
    public ContentResolver getContentResolver() {
        return super.getContentResolver();
    }
}
