package com.stcodesapp.noteit.monetization.ads;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.common.Logger;
import com.stcodesapp.noteit.tasks.functionalTasks.behaviorTrackingTasks.IAPTrackingTasks;

public class AdMob implements AdNetwork, RewardedVideoAdListener {

    public interface Listener{
        void onRewardedFromVideoAd();
    }


    private AdView adView;
    private Activity activity;
    private InterstitialAd interstitialAd;
    private RewardedVideoAd rewardedVideoAd;
    private Listener listener;
    private boolean showAd;

    public AdMob(AdView adView, Activity activity) {
        this.adView = adView;
        this.activity = activity;
        IAPTrackingTasks iapTrackingTasks = new IAPTrackingTasks(activity);
        this.showAd = !iapTrackingTasks.isPaidUser();
        Logger.logMessage("ShowAd",showAd+" is result");
    }

    public AdMob(Activity activity) {
        this.activity = activity;
        IAPTrackingTasks iapTrackingTasks = new IAPTrackingTasks(activity);
        this.showAd = !iapTrackingTasks.isPaidUser();
        Logger.logMessage("ShowAd",showAd+" is result");
    }

    @Override
    public void loadBannerAd() {
        if(showAd)
        {
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        }
    }

    @Override
    public void showBannerAd() {

    }

    @Override
    public void loadInterstitialAd() {
        if(showAd)
        {
            interstitialAd = new InterstitialAd(activity);
            interstitialAd.setAdUnitId(activity.getResources().getString(R.string.admob_interstitial_ad_id));
            interstitialAd.loadAd(new AdRequest.Builder().build());
        }

    }

    @Override
    public void showInterstitialAd() {
        if(showAd)
        {
            if(interstitialAd.isLoaded())
                interstitialAd.show();
            else loadInterstitialAd();
        }


    }

    @Override
    public void loadRewardedVideoAd() {
        if(showAd)
        {
            rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(activity);
            rewardedVideoAd.setRewardedVideoAdListener(this);
            rewardedVideoAd.loadAd(activity.getResources().getString(R.string.admob_rewarded_video_ad_id),
                    new AdRequest.Builder().build());
            Log.e("RWAd","Will Load");
        }


    }

    @Override
    public void showRewardedVideoAd() {
        if(showAd)
        {
            if(rewardedVideoAd.isLoaded())
            {
                Log.e("RWAd","Is Loaded");
                rewardedVideoAd.show();
            }
            else {
                Log.e("RWAd","Not Loaded");
                loadRewardedVideoAd();
            }
        }

    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        listener.onRewardedFromVideoAd();

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        loadRewardedVideoAd();

    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
