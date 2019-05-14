package com.stcodesapp.noteit.dao;

import java.util.List;

public interface DatabaseDao {

    List<Object> getAllXForNote(long noteId);
}
