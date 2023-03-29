package com.liu.org.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.org.pojo.Airplane;
import com.liu.org.service.AirplaneService;
import com.liu.org.mapper.AirplaneMapper;
import org.springframework.stereotype.Service;

/**
* @author kiwi
* @description 针对表【airplane】的数据库操作Service实现
* @createDate 2022-11-09 21:51:25
*/
@Service
public class AirplaneServiceImpl extends ServiceImpl<AirplaneMapper, Airplane>
    implements AirplaneService{

}




