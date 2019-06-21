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
import com.stcodesapp.noteit.dao.AudioDao;
import com.stcodesapp.noteit.dao.AudioDao_Impl;
import com.stcodesapp.noteit.dao.CheckListDao;
import com.stcodesapp.noteit.dao.CheckListDao_Impl;
import com.stcodesapp.noteit.dao.CheckListItemDao;
import com.stcodesapp.noteit.dao.CheckListItemDao_Impl;
import com.stcodesapp.noteit.dao.ContactDao;
import com.stcodesapp.noteit.dao.ContactDao_Impl;
import com.stcodesapp.noteit.dao.EmailDao;
import com.stcodesapp.noteit.dao.EmailDao_Impl;
import com.stcodesapp.noteit.dao.ImageDao;
import com.stcodesapp.noteit.dao.ImageDao_Impl;
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

  private volatile ContactDao _contactDao;

  private volatile AudioDao _audioDao;

  private volatile ImageDao _imageDao;

  private volatile CheckListDao _checkListDao;

  private volatile CheckListItemDao _checkListItemDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(12) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `notes` (`note_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `noteTitle` TEXT, `noteText` TEXT, `backgroundColor` TEXT, `creationTime` INTEGER NOT NULL, `isImportant` INTEGER NOT NULL, `priority` INTEGER NOT NULL, `contactPriority` INTEGER NOT NULL, `emailPriority` INTEGER NOT NULL, `audioPriority` INTEGER NOT NULL, `imagePriority` INTEGER NOT NULL, `checkListPriority` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `email` (`email_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `note_id` INTEGER NOT NULL, `emailName` TEXT, `emailID` TEXT, FOREIGN KEY(`note_id`) REFERENCES `notes`(`note_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `contact` (`contact_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `note_id` INTEGER NOT NULL, `phoneNumber` TEXT, `displayName` TEXT, FOREIGN KEY(`note_id`) REFERENCES `notes`(`note_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `audio` (`audio_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `note_id` INTEGER NOT NULL, `uri` TEXT, `isFilePath` INTEGER NOT NULL, FOREIGN KEY(`note_id`) REFERENCES `notes`(`note_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `images` (`image_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `note_id` INTEGER NOT NULL, `uri` TEXT, FOREIGN KEY(`note_id`) REFERENCES `notes`(`note_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `CheckList` (`checkListId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `note_id` INTEGER NOT NULL, `checkListTitle` TEXT, `checkListSecondFieldTitle` TEXT, FOREIGN KEY(`note_id`) REFERENCES `notes`(`note_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ChecklistItem` (`field1` TEXT, `field2` TEXT, `isChecked` INTEGER NOT NULL, `checkListItemId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `checkListId` INTEGER NOT NULL, FOREIGN KEY(`checkListId`) REFERENCES `CheckList`(`checkListId`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"e5fc2bf07102ec80e3a8193c15795a34\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `notes`");
        _db.execSQL("DROP TABLE IF EXISTS `email`");
        _db.execSQL("DROP TABLE IF EXISTS `contact`");
        _db.execSQL("DROP TABLE IF EXISTS `audio`");
        _db.execSQL("DROP TABLE IF EXISTS `images`");
        _db.execSQL("DROP TABLE IF EXISTS `CheckList`");
        _db.execSQL("DROP TABLE IF EXISTS `ChecklistItem`");
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
        final HashMap<String, TableInfo.Column> _columnsNotes = new HashMap<String, TableInfo.Column>(12);
        _columnsNotes.put("note_id", new TableInfo.Column("note_id", "INTEGER", true, 1));
        _columnsNotes.put("noteTitle", new TableInfo.Column("noteTitle", "TEXT", false, 0));
        _columnsNotes.put("noteText", new TableInfo.Column("noteText", "TEXT", false, 0));
        _columnsNotes.put("backgroundColor", new TableInfo.Column("backgroundColor", "TEXT", false, 0));
        _columnsNotes.put("creationTime", new TableInfo.Column("creationTime", "INTEGER", true, 0));
        _columnsNotes.put("isImportant", new TableInfo.Column("isImportant", "INTEGER", true, 0));
        _columnsNotes.put("priority", new TableInfo.Column("priority", "INTEGER", true, 0));
        _columnsNotes.put("contactPriority", new TableInfo.Column("contactPriority", "INTEGER", true, 0));
        _columnsNotes.put("emailPriority", new TableInfo.Column("emailPriority", "INTEGER", true, 0));
        _columnsNotes.put("audioPriority", new TableInfo.Column("audioPriority", "INTEGER", true, 0));
        _columnsNotes.put("imagePriority", new TableInfo.Column("imagePriority", "INTEGER", true, 0));
        _columnsNotes.put("checkListPriority", new TableInfo.Column("checkListPriority", "INTEGER", true, 0));
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
        final HashMap<String, TableInfo.Column> _columnsContact = new HashMap<String, TableInfo.Column>(4);
        _columnsContact.put("contact_id", new TableInfo.Column("contact_id", "INTEGER", true, 1));
        _columnsContact.put("note_id", new TableInfo.Column("note_id", "INTEGER", true, 0));
        _columnsContact.put("phoneNumber", new TableInfo.Column("phoneNumber", "TEXT", false, 0));
        _columnsContact.put("displayName", new TableInfo.Column("displayName", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysContact = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysContact.add(new TableInfo.ForeignKey("notes", "CASCADE", "NO ACTION",Arrays.asList("note_id"), Arrays.asList("note_id")));
        final HashSet<TableInfo.Index> _indicesContact = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoContact = new TableInfo("contact", _columnsContact, _foreignKeysContact, _indicesContact);
        final TableInfo _existingContact = TableInfo.read(_db, "contact");
        if (! _infoContact.equals(_existingContact)) {
          throw new IllegalStateException("Migration didn't properly handle contact(com.stcodesapp.noteit.models.Contact).\n"
                  + " Expected:\n" + _infoContact + "\n"
                  + " Found:\n" + _existingContact);
        }
        final HashMap<String, TableInfo.Column> _columnsAudio = new HashMap<String, TableInfo.Column>(4);
        _columnsAudio.put("audio_id", new TableInfo.Column("audio_id", "INTEGER", true, 1));
        _columnsAudio.put("note_id", new TableInfo.Column("note_id", "INTEGER", true, 0));
        _columnsAudio.put("uri", new TableInfo.Column("uri", "TEXT", false, 0));
        _columnsAudio.put("isFilePath", new TableInfo.Column("isFilePath", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAudio = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysAudio.add(new TableInfo.ForeignKey("notes", "CASCADE", "NO ACTION",Arrays.asList("note_id"), Arrays.asList("note_id")));
        final HashSet<TableInfo.Index> _indicesAudio = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAudio = new TableInfo("audio", _columnsAudio, _foreignKeysAudio, _indicesAudio);
        final TableInfo _existingAudio = TableInfo.read(_db, "audio");
        if (! _infoAudio.equals(_existingAudio)) {
          throw new IllegalStateException("Migration didn't properly handle audio(com.stcodesapp.noteit.models.Audio).\n"
                  + " Expected:\n" + _infoAudio + "\n"
                  + " Found:\n" + _existingAudio);
        }
        final HashMap<String, TableInfo.Column> _columnsImages = new HashMap<String, TableInfo.Column>(3);
        _columnsImages.put("image_id", new TableInfo.Column("image_id", "INTEGER", true, 1));
        _columnsImages.put("note_id", new TableInfo.Column("note_id", "INTEGER", true, 0));
        _columnsImages.put("uri", new TableInfo.Column("uri", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysImages = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysImages.add(new TableInfo.ForeignKey("notes", "CASCADE", "NO ACTION",Arrays.asList("note_id"), Arrays.asList("note_id")));
        final HashSet<TableInfo.Index> _indicesImages = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoImages = new TableInfo("images", _columnsImages, _foreignKeysImages, _indicesImages);
        final TableInfo _existingImages = TableInfo.read(_db, "images");
        if (! _infoImages.equals(_existingImages)) {
          throw new IllegalStateException("Migration didn't properly handle images(com.stcodesapp.noteit.models.Image).\n"
                  + " Expected:\n" + _infoImages + "\n"
                  + " Found:\n" + _existingImages);
        }
        final HashMap<String, TableInfo.Column> _columnsCheckList = new HashMap<String, TableInfo.Column>(4);
        _columnsCheckList.put("checkListId", new TableInfo.Column("checkListId", "INTEGER", true, 1));
        _columnsCheckList.put("note_id", new TableInfo.Column("note_id", "INTEGER", true, 0));
        _columnsCheckList.put("checkListTitle", new TableInfo.Column("checkListTitle", "TEXT", false, 0));
        _columnsCheckList.put("checkListSecondFieldTitle", new TableInfo.Column("checkListSecondFieldTitle", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCheckList = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCheckList.add(new TableInfo.ForeignKey("notes", "CASCADE", "NO ACTION",Arrays.asList("note_id"), Arrays.asList("note_id")));
        final HashSet<TableInfo.Index> _indicesCheckList = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCheckList = new TableInfo("CheckList", _columnsCheckList, _foreignKeysCheckList, _indicesCheckList);
        final TableInfo _existingCheckList = TableInfo.read(_db, "CheckList");
        if (! _infoCheckList.equals(_existingCheckList)) {
          throw new IllegalStateException("Migration didn't properly handle CheckList(com.stcodesapp.noteit.models.CheckList).\n"
                  + " Expected:\n" + _infoCheckList + "\n"
                  + " Found:\n" + _existingCheckList);
        }
        final HashMap<String, TableInfo.Column> _columnsChecklistItem = new HashMap<String, TableInfo.Column>(5);
        _columnsChecklistItem.put("field1", new TableInfo.Column("field1", "TEXT", false, 0));
        _columnsChecklistItem.put("field2", new TableInfo.Column("field2", "TEXT", false, 0));
        _columnsChecklistItem.put("isChecked", new TableInfo.Column("isChecked", "INTEGER", true, 0));
        _columnsChecklistItem.put("checkListItemId", new TableInfo.Column("checkListItemId", "INTEGER", true, 1));
        _columnsChecklistItem.put("checkListId", new TableInfo.Column("checkListId", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysChecklistItem = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysChecklistItem.add(new TableInfo.ForeignKey("CheckList", "CASCADE", "NO ACTION",Arrays.asList("checkListId"), Arrays.asList("checkListId")));
        final HashSet<TableInfo.Index> _indicesChecklistItem = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoChecklistItem = new TableInfo("ChecklistItem", _columnsChecklistItem, _foreignKeysChecklistItem, _indicesChecklistItem);
        final TableInfo _existingChecklistItem = TableInfo.read(_db, "ChecklistItem");
        if (! _infoChecklistItem.equals(_existingChecklistItem)) {
          throw new IllegalStateException("Migration didn't properly handle ChecklistItem(com.stcodesapp.noteit.models.ChecklistItem).\n"
                  + " Expected:\n" + _infoChecklistItem + "\n"
                  + " Found:\n" + _existingChecklistItem);
        }
      }
    }, "e5fc2bf07102ec80e3a8193c15795a34", "a725404023248d1265feefb869062976");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "notes","email","contact","audio","images","CheckList","ChecklistItem");
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
      _db.execSQL("DELETE FROM `contact`");
      _db.execSQL("DELETE FROM `audio`");
      _db.execSQL("DELETE FROM `images`");
      _db.execSQL("DELETE FROM `CheckList`");
      _db.execSQL("DELETE FROM `ChecklistItem`");
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

  @Override
  public ContactDao contactDao() {
    if (_contactDao != null) {
      return _contactDao;
    } else {
      synchronized(this) {
        if(_contactDao == null) {
          _contactDao = new ContactDao_Impl(this);
        }
        return _contactDao;
      }
    }
  }

  @Override
  public AudioDao audioDao() {
    if (_audioDao != null) {
      return _audioDao;
    } else {
      synchronized(this) {
        if(_audioDao == null) {
          _audioDao = new AudioDao_Impl(this);
        }
        return _audioDao;
      }
    }
  }

  @Override
  public ImageDao imageDao() {
    if (_imageDao != null) {
      return _imageDao;
    } else {
      synchronized(this) {
        if(_imageDao == null) {
          _imageDao = new ImageDao_Impl(this);
        }
        return _imageDao;
      }
    }
  }

  @Override
  public CheckListDao checkListDao() {
    if (_checkListDao != null) {
      return _checkListDao;
    } else {
      synchronized(this) {
        if(_checkListDao == null) {
          _checkListDao = new CheckListDao_Impl(this);
        }
        return _checkListDao;
      }
    }
  }

  @Override
  public CheckListItemDao checkListItemDao() {
    if (_checkListItemDao != null) {
      return _checkListItemDao;
    } else {
      synchronized(this) {
        if(_checkListItemDao == null) {
          _checkListItemDao = new CheckListItemDao_Impl(this);
        }
        return _checkListItemDao;
      }
    }
  }
}
