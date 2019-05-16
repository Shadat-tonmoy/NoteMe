package com.stcodesapp.noteit.dao;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import com.stcodesapp.noteit.models.Email;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class EmailDao_Impl implements EmailDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfEmail;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfEmail;

  public EmailDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEmail = new EntityInsertionAdapter<Email>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `email`(`email_id`,`note_id`,`emailName`,`emailID`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Email value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getNoteId());
        if (value.getEmailName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getEmailName());
        }
        if (value.getEmailID() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getEmailID());
        }
      }
    };
    this.__deletionAdapterOfEmail = new EntityDeletionOrUpdateAdapter<Email>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `email` WHERE `email_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Email value) {
        stmt.bindLong(1, value.getId());
      }
    };
  }

  @Override
  public void insertEmail(Email email) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfEmail.insert(email);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertEmails(Email... emails) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfEmail.insert(emails);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteEmail(Email... emails) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfEmail.handleMultiple(emails);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Email getEmailById(long id) {
    final String _sql = "select * from email where email_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("email_id");
      final int _cursorIndexOfNoteId = _cursor.getColumnIndexOrThrow("note_id");
      final int _cursorIndexOfEmailName = _cursor.getColumnIndexOrThrow("emailName");
      final int _cursorIndexOfEmailID = _cursor.getColumnIndexOrThrow("emailID");
      final Email _result;
      if(_cursor.moveToFirst()) {
        final String _tmpEmailName;
        _tmpEmailName = _cursor.getString(_cursorIndexOfEmailName);
        final String _tmpEmailID;
        _tmpEmailID = _cursor.getString(_cursorIndexOfEmailID);
        _result = new Email(_tmpEmailName,_tmpEmailID);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final long _tmpNoteId;
        _tmpNoteId = _cursor.getLong(_cursorIndexOfNoteId);
        _result.setNoteId(_tmpNoteId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Email> getAllEmailForNote(long noteId) {
    final String _sql = "select * from email where note_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, noteId);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("email_id");
      final int _cursorIndexOfNoteId = _cursor.getColumnIndexOrThrow("note_id");
      final int _cursorIndexOfEmailName = _cursor.getColumnIndexOrThrow("emailName");
      final int _cursorIndexOfEmailID = _cursor.getColumnIndexOrThrow("emailID");
      final List<Email> _result = new ArrayList<Email>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Email _item;
        final String _tmpEmailName;
        _tmpEmailName = _cursor.getString(_cursorIndexOfEmailName);
        final String _tmpEmailID;
        _tmpEmailID = _cursor.getString(_cursorIndexOfEmailID);
        _item = new Email(_tmpEmailName,_tmpEmailID);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final long _tmpNoteId;
        _tmpNoteId = _cursor.getLong(_cursorIndexOfNoteId);
        _item.setNoteId(_tmpNoteId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
