package com.stcodesapp.noteit.tasks.utilityTasks;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.Constants;


public class SharingTasks {

    private Activity activity;

    public SharingTasks(Activity activity) {
        this.activity = activity;
    }


    public void shareText(String text)
    {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType(Constants.TEXT_TYPE);
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, Constants.SHARE_SUBJECT);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
        activity.startActivity(Intent.createChooser(sharingIntent, Constants.SHARE_VIA));
    }

    public void shareApp()
    {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType(Constants.TEXT_TYPE);
        String appURL = Constants.PLAYSTORE_BASE_URL +activity.getPackageName();
        String shareBody = Constants.SHARE_BODY+appURL;
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, Constants.SHARE_SUBJECT);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        activity.startActivity(Intent.createChooser(sharingIntent, Constants.SHARE_VIA));
    }

    public void emailSupport()
    {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType(Constants.TEXT_TYPE);
        String emailSubject = Constants.EMAIL_SUBJECT+activity.getString(R.string.app_name);
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        intent.putExtra(Intent.EXTRA_TEXT, Constants.EMPTY_STRING);
        intent.setData(Uri.parse(Constants.EMAIL_ADDRESS));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public void openAppLink(String appLink)
    {
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appLink)));
        } catch (android.content.ActivityNotFoundException anfe) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appLink)));
        }
    }

    public void rateApp()
    {
        String appID = activity.getPackageName();
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appID)));
        } catch (android.content.ActivityNotFoundException anfe) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appID)));
        }
    }

    public void openURL(String url)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(browserIntent);
    }

}
