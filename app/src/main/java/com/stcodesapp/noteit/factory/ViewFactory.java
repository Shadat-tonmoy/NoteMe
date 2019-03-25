package com.stcodesapp.noteit.factory;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.stcodesapp.noteit.ui.screenViews.NoteFieldScreenView;
import com.stcodesapp.noteit.ui.screenViews.NoteListScreenView;

public class ViewFactory {

    private NoteListScreenView noteListScreenView;
    private NoteFieldScreenView noteFieldScreenView;

    public NoteListScreenView getNoteList(LayoutInflater layoutInflater, @Nullable ViewGroup parent) {
        return new NoteListScreenView(layoutInflater, parent);
    }

    public NoteFieldScreenView getNoteFieldScreenView(LayoutInflater layoutInflater, @Nullable ViewGroup parent)
    {
        return new NoteFieldScreenView(layoutInflater,parent);
    }


}
