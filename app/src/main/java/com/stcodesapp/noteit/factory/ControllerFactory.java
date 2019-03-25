package com.stcodesapp.noteit.factory;

import com.stcodesapp.noteit.controllers.NoteListController;

public class ControllerFactory {

    private final TasksFactory tasksFactory;

    public ControllerFactory(TasksFactory tasksFactory) {
        this.tasksFactory = tasksFactory;
    }

    public NoteListController getNoteListController()
    {
        return new NoteListController(tasksFactory);

    }

}
