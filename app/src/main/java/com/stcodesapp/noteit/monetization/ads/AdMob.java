package com.stcodesapp.noteit.monetization.ads;

import android.app.Activity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.stcodesapp.noteit.R;

public class AdMob implements AdNetwork {

    private AdView adView;
    private Activity activity;
    private InterstitialAd interstitialAd;

    public AdMob(AdView adView, Activity activity) {
        this.adView = adView;
        this.activity = activity;
    }

    public AdMob(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void loadBannerAd() {

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    @Override
    public void showBannerAd() {

    }

    @Override
    public void loadInterstitialAd() {
        interstitialAd = new InterstitialAd(activity);
        interstitialAd.setAdUnitId(activity.getResources().getString(R.string.admob_interstitial_ad_id));
        interstitialAd.loadAd(new AdRequest.Builder().build());

    }

    @Override
    public void showInterstitialAd() {
        if(interstitialAd.isLoaded())
            interstitialAd.show();
        else loadInterstitialAd();

    }

    @Override
    public void loadRewardedVideoAd() {

    }

    @Override
    public void showRewardedVideoAd() {

    }
}
