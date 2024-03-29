package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM files WHERE userid = #{userId}")
    List<File> getFiles(int userId);

    @Insert("INSERT INTO files (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contenttype}, #{filesize}, #{userId}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Delete("DELETE FROM files WHERE fileid = #{fileId} AND userid=#{userId}")
    void deleteFile(int fileId,int userId);

    @Select("SELECT * FROM files WHERE fileid = #{fileId} AND userid=#{userId}")
    File getFileById(int fileId,int userId);

    @Select("SELECT * FROM files WHERE userid = #{userId} AND filename = #{filename}")
    File getFileByFilename(int userId, String filename);
}
