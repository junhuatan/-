package com.liu.org.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.org.pojo.Airline;
import com.liu.org.service.AirlineService;
import com.liu.org.mapper.AirlineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author kiwi
* @description 针对表【airline】的数据库操作Service实现
* @createDate 2022-11-25 20:14:16
*/
@Service
public class AirlineServiceImpl extends ServiceImpl<AirlineMapper, Airline>
    implements AirlineService{

    @Autowired
    private AirlineMapper airlineMapper;

    public void updateDB() {
        String[] d = {"东方航空", "厦门航空", "中国国航", "海航旗下首都航", "四川航空", "国际航空"};
        String[] a = {"Air India", "IndiGo", "Jet Airways", "SpiceJet", "Vistara", "Multiple carriers"};

        for (int i = 0; i < d.length; i++) {
            UpdateWrapper<Airline> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("airline_name", d[i]).eq("airline_name", a[i]);
            airlineMapper.update(null, updateWrapper);
        }
    }
}




