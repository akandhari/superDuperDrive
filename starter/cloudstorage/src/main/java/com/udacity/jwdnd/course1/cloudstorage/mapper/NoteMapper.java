package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM notes WHERE userid = #{userId}")
    List<Note> getNotes(int userId);

    @Insert("INSERT INTO notes (notetitle,  notedescription, userid) VALUES(#{noteTitle}, #{notedescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Delete("DELETE FROM notes WHERE noteid = #{noteId} AND userid = #{userId}")
    void deleteNote(int noteId,int userId);

    @Select("SELECT * FROM notes WHERE noteid = #{noteId} AND userid = #{userId}")
    Note getNoteById(int noteId,int userId);

    @Update("UPDATE notes SET notetitle = #{noteTitle}, notedescription = #{notedescription} WHERE noteid = #{noteId} AND userid = #{userId}")
    public void updateNote(Note note);
}
