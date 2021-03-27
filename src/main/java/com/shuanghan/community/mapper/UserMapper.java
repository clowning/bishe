package com.shuanghan.community.mapper;


import com.shuanghan.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void Insert(User user);

    @Select("select * from user where token = #{token}")
    User  findByToken(String token);
    @Select("select * from user where id = #{id}")
    User findById(Integer creator);
}
