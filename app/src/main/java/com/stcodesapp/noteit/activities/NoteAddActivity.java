package com.stcodesapp.noteit.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.NoteAddScreenManipulationTasks;
import com.stcodesapp.noteit.ui.screenViews.NoteFieldScreenView;
import com.stcodesapp.noteit.viewModel.NoteAddViewModel;

public class NoteAddActivity extends BaseActivity{

    private NoteFieldScreenView noteFieldScreenView;
    private NoteAddViewModel noteAddViewModel;
    private NoteAddScreenManipulationTasks noteAddScreenManipulationTasks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView()
    {
//        noteFieldScreenView = getFactory().getViewFactory().getNoteFieldScreenView(this.getLayoutInflater(),null);
//        noteAddViewModel = getFactory().getViewModelFactory().getNoteAddViewModel();
//        noteAddScreenManipulationTasks = getFactory().getTasksFactory().getNoteAddScreenManipulationTasks();
//        noteAddScreenManipulationTasks.bindView(noteFieldScreenView);
//        noteFieldScreenView.setListener(noteAddViewModel);
//        Note note = (Note) getIntent().getSerializableExtra("note");
//        MutableLiveData<Note> noteLiveData = new MutableLiveData<>();
//        noteLiveData.postValue(note);
//        noteAddViewModel.setNote(noteLiveData);
//        noteAddViewModel.getNote().observe(this, new Observer<Note>() {
//            @Override
//            public void onChanged(@Nullable Note note) {
//                noteAddScreenManipulationTasks.populateScreenFromNote(note);
//            }
//        });
//        setContentView(noteFieldScreenView.getRootView());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_add_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        noteAddViewModel.onOptionItemSelected(item);
        return true;
    }
}
