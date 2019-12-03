package com.stcodesapp.noteit.dao;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import com.stcodesapp.noteit.models.Note;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class NotesDao_Impl implements NotesDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfNote;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfNote;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfNote;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllNote;

  public NotesDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNote = new EntityInsertionAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `notes`(`note_id`,`noteTitle`,`noteText`,`backgroundColor`,`creationTime`,`isImportant`,`priority`,`contactPriority`,`emailPriority`,`audioPriority`,`imagePriority`,`checkListPriority`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        stmt.bindLong(1, value.getId());
        if (value.getNoteTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNoteTitle());
        }
        if (value.getNoteText() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getNoteText());
        }
        if (value.getBackgroundColor() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getBackgroundColor());
        }
        stmt.bindLong(5, value.getCreationTime());
        final int _tmp;
        _tmp = value.isImportant() ? 1 : 0;
        stmt.bindLong(6, _tmp);
        stmt.bindLong(7, value.getPriority());
        stmt.bindLong(8, value.getContactPriority());
        stmt.bindLong(9, value.getEmailPriority());
        stmt.bindLong(10, value.getAudioPriority());
        stmt.bindLong(11, value.getImagePriority());
        stmt.bindLong(12, value.getCheckListPriority());
      }
    };
    this.__deletionAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `notes` WHERE `note_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `notes` SET `note_id` = ?,`noteTitle` = ?,`noteText` = ?,`backgroundColor` = ?,`creationTime` = ?,`isImportant` = ?,`priority` = ?,`contactPriority` = ?,`emailPriority` = ?,`audioPriority` = ?,`imagePriority` = ?,`checkListPriority` = ? WHERE `note_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        stmt.bindLong(1, value.getId());
        if (value.getNoteTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNoteTitle());
        }
        if (value.getNoteText() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getNoteText());
        }
        if (value.getBackgroundColor() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getBackgroundColor());
        }
        stmt.bindLong(5, value.getCreationTime());
        final int _tmp;
        _tmp = value.isImportant() ? 1 : 0;
        stmt.bindLong(6, _tmp);
        stmt.bindLong(7, value.getPriority());
        stmt.bindLong(8, value.getContactPriority());
        stmt.bindLong(9, value.getEmailPriority());
        stmt.bindLong(10, value.getAudioPriority());
        stmt.bindLong(11, value.getImagePriority());
        stmt.bindLong(12, value.getCheckListPriority());
        stmt.bindLong(13, value.getId());
      }
    };
    this.__preparedStmtOfDeleteAllNote = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from notes";
        return _query;
      }
    };
  }

  @Override
  public long insert(Note note) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfNote.insertAndReturnId(note);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public long[] insertAllNote(Note... note) {
    __db.beginTransaction();
    try {
      long[] _result = __insertionAdapterOfNote.insertAndReturnIdsArray(note);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(Note note) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfNote.handle(note);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(Note note) {
    __db.beginTransaction();
    try {
      __updateAdapterOfNote.handle(note);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteAllNote() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllNote.acquire();
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllNote.release(_stmt);
    }
  }

  @Override
  public List<Note> getAllNoes() {
    final String _sql = "SELECT * FROM notes order by note_id desc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("note_id");
      final int _cursorIndexOfNoteTitle = _cursor.getColumnIndexOrThrow("noteTitle");
      final int _cursorIndexOfNoteText = _cursor.getColumnIndexOrThrow("noteText");
      final int _cursorIndexOfBackgroundColor = _cursor.getColumnIndexOrThrow("backgroundColor");
      final int _cursorIndexOfCreationTime = _cursor.getColumnIndexOrThrow("creationTime");
      final int _cursorIndexOfIsImportant = _cursor.getColumnIndexOrThrow("isImportant");
      final int _cursorIndexOfPriority = _cursor.getColumnIndexOrThrow("priority");
      final int _cursorIndexOfContactPriority = _cursor.getColumnIndexOrThrow("contactPriority");
      final int _cursorIndexOfEmailPriority = _cursor.getColumnIndexOrThrow("emailPriority");
      final int _cursorIndexOfAudioPriority = _cursor.getColumnIndexOrThrow("audioPriority");
      final int _cursorIndexOfImagePriority = _cursor.getColumnIndexOrThrow("imagePriority");
      final int _cursorIndexOfCheckListPriority = _cursor.getColumnIndexOrThrow("checkListPriority");
      final List<Note> _result = new ArrayList<Note>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Note _item;
        _item = new Note();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpNoteTitle;
        _tmpNoteTitle = _cursor.getString(_cursorIndexOfNoteTitle);
        _item.setNoteTitle(_tmpNoteTitle);
        final String _tmpNoteText;
        _tmpNoteText = _cursor.getString(_cursorIndexOfNoteText);
        _item.setNoteText(_tmpNoteText);
        final String _tmpBackgroundColor;
        _tmpBackgroundColor = _cursor.getString(_cursorIndexOfBackgroundColor);
        _item.setBackgroundColor(_tmpBackgroundColor);
        final long _tmpCreationTime;
        _tmpCreationTime = _cursor.getLong(_cursorIndexOfCreationTime);
        _item.setCreationTime(_tmpCreationTime);
        final boolean _tmpIsImportant;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsImportant);
        _tmpIsImportant = _tmp != 0;
        _item.setImportant(_tmpIsImportant);
        final int _tmpPriority;
        _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
        _item.setPriority(_tmpPriority);
        final int _tmpContactPriority;
        _tmpContactPriority = _cursor.getInt(_cursorIndexOfContactPriority);
        _item.setContactPriority(_tmpContactPriority);
        final int _tmpEmailPriority;
        _tmpEmailPriority = _cursor.getInt(_cursorIndexOfEmailPriority);
        _item.setEmailPriority(_tmpEmailPriority);
        final int _tmpAudioPriority;
        _tmpAudioPriority = _cursor.getInt(_cursorIndexOfAudioPriority);
        _item.setAudioPriority(_tmpAudioPriority);
        final int _tmpImagePriority;
        _tmpImagePriority = _cursor.getInt(_cursorIndexOfImagePriority);
        _item.setImagePriority(_tmpImagePriority);
        final int _tmpCheckListPriority;
        _tmpCheckListPriority = _cursor.getInt(_cursorIndexOfCheckListPriority);
        _item.setCheckListPriority(_tmpCheckListPriority);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Note> getImportantNoes() {
    final String _sql = "SELECT * FROM notes where isImportant";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("note_id");
      final int _cursorIndexOfNoteTitle = _cursor.getColumnIndexOrThrow("noteTitle");
      final int _cursorIndexOfNoteText = _cursor.getColumnIndexOrThrow("noteText");
      final int _cursorIndexOfBackgroundColor = _cursor.getColumnIndexOrThrow("backgroundColor");
      final int _cursorIndexOfCreationTime = _cursor.getColumnIndexOrThrow("creationTime");
      final int _cursorIndexOfIsImportant = _cursor.getColumnIndexOrThrow("isImportant");
      final int _cursorIndexOfPriority = _cursor.getColumnIndexOrThrow("priority");
      final int _cursorIndexOfContactPriority = _cursor.getColumnIndexOrThrow("contactPriority");
      final int _cursorIndexOfEmailPriority = _cursor.getColumnIndexOrThrow("emailPriority");
      final int _cursorIndexOfAudioPriority = _cursor.getColumnIndexOrThrow("audioPriority");
      final int _cursorIndexOfImagePriority = _cursor.getColumnIndexOrThrow("imagePriority");
      final int _cursorIndexOfCheckListPriority = _cursor.getColumnIndexOrThrow("checkListPriority");
      final List<Note> _result = new ArrayList<Note>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Note _item;
        _item = new Note();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpNoteTitle;
        _tmpNoteTitle = _cursor.getString(_cursorIndexOfNoteTitle);
        _item.setNoteTitle(_tmpNoteTitle);
        final String _tmpNoteText;
        _tmpNoteText = _cursor.getString(_cursorIndexOfNoteText);
        _item.setNoteText(_tmpNoteText);
        final String _tmpBackgroundColor;
        _tmpBackgroundColor = _cursor.getString(_cursorIndexOfBackgroundColor);
        _item.setBackgroundColor(_tmpBackgroundColor);
        final long _tmpCreationTime;
        _tmpCreationTime = _cursor.getLong(_cursorIndexOfCreationTime);
        _item.setCreationTime(_tmpCreationTime);
        final boolean _tmpIsImportant;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsImportant);
        _tmpIsImportant = _tmp != 0;
        _item.setImportant(_tmpIsImportant);
        final int _tmpPriority;
        _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
        _item.setPriority(_tmpPriority);
        final int _tmpContactPriority;
        _tmpContactPriority = _cursor.getInt(_cursorIndexOfContactPriority);
        _item.setContactPriority(_tmpContactPriority);
        final int _tmpEmailPriority;
        _tmpEmailPriority = _cursor.getInt(_cursorIndexOfEmailPriority);
        _item.setEmailPriority(_tmpEmailPriority);
        final int _tmpAudioPriority;
        _tmpAudioPriority = _cursor.getInt(_cursorIndexOfAudioPriority);
        _item.setAudioPriority(_tmpAudioPriority);
        final int _tmpImagePriority;
        _tmpImagePriority = _cursor.getInt(_cursorIndexOfImagePriority);
        _item.setImagePriority(_tmpImagePriority);
        final int _tmpCheckListPriority;
        _tmpCheckListPriority = _cursor.getInt(_cursorIndexOfCheckListPriority);
        _item.setCheckListPriority(_tmpCheckListPriority);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
