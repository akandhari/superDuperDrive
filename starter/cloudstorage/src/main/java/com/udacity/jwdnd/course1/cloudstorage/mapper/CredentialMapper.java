package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId} AND userid = #{userId}")
    Credential findOne(int credentialId,int userId);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> findByUserId(int userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, saltkey, password, userid) VALUES (#{url}, #{username}, #{saltkey}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    void insert(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId} AND userid = #{userId}")
    void delete(int credentialId,int userId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username},saltkey = #{saltkey}, password = #{password} WHERE credentialid = #{credentialId} AND userid = #{userId}")
    void update(Credential credential);
}
