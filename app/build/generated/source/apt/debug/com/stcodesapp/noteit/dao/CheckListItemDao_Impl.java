package com.stcodesapp.noteit.dao;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import com.stcodesapp.noteit.models.ChecklistItem;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("unchecked")
public class CheckListItemDao_Impl implements CheckListItemDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfChecklistItem;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfChecklistItem;

  public CheckListItemDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfChecklistItem = new EntityInsertionAdapter<ChecklistItem>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `ChecklistItem`(`field1`,`field2`,`isChecked`,`checkListItemId`,`checkListId`) VALUES (?,?,?,nullif(?, 0),?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ChecklistItem value) {
        if (value.getField1() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getField1());
        }
        if (value.getField2() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getField2());
        }
        final int _tmp;
        _tmp = value.isChecked() ? 1 : 0;
        stmt.bindLong(3, _tmp);
        stmt.bindLong(4, value.getId());
        stmt.bindLong(5, value.getCheckListId());
      }
    };
    this.__deletionAdapterOfChecklistItem = new EntityDeletionOrUpdateAdapter<ChecklistItem>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `ChecklistItem` WHERE `checkListItemId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ChecklistItem value) {
        stmt.bindLong(1, value.getId());
      }
    };
  }

  @Override
  public void insertCheckListItem(ChecklistItem... checklistItem) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfChecklistItem.insert(checklistItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public long insertSingleCheckListItem(ChecklistItem checklistItem) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfChecklistItem.insertAndReturnId(checklistItem);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteCheckListItem(ChecklistItem checklistItem) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfChecklistItem.handle(checklistItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }
}
