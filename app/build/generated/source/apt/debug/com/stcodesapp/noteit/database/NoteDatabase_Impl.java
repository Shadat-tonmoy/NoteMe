package com.stcodesapp.noteit.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import com.stcodesapp.noteit.dao.EmailDao;
import com.stcodesapp.noteit.dao.EmailDao_Impl;
import com.stcodesapp.noteit.dao.NotesDao;
import com.stcodesapp.noteit.dao.NotesDao_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class NoteDatabase_Impl extends NoteDatabase {
  private volatile NotesDao _notesDao;

  private volatile EmailDao _emailDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `notes` (`note_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `noteTitle` TEXT, `noteText` TEXT, `backgroundColor` TEXT, `creationTime` INTEGER NOT NULL, `isImportant` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `email` (`email_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `note_id` INTEGER NOT NULL, `emailName` TEXT, `emailID` TEXT, FOREIGN KEY(`note_id`) REFERENCES `notes`(`note_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"1932315481959ee1558550926ffff0ae\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `notes`");
        _db.execSQL("DROP TABLE IF EXISTS `email`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsNotes = new HashMap<String, TableInfo.Column>(6);
        _columnsNotes.put("note_id", new TableInfo.Column("note_id", "INTEGER", true, 1));
        _columnsNotes.put("noteTitle", new TableInfo.Column("noteTitle", "TEXT", false, 0));
        _columnsNotes.put("noteText", new TableInfo.Column("noteText", "TEXT", false, 0));
        _columnsNotes.put("backgroundColor", new TableInfo.Column("backgroundColor", "TEXT", false, 0));
        _columnsNotes.put("creationTime", new TableInfo.Column("creationTime", "INTEGER", true, 0));
        _columnsNotes.put("isImportant", new TableInfo.Column("isImportant", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNotes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNotes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNotes = new TableInfo("notes", _columnsNotes, _foreignKeysNotes, _indicesNotes);
        final TableInfo _existingNotes = TableInfo.read(_db, "notes");
        if (! _infoNotes.equals(_existingNotes)) {
          throw new IllegalStateException("Migration didn't properly handle notes(com.stcodesapp.noteit.models.Note).\n"
                  + " Expected:\n" + _infoNotes + "\n"
                  + " Found:\n" + _existingNotes);
        }
        final HashMap<String, TableInfo.Column> _columnsEmail = new HashMap<String, TableInfo.Column>(4);
        _columnsEmail.put("email_id", new TableInfo.Column("email_id", "INTEGER", true, 1));
        _columnsEmail.put("note_id", new TableInfo.Column("note_id", "INTEGER", true, 0));
        _columnsEmail.put("emailName", new TableInfo.Column("emailName", "TEXT", false, 0));
        _columnsEmail.put("emailID", new TableInfo.Column("emailID", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEmail = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysEmail.add(new TableInfo.ForeignKey("notes", "CASCADE", "NO ACTION",Arrays.asList("note_id"), Arrays.asList("note_id")));
        final HashSet<TableInfo.Index> _indicesEmail = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoEmail = new TableInfo("email", _columnsEmail, _foreignKeysEmail, _indicesEmail);
        final TableInfo _existingEmail = TableInfo.read(_db, "email");
        if (! _infoEmail.equals(_existingEmail)) {
          throw new IllegalStateException("Migration didn't properly handle email(com.stcodesapp.noteit.models.Email).\n"
                  + " Expected:\n" + _infoEmail + "\n"
                  + " Found:\n" + _existingEmail);
        }
      }
    }, "1932315481959ee1558550926ffff0ae", "10d392c23d680da388e1a15b07043d3c");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "notes","email");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `notes`");
      _db.execSQL("DELETE FROM `email`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public NotesDao notesDao() {
    if (_notesDao != null) {
      return _notesDao;
    } else {
      synchronized(this) {
        if(_notesDao == null) {
          _notesDao = new NotesDao_Impl(this);
        }
        return _notesDao;
      }
    }
  }

  @Override
  public EmailDao emailDao() {
    if (_emailDao != null) {
      return _emailDao;
    } else {
      synchronized(this) {
        if(_emailDao == null) {
          _emailDao = new EmailDao_Impl(this);
        }
        return _emailDao;
      }
    }
  }
}
