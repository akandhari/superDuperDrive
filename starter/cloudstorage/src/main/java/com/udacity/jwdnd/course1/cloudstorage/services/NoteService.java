package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper _noteMapper;

    public NoteService(NoteMapper noteMapper) {
        _noteMapper = noteMapper;
    }

    public void createNote(Note note){
        _noteMapper.insert(note);
    }

    public void updateNote(Note note){
        _noteMapper.updateNote(note);
    }

    public void deleteNote(int noteId,int userId){
        _noteMapper.deleteNote(noteId,userId);
    }

    public List<Note> getUserNotes(int userId){
        return _noteMapper.getNotes(userId);
    }


    public Note getNoteById(int noteId,int userId){
        return _noteMapper.getNoteById(noteId,userId);
    }
}
