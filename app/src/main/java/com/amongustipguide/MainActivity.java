package com.amongustipguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.amongustipguide.utils.BackUpModel;
import com.amongustipguide.utils.HttpHandler;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.hgkvjebkqs.adx.service.InterstitialAdsManager;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import vn.aib.ratedialog.RatingDialog;

public class MainActivity extends AppCompatActivity {
    ImageView startGuide;
//    public static String NATIVE_AD_ID = "ca-app-pub-3940256099942544/2247696110";
//    public static String INTER_ID = "ca-app-pub-3940256099942544/1033173712";
//    public static String BANNER_ID = "ca-app-pub-3940256099942544/6300978111";
    public static String NATIVE_AD_ID = "ca-app-pub-2077548239432877/5381147636";
    public static String INTER_ID = "ca-app-pub-2077548239432877/6694229302";
    public static String BANNER_ID = "ca-app-pub-2077548239432877/9320392642";
    public static int PERCENT_SHOW_BANNER_AD = 100;
    public static int PERCENT_SHOW_INTER_AD = 100;
    public static int PERCENT_SHOW_NATIVE_AD = 100;
    public static InterstitialAd mInterstitialAd;
    private InterstitialAdsManager adsManager;
    private BackUpModel backUpModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LinearLayout native_ad = findViewById(R.id.native_ad);



        try {
            Void aVoid = new GetBackUp().execute().get();
            if(backUpModel != null){
                if(!backUpModel.isLive){
                    new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                            .setTitle("Notice from developer")
                            .setMessage("Please update the new application to continue using it. We are really sorry for this issue.")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    showApp(MainActivity.this, backUpModel.newAppPackage);
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setCancelable(false)
                            .show();
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        adsManager = new InterstitialAdsManager();
        adsManager.init(true, this, INTER_ID, "#000000", getString(R.string.app_name));
        SharedPreferences prefs = getSharedPreferences("rate_dialog", MODE_PRIVATE);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mInterstitialAd = new InterstitialAd(MainActivity.this);
        mInterstitialAd.setAdUnitId(MainActivity.INTER_ID);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }


            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                loadAds();
            }
        });

//        new FlurryAgent.Builder()
//                .withLogEnabled(true)
//                .build(this, "");

        Boolean rated = prefs.getBoolean("rate", false);
        if(!rated){
            showRateDialog();
        }




        startGuide = findViewById(R.id.startG);
        startGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, list_tips.class);
                startActivity(myIntent);
            }
        });


        int decode = Integer.decode("#FFFFFF");
        final ColorDrawable colorDrawable = new ColorDrawable(decode);
        MobileAds.initialize(this, MainActivity.NATIVE_AD_ID);
        AdLoader adLoader = new AdLoader.Builder(this, MainActivity.NATIVE_AD_ID)
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(colorDrawable).build();
                        TemplateView template = findViewById(R.id.my_template);
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
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (adsManager != null)
            adsManager.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private void showRateDialog() {
        RatingDialog ratingDialog = new RatingDialog(this);
        ratingDialog.setRatingDialogListener(new RatingDialog.RatingDialogInterFace() {
            @Override
            public void onDismiss() {
            }

            @Override
            public void onSubmit(float rating) {
                rateApp(MainActivity.this);
                SharedPreferences.Editor editor = getSharedPreferences("rate_dialog", MODE_PRIVATE).edit();
                editor.putBoolean("rate", true);
                editor.commit();
            }

            @Override
            public void onRatingChanged(float rating) {
            }
        });
        ratingDialog.showDialog();
    }

    public static void rateApp(Context context) {
        Intent intent = new Intent(new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    private class GetBackUp extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://raw.githubusercontent.com/scaryguidegame/amoungus/master/backupdata.json";
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String appPackage = jsonObj.getString("appPackage");
                    Boolean isLive = jsonObj.getBoolean("isLive");
                    String newAppPackage = jsonObj.getString("newAppPackage");
                    Boolean isAdsShow = jsonObj.getBoolean("isAdsShow");
                    String inter = jsonObj.getString("inter");
                    String fb_inter = jsonObj.getString("fb_inter");
                    Boolean isShowGG = jsonObj.getBoolean("isShowGG");
                    String banner = jsonObj.getString("banner");
                    String nativeAd = jsonObj.getString("nativeAd");
                    String rewarded = jsonObj.getString("rewarded");
                    int percent_banner = jsonObj.getInt("percent_banner");
                    int percent_inter = jsonObj.getInt("percent_inter");
                    int percent_native = jsonObj.getInt("percent_native");
                    int numberNativeAd = jsonObj.getInt("numberNativeAd");

                    backUpModel = new BackUpModel();
                    backUpModel.appPackage = appPackage;
                    backUpModel.isLive = isLive;
                    backUpModel.newAppPackage = newAppPackage;
                    backUpModel.isAdsShow = isAdsShow;
                    backUpModel.inter = inter;
                    backUpModel.fb_inter = fb_inter;
                    backUpModel.isShowGG = isShowGG;
                    backUpModel.banner = banner;
                    backUpModel.nativeAd = nativeAd;
                    backUpModel.rewarded = rewarded;
                    backUpModel.percent_banner = percent_banner;
                    backUpModel.percent_inter = percent_inter;
                    backUpModel.percent_native = percent_native;
                    backUpModel.numberNativeAd = numberNativeAd;

                    INTER_ID = backUpModel.inter;
                    BANNER_ID = backUpModel.banner;
                    NATIVE_AD_ID = backUpModel.nativeAd;
                    PERCENT_SHOW_BANNER_AD = backUpModel.percent_banner;
                    PERCENT_SHOW_INTER_AD = backUpModel.percent_inter;
                    PERCENT_SHOW_NATIVE_AD = backUpModel.percent_native;

                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });

                }

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

    public static void showApp(Context context, String pkg) {
        Intent intent = new Intent(new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=" + pkg)));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }
    public static void loadAds() {
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        }
    }
    public static void showInterstitial(Context m) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            if (isOnline()) {
                loadAds();
            } else {
                Toast.makeText(m, "Please check network connection!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
