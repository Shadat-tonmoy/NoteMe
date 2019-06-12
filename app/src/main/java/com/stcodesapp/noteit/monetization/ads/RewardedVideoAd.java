package com.stcodesapp.noteit.monetization.ads;

public class RewardedVideoAd implements MobileAd{

    private AdNetwork adNetwork;

    public RewardedVideoAd(AdNetwork adNetwork) {
        this.adNetwork = adNetwork;
    }

    @Override
    public void loadAd() {
        adNetwork.loadRewardedVideoAd();

    }

    @Override
    public void showAd() {
        adNetwork.showRewardedVideoAd();
    }
}
