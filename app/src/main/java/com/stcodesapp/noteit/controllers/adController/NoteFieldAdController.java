package com.stcodesapp.noteit.controllers.adController;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.ads.AdView;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.common.Logger;
import com.stcodesapp.noteit.constants.AppMetadata;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.monetization.ads.AdMob;
import com.stcodesapp.noteit.monetization.ads.BannerAd;
import com.stcodesapp.noteit.monetization.ads.InterstitialAd;
import com.stcodesapp.noteit.monetization.ads.RewardedVideoAd;
import com.stcodesapp.noteit.tasks.functionalTasks.behaviorTrackingTasks.AdStrategyTrackingTask;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.NoteFieldScreenView;

public class NoteFieldAdController implements AdMob.Listener{

    public interface Listener{
        void onRewardedFromVideoAd();
    }


    private Activity activity;
    private NoteFieldScreenView noteFieldScreenView;
    private InterstitialAd interstitialAd;
    private BannerAd bannerAd;
    private RewardedVideoAd rewardedVideoAd;
    private AdStrategyTrackingTask adStrategyTrackingTask;
    private TasksFactory tasksFactory;
    private Listener listener;

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
        rewardedVideoAd.loadAd();
        interstitialAd.loadAd();
        bannerAd.loadAd();
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
        if(adStrategyTrackingTask.getTotalNoteFieldOpenCount()>= AppMetadata.MAX_NOTE_OPEN_TO_SHOW_FULL_SCREEN_AD)
        {
            showRewardedVideoAd();
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private void initMobileAds()
    {
        AdMob adMob = new AdMob(getBannerAdViewForAdmob(),activity);
        adMob.setListener(this);
        bannerAd = new BannerAd(adMob);
        interstitialAd = new InterstitialAd(adMob);
        rewardedVideoAd = new RewardedVideoAd(adMob);
    }

    private AdView getBannerAdViewForAdmob()
    {
        return noteFieldScreenView.getRootView().findViewById(R.id.admob_banner_ad_view);
    }


    @Override
    public void onRewardedFromVideoAd() {
        Logger.logMessage("onRewarded","donedonadone");
        listener.onRewardedFromVideoAd();

    }
}
