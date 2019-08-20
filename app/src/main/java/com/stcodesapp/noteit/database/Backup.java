package com.stcodesapp.noteit.database;

import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.Image;
import com.stcodesapp.noteit.models.Note;

import java.util.List;

public class Backup
{

    private List<Note> notes;
    private List<Contact> contacts;
    private List<Email> emails;
    private List<Audio> audio;
    private List<Image> images;

    public Backup(List<Note> notes, List<Contact> contacts, List<Email> emails, List<Audio> audio, List<Image> images) {
        this.notes = notes;
        this.contacts = contacts;
        this.emails = emails;
        this.audio = audio;
        this.images = images;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public List<Audio> getAudio() {
        return audio;
    }

    public void setAudio(List<Audio> audio) {
        this.audio = audio;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Backup{" +
                "notes=" + notes +
                ", contacts=" + contacts +
                ", emails=" + emails +
                ", audio=" + audio +
                ", images=" + images +
                '}';
    }
}
