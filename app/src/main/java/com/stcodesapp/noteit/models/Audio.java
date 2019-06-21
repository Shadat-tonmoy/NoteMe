package com.stcodesapp.noteit.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "audio", foreignKeys = @ForeignKey(
        entity = Note.class,
        parentColumns = {"note_id"},
        childColumns = {"note_id"},
        onDelete = ForeignKey.CASCADE
))
public class Audio implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "audio_id")
    private long id;

    @ColumnInfo(name = "note_id")
    private long noteId;

    @Ignore
    private String audioTitle;
    @Ignore
    private String audioSize;
    @ColumnInfo(name = "uri")
    private String audioUri;

    private boolean isFilePath;

    public Audio()
    {

    }

    public Audio(String audioTitle, String audioSize, String audioUri,boolean isFilePath) {
        this.audioTitle = audioTitle;
        this.audioSize = audioSize;
        this.audioUri = audioUri;
        this.isFilePath= isFilePath;
    }

    public String getAudioTitle() {
        return audioTitle;
    }

    public void setAudioTitle(String audioTitle) {
        this.audioTitle = audioTitle;
    }

    public String getAudioSize() {
        return audioSize;
    }

    public void setAudioSize(String audioSize) {
        this.audioSize = audioSize;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public String getAudioUri() {
        return audioUri;
    }

    public void setAudioUri(String audioUri) {
        this.audioUri = audioUri;
    }

    public boolean isFilePath() {
        return isFilePath;
    }

    public void setFilePath(boolean filePath) {
        isFilePath = filePath;
    }

    @Override
    public String toString() {
        return "Audio{" +
                "id=" + id +
                ", noteId=" + noteId +
                ", audioTitle='" + audioTitle + '\'' +
                ", audioSize='" + audioSize + '\'' +
                ", audioUri='" + audioUri + '\'' +
                '}';
    }


}
