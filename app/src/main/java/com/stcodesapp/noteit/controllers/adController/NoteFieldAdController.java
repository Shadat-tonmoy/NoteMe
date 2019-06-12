package com.stcodesapp.noteit.controllers.adController;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.ads.AdView;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.monetization.ads.AdMob;
import com.stcodesapp.noteit.monetization.ads.BannerAd;
import com.stcodesapp.noteit.monetization.ads.InterstitialAd;
import com.stcodesapp.noteit.monetization.ads.RewardedVideoAd;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.NoteFieldScreenView;

public class NoteFieldAdController {

    private Activity activity;
    private NoteFieldScreenView noteFieldScreenView;
    private InterstitialAd interstitialAd;
    private BannerAd bannerAd;
    private RewardedVideoAd rewardedVideoAd;

    public NoteFieldAdController(Activity activity) {
        this.activity = activity;
    }

    public void bindView(NoteFieldScreenView noteFieldScreenView) {
        this.noteFieldScreenView = noteFieldScreenView;
    }

    public void onStart()
    {
        initMobileAds();
        interstitialAd.loadAd();
        bannerAd.loadAd();
        rewardedVideoAd.loadAd();
    }

    public void onStop()
    {

    }

    public void showRewardedVideoAd()
    {
        Log.e("RWAd","Will Show");
        rewardedVideoAd.showAd();
    }

    public void onDestroy()
    {
        interstitialAd.showAd();
    }

    private void initMobileAds()
    {
        bannerAd = new BannerAd(new AdMob(getBannerAdViewForAdmob(),activity));
        interstitialAd = new InterstitialAd(new AdMob(activity));
        rewardedVideoAd = new RewardedVideoAd(new AdMob(activity));
    }

    private AdView getBannerAdViewForAdmob()
    {
        return noteFieldScreenView.getRootView().findViewById(R.id.admob_banner_ad_view);
    }
}
