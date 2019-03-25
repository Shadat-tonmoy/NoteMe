package com.stcodesapp.noteit.factory;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;

import com.stcodesapp.noteit.viewModel.NoteAddViewModel;
import com.stcodesapp.noteit.viewModel.NoteViewModel;

public class ViewModelFactory {

    private Activity activity;

    public ViewModelFactory(Activity activity) {
        this.activity = activity;
    }

    public NoteViewModel getNoteViewModel()
    {
       NoteViewModel noteViewModel = ViewModelProviders.of((AppCompatActivity)activity).get(NoteViewModel .class);
       noteViewModel.bindActivity(activity);
       return noteViewModel;
    }

    public NoteAddViewModel getNoteAddViewModel()
    {
        NoteAddViewModel noteAddViewModel = ViewModelProviders.of((AppCompatActivity)activity).get(NoteAddViewModel.class);
        noteAddViewModel.bindActivity(activity);
        return noteAddViewModel;
    }


}
