package com.stcodesapp.noteit.dao;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import com.stcodesapp.noteit.models.Audio;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class AudioDao_Impl implements AudioDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfAudio;

  public AudioDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAudio = new EntityInsertionAdapter<Audio>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `audio`(`audio_id`,`note_id`,`uri`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Audio value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getNoteId());
        if (value.getAudioUri() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getAudioUri());
        }
      }
    };
  }

  @Override
  public void insertAudio(Audio... audio) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfAudio.insert(audio);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Audio> getAllAudioForNote(long noteId) {
    final String _sql = "select * from audio where note_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, noteId);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("audio_id");
      final int _cursorIndexOfNoteId = _cursor.getColumnIndexOrThrow("note_id");
      final int _cursorIndexOfAudioUri = _cursor.getColumnIndexOrThrow("uri");
      final List<Audio> _result = new ArrayList<Audio>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Audio _item;
        _item = new Audio();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final long _tmpNoteId;
        _tmpNoteId = _cursor.getLong(_cursorIndexOfNoteId);
        _item.setNoteId(_tmpNoteId);
        final String _tmpAudioUri;
        _tmpAudioUri = _cursor.getString(_cursorIndexOfAudioUri);
        _item.setAudioUri(_tmpAudioUri);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
