package com.stcodesapp.noteit.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "audio")
public class Audio implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "audio_id")
    private long id;

    private String audioTitle,audioSize;

    public Audio(String audioTitle, String audioSize) {
        this.audioTitle = audioTitle;
        this.audioSize = audioSize;
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

    @Override
    public String toString() {
        return "Audio{" +
                "id=" + id +
                ", audioTitle='" + audioTitle + '\'' +
                ", audioSize='" + audioSize + '\'' +
                '}';
    }
}
