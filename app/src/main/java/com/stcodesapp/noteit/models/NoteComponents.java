package com.stcodesapp.noteit.models;

import com.stcodesapp.noteit.constants.Constants;

import java.util.ArrayList;
import java.util.List;

public class NoteComponents {

    private String noteTitle, noteText;
    private List<Contact> chosenContacts;
    private List<Email> emails;

    public NoteComponents() {
        this.chosenContacts = new ArrayList<>();
        this.emails = new ArrayList<>();
        this.noteTitle = Constants.EMPTY_STRING;
        this.noteText= Constants.EMPTY_STRING;
    }

    public void setNoteIdToEmails(long noteId)
    {
        for(Email email:getEmails())
        {
            email.setNoteId(noteId);
        }
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public List<Contact> getChosenContacts() {
        return chosenContacts;
    }

    public void setChosenContacts(List<Contact> chosenContacts) {
        this.chosenContacts = chosenContacts;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }
}
