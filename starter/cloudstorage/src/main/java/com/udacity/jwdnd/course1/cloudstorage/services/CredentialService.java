package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {
    private CredentialMapper _credentialMapper;
    private EncryptionService _encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        _credentialMapper = credentialMapper;
        _encryptionService = encryptionService;
    }

    public void CreateCredential(Credential credential) {
        String encodedSaltKey = generateKey();
        credential.setSaltkey(encodedSaltKey);
        String encryptedPassword = _encryptionService.encryptValue(credential.getPassword(), encodedSaltKey);
        credential.setPassword(encryptedPassword);
        _credentialMapper.insert(credential);
    }

    public void updateCredential(Credential credential) {
        String encodedSaltVal = generateKey();
        credential.setSaltkey(encodedSaltVal);
        String encryptedPassword = _encryptionService.encryptValue(credential.getPassword(), encodedSaltVal);
        credential.setPassword(encryptedPassword);
        _credentialMapper.update(credential);
    }

    public void deleteCredential(int credentialId,int userId) {
        _credentialMapper.delete(credentialId,userId);
    }

    public List<Credential> getCredentialsByUserId(int userId) {
        List<Credential> credentialList = _credentialMapper.findByUserId(userId);
        return credentialList;
    }

    public Credential getCredentialByCredentialId(int credentialId,int userId) {

        return _credentialMapper.findOne(credentialId,userId);
    }

    public Boolean CredentialExist(int credentialId,int userId) {
        if (getCredentialByCredentialId(credentialId,userId) != null) {
            return true;
        }
        return false;
    }

    public String generateKey() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}