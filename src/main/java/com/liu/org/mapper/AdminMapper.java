package com.liu.org.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.liu.org.pojo.Admin;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.example.po.Admin
 */


@Repository
public interface AdminMapper extends BaseMapper<Admin> {

    @Select("select count(*) from admin where u_id = #{uId} limit 1")
    Integer selectOneByUId(@Param("uId") Integer uId);
}




