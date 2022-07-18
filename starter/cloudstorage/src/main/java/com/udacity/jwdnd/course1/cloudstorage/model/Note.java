package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private Integer noteId;
    private String noteTitle;
    private String notedescription;
    private Integer userId;

    public Note(String noteTitle, String notedescription, Integer userId) {
        this.noteTitle = noteTitle;
        this.notedescription = notedescription;
        this.userId = userId;
    }
}
