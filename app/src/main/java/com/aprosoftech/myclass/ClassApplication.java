package com.aprosoftech.myclass;

import android.app.Application;

import com.backendless.Backendless;
import com.onesignal.OneSignal;

/**
 * Created by CSB on 20/03/18.
 */

public class ClassApplication extends Application {

    public static final String APPLICATION_ID = "65C6BAD1-C8A1-91BF-FFDF-0803DE39EE00";
    public static final String API_KEY = "754E3E5C-8D7C-4FAB-FF5C-FFC51CE28F00";



    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();


        Backendless.initApp(this,APPLICATION_ID,API_KEY);
    }
}
