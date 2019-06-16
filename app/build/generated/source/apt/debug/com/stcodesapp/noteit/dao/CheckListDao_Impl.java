package com.stcodesapp.noteit.dao;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import com.stcodesapp.noteit.models.CheckList;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class CheckListDao_Impl implements CheckListDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfCheckList;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfCheckList;

  public CheckListDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCheckList = new EntityInsertionAdapter<CheckList>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `CheckList`(`checkListId`,`note_id`,`checkListTitle`,`checkListSecondFieldTitle`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CheckList value) {
        stmt.bindLong(1, value.getCheckListId());
        stmt.bindLong(2, value.getNoteId());
        if (value.getCheckListTitle() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCheckListTitle());
        }
        if (value.getCheckListSecondFieldTitle() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getCheckListSecondFieldTitle());
        }
      }
    };
    this.__deletionAdapterOfCheckList = new EntityDeletionOrUpdateAdapter<CheckList>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `CheckList` WHERE `checkListId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CheckList value) {
        stmt.bindLong(1, value.getCheckListId());
      }
    };
  }

  @Override
  public long insertSingleCheckList(CheckList checkList) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfCheckList.insertAndReturnId(checkList);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public long[] insertCheckList(CheckList... checkList) {
    __db.beginTransaction();
    try {
      long[] _result = __insertionAdapterOfCheckList.insertAndReturnIdsArray(checkList);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteCheckList(CheckList checkList) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfCheckList.handle(checkList);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<CheckList> getCheckListForNote(long noteId) {
    final String _sql = "select * from checklist where note_id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, noteId);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfCheckListId = _cursor.getColumnIndexOrThrow("checkListId");
      final int _cursorIndexOfNoteId = _cursor.getColumnIndexOrThrow("note_id");
      final int _cursorIndexOfCheckListTitle = _cursor.getColumnIndexOrThrow("checkListTitle");
      final int _cursorIndexOfCheckListSecondFieldTitle = _cursor.getColumnIndexOrThrow("checkListSecondFieldTitle");
      final List<CheckList> _result = new ArrayList<CheckList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final CheckList _item;
        _item = new CheckList();
        final long _tmpCheckListId;
        _tmpCheckListId = _cursor.getLong(_cursorIndexOfCheckListId);
        _item.setCheckListId(_tmpCheckListId);
        final long _tmpNoteId;
        _tmpNoteId = _cursor.getLong(_cursorIndexOfNoteId);
        _item.setNoteId(_tmpNoteId);
        final String _tmpCheckListTitle;
        _tmpCheckListTitle = _cursor.getString(_cursorIndexOfCheckListTitle);
        _item.setCheckListTitle(_tmpCheckListTitle);
        final String _tmpCheckListSecondFieldTitle;
        _tmpCheckListSecondFieldTitle = _cursor.getString(_cursorIndexOfCheckListSecondFieldTitle);
        _item.setCheckListSecondFieldTitle(_tmpCheckListSecondFieldTitle);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
