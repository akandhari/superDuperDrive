package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private Logger logger = LoggerFactory.getLogger(CredentialController.class);
    private CredentialService _credentailsService;
    private EncryptionService _encryptionService;
    private UserService _userService;

    public CredentialController(CredentialService _credentailsService, EncryptionService _encryptionService, UserService _userService) {
        this._credentailsService = _credentailsService;
        this._encryptionService = _encryptionService;
        this._userService = _userService;
    }

    @PostMapping("add-credential")
    public String AddUpdateCredential(Model model, Authentication authentication, @ModelAttribute("newCredential") Credential newCredential) {
        int userId = getUserId(authentication);
        newCredential.setUserId(userId);
        if (newCredential.getCredentialId() != null) { //Existing Credentials Update Logic
            try {
                _credentailsService.updateCredential(newCredential);
                model.addAttribute("success", "Credentials Updated Successfully!");
            } catch (Exception e) {
                model.addAttribute("fail", "Failed to Update Credentials");
                logger.error("Error: " + e.getCause() + " Occurred. Message: " + e.getMessage());
            }
        } else { //New Credentials Proceed Add new credentials flow.
            try {
                _credentailsService.CreateCredential(newCredential);
                model.addAttribute("success", "Credentials Created Successfully!");
            } catch (Exception e) {
                model.addAttribute("fail", "Failed to Create Credentials");
                logger.error("Error: " + e.getCause() + " Occurred. Message: " + e.getMessage());
            }
        }
        return "result";
    }
    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(Model model, Authentication authentication, @PathVariable Integer credentialId) {
        int userId = getUserId(authentication);
        if (_credentailsService.CredentialExist(credentialId,userId)) {
            _credentailsService.deleteCredential(credentialId,userId);
            model.addAttribute("success", "Credentials Deleted Successfully!");
            model.addAttribute("credentials", _credentailsService.getCredentialsByUserId(userId));
        } else {
            model.addAttribute("fail", "Resource not found!");
        }

        return "result";
    }


    private int getUserId(Authentication authentication) {
        String username = authentication.getName();
        User user = _userService.getUser(username);
        return user.getUserId();
    }
}
