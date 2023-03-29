package com.liu.org.service;

import com.liu.org.pojo.Airline;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author kiwi
* @description 针对表【airline】的数据库操作Service
* @createDate 2022-11-25 20:14:16
*/
public interface AirlineService extends IService<Airline> {

    void updateDB();
}
