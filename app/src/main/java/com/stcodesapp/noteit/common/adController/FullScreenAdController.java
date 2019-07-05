package com.stcodesapp.noteit.common.adController;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdView;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.common.Logger;
import com.stcodesapp.noteit.constants.AppMetadata;
import com.stcodesapp.noteit.controllers.adController.NoteFieldAdController;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.monetization.ads.AdMob;
import com.stcodesapp.noteit.monetization.ads.BannerAd;
import com.stcodesapp.noteit.monetization.ads.InterstitialAd;
import com.stcodesapp.noteit.monetization.ads.RewardedVideoAd;
import com.stcodesapp.noteit.tasks.functionalTasks.behaviorTrackingTasks.AdStrategyTrackingTask;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.NoteFieldScreenView;

public class FullScreenAdController {


    private Activity activity;
    private InterstitialAd interstitialAd;
    private RewardedVideoAd rewardedVideoAd;
    private AdStrategyTrackingTask adStrategyTrackingTask;
    private TasksFactory tasksFactory;

    public FullScreenAdController(Activity activity, TasksFactory tasksFactory) {
        this.activity = activity;
        this.tasksFactory = tasksFactory;
        this.adStrategyTrackingTask = tasksFactory.getAdStrategyTrackingTask();
    }
    public void onStart()
    {
        initMobileAds();
        rewardedVideoAd.loadAd();
        interstitialAd.loadAd();
    }

    public void onStop()
    {

    }

    public void showRewardedVideoAd()
    {
        Log.e("RWAd","Will Show");
        rewardedVideoAd.showAd();
    }

    private void showFullScreenAd()
    {
        if(adStrategyTrackingTask.getTotalNoteFieldOpenCount()>= AppMetadata.MAX_NOTE_OPEN_TO_SHOW_FULL_SCREEN_AD)
        {
            showRewardedVideoAd();
        }
    }
    private void initMobileAds()
    {
        AdMob adMob = new AdMob(activity);
        interstitialAd = new InterstitialAd(adMob);
        rewardedVideoAd = new RewardedVideoAd(adMob);
    }
}
