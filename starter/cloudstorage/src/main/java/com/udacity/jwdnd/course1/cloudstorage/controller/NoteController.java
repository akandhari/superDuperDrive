package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("note")
public class NoteController {
    private NoteService _noteService;
    private UserService _userService;

    public NoteController(NoteService noteService, UserService userService) {
        _noteService = noteService;
        _userService = userService;
    }

    @PostMapping("add-note")
    public String addNewNote(
            Authentication authentication, @ModelAttribute("newFile") File newFile,
            @ModelAttribute("newNote") Note newNote, @ModelAttribute("newCredential") Credential newCredential,
            Model model) {
        int userId = getUserId(authentication);
        String newTitle = newNote.getNoteTitle();

        String newDescription = newNote.getNotedescription();
        if (newNote.getNoteId() != null) { //Update Existing Note
            int noteId = newNote.getNoteId().intValue();
            Note existingNote = _noteService.getNoteById(noteId, userId);
            if(existingNote != null) {
                existingNote.setNoteTitle(newTitle);
                existingNote.setNotedescription(newDescription);
                _noteService.updateNote(existingNote);
                model.addAttribute("success", "Note Updated Successfully!");
            } else {
                model.addAttribute("fail", "Resource not found!");
            }
        } else {
            _noteService.createNote(new Note(newTitle, newDescription, userId));
            model.addAttribute("success", "Note Added Successfully!");

        }
        model.addAttribute("notes", _noteService.getUserNotes(userId));
        return "result";
    }

    @GetMapping(value = "get-note/{noteId}")
    public Note getNote(@PathVariable int noteId, Authentication authentication, Model model) {
        int userId = getUserId(authentication);
        return _noteService.getNoteById(noteId, userId);
    }

    @GetMapping(value = "/delete-note/{noteId}")
    public String deleteNote(Authentication authentication, @PathVariable Integer noteId, Model model) {
        Integer userId = getUserId(authentication);
        if (_noteService.getNoteById(noteId, userId) != null) {
            _noteService.deleteNote(noteId, userId);
            model.addAttribute("notes", _noteService.getUserNotes(userId));
            model.addAttribute("success", "Note Deleted Successfully!");
        } else {
            model.addAttribute("fail", "Resource not found!");
        }
        return "result";
    }

    private Integer getUserId(Authentication authentication) {
        String userName = authentication.getName();
        User user = _userService.getUser(userName);
        return user.getUserId();
    }
}
