package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.config.FileStoreConfig;
import com.udacity.jwdnd.course1.cloudstorage.exception.FileNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.List;

@Service
public class FileService {
    private Logger logger = LoggerFactory.getLogger(FileService.class);
    private FileMapper _fileMapper;
    private Path _filePath;

    public FileService(FileMapper fileMapper, FileStoreConfig fileStoreConfig) {
        _fileMapper = fileMapper;
        _filePath = Paths.get(fileStoreConfig.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(_filePath);
            logger.info("File Upload Directory :- " + _filePath);
        } catch (IOException ex) {
            try {
                throw new FileSystemException("Could not create a folder to store files " + ex.getLocalizedMessage());
            } catch (FileSystemException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void uploadFile(MultipartFile multipartFile, int userId) throws IOException {
        File file = new File();
        file.setContenttype(multipartFile.getContentType());
        file.setFilename(userId + "_" + multipartFile.getOriginalFilename());
        file.setUserId(userId);
        file.setFilesize(multipartFile.getSize() + "");
        file.setFiledata(multipartFile.getBytes());
        try {
            Path target = _filePath.resolve(file.getFilename());
            file.setFilename(multipartFile.getOriginalFilename());
            Files.copy(multipartFile.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            _fileMapper.insert(file);
        } catch (IOException ex) {
            throw new FileSystemException("Error Occurred while storing file : " + ex.getLocalizedMessage());
        }
    }

    public boolean deleteFile(int fileId, int userId) {
        try {
            File file = _fileMapper.getFileById(fileId, userId);
            if (file != null) {
                Path filePath = _filePath.resolve(userId + "_" + file.getFilename()).normalize();
                if (Files.deleteIfExists(filePath)) {
                    _fileMapper.deleteFile(fileId, userId);
                    return true;
                }
            }
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }
        return false;
    }

    public List<File> getUserFiles(int userId) {
        return _fileMapper.getFiles(userId);
    }

    public Resource getFilebyId(int fileId, int userId) {
        try {
            File file = _fileMapper.getFileById(fileId, userId);
            Path filePath = _filePath.resolve(userId + "_" + file.getFilename()).normalize();
            Resource fileResource = new UrlResource(filePath.toUri());
            if (fileResource.exists()) {
                return fileResource;
            } else {
                throw new FileNotFoundException("File Not Found : " + file.getFilename());
            }
        } catch (FileNotFoundException | MalformedURLException ex) {
            throw new FileNotFoundException(ex.getLocalizedMessage(), ex);
        }
    }
}
