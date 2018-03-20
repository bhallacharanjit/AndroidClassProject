package com.aprosoftech.myclass;

import android.app.Application;

import com.onesignal.OneSignal;

/**
 * Created by CSB on 20/03/18.
 */

public class ClassApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }
}
