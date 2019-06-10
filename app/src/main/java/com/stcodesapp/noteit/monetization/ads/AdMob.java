package com.stcodesapp.noteit.monetization.ads;

import android.app.Activity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.stcodesapp.noteit.R;

public class AdMob implements AdNetwork {

    private AdView adView;
    private Activity activity;

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

    }

    @Override
    public void showInterstitialAd() {

    }

    @Override
    public void loadRewardedVideoAd() {

    }

    @Override
    public void showRewardedVideoAd() {

    }
}
