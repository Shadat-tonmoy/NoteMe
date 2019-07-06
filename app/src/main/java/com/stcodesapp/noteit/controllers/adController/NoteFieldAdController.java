package com.stcodesapp.noteit.controllers.adController;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.ads.AdView;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.common.CustomApplication;
import com.stcodesapp.noteit.common.Logger;
import com.stcodesapp.noteit.common.adController.FullScreenAdController;
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
    private BannerAd bannerAd;
    private AdStrategyTrackingTask adStrategyTrackingTask;
    private TasksFactory tasksFactory;
    private Listener listener;
    private FullScreenAdController fullScreenAdController;

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
        fullScreenAdController = ((CustomApplication)activity.getApplication()).getFullScreenAdController();
        fullScreenAdController.getAdMob().setListener(this);
        fullScreenAdController.getAdMob().setAdView(getBannerAdViewForAdmob());
        bannerAd = new BannerAd(fullScreenAdController.getAdMob());
        bannerAd.loadAd();
    }

    public void onStop()
    {

    }

    public void showRewardedVideoAd()
    {
        Log.e("RWAd","Will Show");
        fullScreenAdController.showRewardedVideoAd();
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

    private AdView getBannerAdViewForAdmob()
    {
        return noteFieldScreenView.getRootView().findViewById(R.id.admob_banner_ad_view);
    }

    @Override
    public void onRewardedFromVideoAd() {
        Logger.logMessage("onRewarded","donedonadone");
        if(listener!=null)
            listener.onRewardedFromVideoAd();

    }
}
