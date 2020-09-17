package com.amongustipguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.util.Random;

public class list_tips extends AppCompatActivity {
    private LinearLayout tips1,tips2,tips3,tips4,tips5,tips6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tips);
        final LinearLayout native_ad = findViewById(R.id.native_ad2);

        int decode = Integer.decode("#FFFFFF");
        final ColorDrawable colorDrawable = new ColorDrawable(decode);
        MobileAds.initialize(this, MainActivity.NATIVE_AD_ID);
        AdLoader adLoader = new AdLoader.Builder(this, MainActivity.NATIVE_AD_ID)
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(colorDrawable).build();
                        TemplateView template = findViewById(R.id.my_template2);
                        template.setStyles(styles);
                        template.setNativeAd(unifiedNativeAd);
                        Random r = new Random();
                        int ads = r.nextInt(100);
                        if (ads <= MainActivity.PERCENT_SHOW_INTER_AD){
                            native_ad.setVisibility(View.VISIBLE);
                        }

                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
        tips1 = findViewById(R.id.us1);
        tips1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                int ads = r.nextInt(100);
                if (ads < MainActivity.PERCENT_SHOW_INTER_AD){
                    MainActivity.showInterstitial(getApplicationContext());
                    MainActivity.mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent myIntent = new Intent(list_tips.this, tips1.class);
                            startActivity(myIntent);
                        }
                    });
                }
                else {
                    Intent myIntent = new Intent(list_tips.this, tips1.class);
                    startActivity(myIntent);

                }
            }
        });
        tips2 = findViewById(R.id.us2);
        tips2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(list_tips.this, tips2.class);
                startActivity(myIntent);
                Random r = new Random();
                int ads = r.nextInt(100);
                if (ads < MainActivity.PERCENT_SHOW_INTER_AD){
                    MainActivity.showInterstitial(getApplicationContext());
                    MainActivity.mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {

                        }
                    });
                }
                else {


                }
            }
        });
        tips3 = findViewById(R.id.us3);
        tips3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random r = new Random();
                int ads = r.nextInt(100);
                if (ads < MainActivity.PERCENT_SHOW_INTER_AD){
                    MainActivity.showInterstitial(getApplicationContext());
                    MainActivity.mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent myIntent = new Intent(list_tips.this, tips3.class);
                            startActivity(myIntent);
                        }
                    });
                }
                else {
                    Intent myIntent = new Intent(list_tips.this, tips3.class);
                    startActivity(myIntent);

                }
            }
        });
        tips4 = findViewById(R.id.us4);
        tips4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random r = new Random();
                int ads = r.nextInt(100);
                if (ads < MainActivity.PERCENT_SHOW_INTER_AD){
                    MainActivity.showInterstitial(getApplicationContext());
                    MainActivity.mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent myIntent = new Intent(list_tips.this, tips4.class);
                            startActivity(myIntent);
                        }
                    });
                }
                else {

                    Intent myIntent = new Intent(list_tips.this, tips4.class);
                    startActivity(myIntent);
                }
            }
        });
        tips5 = findViewById(R.id.us5);
        tips5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random r = new Random();
                int ads = r.nextInt(100);
                if (ads < MainActivity.PERCENT_SHOW_INTER_AD){
                    MainActivity.showInterstitial(getApplicationContext());
                    MainActivity.mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent myIntent = new Intent(list_tips.this, tips5.class);
                            startActivity(myIntent);
                        }
                    });
                }
                else {
                    Intent myIntent = new Intent(list_tips.this, tips5.class);
                    startActivity(myIntent);

                }
            }
        });
        tips6 = findViewById(R.id.us6);
        tips6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random r = new Random();
                int ads = r.nextInt(100);
                if (ads < MainActivity.PERCENT_SHOW_INTER_AD){
                    MainActivity.showInterstitial(getApplicationContext());
                    MainActivity.mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent myIntent = new Intent(list_tips.this, tips6.class);
                            startActivity(myIntent);
                        }
                    });
                }
                else {

                    Intent myIntent = new Intent(list_tips.this, tips6.class);
                    startActivity(myIntent);
                }
            }
        });
        View adContainer = findViewById(R.id.adMobView);
        AdView mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(MainActivity.BANNER_ID);
        ((RelativeLayout)adContainer).addView(mAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Random r = new Random();
        int ads = r.nextInt(100);
        if (ads >= MainActivity.PERCENT_SHOW_BANNER_AD){
            mAdView.destroy();
            mAdView.setVisibility(View.GONE);
        }
    }
}
