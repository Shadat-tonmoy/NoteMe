package com.stcodesapp.noteit.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.stcodesapp.noteit.models.Audio;

import java.util.List;

@Dao
public interface AudioDao {

    @Insert
    void insertAudio(Audio... audio);


    @Delete
    void deleteAudio(Audio... audio);

    @Query("select * from audio where note_id = :noteId")
    List<Audio> getAllAudioForNote(long noteId);


}
