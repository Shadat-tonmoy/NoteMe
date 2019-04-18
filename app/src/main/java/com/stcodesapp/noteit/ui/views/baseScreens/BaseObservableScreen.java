package com.stcodesapp.noteit.ui.views.baseScreens;

public interface BaseObservableScreen<ListenerType> extends BaseScreen {

    void initUserInteractions();

    void registerListener(ListenerType listener);

    void unregisterListener(ListenerType listener);
}
