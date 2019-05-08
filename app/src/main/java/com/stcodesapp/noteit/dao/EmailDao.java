package com.stcodesapp.noteit.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.stcodesapp.noteit.models.Email;

import java.util.List;

@Dao
public interface EmailDao {

    @Insert
    void insertEmail(Email email);

    @Query("select * from email where email_id = :id")
    Email getEmailById(long id);

    @Query("select * from email where note_id = :noteId")
    List<Email> getAllEmailForNote(long noteId);


}
