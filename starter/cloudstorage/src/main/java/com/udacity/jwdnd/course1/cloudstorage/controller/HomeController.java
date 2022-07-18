package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private NoteService _noteService;
    private FileService _fileService;
    private CredentialService _credentailsService;
    private EncryptionService _encryptionService;
    private UserService _userService;

    public HomeController(NoteService noteService, FileService fileService,
                          CredentialService credentailsService,UserService userService,
                          EncryptionService encryptionService) {
        _noteService = noteService;
        _fileService = fileService;
        _credentailsService = credentailsService;
        _userService = userService;
        _encryptionService = encryptionService;
    }

    @GetMapping
    public String home(Authentication authentication, Model model, @ModelAttribute("newFile") File newFile,
                       @ModelAttribute("newNote") Note newNote, @ModelAttribute("newCredential") Credential newCredential) {
        String username = authentication.getName();
        User user = _userService.getUser(username);
        if(user != null) {
            model.addAttribute("notes", _noteService.getUserNotes(user.getUserId()));
            model.addAttribute("credentials", _credentailsService.getCredentialsByUserId(user.getUserId()));
            model.addAttribute("files", _fileService.getUserFiles(user.getUserId()));
            model.addAttribute("encryptionService", _encryptionService);
            model.addAttribute("userId",user.getUserId());
            return "home";
        }
        return "signup";
    }
}
