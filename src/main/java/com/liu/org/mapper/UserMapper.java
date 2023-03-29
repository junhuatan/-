package com.liu.org.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.liu.org.pojo.User;
import org.springframework.stereotype.Repository;

/**
* @author kiwi
* @description 针对表【user】的数据库操作Mapper
* @createDate 2022-10-27 15:11:25
* @Entity com/tan.pojo.User
*/
@Repository
public interface UserMapper extends BaseMapper<User> {

}




