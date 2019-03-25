package com.stcodesapp.noteit.lifecycleObserver;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

public class MainActivityObserver implements LifecycleObserver {
    private final String TAG = this.getClass().getSimpleName();

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreateObserver()
    {
        Log.e(TAG,"onCreateObserver");

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)

    public void onResumeObserver()
    {
        Log.e(TAG,"onResumeObserver");

    }
}
