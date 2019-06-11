package com.stcodesapp.noteit.monetization.ads;

public class InterstitialAd implements MobileAd{

    private AdNetwork adNetwork;

    public InterstitialAd(AdNetwork adNetwork) {
        this.adNetwork = adNetwork;
    }

    @Override
    public void loadAd() {
        adNetwork.loadInterstitialAd();
    }

    @Override
    public void showAd() {
        adNetwork.showInterstitialAd();
    }
}
