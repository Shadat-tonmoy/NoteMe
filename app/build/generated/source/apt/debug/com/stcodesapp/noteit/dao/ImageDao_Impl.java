package com.stcodesapp.noteit.dao;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import com.stcodesapp.noteit.models.Image;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class ImageDao_Impl implements ImageDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfImage;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfImage;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllImageForNote;

  public ImageDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfImage = new EntityInsertionAdapter<Image>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `images`(`image_id`,`note_id`,`uri`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Image value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getNoteId());
        if (value.getImageURI() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getImageURI());
        }
      }
    };
    this.__deletionAdapterOfImage = new EntityDeletionOrUpdateAdapter<Image>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `images` WHERE `image_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Image value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__preparedStmtOfDeleteAllImageForNote = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from images where note_id = ?";
        return _query;
      }
    };
  }

  @Override
  public void insertImage(Image... images) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfImage.insert(images);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public long insertSingleImage(Image image) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfImage.insertAndReturnId(image);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteImage(Image... images) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfImage.handleMultiple(images);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteAllImageForNote(long noteId) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllImageForNote.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      _stmt.bindLong(_argIndex, noteId);
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllImageForNote.release(_stmt);
    }
  }

  @Override
  public List<Image> getAllImageForNote(long noteId) {
    final String _sql = "select * from images where note_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, noteId);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("image_id");
      final int _cursorIndexOfNoteId = _cursor.getColumnIndexOrThrow("note_id");
      final int _cursorIndexOfImageURI = _cursor.getColumnIndexOrThrow("uri");
      final List<Image> _result = new ArrayList<Image>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Image _item;
        _item = new Image();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final long _tmpNoteId;
        _tmpNoteId = _cursor.getLong(_cursorIndexOfNoteId);
        _item.setNoteId(_tmpNoteId);
        final String _tmpImageURI;
        _tmpImageURI = _cursor.getString(_cursorIndexOfImageURI);
        _item.setImageURI(_tmpImageURI);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
