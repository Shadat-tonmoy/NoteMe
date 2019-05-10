package com.stcodesapp.noteit.models;

import com.stcodesapp.noteit.constants.Constants;

import java.util.ArrayList;
import java.util.List;

public class NoteComponents {

    private List<Contact> chosenContacts;
    private List<Email> emails;
    private List<Audio> chosenAudios;
    private Note note;

    public NoteComponents() {
        this.chosenContacts = new ArrayList<>();
        this.emails = new ArrayList<>();
        this.chosenAudios = new ArrayList<>();
        this.note = new Note();
    }

    public void setNoteIdToNoteComponents(long noteId)
    {
        for(Email email:getEmails())
            email.setNoteId(noteId);
        for(Contact contact:getChosenContacts())
            contact.setNoteId(noteId);
        for(Audio audio:getChosenAudios())
            audio.setNoteId(noteId);
    }

    public List<Contact> getChosenContacts() {
        return chosenContacts;
    }

    public void setChosenContacts(List<Contact> chosenContacts) {
        this.chosenContacts = chosenContacts;
    }

    public List<Audio> getChosenAudios() {
        return chosenAudios;
    }

    public void setChosenAudios(List<Audio> chosenAudios) {
        this.chosenAudios = chosenAudios;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
