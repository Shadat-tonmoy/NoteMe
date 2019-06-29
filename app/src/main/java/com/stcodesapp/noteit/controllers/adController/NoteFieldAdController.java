package com.stcodesapp.noteit.controllers.adController;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.ads.AdView;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.AppMetadata;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.monetization.ads.AdMob;
import com.stcodesapp.noteit.monetization.ads.BannerAd;
import com.stcodesapp.noteit.monetization.ads.InterstitialAd;
import com.stcodesapp.noteit.monetization.ads.RewardedVideoAd;
import com.stcodesapp.noteit.tasks.functionalTasks.behaviorTrackingTasks.AdStrategyTrackingTask;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.NoteFieldScreenView;

public class NoteFieldAdController {

    private Activity activity;
    private NoteFieldScreenView noteFieldScreenView;
    private InterstitialAd interstitialAd;
    private BannerAd bannerAd;
    private RewardedVideoAd rewardedVideoAd;
    private AdStrategyTrackingTask adStrategyTrackingTask;
    private TasksFactory tasksFactory;

    public NoteFieldAdController(Activity activity, TasksFactory tasksFactory) {
        this.activity = activity;
        this.tasksFactory = tasksFactory;
        this.adStrategyTrackingTask = tasksFactory.getAdStrategyTrackingTask();
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
        showFullScreenAd();
    }

    public void updateAdShowingStrategy()
    {
        adStrategyTrackingTask.updateNoteFieldOpenCount();
    }

    private void showFullScreenAd()
    {
        Log.e("TotalCounter", adStrategyTrackingTask.getTotalNoteFieldOpenCount()+" ");
        if(adStrategyTrackingTask.getTotalNoteFieldOpenCount()>= AppMetadata.MAX_NOTE_OPEN_TO_SHOW_FULL_SCREEN_AD)
        {
            showRewardedVideoAd();
        }
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
