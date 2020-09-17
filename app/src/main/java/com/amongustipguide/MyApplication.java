package com.amongustipguide;

import android.app.Application;

import com.facebook.ads.AudienceNetworkAds;
import com.flurry.android.FlurryAgent;
import com.hgkvjebkqs.adx.service.AdsExchange;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AudienceNetworkAds.initialize(this);
        AdsExchange.init(this, "5f62d26349ca095ee7ce7dd1");
        new FlurryAgent.Builder()
                .withLogEnabled(true)
                .build(this, "JTPHYD9ZQTH99P5DG9P3");
    }
}