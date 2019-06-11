package com.stcodesapp.noteit.controllers.adController;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.ads.AdView;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.monetization.ads.AdMob;
import com.stcodesapp.noteit.monetization.ads.BannerAd;
import com.stcodesapp.noteit.monetization.ads.InterstitialAd;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.NoteFieldScreenView;

public class NoteFieldAdController {

    private Activity activity;
    private NoteFieldScreenView noteFieldScreenView;
    private InterstitialAd interstitialAd;
    private BannerAd bannerAd;

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
    }

    public void onStop()
    {

    }

    public void onDestroy()
    {
        interstitialAd.showAd();
    }

    private void initMobileAds()
    {
        interstitialAd = new InterstitialAd(new AdMob(activity));
        bannerAd = new BannerAd(new AdMob(getBannerAdViewForAdmob(),activity));
    }

    private AdView getBannerAdViewForAdmob()
    {
        return noteFieldScreenView.getRootView().findViewById(R.id.admob_banner_ad_view);
    }
}
