package com.stcodesapp.noteit.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.stcodesapp.noteit.models.Contact;

import java.util.List;

@Dao
public interface ContactDao {

    @Query("select * from contact")
    List<Contact> getAllContacts();

    @Insert
    void insertContact(Contact... contact);

    @Insert
    long insertSingleContact(Contact contact);

    @Delete
    void deleteContact(Contact... contacts);


    @Query("select * from contact where note_id = :noteId")
    List<Contact> getAllContactsForNote(long noteId);

    @Query("delete from contact where note_id = :noteId")
    int deleteAllContactForNote(long noteId);

}
