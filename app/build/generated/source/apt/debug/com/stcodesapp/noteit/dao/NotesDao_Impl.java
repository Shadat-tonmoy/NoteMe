package com.stcodesapp.noteit.dao;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import android.support.annotation.NonNull;
import com.stcodesapp.noteit.entities.Note;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class NotesDao_Impl implements NotesDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfNote;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfNote;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfNote;

  public NotesDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNote = new EntityInsertionAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `notes`(`id`,`noteTitle`,`noteText`,`backgroundColor`,`imageName`,`audioName`,`tag`,`phoneNumbers`,`creationTime`,`important`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
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
        if (value.getImageName() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getImageName());
        }
        if (value.getAudioName() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getAudioName());
        }
        if (value.getTag() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getTag());
        }
        if (value.getPhoneNumbers() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getPhoneNumbers());
        }
        stmt.bindLong(9, value.getCreationTime());
        final int _tmp;
        _tmp = value.isImportant() ? 1 : 0;
        stmt.bindLong(10, _tmp);
      }
    };
    this.__deletionAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `notes` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
      }
    };
    this.__updateAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `notes` SET `id` = ?,`noteTitle` = ?,`noteText` = ?,`backgroundColor` = ?,`imageName` = ?,`audioName` = ?,`tag` = ?,`phoneNumbers` = ?,`creationTime` = ?,`important` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
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
        if (value.getImageName() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getImageName());
        }
        if (value.getAudioName() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getAudioName());
        }
        if (value.getTag() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getTag());
        }
        if (value.getPhoneNumbers() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getPhoneNumbers());
        }
        stmt.bindLong(9, value.getCreationTime());
        final int _tmp;
        _tmp = value.isImportant() ? 1 : 0;
        stmt.bindLong(10, _tmp);
        if (value.getId() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getId());
        }
      }
    };
  }

  @Override
  public void insert(Note note) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfNote.insert(note);
      __db.setTransactionSuccessful();
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
  public LiveData<List<Note>> getAllNoes() {
    final String _sql = "SELECT * FROM notes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Note>>() {
      private Observer _observer;

      @Override
      protected List<Note> compute() {
        if (_observer == null) {
          _observer = new Observer("notes") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfNoteTitle = _cursor.getColumnIndexOrThrow("noteTitle");
          final int _cursorIndexOfNoteText = _cursor.getColumnIndexOrThrow("noteText");
          final int _cursorIndexOfBackgroundColor = _cursor.getColumnIndexOrThrow("backgroundColor");
          final int _cursorIndexOfImageName = _cursor.getColumnIndexOrThrow("imageName");
          final int _cursorIndexOfAudioName = _cursor.getColumnIndexOrThrow("audioName");
          final int _cursorIndexOfTag = _cursor.getColumnIndexOrThrow("tag");
          final int _cursorIndexOfPhoneNumbers = _cursor.getColumnIndexOrThrow("phoneNumbers");
          final int _cursorIndexOfCreationTime = _cursor.getColumnIndexOrThrow("creationTime");
          final int _cursorIndexOfImportant = _cursor.getColumnIndexOrThrow("important");
          final List<Note> _result = new ArrayList<Note>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Note _item;
            _item = new Note();
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
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
            final String _tmpImageName;
            _tmpImageName = _cursor.getString(_cursorIndexOfImageName);
            _item.setImageName(_tmpImageName);
            final String _tmpAudioName;
            _tmpAudioName = _cursor.getString(_cursorIndexOfAudioName);
            _item.setAudioName(_tmpAudioName);
            final String _tmpTag;
            _tmpTag = _cursor.getString(_cursorIndexOfTag);
            _item.setTag(_tmpTag);
            final String _tmpPhoneNumbers;
            _tmpPhoneNumbers = _cursor.getString(_cursorIndexOfPhoneNumbers);
            _item.setPhoneNumbers(_tmpPhoneNumbers);
            final long _tmpCreationTime;
            _tmpCreationTime = _cursor.getLong(_cursorIndexOfCreationTime);
            _item.setCreationTime(_tmpCreationTime);
            final boolean _tmpImportant;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfImportant);
            _tmpImportant = _tmp != 0;
            _item.setImportant(_tmpImportant);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }
}
