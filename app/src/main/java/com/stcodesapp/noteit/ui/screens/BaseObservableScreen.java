package com.stcodesapp.noteit.ui.screens;

import java.util.Set;

public interface BaseObservableScreen<ListenerType> extends BaseScreen{

    void registerListener(ListenerType listener);

    void unregisterListener(ListenerType listener);

    Set<ListenerType> getListener();

}
