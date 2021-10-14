package com.vinnovateit.studyhub;

import android.app.Application;
import com.onesignal.OneSignal;
public class ApplicationClass extends Application {
    private static final String ONESIGNAL_APP_ID = "b83766ae-5617-416e-9bc6-ccf9630fa0ee";
    @Override
    public void onCreate() {
        super.onCreate();

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }
}
