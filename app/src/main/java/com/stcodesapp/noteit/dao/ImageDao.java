package com.stcodesapp.noteit.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Image;

import java.util.List;

@Dao
public interface ImageDao {

    @Insert
    void insertImage(Image... images);

    @Insert
    long insertSingleImage(Image image);

    @Delete
    void deleteImage(Image... images);

    @Query("select * from images where note_id = :noteId")
    List<Image> getAllImageForNote(long noteId);


}
