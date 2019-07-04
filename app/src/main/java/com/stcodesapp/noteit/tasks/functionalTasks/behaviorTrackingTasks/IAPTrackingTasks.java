package com.stcodesapp.noteit.tasks.functionalTasks.behaviorTrackingTasks;

import android.app.Activity;

public class IAPTrackingTasks {

    private Activity activity;

    public IAPTrackingTasks(Activity activity) {
        this.activity = activity;
    }

    public boolean isPaidUser()
    {
        return false;
    }

    public void setSubscribedUser(boolean isSubscribed)
    {

    }
}
