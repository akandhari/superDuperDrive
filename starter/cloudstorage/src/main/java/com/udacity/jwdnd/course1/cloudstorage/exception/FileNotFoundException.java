package com.udacity.jwdnd.course1.cloudstorage.exception;

public class FileNotFoundException extends RuntimeException{

    public FileNotFoundException(String msg){
        super(msg);
    }
    public  FileNotFoundException(String msg,Throwable ex){
        super(msg,ex);
    }
}
