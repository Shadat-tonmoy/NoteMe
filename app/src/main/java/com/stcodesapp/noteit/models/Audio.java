package com.stcodesapp.noteit.models;

public class Audio {

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

    @Override
    public String toString() {
        return "Audio{" +
                "audioTitle='" + audioTitle + '\'' +
                ", audioSize='" + audioSize + '\'' +
                '}';
    }
}
