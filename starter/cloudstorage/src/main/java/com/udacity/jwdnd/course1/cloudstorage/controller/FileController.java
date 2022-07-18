package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {
    private FileService _fileService;
    private UserService _userService;

    public FileController(FileService fileService, UserService userService) {
        _fileService = fileService;
        _userService = userService;
    }

    @PostMapping()
    public String saveFile(Model model, Authentication authentication, @RequestParam("newFile") MultipartFile multipartFile,
                           @ModelAttribute("newNote") Note newNote, @ModelAttribute("newCredential") Credential newCredential) throws IOException {
        int userId = getUserId(authentication);

        if (multipartFile.isEmpty()) {
            model.addAttribute("errorMessage", "File is empty");
        } else {
            boolean duplicateFile = false;
            File[] existingFiles = _fileService.getUserFiles(userId).toArray(new File[0]);
            for (File file : existingFiles) {
                if (file.getFilename().trim().equalsIgnoreCase(multipartFile.getOriginalFilename().trim())) {
                    duplicateFile = true;
                    break;
                }
            }
            if (!duplicateFile) {
                _fileService.uploadFile(multipartFile, userId);
                model.addAttribute("success", "File saved Successfully!");
            } else {
                model.addAttribute("errorMessage", "File already exists!");
            }
        }
        return "result";

    }
    @GetMapping("delete-file/{fileId}")
    public String deleteFile(@PathVariable(name = "fileId") int fileId, Model model, Authentication authentication) {
        int userId = getUserId(authentication);
        if (fileId > 0 && _fileService.getFilebyId(fileId, userId) != null) {
            if (_fileService.deleteFile(fileId, userId)) {
                model.addAttribute("success", "file Deleted Successfully");
            } else {
                model.addAttribute("errorMessage", "Internal Error Occurred.");
            }
        } else {
            model.addAttribute("fail", "Resource not found!");
        }
        return "result";
    }

    @GetMapping("get-file/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable(name = "fileId") int fileId,Authentication authentication) {
        int userId = getUserId(authentication);
        Resource file = _fileService.getFilebyId(fileId,userId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

//PRIVATE METHODS
    private Integer getUserId(Authentication authentication) {
        String username = authentication.getName();
        User user = _userService.getUser(username);
        return user.getUserId();
    }
}
