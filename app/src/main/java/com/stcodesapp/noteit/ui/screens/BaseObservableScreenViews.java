package com.stcodesapp.noteit.ui.screens;

import android.content.Context;
import android.view.View;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseObservableScreenViews<Listener> implements BaseObservableScreen<Listener> {

    private View rootView;
    private Set<Listener> listeners = new HashSet<>();

    @Override
    public void registerListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void unregisterListener(Listener listener) {
        listeners.remove(listener);
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public Set<Listener> getListener() {
        return Collections.unmodifiableSet(listeners);
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    protected Context getContext()
    {
        return getRootView().getContext();
    }

    protected <T extends View> T findViewById(int id)
    {
        return getRootView().findViewById(id);
    }
}
