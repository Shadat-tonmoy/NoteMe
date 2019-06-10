package com.stcodesapp.noteit.monetization.ads;

import android.view.View;

public class BannerAd implements MobileAd {


    private AdNetwork adNetwork;

    public BannerAd(AdNetwork adNetwork) {
        this.adNetwork = adNetwork;
    }

    @Override
    public void loadAd() {
        adNetwork.loadBannerAd();
    }

    @Override
    public void showAd() {
        adNetwork.showBannerAd();
    }
}
