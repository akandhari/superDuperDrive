package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private UserMapper _userMapper;
    private HashService _hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this._userMapper = userMapper;
        this._hashService = hashService;
    }
    public boolean isUsernameAvailable(String username){
        return _userMapper.findByUsername(username) == null;
    }
    public int createUserAccount(User user){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = _hashService.getHashedValue(user.getPassword(), encodedSalt);
        user.setSalt(encodedSalt);
        user.setPassword(hashedPassword);
        return _userMapper.insert(new  User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
    }
    public User getUser(String username){
        return _userMapper.findByUsername(username);
    }
}
